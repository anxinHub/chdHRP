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
import com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount.AccSupAuxiliaryAccountServiceImpl;
@Controller
public class AccSupAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(AccSupAuxiliaryAccountController.class);
	
	@Resource(name = "accSupAuxiliaryAccountService")
	private final AccSupAuxiliaryAccountServiceImpl accSupAuxiliaryAccountService = null;
	
	@Resource(name = "hrpAccSelectService")
	private final HrpAccSelectService hrpAccSelectService = null;
	/**
	*项目科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/accSupGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accsupGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "供应商");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accsupauxiliaryaccount/accSupGeneralLedgerMain";
	}
	
	/**
	*项目科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/accSupDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSupDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "供应商");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accsupauxiliaryaccount/accSupDetailLedgerMain";
	}
	
	/**
	*科目项目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/accSubjSupGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjSupGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "供应商");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accsupauxiliaryaccount/accSubjSupGeneralLedgerMain";
	}
	
	/**
	*科目项目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/accSubjSupDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjSupDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "供应商");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accsupauxiliaryaccount/accSubjSupDetailLedgerMain";
	}
	
	/**
	*供应商余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/accSupEndOsMainPage", method = RequestMethod.GET)
	public String accSupEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "供应商");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accsupauxiliaryaccount/accSupEndOsMain";
	}
	
	/**
	 * 供应商科目总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/querySupSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accSupAuxiliaryAccountService.collectSupSubjGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 供应商科目明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/queryAccSupSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSupSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accSupAuxiliaryAccountService.collectAccSupSubjDetailLedger(getPage(mapVo));
		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 科目供应商总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/queryAccSubjSupGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjSupGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accSupAuxiliaryAccountService.collectAccSubjSupGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 科目供应商明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/queryAccSubjSupDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjSupDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accSupAuxiliaryAccountService.collectAccSubjSupDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 供应商余额表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsupauxiliaryaccount/querySupEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String results = accSupAuxiliaryAccountService.collectSupEndOs(getPage(mapVo));

		return JSONObject.parseObject(results);
	}
}
