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
import com.chd.hrp.acc.service.books.memorandumbook.AccEducationService;

@Controller
public class AccEducationController extends BaseController{
private static Logger logger = Logger.getLogger(AccEducationController.class);
	
	@Resource(name = "accEducationService")
	private final AccEducationService accEducationService = null;
	
	
	@RequestMapping(value = "/hrp/acc/acceducation/accEducationMainPage", method = RequestMethod.GET)
	public String accEducationMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/acceducation/accEducationMain";
	}
	
	//跳转项目备查簿东阳专用的页面
	@RequestMapping(value = "/hrp/acc/acceducation/accEducationBySplitMainPage", method = RequestMethod.GET)
	public String accEducationBySplitMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		//返回当前月
		int month = new Date().getMonth() + 1;
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", month < 10 ? "0"+month:month);
		return "hrp/acc/acceducation/accEducationBySplitMain";
	}
	
	
	@RequestMapping(value = "/hrp/acc/acceducation/queryAccEducation", method = RequestMethod.POST)
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
		String json = accEducationService.queryAccEducation(getPage(mapVo));

		return JSONObject.parseObject(json);
	}
	//项目备查簿 东阳专用
	@RequestMapping(value = "/hrp/acc/acceducation/queryAccEducationBySplit", method = RequestMethod.POST)
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
		String json = accEducationService.queryAccEducationBySplit(getPage(mapVo));
		
		return JSONObject.parseObject(json);
	}
	
	@RequestMapping(value = "/hrp/acc/acceducation/acceducationDataMiningPage", method = RequestMethod.GET)
	public String acceducationDataMiningPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		  String identification = mapVo.get("identification").toString();
	        
	        if("Education_total_sr".equals(identification)){
	        	/*合计资金 本期收入*/
	        	 mode.addAttribute("identification", identification);
	        	 
	        	return "hrp/acc/acceducation/accEducationDataMiningTotal";
	        	
	        }else if("Education_total_ot".equals(identification)){
	        	/*合计资金 本期支出*/
	       	    mode.addAttribute("identification", identification);
	       	    
	        	return "hrp/acc/acceducation/accEducationDataMiningTotal";
	        	
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
		
		return "hrp/acc/acceducation/accEducationDataMining";
	}
	
	//项目备查簿 跳转凭证 东阳专用
	@RequestMapping(value = "/hrp/acc/acceducation/acceducationDataMiningByDyPage", method = RequestMethod.GET)
	public String acceducationDataMiningByDyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String identification = mapVo.get("identification").toString();
		
		if("Education_total_sr_dy".equals(identification)){
			/*合计资金 本期收入*/
			mode.addAttribute("identification", identification);
			
			return "hrp/acc/acceducation/accEducationDataMiningTotal";
			
		}else if("Education_total_ot_dy".equals(identification)){
			
			/*合计资金 本期支出*/
			mode.addAttribute("identification", identification);
			
			return "hrp/acc/acceducation/accEducationDataMiningTotal";
			
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
		
		return "hrp/acc/acceducation/accEducationDataMining";
	}
	
	@RequestMapping(value = "/hrp/acc/acceducation/queryAccEducationDataMining", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEducationDataMining(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());
		
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		    String year_month1 = mapVo.get("year_month1").toString();
		
		    mapVo.put("acc_year", year_month1.split("-")[0]);
		    
		    String json = accEducationService.queryAccEducationDataMining(mapVo);

		return JSONObject.parseObject(json);
	}
	
	//项目备查簿  东阳专用
	@RequestMapping(value = "/hrp/acc/acceducation/queryAccEducationDataMiningByDy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEducationDataMiningByDy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String year_month1 = mapVo.get("year_month1").toString();
		
		mapVo.put("acc_year", year_month1.split("-")[0]);
		
		String json = accEducationService.queryAccEducationDataMiningByDy(mapVo);
		
		return JSONObject.parseObject(json);
	}
}
