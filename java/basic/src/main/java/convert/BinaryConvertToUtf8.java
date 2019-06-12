package convert;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

public class BinaryConvertToUtf8 {

	@Test
	public void convertStringToBytes() {
		// System.out.println((byte)0x88);
		String data = "5A3c88";
		int len = data.length() / 2;
		byte[] bytearray = new byte[len];
		for (int i = 0; i < len; i++) {
			String itemStr = data.substring(i * 2, (i + 1) * 2);
			bytearray[i] = (byte) Integer.parseInt(itemStr, 16);
			System.out.println(bytearray[i]);
		}

	}

	@Test
	public void httpClientPostByteDate() throws HttpException, IOException {
		String url = "http://api.heclouds.com/cmds?device_id=11539184&qos=0";
		PostMethod method = new PostMethod(url);
//		method.setRequestHeader("Content-Type", "application/json");
		method.setRequestHeader("api-key", "fqAr0Plx0EfEBGFhNym2Lx3g6Ks=");
//		String testStr = "A5000B5A920217127204301678330125AA";
		String testStr = "A5000C5A81000000000000000000010E90AA";
		byte[] inputArray = hexStringToBytes(testStr);
		method.setRequestEntity(new ByteArrayRequestEntity(inputArray));
		HttpClient client = new HttpClient();
		int HttpCode = client.executeMethod(method);
		if (HttpCode != HttpStatus.SC_OK)
			throw new IOException("Invalid HttpStatus: " + HttpCode);
		InputStream respStream = method.getResponseBodyAsStream();
		int respBodySize = respStream.available();
		if (respBodySize <= 0)
			throw new IOException("Invalid respBodySize: " + respBodySize);
		byte[] respBuffer = new byte[respBodySize];
		if (respStream.read(respBuffer) != respBodySize)
			throw new IOException("Read respBody Error");

	}

	public byte[] convertHexStringToBytes(String input) {
		int len = input.length() / 2;
		byte[] bytearray = new byte[len];
		for (int i = 0; i < len; i++) {
			String itemStr = input.substring(i * 2, (i + 1) * 2);
			bytearray[i] = (byte) Integer.parseInt(itemStr, 16);
		}

		return bytearray;
	}
	
	public static byte[] hexStringToBytes(String hexString) {   
	    if (hexString == null || hexString.equals("")) {   
	        return null;   
	    }   
	    hexString = hexString.toUpperCase();   
	    int length = hexString.length() / 2;   
	    char[] hexChars = hexString.toCharArray();   
	    byte[] d = new byte[length];   
	    for (int i = 0; i < length; i++) {   
	        int pos = i * 2;   
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
	    }   
	    return d;   
	}   
	
	 private static byte charToByte(char c) {   
		    return (byte) "0123456789ABCDEF".indexOf(c);   
	}  
}
