/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wagedata;
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
import com.chd.hrp.acc.entity.AccWageTaxSet;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageTaxSetServiceImpl;

/**
* @Title. @Description.
* 工资套合并日志
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageTaxSetController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageTaxSetController.class);
	
	
	@Resource(name = "accWageTaxSetService")
	private final AccWageTaxSetServiceImpl accWageTaxSetService = null;
   
    
	/**
	*
	*维护页面跳转AccWageTaxSetAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagetaxset/accWageTaxSetMainPage", method = RequestMethod.GET)
	public String accWageTaxSetMainPage(Model mode) throws Exception {
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
	       
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AccWageTaxSet accWageTaxSet = new AccWageTaxSet();
		
		accWageTaxSet = accWageTaxSetService.queryAccWageTaxSetByCode(mapVo);
		
		if(accWageTaxSet !=null){
			
			mode.addAttribute("tax_value", accWageTaxSet.getTax_value());
			
			mode.addAttribute("ass_value", accWageTaxSet.getAss_value());
			
		}

		return "hrp/acc/accwagetaxset/accWageTaxSetMain";

	}
	/**
	*
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwagetaxset/accWageTaxSetAddPage", method = RequestMethod.GET)
	public String accWageTaxSetAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("wage_item_code", mapVo.get("wage_item_code"));
		
		mode.addAttribute("wage_item_name", mapVo.get("wage_item_name"));
		
		mode.addAttribute("acc_year", mapVo.get("year_month"));
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		mode.addAttribute("wage_name", mapVo.get("wage_name"));

		return "hrp/acc/accwagetaxset/accWageTaxSetAdd";

	}
	
	/**
	*
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accwagetaxset/addAccWageTaxSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWageTaxSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			/*if(mapVo.get("rate") == null){
				
		        mapVo.put("rate", "");
		        
			}
			
			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("create_date", new Date());*/
		
		String accWageTaxSetJson = accWageTaxSetService.addAccWageTaxSet(mapVo);

		return JSONObject.parseObject(accWageTaxSetJson);
		
	}
	/**
	*
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwagetaxset/queryAccWageTaxSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageTaxSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWageTaxSet = accWageTaxSetService.queryAccWageTaxSet(getPage(mapVo));

		return JSONObject.parseObject(accWageTaxSet);
		
	}
	/**
	*
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwagetaxset/deleteAccWageTaxSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageTaxSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("t", id);//实际实体类变量
           
            listVo.add(mapVo);
        }
		
		String accWageTaxSetJson = accWageTaxSetService.deleteBatchAccWageTaxSet(listVo);
	   
		return JSONObject.parseObject(accWageTaxSetJson);
			
	}
	
	/**
	*
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwagetaxset/accWageTaxSetUpdatePage", method = RequestMethod.GET)
	
	public String accWageTaxSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccWageTaxSet accWageTaxSet = new AccWageTaxSet();
		/*AccWageTaxSet = AccWageTaxSetService.queryAccWageTaxSetByCode(mapVo);
		mode.addAttribute("para_code", AccWageTaxSet.getPara_code());
		mode.addAttribute("para_name", AccWageTaxSet.getPara_name());
		mode.addAttribute("group_id", AccWageTaxSet.getGroup_id());
		mode.addAttribute("hos_id", AccWageTaxSet.getHos_id());
		mode.addAttribute("copy_code", AccWageTaxSet.getCopy_code());
		mode.addAttribute("mod_code", AccWageTaxSet.getMod_code());
		mode.addAttribute("para_type", AccWageTaxSet.getPara_type());
		mode.addAttribute("para_json", AccWageTaxSet.getPara_json());
		mode.addAttribute("para_value", AccWageTaxSet.getPara_value());
		mode.addAttribute("note", AccWageTaxSet.getNote());
		mode.addAttribute("is_stop", AccWageTaxSet.getIs_stop());*/
		return "hrp/acc/accwagetaxset/accWageTaxSetUpdate";
	}
	/**
	*
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwagetaxset/updateAccWageTaxSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageTaxSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWageTaxSetJson = accWageTaxSetService.updateAccWageTaxSet(mapVo);
		
		return JSONObject.parseObject(accWageTaxSetJson);
	}
	/**
	*
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagetaxset/importAccWageTaxSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWageTaxSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWageTaxSetJson = accWageTaxSetService.importAccWageTaxSet(mapVo);
		
		return JSONObject.parseObject(accWageTaxSetJson);
	}
	
}

