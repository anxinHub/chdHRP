package com.chd.hrp.budg.service.common;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface Budg_No_Manage {	
	/**
	 * 生成table_code表的下一个单号（使用于出入库业务）
	 * @param entityMap
	 * @return
	 */	
public String getMatNextNo(Map<String, Object> entityMap) throws DataAccessException;
		
}
