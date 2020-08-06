package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InputStream {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			//如何读取文件的字节
			FileInputStream  fis = new FileInputStream(new File("C:\\Users\\dwx\\Desktop\\2.csv"));
			byte[] b= new byte[fis.available()];
			fis.read(b); 
			System.out.println(new String(b));
			System.out.println(new String(b, "GBK"));
			System.out.println(new String(new String(b, "GB2312").getBytes("utf8")));
			for(byte e : b){
				System.out.println(e);
			}
			
 		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

}
 
