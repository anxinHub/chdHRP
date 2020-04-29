package com.chd.hrp.ass.dao.base;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface InassClosingAccountMapper extends SqlMapper{
	public Map<String,Object> collectInassDepreALL(Map<String,Object> map)throws DataAccessException;
	
	public Map<String,Object> collectInassDelDepreALL(Map<String,Object> map)throws DataAccessException;
	
	
	public Map<String,Object> collectInassGenerate(Map<String,Object> map)throws DataAccessException;

	public Map<String,Object> collectInassGenerateStore(Map<String,Object> map)throws DataAccessException;
	
	public Map<String,Object> collectInassReportApp(Map<String,Object> map)throws DataAccessException;
	
	
	
	public int copyInassShareDeptRInassets(Map<String,Object> map)throws DataAccessException;
	
	public int copyInassShareDeptRLand(Map<String,Object> map)throws DataAccessException;
	//检查是否有未确认的单据
	public Map<String,Object> checkInassBillConfirm(Map<String, Object> map)throws DataAccessException;
}
