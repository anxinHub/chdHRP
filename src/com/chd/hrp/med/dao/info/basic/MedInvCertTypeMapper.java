
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
import com.chd.hrp.med.entity.MedInvCertType;
/**
 * 
 * @Description:
 * 08601 材料证件类型字典
 * @Table:
 * MED_INV_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedInvCertTypeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedInvCertType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedInvCertType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedInvCertType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return MedInvCertType
	 * @throws DataAccessException
	*/
	public int updateBatchMedInvCertType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInvCertType(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedInvCertType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08601 材料证件类型字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedInvCertType> queryMedInvCertType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08601 材料证件类型字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedInvCertType> queryMedInvCertType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取08601 材料证件类型字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedInvCertType
	 * @throws DataAccessException
	*/
	public MedInvCertType queryMedInvCertTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取08601 材料证件类型字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInvCertType
	 * @throws DataAccessException
	*/
	public MedInvCertType queryMedInvCertTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 添加、修改时 根据证件类型编码、证件类型名称查询 证件类型（判断 证件类型编码、证件类型名称是否已存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedInvCertType> queryMedInvCertTypeById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 删除的数据是否也被引用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDelDate(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
}
