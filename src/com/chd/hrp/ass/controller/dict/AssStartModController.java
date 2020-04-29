
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.controller.dict;
import java.util.Date;
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
import com.chd.hrp.ass.service.dict.AssStartModService;

/**
 * 
 * @Description:
 * 050112 系统启用设置
 * @Table:
 * ASS_START_MONTH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class AssStartModController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssStartModController.class);
	
	@Resource(name = "assStartModService")
	private final AssStartModService assStartModService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assmod/assModMainPage", method = RequestMethod.GET)
	public String assStartMonthMainPage(Model mode) throws Exception {

		return "hrp/ass/assmod/assModMain";

	}
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assmod/issModMainPage", method = RequestMethod.GET)
	public String issStartMonthMainPage(Model mode) throws Exception {

		return "hrp/ass/assmod/issModMain";

	}
	// 查询
		@RequestMapping(value = "/hrp/ass/assmod/assStartQueryMod", method = RequestMethod.POST)
		@ResponseBody
		
		public Map<String, Object> assStartQueryMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			String mod = "" ;
			
			mapVo.put("mod_code", "05%");
			
			mapVo.put("group_id", SessionManager.getGroupId());
	        
			mapVo.put("hos_id", SessionManager.getHosId());
	        
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			mapVo.put("acc_year", SessionManager.getAcctYear());
	        
			try{
			
				  mod = assStartModService.queryMod(getPage(mapVo));
			
			}catch(Exception e){
				
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}
		
			return JSONObject.parseObject(mod);
			
		}
		// 查询
				@RequestMapping(value = "/hrp/ass/assmod/issStartQueryMod", method = RequestMethod.POST)
				@ResponseBody
				
				public Map<String, Object> issStartQueryMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
					
					String mod = "" ;
					
					mapVo.put("mod_code", "14%");
					
					mapVo.put("group_id", SessionManager.getGroupId());
			        
					mapVo.put("hos_id", SessionManager.getHosId());
			        
					mapVo.put("copy_code", SessionManager.getCopyCode());
			        
					mapVo.put("acc_year", SessionManager.getAcctYear());
			        
					try{
					
						  mod = assStartModService.queryMod(getPage(mapVo));
					
					}catch(Exception e){
						
						return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
					}
				
					return JSONObject.parseObject(mod);
					
				}
		/**
		*系统启用<BR>
		*保存
		*/
		@RequestMapping(value = "/hrp/ass/assmod/addModStart", method = RequestMethod.POST)
		@ResponseBody
		
		public Map<String, Object> addModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			String modStartJson = "" ;
			
			mapVo.put("group_id", SessionManager.getGroupId());
	        
			mapVo.put("hos_id", SessionManager.getHosId());
	        
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			mapVo.put("create_user", SessionManager.getUserCode());
	        
			mapVo.put("create_date", new Date());
	        
			try{
			
				  modStartJson = assStartModService.addModStart(mapVo);
			
			}catch(Exception e){
				
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}
			
			return JSONObject.parseObject(modStartJson);
			
		}
    
}

