package dimu.tech.res.mongodb.entity;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.bson.BSONObject;
import org.bson.BsonDocument;
import org.bson.BsonValue;

/**
 * 历史属性保存表
 */
@Data
public class FunctionPoint {

	private Object pid;

	private Long did;

	private Integer fid;

	private Date at;

	private Object v;

}
