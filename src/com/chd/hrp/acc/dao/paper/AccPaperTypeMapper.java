/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.paper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCashFlow;

/**
* @Title. @Description.
* 票据类型<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccPaperTypeMapper extends SqlMapper{
	
	public int addAccPaperType(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchAccPaperType(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateAccPaperType(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchAccPaperType(List<Map<String, Object>> list)throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperType(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryAccPaperTypeByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	

	
	
	
}
