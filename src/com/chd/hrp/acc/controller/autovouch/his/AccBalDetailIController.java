package com.chd.hrp.acc.controller.autovouch.his;

import java.util.HashMap;
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
import com.chd.hrp.acc.service.autovouch.his.AccBalDetailIService;
import com.chd.hrp.acc.service.autovouch.his.HisAccPreService;
@Controller
public class AccBalDetailIController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBalDetailIController.class);

	@Resource(name = "accBalDetailIService")
	private final AccBalDetailIService accBalDetailIService = null;
	@Resource(name = "hisAccPreService")
	private final HisAccPreService hisAccPreService = null;
	
	/**
	 * <BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/his/baldetail/accBalDetailIMainPage", method = RequestMethod.GET)
	public String accBalDetailIMainPage(Model mode) throws Exception {

		Map<String, Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("his_table", "acc_bal_detail_i");
		int type=hisAccPreService.queryHisAccType(mapVo);
		mode.addAttribute("type", type);
		
		return "hrp/acc/autovouch/his/baldetail/accBalDetailIMain";
	}

	/**
	 * <BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/his/baldetail/queryAccBalDetailI", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBalDetailI(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}
		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accBankCheckTell = accBalDetailIService.queryAccBalDetailI(getPage(mapVo));

		return JSONObject.parseObject(accBankCheckTell);

	}
}
