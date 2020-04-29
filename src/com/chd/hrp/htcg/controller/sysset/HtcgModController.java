package com.chd.hrp.htcg.controller.sysset;
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
import com.chd.hrp.htcg.service.syset.HtcgStartModService;

/**
 * 
 * @Description:
 * 050112 系统启用设置
 * @Table:
 * @Version: 1.0
 */
 


@Controller
public class HtcgModController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcgModController.class);
	
	@Resource(name = "htcgStartModService")
	private final HtcgStartModService htcgStartModService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/htcg/sysset/mod/htcgModMainPage", method = RequestMethod.GET)
	public String htcgModMainPage(Model mode) throws Exception {
		return "hrp/htcg/sysset/mod/htcgModMain";

	}
	// 查询
	@RequestMapping(value = "/hrp/htcg/sysset/mod/queryhtcgMod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryhtcgMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String mod = "" ;
		
		mapVo.put("mod_code", "13%");
		
		mapVo.put("group_id", SessionManager.getGroupId());
        
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		mapVo.put("acc_year", SessionManager.getAcctYear());
        
		try{
		
			  mod = htcgStartModService.queryhtcgMod(getPage(mapVo));
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	
		return JSONObject.parseObject(mod);
		
	}
		/**
		*系统启用<BR>
		*保存
		*/
		@RequestMapping(value = "/hrp/htcg/sysset/mod/saveHtcgMod", method = RequestMethod.POST)
		@ResponseBody
		
		public Map<String, Object> saveHtcgMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			String modStartJson = "" ;
			
			mapVo.put("group_id", SessionManager.getGroupId());
	        
			mapVo.put("hos_id", SessionManager.getHosId());
	        
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			mapVo.put("create_user", SessionManager.getUserCode());
	        
			mapVo.put("create_date", new Date());
	        
			try{
			
				  modStartJson = htcgStartModService.saveHtcgMod(mapVo);
			
			}catch(Exception e){
				
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}
			
			return JSONObject.parseObject(modStartJson);
			
		}
    
}

