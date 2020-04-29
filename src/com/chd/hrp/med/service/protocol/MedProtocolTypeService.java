/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.protocol;
import java.util.*;
import org.springframework.dao.DataAccessException;
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
 

public interface MedProtocolTypeService {

	/**
	 * @Description 
	 * 添加04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedProtocolType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedProtocolType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedProtocolType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedProtocolType(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedProtocolType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedProtocolType(String paramVo)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedProtocolType(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedProtocolType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象04501 付款协议类别<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
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
	public MedProtocolType queryMedProtocolTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
