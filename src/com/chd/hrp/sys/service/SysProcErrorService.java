package com.chd.hrp.sys.service;

import java.util.List;

import org.dom4j.DocumentException;

import com.chd.hrp.sys.entity.SysProcError;


public interface SysProcErrorService {

	public int errorAdd(SysProcError record);
	public int errorDelById(String id);
	public int errorDelAll();
	public String queryErrorsByModCode(String modCode,String mtype);
	public String getMods();
	public String errorExecuteSql(String sqlIds) throws DocumentException;
	public int errorUpdate(SysProcError record);
	int updateErrorCompile();
}
