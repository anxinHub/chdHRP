/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.mat.controller.base;

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
import com.chd.hrp.mat.service.base.HrpMatSelectService;
import com.chd.hrp.mat.serviceImpl.base.HrpMatSelectServiceImpl;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class HrpMatSelectController extends BaseController {

	private static Logger logger = Logger.getLogger(HrpMatSelectController.class);

	@Resource(name = "hrpMatSelectService")
	private final HrpMatSelectService hrpMatSelectService = null;
	
	// 是否
	@RequestMapping(value = "/hrp/mat/queryMatYearOrNo", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryMatYearOrNo(mapVo);
		return hrpMatSelect;
	}
	
	// 物资类别
	@RequestMapping(value = "/hrp/mat/queryMatType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatType(mapVo);
		return hrpMatSelect;
	}
	
	// 物资分类
	@RequestMapping(value = "/hrp/mat/queryMatTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatTypeDict(mapVo);
		return hrpMatSelect;
	}
	
	// 物资分类
	@RequestMapping(value = "/hrp/mat/queryMatTypeDictByWrite", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypeDictByWrite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String hrpMatSelect = hrpMatSelectService.queryMatTypeDict(mapVo);
		return hrpMatSelect;
	}
	
	//材料改变物资类别查询改类别材料数
	@RequestMapping(value = "/hrp/mat/queryChangeMatTypeCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryChangeMatTypeCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String hrpMatSelect = hrpMatSelectService.queryChangeMatTypeCode(mapVo);
		return hrpMatSelect;
	}
	// 物资财务分类
	@RequestMapping(value = "/hrp/mat/queryMatFimTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatFimTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatFimTypeDict(mapVo);
		return hrpMatSelect;
	}
	
	// 物资分类
	@RequestMapping(value = "/hrp/mat/queryMatTypeDicts", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypeDicts(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatTypeDicts(mapVo);
		return hrpMatSelect;
	}
	
	// 物资分类 加编码
	@RequestMapping(value = "/hrp/mat/queryMatTypeDictCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypeDictCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String hrpMatSelectCode = hrpMatSelectService.queryMatTypeDictCode(mapVo);
		return hrpMatSelectCode;
	}
	
	// 财务类别
	@RequestMapping(value = "/hrp/mat/queryMatFinaType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatFinaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatFinaType(mapVo);
		return hrpMatSelect;
	}
	
	// 物资字典
	@RequestMapping(value = "/hrp/mat/queryMatInv", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatInv(mapVo);
		return hrpMatSelect;
	}
	
	// 物资变更字典表
	@RequestMapping(value = "/hrp/mat/queryMatInvDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatInvDict(mapVo);
		return hrpMatSelect;
	}
	
	// 供应商
	@RequestMapping(value = "/hrp/mat/queryHosSup", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosSup(mapVo);
		return hrpMatSelect;
	}
	
	// 供应商变更
	@RequestMapping(value = "/hrp/mat/queryHosSupDict", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosSupDict(mapVo);
		return hrpMatSelect;
	}
	
	
	@RequestMapping(value = "/hrp/mat/queryHosSupDictUniversalMethod", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSupDictUniversalMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryHosSupDictUniversalMethod(mapVo);
		return hrpMatSelect;
	}
	// 供应商变更不显示停用
		@RequestMapping(value = "/hrp/mat/queryHosSupDictDisable", method = RequestMethod.POST)
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
			String hrpMatSelect = hrpMatSelectService.queryHosSupDictDisable(mapVo);
			return hrpMatSelect; 
		} 
		// 付款单页面的供应商下拉框(显示发生过业务且停用的供应商)
		@RequestMapping(value = "/hrp/mat/queryHosSupDictForPay", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosSupDictForPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMatSelect = hrpMatSelectService.queryHosSupDictForPay(mapVo);
			return hrpMatSelect;
		}
	// 生成厂商
	@RequestMapping(value = "/hrp/mat/queryHosFac", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosFac(mapVo);
		return hrpMatSelect;
	}
	
	@RequestMapping(value = "/hrp/mat/queryHosFacInv", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosFacInv(mapVo);
		return hrpMatSelect;
	}
	
	// 生成厂商变更
	@RequestMapping(value = "/hrp/mat/queryHosFacDict", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosFacDict(mapVo);
		return hrpMatSelect;
	}
	
	// 计量单位
	@RequestMapping(value = "/hrp/mat/queryHosUnit", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosUnit(mapVo);
		return hrpMatSelect;
	}
	
	// 获取内置数据
	@RequestMapping(value = "/hrp/mat/queryMatSysList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatSysList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatSysList(mapVo);
		return hrpMatSelect;
	}
	//货位分类
	@RequestMapping(value = "/hrp/mat/queryMatLocationType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatLocationType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatLocationType(mapVo);
		return hrpMatSelect;
	}

	//货位字典
	@RequestMapping(value = "/hrp/mat/queryMatLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatLocationDict(mapVo);
		return hrpMatSelect;
	}
	
	//仓库(带权限)
	@RequestMapping(value = "/hrp/mat/queryMatStore", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatStore(mapVo);
		return hrpMatSelect;
	}
	

	//仓库(带权限)
	@RequestMapping(value = "/hrp/mat/queryMatStoredisable", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStoredisable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatStoredisable(mapVo);
		return hrpMatSelect;
	}
	//仓库(只有读的权限)
	@RequestMapping(value = "/hrp/mat/queryMatStoreByRead", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStoreByRead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatStoreByRead(mapVo);
		return hrpMatSelect;
	}
	//
		@RequestMapping(value = "/hrp/mat/queryMatStoreByReaddisable", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatStoreByReaddisable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpMatSelectService.queryMatStoreByReaddisable(mapVo);
			return hrpMatSelect;
		}
	//仓库(只有写的权限)
		@RequestMapping(value = "/hrp/mat/queryMatStoreByWrite", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatStoreByWrite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpMatSelectService.queryMatStoreByWrite(mapVo);
			return hrpMatSelect;
		}
	
	//仓库(全部)
	@RequestMapping(value = "/hrp/mat/queryMatStoreAll", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStoreAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String hrpMatSelect = hrpMatSelectService.queryMatStoreAll(mapVo);
		return hrpMatSelect;
	}
	//仓库(带权限) 并且带 别名
	@RequestMapping(value = "/hrp/mat/queryMatStoreAndAlias", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStoreAndAlias(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatStoreAlias(mapVo);
		return hrpMatSelect;
	}
	
	
	//材料证件分类
	@RequestMapping(value = "/hrp/mat/qryMatInvCertType", method = RequestMethod.POST)
	@ResponseBody
	public String qryMatInvCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String certType = hrpMatSelectService.queryMatInvCertType(mapVo);
		return certType;
	}
	
	//职能科室
	@RequestMapping(value = "/hrp/mat/queryMatDeptIsManager", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryMatDeptIsManager(mapVo);
		return hrpMatSelect;
	}
	
	//库房管理员
	@RequestMapping(value = "/hrp/mat/queryMatManagerEmp", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryMatManagerEmp(mapVo);
		return hrpMatSelect;
	}
	
	//库房分类 queryMatStoreType
	@RequestMapping(value = "/hrp/mat/queryMatStoreType", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryMatStoreType(mapVo);
		return hrpMatSelect;
	}

	//物资仓库配套表信息
		@RequestMapping(value = "/hrp/mat/queryMatStoreMatch", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatStoreMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
					
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
					
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
					
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMatSelect = hrpMatSelectService.queryMatStoreMatch(mapVo);
				
			return hrpMatSelect;
		}
	
		//物资科室配套表信息
		@RequestMapping(value = "/hrp/mat/queryMatDeptMatch", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatDeptMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
							
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
							
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
							
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMatSelect = hrpMatSelectService.queryMatDeptMatch(mapVo);
						
			return hrpMatSelect;
		}
		
		//证件编码 下拉框
		@RequestMapping(value = "/hrp/mat/queryMatInvCert", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
					
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
					
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
					
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String invCert = hrpMatSelectService.queryMatInvCert(mapVo);
				
			return invCert;
		}
			
		//科室需求计划状态
		@RequestMapping(value = "/hrp/mat/queryMatPlanState", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatPlanState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpMatSelectService.queryMatPlanState(mapVo);
			return hrpMatSelect;
		}
		
		//编制科室 无权限
		@RequestMapping(value = "/hrp/mat/queryMatDeptDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpMatSelectService.queryMatDeptDict(mapVo);
			return hrpMatSelect;
		}
		//领料人
		@RequestMapping(value="/hrp/mat/queryMatEmpDictData",method=RequestMethod.POST)
		@ResponseBody
		public String queryMatEmpDictData(@RequestParam Map<String, Object> mapVo, Model mode){
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
   
			
			String hrpMatSelect = hrpMatSelectService.queryMatEmpDictData(mapVo);
			return hrpMatSelect;
		}
		//通过参数控制 编制科室权限 
		@RequestMapping(value = "/hrp/mat/queryMatDeptDictDate", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatDeptDictDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			
			
			String hrpMatSelect = hrpMatSelectService.queryMatDeptDictDate(mapVo);
			return hrpMatSelect;
		}
		
		//通过参数控制 物资类别权限 
			@RequestMapping(value = "/hrp/mat/queryMatTypeDictDate", method = RequestMethod.POST)
			@ResponseBody
			public String queryMatTypeDictDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
					
					
					String hrpMatSelect = hrpMatSelectService.queryMatTypeDictDate(mapVo);
					return hrpMatSelect;
			}
			//通过仓库id查找对应仓库的物资分类信息
			@RequestMapping(value = "/hrp/mat/queryMatTypeByStoreID", method = RequestMethod.POST)
			@ResponseBody
			public String queryMatTypeByStoreID(@RequestParam Map<String, Object> mapVo, Model mode){
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
				
				
				String hrpMatSelect = hrpMatSelectService.queryMatTypeByStoreID(mapVo);
				return hrpMatSelect;
			}
			//通过参数控制 仓库权限 
			@RequestMapping(value = "/hrp/mat/queryMatStoreDictDate", method = RequestMethod.POST)
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
					
					
					String hrpMatSelect = hrpMatSelectService.queryMatStoreDictDate(mapVo);
					return hrpMatSelect;
			}
			
			//通过参数控制 仓库权限 
			@RequestMapping(value = "/hrp/mat/queryMatStoreDictPro", method = RequestMethod.POST)
			@ResponseBody 
			public String queryMatStoreDictPro(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
					
					
					String hrpMatSelect = hrpMatSelectService.queryMatStoreDictPro(mapVo);
					return hrpMatSelect;
			}
		//盘点科室
				@RequestMapping(value = "/hrp/mat/queryMatPDDeptDict", method = RequestMethod.POST)
				@ResponseBody
				public String queryMatPDDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
					
					String hrpMatSelect = hrpMatSelectService.queryMatPDDeptDict(mapVo);
					return hrpMatSelect;
				}	
		
		
		//领料科室  
		@RequestMapping(value = "/hrp/mat/queryMatAppDept", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatAppDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpMatSelectService.queryMatAppDept(mapVo);
			return hrpMatSelect;
		}	
		
		//获取年份
		@RequestMapping(value = "/hrp/mat/queryMatYear", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpMatSelectService.queryMatYear(mapVo);
			return hrpMatSelect;
		}	
		
		//获取月份
		@RequestMapping(value = "/hrp/mat/queryMatMonth", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpMatSelectService.queryMatMonth(mapVo);
			return hrpMatSelect;
		}
		
		//供应商类别 下拉框
		@RequestMapping(value = "/hrp/mat/queryHosSupType", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosSupType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
					
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
					
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			String supType = hrpMatSelectService.queryHosSupType(mapVo);
				
			return supType;
		}
		
		//供应商类别 下拉框
		@RequestMapping(value = "/hrp/mat/queryMatVenCertType", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
					
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
					
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String venCertType = hrpMatSelectService.queryMatVenCertType(mapVo);
				
			return venCertType;
		}
		
		//编制科室(带权限)
		@RequestMapping(value = "/hrp/mat/queryMatDept", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpMatSelectService.queryMatDept(mapVo);
			return hrpMatSelect;
		}
		
		//业务类型
		@RequestMapping(value = "/hrp/mat/queryMatBusType", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatBusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpMatSelectService.queryMatBusType(mapVo);
			return hrpMatSelect;
		}
		//库存明细查询业务类型
	    @RequestMapping(value = "/hrp/mat/queryMatBusTypes", method = RequestMethod.POST)
	    @ResponseBody
	    public String queryMatBusTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
					String hrpMatSelect = hrpMatSelectService.queryMatBusTypes(mapVo);
					return hrpMatSelect;
		 }
		/** 
		 * 物资分类级次 
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/mat/queryMatTypeLevel", method = RequestMethod.POST)
		@ResponseBody
		public String yqueryMatTypeLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMatSelect = hrpMatSelectService.queryMatTypeLevel(mapVo);
			return hrpMatSelect;
		}
		
		/**
		 * 物资分类级次
		 * @param mapVo
		 * 注：带id,text
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/mat/queryMatTypeLevel_2", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatTypeLevel_2(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMatSelect = hrpMatSelectService.queryMatTypeLevel_2(mapVo);
			return hrpMatSelect;
		}	
		
		//采购协议类型
		@RequestMapping(value = "/hrp/mat/queryMatProtocolType", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatProtocolType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			
			String hrpMatSelect = hrpMatSelectService.queryMatProtocolType(mapVo);
			
			return hrpMatSelect;
			
		}
		// 采购科室(带权限)
		@RequestMapping(value = "/hrp/mat/queryPurDept", method = RequestMethod.POST)
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
					
			String hrpMatSelect = hrpMatSelectService.queryPurDept(mapVo);
					
			return hrpMatSelect;
					
		}
		/**	
		 * 包装单位下拉框
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/mat/queryHosPackage", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosPackage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
		
			}
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			String hrpMatSelect = hrpMatSelectService.queryHosPackage(mapVo);
			
			return hrpMatSelect;
			
		}
		// 采购计划--计划类型
		@RequestMapping(value = "/hrp/mat/queryMatPlanType", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatPlanType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
							
			String hrpMatSelect = hrpMatSelectService.queryMatPlanType(mapVo);
							
			return hrpMatSelect;
							
		}
		// 采购计划--采购员
		@RequestMapping(value = "/hrp/mat/queryMatPurStockEmp", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatPurStockEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
									
			String hrpMatSelect = hrpMatSelectService.queryMatPurStockEmp(mapVo);
									
			return hrpMatSelect;
									
		}

	
	/**
	 * 包装单位 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatHosPackage", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatHosPackage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatHosPackage(mapVo);
		return hrpMatSelect;
	}	
	/**
	 * 签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/querySignedDept", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.querySignedDept(mapVo);
		return hrpMatSelect;
	}	
	/**
	 * 采购类型
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatStockType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatStockType(mapVo);
		return hrpMatSelect;
	}	
	/**
	 * 采购协议主表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatProtocolMain(mapVo);
		return hrpMatSelect;
	}	
	/**
	 * 付款协议主表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatPactFkxyMain", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatPactFkxyMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatPactFkxyMain(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 付款方式(结算方式)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatPayType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatPayType(mapVo);
		return hrpMatSelect;
	}
	
	//普通职工/领料人  --查员工表
	@RequestMapping(value = "/hrp/mat/queryMatEmp", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryMatEmp(mapVo);
		return hrpMatSelect;
	}
	
	//普通职工/领料人--查变更表
	@RequestMapping(value = "/hrp/mat/queryMatEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		
		String hrpMatSelect = hrpMatSelectService.queryMatEmpDict(mapVo);
		return hrpMatSelect;
	}
	
	//采购人 
	@RequestMapping(value = "/hrp/mat/queryMatStockEmp", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryMatStockEmp(mapVo);
		return hrpMatSelect;
	}
	//采购人--变更表
	@RequestMapping(value = "/hrp/mat/queryMatStoctEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStockEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatStockEmpDict(mapVo);
		return hrpMatSelect;
	}
	
	//采购人 一个仓库对应多个采购员 查询   即墨需求   2017/04/06    gaopei  
	@RequestMapping(value = "/hrp/mat/queryMatStockEmpByStore", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStockEmpByStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatStockEmpByStore(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 采购发票    付款条件下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatPayTerm", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryMatPayTerm(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 科室
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryHosDept", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosDept(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 科室--变更
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryHosDeptDict", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosDeptDict(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 权限科室
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryHosDeptByPerm", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosDeptByPerm(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 权限科室--变更
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryHosDeptDictByPerm", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryHosDeptDictByPerm(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 申请科室--变更
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatHosInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatHosInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatHosInfoDict(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 申请科室--变更
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatHosInfo", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatHosInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatHosInfo(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 根据仓库对应关系查询材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInvDictByStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvDictByStoreInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatInvDictByStoreInv(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 项目
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatProj", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatProj(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 项目(含变更号)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatProjDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatProjDict(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 出库物资用途
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatOutUse", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatOutUse(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 科室需求计划编制--维护供应商
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatSupByInvId", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatSupByInvId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatSupByInvId(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 科室级次
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryHosDeptLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String hrpMatSelect = hrpMatSelectService.queryHosDeptLevel(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 仓库(虚仓)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatVirStore", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatVirStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String hrpMatSelect = hrpMatSelectService.queryMatVirStore(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 虚仓过滤库房读权限
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatVirStoreByWrite", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatVirStoreByWrite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String hrpMatSelect = hrpMatSelectService.queryMatVirStoreByWrite(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 仓库(虚仓)过滤多或写权限 只要有一个仓库没有读或写选线,对应的虚仓都不显示
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatVirStoreWithEntireStoreWriteOrRead", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatVirStoreWithEntireStoreWriteOrRead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatVirStoreWithEntireStoreWriteOrRead(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 仓库信息已经所对应的采购员
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatStoreStocker", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStoreStocker(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatStoreStocker(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 入库--注册证号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInvCertId", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvCertId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatInvCertId(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 入库--注册证号 带起止日期
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInvCertIdWithDate", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvCertIdWithDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatInvCertIdWithDate(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 管理类别
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatManageType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatManageType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatManageType(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 货位
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatLocation", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatLocation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatLocation(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 查询带权限科室、权限仓库
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryPermDeptAndStoreDict", method = RequestMethod.POST)
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
		String hrpMatSelect = hrpMatSelectService.queryPermDeptAndStoreDict(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * 查询带权限的物资分类
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryPermMatTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPermMatTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryPermMatTypeDict(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 查询制单人
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/querySysUser", method = RequestMethod.POST)
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

		String hrpMatSelect = hrpMatSelectService.querySysUser(mapVo);
		return hrpMatSelect;
	}
	
	@RequestMapping(value = "/hrp/mat/queryMatStoreMatchRead", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStoreMatchRead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatStoreMatchRead(mapVo);
		return hrpMatSelect;
	}
	
	@RequestMapping(value = "/hrp/mat/queryMatDeptMatchRead", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatDeptMatchRead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatDeptMatchRead(mapVo);
		return hrpMatSelect;
	}
	
	
	@RequestMapping(value = "/hrp/mat/queryYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public String queryYearMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryYearMonth(mapVo);
		return hrpMatSelect;
	}
	
	@RequestMapping(value = "/hrp/mat/queryMatExaminerEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatExaminerEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrpMatSelect = hrpMatSelectService.queryMatExaminerEmp(mapVo);
		return hrpMatSelect;
	}
	
	@RequestMapping(value = "/hrp/mat/queryHosSupDictMethod", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSupDictMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryHosSupDictMethod(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 医疗器械分类
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInstruType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInstruType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatInstruType(mapVo);
		return hrpMatSelect;
	}
	/**
	 * 医疗保险分类
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInsuraType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInsuraType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatInsuraType(mapVo);
		return hrpMatSelect;
	}
	
	//证件类型
	@RequestMapping(value = "/hrp/mat/queryMatCertType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatCertType(mapVo);
		return hrpMatSelect;
	}

	@RequestMapping(value = "/hrp/mat/queryMatWarnType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatWarnType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = hrpMatSelectService.queryMatWarnType(mapVo);
		return hrpMatSelect;
	}
	
}
