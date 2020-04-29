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
import com.chd.hrp.cost.service.CostIassetImputationService;


/**
 * @Title. @Description. 科室材料支出明细表
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostIassetImputationController extends BaseController {

	private static Logger logger = Logger.getLogger(CostIassetImputationController.class);

	@Resource(name = "costIassetImputationService")
	private final CostIassetImputationService costIassetImputationService = null;

	/**
	 * 科室材料支出明细表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costIassetImputation/costIassetImputationMainPage", method = RequestMethod.GET)
	public String costIassetImputationMainPage(Model mode) throws Exception {

		return "hrp/cost/costiassetimputation/costIassetImputationMain";

	}

	/**
	 * 科室材料支出明细表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costIassetImputation/queryCostIassetImputation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostIassetImputation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}
		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());

		mapVo.put("is_flag", para_value);

		String costIassetImputation = costIassetImputationService.queryCostIassetImputation(getPage(mapVo));

		return JSONObject.parseObject(costIassetImputation);

	}

}
