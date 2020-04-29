/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.protocol;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedProtocolType;
/**
 * 
 * @Description:
 * 04501 付款协议类别
 * @Table:
 * MED_PROTOCOL_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedProtocolTypeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedProtocolType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedProtocolType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedProtocolType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return MedProtocolType
	 * @throws DataAccessException
	*/
	public int updateBatchMedProtocolType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedProtocolType(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedProtocolType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04501 付款协议类别<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedProtocolType> queryMedProtocolType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04501 付款协议类别<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedProtocolType> queryMedProtocolType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04501 付款协议类别<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedProtocolType
	 * @throws DataAccessException
	*/
	public MedProtocolType queryMedProtocolTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04501 付款协议类别<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedProtocolType
	 * @throws DataAccessException
	*/
	public MedProtocolType queryMedProtocolTypeByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 根据 输入的类别编码、类别名称查询协议类别（判断类别编码、类别名称是否已存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedProtocolType> queryMedProtocolTypeByID(Map<String, Object> entityMap)  throws DataAccessException;
	/**
	 * 获取当前物流管理系统的启用年月
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
}
