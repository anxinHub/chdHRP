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
import com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount.AccHosAuxiliaryAccountServiceImpl;
@Controller
public class AccHosAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(AccHosAuxiliaryAccountController.class);
	
	@Resource(name = "accHosAuxiliaryAccountService")
	private final AccHosAuxiliaryAccountServiceImpl accHosAuxiliaryAccountService = null;
	
	@Resource(name = "hrpAccSelectService")
	private final HrpAccSelectService hrpAccSelectService = null;
	
	/**
	*单位科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/accHosGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accHosGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "单位");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acchosauxiliaryaccount/accHosGeneralLedgerMain";
	}
	
	/**
	*单位科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/accHosDetailLedgerMainPage", method = RequestMethod.GET)
	public String accHosDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "单位");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acchosauxiliaryaccount/accHosDetailLedgerMain";
	}
	
	/**
	*科目单位总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/accSubjHosGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjHosGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "单位");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acchosauxiliaryaccount/accSubjHosGeneralLedgerMain";
	}
	
	/**
	*科目单位明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/accSubjHosDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjHosDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "单位");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acchosauxiliaryaccount/accSubjHosDetailLedgerMain";
	}
	
	/**
	*单位余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/accHosEndOsMainPage", method = RequestMethod.GET)
	public String accStoreEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "单位");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/acchosauxiliaryaccount/accHosEndOsMain";
	}
	
	/**
	 * 单位科目总账 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/queryHosSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accHosAuxiliaryAccountService.collectHosSubjGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 单位科目明细账  
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/queryAccHosSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccHosSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accHosAuxiliaryAccountService.collectAccHosSubjDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 科目单位总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/queryAccSubjHosGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjHosGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accHosAuxiliaryAccountService.collectAccSubjHosGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 科目单位明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/queryAccSubjHosDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjHosDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accHosAuxiliaryAccountService.collectAccSubjHosDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	/**
	 * 单位余额表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acchosauxiliaryaccount/queryHosEndOs", method = RequestMethod.POST)
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
		String results = accHosAuxiliaryAccountService.collectHosEndOs(getPage(mapVo));

		return JSONObject.parseObject(results);
	}
}
