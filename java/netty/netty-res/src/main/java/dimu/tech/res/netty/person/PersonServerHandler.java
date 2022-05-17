package dimu.tech.res.netty.person;

import dimu.tech.res.netty.pojo.Person;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class PersonServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        String msg = "server receive connection from client!" + new Date();
        System.out.println(msg);
        /**
         * 输出string对象必须转换为bytebuf，如果后续没有outbound进行转换，就会隐式的报类型不匹配错误。
         * 例如输出字符串，就需要后续使用StringEncoder进行转换输出。
         */
        ctx.writeAndFlush(msg + " now.");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Person person = (Person) msg;
        System.out.println("server handler recieve:" + person.toString());
        System.out.println(ctx.channel().toString());
        ctx.write("Server has processed the message" + new Date());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server process over, flush response!");
        System.out.println(ctx.channel().toString());
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}