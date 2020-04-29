/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.verification;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccBudgRange;

/**
* @Title. @Description.
* 账龄区间表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBudgRangeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 添加AccBudgRange
	 * @param AccBudgRange entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccBudgRange(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 批量添加AccBudgRange
	 * @param  AccBudgRange entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccBudgRange(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 查询AccBudgRange分页
	 * @param  entityMap RowBounds
	 * @return List<AccBudgRange>
	 * @throws DataAccessException
	*/
	public List<AccBudgRange> queryAccBudgRange(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 账龄区间表<BR> 查询AccBudgRange所有数据
	 * @param  entityMap
	 * @return List<AccBudgRange>
	 * @throws DataAccessException
	*/
	public List<AccBudgRange> queryAccBudgRange(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 查询AccBudgRangeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccBudgRange queryAccBudgRangeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 删除AccBudgRange
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccBudgRange(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 批量删除AccBudgRange
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccBudgRange(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 账龄区间表<BR> 更新AccBudgRange
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccBudgRange(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 批量更新AccBudgRange
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccBudgRange(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public AccBudgRange getMaxDay(Map<String,Object> entityMap)throws DataAccessException;

	public AccBudgRange queryAccBugRangeTop(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccBudgRangePrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
