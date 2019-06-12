package io;

import java.util.zip.*;

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.junit.Test;

import java.io.*;

/**
 * gzip compress file
 * 
 * @author dwx 2016��9��30��
 *
 */
public class GZIPcompress {

	public static void main(String[] args) throws IOException {
		// input file path as argument
		if (args.length == 0) {
			System.out.println(
					"Usage: \nGZIPcompress file\n" + "\tUses GZIP compression to compress " + "the file to test.gz");
			System.exit(1);
		}

		// can only gzip file, directory is not supported, buffered is more
		// efficient
		BufferedReader in = new BufferedReader(new FileReader(args[0]));
		BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("test.gz")));
		System.out.println("Writing file");
		int c;
		while ((c = in.read()) != -1) {
			out.write(c);
		}
		in.close();
		out.close();
		System.out.println("Reading file");
		BufferedReader in2 = new BufferedReader(
				new InputStreamReader(new GZIPInputStream(new FileInputStream("test.gz"))));
		String s;
		while ((s = in2.readLine()) != null) {
			System.out.println(s);
		}
	}

	@Test
	public void ZipArchive() throws IOException {
		byte[] input = "abc".getBytes();
		OutputStream zp = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream("d:\\testg.gz")));
		zp.write(input);
		zp.flush();
		zp.close();
	}
}
