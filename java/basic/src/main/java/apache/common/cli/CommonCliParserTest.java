package apache.common.cli;

import java.util.Date;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * common-cli 命令行解析工具研究
 * @author dwx
 * 2016年12月13日
 *
 */
public class CommonCliParserTest {
	
	public static void main(String[] args) throws ParseException {
		// create Options object,Options参数，option中定义可以输入的参数
		Options options = new Options();

		// add t option
		options.addOption("t", "time", true, "display current time");
		options.addOption("h", "help", false, "display help information");
		
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);
		
		if (cmd.hasOption("t")) {
			String time = cmd.getOptionValue("t");
			System.out.println(time);
			System.out.println(new Date());
		} else {
			System.out.println("not display current time!");
		}
		
		//输出所有的options帮助文档
		if (cmd.hasOption("h")) {
			 HelpFormatter hf = new HelpFormatter(); 
			 hf.printHelp("Options", options); 
		}
	}
}
