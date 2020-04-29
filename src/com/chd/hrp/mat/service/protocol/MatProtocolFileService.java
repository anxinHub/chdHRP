/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.protocol;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.mat.entity.MatProtocolFile;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_PROTOCOL_Files
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatProtocolFileService {

	/**
	 * @Description 
	 * 添加 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMatProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMatProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @De
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMatProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatProtocolFile(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MatProtocolFile queryMatProtocolFileByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatProtocolFile
	 * @throws DataAccessException
	*/
	public MatProtocolFile queryMatProtocolFileByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
