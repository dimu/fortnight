package dimu.ssm.auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 自定义web securityonfigurer 适配器
 * @author dwx
 *
 */
@EnableWebSecurity
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter{

    /**
     * 重写httpSecurity 安全控制
     */
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
                .antMatchers("/css/**", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin().and()
            .httpBasic();
    }
}
