package dimu.ssm.auth;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 扩展用户信息功能
 * @author dwx
 *
 */
public interface CustomUserDetails extends UserDetails {

	//获取菜单Urls
	List<String> getActionUrls();
	
	//获取所有的权限字段
	List<String> getPermissions();
	
	//获取用户主键信息
	Integer getUserId();
	
	//获取邮箱地址
	String getEmail();
	
	//获取账号信息
	String getAccount();
	
	//获取盐值
	String getSalt();
}
