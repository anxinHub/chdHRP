package com.chd.hrp.htc.controller.income.cost.deptcost;

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
import com.chd.hrp.htc.entity.income.cost.deptcost.HtcIncomeDeptCost;
import com.chd.hrp.htc.service.income.cost.deptcost.HtcIncomeDeptCostService;

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
public class HtcIncomeDeptCostController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcIncomeDeptCostController.class);

	@Resource(name = "htcIncomeDeptCostService")
	private final HtcIncomeDeptCostService htcIncomeDeptCostService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/htcIncomeDeptCostMainPage", method = RequestMethod.GET)
	public String htcIncomeDeptCostMainPage(Model mode) throws Exception {

		return "hrp/htc/income/cost/deptcost/htcIncomeDeptCostMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/htcIncomeDeptCostAddPage", method = RequestMethod.GET)
	public String htcIncomeDeptCostAddPage(Model mode) throws Exception {


		return "hrp/htc/income/cost/deptcost/htcIncomeDeptCostAdd";

	}

	

	// 保存
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/addHtcIncomeDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIncomeDeptCost(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptCostJson = "";
		
		try {
	  		
			deptCostJson = htcIncomeDeptCostService.addHtcIncomeDeptCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptCostJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptCostJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/queryHtcIncomeDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeDeptCost(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
        mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptCost = htcIncomeDeptCostService.queryHtcIncomeDeptCost(getPage(mapVo));

		return JSONObject.parseObject(deptCost);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/deleteHtcIncomeDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcIncomeDeptCost(
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
			
			 deptCostJson = htcIncomeDeptCostService.deleteBatchHtcIncomeDeptCost(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptCostJson = e.getMessage();
		}

		return JSONObject.parseObject(deptCostJson);

	}




	// 查询页面跳转
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/htcIncomeDeptCostSelectPage", method = RequestMethod.GET)
	public String htcIncomeDeptCostSelectPage(@RequestParam Map<String, Object> mapVo,
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
			rest = "hrp/htc/income/cost/deptcost/htcIncomeDeptCostSelectTot";
			break;
		case 2:
			rest = "hrp/htc/income/cost/deptcost/htcIncomeDeptCostSelectPrime";
			break;
		case 3:
			rest = "hrp/htc/income/cost/deptcost/htcIncomeDeptCostSelectPub";
			break;
		case 4:
			rest = "hrp/htc/income/cost/deptcost/htcIncomeDeptCostSelectMan";
			break;
		case 5:
			rest = "hrp/htc/income/cost/deptcost/htcIncomeDeptCostSelectAss";
			break;
		default:
			break;
		}
		return rest;
	}

	
	
	
	// 查询成本项目多对应的资金来源(总成本)
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/queryHtcIncomeDeptCostTotAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeDeptCostTotAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcIncomeDeptCostService.queryHtcIncomeDeptCostTotAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(直接成本)
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/queryHtcIncomeDeptCostPrimeAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeDeptCostPrimeAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcIncomeDeptCostService.queryHtcIncomeDeptCostPrimeAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(公用分摊成本)
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/queryHtcIncomeDeptCostPubAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeDeptCostPubAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcIncomeDeptCostService.queryHtcIncomeDeptCostPubAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(管理分摊成本)
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/queryHtcIncomeDeptCostManAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeDeptCostManAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcIncomeDeptCostService.queryHtcIncomeDeptCostManAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(医辅分摊成本)
	@RequestMapping(value = "/hrp/htc/income/cost/deptcost/queryHtcIncomeDeptCostAssAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeDeptCostAssAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcIncomeDeptCostService.queryHtcIncomeDeptCostAssAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}
	
	/**
	* 导入
	*/ 
	@RequestMapping(value="/hrp/htc/income/cost/deptcost/impHtcIncomeDeptCost",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcPeopleTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String deptCost = "";
		 
		try {
			  deptCost= htcIncomeDeptCostService.impHtcIncomeDeptCost(mapVo);
			 
		} catch (Exception e) {
			
			 deptCost = e.getMessage();
		}
		
		return JSONObject.parseObject(deptCost);
    }  
	
}
