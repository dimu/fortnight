/**
 * 
 */
package dimu.ssm.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dimu.ssm.mapper.UserMapper;
import dimu.ssm.model.User;

/**
 * common single operate
 * @author dwx
 *
 */
@RestController
@RequestMapping("/usr")
public class UserController {
    
    /**
     * user table mapper instance
     */
    @Resource
    private UserMapper userMapper;

    /**
     * insert operation
     * @param user the parameter object include database value 
     * @return the success insert element numbers
     */
    @PutMapping("/insert")
    public int insertUser(@RequestBody User user) {
        return userMapper.insert(user);
    }
    
    /**
     * query by user account
     * @param name account parameter
     * @return the user object return by sql query
     */
    @GetMapping("/find/{name}")
    public User getUserByName(@PathVariable("name") String name) {
        return userMapper.findByName(name);
    }
    
    /**
     * delete element by query condition
     * @param name account 
     * @return the deleted element number
     */
    @DeleteMapping("/delete/{account}")
    public int deleteByUserAccount(@PathVariable("account") String name) {
        return userMapper.deleteByAccount(name);
    }
    
    /**
     * single table page query
     * @param pageNo start page
     * @param pageSize page size
     * @param queryMap query parameter
     * @return query list 
     */
    @GetMapping("/list")
    public List<User> pageQuery(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize,
               Map<String, Object> queryMap) {
        return userMapper.pageQuery(pageSize, (pageNo-1)*pageSize, queryMap);
    }
    
}
