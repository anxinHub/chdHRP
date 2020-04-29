
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.dao.PrmHosKpiMapper;
import com.chd.hrp.prm.dao.PrmHosKpiObjMapper;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmHosKpi;
import com.chd.hrp.prm.entity.PrmHosKpiObj;
import com.chd.hrp.prm.entity.PrmKpiDim;
import com.chd.hrp.prm.entity.PrmKpiLib;
import com.chd.hrp.prm.service.PrmHosKpiService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0201 医院绩效考核指标表
 * @Table:
 * PRM_HOS_KPI
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmHosKpiService")
public class PrmHosKpiServiceImpl implements PrmHosKpiService {

	private static Logger logger = Logger.getLogger(PrmHosKpiServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmHosKpiMapper")
	private final PrmHosKpiMapper prmHosKpiMapper = null;
	
	//引入DAO操作
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
	
	//引入DAO操作
	@Resource(name = "prmHosKpiObjMapper")
	private final PrmHosKpiObjMapper prmHosKpiObjMapper = null;
    
	/**
	 * @Description 
	 * 添加0201 医院绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmHosKpi(Map<String,Object> entityMap)throws DataAccessException{
		
		PrmGoal pg = prmGoalMapper.queryPrmGoalByCode(entityMap);
		
		if(pg.getIs_audit() == 1){
			return "{\"error\":\"已审核的目标不允许维护  \"}";
		}
		
		//获取对象0201 医院绩效考核指标表
		PrmHosKpi prmHosKpi = queryPrmHosKpiByCode(entityMap);
		
		//entityMap.put("", value)

		if (prmHosKpi != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		Map<String,Object> map = new  HashMap<String,Object>();
		
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("acc_year", entityMap.get("acc_year"));
		map.put("goal_code", entityMap.get("goal_code"));
		map.put("kpi_code", entityMap.get("super_kpi_code"));
		
		PrmHosKpi phk = queryPrmHosKpiByCode(map);
		
		if(phk != null){
			
			entityMap.put("kpi_level", phk.getKpi_level()+1);
		}else{//为空 就是级次就是TOP
			entityMap.put("kpi_level", 1);
		}
		
		try {
			
			int state = prmHosKpiMapper.addPrmHosKpi(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosKpi\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0201 医院绩效考核指标表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmHosKpi(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			List<Map<String,Object>> hosKpiList = new ArrayList<Map<String,Object>>();
			
			JSONArray hos_kpi_json = JSONArray.parseArray((String)entityMap.get("data"));
			
			Iterator hos_kpi_it = hos_kpi_json.iterator();
			
			String msg = "";
			
			while(hos_kpi_it.hasNext()){
				
				JSONObject jsonObj = JSONObject.parseObject(hos_kpi_it.next().toString());
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				
				mapVo.put("group_id", jsonObj.get("group_id"));
				
				mapVo.put("hos_id", jsonObj.get("hos_id"));
				
				mapVo.put("copy_code", jsonObj.get("copy_code"));
				
				mapVo.put("acc_year", jsonObj.get("acc_year"));
				
				mapVo.put("goal_code", jsonObj.get("goal_code"));
				
				mapVo.put("kpi_code", jsonObj.get("kpi_code"));
				
				PrmGoal pg = prmGoalMapper.queryPrmGoalByCode(mapVo);
				
				if(pg.getIs_audit() == 1){
					
					msg =  "{\"error\":\"已审核的目标不允许维护  \"}";
					
					break;
				}
				
				PrmHosKpi  prmHosKpi = queryPrmHosKpiByCode(mapVo);
				
				if(prmHosKpi != null ){
					
					msg = "{\"error\":\"保存失败! "+mapVo.get("goal_code").toString()+"下已经存在该指标【"+mapVo.get("kpi_code")+"】\"}";
					
					break;
				}
				
				mapVo.put("kpi_name", jsonObj.get("kpi_name"));
				
				mapVo.put("dim_code", jsonObj.get("dim_code"));
				
				mapVo.put("nature_code", jsonObj.get("nature_code"));
				
				mapVo.put("super_kpi_code", jsonObj.get("super_kpi_code"));
				
				mapVo.put("order_no", jsonObj.get("order_no"));
				
				mapVo.put("kpi_level", jsonObj.get("kpi_level"));
				
				if(jsonObj.get("spell_code") == null){
					mapVo.put("spell_code",StringTool.toPinyinShouZiMu(String.valueOf(jsonObj.get("kpi_name"))));
				}else{
					mapVo.put("spell_code",jsonObj.get("spell_code"));
				}
				
				if(jsonObj.get("wbx_code") == null){
					mapVo.put("wbx_code", StringTool.toWuBi(String.valueOf(jsonObj.get("kpi_name"))));
				}else{
					mapVo.put("wbx_code", jsonObj.get("wbx_code"));
				}
				
				mapVo.put("is_last", jsonObj.get("is_last"));
				
				hosKpiList.add(mapVo);
				
			}
			
			if(msg != ""){
				
				return msg ;
			}
			
			prmHosKpiMapper.addBatchPrmHosKpi(hosKpiList);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException("{\"error\":\"保存失败 \"}");
		}
		
	}
	
		/**
	 * @Description 
	 * 更新0201 医院绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmHosKpi(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("acc_year", entityMap.get("acc_year"));
			map.put("goal_code", entityMap.get("goal_code"));
			map.put("kpi_code", entityMap.get("super_kpi_code"));
			
			//查询上级编码的级次
			PrmHosKpi phk = queryPrmHosKpiByCode(map);
			
			if(phk != null){//如果上级编码不是顶级TOP
				
				entityMap.put("kpi_level", phk.getKpi_level()+1);
			}else{
				entityMap.put("kpi_level", "1");
			}
			
			int state = prmHosKpiMapper.updatePrmHosKpi(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosKpi\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0201 医院绩效考核指标表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmHosKpi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmHosKpiMapper.updateBatchPrmHosKpi(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpi\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0201 医院绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmHosKpi(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmHosKpiMapper.deletePrmHosKpi(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosKpi\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0201 医院绩效考核指标表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmHosKpi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String msg = "";
			
			for(Map<String,Object> map: entityList){
				
				List<PrmHosKpiObj> list = prmHosKpiObjMapper.queryPrmHosKpiObj(map);
				
				if(list.size() > 0){
					msg = "{\"error\":\"医院考核对象中已引用指标【"+map.get("kpi_code").toString()+"】不能删除 \"}";
					break;
				}
			}
			
			if(!"".equals(msg)){
				
				return msg;
			}
			
			prmHosKpiMapper.deleteBatchPrmHosKpi(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosKpi\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0201 医院绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmHosKpi(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosKpi> list = prmHosKpiMapper.queryPrmHosKpi(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosKpi> list = prmHosKpiMapper.queryPrmHosKpi(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0201 医院绩效考核指标表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmHosKpi queryPrmHosKpiByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmHosKpiMapper.queryPrmHosKpiByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 查询对象0201 医院绩效考核指标表 查出结果 存储树形结构<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmHosKpiTree(Map<String, Object> entityMap) throws DataAccessException {
		
		List<PrmHosKpi> list_hos_kpi = prmHosKpiMapper.queryPrmHosKpi(entityMap);
		
		List<PrmGoal> list_goal = prmGoalMapper.queryPrmGoal(entityMap);
		
		StringBuilder json = new StringBuilder();
		
		if (list_hos_kpi.size() ==0 && list_goal.size() ==0) {

			json.append("{Rows:[]}");

			return json.toString();
			
		}

		try {

			json.append("{Rows:[");
			
			for (PrmGoal g : list_goal) {
				
				json.append("{");
				json.append("\"pid\":\"-1\"," + "\"id\":\"" + g.getGoal_code()+ "\"," + "\"text\":" + "\"" + g.getGoal_name()+ "\"");
				json.append("},");
			}
			
			for(PrmGoal g : list_goal){
				
				for (PrmHosKpi p : list_hos_kpi) {
					
					if(p.getGoal_code().equals(g.getGoal_code())){
						
						String pid = "";
						if(p.getSuper_kpi_code().equals("TOP")){
							pid = p.getGoal_code();
							json.append("{");
							json.append("\"pid\":\""+pid+"\"," + "\"id\":\"" + p.getGoal_code()+"," + p.getKpi_code()+ "\"," + "\"text\":" + "\"" + p.getKpi_name() + "\"");
							json.append("},");
						}else{
							pid = p.getGoal_code()+","+p.getSuper_kpi_code();
							json.append("{");
							json.append("\"pid\":\""+pid+"\"," + "\"id\":\"" + p.getGoal_code()+"," + p.getKpi_code()+ "\"," + "\"text\":" + "\"" + p.getKpi_name() + "\"");
							json.append("},");
						}		
					}
				}
			}
			
			json.setCharAt(json.length() - 1, ']');
			
			json.append("}");
			
		} catch (Exception e) {
			
			json.append("{Rows:[]}");

			return json.toString();
			
		}
		
		return json.toString();
	}
	
	
	public Map<String,Object> defaultMap(){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		
		return map;
	}
	
	@Override
	public String queryPrmHosKpis(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosKpi> list = prmHosKpiMapper.queryPrmHosKpis(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosKpi> list = prmHosKpiMapper.queryPrmHosKpis(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
}
