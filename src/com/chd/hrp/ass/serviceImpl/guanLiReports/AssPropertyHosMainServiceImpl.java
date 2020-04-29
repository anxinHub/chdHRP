/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.guanLiReports;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.guanLiReports.AssPropertyMonthMainMapper;
import com.chd.hrp.ass.entity.disposal.AssDisposalMain;
import com.chd.hrp.ass.entity.guanLiReports.AssPropertyMonthMain;
import com.chd.hrp.ass.service.guanLiReports.AssPropertyHosMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051101 资产汇总   全院
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assPropertyHosMainService")
public class AssPropertyHosMainServiceImpl implements AssPropertyHosMainService {

	private static Logger logger = Logger.getLogger(AssPropertyHosMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPropertyMonthMainMapper")
	private final AssPropertyMonthMainMapper assPropertyMonthMainMapper = null;
 

	/**
	 * @Description 
	 * 查询结果集051101   (资产汇总  全院    <财务制度>)
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssPropertyHosMain(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List <AssPropertyMonthMain> list = assPropertyMonthMainMapper.queryAssPropertyHosMain(entityMap) ;
			 
			return ChdJson.toJson(list);
			
		}else{
			
		
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List <AssPropertyMonthMain> list = assPropertyMonthMainMapper.queryAssPropertyHosMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 查询结果集051101   (资产汇总  全院    <财务制度>)
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List <AssPropertyMonthMain> list = null ;
			 
			if (entityMap.get("ass_nature").equals("02")){ 
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainSpecial(entityMap);
			
				}
			if (entityMap.get("ass_nature").equals("03")){
				 
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainGeneral(entityMap);
			
				}
			if (entityMap.get("ass_nature").equals("01")){
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainHouse(entityMap);
			
				}
			if (entityMap.get("ass_nature").equals("04")){
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainOther(entityMap);
			
				} 
			
			return ChdJson.toJson(list);
			
		}else{
			
		
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List <AssPropertyMonthMain> list = null ;
		 
			if (entityMap.get("ass_nature").equals("02")){ 
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainSpecial(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("03")){
				 
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainGeneral(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("01")){
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainHouse(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("04")){
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainOther(entityMap, rowBounds);
			
				}  
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	
	/**
	 * @Description 
	 * 查询结果集051101   (资产汇总  全院   <内部管理>)
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssPropertyHosMainManage(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List <AssPropertyMonthMain> list = null ;
			 
			if (entityMap.get("ass_nature").equals("02")){ 
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainManageSpecial(entityMap);
			
				}
			if (entityMap.get("ass_nature").equals("03")){
				 
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainManageGeneral(entityMap);
			
				}
			if (entityMap.get("ass_nature").equals("01")){
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainManageHouse(entityMap);
			
				}
			if (entityMap.get("ass_nature").equals("04")){
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainManageOther(entityMap);
			
				}  
			
			return ChdJson.toJson(list);
			
		}else{
			

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List <AssPropertyMonthMain> list = null ;
		 
			if (entityMap.get("ass_nature").equals("02")){ 
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainManageSpecial(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("03")){  
				 
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainManageGeneral(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("01")){ 
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainManageHouse(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("04")){
				
				list = assPropertyMonthMainMapper.queryAssPropertyHosMainManageOther(entityMap, rowBounds);
			
				}  
			 
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}	 
		
	}
	
	
	
	/**
	 * @Description 
	 * 获取对象051101 盘点单<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assPropertyMonthMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 盘点单<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCheckMain
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assPropertyMonthMainMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 盘点单<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCheckMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assPropertyMonthMainMapper.queryExists(entityMap);
	}

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> queryAssPropertyHosMainPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		if(entityMap.get("year_month") != null){
			String year = entityMap.get("year_month").toString().substring(0, 4);
			String month = entityMap.get("year_month").toString().substring(4, 6);
			entityMap.put("acc_year",year);
			entityMap.put("acc_month",month);
		}
		List<Map<String, Object>> list = assPropertyMonthMainMapper.queryAssPropertyHosMainPrint(entityMap);
	
		return list;
	}


}
