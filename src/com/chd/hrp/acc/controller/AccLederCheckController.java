/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.util.*;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccLederCheck;
import com.chd.hrp.acc.serviceImpl.AccLederCheckServiceImpl;

/**
* @Title. @Description.
* 财务辅助账表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccLederCheckController extends BaseController{
	private static Logger logger = Logger.getLogger(AccLederCheckController.class);
	
	
	@Resource(name = "accLederCheckService")
	private final AccLederCheckServiceImpl accLederCheckService = null;
   
    
	/**
	*财务辅助账表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accledercheck/accLederCheckMainPage", method = RequestMethod.GET)
	public String accLederCheckMainPage(Model mode) throws Exception {

		return "hrp/acc/accledercheck/accLederCheckMain";

	}
	/**
	*财务辅助账表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accledercheck/accLederCheckAddPage", method = RequestMethod.GET)
	public String accLederCheckAddPage(Model mode) throws Exception {

		return "hrp/acc/accledercheck/accLederCheckAdd";

	}
	/**
	*财务辅助账表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accledercheck/addAccLederCheck", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccLederCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String accLederCheckJson = "";//accLederCheckService.addAccLederCheck(mapVo);

		return JSONObject.parseObject(accLederCheckJson);
		
	}
	/**
	*财务辅助账表<BR>
	*查询
	*//*
	@RequestMapping(value = "/hrp/acc/accledercheck/queryAccLederCheck", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccLederCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accLederCheck = accLederCheckService.queryAccLederCheck(getPage(mapVo));

		return JSONObject.parseObject(accLederCheck);
		
	}*/

	
	/**
	*财务辅助账表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accledercheck/accLederCheckUpdatePage", method = RequestMethod.GET)
	
	public String accLederCheckUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccLederCheck accLederCheck = new AccLederCheck();
		//accLederCheck = accLederCheckService.queryAccLederCheckByCode(mapVo);
		mode.addAttribute("group_id", accLederCheck.getGroup_id());
		mode.addAttribute("hos_id", accLederCheck.getHos_id());
		mode.addAttribute("copy_code", accLederCheck.getCopy_code());
		mode.addAttribute("acc_year", accLederCheck.getAcc_year());
		mode.addAttribute("acc_month", accLederCheck.getAcc_month());
		mode.addAttribute("subj_code", accLederCheck.getSubj_code());
		mode.addAttribute("bal_os", accLederCheck.getBal_os());
		mode.addAttribute("this_od", accLederCheck.getThis_od());
		mode.addAttribute("this_oc", accLederCheck.getThis_oc());
		mode.addAttribute("sum_od", accLederCheck.getSum_od());
		mode.addAttribute("sum_oc", accLederCheck.getSum_oc());
		mode.addAttribute("end_os", accLederCheck.getEnd_os());
		mode.addAttribute("bal_os_w", accLederCheck.getBal_os_w());
		mode.addAttribute("this_od_w", accLederCheck.getThis_od_w());
		mode.addAttribute("this_oc_w", accLederCheck.getThis_oc_w());
		mode.addAttribute("sum_od_w", accLederCheck.getSum_od_w());
		mode.addAttribute("sum_oc_w", accLederCheck.getSum_oc_w());
		mode.addAttribute("end_os_w", accLederCheck.getEnd_os_w());
		mode.addAttribute("check1_id", accLederCheck.getCheck1_id());
		mode.addAttribute("check1_no", accLederCheck.getCheck1_no());
		mode.addAttribute("check2_id", accLederCheck.getCheck2_id());
		mode.addAttribute("check2_no", accLederCheck.getCheck2_no());
		mode.addAttribute("check3_id", accLederCheck.getCheck3_id());
		mode.addAttribute("check3_no", accLederCheck.getCheck3_no());
		mode.addAttribute("check4_id", accLederCheck.getCheck4_id());
		mode.addAttribute("check4_no", accLederCheck.getCheck4_no());
		mode.addAttribute("check5_id", accLederCheck.getCheck5_id());
		mode.addAttribute("check5_no", accLederCheck.getCheck5_no());
		mode.addAttribute("check6_id", accLederCheck.getCheck6_id());
		mode.addAttribute("check6_no", accLederCheck.getCheck6_no());
		mode.addAttribute("check7_id", accLederCheck.getCheck7_id());
		return "hrp/acc/accledercheck/accLederCheckUpdate";
	}
	/**
	*财务辅助账表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accledercheck/updateAccLederCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccLederCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
		String accLederCheckJson = "";//accLederCheckService.updateAccLederCheck(mapVo);
		
		return JSONObject.parseObject(accLederCheckJson);
	}
	/**
	*财务辅助账表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accledercheck/importAccLederCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccLederCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accLederCheckJson = accLederCheckService.importAccLederCheck(mapVo);
		
		return JSONObject.parseObject(accLederCheckJson);
	}

}

