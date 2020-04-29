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
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageItemPayServiceImpl;
 
/**
* @Title. @Description.
* 工资数据
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageItemPayController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageItemPayController.class);
	
	
	@Resource(name = "accWageItemPayService")
	private final AccWageItemPayServiceImpl accWageItemPayService = null;
   
    
	/**
	*工资数据<BR>
	*维护页面跳转accWageItemPayAddPage
	*/
	
	@RequestMapping(value = "/hrp/acc/accwageitempay/accWageItemPayMainPage", method = RequestMethod.GET)
	public String accWageItemPayMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accwageitempay/accWageItemPayMain";

	}
	/**
	*工资数据<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwageitempay/accWageItemPayAddPage", method = RequestMethod.GET)
	public String accWageItemPayAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accwageitempay/accWageItemPayAdd";

	}
	
	@RequestMapping(value = "/hrp/acc/accwageitempay/accWageItemPaySchemeUpdate", method = RequestMethod.GET)
	public String accWageItemPaySchemeUpdate(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accwageitempay/accWageItemPaySchemeUpdate";

	}
	
	/**
	*工资数据<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accwageitempay/addAccWageItemPay", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWageItemPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
		String accWageItemPayJson = accWageItemPayService.addAccWageItemPay(mapVo);

		return JSONObject.parseObject(accWageItemPayJson);
		
	}
	/**
	*工资数据<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwageitempay/queryAccWageItemPay", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageItemPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("emp_code") != null){
			//用来转换大写
			mapVo.put("emp_code",mapVo.get("emp_code").toString().toUpperCase());
		}
		
        String accWageItemPay = accWageItemPayService.queryAccWageItemPay(getPage(mapVo));

		return JSONObject.parseObject(accWageItemPay);
		
	}
	/**
	*工资数据<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwageitempay/deleteAccWageItemPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageItemPay(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("t", id);//实际实体类变量
           
            listVo.add(mapVo);
        }
		
		String AccWageItemPayJson = accWageItemPayService.deleteBatchAccWageItemPay(listVo);
	   
		return JSONObject.parseObject(AccWageItemPayJson);
			
	}
	
	/**
	*工资数据<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwageitempay/accWageItemPayUpdatePage", method = RequestMethod.GET)
	
	public String accWageItemPayUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        //AccWagePay accWageItemPay = new AccWagePay();
		/*AccWageItemPay = AccWageItemPayService.queryAccWageItemPayByCode(mapVo);
		mode.addAttribute("para_code", AccWageItemPay.getPara_code());
		mode.addAttribute("para_name", AccWageItemPay.getPara_name());
		mode.addAttribute("group_id", AccWageItemPay.getGroup_id());
		mode.addAttribute("hos_id", AccWageItemPay.getHos_id());
		mode.addAttribute("copy_code", AccWageItemPay.getCopy_code());
		mode.addAttribute("mod_code", AccWageItemPay.getMod_code());
		mode.addAttribute("para_type", AccWageItemPay.getPara_type());
		mode.addAttribute("para_json", AccWageItemPay.getPara_json());
		mode.addAttribute("para_value", AccWageItemPay.getPara_value());
		mode.addAttribute("note", AccWageItemPay.getNote());
		mode.addAttribute("is_stop", AccWageItemPay.getIs_stop());*/
		return "hrp/acc/accwageitempay/accWageItemPayUpdate";
	}
	/**
	*工资数据<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwageitempay/updateAccWageItemPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageItemPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWageItemPayJson = accWageItemPayService.updateAccWageItemPay(mapVo);
		
		return JSONObject.parseObject(accWageItemPayJson);
	}
	/**
	*工资数据<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accwageitempay/importAccWageItemPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWageItemPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWageItemPayJson = accWageItemPayService.importAccWageItemPay(mapVo);
		
		return JSONObject.parseObject(accWageItemPayJson);
	}
	
	@RequestMapping(value = "/hrp/acc/accwageitempay/accBankNetWageMainPage", method = RequestMethod.GET)
	public String accBankNetWageMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage());
		return "hrp/acc/accwageitempay/accBankNetWageMain";

	}
	
	@RequestMapping(value = "/hrp/acc/accwageitempay/queryAccBankNetWageMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetWageMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWageItemPay = accWageItemPayService.collectAccBankNetWage(mapVo);

		return JSONObject.parseObject(accWageItemPay);
		
	}
	
}

