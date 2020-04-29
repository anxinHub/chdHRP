/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.dict;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.dict.AssShareDeptCost;
/**
 * 
 * @Description:
 * 050807 分摊科室设置
 * @Table:
 * ASS_SHARE_DEPT_COST
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssShareDeptCostMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssShareDeptCost(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return AssShareDeptCost
	 * @throws DataAccessException
	*/
	public int updateBatchAssShareDeptCost(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssShareDeptCost(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050807 分摊科室设置<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssShareDeptCost> queryAssShareDeptCost(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050807 分摊科室设置<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssShareDeptCost> queryAssShareDeptCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050807 分摊科室设置<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssShareDeptCost
	 * @throws DataAccessException
	*/
	public AssShareDeptCost queryAssShareDeptCostByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050807 分摊科室设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssShareDeptCost
	 * @throws DataAccessException
	*/
	public AssShareDeptCost queryAssShareDeptCostByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
