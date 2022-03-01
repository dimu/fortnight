package dwx.tech.res.avro;

import dwx.tech.res.avro.serde.SerializeLocalAvroData;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );
        SerializeLocalAvroData.testSerializeToLocalFile();
        SerializeLocalAvroData.testDeserializeFromAvroFile();
    }
}
