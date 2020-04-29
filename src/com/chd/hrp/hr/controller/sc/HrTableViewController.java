/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.controller.sc;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.sc.HrTableStrucService;


/**
 * 
 * @Description:
 * 
 * @Table: 
 * @Author: zn
 * @email: 
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/sc/hrtableview")
public class HrTableViewController extends BaseController {

	private static Logger logger = Logger.getLogger(HrTableViewController.class);

	// 引入Service服务
	@Resource(name = "hrTableStrucService")
	private final HrTableStrucService hrTableStrucService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableViewMainPage", method = RequestMethod.GET)
	public String hrTableViewMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtableview/hrTableViewMain";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTableView", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTableView(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        return hrTableStrucService.queryColSetByCode(mapVo);
	}

	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrTableView", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrTableView(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("tab_code") == null){
			return "{\"error\":\"请求参数不完整!\"}";
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			String reJson = hrTableStrucService.updateColSetByTabCode(mapVo);
			return reJson;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}


}
