package com.chd.hrp.acc.controller.books.auxiliaryaccount;

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
import com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount.AccDeptAuxiliaryAccountServiceImpl;
@Controller
public class AccDeptAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(AccDeptAuxiliaryAccountController.class);
	@Resource(name = "accDeptAuxiliaryAccountService")
	private final AccDeptAuxiliaryAccountServiceImpl accDeptAuxiliaryAccountService = null;
	
	@Resource(name = "hrpAccSelectService")
	private final HrpAccSelectService hrpAccSelectService = null;
	
	/**
	*部门科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/accDeptGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accDeptGeneralLedgerMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "部门");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		 
		
	
		return "hrp/acc/accauxiliaryaccount/accdeptauxiliaryaccount/accDeptGeneralLedgerMain";
	}
	
	/**
	*部门科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/accDeptDetailLedgerMainPage", method = RequestMethod.GET)
	public String accDeptDetailLedgerMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "部门");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));

		return "hrp/acc/accauxiliaryaccount/accdeptauxiliaryaccount/accDeptDetailLedgerMain";
	}
	
	/**
	*科目部门总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/accSubjDeptGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjDeptGeneralLedgerMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "部门");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));

		return "hrp/acc/accauxiliaryaccount/accdeptauxiliaryaccount/accSubjDeptGeneralLedgerMain";
	}
	
	/**
	*科目部门明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/accSubjDeptDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjDeptDetailLedgerMainPage(Model mode) throws Exception {
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "部门");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accauxiliaryaccount/accdeptauxiliaryaccount/accSubjDeptDetailLedgerMain";
	}
	
	/**
	*部门余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/accDeptEndOsMainPage", method = RequestMethod.GET)
	public String accDeptEndOsMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "部门");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
				
		mode.addAttribute("check_type_id",check_type_id);
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));

		return "hrp/acc/accauxiliaryaccount/accdeptauxiliaryaccount/accDeptEndOsMain";
	}
	//部门辅助账  部门科目总账
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/queryDeptSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accDeptAuxiliaryAccountService.collectDeptSubjGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	//部门辅助账  部门科目明细账
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/queryAccDeptSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccDeptSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accDeptAuxiliaryAccountService.collectAccDeptSubjDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 科目部门总账  查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/queryAccSubjDeptGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjDeptGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accDeptAuxiliaryAccountService.collectAccSubjDeptGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	//科目部门明细账
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/queryAccSubjDeptDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjDeptDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		//mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = accDeptAuxiliaryAccountService.collectAccSubjDeptDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 部门余额表查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accdeptauxiliaryaccount/queryDeptEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String results = accDeptAuxiliaryAccountService.collectAccDeptBalance(getPage(mapVo));

		return JSONObject.parseObject(results);
	}
}
