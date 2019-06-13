package dimu.ssm.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * 自定义的spring security user 对象，需要扩展
 * @author dwx
 *
 */
public class CustomUser extends User implements CustomUserDetails{

	private static final long serialVersionUID = 1L;

	private Integer id;
    
    private String email;
    
    //用户类型
    private String userType;
    
    //自定义盐值
    private String salt;

    //可以访问的菜单集合
    private List<String> actionUrls;
    
    //权限keys集合
    private List<String> permissions;
    
    private String phone;

	private String usable;

	private String roleName;

	private Integer roleId;
    

    public CustomUser(String loginName,
                      String password,
                      boolean enabled,
                      boolean accountNonExpired,
                      boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities, 
                      String userType,
                      String usable) {
        super(loginName,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
        this.userType = userType;
        this.usable = usable;
       
    }

    @Override
	public Integer getUserId() {
		return this.getId();
	}

	@Override
	public String getAccount() {
		return this.getUsername();
	}


    @Override
    public List<String> getActionUrls() {
        return this.actionUrls;
    }


    @Override
    public List<String> getPermissions() {
        return this.permissions;
    }

    @Override
    public String getEmail() {
        return this.email;
    }


    @Override
    public String getSalt() {
        return this.salt;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }


    public void setUserType(String userType) {
        this.userType = userType;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getUsable() {
        return usable;
    }


    public void setUsable(String usable) {
        this.usable = usable;
    }


    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public Integer getRoleId() {
        return roleId;
    }


    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setSalt(String salt) {
        this.salt = salt;
    }


    public void setActionUrls(List<String> actionUrls) {
        this.actionUrls = actionUrls;
    }


    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    
    

}
