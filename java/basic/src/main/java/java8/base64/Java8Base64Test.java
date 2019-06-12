package java8.base64;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

import org.junit.Test;

/**
 * description: Base64类加解密
 *
 * @version 2017年5月4日 上午10:52:23
 * @see modify content------------author------------date
 */
public class Java8Base64Test {

	@Test
	public void arrayTest() {
		try {

			// Encode using basic encoder
			String base64encodedString = Base64.getEncoder().encodeToString("java8%".getBytes("utf-8"));
			System.out.println("Base64 Encoded String (Basic) :" + base64encodedString);

			// Decode !SRQOlRdOgTkOfYYOfIKOgIhOgOIOffg
			byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);

			System.out.println("Original String: " + new String(base64decodedBytes, "utf-8"));
			base64encodedString = Base64.getUrlEncoder().encodeToString("java8%".getBytes("utf-8"));
			System.out.println("Base64 Encoded String (URL) :" + base64encodedString);
			
			base64encodedString = Base64.getMimeEncoder().encodeToString("java8%".getBytes());
			System.out.println("Base64 Mime Encode :"  + base64encodedString);

			StringBuilder stringBuilder = new StringBuilder();

			for (int i = 0; i < 10; ++i) {
				stringBuilder.append(UUID.randomUUID().toString());
			}

			byte[] mimeBytes = stringBuilder.toString().getBytes("utf-8");
			String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
			System.out.println("Base64 Encoded String (MIME) :" + mimeEncodedString);
			
			System.out.println(new String(Base64.getMimeDecoder().decode(mimeEncodedString),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error :" + e.getMessage());
		}
	}
}
