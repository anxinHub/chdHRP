/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.med.controller.termend;


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
import com.chd.base.util.DateUtil;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.termend.MedUnconfirmBillService;


/**
 * 
 * @Description:  常备药品入库
 * @Table: MED_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedUnconfirmBillController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedUnconfirmBillController.class);

	// 引入Service服务
	@Resource(name = "medUnconfirmBillService")
	private final MedUnconfirmBillService medUnconfirmBillService = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/termend/unconfirm/medUnconfirmBillPage", method = RequestMethod.GET)
	public String MedUnconfirmMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> map = medCommonService.queryMedCurYearMonth(mapVo);
		
		mode.addAttribute("med_year", map.get("acc_year"));
		mode.addAttribute("med_month", map.get("acc_month"));
		mode.addAttribute("med_begin_date", map.get("begin_date"));
		mode.addAttribute("med_end_date", map.get("end_date"));

		return "hrp/med/termend/unconfirm/medUnconfirmBill";
	}
	
	@RequestMapping(value = "/hrp/med/termend/unconfirm/queryMedUnconfirmBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedUnconfirmBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//转换日期格式
		if(mapVo.get("createDateB") != null && !"".equals(mapVo.get("createDateB"))){
			mapVo.put("createDateB", DateUtil.stringToDate(mapVo.get("createDateB").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("createDateE") != null && !"".equals(mapVo.get("createDateE"))){
			mapVo.put("createDateE", DateUtil.stringToDate(mapVo.get("createDateE").toString(), "yyyy-MM-dd"));
		}
		
		String medUnBill = medUnconfirmBillService.query(getPage(mapVo));
		logger.info(medUnBill);
		
		return JSONObject.parseObject(medUnBill);
	}
	
	@RequestMapping(value = "/hrp/med/termend/unconfirm/updateUnconfirmBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUnconfirmBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String medJson = medUnconfirmBillService.updateUnconfirmBill(mapVo);

		return JSONObject.parseObject(medJson);
	}

}
