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
import com.chd.hrp.prm.dao.PrmEmpKpiMapper;
import com.chd.hrp.prm.dao.PrmEmpKpiObjMapper;
import com.chd.hrp.prm.dao.PrmEmpSchemeMapper;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.entity.PrmDeptKpiObj;
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.entity.PrmEmpKpiObj;
import com.chd.hrp.prm.entity.PrmEmpScheme;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmHosKpiObj;
import com.chd.hrp.prm.service.PrmEmpKpiObjService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0402 职工绩效方案核算对象表
 * @Table: PRM_EMP_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmEmpKpiObjService")
public class PrmEmpKpiObjServiceImpl implements PrmEmpKpiObjService {

	private static Logger logger = Logger.getLogger(PrmEmpKpiObjServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmEmpKpiObjMapper")
	private final PrmEmpKpiObjMapper prmEmpKpiObjMapper = null;
	
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
	
	@Resource(name = "prmEmpSchemeMapper")
	private final PrmEmpSchemeMapper prmEmpSchemeMapper = null;

	/**
	 * @Description 添加0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmEmpKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			//List<PrmEmpKpiObj> list_Emp = queryPrmEmpForAdd(entityMap);
			
			Map<String,Object> goalMap = empKpiObjValue(entityMap);//查询战略目标条件map
			PrmGoal prmGoal = prmGoalMapper.queryPrmGoalByCode(goalMap);//查询战略目标
			if(prmGoal.getIs_audit() == 1){//判断战略目标是否审核
				return "{\"error\":\"该战略目标已经审核不能继续操作  \"}";
			}
			
			List<Map<String, Object>> emp_kpi_obj_batch = new ArrayList<Map<String, Object>>();//存储未选中的指标
			List<Map<String, Object>> list_emp_kpi_obj_batch = new ArrayList<Map<String, Object>>();//存储选中的考核对象
			
			JSONArray emp_kpi_data = JSONArray.parseArray((String) entityMap.get("emp_kpi_data"));//获取JSON对象数组
			Iterator emp_kpi_it = emp_kpi_data.iterator();
			
			boolean flag = true;//跳出循环标记
			String msg = "";//提示信息
			
			if("1".equals(entityMap.get("edit_id"))){//判断编辑模式
				//---------------------------------单条模式 begin---------------------------------
				while (emp_kpi_it.hasNext()) {
					
					JSONObject jsonObj = JSONObject.parseObject(emp_kpi_it.next().toString());
					
					if(jsonObj.get("kpi_name") == null){
						continue;
					}
					
					String check_emp  = String.valueOf(jsonObj.get(entityMap.get("emp_colunm")));//获取考核对象设定值
						
					Map<String, Object> map = new HashMap<String, Object>();
					//map = empKpiObjValue(entityMap);
					map.put("group_id",entityMap.get("group_id"));
					map.put("hos_id",entityMap.get("hos_id"));
					map.put("copy_code",entityMap.get("copy_code"));
					map.put("acc_year",entityMap.get("acc_year"));
					map.put("goal_code", entityMap.get("goal_code"));
					map.put("kpi_code", jsonObj.get("kpi_code"));
					map.put("emp_id", entityMap.get("colunm_emp_id"));
					map.put("emp_no", entityMap.get("colunm_emp_no"));
						
					List<PrmEmpScheme> pes_list = prmEmpSchemeMapper.queryPrmEmpScheme(map);//查询职工绩效方案
						
					map.put("super_kpi_code", (String) jsonObj.get("super_kpi_code"));
					map.put("kpi_level", jsonObj.get("kpi_level"));
					map.put("is_last", jsonObj.get("is_last"));
					map.put("order_no","0");
						
					if(pes_list.size() == 0){//未制定方案
						emp_kpi_obj_batch.add(map);
							
						if (check_emp!= null && !"null".equals(check_emp) && !"".equals(check_emp)) {//已选
							list_emp_kpi_obj_batch.add(map);
						}
					}else{//已制定方案
						
						String months = "";
						
						for(int x = 0 ; x < pes_list.size() ; x++){
							PrmEmpScheme pes = pes_list.get(x);
							months = pes.getAcc_month() + ",";
						}
						
						PrmEmpKpiObj prmEmpKpiObj = prmEmpKpiObjMapper.queryPrmEmpByCode(map);
						
						months = months.substring(0, months.length()-1);
						
						msg = "{\"error\":\"该职工【"+prmEmpKpiObj.getEmp_name()+"】已在【"+months+"】月绩效方案中配置不能更改  \"}";
						
					}
				}
				//------------------------------------------单条模式 end----------------------------------------
			}else{
				//------------------------------------------批量模式  begin----------------------------------------
				
				String[] list_emp = entityMap.get("emp_head").toString().split(",");//获取字符串,职工表头和职工id、变更号
				
				while (emp_kpi_it.hasNext()) {
					
					if(!flag){
						break;
					}
					
					JSONObject jsonObj = JSONObject.parseObject(emp_kpi_it.next().toString());
					
					if(jsonObj.get("kpi_name") == null){
						continue;
					}
					
					for (String emp_head : list_emp) {//获取职工表头,几个表头就是几个职工,遍历页面所有职工信息
						
						String[] emp_msg = emp_head.split(";");//emp_msg[0] 职工表头, emp_msg[1] 职工id , emp_msg[2] 职工变更号
						
						String check_emp  = String.valueOf(jsonObj.get(emp_msg[0]));//获取考核对象设定值
						
						Map<String, Object> map = new HashMap<String, Object>();
						//map = empKpiObjValue(entityMap);
						map.put("group_id",entityMap.get("group_id"));
						map.put("hos_id",entityMap.get("hos_id"));
						map.put("copy_code",entityMap.get("copy_code"));
						map.put("acc_year",entityMap.get("acc_year"));
						map.put("goal_code", entityMap.get("goal_code"));
						map.put("kpi_code", jsonObj.get("kpi_code"));
						map.put("emp_id", emp_msg[1]);
						map.put("emp_no", emp_msg[2]);
						
						List<PrmEmpScheme> pes_list = prmEmpSchemeMapper.queryPrmEmpScheme(map);//查询职工绩效方案
						
						map.put("super_kpi_code", (String) jsonObj.get("super_kpi_code"));
						map.put("kpi_level", jsonObj.get("kpi_level"));
						map.put("is_last", jsonObj.get("is_last"));
						map.put("order_no","0");
						
						if(pes_list.size() == 0){//未制定方案
							emp_kpi_obj_batch.add(map);
							
							if (check_emp!= null && !"null".equals(check_emp) && !"".equals(check_emp)) {//已选
								list_emp_kpi_obj_batch.add(map);
							}
						}else{
							flag = false;
							String months = "";
							
							for(int x = 0 ; x < pes_list.size() ; x++){
								PrmEmpScheme pes = pes_list.get(x);
								months = pes.getAcc_month() + ",";
							}
							
							map.put("is_stop", 0);
							List<PrmEmpKpiObj> pek_list = prmEmpKpiObjMapper.queryPrmEmp(map);
							PrmEmpKpiObj pek = pek_list.get(0);
							months = months.substring(0, months.length()-1);
							
							msg = "{\"error\":\"该职工【"+pek.getEmp_name()+"】已在【"+months+"】月绩效方案中配置不能更改  \"}";
							break;
						}
					}
				}
				//------------------------------------------批量模式 end----------------------------------------
			}
			
			if(!"".equals(msg)){
				return msg; 
			}
			
			if (emp_kpi_obj_batch.size() > 0) {//删除未制定方案的考核对象
				prmEmpKpiObjMapper.deleteBatchPrmEmpKpiObj(emp_kpi_obj_batch);
			}

			if(list_emp_kpi_obj_batch.size() > 0){//保存已选中考核对象
				prmEmpKpiObjMapper.addBatchPrmEmpKpiObj(list_emp_kpi_obj_batch);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	/**
	 * @Description 批量添加0402 职工绩效方案核算对象表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmEmpKpiObj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			if (entityList.size() > 0) {

				prmEmpKpiObjMapper.addBatchPrmEmpKpiObj(entityList);

				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			} else {

				return "{\"msg\":\"添加失败.\",\"\":\"\"}";
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmEmpKpiObj\"}";

		}

	}

	/**
	 * @Description 更新0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmEmpKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmEmpKpiObjMapper.updatePrmEmpKpiObj(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpKpiObj\"}";

		}

	}

	/**
	 * @Description 批量更新0402 职工绩效方案核算对象表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmEmpKpiObj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmEmpKpiObjMapper.updateBatchPrmEmpKpiObj(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpKpiObj\"}";

		}

	}

	/**
	 * @Description 删除0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmEmpKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmEmpKpiObjMapper.deletePrmEmpKpiObj(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpKpiObj\"}";

		}

	}

	/**
	 * @Description 批量删除0402 职工绩效方案核算对象表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmEmpKpiObj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmEmpKpiObjMapper.deleteBatchPrmEmpKpiObj(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmEmpKpiObj\"}";

		}
	}

	/**
	 * @Description 获取对象0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmEmpKpiObj queryPrmEmpKpiObjByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmEmpKpiObjMapper.queryPrmEmpKpiObjByCode(entityMap);
	}

	/**
	 * @Description 查询结果集0202 职工绩效 left join Emp <BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmEmpKpiObj(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = prmEmpKpiObjMapper.queryPrmEmpKpiObjEmp(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			Map<String, Object> MapEmpInfo = new HashMap<String, Object>();

			StringBuffer sql = new StringBuffer();

			MapEmpInfo.put("group_id", entityMap.get("group_id"));
			
			MapEmpInfo.put("hos_id", entityMap.get("hos_id"));
			
			MapEmpInfo.put("copy_code", entityMap.get("copy_code"));
			
			MapEmpInfo.put("emp_id", entityMap.get("emp_id"));
			
			MapEmpInfo.put("emp_no", entityMap.get("emp_no"));
			
			entityMap.put("is_stop", "0");
			
			List<PrmEmpKpiObj> prmEmpKpiObjlist = prmEmpKpiObjMapper.queryPrmEmp(entityMap);

			for (int i = 0; i < prmEmpKpiObjlist.size(); i++) {

				PrmEmpKpiObj prmEmpKpiObj = (PrmEmpKpiObj) prmEmpKpiObjlist.get(i);

				sql = sql.append("sum(NVL((case when peko.emp_no = " + prmEmpKpiObj.getEmp_no() + " then peko.emp_no end),'')) as emp_no" + i + "," + "sum(NVL((case when peko.emp_id = " + prmEmpKpiObj.getEmp_id() + " then peko.emp_id end),'')) as emp_id" + i + ",");
			}

			entityMap.put("sql", sql.toString());

			List<Map<String,Object>> list = prmEmpKpiObjMapper.queryPrmEmpKpiObjEmp(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	/**
	 * @Description 查询 职工绩效 add by alfred 返回JSP页面医院coloum名称JSON
	 */
	@Override
	public String queryPrmEmp(Map<String, Object> entityMap) throws DataAccessException {

		List<PrmEmpKpiObj> list_emp = prmEmpKpiObjMapper.queryPrmEmp(entityMap);

		StringBuffer sb_columns_emp = new StringBuffer();

		Integer size = list_emp.size();

		sb_columns_emp.append("[");

		sb_columns_emp.append("{ display: \'指标编码\', name: \'kpi_code\', align: \'left\',width:180},");

		sb_columns_emp.append("{ display: \'指标名称\', name: \'kpi_name\', align: \'left\',width:360"+
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

		sb_columns_emp.append("{ display: \'指标性质\', name: \'nature_name\', align: \'left\',width:180},");

		for (int i = 0; i < size; i++) {
			
			PrmEmpKpiObj peko = list_emp.get(i);
			
			sb_columns_emp.append("{display : \'<div class=\"total\" gridid=\"maingrid\" style=\"display: inline-block;"+
					"width: 13px;margin: 0;vertical-align: middle; \" columnname=\"emp_id"+ i +"\"></div>&nbsp;&nbsp;" 
			+ peko.getEmp_name() + "\',name : \'emp_id" + i + "\',id:\'"+peko.getEmp_id() + ";" + peko.getEmp_no()+"\',align : \'left\',width:\'120\',render : checkboxRender},");

		}

		sb_columns_emp.setCharAt(sb_columns_emp.length() - 1, ']');

		return sb_columns_emp.toString();
	}

	/**
	 * @Description 查询 职工绩效 add by alfred 返回controller 所有职工的信息 为保存是使用
	 */
	@Override
	public List<PrmEmpKpiObj> queryPrmEmpForAdd(Map<String, Object> entityMap) throws DataAccessException {

		List<PrmEmpKpiObj> list_emp = prmEmpKpiObjMapper.queryPrmEmp(entityMap);

		return list_emp;
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> empKpiObjValue(Map<String, Object> mapVo) {
							
		Map<String,Object> map = new HashMap<String, Object>();
							
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
		map.put("order_no",  0);
		map.put("kpi_level",  0);
		map.put("is_last",  1);
					
		return map;
	}
}
