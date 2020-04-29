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
import com.chd.hrp.med.entity.MedVenCertType;
/**
 * 
 * @Description:
 * 08604 供应商证件类型
 * @Table:
 * MED_VEN_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedVenCertTypeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedVenCertType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedVenCertType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedVenCertType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return MedVenCertType
	 * @throws DataAccessException
	*/
	public int updateBatchMedVenCertType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedVenCertType(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除08604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedVenCertType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08604 供应商证件类型<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedVenCertType> queryMedVenCertType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08604 供应商证件类型<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedVenCertType> queryMedVenCertType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取08604 供应商证件类型<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedVenCertType
	 * @throws DataAccessException
	*/
	public MedVenCertType queryMedVenCertTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取08604 供应商证件类型<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedVenCertType
	 * @throws DataAccessException
	*/
	public MedVenCertType queryMedVenCertTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询 与 输入证件类型编码、证件类型名称 相同的记录(判断证件类型编码、证件类型名称是否已存在)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedVenCertType> queryMedVenCertTypeByID(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
}
