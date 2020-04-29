/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.info.basic;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedStoreEmp;
import com.chd.hrp.med.entity.MedStoreSet;
/**
 * 
 * @Description:
 * 仓库采购员对应关系 
 * @Table:
 * MED_STORE_EMP_MAP 
 */ 
public interface MedStoreEmpSetService {

	/**
	 * @Description 
	 * 添加   仓库采购员对应关系 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedStoreEmpSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加   仓库采购员对应关系 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedStoreEmpSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新   仓库采购员对应关系 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedStoreEmpSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新   仓库采购员对应关系 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedStoreEmpSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除   仓库采购员对应关系 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedStoreEmpSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除   仓库采购员对应关系 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedStoreEmpSet(List<Map<String, Object>> entityMap)throws DataAccessException;
 
	/**
	 * @Description 
	 * 查询结果集   仓库采购员对应关系 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedStoreEmpSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象  仓库采购员对应关系 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedStoreEmp queryMedStoreEmpSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	 

}
