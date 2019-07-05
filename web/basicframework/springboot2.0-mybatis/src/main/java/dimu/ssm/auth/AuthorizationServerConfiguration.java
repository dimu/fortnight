package dimu.ssm.auth;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务器配置,自定义授权服务器
 * @author dwx
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

    @Resource
    private DataSource datasource;
    
    @Resource
    private JedisConnectionFactory jedisConnectionFactory;
    
    @Resource
    private TokenStore redisTokenStore;
    
    //注入spring security 管理的鉴权管理器
    @Resource
    private AuthenticationManager authenticationManager;
    
    @Resource
    private UserDetailsService userDetailsService;
    
    /**
     * 采用redis内存数据库存储token
     * @return
     */
    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(jedisConnectionFactory);
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("")
                .authorizedGrantTypes("passoword")
                .scopes("read", "write")
                .secret("front");
    }
    

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(redisTokenStore)
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
    }

    /**
     * 生成鉴权管理器对象，需要注入鉴权provider list
     * @return spring托管的对象
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> list =  new ArrayList<AuthenticationProvider>();
        list.add(customAuthenticationProvider());

        return new ProviderManager(list);
    }

    /**
     * 返回自定义的鉴权类，供鉴权管理器AuthenticationManager使用
     * @return spring管理的对象
     */
    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    /**
     * 生成用户获取服务类，需要注入最终调用的service类
     * @param userService spring单例生成的对象
     * @return spring托管的获取用户细节的单例
     */
    @Bean
    public UserDetailsService customUserDetailsService(UserService userService) {
        return  new CustomUserDetailsService(userService);
    }
}
