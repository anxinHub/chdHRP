﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmFunLinkMapper;
import com.chd.hrp.prm.entity.PrmFunLink;
import com.chd.hrp.prm.service.PrmFunLinkService;
import com.github.pagehelper.PageInfo;

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
 


@Service("prmFunLinkService")
public class PrmFunLinkServiceImpl implements PrmFunLinkService {

	private static Logger logger = Logger.getLogger(PrmFunLinkServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmFunLinkMapper")
	private final PrmFunLinkMapper prmFunLinkMapper = null;
    
	/**
	 * @Description 
	 * 添加9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFunLink(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9910 绩效函数页面配置表
		PrmFunLink prmFunLink = queryPrmFunLinkByCode(entityMap);

		if (prmFunLink != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmFunLinkMapper.addPrmFunLink(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFunLink\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9910 绩效函数页面配置表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmFunLink(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmFunLinkMapper.addBatchPrmFunLink(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFunLink\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmFunLink(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmFunLinkMapper.updatePrmFunLink(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmFunLink\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9910 绩效函数页面配置表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmFunLink(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmFunLinkMapper.updateBatchPrmFunLink(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFunLink\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmFunLink(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmFunLinkMapper.deletePrmFunLink(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFunLink\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9910 绩效函数页面配置表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmFunLink(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmFunLinkMapper.deleteBatchPrmFunLink(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFunLink\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmFunLink(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmFunLink> list = prmFunLinkMapper.queryPrmFunLink(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmFunLink> list = prmFunLinkMapper.queryPrmFunLink(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9910 绩效函数页面配置表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmFunLink queryPrmFunLinkByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmFunLinkMapper.queryPrmFunLinkByCode(entityMap);
	}
	
}