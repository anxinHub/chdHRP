/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.ass.controller.card;

import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.base.SessionManager;
import com.chd.hrp.ass.serviceImpl.HrpAssSelectServiceImpl;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo 
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class HrpAssCardSelectController extends BaseController {    

	private static Logger logger = Logger.getLogger(HrpAssCardSelectController.class);

	@Resource(name = "hrpAssSelectService")
	private final HrpAssSelectServiceImpl hrpAssSelectService = null;

	/**
	 * 资产分类下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssTypeDict(mapVo);
	}
	
	@RequestMapping(value = "/hrp/ass/card/queryMatStockEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStockEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpAssSelectService.queryMatStockEmp(mapVo);
		return hrpMatSelect;
	}
	
	
	@RequestMapping(value = "/hrp/ass/card/queryAssFinaType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatFinaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryMatFinaType(mapVo);
	}

	/**
	 * 资产分类下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssTypeSuperCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssTypeSuperCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssTypeSuperCode(mapVo);
	}

	/**
	 * 仓库下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryHosStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryHosStoreDict(mapVo);
	}

	/**
	 * 生产厂商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryHosFacDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryHosFacDict(mapVo);
	}

	/**
	 * 注册证号下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryCertNo", method = RequestMethod.POST)
	@ResponseBody 
	public String queryCertNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryCertNo(mapVo);
	}
	
	/**
	 * 供应商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryHosSupDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryHosSupDict(mapVo);
	}

	/**
	 * 资产用途
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssUsageDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssUsageDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssUsageDict(mapVo);
	}
	
	/**
	 * 项目
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssProjDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssProjDict(mapVo);
	}
	

	/**
	 * 供应商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssDepreMethodDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDepreMethodDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssDepreMethodDict(mapVo);
	}

	/**
	 * 资产字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssNoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssNoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssNoDict(mapVo);
	}

	@RequestMapping(value = "/hrp/ass/card/queryAssStructDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssStructDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssStructDict(mapVo);
	}

	@RequestMapping(value = "/hrp/ass/card/queryAssLandSourceDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssLandSourceDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssLandSourceDict(mapVo);
	}

	@RequestMapping(value = "/hrp/ass/card/queryAssPropDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssPropDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssPropDict(mapVo);
	}

	@RequestMapping(value = "/hrp/ass/card/queryAssRelicGradeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRelicGradeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssRelicGradeDict(mapVo);
	}

	@RequestMapping(value = "/hrp/ass/card/queryUserDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryUserDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryUserDict(mapVo);
	}

	@RequestMapping(value = "/hrp/ass/card/queryAssInMainDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssInMainDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssInMainDict(mapVo);
	}

	@RequestMapping(value = "/hrp/ass/card/queryAssBarTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssBarTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssBarTypeDict(mapVo);
	}

	@RequestMapping(value = "/hrp/ass/card/queryAssCardUseStateDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssCardUseStateDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssCardUseStateDict(mapVo);
	}
	
	@RequestMapping(value = "/hrp/ass/card/queryAssDisposeTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDisposeTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssDisposeTypeDict(mapVo);
	}

	/**
	 * 资产字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssNoDictTable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssNoDictTable(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssNoDictTable(mapVo);

		return JSONObject.parseObject(subjType);
	}

	/**
	 * 资产字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryDeptDict(mapVo);
	}

	/**
	 * 资产字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryHeadEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryHeadEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryHeadEmp(mapVo);
	}

	/**
	 * 合同下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryContractMain", method = RequestMethod.POST)
	@ResponseBody
	public String queryContractMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryContractMain(mapVo);
	}

	/**
	 * 资产性质下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssNaturs", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssNaturs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("mod_code", SessionManager.getModCode());
		return hrpAssSelectService.queryAssNaturs(mapVo);
	}

	/**
	 * 资产业务类型下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssBusType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssBusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssBusType(mapVo);
	}


	/**
	 * 申请单号
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssApplyNoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssApplyNoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssApplyNoDict(mapVo);
	}

	/**
	 * 保养计划
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssMaintainPlanDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssMaintainPlanDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssMaintainPlanDict(mapVo);
	}

	/**
	 * 保养项目字典下拉框检索，表格形式
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssMaintainItemTable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainItemTable(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssMaintainItemTable(mapVo);

		return JSONObject.parseObject(subjType);
	}

	@RequestMapping(value = "/hrp/ass/card/queryAssYesAndNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssYesAndNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		JSONArray json = new JSONArray();
		String str1 = "{\"id\":\"0\",\"text\":\"否\"}";
		String str2 = "{\"id\":\"1\",\"text\":\"是\"}";
		JSONObject jsonObj1 = JSONArray.parseObject(str1);
		JSONObject jsonObj2 = JSONArray.parseObject(str2);
		json.add(0, jsonObj1);
		json.add(1, jsonObj2);
		logger.warn("json:" + json.toString());
		return json.toString();
	}
	
	@RequestMapping(value = "/hrp/ass/card/queryAssIsDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssIsDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssIsDept(mapVo);
	}

	/**
	 * 计量单位
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/card/queryHosUnitDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosUnitDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryHosUnitDict(mapVo);
	}
	
	/**
	 * 国标码
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/card/queryAssGBDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssGBDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssGBDict(mapVo);
	}
	
	@RequestMapping(value = "/hrp/ass/card/queryAssMeasureKingDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssMeasureKingDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssMeasureKingDict(mapVo);
	}
	
	
	@RequestMapping(value = "/hrp/ass/card/queryAssMeasureType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssMeasureType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		JSONArray json = new JSONArray();
		String str1 = "{\"id\":\"0\",\"text\":\"A类\"}";
		String str2 = "{\"id\":\"1\",\"text\":\"B类\"}";
		String str3 = "{\"id\":\"2\",\"text\":\"C类\"}";
		JSONObject jsonObj1 = JSONArray.parseObject(str1);
		JSONObject jsonObj2 = JSONArray.parseObject(str2);
		JSONObject jsonObj3 = JSONArray.parseObject(str3);
		json.add(0, jsonObj1);
		json.add(1, jsonObj2);
		json.add(1, jsonObj3);
		return json.toString();
	}

	@RequestMapping(value = "/hrp/ass/card/queryAssTypeSixEight", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssTypeSixEight(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssTypeSixEight(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"全部\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}
	}
}
