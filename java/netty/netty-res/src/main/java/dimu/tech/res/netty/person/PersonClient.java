package dimu.tech.res.netty.person;

import dimu.tech.res.netty.pojo.Person;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;


/**
 * mock client，use thread scheduler to send person message to person server
 */
public class PersonClient {
    public static void main(String[] args) throws Exception {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new StringDecoder(), new PersonEncoder(), new PersonClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", 8081).sync();
            /**
             * 通过chanel绑定的时间循环来定时执行任务
             */
            f.channel().eventLoop().scheduleAtFixedRate((Runnable) () -> {
                try {
                    Person person = new Person();
                    person.setId(SecureRandom.getInstanceStrong().nextLong());

                    person.setAge(SecureRandom.getInstanceStrong().nextInt(100));
                    person.setName("dimu");
                    f.channel().writeAndFlush(person);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }, 0,10, TimeUnit.SECONDS);

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
