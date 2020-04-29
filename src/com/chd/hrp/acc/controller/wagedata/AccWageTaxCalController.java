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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccWageTaxCal;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageItemCalServiceImpl;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageTaxCalServiceImpl;

/**
* @Title. @Description.
* 个税计算公式
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageTaxCalController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageTaxCalController.class);
	
	
	@Resource(name = "accWageTaxCalService")
	private final AccWageTaxCalServiceImpl accWageTaxCalService = null;
	
	@Resource(name = "accWageItemCalService")
	private final AccWageItemCalServiceImpl accWageItemCalService = null;
   
    
	/**
	*个税计算公式<BR>
	*维护页面跳转AccWageTaxCalAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/accWageTaxCalMainPage", method = RequestMethod.GET)
	public String accWageTaxCalMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accwagetaxcal/accWageTaxCalMain";

	}
	
	/**
	*个税计算公式<BR>
	*设置公式页面跳转AccWageTaxCalAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/accWageTaxCalSetPage", method = RequestMethod.GET)
	public String accWageTaxCalSetPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		java.net.URLDecoder urlDecoder=new java.net.URLDecoder();
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		mode.addAttribute("cal_name", urlDecoder.decode(mapVo.get("cal_name").toString(),"utf-8"));
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		return "hrp/acc/accwagetaxcal/accWageTaxCalSet";

	}
	
	/**
	*个税计算公式<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/accWageTaxCalAddPage", method = RequestMethod.GET)
	public String accWageTaxCalAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("wage_item_code", mapVo.get("wage_item_code"));
		
		mode.addAttribute("wage_item_name", mapVo.get("wage_item_name"));
		
		mode.addAttribute("acc_year", mapVo.get("year_month"));
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		mode.addAttribute("wage_name",mapVo.get("wage_name"));

		return "hrp/acc/accwagetaxcal/accWageTaxCalAdd";

	}
	
	/**
	*个税计算公式<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/addAccWageTaxCal", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWageTaxCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			String accWageTaxCalJson = accWageTaxCalService.addAccWageTaxCal(mapVo);

		return JSONObject.parseObject(accWageTaxCalJson);
		
	}
	/**
	*个税计算公式<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/queryAccWageTaxCal", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageTaxCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWageTaxCal = accWageTaxCalService.queryAccWageTaxCal(getPage(mapVo));

		return JSONObject.parseObject(accWageTaxCal);
		
	}
	/**
	*个税计算公式<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/deleteAccWageTaxCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageTaxCal(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("group_id", SessionManager.getGroupId());
		       
			mapVo.put("hos_id", SessionManager.getHosId());
	        
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
            mapVo.put("cal_id", id);//实际实体类变量
           
            listVo.add(mapVo);
        }
		
		String accWageTaxCalJson = accWageTaxCalService.deleteBatchAccWageTaxCal(listVo);
	   
		return JSONObject.parseObject(accWageTaxCalJson);
			
	}
	
	/**
	*个税计算公式<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/accWageTaxCalUpdatePage", method = RequestMethod.GET)
	
	public String accWageTaxCalUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccWageTaxCal accWageTaxCal = new AccWageTaxCal();
		/*AccWageTaxCal = AccWageTaxCalService.queryAccWageTaxCalByCode(mapVo);
		mode.addAttribute("para_code", AccWageTaxCal.getPara_code());
		mode.addAttribute("para_name", AccWageTaxCal.getPara_name());
		mode.addAttribute("group_id", AccWageTaxCal.getGroup_id());
		mode.addAttribute("hos_id", AccWageTaxCal.getHos_id());
		mode.addAttribute("copy_code", AccWageTaxCal.getCopy_code());
		mode.addAttribute("mod_code", AccWageTaxCal.getMod_code());
		mode.addAttribute("para_type", AccWageTaxCal.getPara_type());
		mode.addAttribute("para_json", AccWageTaxCal.getPara_json());
		mode.addAttribute("para_value", AccWageTaxCal.getPara_value());
		mode.addAttribute("note", AccWageTaxCal.getNote());
		mode.addAttribute("is_stop", AccWageTaxCal.getIs_stop());*/
		return "hrp/acc/accwagetaxcal/accWageTaxCalUpdate";
	}
	/**
	*个税计算公式<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/updateAccWageTaxCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageTaxCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWageTaxCalJson = accWageTaxCalService.updateAccWageTaxCal(mapVo);
		
		return JSONObject.parseObject(accWageTaxCalJson);
	}
	/**
	*个税计算公式<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/importAccWageTaxCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWageTaxCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWageTaxCalJson = accWageTaxCalService.importAccWageTaxCal(mapVo);
		
		return JSONObject.parseObject(accWageTaxCalJson);
	}
	
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/extendAccWageTaxCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccWageItemCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWageItemCal = accWageTaxCalService.extendAccWageTaxCal(mapVo);

		return JSONObject.parseObject(accWageItemCal);
		
	}
	
	/**
	*工资项目计算公式<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/queryAccWageTaxCalById", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageTaxCalById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String result=accWageTaxCalService.queryAccWageTaxCalById(mapVo);
		
		return  JSONObject.parseObject("{\"rest\":\""+result+"\"}");
		
	}
	
	/**
	*个税计算公式<BR>
	*设置公式页面跳转AccWageTaxCalAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagetaxcal/accWageTaxSetCal", method = RequestMethod.GET)
	public String accWageTaxSetCal(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("ele_code", mapVo.get("ele_code"));
		
		//mode.addAttribute("cal_name", mapVo.get("cal_name"));
		
		return "hrp/acc/accwagetaxcal/accWageTaxSetCal";

	}
	
}

