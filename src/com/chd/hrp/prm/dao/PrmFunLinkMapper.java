
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.dao;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.prm.entity.PrmFunLink;
/**
 * 
 * @Description:
 * 9910 绩效函数页面配置表
 * @Table:
 * PRM_FUN_LINK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmFunLinkMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmFunLink(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmFunLink(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmFunLink(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return PrmFunLink
	 * @throws DataAccessException
	*/
	public int updateBatchPrmFunLink(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmFunLink(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmFunLink(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9910 绩效函数页面配置表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmFunLink> queryPrmFunLink(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9910 绩效函数页面配置表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmFunLink> queryPrmFunLink(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return PrmFunLink
	 * @throws DataAccessException
	*/
	public PrmFunLink queryPrmFunLinkByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
