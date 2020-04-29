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
import com.chd.hrp.ass.service.depre.acc.AssDepreAccInitGeneralService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccInitHouseService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccInitInassetsService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccInitLandService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccInitOtherService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccInitSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDepreAccInitController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDepreAccInitController.class);

	// 引入Service服务
	@Resource(name = "assDepreAccInitGeneralService")
	private final AssDepreAccInitGeneralService assDepreAccInitGeneralService = null;

	@Resource(name = "assDepreAccInitHouseService")
	private final AssDepreAccInitHouseService assDepreAccInitHouseService = null;

	@Resource(name = "assDepreAccInitOtherService")
	private final AssDepreAccInitOtherService assDepreAccInitOtherService = null;

	@Resource(name = "assDepreAccInitSpecialService")
	private final AssDepreAccInitSpecialService assDepreAccInitSpecialService = null;
	
	@Resource(name = "assDepreAccInitInassetsService")
	private final AssDepreAccInitInassetsService assDepreAccInitInassetsService = null;

	@Resource(name = "assDepreAccInitLandService")
	private final AssDepreAccInitLandService assDepreAccInitLandService = null;

	
	
	
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssDepreAccInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreAccInit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreAcc = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assDepreAcc = assDepreAccInitHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assDepreAcc = assDepreAccInitSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assDepreAcc = assDepreAccInitGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assDepreAcc = assDepreAccInitOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assDepreAcc = assDepreAccInitInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assDepreAcc = assDepreAccInitLandService.query(getPage(mapVo));
		}
		return JSONObject.parseObject(assDepreAcc);
	}
	
	
	@RequestMapping(value = "/hrp/ass/asscardinit/collectDepreAccInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectDepreAccInit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreAcc = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assDepreAcc = "";
		} else if (ass_nature.equals("02")) {
			assDepreAcc = "";
		} else if (ass_nature.equals("03")) {
			assDepreAcc = "";
		} else if (ass_nature.equals("04")) {
			assDepreAcc = "";
		} else if (ass_nature.equals("05")) {
			assDepreAcc = "";
		} else if (ass_nature.equals("06")) {
			assDepreAcc = "";
		}
		return JSONObject.parseObject(assDepreAcc);
	}

}
