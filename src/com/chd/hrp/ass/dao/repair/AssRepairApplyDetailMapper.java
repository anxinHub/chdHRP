/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.repair;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
import com.chd.hrp.ass.entity.repair.AssRepairApplyDetail;
/**
 * 
 * @Description:
 * 051201 资产维修明细
 * @Table:
 * ASS_REPAIR_APPLY_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssRepairApplyDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssRepairApplyDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssRepairApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssRepairApplyDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return AssRepairApplyDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssRepairApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssRepairApplyDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssRepairApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051201 资产维修明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssRepairApplyDetail> queryAssRepairApplyDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051201 资产维修明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssRepairApplyDetail> queryAssRepairApplyDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051201 资产维修明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssRepairApplyDetail
	 * @throws DataAccessException
	*/
	public AssRepairApplyDetail queryAssRepairApplyDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取051201 资产维修明细<BR>  用于维修记录生成
	 * @param  entityMap <BR>
	 * @return AssRepairApplyDetail
	 * @throws DataAccessException
	*/
	public  List<AssRepairApplyDetail> queryAssRepairApplyDetailByCreate (Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 获取051201 资产维修明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssRepairApplyDetail
	 * @throws DataAccessException
	*/
	public AssRepairApplyDetail queryAssRepairApplyDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051201 资产维修明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssRepairApplyDetail>
	 * @throws DataAccessException
	*/
	public List<AssRepairApplyDetail> queryAssRepairApplyDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssMaintainPlanItem> queryAssMaintainPlanItemExists(Map<String,Object> entityMap)throws DataAccessException;
	 
	public List<AssRepairApplyDetail> queryAssRepairApplyById(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  资产维修明细<BR>带分页  (专用设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssRepairApplyDetail> queryAssRepairApplyDetailSpecial(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  资产维修明细<BR>带分页  (一般设备查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssRepairApplyDetail> queryAssRepairApplyDetailGeneral(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  资产维修明细<BR>带分页  (房屋及建筑查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssRepairApplyDetail> queryAssRepairApplyDetailHouse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集051202  资产维修明细<BR>带分页  (其他固定资产查询)
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssRepairApplyDetail> queryRepairApplyDetailOther(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
}
