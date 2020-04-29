package com.chd.hrp.htcg.controller.base;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.htcg.service.base.HtcgSelectDictService;


@Controller
public class HtcgSelectDictController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HtcgSelectDictController.class);
	@Resource(name = "htcgSelectDictService")
	private final HtcgSelectDictService htcgSelectDictService = null;

	
	// 是或否下拉框
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgYearOrNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcYearOrNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		    String result = htcgSelectDictService.queryHtcgYearOrNo(mapVo);
		    return result;
	}
	
	/**
	 * 医嘱分类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgRecipeTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgRecipeTypeDict(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgRecipeTypeDict(map);
		return result;
	}
	
	/**
	 * 医保类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgIdentityTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgIdentityTypeDict(
			@RequestParam Map<String, Object> entityMap, Model model)
			throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgIdentityTypeDict(entityMap);
		return result;
	}
	/**
	 * 诊断性质
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgIcd10NatureDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgIcd10NatureDict(@RequestParam Map<String, Object> map, Model model)throws Exception {

		String result = htcgSelectDictService.queryHtcgIcd10NatureDict(map);
		
		return result;
	}
	/**
	 * 诊断类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgIcd10TypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgIcd10TypeDict(@RequestParam Map<String, Object> map, Model model)throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgIcd10TypeDict(map);
		return result;
	}
	
	/**
	 * 麻醉种类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgAnestTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgAnestTypeDict(@RequestParam Map<String, Object> map, Model model)throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgAnestTypeDict(map);
		return result;
	}
	/**
	 * 转归字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgOutcomeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgOutcomeDict(@RequestParam Map<String, Object> map,Model model) throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgOutcomeDict(map);
		return result;
	}
	/**
	 * 药品类别
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgDrugTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgDrugTypeDict(
			@RequestParam Map<String, Object> map, Model model)
					throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgDrugTypeDict(map);
		return result;
	}
	/**
	 * 药品
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgDrugDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryVDrugDict(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgDrugDict(map);
		return result;
	}
	/**
	 * 诊疗项目
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryCostChargeItemArrtDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostChargeItemArrtDict(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryCostChargeItemArrtDict(map);
		return result;
	}
	/**
	 * 材料
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryhtcMaterialDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryhtcMaterialDict(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryhtcMaterialDict(map);
		return result;
	}
	/**
	 * 生产厂商
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHosFacDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosFacDict(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHosFacDict(map);
		return result;
	}
	
	/**
	 * 科室
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgDeptDict(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgDeptDict(map);
		return result;
	}
	
	/**
	 * 核算方案
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgSchemeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgSchemeDict(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgSchemeDict(map);
		return result;
	}
	
	/**
	 * 病种分类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgDrgsTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgDrgsTypeDict(@RequestParam Map<String, Object> map,Model model) throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgDrgsTypeDict(map);
		return result;
	}
	/**
	 * 病种字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgDrgsDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgDrgsDict(@RequestParam Map<String, Object> map, Model model) throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgDrgsDict(map);
		return result;
	}
	/**
	 * 诊断字典 
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgIcd10Dict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgIcd10Dict(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgIcd10Dict(map);
		return result;
	}
	/**
	 * 手术字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgIcd9Dict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgIcd9Dict(@RequestParam Map<String, Object> map,
			Model model) throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgIcd9Dict(map);
		return result;
	}
	
	/**
	 * ICD入组规则 (诊断,手术)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgIcdRuleDict", method = RequestMethod.POST)
	@ResponseBody    
	public String queryHtcgIcdRuleDict(@RequestParam Map<String, Object> map, Model model)throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgIcdRuleDict(map);
		return result;
	}
	
	/**
	 * 入组样本抽取规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgMrRuleDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMrRuleSelect(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		String result = htcgSelectDictService.queryHtcgMrRuleDict(map);
		return result;
	}
	
	/**
	 * 临床路径时程划分(期间)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgCipStepDateDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCipStepDateSelect(@RequestParam Map<String, Object> map, Model model)throws Exception {
		String result = htcgSelectDictService.queryHtcgCipStepDateDict(map);
		return result;
	}
	
	/**
	 * 临床路径时程划分(地点)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgCipStepPlaceDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCipStepPlaceSelect(@RequestParam Map<String, Object> map, Model model)throws Exception {
		String result = htcgSelectDictService.queryHtcgCipStepPlaceDict(map);
		return result;
	}
	/**
	 * 相似治疗效果合并规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgRecipeMergeRuleDict", method = RequestMethod.POST)
	@ResponseBody 
	public String queryRecipeMergeRuleSelect(@RequestParam Map<String, Object> map, Model model)throws Exception {
		String result = htcgSelectDictService.queryHtcgRecipeMergeRuleDict(map);
		return result;
	}
	/**
	 * 相似治疗效果项目性质
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgChargeNatureDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryChargeCostSelect(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		String result = htcgSelectDictService.queryHtcgChargeNatureDict(map);
		return result;
	}
	/**
	 * 医嘱项目准入规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgRecipeGroupRuleDict", method = RequestMethod.POST)
	@ResponseBody 
	public String queryRecipeGroupRuleSelect(@RequestParam Map<String, Object> map, Model model)throws Exception {
		String result = htcgSelectDictService.queryHtcgRecipeGroupRuleDict(map);
		return result;
	}
	/**
	 * 核算方案应用序号
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgSeqTableDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgSeqTableDict(
			@RequestParam Map<String, Object> map, Model model)
					throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgSeqTableDict(map);
		return result;
	}
	/**
	 * 期间类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgPeriodTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgPeriodTypeDict(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgPeriodTypeDict(map);
		return result;
	}
	/**
	 * 期间
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryHtcgPeriodDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcgPeriodDict(
			@RequestParam Map<String, Object> map, Model model)
			throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		String result = htcgSelectDictService.queryHtcgPeriodDict(map);
		return result;
	}
	/**
	 * 药品管理成本科室
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/base/queryDeptDrugAdminCostDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDrugAdminCostDict(@RequestParam Map<String, Object> map, Model model)throws Exception {
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("acc_year", SessionManager.getAcctYear());
		String result = htcgSelectDictService.queryDeptDrugAdminCostDict(map);
		return result;
	}
}
