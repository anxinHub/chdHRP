package com.chd.hrp.budg.controller.base;

import java.util.Map;

import org.apache.log4j.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.budg.service.base.BudgSelectService;

@Controller
public class BudgSelectController extends BaseController {
	
	private static Logger logger = Logger.getLogger(BudgSelectController.class);
	
	@Resource(name ="budgSelectService")
	private BudgSelectService budgSelectService = null;
	
	/**
	 * 医保类型性质下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgYBTypeNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBTypeNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String typeNature = budgSelectService.queryBudgYBTypeNature(mapVo);
		return typeNature;

	}
	/**
	 * 病种名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSingleDisease(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = budgSelectService.queryBudgSingleDisease(mapVo);
		return json;

	}
	/**
	 * 病种名称下拉框 (根据医保类型编码 查询对应的 单病种)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgSingleDiseaseByInsCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSingleDiseaseByInsCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = budgSelectService.queryBudgSingleDiseaseByInsCode(mapVo);
		return json;

	}
	/**
	 *   物资分类名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryMatTypes", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgSelectService.queryMatTypes(mapVo);
		return item;

	}
	/**
	 * 指标性质下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgIndexNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndexNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String typeNature = budgSelectService.queryBudgIndexNature(mapVo);
		return typeNature;

	}
	
	/**
	 * 医保类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgYBType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String ybType = budgSelectService.queryBudgYBType(mapVo);
		return ybType;

	}
	
	/**
	 * 根据付费机制 查询 医保类型下拉框（查 医保付费机制维护表 BUDG_YB_PAY_MODE_SET）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgYBTypeByMode", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBTypeByMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String ybType = budgSelectService.queryBudgYBTypeByMode(mapVo);
		return ybType;

	}
	
	/**
	 * 部门类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDeptType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String deptType = budgSelectService.queryBudgDeptType(mapVo);
		return deptType;

	}
	
	/**
	 * 部门分类下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String kind = budgSelectService.queryBudgDeptKind(mapVo);
		return kind;

	}
	
	/**
	 * 部门性质下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDeptNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String natur = budgSelectService.queryBudgDeptNature(mapVo);
		return natur;

	}
	
	/**
	 * 部门支出性质下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDeptOut", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String out = budgSelectService.queryBudgDeptOut(mapVo);
		return out;

	}
	
	/**
	 * 费用标准性质下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgCharStanNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCharStanNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String nature = budgSelectService.queryBudgCharStanNature(mapVo);
		return nature;

	}
	
	/**
	 * 费用标准下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgChargeStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String stan = budgSelectService.queryBudgChargeStan(mapVo);
		return stan;

	}
	
	/**
	 * 费用标准下拉框 费用标准取值方法维护 添加、修改页面专用
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgChargeStanGetWay", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgChargeStanGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String stan = budgSelectService.queryBudgChargeStanGetWay(mapVo);
		return stan;

	}
	
	/**
	 * 根据费用标准 查询其对应科室 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgChargeStanDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgChargeStanDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String stan = budgSelectService.queryBudgChargeStanDept(mapVo);
		return stan;

	}
	/**
	 *数据性质下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDataNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDataNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String stan = budgSelectService.queryBudgDataNature(mapVo);
		return stan;

	}
	
	
	/**
	 * 取值方法下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgGetValueWay", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgGetValueWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String method = budgSelectService.queryBudgGetValueWay(mapVo);
		return method;

	}
	
	
	/**
	 * 计算公式下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgFormula", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgFormula(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String formula = budgSelectService.queryBudgFormula(mapVo);
		return formula;

	}
	
	/**
	 * 取值函数下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgFun", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String fun = budgSelectService.queryBudgFun(mapVo);
		return fun;

	}
	
	/**
	 * 函数参数取值 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgFunParaMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String fun = budgSelectService.queryBudgFunParaMethod(mapVo);
		return fun;

	}
	
	/**
	 * 函数参数 部件类型 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgComType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String fun = budgSelectService.queryBudgComType(mapVo);
		return fun;

	}
	
	/**
	 * 存储过程包名 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgOraclePkg", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgOraclePkg(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String pkg = budgSelectService.queryBudgOraclePkg(mapVo);
		
		return pkg;

	}
	
	/**
	 * 收入科目级次下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgSubjLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSubjLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String level = budgSelectService.queryBudgSubjLevel(mapVo);
		return level;

	}
	
	/**
	 * 支出科目级次下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgCostSubjLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCostSubjLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String level = budgSelectService.queryBudgCostSubjLevel(mapVo);
		return level;

	}
	
	/**
	 * 科目性质下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgAccSubjNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgAccSubjNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String nature = budgSelectService.queryBudgAccSubjNature(mapVo);
		return nature;

	}
	
	/**
	 * 预算科目下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("budg_year") == null|| mapVo.get("budg_year").equals("")) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		if(mapVo.get("subj_type").equals("05")){
			mapVo.put("table", "BUDG_COST_SUBJ");
		}
		if(mapVo.get("subj_type").equals("04")){
			mapVo.put("table", "BUDG_INCOME_SUBJ");
		}
		
		String budgSubj = budgSelectService.queryBudgSubj(mapVo);
		return budgSubj;

	}
	
	/**
	 * 会计科目下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String accSubj = budgSelectService.queryAccSubj(mapVo);
		return accSubj;

	}
	
	/**
	 * 会计科目下拉框 支出项目字典页面 专用 勿动
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryAccSubjDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccSubjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String accSubj = budgSelectService.queryAccSubjDict(mapVo);
		return accSubj;

	}
	/**
	 * 审批类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgCheckType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCheckType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgYear = budgSelectService.queryBudgCheckType(mapVo);
		return budgYear;

	}
	/**
	 * 审批状态下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgBcState", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgBcState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgYear = budgSelectService.queryBudgBcState(mapVo);
		return budgYear;

	}
	/**
	 * 预算年度下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgYear", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgYear = budgSelectService.queryBudgYear(mapVo);
		return budgYear;

	}
	/**
	 * 预算年度下拉框(上下十年)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgYearTen", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYearTen(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgYear = budgSelectService.queryBudgYearTen(mapVo);
		return budgYear;

	}
	/**
	 * 预算年度下拉框(启始值五年)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgYearFive", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYearFive(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgYear = budgSelectService.queryBudgYearFive(mapVo);
		return budgYear;

	}
	/**
	 * 收入预算科目类别下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgIncomeSubjType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String incomeType = budgSelectService.queryBudgIncomeSubjType(mapVo);
		return incomeType;

	}
	
	/**
	 * 支出预算科目类别下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgCostSubjType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCostSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String incomeType = budgSelectService.queryBudgCostSubjType(mapVo);
		return incomeType;

	}
	
	/**
	 * 根据科目类型 查询收入预算科目 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgIncomeTypeSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeTypeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String incomeSubj = budgSelectService.queryBudgIncomeTypeSet(mapVo);
		return incomeSubj;

	}
	
	/**
	 * 根据科目类型 查询支出预算科目 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgCostTypeSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCostTypeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costSubj = budgSelectService.queryBudgCostTypeSet(mapVo);
		return costSubj;

	}
	
	/**
	 * 收入预算科目下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String nature = budgSelectService.queryBudgIncomeSubj(mapVo);
		return nature;

	}
	
	/**
	 * 科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		String nature = budgSelectService.queryDept(mapVo);
		return nature;

	}
	/**
	 * 全部科室下拉框包括已停用  
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDeptDictAll", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptDictAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String nature = budgSelectService.queryBudgDeptDictAll(mapVo);
		return nature;
		
	}
	/**
	 * 基本指标下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDeptindex_code_name", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptindex_code_name(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String deptType = budgSelectService.queryBudgDeptindex_code_name(mapVo);
		return deptType;

	}
	
	/**
	 * 基本指标下拉框   基本指标取值方法维护 添加、修改页面专用
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgIndexCodeGetWay", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndexCodeGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String deptType = budgSelectService.queryBudgIndexCodeGetWay(mapVo);
		return deptType;

	}
	
	/**
	 * 基本指标对应 预算科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgBasicIndexDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgBasicIndexDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String deptType = budgSelectService.queryBudgBasicIndexDept(mapVo);
		return deptType;

	}
	
	/**
	 * 函数分类 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/qureyBudgFunType", method = RequestMethod.POST)
	@ResponseBody
	public String qureyBudgFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String funType = budgSelectService.qureyBudgFunType(mapVo);
		return funType;

	}
	
	/**
	 * 元素类型下拉框（计算公式页面用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryElementType", method = RequestMethod.POST)
	@ResponseBody
	public String queryElementType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String method = budgSelectService.queryElementType(mapVo);
		return method;

	}
	
	/**
	 * 工资项目 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgWageItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgSelectService.queryBudgWageItem(mapVo);
		return item;

	}
	
	/**
	 * 期间属性 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgPeriodNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPeriodNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String period = budgSelectService.queryBudgPeriodNature(mapVo);
		return period;

	}
	
	
	/**
	 * 奖金项目 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgAwardsItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgAwardsItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgSelectService.queryBudgAwardsItem(mapVo);
		return item;

	}
	/**
	 * 所有奖金项目包括已停用 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgAwardsItemAll", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgAwardsItemAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgSelectService.queryBudgAwardsItemAll(mapVo);
		return item;
		
	}
	
	
	/**
	 * 编制方法 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgEditMethod", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgEditMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String method = budgSelectService.queryBudgEditMethod(mapVo);
		return method;

	}
	
	
	/**
	 * 取值方法（get_way） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgGetWay", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String method = budgSelectService.queryBudgGetWay(mapVo);
		return method;

	}
	
	
	/**
	 * 分解方法（resolve_way） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgResolveWay", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgResolveWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String method = budgSelectService.queryBudgResolveWay(mapVo);
		return method;

	}
	
	/**
	 * 药品类别 下拉框 带变更号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDrugType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDrugType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgSelectService.queryBudgDrugType(mapVo);
		return item;

	}
	
	/**
	 * 药品类别 下拉框不带变更号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgMedType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMedType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String type = budgSelectService.queryBudgMedType(mapVo);
		return type;

	}
	
	/**
	 * 固定资产类别 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgCostFassetType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCostFassetType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String method = budgSelectService.queryBudgCostFassetType(mapVo);
		return method;

	}
	
	/**
	 * 支出项目下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgSelectService.queryBudgPaymentItem(mapVo);
		return item;

	}
	
	/**
	 * 支出项目下拉框 带变更号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgPaymentItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPaymentItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String item = budgSelectService.queryBudgPaymentItemDict(mapVo);
		return item;

	}
	/**
	 * 根据 预算层次 budg_level （01医院年度 02医院月份 03科室年度 04科室月份 ） 
	 * 编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 从  业务预算编制方案表 查询 预算指标下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/qureyBudgIndexFromPlan", method = RequestMethod.POST)
	@ResponseBody
	public String qureyBudgIndexFromPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String index = budgSelectService.qureyBudgIndexFromPlan(mapVo);
		return index;

	}
	
	/**	
	 * 根据  
	 *  编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 从  医院年度收入预算编制方案表 查询 科目下拉框
	 */
	@RequestMapping(value = "/hrp/budg/qureySubjIndexFromPlan", method = RequestMethod.POST)
	@ResponseBody
	public String qureySubjIndexFromPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String index = budgSelectService.qureySubjIndexFromPlan(mapVo);
		return index;

	}
	
	/**	
	 * 查询年度收入中为非独立预算方法的科目
	 */
	@RequestMapping(value = "/hrp/budg/qureyResolveSubjIndexFromPlan", method = RequestMethod.POST)
	@ResponseBody
	public String qureyResolveSubjIndexFromPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String index = budgSelectService.qureyResolveSubjIndexFromPlan(mapVo);
		return index;

	}
	
	/**
	 * 预算指标下拉框（查询表 BUDG_INDEX_DICT）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndexDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String index = budgSelectService.queryBudgIndexDict(mapVo);
		return index;

	}
	/**
	 * 预算指标(可累加)下拉框（查询表 BUDG_INDEX_DICT）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgIndexAccumulationDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndexAccumulationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String index = budgSelectService.queryBudgIndexAccumulationDict(mapVo);
		return index;

	}
	
	/**
	 * 分解或汇总（resolve_or_sum） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgResolveOrSum", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgResolveOrSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String method = budgSelectService.queryBudgResolveOrSum(mapVo);
		return method;

	}
	
	/**
	 * 预算层次（budg_level） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String level = budgSelectService.queryBudgLevel(mapVo);
		return level;

	}
	
	/**
	 * 业务预算状态（state） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgState", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String state = budgSelectService.queryBudgState(mapVo);
		return state;

	}
	
	
	/**
	 * 根据指标查询科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgIndexDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndexDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String nature = budgSelectService.queryBudgIndexDeptSet(mapVo);
		return nature;

	}
	
	/**	
	 * 根据  编制方法为分解方法 
			从  医院年度收入预算编制方案表 查询 分解 科目下拉框
	 */
	@RequestMapping(value = "/hrp/budg/qureySubjCodeFromPlan", method = RequestMethod.POST)
	@ResponseBody
	public String qureySubjCodeFromPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String index = budgSelectService.qureySubjCodeFromPlan(mapVo);
		return index;

	}
	/**
	 * 项目名称（proj_name） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryProjName", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjName(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String proj_name = budgSelectService.queryProjName(mapVo);
		return proj_name;

	}
	/**
	 * 项目状态 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/qureyProjStateSelect", method = RequestMethod.POST)
	@ResponseBody
	public String qureyProjStateSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String proj_state = budgSelectService.qureyProjStateSelect(mapVo);
		
		return proj_state;
		
	}
	/**
	 * 资金计划 状态 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/qureyCashPlanStateSelect", method = RequestMethod.POST)
	@ResponseBody
	public String qureyCashPlanStateSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String cash_state = budgSelectService.qureyCashPlanStateSelect(mapVo);
		
		return cash_state;
		
	}
	/**
	 * 项目立项状态 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/qureyProjSetUpStateSelect", method = RequestMethod.POST)
	@ResponseBody
	public String qureyProjSetUpStateSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String proj_state = budgSelectService.qureyProjSetUpStateSelect(mapVo);
		
		return proj_state;
		
	}
	/**
	 * 项目负责人（state） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryConEmpId", method = RequestMethod.POST)
	@ResponseBody
	public String queryConEmpId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String emp_id = budgSelectService.queryConEmpId(mapVo);
		return emp_id;

	}
	
	/**
	 * 状态 下拉框（01 新建 02已提交 03 已审核）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgApplyState", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgApplyState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String proj_state = budgSelectService.queryBudgApplyState(mapVo);
		
		return proj_state;
		
	}
	
	
	/**
	 * 预算申报单号 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgApplyCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgApplyCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("budg_year") == null) {
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		String applyCode = budgSelectService.queryBudgApplyCode(mapVo);
		
		return applyCode;

	}
	
	/**
	 * 申报类型 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgApplyType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgApplyType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		String applyType = budgSelectService.queryBudgApplyType(mapVo);
		
		return applyType;

	}
	
	/**
	 * 资金来源 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgSource", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		
		String source = budgSelectService.queryBudgSource(mapVo);
		
		return source;

	}
	/**
	 * 资金来源 下拉框 不包括 自筹资金
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgSourceNotExistsZCZJ", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSourceNotExistsZCZJ(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		
		String source = budgSelectService.queryBudgSourceNotExistsZCZJ(mapVo);
		
		return source;
		
	}
	
	
	/**
	 * 根据项目状态       查询项目下拉框 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryProjByState", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjByState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		
		String proj_name = budgSelectService.queryProjByState(mapVo);
		
		return proj_name;
		
	}
	
	
	/**
	 * 项目类型 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgProjType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgProjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String type = budgSelectService.queryBudgProjType(mapVo);
		return type;

	}
	
	/**
	 * 项目级别 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgProjLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgProjLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String level = budgSelectService.queryBudgProjLevel(mapVo);
		return level;

	}
	/**
	 * 医院下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryHosInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		String hos = budgSelectService.queryHosInfoDict(mapVo);

		return hos;

	}
	
	/**
	 * 支出项目 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = budgSelectService.queryPaymentItem(mapVo);

		return paymentItem;

	}
	
	/**
	 * 资产性质  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryAssNatures", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssNatures(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = budgSelectService.queryAssNatures(mapVo);

		return paymentItem;

	}
	/**
	 * 资产性质  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryAssNaturesInassets", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssNaturesInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = budgSelectService.queryAssNaturesInassets(mapVo);

		return paymentItem;

	}
	
	/**
	 * 资产字典  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryAssDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = budgSelectService.queryAssDict(mapVo);

		return paymentItem;

	}
	
	/**
	 * 资产字典无形资产  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryAssDictInassets", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDictInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = budgSelectService.queryAssDictInassets(mapVo);

		return paymentItem;

	}
	
	/**
	 * 现金流量类型  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryCashType", method = RequestMethod.POST)
	@ResponseBody
	public String queryCashType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String type = budgSelectService.queryCashType(mapVo);
		return type;

	}
	
	/**
	 * 业务  调整单号 下拉框  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgAdjustCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgAdjustCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") ==  null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String adjCode = budgSelectService.queryBudgAdjustCode(mapVo);

		return adjCode;

	}
	
	
	
	/**
	 * 项目类型 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryCashItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryCashItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgSelectService.queryCashItem(mapVo);
		return item;

	}
	
	/**
	 * 科室  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgHosDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgHosDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String paymentItem = budgSelectService.queryBudgHosDept(mapVo);

		return paymentItem;

	}
	/**
	 * 预算月份  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgMonth", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String paymentItem = budgSelectService.queryBudgMonth(mapVo);
		
		return paymentItem;
		
	}
	/**
	 * 资金流向  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryCashDire", method = RequestMethod.POST)
	@ResponseBody
	public String queryCashDire(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String cashDire = budgSelectService.queryCashDire(mapVo);
		
		return cashDire;
		
	}
	/**
	 * 部门 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String cashDire = budgSelectService.queryDept(mapVo);
		
		return cashDire;
		
	}
	
	/**
	 * 物资分类与预算科目对应关系表中查询物资分类
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgMatTypeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMatTypeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String cashDire = budgSelectService.queryBudgMatTypeSubj(mapVo);
		
		return cashDire;
		
	}
	
	/**
	 * 系统字典表
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgSysDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSysDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = budgSelectService.queryBudgSysDict(mapVo);
		return subjType;
	}
	
	
	/**
	 * 职工
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = budgSelectService.queryEmpDict(mapVo);
		return subjType;
	}
	
	/**
	 * 职工类别
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryEmpType", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = budgSelectService.queryEmpType(mapVo);
		return subjType;
	}
	/**
	 * 所有职工类别 包括已停用
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryEmpTypeAll", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpTypeAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = budgSelectService.queryEmpTypeAll(mapVo);
		return subjType;
	}
	
	/**
	 * 职工类别下拉框
	 * @param mapVo 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgEmpType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgEmpType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String empType = budgSelectService.queryBudgEmpType(mapVo);
		return empType;
	}
	
	/**
	 * 职工名称下拉框
	 * @param mapVo 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgEmpName", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgEmpName(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String empName = budgSelectService.queryBudgEmpName(mapVo);
		return empName;
	}
	
	/**
	 * 计划类型下拉框
	 * @param mapVo 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgPlanType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPlanType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String plantype = budgSelectService.queryBudgPlanType(mapVo);
		return plantype;
	}
	/**
	 *  科室 下拉框 (其他费用预算使用   dept_no ) 为预算科室（IS_BUDG=1）且部门类型（dept_type）为01和02的部门
	 * @param mapVo 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		String budgDept = budgSelectService.queryBudgDept(mapVo);
		return budgDept;
	}
	
	/**
	 * 医疗收入 调整单号  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryIncomeAdjustCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryIncomeAdjustCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") ==  null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String adjCode = budgSelectService.queryIncomeAdjustCode(mapVo);

		return adjCode;

	}
	
	/**
	 * 其他收入 调整单号  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryElseIncomeAdjustCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryElseIncomeAdjustCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") ==  null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String adjCode = budgSelectService.queryElseIncomeAdjustCode(mapVo);

		return adjCode;

	}
	
	/**
	 * 采购资产性质  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryAssNaturesBuget", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssNaturesBuget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = budgSelectService.queryAssNaturesBuget(mapVo);

		return paymentItem;

	}
	
	/**
	 * 仓库  下拉框 不带NO
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryHosStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = budgSelectService.queryHosStoreDict(mapVo);

		return paymentItem;

	}
	
	/**
	 * 仓库  下拉框 带NO
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryHosStoreDictNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosStoreDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = budgSelectService.queryHosStoreDictNo(mapVo);

		return paymentItem;

	}
	
	/**
	 * 自定义分解系数下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/queryBudgResolveDataDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgResolveDataDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String resolve = budgSelectService.queryBudgResolveDataDict(mapVo);
		return resolve;

	}
	//查询收入预算科目类别
	@RequestMapping(value = "/hrp/budg/queryBudgTypeCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgTypeCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String resolve = budgSelectService.queryBudgTypeCode(mapVo);
		return resolve;

	}
	//查询支出预算科目类别
	@RequestMapping(value = "/hrp/budg/queryBudgCostTypeCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCostTypeCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String resolve = budgSelectService.queryBudgCostTypeCode(mapVo);
		
		return resolve;

	}
	//
	@RequestMapping(value = "/hrp/budg/queryBudgCPlan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryBudgCPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
if(mapVo.get("year") ==  null||mapVo.get("year").toString().equals("")){
			
			mapVo.put("year", SessionManager.getAcctYear());
		}
		String json = budgSelectService.queryBudgCPlan(mapVo);
		return json;

	}
	//
	//查询控制层次
		@RequestMapping(value = "/hrp/budg/queryContLSelect", method = RequestMethod.POST)
		@ResponseBody
		public String queryContLSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String resolve = budgSelectService.queryContLSelect(mapVo);
			
			return resolve;

		}
	//查询控制期间 
		@RequestMapping(value = "/hrp/budg/queryContPSelect", method = RequestMethod.POST)
		@ResponseBody
		public String queryContPSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String resolve = budgSelectService.queryContPSelect(mapVo);
			
			return resolve;

		}
	//查询控制方式 
		@RequestMapping(value = "/hrp/budg/queryContWSelect", method = RequestMethod.POST)
		@ResponseBody
		public String queryContWSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String resolve = budgSelectService.queryContWSelect(mapVo);
			
			return resolve;

		}
		//查询预算表
		
		@RequestMapping(value = "/hrp/budg/queryTabCode", method = RequestMethod.POST)
		@ResponseBody
		public String queryTabCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			if(mapVo.get("budg_year") ==  null||mapVo.get("budg_year").toString().equals("")){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
			}
			String resolve = budgSelectService.queryTabCode(mapVo);
			
			return resolve;

		}
  //查询控制模式
		
	@RequestMapping(value = "/hrp/budg/queryContMSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryContMSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	mapVo.put("group_id", SessionManager.getGroupId());
					
	mapVo.put("hos_id", SessionManager.getHosId());
					
	mapVo.put("copy_code", SessionManager.getCopyCode());
	if(mapVo.get("budg_year") ==  null||mapVo.get("budg_year").toString().equals("")){
						
			mapVo.put("budg_year", SessionManager.getAcctYear());
			}
	String resolve = budgSelectService.queryContMSelect(mapVo);
					
		return resolve;

	}
//查询关联节点
				
	@RequestMapping(value = "/hrp/budg/queryReLinkSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryReLinkSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
					
			mapVo.put("hos_id", SessionManager.getHosId());
					
			mapVo.put("copy_code", SessionManager.getCopyCode());
			if(mapVo.get("budg_year") ==  null||mapVo.get("budg_year").toString().equals("")){
						
				mapVo.put("budg_year", SessionManager.getAcctYear());
			}
			String resolve = budgSelectService.queryReLinkSelect(mapVo);
					
			return resolve;

		}
	//查询控制节点
				
	@RequestMapping(value = "/hrp/budg/queryContNoteSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryContNoteSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
					mapVo.put("group_id", SessionManager.getGroupId());
					
					mapVo.put("hos_id", SessionManager.getHosId());
					
					mapVo.put("copy_code", SessionManager.getCopyCode());
					if(mapVo.get("budg_year") ==  null||mapVo.get("budg_year").toString().equals("")){
						
						mapVo.put("budg_year", SessionManager.getAcctYear());
					}
					String resolve = budgSelectService.queryContNoteSelect(mapVo);
					
					return resolve;

				}
	//查询占用状态
				
	@RequestMapping(value = "/hrp/budg/queryUseStateSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryUseStateSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
					mapVo.put("group_id", SessionManager.getGroupId());
					
					mapVo.put("hos_id", SessionManager.getHosId());
					
					mapVo.put("copy_code", SessionManager.getCopyCode());
					if(mapVo.get("budg_year") ==  null||mapVo.get("budg_year").toString().equals("")){
						
						mapVo.put("budg_year", SessionManager.getAcctYear());
					}
					String resolve = budgSelectService.queryUseStateSelect(mapVo);
					
					return resolve;

				}
}
