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

import org.apache.commons.lang.ArrayUtils;
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
import com.chd.hrp.acc.entity.AccWageTax;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageTaxServiceImpl;

/**
* @Title. @Description.
* 工资套合并日志
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageTaxController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageTaxController.class);
	
	
	@Resource(name = "accWageTaxService")
	private final AccWageTaxServiceImpl accWageTaxService = null;
   
    
	/**
	*
	*维护页面跳转AccWageTaxAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagetax/accWageTaxMainPage", method = RequestMethod.GET)
	public String accWageTaxMainPage(Model mode) throws Exception {

		return "hrp/acc/accwagetax/accWageTaxMain";

	}
	
	@RequestMapping(value = "/hrp/acc/accwagetax/accWageTaxIndexMainPage", method = RequestMethod.GET)
	public String accWageTaxIndexMainPage(Model mode) throws Exception {

		return "hrp/acc/accwagetax/accWageTaxIndexMain";

	}
	/**
	*
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwagetax/accWageTaxAddPage", method = RequestMethod.GET)
	public String accWageTaxAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		return "hrp/acc/accwagetax/accWageTaxAdd";

	}
	
	/**
	*
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accwagetax/addAccWageTax", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWageTax(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String accWageTaxJson="";
		
		if(!"".equals(paramVo) && null!=paramVo){
			
			for ( int i=0; i<paramVo.split(",").length;i++) {
				
				String [] id = paramVo.split(",");
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String [] rs = id[i].split("@");
				
				if(rs.length < 5) {
					String[] newRs = new String[5];
					System.arraycopy(rs, 0, newRs, 0, rs.length);
					rs = newRs;
				}
				
				if(!"undefined".equals(rs[0])&&!"".equals(rs[0]) && null != rs[0]){
					
					mapVo.put("group_id", SessionManager.getGroupId());
				       
					mapVo.put("hos_id", SessionManager.getHosId());
					
					mapVo.put("copy_code", SessionManager.getCopyCode());
					
					mapVo.put("note", rs[0]);
					
					if(null==rs[1]||"".equals(rs[1])){
						
						mapVo.put("starts", "0");
						
					}else{
						
						mapVo.put("starts", rs[1]);
						
					}
						
					if(null==rs[2]||"".equals(rs[2])||"undefined".equals(rs[2])||"0".equals(rs[2])){
						
						mapVo.put("ends", "0");
						
						if(i!=id.length-1){
							
							accWageTaxJson="{\"msg\":\"添加失败,终点数已是正无穷大.\",\"state\":\"false\"}";
						}
						
					}else{
						
						mapVo.put("ends", rs[2]);
						
					}
					
					mapVo.put("rate", rs[3]);
					
					mapVo.put("deduct", rs[4]);
					
		            listVo.add(mapVo);
					
				}
	        }
		
			if("".equals(accWageTaxJson)){
				
				try {
					
					accWageTaxJson = accWageTaxService.addBatchAccWageTax(listVo);
					
				} catch (Exception e) {
					
					accWageTaxJson = e.getMessage();
					
				}
				
			}
			
			return JSONObject.parseObject(accWageTaxJson);
			
		}
		
		return JSONObject.parseObject(accWageTaxJson);
		
	}
	/**
	*
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwagetax/queryAccWageTax", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageTax(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWageTax = accWageTaxService.queryAccWageTax(getPage(mapVo));

		return JSONObject.parseObject(accWageTax);
		
	}
	/**
	*
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwagetax/deleteAccWageTax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageTax(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("rate_id", id);//实际实体类变量
            
            mapVo.put("group_id", SessionManager.getGroupId());
            
    		mapVo.put("hos_id", SessionManager.getHosId());
            
    		mapVo.put("copy_code", SessionManager.getCopyCode());
           
            listVo.add(mapVo);
        }
		
		String accWageTaxJson = accWageTaxService.deleteBatchAccWageTax(listVo);
	   
		return JSONObject.parseObject(accWageTaxJson);
			
	}
	
	/**
	*
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwagetax/accWageTaxUpdatePage", method = RequestMethod.GET)
	
	public String accWageTaxUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccWageTax accWageTax = new AccWageTax();
		/*AccWageTax = AccWageTaxService.queryAccWageTaxByCode(mapVo);
		mode.addAttribute("para_code", AccWageTax.getPara_code());
		mode.addAttribute("para_name", AccWageTax.getPara_name());
		mode.addAttribute("group_id", AccWageTax.getGroup_id());
		mode.addAttribute("hos_id", AccWageTax.getHos_id());
		mode.addAttribute("copy_code", AccWageTax.getCopy_code());
		mode.addAttribute("mod_code", AccWageTax.getMod_code());
		mode.addAttribute("para_type", AccWageTax.getPara_type());
		mode.addAttribute("para_json", AccWageTax.getPara_json());
		mode.addAttribute("para_value", AccWageTax.getPara_value());
		mode.addAttribute("note", AccWageTax.getNote());
		mode.addAttribute("is_stop", AccWageTax.getIs_stop());*/
		return "hrp/acc/accwagetax/accWageTaxUpdate";
	}
	/**
	*
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwagetax/updateAccWageTax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageTax(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWageTaxJson = accWageTaxService.updateAccWageTax(mapVo);
		
		return JSONObject.parseObject(accWageTaxJson);
	}
	/**
	*
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagetax/importAccWageTax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWageTax(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWageTaxJson = accWageTaxService.importAccWageTax(mapVo);
		
		return JSONObject.parseObject(accWageTaxJson);
	}
	
}

