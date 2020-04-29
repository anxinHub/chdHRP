package com.chd.hrp.htc.controller.relative.cost.deptcost;

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
import com.chd.hrp.htc.service.relative.cost.deptcost.HtcRelativeDeptCostService;

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
public class HtcRelativeDeptCostController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcRelativeDeptCostController.class);

	@Resource(name = "htcRelativeDeptCostService")
	private final HtcRelativeDeptCostService htcRelativeDeptCostService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/htcRelativeDeptCostMainPage", method = RequestMethod.GET)
	public String htcRelativeDeptCostMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/cost/deptcost/htcRelativeDeptCostMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/htcRelativeDeptCostAddPage", method = RequestMethod.GET)
	public String htcRelativeDeptCostAddPage(Model mode) throws Exception {


		return "hrp/htc/relative/cost/deptcost/htcRelativeDeptCostAdd";

	}

	

	// 保存
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/addHtcRelativeDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcRelativeDeptCost(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptCostJson = "";
		
		try {
	  		
			deptCostJson = htcRelativeDeptCostService.addHtcRelativeDeptCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptCostJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptCostJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/queryHtcRelativeDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeDeptCost(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
        mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptCost = htcRelativeDeptCostService.queryHtcRelativeDeptCost(getPage(mapVo));

		return JSONObject.parseObject(deptCost);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/deleteHtcRelativeDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcRelativeDeptCost(
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
			
			 deptCostJson = htcRelativeDeptCostService.deleteBatchHtcRelativeDeptCost(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptCostJson = e.getMessage();
		}

		return JSONObject.parseObject(deptCostJson);

	}




	// 查询页面跳转
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/htcRelativeDeptCostSelectPage", method = RequestMethod.GET)
	public String htcRelativeDeptCostSelectPage(@RequestParam Map<String, Object> mapVo,
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
			rest = "hrp/htc/relative/cost/deptcost/htcRelativeDeptCostSelectTot";
			break;
		case 2:
			rest = "hrp/htc/relative/cost/deptcost/htcRelativeDeptCostSelectPrime";
			break;
		case 3:
			rest = "hrp/htc/relative/cost/deptcost/htcRelativeDeptCostSelectPub";
			break;
		case 4:
			rest = "hrp/htc/relative/cost/deptcost/htcRelativeDeptCostSelectMan";
			break;
		case 5:
			rest = "hrp/htc/relative/cost/deptcost/htcRelativeDeptCostSelectAss";
			break;
		default:
			break;
		}
		return rest;
	}

	
	
	
	// 查询成本项目多对应的资金来源(总成本)
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/queryHtcRelativeDeptCostTotAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeDeptCostTotAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcRelativeDeptCostService.queryHtcRelativeDeptCostTotAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(直接成本)
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/queryHtcRelativeDeptCostPrimeAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeDeptCostPrimeAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcRelativeDeptCostService.queryHtcRelativeDeptCostPrimeAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(公用分摊成本)
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/queryHtcRelativeDeptCostPubAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeDeptCostPubAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcRelativeDeptCostService.queryHtcRelativeDeptCostPubAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(管理分摊成本)
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/queryHtcRelativeDeptCostManAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeDeptCostManAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcRelativeDeptCostService.queryHtcRelativeDeptCostManAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}

	// 查询成本项目多对应的资金来源(医辅分摊成本)
	@RequestMapping(value = "/hrp/htc/relative/cost/deptcost/queryHtcRelativeDeptCostAssAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeDeptCostAssAmount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptCostSource = htcRelativeDeptCostService.queryHtcRelativeDeptCostAssAmount(getPage(mapVo));
		return JSONObject.parseObject(deptCostSource);
	}
	
	/**
	* 导入
	*/ 
	@RequestMapping(value="/hrp/htc/relative/cost/deptcost/impHtcRelativeDeptCost",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcPeopleTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String deptCost = "";
		 
		try {
			  deptCost= htcRelativeDeptCostService.impHtcRelativeDeptCost(mapVo);
			 
		} catch (Exception e) {
			
			 deptCost = e.getMessage();
		}
		
		return JSONObject.parseObject(deptCost);
    }  
	
}
