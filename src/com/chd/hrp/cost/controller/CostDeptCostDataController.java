/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostDeptCostData;
import com.chd.hrp.cost.service.CostDeptCostDataService;
import com.chd.hrp.cost.service.CostDeptDriDataService;
import com.chd.hrp.cost.service.CostParaService;

/**
 * @Title. @Description. 科室成本核算表
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostDeptCostDataController extends BaseController {

	private static Logger logger = Logger.getLogger(CostDeptCostDataController.class);

	@Resource(name = "costDeptCostDataService")
	private final CostDeptCostDataService costDeptCostDataService = null;

	@Resource(name = "costDeptDriDataService")
	private final CostDeptDriDataService costDeptDriDataService = null;

	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;

	/**
	 * 科室成本核算表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costdeptcostdata/costDeptCostDataMainPage", method = RequestMethod.GET)
	public String costDeptCostDataMainPage(Model mode) throws Exception {

		return "hrp/cost/costdeptcostdata/costDeptCostDataMain";

	}

	/**
	 * 科室成本核算表<BR>
	 * 科室成本分摊
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcostdata/deptCostShareData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deptCostShareData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String result = costDeptCostDataService.deptCostShareData(mapVo);

		return JSONObject.parseObject(result);

	}

	/**
	 * 科室成本核算表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcostdata/queryCostDeptCostData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptCostData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String para_value = (String) ((SessionManager.getCostParaMap().get("03001") == null) ? 0 : SessionManager.getCostParaMap().get("03001").toString());

		mapVo.put("is_flag", para_value);

		String costDeptCostData = costDeptCostDataService.queryCostDeptCostData(getPage(mapVo));

		return JSONObject.parseObject(costDeptCostData);

	}
}
