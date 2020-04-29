/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.base;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedApplyOutRela;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_OUT_RESOURCE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedApplyOutRelaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询科室申请与出库调拨关系
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public MedApplyOutRela queryMedApplyOutRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询科室申请与出库调拨关系列表
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public List<MedApplyOutRela> queryMedApplyOutRelaList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询科室申请与出库调拨关系列表
	 * @param  entityList
	 * @return Date
	 * @throws DataAccessException
	*/
	public List<MedApplyOutRela> queryMedApplyOutRelaList(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 插入入库科室申请与出库调拨关系
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedApplyOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 修改入库科室申请与出库调拨关系
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedApplyOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 删除入库科室申请与出库调拨关系
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedApplyOutRela(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 批量删除入库科室申请与出库调拨关系
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedApplyOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 批量判断单据是否可删除、修改
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMedApplyOutRelaStateBatch(List<Map<String,Object>> entityList) throws DataAccessException;
}
