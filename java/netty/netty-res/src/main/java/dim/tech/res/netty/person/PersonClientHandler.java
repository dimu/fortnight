package dim.tech.res.netty.person;

import dim.tech.res.netty.pojo.Person;
import dim.tech.res.netty.pojo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class PersonClientHandler extends ChannelInboundHandlerAdapter {
//    @Override
//    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {
//        out.writeLong(msg.getId());
//        out.writeInt(msg.getName().length());
//        out.writeBytes(msg.getName().getBytes(StandardCharsets.UTF_8));
//        out.writeInt(msg.getAge());
//    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Person person = new Person();
        person.setId(SecureRandom.getInstanceStrong().nextLong());
        person.setAge(SecureRandom.getInstanceStrong().nextInt(100));
        person.setName("dimu");
        System.out.println("client handler send message:" + person.toString());
        ctx.writeAndFlush(person);
//        ctx.write(person);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("person client received " + msg.toString());
//        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
