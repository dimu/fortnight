package dimu.res.config.mapper;

import javax.annotation.Resource;

import dimu.res.Application;
import dimu.res.mapper.LgUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * lg user mapper test
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class LgUserMapperTest {

	@Resource
	private LgUserMapper lgUserMapper;

	/**
	 * 测试查询所有用户
	 */
	@Test
	public void queryAllUser() {
		System.out.println(lgUserMapper.queryAllUser().size());
	}
}
