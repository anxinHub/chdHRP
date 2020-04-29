package com.chd.hrp.hr.controller.organize;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.serviceImpl.organize.HrDeptDictServiceImpl;
@Controller
@RequestMapping(value = "/hrp/hr/deptDict")
public class HrDeptDictController extends BaseController{

	private static Logger logger = Logger.getLogger(HrDeptDictController.class);

	@Resource(name = "hrDeptDictService")
	private final HrDeptDictServiceImpl hrDeptDictService = null;
	
	// 查询
		@RequestMapping(value = "/queryDeptDict", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId()); 
			if (mapVo.get("dept_code") != null && !mapVo.get("dept_code").equals("")) { 
				String key=mapVo.get("dept_code").toString();
				mapVo.put("dept_code", key.toUpperCase());
			}
			
			String deptDict = hrDeptDictService.queryDeptDict(getPage(mapVo));

			return JSONObject.parseObject(deptDict);
			
		}
}
