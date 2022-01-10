package json;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * fastjson序列化与反序列化测试
 *
 * @author dwx
 */
@Data
public class PersonVo {

    @JSONField(ordinal = 1)
    private String name;

    @JSONField( ordinal = 0)
    private Integer age;

    @JSONField(serialize = false, ordinal = 2)
    private ClassMate mate;

    @Data
    public static class ClassMate {
        @JSONField( ordinal = 1)
        private String name;

        @JSONField( ordinal = 0)
        private Boolean sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getSex() {
            return sex;
        }

        public void setSex(Boolean sex) {
            this.sex = sex;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ClassMate getMate() {
        return mate;
    }

    public void setMate(ClassMate mate) {
        this.mate = mate;
    }
}
