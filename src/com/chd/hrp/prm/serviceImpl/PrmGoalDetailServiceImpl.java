
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmGoalDetailKpiMapper;
import com.chd.hrp.prm.dao.PrmGoalDetailMapper;
import com.chd.hrp.prm.entity.PrmGoalDetail;
import com.chd.hrp.prm.service.PrmGoalDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0102 目标管理明细表
 * @Table:
 * PRM_GOAL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmGoalDetailService")
public class PrmGoalDetailServiceImpl implements PrmGoalDetailService {

	private static Logger logger = Logger.getLogger(PrmGoalDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmGoalDetailMapper")
	private final PrmGoalDetailMapper prmGoalDetailMapper = null;
	
	@Resource(name = "prmGoalDetailKpiMapper")
	private final PrmGoalDetailKpiMapper prmGoalDetailKpiMapper = null;
	/**
	 * @Description 
	 * 添加0102 目标管理明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmGoalDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		
		
		List<Map<String, Object>>  listUpdate = (List<Map<String, Object>>) entityMap.get("listUpdateVo");
		
		List<Map<String, Object>>  listAdd = (List<Map<String, Object>>) entityMap.get("listAddVo");
		
		if(listUpdate.size()>0){
			
			  prmGoalDetailMapper.updateBatchPrmGoalDetail(listUpdate);
			
			return  "{\"msg\":\"更新成功 数据库异常 请联系管理员! 方法 addPrmHosKpiSummary\"}";
			
		}
		
		if(listAdd.size()<=0){
			/*
			//获取对象0102 目标管理明细表
			PrmGoalDetail prmGoalDetail = queryPrmGoalDetailByCode(entityMap);

			if (prmGoalDetail != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";

			}
			*/
			prmGoalDetailMapper.addBatchPrmGoalDetail(listAdd);
			
			return "{\"msg\":\"添加成功 数据库异常 请联系管理员! 方法 addPrmHosKpiSummary\"}";
			
		}

		return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 addPrmHosKpiSummary\"}";
		
	}
	/**
	 * @Description 
	 * 批量添加0102 目标管理明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmGoalDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmGoalDetailMapper.addBatchPrmGoalDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmGoalDetail\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0102 目标管理明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmGoalDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmGoalDetailMapper.updatePrmGoalDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmGoalDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0102 目标管理明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmGoalDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try { 
			
		  prmGoalDetailMapper.updateBatchPrmGoalDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmGoalDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0102 目标管理明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmGoalDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmGoalDetailMapper.deletePrmGoalDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmGoalDetail\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0102 目标管理明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmGoalDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			prmGoalDetailKpiMapper.deleteBatchPrmGoalDetailKpi(entityList);
			prmGoalDetailMapper.deleteBatchPrmGoalDetail(entityList);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmGoalDetail\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0102 目标管理明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmGoalDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmGoalDetail> list = prmGoalDetailMapper.queryPrmGoalDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmGoalDetail> list = prmGoalDetailMapper.queryPrmGoalDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0102 目标管理明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmGoalDetail queryPrmGoalDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmGoalDetailMapper.queryPrmGoalDetailByCode(entityMap);
	}
	
	
	@Override
	public String updateBatchPrmGoalDetailList(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();//存放添加明细
			List<Map<String, Object>> uodateList = new ArrayList<Map<String, Object>>();//存放修改明细
			
			JSONArray goal_detail__data=JSONArray.parseArray("");
			
			String prmGoalDetailJson =""; 
			
			//判断页面传过的是     得到添加数据
			if (entityMap.get("goal_detail_add_data")!=""){
				//解析 添加的json串   目标管理明细表
				JSONArray goal_detail_add_data = JSONArray.parseArray((String) entityMap.get("goal_detail_add_data"));
				
				Iterator goal_detail_add_it =  goal_detail_add_data.iterator();

				while (goal_detail_add_it.hasNext()) {
					Map<String, Object> mapDetailVo = new HashMap<String, Object>();
					
					JSONObject jsonObj = JSONObject.parseObject(goal_detail_add_it.next().toString());
					
					if(jsonObj.get("child_goal_code") != null && !"".equals(jsonObj.get("child_goal_code").toString())){
						
						mapDetailVo.put("group_id", SessionManager.getGroupId()); 
						mapDetailVo.put("hos_id", SessionManager.getHosId());
						mapDetailVo.put("copy_code", SessionManager.getCopyCode());
						mapDetailVo.put("child_goal_code", jsonObj.get("child_goal_code"));
						mapDetailVo.put("child_goal_name", jsonObj.get("child_goal_name"));
						
						if(jsonObj.get("child_goal_note") != null){
							mapDetailVo.put("child_goal_note", jsonObj.get("child_goal_note"));
						}else{
							mapDetailVo.put("child_goal_note","");
						}
						
						mapDetailVo.put("acc_year", entityMap.get("acc_year"));
						mapDetailVo.put("goal_code", entityMap.get("goal_code"));
						 
						addList.add(mapDetailVo);
					}
				} 
			}
			
			
			if (entityMap.get("goal_detail_update_data")!=""){
				//解析更新的sjson串 目标管理明细表
				JSONArray goal_detail_update_data = JSONArray.parseArray((String) entityMap.get("goal_detail_update_data"));
				Iterator goal_detail_update_it =  goal_detail_update_data.iterator();

				while (goal_detail_update_it.hasNext()) {

					Map<String, Object> mapDetailVo = new HashMap<String, Object>();
					JSONObject jsonObj = JSONObject.parseObject(goal_detail_update_it.next().toString());
					
					if(jsonObj.get("child_goal_code") != null && !"".equals(jsonObj.get("child_goal_code"))){
						mapDetailVo.put("group_id", SessionManager.getGroupId()); 
						mapDetailVo.put("hos_id", SessionManager.getHosId());
						mapDetailVo.put("copy_code", SessionManager.getCopyCode());
						mapDetailVo.put("child_goal_code", jsonObj.get("child_goal_code"));
						mapDetailVo.put("child_goal_name", jsonObj.get("child_goal_name"));
						if(jsonObj.get("child_goal_note") != null){
							mapDetailVo.put("child_goal_note", jsonObj.get("child_goal_note"));
						}else{
							mapDetailVo.put("child_goal_note", "");
						}
						
						mapDetailVo.put("acc_year", entityMap.get("acc_year"));
						mapDetailVo.put("goal_code", entityMap.get("goal_code"));
						
						uodateList.add(mapDetailVo);
					} 
				}
			}
			
			if(addList.size() > 0 ){
				//执行添加方法
				prmGoalDetailMapper.addBatchPrmGoalDetail(addList);
			}
			
			if(uodateList.size() > 0 ){
				
				//执行更新方法
				prmGoalDetailMapper.updateBatchPrmGoalDetail(uodateList);
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			throw new SysException("操作失败 ");
		}
	}
}
