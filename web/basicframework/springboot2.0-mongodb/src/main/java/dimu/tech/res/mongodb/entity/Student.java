package dimu.tech.res.mongodb.entity;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * student entity
 *
 * @author dwx
 */
@Data
public class Student {

	private String name;

	private Object age;

	private Boolean sex;

	private ObjectId classId;

	private String id;

}
