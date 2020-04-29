/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccMpayVeri;

public interface AccMpayVeriMapper extends SqlMapper{
	
	public int addAccMpayVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchAccMpayVeri(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccMpayVeri(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccMpayVeri(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccMpayVeri queryAccMpayVeriByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteAccMpayVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchAccMpayVeri(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccMpayVeriR(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccMpayVeriR(Map<String,Object> entityMap) throws DataAccessException;
	
}
