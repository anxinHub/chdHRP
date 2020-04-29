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
import com.chd.hrp.acc.entity.AccMultiPlanSubj;
import com.chd.hrp.acc.serviceImpl.books.subjaccount.AccMultiPlanSubjServiceImpl;

/**
* @Title. @Description.
* 多栏方案分析科目
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccMultiPlanSubjController extends BaseController{
	private static Logger logger = Logger.getLogger(AccMultiPlanSubjController.class);
	
	
	@Resource(name = "accMultiPlanSubjService")
	private final AccMultiPlanSubjServiceImpl accMultiPlanSubjService = null;
   
    
	/**
	*多栏方案分析科目<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accsubjledger/accMultiPlanSubjMainPage", method = RequestMethod.GET)
	public String accMultiPlanSubjMainPage(Model mode) throws Exception {

		return "hrp/acc/accsubjnature/accMultiPlanSubjMain";

	}
	/**
	*多栏方案分析科目<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accsubjledger/accMultiPlanSubjAddPage", method = RequestMethod.GET)
	public String accMultiPlanSubjAddPage(Model mode) throws Exception {

		return "hrp/acc/accsubjnature/accMultiPlanSubjAdd";

	}
	/**
	*多栏方案分析科目<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/addAccMultiPlanSubj", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccMultiPlanSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accMultiPlanSubjJson = accMultiPlanSubjService.addAccMultiPlanSubj(mapVo);

		return JSONObject.parseObject(accMultiPlanSubjJson);
		
	}
	/**
	*多栏方案分析科目<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/queryAccMultiPlanSubj", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccMultiPlanSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
		String accMultiPlanSubj = accMultiPlanSubjService.queryAccMultiPlanSubj(getPage(mapVo));

		return JSONObject.parseObject(accMultiPlanSubj);
		
	}
	/**
	*多栏方案分析科目<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/deleteAccMultiPlanSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccMultiPlanSubj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            
            listVo.add(mapVo);
        }
		
		String accMultiPlanSubjJson = accMultiPlanSubjService.deleteBatchAccMultiPlanSubj(listVo);
	  
		return JSONObject.parseObject(accMultiPlanSubjJson);
			
	}
	
	/**
	*多栏方案分析科目<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsubjledger/accMultiPlanSubjUpdatePage", method = RequestMethod.GET)
	
	public String accMultiPlanSubjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
       
		AccMultiPlanSubj accMultiPlanSubj = new AccMultiPlanSubj();
		
        accMultiPlanSubj = accMultiPlanSubjService.queryAccMultiPlanSubjByCode(mapVo);
        
		mode.addAttribute("plan_code", accMultiPlanSubj.getPlan_code());
		
		mode.addAttribute("group_id", accMultiPlanSubj.getGroup_id());
		
		mode.addAttribute("hos_id", accMultiPlanSubj.getHos_id());
		
		mode.addAttribute("copy_code", accMultiPlanSubj.getCopy_code());
		
		mode.addAttribute("plan_name", accMultiPlanSubj.getPlan_name());
		
		mode.addAttribute("subj_code", accMultiPlanSubj.getSubj_code());
		
		mode.addAttribute("subj_name", accMultiPlanSubj.getSubj_name());
		
		return "hrp/acc/accsubjnature/accMultiPlanSubjUpdate";
	}
	/**
	*多栏方案分析科目<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accsubjledger/updateAccMultiPlanSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccMultiPlanSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accMultiPlanSubjJson = accMultiPlanSubjService.updateAccMultiPlanSubj(mapVo);
		
		return JSONObject.parseObject(accMultiPlanSubjJson);
	}
	

	@RequestMapping(value = "/hrp/acc/accsubjledger/queryAccMultiPlanSubjList", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccMultiPlanSubjList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String accMultiPlanSubjJson = accMultiPlanSubjService.queryAccMultiPlanSubjList(mapVo);
		
		return accMultiPlanSubjJson;
	}
	

}

