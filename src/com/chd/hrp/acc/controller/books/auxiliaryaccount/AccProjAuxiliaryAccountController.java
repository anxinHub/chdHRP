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
import com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount.AccProjAuxiliaryAccountServiceImpl;

@Controller
public class AccProjAuxiliaryAccountController extends BaseController {
	private static Logger logger = Logger.getLogger(AccProjAuxiliaryAccountController.class);

	@Resource(name = "accProjAuxiliaryAccountService")
	private final AccProjAuxiliaryAccountServiceImpl accProjAuxiliaryAccountService = null;
	
	@Resource(name = "hrpAccSelectService")
	private final HrpAccSelectService hrpAccSelectService = null;
	
	/**
	 * 项目科目总账<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/accProjGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accProjGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "项目");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accprojauxiliaryaccount/accProjGeneralLedgerMain";
	}

	/**
	 * 项目科目明细账<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/accProjDetailLedgerMainPage", method = RequestMethod.GET)
	public String accProjDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "项目");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accprojauxiliaryaccount/accProjDetailLedgerMain";
	}

	/**
	 * 科目项目总账<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/accSubjProjGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjProjGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "项目");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accprojauxiliaryaccount/accSubjProjGeneralLedgerMain";
	}

	/**
	 * 科目项目明细账<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/accSubjProjDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjProjDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "项目");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accprojauxiliaryaccount/accSubjProjDetailLedgerMain";
	}

	/**
	 * 项目余额表<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/accProjEndOsMainPage", method = RequestMethod.GET)
	public String accProjEndOsMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("check_type_name", "项目");
		
		String check_type_id =hrpAccSelectService.queryCheckTypeIdByCheckName(mapVo);
		mode.addAttribute("check_type_id",check_type_id);
		
		return "hrp/acc/accauxiliaryaccount/accprojauxiliaryaccount/accProjEndOsMain";
	}
	/**
	 * 项目科目总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/queryProjSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryProjSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accProjAuxiliaryAccountService.collectProjSubjGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 项目科目明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/queryAccProjSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccProjSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accProjAuxiliaryAccountService.collectAccProjSubjDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 科目项目总账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/queryAccSubjProjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjProjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accProjAuxiliaryAccountService.collectAccSubjProjGeneralLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 科目项目明细账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/queryAccSubjProjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjProjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accLederZcheck = accProjAuxiliaryAccountService.collectAccSubjProjDetailLedger(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 项目余额表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accprojauxiliaryaccount/queryProjEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryProjEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		String results = accProjAuxiliaryAccountService.collectProjEndOs(getPage(mapVo));

		return JSONObject.parseObject(results);
	}
}
