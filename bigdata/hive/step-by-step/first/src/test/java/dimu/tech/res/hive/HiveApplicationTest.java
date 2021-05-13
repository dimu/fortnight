package dimu.tech.res.hive;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * hive 基本操作测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HiveApplicationTest {

	@Resource
	@Qualifier("hiveDruidTemplate")
	private JdbcTemplate jdbcTemplate;

	@Test
	public void contextLoads() {

	}

	/**
	 * 数据查询
	 */
	@Test
	public void queryData() {
		String sql = "select * from person1 where age=32";
		System.out.println(jdbcTemplate.queryForObject(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int i) throws SQLException {
				return resultSet.getInt(1) + ":" + resultSet.getString(2) + ":" + resultSet.getInt(3);
			}
		}));
	}

	/**
	 * 创建表-包含基本字段名
	 */
	@Test
	public void createTable() {
		String sql = "CREATE  TABLE IF NOT EXISTS test(idx int, sex string) ";
		jdbcTemplate.execute(sql);
	}

	/**
	 * 单条插入
	 */
	@Test
	public void singleInsert() {
		String sql = "insert into test values(1, \"female\")";
		jdbcTemplate.execute(sql);
	}

	/**
	 * `删除表
	 */
	@Test
	public void dropTable() {
		String dropSql = "drop table if exists test";
		jdbcTemplate.execute(dropSql);
	}

	/**
	 * 清空表
	 */
	@Test
	public void truncateTable() {
		String sql = "truncate table test ";
		jdbcTemplate.execute(sql);
	}

	/**
	 * 创建视图
	 */
	@Test
	public void createView() {
		String sql = "create view testview (sex) as select sex from test";
		jdbcTemplate.execute(sql);
	}

	/**
	 * 删除视图
	 */
	@Test
	public void dropView() {
		String sql = "drop view testview";
		jdbcTemplate.execute(sql);
	}

}
