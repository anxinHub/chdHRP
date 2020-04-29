/**
 * @Copyright: Copyright (c) 2015年10月22日 下午5:41:22
 * @Company: 智慧云康（北京）数据科教有限公司
 */
package com.chd.base.util;

import java.util.List;

/**
 * @Title. @Description. <br>
 * 适配LgerUI 表格json 格式
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class ViewToLgerUI<T> {

	private List<T> Rows;

	private int Total;

	public int getTotal() {
		return Total;
	}

	public List<T> getRows() {
		return Rows;
	}

	public void setRows(List<T> rows) {
		Rows = rows;
	}

	public void setTotal(int total) {
		this.Total = total;
	}

}
