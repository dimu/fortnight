package dimu.tech.res.netty.protobuf;

import dim.tech.res.netty.protobuf.function.CurrentReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GrpcClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(generateReq());
    }

    private CurrentReq generateReq() {
        CurrentReq.Builder builder = CurrentReq.newBuilder();
        builder.setDeviceName("my");
        builder.setProductId("sdfx");
        builder.setType("dsddd");

        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.flush();
    }

}
