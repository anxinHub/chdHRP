/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.verification;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccBudgRange;

/**
* @Title. @Description.
* 账龄区间表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBudgRangeService {

	/**
	 * @Description 
	 * 账龄区间表<BR> 添加AccBudgRange
	 * @param AccBudgRange entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccBudgRange(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 批量添加AccBudgRange
	 * @param  AccBudgRange entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccBudgRange(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 查询AccBudgRange分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccBudgRange(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 查询AccBudgRange
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<AccBudgRange> queryAccBudgRangeList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 查询AccBudgRangeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccBudgRange queryAccBudgRangeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 删除AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccBudgRange(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 批量删除AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccBudgRange(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 更新AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccBudgRange(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 批量更新AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccBudgRange(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 导入AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccBudgRange(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public AccBudgRange getMaxDay(Map<String,Object> entityMap)throws DataAccessException;

	public String queryAccBugRangeTop(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccBudgRangePrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
