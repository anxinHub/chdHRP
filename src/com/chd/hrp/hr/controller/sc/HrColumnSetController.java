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
 * @Table: HR_TABLE_STRUC
 * @Author: zn
 * @email: 
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/sc/hrcolumnset")
public class HrColumnSetController extends BaseController {

	private static Logger logger = Logger.getLogger(HrColumnSetController.class);

	// 引入Service服务
	@Resource(name = "hrTableStrucService")
	private final HrTableStrucService hrTableStrucService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrColumnSetMainPage", method = RequestMethod.GET)
	public String hrColumnSetMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrcolumnset/hrColumnSetMain";
	}
	
	/**
	 * @Description 预览页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrColumnSetPreviewPage", method = RequestMethod.GET)
	public String hrColumnSetPreviewPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrcolumnset/preview";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrColumnSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrColumnSet(@RequestParam Map<String, Object> mapVo, Model mode)
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
	@RequestMapping(value = "/saveHrColumnSet", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrColumnSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
