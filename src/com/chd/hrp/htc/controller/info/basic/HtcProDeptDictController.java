package com.chd.hrp.htc.controller.info.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.htc.entity.info.basic.HtcProDeptDict;
import com.chd.hrp.htc.service.info.basic.HtcProDeptDictService;
@Controller
public class HtcProDeptDictController extends BaseController{

	private static Logger logger = Logger.getLogger(HtcProDeptDictController.class);
	
	@Resource(name = "htcProDeptDictService")
	private final HtcProDeptDictService htcProDeptDictService = null;
	

	@RequestMapping(value = "/hrp/htc/info/basic/prodept/htcProDeptMainPage", method = RequestMethod.GET)
	public String htcProDeptDictMainPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/prodept/htcProDeptMain";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/info/basic/prodept/htcProDeptAddPage", method = RequestMethod.GET)
	public String htcProDeptDictAddPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/prodept/htcProDeptAdd";

	}
	
   // 保存
	@RequestMapping(value = "/hrp/htc/info/basic/prodept/addHtcProDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcProDept(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String proDeptDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("proj_dept_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("proj_dept_name").toString()));
		
		try {
			
			proDeptDictJson = htcProDeptDictService.addHtcProDept(mapVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(proDeptDictJson);

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/info/basic/prodept/synchroHtcProDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> synchroHtcProDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		String proDeptDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		try {
			
			proDeptDictJson = htcProDeptDictService.synchroHtcProDept(mapVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(proDeptDictJson);

	}
		
		
		// 查询
		@RequestMapping(value = "/hrp/htc/info/basic/prodept/queryHtcProDept", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHtcProDept(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());

			String deptDict = htcProDeptDictService.queryHtcProDept(getPage(mapVo));

			return JSONObject.parseObject(deptDict);

		}
		
		// 删除
		@RequestMapping(value = "/hrp/htc/info/basic/prodept/deleteHtcProDept", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteHtcProDept(
				@RequestParam(value = "ParamVo", required = true) String paramVo,
				Model mode) throws Exception {
			
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("proj_dept_id", ids[3]);
				listVo.add(mapVo);
			}
			
			String deptDictJson = "";
			
			try {
				
				deptDictJson = htcProDeptDictService.deleteBatchHtcProDept(listVo);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return JSONObject.parseObject(deptDictJson);

		}
		
		// 修改页面跳转
		@RequestMapping(value = "/hrp/htc/info/basic/prodept/htcProDeptUpdatePage", method = RequestMethod.GET)
		public String htcProDeptUpdatePage(@RequestParam Map<String, Object> mapVo,
				Model mode) throws Exception {

			HtcProDeptDict proDept = htcProDeptDictService.queryHtcProDeptByCode(mapVo);

			mode.addAttribute("group_id", proDept.getGroup_id());
			mode.addAttribute("hos_id", proDept.getHos_id());
			mode.addAttribute("copy_code", proDept.getCopy_code());
			mode.addAttribute("proj_dept_id", proDept.getProj_dept_id());
			mode.addAttribute("proj_dept_code", proDept.getProj_dept_code());
			mode.addAttribute("proj_dept_name", proDept.getProj_dept_name());
			mode.addAttribute("natur_code", proDept.getNatur_code());
			mode.addAttribute("natur_name", proDept.getNatur_name());
			mode.addAttribute("is_stop", proDept.getIs_stop());
			mode.addAttribute("is_last", proDept.getIs_last());
			mode.addAttribute("spell_code", proDept.getSpell_code());
			mode.addAttribute("wbx_code", proDept.getWbx_code());

			return "hrp/htc/info/basic/prodept/htcProDeptUpdate";
		}
		
		// 修改保存
		@RequestMapping(value = "/hrp/htc/info/basic/prodept/updateHtcProDept", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateHtcProDept(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {
			
			String deptDictJson = "";

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("proj_dept_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("proj_dept_name").toString()));
			
			try {
				
				deptDictJson =  htcProDeptDictService.updateHtcProDept(mapVo);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return JSONObject.parseObject(deptDictJson);
		}
}
