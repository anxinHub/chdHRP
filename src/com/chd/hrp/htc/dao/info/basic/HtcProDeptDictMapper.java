package com.chd.hrp.htc.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.info.basic.HtcProDeptDict;



public interface HtcProDeptDictMapper extends SqlMapper{

	public int addHtcProDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addHtcProDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public long queryProjDeptSeq()throws DataAccessException;
	
	public long queryProjDeptDictSeq()throws DataAccessException;
	
	public int synchroHtcProDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public HtcProDeptDict queryHtcProDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcProDeptDict> queryHtcProDept(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcProDeptDict> queryHtcProDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int deleteBatchHtcProDept(List<Map<String,Object>> list)throws DataAccessException;
	
	public int deleteBatchHtcProDeptDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcProDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateHtcProDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateHtcProDeptDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	
}

