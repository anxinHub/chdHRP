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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccWageVouch;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageVouchServiceImpl;
 
/**  
* @Title. @Description.
* 工资转账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageVouchController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageVouchController.class);
	
	
	@Resource(name = "accWageVouchService")
	private final AccWageVouchServiceImpl accWageVouchService = null;
   
	/**
	 * 【工资管理-工资转账-工资转账】：主页面
	 */
	@RequestMapping(value = "hrp/acc/accwagevouch/accWageVouchMainPage", method = RequestMethod.GET)
	public String accWageVouchMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accwagevouch/accWageVouchMain";
	}
	
	/**
	*工资转账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "hrp/acc/accwagevouch/accWageVouchBatchAddPage", method = RequestMethod.GET)
	public String accWageVouchBatchAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accwagevouch/accWageVouchBatchAdd";

	}
	
	/**
	*工资转账<BR>
	*/
	
	@RequestMapping(value = "hrp/acc/accwagevouch/accWageVouchSetPage", method = RequestMethod.GET)
	public String accWageVouchSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/acc/accwagevouch/accWageVouchSet";

	}
	
	/**
	*工资转账设置公式<BR>
	*/
	
	@RequestMapping(value = "hrp/acc/accwagevouch/accWageVouchCalPage", method = RequestMethod.GET)
	public String accWageVouchCalPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		mode.addAttribute("cal_name", mapVo.get("cal_name"));
		mode.addAttribute("wage_year_month", MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage());
		return "hrp/acc/accwagevouch/accWageVouchCal";

	}
	
	/**
	*工资转账<BR>
	*批量录入页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "hrp/acc/accwagevouch/accWageVouchAddPage", method = RequestMethod.GET)
	public String accWageVouchAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accwagevouch/accWageVouchAdd";

	}
	
	/**
	*工资转账<BR>
	*保存
	*/
	@RequestMapping(value = "hrp/acc/accwagevouch/addAccWageVouch", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccWageVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			
			String accWageVouchJson = "";
			
			try {
				
				accWageVouchJson = accWageVouchService.addAccWageVouch(mapVo);
				
			} catch (Exception e) {
				
				accWageVouchJson = e.getMessage();
				
			}
			
		return JSONObject.parseObject(accWageVouchJson);
		
	}
	
	@RequestMapping(value = "hrp/acc/accwagevouch/addBatchAccWageVouch", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addBatchAccWageVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
		String accWageVouchJson = accWageVouchService.addAccWageVouch(mapVo);

		return JSONObject.parseObject(accWageVouchJson);
		
	}
	
	/**
	 * 【工资管理-工资转账-工资转账】：主页面-查询
	 */
	@RequestMapping(value = "hrp/acc/accwagevouch/queryAccWageVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String accWageVouch = accWageVouchService.queryAccWageVouch(getPage(mapVo));
		return JSONObject.parseObject(accWageVouch);
	}
	
	/**
	*工资转账<BR>
	*查询转账科目
	*/
	@RequestMapping(value = "hrp/acc/accwagevouch/queryAccWageVouchSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageVouchSubj(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		String accWageVouch = accWageVouchService.queryAccWageVouchSubj(getPage(mapVo));
		return JSONObject.parseObject(accWageVouch);
	}
	
	/**
	*工资转账<BR>
	*删除
	*/
	@RequestMapping(value = "hrp/acc/accwagevouch/deleteAccWageVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageVouch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String [] res = id.split("@");
			
            mapVo.put("scheme_code", res[0]);//实际实体类变量
            
            mapVo.put("group_id", res[1]);
            
            mapVo.put("hos_id", res[2]);
            
            mapVo.put("copy_code", res[3]);
            
            mapVo.put("acc_year", res[4]);
            
            listVo.add(mapVo);
        }
		
		String AccWageVouchJson ="";
		
		try {
			
			 AccWageVouchJson = accWageVouchService.deleteBatchAccWageVouch(listVo);
			
		} catch (Exception e) {
			
			 AccWageVouchJson =e.getMessage();
		}
	   
		return JSONObject.parseObject(AccWageVouchJson);
			
	}
	
	/**
	 * 【工资管理-工资转账-工资转账】：主页面-工资转账修改页面
	 */
	@RequestMapping(value = "hrp/acc/accwagevouch/accWageVouchUpdatePage", method = RequestMethod.GET)
	public String accWageVouchUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		AccWageVouch accWageVouch = new AccWageVouch();
		accWageVouch = accWageVouchService.queryAccWageVouchBySchemeCode(mapVo);

		mode.addAttribute("group_id", accWageVouch.getGroup_id());
		mode.addAttribute("hos_id", accWageVouch.getHos_id());
		mode.addAttribute("copy_code", accWageVouch.getCopy_code());
		mode.addAttribute("acc_year", accWageVouch.getAcc_year());
		mode.addAttribute("scheme_code", accWageVouch.getScheme_code());
		mode.addAttribute("scheme_name", accWageVouch.getScheme_name());
		mode.addAttribute("summary", accWageVouch.getSummary());
		mode.addAttribute("vouch_type_code", accWageVouch.getVouch_type_code());
		mode.addAttribute("vouch_type_name", accWageVouch.getVouch_type_name());
		mode.addAttribute("wage_code", accWageVouch.getWage_code());
		mode.addAttribute("wage_name", accWageVouch.getWage_name());
		return "hrp/acc/accwagevouch/accWageVouchUpdate";
	}
	
	/**
	*工资转账<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "hrp/acc/accwagevouch/updateAccWageVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageVouch(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		String accWageVouchJson = "";
		try {
			accWageVouchJson = accWageVouchService.updateAccWageVouch(mapVo);
		} catch (Exception e) {
			accWageVouchJson = e.getMessage();
		}
		return JSONObject.parseObject(accWageVouchJson);
	}
	
	@RequestMapping(value = "hrp/acc/accwagevouch/updateBatchAccWageVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchAccWageVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
		String accWageVouchJson = accWageVouchService.updateAccWageVouch(mapVo);
		
		return JSONObject.parseObject(accWageVouchJson);
	}
	/**
	*工资转账<BR>
	*导入
	*/
	
	@RequestMapping(value = "hrp/acc/accwagevouch/importAccWageVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWageVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWageVouchJson = accWageVouchService.importAccWageVouch(mapVo);
		
		return JSONObject.parseObject(accWageVouchJson);
	}
	
	/**
	 * 工资转账<BR>
	 */
	@RequestMapping(value = "hrp/acc/accwagevouch/addTransferAccWageVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTransferAccWageVouch(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] res = id.split("@");
			mapVo.put("scheme_code", res[0]);// 实际实体类变量
			mapVo.put("group_id", res[1]);
			mapVo.put("hos_id", res[2]);
			mapVo.put("copy_code", res[3]);
			mapVo.put("acc_year", res[4]);
			mapVo.put("wage_code", res[5]);
			mapVo.put("acc_month", res[6]);
			
			listVo.add(mapVo);
		}
		
		String json = null;
		try {
			json = accWageVouchService.addTransferAccWageVouch(listVo);
		} catch (Exception e) {
			json = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(json);
	}
	
	@RequestMapping(value = "hrp/acc/accwagevouch/extendAccWageVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccWageVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
            mapVo.put("group_id", SessionManager.getGroupId());
            
            mapVo.put("hos_id", SessionManager.getHosId());
            
            mapVo.put("copy_code", SessionManager.getCopyCode());
            
            mapVo.put("acc_year", Integer.parseInt(mapVo.get("acc_year").toString()));
            
		String AccWageVouchJson = accWageVouchService.extendAccWageVouch(mapVo);
	   
		return JSONObject.parseObject(AccWageVouchJson);
			
	}
		
}

