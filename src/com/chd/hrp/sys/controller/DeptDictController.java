/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.util.*;

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
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class DeptDictController extends BaseController{
	private static Logger logger = Logger.getLogger(DeptDictController.class);
	
	
	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/deptdict/deptDictMainPage", method = RequestMethod.GET)
	public String deptDictMainPage(Model mode) throws Exception {

		return "hrp/sys/deptdict/deptDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/deptdict/deptDictAddPage", method = RequestMethod.GET)
	public String deptDictAddPage(Model mode) throws Exception {

		return "hrp/sys/deptdict/deptDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/deptdict/addDeptDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String deptDictJson = deptDictService.addDeptDict(mapVo);

		return JSONObject.parseObject(deptDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/deptdict/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId()); 
		if (mapVo.get("dept_code") != null && !mapVo.get("dept_code").equals("")) { 
			String key=mapVo.get("dept_code").toString();
			mapVo.put("dept_code", key.toUpperCase());
		}
		
		String deptDict = deptDictService.queryDeptDict(getPage(mapVo));

		return JSONObject.parseObject(deptDict);
		
	}
	
	
	@RequestMapping(value = "/hrp/sys/deptdict/queryDeptDictByDeptNo", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryDeptDictByDeptNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		DeptDict deptDict = deptDictService.queryDeptDictByDeptNo(mapVo);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("deptDict", deptDict);
		return jsonObj;
		
	}
	
	
	//删除
	@RequestMapping(value = "/hrp/sys/deptdict/deleteDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String deptDictJson = deptDictService.deleteBatchDeptDict(listVo);
	   return JSONObject.parseObject(deptDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/deptdict/deptDictUpdatePage", method = RequestMethod.GET)
	
	public String deptDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        DeptDict deptDict = new DeptDict();
		deptDict = deptDictService.queryDeptDictByCode(mapVo);
		mode.addAttribute("dept_no", deptDict.getDept_no());
		mode.addAttribute("group_id", deptDict.getGroup_id());
		mode.addAttribute("hos_id", deptDict.getHos_id());
		mode.addAttribute("dept_id", deptDict.getDept_id());
		mode.addAttribute("dept_code", deptDict.getDept_code());
		mode.addAttribute("dept_name", deptDict.getDept_name());
		mode.addAttribute("user_code", deptDict.getUser_code());
		mode.addAttribute("create_date", deptDict.getCreate_date());
		mode.addAttribute("note", deptDict.getNote());
		mode.addAttribute("is_stop", deptDict.getIs_stop());
		
		return "hrp/sys/deptdict/deptDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/deptdict/updateDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String deptDictJson = deptDictService.updateDeptDict(mapVo);
		
		return JSONObject.parseObject(deptDictJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/deptdict/importDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String deptDictJson = deptDictService.importDeptDict(mapVo);
		
		return JSONObject.parseObject(deptDictJson);
	}
	
	@RequestMapping(value = "/hrp/sys/deptdict/queryDeptDictByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptDictByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String deptDict = deptDictService.queryDeptDictByMenu(mapVo);

		return JSONObject.parseObject(deptDict);
		
	}

}

