
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.mat.dao.info.basic;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatInvCertType;
import com.chd.hrp.mat.entity.MatInvImpower;
/**
 * 
 * @Description:
 * 04601 材料证件类型字典
 * @Table:
 * MAT_INV_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatInvImpowerMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatInvImpower(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatInvImpower(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatInvImpower(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return MatInvCertType
	 * @throws DataAccessException
	*/
	public int updateBatchMatInvImpower(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatInvImpower(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatInvImpower(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04601 材料证件类型字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInvImpower> queryMatInvImpower(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04601 材料证件类型字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInvImpower> queryMatInvImpower(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04601 材料证件类型字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatInvCertType
	 * @throws DataAccessException
	*/
	public MatInvImpower queryMatInvImpowerByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04601 材料证件类型字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvCertType
	 * @throws DataAccessException
	*/
	public MatInvImpower queryMatInvImpowerByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 添加、修改时 根据证件类型编码、证件类型名称查询 证件类型（判断 证件类型编码、证件类型名称是否已存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatInvImpower> queryMatInvImpowerById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 删除的数据是否也被引用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDelDate(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryMatImpowerInvList(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatImpowerInvList(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public Long queryMatInvImpowerNextId() throws DataAccessException;

	public List<Map<String, Object>> queryMatImpowerInvChoiceInvList(
			List<Map<String, Object>> detailList)throws DataAccessException;

	
	
	
}
