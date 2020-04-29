/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.cost.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Times;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.entity.CostParaBill;
import com.chd.hrp.cost.entity.CostParaDept;
import com.chd.hrp.cost.entity.CostParaSetverDept;
import com.chd.hrp.cost.service.CostParaBillService;
import com.chd.hrp.cost.service.CostParaDeptService;
import com.chd.hrp.cost.service.CostParaSetverDeptService;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;

/**
 * @Description: 成本_分摊定向单据
 * @Table: COST_PARA_BILL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostParaBillController extends BaseController {

	private static Logger logger = Logger.getLogger(CostParaBillController.class);

	// 引入Service服务
	@Resource(name = "costParaBillService")
	private final CostParaBillService costParaBillService = null;

	// 引入Service服务
	@Resource(name = "costParaSetverDeptService")
	private final CostParaSetverDeptService costParaSetverDeptService = null;

	// 引入Service服务
	@Resource(name = "costParaDeptService")
	private final CostParaDeptService costParaDeptService = null;

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/costParaSetverDeptMainPage", method = RequestMethod.GET)
	public String costParaSetverDeptMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		mode.addAttribute("bill_code", mapVo.get("bill_code"));
		mode.addAttribute("type_code", mapVo.get("type_code"));
		return "hrp/cost/costparabill/costParaSetverDeptMain";

	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/costParaDeptMainPage", method = RequestMethod.GET)
	public String costParaDeptMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		mode.addAttribute("bill_code", mapVo.get("bill_code"));
		mode.addAttribute("type_code", mapVo.get("type_code"));
		return "hrp/cost/costparabill/costParaDeptMain";

	}
	

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/costParaBillMainPage", method = RequestMethod.GET)
	public String costParaBillMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("acct_year", Times.format("yyyy", new Date()));
		mapVo.put("acc_month", Times.format("MM", new Date()));
		mapVo.put("rownum", "1");

		List<CostParaBill> costParaBillJson = (List<CostParaBill>) costParaBillService.queryExists(mapVo);

		if (costParaBillJson.size() > 0) {
			mode.addAttribute("bill_code", costParaBillJson.get(0).getBill_code());
		} else {
			mode.addAttribute("bill_code", "0");
		}

		return "hrp/cost/costparabill/costParaBillMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/costParaBillAddPage", method = RequestMethod.GET)
	public String costParaBillAddPage(Model mode) throws Exception {

		return "hrp/cost/costparabill/costParaBillAdd";

	}

	/**
	 * @Description 添加数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/addCostParaBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostParaBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String costParaBillJson = costParaBillService.add(mapVo);

		return JSONObject.parseObject(costParaBillJson);

	}

	/**
	 * @Description 更新跳转页面 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/costParaBillUpdatePage", method = RequestMethod.GET)
	public String costParaBillUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		CostParaBill costParaBill = new CostParaBill();

		costParaBill = costParaBillService.queryByCode(mapVo);

		mode.addAttribute("group_id", costParaBill.getGroup_id());
		mode.addAttribute("hos_id", costParaBill.getHos_id());
		mode.addAttribute("copy_code", costParaBill.getCopy_code());
		mode.addAttribute("acc_year", costParaBill.getAcc_year());
		mode.addAttribute("acc_month", costParaBill.getAcc_month());
		mode.addAttribute("bill_code", costParaBill.getBill_code());
		mode.addAttribute("bill_name", costParaBill.getBill_name());
		mode.addAttribute("cost_type_code", costParaBill.getCost_type_code());

		return "hrp/cost/costparabill/costParaBillUpdate";
	}

	/**
	 * @Description 更新数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/updateCostParaBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostParaBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costParaBillJson = costParaBillService.update(mapVo);

		return JSONObject.parseObject(costParaBillJson);
	}

	/**
	 * @Description 更新数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/addOrUpdateCostParaBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateCostParaBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String costParaBillJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}

			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}

			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}

			costParaBillJson = costParaBillService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(costParaBillJson);
	}

	/**
	 * @Description 删除数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/deleteCostParaBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostParaBill(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String group_id = SessionManager.getGroupId();

		String hos_id = SessionManager.getHosId();

		String copy_code = SessionManager.getCopyCode();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", group_id);
			mapVo.put("hos_id", hos_id);
			mapVo.put("copy_code", copy_code);
			mapVo.put("acc_year", ids[0]);
			mapVo.put("acc_month", ids[1]);
			mapVo.put("bill_code", ids[2]);

			listVo.add(mapVo);

		}

		String costParaBillJson = costParaBillService.deleteBatch(listVo);

		costParaDeptService.deleteBatch(listVo);

		costParaSetverDeptService.deleteBatch(listVo);

		return JSONObject.parseObject(costParaBillJson);

	}

	/**
	 * @Description 删除数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/deleteCostParaDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostParaDept(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("bill_code", ids[5]);
			mapVo.put("dept_id", ids[6]);
			mapVo.put("dept_no", ids[7]);

			listVo.add(mapVo);

		}

		String costParaBillJson = costParaDeptService.deleteBatch(listVo);

		return JSONObject.parseObject(costParaBillJson);

	}

	/**
	 * @Description 删除数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/deleteCostParaServerDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostParaServerDept(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("bill_code", ids[5]);
			mapVo.put("dept_id", ids[6]);
			mapVo.put("dept_no", ids[7]);

			listVo.add(mapVo);

		}

		String costParaBillJson = costParaSetverDeptService.deleteBatch(listVo);

		return JSONObject.parseObject(costParaBillJson);

	}

	/**
	 * @Description 查询数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/queryCostParaBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String costParaBill = costParaBillService.query(getPage(mapVo));

		return JSONObject.parseObject(costParaBill);

	}

	/**
	 * @Description 查询数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/queryCostParaServerDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaServerDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		if (mapVo.get("type_code") != null) {

			if (mapVo.get("type_code").toString().equals("01")) {
				mapVo.put("type_code", "('01','02','03','04')");
			}
			if (mapVo.get("type_code").toString().equals("02")) {
				mapVo.put("type_code", "('01','02','03')");
			}
			if (mapVo.get("type_code").toString().equals("03")) {
				mapVo.put("type_code", "('01','02')");
			}
		}

		String dept = deptDictService.queryDeptDictCost(getPage(mapVo));

		return JSONObject.parseObject(dept);

	}

	/**
	 * @Description 查询数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/queryCostParaDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		if (mapVo.get("type_code") != null) {

			if (mapVo.get("type_code").toString().equals("01")) {
				mapVo.put("type_code", "('04')");
			}
			if (mapVo.get("type_code").toString().equals("02")) {
				mapVo.put("type_code", "('03')");
			}
			if (mapVo.get("type_code").toString().equals("03")) {
				mapVo.put("type_code", "('02')");
			}
			
			
		}

		String dept = deptDictService.queryDeptDictCost(getPage(mapVo));

		return JSONObject.parseObject(dept);

	}

	/**
	 * @Description 查询数据 成本_分摊定向单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/queryByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		List<?> l_map = costParaBillService.queryByTree(mapVo);
		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	/**
	 * @Description 添加数据 成本_分摊服务科室
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/addCostParaDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostParaDept(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		Map<String, Object> delMap = new HashMap<String, Object>();
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> qmap=new HashMap<String, Object>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", ids[2]);
			mapVo.put("acc_month", ids[3]);
			mapVo.put("bill_code", ids[4]);
			mapVo.put("dept_id", ids[5]);
			mapVo.put("dept_no", ids[6]);
			mapVo.put("type_code", ids[7]);
			mapVo.put("natur_code", ids[8]);
			mapVo.put("para_code", ids[9]);

			if (delMap.get("bill_code") == null) {
				delMap.put("group_id", ids[0]);
				delMap.put("hos_id", ids[1]);
				delMap.put("copy_code", SessionManager.getCopyCode());
				delMap.put("acc_year", ids[2]);
				delMap.put("acc_month", ids[3]);
				delMap.put("bill_code", ids[4]);
				
				qmap.put("group_id", ids[0]);
				qmap.put("hos_id", ids[1]);
				qmap.put("copy_code", SessionManager.getCopyCode());
				qmap.put("acc_year", ids[2]);
				qmap.put("acc_month", ids[3]);
				
			}
			listVo.add(mapVo);

		}


		List<CostParaDept> list= costParaDeptService.queryList(qmap);
		Set<String> set=new HashSet<String>();
		for (CostParaDept c : list) {
	        set.add(c.getDept_id().toString());
        }
		boolean flag=false;
		for (Map<String, Object> m : listVo) {
			if(set.contains(m.get("dept_id").toString())){
				
				flag=true;
				
				break;
			}
	        
        }
		
		if(flag){
			return JSONObject.parseObject("{\"msg\":\"同一个科室不允许多次配置，.\",\"state\":\"true\"}");
			
		}else{
			//costParaDeptService.delete(delMap);

			String costParaDeptJson = costParaDeptService.addBatch(listVo);

			return JSONObject.parseObject(costParaDeptJson);
		}
		
		

	}

	/**
	 * @Description 添加数据 成本_分摊受益科室
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/addCostParaServerDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostParaServerDept(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		Map<String, Object> delMap = new HashMap<String, Object>();
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", ids[2]);
			mapVo.put("acc_month", ids[3]);
			mapVo.put("bill_code", ids[4]);
			mapVo.put("dept_id", ids[5]);
			mapVo.put("dept_no", ids[6]);
			mapVo.put("type_code", ids[7]);
			mapVo.put("natur_code", ids[8]);
			mapVo.put("para_code", ids[9]);

			if (delMap.get("bill_code") == null) {
				delMap.put("group_id", ids[0]);
				delMap.put("hos_id", ids[1]);
				delMap.put("copy_code", SessionManager.getCopyCode());
				delMap.put("acc_year", ids[2]);
				delMap.put("acc_month", ids[3]);
				delMap.put("bill_code", ids[4]);
			}
			listVo.add(mapVo);

		}
		List<CostParaSetverDept> list= (List<CostParaSetverDept>) costParaSetverDeptService.queryExists(delMap);
		Set<String> set=new HashSet<String>();
		for (CostParaSetverDept c : list) {
	        set.add(c.getDept_id().toString());
        }
		boolean flag=false;
		for (Map<String, Object> m : listVo) {
			if(set.contains(m.get("dept_id").toString())){
				
				flag=true;
				
				break;
			}
	        
        }
		
		if(flag){
			return JSONObject.parseObject("{\"msg\":\"相同的服务科室不允许重复配置受益科室，.\",\"state\":\"true\"}");
		}else{
			//costParaSetverDeptService.delete(delMap);

			String costParaDeptJson = costParaSetverDeptService.addBatch(listVo);

			return JSONObject.parseObject(costParaDeptJson);
		}
		
	}

	/**
	 * @Description 查询数据 成本_分摊受益科室
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/queryCostParaSetverDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaSetverDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String costParaSetverDept = costParaSetverDeptService.query(getPage(mapVo));

		return JSONObject.parseObject(costParaSetverDept);

	}

	/**
	 * @Description 查询数据 成本_分摊服务科室
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/queryCostParaDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String costParaDept = costParaDeptService.query(getPage(mapVo));

		return JSONObject.parseObject(costParaDept);

	}

	/**
	 * @Description 生成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/generate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String l_map = costParaBillService.generate(mapVo);

		return JSONObject.parseObject(l_map);
	}
	/**
	 * @Description 继承跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/costParaBillSetExtendPage", method = RequestMethod.GET)
	public String costParaBillSetExtendPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/cost/costparabill/costParaBillSetExtend";
		
	}
	/**
	 * @Description 继承
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparabill/inheritance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inheritance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String l_map = costParaBillService.inheritance(mapVo);

		return JSONObject.parseObject(l_map);
	}
}
