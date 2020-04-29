/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller.commonbuilder;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccCheckItem;
import com.chd.hrp.acc.entity.AccCheckItemType;
import com.chd.hrp.acc.entity.AccCusAttr;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.entity.AccEmpAttr;
import com.chd.hrp.acc.entity.AccProjAttr;
import com.chd.hrp.acc.entity.AccStoreAttr;
import com.chd.hrp.acc.entity.AccSupAttr;
import com.chd.hrp.acc.service.commonbuilder.AccCheckItemTypeService;
import com.chd.hrp.acc.serviceImpl.AccCusAttrServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccDeptAttrServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccEmpAttrServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccProjAttrServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccStoreAttrServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccSupAttrServiceImpl;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCheckItemServiceImpl;
import com.chd.hrp.sys.entity.Source;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.HrpSysSelectServiceImpl;
import com.chd.hrp.sys.serviceImpl.SourceServiceImpl;

/**
 * @Title. @Description. 核算项
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class AccCheckItemController extends BaseController {
	private static Logger logger = Logger.getLogger(AccCheckItemController.class);

	@Resource(name = "accCheckItemService")
	private final AccCheckItemServiceImpl accCheckItemService = null;
	
	@Resource(name = "accDeptAttrService")
	private final AccDeptAttrServiceImpl accDeptAttrService = null;
	
	@Resource(name = "accEmpAttrService")
	private final AccEmpAttrServiceImpl accEmpAttrService = null;
	
	@Resource(name = "accStoreAttrService")
	private final AccStoreAttrServiceImpl accStoreAttrService = null;
	
	@Resource(name = "accProjAttrService")
	private final AccProjAttrServiceImpl accProjAttrService = null;
	
	@Resource(name = "sourceService")
	private final SourceServiceImpl sourceService = null;
	
	@Resource(name = "accCusAttrService")
	private final AccCusAttrServiceImpl accCusAttrService = null;
	
	@Resource(name = "accSupAttrService")
	private final AccSupAttrServiceImpl accSupAttrService = null;

	@Resource(name = "hrpSysSelectService")
	private final HrpSysSelectServiceImpl hrpSysSelectService = null;
	
	@Resource(name = "accCheckItemTypeService")
	private final AccCheckItemTypeService accCheckItemTypeService = null;
	
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	/**
	 * 核算项<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckItemMainPage", method = RequestMethod.GET)
	public String accCheckItemMainPage(Model mode, String check_type_id, String check_type_name) throws Exception {
		mode.addAttribute("check_type_id", check_type_id);
		mode.addAttribute("check_type_name", check_type_name);
		return "hrp/acc/acccheckitem/accCheckItemMain";

	}
	// 部门
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckDeptPage", method = RequestMethod.GET)
	public String accCheckDeptPage(Model mode, String check_type_id) throws Exception {
		mode.addAttribute("check_type_id", check_type_id);
		return "hrp/acc/acccheckitem/accCheckDept";
	}
	
	// 部门
	@RequestMapping(value = "/hrp/acc/acccheckitem/costCheckDeptPage", method = RequestMethod.GET)
	public String costCheckDeptPage(Model mode, String check_type_id) throws Exception {
		mode.addAttribute("check_type_id", 1);
		return "hrp/acc/acccheckitem/accCheckDept";
	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckDeptBatchUpdatePage", method = RequestMethod.GET)
	public String accCheckDeptBatchUpdatePage(String param,Model mode) throws Exception {
		
		mode.addAttribute("dept_ids", param.toString().substring(0, param.length()-1));
		
		return "hrp/acc/acccheckitem/accCheckDeptBatchUpdate";
	}

	// 职工
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckEmpPage", method = RequestMethod.GET)
	public String accCheckEmpPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/acccheckitem/accCheckEmp";

	}
	
	// 职工设置自定义属性
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckEmpSetPage", method = RequestMethod.GET)
	public String accCheckEmpSetPage(@RequestParam Map<String, Object> mapVo , Model mode) throws Exception {

		return "hrp/acc/acccheckitem/accCheckEmpSet";

	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckEmpSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckEmpSet(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		
		String matDura = accCheckItemService.queryAccCheckEmpSet(getPage(mapVo));
		
		return JSONObject.parseObject(matDura);
	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/saveAccCheckEmpSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccCheckEmpSet(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String matJson;
		try {
			
			matJson = accCheckItemService.saveAccCheckEmpSet(mapVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}

		return JSONObject.parseObject(matJson);
	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/deleteAccCheckEmpSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCheckEmpSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		String matJson;
		try {
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("old_code", ids[2]);
				listVo.add(mapVo);
			}
			
			matJson = accCheckItemService.deleteAccCheckEmpSet(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}

	// 项目
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckProjPage", method = RequestMethod.GET)
	public String accCheckProjPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/acccheckitem/accCheckProj";

	}

	// 供应商
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckSupPage", method = RequestMethod.GET)
	public String accCheckSupPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/acccheckitem/accCheckSup";

	}

	// 客戶
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckCusPage", method = RequestMethod.GET)
	public String accCheckCusPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/acccheckitem/accCheckCus";

	}

	// 库房
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckStorePage", method = RequestMethod.GET)
	public String accCheckStorePage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/acccheckitem/accCheckStore";

	}

	//资金来源
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckSourcePage", method = RequestMethod.GET)
	public String accCheckSourcePage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/acccheckitem/accCheckSource";

	}
	
	//单位
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckHosInfoPage", method = RequestMethod.GET)
	public String accCheckHosInfoPage(Model mode, String check_type_id) throws Exception {

		mode.addAttribute("check_type_id", check_type_id);

		return "hrp/acc/acccheckitem/accCheckHosInfo";

	}

	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckDeptUpdatePage", method = RequestMethod.GET)
	public String accCheckDeptUpdatePage(Model mode,@RequestParam Map<String, Object> mapVo) throws Exception {
		String result = "";
		AccDeptAttr accDeptAttr = accDeptAttrService.queryDeptByCode(mapVo);
		if(accDeptAttr != null){
			mode.addAttribute("group_id", accDeptAttr.getGroup_id());
			mode.addAttribute("hos_id", accDeptAttr.getHos_id());
			mode.addAttribute("dept_id", accDeptAttr.getDept_id());
			mode.addAttribute("dept_code", accDeptAttr.getDept_code());
			mode.addAttribute("dept_name", accDeptAttr.getDept_name());
			mode.addAttribute("super_code", accDeptAttr.getSuper_code());
			mode.addAttribute("super_name", accDeptAttr.getSuper_name());
			mode.addAttribute("kind_code", accDeptAttr.getKind_code());
			mode.addAttribute("kind_name", accDeptAttr.getKind_name());
			mode.addAttribute("type_code", accDeptAttr.getType_code());
			mode.addAttribute("natur_code", accDeptAttr.getNatur_code());
			mode.addAttribute("out_code", accDeptAttr.getOut_code());
			mode.addAttribute("emp_id", accDeptAttr.getEmp_id());
			mode.addAttribute("type_name", accDeptAttr.getType_name());
			mode.addAttribute("natur_name", accDeptAttr.getNatur_name());
			mode.addAttribute("out_name", accDeptAttr.getOut_name());
			mode.addAttribute("emp_name", accDeptAttr.getEmp_name());
			mode.addAttribute("emp_code", accDeptAttr.getEmp_code());
			mode.addAttribute("is_manager", accDeptAttr.getIs_manager());
			mode.addAttribute("is_stock", accDeptAttr.getIs_stock());
			mode.addAttribute("is_stop", accDeptAttr.getIs_stop());
			mode.addAttribute("is_disable", accDeptAttr.getIs_disable());
			result = "hrp/acc/acccheckitem/accCheckDeptUpdate";
		}else{
			accDeptAttr = accDeptAttrService.queryHosDeptByCode(mapVo);
			mode.addAttribute("group_id", accDeptAttr.getGroup_id());
			mode.addAttribute("hos_id", accDeptAttr.getHos_id());
			mode.addAttribute("dept_id", accDeptAttr.getDept_id());
			mode.addAttribute("dept_code", accDeptAttr.getDept_code());
			mode.addAttribute("dept_name", accDeptAttr.getDept_name());
			mode.addAttribute("super_code", accDeptAttr.getSuper_code());
			mode.addAttribute("super_name", accDeptAttr.getSuper_name());
			mode.addAttribute("kind_code", accDeptAttr.getKind_code());
			mode.addAttribute("kind_name", accDeptAttr.getKind_name());
			result = "hrp/acc/acccheckitem/accCheckDeptAdd";
		}
		
		return result;

	}

	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckProjUpdatePage", method = RequestMethod.GET)
	public String accCheckProjUpdatePage(Model mode,@RequestParam Map<String, Object> mapVo) throws Exception {
		
		String result = "";
		
		if(mapVo.isEmpty()){
				
				result = "hrp/acc/acccheckitem/accCheckProjAdd";
				
		}else{
			
			AccProjAttr accProjAttr = accProjAttrService.queryProjByCode(mapVo);
			if(accProjAttr != null){
				mode.addAttribute("proj_id", accProjAttr.getProj_id());
				mode.addAttribute("group_id", accProjAttr.getGroup_id());
				mode.addAttribute("hos_id", accProjAttr.getHos_id());
				mode.addAttribute("proj_code", accProjAttr.getProj_code());
				mode.addAttribute("type_code", accProjAttr.getType_code());
				mode.addAttribute("type_name", accProjAttr.getType_name());
				mode.addAttribute("proj_name", accProjAttr.getProj_name());
				mode.addAttribute("proj_simple", accProjAttr.getProj_simple());
				mode.addAttribute("is_stop", accProjAttr.getIs_stop());
				mode.addAttribute("is_disable", accProjAttr.getIs_disable());
				mode.addAttribute("level_code", accProjAttr.getLevel_code());
				mode.addAttribute("use_code", accProjAttr.getUse_code());
				mode.addAttribute("con_emp_id", accProjAttr.getCon_emp_id());
				mode.addAttribute("level_name", accProjAttr.getLevel_name());
				mode.addAttribute("use_name", accProjAttr.getUse_name());
				mode.addAttribute("con_emp_name", accProjAttr.getCon_emp_name());
				mode.addAttribute("con_phone", accProjAttr.getCon_phone());
				mode.addAttribute("acc_emp_id", accProjAttr.getAcc_emp_id());
				mode.addAttribute("acc_emp_name", accProjAttr.getAcc_emp_name());
				mode.addAttribute("acc_phone", accProjAttr.getAcc_phone());
				mode.addAttribute("app_emp_id", accProjAttr.getApp_emp_id());
				mode.addAttribute("app_emp_name", accProjAttr.getApp_emp_name());
				mode.addAttribute("dept_id", accProjAttr.getDept_id());
				mode.addAttribute("dept_name", accProjAttr.getDept_name());
				mode.addAttribute("app_date", DateUtil.dateToString(accProjAttr.getApp_date(), "yyyy-MM-dd"));
				mode.addAttribute("app_phone", accProjAttr.getApp_phone());
				mode.addAttribute("email", accProjAttr.getEmail());
				mode.addAttribute("note", accProjAttr.getNote());
				result = "hrp/acc/acccheckitem/accCheckProjUpdate";
			}
			
		}
		
		return result;

	}

	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckEmpUpdatePage", method = RequestMethod.GET)
	public String accCheckEmpUpdatePage(Model mode,@RequestParam Map<String, Object> mapVo) throws Exception {
		
		AccEmpAttr accEmpAttr = accEmpAttrService.queryEmpByCode(mapVo);
		if(accEmpAttr != null){
			mode.addAttribute("group_id", accEmpAttr.getGroup_id());
			mode.addAttribute("hos_id", accEmpAttr.getHos_id());
			mode.addAttribute("emp_id", accEmpAttr.getEmp_id());
			mode.addAttribute("emp_no", mapVo.get("emp_no"));
			mode.addAttribute("emp_code", accEmpAttr.getEmp_code());
			mode.addAttribute("emp_name", accEmpAttr.getEmp_name());
			mode.addAttribute("dept_id", accEmpAttr.getDept_id());
			mode.addAttribute("dept_name", accEmpAttr.getDept_name());
			mode.addAttribute("kind_code", accEmpAttr.getKind_code());
			mode.addAttribute("kind_name", accEmpAttr.getKind_name());
			mode.addAttribute("is_stop", accEmpAttr.getIs_stop());
			mode.addAttribute("is_disable", accEmpAttr.getIs_disable());
			mode.addAttribute("sex", accEmpAttr.getSex());
			mode.addAttribute("is_pay", accEmpAttr.getIs_pay());
			mode.addAttribute("is_buyer", accEmpAttr.getIs_buyer());
			mode.addAttribute("pay_type_code", accEmpAttr.getPay_type_code());
			mode.addAttribute("pay_type_name", accEmpAttr.getPay_type_name());
			mode.addAttribute("station_code", accEmpAttr.getStation_code());
			mode.addAttribute("station_name", accEmpAttr.getStation_name());
			mode.addAttribute("duty_code", accEmpAttr.getDuty_code());
			mode.addAttribute("duty_name", accEmpAttr.getDuty_name());
			mode.addAttribute("id_number", accEmpAttr.getId_number());
			mode.addAttribute("phone", accEmpAttr.getPhone());
			mode.addAttribute("mobile", accEmpAttr.getMobile());
			mode.addAttribute("email", accEmpAttr.getEmail());
			mode.addAttribute("note", accEmpAttr.getNote());
			mode.addAttribute("attr_code", accEmpAttr.getAttr_code());
			mode.addAttribute("attr_name", accEmpAttr.getAttr_name());
			return "hrp/acc/acccheckitem/accCheckEmpUpdate";
		}else{
			accEmpAttr = accEmpAttrService.queryHosEmpByCode(mapVo);
			mode.addAttribute("group_id", accEmpAttr.getGroup_id());
			mode.addAttribute("hos_id", accEmpAttr.getHos_id());
			mode.addAttribute("emp_id", accEmpAttr.getEmp_id());
			mode.addAttribute("emp_no", accEmpAttr.getEmp_no());
			mode.addAttribute("emp_code", accEmpAttr.getEmp_code());
			mode.addAttribute("emp_name", accEmpAttr.getEmp_name());
			mode.addAttribute("dept_id", accEmpAttr.getDept_id());
			mode.addAttribute("dept_name", accEmpAttr.getDept_name());
			mode.addAttribute("kind_code", accEmpAttr.getKind_code());
			mode.addAttribute("kind_name", accEmpAttr.getKind_name());
			mode.addAttribute("is_stop", accEmpAttr.getIs_stop());
			return "hrp/acc/acccheckitem/accCheckEmpAdd";
		}
		

	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckCusUpdatePage", method = RequestMethod.GET)
	public String accCheckCusUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String result = "";
		
		if(!mapVo.containsKey("cus_id")){
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			Map<String, Map<String, Object>> map = sysBaseService.getHosRulesList(mapVo);
			if(map.containsKey("HOS_CUS")){
				mode.addAttribute("is_auto", map.get("HOS_CUS").get("is_auto"));
			}else{
				mode.addAttribute("is_auto", 0);
			}
			result = "hrp/acc/acccheckitem/accCheckCusAdd";
			
		}else{
			
			AccCusAttr accCusAttr = accCusAttrService.queryCusByCode(mapVo);
				if(accCusAttr != null){
					mode.addAttribute("group_id", accCusAttr.getGroup_id());
					mode.addAttribute("hos_id", accCusAttr.getHos_id());
					mode.addAttribute("cus_id", accCusAttr.getCus_id());
					mode.addAttribute("cus_code", accCusAttr.getCus_code());
					mode.addAttribute("cus_name", accCusAttr.getCus_name());
					mode.addAttribute("type_code", accCusAttr.getType_code());
					mode.addAttribute("type_name", accCusAttr.getType_name());
					mode.addAttribute("spell_code", accCusAttr.getSpell_code());
					mode.addAttribute("wbx_code", accCusAttr.getWbx_code());
					mode.addAttribute("is_stop", accCusAttr.getIs_stop());
					mode.addAttribute("is_disable", accCusAttr.getIs_disable());
					mode.addAttribute("bank_name", accCusAttr.getBank_name());
					mode.addAttribute("bank_number", accCusAttr.getBank_number());
					mode.addAttribute("legal", accCusAttr.getLegal());
					mode.addAttribute("regis_no", accCusAttr.getRegis_no());
					mode.addAttribute("phone", accCusAttr.getPhone());
					mode.addAttribute("mobile", accCusAttr.getMobile());
					mode.addAttribute("contact", accCusAttr.getContact());
					mode.addAttribute("fax", accCusAttr.getMobile());
					mode.addAttribute("email", accCusAttr.getEmail());
					mode.addAttribute("region", accCusAttr.getRegion());
					mode.addAttribute("zip_code", accCusAttr.getZip_code());
					mode.addAttribute("note", accCusAttr.getNote());
					mode.addAttribute("address", accCusAttr.getAddress());
					mode.addAttribute("is_mat", accCusAttr.getIs_mat());
					mode.addAttribute("is_med", accCusAttr.getIs_med());
					mode.addAttribute("is_ass", accCusAttr.getIs_ass());
					mode.addAttribute("is_sup", accCusAttr.getIs_sup());
					mode.addAttribute("aff_code", accCusAttr.getAff_code());
					mode.addAttribute("inst_code", accCusAttr.getInst_code());
					mode.addAttribute("dang_code", accCusAttr.getDang_code());
					mode.addAttribute("tmop_code", accCusAttr.getTmop_code());
					mode.addAttribute("zhagnqi_code", accCusAttr.getRange_id());
					return "hrp/acc/acccheckitem/accCheckCusUpdate";
				}

		}
		return result ;

	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckStoreUpdatePage", method = RequestMethod.GET)
	public String accCheckStoreUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		AccStoreAttr accStoreAttr = accStoreAttrService.queryStoreByCode(mapVo);
		if(accStoreAttr != null){
			mode.addAttribute("group_id", accStoreAttr.getGroup_id());
			mode.addAttribute("hos_id", accStoreAttr.getHos_id());
			mode.addAttribute("store_id", accStoreAttr.getStore_id());
			mode.addAttribute("store_code", accStoreAttr.getStore_code());
			mode.addAttribute("store_name", accStoreAttr.getStore_name());
			mode.addAttribute("type_code", accStoreAttr.getType_code());
			mode.addAttribute("type_name", accStoreAttr.getType_name());
			mode.addAttribute("spell_code", accStoreAttr.getSpell_code());
			mode.addAttribute("wbx_code", accStoreAttr.getWbx_code());
			mode.addAttribute("is_stop", accStoreAttr.getIs_stop());
			mode.addAttribute("is_disable", accStoreAttr.getIs_disable());
			mode.addAttribute("dept_id", accStoreAttr.getDept_id());
			mode.addAttribute("dept_no", accStoreAttr.getDept_no());
			mode.addAttribute("dept_code", accStoreAttr.getDept_code());
			mode.addAttribute("dept_name", accStoreAttr.getDept_name());
			mode.addAttribute("is_proc", accStoreAttr.getIs_proc());
			mode.addAttribute("head_emp_name", accStoreAttr.getHead_emp_name());
			mode.addAttribute("head_emp_id", accStoreAttr.getHead_emp_id());
			mode.addAttribute("mobile", accStoreAttr.getMobile());
			mode.addAttribute("acc_emp_id", accStoreAttr.getAcc_emp_id());
			mode.addAttribute("acc_emp_name", accStoreAttr.getAcc_emp_name());
			mode.addAttribute("safe_emp_id", accStoreAttr.getSafe_emp_id());
			mode.addAttribute("safe_emp_name", accStoreAttr.getSafe_emp_name());
			mode.addAttribute("proc_emp_id", accStoreAttr.getProc_emp_id());
			mode.addAttribute("proc_emp_name", accStoreAttr.getProc_emp_name());
			mode.addAttribute("note", accStoreAttr.getNote());
			mode.addAttribute("address", accStoreAttr.getAddress());
			return "hrp/acc/acccheckitem/accCheckStoreUpdate";
		}else{
			accStoreAttr = accStoreAttrService.queryHosStoreByCode(mapVo);
			mode.addAttribute("group_id", accStoreAttr.getGroup_id());
			mode.addAttribute("hos_id", accStoreAttr.getHos_id());
			mode.addAttribute("store_id", accStoreAttr.getStore_id());
			mode.addAttribute("store_code", accStoreAttr.getStore_code());
			mode.addAttribute("store_name", accStoreAttr.getStore_name());
			mode.addAttribute("type_code", accStoreAttr.getType_code());
			mode.addAttribute("type_name", accStoreAttr.getType_name());
			mode.addAttribute("spell_code", accStoreAttr.getSpell_code());
			mode.addAttribute("wbx_code", accStoreAttr.getWbx_code());
			mode.addAttribute("is_stop", accStoreAttr.getIs_stop());
			mode.addAttribute("is_disable", accStoreAttr.getIs_disable());
			return "hrp/acc/acccheckitem/accCheckStoreAdd";
		}

	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckSupUpdatePage", method = RequestMethod.GET)
	public String accCheckSupUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String result = "";
		
		if(!mapVo.containsKey("sup_id")){
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			Map<String, Map<String, Object>> map = sysBaseService.getHosRulesList(mapVo);
			if(map.containsKey("HOS_SUP")){
				mode.addAttribute("is_auto", map.get("HOS_SUP").get("is_auto"));
			}else{
				mode.addAttribute("is_auto", 0);
			}
			result = "hrp/acc/acccheckitem/accCheckSupAdd";
			
		}else{
			
			AccSupAttr accSupAttr = accSupAttrService.queryAccSupAttrByCode(mapVo);
			
			if(accSupAttr != null){
				
				mode.addAttribute("group_id", accSupAttr.getGroup_id());
				mode.addAttribute("hos_id", accSupAttr.getHos_id());
				mode.addAttribute("sup_id", accSupAttr.getSup_id());
				mode.addAttribute("sup_code", accSupAttr.getSup_code());
				mode.addAttribute("sup_name", accSupAttr.getSup_name());
				mode.addAttribute("type_code", accSupAttr.getType_code());
				mode.addAttribute("type_name", accSupAttr.getType_name());
				mode.addAttribute("spell_code", accSupAttr.getSpell_code());
				mode.addAttribute("wbx_code", accSupAttr.getWbx_code());
				mode.addAttribute("is_stop", accSupAttr.getIs_stop());
				mode.addAttribute("is_disable", accSupAttr.getIs_disable());
				mode.addAttribute("bank_name", accSupAttr.getBank_name());
				mode.addAttribute("bank_number", accSupAttr.getBank_number());
				mode.addAttribute("legal", accSupAttr.getLegal());
				mode.addAttribute("regis_no", accSupAttr.getRegis_no());
				mode.addAttribute("phone", accSupAttr.getPhone());
				mode.addAttribute("mobile", accSupAttr.getMobile());
				mode.addAttribute("contact", accSupAttr.getContact());
				mode.addAttribute("fax", accSupAttr.getMobile());
				mode.addAttribute("email", accSupAttr.getEmail());
				mode.addAttribute("region", accSupAttr.getRegion());
				mode.addAttribute("zip_code", accSupAttr.getZip_code());
				mode.addAttribute("note", accSupAttr.getNote());
				mode.addAttribute("address", accSupAttr.getAddress());
				mode.addAttribute("is_mat", accSupAttr.getIs_mat());
				mode.addAttribute("is_med", accSupAttr.getIs_med());
				mode.addAttribute("is_ass", accSupAttr.getIs_ass());
				mode.addAttribute("is_sup", accSupAttr.getIs_sup());
				mode.addAttribute("range_id", accSupAttr.getRange_id());
				result = "hrp/acc/acccheckitem/accCheckSupUpdate";
				
			}

		}
		return result ;
	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckSourceUpdatePage", method = RequestMethod.GET)
	public String accCheckSourceUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
			}
	        
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		
			Source source = sourceService.querySourceByCode(mapVo);
			
			mode.addAttribute("group_id",source.getGroup_id());
			
			mode.addAttribute("hos_id",source.getHos_id());
			
			mode.addAttribute("source_id",source.getSource_id());
			
			mode.addAttribute("source_code",source.getSource_code());
			
			mode.addAttribute("source_name",source.getSource_name());
			
			mode.addAttribute("source_attr",source.getSource_attr());
			
			mode.addAttribute("nature_name",source.getNature_name());
			
			mode.addAttribute("is_stop",source.getIs_stop());
			
			mode.addAttribute("note",source.getNote());

		return "hrp/acc/acccheckitem/accCheckSourceUpdate";

	}

	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckItemIndexPage", method = RequestMethod.GET)
	public String accCheckItemIndexPage(Model mode) throws Exception {

		return "hrp/acc/acccheckitem/accCheckItemIndex";

	}

	/**
	 * 核算项<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckItemAddPage", method = RequestMethod.GET)
	public String accCheckItemAddPage(Model mode, String check_type_id,String check_type_name) throws Exception {
		mode.addAttribute("check_type_id", check_type_id);
		mode.addAttribute("check_type_name",check_type_name);
		return "hrp/acc/acccheckitem/accCheckItemAdd";

	}

	/**
	 * 核算项<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/addAccCheckItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccCheckItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		// 根据名称生成拼音码
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("check_item_name").toString()));
		// 根据名称生成五笔码
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("check_item_name").toString()));
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String accCheckItemJson = accCheckItemService.addAccCheckItem(mapVo);

		return JSONObject.parseObject(accCheckItemJson);

	}

	/**
	 * 核算项<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accCheckItem = accCheckItemService.queryAccCheckItem(getPage(mapVo));

		return JSONObject.parseObject(accCheckItem);

	}
	/**
	 * 系统核算项<BR>
	 * 查询---部门
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckItemDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckItemDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());

		String accCheckItem = accCheckItemService.queryAccCheckItemDept(mapVo);

		return JSONObject.parseObject(accCheckItem);

	}
	
	/**
	 * 系统核算项<BR>
	 * 查询---职工
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckItemEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckItemEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());

		String accCheckItem = accCheckItemService.queryAccCheckItemEmp(mapVo);

		return JSONObject.parseObject(accCheckItem);

	}
	
	/**
	 * 系统核算项<BR>
	 * 查询---项目
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckItemProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckItemProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
		String accCheckItem = accCheckItemService.queryAccCheckItemProj(mapVo);

		return JSONObject.parseObject(accCheckItem);

	}
	
	/**
	 * 系统核算项<BR>
	 * 查询---库房
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckItemStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckItemStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
		String accCheckItem = accCheckItemService.queryAccCheckItemStore(mapVo);

		return JSONObject.parseObject(accCheckItem);

	}
	
	/**
	 * 系统核算项<BR>
	 * 查询---客户
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckItemCus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckItemCus(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
		String accCheckItem = accCheckItemService.queryAccCheckItemCus(mapVo);

		return JSONObject.parseObject(accCheckItem);

	}
	
	/**
	 * 系统核算项<BR>
	 * 查询---供应商
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckItemSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckItemSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
		String accCheckItem = accCheckItemService.queryAccCheckItemSup(mapVo);

		return JSONObject.parseObject(accCheckItem);

	}
	
	/**
	 * 系统核算项<BR>
	 * 查询---资金来源
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckItemSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckItemSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
		String accCheckItem = accCheckItemService.queryAccCheckItemSource(mapVo);

		return JSONObject.parseObject(accCheckItem);

	}
	/**
	 * 系统核算项<BR>
	 * 查询---单位
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/queryAccCheckItemHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckItemHos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
		String accCheckItem = accCheckItemService.queryAccCheckItemHos(mapVo);

		return JSONObject.parseObject(accCheckItem);

	}

	/**
	 * 核算项<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/deleteAccCheckItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCheckItem(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("check_item_id", id.split("@")[0]);// 实际实体类变量
			mapVo.put("check_type_id", id.split("@")[1]);// 实际实体类变量
			mapVo.put("group_id", id.split("@")[2]);// 实际实体类变量
			mapVo.put("hos_id", id.split("@")[3]);// 实际实体类变量
			mapVo.put("copy_code", id.split("@")[4]);// 实际实体类变量
			mapVo.put("column_check", id.split("@")[5]);// 实际实体类变量
			listVo.add(mapVo);
		}
		String accCheckItemJson = accCheckItemService.deleteBatchAccCheckItem(listVo);
		return JSONObject.parseObject(accCheckItemJson);

	}

	/**
	 * 核算项<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckItemUpdatePage", method = RequestMethod.GET)
	public String accCheckItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		AccCheckItem accCheckItem = new AccCheckItem();
		accCheckItem = accCheckItemService.queryAccCheckItemByCode(mapVo);
		mode.addAttribute("check_item_id", accCheckItem.getCheck_item_id());
		mode.addAttribute("check_type_id", accCheckItem.getCheck_type_id());
		mode.addAttribute("check_type_name", accCheckItem.getCheck_type_name());
		mode.addAttribute("check_item_name", accCheckItem.getCheck_item_name());
		mode.addAttribute("group_id", accCheckItem.getGroup_id());
		mode.addAttribute("hos_id", accCheckItem.getHos_id());
		mode.addAttribute("copy_code", accCheckItem.getCopy_code());
		mode.addAttribute("check_item_code", accCheckItem.getCheck_item_code());
		mode.addAttribute("check_item_name", accCheckItem.getCheck_item_name());
		mode.addAttribute("sort_code", accCheckItem.getSort_code());
		mode.addAttribute("spell_code", accCheckItem.getSpell_code());
		mode.addAttribute("wbx_code", accCheckItem.getWbx_code());
		mode.addAttribute("is_stop", accCheckItem.getIs_stop());
		mode.addAttribute("type_code", accCheckItem.getType_code());
		mode.addAttribute("type_name", accCheckItem.getType_name());
		mode.addAttribute("column_check", accCheckItem.getColumn_check());
		return "hrp/acc/acccheckitem/accCheckItemUpdate";
	}

	/**
	 * 核算项<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/acccheckitem/updateAccCheckItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCheckItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("is_auto").equals("true")){
			// 根据名称生成拼音码
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("check_item_name").toString()));
	
			// 根据名称生成五笔码
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("check_item_name").toString()));
		}
		String accCheckItemJson = accCheckItemService.updateAccCheckItem(mapVo);

		return JSONObject.parseObject(accCheckItemJson);
	}

	 /**
	 * @Description 
	 * 导入跳转页面 核算项 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckItemImportPage", method = RequestMethod.GET)
	public String accCheckItemImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("check_type_id", mapVo.get("check_type_id"));
		mode.addAttribute("check_type_name", mapVo.get("check_type_name"));
		return "hrp/acc/acccheckitem/accCheckItemImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 核算项
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/acc/acccheckitem/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"acc\\基础设置","核算项模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 预算科室分组字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/acc/acccheckitem/readAccCheckItemFiles",method = RequestMethod.POST)  
    public String readAccCheckItemFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,@RequestParam Map<String, Object> entityMap,Model mode) throws IOException { 
			 
		List<AccCheckItem> list_err = new ArrayList<AccCheckItem>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++){
				
				StringBuffer err_sb = new StringBuffer();
				
				AccCheckItem accCheckItem = new AccCheckItem();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("check_type_id",entityMap.get("check_type_id"));
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if (StringTool.isNotBlank(temp[0])){
						if(temp[0].equals(error[0])){
							err_sb.append("第"+i+"行核算项编码与第 "+j+"行核算项编码重复");
						}
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行核算项名称与第 "+j+"行核算项名称重复");
						}
					}
				}
				
					if (StringTool.isNotBlank(temp[0])) {
						
						accCheckItem.setCheck_item_code(temp[0]);
								
		    		mapVo.put("check_item_code", temp[0]);
		    		
					} else {
						
						err_sb.append("核算项编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
						accCheckItem.setCheck_item_name(temp[1]);
								
		    		mapVo.put("check_item_name", temp[1]);
		    		
					} else {
						
						err_sb.append("核算项名称为空  ");
						
					}
					if (StringTool.isNotBlank(temp[2])) {
						
						accCheckItem.setType_code(String.valueOf(temp[2]));
								
						mapVo.put("type_code", temp[2]);
						//查询  核算项分类编码  是否存在 
						AccCheckItemType type = accCheckItemTypeService.queryAccCheckItemTypeById(mapVo);
						
						if(type == null){
							err_sb.append("核算项分类编码 :"+mapVo.get("type_code")+"不存在");
						}
		    		
					}else{
						
						accCheckItem.setType_code("");
						
						mapVo.put("type_code", "");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
						accCheckItem.setIs_stop(Integer.valueOf(temp[3]));
								
		    		mapVo.put("is_stop", temp[3]);
		    		
					}else{
						
						err_sb.append("是否停用（0:不停用 1:停用）为空  ");
						
					}
					
					
				int code = accCheckItemService.existsCodeUpdate(mapVo);
				
				if (code > 0 ) {
					
					err_sb.append("编码已被占用！ ");
					
				}
				
				int count = accCheckItemService.existsNameAdd(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("名称已被占用！ ");
					
				}
				
				if (err_sb.toString().length() > 0) {
					
					accCheckItem.setError_type(err_sb.toString());
					
					list_err.add(accCheckItem);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("check_item_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("check_item_name").toString()));
			  
				  addList.add(mapVo);
				}
				
			}
			if(list_err.size() ==  0){
				String dataJson = accCheckItemService.addBatchAccCheckItem(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			AccCheckItem data_exc = new AccCheckItem();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 预算科室分组字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/acc/acccheckitem/importBatchAccCheckItem", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> importBatchAccCheckItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AccCheckItem> list_err = new ArrayList<AccCheckItem>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
		Iterator it = json.iterator();
		
		try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			AccCheckItem accCheckItem = new AccCheckItem();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("check_item_code"))) {
						
						accCheckItem.setCheck_item_code(String.valueOf(jsonObj.get("check_item_code")));
											
						mapVo.put("check_item_code", jsonObj.get("check_item_code"));
		    		
					} else {
						
						err_sb.append("核算项编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("check_item_name"))) {
						
						accCheckItem.setCheck_item_name(String.valueOf(jsonObj.get("check_item_name")));
											
		    		mapVo.put("check_item_name", jsonObj.get("check_item_name"));
		    		
					} else {
						
						err_sb.append("核算项名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
						accCheckItem.setType_code(String.valueOf(jsonObj.get("type_code")));
											
						mapVo.put("type_code", jsonObj.get("type_code"));
		    		
					} else {
						
						accCheckItem.setType_code("");
						
						mapVo.put("type_code", "");
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
						accCheckItem.setIs_stop(Integer.valueOf(String.valueOf(jsonObj.get("is_stop"))));
											
						mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		
					} else {
						
						err_sb.append("是否停用（0:不停用 1:停用）为空  ");
						
					}
					
					int code = accCheckItemService.existsCodeUpdate(mapVo);
					
					if (code > 0 ) {
						
						err_sb.append("编码已被占用！ ");
						
					}
					
					int count = accCheckItemService.existsNameAdd(mapVo);
					
					if (count > 0 ) {
						
						err_sb.append("名称已被占用！ ");
						
					}
				if (err_sb.toString().length() > 0) {
					
					accCheckItem.setError_type(err_sb.toString());
					
					list_err.add(accCheckItem);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("group_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("group_name").toString()));
				  addList.add(mapVo);
				}
				
			}
			
			if(list_err.size() == 0 && addList.size() > 0){
				String dataJson = accCheckItemService.addBatchAccCheckItem(addList);
			}
			
		} catch (DataAccessException e) {
			
			AccCheckItem data_exc = new AccCheckItem();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }

	@RequestMapping(value = "/hrp/acc/acccheckitem/accCheckItemExtendPage", method = RequestMethod.GET)
	public String accCheckItemExtendPage(Model mode,String check_type_id, String check_type_name) throws Exception {
		mode.addAttribute("check_type_id", check_type_id);
		mode.addAttribute("check_type_name", check_type_name);
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());		
		mode.addAttribute("hos_code",  hrpSysSelectService.queryHosInfoDict(mapVo));
		return "hrp/acc/acccheckitem/accCheckItemExtend";

	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/addAccStoreAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccStoreAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}

		String accCheckItemJson = accStoreAttrService.addAccStoreAttr(mapVo);

		return JSONObject.parseObject(accCheckItemJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/addAccCusAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccCusAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}

		String accCusJson = accCusAttrService.addAccCusAttr(mapVo);

		return JSONObject.parseObject(accCusJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/updateAccCusAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCusAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}

		String accCusJson = accCusAttrService.updateAccCusAttr(mapVo);

		return JSONObject.parseObject(accCusJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/addAccSupAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccSupAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		if (mapVo.get("user_id") == null) {
			
			mapVo.put("user_id", SessionManager.getUserId());
			
		}

		String accSupJson = accSupAttrService.addAccSupAttr(mapVo);

		return JSONObject.parseObject(accSupJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/updateAccSupAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccSupAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}

		String accSupJson = accSupAttrService.updateAccSupAttr(mapVo);

		return JSONObject.parseObject(accSupJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/acccheckitem/addBatchAccCheckItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccCheckItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("cur_hos_id", SessionManager.getHosId());
		mapVo.put("cur_copy_code", SessionManager.getCopyCode());
		
		String accCheckJson = accCheckItemService.insertBatchAccCheckItem(mapVo);

		return JSONObject.parseObject(accCheckJson);
		
	}
	 
	@RequestMapping(value = "/hrp/acc/acccheckitem/checkItemCodeOrNameRepeat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkItemCodeOrNameRepeat(@RequestParam Map<String, Object> mapVo, Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String accCheckJson = accCheckItemService.checkItemCodeOrNameRepeat(mapVo);
		return JSONObject.parseObject(accCheckJson);
	}

}
