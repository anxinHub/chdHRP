package com.chd.hrp.ass.dao.base;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssClosingAccountMapper extends SqlMapper{
	public Map<String,Object> collectDepreALL(Map<String,Object> map)throws DataAccessException;
	
	public Map<String,Object> collectDelDepreALL(Map<String,Object> map)throws DataAccessException;
	
	
	public Map<String,Object> collectGenerate(Map<String,Object> map)throws DataAccessException;

	public Map<String,Object> collectGenerateStore(Map<String,Object> map)throws DataAccessException;
	
	public Map<String,Object> collectAssReportApp(Map<String,Object> map)throws DataAccessException;
	
	
	
	public int copyShareDeptRSpecial(Map<String,Object> map)throws DataAccessException;
	
	public int copyShareDeptRGeneral(Map<String,Object> map)throws DataAccessException;
	
	public int copyShareDeptRHouse(Map<String,Object> map)throws DataAccessException;
	
	//public int copyShareDeptRInassets(Map<String,Object> map)throws DataAccessException;
	
	public int copyShareDeptROther(Map<String,Object> map)throws DataAccessException;
	
	//public int copyShareDeptRLand(Map<String,Object> map)throws DataAccessException;
	//检查是否有未确认的单据
	public Map<String,Object> checkAssBillConfirm(Map<String, Object> map)throws DataAccessException;
}
