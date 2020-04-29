/**
 * 2015-2-2 SysUserService.java author:alfred
 */
package com.chd.hrp.hpm.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.service.AphiSelectDictService;

@Controller
public class AphiSelectDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiSelectDictController.class);

	@Resource(name = "aphiSelectDictService")
	private AphiSelectDictService aphiSelectDictService = null;

	// 奖金项目
	@RequestMapping(value = "/hrp/hpm/queryItemAllDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryItemAllDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		mapVo.put("user_id", SessionManager.getUserId());

		String app_mod_code = (String) mapVo.get("app_mod_code");

		mapVo.put("app_mod_code", app_mod_code);

		String itemDict = aphiSelectDictService.queryItemAllDict(mapVo);

		return itemDict;
	}

	// 应用模式字典
	@RequestMapping(value = "/hrp/hpm/queryAppModDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAppModDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryAppModDict(mapVo);

	}

	// 科室分类字典
	@RequestMapping(value = "/hrp/hpm/queryDeptKindDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptKindDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryDeptKindDict(mapVo);

	}

	// 科室性质字典
	@RequestMapping(value = "/hrp/hpm/queryDeptNatureDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptNatureDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryDeptNatureDict(mapVo);

	}

	// 科室字典
	@RequestMapping(value = "/hrp/hpm/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String nature_code = (String) mapVo.get("nature_code");

		mapVo.put("nature_code", nature_code);
		
		if(MyConfig.getSysPara("09004") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09004"));
		}

		return aphiSelectDictService.queryDeptDict(mapVo);

	}
	
	// 科室字典
	@RequestMapping(value = "/hrp/hpm/queryDeptDictByPerm", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictByPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if (mapVo.get("user_id") == null) {
			
			mapVo.put("user_id", SessionManager.getUserId());
			
		}

		String nature_code = (String) mapVo.get("nature_code");

		mapVo.put("nature_code", nature_code);

		return aphiSelectDictService.queryDeptDictByPerm(mapVo);

	}
	
	// 科室字典(传入时间)
	@RequestMapping(value = "/hrp/hpm/queryDeptDictTime", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictTime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		if(mapVo.get("nature_code") != null){
			String nature_code = (String) mapVo.get("nature_code");

			mapVo.put("nature_code", nature_code);
		}
		
		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);
		}

		return aphiSelectDictService.queryDeptDictTime(mapVo);

	}

	// 平台科室字典
	@RequestMapping(value = "/hrp/hpm/querySysDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySysDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.querySysDeptDict(mapVo);

	}

	// 对应性质
	@RequestMapping(value = "/hrp/hpm/queryDeptRefDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptRefDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryDeptRefDict(mapVo);

	}

	// 职务字典
	@RequestMapping(value = "/hrp/hpm/queryEmpDutyDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDutyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		return aphiSelectDictService.queryEmpDutyDict(mapVo);

	}

	// 职工字典
	@RequestMapping(value = "/hrp/hpm/queryEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		return aphiSelectDictService.queryEmpDict(mapVo);

	}
	
	// 职工字典(获取职工编码)
	@RequestMapping(value = "/hrp/hpm/queryEmpDictByCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDictByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		
		return aphiSelectDictService.queryEmpDictByCode(mapVo);

	}

	// 指标性质字典表
	@RequestMapping(value = "/hrp/hpm/queryTargetNatureDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryTargetNatureDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryTargetNatureDict(mapVo);

	}

	// 收入项目下拉框
	@RequestMapping(value = "/hrp/hpm/queryAphiIncomeItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryAphiIncomeItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryAphiIncomeItem(mapVo);

	}

	// 支出项目下拉框
	@RequestMapping(value = "/hrp/hpm/queryAphiCostItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryAphiCostItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryAphiCostItem(mapVo);

	}

	// 收入项目下拉框
	@RequestMapping(value = "/hrp/hpm/queryIncomeItemSeq", method = RequestMethod.POST)
	@ResponseBody
	public String queryIncomeItemSeq(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryIncomeItemSeq(mapVo);

	}
	// 收入项目下拉框(数据准备日期查询)
	@RequestMapping(value = "/hrp/hpm/queryIncomeItemSeqTime", method = RequestMethod.POST)
	@ResponseBody
	public String queryIncomeItemSeqTime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);
		}

		return aphiSelectDictService.queryIncomeItemSeqTime(mapVo);

	}

	// 支出项目下拉框
	@RequestMapping(value = "/hrp/hpm/queryCostItemSeq", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostItemSeq(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryCostItemSeq(mapVo);

	}
	
	// 支出项目下拉框(带日期)
	@RequestMapping(value = "/hrp/hpm/queryCostItemSeqTime", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostItemSeqTime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);
		}

		return aphiSelectDictService.queryCostItemSeqTime(mapVo);

	}

	// 工作量项目下拉框
	@RequestMapping(value = "/hrp/hpm/queryWorkItemSeq", method = RequestMethod.POST)
	@ResponseBody
	public String queryWorkItemSeq(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryWorkItemSeq(mapVo);

	}
	
	// 指标取值方法参数表TARGET_METHOD_PARA
	@RequestMapping(value = "/hrp/hpm/queryTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public String queryTargetMethodPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryTargetMethodPara(mapVo);

	}

	// 表名:APHI_FORMULA 解释:奖金计算公式表
	@RequestMapping(value = "/hrp/hpm/queryFormula", method = RequestMethod.POST)
	@ResponseBody
	public String queryFormula(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryFormula(mapVo);

	}

	// 表名:APHI_FUN 解释:奖金函数表
	@RequestMapping(value = "/hrp/hpm/queryFun", method = RequestMethod.POST)
	@ResponseBody
	public String queryFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryFun(mapVo);

	}

	// 表名:APHI_WORKITEM 解释:工作量指标表
	@RequestMapping(value = "/hrp/hpm/queryWorkItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryWorkItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryWorkItem(mapVo);

	}
	
	// 工作量指标表查询(带时间)
	@RequestMapping(value = "/hrp/hpm/queryHpmWorkitemSeqTime", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmWorkitemSeqTime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);
		}
		
		return aphiSelectDictService.queryHpmWorkitemSeqTime(getPage(mapVo));
	}

	// 表名:APHI_TARGET 解释:奖金指标字典表
	@RequestMapping(value = "/hrp/hpm/queryTarget", method = RequestMethod.POST)
	@ResponseBody
	public String queryTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryTarget(mapVo);

	}
	
	// 表名:APHI_TARGET 解释:奖金指标字典表 带编码查询
	@RequestMapping(value = "/hrp/hpm/queryTargetCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryTargetCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryTargetCode(mapVo);

	}

	// 表名:sys_comp
	@RequestMapping(value = "/hrp/hpm/querySysComp", method = RequestMethod.POST)
	@ResponseBody
	public String querySysComp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return aphiSelectDictService.querySysComp(mapVo);

	}

	// 表名:sys_copy
	@RequestMapping(value = "/hrp/hpm/querySysCopy", method = RequestMethod.POST)
	@ResponseBody
	public String querySysCopy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return aphiSelectDictService.querySysCopy(mapVo);

	}

	// 方案序号
	@RequestMapping(value = "/hrp/hpm/querySchemeSeq", method = RequestMethod.POST)
	@ResponseBody
	public String querySchemeSeq(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.querySchemeSeq(mapVo);

	}

	// 方案序号
	@RequestMapping(value = "/hrp/hpm/queryHpmFunType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryHpmFunType(mapVo);

	}

	// 方案序号
	@RequestMapping(value = "/hrp/hpm/queryHpmComType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryHpmComType(mapVo);

	}

	// 方案序号
	@RequestMapping(value = "/hrp/hpm/queryHpmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmFunParaMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryHpmFunParaMethod(mapVo);

	}

	// 方案序号
	@RequestMapping(value = "/hrp/hpm/querySubSchemeSeqDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySubSchemeSeqDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.querySubSchemeSeqDict(mapVo);

	}

	@RequestMapping(value = "/hrp/hpm/queryTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public String queryTargetMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryTargetMethod(mapVo);

	}

	@RequestMapping(value = "/hrp/hpm/queryCostTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryCostTypeDict(mapVo);

	}

	@RequestMapping(value = "/hrp/hpm/queryWorkItemSeqMore", method = RequestMethod.POST)
	@ResponseBody
	public String queryWorkItemSeqMore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String year_month = (String) mapVo.get("acct_yearm");

		if (StringUtils.isNotEmpty(year_month)) {

			mapVo.put("acct_month", year_month.substring(4, 6));

			mapVo.put("acct_year", year_month.substring(0, 4));

		}

		return aphiSelectDictService.queryWorkItemSeqMore(mapVo);

	}

	// 查询 函数参数类型
	@RequestMapping(value = "/hrp/hpm/queryHpmFunParaType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmFunParaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryHpmFunParaType(mapVo);

	}

	// 查询 函数参数类型
	@RequestMapping(value = "/hrp/hpm/queryHpmDeptNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return aphiSelectDictService.queryHpmDeptNature(mapVo);

	}
	
	@RequestMapping(value = "/hrp/hpm/queryHpmOraclePkg", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmOraclePkg(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hpmOraclePkg = aphiSelectDictService.queryHpmOraclePkg(mapVo);

		return hpmOraclePkg;
	}
	
	@RequestMapping(value = "/hrp/hpm/querySysGroupDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySysGroupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String sysGroupDict = aphiSelectDictService.querySysGroupDict(mapVo);

		return sysGroupDict;
	}
	
	@RequestMapping(value = "/hrp/hpm/queryAphiDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public String queryAphiDeptHip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String aphiDeptHip = aphiSelectDictService.queryAphiDeptHip(mapVo);

		return aphiDeptHip;
	}
	
	@RequestMapping(value = "/hrp/hpm/queryAphiDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAphiDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String aphiDeptDict = aphiSelectDictService.queryAphiDeptDict(mapVo);

		return aphiDeptDict;
	}
	
	@RequestMapping(value = "/hrp/hpm/queryAphiEmpItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryAphiEmpItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String aphiEmpItem = aphiSelectDictService.queryAphiEmpItem(mapVo);

		return aphiEmpItem;
	}
	
	@RequestMapping(value = "/hrp/hpm/queryAphiTemplateKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryAphiTemplateKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String aphiTemplateKind = aphiSelectDictService.queryAphiTemplateKind(mapVo);

		return aphiTemplateKind;
	}
}
