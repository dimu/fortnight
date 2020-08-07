package nio.channel;


import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

/**
 * channel to channel transfer
 *
 * @author dwx
 * @date 2020-08-07
 */
public class ChannelTransfer {

    @Test
    public void channelToChannel() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\test.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        /**
         * file channel can direct transfer to a writeable channel.
         */
        fileChannel.transferTo(0, fileChannel.size(), Channels.newChannel(System.out));
        fileChannel.close();
        randomAccessFile.close();
    }
}
