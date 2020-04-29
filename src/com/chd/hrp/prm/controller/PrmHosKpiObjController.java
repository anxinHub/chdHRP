/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.interceptor.Session;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.prm.entity.PrmHosKpi;
import com.chd.hrp.prm.entity.PrmHosKpiObj;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmHosKpiObjService;
import com.chd.hrp.prm.service.PrmHosKpiService;
import com.chd.hrp.prm.service.PrmHosSchemeService;

/**
 * 
 * @Description: 0202 医院绩效方案核算对象表
 * @Table: PRM_HOS_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmHosKpiObjController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmHosKpiObjController.class);

	// 引入Service服务
	@Resource(name = "prmHosKpiObjService")
	private final PrmHosKpiObjService prmHosKpiObjService = null;

	// 引入Service服务
	@Resource(name = "prmHosKpiService")
	private final PrmHosKpiService prmHosKpiService = null;

	// 引入Service服务
	@Resource(name = "prmHosSchemeService")
	private final PrmHosSchemeService prmHosSchemeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/prmHosKpiObjMainPage", method = RequestMethod.GET)
	public String prmHosKpiObjMainPage(Model mode) throws Exception {

		return "hrp/prm/prmhoskpiobj/prmHosKpiObjMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/prmHosKpiObjAddPage", method = RequestMethod.GET)
	public String prmHosKpiObjAddPage(Model mode) throws Exception {

		return "hrp/prm/prmhoskpiobj/prmHosKpiObjAdd";

	}

	/**
	 * @Description 添加数据 0202 医院绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/addPrmHosKpiObj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmHosKpiObj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		mapVo.put("is_stop", 0);

		List<PrmHosKpiObj> list_hos = prmHosKpiObjService.queryPrmHosInfoForAdd(mapVo);// 查询一共存在多少家下级医院

		List<Map<String, Object>> listHosKpiObjBatch = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> list_hos_kpi_obj_batch = new ArrayList<Map<String, Object>>();

		//List<Map<String, Object>> list_hos_kpi = new ArrayList<Map<String, Object>>();

		JSONArray hos_kpi_json = JSONArray.parseArray((String) mapVo.get("hos_kpi_data"));

		Iterator hos_kpi_it = hos_kpi_json.iterator();

		try {

			while (hos_kpi_it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(hos_kpi_it.next().toString());

				for (PrmHosKpiObj list_hos2 : list_hos) {
					
					String check_hos_id  = String.valueOf(jsonObj.get("check_hos_id" + list_hos2.getHos_id()));

					if (check_hos_id!= null && !"null".equals(check_hos_id)) {

						if ("true".equals(check_hos_id) || !"".equals(check_hos_id)) {

							Map<String, Object> map = new HashMap<String, Object>();

							map = hosKpiObjValue(mapVo);

							String kpi_code = (String) jsonObj.get("kpi_code");
							
							String super_kpi_code = (String) jsonObj.get("super_kpi_code");

							map.put("kpi_code", kpi_code);
							
							map.put("super_kpi_code", super_kpi_code);

							map.put("check_hos_id", list_hos2.getHos_id());

							PrmHosScheme prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(map);

							if (prmHosScheme == null) {

								listHosKpiObjBatch.add(map);
							}

						} else {

							Map<String, Object> mapV = new HashMap<String, Object>();

							mapV = hosKpiObjValue(mapVo);

							String kpi_code = (String) jsonObj.get("kpi_code");
							
							String super_kpi_code = (String) jsonObj.get("super_kpi_code");
							
							mapV.put("super_kpi_code", super_kpi_code);

							mapV.put("kpi_code", kpi_code);

							mapV.put("check_hos_id", list_hos2.getHos_id());

							PrmHosScheme prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(mapV);

							if (prmHosScheme == null) {

								list_hos_kpi_obj_batch.add(mapV);
							}

						}

					}

				}

				/*Map<String, Object> map = new HashMap<String, Object>();

				map = hosKpiValue(mapVo, jsonObj);

				PrmHosKpi prmHosKpi = prmHosKpiService.queryPrmHosKpiByCode(map);

				if (prmHosKpi == null) {

					list_hos_kpi.add(map);
				}*/

			}
		} catch (DataAccessException e) {

			e.printStackTrace();
		}
		
		/*if (list_hos_kpi.size() > 0) {

			prmHosKpiService.addBatchPrmHosKpi(list_hos_kpi);

		}*/

		if (list_hos_kpi_obj_batch.size() > 0) {
			
			prmHosKpiObjService.deleteBatchPrmHosKpiObj(list_hos_kpi_obj_batch);
			
		}
		
		/*if (listHosKpiObjBatch.size() > 0) {
			
			prmHosKpiObjService.deleteBatchPrmHosKpiObj(listHosKpiObjBatch);
			
		}*/
		String prmHosKpiObjJson = prmHosKpiObjService.addBatchPrmHosKpiObj(listHosKpiObjBatch);

		return JSONObject.parseObject(prmHosKpiObjJson);

	}
	
	/**
	 * @Description 保存数据 0202 医院绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/savePrmHosKpiObj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePrmHosKpiObj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null ){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("copy_code") == null ){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("is_stop", 0);
		String prmHosKpiObjJson = null;
		
		try {
			prmHosKpiObjJson = prmHosKpiObjService.savePrmHosKpiObj(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			prmHosKpiObjJson = e.getMessage();
		}
		
		return JSONObject.parseObject(prmHosKpiObjJson);
	}
	
	

	public boolean validateJSON(String str) {

		if (str != null && str != "null" && str != "" && str != "0") {

			return true;

		}

		return false;

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

	// 返回用用于保存的默认值
	public Map<String, Object> hosKpiValue(Map<String, Object> mapVo, JSONObject jSONObject) {

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

		map.put("kpi_code", jSONObject.get("kpi_code"));

		map.put("kpi_name", jSONObject.get("kpi_name"));

		map.put("nature_code", jSONObject.get("nature_code"));

		map.put("super_kpi_code", mapVo.get("super_kpi_code"));

		map.put("order_no", 0);

		map.put("kpi_level", 0);

		map.put("spell_code", StringTool.toPinyinShouZiMu((String) jSONObject.get("kpi_name")));

		map.put("wbx_code", StringTool.toWuBi((String) jSONObject.get("kpi_name")));

		map.put("is_last", 0);

		return map;
	}

	/**
	 * @Description 更新跳转页面 0202 医院绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/prmHosKpiObjUpdatePage", method = RequestMethod.GET)
	public String prmHosKpiObjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		PrmHosKpiObj prmHosKpiObj = new PrmHosKpiObj();

		prmHosKpiObj = prmHosKpiObjService.queryPrmHosKpiObjByCode(mapVo);

		mode.addAttribute("group_id", prmHosKpiObj.getGroup_id());
		mode.addAttribute("hos_id", prmHosKpiObj.getHos_id());
		mode.addAttribute("copy_code", prmHosKpiObj.getCopy_code());
		mode.addAttribute("acc_year", prmHosKpiObj.getAcc_year());
		mode.addAttribute("goal_code", prmHosKpiObj.getGoal_code());
		mode.addAttribute("kpi_code", prmHosKpiObj.getKpi_code());
		mode.addAttribute("check_hos_id", prmHosKpiObj.getCheck_hos_id());
		mode.addAttribute("super_kpi_code", prmHosKpiObj.getSuper_kpi_code());
		mode.addAttribute("order_no", prmHosKpiObj.getOrder_no());
		mode.addAttribute("is_last", prmHosKpiObj.getIs_last());

		return "hrp/prm/prmhoskpiobj/prmHosKpiObjUpdate";
	}

	/**
	 * @Description 更新数据 0202 医院绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/updatePrmHosKpiObj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmHosKpiObj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String prmHosKpiObjJson = prmHosKpiObjService.updatePrmHosKpiObj(mapVo);

		return JSONObject.parseObject(prmHosKpiObjJson);
	}

	/**
	 * @Description 删除数据 0202 医院绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/deletePrmHosKpiObj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmHosKpiObj(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("goal_code", ids[4]);
			mapVo.put("kpi_code", ids[5]);
			mapVo.put("check_hos_id", ids[6]);

			listVo.add(mapVo);

		}

		String prmHosKpiObjJson = prmHosKpiObjService.deleteBatchPrmHosKpiObj(listVo);

		return JSONObject.parseObject(prmHosKpiObjJson);

	}

	/**
	 * @Description 查询数据 0202 医院绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/queryPrmHosKpiObjHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiObjHos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String prmHosKpiObj = prmHosKpiObjService.queryPrmHosKpiObjHos(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiObj);

	}

	/**
	 * @Description 查询数据 0202 医院绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/queryPrmHosInfoColumns", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmHosInfoColumns(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		mapVo.put("is_stop", 0);

		String hosInfo = prmHosKpiObjService.queryPrmHosInfo(mapVo);

		return hosInfo;

	}

	/**
	 * @Description 查询数据 0202 医院绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiobj/queryPrmHosKpiTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null || "".equals(mapVo.get("hos_id"))) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String prmHosKpiObj = prmHosKpiService.queryPrmHosKpiTree(mapVo);

		return JSONObject.parseObject(prmHosKpiObj);

	}
}
