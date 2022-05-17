package dimu.tech.res.netty.protobuf;

import dim.tech.res.netty.protobuf.function.CurrentReq;
import dim.tech.res.netty.protobuf.function.CurrentRes;
import dim.tech.res.netty.protobuf.function.SingleFunction;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author dwx
 */
@ChannelHandler.Sharable
public class GrpcServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CurrentReq req = (CurrentReq) msg;
        System.out.println("receive request:" + req.toString());
        ctx.writeAndFlush(process(req));
    }

    private CurrentRes process(CurrentReq req) {
        CurrentRes.Builder buidler  = CurrentRes.newBuilder();
        buidler.setCode(1);
        buidler.setMsg("success");

        SingleFunction.Builder singleBuilder = SingleFunction.newBuilder();
        singleBuilder.setIdentifier("temp");
        singleBuilder.setDataType("string");
        singleBuilder.setTime(1235566L);
        singleBuilder.setValue("32");

        buidler.addData(singleBuilder.build());
        return buidler.build();
    }
}
