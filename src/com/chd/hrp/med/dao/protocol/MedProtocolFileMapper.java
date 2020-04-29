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
import com.chd.hrp.med.entity.MedProtocolFile;
/**
 * 
 * @Description:
 * 04503 付款协议明细表
 * @Table:
 * MED_PROTOCOL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedProtocolFileMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新
	 * @param  entityMap
	 * @return MedProtocolFile
	 * @throws DataAccessException
	*/
	public int updateBatchMedProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedProtocolFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedProtocolFile> queryMedProtocolFile(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集  带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedProtocolFile> queryMedProtocolFile(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedProtocolFile
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
	public MedProtocolFile queryMedProtocolFileByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 协议文档详细 信息（多表查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedProtocolFileByID(Map<String, Object> mapVo) throws DataAccessException;
	
	
	
}
