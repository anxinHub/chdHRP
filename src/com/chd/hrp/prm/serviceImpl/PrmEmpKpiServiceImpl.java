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
import com.chd.hrp.prm.dao.PrmEmpKpiMapper;
import com.chd.hrp.prm.dao.PrmEmpKpiObjMapper;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.entity.PrmEmpKpi;
import com.chd.hrp.prm.entity.PrmEmpKpiObj;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.service.PrmEmpKpiService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0401 职工绩效考核指标表
 * @Table: PRM_EMP_KPI
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmEmpKpiService")
public class PrmEmpKpiServiceImpl implements PrmEmpKpiService {

	private static Logger logger = Logger.getLogger(PrmEmpKpiServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmEmpKpiMapper")
	private final PrmEmpKpiMapper prmEmpKpiMapper = null;

	// 引入DAO操作
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
	
	// 引入DAO操作
	@Resource(name = "prmEmpKpiObjMapper")
	private final PrmEmpKpiObjMapper prmEmpKpiObjMapper = null;
	
	
	/**
	 * @Description 添加0401 职工绩效考核指标表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmEmpKpi(Map<String, Object> entityMap) throws DataAccessException {

		PrmGoal pg = prmGoalMapper.queryPrmGoalByCode(entityMap);
		
		if(pg.getIs_audit() == 1){
			return "{\"error\":\"已审核的目标不允许维护  \"}";
		}
		
		//获取对象0201 医院绩效考核指标表
		PrmEmpKpi prmEmpKpi = queryPrmEmpKpiByCode(entityMap);
		
		//entityMap.put("", value)

		if (prmEmpKpi != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		Map<String,Object> map = new  HashMap<String,Object>();
		
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("acc_year", entityMap.get("acc_year"));
		map.put("goal_code", entityMap.get("goal_code"));
		map.put("kpi_code", entityMap.get("super_kpi_code"));
		
		PrmEmpKpi pek = queryPrmEmpKpiByCode(map);
		
		if(pek != null){
			
			entityMap.put("kpi_level", pek.getKpi_level()+1);
		}else{//为空 就是级次就是TOP
			entityMap.put("kpi_level", 1);
		}
		
		try {
			
			int state = prmEmpKpiMapper.addPrmEmpKpi(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosKpi\"}";

		}

	}

	/**
	 * @Description 批量添加0401 职工绩效考核指标表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmEmpKpi(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			List<Map<String,Object>> empKpiList = new ArrayList<Map<String,Object>>();
			
			JSONArray emp_kpi_json = JSONArray.parseArray((String)entityMap.get("data"));
			
			Iterator emp_kpi_it = emp_kpi_json.iterator();
			
			String msg = "";
			
			while(emp_kpi_it.hasNext()){
				
				JSONObject jsonObj = JSONObject.parseObject(emp_kpi_it.next().toString());
				
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
				
				PrmEmpKpi  prmEmpKpi = queryPrmEmpKpiByCode(mapVo);
				
				if(prmEmpKpi != null ){
					
					msg = "{\"error\":\"保存失败! "+mapVo.get("goal_code").toString()+"下已经存在该指标【"+mapVo.get("kpi_code")+"】\"}";
					
					break;
				}
				
				mapVo.put("dept_no", jsonObj.get("dept_no"));
				
				mapVo.put("dept_id", jsonObj.get("dept_id"));
				
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
				
				empKpiList.add(mapVo);
				
			}
			
			if(msg != ""){
				
				return msg ;
			}
			
			prmEmpKpiMapper.addBatchPrmEmpKpi(empKpiList);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException("{\"error\":\"保存失败 \"}");
		}

	}

	/**
	 * @Description 更新0401 职工绩效考核指标表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmEmpKpi(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("acc_year", entityMap.get("acc_year"));
			map.put("goal_code", entityMap.get("goal_code"));
			map.put("kpi_code", entityMap.get("super_kpi_code"));
			
			//查询上级编码的级次
			PrmEmpKpi pek = queryPrmEmpKpiByCode(map);
			
			if(pek != null){//如果上级编码不是顶级TOP
				
				entityMap.put("kpi_level", pek.getKpi_level()+1);
			}else{
				entityMap.put("kpi_level", "1");
			}
			
			int state = prmEmpKpiMapper.updatePrmEmpKpi(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			throw new SysException("{\"error\":\"操作失败 \"}");

			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpKpi\"}";

		}

	}

	/**
	 * @Description 批量更新0401 职工绩效考核指标表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmEmpKpi(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmEmpKpiMapper.updateBatchPrmEmpKpi(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpKpi\"}";

		}

	}

	/**
	 * @Description 删除0401 职工绩效考核指标表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmEmpKpi(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmEmpKpiMapper.deletePrmEmpKpi(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpKpi\"}";

		}

	}

	/**
	 * @Description 批量删除0401 职工绩效考核指标表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmEmpKpi(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			String msg = "";
			
			for(Map<String, Object> map: entityList){
				
				List<PrmEmpKpiObj> list = prmEmpKpiObjMapper.queryPrmEmpKpiObj(map);
				
				if(list.size() > 0 ){
					
					msg = "{\"error\":\"职工考核对象中已引用指标【"+map.get("kpi_code").toString()+"】不能删除 \"}";
					break;
				}
			}
			
			if(!"".equals(msg)){
				
				return msg ; 
			}

			prmEmpKpiMapper.deleteBatchPrmEmpKpi(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			throw new SysException("{\"error\":\"删除失败 \"}");

		}
	}

	/**
	 * @Description 查询结果集0401 职工绩效考核指标表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmEmpKpi(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmEmpKpi> list = prmEmpKpiMapper.queryPrmEmpKpi(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmpKpi> list = prmEmpKpiMapper.queryPrmEmpKpi(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象0401 职工绩效考核指标表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmEmpKpi queryPrmEmpKpiByCode(Map<String, Object> entityMap) throws DataAccessException {

		return prmEmpKpiMapper.queryPrmEmpKpiByCode(entityMap);

	}

	@Override
	public String queryPrmEmpKpiTree(Map<String, Object> entityMap) throws DataAccessException {

		List<PrmEmpKpi> list_emp_kpi = prmEmpKpiMapper.queryPrmEmpKpi(entityMap);

		List<PrmGoal> list_goal = prmGoalMapper.queryPrmGoal(entityMap);

		StringBuilder json = new StringBuilder();

		if (list_emp_kpi.size() == 0 && list_goal.size() == 0) {

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
				
				for (PrmEmpKpi p : list_emp_kpi) {
					
					if(p.getGoal_code().equals(g.getGoal_code())){
						
						String pid = "";
						if(p.getSuper_kpi_code().equals("TOP")){
							pid = p.getGoal_code();
							json.append("{");
							json.append("\"pid\":\""+pid+"\"," + "\"id\":\"" + p.getGoal_code()+","+p.getKpi_code()+ "\"," + "\"text\":" + "\"" + p.getKpi_name() + "\"");
							json.append("},");
						}else{
							pid = p.getGoal_code()+","+p.getSuper_kpi_code();
							json.append("{");
							json.append("\"pid\":\""+pid+"\"," + "\"id\":\"" + p.getGoal_code() +","+ p.getKpi_code()+ "\"," + "\"text\":" + "\"" + p.getKpi_name() + "\"");
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

}
