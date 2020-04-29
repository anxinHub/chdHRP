
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
 
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmTargetMapper;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.service.PrmTargetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9901 绩效指标字典表
 * @Table:
 * PRM_TARGET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmTargetService")
public class PrmTargetServiceImpl implements PrmTargetService {

	private static Logger logger = Logger.getLogger(PrmTargetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmTargetMapper")
	private final PrmTargetMapper prmTargetMapper = null;
    
	/**
	 * @Description 
	 * 添加9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmTarget(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9901 根据编码查询绩效指标字典表
		PrmTarget prmTarget = queryPrmTargetByCode(entityMap);

		if (prmTarget != null) {

			return "{\"error\":\"指标编码重复,请重新添加.\"}";

		}
		
		//获取对象9901 根据名称查询 绩效指标字典表
		PrmTarget pt = prmTargetMapper.queryPrmTargetByName(entityMap);
		
		if (pt != null) {

			return "{\"error\":\"指标名称重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmTargetMapper.addPrmTarget(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmTarget\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9901 绩效指标字典表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmTarget(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmTargetMapper.addBatchPrmTarget(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmTarget\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmTarget(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
		
		//获取对象9901 根据名称查询 绩效指标字典表
		PrmTarget pt = prmTargetMapper.queryPrmTargetByName(entityMap);
			
		if (pt != null) {

			return "{\"error\":\"指标名称重复.\"}";

		}
			
		int state = prmTargetMapper.updatePrmTarget(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmTarget\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9901 绩效指标字典表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmTarget(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmTargetMapper.updateBatchPrmTarget(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmTarget\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmTarget(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmTargetMapper.deletePrmTarget(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmTarget\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9901 绩效指标字典表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmTarget(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmTargetMapper.deleteBatchPrmTarget(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmTarget\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmTarget(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmTarget> list = prmTargetMapper.queryPrmTarget(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmTarget> list = prmTargetMapper.queryPrmTarget(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmTarget queryPrmTargetByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmTargetMapper.queryPrmTargetByCode(entityMap);
	}
	@Override
	public String queryPrmTargetNature(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
              SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmTarget> list = prmTargetMapper.queryPrmTargetNature(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmTarget> list = prmTargetMapper.queryPrmTargetNature(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * @Description 
	 * 获取对象9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmTarget queryPrmTargetNatureGetByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return prmTargetMapper.queryPrmTargetNatureGetByCode(entityMap);
	}
	@Override
	public String queryTargetTree(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List list = prmTargetMapper.queryPrmTarget(entityMap);
		
		List listNature =prmTargetMapper.queryPrmTargetNatureDanbiao(entityMap);
		
		// 拼接SQL
		StringBuilder json = new StringBuilder();
		
		json.append("{Rows:[");
		
		for( int i =0 ;i < listNature.size(); i++){
			
			PrmTarget t = (PrmTarget)listNature.get(i);
			
			json.append("{");
			
			json.append("\"pid\":\"0\","+"\"id\":\""+t.getNature_code()+"\","+"\"text\":"+"\""+t.getNature_name()+"\"");

			json.append("},");
		}
		
		
		for( int j = 0 ; j < list.size() ;j++){
			
			PrmTarget tn=(PrmTarget)list.get(j);
			
			json.append("{");
			
			json.append("\"pid\":\""+tn.getTarget_nature()+"\","+"\"id\":\""+tn.getTarget_code()+"\","+"\"text\":"+"\""+tn.getTarget_name()+"\"");

			json.append("},");
		}
		
		

		json.setCharAt(json.length() - 1, ']');
		json.append("}");
 
        return json.toString();  
		//return JsonListBeanUtil.listToJson();
	}
	
	
	@Override
	public List<Map<String, Object>> queryPrmTargetNaturePrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmTarget> list = prmTargetMapper.queryPrmTargetNature(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}
	
}
