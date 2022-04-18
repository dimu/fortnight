package dim.tech.res.netty.person;

import dim.tech.res.netty.pojo.Person;
import io.netty.channel.*;

import java.security.SecureRandom;

/**
 * @author dwx
 */
public class PersonClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(ctx.channel().toString());
        System.out.println("person client received " + msg.toString());
//        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
