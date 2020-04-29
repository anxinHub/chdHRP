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
import com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount.AccEmpAuxiliaryAccountServiceImpl;
@Controller
public class AccEmpAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(AccEmpAuxiliaryAccountController.class);
	
	@Resource(name = "accEmpAuxiliaryAccountService")
	private final AccEmpAuxiliaryAccountServiceImpl accEmpAuxiliaryAccountService = null;
	
	@Resource(name = "hrpAccSelectService")
	private final HrpAccSelectService hrpAccSelectService = null;
	
	/**
	*项目科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/accEmpGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accEmpGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "职工");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accempauxiliaryaccount/accEmpGeneralLedgerMain";
	}
	
	/**
	*项目科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/accEmpDetailLedgerMainPage", method = RequestMethod.GET)
	public String accEmpDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "职工");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accempauxiliaryaccount/accEmpDetailLedgerMain";
	}
	
	/**
	*科目项目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/accSubjEmpGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjEmpGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "职工");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accempauxiliaryaccount/accSubjEmpGeneralLedgerMain";
	}
	
	/**
	*科目项目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/accSubjEmpDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjEmpDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "职工");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accempauxiliaryaccount/accSubjEmpDetailLedgerMain";
	}
	
	/**
	*职工余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/accEmpEndOsMainPage", method = RequestMethod.GET)
	public String accEmpEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "职工");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accempauxiliaryaccount/accEmpEndOsMain";
	}
	
	/**
	 * 职工科目总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/queryEmpSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accEmpAuxiliaryAccountService.collectEmpSubjGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 职工科目明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/queryAccEmpSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEmpSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accEmpAuxiliaryAccountService.collectAccEmpSubjDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	/**
	 * 科目职工总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/queryAccSubjEmpGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjEmpGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accEmpAuxiliaryAccountService.collectAccSubjEmpGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 科目职工明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/queryAccSubjEmpDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjEmpDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accEmpAuxiliaryAccountService.collectAccSubjEmpDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 职工余额表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accempauxiliaryaccount/queryEmpEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String results = accEmpAuxiliaryAccountService.collectEmpEndOs(getPage(mapVo));

		return JSONObject.parseObject(results);
	}
}
