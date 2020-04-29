/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wagedata;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.acc.entity.AccWageLog;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageLogServiceImpl;

/**
* @Title. @Description.
* 工资套合并日志
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageLogController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageLogController.class);
	
	
	@Resource(name = "accWageLogService")
	private final AccWageLogServiceImpl accWageLogService = null;
   
    
	/**
	*工资套合并日志<BR>
	*维护页面跳转AccWageLogAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagelog/accWageLogMainPage", method = RequestMethod.GET)
	public String accWageLogMainPage(Model mode) throws Exception {

		return "hrp/acc/accwagelog/accWageLogMain";

	}
	/**
	*工资套合并日志<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwagelog/accWageLogAddPage", method = RequestMethod.GET)
	public String accWageLogAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("wage_item_code", mapVo.get("wage_item_code"));
		
		mode.addAttribute("wage_item_name", mapVo.get("wage_item_name"));
		
		mode.addAttribute("acc_year", mapVo.get("year_month"));
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		mode.addAttribute("wage_name", mapVo.get("wage_name"));

		return "hrp/acc/accwagelog/accWageLogAdd";

	}
	
	/**
	*工资套合并日志<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accwagelog/addAccWageLog", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWageLog(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			if(mapVo.get("rate") == null){
				
		        mapVo.put("rate", "");
		        
			}
			
			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("create_date", new Date());
		
		String accWageLogJson = accWageLogService.addAccWageLog(mapVo);

		return JSONObject.parseObject(accWageLogJson);
		
	}
	/**
	*工资套合并日志<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwagelog/queryAccWageLog", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageLog(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWageLog = accWageLogService.queryAccWageLog(getPage(mapVo));

		return JSONObject.parseObject(accWageLog);
		
	}
	/**
	*工资套合并日志<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwagelog/deleteAccWageLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageLog(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("t", id);//实际实体类变量
           
            listVo.add(mapVo);
        }
		
		String accWageLogJson = accWageLogService.deleteBatchAccWageLog(listVo);
	   
		return JSONObject.parseObject(accWageLogJson);
			
	}
	
	/**
	*工资套合并日志<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwagelog/accWageLogUpdatePage", method = RequestMethod.GET)
	
	public String accWageLogUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
       // AccWageLog accWageLog = new AccWageLog();
		/*AccWageLog = AccWageLogService.queryAccWageLogByCode(mapVo);
		mode.addAttribute("para_code", AccWageLog.getPara_code());
		mode.addAttribute("para_name", AccWageLog.getPara_name());
		mode.addAttribute("group_id", AccWageLog.getGroup_id());
		mode.addAttribute("hos_id", AccWageLog.getHos_id());
		mode.addAttribute("copy_code", AccWageLog.getCopy_code());
		mode.addAttribute("mod_code", AccWageLog.getMod_code());
		mode.addAttribute("para_type", AccWageLog.getPara_type());
		mode.addAttribute("para_json", AccWageLog.getPara_json());
		mode.addAttribute("para_value", AccWageLog.getPara_value());
		mode.addAttribute("note", AccWageLog.getNote());
		mode.addAttribute("is_stop", AccWageLog.getIs_stop());*/
		return "hrp/acc/accwagelog/accWageLogUpdate";
	}
	/**
	*工资套合并日志<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwagelog/updateAccWageLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageLog(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWageLogJson = accWageLogService.updateAccWageLog(mapVo);
		
		return JSONObject.parseObject(accWageLogJson);
	}
	/**
	*工资套合并日志<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagelog/importAccWageLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWageLog(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWageLogJson = accWageLogService.importAccWageLog(mapVo);
		
		return JSONObject.parseObject(accWageLogJson);
	}
	
}

