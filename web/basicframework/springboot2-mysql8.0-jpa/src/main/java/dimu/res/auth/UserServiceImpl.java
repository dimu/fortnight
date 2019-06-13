package dimu.ssm.auth;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dimu.ssm.mapper.UserMapper;
import dimu.ssm.model.User;



@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public CustomUserDetails findUserByLoginName(String userName) {
        User user = Optional.ofNullable(userMapper.findByName(userName))
                .orElseThrow(() -> new UsernameNotFoundException("无当前用户记录"));
        
//        MeterSysRole meterSysRole = meterSysRoleRepository.findByUserId(user.getId());
//        user.setRoleName(meterSysRole.getRoleName());
//        user.setRoleId(meterSysRole.getId());
        
        return userToDtoMapper.apply(user);
    }
    
    public Function<User, CustomUserDetails> userToDtoMapper = user -> {
        CustomUserDetails customUser = new CustomUser(null, null, false, false, false, false, null, null, null);
        return customUser;
    };

}
