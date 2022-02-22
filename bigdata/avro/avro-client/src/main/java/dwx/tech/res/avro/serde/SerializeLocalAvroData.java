package dwx.tech.res.avro.serde;

import dwx.tech.avro.schema.Person;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

/**
 * avro basic research, serialization avro data into local file.
 *
 * @author dwx
 * @date 2022-02-14
 */
public class SerializeLocalAvroData {

    /**
     * 序列化到本地文件
     * @throws IOException
     */
    public static void testSerializeToLocalFile() throws IOException {
        Person p1 = new Person();
        p1.setId(1);
        p1.setUsername("mrscarter");
        p1.setFirstName("Beyonce");
        p1.setLastName("Knowles-Carter");
        p1.setBirthdate("1981-09-04");
        p1.setJoinDate("2016-01-01");
        p1.setPreviousLogins(10000);

        Person p2 = new Person();
        p2.setId(2);
        p2.setUsername("jayz");
        p2.setFirstName("Shawn");
        p2.setMiddleName("Corey");
        p2.setLastName("Carter");
        p2.setBirthdate("1969-12-04");
        p2.setJoinDate("2016-01-01");
        p2.setPreviousLogins(20000);

        File avroOutput = new File("person-test.avro");
        DatumWriter<Person> personDatumWriter = new SpecificDatumWriter<Person>(Person.class);
        DataFileWriter<Person> dataFileWriter = new DataFileWriter<Person>(personDatumWriter);
        dataFileWriter.create(p1.getSchema(), avroOutput);
        dataFileWriter.append(p1);
        dataFileWriter.append(p2);
        dataFileWriter.close();
    }

    /**
     * 反序列化本地生成的avro文件
     * @throws IOException
     */
    public static void testDeserializeFromAvroFile() throws IOException {
        File file = new File("person-test.avro");
        DatumReader<Person> datumReader = new SpecificDatumReader<>(Person.class);
        DataFileReader<Person> dataFileReader = new DataFileReader<Person>(file,datumReader);
        Person person;
        while (dataFileReader.hasNext()) {
            person = dataFileReader.next();
            System.out.println(person);
        }
    }
}
