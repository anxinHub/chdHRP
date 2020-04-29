/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wage;
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
import com.chd.hrp.acc.serviceImpl.wage.AccWageCalServiceImpl;

/**
* @Title. @Description.
* 工资套合并计算公式
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageCalController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageCalController.class);
	
	
	@Resource(name = "accWageCalService")
	private final AccWageCalServiceImpl accWageCalService = null;
   
    
	/**
	*工资套合并计算公式<BR>
	*维护页面跳转AccWageCalAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagecal/accWageCalMainPage", method = RequestMethod.GET)
	public String accWageCalMainPage(Model mode) throws Exception {

		return "hrp/acc/accwagecal/accWageCalMain";

	}
	
	/**
	*工资套合并页面<BR>
	*维护页面跳转AccWageCalAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagecal/accWagePayAddMainPage", method = RequestMethod.GET)
	public String accWagePayAddMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String [] wage = mapVo.get("wage_code").toString().split(",");
		
		StringBuffer wage_code = new StringBuffer();
		
		StringBuffer wage_name = new StringBuffer();
		
		for (int i = 0; i < wage.length; i++) {
			
			wage_code.append("'"+wage[i].split("@")[0]+"',");
			
			wage_name.append("【"+wage[i].split("@")[1]+"】,");
			
			mode.addAttribute("acc_year",wage[i].split("@")[2].split("\\.")[0]);
			
			mode.addAttribute("acc_month",wage[i].split("@")[2].split("\\.")[1]);
			
			mode.addAttribute("group_id",wage[i].split("@")[3]);
			
			mode.addAttribute("hos_id",wage[i].split("@")[4]);
			
			mode.addAttribute("copy_code",wage[i].split("@")[5]);
			
		}
		
		mode.addAttribute("wage_code",wage_code.substring(0,wage_code.length()-1));
		
		mode.addAttribute("wage_name",wage_name.substring(0,wage_name.length()-1));
		
		return "hrp/acc/accwagecal/accWagePayAdd";

	}
	
	/**
	*工资套合并计算公式<BR>
	*计算公式页面跳转AccWageCalAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagecal/accWageCalSetMainPage", method = RequestMethod.GET)
	public String accWageCalSetMainPage(Model mode) throws Exception {

		return "hrp/acc/accwagecal/accWageCalSetMain";

	}
	/**
	*工资套合并计算公式<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwagecal/accWageCalAddPage", method = RequestMethod.GET)
	public String accWageCalAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("wage_item_code", mapVo.get("wage_item_code"));
		
		mode.addAttribute("wage_item_name", mapVo.get("wage_item_name"));
		
		mode.addAttribute("acc_year", mapVo.get("year_month"));
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		mode.addAttribute("wage_name", mapVo.get("wage_name"));

		return "hrp/acc/accwagecal/accWageCalAdd";

	}
	
	/**
	*工资套合并计算公式<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accwagecal/addAccWageCal", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWageCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String accWageCalJson = accWageCalService.addAccWageCal(mapVo);

		return JSONObject.parseObject(accWageCalJson);
		
	}
	/**
	*工资套合并计算公式<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwagecal/queryAccWageCal", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWageCal = accWageCalService.queryAccWageCal(getPage(mapVo));

		return JSONObject.parseObject(accWageCal);
		
	}
	
	/**
	*根据工资套查询工资套合并<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwagecal/queryAccWageItem", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
        String accWageCal = accWageCalService.queryAccWageCal(getPage(mapVo));

		return JSONObject.parseObject(accWageCal);
		
	}
	/**
	*工资套合并计算公式<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwagecal/deleteAccWageCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageCal(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("cal_id", id);//实际实体类变量
            
            mapVo.put("group_id", SessionManager.getGroupId());
            
    		mapVo.put("hos_id", SessionManager.getHosId());
            
    		mapVo.put("copy_code", SessionManager.getCopyCode());
    		
    		/*if(!"".equals(id.split("@")[1])&&id.split("@")[1] != null){
    			
    			mapVo.put("acc_year", id.split("@")[1]);
    			
    		}else{
    			
    			mapVo.put("acc_year", SessionManager.getAcctYear());
    			
    		}*/
    		
           
            listVo.add(mapVo);
        }
		
		String accWageCalJson = accWageCalService.deleteBatchAccWageCal(listVo);
	   
		return JSONObject.parseObject(accWageCalJson);
			
	}
	
	/**
	*工资套合并计算公式<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwagecal/accWageCalUpdatePage", method = RequestMethod.GET)
	
	public String accWageCalUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		/*AccWageCal = AccWageCalService.queryAccWageCalByCode(mapVo);
		mode.addAttribute("para_code", AccWageCal.getPara_code());
		mode.addAttribute("para_name", AccWageCal.getPara_name());
		mode.addAttribute("group_id", AccWageCal.getGroup_id());
		mode.addAttribute("hos_id", AccWageCal.getHos_id());
		mode.addAttribute("copy_code", AccWageCal.getCopy_code());
		mode.addAttribute("mod_code", AccWageCal.getMod_code());
		mode.addAttribute("para_type", AccWageCal.getPara_type());
		mode.addAttribute("para_json", AccWageCal.getPara_json());
		mode.addAttribute("para_value", AccWageCal.getPara_value());
		mode.addAttribute("note", AccWageCal.getNote());
		mode.addAttribute("is_stop", AccWageCal.getIs_stop());*/
		return "hrp/acc/accwagecal/accWageCalUpdate";
	}
	/**
	*工资套合并计算公式<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwagecal/updateAccWageCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWageCalJson = accWageCalService.updateAccWageCal(mapVo);
		
		return JSONObject.parseObject(accWageCalJson);
	}
	
}

