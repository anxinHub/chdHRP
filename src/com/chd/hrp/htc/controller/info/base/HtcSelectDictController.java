package com.chd.hrp.htc.controller.info.base;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.htc.service.info.base.HtcSelectDictService;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcSelectDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcSelectDictController.class);

	@Resource(name = "htcSelectDictService")
	private final HtcSelectDictService htcSelectDictService = null;

	/*成本项目-上级项目*/
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcItemSuppDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcItemSuppDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcItemSuppDict = htcSelectDictService.queryHtcItemSuppDict(mapVo);
		return htcItemSuppDict;
	}
	
	// 成本项目字典表
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcCostItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	    String htcCostItemDict = htcSelectDictService.queryHtcCostItemDict(mapVo);
	    return htcCostItemDict;
	}
		
		
	//资金来源字典表
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcSourceDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcSourceDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
            mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
		    String queryHtcSourceDict = htcSelectDictService.queryHtcSourceDict(mapVo);
		    return queryHtcSourceDict;
	}
    //成本习性
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcDeptNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String htcDeptNature = htcSelectDictService.queryHtcDeptNature(mapVo);
		return htcDeptNature;
	}
		
	//成本项目来源
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcDataSource", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcDataSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcDataSource = htcSelectDictService.queryHtcDataSource(mapVo);
		return htcDataSource;
	}
	
	// 成本项目级次
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcItemGrade", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcItemGrade(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String HtcItemGrade = htcSelectDictService.queryHtcItemGrade(mapVo);	   
	    return HtcItemGrade;
	}
	
	// 是或否下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcYearOrNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcYearOrNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String HtcYearOrNo = htcSelectDictService.queryHtcYearOrNo(mapVo);
		return HtcYearOrNo;
	}
	
	// 成本分类
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcDeptTypeDictNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptTypeDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcDeptTypeDictNo = htcSelectDictService.queryHtcDeptTypeDictNo(mapVo);
		return htcDeptTypeDictNo;
	}
	
	//成本项目分摊类型
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcParaType", method = RequestMethod.POST)
	@ResponseBody
	public String queryParaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcParaType = htcSelectDictService.queryHtcParaType(mapVo);
		return htcParaType;
	}
	
	
	//收入类别
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcIncomeType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcIncomeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcIncomeType = htcSelectDictService.queryHtcIncomeType(mapVo);
		return htcIncomeType;
	}
		
	
	//收入项目 (门诊|住院)
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcIncomeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcIncomeItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcIncomeItemDict = htcSelectDictService.queryHtcIncomeItemDict(mapVo);
		return htcIncomeItemDict;
	}
	
	// 收费类别字典表
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcChargeKindArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcChargeKindArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		    String htcChargeKindArrt = htcSelectDictService.queryHtcChargeKindArrt(mapVo);
		    return htcChargeKindArrt;
	}
			
   // 收费项目字典表
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcChargeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcChargeItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
            mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		    String htcChargeItemArrt = htcSelectDictService.queryHtcChargeItemArrt(mapVo);
		    return htcChargeItemArrt;
	}
		
	// 科室性质
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcDeptNatur", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcDeptNatur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcDeptNatur = htcSelectDictService.queryHtcDeptNatur(mapVo);
		return htcDeptNatur;
	}
	// 科室类型
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcDeptType", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcDeptType = htcSelectDictService.queryHtcDeptType(mapVo);
		return htcDeptType;
	}
	// 科室级次
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcDeptLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("user_id", SessionManager.getUserId());
		  String htcDeptLevel = htcSelectDictService.queryHtcDeptLevel(mapVo);
		   return htcDeptLevel;
	}
	// 科室分类
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
		   String htcDeptKind = htcSelectDictService.queryHtcDeptKind(mapVo);
		   return htcDeptKind;
	}
	
	// 查询科室字典表
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcDeptDict = htcSelectDictService.queryHtcDeptDict(mapVo);
		return htcDeptDict;
	}
	// 核算科室
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcProjDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcProjDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcProjDeptDict = htcSelectDictService.queryHtcProjDeptDict(mapVo);
		return htcProjDeptDict;
	}
	// 方案核算方法
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcCheckMethod", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcCheckMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcCheckMethod= htcSelectDictService.queryHtcCheckMethod(mapVo);	
		return htcCheckMethod;
	}
	// 方案下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcPlan", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (null == mapVo.get("acc_year")) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		String htcPlan = htcSelectDictService.queryHtcPlan(mapVo);
		return htcPlan;
	}
	
	// 职称下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcPeopleTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcPeopleTitleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcPeopleTitleDict = htcSelectDictService.queryHtcPeopleTitleDict(mapVo);
		return htcPeopleTitleDict;
	}
	
	// 人员类别下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcPeopleTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcPeopleTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcPeopleTypeDict = htcSelectDictService.queryHtcPeopleTypeDict(mapVo);
		return htcPeopleTypeDict;
	}
	
	// 人员查询下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcPeopleDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcPeopleDictAllDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcPeopleDict = htcSelectDictService.queryHtcPeopleDict(mapVo);
		return htcPeopleDict;
	}
	
	// 工作项类下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcWageItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcWageItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcWageItemDict = htcSelectDictService.queryHtcWageItemDict(mapVo);
		return htcWageItemDict;
	}
	
	// 奖金下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcBonusItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcBonusItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcBonusItemDict = htcSelectDictService.queryHtcBonusItemDict(mapVo);
		return htcBonusItemDict;
	}
	// 资产分类下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcFassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcFassetTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcFassetTypeDict = htcSelectDictService.queryHtcFassetTypeDict(mapVo);
		return htcFassetTypeDict;
	}
	
	// 固定资产资产信息字典
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcFassetDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcFassetDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcFassetDict = htcSelectDictService.queryHtcFassetDict(mapVo);
		return htcFassetDict;
	}
	
	// 无形资产资产信息字典
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcIassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcIassetTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcIassetTypeDict = htcSelectDictService.queryHtcIassetTypeDict(mapVo);
		return htcIassetTypeDict;

	}
	// 无形资产资产信息字典
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcIassetDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcIassetDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcIassetDict = htcSelectDictService.queryHtcIassetDict(mapVo);
		return htcIassetDict;
	}
	// 材料分类字典下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcMaterialTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcMaterialTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcMaterialTypeDict = htcSelectDictService.queryHtcMaterialTypeDict(mapVo);
		return htcMaterialTypeDict;
	}
	// 材料信息字典下拉框
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcMaterialDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcMaterialDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcMaterialDict = htcSelectDictService.queryHtcMaterialDict(mapVo);
		return htcMaterialDict;
	}
	//单位
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcHosUnitDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcHosUnitDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcMaterialTypeDict = htcSelectDictService.queryHtcHosUnitDict(mapVo);
		return htcMaterialTypeDict;
	}
	//生产厂商
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcHosFacDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcHosFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcMaterialTypeDict = htcSelectDictService.queryHtcHosFacDict(mapVo);
		return htcMaterialTypeDict;
	}
	
	// 资源动因
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcResCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcResCauseDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcWorkTypeDict = htcSelectDictService.queryHtcResCauseDict(mapVo);
		return htcWorkTypeDict;
	}
	
	// 作业动因
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcWorkCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcWorkCauseDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcWorkTypeDict = htcSelectDictService.queryHtcWorkCauseDict(mapVo);
		return htcWorkTypeDict;
	}
	// 作业分类
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcWorkTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcWorkTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcWorkTypeDict = htcSelectDictService.queryHtcWorkTypeDict(mapVo);
		return htcWorkTypeDict;
	}
	// 作业字典
	@RequestMapping(value = "/hrp/htc/info/base/queryHtcWorkDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcWorkDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcWorkDict = htcSelectDictService.queryHtcWorkDict(mapVo);
		return htcWorkDict;
	}

	

}
