package com.chd.base.util;

import java.util.List;
import java.util.Map;

public class ZtreeVo {

	// 用于返回json
	private List<Map<String, Object>> Rows;

	/**
	 * 获取 rows
	 * 
	 * @return rows
	 */
	public List<Map<String, Object>> getRows() {
		return Rows;
	}

	/**
	 * 设置 rows
	 * 
	 * @param rows
	 */
	public void setRows(List<Map<String, Object>> rows) {
		Rows = rows;
	}

}
