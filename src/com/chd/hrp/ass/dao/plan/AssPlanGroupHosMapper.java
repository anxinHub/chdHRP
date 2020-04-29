/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.plan;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.plan.AssPlanGroupHos;
/**
 * 
 * @Description:
 * 050302 集团与医院购置计划关系表
 * @Table:
 * ASS_PLAN_GROUP_HOS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssPlanGroupHosMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssPlanGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssPlanGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssPlanGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssPlanGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050302 集团与医院购置计划关系表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPlanGroupHos> queryAssPlanGroupHos(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050302 集团与医院购置计划关系表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPlanGroupHos> queryAssPlanGroupHos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 集团购置计划打印主表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryAssPlanGroupBatch(
			Map<String, Object> map);
	/**
	 * 集团购置计划打印从表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryAssPlanGroup_detail(
			Map<String, Object> map);
	
	public Map<String, Object> queryAssPlanGroupByPrint(Map<String, Object> map);

	public List<String> queryPlanGroupDeptState(Map<String, Object> mapVo);
	
}
