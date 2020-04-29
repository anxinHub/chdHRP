package com.chd.hrp.sup.controller;

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
import com.chd.hrp.sup.service.HrpSupSelectService;
import com.chd.hrp.sys.controller.HrpSysSelectController;

@Controller
public class HrpSupSelectController extends BaseController {

	private static Logger logger = Logger.getLogger(HrpSysSelectController.class);

	@Resource(name = "hrpSupSelectService")
	private final HrpSupSelectService hrpSupSelectService = null;

	// 部门下拉框
	@RequestMapping(value = "/hrp/sup/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String dept = hrpSupSelectService.queryDeptDict(mapVo);
		return dept;

	}

	// 部门下拉框末级
	@RequestMapping(value = "/hrp/sup/queryDeptDictLast", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictLast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String dept = hrpSupSelectService.queryDeptDictLast(mapVo);
		return dept;

	}
	
	// 供应商
		@RequestMapping(value = "/hrp/sup/querySupDict", method = RequestMethod.POST)
		@ResponseBody
		public String querySupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			String dept = hrpSupSelectService.querySupDict(mapVo);
			return dept;

		}
		
		@RequestMapping(value = "/hrp/sup/querySupStockType", method = RequestMethod.POST)
		@ResponseBody
		public String querySupStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			String dept = hrpSupSelectService.querySupStockType(mapVo);
			return dept;

		}
		
		@RequestMapping(value = "/hrp/sup/querySupPayTerm", method = RequestMethod.POST)
		@ResponseBody
		public String querySupPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			String dept = hrpSupSelectService.querySupPayTerm(mapVo);
			return dept;

		}
		
		@RequestMapping(value = "/hrp/sup/querySupPurDept", method = RequestMethod.POST)
		@ResponseBody
		public String querySupPurDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			String dept = hrpSupSelectService.querySupPurDept(mapVo);
			return dept;

		}
		
		@RequestMapping(value = "/hrp/sup/querySupStockEmp", method = RequestMethod.POST)
		@ResponseBody
		public String querySupStockEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			String dept = hrpSupSelectService.querySupStockEmp(mapVo);
			return dept;

		}
		
		@RequestMapping(value = "/hrp/sup/querySupDeptDict", method = RequestMethod.POST)
		@ResponseBody
		public String querySupDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			String dept = hrpSupSelectService.querySupDeptDict(mapVo);
			return dept;

		}
		@RequestMapping(value = "/hrp/sup/queryHosInfo", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			String info = hrpSupSelectService.queryHosInfo(mapVo);
			return info;
			
		}
}
