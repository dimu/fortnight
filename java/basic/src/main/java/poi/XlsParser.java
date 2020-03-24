package poi;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 问卷调查数据解析入库
 * @author dwx
 */
public class XlsParser {

    public static final String DIRECTORY = "D:\\ny\\hengdm\\text data2";
    public static final String DIRECTORY1 = "D:\\ny\\hengdm\\text data3";
    public static final Pattern PATTERN = Pattern.compile("(.*)(?:_)(.*)(?:.xls)");
    public static final Table<String, Integer, String> employeeTable = HashBasedTable.create();

    public static Map<String, Integer> compareMap = new HashMap<String, Integer>(2048);
    public static Map<String, String> fileMap = new HashMap<String, String>(2048);
    public static int count = 0;

    public static void main(String[] args) {
        File parentDirectory = new File(DIRECTORY);
        File[] childrenFiles = parentDirectory.listFiles();

        for (int i = 0; i < childrenFiles.length; i++) {
            File item = childrenFiles[i];
            parserExceltoDB1(item);
            System.out.println("文本解析完第" + (i + 1) + "个文件");
        }

        File parentDirectory1 = new File(DIRECTORY1);
        File[] childrenFiles1 = parentDirectory1.listFiles();


        for (int i = 0; i < childrenFiles1.length; i++) {
            File item = childrenFiles1[i];
            parserExceltoDB1(item);
            System.out.println("数值解析完第" + (i + 1) + "个文件");
        }


        fileMap.forEach((key, value) -> {
            String[] files = value.split("#");
            if (files.length != 2) {
                System.out.println(key + "数据异常" + value);
            }
            String wordStr = files[0];
            String numStr = files[1];
            FileInputStream wordIs = null;
            FileInputStream numIs = null;
            try {
                wordIs = new FileInputStream(wordStr);
                numIs = new FileInputStream(numStr);

                System.out.printf("解析第%d对数据\n", count++);
                Workbook numWb = new HSSFWorkbook(numIs);
                Sheet numSheet = numWb.getSheetAt(0);

                Workbook wordWb = new HSSFWorkbook(wordIs);
                Sheet wordSheet = wordWb.getSheetAt(0);

                int rowTotal = numSheet.getRow(0).getPhysicalNumberOfCells();
                System.out.println(numStr + "包含列数：" + rowTotal);

                //获取sheet最大列数
                if (numSheet.getPhysicalNumberOfRows() != wordSheet.getPhysicalNumberOfRows()) {
                    System.out.println(value + "两者不匹配");
                }

                for (int i = 1; i < numSheet.getPhysicalNumberOfRows(); i++) {
                    Row numRow = numSheet.getRow(i);
                    Cell numCell = numRow.getCell(6);
                    int columnkey = (int) numCell.getNumericCellValue();
                    Row wordRow = wordSheet.getRow(i);
                    Cell wordCell = wordRow.getCell(6);
                    String columnValue = wordCell.getStringCellValue();
                    if (columnValue.equals(employeeTable.get(key, columnkey))) {
                        continue;
                    } else {
                        employeeTable.put(key, columnkey, columnValue);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("发生错误的文件是" + value);
            } finally {
                if (numIs != null) {
                    try {
                        numIs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (wordIs != null) {
                    try {
                        wordIs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        employeeTable.columnMap().forEach((key, value) -> {
            value.forEach((key1, value1) ->
                    System.out.println("医院" + key + "key" + key1 + "对应的科室为" + value1));
        });

        System.out.println("共计多少个科室" + new HashSet(employeeTable.values()).size());
    }

    public static void parserExceltoDB1(File file) {
        String fullname = file.getName();

        if (fileMap.containsKey(fullname)) {
            System.out.println("插入数值" + file.getAbsolutePath());
            fileMap.put(fullname, fileMap.get(fullname) + "#" + file.getAbsolutePath());
        } else {
            System.out.println("插入文本" + file.getAbsolutePath());
            fileMap.put(fullname, file.getAbsolutePath());
        }

    }
}
