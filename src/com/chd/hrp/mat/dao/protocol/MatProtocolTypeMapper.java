/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.protocol;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatProtocolType;
/**
 * 
 * @Description:
 * 04501 付款协议类别
 * @Table:
 * MAT_PROTOCOL_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatProtocolTypeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatProtocolType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatProtocolType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatProtocolType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return MatProtocolType
	 * @throws DataAccessException
	*/
	public int updateBatchMatProtocolType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatProtocolType(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatProtocolType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04501 付款协议类别<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatProtocolType> queryMatProtocolType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04501 付款协议类别<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatProtocolType> queryMatProtocolType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04501 付款协议类别<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatProtocolType
	 * @throws DataAccessException
	*/
	public MatProtocolType queryMatProtocolTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04501 付款协议类别<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatProtocolType
	 * @throws DataAccessException
	*/
	public MatProtocolType queryMatProtocolTypeByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 根据 输入的类别编码、类别名称查询协议类别（判断类别编码、类别名称是否已存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatProtocolType> queryMatProtocolTypeByID(Map<String, Object> entityMap)  throws DataAccessException;
	/**
	 * 获取当前物流管理系统的启用年月
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
}
