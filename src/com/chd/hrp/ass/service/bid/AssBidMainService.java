/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.bid;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.bid.AssBidMain;
import com.chd.hrp.ass.entity.plan.AssPlanDept;
/**
 * 
 * @Description:
 * 050401 招标管理
 * @Table:
 * ASS_BID_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssBidMainService {
	/**
	 * 获取当前的序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssBidMainSequence()throws DataAccessException;
	/**
	 * @Description 
	 * 添加050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssBidMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssBidMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssBidMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssBidMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssBidMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssBidMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssBidMain(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssBidMain(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集050401 招标管理<BR> (关联招标文件)
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssBidFileMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssBidMain queryAssBidMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidMain
	 * @throws DataAccessException
	*/
	public AssBidMain queryAssBidMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidMain>
	 * @throws DataAccessException
	*/
	public List<AssBidMain> queryAssBidMainExists(Map<String,Object> entityMap)throws DataAccessException;

	//审核
	public String updateToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	//销审
	public String updateNotToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	
	public List<AssBidMain> queryAssBidMainList(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 引入购置计划时   往中间表 添加数据  《ASS_BID_PLAN_MAP》
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssBidMainImportPlan(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 引入购置计划时  批量往中间表 添加数据  《ASS_BID_PLAN_MAP》
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssBidMainImportPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
			
}
