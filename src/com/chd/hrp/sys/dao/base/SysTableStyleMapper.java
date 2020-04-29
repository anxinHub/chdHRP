package com.chd.hrp.sys.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.EmpDict;
import com.chd.hrp.sys.entity.SysTableStyle;


public interface SysTableStyleMapper extends SqlMapper {
	/** 
	 * @Description 
	 * 查询全部<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	*/
	public List<SysTableStyle> querySysTableStyle(Map<String, Object> entityMap) throws DataAccessException;
	/** 
	 * @Description 
	 * 查询全部<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	*/
	public SysTableStyle querySysTableStyleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/** 
	 * @Description 
	 * 添加<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	*/
	public int addSysTableStyle(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加EmpDict
	 * @param  EmpDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public int addBatchSysTableStyle(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除EmpDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteSysTableStyle(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 批量删除EmpDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchSysTableStyle(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	//删除列表打印格式
	public int deleteStyleByUrl(Map<String,Object> map)throws DataAccessException;
	
	//添加列表打印格式
	public int insertStyleByUrl(Map<String,Object> map)throws DataAccessException;
  
	//查询列表格式
	public List<Map<String, Object>> queryGridByUserID(Map<String,Object> map)throws DataAccessException;
	
	//查询打印格式
	public String queryPrintByPageUrl(Map<String,Object> map)throws DataAccessException;
  
}
