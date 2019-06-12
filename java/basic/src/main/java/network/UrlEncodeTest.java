package network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

/**
 * description: 采用jdk中的HTTPUrlConnection发送http请求时，需要对url进行编码，确保
 * 服务端在接收请求时能够正常解析。
 *
 * @version 2017年11月6日 上午10:48:00
 * @see
 * modify content------------author------------date
 */
public class UrlEncodeTest {

	@Test
	public void urlEncodeTest() throws UnsupportedEncodingException {
		//原始数据
		String url = "[{\"mcc\":\"460\",\"mnc\":\"2\",\"lac\":\"9341\",\"cell\":\"4082\"},{\"mcc\":\"460\",\"mnc\":\"0\",\"lac\":\"9341\",\"cell\":\"3823\"},{\"mcc\":\"460\",\"mnc\":\"0\",\"lac\":\"9341\",\"cell\":\"4081\"}]";
		System.out.println(url);
		byte[] urlByte = url.getBytes();
		for (byte item : urlByte) {
			System.out.print(item + ",");
		}
		System.out.println();
		//进行url编码
		String urlEncode = URLEncoder.encode(url, "UTF-8");
		System.out.println(urlEncode);
		byte[] urlEncodeByte = urlEncode.getBytes();
		for (byte item : urlEncodeByte) {
			System.out.print(item + ",");
		}
	}
}
