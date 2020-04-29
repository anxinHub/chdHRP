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
import com.chd.hrp.mat.entity.MatProtocolFile;
/**
 * 
 * @Description:
 * 04503 付款协议明细表
 * @Table:
 * MAT_PROTOCOL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatProtocolFileMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新
	 * @param  entityMap
	 * @return MatProtocolFile
	 * @throws DataAccessException
	*/
	public int updateBatchMatProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatProtocolFile> queryMatProtocolFile(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集  带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatProtocolFile> queryMatProtocolFile(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatProtocolFile
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
	public MatProtocolFile queryMatProtocolFileByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 协议文档详细 信息（多表查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatProtocolFileByID(Map<String, Object> mapVo) throws DataAccessException;
	
	
	
}
