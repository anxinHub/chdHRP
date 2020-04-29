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
import com.chd.hrp.ass.service.change.AssChangeDetailGeneralService;
import com.chd.hrp.ass.service.change.AssChangeDetailHouseService;
import com.chd.hrp.ass.service.change.AssChangeDetailInassetsService;
import com.chd.hrp.ass.service.change.AssChangeDetailLandService;
import com.chd.hrp.ass.service.change.AssChangeDetailOtherService;
import com.chd.hrp.ass.service.change.AssChangeDetailSpecialService;

@Controller
public class AssCardChangeInitController extends BaseController{
	private static Logger logger = Logger.getLogger(AssCardController.class);
	
	@Resource(name = "assChangeDetailGeneralService")
	private final AssChangeDetailGeneralService assChangeDetailGeneralService = null;
	
	@Resource(name = "assChangeDetailHouseService")
	private final AssChangeDetailHouseService assChangeDetailHouseService = null;
	
	@Resource(name = "assChangeDetailInassetsService")
	private final AssChangeDetailInassetsService assChangeDetailInassetsService = null;
	
	@Resource(name = "assChangeDetailLandService")
	private final AssChangeDetailLandService assChangeDetailLandService = null;
	
	@Resource(name = "assChangeDetailOtherService")
	private final AssChangeDetailOtherService assChangeDetailOtherService = null;
	
	@Resource(name = "assChangeDetailSpecialService")
	private final AssChangeDetailSpecialService assChangeDetailSpecialService = null;
	
	
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssChangeCardInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeCardInit(@RequestParam Map<String, Object> mapVo, Model mode)
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
			assDepreAcc = assChangeDetailHouseService.queryByAssCardNo(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assDepreAcc = assChangeDetailSpecialService.queryByAssCardNo(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assDepreAcc = assChangeDetailGeneralService.queryByAssCardNo(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assDepreAcc = assChangeDetailOtherService.queryByAssCardNo(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assDepreAcc = assChangeDetailInassetsService.queryByAssCardNo(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assDepreAcc = assChangeDetailLandService.queryByAssCardNo(getPage(mapVo));
		}
		return JSONObject.parseObject(assDepreAcc);
	}
}
