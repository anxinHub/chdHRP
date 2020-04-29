/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.plan;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

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
 

public interface AssPlanGroupHosService {

	/**
	 * @Description 
	 * 添加050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssPlanGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssPlanGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssPlanGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssPlanGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssPlanGroupHos(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssPlanGroupHos> queryAssPlanGroupHosList(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 集团购置计划打印
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryAssPlanGroupDY(Map<String, Object> map)
			throws DataAccessException;

	public List<String> queryPlanGroupDeptState(Map<String, Object> mapVo)throws DataAccessException;
	
}
