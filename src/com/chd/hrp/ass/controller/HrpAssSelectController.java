/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.ass.controller;

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
import com.chd.hrp.ass.service.HrpAssSelectService;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */ 
@Controller
public class HrpAssSelectController extends BaseController {  
 
	private static Logger logger = Logger.getLogger(HrpAssSelectController.class);

	@Resource(name = "hrpAssSelectService")
	private final HrpAssSelectService hrpAssSelectService = null; 

	/**
	 * 资产分类下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());

		String subjType = hrpAssSelectService.queryAssTypeDict(mapVo);
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
	/**
	 * 固定资产维修材料
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssInvArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssInvArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		String subjType = hrpAssSelectService.queryAssInvArrt(mapVo);
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

	/**
	 * 科室的tree
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryHosDeptTerr", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject queryHosDeptTerr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String subjType = hrpAssSelectService.queryHosDeptTerr(mapVo);
			return JSONObject.parseObject(subjType);
		}
		
	/**
	 * 是否停用 yesOrNo
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryAssYesAndNo", method = RequestMethod.POST)
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
	/**
	 * 资产分类下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssTypeDictIsLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssTypeDictIsLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());

		String subjType = hrpAssSelectService.queryAssTypeDictIsLast(mapVo);
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

	@RequestMapping(value = "/hrp/ass/queryAssDeperMethodDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDeperMethodDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssDeperMethodDict(mapVo);
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

	/**
	 * 资产分类下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssTypeSuperCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssTypeSuperCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssTypeSuperCode(mapVo);
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

	/**
	 * 仓库下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryHosStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String subjType = hrpAssSelectService.queryHosStoreDict(mapVo);
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

	/**
	 * 查询移出仓库（资产转移 集团调拨使用）
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryOutHosStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryOutHosStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("user_id", SessionManager.getUserId());

		String subjType = hrpAssSelectService.queryAllotHosStoreDict(mapVo);

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

	/**
	 * 查询移入仓库（资产转移 集团调拨使用）
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryInHosStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryInHosStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAllotHosStoreDict(mapVo);
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

	// 主表 库房是否停用
	@RequestMapping(value = "/hrp/ass/queryMatYearOrNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatYearOrNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpAssSelectService.queryMatYearOrNo(mapVo);
		return hrpMatSelect;
	}

	// 库房分类 queryMatStoreType
	@RequestMapping(value = "/hrp/ass/queryMatStoreType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpAssSelectService.queryMatStoreType(mapVo);
		return hrpMatSelect;
	}

	// 职能科室
	@RequestMapping(value = "/hrp/ass/queryMatDeptIsManager", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatDeptIsManager(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpAssSelectService.queryMatDeptIsManager(mapVo);
		return hrpMatSelect;
	}

	// 普通职工/领料人 --查员工表
	@RequestMapping(value = "/hrp/ass/queryMatEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpAssSelectService.queryMatEmp(mapVo);
		return hrpMatSelect;
	}

	// 采购人
	@RequestMapping(value = "/hrp/ass/queryMatStockEmp", method = RequestMethod.POST)
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

	// 库房管理员
	@RequestMapping(value = "/hrp/ass/queryMatManagerEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatManagerEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpAssSelectService.queryMatManagerEmp(mapVo);
		return hrpMatSelect;
	}

	/**
	 * 生产厂商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryHosFacDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryHosFacDict(mapVo);
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

	// 生产厂商
	@RequestMapping(value = "/hrp/ass/queryHosFac", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosFac(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpAssSelectService.queryHosFac(mapVo);
		return hrpMatSelect;
	}

	/**
	 * 生产厂商类别下拉
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryFacTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryFacTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		String facType = hrpAssSelectService.queryFacTypeDict(mapVo);
		return facType;

	}

	/**
	 * 采购发票 付款条件下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryMatPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpAssSelectService.queryMatPayTerm(mapVo);
		return hrpMatSelect;
	}

	/**
	 * 供应商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryHosSupDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryHosSupDict(mapVo);
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
	
	/**
	 * 采购入库供应商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryHosSupDictNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSupDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String subjType = hrpAssSelectService.queryHosSupDictNo(mapVo);
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

	/**
	 * 资产用途
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssUsageDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssUsageDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssUsageDict(mapVo);
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

	/**
	 * 项目
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssProjDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssProjDict(mapVo);
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

	/**
	 * 供应商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssDepreMethodDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDepreMethodDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssDepreMethodDict(mapVo);
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

	// 供应商类别下拉
	@RequestMapping(value = "/hrp/ass/queryassSupTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySupTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		String supType = hrpAssSelectService.querySupTypeDict(mapVo);
		return supType;

	}
	/**
	 * 资产字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssNoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssNoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssNoDict(mapVo);
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

	/**
	 * 资产字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssNoDictTable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssNoDictTable(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssNoDictTable(getPage(mapVo));

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
	@RequestMapping(value = "/hrp/ass/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		
		String key = "";
		if(mapVo.get("key") != null){
			key = mapVo.get("key").toString();
			key = key.toUpperCase();
			mapVo.put("key", key);
		}
		
		
		
		String subjType = hrpAssSelectService.queryDeptDict(mapVo);
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

	/**
	 * 资产字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryHeadEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryHeadEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryHeadEmp(mapVo);
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

	/**
	 * 合同下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryContractMain", method = RequestMethod.POST)
	@ResponseBody
	public String queryContractMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryContractMain(mapVo);
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

	/**
	 * 资产性质下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssNaturs", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssNaturs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		
		mapVo.put("mod_code", SessionManager.getModCode());
		
		String subjType = hrpAssSelectService.queryAssNaturs(mapVo);
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

	/**
	 * 资产业务类型下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssBusType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssBusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssBusType(mapVo);
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

	/**
	 * 申请单号
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssApplyNoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssApplyNoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssApplyNoDict(mapVo);
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

	/**
	 * 保养计划
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssMaintainPlanDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssMaintainPlanDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssMaintainPlanDict(mapVo);
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

	/**
	 * 保养项目字典下拉框检索，表格形式
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssMaintainItemTable", method = RequestMethod.POST)
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
	
	/**
	 * 保养项目字典下拉框检索，表格形式
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssMaintainItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssMaintainItem(mapVo);

		return JSONObject.parseObject(subjType);
	}

	/**
	 * 计量计划
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssMeasurePlanDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssMeasurePlanDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssMeasurePlanDict(mapVo);
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

	/**
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssCardTable", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssCardTable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssCardTable(getPage(mapVo));
		return subjType;
	}
	
	@RequestMapping(value = "/hrp/ass/queryPayStageGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryPayStageGrid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryPayStageGrid(mapVo);
		return subjType;
	}
	
	
	@RequestMapping(value = "/hrp/ass/queryBillDetailGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryBillDetailGrid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryBillDetailGrid(getPage(mapVo));
		return subjType;
	}
	
	@RequestMapping(value = "/hrp/ass/queryBackBillDetailGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryBackBillDetailGrid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryBackBillDetailGrid(mapVo);
		return subjType;
	}
	

	/**
	 * 资金来源字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/querySourceDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySourceDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.querySourceDict(mapVo);
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
	
	/**
	 * 设备来源字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryDeviceDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeviceDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryDeviceDict(mapVo);
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

	/**
	 * 资金来源字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryBuyDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBuyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryBuyDict(mapVo);
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


	/**
	 * 计量单位字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryHosUnitDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosUnitDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String hosUnit = hrpAssSelectService.queryHosUnitDict(mapVo);
		JSONArray json = JSONArray.parseArray(hosUnit);
		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"全部\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}
	}

	/**
	 * 集团对应医院字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryHosInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		String hosUnit = hrpAssSelectService.queryHosInfoDict(mapVo);
		JSONArray json = JSONArray.parseArray(hosUnit);
		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"全部\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}
	}

	@RequestMapping(value = "/hrp/ass/queryHosCopyDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosCopyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		String hosUnit = hrpAssSelectService.queryHosCopyDict(mapVo);
		JSONArray json = JSONArray.parseArray(hosUnit);
		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"全部\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}
	}

	/**
	 * 集团字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryGroupDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryGroupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		String hosUnit = hrpAssSelectService.queryGroupDict(mapVo);
		JSONArray json = JSONArray.parseArray(hosUnit);
		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"全部\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}
	}

	@RequestMapping(value = "/hrp/ass/queryContractMainDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryContractMainDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("user_id", SessionManager.getUserId());
		String hosUnit = hrpAssSelectService.queryContractMainDict(mapVo);
		JSONArray json = JSONArray.parseArray(hosUnit);
		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"全部\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}
	}

	/**
	 * 财务分类
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryMatFinaType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatFinaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryMatFinaType(mapVo);
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

	/**
	 * 财务分类(cjc)
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryMatFinaTypes", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatFinaTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryMatFinaTypes(mapVo);
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

	@RequestMapping(value = "/hrp/ass/queryDeptDictInitValue", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictInitValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryDeptDictInitValue(mapVo);
	}

	// 查询验收项目
	@RequestMapping(value = "/hrp/ass/queryAssAcceptItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAcceptItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());

		return JSONObject.parseObject(hrpAssSelectService.queryAssAcceptItem(mapVo));
	}

	@RequestMapping(value = "/hrp/ass/queryAssFileTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssFileTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		return hrpAssSelectService.queryAssFileTypeDict(mapVo);
	}

	@RequestMapping(value = "/hrp/ass/queryAssNoDictTree", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssNoDictTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssNoDictTree(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		String str = "{\"id\":\"-1\",\"text\":\"全部折叠\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		return json.toString();
	}

	/**
	 * 资产分类快速查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryAssFinaDictTree", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssFinaDictTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssFinaDictTree(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		String str = "{\"id\":\"-1\",\"text\":\"全部折叠\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		return json.toString();
	}
	@RequestMapping(value = "/hrp/ass/queryAssStructDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssStructDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssStructDict(mapVo);
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

	@RequestMapping(value = "/hrp/ass/queryAssLandSourceDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssLandSourceDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssLandSourceDict(mapVo);
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

	@RequestMapping(value = "/hrp/ass/queryAssPropDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssPropDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssPropDict(mapVo);
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

	@RequestMapping(value = "/hrp/ass/queryAssRelicGradeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRelicGradeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssRelicGradeDict(mapVo);
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

	@RequestMapping(value = "/hrp/ass/queryUserDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryUserDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		//mapVo.put("user_id", SessionManager.getUserId()); 
		String subjType = hrpAssSelectService.queryUserDict(mapVo);
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
	
	@RequestMapping(value = "/hrp/ass/queryAccEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccEmpAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String subjType = hrpAssSelectService.queryAccEmpAttr(mapVo);
		return subjType;
	}
	

	@RequestMapping(value = "/hrp/ass/queryAssInMainDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssInMainDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssInMainDict(mapVo);
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

	@RequestMapping(value = "/hrp/ass/queryAssBarTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssBarTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssBarTypeDict(mapVo);
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

	@RequestMapping(value = "/hrp/ass/queryAssCardUseStateDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssCardUseStateDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssCardUseStateDict(mapVo);
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

	@RequestMapping(value = "/hrp/ass/queryAssDisposeTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDisposeTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssDisposeTypeDict(mapVo);
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

	/**
	 * 财务分类
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryMatFinaTypeIsLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatFinaTypeIsLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryMatFinaTypeIsLast(mapVo);
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

	/**
	 * 业务类型
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryBusType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryBusType(mapVo);
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
	
	@RequestMapping(value = "/hrp/ass/queryAccPayType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAccPayType(mapVo);
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
	/**
	 * 维修班组下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryRepTeam", method = RequestMethod.POST)
	@ResponseBody
	public String queryRepTeam(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryRepTeam(mapVo);
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
	
	/**
	 * 卡片下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryAssCardNoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssCardNoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssCardNoDict(mapVo);
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
	/**
	 * 维修申请资产卡片下拉框表单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryAssCardNoDictTable", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssCardNoDictTable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssCardNoDictTable(getPage(mapVo));
		return subjType;
	}
	/**
	 * 维修申请资产卡片下拉框表单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryAssCardNoDictSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssCardNoDictSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssCardNoDictSelect(mapVo);
		return subjType;
	}
	
	
	
	
	/**
	 * 资产分类快速查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryAssTypeDictTree", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssTypeDictTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssTypeDictTree(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		String str = "{\"id\":\"-1\",\"text\":\"全部折叠\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		return json.toString();
	}
	/**
	 * 上级位置下拉
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/querySuperLocationSelect", method = RequestMethod.POST)
	@ResponseBody
	public String querySuperLocationSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.querySuperLocationSelect(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		return json.toString();
	}
	/**
	 * 位置与科室对应关系添加用下落框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryNotExistsLocationSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryNotExistsLocationSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryNotExistsLocationSelect(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		return json.toString();
	}
	/**
	 * 上级故障下拉
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/querySuperFaultTypeSelect", method = RequestMethod.POST)
	@ResponseBody
	public String querySuperFaultTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.querySuperFaultTypeSelect(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		return json.toString();
	}
	
	/**
	 * 国标码字典下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryAssGBDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssGBDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssGBDict(mapVo);
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
	
	/**
	 * 科室分类下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryDeptKindDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptKindDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryDeptKindDict(mapVo);
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
	/**
	 * 仓库对应管理部门下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/queryAssStoreDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssStoreDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssStoreDept(mapVo);
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
	
	/**
	 * 资产字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryDeptDictAll", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryDeptDictAll(mapVo);
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
	

	/**
	 * 采购入库生产厂商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryHosFacDictNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosFacDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String subjType = hrpAssSelectService.queryHosFacDictNo(mapVo);
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
	
	/**
	 * 资产凭证下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryCertNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryCertNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryCertNo(mapVo);
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
	
	@RequestMapping(value = "/hrp/ass/queryAssMeasureKingDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssMeasureKingDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssMeasureKingDict(mapVo);
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
	
	@RequestMapping(value = "/hrp/ass/queryReportBusType", method = RequestMethod.POST)
	@ResponseBody
	public String queryReportBusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryReportBusType(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		return json.toString();
	}
	
	@RequestMapping(value = "/hrp/ass/queryAssTypeSixEight", method = RequestMethod.POST)
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
	
	/**
	 * 购置申请  预算信息 下拉框检索
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/queryAssBudgTable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBudgTable(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryAssBudgTable(getPage(mapVo));

		return JSONObject.parseObject(subjType);
	}
	
	//合同付款主表
	@RequestMapping(value = "/hrp/ass/queryPactMainFkht", method = RequestMethod.POST)
	@ResponseBody
	public String queryPactMainFkht(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAssSelectService.queryPactMainFkht(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		return json.toString();
	}
	
}
