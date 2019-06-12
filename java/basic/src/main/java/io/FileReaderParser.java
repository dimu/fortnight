package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderParser {

	public static void main(String[] args) throws IOException {
		String si = "<si>";
		String six = "</si>";
		File file = new File("C:\\Users\\dwx\\Desktop\\sharedStrings.xml");
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(file));
		int line = 1;
		String tmp = null;
		int evn = 0;
		int position = 0;
		int oldposition = 0;
		int type=0;
		
		 while ((tmp = br.readLine()) != null) {
             // 显示行号
			 position = tmp.indexOf(si);
			 if (position != -1) {
				 if(evn /2 ==0) {
					 evn++;
				 } else {
					 System.out.println("line:" + line + "posit");
				 }
			 } 
             line++;
         }
		
	}

	class Xs {
		int row;
		int column;
		int value;
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public int getColumn() {
			return column;
		}
		public void setColumn(int column) {
			this.column = column;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
		
	}
}
