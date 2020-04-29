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
import com.chd.hrp.ass.entity.bid.AssBidMain;
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
 

public interface AssBidMainMapper extends SqlMapper{
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
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssBidMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssBidMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssBidMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050401 招标管理<BR> 
	 * @param  entityMap
	 * @return AssBidMain
	 * @throws DataAccessException
	*/
	public int updateBatchAssBidMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssBidMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050401 招标管理<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssBidMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050401 招标管理<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidMain> queryAssBidMain(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集050401 招标管理<BR>全部 (关联招标文件)
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidMain> queryAssBidFileMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<AssBidMain> queryAssBidFileMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集050401 招标管理<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidMain> queryAssBidMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050401 招标管理<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssBidMain
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
	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 消审<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateNotToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 引入购置计划时  批量往中间表 添加数据  《ASS_BID_PLAN_MAP》
	 * @param  entityMap	
	 * @return String
	 * @throws DataAccessException
	*/
	public int addBatchAssBidMainImportPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 引入购置计划时  往中间表 添加数据  《ASS_BID_PLAN_MAP》
	 * @param  entityMap	
	 * @return String
	 * @throws DataAccessException
	*/
	public int addAssBidMainImportPlan(Map<String,Object> entityMap)throws DataAccessException;
}
