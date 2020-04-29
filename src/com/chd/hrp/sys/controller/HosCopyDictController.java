/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
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
import com.chd.hrp.sys.serviceImpl.HosCopyDictServiceImpl;

 /**
  * 单位账套维护
  * @author gaopei
  *
  */

@Controller
public class HosCopyDictController extends BaseController{
	private static Logger logger = Logger.getLogger(HosCopyDictController.class);
	
	
	@Resource(name = "hosCopyDictService")
	private final HosCopyDictServiceImpl hosCopyDictService = null; 
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "hrp/sys/hoscopydict/sysHosCopyDictMainPage", method = RequestMethod.GET)
	public String sysHosCopyDictMainPage(Model mode) throws Exception {

		return "hrp/sys/hoscopydict/hosCopyDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/hoscopydict/hosCopyDictAddPage", method = RequestMethod.GET)
	public String hosCopyDictAddPage(Model mode) throws Exception {

		return "hrp/sys/hoscopydict/hosCopyDictAdd";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/sys/hoscopydict/querySysHosCopyDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysHosCopyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId()); 
		if(mapVo.get("rela_code")!=null){
			mapVo.put("rela_code", mapVo.get("rela_code").toString().toUpperCase()); 
		}
		
		 
		String hosCopy = hosCopyDictService.querySysHosCopyDict(getPage(mapVo));

		return JSONObject.parseObject(hosCopy);
		
	}
		

	// 保存
	@RequestMapping(value = "/hrp/sys/hoscopydict/addSysHosCopyDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addSysHosCopyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String hosCopy = hosCopyDictService.addSysHosCopyDict(mapVo);

		return JSONObject.parseObject(hosCopy);
		
	}

	
	
	 //删除
	@RequestMapping(value = "/hrp/sys/hoscopydict/deleteSysHosCopyDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSysHosCopyDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("group_id", id.split("@")[0]);
			
			mapVo.put("rela_code", id.split("@")[1]);
			 
            listVo.add(mapVo);
        }
		
		String hosCopy = hosCopyDictService.deleteSysHosCopyDict(listVo);
		
	   return JSONObject.parseObject(hosCopy);
			
	}
	
	 
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/hoscopydict/hosCopyDictUpdatePage", method = RequestMethod.GET)
	
	public String hosCopyDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String,Object> hosCopyMap = hosCopyDictService.queryHosCopyDictByCode(mapVo);
		mode.addAttribute("group_id", hosCopyMap.get("group_id"));
		mode.addAttribute("rela_code", hosCopyMap.get("rela_code"));
		mode.addAttribute("rela_name", hosCopyMap.get("rela_name"));
		mode.addAttribute("hos_id",hosCopyMap.get("hos_id"));
		mode.addAttribute("hos_code",hosCopyMap.get("hos_code"));
		mode.addAttribute("hos_name",hosCopyMap.get("hos_name"));
		mode.addAttribute("copy_code", hosCopyMap.get("copy_code"));
		mode.addAttribute("copy_name", hosCopyMap.get("copy_name")); 
		mode.addAttribute("is_last", hosCopyMap.get("is_last"));  
		mode.addAttribute("super_code", hosCopyMap.get("super_code"));  
		mode.addAttribute("is_stop", hosCopyMap.get("is_stop"));  
		
		return "/hrp/sys/hoscopydict/hosCopyDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/hoscopydict/updateSysHosCopyDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSysHosCopyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String hosCopy = hosCopyDictService.updateSysHosCopyDict(mapVo);
		
		return JSONObject.parseObject(hosCopy);
	}
	 
	// 对应关系维护页面跳转
	@RequestMapping(value = "hrp/sys/hoscopydict/sysHosCopySetMainPage", method = RequestMethod.GET)
	public String sysHosCopySetMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		System.out.println("aaa"+mapVo);
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("rela_code", mapVo.get("rela_code"));
		mode.addAttribute("rela_name", mapVo.get("rela_name"));
		return "hrp/sys/hoscopydict/hosCopySetMain";

	}
	
	// 对应关系设置操作
	@RequestMapping(value = "/hrp/sys/hoscopydict/querySysHosCopyRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysHosCopyRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId()); 
		if(mapVo.get("rela_code")!=null){
			mapVo.put("rela_code", mapVo.get("rela_code").toString().toUpperCase()); 
		}
		
		 
		String hosCopy = hosCopyDictService.querySysHosCopyRela(getPage(mapVo));

		return JSONObject.parseObject(hosCopy);
		
	}
	
	// 保存对应关系
	@RequestMapping(value = "/hrp/sys/hoscopydict/addSysHosCopyRela", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addSysHosCopyRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> deleteList= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			Map<String, Object> deleteMap = new HashMap<String, Object>();
			
			if("null".equals(id.split("@")[0])){
					
				deleteMap.put("group_id", id.split("@")[1]);
				
				deleteMap.put("rela_code", id.split("@")[2]);
				
				deleteList.add(deleteMap);
				
			}else {
				
				mapVo.put("group_id", id.split("@")[0]);
				
				mapVo.put("rela_code", id.split("@")[1]);
				
				mapVo.put("hos_id", id.split("@")[2]);
				
				mapVo.put("copy_code", id.split("@")[3]);
				  
	            listVo.add(mapVo);
				deleteMap.put("rela_code", id.split("@")[1]);
				deleteMap.put("group_id", id.split("@")[0]); 
				deleteList.add(deleteMap);
				
			}
			
        }
		String hosCopy =null;
		hosCopy = hosCopyDictService.deleteBatchSysHosCopyRela(deleteList);
		
		if(listVo.size()>0){
			hosCopy = hosCopyDictService.addBatchSysHosCopyRela(listVo);
		}

		return JSONObject.parseObject(hosCopy);
		
	}
		

}

