package dimu.res.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dimu.res.service.ExcelExportHelper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * excel导入与查询生成下载类
 *
 * @author dwx
 * @date 2020-09-16
 */
@RestController
@RequestMapping("/excel")
public class ExcelImportAndExport {

	@Resource
	private ExcelExportHelper excelExportHelper;

	@GetMapping("/download")
	public String exportExcel(HttpServletResponse response) throws IOException {
		excelExportHelper.downloadExcel(response);
		return "OK";
	}

	@PostMapping("/import")
	public String importExcel(@RequestParam("file") MultipartFile file)  {

		try {
			InputStream inputStream = file.getInputStream();
			return excelExportHelper.importExcel(inputStream);
		}
		catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}

	}
}
