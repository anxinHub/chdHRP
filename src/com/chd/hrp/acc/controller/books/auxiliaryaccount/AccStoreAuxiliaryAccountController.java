package com.chd.hrp.acc.controller.books.auxiliaryaccount;

import java.util.ArrayList;
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
import com.chd.hrp.acc.service.HrpAccSelectService;
import com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount.AccStoreAuxiliaryAccountServiceImpl;
@Controller
public class AccStoreAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(AccStoreAuxiliaryAccountController.class);
	
	@Resource(name = "accStoreAuxiliaryAccountService")
	private final AccStoreAuxiliaryAccountServiceImpl accStoreAuxiliaryAccountService = null;
	
	@Resource(name = "hrpAccSelectService")
	private final HrpAccSelectService hrpAccSelectService = null;
	
	/**
	*项目科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/accStoreGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accStoreGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "库房");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accstoreauxiliaryaccount/accStoreGeneralLedgerMain";
	}
	
	/**
	*项目科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/accStoreDetailLedgerMainPage", method = RequestMethod.GET)
	public String accStoreDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "库房");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accstoreauxiliaryaccount/accStoreDetailLedgerMain";
	}
	
	/**
	*科目项目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/accSubjStoreGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjStoreGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "库房");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accstoreauxiliaryaccount/accSubjStoreGeneralLedgerMain";
	}
	
	/**
	*科目项目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/accSubjStoreDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjStoreDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "库房");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accstoreauxiliaryaccount/accSubjStoreDetailLedgerMain";
	}
	
	/**
	*库房余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/accStoreEndOsMainPage", method = RequestMethod.GET)
	public String accStoreEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "库房");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accstoreauxiliaryaccount/accStoreEndOsMain";
	}
	
	/**
	 * 库房科目总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/queryStoreSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accStoreAuxiliaryAccountService.collectStoreSubjGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 库房科目明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/queryAccStoreSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccStoreSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accStoreAuxiliaryAccountService.collectAccStoreSubjDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 科目库房总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/queryAccSubjStoreGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjStoreGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accStoreAuxiliaryAccountService.collectAccSubjStoreGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 科目库房明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/queryAccSubjStoreDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjStoreDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accStoreAuxiliaryAccountService.collectAccSubjStoreDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 库房余额表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accstoreauxiliaryaccount/queryStoreEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String results = accStoreAuxiliaryAccountService.collectStoreEndOs(getPage(mapVo));

		return JSONObject.parseObject(results);
	}
}
