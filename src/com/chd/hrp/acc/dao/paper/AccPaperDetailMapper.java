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
* 票据管理主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccPaperDetailMapper extends SqlMapper{
	
	public int addAccPaperDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchAccPaperDetail(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateAccPaperDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchAccPaperDetail(List<Map<String, Object>> list)throws DataAccessException;
	
	public int deleteBatchAccPaperDetail(List<Map<String, Object>> list)throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryAccPaperDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
	//票据管理查询
	public List<Map<String, Object>> queryAccPaperDetailManage(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperDetailManage(Map<String,Object> entityMap) throws DataAccessException;
	
	
	//单张票据核销查询
	public List<Map<String, Object>> queryAccPaperDetailSola(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaperDetailSola(Map<String,Object> entityMap) throws DataAccessException;
	

	
	
}
