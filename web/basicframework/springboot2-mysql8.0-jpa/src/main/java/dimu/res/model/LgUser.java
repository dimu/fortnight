package dimu.res.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * lg user测试
 *
 * @author dwx
 * @date 2020-09-16
 */
//@Data
public class LgUser {

	@ExcelProperty("主键")
	@ColumnWidth(16)
	private Long id;

	@ExcelProperty("微信id")
	@ColumnWidth(48)
	private String openid;

	@ColumnWidth(32)
	@ExcelProperty("微信名")
	private String wxName;

	@ColumnWidth(32)
	@ExcelProperty("真实姓名")
	private String realName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
