/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.protocol;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.med.entity.MedProtocolFile;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_PROTOCOL_Files
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedProtocolFileService {

	/**
	 * @Description 
	 * 添加 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @De
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedProtocolFile(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedProtocolFile queryMedProtocolFileByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedProtocolFile
	 * @throws DataAccessException
	*/
	public MedProtocolFile queryMedProtocolFileByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
