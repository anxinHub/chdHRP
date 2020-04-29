
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.serviceImpl.asstendcheck;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.asstendcheck.AssTendCheckMapper;
import com.chd.hrp.ass.service.asstendcheck.AssTendCheckService;
import com.chd.hrp.mat.entity.MatInvCertSup;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 定标审核
 */

@Service("assTendCheckService")
public class AssTendCheckServiceImpl implements AssTendCheckService {

	private static Logger logger = Logger.getLogger(AssTendCheckServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assTendCheckMapper")
	private final AssTendCheckMapper assTendCheckMapper = null;
	
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssTendCheck(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = assTendCheckMapper.queryAssTendCheck(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = assTendCheckMapper.queryAssTendCheck(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	
	/**
	 * @Description 
	 * 更新<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
		 * @throws ParseException 
	*/
	@Override
	public String updateAssTendCheck(Map<String,Object> entityMap)throws DataAccessException, ParseException{
		try{
			
			assTendCheckMapper.updateAssTendCheck(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败!\"}");
		}	
		
	}
	/**
	 * @Description 
	 * 批量更新MAT_INV_CERT<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssTendCheck(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assTendCheckMapper.updateBatchAssTendCheck(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatInvCert\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssTendCheck(Map<String, Object> entityMap) throws DataAccessException {
    	
	    try {
				assTendCheckMapper.deleteAssTendCheck(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败!\"}");
			}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssTendCheck(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assTendCheckMapper.deleteBatchAssTendCheck(entityList);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败!\"}");
		}	
	}


	@Override
	public String updateAssTendCheckState(List<Map<String, Object>> entityList)	throws DataAccessException {
		try {
			
			assTendCheckMapper.updateAssTendCheckState(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败!\"}");
		}	
	}


	@Override
	public String updateAssTendCheckInfo(List<Map<String, Object>> listVo)	throws DataAccessException {
		try {
			
			assTendCheckMapper.updateAssTendCheckInfo(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败!\"}");
		}	
	}
	
	
}
