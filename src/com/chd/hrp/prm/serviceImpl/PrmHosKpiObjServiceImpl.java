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
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.dao.PrmHosKpiMapper;
import com.chd.hrp.prm.dao.PrmHosKpiObjMapper;
import com.chd.hrp.prm.dao.PrmHosSchemeMapper;
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmHosKpiObj;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmHosKpiObjService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0202 医院绩效方案核算对象表
 * @Table: PRM_HOS_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmHosKpiObjService")
public class PrmHosKpiObjServiceImpl implements PrmHosKpiObjService {

	private static Logger logger = Logger.getLogger(PrmHosKpiObjServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmHosKpiObjMapper")
	private final PrmHosKpiObjMapper prmHosKpiObjMapper = null;
	
	@Resource(name = "prmHosSchemeMapper")
	private final PrmHosSchemeMapper prmHosSchemeMapper = null;
	
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;

	/**
	 * @Description 添加0202 医院绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmHosKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0202 医院绩效方案核算对象表
		// PrmHosKpiObj prmHosKpiObj = queryPrmHosKpiObjByCode(entityMap);
		//
		// if (prmHosKpiObj != null) {
		//
		// return "{\"error\":\"数据重复,请重新添加.\"}";
		//
		// }

		try {

			List<Map<String, Object>> list_hos_kpi_batch = (List<Map<String, Object>>) entityMap.get("list_hos_kpi_batch");

			/*for (int i = 0; i < list_hos_kpi_batch.size(); i++) {

				Map<String, Object> map = list_hos_kpi_batch.get(i);

				for (String key : map.keySet()) {

					System.out.println("kpikey = " + key + " kpivalue = " + map.get(key));

				}

			}*/

			List<Map<String, Object>> list_hos_kpi_obj_batch = (List<Map<String, Object>>) entityMap.get("list_hos_kpi_obj_batch");

			/*for (int i = 0; i < list_hos_kpi_obj_batch.size(); i++) {

				Map<String, Object> map = list_hos_kpi_obj_batch.get(i);

				for (String key : map.keySet()) {

					System.out.println("kpiobjkey = " + key + " kpiobjvalue = " + map.get(key));

				}

			}*/

			// prmHosKpiMapper.addBatchPrmHosKpi(list_hos_kpi_batch);

			// prmHosKpiObjMapper.addBatchPrmHosKpiObj(list_hos_kpi_obj_batch);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosKpiObj\"}";

		}

	}

	/**
	 * @Description 批量添加0202 医院绩效方案核算对象表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmHosKpiObj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			if (entityList.size() > 0) {

				prmHosKpiObjMapper.addBatchPrmHosKpiObj(entityList);

				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"添加失败.\",\"\":\"\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosKpiObj\"}";

		}

	}

	/**
	 * @Description 更新0202 医院绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmHosKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmHosKpiObjMapper.updatePrmHosKpiObj(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosKpiObj\"}";

		}

	}

	/**
	 * @Description 批量更新0202 医院绩效方案核算对象表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmHosKpiObj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmHosKpiObjMapper.updateBatchPrmHosKpiObj(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpiObj\"}";

		}

	}

	/**
	 * @Description 删除0202 医院绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmHosKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmHosKpiObjMapper.deletePrmHosKpiObj(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosKpiObj\"}";

		}

	}

	/**
	 * @Description 批量删除0202 医院绩效方案核算对象表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmHosKpiObj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmHosKpiObjMapper.deleteBatchPrmHosKpiObj(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosKpiObj\"}";

		}
	}

	/**
	 * @Description 查询结果集0202 医院绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmHosKpiObj(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmHosKpiObj> list = prmHosKpiObjMapper.queryPrmHosKpiObj(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosKpiObj> list = prmHosKpiObjMapper.queryPrmHosKpiObj(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象0202 医院绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmHosKpiObj queryPrmHosKpiObjByCode(Map<String, Object> entityMap) throws DataAccessException {
		return prmHosKpiObjMapper.queryPrmHosKpiObjByCode(entityMap);
	}

	/**
	 * @Description 查询结果集0202 医院绩效方案核算对象表 left join hos_info <BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmHosKpiObjHos(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = prmHosKpiObjMapper.queryPrmHosKpiObjHos(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			Map<String, Object> mapHosInfo = new HashMap<String, Object>();

			StringBuffer sql = new StringBuffer();
			

			mapHosInfo.put("group_id", entityMap.get("group_id"));

			List<PrmHosKpiObj> prmHosKpiObjList = prmHosKpiObjMapper.queryPrmHosInfo(mapHosInfo);

			for (int i = 0; i < prmHosKpiObjList.size(); i++) {

				PrmHosKpiObj prmHosKpiObj = (PrmHosKpiObj) prmHosKpiObjList.get(i);

				sql = sql.append("sum(NVL((case when phko.check_hos_id = " + prmHosKpiObj.getHos_id() + "  then phko.check_hos_id end),'')) as check_hos_id" + prmHosKpiObj.getHos_id()  + ",");
				
			}

			entityMap.put("sql", sql.toString());
			
			List<Map<String,Object>> list = prmHosKpiObjMapper.queryPrmHosKpiObjHos(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	/**
	 * @Description 查询 医院信息 add by alfred 返回JSP页面医院coloum名称JSON
	 */
	@Override
	public String queryPrmHosInfo(Map<String, Object> entityMap) throws DataAccessException {

		List<PrmHosKpiObj> list_hosIfno = prmHosKpiObjMapper.queryPrmHosInfo(entityMap);

		Map<String, Map<String, Object>> mapVo = new HashMap<String, Map<String, Object>>();

		StringBuffer sb_columns_hosIfno = new StringBuffer();

		Integer size = list_hosIfno.size();

		sb_columns_hosIfno.append("[");

		sb_columns_hosIfno.append("{ display: \'指标编码\', name: \'kpi_code\', align: \'left\',width:180},");

		sb_columns_hosIfno.append("{ display: \'指标名称\', name: \'kpi_name\', align: \'left\',width:360"+
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

		sb_columns_hosIfno.append("{ display: \'指标性质\', name: \'nature_name\', align: \'left\',width:180},");

		for (int i = 0; i < size; i++) {

			PrmHosKpiObj phko = list_hosIfno.get(i);
			sb_columns_hosIfno.append(
				"{display : \'<div class=\"total\" gridid=\"maingrid\" style=\"display: inline-block;"+
				"width: 13px;margin: 0;vertical-align: middle; \" columnname=\"check_hos_id"+phko.getHos_id()+"\"></div>&nbsp;&nbsp;" 
				+ phko.getHos_simple() + "\',name : \'check_hos_id" + phko.getHos_id()  + "\',id:\'"+phko.getHos_id() + ";" + phko.getHos_code() +"\',align : \'left\',width:\'120\',render : checkboxRender},"
			);
		}

		sb_columns_hosIfno.setCharAt(sb_columns_hosIfno.length() - 1, ']');

		return sb_columns_hosIfno.toString();
	}

	/**
	 * @Description 查询 医院信息 add by alfred 返回controller 所有医院的信息 为保存是使用
	 */
	@Override
	public List<PrmHosKpiObj> queryPrmHosInfoForAdd(Map<String, Object> entityMap) throws DataAccessException {

		List<PrmHosKpiObj> list_hosIfno = prmHosKpiObjMapper.queryPrmHosInfo(entityMap);

		return list_hosIfno;
	}
	
	
	/**
	 * @Description 保存对象0202 医院绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String savePrmHosKpiObj(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			Map<String,Object> groupMap = new HashMap<String,Object>();
			groupMap.put("group_id", SessionManager.getGroupId());
			
			List<PrmHosKpiObj> list_hos = prmHosKpiObjMapper.queryPrmHosInfo(groupMap);// 查询一共存在多少家下级医院
			
			Map<String,Object> goalMap = hosKpiObjValue(entityMap);
			PrmGoal prmGoal = prmGoalMapper.queryPrmGoalByCode(goalMap);//查询战略目标
			if(prmGoal.getIs_audit() == 1){
				return "{\"error\":\"该战略目标已经审核不能继续操作  \"}";
			}
			
			List<Map<String, Object>> listHosKpiObjBatch = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list_hos_kpi_obj_batch = new ArrayList<Map<String, Object>>();

			JSONArray hos_kpi_json = JSONArray.parseArray((String) entityMap.get("hos_kpi_data"));//获取JSON对象数组
			Iterator hos_kpi_it = hos_kpi_json.iterator();
			
			boolean flag = true;//用于跳出循环
			String msg = "";//提示信息
			
			if("1".equals(entityMap.get("edit_id"))){
				//--------------------------------------------单条模式   begin--------------------------------------
				while (hos_kpi_it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(hos_kpi_it.next().toString());//获取JSON对象
					
					if(jsonObj.get("kpi_name") == null){
						continue;
					}
					
					String check_hos_id  = String.valueOf(jsonObj.get(entityMap.get("hos_colunm")));//获取考核对象
						
					Map<String, Object> map = new HashMap<String, Object>();
						
					map.put("group_id",entityMap.get("group_id"));
					if(entityMap.get("hos_id") == null){
						map.put("hos_id", SessionManager.getHosId());
					}
					map.put("hos_id",entityMap.get("hos_id"));
					map.put("copy_code",entityMap.get("copy_code"));
					map.put("acc_year",entityMap.get("acc_year"));
					map.put("goal_code", entityMap.get("goal_code"));
					map.put("kpi_code", (String) jsonObj.get("kpi_code"));
					map.put("check_hos_id", entityMap.get("colunm_hos_id"));
						
					List<PrmHosScheme> phs_list = prmHosSchemeMapper.queryPrmHosScheme(map);//查询医院绩效方案
					String super_kpi_code = (String) jsonObj.get("super_kpi_code");
					map.put("super_kpi_code", super_kpi_code);
					map.put("kpi_level", jsonObj.get("kpi_level"));
					map.put("is_last", jsonObj.get("is_last"));
					map.put("order_no", "0");
						
					if (phs_list.size() == 0) {//未制定医院绩效方案
						list_hos_kpi_obj_batch.add(map);
						if (check_hos_id!= null && !"null".equals(check_hos_id) && !"".equals(check_hos_id)) {//已选
								
							listHosKpiObjBatch.add(map);
						}
					}else{
						String months = "";
						
						for(int x= 0; x< phs_list.size() ; x++){
							
							PrmHosScheme phs = phs_list.get(x);
							months = phs.getAcc_month() +",";
						}
						
						Map<String,Object> hosMap = new HashMap<String,Object>();
						hosMap.put("group_id", map.get("group_id"));
						hosMap.put("hos_id", map.get("check_hos_id"));
						hosMap.put("is_stop", map.get("0"));
						
						PrmHosKpiObj prmHosKpiObj = prmHosKpiObjMapper.queryPrmHosInfoByCode(hosMap);
						
						months = months.substring(0, months.length()-1);
						
						msg = "{\"error\":\"该医院【"+prmHosKpiObj.getHos_simple()+"】已在【"+months+"】月绩效方案中配置不能更改  \"}";
					}
				}
				//--------------------------------------------单条模式   end-------------------------------------------
			}else{
				//--------------------------------------------批量模式   begin-----------------------------------------
				while (hos_kpi_it.hasNext()) {
					
					if(!flag){
						break;
					}
					
					JSONObject jsonObj = JSONObject.parseObject(hos_kpi_it.next().toString());//获取JSON对象
					
					if(jsonObj.get("kpi_name") == null){
						continue;
					}
					
					for (PrmHosKpiObj list_hos2 : list_hos) {
						String check_hos_id  = String.valueOf(jsonObj.get("check_hos_id" + list_hos2.getHos_id()));//获取考核对象
						
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("group_id",entityMap.get("group_id"));
						if(entityMap.get("hos_id") == null){
							map.put("hos_id", SessionManager.getHosId());
						}
						map.put("hos_id",entityMap.get("hos_id"));
						map.put("copy_code",entityMap.get("copy_code"));
						map.put("acc_year",entityMap.get("acc_year"));
						map.put("goal_code", entityMap.get("goal_code"));
						map.put("kpi_code", (String) jsonObj.get("kpi_code"));
						map.put("check_hos_id", list_hos2.getHos_id());
						
						List<PrmHosScheme> phs_list = prmHosSchemeMapper.queryPrmHosScheme(map);//查询医院绩效方案
						String super_kpi_code = (String) jsonObj.get("super_kpi_code");
						map.put("super_kpi_code", super_kpi_code);
						map.put("kpi_level", jsonObj.get("kpi_level"));
						map.put("is_last", jsonObj.get("is_last"));
						map.put("order_no", "0");
						
						if (phs_list.size() == 0) {//未制定医院绩效方案
							list_hos_kpi_obj_batch.add(map);
							if (check_hos_id!= null && !"null".equals(check_hos_id) && !"".equals(check_hos_id)) {//已选
								
								listHosKpiObjBatch.add(map);
							}
						}else{
							PrmHosScheme scheme = phs_list.get(0);
							msg = "{\"error\":\"该科室【"+list_hos2.getHos_simple()+"】已在【"+scheme.getAcc_month()+"】月绩效方案中配置不能更改  \"}";
							flag = false;//用于跳出外层循环
							break;//跳出循环
						}
					}
				}
				//--------------------------------------------批量模式  end-----------------------------------------
			}
			
			if(!"".equals(msg)){
				return msg; 
			}
			
			if (list_hos_kpi_obj_batch.size() > 0) {
				//删除未制定医院绩效方案的考核对象
				prmHosKpiObjMapper.deleteBatchPrmHosKpiObj(list_hos_kpi_obj_batch);
			}
			
			if(listHosKpiObjBatch.size() > 0 ){
				//保存选中的考核对象
				prmHosKpiObjMapper.addBatchPrmHosKpiObj(listHosKpiObjBatch);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败 \"}");
		}
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> hosKpiObjValue(Map<String, Object> mapVo) {

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
