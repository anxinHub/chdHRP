package com.chd.hrp.acc.controller.books.auxiliaryaccount;

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
import com.chd.hrp.acc.serviceImpl.books.auxiliaryaccount.AccZcheckAuxiliaryAccountServiceImpl;
@Controller
public class AccZcheckAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(AccZcheckAuxiliaryAccountController.class);
	
	@Resource(name = "accZcheckAuxiliaryAccountService")
	private final AccZcheckAuxiliaryAccountServiceImpl accZcheckAuxiliaryAccountService = null;
	
	
	/**
	*自定义核算项科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accZcheckGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckGeneralLedgerMain";
	}
	
	/**
	*自定义核算项科目总账<BR>
	*查询数据
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/collectAccZcheckGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccZcheckGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result=accZcheckAuxiliaryAccountService.collectAccZcheckGeneralLedger(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义核算项科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckDetailLedgerMainPage", method = RequestMethod.GET)
	public String accZcheckDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckDetailLedgerMain";
	}
	
	/**
	*核算项科目明细账
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/collectAccZcheckDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccZcheckDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= accZcheckAuxiliaryAccountService.collectAccZcheckDetailLedger(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义科目核算项总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accSubjZcheckGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjZcheckGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accSubjZcheckGeneralLedgerMain";
	}
	
	/**
	*科目核算项总账
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/collectAccSubjZcheckGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccSubjZcheckGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= accZcheckAuxiliaryAccountService.collectAccSubjZcheckGeneralLedger(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义科目核算项明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accSubjZcheckDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjZcheckDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accSubjZcheckDetailLedgerMain";
	}
	
	/**
	*科目核算项明细账
	*
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/collectAccSubjZcheckDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccSubjZcheckDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= accZcheckAuxiliaryAccountService.collectAccSubjZcheckDetailLedger(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义核算项余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckEndOsMainPage", method = RequestMethod.GET)
	public String accZcheckEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckEndOsMain";
	}
	/**
	*自定义科目核算项余额表<BR>
	*查询数据
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/collectAccSubjZcheckEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccSubjZcheckEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= accZcheckAuxiliaryAccountService.collectAccZcheckEndOs(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义核算项科目明细账查询条件<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accSubjZcheckDetailLedgerWhere", method = RequestMethod.GET)
	public String accSubjZcheckDetailLedgerWhere(Model mode) throws Exception {
		
		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckGeneralLedgerWhere";
	}
	/**
	*自定义核算项科目明细账查询条件<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/queryAccZcheckDetailMain", method = RequestMethod.GET)
	public String queryAccZcheckDetailMain(Model mode) throws Exception {
		
		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckLedgerWhere";
	}
	
	// 部门
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accCheckDeptPage", method = RequestMethod.GET)
	public String accCheckDeptPage(Model mode, String check_type_id) throws Exception {
		mode.addAttribute("check_type_id", check_type_id);
		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckDept";
	}
		
	// 部门
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckDeptPage", method = RequestMethod.GET)
	public String accZcheckDeptPage(Model mode, String check_type_id) throws Exception {
		mode.addAttribute("check_type_id", 1);
		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckDept";
	}

	// 职工
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckEmpPage", method = RequestMethod.GET)
	public String accZcheckEmpPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckEmp";

	}

	// 项目
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckProjPage", method = RequestMethod.GET)
	public String accZcheckProjPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckProj";

	}

	// 供应商
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckSupPage", method = RequestMethod.GET)
	public String accZcheckSupPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckSup";

	}

	// 客戶
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckCusPage", method = RequestMethod.GET)
	public String accZcheckCusPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckCus";

	}

	// 库房
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckStorePage", method = RequestMethod.GET)
	public String accZcheckStorePage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckStore";

	}

	//资金来源
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZcheckSourcePage", method = RequestMethod.GET)
	public String accZcheckSourcePage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckSource";

	}
	
	//资金来源
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accCheckHosInfoPage", method = RequestMethod.GET)
	public String accCheckHosInfoPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZcheckHosInfo";

	}
	
	//自定义核算项
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accCheckItemMainPage", method = RequestMethod.GET)
	public String accCheckItemMainPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accCheckItemMain";

	}
	
	//自定义核算项
	@RequestMapping(value = "/hrp/acc/acczcheckauxiliaryaccount/accZCheckItemCheckPage", method = RequestMethod.GET)

	public String accZCheckItemCheckPage(Model mode, String check_type_id,String check_type_name) throws Exception {
        
		mode.addAttribute("check_type_id", check_type_id);	
		mode.addAttribute("check_type_name", check_type_name);

		return "hrp/acc/accauxiliaryaccount/acczcheckauxiliaryaccount/accZCheckItemCheck";

	}
}
