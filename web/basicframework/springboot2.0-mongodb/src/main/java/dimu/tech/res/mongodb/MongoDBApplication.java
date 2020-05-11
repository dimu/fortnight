package dimu.tech.res.mongodb;

import javax.annotation.Resource;

import dimu.tech.res.mongodb.entity.StuClass;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * SpringBoot2.0+集成MongoDB4.0+
 *
 * @author dwx
 */
@SpringBootApplication
public class MongoDBApplication {

    @Resource
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MongoDBApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        StuClass stuClass = new StuClass();
//        stuClass.setGrade(1);
//        stuClass.setName("1年级4班");
//        stuClass.setTeacher("lily");
//        stuClass.setTotalStudent(32);
//
//        mongoTemplate.save(stuClass);
//    }
}






























