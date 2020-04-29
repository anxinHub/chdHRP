package com.chd.hrp.acc.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.acc.serviceImpl.AccVouchAdjunctServiceImpl;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright 
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccVouchAdjunctController extends BaseController {
	private static Logger logger = Logger.getLogger(AccVouchAdjunctController.class);

	@Resource(name = "accVouchAdjunctService")
	private final AccVouchAdjunctServiceImpl accVouchAdjunctService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	/**
	 * 凭证附件<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/adjunct/accVouchAdjunctMainPage", method = RequestMethod.GET)
	public String accVouchAdjunctMainPage(Model mode) throws Exception {
		
		return "hrp/acc/accvouch/adjunct/accVouchAdjunctMain";

	}
	/**
	 * 凭证附件<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/adjunct/queryAccVouchAdjunct", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccVouchAdjunct(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accVouch = accVouchAdjunctService.queryAccVouchAdjunct(getPage(mapVo));

		return JSONObject.parseObject(accVouch);

	}
	
	//下载附件
	@RequestMapping(value = "/hrp/acc/accvouch/adjunct/downAccFile", method = RequestMethod.GET)
	public String downAccFile(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		superVouchService.downloadFile(response, mapVo);
		return null;
	}
}
