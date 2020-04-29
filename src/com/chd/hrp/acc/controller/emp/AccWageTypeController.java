/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.emp;
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
import com.chd.hrp.acc.entity.AccWageType;
import com.chd.hrp.acc.serviceImpl.emp.AccWageTypeServiceImpl;

/**
* @Title. @Description.
* 账户类别
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageTypeController.class);
	
	
	@Resource(name = "accWageTypeService")
	private final AccWageTypeServiceImpl accWageTypeService = null;
   
    
	/**
	*账户类别<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagetype/accWageTypeMainPage", method = RequestMethod.GET)
	public String accWageTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/accwagetype/accWageTypeMain";

	}
	/**
	*账户类别<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwagetype/accWageTypeAddPage", method = RequestMethod.GET)
	public String accWageTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/accwagetype/accWageTypeAdd";

	}
	/**
	*账户类别<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accwagetype/addAccWageType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWageType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
		String accWageTypeJson = accWageTypeService.addAccWageType(mapVo);

		return JSONObject.parseObject(accWageTypeJson);
		
	}
	/**
	*账户类别<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwagetype/queryAccWageType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWageType = accWageTypeService.queryAccWageType(getPage(mapVo));

		return JSONObject.parseObject(accWageType);
		
	}
	/**
	*账户类别<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwagetype/deleteAccWageType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("type_id", res[0]);//实际实体类变量
            
            mapVo.put("group_id", res[1]);
            
            mapVo.put("hos_id", res[2]);
            
            mapVo.put("copy_code", res[3]);
           
            listVo.add(mapVo);
        }
		
		String accWageTypeJson = accWageTypeService.deleteBatchAccWageType(listVo);
	   
		return JSONObject.parseObject(accWageTypeJson);
			
	}
	
	/**
	*账户类别<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwagetype/accWageTypeUpdatePage", method = RequestMethod.GET)
	
	public String accWageTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
       
		AccWageType accWageType = new AccWageType();
		
		accWageType = accWageTypeService.queryAccWageTypeByUpdate(mapVo);
		
		mode.addAttribute("group_id", accWageType.getGroup_id());
		
		mode.addAttribute("hos_id", accWageType.getHos_id());
		
		mode.addAttribute("copy_code", accWageType.getCopy_code());
		
		mode.addAttribute("type_id", accWageType.getType_id());
		
		mode.addAttribute("type_code", accWageType.getType_code());
		
		mode.addAttribute("type_name", accWageType.getType_name());
		
		mode.addAttribute("bank_number", accWageType.getBank_number());
		
		mode.addAttribute("simple_name", accWageType.getSimple_name());
		
		mode.addAttribute("note", accWageType.getNote());
		
		mode.addAttribute("is_stop", accWageType.getIs_stop());
		
		return "hrp/acc/accwagetype/accWageTypeUpdate";
	}
	/**
	*账户类别<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwagetype/updateAccWageType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
		String accWageTypeJson = accWageTypeService.updateAccWageType(mapVo);
		
		return JSONObject.parseObject(accWageTypeJson);
	}
	

}

