package dimu.res.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import dimu.res.mapper.LgUserMapper;
import dimu.res.model.LgUser;

/**
 * excel解析监听器
 *
 * @author dwx
 * @date 2020-09-16
 */
public class ExcelDataSaveListener extends AnalysisEventListener<LgUser> {

	private LgUserMapper lgUserMapper;

	private List<LgUser> list = new ArrayList<>();

	public ExcelDataSaveListener(LgUserMapper lgUserMapper) {
		this.lgUserMapper = lgUserMapper;
	}

	@Override
	public void invoke(LgUser data, AnalysisContext context) {
		list.add(data);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		map
	}
}
