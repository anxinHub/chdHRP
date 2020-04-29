/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.info.basic;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedStoreEmp;
import com.chd.hrp.med.entity.MedStoreSet;
/**
 *  
 * @Description:
 * 仓库采购员对应关系设置
 * @Table:
 * MED_STORE_EMP_MAP 
 */ 
public interface MedStoreEmpSetMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加    仓库采购员对应关系 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedStoreEmpSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加    仓库采购员对应关系 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedStoreEmpSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新  仓库采购员对应关系 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedStoreEmpSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新   仓库采购员对应关系 
	 * @param  entityMap
	 * @return MedStoreSet
	 * @throws DataAccessException
	*/
	public int updateBatchMedStoreEmpSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除  仓库采购员对应关系 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedStoreEmpSet(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除   仓库采购员对应关系 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedStoreEmpSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集  仓库采购员对应关系 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedStoreEmp> queryMedStoreEmpSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集   仓库采购员对应关系 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedStoreEmp> queryMedStoreEmpSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取 仓库采购员对应关系 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedStoreSet
	 * @throws DataAccessException
	*/
	public MedStoreEmp queryMedStoreEmpSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取  仓库采购员对应关系 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStoreSet
	 * @throws DataAccessException
	*/
	public MedStoreEmp queryMedStoreEmpSetByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	 
}
