/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller.report;

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
import com.chd.hrp.prm.service.report.PrmDeptReportService;

/**
 * 
 * @Description: 8801 科室字典表
 * @Table: APHI_DEPT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptReportController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptReportController.class);

	// 引入Service服务
	@Resource(name = "prmDeptReportService")
	private final PrmDeptReportService prmDeptReportService = null;

	@RequestMapping(value = "/hrp/prm/report/prmDeptReportMainPage", method = RequestMethod.GET)
	public String prmDeptReportMainPage(Model mode) throws Exception {

		return "hrp/prm/report/prmDeptReportMain";

	}

	/**
	 * @Description 查询数据 返回Grid
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/report/queryPrmDeptReportGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptReportGrid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String grid = prmDeptReportService.queryPrmDeptReportGrid(mapVo);

		return JSONObject.parseObject(grid);

	}

}
