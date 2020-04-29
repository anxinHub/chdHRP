/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.hrp.sys.entity.Info;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.serviceImpl.InfoDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.InfoServiceImpl;
import com.chd.hrp.sys.serviceImpl.UserServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class InfoController extends BaseController{
	private static Logger logger = Logger.getLogger(InfoController.class);
	
	
	@Resource(name = "infoService")
	private final InfoServiceImpl infoService = null;
	
	@Resource(name = "infoDictService")
	private final InfoDictServiceImpl infoDictService = null;
   
	@Resource(name = "userService")
	private final UserServiceImpl userService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/info/infoMainPage", method = RequestMethod.GET)
	public String infoMainPage(Model mode) throws Exception {

		return "hrp/sys/info/infoMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/info/infoAddPage", method = RequestMethod.GET)
	public String infoAddPage(Model mode) throws Exception {

		return "hrp/sys/info/infoAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/info/addInfo", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		try{
			String infoJson = infoService.addInfo(mapVo);

			return JSONObject.parseObject(infoJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
		
	}
	
	@RequestMapping(value = "/hrp/sys/info/addInfoDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String dictType = mapVo.get("dictType").toString();
		if(dictType.equals("0")){
			infoService.updateInfoCode(mapVo);
		}else{
			infoService.updateInfoName(mapVo);
		}
		
		String infoJson = null;
		if(Integer.parseInt(mapVo.get("reserve").toString()) == 1){
			
			
			infoJson = infoDictService.addInfoDict(mapVo);
			 
		}else {
			
			infoJson = "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}

		return JSONObject.parseObject(infoJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/info/queryInfo", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		String info = infoService.queryInfo(getPage(mapVo));

		return JSONObject.parseObject(info);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/info/deleteInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteInfo(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String infoJson = "";
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("hos_id", id.split("@")[0]);
            mapVo.put("group_id", id.split("@")[1]);
            infoJson = infoService.deleteInfo(mapVo);
        }
		
	   return JSONObject.parseObject(infoJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/info/infoUpdatePage", method = RequestMethod.GET)
	
	public String infoUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Info info = new Info();
		info = infoService.queryInfoByCode(mapVo);
		mode.addAttribute("group_id", info.getGroup_id());
		mode.addAttribute("hos_id", info.getHos_id());
		mode.addAttribute("hos_code", info.getHos_code());
		mode.addAttribute("hos_name", info.getHos_name());
		mode.addAttribute("hos_simple", info.getHos_simple());
		mode.addAttribute("super_code", info.getSuper_code());
		mode.addAttribute("hos_level", info.getHos_level());
		mode.addAttribute("hos_city", info.getHos_city());
		mode.addAttribute("hos_sort", info.getHos_sort());
		mode.addAttribute("hos_contact", info.getHos_contact());
		mode.addAttribute("hos_phone", info.getHos_phone());
		mode.addAttribute("hos_email", info.getHos_email());
		mode.addAttribute("hos_zipcode", info.getHos_zipcode());
		mode.addAttribute("hos_type", info.getHos_type());
		mode.addAttribute("hos_address", info.getHos_address());
		mode.addAttribute("is_last", info.getIs_last());
		mode.addAttribute("is_stop", info.getIs_stop());
		mode.addAttribute("note", info.getNote());
		mode.addAttribute("spell_code", info.getSpell_code());
		mode.addAttribute("wbx_code", info.getWbx_code());
		return "hrp/sys/info/infoUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/info/updateInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		try{
			String infoJson = infoService.updateInfo(mapVo);
			
			return JSONObject.parseObject(infoJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/info/importInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String infoJson = infoService.importInfo(mapVo);
		
		return JSONObject.parseObject(infoJson);
	}
	

	
	@RequestMapping(value = "/hrp/sys/info/queryInfoByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInfoByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		String group = infoService.queryInfoByMenu(mapVo);

		return JSONObject.parseObject(group);
		
	}
	
	@RequestMapping(value = "/hrp/sys/info/queryInfoByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInfoByCode(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		
		Info info  = infoService.queryInfoByCode(mapVo);
		//String user = userService.queryUserByAdmin(mapVo);
		//info.setUser_code(user);
		//JSONObject jsonObj = new JSONObject();
		//jsonObj.put("info", info);
		//response.getWriter().print(jsonObj.toString());
		Map<String, Object> reMap=new HashMap<String, Object>(); 
		reMap.put("info", info);
		return reMap;
	}

}

