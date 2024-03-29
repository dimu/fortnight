package dimu.tech.res.netty.person;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author dwx
 */
public class PersonClientHandler extends MessageToMessageDecoder<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println(ctx.channel().toString());
//        System.out.println("person client received " + msg.toString());
//    }

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
