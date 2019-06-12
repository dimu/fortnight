package dwx.res.hadoop.hbase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dwx.res.hadoop.hbase.parser.HWParserThread;


/**
 * 解析主程序
 * @author dwx
 * 2016年10月13日
 *
 */
public class MRParserWithDOMAndHbaseApp {
	
	public static volatile List<File> fileList= new ArrayList<File>();
	public static int count = 0;
	
	public static synchronized File popFile() {
		File file = null;
		if (count < fileList.size()) {
			System.out.print("file num:" + count);
			file = fileList.get(count++);
			System.out.println(" file name:" + file.getName());
		}
		
		return file;
	}

	/**
	 * main application entrance
	 * @param args input arguments
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("please  input mro directory path!");
			return;
		}
		
		String directory = args[0];
		File file = new File(directory);
		if (!file.isDirectory()) {
			System.out.println("please input directory path not file name");
			return;
		}
		
		File[] files = file.listFiles();
		for (final File em : files) {
			if (em.getName().contains("MRO")) {
				fileList.add(em);
			}
		}
		
		//开辟10个线程
		for (int i = 0; i < 30; i++) {
			new Thread(new HWParserThread()).start();
		}
		
	}

}
