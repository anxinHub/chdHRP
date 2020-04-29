/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
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
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccFinaContent;
import com.chd.hrp.acc.serviceImpl.AccFinaContentServiceImpl;

/**
* @Title. @Description.
* 财政补助内容表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccFinaContentController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccFinaContentController.class);
	
	
	@Resource(name = "accFinaContentService")
	private final AccFinaContentServiceImpl accFinaContentService = null;
   
    
	/**
	*财政补助内容表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accfinacontent/accFinaContentMainPage", method = RequestMethod.GET)
	public String accFinaContentMainPage(Model mode) throws Exception {

		return "hrp/acc/accfinacontent/accFinaContentMain";

	}
	/**
	*财政补助内容表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accfinacontent/accFinaContentAddPage", method = RequestMethod.GET)
	public String accFinaContentAddPage(Model mode) throws Exception {

		return "hrp/acc/accfinacontent/accFinaContentAdd";

	}
	/**
	*财政补助内容表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accfinacontent/addAccFinaContent", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccFinaContent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		String AccFinaContentJson = accFinaContentService.addAccFinaContent(mapVo);

		return JSONObject.parseObject(AccFinaContentJson);
		
	}
	/**
	*财政补助内容表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accfinacontent/queryAccFinaContent", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccFinaContent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		String AccFinaContent = accFinaContentService.queryAccFinaContent(getPage(mapVo));

		return JSONObject.parseObject(AccFinaContent);
		
	}
	/**
	*财政补助内容表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accfinacontent/deleteAccFinaContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccFinaContent(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<String> listVo2 = JSONArray.parseArray(paramVo, String.class);
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for (String string : listVo2) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String [] res = string.split("@");
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("group_id", res[0]);
			 
			mapVo.put("hos_id", res[1]);
			
            mapVo.put("content_code", res[2]);
            
            listVo.add(mapVo);
		}
		
		
		String AccFinaContentJson = accFinaContentService.deleteBatchAccFinaContent(listVo);
	   
		return JSONObject.parseObject(AccFinaContentJson);
			
	}
	
	/**
	*财政补助内容表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accfinacontent/accFinaContentUpdatePage", method = RequestMethod.GET)
	
	public String accFinaContentUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		AccFinaContent accFinaContent = new AccFinaContent();
        
		accFinaContent = accFinaContentService.queryAccFinaContentByCode(mapVo);
		
		mode.addAttribute("group_id", accFinaContent.getGroup_id());
		
		mode.addAttribute("hos_id", accFinaContent.getHos_id());
		
		mode.addAttribute("content_code", accFinaContent.getContent_code());
		
		mode.addAttribute("content_name", accFinaContent.getContent_name());
		
		return "hrp/acc/accfinacontent/accFinaContentUpdate";
	}
	/**
	*财政补助内容表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accfinacontent/updateAccFinaContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccFinaContent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
		String AccFinaContentJson = accFinaContentService.updateAccFinaContent(mapVo);
		
		return JSONObject.parseObject(AccFinaContentJson);
	}
	

}

