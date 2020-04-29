/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.maintain;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanAss;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
/**
 * 
 * @Description:
 * 051202 保养计划项目明细
 * @Table:
 * ASS_MAINTAIN_PLAN_ITEM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMaintainPlanItemMapper extends SqlMapper{ 
	/**
	 * @Description 
	 * 添加051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssMaintainPlanItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return AssMaintainPlanItem
	 * @throws DataAccessException
	*/
	public int updateBatchAssMaintainPlanItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssMaintainPlanItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanItem> queryAssMaintainPlanItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanItem> queryAssMaintainPlanItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划项目明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssMaintainPlanItem
	 * @throws DataAccessException
	*/
	public AssMaintainPlanItem queryAssMaintainPlanItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划项目明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainPlanItem
	 * @throws DataAccessException
	*/
	public AssMaintainPlanItem queryAssMaintainPlanItemByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划项目明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainPlanItem>
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanItem> queryAssMaintainPlanItemExists(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (专用设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanItem> queryAssMaintainPlanItemSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (一般设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanItem> queryAssMaintainPlanItemGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (房屋及建筑查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanItem> queryAssMaintainPlanItemHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR>带分页  (其他固定资产查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanItem> queryAssMaintainPlanItemOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<AssMaintainPlanItem> queryAssMaintainItem(
			Map<String, Object> entityMap);

	public List<AssMaintainPlanItem> queryAssMaintainItem(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public void deleteAssMaintaintItemByAssMaintainDetail(
			AssMaintainPlanAss assAcceptDetail);
	
}
