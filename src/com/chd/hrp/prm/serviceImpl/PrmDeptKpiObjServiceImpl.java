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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmDeptDictMapper;
import com.chd.hrp.prm.dao.PrmDeptKpiMapper;
import com.chd.hrp.prm.dao.PrmDeptKpiObjMapper;
import com.chd.hrp.prm.dao.PrmDeptSchemeMapper;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmDeptKpiObj;
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.entity.PrmEmpKpiObj;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmHosKpiObj;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmDeptKpiObjService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0302 科室绩效方案核算对象表
 * @Table: PRM_DEPT_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptKpiObjService")
public class PrmDeptKpiObjServiceImpl implements PrmDeptKpiObjService {

	private static Logger logger = Logger.getLogger(PrmDeptKpiObjServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptKpiObjMapper")
	private final PrmDeptKpiObjMapper prmDeptKpiObjMapper = null;
	
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
	
	@Resource(name = "prmDeptSchemeMapper")
	private final PrmDeptSchemeMapper prmDeptSchemeMapper = null;
	
	@Resource(name = "prmDeptDictMapper")
	private final PrmDeptDictMapper prmDeptDictMapper = null;

	/**
	 * @Description 添加0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptKpiObj(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			List<PrmDeptKpiObj> list_Dept = queryPrmDeptForAdd(entityMap);//查询科室
			Map<String,Object> goalMap = deptKpiObjValue(entityMap);//查询战略目标条件map
			PrmGoal prmGoal = prmGoalMapper.queryPrmGoalByCode(goalMap);
			if(prmGoal.getIs_audit() == 1){//判断战略目标是否审核
				return "{\"error\":\"该战略目标已经审核不能继续操作  \"}";
			}
			
			List<Map<String, Object>> dept_kpi_obj_batch = new ArrayList<Map<String, Object>>();//存储未选中的指标
			List<Map<String, Object>> list_dept_kpi_obj_batch = new ArrayList<Map<String, Object>>();//存储选中的考核对象
			
			JSONArray dept_kpi_data = JSONArray.parseArray((String) entityMap.get("dept_kpi_data"));//获取JSON对象数组
			Iterator dept_kpi_it = dept_kpi_data.iterator();
			boolean flag = true;
			String msg = "";
			
			if("1".equals(entityMap.get("edit_id"))){
				
				//-------------------------------------单条模式    begin--------------------------------------
				while (dept_kpi_it.hasNext()) {
					
					JSONObject jsonObj = JSONObject.parseObject(dept_kpi_it.next().toString());
					
					if(jsonObj.get("kpi_name") == null){
						continue;
					}
					
					String check_dept  = String.valueOf(jsonObj.get(entityMap.get("dept_colunm")));//获取考核对象设定值
					Map<String, Object> map = new HashMap<String, Object>();
						
					map.put("group_id",entityMap.get("group_id"));
					map.put("hos_id",entityMap.get("hos_id"));
					map.put("copy_code",entityMap.get("copy_code"));
					map.put("acc_year", entityMap.get("acc_year"));
					map.put("goal_code", entityMap.get("goal_code"));
					map.put("kpi_code", (String) jsonObj.get("kpi_code"));
					map.put("dept_id", entityMap.get("colunm_dept_id"));
					map.put("dept_no", entityMap.get("colunm_dept_no"));
						
					List<PrmDeptScheme> pds_list = prmDeptSchemeMapper.queryPrmDeptScheme(map);//查询科室绩效方案
					map.put("super_kpi_code", (String) jsonObj.get("super_kpi_code"));
					map.put("kpi_level", jsonObj.get("kpi_level"));
					map.put("is_last", jsonObj.get("is_last"));
					map.put("order_no", "0");
						
					if(pds_list.size() == 0){//未制定方案
						dept_kpi_obj_batch.add(map);
						
						if (check_dept!= null && !"null".equals(check_dept) && !"".equals(check_dept)) {//已选
							list_dept_kpi_obj_batch.add(map);
						}
					}else{
						String months = "";
						
						for(int x = 0; x < pds_list.size(); x++ ){
							PrmDeptScheme scheme = pds_list.get(0);
							months = scheme.getAcc_month() +",";
						}
						
						months = months.substring(0, months.length()-1);
						PrmDeptDict pdd = prmDeptDictMapper.queryPrmDeptDictByCode(map);
							
						msg = "{\"error\":\"该科室【"+pdd.getDept_name()+"】已在【"+months+"】月绩效方案中配置不能更改  \"}";
						
					}
				}
				//-------------------------------------单条模式    end--------------------------------------
			}else{
				//-------------------------------------批量模式    begin--------------------------------------
				while (dept_kpi_it.hasNext()) {
					
					if(!flag){
						break;
					}
					
					JSONObject jsonObj = JSONObject.parseObject(dept_kpi_it.next().toString());
					
					if(jsonObj.get("kpi_name") == null){
						continue;
					}
					
					
					for (int x = 0 ; x < list_Dept.size() ; x++) {
						
						PrmDeptKpiObj pdko = list_Dept.get(x);//获取科室对象
						
						String check_dept  = String.valueOf(jsonObj.get("dept_no" + x));//获取考核对象设定值
						
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("group_id",entityMap.get("group_id"));
						map.put("hos_id",entityMap.get("hos_id"));
						map.put("copy_code",entityMap.get("copy_code"));
						map.put("acc_year", entityMap.get("acc_year"));
						map.put("goal_code", entityMap.get("goal_code"));
						map.put("kpi_code", (String) jsonObj.get("kpi_code"));
						map.put("dept_id", pdko.getDept_id());
						map.put("dept_no", pdko.getDept_no());
						
						List<PrmDeptScheme> pds_list = prmDeptSchemeMapper.queryPrmDeptScheme(map);//查询科室绩效方案
						map.put("super_kpi_code", (String) jsonObj.get("super_kpi_code"));
						map.put("kpi_level", jsonObj.get("kpi_level"));
						map.put("is_last", jsonObj.get("is_last"));
						map.put("order_no", "0");
						
						if(pds_list.size() == 0){//未制定方案
							dept_kpi_obj_batch.add(map);
							
							if (check_dept!= null && !"null".equals(check_dept) && !"".equals(check_dept)) {//已选
								
								list_dept_kpi_obj_batch.add(map);
							}
						}else{
							PrmDeptScheme scheme = pds_list.get(0);
							
							msg = "{\"error\":\"该科室【"+pdko.getDept_name()+"】已在【"+scheme.getAcc_month()+"】月绩效方案中配置不能更改  \"}";
							flag = false;
							break;
						}
					}
				}
				//-------------------------------------批量模式    end--------------------------------------
			}
			
			if(!"".equals(msg)){
				return msg; 
			}
			
			if (dept_kpi_obj_batch.size() > 0) {//删除未制定方案考核对象
				prmDeptKpiObjMapper.deleteBatchPrmDeptKpiObj(dept_kpi_obj_batch);
			}
			
			if(list_dept_kpi_obj_batch.size() > 0){//保存考核对象
				prmDeptKpiObjMapper.addBatchPrmDeptKpiObj(list_dept_kpi_obj_batch);
			}
			if("1".equals(entityMap.get("edit_id"))){//如果单条模式不提示成功
				return "{\"msg1\":\"保存成功.\",\"state\":\"true\"}";
			}else {
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			}
			
			
		} catch (Exception e) {
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	/**
	 * @Description 批量添加0302 科室绩效方案核算对象表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptKpiObj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			if (entityList.size() > 0) {

				prmDeptKpiObjMapper.addBatchPrmDeptKpiObj(entityList);

				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			} else {

				return "{\"msg\":\"添加失败.\",\"\":\"\"}";

			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptKpiObj\"}";

		}

	}

	/**
	 * @Description 更新0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptKpiObjMapper.updatePrmDeptKpiObj(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptKpiObj\"}";

		}

	}

	/**
	 * @Description 批量更新0302 科室绩效方案核算对象表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptKpiObj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptKpiObjMapper.updateBatchPrmDeptKpiObj(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKpiObj\"}";

		}

	}

	/**
	 * @Description 删除0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmDeptKpiObjMapper.deletePrmDeptKpiObj(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptKpiObj\"}";

		}

	}

	/**
	 * @Description 批量删除0302 科室绩效方案核算对象表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptKpiObj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmDeptKpiObjMapper.deleteBatchPrmDeptKpiObj(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptKpiObj\"}";

		}
	}

	/**
	 * @Description 查询结果集0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmDeptKpiObj> list = prmDeptKpiObjMapper.queryPrmDeptKpiObj(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptKpiObj> list = prmDeptKpiObjMapper.queryPrmDeptKpiObj(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmDeptKpiObj queryPrmDeptKpiObjByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmDeptKpiObjMapper.queryPrmDeptKpiObjByCode(entityMap);
	}

	/**
	 * @Description 查询结果集0202 科室绩效 left join Dept <BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptKpiObjDept(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = prmDeptKpiObjMapper.queryPrmDeptKpiObjDept(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			Map<String, Object> MapHosInfo = new HashMap<String, Object>();

			StringBuffer sql = new StringBuffer();

			MapHosInfo.put("group_id", entityMap.get("group_id"));
			
			MapHosInfo.put("hos_id", entityMap.get("hos_id"));
			
			MapHosInfo.put("copy_code", entityMap.get("copy_code"));
			
			MapHosInfo.put("dept_kind_code", entityMap.get("dept_kind_code"));
			
			entityMap.put("is_stop", "0");
			
			List<PrmDeptKpiObj> prmDeptKpiObjList = prmDeptKpiObjMapper.queryPrmDept(entityMap);

			for (int i = 0; i < prmDeptKpiObjList.size(); i++) {

				PrmDeptKpiObj prmDeptKpiObj = (PrmDeptKpiObj) prmDeptKpiObjList.get(i);

				sql = sql.append("sum(NVL((case when pdko.dept_no = " + prmDeptKpiObj.getDept_no() + " then pdko.dept_no end),'')) as dept_no" + i + "," + "sum(NVL((case when pdko.dept_id = " + prmDeptKpiObj.getDept_id() + " then pdko.dept_id end),'')) as dept_id" + i + ",");
			}

			entityMap.put("sql", sql.toString());

			List<Map<String,Object>> list = prmDeptKpiObjMapper.queryPrmDeptKpiObjDept(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	/**
	 * @Description 查询 科室绩效 add by alfred 返回JSP页面医院coloum名称JSON
	 */
	@Override
	public String queryPrmDept(Map<String, Object> entityMap) throws DataAccessException {

		List<PrmDeptKpiObj> list_dept = prmDeptKpiObjMapper.queryPrmDept(entityMap);

		StringBuffer sb_columns_dept = new StringBuffer();

		Integer size = list_dept.size();

		sb_columns_dept.append("[");

		sb_columns_dept.append("{ display: \'指标编码\', name: \'kpi_code\', align: \'left\',width:180},");

		sb_columns_dept.append("{ display: \'指标名称\', name: \'kpi_name\', align: \'left\',width:360"+
				",render:function(rowdata,rowindex,value){" +
				"	var nbspNum = rowdata.kpi_level * 1;" +
				"	var nbspStr = '';" +
				"	for(var i = 1 ; i < nbspNum; i ++){" +
				"		nbspStr += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'; " +
				"	}" +
				"	var str = nbspStr+rowdata.kpi_name;" +
				"	if(str == null || str == 'undefined'){" +
				"		str = '';" +
				"	}" +
				"	return str;" +
				"}"
			+"},");

		sb_columns_dept.append("{ display: \'指标性质\', name: \'nature_name\', align: \'left\',width:180},");

		for (int i = 0; i < size; i++) {

			PrmDeptKpiObj pdko = list_dept.get(i);

			sb_columns_dept.append("{display : \'<div class=\"total\" gridid=\"maingrid\" style=\"display: inline-block;"+
					"width: 13px;margin: 0;vertical-align: middle; \" columnname=\"dept_no"+ i +"\"></div>&nbsp;&nbsp;" 
			+ pdko.getDept_name() + "\',name : \'dept_no" + i + "\',id:\'" + pdko.getDept_id() + ";" + pdko.getDept_no() + "\',align : \'left\',width:\'120\',render : checkboxRender},");

		}

		sb_columns_dept.setCharAt(sb_columns_dept.length() - 1, ']');

		return sb_columns_dept.toString();
	}

	/**
	 * @Description 查询 科室绩效add by alfred 返回controller 所有科室的信息 为保存是使用
	 */
	@Override
	public List<PrmDeptKpiObj> queryPrmDeptForAdd(Map<String, Object> entityMap) throws DataAccessException {

		List<PrmDeptKpiObj> list_dept = prmDeptKpiObjMapper.queryPrmDept(entityMap);

		return list_dept;
	}
	
	
	// 返回用用于保存的默认值
		public Map<String, Object> deptKpiObjValue(Map<String, Object> mapVo) {

			Map<String, Object> map = new HashMap<String, Object>();

			if (map.get("group_id") == null) {
				map.put("group_id", SessionManager.getGroupId());
			}

			if (map.get("hos_id") == null) {
				map.put("hos_id", SessionManager.getHosId());
			}

			if (map.get("copy_code") == null) {
				map.put("copy_code", SessionManager.getCopyCode());
			}

			if (map.get("acc_year") == null) {
				map.put("acc_year", SessionManager.getAcctYear());
			}

			map.put("goal_code", mapVo.get("goal_code"));
			map.put("super_kpi_code", mapVo.get("super_kpi_code"));
			map.put("order_no", 0);
			map.put("kpi_level", 0);
			map.put("is_last", 1);

			return map;
		}
}
