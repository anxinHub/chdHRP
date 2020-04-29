/** 
 * @Description:
 * @Copyright: Copyright (c) 2016-08-06 下午3:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.mat.controller.termend;

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
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.termend.MatFinalChargeService;
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
public class MatFinalChargeController  extends BaseController{
	private static Logger logger = Logger.getLogger(MatFinalChargeController.class);
	
	// 引入Service服务
	@Resource(name = "matFinalChargeService")
	private final MatFinalChargeService matFinalChargeService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
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
	@RequestMapping(value = "/hrp/mat/termend/final/matFinalChargePage", method = RequestMethod.GET)
	public String MatFinalChargePage(Model mode) throws Exception {
		
		Map<String,Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		int by_store = Integer.valueOf(MyConfig.getSysPara("04045"));
		
		if(by_store == 0){
			//统一结账
			Map<String, Object> mapCur = matCommonService.queryMatCurYearMonth(mapVo);
			Map<String, Object> mapLast = matCommonService.queryMatLastYearMonth(mapVo);
			
			mode.addAttribute("mat_year", mapCur.get("acc_year"));
			mode.addAttribute("mat_month", mapCur.get("acc_month"));
	
			mode.addAttribute("last_year", mapLast.get("last_year"));
			mode.addAttribute("last_month", mapLast.get("last_month"));
			
			mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
			
			return "hrp/mat/termend/final/matFinalCharge";
		}else{
			//分库房结账
			return "hrp/mat/termend/final/matFinalChargeStore";
		}
	}
	
	@RequestMapping(value = "/hrp/mat/termend/final/queryMatFinalCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatFinalCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matJson=matFinalChargeService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}
	@RequestMapping(value = "/hrp/mat/termend/final/updateMatFinalCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatFinalCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson=matFinalChargeService.updateMatFinalCharge(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	
	@RequestMapping(value = "/hrp/mat/termend/final/updateMatFinalInverse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatFinalInverse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson=matFinalChargeService.updateMatFinalInverse(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	
	//查询是否有未确认单据
	@RequestMapping(value = "/hrp/mat/termend/final/queryExistsMatFinalUnconfirm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExistsMatFinalUnconfirm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson=matFinalChargeService.queryExistsMatFinalUnconfirm(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	
	//查询虚仓对应期间
	@RequestMapping(value = "/hrp/mat/termend/final/queryYearMonthByStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryYearMonthByStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson=matFinalChargeService.queryYearMonthByStoreSet(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	
}
