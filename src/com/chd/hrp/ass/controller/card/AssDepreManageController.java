/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.card;

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
import com.chd.hrp.ass.service.depre.manager.AssDepreManageGeneralService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageHouseService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageInassetsService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageLandService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageOtherService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDepreManageController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDepreManageController.class);

	// 引入Service服务
	@Resource(name = "assDepreManageGeneralService")
	private final AssDepreManageGeneralService assDepreManageGeneralService = null;

	@Resource(name = "assDepreManageHouseService")
	private final AssDepreManageHouseService assDepreManageHouseService = null;

	@Resource(name = "assDepreManageOtherService")
	private final AssDepreManageOtherService assDepreManageOtherService = null;

	@Resource(name = "assDepreManageSpecialService")
	private final AssDepreManageSpecialService assDepreManageSpecialService = null;
	
	@Resource(name = "assDepreManageInassetsService")
	private final AssDepreManageInassetsService assDepreManageInassetsService = null;

	@Resource(name = "assDepreManageLandService")
	private final AssDepreManageLandService assDepreManageLandService = null;

	
	
	
	@RequestMapping(value = "/hrp/ass/asscard/queryAssDepreManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreManage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreManage = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assDepreManage = assDepreManageHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assDepreManage = assDepreManageSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assDepreManage = assDepreManageGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assDepreManage = assDepreManageOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assDepreManage = assDepreManageInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assDepreManage = assDepreManageLandService.query(getPage(mapVo));
		}
		return JSONObject.parseObject(assDepreManage);
	}

}
