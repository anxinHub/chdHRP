
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hr.controller.base;
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
import com.chd.hrp.hr.service.base.HrStoreModService;

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
public class HrModStartController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrModStartController.class);
	
	@Resource(name = "hrStartModService")
	private final HrStoreModService hrStartModService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hr/hrmod/modMainPage", method = RequestMethod.GET)
	public String assStartMonthMainPage(Model mode) throws Exception {

		return "hrp/hr/hrmod/hrModMain";

	}
	// 查询
		@RequestMapping(value = "/hrp/hr/hrmod/hrStartQueryMod", method = RequestMethod.POST)
		@ResponseBody
		
		public Map<String, Object> hrStartQueryMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			String mod = "" ;
			
			mapVo.put("mod_code", "06%");
			
			mapVo.put("group_id", SessionManager.getGroupId());
	        
			mapVo.put("hos_id", SessionManager.getHosId());
	        
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			mapVo.put("acc_year", SessionManager.getAcctYear());
	        
			try{
			
				  mod = hrStartModService.queryMod(getPage(mapVo));
			
			}catch(Exception e){
				
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}
		
			return JSONObject.parseObject(mod);
			
		}
		/**
		*系统启用<BR>
		*保存
		*/
		@RequestMapping(value = "/hrp/hr/hrmodstart/addModStart", method = RequestMethod.POST)
		@ResponseBody
		
		public Map<String, Object> addModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			String modStartJson = "" ;
			
			mapVo.put("group_id", SessionManager.getGroupId());
	        
			mapVo.put("hos_id", SessionManager.getHosId());
	        
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			mapVo.put("create_user", SessionManager.getUserCode());
	        
			mapVo.put("create_date", new Date());
	        
			try{
			
				  modStartJson = hrStartModService.addModStart(mapVo);
			
			}catch(Exception e){
				
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}
			
			return JSONObject.parseObject(modStartJson);
			
		}
    
}

