package io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BufferAndNoBuffer {
	
	static String file = "D:\\attl_ca_host_1393858547425.xls";
	
	public static void main(String args[]){
		readUseInputStreamNoBuffer();
		readUseInputStreamWithBuffer();
		readUseInputStreamWithBufferReader();
		
	}
	
	public static void readUseInputStreamNoBuffer() {
		long start = System.currentTimeMillis();
		for (int j = 0; j < 10000; j++) {
			File file1 = new File(file);
			InputStream in = null;
//			OutputStream out = new FileOutputStream(new File("D:\\noBuffer.xls"));
			try {
				in = new FileInputStream(file1);
				byte[] b = new byte[1024];
				int i = 0;
	
				while ((i = in.read(b)) > 0) {
				}
			} catch(IOException e) {
				
			}
		}
		long end = System.currentTimeMillis();
		
		System.out.println("read with no buffer:" + (end-start));
	}
	
	public static void readUseInputStreamWithBuffer() {
		long start = System.currentTimeMillis();
		for (int j = 0; j < 10000; j++) {
			File file1 = new File(file);
			InputStream in = null;
			BufferedInputStream bufIn = null;
			try {
				in = new FileInputStream(file1);
				bufIn = new BufferedInputStream(in);
				
				byte[] b = new byte[1024];
				int i = 0;
	
				while ((i = bufIn.read(b)) > 0) {
				}
			} catch(IOException e) {
				
			}
		}
		long end = System.currentTimeMillis();
		
		System.out.println("read with  buffer:" + (end-start));
	}
	
	public static void readUseInputStreamWithBufferReader() {
		long start = System.currentTimeMillis();
		for (int j = 0; j < 10000; j++) {
			File file1 = new File(file);
			FileReader in = null;
			BufferedReader bufIn = null;
			try {
				in = new FileReader(file1);
				bufIn = new BufferedReader(in);
				
				int i = 0;
	
				while ((i = bufIn.read()) > 0) {
				}
			} catch(IOException e) {
				
			}
		}
		long end = System.currentTimeMillis();
		
		System.out.println("read with  buffer reader:" + (end-start));
	}


}
