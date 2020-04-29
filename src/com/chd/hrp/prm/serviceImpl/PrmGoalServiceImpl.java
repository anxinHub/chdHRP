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
import com.chd.hrp.prm.dao.PrmGoalDetailKpiMapper;
import com.chd.hrp.prm.dao.PrmGoalDetailMapper;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmGoalDetail;
import com.chd.hrp.prm.service.PrmGoalService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0101 目标管理表
 * @Table: PRM_GOAL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmGoalService")
public class PrmGoalServiceImpl implements PrmGoalService {

	private static Logger logger = Logger.getLogger(PrmGoalServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
	
	@Resource(name = "prmGoalDetailMapper")
	private final PrmGoalDetailMapper prmGoalDetailMapper = null;
	
	@Resource(name = "prmGoalDetailKpiMapper")
	private final PrmGoalDetailKpiMapper prmGoalDetailKpiMapper = null;

	/**
	 * @Description 添加0101 目标管理表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmGoal(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			// 获取对象0101 目标管理表
			PrmGoal prmGoal = queryPrmGoalByCode(entityMap);

			StringBuffer sb_err = new StringBuffer();
			
			if (prmGoal != null) {

				sb_err.append("目标编码["+prmGoal.getGoal_code()+"]已经存在,请重新添加\n");

			}
			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();//存放子目标明细List
			
			List<Map<String, Object>> detailKpiList = new ArrayList<Map<String, Object>>();//存放指标List
			
			JSONArray goal_detail_json = JSONArray.parseArray((String) entityMap.get("goal_detail_data"));//获取子目标JSON

			Iterator goal_detail_it = goal_detail_json.iterator();
			
			while(goal_detail_it.hasNext()){//遍历子目标JSON
				
				JSONObject jsonObj = JSONObject.parseObject(goal_detail_it.next().toString());
				
				if(jsonObj.get("child_goal_code") != null && !"".equals(jsonObj.get("child_goal_code").toString())){
					
					Map<String,Object> goalDetailMap = new HashMap<String,Object>();
					goalDetailMap.put("group_id", entityMap.get("group_id"));
					goalDetailMap.put("hos_id", entityMap.get("hos_id"));
					goalDetailMap.put("copy_code", entityMap.get("copy_code"));
					goalDetailMap.put("acc_year", entityMap.get("acc_year"));
					goalDetailMap.put("goal_code", entityMap.get("goal_code"));
					goalDetailMap.put("child_goal_code", jsonObj.get("child_goal_code").toString());
					
					PrmGoalDetail pgd = prmGoalDetailMapper.queryPrmGoalDetailByCode(goalDetailMap);
					
					if (pgd != null) {
						sb_err.append("编码["+pgd.getChild_goal_code()+"]下子目标编码["+pgd.getChild_goal_code()+"]已经存在,请重新添加\n");
						break;
					}
					
					if(jsonObj.get("child_goal_name") != null && !"".equals(jsonObj.get("child_goal_name").toString())){
						goalDetailMap.put("child_goal_name", jsonObj.get("child_goal_name").toString());
					}
					
					if(jsonObj.get("child_goal_note") != null){
						goalDetailMap.put("child_goal_note", jsonObj.get("child_goal_note").toString());
					}else{
						goalDetailMap.put("child_goal_note","");
					}
					
					detailList.add(goalDetailMap);
					
				}
			}
			
			JSONArray kpi_target_data_json = JSONArray.parseArray((String)entityMap.get("kpi_target_data"));//获取指标JSON
			
			Iterator goal_detail_kpi_it = kpi_target_data_json.iterator();
			
			while(goal_detail_kpi_it.hasNext()){//遍历指标JSON
				
				JSONObject jsonKpiObj = JSONObject.parseObject(goal_detail_kpi_it.next().toString());
				
				if(jsonKpiObj.get("child_goal_code") != null && !"".equals(jsonKpiObj.get("child_goal_code"))){
					Map<String,Object> goalDetailKpiMap = new HashMap<String,Object>();
					goalDetailKpiMap.put("group_id", entityMap.get("group_id"));
					goalDetailKpiMap.put("hos_id", entityMap.get("hos_id"));
					goalDetailKpiMap.put("copy_code", entityMap.get("copy_code"));
					goalDetailKpiMap.put("acc_year", entityMap.get("acc_year"));
					goalDetailKpiMap.put("goal_code", entityMap.get("goal_code"));
					goalDetailKpiMap.put("child_goal_code", jsonKpiObj.get("child_goal_code"));
					goalDetailKpiMap.put("super_kpi_code", "0");
					goalDetailKpiMap.put("is_last", 0);
					goalDetailKpiMap.put("kpi_code", jsonKpiObj.get("kpi_code").toString());
					
					if(jsonKpiObj.get("kpi_name") != null && !"".equals(jsonKpiObj.get("kpi_name").toString())){
						goalDetailKpiMap.put("kpi_name", jsonKpiObj.get("kpi_name"));
					}
					
					if(jsonKpiObj.get("nature_code") !=null && !"".equals(jsonKpiObj.get("nature_code"))){
						goalDetailKpiMap.put("nature_code", jsonKpiObj.get("nature_code"));
					}else{
						goalDetailKpiMap.put("nature_code", "01");
					}
					
					if(jsonKpiObj.get("goal_value") !=null && !"".equals(jsonKpiObj.get("goal_value"))){
						goalDetailKpiMap.put("goal_value", jsonKpiObj.get("goal_value"));
					}else{
						goalDetailKpiMap.put("goal_value","");
					}
					
					if(jsonKpiObj.get("kpi_act_note") !=null && !"".equals(jsonKpiObj.get("kpi_act_note"))){
						goalDetailKpiMap.put("action_note",jsonKpiObj.get("kpi_act_note"));
					}else{
						goalDetailKpiMap.put("action_note","");
					}
					detailKpiList.add(goalDetailKpiMap);
				}
			}
			
			if(sb_err.toString().length()>0){
				return "{\"error\":\""+sb_err.toString()+"\"}";
			}
			
			prmGoalMapper.addPrmGoal(entityMap);
			
			if(detailList.size() > 0 ){
				prmGoalDetailMapper.addBatchPrmGoalDetail(detailList);
			}

			if(detailKpiList.size() > 0 ){
				prmGoalDetailKpiMapper.addBatchPrmGoalDetailKpi(detailKpiList);
			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	/**
	 * @Description 批量添加0101 目标管理表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmGoal(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmGoalMapper.addBatchPrmGoal(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmGoal\"}";

		}

	}

	/**
	 * @Description 更新0101 目标管理表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmGoal(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			//修改目标管理主表
			prmGoalMapper.updatePrmGoal(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			throw new SysException("{\"error\":\"操作失败 \"}");
		}

	}

	/**
	 * @Description 批量更新0101 目标管理表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmGoal(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmGoalMapper.updateBatchPrmGoal(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmGoal\"}";

		}

	}

	/**
	 * @Description 删除0101 目标管理表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmGoal(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmGoalMapper.deletePrmGoal(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmGoal\"}";

		}

	}

	/**
	 * @Description 批量删除0101 目标管理表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmGoal(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmGoalDetailKpiMapper.deleteBatchPrmGoalDetailKpi(entityList);
			
			prmGoalDetailMapper.deleteBatchPrmGoalDetail(entityList);
			
			prmGoalMapper.deleteBatchPrmGoal(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmGoal\"}";

		}
	}

	/**
	 * @Description 查询结果集0101 目标管理表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmGoal(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmGoal> list = prmGoalMapper.queryPrmGoal(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmGoal> list = prmGoalMapper.queryPrmGoal(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象0101 目标管理表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmGoal queryPrmGoalByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return prmGoalMapper.queryPrmGoalByCode(entityMap);
		
	}

	/**
	 * @Description 
	 * 查询结果集0101 目标管理表<BR>包含单位信息 带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmGoalHos(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmGoal> list = prmGoalMapper.queryPrmGoalHos(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmGoal> list = prmGoalMapper.queryPrmGoalHos(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
		
	}

	@Override
	public Long queryPrmGoalByAudit(Map<String, Object> entityMap) throws DataAccessException {
		return prmGoalMapper.queryPrmGoalByAudit(entityMap);
	}

}
