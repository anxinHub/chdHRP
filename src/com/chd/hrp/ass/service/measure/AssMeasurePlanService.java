/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.measure;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.measure.AssMeasurePlan;
/**
 * 
 * @Description:
 * 051204 检查计量计划
 * @Table:
 * ASS_MEASURE_PLAN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMeasurePlanService {

	/**
	 * @Description 
	 * 添加051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssMeasurePlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检查计量计划<BR> (审核)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMeasurePlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检查计量计划<BR> (消审)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMeasurePlanBack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检查计量计划<BR> (终止)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMeasurePlanStop(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssMeasurePlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssMeasurePlan(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象051204 检查计量计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssMeasurePlan queryAssMeasurePlanByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检查计量计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMeasurePlan
	 * @throws DataAccessException
	*/
	public AssMeasurePlan queryAssMeasurePlanByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检查计量计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMeasurePlan>
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlan> queryAssMeasurePlanExists(Map<String,Object> entityMap)throws DataAccessException;

	   /**
  * 查询专用设备卡片
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
	public String queryAssCardSpecial(Map<String, Object> mapVo)throws DataAccessException;
/**
 * 查询一般设备卡片
 * @param mapVo
 * @return
 * @throws DataAccessException
 */
	public String queryAssCardGeneral(Map<String, Object> mapVo)throws DataAccessException;
 /**
  * 查询房屋及建筑卡片
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
	public String queryAssCardHouse(Map<String, Object> mapVo)throws DataAccessException;
 /**
  * 查询其他无形资产卡片
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
	public String queryAssCardInassets(Map<String, Object> mapVo)throws DataAccessException;
 /**
  * 查询其他固定资产卡片
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
	public String queryAssCardOther(Map<String, Object> mapVo)throws DataAccessException;
 /**
  * 查询土地卡片
  * @param mapVo
  * @return
  * @throws DataAccessException
  */
	public String queryAssCardLand(Map<String, Object> mapVo)throws DataAccessException;

	public String queryAssMeasureRemindPlan(Map<String, Object> page)throws DataAccessException;


	Map<String, Object> queryAssMeasurePlanPrint(Map<String, Object> entityMap)
			throws DataAccessException;

	public List<String> queryAssMeasurePlanState(Map<String, Object> mapVo);
	
	
	
	
}
