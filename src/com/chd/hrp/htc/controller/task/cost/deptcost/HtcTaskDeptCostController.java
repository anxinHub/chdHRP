package com.chd.hrp.htc.controller.task.cost.deptcost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.htc.service.task.cost.deptcost.HtcTaskDeptCostService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34 
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcTaskDeptCostController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcTaskDeptCostController.class);

	@Resource(name = "htcTaskDeptCostService")
	private final HtcTaskDeptCostService htcTaskDeptCostService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/htcTaskDeptCostMainPage", method = RequestMethod.GET)
	public String htcTaskDeptCostMainPage(Model mode) throws Exception {

		return "hrp/htc/task/cost/deptcost/htcTaskDeptCostMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/htcTaskDeptCostAddPage", method = RequestMethod.GET)
	public String htcTaskDeptCostAddPage(Model mode) throws Exception {


		return "hrp/htc/task/cost/deptcost/htcTaskDeptCostAdd";

	}

	

	// 保存
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/addHtcTaskDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcTaskDeptCost(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptCostJson = "";
		
		try {
	  		
			deptCostJson = htcTaskDeptCostService.addHtcTaskDeptCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptCostJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptCostJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/queryHtcTaskDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskDeptCost(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
        mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptCost = htcTaskDeptCostService.queryHtcTaskDeptCost(getPage(mapVo));

		return JSONObject.parseObject(deptCost);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/deleteHtcTaskDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcTaskDeptCost(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("&");
		  	mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("dept_id", ids[5]);
			listVo.add(mapVo);
		}
		
		String deptCostJson = "";
		
		try {
			
			 deptCostJson = htcTaskDeptCostService.deleteBatchHtcTaskDeptCost(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptCostJson = e.getMessage();
		}

		return JSONObject.parseObject(deptCostJson);

	}




	// 查询页面跳转
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/htcTaskDeptCostSelectPage", method = RequestMethod.GET)
	public String htcTaskDeptCostSelectPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_name", new String(mapVo.get("dept_name").toString().getBytes("iso8859-1"),"UTF-8"));//中文转码

		int costTpe = Integer.parseInt(mapVo.get("cost_type").toString());
		String rest = "";
		switch (costTpe) {
		case 1:
			rest = "hrp/htc/task/cost/deptcost/htcTaskDeptCostSelectTot";
			break;
		case 2:
			rest = "hrp/htc/task/cost/deptcost/htcTaskDeptCostSelectPrime";
			break;
		case 3:
			rest = "hrp/htc/task/cost/deptcost/htcTaskDeptCostSelectPub";
			break;
		case 4:
			rest = "hrp/htc/task/cost/deptcost/htcTaskDeptCostSelectMan";
			break;
		case 5:
			rest = "hrp/htc/task/cost/deptcost/htcTaskDeptCostSelectAss";
			break;
		default:
			break;
		}
		return rest;
	}

	
	
	
	// 查询成本项目多对应的资金来源(总成本)
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/queryHtcTaskDeptCostTotAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskDeptCostTotAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcTaskDeptCostService.queryHtcTaskDeptCostTotAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(直接成本)
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/queryHtcTaskDeptCostPrimeAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskDeptCostPrimeAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcTaskDeptCostService.queryHtcTaskDeptCostPrimeAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(公用分摊成本)
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/queryHtcTaskDeptCostPubAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskDeptCostPubAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcTaskDeptCostService.queryHtcTaskDeptCostPubAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(管理分摊成本)
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/queryHtcTaskDeptCostManAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskDeptCostManAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcTaskDeptCostService.queryHtcTaskDeptCostManAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(医辅分摊成本)
	@RequestMapping(value = "/hrp/htc/task/cost/deptcost/queryHtcTaskDeptCostAssAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskDeptCostAssAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcTaskDeptCostService.queryHtcTaskDeptCostAssAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}
	
	/**
	* 导入
	*/ 
	@RequestMapping(value="/hrp/htc/task/cost/deptcost/impHtcTaskDeptCost",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcPeopleTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String deptCost = "";
		 
		try {
			  deptCost= htcTaskDeptCostService.impHtcTaskDeptCost(mapVo);
			 
		} catch (Exception e) {
			
			 deptCost = e.getMessage();
		}
		
		return JSONObject.parseObject(deptCost);
    }  
	
}
