package dimu.tech.res.mongodb.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 班级表
 */
@Data
@Document("class")
public class StuClass {

	@Field("name")
	private String name;

	@Field("total_student")
	private Integer totalStudent;

	@Field("grade")
	private Integer grade;

	@Field("teacher")
	private String teacher;
}
