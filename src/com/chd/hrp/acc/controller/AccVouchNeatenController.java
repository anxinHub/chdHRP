package com.chd.hrp.acc.controller;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchServiceImpl;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccVouchNeatenController extends BaseController {
	private static Logger logger = Logger.getLogger(AccVouchNeatenController.class);

	@Resource(name = "accVouchService")
	private final AccVouchServiceImpl accVouchService = null;
	/**
	 * 凭证统计<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/neaten/accVouchNeatenMainPage", method = RequestMethod.GET)
	public String accVouchNeatenMainPage(Model mode) throws Exception {

		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		return "hrp/acc/accvouch/neaten/accVouchNeatenMain";

	}
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/neaten/queryAccVouchNeaten", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVouchNeaten(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		}
		
		if (mapVo.get("acc_year") == null) {
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
		
		}
		
		String accVouch = accVouchService.queryAccVouchNeaten(mapVo);

		return JSONObject.parseObject(accVouch);

	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/neaten/updateVouchNeaten", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateVouchNeaten(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String vouch ="";
		
		String para = mapVo.get("ParamVo").toString();
		
		String [] vouch_no = para.split(",");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("group_id", vouch_no[0].split("@")[2]);
		
		map.put("hos_id", vouch_no[0].split("@")[3]);
			
		map.put("copy_code", vouch_no[0].split("@")[4]); 
		
		map.put("acc_year", vouch_no[0].split("@")[0].split("\\.")[0]);
			
		map.put("acc_month",vouch_no[0].split("@")[0].split("\\.")[1]);
		
		map.put("vouch_type_code", vouch_no[0].split("@")[1]);
		
		for (int i = 0; i < vouch_no.length; i++) {
			
			String [] ids = vouch_no[i].split("@");
			
			//vouch+=ids[5].substring(2, ids[5].length())+",";
			vouch+=ids[5].substring(0, ids[5].length())+",";
		}
		
		map.put("vouch_no", vouch.substring(0, vouch.length()-1));
		
		String accVouch = accVouchService.updateBatchAccVouchLen(map);

		return JSONObject.parseObject(accVouch);

	}
}
