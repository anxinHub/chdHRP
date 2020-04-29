package com.chd.hrp.ass.controller.repair.asslocation;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.controller.repair.AssInvArrtController;
import com.chd.hrp.ass.service.repair.asslocation.AssLocationDeptService;

@Controller
@RequestMapping(value = "hrp/ass/repair/assLocationDept")
public class AssLocationDeptController {
	
	private static Logger logger = Logger.getLogger(AssInvArrtController.class);

	@Resource(name = "assLocationDeptService")
	private final AssLocationDeptService assLocationDeptService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "/assLocationDeptMainPage", method = RequestMethod.GET)
	public String assLocationDeptMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/assLocationDept/assLocationDeptMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/assLocationDeptAddPage", method = RequestMethod.GET)
	public String assLocationDeptAddPage(@RequestParam Map<String, Object> mapVo , Model mode) throws Exception {
		mode.addAttribute("dept_id",mapVo.get("dept_id"));
		return "hrp/ass/repair/assLocationDept/assLocationDeptAdd";

	}
	/**
	 * @Description 
	 * 修改页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/assLocationDeptUpdatePage", method = RequestMethod.GET)
	public String assLocationDeptUpdatePage(@RequestParam Map<String, Object> mapVo ,Model mode) throws Exception {

		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		Map<String,Object> assLocation=assLocationDeptService.queryAssLocationDeptByCode(mapVo);
		mode.addAttribute("group_id",assLocation.get("GROUP_ID"));
		mode.addAttribute("hos_id",assLocation.get("HOS_ID"));
		mode.addAttribute("copy_code",assLocation.get("COPY_CODE"));
		mode.addAttribute("loc_code",assLocation.get("LOC_CODE"));
		mode.addAttribute("dept_id",mapVo.get("dept_id").toString().split(",")[0]+","+mapVo.get("dept_id").toString().split(",")[1]);
	
		return "hrp/ass/repair/assLocationDept/assLocationDeptUpdate";
		
	}
	/**
	 * @Description 
	 * 主页面Tree 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAssLocationDeptTree", method = RequestMethod.POST ,produces = "application/json;charset=UTF-8") 
	@ResponseBody
	public String queryAssLocationDeptTree(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return assLocationDeptService.queryHosDeptTree(mapVo);
		
	}
	/**
	 * @Description 
	 * 主页面EtGrid数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAssLocationDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssLocationDept(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return assLocationDeptService.queryAssLocationDept(mapVo);
		
	}
	/**
	 * @Description 
	 * 添加数据 addAssLocationDept
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addAssLocationDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssLocationDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		 String assLocationDept = assLocationDeptService.add(mapVo);

		return JSONObject.parseObject(assLocationDept);
		
	}
	
	/**
	 * @Description 
	 * 更新数据 UpdateAssLocationDept
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAssLocationDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssLocationDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assLocationDept = assLocationDeptService.update(mapVo);
		
		return JSONObject.parseObject(assLocationDept);
		
	}
	
	/**
	 * @Description 
	 * 删除数据 deleteAssLocationDept
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAssLocationDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssLocationDept(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id",SessionManager.getGroupId());
			mapVo.put("hos_id",SessionManager.getHosId());
			mapVo.put("copy_code",SessionManager.getCopyCode());
			mapVo.put("loc_code", ids[0]);
			mapVo.put("dept_id", ids[1]);

			listVo.add(mapVo);

		}
		
		String assLocationDept = assLocationDeptService.deleteAssLocationDeptBatch(listVo);
		
		return JSONObject.parseObject(assLocationDept);
		
	}
	
}
