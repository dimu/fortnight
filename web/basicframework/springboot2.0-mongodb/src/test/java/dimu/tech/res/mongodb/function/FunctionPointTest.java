package dimu.tech.res.mongodb.function;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import dimu.tech.res.mongodb.MongoDBApplication;
import dimu.tech.res.mongodb.entity.FunctionPoint;
import org.bson.BSON;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.conversions.Bson;
import org.bson.json.JsonReader;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试mongodb 写入Json数据
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class FunctionPointTest {

	@Resource
	private MongoTemplate mongoTemplate;

	@Test
	public void saveFunction() {

		FunctionPoint entity = new FunctionPoint();

		entity.setPid(123);
		entity.setDid(1L);
		entity.setFid(1);
		entity.setAt(Calendar.getInstance().getTime());

//		String json = "12344";
//		String json = JSONObject.toJSONString(true);
//		String json = JSONObject.toJSONString(9888L);
//		String json  = "[{\"id\":1001,\"name\":\"Jobs\"}]";
		String json = JSONObject.toJSONString(0xfffffffffL);


//		BsonValue document = BsonDocument.parse(json);

		entity.setV(JSONObject.parse(json));
//		document.is
		//此方法时解码二进制格式为bson的数据
//		entity.setValue(BSON.decode(json.getBytes()));

		mongoTemplate.save(entity, "st01_202005");
		mongoTemplate.save(entity, "st02_202005");
	}
}
