package com.chd.hrp.ass.controller.balance;
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
import com.chd.hrp.ass.service.balance.AssBalanceGeneralService;
import com.chd.hrp.ass.service.balance.AssBalanceHouseService;
import com.chd.hrp.ass.service.balance.AssBalanceOtherService;
import com.chd.hrp.ass.service.balance.AssBalanceSpecialService;

@Controller
public class AssBalanceController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssBalanceController.class);
	
	
	@Resource(name = "assBalanceSpecialService")
	private final  AssBalanceSpecialService assBalanceSpecialService = null;
	
	@Resource(name = "assBalanceGeneralService")
	private final  AssBalanceGeneralService assBalanceGeneralService = null;
	
	@Resource(name = "assBalanceHouseService")
	private final  AssBalanceHouseService assBalanceHouseService = null;
	
	@Resource(name = "assBalanceOtherService")
	private final  AssBalanceOtherService assBalanceOtherService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assbalance/assBalanceAccountMainPage", method = RequestMethod.GET)
	public String assAllHosAssDistributionPage(Model mode) throws Exception {

		return "hrp/ass/assbalance/assBalanceAccountMain";
	}

	
	/**
	 * @Description 查询数据 050701 资产入库主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbalance/assBalanceAccountMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> assBalanceAccountMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		
		String ass_nature  = (String)mapVo.get("ass_nature");
		
		String assJson= "";
		
		if(("01").equals(ass_nature)){
			
			 assJson = assBalanceSpecialService.queryBalanceAccountSpecialMain(getPage(mapVo));
			
			return JSONObject.parseObject(assJson);
			
		}else if(("02").equals(ass_nature)){
			
			 assJson = assBalanceGeneralService.queryBalanceAccountGeneralMain(getPage(mapVo));
			
		}else if(("03").equals(ass_nature)){
			
			 assJson = assBalanceHouseService.queryBalanceAccountHouseMain(getPage(mapVo));
			
		}else if(("04").equals(ass_nature)){
			
			 assJson = assBalanceOtherService.queryBalanceAccountOtherMain(getPage(mapVo));
			
		}
		
		
          return JSONObject.parseObject(assJson);
	}
	
	
	
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assbalance/assBalanceAccountDetailPage", method = RequestMethod.GET)
	public String assBalanceAccountDetailPage(Model mode) throws Exception {

		return "hrp/ass/assbalance/assBalanceAccountDetail";
	}
	
	
	/**
	 * @Description 查询数据 050701 资产入库主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbalance/assBalanceAccountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> assBalanceAccountDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		
		String ass_nature  = (String)mapVo.get("ass_nature");
		
		String assJson= "";
		
		if(("01").equals(ass_nature)){
			
			 assJson = assBalanceSpecialService.queryBalanceAccountSpecialDetail(getPage(mapVo));
			
			return JSONObject.parseObject(assJson);
			
		}else if(("02").equals(ass_nature)){
			
			 assJson = assBalanceGeneralService.queryBalanceAccountGeneralDetail(getPage(mapVo));
			
		}else if(("03").equals(ass_nature)){
			
			 assJson = assBalanceHouseService.queryBalanceAccountHouseDetail(getPage(mapVo));
			
		}else if(("04").equals(ass_nature)){
			
			 assJson = assBalanceOtherService.queryBalanceAccountOtherDetail(getPage(mapVo));
			
		}
		
		
          return JSONObject.parseObject(assJson);
	}
	
}
