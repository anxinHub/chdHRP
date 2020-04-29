/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.books.subjaccount;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccMultiPlanSubj;

/**
* @Title. @Description.
* 多栏方案分析科目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccMultiPlanSubjMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 添加AccMultiPlanSubj
	 * @param AccMultiPlanSubj entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccMultiPlanSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 批量添加AccMultiPlanSubj
	 * @param  AccMultiPlanSubj entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccMultiPlanSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 查询AccMultiPlanSubj分页
	 * @param  entityMap RowBounds
	 * @return List<AccMultiPlanSubj>
	 * @throws DataAccessException
	*/
	public List<AccMultiPlanSubj> queryAccMultiPlanSubj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 查询AccMultiPlanSubj所有数据
	 * @param  entityMap
	 * @return List<AccMultiPlanSubj>
	 * @throws DataAccessException
	*/
	public List<AccMultiPlanSubj> queryAccMultiPlanSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 查询AccMultiPlanSubjByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMultiPlanSubj queryAccMultiPlanSubjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 删除AccMultiPlanSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccMultiPlanSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 批量删除AccMultiPlanSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccMultiPlanSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 更新AccMultiPlanSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccMultiPlanSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 批量更新AccMultiPlanSubj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccMultiPlanSubj(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryAccMultiPlanSubjList(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryByCodeMap(Map<String, Object> mapVo);
	
}
