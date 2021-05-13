package dimu.tech.res.hive.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * random utils
 * @author dwx
 */
public class RandomUtils {

	private static final char[] lowers =
			"abcdefghijklmnopqrstuvwxyz".toCharArray();

	public static int rangeInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max+1);
	}

	public static String rangeStr(int size) {

		char[] buffer = new char[size];
		for (int i = 0; i < size; i++) {
			buffer[i] = lowers[ThreadLocalRandom.current().nextInt(lowers.length)];
		}

		return new String(buffer);
	}

}
