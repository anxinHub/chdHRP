package com.chd.hrp.acc.controller.books.memorandumbook;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccScienceEducationService;

@Controller
public class GroupAccScienceEducationController extends BaseController{
private static Logger logger = Logger.getLogger(GroupAccScienceEducationController.class);
	
	@Resource(name = "groupAccScienceEducationService")
	private final GroupAccScienceEducationService groupAccScienceEducationService = null;
	
	
	@RequestMapping(value = "/hrp/acc/accscienceeducation/group/groupAccScienceEducationMainPage", method = RequestMethod.GET)
	public String accScienceEducationMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/accscienceeducation/group/groupAccScienceEducationMain";
	}
	
	
	@RequestMapping(value = "/hrp/acc/accscienceeducation/group/queryGroupAccScienceEducation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccScienceEducation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String year_month1 = mapVo.get("year_month1").toString();
		
		mapVo.put("acc_year", year_month1.split("-")[0]);
		String json = groupAccScienceEducationService.queryGroupAccScienceEducation(getPage(mapVo));

		return JSONObject.parseObject(json);
	}
	
	@RequestMapping(value = "/hrp/acc/accscienceeducation/group/groupAccscienceeducationDataMiningPage", method = RequestMethod.GET)
	public String accscienceeducationDataMiningPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		  String identification = mapVo.get("identification").toString();
	        
	        if("ScienceEducation_total_sr".equals(identification)){
	        	/*合计资金 本期收入*/
	        	 mode.addAttribute("identification", identification);
	        	 
	        	return "hrp/acc/accscienceeducation/group/groupAccScienceEducationDataMiningTotal";
	        	
	        }else if("ScienceEducation_total_ot".equals(identification)){
	        	/*合计资金 本期支出*/
	       	    mode.addAttribute("identification", identification);
	       	    
	        	return "hrp/acc/accscienceeducation/group/groupAccScienceEducationDataMiningTotal";
	        	
	        }else if("ScienceEducation_bal_sr".equals(identification)){
	        	/*外拨资金 本期收入*/
	            mode.addAttribute("identification", identification);
	        	
	        }else if("ScienceEducation_bal_ot".equals(identification)){
	        	/*外拨资金 本期支出*/
	        	mode.addAttribute("identification", identification);
	        	
	        }else if("ScienceEducation_match_sr".equals(identification)){
	        	/*配套资金本期收入*/
	        	mode.addAttribute("identification", identification);
	        	
	        }else if("ScienceEducation_match_ot".equals(identification)){
	        	/*配套 资金本期收入*/
	        	mode.addAttribute("identification", identification);
	        }
		
		return "hrp/acc/accscienceeducation/group/groupAccScienceEducationDataMining";
	}
	
	@RequestMapping(value = "/hrp/acc/accscienceeducation/group/queryGroupAccScienceEducationDataMining", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccScienceEducationDataMining(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());
		
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		    String year_month1 = mapVo.get("year_month1").toString();
		
		    mapVo.put("acc_year", year_month1.split("-")[0]);
		    
		    String json = groupAccScienceEducationService.queryGroupAccScienceEducationDataMining(mapVo);

		return JSONObject.parseObject(json);
	}
}
