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
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccEducationService;

@Controller
public class GroupAccEducationController extends BaseController{
private static Logger logger = Logger.getLogger(GroupAccEducationController.class);
	
	@Resource(name = "groupAccEducationService")
	private final GroupAccEducationService groupAccEducationService = null;
	
	
	@RequestMapping(value = "/hrp/acc/acceducation/group/groupAccEducationMainPage", method = RequestMethod.GET)
	public String accEducationMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/acceducation/group/groupAccEducationMain";
	}
	
	//跳转项目备查簿东阳专用的页面
	@RequestMapping(value = "/hrp/acc/acceducation/group/groupAccEducationBySplitMainPage", method = RequestMethod.GET)
	public String accEducationBySplitMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/acceducation/group/groupAccEducationBySplitMain";
	}
	
	
	@RequestMapping(value = "/hrp/acc/acceducation/group/queryGroupAccEducation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEducation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		String json = groupAccEducationService.queryGroupAccEducation(getPage(mapVo));

		return JSONObject.parseObject(json);
	}
	//项目备查簿 东阳专用
	@RequestMapping(value = "/hrp/acc/acceducation/group/queryGroupAccEducationBySplit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEducationBySplit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String json = groupAccEducationService.queryGroupAccEducationBySplit(getPage(mapVo));
		
		return JSONObject.parseObject(json);
	}
	
	@RequestMapping(value = "/hrp/acc/acceducation/group/groupAcceducationDataMiningPage", method = RequestMethod.GET)
	public String acceducationDataMiningPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		  String identification = mapVo.get("identification").toString();
	        
	        if("Education_total_sr".equals(identification)){
	        	/*合计资金 本期收入*/
	        	 mode.addAttribute("identification", identification);
	        	 
	        	return "hrp/acc/acceducation/group/groupAccEducationDataMiningTotal";
	        	
	        }else if("Education_total_ot".equals(identification)){
	        	/*合计资金 本期支出*/
	       	    mode.addAttribute("identification", identification);
	       	    
	        	return "hrp/acc/acceducation/group/groupAccEducationDataMiningTotal";
	        	
	        }else if("Education_bal_sr".equals(identification)){
	        	/*外拨资金 本期收入*/
	            mode.addAttribute("identification", identification);
	        	
	        }else if("Education_bal_ot".equals(identification)){
	        	/*外拨资金 本期支出*/
	        	mode.addAttribute("identification", identification);
	        	
	        }else if("Education_match_sr".equals(identification)){
	        	/*配套资金本期收入*/
	        	mode.addAttribute("identification", identification);
	        	
	        }else if("Education_match_ot".equals(identification)){
	        	/*配套 资金本期收入*/
	        	mode.addAttribute("identification", identification);
	        }
		
		return "hrp/acc/acceducation/group/groupAccEducationDataMining";
	}
	
	//项目备查簿 跳转凭证 东阳专用
	@RequestMapping(value = "/hrp/acc/acceducation/group/groupAcceducationDataMiningByDyPage", method = RequestMethod.GET)
	public String acceducationDataMiningByDyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String identification = mapVo.get("identification").toString();
		
		if("Education_total_sr_dy".equals(identification)){
			/*合计资金 本期收入*/
			mode.addAttribute("identification", identification);
			
			return "hrp/acc/acceducation/group/groupAccEducationDataMiningTotal";
			
		}else if("Education_total_ot_dy".equals(identification)){
			
			/*合计资金 本期支出*/
			mode.addAttribute("identification", identification);
			
			return "hrp/acc/acceducation/group/groupAccEducationDataMiningTotal";
			
		}else if("Education_bal_sr_cz_dy".equals(identification)){
			/*财政外拨资金 本期收入*/
			mode.addAttribute("identification", identification);
			
		}else if("Education_bal_ot_cz_dy".equals(identification)){
			/*财政外拨资金 本期支出*/
			mode.addAttribute("identification", identification);
			
		}else if("Education_bal_sr_kj_dy".equals(identification)){
			/*科教外拨资金 本期收入*/
			mode.addAttribute("identification", identification);
			
		}else if("Education_bal_ot_kj_dy".equals(identification)){
			/*科教外拨资金 本期支出*/
			mode.addAttribute("identification", identification);
			
		}else if("Education_match_sr".equals(identification)){
			/*配套资金本期收入*/
			mode.addAttribute("identification", identification);
			
		}else if("Education_match_ot".equals(identification)){
			/*配套 资金本期收入*/
			mode.addAttribute("identification", identification);
		}
		
		return "hrp/acc/acceducation/group/groupAccEducationDataMining";
	}
	
	@RequestMapping(value = "/hrp/acc/acceducation/group/queryGroupAccEducationDataMining", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEducationDataMining(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());
		
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		    String year_month1 = mapVo.get("year_month1").toString();
		
		    mapVo.put("acc_year", year_month1.split("-")[0]);
		    
		    String json = groupAccEducationService.queryGroupAccEducationDataMining(mapVo);

		return JSONObject.parseObject(json);
	}
	
	//项目备查簿  东阳专用
	@RequestMapping(value = "/hrp/acc/acceducation/group/queryGroupAccEducationDataMiningByDy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEducationDataMiningByDy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String year_month1 = mapVo.get("year_month1").toString();
		
		mapVo.put("acc_year", year_month1.split("-")[0]);
		
		String json = groupAccEducationService.queryGroupAccEducationDataMiningByDy(mapVo);
		
		return JSONObject.parseObject(json);
	}
}
