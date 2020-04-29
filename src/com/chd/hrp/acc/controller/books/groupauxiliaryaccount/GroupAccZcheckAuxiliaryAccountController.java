package com.chd.hrp.acc.controller.books.groupauxiliaryaccount;

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
import com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount.GroupAccZcheckAuxiliaryAccountServiceImpl;
@Controller
public class GroupAccZcheckAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccZcheckAuxiliaryAccountController.class);
	
	@Resource(name = "groupAccZcheckAuxiliaryAccountService")
	private final GroupAccZcheckAuxiliaryAccountServiceImpl groupAccZcheckAuxiliaryAccountService = null;
	
	
	/**
	*自定义核算项科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccZcheckGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckGeneralLedgerMain";
	}
	
	/**
	*自定义核算项科目总账<BR>
	*查询数据
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/collectGroupAccZcheckGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupAccZcheckGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result=groupAccZcheckAuxiliaryAccountService.collectGroupAccZcheckGeneralLedger(mapVo);
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义核算项科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccZcheckDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckDetailLedgerMain";
	}
	
	/**
	*自定义核算项科目明细账<BR>
	*查询数据
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/collectGroupAccZcheckDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupAccZcheckDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= groupAccZcheckAuxiliaryAccountService.collectGroupAccZcheckDetailLedger(mapVo);
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义科目核算项总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccSubjZcheckGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjZcheckGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccSubjZcheckGeneralLedgerMain";
	}
	
	/**
	*自定义科目核算项总账<BR>
	*查询数据
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/collectGroupAccSubjZcheckGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupAccSubjZcheckGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= groupAccZcheckAuxiliaryAccountService.collectGroupAccSubjZcheckGeneralLedger(mapVo);
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义科目核算项明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccSubjZcheckDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjZcheckDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccSubjZcheckDetailLedgerMain";
	}
	
	/**
	*自定义科目核算项明细账<BR>
	*查询数据
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/collectGroupAccSubjZcheckDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupAccSubjZcheckDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= groupAccZcheckAuxiliaryAccountService.collectGroupAccSubjZcheckDetailLedger(mapVo);
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义核算项余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckEndOsMainPage", method = RequestMethod.GET)
	public String groupAccZcheckEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckEndOsMain";
	}
	/**
	*自定义科目核算项余额表<BR>
	*查询数据
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/collectGroupAccSubjZcheckEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupAccSubjZcheckEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= groupAccZcheckAuxiliaryAccountService.collectGroupAccZcheckEndOs(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	/**
	*自定义核算项科目明细账查询条件<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccSubjZcheckDetailLedgerWhere", method = RequestMethod.GET)
	public String groupAccSubjZcheckDetailLedgerWhere(Model mode) throws Exception {
		
		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckGeneralLedgerWhere";
	}
	/**
	*自定义核算项科目明细账查询条件<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/queryGroupAccZcheckDetailMain", method = RequestMethod.GET)
	public String queryGroupAccZcheckDetailMain(Model mode) throws Exception {
		
		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckLedgerWhere";
	}
	
	// 部门
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccCheckDeptPage", method = RequestMethod.GET)
	public String groupAccCheckDeptPage(Model mode, String check_type_id) throws Exception {
		mode.addAttribute("check_type_id", check_type_id);
		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckDept";
	}
		
	// 部门
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckDeptPage", method = RequestMethod.GET)
	public String groupAccZcheckDeptPage(Model mode, String check_type_id) throws Exception {
		mode.addAttribute("check_type_id", 1);
		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckDept";
	}

	// 职工
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckEmpPage", method = RequestMethod.GET)
	public String groupAccZcheckEmpPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckEmp";

	}

	// 项目
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckProjPage", method = RequestMethod.GET)
	public String groupAccZcheckProjPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckProj";

	}

	// 供应商
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckSupPage", method = RequestMethod.GET)
	public String groupAccZcheckSupPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckSup";

	}

	// 客戶
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckCusPage", method = RequestMethod.GET)
	public String groupAccZcheckCusPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckCus";

	}

	// 库房
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckStorePage", method = RequestMethod.GET)
	public String groupAccZcheckStorePage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckStore";

	}

	//资金来源
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckSourcePage", method = RequestMethod.GET)
	public String groupAccZcheckSourcePage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckSource";

	}
	
	//资金来源
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccCheckHosInfoPage", method = RequestMethod.GET)
	public String groupAccCheckHosInfoPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZcheckHosInfo";

	}
	
	//自定义核算项
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccCheckItemMainPage", method = RequestMethod.GET)
	public String groupAccCheckItemMainPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccCheckItemMain";

	}
	
	//自定义核算项
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZCheckItemCheckPage", method = RequestMethod.GET)

	public String groupAccZCheckItemCheckPage(Model mode, String check_type_id,String check_type_name) throws Exception {
        
		mode.addAttribute("check_type_id", check_type_id);	
		mode.addAttribute("check_type_name", check_type_name);

		return "hrp/acc/groupaccauxiliaryaccount/groupacczcheckauxiliaryaccount/groupAccZCheckItemCheck";

	}
}
