package dim.tech.res.netty.person;

import dim.tech.res.netty.pojo.Person;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * encoder person request
 *
 * @author dimu
 * @date 2022-03-05
 */
public class PersonEncoder extends MessageToByteEncoder<Person> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {
        if (msg == null) {
            throw new Exception("person msg is null!");
        }
        System.out.println("encode received msg:" + msg.toString());
        out.writeLong(msg.getId());
        out.writeInt(msg.getName().length());
        out.writeBytes(msg.getName().getBytes(StandardCharsets.UTF_8));
        out.writeInt(msg.getAge());
        System.out.println("client encode process over!" + System.currentTimeMillis());
    }
}
