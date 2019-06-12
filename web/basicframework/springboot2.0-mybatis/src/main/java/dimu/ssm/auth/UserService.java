package dimu.ssm.auth;

import java.util.List;

public interface UserService {

    /**
     * 根据登录名查找用户
     *
     * @param _loginName
     * @return
     */
    CustomUserDetails findUserByLoginName(String _loginName);

    /**
     * 更新用户权限
     *
     * @param _userId
     * @param _authorities
     */
    default void updateAuthorities(Integer _userId, List<String> _authorities) {
        
    };

}
