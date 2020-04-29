
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.mat.service.info.basic;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.MatInvCertType;
import com.chd.hrp.mat.entity.MatInvImpower;
/**
 * 
 * @Description:
 * 04601 注册授权字典
 * @Table:
 * MAT_INV_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatInvImpowerService {

	/**
	 * @Description 
	 * 添加04601 注册授权字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMatInvImpower(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04601 注册授权字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMatInvImpower(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04601 注册授权字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMatInvImpower(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04601 注册授权字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMatInvImpower(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除04601 注册授权字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMatInvImpower(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除04601 注册授权字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMatInvImpower(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集04601 注册授权字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInvImpower(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象04601 注册授权字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MatInvImpower queryMatInvImpowerByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04601 注册授权字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvCertType
	 * @throws DataAccessException
	*/
	public MatInvImpower queryMatInvImpowerByUniqueness(Map<String,Object> entityMap)throws DataAccessException;

	public String queryMatImpowerInvList(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询选择的材料
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatImpowerInvChoiceInvList(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询对应关系明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvImpowerDetail(Map<String, Object> entityMap) throws DataAccessException;
}
