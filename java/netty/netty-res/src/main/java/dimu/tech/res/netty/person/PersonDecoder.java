package dimu.tech.res.netty.person;

import dimu.tech.res.netty.pojo.Person;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * decode person package
 *
 * @author dimu
 * @date 2022-03-05
 */
public class PersonDecoder extends ReplayingDecoder<PersonDecoder.State> {

    public PersonDecoder() {
        super(State.id);
    }

    /**
     * 根据输入的person encode顺序进行解压
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("step into person decoder!");
        Person person = new Person();
        switch (state()) {
            case id:
                person.setId(in.readLong());
                checkpoint(State.age);
            case name:
                int length = in.readInt();
                byte[] agebyte = new byte[length];
                in.readBytes(agebyte);
                person.setName(new String(agebyte, StandardCharsets.UTF_8));
                checkpoint(State.age);
            case age:
                person.setAge(in.readInt());
//                checkpoint(State.id);
                out.add(person);
            default:

        }

    }

    enum State {
        id,
        name,
        age;
    }
}
