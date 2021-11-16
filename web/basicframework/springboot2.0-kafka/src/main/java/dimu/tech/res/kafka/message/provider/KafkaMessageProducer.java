package dimu.tech.res.kafka.message.provider;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParser;
import dimu.tech.res.kafka.model.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * kafka message producer
 * @author dwx
 *
 */
@Component
public class KafkaMessageProducer {

    private static volatile int count = 0;

    @Resource
    private KafkaTemplate<String, Object> kafkaTempalte;

    @Resource
    private Executor poolTaskExecutor;

    public void produceMessage() {
        for (; ; ) {
        poolTaskExecutor.execute(() -> {
            kafkaTempalte.send("kafka-test", randomUser());
//            System.out.println(Thread.currentThread().getName() + " producer count " + count++);
        });
        }
    }

    public void producePropertyMessage() {
//        for (; ; ) {
            poolTaskExecutor.execute(() -> {
                kafkaTempalte.send("tm-property", morkPropertyMessage());
                System.out.println(Thread.currentThread().getName() + " producer count " + count++);
            });
//        }
    }

    public void produceEventMessage() {
        for (; ; ) {
            poolTaskExecutor.execute(() -> {
                kafkaTempalte.send("tm-event", morkEventMessage());
                System.out.println(Thread.currentThread().getName() + " producer count " + count++);
            });
        }
    }

    public void produceCmdMessage() {
//        for (; ; ) {
            poolTaskExecutor.execute(() -> {
                kafkaTempalte.send("tm-setPropertiesReply", morkCmdMessage());
                System.out.println(Thread.currentThread().getName() + " producer count " + count++);
            });
//        }
    }

    public void produceGetCmdMessage() {
//        for (; ; ) {
        poolTaskExecutor.execute(() -> {
            String str = "{\"inner\":{\"ipid\":100123,\"idid\":100381,\"traceId\":\"614030c1fd4c11eab9dbfb5563b771b8\",\"spanId\":\"614030c0fd4c11eab9dbfb5563b771b8\"},\"pid\":\"Cm71w50gt7xL9OJP\",\"uid\":\"37901\",\"devName\":\"voice1-A04\",\"request\":{\"id\":\"11\",\"version\":\"1.0\",\"params\":[\"$OneNET_LBS_WIFI\"]},\"response\":{\"id\":\"11\",\"code\":200,\"msg\":\"success\",\"data\":{\"$OneNET_LBS_WIFI\":{\"mmac\":\"tanzhao\",\"macs\":\"tanzhao\",\"idfa\":\"tanzhao\",\"serverip\":\"tanzhao\",\"smac\":\"tanzhao\",\"imsi\":\"tanzhao\"}}},\"stime\":1600831507929,\"rtime\":1600831508952}";
            kafkaTempalte.send("tm-getPropertiesReply", str);
            System.out.println(Thread.currentThread().getName() + " producer count " + count++);
        });
//        }
    }

    public void procduceServiceMessage() {
        String str = "{\"inner\":{\"ipid\":10228,\"idid\":91822,\"traceId\":\"123456\",\"spanId\":\"123456\"},\"messageType\":\"call\",\"callType\":\"async\",\"pid\":\"9UaW90QeSJ\",\"uid\":\"38088\",\"devName\":\"test_device\",\"request\":{\"id\":\"49\",\"version\":\"1.0\",\"params\":{\"identifier\":\"service_test\",\"input\":{\"param_identifier1\":[0,3,10]}}},\"response\":null,\"stime\":1599639320397,\"rtime\":null}";
        poolTaskExecutor.execute(() -> {
            kafkaTempalte.send("tm-service", str);
            System.out.println(Thread.currentThread().getName() + " producer count " + count++);
        });
    }

