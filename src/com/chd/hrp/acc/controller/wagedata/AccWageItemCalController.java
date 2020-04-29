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
import com.chd.hrp.acc.entity.AccWageCal;
import com.chd.hrp.acc.serviceImpl.wage.AccWageCalServiceImpl;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageItemCalServiceImpl;

/**
* @Title. @Description.
* 工资项目计算公式
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageItemCalController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageItemCalController.class);
	
	
	@Resource(name = "accWageItemCalService")
	private final AccWageItemCalServiceImpl accWageItemCalService = null;
   
	@Resource(name = "accWageCalService")
	private final AccWageCalServiceImpl accWageCalService = null;
    
	/**
	 * 【工资管理-工资数据-工资套合并】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accwageitemcal/accWageItemCalMainPage", method = RequestMethod.GET)
	public String accWageItemCalMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accwageitemcal/accWageItemCalMain";
	}
	
	/**
	*工资套合并页面<BR>
	*维护页面跳转AccWageItemCalAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwageitemcal/accWagePayAddMainPage", method = RequestMethod.GET)
	public String accWagePayAddMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String [] wage = mapVo.get("wage_code").toString().split(",");
		
		StringBuffer wage_code = new StringBuffer();
		
		StringBuffer wage_name = new StringBuffer();
		
		for (int i = 0; i < wage.length; i++) {
			
			wage_code.append("'"+wage[i].split("@")[0]+"',");
			
			wage_name.append("【"+wage[i].split("@")[1]+"】,");
			
			mode.addAttribute("acc_year",wage[i].split("@")[2].split("\\.")[0]);
			
			mode.addAttribute("acc_month",wage[i].split("@")[2].split("\\.")[1]);
			
			mode.addAttribute("group_id",wage[i].split("@")[3]);
			
			mode.addAttribute("hos_id",wage[i].split("@")[4]);
			
			mode.addAttribute("copy_code",wage[i].split("@")[5]);
			
		}
		
		mode.addAttribute("wage_code",wage_code.substring(0,wage_code.length()-1));
		
		mode.addAttribute("wage_name",wage_name.substring(0,wage_name.length()-1));
		
		return "hrp/acc/accwageitemcal/accWagePayAdd";

	}
	
	/**
	 * 工资项目计算公式<BR>
	 * 计算公式页面跳转AccWageItemCalAddPage
	 */
	@RequestMapping(value = "/hrp/acc/accwageitemcal/accWageItemCalSetMainPage", method = RequestMethod.GET)
	public String accWageItemCalSetMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", MyConfig
				.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode())
				.getCurYearMonthWage());
		return "hrp/acc/accwageitemcal/accWageItemCalSetMain";
	}
	
	/**
	*工资项目计算公式<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwageitemcal/accWageItemCalAddPage", method = RequestMethod.GET)
	public String accWageItemCalAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("wage_item_code", mapVo.get("wage_item_code"));
		
		mode.addAttribute("wage_item_name", mapVo.get("wage_item_name"));
		
		mode.addAttribute("acc_year", mapVo.get("year_month"));
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		mode.addAttribute("wage_name", mapVo.get("wage_name"));
		
		mode.addAttribute("wage_year_month", MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage());
		
		return "hrp/acc/accwageitemcal/accWageItemCalAdd";

		
	}
	
	/**
	*工资项目计算公式<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accwageitemcal/addAccWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWageItemCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			if(mapVo.get("acc_year") == null){
				
		        mapVo.put("acc_year", SessionManager.getAcctYear());
		        
			}
		
		String accWageItemCalJson = accWageItemCalService.addAccWageItemCal(mapVo);

		return JSONObject.parseObject(accWageItemCalJson);
		
	}
	/**
	*工资项目计算公式<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwageitemcal/queryAccWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageItemCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
        String accWageItemCal = accWageItemCalService.queryAccWageItemCal(getPage(mapVo));

		return JSONObject.parseObject(accWageItemCal);
		
	}
	
	/**
	*根据工资套查询工资项目<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwageitemcal/queryAccWageItem", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			if(mapVo.get("acc_year") == null){
				
		        mapVo.put("acc_year", SessionManager.getAcctYear());
		        
			}
		
        String accWageItemCal = accWageItemCalService.queryAccWageItem(getPage(mapVo));

		return JSONObject.parseObject(accWageItemCal);
		
	}
	/**
	*工资项目计算公式<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwageitemcal/deleteAccWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageItemCal(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("cal_id", id.split("@")[0]);//实际实体类变量
            
            mapVo.put("group_id", SessionManager.getGroupId());
            
    		mapVo.put("hos_id", SessionManager.getHosId());
            
    		mapVo.put("copy_code", SessionManager.getCopyCode());
    		
    		if(!"".equals(id.split("@")[1])&&id.split("@")[1] != null){
    			
    			mapVo.put("acc_year", id.split("@")[1]);
    			
    		}else{
    			
    			mapVo.put("acc_year", SessionManager.getAcctYear());
    			
    		}
    		
           
            listVo.add(mapVo);
        }
		
		String accWageItemCalJson = accWageItemCalService.deleteBatchAccWageItemCal(listVo);
	   
		return JSONObject.parseObject(accWageItemCalJson);
			
	}
	
	/**
	*工资项目计算公式<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwageitemcal/accWageItemCalUpdatePage", method = RequestMethod.GET)
	
	public String accWageItemCalUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
		AccWageCal accWageCal = accWageCalService.queryAccWageCalByCode(mapVo);
		
		if(accWageCal != null){
			
			mode.addAttribute("group_id", accWageCal.getGroup_id());
			
			mode.addAttribute("hos_id", accWageCal.getHos_id());
			
			mode.addAttribute("copy_code", accWageCal.getCopy_code());
			
			mode.addAttribute("wage_code", accWageCal.getWage_code());
			
			mode.addAttribute("wage_name", accWageCal.getWage_name());
			
			mode.addAttribute("kind_code", accWageCal.getKind_code());
			
			mode.addAttribute("item_code", accWageCal.getItem_code());
			
			mode.addAttribute("cal_id", accWageCal.getCal_id());
			
			mode.addAttribute("acc_year", accWageCal.getAcc_year());
			
			mode.addAttribute("cal_name", accWageCal.getCal_name());
			
			return "hrp/acc/accwageitemcal/accWageItemCalUpdate";
			
		}
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		mode.addAttribute("kind_code", mapVo.get("kind_code"));
		
		mode.addAttribute("item_code",mapVo.get("item_code"));
		
		return "hrp/acc/accwageitemcal/accWageItemCalUpdate";
		
	}
	/**
	*工资项目计算公式<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwageitemcal/updateAccWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageItemCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWageItemCalJson = accWageItemCalService.updateAccWageItemCal(mapVo);
		
		return JSONObject.parseObject(accWageItemCalJson);
	}
	/**
	*工资项目计算公式<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accwageitemcal/importAccWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWageItemCal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWageItemCalJson = accWageItemCalService.importAccWageItemCal(mapVo);
		
		return JSONObject.parseObject(accWageItemCalJson);
	}
	
	@RequestMapping(value = "/hrp/acc/accwageitemcal/queryAccWageItemCalList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageItemCalList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			if(mapVo.get("acc_year") == null){
				
		        mapVo.put("acc_year", SessionManager.getAcctYear());
		        
			}
		
        String accWageItemCal = accWageItemCalService.queryAccWageItemCalList(getPage(mapVo));

		return JSONObject.parseObject(accWageItemCal);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accwageitemcal/extendAccWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccWageItemCal(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String jsonResult = accWageItemCalService.addBatchAccWageItemCal(mapVo);
		return JSONObject.parseObject(jsonResult);
	}
	
	/**
	*工资项目计算公式<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwageitemcal/queryAccWageItemCalById", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageItemCalById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String result=accWageItemCalService.queryAccWageItemCalById(mapVo);
		
		return  JSONObject.parseObject("{\"rest\":\""+result+"\"}");
		
	}
	
	@RequestMapping(value = "/hrp/acc/accwageitemcal/queryAccWageItemTree", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageItemTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			if(mapVo.get("acc_year") == null){
				
		        mapVo.put("acc_year", SessionManager.getAcctYear());
		        
			}
		
        String accWageItemCal = accWageItemCalService.queryAccWageItemTree(getPage(mapVo));

		return JSONObject.parseObject(accWageItemCal);
		
	}
}

