/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 鏅烘収浜戝悍锛堝寳浜級鏁版嵁绉戞妧鏈夐檺鍏徃
 */
package com.chd.hrp.sys.controller;

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
import com.chd.hrp.sys.serviceImpl.HrpSysSelectServiceImpl;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class HrpSysSelectController extends BaseController {

	private static Logger logger = Logger.getLogger(HrpSysSelectController.class);

	@Resource(name = "hrpSysSelectService")
	private final HrpSysSelectServiceImpl hrpSysSelectService = null;

	// 鑱屽伐
	@RequestMapping(value = "/hrp/sys/queryEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.queryEmpDict(mapVo);
		return emp;

	}
	
	@RequestMapping(value = "/hrp/sys/queryEmpDictDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDictDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.queryEmpDictDept(mapVo);
		return emp;

	}

	// 鑱屽伐
	@RequestMapping(value = "/hrp/sys/queryEmpDictForCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDictForCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.queryEmpDictForCode(mapVo);
		return emp;

	}

	@RequestMapping(value = "/hrp/sys/queryEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.queryEmp(mapVo);
		return emp;

	}
	
	@RequestMapping(value = "/hrp/sys/queryEmpNew", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.queryEmpNew(mapVo);
		return emp;

	}

	/**
	 * 加载项目
	 * @param mapVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryProjDictDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjDictDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.queryProjDictDict(mapVo);
		return emp;

	}
	
	/**
	 * 加载项目明细
	 * @param mapVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryProjDictDictDet", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjDictDictDet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.queryProjDictDictDet(mapVo);
		return emp;

	}

	/**
	 * 加载仓库
	 * @param mapVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryStoreDictDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryStoreDictDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.queryStoreDictDict(mapVo);
		return emp;
	}
	
	/**
	 * 加载虚仓
	 * @param mapVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/querySetDictDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySetDictDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.querySetDictDict(mapVo);
		return emp;
	}

	/**
	 * 加载供应商
	 * @param mapVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/querySupDictDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySupDictDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.querySupDictDict(mapVo);
		return emp;

	}

	// 鍖婚櫌绛夌骇
	@RequestMapping(value = "/hrp/sys/queryHosLevelDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosLevelDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosLevel = hrpSysSelectService.queryHosLevelDict(mapVo);
		return hosLevel;

	}

	// 鍖婚櫌鍒嗙被
	@RequestMapping(value = "/hrp/sys/queryHosTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosType = hrpSysSelectService.queryHosTypeDict(mapVo);
		return hosType;

	}

	// 鍥界睄
	@RequestMapping(value = "/hrp/sys/queryCountriesDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCountriesDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String countries = hrpSysSelectService.queryCountriesDict(mapVo);
		return countries;

	}

	// 鍦板尯
	@RequestMapping(value = "/hrp/sys/queryRegionDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryRegionDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String region = hrpSysSelectService.queryRegionDict(mapVo);
		return region;

	}

	// 鏀挎不闈㈣矊
	@RequestMapping(value = "/hrp/sys/queryPoliticalDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPoliticalDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String political = hrpSysSelectService.queryPoliticalDict(mapVo);
		return political;

	}

	// 鐢ㄦ埛
	@RequestMapping(value = "/hrp/sys/queryUserDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryUserDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String user = hrpSysSelectService.queryUserDict(mapVo);
		JSONArray json = JSONArray.parseArray(user);

		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"全部\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}

	}
	
	
	@RequestMapping(value = "/hrp/sys/queryUserDictBySys", method = RequestMethod.POST)
	@ResponseBody
	public String queryUserDictBySys(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("type_code") == null) {
			mapVo.put("type_code", SessionManager.getTypeCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		
		String user = hrpSysSelectService.queryUserDictBySys(mapVo);
		JSONArray json = JSONArray.parseArray(user);
		return json.toString();

	}
	
	// 璁拌处浜�
	@RequestMapping(value = "/hrp/sys/queryAccUserDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccUserDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("type_code") == null) {
			if (SessionManager.getTypeCode().equals("4")) {
				mapVo.put("type_code", "0");
			}
			if (SessionManager.getTypeCode().equals("3")) {
				mapVo.put("type_code", "1");
			} else {
				mapVo.put("type_code", "0");
			}
		}
		//鑾峰彇鍓嶅彴浼犲叆鐨勬椂闂达紝鍓叉帀鍗曞紩鍙�
		mapVo.put("create_date_b", mapVo.get("create_date_b").toString().replace("'",""));
		mapVo.put("create_date_e", mapVo.get("create_date_e").toString().replace("'",""));
		String user = hrpSysSelectService.queryAccUserDict(mapVo);
		JSONArray json = JSONArray.parseArray(user);

		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"鍏ㄩ儴\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}

	}
	
	// 瀹℃牳浜�
	@RequestMapping(value = "/hrp/sys/queryAuditUserDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAuditUserDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("type_code") == null) {
			if (SessionManager.getTypeCode().equals("4")) {
				mapVo.put("type_code", "0");
			}
			if (SessionManager.getTypeCode().equals("3")) {
				mapVo.put("type_code", "1");
			} else {
				mapVo.put("type_code", "0");
			}

		}
		//鑾峰彇鍓嶅彴浼犲叆鐨勬椂闂达紝鍓叉帀鍗曞紩鍙�
		mapVo.put("create_date_b", mapVo.get("create_date_b").toString().replace("'",""));
		mapVo.put("create_date_e", mapVo.get("create_date_e").toString().replace("'",""));
		String user = hrpSysSelectService.queryAuditUserDict(mapVo);
		JSONArray json = JSONArray.parseArray(user);

		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"鍏ㄩ儴\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}

	}
	
	// 鍒跺崟浜�
	@RequestMapping(value = "/hrp/sys/queryCreateUserDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCreateUserDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("type_code") == null) {
			if (SessionManager.getTypeCode().equals("4")) {
				mapVo.put("type_code", "0");
			}
			if (SessionManager.getTypeCode().equals("3")) {
				mapVo.put("type_code", "1");
			} else {
				mapVo.put("type_code", "0");
			}

		}
		//鑾峰彇鍓嶅彴浼犲叆鐨勬椂闂达紝鍓叉帀鍗曞紩鍙�
		mapVo.put("create_date_b", mapVo.get("create_date_b").toString().replace("'",""));
		mapVo.put("create_date_e", mapVo.get("create_date_e").toString().replace("'",""));
		String user = hrpSysSelectService.queryCreateUserDict(mapVo);
		JSONArray json = JSONArray.parseArray(user);

		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"鍏ㄩ儴\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}

	}
	
	//鍑虹撼浜�
	@RequestMapping(value = "/hrp/sys/queryCashUserDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCashUserDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("type_code") == null) {
			if (SessionManager.getTypeCode().equals("4")) {
				mapVo.put("type_code", "0");
			}
			if (SessionManager.getTypeCode().equals("3")) {
				mapVo.put("type_code", "1");
			} else {
				mapVo.put("type_code", "0");
			}

		}
		//鑾峰彇鍓嶅彴浼犲叆鐨勬椂闂达紝鍓叉帀鍗曞紩鍙�
		mapVo.put("create_date_b", mapVo.get("create_date_b").toString().replace("'",""));
		mapVo.put("create_date_e", mapVo.get("create_date_e").toString().replace("'",""));
		String user = hrpSysSelectService.queryCashUserDict(mapVo);
		JSONArray json = JSONArray.parseArray(user);

		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"鍏ㄩ儴\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}

	}

	// 鍖婚櫌淇℃伅
	@RequestMapping(value = "/hrp/sys/queryHosInfo", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		String hos = hrpSysSelectService.queryHosInfoDict(mapVo);
		JSONArray json = JSONArray.parseArray(hos);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		logger.warn("json:" + json.toString());
		return json.toString();

	}

	// 鍖婚櫌鍙樻洿
	@RequestMapping(value = "/hrp/sys/queryHosInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		String hos = hrpSysSelectService.queryHosInfoDict(mapVo);

		return hos;

	}

	// 鍖婚櫌鍙樻洿
	@RequestMapping(value = "/hrp/sys/queryHosInfoDictPerm", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosInfoDictPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		/*
		 * if (mapVo.get("hos_id") == null) { mapVo.put("hos_id",
		 * SessionManager.getHosId()); }
		 */
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String hos = hrpSysSelectService.queryHosInfoDictPerm(mapVo);

		return hos;

	}

	// 璐﹀
	@RequestMapping(value = "/hrp/sys/queryCopyCodeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCopyCodeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String copy = hrpSysSelectService.queryCopyCodeDict(mapVo);
		return copy;

	}

	/**
	 * 鏍规嵁鐢ㄦ埛鏉冮檺鑾峰彇璐﹀淇℃伅
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryCopyCodeDictPerm", method = RequestMethod.POST)
	@ResponseBody
	public String queryCopyCodeDictPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		/*
		 * if (mapVo.get("hos_id") == null) { mapVo.put("hos_id",
		 * SessionManager.getHosId()); } 
		 */
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String copy = hrpSysSelectService.queryCopyCodeDictPerm(mapVo);
		return copy;

	}

	// 瑙掕壊
	@RequestMapping(value = "/hrp/sys/queryRoleDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryRoleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("type_code", SessionManager.getTypeCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String role = hrpSysSelectService.queryRoleDict(mapVo);
		return role;

	}

	// 绯荤粺妯″潡
	@RequestMapping(value = "/hrp/sys/queryModDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryModDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String mod = hrpSysSelectService.queryModDict(mapVo);
		return mod;

	}

	/**
	 * 鏍规嵁鐢ㄦ埛鏉冮檺鏌ヨ妯″潡淇℃伅
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryModDictPerm", method = RequestMethod.POST)
	@ResponseBody
	public String queryModDictPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String mod = hrpSysSelectService.queryModDictPerm(mapVo);
		return mod;

	}

	/**
	 * 鏍规嵁绠＄悊鍛樻潈闄愭煡璇㈡ā鍧椾俊鎭�
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryModDictAdminPerm", method = RequestMethod.POST)
	@ResponseBody
	public String queryModDictAdminPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		mapVo.put("type_code", SessionManager.getTypeCode());
		String mod = hrpSysSelectService.queryModDictAdminPerm(mapVo);
		return mod;

	}
	

	// 閮ㄩ棬绫诲埆
	@RequestMapping(value = "/hrp/sys/queryDeptKindDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptKindDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String deptKind = hrpSysSelectService.queryDeptKindDict(mapVo);
		return deptKind;

	}

	// 閮ㄩ棬涓嬫媺妗�
	@RequestMapping(value = "/hrp/sys/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String dept = hrpSysSelectService.queryDeptDict(mapVo);
		return dept;

	}

	// 閮ㄩ棬涓嬫媺妗嗘湯绾�
	@RequestMapping(value = "/hrp/sys/queryDeptDictLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String dept = hrpSysSelectService.queryDeptDictLast(mapVo);
		return dept;

	}

	// 閮ㄩ棬涓嬫媺妗嗘彁渚涚粰閮ㄩ棬缁存姢浣跨敤
	@RequestMapping(value = "/hrp/sys/queryDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String dept = hrpSysSelectService.queryDept(mapVo);
		JSONArray json = JSONArray.parseArray(dept);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		// json.add(0, jsonObj);
		return json.toString();

	}

	@RequestMapping(value = "/hrp/sys/queryDeptAll", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String dept = hrpSysSelectService.queryDept(mapVo);
		return dept;

	}

	// 瀛﹀巻
	@RequestMapping(value = "/hrp/sys/querySchoolDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySchoolDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String school = hrpSysSelectService.querySchoolDict(mapVo);
		return school;

	}

	// 宀椾綅
	@RequestMapping(value = "/hrp/sys/queryStationDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryStationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String station = hrpSysSelectService.queryStationDict(mapVo);
		return station;

	}

	// 鑱屽姟
	@RequestMapping(value = "/hrp/sys/queryDutyDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDutyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String duty = hrpSysSelectService.queryDutyDict(mapVo);
		return duty;

	}

	// 鑱屽伐鍒嗙被
	@RequestMapping(value = "/hrp/sys/queryEmpKindDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpKindDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String empKind = hrpSysSelectService.queryEmpKindDict(mapVo);
		return empKind;

	}

	// 椤圭洰绫诲瀷
	@RequestMapping(value = "/hrp/sys/queryProjTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String projType = hrpSysSelectService.queryProjTypeDict(mapVo);
		return projType;

	}

	// 椤圭洰绾у埆
	@RequestMapping(value = "/hrp/sys/queryProjLevelDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjLevelDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String projLevel = hrpSysSelectService.queryProjLevelDict(mapVo);
		return projLevel;

	}

	// 椤圭洰鐢ㄩ��
	@RequestMapping(value = "/hrp/sys/queryProjUseDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjUseDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String projUse = hrpSysSelectService.queryProjUseDict(mapVo);
		return projUse;

	}

	// 椤圭洰
	@RequestMapping(value = "/hrp/sys/queryProjDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String proj = hrpSysSelectService.queryProjDict(mapVo);
		return proj;

	}

	// 渚涘簲鍟�
	@RequestMapping(value = "/hrp/sys/querySupDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String sup = hrpSysSelectService.querySupDict(mapVo);
		return sup;

	}

	// 渚涘簲鍟嗙被鍒�
	@RequestMapping(value = "/hrp/sys/querySupTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySupTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String supType = hrpSysSelectService.querySupTypeDict(mapVo);
		return supType;

	}

	// 瀹㈡埛
	@RequestMapping(value = "/hrp/sys/queryCusDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCusDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String cus = hrpSysSelectService.queryCusDict(mapVo);
		return cus;

	}

	// 瀹㈡埛绫诲埆
	@RequestMapping(value = "/hrp/sys/queryCusTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCusTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String cusType = hrpSysSelectService.queryCusTypeDict(mapVo);
		return cusType;

	}

	// 鐢熶骇鍘傚晢
	@RequestMapping(value = "/hrp/sys/queryFacDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String fac = hrpSysSelectService.queryFacDict(mapVo);
		return fac;

	}

	// 鐢熶骇鍘傚晢绫诲埆
	@RequestMapping(value = "/hrp/sys/queryFacTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryFacTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String facType = hrpSysSelectService.queryFacTypeDict(mapVo);
		return facType;

	}

	// 搴撴埧
	@RequestMapping(value = "/hrp/sys/queryStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String store = hrpSysSelectService.queryStoreDict(mapVo);
		return store;

	}

	// 搴撴埧绫诲埆
	@RequestMapping(value = "/hrp/sys/queryStoreTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryStoreTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String storeType = hrpSysSelectService.queryStoreTypeDict(mapVo);
		return storeType;

	}

	// 璁￠噺鍗曚綅
	@RequestMapping(value = "/hrp/sys/queryUnitDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryUnitDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String unit = hrpSysSelectService.queryUnitDict(mapVo);
		return unit;

	}

	// 鐥呬汉绫诲埆
	@RequestMapping(value = "/hrp/sys/queryPatientTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPatientTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String patientType = hrpSysSelectService.queryPatientTypeDict(mapVo);
		return patientType;

	}

	// 璧勯噾鏉ユ簮
	@RequestMapping(value = "/hrp/sys/querySourceDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySourceDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String source = hrpSysSelectService.querySourceDict(mapVo);
		JSONArray json = JSONArray.parseArray(source);

		if (mapVo.get("query_key") == null) {
			return json.toString();
		} else {
			String str = "{\"id\":\"\",\"text\":\"鍏ㄩ儴\"}";
			JSONObject jsonObj = JSONArray.parseObject(str);
			json.add(0, jsonObj);
			return json.toString();
		}

	}

	// 璧勯噾鏉ユ簮
	@RequestMapping(value = "/hrp/sys/queryHosNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String source = hrpSysSelectService.queryHosNature(mapVo);
		return source;
	}

	@RequestMapping(value = "/hrp/sys/queryPermData", method = RequestMethod.POST)
	@ResponseBody
	public String queryPermData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("type_code") == null) {
			mapVo.put("type_code", SessionManager.getTypeCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		String source = hrpSysSelectService.queryPermData(mapVo);
		return source;
	}

	// 璁￠噺鍗曚綅
	@RequestMapping(value = "/hrp/sys/queryHosUnitDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosUnitDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosUnit = hrpSysSelectService.queryHosUnitDict(mapVo);
		return hosUnit;

	}

	// 璁￠噺鍗曚綅
	@RequestMapping(value = "/hrp/sys/querySysBusiMod", method = RequestMethod.POST)
	@ResponseBody
	public String querySysBusiMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosUnit = hrpSysSelectService.querySysBusiMod(mapVo);
		return hosUnit;

	}

	// 璁￠噺鍗曚綅
	@RequestMapping(value = "/hrp/sys/querySysSourceNature", method = RequestMethod.POST)
	@ResponseBody
	public String querySysSourceNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return hrpSysSelectService.querySysSourceNature(mapVo);

	}
	
	// 部门类型
	@RequestMapping(value = "/hrp/sys/queryAccDeptType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccDeptType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosUnit = hrpSysSelectService.queryAccDeptType(mapVo);
		return hosUnit;

	}
	
	// 支出性质
	@RequestMapping(value = "/hrp/sys/queryAccDeptOut", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccDeptOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosUnit = hrpSysSelectService.queryAccDeptOut(mapVo);
		return hosUnit;

	}
	
	/**
	 * 查询会计科目类别
	 * @param mapVo
	 * @param mdl
	 * @return
	 */
	@RequestMapping(value="/hrp/sys/queryAccSubjType",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjType(@RequestParam Map<String, Object> mapVo,Model mdl){
		if(mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		if(mapVo.get("hos_id") == null) {mapVo.put("hos_id", "0");}
		if(mapVo.get("copy_code") == null) {mapVo.put("copy_code", "0");}
		return JSONObject.parseObject(hrpSysSelectService.queryAccSubjType(mapVo));
	}
	/**
	 * 查询会计科目级次
	 * @param mapVo
	 * @param mdl
	 * @return
	 */
	@RequestMapping(value="/hrp/sys/querySubjLevel",method=RequestMethod.POST)
	@ResponseBody
	public String querySubjLevel(@RequestParam Map<String, Object> mapVo,Model mdl) {
		if(mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		if(mapVo.get("hos_id") == null) {mapVo.put("hos_id", "0");}
		if(mapVo.get("copy_code") == null) {mapVo.put("copy_code", "0");}
		if(mapVo.get("acc_year") == null) {mapVo.put("acc_year", "0");}
		return hrpSysSelectService.querySubjLevel(mapVo);
	}
	// 凭证类型
	@RequestMapping(value = "/hrp/sys/queryVouchType", method = RequestMethod.POST)
	@ResponseBody
	public String queryVouchType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", "0");}
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", "0");}
		String vouchType = hrpSysSelectService.queryVouchType(mapVo);
		return vouchType;
	}

	// 币种
	@RequestMapping(value = "/hrp/sys/queryCur", method = RequestMethod.POST)
	@ResponseBody
	public String queryCur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", "0");
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", "0");
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", "0");
		}
		String cur = hrpSysSelectService.queryCur(mapVo);
		return cur;
	}
	// 币种集团专用
	@RequestMapping(value = "/hrp/sys/queryGroupCur", method = RequestMethod.POST)
	@ResponseBody
	public String queryGroupCur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", "0");
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", "0");
		}
		
		String cur = hrpSysSelectService.queryGroupCur(mapVo);
		return cur;
	}
	/**
	 * 核算类型
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryCheckType", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", "0");
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", "0");
		}
		String checkType = hrpSysSelectService.queryCheckType(mapVo);
		return checkType;
	}
	/**
	 * 科目性质
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/querySubjNature", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", "0");
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", "0");
		}
		String subjNature = hrpSysSelectService.querySubjNature(mapVo);
		return subjNature;
	}
	/**
	 * 支出性质-现金流量项目
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryDeptOutSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptOutSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String accDeptOut = hrpSysSelectService.queryDeptOutSelect(mapVo);

		return accDeptOut;
	}
 
	/*
	 * 科室性质 （用于辅助核算 系统核算部门）
	 */
	@RequestMapping(value = "/hrp/sys/queryDeptNatur", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptNatur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String checkType = hrpSysSelectService.queryDeptNatur(mapVo);
		return checkType;
	}
	/**
	 * 发放方式 --- 职工辅助核算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryEmpPay", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String checkType = hrpSysSelectService.queryEmpPay(mapVo);
		return checkType;
	}
	

 
	/**
	 * 科目类别下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/queryAccSubjTypeSelect", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpSysSelectService.querySubjTypeForSelect(mapVo);
		return subjType;
	}
	@RequestMapping(value = "/hrp/sys/queryDeptType", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String checkType = hrpSysSelectService.queryDeptType(mapVo);
		return checkType;
	}
	//科室字典(根据参数决定是否按权限)
	@RequestMapping(value = "/hrp/sys/queryHosDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if (mapVo.get("is_perm") == null) {
			mapVo.put("is_perm", "0");
		}

		String rejson = hrpSysSelectService.queryHosDept(mapVo);

		return rejson;
	}
	
	//查询自定义辅助核算字典对应表
	@RequestMapping(value = "/hrp/sys/queryAccBusiZCheck", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccBusiZCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrpSysSelectService.queryAccBusiZCheck(mapVo);
	}
	// 行业性质
	@RequestMapping(value = "/hrp/sys/queryHosNatureDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosNatureDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosNature = hrpSysSelectService.queryHosNatureDict(mapVo);
		return hosNature;
	}
	
	//账套
	@RequestMapping(value = "/hrp/sys/queryCopyDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCopyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosNature = hrpSysSelectService.queryCopyDict(mapVo);
		return hosNature;
	}
	
	//合计年月
	@RequestMapping(value = "/hrp/sys/queryAcctYearDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAcctYearDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", "0");
		}
		String hosNature = hrpSysSelectService.queryAcctYearDict(mapVo);
		return hosNature;
	}
	
	// 医院账套
	@RequestMapping(value = "/hrp/sys/queryHosCopyDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosCopyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String hosCopy = hrpSysSelectService.queryHosCopyDict(mapVo);
		return hosCopy;
	}
	
	// 集团年
	@RequestMapping(value = "/hrp/sys/queryAccYearDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccYearDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		String hosCopy = hrpSysSelectService.queryAccYearDict(mapVo);
		return hosCopy;
	}
	// 集团  部门权限   用于 部门辅助账 
	@RequestMapping(value = "/hrp/sys/queryDeptByRela", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptByRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		String hosCopy = hrpSysSelectService.queryDeptByRela(mapVo);
		return hosCopy;
	}
	// 集团  职工 权限   用于 职工辅助账 
	@RequestMapping(value = "/hrp/sys/queryEmpByRela", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpByRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		String hosCopy = hrpSysSelectService.queryEmpByRela(mapVo);
		return hosCopy;
	}
	// 集团  项目字典      用于 项目辅助账 
	@RequestMapping(value = "/hrp/sys/queryProjDictDictByRela", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjDictDictByRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		String hosCopy = hrpSysSelectService.queryProjDictDictByRela(mapVo);
		return hosCopy;
	}
	
	// 集团  项目字典      用于 项目辅助账 
	@RequestMapping(value = "/hrp/sys/querySupDictDictByRela", method = RequestMethod.POST)
	@ResponseBody
	public String querySupDictDictByRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.querySupDictDictByRela(mapVo);
		return emp;

	}
	// 集团  客户字典      用于 客户辅助账 
	@RequestMapping(value = "/hrp/sys/queryCusDictByRela", method = RequestMethod.POST)
	@ResponseBody
	public String queryCusDictByRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String emp = hrpSysSelectService.queryCusDictByRela(mapVo);
		return emp;
		
	}
	
	// 集团  客户字典      用于 客户辅助账 
	@RequestMapping(value = "/hrp/sys/querySysHisDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySysHisDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String hisDept = hrpSysSelectService.querySysHisDeptDict(mapVo);
		return hisDept;
		
	}
}
