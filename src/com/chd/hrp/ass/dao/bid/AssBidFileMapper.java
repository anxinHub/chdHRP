/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bid;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.bid.AssBidFile;
 

public interface AssBidFileMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssBidFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssBidFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssBidFile(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return AssBidFile
	 * @throws DataAccessException
	*/
	public int updateBatchAssBidFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssBidFile(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssBidFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050401 招标资产明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidFile> queryAssBidFile(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050401 招标资产明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidFile> queryAssBidFile(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssBidFile
	 * @throws DataAccessException
	*/
	public AssBidFile queryAssBidFileByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidFile
	 * @throws DataAccessException
	*/
	public AssBidFile queryAssBidFileByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidFile>
	 * @throws DataAccessException
	*/
	public List<AssBidFile> queryAssBidFileExists(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
