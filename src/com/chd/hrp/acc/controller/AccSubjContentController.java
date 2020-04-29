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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccSubjContent;
import com.chd.hrp.acc.serviceImpl.AccSubjContentServiceImpl;

/**
* @Title. @Description.
* 财政补助内容表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccSubjContentController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccSubjContentController.class);
	
	
	@Resource(name = "accSubjContentService")
	private final AccSubjContentServiceImpl accSubjContentService = null;
   
    
	/**
	*财政补助内容表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accsubjcontent/accSubjContentMainPage", method = RequestMethod.GET)
	public String accSubjContentMainPage(Model mode) throws Exception {

		return "hrp/acc/accsubjcontent/accSubjContentMain";

	}
	/**
	*财政补助内容表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accsubjcontent/accSubjContentAddPage", method = RequestMethod.GET)
	public String accSubjContentAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		return "hrp/acc/accsubjcontent/accSubjContentAdd";

	}
	/**
	*财政补助内容表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accsubjcontent/addAccSubjContent", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccSubjContent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String AccSubjContentJson = accSubjContentService.addAccSubjContent(mapVo);

		return JSONObject.parseObject(AccSubjContentJson);
		
	}
	/**
	*财政补助内容表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accsubjcontent/queryAccSubjContent", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccSubjContent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acc_year") == null){
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String AccSubjContent = accSubjContentService.queryAccSubjContent(getPage(mapVo));

		return JSONObject.parseObject(AccSubjContent);
		
	}
	/**
	*财政补助内容表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accsubjcontent/deleteAccSubjContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccSubjContent(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String [] res = id.split("@");
			
			mapVo.put("group_id", res[0]);
			 
			mapVo.put("hos_id", res[1]);        
			
            mapVo.put("content_code", res[2]);//实际实体类变量
            
            mapVo.put("subj_code", res[3]);
            
            mapVo.put("acc_year", res[4]);
            
            mapVo.put("copy_code", res[5]);
            
            listVo.add(mapVo);
        }
		
		String AccSubjContentJson = accSubjContentService.deleteBatchAccSubjContent(listVo);
	   
		return JSONObject.parseObject(AccSubjContentJson);
			
	}
	
	/**
	*财政补助内容表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsubjcontent/accSubjContentUpdatePage", method = RequestMethod.GET)
	
	public String accSubjContentUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		AccSubjContent accSubjContent = new AccSubjContent();
        
		accSubjContent = accSubjContentService.queryAccSubjContentByCode(mapVo);
		
		mode.addAttribute("group_id", accSubjContent.getGroup_id());
		
		mode.addAttribute("hos_id", accSubjContent.getHos_id());
		
		mode.addAttribute("content_code", accSubjContent.getContent_code());
		
		mode.addAttribute("content_name", accSubjContent.getContent_name());
		
		return "hrp/acc/accsubjcontent/accSubjContentUpdate";
	}
	/**
	*财政补助内容表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accsubjcontent/updateAccSubjContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccSubjContent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
		String AccSubjContentJson = accSubjContentService.updateAccSubjContent(mapVo);
		
		return JSONObject.parseObject(AccSubjContentJson);
	}
	

}

