package dimu.tech.res.mongodb.operation;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import dimu.tech.res.mongodb.MongoDBApplication;
import dimu.tech.res.mongodb.entity.Student;
import dimu.tech.res.mongodb.utils.RandomUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
		String jsonString = "{\"name\": \"dimu\", \"age\":16.0, \"id\": \"123\"}";
		mongoTemplate.insert(jsonString, "person");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "dimu");
		jsonObject.put("age", 17);
		jsonObject.put("id", 456);
		mongoTemplate.insert(jsonObject, "person");
	}


	/**
	 * single insert
	 */
	@Test
	public void singleInsert() {

		Student student = new Student();
		float ft = new BigDecimal("15.21").floatValue();
		System.out.println(ft);
		student.setAge(ft);
		student.setSex(RandomUtils.rangeInt(0, 1) == 1);
		student.setName(RandomUtils.rangeStr(3));
		student.setClassId(new ObjectId("5eb8b136ca4a7e08f5b0c839"));
		/**
		 * mongotemplate 默认将id保存为_id，可以使用@Field注解来指定保存的名字
		 */
		student.setId("124");

		mongoTemplate.insert(JSONObject.toJSONString(student), "person");
	}

	/**
	 * batch insert
	 */
	@Test
	public void batchInsert() {

		List<Student> list = new ArrayList<>();

		for (int i = 0; i < 1000000; i++) {
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
	public void pageQuery() {
		long start = System.currentTimeMillis();
		System.out.println(mongoTemplate.count(null, "person"));
		long end = System.currentTimeMillis();

		System.out.println("query time: " + (end - start));
	}

	@Test
	public void randomStr() {
		System.out.println(RandomUtils.rangeStr(3));
	}

	@Test
	public void documentQuery() {
		Query query = new Query();
		List<Document> listDocument = mongoTemplate
				.find(query.addCriteria(new Criteria().and("name").is("dimu")), Document.class, "person");
		listDocument.forEach(document -> {
			System.out.println(document.getObjectId("_id"));
			System.out.println(document.getString("_id"));
			System.out.println(document.toJson());
		});
	}

	@Test
	public void stringQuery() {
		Query query = new Query();
		String str = mongoTemplate
				.findOne(query.addCriteria(new Criteria().and("name").is("dimu")), String.class, "person");
		System.out.println(str);
	}
}
