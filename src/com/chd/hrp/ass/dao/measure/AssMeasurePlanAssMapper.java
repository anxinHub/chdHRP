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
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanAss;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecItem;
import com.chd.hrp.ass.entity.measure.AssMeasurePlanAss;
/**
 * 
 * @Description:
 * 051204 检查计量计划资产明细
 * @Table:
 * ASS_MEASURE_PLAN_ASS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMeasurePlanAssMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加051204 检查计量计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssMeasurePlanAss(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051204 检查计量计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssMeasurePlanAss(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051204 检查计量计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssMeasurePlanAss(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051204 检查计量计划资产明细<BR> 
	 * @param  entityMap
	 * @return AssMeasurePlanAss
	 * @throws DataAccessException
	*/
	public int updateBatchAssMeasurePlanAss(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051204 检查计量计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssMeasurePlanAss(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051204 检查计量计划资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssMeasurePlanAss(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051204 检查计量计划资产明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlanAss> queryAssMeasurePlanAss(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051204 检查计量计划资产明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlanAss> queryAssMeasurePlanAss(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检查计量计划资产明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssMeasurePlanAss
	 * @throws DataAccessException
	*/
	public AssMeasurePlanAss queryAssMeasurePlanAssByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检查计量计划资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMeasurePlanAss
	 * @throws DataAccessException
	*/
	public AssMeasurePlanAss queryAssMeasurePlanAssByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051204 检查计量计划资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMeasurePlanAss>
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlanAss> queryAssMeasurePlanAssExists(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (专用设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlanAss> queryAssMeasuerAssSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (一般设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlanAss> queryAssMeasuerAssGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (房屋及建筑查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlanAss> queryAssMeasuerAssHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (其他固定资产查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMeasurePlanAss> queryAssMeasuerAssOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<AssMeasurePlanAss> queryByAssMeasurePlanId(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssMeasurePlanAss> queryAssMaintainPlanItemOther(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<AssMeasurePlanAss> queryAssMaintainPlanItemHouse(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<AssMeasurePlanAss> queryAssMaintainPlanItemGeneral(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<AssMeasurePlanAss> queryAssMaintainPlanItemSpecial(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryMeasurePlanRec(
			Map<String, Object> entityMap, RowBounds rowBounds);

	
	
	
}
