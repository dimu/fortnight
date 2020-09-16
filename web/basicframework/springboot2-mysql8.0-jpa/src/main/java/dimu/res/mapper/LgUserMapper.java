package dimu.res.mapper;

import java.util.List;

import dimu.res.model.LgUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * lg user mapper interface
 *
 * @author dwx
 * @date 2020-09-16
 */
@Mapper
public interface LgUserMapper {

	/**
	 * 查询所有用户
	 * @return 返回用户表数据
	 */
	@Select("select id, openid, wx_name as wxName from inner_user")
	List<LgUser> queryAllUser();


	@Update({
			"<script>",
			"<foreach collection='lgUserList' item='item' index='index' separator=';'>",
			"UPDATE inner_user SET ",
			"real_name=#{item.realName}",
			"WHERE id=#{item.id}",
			"</foreach>",
			"</script>"
	})
	void updateRealName(List<LgUser> lgUserList);
}
