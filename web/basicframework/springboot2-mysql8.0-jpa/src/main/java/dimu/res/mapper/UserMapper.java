package dimu.res.mapper;

import java.util.List;
import java.util.Map;

import dimu.res.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 
 * @author dimu
 *
 */
@Mapper
public interface UserMapper {

    /**
     * use annotation method not xml 
     * @param name1 user name
     * @return the selected name
     */
    @Select("select * from user where account = #{name}")
    User findByName(@Param("name") String name1);
    
    /**
     * insert element by entity object
     * @param user user object
     * @return insert success element num
     */
    @Insert("insert into user(account, password) values (#{user.account}, #{user.password})")
    int insert(@Param("user") User user);
    
    /**
     * delete element from single table by condition
     * @param account 账号
     * @return deleted element count
     */
    @Delete("delete from user where account=#{account}")
    int deleteByAccount(@Param("account") String account);

    /**
     * annotation page query
     * @param pageSize page no. start from 0
     * @param offset offset value
     * @param queryMap query condition map
     * @return search result
     */
    @Select("select * from user limit #{pageSize} offset #{offset}")
    List<User> pageQuery(@Param("pageSize") int pageSize, @Param("offset") int offset, Map<String, Object> queryMap);
}
