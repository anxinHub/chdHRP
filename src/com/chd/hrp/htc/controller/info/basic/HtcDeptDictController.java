package com.chd.hrp.htc.controller.info.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.htc.service.info.basic.HtcDeptDictService;
import com.chd.hrp.sys.entity.DeptDict;

@Controller
public class HtcDeptDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcDeptDictController.class);

	@Resource(name = "htcDeptDictService")
	private final HtcDeptDictService htcDeptDictService = null;
	
	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	
	@RequestMapping(value = "/hrp/htc/info/basic/dept/htcDeptDictMainPage", method = RequestMethod.GET)
	public String htcCostDeptDictMainPage(Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("acct_year", SessionManager.getAcctYear());

		// 添加编码规则判断
		mapVo.put("proj_code", "HOS_DEPT");
		mapVo.put("mod_code", "00");
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String rules_view = Strings.removeFirst(rules_v);
		// 获取第一级长度
		int first_length = (Integer) level_length.get(1);
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);
		
		return "hrp/htc/info/basic/dept/htcDeptDictMain";

	}
	
	
	/**
	 * @Description 查询数据部门字典tree
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htc/info/basic/dept/queryHtcDeptDictByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptDictByTree(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("acct_year", SessionManager.getAcctYear());
		
		    List<?> l_map = htcDeptDictService.queryHtcDeptDictByTree(mapVo);
		
		    return JSONObject.parseObject(ChdJson.toJson(l_map));
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/info/basic/dept/queryHtcDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptDict(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		
		String deptDict = htcDeptDictService.queryHtcDeptDict(getPage(mapVo));

		return JSONObject.parseObject(deptDict);
	}
	
	@RequestMapping(value = "/hrp/htc/info/basic/dept/htcDeptDictUpdatePage", method = RequestMethod.GET)
	public String htcDeptDictUpdatePage(Model mode,@RequestParam Map<String, Object> mapVo) throws Exception {
		
		String result = "";
		Map<String,Object> deptAttrMapVo = htcDeptDictService.queryHtcDeptAttrByCode(mapVo);
		//如果不是末级,不可以修改部门类型和部门性质
		mode.addAttribute("is_last", mapVo.get("is_last"));
		if (deptAttrMapVo != null) {
			mode.addAttribute("dept_no", deptAttrMapVo.get("dept_no"));
			mode.addAttribute("group_id", deptAttrMapVo.get("group_id"));
			mode.addAttribute("hos_id", deptAttrMapVo.get("hos_id"));
			mode.addAttribute("dept_id", deptAttrMapVo.get("dept_id"));
			mode.addAttribute("dept_code", deptAttrMapVo.get("dept_code"));
			mode.addAttribute("dept_name", deptAttrMapVo.get("dept_name"));
			mode.addAttribute("kind_code", deptAttrMapVo.get("kind_code"));
			mode.addAttribute("kind_name", deptAttrMapVo.get("kind_name"));
			mode.addAttribute("type_code", deptAttrMapVo.get("type_code"));
			mode.addAttribute("type_name", deptAttrMapVo.get("type_name"));
			mode.addAttribute("natur_code", deptAttrMapVo.get("natur_code"));
			mode.addAttribute("natur_name", deptAttrMapVo.get("natur_name"));
			result = "hrp/htc/info/basic/dept/htcDeptDictUpdate";
		} else {

			Map<String,Object> deptmapVo = htcDeptDictService.queryHtcDeptDictByCode(mapVo);
			mode.addAttribute("dept_no", deptmapVo.get("dept_no"));
			mode.addAttribute("group_id", deptmapVo.get("group_id"));
			mode.addAttribute("hos_id", deptmapVo.get("hos_id"));
			mode.addAttribute("dept_id", deptmapVo.get("dept_id"));
			mode.addAttribute("dept_code", deptmapVo.get("dept_code"));
			mode.addAttribute("dept_name", deptmapVo.get("dept_name"));
			mode.addAttribute("kind_code", deptmapVo.get("kind_code"));
			mode.addAttribute("kind_name", deptmapVo.get("kind_name"));
			result = "hrp/htc/info/basic/dept/htcDeptDictAdd";

		}
		return result;
	}
	
	@RequestMapping(value = "/hrp/htc/info/basic/dept/addHtcDeptAttrDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptAttrDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String deptAttrJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
	  	
		try {
			 deptAttrJson = htcDeptDictService.addHtcDeptAttrDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(deptAttrJson);

	}

	@RequestMapping(value = "/hrp/htc/info/basic/dept/updateHtcDeptAttrDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcDeptAttrDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
		
			String deptAttrJson = "";

			try {
				deptAttrJson = htcDeptDictService.updateHtcDeptAttrDict(mapVo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return JSONObject.parseObject(deptAttrJson);
			

	}
	
	

	@RequestMapping(value = "/hrp/htc/info/basic/dept/htcDeptDictManagePage", method = RequestMethod.GET)
	public String htcDeptDictManagePage(Model mode,@RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> mapVo) throws Exception {
		
		mode.addAttribute("paramVo", paramVo);
		
		return "hrp/htc/info/basic/dept/htcDeptDictManage";

	}
	
	/**
	 * 批量管理
	 * 
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htc/info/basic/dept/htcDeptDictManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> htcDeptDictManage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
			
		    String deptAttrManageJson = null;
		    
		    List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		    
			String paramVo = (String) mapVo.get("paramVo");
			
			for (String id : paramVo.split(",")) {
					String[] ids = id.split("@");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", ids[0]);
					map.put("hos_id", ids[1]);
					map.put("dept_id", ids[2]);
					map.put("type_code", mapVo.get("type_code"));
					map.put("natur_code", mapVo.get("natur_code"));
					listVo.add(map);
		    	}
			
			try {
				deptAttrManageJson = htcDeptDictService.htcDeptDictManage(listVo);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return JSONObject.parseObject(deptAttrManageJson);
	}
}
