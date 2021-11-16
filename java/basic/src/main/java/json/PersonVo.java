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

    }
}
