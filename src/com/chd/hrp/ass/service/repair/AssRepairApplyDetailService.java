/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.repair;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 

public interface AssRepairApplyDetailService {

	/**
	 * @Description 
	 * 添加051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssRepairApplyDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssRepairApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssRepairApplyDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssRepairApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssRepairApplyDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssRepairApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssRepairApplyDetail(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051201 资产维修明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssRepairApplyDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象051201 资产维修明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssRepairApplyDetail queryAssRepairApplyDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象051201 资产维修明细<BR>  用于维修记录生成
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public  List<AssRepairApplyDetail> queryAssRepairApplyDetailByCreate(Map<String,Object> entityMap)throws DataAccessException;
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
}
