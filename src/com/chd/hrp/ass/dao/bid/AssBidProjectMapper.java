/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bid;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.bid.AssBidProject;
/**
 * 
 * @Description:
 * 050401 投标立项
 * @Table:
 * ASS_BID_MAIN
 * @Author: brian
 * @email:  brian@e-tonggroup.com 
 * @Version: 1.0
 */
 

public interface AssBidProjectMapper extends SqlMapper{
	/**
	 * 获取当前的序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssBidProjectSequence()throws DataAccessException;
	/**
	 * @Description 
	 * 添加050401 投标立项<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssBidProject(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050401 投标立项<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssBidProject(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050401 投标立项<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssBidProject(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050401 投标立项<BR> 
	 * @param  entityMap
	 * @return AssBidMain
	 * @throws DataAccessException
	*/
	public int updateBatchAssBidProject(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050401 投标立项<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssBidProject(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050401 投标立项<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssBidProject(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050401 投标立项<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidProject> queryAssBidProject(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集050401 投标立项<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidProject> queryAssBidProject(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 投标立项<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssBidMain
	 * @throws DataAccessException
	*/
	public AssBidProject queryAssBidProjectByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 投标立项<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidMain
	 * @throws DataAccessException
	*/
	public AssBidProject queryAssBidProjectByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 投标立项<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidMain>
	 * @throws DataAccessException
	*/
	public List<AssBidProject> queryAssBidProjectExists(Map<String,Object> entityMap)throws DataAccessException;
	
}
