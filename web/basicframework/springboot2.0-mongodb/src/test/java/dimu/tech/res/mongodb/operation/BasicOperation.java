package dimu.tech.res.mongodb.operation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import dimu.tech.res.mongodb.MongoDBApplication;
import dimu.tech.res.mongodb.entity.Student;
import dimu.tech.res.mongodb.utils.RandomUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * add mongodb basic CRUD, page query operation
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class BasicOperation {

	@Resource
	private MongoTemplate mongoTemplate;

	/**
	 * insert single document
	 */
	@Test
	public void insert() {
		String jsonString = "{\"name\": \"dimu\", \"age\":12}";
		mongoTemplate.insert(jsonString, "person");
	}

	@Test
	public void batchInsert() {

		List<Student> list = new ArrayList<>();

		for (int i = 0; i< 50; i++) {
			Student student = new Student();
			student.setAge(RandomUtils.rangeInt(7, 12));
			student.setSex(RandomUtils.rangeInt(0, 1) == 1);
			student.setName(RandomUtils.rangeStr(3));
			student.setClassId(new ObjectId("5eb8b136ca4a7e08f5b0c839"));

			list.add(student);
		}

		mongoTemplate.insert(list, "person");
	}

	@Test
	public void randomStr() {
		System.out.println(RandomUtils.rangeStr(3));
	}
}
