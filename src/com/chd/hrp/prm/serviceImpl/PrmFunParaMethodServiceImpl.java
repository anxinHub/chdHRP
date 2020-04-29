
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
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmFunParaMethodMapper;
import com.chd.hrp.prm.entity.PrmFunParaMethod;
import com.chd.hrp.prm.service.PrmFunParaMethodService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9912 绩效函数参数取值表
 * @Table:
 * PRM_FUN_PARA_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmFunParaMethodService")
public class PrmFunParaMethodServiceImpl implements PrmFunParaMethodService {

	private static Logger logger = Logger.getLogger(PrmFunParaMethodServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmFunParaMethodMapper")
	private final PrmFunParaMethodMapper prmFunParaMethodMapper = null;
    
	/**
	 * @Description 
	 * 添加9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFunParaMethod(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9912 绩效函数参数取值表
		PrmFunParaMethod prmFunParaMethod = queryPrmFunParaMethodByCode(entityMap);

		if (prmFunParaMethod != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmFunParaMethodMapper.addPrmFunParaMethod(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFunParaMethod\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9912 绩效函数参数取值表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmFunParaMethod(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmFunParaMethodMapper.addBatchPrmFunParaMethod(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFunParaMethod\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmFunParaMethod(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmFunParaMethodMapper.updatePrmFunParaMethod(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmFunParaMethod\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9912 绩效函数参数取值表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmFunParaMethod(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmFunParaMethodMapper.updateBatchPrmFunParaMethod(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFunParaMethod\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmFunParaMethod(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmFunParaMethodMapper.deletePrmFunParaMethod(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFunParaMethod\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9912 绩效函数参数取值表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmFunParaMethod(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmFunParaMethodMapper.deleteBatchPrmFunParaMethod(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFunParaMethod\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmFunParaMethod(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmFunParaMethod> list = prmFunParaMethodMapper.queryPrmFunParaMethod(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmFunParaMethod> list = prmFunParaMethodMapper.queryPrmFunParaMethod(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9912 绩效函数参数取值表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmFunParaMethod queryPrmFunParaMethodByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return prmFunParaMethodMapper.queryPrmFunParaMethodByCode(entityMap);
		
	}
	/**
	 * 应用模式
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmFunParaByDict(Map<String, Object> map) throws DataAccessException {
		
		PrmFunParaMethod pfpm= prmFunParaMethodMapper.queryPrmFunParaMethodByCode(map);
		
		if (pfpm!=null){
			
			map.put("sql", pfpm.getPara_sql().replace("{", "#{"));
			 
			
			return JSON.toJSONString(prmFunParaMethodMapper.queryPrmFunParaByDict(map));
		}
		else {
			return "{\"error\":\"该函数编码没有配置部件类型\"}";
			
		}
		
		
	}
	
	@Override
	public List<Map<String, Object>> queryPrmFunParaMethodPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmFunParaMethod> list = prmFunParaMethodMapper.queryPrmFunParaMethod(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}
	 
}
