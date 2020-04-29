/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.measure;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 

public interface AssMeasurePlanMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssMeasurePlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检查计量计划<BR> (消审)
	 * @param  entityMap
	 * @return AssMeasurePlan
	 * @throws DataAccessException
	*/
	public int updateBatchAssMeasurePlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检查计量计划<BR> (消审)
	 * @param  entityMap
	 * @return AssMeasurePlan
	 * @throws DataAccessException
	*/
	public int updateBatchAssMeasurePlanBack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 批量更新051204 检查计量计划<BR> (终止)
	 * @param  entityMap
	 * @return AssMeasurePlan
	 * @throws DataAccessException
	*/
	public int updateBatchAssMeasurePlanStop(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 删除051204 检查计量计划<BR> (终止)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssMeasurePlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051204 检查计量计划<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlan> queryAssMeasurePlan(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051204 检查计量计划<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlan> queryAssMeasurePlan(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检查计量计划<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssMeasurePlan
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
	
	public Long queryCurrentSequence()throws DataAccessException;
	  /**
     * 查询土地卡片
     * @param entityMap
     * @param rowBounds
     * @return
     */
	public List<Map<String, Object>> queryAssCardLand(
			Map<String, Object> entityMap, RowBounds rowBounds);
	 /**
     * 查询土地卡片
     * @param entityMap
     * @param rowBounds
     * @return
     */
	public List<Map<String, Object>> queryAssCardOther(
			Map<String, Object> entityMap, RowBounds rowBounds);
	 /**
     * 查询其他无形资产卡片
     * @param entityMap
     * @param rowBounds
     * @return
     */
	public List<Map<String, Object>> queryAssCardInassets(
			Map<String, Object> entityMap, RowBounds rowBounds);
	 /**
     * 查询房屋及建筑卡片
     * @param entityMap
     * @param rowBounds
     * @return
     */
	public List<Map<String, Object>> queryAssCardHouse(
			Map<String, Object> entityMap, RowBounds rowBounds);
	 /**
     * 查询一般设备卡片
     * @param entityMap
     * @param rowBounds
     * @return
     */
	public List<Map<String, Object>> queryAssCardGeneral(
			Map<String, Object> entityMap, RowBounds rowBounds);
	 /**
     * 查询专用设备卡片
     * @param entityMap
     * @param rowBounds
     * @return
     */
	public List<Map<String, Object>> queryAssCardSpecial(
			Map<String, Object> entityMap, RowBounds rowBounds);
/**
 * 计量提醒
 * @param entityMap
 * @return
 */
	public List<AssMeasurePlan> queryAssMeasureRemindPlan(
			Map<String, Object> entityMap);

	public List<AssMeasurePlan> queryAssMeasureRemindPlan(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssMeasurePlanByMainBatch(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssMeasurePlanByDetail(
			Map<String, Object> entityMap);

	public Map<String, Object> queryAssMeasurePlanByMain(
			Map<String, Object> entityMap);

	public List<String> queryAssMeasurePlanState(Map<String, Object> mapVo);
}
