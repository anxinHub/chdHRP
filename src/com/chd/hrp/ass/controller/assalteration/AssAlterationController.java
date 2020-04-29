package com.chd.hrp.ass.controller.assalteration;

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
import com.chd.hrp.ass.service.assalteration.AssAlterationService;
@Controller
public class AssAlterationController extends BaseController {
	
	@Resource(name="assAlterationService")
	private final AssAlterationService assAlterationService = null;
	
	
	@RequestMapping(value="/hrp/ass/assalteration/AssAlterationPage", method = RequestMethod.GET)
	public String AssAlterationPage(Model mode){
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		mode.addAttribute("user_name",SessionManager.getUserName());
		return "hrp/ass/assalteration/assAlterationMain";
	}

	
	/**
	 * @Description 
	 * 查询数据
	 * @param  mapVo
	 * @param  mode 
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assalteration/queryAssAlteration", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAlteration(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("year_month")!= null ){
			String year = mapVo.get("year_month").toString().substring(0, 4);
			String month = mapVo.get("year_month").toString().substring(4, 6);
			mapVo.put("acc_year",year);
			mapVo.put("acc_month",month);
		}
	    
		String assAlterationMain = assAlterationService.queryAssAlteration(getPage(mapVo));

		return JSONObject.parseObject(assAlterationMain);
		
	}
}
