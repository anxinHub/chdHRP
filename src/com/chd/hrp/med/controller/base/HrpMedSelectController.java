/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.med.controller.base;

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
import com.chd.hrp.med.service.base.HrpMedSelectService;
import com.chd.hrp.med.serviceImpl.base.HrpMedSelectServiceImpl;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class HrpMedSelectController extends BaseController {

	private static Logger logger = Logger.getLogger(HrpMedSelectController.class);

	@Resource(name = "hrpMedSelectService")
	private final HrpMedSelectService hrpMedSelectService = null;
	
	// 是否
	@RequestMapping(value = "/hrp/med/queryMedYearOrNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedYearOrNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedYearOrNo(mapVo);
		return hrpMedSelect;
	}
	
	// 药品类别
	@RequestMapping(value = "/hrp/med/queryMedType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedType(mapVo);
		return hrpMedSelect;
	}
	
	// 药品分类
	@RequestMapping(value = "/hrp/med/queryMedTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedTypeDict(mapVo);
		return hrpMedSelect;
	}
	
	// 药品分类
	@RequestMapping(value = "/hrp/med/queryMedTypeDictByWrite", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedTypeDictByWrite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String hrpMedSelect = hrpMedSelectService.queryMedTypeDict(mapVo);
		return hrpMedSelect;
	}
	
	//材料改变药品类别查询改类别材料数
	@RequestMapping(value = "/hrp/med/queryChangeMedTypeCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryChangeMedTypeCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String hrpMedSelect = hrpMedSelectService.queryChangeMedTypeCode(mapVo);
		return hrpMedSelect;
	}
	// 药品财务分类
	@RequestMapping(value = "/hrp/med/queryMedFimTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedFimTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedFimTypeDict(mapVo);
		return hrpMedSelect;
	}
	
	// 药品分类
	@RequestMapping(value = "/hrp/med/queryMedTypeDicts", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedTypeDicts(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedTypeDicts(mapVo);
		return hrpMedSelect;
	}
	
	// 药品分类 加编码
	@RequestMapping(value = "/hrp/med/queryMedTypeDictCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedTypeDictCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String hrpMedSelectCode = hrpMedSelectService.queryMedTypeDictCode(mapVo);
		return hrpMedSelectCode;
	}
	
	// 财务类别
	@RequestMapping(value = "/hrp/med/queryMedFinaType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedFinaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedFinaType(mapVo);
		return hrpMedSelect;
	}
	
	// 药品字典
	@RequestMapping(value = "/hrp/med/queryMedInv", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedInv(mapVo);
		return hrpMedSelect;
	}
	
	// 药品变更字典表
	@RequestMapping(value = "/hrp/med/queryMedInvDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedInvDict(mapVo);
		return hrpMedSelect;
	}
	
	// 供应商
	@RequestMapping(value = "/hrp/med/queryHosSup", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryHosSup(mapVo);
		return hrpMedSelect;
	}
	
	// 供应商变更
	@RequestMapping(value = "/hrp/med/queryHosSupDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryHosSupDict(mapVo);
		return hrpMedSelect;
	}
	// 供应商变更不显示停用
		@RequestMapping(value = "/hrp/med/queryHosSupDictDisable", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosSupDictDisable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMedSelect = hrpMedSelectService.queryHosSupDictDisable(mapVo);
			return hrpMedSelect;
		}
	
	// 生成厂商
	@RequestMapping(value = "/hrp/med/queryHosFac", method = RequestMethod.POST)
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
		String hrpMedSelect = hrpMedSelectService.queryHosFac(mapVo);
		return hrpMedSelect;
	}
	
	@RequestMapping(value = "/hrp/med/queryHosFacInv", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosFacInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryHosFacInv(mapVo);
		return hrpMedSelect;
	}
	
	// 生成厂商变更
	@RequestMapping(value = "/hrp/med/queryHosFacDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryHosFacDict(mapVo);
		return hrpMedSelect;
	}
	
	// 计量单位
	@RequestMapping(value = "/hrp/med/queryHosUnit", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosUnit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryHosUnit(mapVo);
		return hrpMedSelect;
	}
	
	// 获取内置数据
	@RequestMapping(value = "/hrp/med/queryMedSysList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedSysList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedSysList(mapVo);
		return hrpMedSelect;
	}
	//货位分类
	@RequestMapping(value = "/hrp/med/queryMedLocationType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedLocationType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedLocationType(mapVo);
		return hrpMedSelect;
	}

	//货位字典
	@RequestMapping(value = "/hrp/med/queryMedLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedLocationDict(mapVo);
		return hrpMedSelect;
	}
	
	//仓库(带权限)
	@RequestMapping(value = "/hrp/med/queryMedStore", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedStore(mapVo);
		return hrpMedSelect;
	}
	

	//仓库(带权限)
	@RequestMapping(value = "/hrp/med/queryMedStoredisable", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStoredisable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedStoredisable(mapVo);
		return hrpMedSelect;
	}
	//仓库(只有读的权限)
	@RequestMapping(value = "/hrp/med/queryMedStoreByRead", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStoreByRead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedStoreByRead(mapVo);
		return hrpMedSelect;
	}
	//
		@RequestMapping(value = "/hrp/med/queryMedStoreByReaddisable", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedStoreByReaddisable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMedSelect = hrpMedSelectService.queryMedStoreByReaddisable(mapVo);
			return hrpMedSelect;
		}
	//仓库(只有写的权限)
		@RequestMapping(value = "/hrp/med/queryMedStoreByWrite", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedStoreByWrite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMedSelect = hrpMedSelectService.queryMedStoreByWrite(mapVo);
			return hrpMedSelect;
		}
	
	//仓库(全部)
	@RequestMapping(value = "/hrp/med/queryMedStoreAll", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStoreAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String hrpMedSelect = hrpMedSelectService.queryMedStoreAll(mapVo);
		return hrpMedSelect;
	}
	//仓库(带权限) 并且带 别名
	@RequestMapping(value = "/hrp/med/queryMedStoreAndAlias", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStoreAndAlias(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedStoreAlias(mapVo);
		return hrpMedSelect;
	}
	
	
	//材料证件分类
	@RequestMapping(value = "/hrp/med/qryMedInvCertType", method = RequestMethod.POST)
	@ResponseBody
	public String qryMedInvCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String certType = hrpMedSelectService.queryMedInvCertType(mapVo);
		return certType;
	}
	
	//职能科室
	@RequestMapping(value = "/hrp/med/queryMedDeptIsManager", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedDeptIsManager(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedDeptIsManager(mapVo);
		return hrpMedSelect;
	}
	
	//库房管理员
	@RequestMapping(value = "/hrp/med/queryMedManagerEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedManagerEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedManagerEmp(mapVo);
		return hrpMedSelect;
	}
	
	//库房分类 queryMedStoreType
	@RequestMapping(value = "/hrp/med/queryMedStoreType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedStoreType(mapVo);
		return hrpMedSelect;
	}

	//药品仓库配套表信息
		@RequestMapping(value = "/hrp/med/queryMedStoreMatch", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedStoreMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
					
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
					
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
					
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMedSelect = hrpMedSelectService.queryMedStoreMatch(mapVo);
				
			return hrpMedSelect;
		}
	
		//药品科室配套表信息
		@RequestMapping(value = "/hrp/med/queryMedDeptMatch", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedDeptMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
							
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
							
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
							
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMedSelect = hrpMedSelectService.queryMedDeptMatch(mapVo);
						
			return hrpMedSelect;
		}
		
		//证件编码 下拉框
		@RequestMapping(value = "/hrp/med/queryMedInvCert", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
					
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
					
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
					
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String invCert = hrpMedSelectService.queryMedInvCert(mapVo);
				
			return invCert;
		}
			
		//科室需求计划状态
		@RequestMapping(value = "/hrp/med/queryMedPlanState", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedPlanState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMedSelect = hrpMedSelectService.queryMedPlanState(mapVo);
			return hrpMedSelect;
		}
		
		//通过参数控制 物资类别权限 
		@RequestMapping(value = "/hrp/med/queryMedTypeDictDate", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedTypeDictDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
				
				
				String hrpMedSelect = hrpMedSelectService.queryMedTypeDictDate(mapVo);
				return hrpMedSelect;
		}
		//通过参数控制 仓库权限 
		@RequestMapping(value = "/hrp/med/queryMedStoreDictDate", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatStoreDictDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
				
				
				String hrpMedSelect = hrpMedSelectService.queryMedStoreDictDate(mapVo);
				return hrpMedSelect;
		}
		//编制科室 无权限
		@RequestMapping(value = "/hrp/med/queryMedDeptDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMedSelect = hrpMedSelectService.queryMedDeptDict(mapVo);
			return hrpMedSelect;
		}
		//领料人
		@RequestMapping(value="/hrp/med/queryMedEmpDictData",method=RequestMethod.POST)
		@ResponseBody
		public String queryMedEmpDictData(@RequestParam Map<String, Object> mapVo, Model mode){
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
   
			
			String hrpMedSelect = hrpMedSelectService.queryMedEmpDictData(mapVo);
			return hrpMedSelect;
		}
		//通过参数控制 编制科室权限 
		@RequestMapping(value = "/hrp/med/queryMedDeptDictDate", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedDeptDictDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			
			
			String hrpMedSelect = hrpMedSelectService.queryMedDeptDictDate(mapVo);
			return hrpMedSelect;
		}
		
		//盘点科室
				@RequestMapping(value = "/hrp/med/queryMedPDDeptDict", method = RequestMethod.POST)
				@ResponseBody
				public String queryMedPDDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
					mapVo.put("dept_id", SessionManager.getUserId());
					
					String hrpMedSelect = hrpMedSelectService.queryMedPDDeptDict(mapVo);
					return hrpMedSelect;
				}	
		
		
		//领料科室  
		@RequestMapping(value = "/hrp/med/queryMedAppDept", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedAppDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMedSelect = hrpMedSelectService.queryMedAppDept(mapVo);
			return hrpMedSelect;
		}	
		
		//获取年份
		@RequestMapping(value = "/hrp/med/queryMedYear", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMedSelect = hrpMedSelectService.queryMedYear(mapVo);
			return hrpMedSelect;
		}	
		
		//获取月份
		@RequestMapping(value = "/hrp/med/queryMedMonth", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMedSelect = hrpMedSelectService.queryMedMonth(mapVo);
			return hrpMedSelect;
		}
		
		//供应商类别 下拉框
		@RequestMapping(value = "/hrp/med/queryHosSupType", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosSupType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
					
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
					
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			String supType = hrpMedSelectService.queryHosSupType(mapVo);
				
			return supType;
		}
		
		//供应商类别 下拉框
		@RequestMapping(value = "/hrp/med/queryMedVenCertType", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
					
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
					
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String venCertType = hrpMedSelectService.queryMedVenCertType(mapVo);
				
			return venCertType;
		}
		
		//编制科室(带权限)
		@RequestMapping(value = "/hrp/med/queryMedDept", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMedSelect = hrpMedSelectService.queryMedDept(mapVo);
			return hrpMedSelect;
		}
		
		//业务类型
		@RequestMapping(value = "/hrp/med/queryMedBusType", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedBusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMedSelect = hrpMedSelectService.queryMedBusType(mapVo);
			return hrpMedSelect;
		}
		//库存明细查询业务类型
	    @RequestMapping(value = "/hrp/med/queryMedBusTypes", method = RequestMethod.POST)
	    @ResponseBody
	    public String queryMedBusTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
					String hrpMedSelect = hrpMedSelectService.queryMedBusTypes(mapVo);
					return hrpMedSelect;
		 }
		/**
		 * 药品分类级次
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/med/queryMedTypeLevel", method = RequestMethod.POST)
		@ResponseBody
		public String yqueryMedTypeLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMedSelect = hrpMedSelectService.queryMedTypeLevel(mapVo);
			return hrpMedSelect;
		}
		
		/**
		 * 药品分类级次
		 * @param mapVo
		 * 注：带id,text
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/med/queryMedTypeLevel_2", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedTypeLevel_2(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMedSelect = hrpMedSelectService.queryMedTypeLevel_2(mapVo);
			return hrpMedSelect;
		}	
		
		//采购协议类型
		@RequestMapping(value = "/hrp/med/queryMedProtocolType", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedProtocolType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			
			String hrpMedSelect = hrpMedSelectService.queryMedProtocolType(mapVo);
			
			return hrpMedSelect;
			
		}
		// 采购科室(带权限)
		@RequestMapping(value = "/hrp/med/queryPurDept", method = RequestMethod.POST)
		@ResponseBody
		public String queryPurDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
					
			String hrpMedSelect = hrpMedSelectService.queryPurDept(mapVo);
					
			return hrpMedSelect;
					
		}
		/**	
		 * 包装单位下拉框
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/med/queryHosPackage", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosPackage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
		
			}
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			String hrpMedSelect = hrpMedSelectService.queryHosPackage(mapVo);
			
			return hrpMedSelect;
			
		}
		// 采购计划--计划类型
		@RequestMapping(value = "/hrp/med/queryMedPlanType", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedPlanType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
							
			String hrpMedSelect = hrpMedSelectService.queryMedPlanType(mapVo);
							
			return hrpMedSelect;
							
		}
		// 采购计划--采购员
		@RequestMapping(value = "/hrp/med/queryMedPurStockEmp", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedPurStockEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
									
			String hrpMedSelect = hrpMedSelectService.queryMedPurStockEmp(mapVo);
									
			return hrpMedSelect;
									
		}

	
	/**
	 * 包装单位 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedHosPackage", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedHosPackage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedHosPackage(mapVo);
		return hrpMedSelect;
	}	
	/**
	 * 签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/querySignedDept", method = RequestMethod.POST)
	@ResponseBody
	public String querySignedDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.querySignedDept(mapVo);
		return hrpMedSelect;
	}	
	/**
	 * 采购类型
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedStockType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedStockType(mapVo);
		return hrpMedSelect;
	}	
	/**
	 * 采购协议主表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedProtocolMain(mapVo);
		return hrpMedSelect;
	}	
	/**
	 * 付款方式(结算方式)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedPayType(mapVo);
		return hrpMedSelect;
	}
	
	//普通职工/领料人  --查员工表
	@RequestMapping(value = "/hrp/med/queryMedEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedEmp(mapVo);
		return hrpMedSelect;
	}
	
	//普通职工/领料人--查变更表
	@RequestMapping(value = "/hrp/med/queryMedEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		
		String hrpMedSelect = hrpMedSelectService.queryMedEmpDict(mapVo);
		return hrpMedSelect;
	}
	
	//采购人 
	@RequestMapping(value = "/hrp/med/queryMedStockEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStockEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedStockEmp(mapVo);
		return hrpMedSelect;
	}
	//采购人--变更表
	@RequestMapping(value = "/hrp/med/queryMedStoctEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStockEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedStockEmpDict(mapVo);
		return hrpMedSelect;
	}
	
	//采购人 一个仓库对应多个采购员 查询   即墨需求   2017/04/06    gaopei  
	@RequestMapping(value = "/hrp/med/queryMedStockEmpByStore", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStockEmpByStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedStockEmpByStore(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 采购发票    付款条件下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedPayTerm(mapVo);
		return hrpMedSelect;
	}
	/**
	 * 科室
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryHosDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryHosDept(mapVo);
		return hrpMedSelect;
	}
	/**
	 * 科室--变更
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryHosDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryHosDeptDict(mapVo);
		return hrpMedSelect;
	}
	/**
	 * 权限科室
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryHosDeptByPerm", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptByPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryHosDeptByPerm(mapVo);
		return hrpMedSelect;
	}
	/**
	 * 权限科室--变更
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryHosDeptDictByPerm", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptDictByPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryHosDeptDictByPerm(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 申请科室--变更
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedHosInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedHosInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedHosInfoDict(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 申请科室--变更
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedHosInfo", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedHosInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedHosInfo(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 根据仓库对应关系查询材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInvDictByStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvDictByStoreInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedInvDictByStoreInv(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 项目
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedProj", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedProj(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 项目(含变更号)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedProjDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedProjDict(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 出库药品用途
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedOutUse", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedOutUse(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 科室需求计划编制--维护供应商
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedSupByInvId", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedSupByInvId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedSupByInvId(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 科室级次
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryHosDeptLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String hrpMedSelect = hrpMedSelectService.queryHosDeptLevel(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 仓库(虚仓)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedVirStore", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedVirStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String hrpMedSelect = hrpMedSelectService.queryMedVirStore(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 虚仓过滤库房读权限
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedVirStoreByWrite", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedVirStoreByWrite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String hrpMedSelect = hrpMedSelectService.queryMedVirStoreByWrite(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 仓库信息已经所对应的采购员
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedStoreStocker", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStoreStocker(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedStoreStocker(mapVo);
		return hrpMedSelect;
	}
	/**
	 * 入库--注册证号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInvCertId", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvCertId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedInvCertId(mapVo);
		return hrpMedSelect;
	}
	/**
	 * 入库--注册证号 带起止日期
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInvCertIdWithDate", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvCertIdWithDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryMedInvCertIdWithDate(mapVo);
		return hrpMedSelect;
	}
	/**
	 * 管理类别
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedManageType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedManageType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedManageType(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 货位
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedLocation", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedLocation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = hrpMedSelectService.queryMedLocation(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 查询带权限科室、权限仓库
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryPermDeptAndStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPermDeptAndStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryPermDeptAndStoreDict(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * 查询带权限的药品分类
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryPermMedTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPermMedTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMedSelect = hrpMedSelectService.queryPermMedTypeDict(mapVo);
		return hrpMedSelect;
	}
	/**
	 * 查询制单人
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/querySysUser", method = RequestMethod.POST)
	@ResponseBody
	public String querySysUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String hrpMedSelect = hrpMedSelectService.querySysUser(mapVo);
		return hrpMedSelect;
	}
	
	// 药品属性
			@RequestMapping(value = "/hrp/med/queryMedSx", method = RequestMethod.POST)
			@ResponseBody
			public String queryMedSx(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String hrpMedSelect = hrpMedSelectService.queryMedSx(mapVo);
				return hrpMedSelect;
			}
			// 药品剂型
			@RequestMapping(value = "/hrp/med/queryMedJx", method = RequestMethod.POST)
			@ResponseBody
			public String queryMedJx(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String hrpMedSelect = hrpMedSelectService.queryMedJx(mapVo);
				return hrpMedSelect;
			}
}
