package com.chd.hrp.acc.controller.vouch;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.vouch.AccVouchDifferService;

@Controller
@RequestMapping(value = "hrp/acc/accvouch/diff")
public class AccVouchDifferController extends BaseController {

	@Resource(name = "accVouchDifferService")
	private AccVouchDifferService accVouchDifferService;

	@RequestMapping(value = "/accDifferQueryMainPage", method = RequestMethod.GET)
	public String toAccDifferQueryMainPage(Model mode) {
		String yearMonth = MyConfig.getCurAccYearMonth();
		if (yearMonth == null || yearMonth.equals("000000")) {
			String curDate = DateUtil.dateToString(new Date());
			yearMonth = curDate.substring(0, 4) + curDate.substring(5, 7);
		}

		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/accvouch/diff/accDifferQueryMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryAccDifferQuery", method = RequestMethod.POST)
	public Map<String, Object> queryAccDifferQuery(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = accVouchDifferService.queryAccDifferQuery(getPage(mapVo));
		return JSONObject.parseObject(result);
	}
}