    public String randomUser() {
        User user = new User();
        user.setAccount(UUID.randomUUID().toString().replace("-", ""));
        user.setPassword(new SecureRandom().generateSeed(8).toString());

        return JSON.toJSONString(user);

//        return "{\"id\": \"123\",\"version\": \"1.0\",\"params\": {\"Power\": {\"value\": \"on\",\"time\": 1524448722123 },\"WF\": {\"value\": 23.6,\"time\": 1524448722123}}}";

//        return "{\"inner\":{\"inPid\":25684,\"inDid\":32156,\"traceId\":\"134\",\"spanId\":\"1234\"},\"pid\":\"10086\",\"devName\":\"device003\",\"data\":{\"id\":\"123\",\"version\":\"1.0\",\"params\":{\"Power\":{\"value\":\"on\",\"time\":1524448722123},\"WF\":{\"value\":23.6,\"time\":1524448722123}}}}";
    }

    public String morkPropertyMessage() {
        return "{\"inner\":{\"ipid\":100123,\"idid\":100379,\"traceId\":\"758506a09ea0455ca372dcf3c6abeb63\",\"spanId\":null},\"pid\":\"Cm71w50gt7xL9OJP\",\"uid\":\"37901\",\"devName\":\"voice1-A02\",\"data\":{\"id\":\"1600844477174\",\"version\":\"1.0\",\"params\":{\"$OneNET_LBS_WIFI\":{\"value\":{\"imsi\":\"test\",\"serverip\":\"test\",\"macs\":\"test\",\"mmac\":\"test\",\"smac\":\"test\",\"idfa\":\"test\"},\"time\":1600844480432},\"someInt32\":{\"value\":43,\"time\":1600844480434}}},\"messageType\":\"notify\",\"notifyType\":\"property\"}";
    }

    public String morkEventMessage() {
        return "{\"inner\":{\"inPid\":25684,\"indid\":100002,\"traceId\":\"134\",\"spanId\":\"1234\"},\"pid\":\"10086\",\"devName\":\"device003\",\"type\":\"info\",\"messageType\":\"notify\",\"notifyType\":\"event\",\"data\":{\"id\":\"123\",\"version\":\"1.0\",\"params\":{\"voltage\":{\"value\":{\"Power\":\"on\",\"WF\":\"2\"},\"time\":1590108664000},\"location\":{\"value\":{\"lat\":34.1256,\"log\":130.152},\"time\":159010865400}}}}";
    }

    public String morkCmdMessage() {
        return "{\"inner\":{\"ipid\":100123,\"idid\":100381,\"traceId\":\"614030c1fd4c11eab9dbfb5563b771b8\",\"spanId\":\"614030c0fd4c11eab9dbfb5563b771b8\"},\"pid\":\"Cm71w50gt7xL9OJP\",\"uid\":\"37901\",\"devName\":\"voice1-A04\",\"request\":{\"id\":\"11\",\"version\":\"1.0\",\"params\":[\"$OneNET_LBS_WIFI\"]},\"response\":{\"id\":\"11\",\"code\":200,\"msg\":\"success\",\"data\":{\"$OneNET_LBS_WIFI\":{\"mmac\":\"tanzhao\",\"macs\":\"tanzhao\",\"idfa\":\"tanzhao\",\"serverip\":\"tanzhao\",\"smac\":\"tanzhao\",\"imsi\":\"tanzhao\"}}},\"stime\":1600831507929,\"rtime\":1600831508952}";
//        return "{\"inner\":{\"ipid\":123455,\"idid\":100002,\"traceId\":\"134\",\"spanId\":\"1234\"},\"pid\":\"10086\",\"devName\":\"device003\",\"data\":{\"id\":\"123\",\"version\":\"1.0\",\"params\":{\"Power\":{\"value\":\"off\",\"time\":1590739130000},\"WF\":{\"value\":23.6,\"time\":1590739130000}}},\"sTime\":1590739130856,\"resp\":{\"id\":\"123\",\"code\":1,\"msg\":\"xxxx\"},\"rTime\":1590739130000}";
    }

}
