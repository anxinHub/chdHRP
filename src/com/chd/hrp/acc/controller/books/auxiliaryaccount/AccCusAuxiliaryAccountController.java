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
import com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount.AccCusAuxiliaryAccountServiceImpl;
@Controller
public class AccCusAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCusAuxiliaryAccountController.class);
	@Resource(name = "accCusAuxiliaryAccountService")
	private final AccCusAuxiliaryAccountServiceImpl accCusAuxiliaryAccountService = null;
	
	@Resource(name = "hrpAccSelectService")
	private final HrpAccSelectService hrpAccSelectService = null;
	
	/**
	*客户科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/accCusGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accCusGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "客户");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acccusauxiliaryaccount/accCusGeneralLedgerMain";
	}
	
	/**
	*客户科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/accCusDetailLedgerMainPage", method = RequestMethod.GET)
	public String accCusDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "客户");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acccusauxiliaryaccount/accCusDetailLedgerMain";
	}
	
	/**
	*科目客户总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/accSubjCusGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjCusGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "客户");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acccusauxiliaryaccount/accSubjCusGeneralLedgerMain";
	}
	
	/**
	*科目客户明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/accSubjCusDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjCusDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "客户");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acccusauxiliaryaccount/accSubjCusDetailLedgerMain";
	}
	
	/**
	*客户余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/accCusEndOsMainPage", method = RequestMethod.GET)
	public String accCusEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "客户");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acccusauxiliaryaccount/accCusEndOsMain";
	}
	/**
	 * 客户科目总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/queryCusSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCusSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accCusAuxiliaryAccountService.collectCusSubjGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 客户科目明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/queryAccCusSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCusSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accCusAuxiliaryAccountService.collectAccCusSubjDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 科目客户总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/queryAccSubjCusGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjCusGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accCusAuxiliaryAccountService.collectAccSubjCusGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 科目客户明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/queryAccSubjCusDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjCusDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accCusAuxiliaryAccountService.collectAccSubjCusDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 客户余额表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acccusauxiliaryaccount/queryCusEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCusEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String results = accCusAuxiliaryAccountService.collectCusEndOs(getPage(mapVo));

		return JSONObject.parseObject(results);
	}
}
