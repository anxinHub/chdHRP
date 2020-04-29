/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.books.subjaccount;
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
import com.chd.hrp.acc.entity.AccMultiPlan;
import com.chd.hrp.acc.serviceImpl.books.subjaccount.AccMultiPlanServiceImpl;

/**
* @Title. @Description.
* 多栏方案
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccMultiPlanController extends BaseController{
	private static Logger logger = Logger.getLogger(AccMultiPlanController.class);
	
	
	@Resource(name = "accMultiPlanService")
	private final AccMultiPlanServiceImpl accMultiPlanService = null;
   
    
	/**
	*多栏方案<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accsubjledger/accMultiPlanMainPage", method = RequestMethod.GET)
	public String accMultiPlanMainPage(Model mode) throws Exception {

		return "hrp/acc/accsubjnature/accMultiPlanMain";

	}
	/**
	*多栏方案<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accsubjledger/accMultiPlanAddPage", method = RequestMethod.GET)
	public String accMultiPlanAddPage(Model mode) throws Exception {

		return "hrp/acc/accsubjnature/accMultiPlanAdd";

	}
	/**
	*多栏方案<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/addAccMultiPlan", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccMultiPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String accMultiPlanJson = accMultiPlanService.addAccMultiPlan(mapVo);

		return JSONObject.parseObject(accMultiPlanJson);
		
	}
	/**
	*多栏方案<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/queryAccMultiPlan", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccMultiPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String accMultiPlan = accMultiPlanService.queryAccMultiPlan(getPage(mapVo));

		return JSONObject.parseObject(accMultiPlan);
		
	}
	/**
	*多栏方案<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/deleteAccMultiPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccMultiPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
		}
		
		String accMultiPlanJson = accMultiPlanService.deleteAccMultiPlan(mapVo);
	  
		return JSONObject.parseObject(accMultiPlanJson);
			
	}
	/**
	 *多栏方案<BR>
	 *删除
	 */
	@RequestMapping(value = "/hrp/acc/accsubjledger/getAccMultiPlanDate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAccMultiPlanDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
			mapVo.put("group_id", SessionManager.getGroupId());
		
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String accMultiPlanJson = accMultiPlanService.getAccMultiPlanDate(mapVo);
		
		return JSONObject.parseObject(accMultiPlanJson);
		
	}
	
	/**
	*多栏方案<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/accMultiPlanUpdatePage", method = RequestMethod.GET)
	
	public String accMultiPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
       
		AccMultiPlan accMultiPlan = new AccMultiPlan();
		
        accMultiPlan = accMultiPlanService.queryAccMultiPlanByCode(mapVo);
        
		mode.addAttribute("plan_code", accMultiPlan.getPlan_code());
		
		mode.addAttribute("group_id", accMultiPlan.getGroup_id());
		
		mode.addAttribute("hos_id", accMultiPlan.getHos_id());
		
		mode.addAttribute("copy_code", accMultiPlan.getCopy_code());
		
		mode.addAttribute("plan_name", accMultiPlan.getPlan_name());
		
		mode.addAttribute("subj_code", accMultiPlan.getSubj_code());
		
		mode.addAttribute("subj_name", accMultiPlan.getSubj_name());
		
		mode.addAttribute("analy_type", accMultiPlan.getAnaly_type());
		
		return "hrp/acc/accsubjnature/accMultiPlanUpdate";
	}
	/**
	*多栏方案<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accsubjledger/updateAccMultiPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccMultiPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String accMultiPlanJson = accMultiPlanService.updateAccMultiPlan(mapVo);
		
		return JSONObject.parseObject(accMultiPlanJson);
	}
	

	/**
	*多栏方案<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/queryAccMultiPlanTree", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccMultiPlanTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String accMultiPlan = accMultiPlanService.queryAccMultiPlanTree(mapVo);

		return JSONObject.parseObject(accMultiPlan);
		
	}
	
	/**
	*多栏方案<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/queryAccSubjList", method = RequestMethod.POST)
	@ResponseBody
	
	public String queryAccSubjList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String accMultiPlan = accMultiPlanService.queryAccSubjList(mapVo);

		return accMultiPlan;
		
	}
	
}

