/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.repair;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.service.AccParaService;
import com.chd.hrp.ass.service.repair.AssRepairApplyService;
/**
 * 
 * @Description:
 * 051201 维修提醒 
 * @Table:
 * ASS_REPAIR_APPLY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class AssRepairRemindController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRepairRemindController.class);
	
	//引入Service服务
	@Resource(name = "assRepairApplyService")
	private final AssRepairApplyService assRepairApplyService = null;
	
	@Resource(name = "accParaService")
	private final AccParaService accParaService = null;
 
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/assRepairRemindMainPage", method = RequestMethod.GET)
	public String assRepairRemindMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05002",MyConfig.getSysPara("05002"));
		
		return "hrp/ass/assrepairremind/assRepairRemindMain";

	}
	
	/**
	 * @Description 
	 * 查询数据 051201 资产维修
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/queryAssRepairRemind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairRemind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		/*Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("group_id", SessionManager.getGroupId());
		
		map.put("hos_id", SessionManager.getHosId());
		
		map.put("copy_code", SessionManager.getCopyCode());
		
		map.put("mod_code", "05"); 
		
		map.put("para_code", "05002");
		 
		AccPara accPara = accParaService.queryAccParaByCode(map);
		
		Integer para_value = Integer.parseInt(accPara.getPara_value()) ;
	 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date date =new Date() ;
		 
		 String audit_date = sdf.format(date.getTime()-para_value* 24 * 60 * 60 * 1000) ;
		  
         mapVo.put("audit_date",DateUtil.stringToDate(audit_date, "yyyy-MM-dd") );*/
		
      if(mapVo.get("queryDate") == null/*||mapVo.get("queryDate").toString().equals("")*/){
			
			mapVo.put("queryDate", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
	        
		}
        String assRepairRemind = assRepairApplyService.queryAssRepairApply(getPage(mapVo));

		return JSONObject.parseObject(assRepairRemind);
		
	}
	
	 
 
}

