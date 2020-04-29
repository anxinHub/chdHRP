/** 
 * @Description:
 * @Copyright: Copyright (c) 2016-08-06 下午3:54:34
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.AccYearMonthService;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.termend.MedFinalChargeService;
import com.chd.hrp.sys.service.ModStartService;

/**
 * 
 * @Description:  期末结账
 * @Table: 
 * @Author: Shirley
 * @email: Shirley@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedFinalChargeController  extends BaseController{
	private static Logger logger = Logger.getLogger(MedFinalChargeController.class);
	
	// 引入Service服务
	@Resource(name = "medFinalChargeService")
	private final MedFinalChargeService medFinalChargeService = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	@Resource(name = "modStartService")
	private final ModStartService modStartService = null;
	@Resource(name = "accYearMonthService")
	private final AccYearMonthService accYearMonthService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/termend/final/medFinalChargePage", method = RequestMethod.GET)
	public String MedFinalChargePage(Model mode) throws Exception {
		
		Map<String,Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		int by_store = Integer.valueOf(MyConfig.getSysPara("08045").toString());
		
		//统一结账
		Map<String, Object> mapCur = medCommonService.queryMedCurYearMonth(mapVo);
		Map<String, Object> mapLast = medCommonService.queryMedLastYearMonth(mapVo);
		
		mode.addAttribute("med_year", mapCur.get("acc_year"));
		mode.addAttribute("med_month", mapCur.get("acc_month"));

		mode.addAttribute("last_year", mapLast.get("last_year"));
		mode.addAttribute("last_month", mapLast.get("last_month"));
		
		//mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		if(by_store == 0){
			
			return "hrp/med/termend/final/medFinalCharge";
		}else{
			//分库房结账
			return "hrp/med/termend/final/medFinalChargeStore";
		}
	}
	
	@RequestMapping(value = "/hrp/med/termend/final/queryMedFinalCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedFinalCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String medJson=medFinalChargeService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medJson);
	}
	@RequestMapping(value = "/hrp/med/termend/final/updateMedFinalCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedFinalCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medJson=medFinalChargeService.updateMedFinalCharge(mapVo);
		
		return JSONObject.parseObject(medJson);
	}
	
	@RequestMapping(value = "/hrp/med/termend/final/updateMedFinalInverse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedFinalInverse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medJson=medFinalChargeService.updateMedFinalInverse(mapVo);
		
		return JSONObject.parseObject(medJson);
	}
	
	//查询是否有未确认单据
	@RequestMapping(value = "/hrp/med/termend/final/queryExistsMedFinalUnconfirm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExistsMedFinalUnconfirm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medJson=medFinalChargeService.queryExistsMedFinalUnconfirm(mapVo);
		
		return JSONObject.parseObject(medJson);
	}
	
	//查询虚仓对应期间
	@RequestMapping(value = "/hrp/med/termend/final/queryYearMonthByStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryYearMonthByStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medJson=medFinalChargeService.queryYearMonthByStoreSet(mapVo);
		
		return JSONObject.parseObject(medJson);
	}
	
}
