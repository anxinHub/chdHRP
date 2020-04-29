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
import com.chd.hrp.ass.entity.repair.AssRepairRecDetail;
/**
 * 
 * @Description:
 * 051201 资产维修记录明细
 * @Table:
 * ASS_REPAIR_REC_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssRepairRecDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssRepairRecDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssRepairRecDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssRepairRecDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return AssRepairRecDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssRepairRecDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssRepairRecDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssRepairRecDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051201 资产维修记录明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssRepairRecDetail> queryAssRepairRecDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051201 资产维修记录明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssRepairRecDetail> queryAssRepairRecDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051201 资产维修记录明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssRepairRecDetail
	 * @throws DataAccessException
	*/
	public AssRepairRecDetail queryAssRepairRecDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051201 资产维修记录明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssRepairRecDetail
	 * @throws DataAccessException
	*/
	public AssRepairRecDetail queryAssRepairRecDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051201 资产维修记录明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssRepairRecDetail>
	 * @throws DataAccessException
	*/
	public List<AssRepairRecDetail> queryAssRepairRecDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public Long queryCurrentSequence()throws DataAccessException;
	
	public List<AssRepairRecDetail> queryAssRepairRecById(Map<String,Object> entityMap)throws DataAccessException;
	
}
