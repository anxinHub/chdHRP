/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.bid;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.bid.AssBidProject;
import com.chd.hrp.ass.entity.plan.AssPlanDept;
/**
 * 
 * @Description:
 * 050401 招标管理
 * @Table:
 * ASS_BID_Project
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssBidProjectService {
	/**
	 * 获取当前的序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssBidProjectSequence()throws DataAccessException;
	/**
	 * @Description 
	 * 添加050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssBidProject(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssBidProject(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssBidProject(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssBidProject(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssBidProject(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssBidProject(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssBidProject(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssBidProject(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询对象050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssBidProject queryAssBidProjectByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidProject
	 * @throws DataAccessException
	*/
	public AssBidProject queryAssBidProjectByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidProject>
	 * @throws DataAccessException
	*/
	public List<AssBidProject> queryAssBidProjectExists(Map<String,Object> entityMap)throws DataAccessException;

	
	public List<AssBidProject> queryAssBidProjectList(Map<String,Object> entityMap)throws DataAccessException;
	
			
}
