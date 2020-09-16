package dimu.res.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import dimu.res.mapper.LgUserMapper;
import dimu.res.model.LgUser;

import org.springframework.stereotype.Component;

/**
 * excel操作工具类
 *
 * @author dwx
 * @date 2020-09-16
 */
@Component
public class ExcelExportHelper {

	private static final String WITH = "with";

	private static final String WITHOUT = "without";

	private static final String FILE = "用户信息";

	@Resource
	private LgUserMapper lgUserMapper;

	public void downloadExcel(HttpServletResponse response) throws IOException {
		ExcelWriter excelWriter = null;
		try {
			Map<String, List<LgUser>> map = splitRealNameIsNull();

			List<LgUser> withoutList = map.get(WITHOUT);
			List<LgUser> withList = map.get(WITH);

			OutputStream outputStream = getOutputStream(response);

			excelWriter = EasyExcel.write(outputStream).build();

			WriteSheet withoutSheet = EasyExcel.writerSheet(0, "未录入").head(LgUser.class).build();
			excelWriter.write(withoutList, withoutSheet);

			WriteSheet withSheet = EasyExcel.writerSheet(1, "已录入").head(LgUser.class).build();
			excelWriter.write(withList, withSheet);
		} finally {
			if (null != excelWriter) {
				excelWriter.finish();
			}
		}

	}

	public String importExcel(HttpServletRequest request) {
		ExcelReader excelReader = null;
		try {
			InputStream inputStream = getInputStream(request);
			excelReader = EasyExcel.read(inputStream, LgUser.class, new ExcelDataSaveListener(lgUserMapper)).build();
			ReadSheet readSheet = EasyExcel.readSheet(0).build();
			excelReader.read(readSheet);
		} finally {
			if (null != excelReader) {
				excelReader.finish();
			}
		}

		return "ok";
	}

	public InputStream getInputStream(HttpServletRequest request) {
		return null;
	}

	public OutputStream getOutputStream(HttpServletResponse response) throws IOException {
		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + FILE + ".xls");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			return response.getOutputStream();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Map<String, List<LgUser>> splitRealNameIsNull() {
		List<LgUser> dbUser = lgUserMapper.queryAllUser();
		List<LgUser> withNameList = new ArrayList<>();
		List<LgUser> withOutNameList = new ArrayList<>();

		dbUser.forEach(item -> {
			String realName = item.getRealName();
			if (null == realName || realName.isEmpty()) {
				withOutNameList.add(item);
			} else {
				withNameList.add(item);
			}
		});

		Map<String, List<LgUser>> map = new HashMap<>();
		map.put(WITH, withNameList);
		map.put(WITHOUT, withOutNameList);

		return  map;
	}
}
