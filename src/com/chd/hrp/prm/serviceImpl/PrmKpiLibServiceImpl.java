
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmKpiDimMapper;
import com.chd.hrp.prm.dao.PrmKpiLibMapper;
import com.chd.hrp.prm.entity.PrmKpiDim;
import com.chd.hrp.prm.entity.PrmKpiLib;
import com.chd.hrp.prm.service.PrmKpiLibService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0502 KPI指标库表
 * @Table:
 * PRM_KPI_LIB
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmKpiLibService")
public class PrmKpiLibServiceImpl implements PrmKpiLibService {

	private static Logger logger = Logger.getLogger(PrmKpiLibServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmKpiLibMapper")
	private final PrmKpiLibMapper prmKpiLibMapper = null;
	
	//引入DAO操作
	@Resource(name = "prmKpiDimMapper")
	private final PrmKpiDimMapper prmKpiDimMapper = null;
    
	/**
	 * @Description 
	 * 添加0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmKpiLib(Map<String,Object> entityMap)throws DataAccessException{
		 
		//获取对象0502 KPI指标库表
		PrmKpiLib prmKpiLib = queryPrmKpiLibByCode(entityMap);

		if (prmKpiLib != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmKpiLibMapper.addPrmKpiLib(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmKpiLib\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0502 KPI指标库表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmKpiLib(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmKpiLibMapper.addBatchPrmKpiLib(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmKpiLib\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmKpiLib(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmKpiLibMapper.updatePrmKpiLib(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmKpiLib\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0502 KPI指标库表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmKpiLib(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmKpiLibMapper.updateBatchPrmKpiLib(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmKpiLib\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmKpiLib(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmKpiLibMapper.deletePrmKpiLib(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmKpiLib\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0502 KPI指标库表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmKpiLib(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmKpiLibMapper.deleteBatchPrmKpiLib(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmKpiLib\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmKpiLib(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmKpiLib> list = prmKpiLibMapper.queryPrmKpiLib(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmKpiLib> list = prmKpiLibMapper.queryPrmKpiLib(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmKpiLib queryPrmKpiLibByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmKpiLibMapper.queryPrmKpiLibByCode(entityMap);
	}
	/**
	 * @Description 
	 * KPI指标库树形菜单
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmKpiLibByMenu(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		

		List<PrmKpiDim> list  = prmKpiDimMapper.queryPrmKpiDim(entityMap);
		
		List<PrmKpiLib> listNature = prmKpiLibMapper.queryPrmKpiLibDimPkn(entityMap);
		
		StringBuilder json = new StringBuilder();
		
		
		if(list.size()==0  && listNature.size()==0){
			
			
			json.append("{Rows:[]}");
			
			return json.toString();
		}
		
	
		
		try {
			
		
			
		   	json.append("{Rows:[");
		   	
		   	//json.append("{");
		   	
		   	//json.append("\"pid\":\"-2\","+"\"id\":\"-1\","+"\"text\":"+"\"指标库\""); 
		   	
		   //	json.append("},");
		   	json.append("{");
			
			json.append("\"pid\":\"-2\","+"\"id\":\"-1\","+"\"text\":"+"\"指标库\"");

			json.append("},");
		   	
		   	for(PrmKpiDim  t :  list ){
		   		
				json.append("{");
				
				json.append("\"pid\":\"-1\","+"\"id\":\""+t.getDim_code()+"\","+"\"text\":"+"\""+t.getDim_name()+"\"");

				json.append("},");
		   	}
			
		   
		    	for(PrmKpiLib  tn :  listNature ){
		   		
					json.append("{");
					
					json.append("\"pid\":\""+tn.getDim_code()+"\","+"\"id\":\""+tn.getKpi_code()+"\","+"\"text\":"+"\""+tn.getKpi_name()+"\"");

					json.append("},");
			   	}
	     

	   
			json.setCharAt(json.length() - 1, ']');
			json.append("}");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
        return json.toString(); 
        
	}
	@Override
	public String queryPrmKpiLibDimPkn(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
           SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmKpiLib> list = prmKpiLibMapper.queryPrmKpiLibDimPkn(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmKpiLib> list = prmKpiLibMapper.queryPrmKpiLibDimPkn(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryPrmKpiLibDimPkns(Map<String, Object> entityMap) throws DataAccessException {
		
		List<PrmKpiLib> list = prmKpiLibMapper.queryPrmKpiLibDimPkns(entityMap);
		
		return ChdJson.toJson(list);
//		SysPage sysPage = new SysPage();
//		
//		sysPage = (SysPage) entityMap.get("sysPage");
//		
//		if (sysPage.getTotal()==-1){
//			
//			List<PrmKpiLib> list = prmKpiLibMapper.queryPrmKpiLibDimPkns(entityMap);
//			
//			return ChdJson.toJson(list);
//			
//		}else{
//			
//			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
//			
//			List<PrmKpiLib> list = prmKpiLibMapper.queryPrmKpiLibDimPkns(entityMap, rowBounds);
//			
//			PageInfo page = new PageInfo(list);
//			
//			return ChdJson.toJson(list, page.getTotal());
//			
//		}
	}
	@Override
	public List<Map<String, Object>> queryPrmKpiLibPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmKpiLib> list = prmKpiLibMapper.queryPrmKpiLib(entityMap);
		
		return JsonListMapUtil.beanToListMap(list);
	}
}
