/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

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
import com.chd.hrp.cost.service.CostReportService;

/**
 * @Title. @Description. 科室直接成本报表
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostReportController extends BaseController {

	private static Logger logger = Logger.getLogger(CostReportController.class);

	@Resource(name = "costReportService")
	private final CostReportService costReportService = null;

	/**
	 * 医技分摊设置采集数据表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costreport/costReportMainPage1", method = RequestMethod.GET)
	public String costReportMainPage1(Model mode) throws Exception {

		return "hrp/cost/costreport/costReportMain1";

	}

	/**
	 * 医技分摊设置采集数据表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costreport/costReportMainPage2", method = RequestMethod.GET)
	public String costReportMainPage2(Model mode) throws Exception {

		return "hrp/cost/costreport/costReportMain2";

	}

	/**
	 * 医技分摊设置采集数据表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costreport/costReportMainPage3", method = RequestMethod.GET)
	public String costReportMainPage3(Model mode) throws Exception {

		return "hrp/cost/costreport/costReportMain3";

	}
	
   /**
    * 
	* @Title: queryCostTypeDictThead
	* @Description: 动态查询成本类型表头
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年11月11日 上午10:05:06
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costreport/queryCostTypeDictThead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostTypeDictThead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String costTypeDictTheadJson = costReportService.queryCostTypeDictThead(mapVo);

		return JSONObject.parseObject(costTypeDictTheadJson);

	}
	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costreport/queryCostDeptReport_1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptReport_1(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());

		String costReport = costReportService.queryCostDeptReport_1(getPage(mapVo));

		return JSONObject.parseObject(costReport);

	}

	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costreport/queryCostDeptReport_2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptReport_2(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String costReport = costReportService.queryCostDeptReport_2(getPage(mapVo));

		return JSONObject.parseObject(costReport);

	}

	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costreport/queryCostDeptReport_3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptReport_3(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String costReport = costReportService.queryCostDeptReport_3(getPage(mapVo)); 

		return JSONObject.parseObject(costReport);

	}
}
