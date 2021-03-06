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
import com.chd.hrp.ass.service.depre.acc.AssDepreAccGeneralService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccHouseService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccInassetsService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccLandService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccOtherService;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDepreAccController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDepreAccController.class);

	// 引入Service服务
	@Resource(name = "assDepreAccGeneralService")
	private final AssDepreAccGeneralService assDepreAccGeneralService = null;

	@Resource(name = "assDepreAccHouseService")
	private final AssDepreAccHouseService assDepreAccHouseService = null;

	@Resource(name = "assDepreAccOtherService")
	private final AssDepreAccOtherService assDepreAccOtherService = null;

	@Resource(name = "assDepreAccSpecialService")
	private final AssDepreAccSpecialService assDepreAccSpecialService = null;
	
	@Resource(name = "assDepreAccInassetsService")
	private final AssDepreAccInassetsService assDepreAccInassetsService = null;

	@Resource(name = "assDepreAccLandService")
	private final AssDepreAccLandService assDepreAccLandService = null;

	
	
	
	@RequestMapping(value = "/hrp/ass/asscard/queryAssDepreAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreAcc(@RequestParam Map<String, Object> mapVo, Model mode)
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
			assDepreAcc = assDepreAccHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assDepreAcc = assDepreAccSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assDepreAcc = assDepreAccGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assDepreAcc = assDepreAccOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assDepreAcc = assDepreAccInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assDepreAcc = assDepreAccLandService.query(getPage(mapVo));
		}
		return JSONObject.parseObject(assDepreAcc);
	}
	
	
	@RequestMapping(value = "/hrp/ass/asscard/collectDepreAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectDepreAcc(@RequestParam Map<String, Object> mapVo, Model mode)
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
