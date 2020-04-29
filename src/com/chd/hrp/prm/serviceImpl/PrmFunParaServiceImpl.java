
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

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson; 
import com.chd.hrp.prm.dao.PrmFunParaMapper;
import com.chd.hrp.prm.dao.PrmFunParaMethodMapper;
import com.chd.hrp.prm.entity.PrmFunPara;
import com.chd.hrp.prm.entity.PrmFunParaMethod;
import com.chd.hrp.prm.service.PrmFunParaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9911 绩效函数参数表
 * @Table:
 * PRM_FUN_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmFunParaService")
public class PrmFunParaServiceImpl implements PrmFunParaService {

	private static Logger logger = Logger.getLogger(PrmFunParaServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmFunParaMapper")
	private final PrmFunParaMapper prmFunParaMapper = null;
	
	//引入DAO操作
		@Resource(name = "prmFunParaMethodMapper")
		private final PrmFunParaMethodMapper prmFunParaMethodMapper = null;
	
	/**
	 * @Description 
	 * 添加9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFunPara(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9911 绩效函数参数表
		PrmFunPara prmFunPara = queryPrmFunParaByCode(entityMap);

		if (prmFunPara != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmFunParaMapper.addPrmFunPara(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFunPara\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9911 绩效函数参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmFunPara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmFunParaMapper.addBatchPrmFunPara(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFunPara\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmFunPara(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmFunParaMapper.updatePrmFunPara(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmFunPara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9911 绩效函数参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmFunPara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmFunParaMapper.updateBatchPrmFunPara(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFunPara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmFunPara(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmFunParaMapper.deletePrmFunPara(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFunPara\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9911 绩效函数参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmFunPara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmFunParaMapper.deleteBatchPrmFunPara(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFunPara\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmFunPara(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmFunPara> list = prmFunParaMapper.queryPrmFunPara(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmFunPara> list = prmFunParaMapper.queryPrmFunPara(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9911 绩效函数参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmFunPara queryPrmFunParaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmFunParaMapper.queryPrmFunParaByCode(entityMap);
	}
	
	@Override
    public String queryComTypePara(Map<String, Object> entityMap) throws DataAccessException {
	    
		List<PrmFunPara> list = prmFunParaMapper.queryComTypePara(entityMap); 
		 
		if (list.size() >0){
	 
			return ChdJson.toJson(list);

		}
		else { 
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
		}
		

    }
	@Override
	public String queryComTypeParaByDept(Map<String, Object> entityMap) throws DataAccessException {
		List<PrmFunPara> list = prmFunParaMapper.queryComTypeParaByDept(entityMap); 
		 
		if (list.size() >0){
	 
			return ChdJson.toJson(list);

		}
		else { 
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
		}
	}
	@Override
	public String queryComTypeParaByEmp(Map<String, Object> entityMap) throws DataAccessException {
		List<PrmFunPara> list = prmFunParaMapper.queryComTypeParaByEmp(entityMap); 
		 
		if (list.size() >0){
	 
			return ChdJson.toJson(list);

		}
		else { 
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
		}
	}
	@Override
	public String queryComTypeParaByHos(Map<String, Object> entityMap) throws DataAccessException {
		List<PrmFunPara> list = prmFunParaMapper.queryComTypeParaByHos(entityMap); 
		 
		if (list.size() >0){
	 
			return ChdJson.toJson(list);

		}
		else { 
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
		}
	}
	@Override
	public String queryPrmFunParaByFunCode(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmFunPara> list = prmFunParaMapper.queryPrmFunParaByFunCode(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmFunPara> list = prmFunParaMapper.queryPrmFunParaByFunCode(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
    
	
}
