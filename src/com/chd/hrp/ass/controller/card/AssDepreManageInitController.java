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
import com.chd.hrp.ass.service.depre.manager.AssDepreManageInitGeneralService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageInitHouseService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageInitInassetsService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageInitLandService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageInitOtherService;
import com.chd.hrp.ass.service.depre.manager.AssDepreManageInitSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDepreManageInitController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDepreManageInitController.class);

	// 引入Service服务
	@Resource(name = "assDepreManageInitGeneralService")
	private final AssDepreManageInitGeneralService assDepreManageInitGeneralService = null;

	@Resource(name = "assDepreManageInitHouseService")
	private final AssDepreManageInitHouseService assDepreManageInitHouseService = null;

	@Resource(name = "assDepreManageInitOtherService")
	private final AssDepreManageInitOtherService assDepreManageInitOtherService = null;

	@Resource(name = "assDepreManageInitSpecialService")
	private final AssDepreManageInitSpecialService assDepreManageInitSpecialService = null;
	
	@Resource(name = "assDepreManageInitInassetsService")
	private final AssDepreManageInitInassetsService assDepreManageInitInassetsService = null;

	@Resource(name = "assDepreManageInitLandService")
	private final AssDepreManageInitLandService assDepreManageInitLandService = null;

	
	
	
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssDepreManageInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreManageInit(@RequestParam Map<String, Object> mapVo, Model mode)
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
			assDepreManage = assDepreManageInitHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assDepreManage = assDepreManageInitSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assDepreManage = assDepreManageInitGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assDepreManage = assDepreManageInitOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assDepreManage = assDepreManageInitInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assDepreManage = assDepreManageInitLandService.query(getPage(mapVo));
		}
		return JSONObject.parseObject(assDepreManage);
	}

}
