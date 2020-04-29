/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

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
import com.chd.hrp.cost.serviceImpl.HrpCostSelectServiceImpl;
  
/** 
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class HrpCostSelectController extends BaseController {
 
	private static Logger logger = Logger.getLogger(HrpCostSelectController.class);

	@Resource(name = "hrpCostSelectService")
	private final HrpCostSelectServiceImpl hrpCostSelectService = null;

	@RequestMapping(value = "/hrp/cost/queryHosDeptLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String deptLevel = hrpCostSelectService.queryHosDeptLevel(mapVo);
		return deptLevel;
	}
	
	@RequestMapping(value = "/hrp/cost/queryIncomeType", method = RequestMethod.POST)
	@ResponseBody
	public String queryIncomeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String subjType = hrpCostSelectService.queryIncomeType(mapVo);
		return subjType;
	}

	@RequestMapping(value = "/hrp/cost/queryIncomeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryIncomeItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String yearMonth = hrpCostSelectService.queryIncomeItemArrt(mapVo);
		return yearMonth;
	}

	@RequestMapping(value = "/hrp/cost/queryChargeKindArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryChargeKindArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String yearMonth = hrpCostSelectService.queryChargeKindArrt(mapVo);
		return yearMonth;
	}
	
	@RequestMapping(value = "/hrp/cost/queryChargeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryChargeItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String yearMonth = hrpCostSelectService.queryChargeItemArrt(mapVo);
		return yearMonth;
	}
	
	
	@RequestMapping(value = "/hrp/cost/queryDeptLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String yearMonth = hrpCostSelectService.queryDeptLevel(mapVo);
		return yearMonth;
	}
	

	@RequestMapping(value = "/hrp/cost/queryMateTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryMateTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String mateTypeArrt = hrpCostSelectService.queryMateTypeArrt(mapVo);

		return mateTypeArrt;

	}

	@RequestMapping(value = "/hrp/cost/queryFassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryFassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String fassetTypeArrt = hrpCostSelectService.queryFassetTypeArrt(mapVo);

		return fassetTypeArrt;
	}

	@RequestMapping(value = "/hrp/cost/queryIassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryIassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String fassetTypeArrt = hrpCostSelectService.queryIassetTypeArrt(mapVo);

		return fassetTypeArrt;
	}

	@RequestMapping(value = "/hrp/cost/queryEmpTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String empTypeArrt = hrpCostSelectService.queryEmpTypeArrt(mapVo);
		return empTypeArrt;
	}

	@RequestMapping(value = "/hrp/cost/queryEmpArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String empArrt = hrpCostSelectService.queryEmpArrt(mapVo);
		return empArrt;
	}
	
	@RequestMapping(value = "/hrp/cost/queryCostEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String empArrt = hrpCostSelectService.queryCostEmpDict(mapVo);
		return empArrt;
	}

	@RequestMapping(value = "/hrp/cost/queryWageItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryWageItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String wageItemArrt = hrpCostSelectService.queryWageItemArrt(mapVo);
		return wageItemArrt;
	}

	@RequestMapping(value = "/hrp/cost/queryEmpTitleArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpTitleArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String empTitleArrt = hrpCostSelectService.queryEmpTitleArrt(mapVo);
		return empTitleArrt;
	}

	@RequestMapping(value = "/hrp/cost/queryDeptNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String subjType = hrpCostSelectService.queryDeptNature(mapVo);
		return subjType;
	}
	
	//成本项目来源
	@RequestMapping(value = "/hrp/cost/queryDataSource", method = RequestMethod.POST)
	@ResponseBody
	public String queryDataSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String subjType = hrpCostSelectService.queryDataSource(mapVo);
		return subjType;
	}

	@RequestMapping(value = "/hrp/cost/queryItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String yearMonth = hrpCostSelectService.queryItemDict(mapVo);
		JSONArray json = JSONArray.parseArray(yearMonth);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		return json.toString();
	}
	@RequestMapping(value = "/hrp/cost/queryItemDictLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryItemDictLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String yearMonth = hrpCostSelectService.queryItemDictLast(mapVo);
		JSONArray json = JSONArray.parseArray(yearMonth);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		return json.toString();
	}

	@RequestMapping(value = "/hrp/cost/queryDeptTypeDictNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptTypeDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String yearMonth = hrpCostSelectService.queryDeptTypeDictNo(mapVo);
		return yearMonth;
	}

	@RequestMapping(value = "/hrp/cost/queryItemDictNoItemGrade", method = RequestMethod.POST)
	@ResponseBody
	public String queryItemDictNoItemGrade(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		String ItemDictNoItemGrade = hrpCostSelectService.queryItemDictNoItemGrade(mapVo);
		return ItemDictNoItemGrade;
	}
	
	@RequestMapping(value = "/hrp/cost/queryItemDictNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryItemDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String itemDictNo = hrpCostSelectService.queryItemDictNo(mapVo);

		return itemDictNo;
	}
	@RequestMapping(value = "/hrp/cost/queryItemDictNoLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryItemDictNoLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String itemDictNo = hrpCostSelectService.queryItemDictNoLast(mapVo);
		
		return itemDictNo;
	}

	@RequestMapping(value = "/hrp/cost/queryBonusItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryBonusItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String bonusItem = hrpCostSelectService.queryBonusItemArrt(mapVo);
		return bonusItem;
	}

	@RequestMapping(value = "/hrp/cost/queryWangSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryWangSchemeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String bonusItem = hrpCostSelectService.queryWangSchemeSet(mapVo);
		return bonusItem;
	}
	
	@RequestMapping(value = "/hrp/cost/queryBonusSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryBonusSchemeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String bonusItem = hrpCostSelectService.queryBonusSchemeSet(mapVo);
		return bonusItem;
	}
	
	/**
	 * 科室变更字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryDeptDictNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String deptDict = hrpCostSelectService.queryDeptDictNo(mapVo);
		return deptDict;
	}
	/**
	 * 科室变更字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryDeptDictNoLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictNoLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String deptDict = hrpCostSelectService.queryDeptDictNoLast(mapVo);
		return deptDict;
	}
	/**
	 * 科室变更字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryDeptDictCodeLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictCodeLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String deptDict = hrpCostSelectService.queryDeptDictCodeLast(mapVo);
		return deptDict;
	}
	/**
	 * 科室变更字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryDeptDictCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String deptDictcode = hrpCostSelectService.queryDeptDictCode(mapVo);
		return deptDictcode;
	}
	
	/**
	 * 病人类别字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryCostPatientTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostPatientTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String costPatientTypeDict = hrpCostSelectService.queryCostPatientTypeDict(mapVo);
		return costPatientTypeDict;
	}
	
	/**
	 * 分摊参数下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryDeptParaDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptParaDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String deptParaDict = hrpCostSelectService.queryDeptParaDict(mapVo);
		return deptParaDict;
	}
	
	/**
	 * 材料信息字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryMateArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryMateArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String mateArrt = hrpCostSelectService.queryMateArrt(mapVo);
		return mateArrt;
	}
	
	/**
	 * 无形资产字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryIassetArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryIassetArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String iassetArrt = hrpCostSelectService.queryIassetArrt(mapVo);
		return iassetArrt;
	}
	
	/**
	 * 无形资产字典查分类下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryIassetArrtType", method = RequestMethod.POST)
	@ResponseBody
	public String queryIassetArrtType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String iassetArrt = hrpCostSelectService.queryIassetArrtType(mapVo);
		return iassetArrt;
	}
	
	/**
	 * 固定资产字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryFassetArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryFassetArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String fassetArrt = hrpCostSelectService.queryFassetArrt(mapVo);
		return fassetArrt;
	}
	
	
	/**
	 * 固定资产字典查分类
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryFassetArrtType", method = RequestMethod.POST)
	@ResponseBody
	public String queryFassetArrtType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String fassetArrtType = hrpCostSelectService.queryFassetArrtType(mapVo);
		
		return fassetArrtType;
	}
	
	/**
	 * 资金来源字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/querySourceArrt", method = RequestMethod.POST)
	@ResponseBody
	public String querySourceArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String sourceArrt = hrpCostSelectService.querySourceArrt(mapVo);
		return sourceArrt;
	}
	/**
	 * 服务项目字典下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryServerItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryServerItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String serverItemDict = hrpCostSelectService.queryServerItemDict(mapVo);
		
		return serverItemDict;
	}
	
	/**
	 * 科室类型下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryCostDeptKindDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostDeptKindDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String DeptKindDict = hrpCostSelectService.queryCostDeptKindDict(mapVo);
		
		return DeptKindDict;
	}
	
	/**
	 * 科室性质下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryCostDeptNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String DeptNature = hrpCostSelectService.queryCostDeptNature(mapVo);
		
		return DeptNature;
	}

	
	
	/**
	 * 收入项目下拉狂
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryCostIncomeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostIncomeItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String DeptNature = hrpCostSelectService.queryCostIncomeItemArrt(mapVo);
		
		return DeptNature;
	}

	
	/**
	 * 2016/10/25 lxj
	 * 材料分类：上级编码下拉框
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryMateTypeSupperCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryMateTypeSupperCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String supperCode = hrpCostSelectService.queryMateTypeSupperCode(mapVo);
		JSONArray supperCodeJson = JSONArray.parseArray(supperCode);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		supperCodeJson.add(0, jsonObj);
		return supperCodeJson.toString();
	}
	
	/**
	 * 2016/10/25 lxj
	 * 固定资产分类：上级编码下拉框
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryFassetTypeSupperCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryFassetTypeSupperCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String supperCode = hrpCostSelectService.queryFassetTypeSupperCode(mapVo);
		JSONArray supperCodeJson = JSONArray.parseArray(supperCode);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		supperCodeJson.add(0, jsonObj);
		return supperCodeJson.toString();
	}
	
	/**
	 * 2016/10/25 lxj
	 * 无形资产分类：上级编码下拉框
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/queryIassetTypeSupperCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryIassetTypeSupperCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String supperCode = hrpCostSelectService.queryIassetTypeSupperCode(mapVo);
		JSONArray supperCodeJson = JSONArray.parseArray(supperCode);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		supperCodeJson.add(0, jsonObj);
		return supperCodeJson.toString();
	}
	
	@RequestMapping(value = "/hrp/cost/queryParaType", method = RequestMethod.POST)
	@ResponseBody
	public String queryParaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String supperCode = hrpCostSelectService.queryParaType(mapVo);
		JSONArray supperCodeJson = JSONArray.parseArray(supperCode);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		supperCodeJson.add(0, jsonObj);
		return supperCodeJson.toString();
	}
	
	@RequestMapping(value = "/hrp/cost/queryParaTypeLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryParaTypeLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String supperCode = hrpCostSelectService.queryParaType(mapVo);
		JSONArray supperCodeJson = JSONArray.parseArray(supperCode);
		return supperCodeJson.toString();
	}
	@RequestMapping(value = "/hrp/cost/queryParaNatur", method = RequestMethod.POST)
	@ResponseBody
	public String queryParaNatur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String supperCode = hrpCostSelectService.queryParaNatur(mapVo);
		JSONArray supperCodeJson = JSONArray.parseArray(supperCode);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		supperCodeJson.add(0, jsonObj);
		return supperCodeJson.toString();
	}
	@RequestMapping(value = "/hrp/cost/queryParaNaturLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryParaNaturLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String supperCode = hrpCostSelectService.queryParaNatur(mapVo);
		JSONArray supperCodeJson = JSONArray.parseArray(supperCode);
		return supperCodeJson.toString();
	}
	
	
	@RequestMapping(value = "/hrp/cost/queryCostSubjItemMapByItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostSubjItemMapByItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrpCostSelectService.queryCostSubjItemMapByItem(mapVo);
		return json;
	}
	
	@RequestMapping(value = "/hrp/cost/queryCostSubjItemMapBySubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostSubjItemMapBySubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
		String json = hrpCostSelectService.queryCostSubjItemMapBySubj(mapVo);
		return json;
	}
	
	@RequestMapping(value = "/hrp/cost/queryCostUserDefinedParaTarget", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostUserDefinedParaTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
		String json = hrpCostSelectService.queryCostUserDefinedParaTarget(mapVo);
		
		return json;
	}
	@RequestMapping(value = "/hrp/cost/queryItemDictCodeLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryItemDictCodeLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
		String json = hrpCostSelectService.queryItemDictCodeLast(mapVo);
		
		return json;
	}
}
