package com.chd.hrp.mat.controller.dura.termend;

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
import com.chd.hrp.mat.service.dura.termend.MatDuraFinalChargeService;
import com.chd.hrp.sys.service.ModStartService;

/**
 * 
 * @Description:  耐用品期末结账
 * @Table: 
 * @Author: yaoqingsong
 * @email: yaoqingsong@dhcc.com.cn
 * @Version: 1.0
 */
@Controller
public class MatDuraFinalChargeController  extends BaseController{
	private static Logger logger = Logger.getLogger(MatDuraFinalChargeController.class);
	
	// 引入Service服务
	@Resource(name = "matDuraFinalChargeService")
	private final MatDuraFinalChargeService matDuraFinalChargeService = null;
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
	
	@RequestMapping(value = "/hrp/mat/dura/termend/mainPage", method = RequestMethod.GET)
	public String MatFinalChargePage(Model mode) throws Exception {
		
		Map<String,Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//统一结账
		Map<String, Object> mapCur = matCommonService.queryMatDuraCurYearMonth(mapVo);
		Map<String, Object> mapLast = matCommonService.queryMatDuraLastYearMonth(mapVo);
		
		mode.addAttribute("mat_year", mapCur.get("acc_year"));
		mode.addAttribute("mat_month", mapCur.get("acc_month"));

		mode.addAttribute("last_year", mapLast.get("last_year"));
		mode.addAttribute("last_month", mapLast.get("last_month"));
		
		return "hrp/mat/dura/termend/matDuraFinalCharge";
		
	}
	
	@RequestMapping(value = "/hrp/mat/dura/termend/updateMatDuraFinalCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDuraFinalCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson=matDuraFinalChargeService.updateMatDuraFinalCharge(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	
	@RequestMapping(value = "/hrp/mat/dura/termend/updateMatDuraFinalInverse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDuraFinalInverse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson=matDuraFinalChargeService.updateMatDuraFinalInverse(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	
	//查询是否有未确认单据
	@RequestMapping(value = "/hrp/mat/dura/termend/queryExistsMatDuraFinalUnconfirm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExistsMatFinalUnconfirm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson=matDuraFinalChargeService.queryExistsMatDuraFinalUnconfirm(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
}
