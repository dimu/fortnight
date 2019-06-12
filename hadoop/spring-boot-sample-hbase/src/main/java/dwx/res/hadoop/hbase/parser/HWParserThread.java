package dwx.res.hadoop.hbase.parser;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dwx.res.hadoop.hbase.MRParserWithDOMAndHbaseApp;

/**
 * use multi thread to parser single hw mro file
 * @author dwx
 * 2016年10月13日
 *
 */
public class HWParserThread implements Runnable{

	@Override
	public void run() {
		long start = new Date().getTime();
		while (true) {
			File file = MRParserWithDOMAndHbaseApp.popFile();
			if (null == file) {
				System.out.println("解析已完成");
				long end = new Date().getTime();
				System.out.println("总共花费:" + (end-start)/1000);
				break;
			}
			
//			System.out.println(Thread.currentThread().getId());
			try {
				HWMROParser.parserHWMro(file);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			try {
//				System.out.println(Thread.currentThread().getId());
////				Thread.sleep((int)Math.random()*10000);
////				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		
		
	}
	
}
