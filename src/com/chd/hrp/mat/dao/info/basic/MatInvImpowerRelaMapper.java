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
import com.chd.hrp.mat.entity.MatInvCertRela;
/**
 * 
 * @Description:
 * 

 * @Table:
 * MAT_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatInvImpowerRelaMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatInvCertRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatInvImpowerRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 删除
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatInvCertRela(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatInvImpowerRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集
<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInvCertRela> queryMatInvCertRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集
<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInvCertRela> queryMatInvCertRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取
<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatInvCertRela
	 * @throws DataAccessException
	*/
	public MatInvCertRela queryMatInvCertRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取
<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvCertRela
	 * @throws DataAccessException
	*/
	public MatInvCertRela queryMatInvCertRelaByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询 该集团、该医院的所有材料 inv_id 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryInvId(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 该证件是否 存在证件材料对应关系
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据cert_id 获得对应关系数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatInvCertDetail(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatInvCertDetail(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 根据cert_id 获得对应关系数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMatInvInCert(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 根据材料 批量删除
	<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatInvCertRelaByInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	//	供应商录入
	public int addBatchMatInvCertSupRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	public int deleteMatInvCertSupRela(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteBatchMatInvCertSupRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryMatInvCertSupDetail(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatInvCertSupDetail(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryMatInvImpowerDetail(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatInvImpowerDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public Map<String, Object> queryMaxImpowerId(Map<String, Object> mapVo);

	
}


