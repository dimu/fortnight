package dimu.tech.res.hive;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot2.0+集成MongoDB4.0+
 *
 * @author dwx
 */
@SpringBootApplication
public class HiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiveApplication.class, args);
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






























