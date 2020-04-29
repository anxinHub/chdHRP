package com.chd.hrp.cost.controller;

import java.util.ArrayList;
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
import com.chd.base.jdbc.ConfigInit;
import com.chd.base.util.StringTool;
import com.chd.hrp.cost.service.CostIncomeHisViewLogService;
/**
* @Title. @Description.
* 
* @Author: linuxxu
* @email: linuxxu@s-chd.com
* @Version: 1.0
*/
@Controller
public class CostIncomeHisViewLogController extends BaseController{

	private static Logger logger = Logger.getLogger(CostIncomeHisViewLogController.class);
	
	@Resource(name = "costIncomeHisViewLogService")
	private final CostIncomeHisViewLogService costIncomeHisViewLogService = null;
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/costIncomeHisViewLogMainPage", method = RequestMethod.GET)
	public String accAutoHisViewLogMainPage(Model mode) throws Exception {

		return "hrp/cost/coshisview/costIncomeHisViewLogMain";
	}
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/queryCostHisViewLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostHisViewLog(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

		    String incomHisViewJson = costIncomeHisViewLogService.queryCostHisViewLog(getPage(mapVo));

		    return JSONObject.parseObject(incomHisViewJson);

	}
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/deleteCostHisViewLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostHisViewLog(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("his_log_code", ids[3]);
			mapVo.put("acc_year", ids[4]);
			mapVo.put("acc_month", ids[5]);
			listVo.add(mapVo);
		}
		
		String incomHisViewJson;
		
		try {
			incomHisViewJson = costIncomeHisViewLogService.deleteBatchDetailCostHisViewLog(listVo);
			
		} catch (Exception e) {
			incomHisViewJson = e.getMessage();
		}

		return JSONObject.parseObject(incomHisViewJson);
	}
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/costHisViewLogSettingPage", method = RequestMethod.GET)
	public String costHisViewLogSettingPage(Model mode) throws Exception {

		return "hrp/cost/coshisview/costHisViewLogSetting";
	}
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/queryCostIncomeHisViewSetting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostIncomeHisViewSetting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

		    String incomHisViewJson = costIncomeHisViewLogService.queryCostIncomeHisViewSetting(getPage(mapVo));

		    return JSONObject.parseObject(incomHisViewJson);

	}
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/updateOrAddCostIncomeHisViewSetting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOrAddCostIncomeHisViewSetting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		  
		String incomHisViewJson;
				
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			try {
				
				incomHisViewJson = costIncomeHisViewLogService.updateOrAddCostIncomeHisViewSetting(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				incomHisViewJson = e.getMessage();
			}
		     

		    return JSONObject.parseObject(incomHisViewJson);

	}
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/costGetDateCostIncomeHisViewPage", method = RequestMethod.GET)
	public String costGetDateCostIncomeHisViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

        Map<String, Object> costHis = costIncomeHisViewLogService.queryCostHisViewInitByCode(mapVo);
		
		mode.addAttribute("costHis", costHis);
		
		return "hrp/cost/coshisview/costGetDateCostIncomeHisView";
	}
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/getDateCostIncomeHisView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDateCostIncomeHisView(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String incomHisViewJson;
		try {
			incomHisViewJson = costIncomeHisViewLogService.getDateCostIncomeHisView(mapVo);
			
		} catch (Exception e) {
			incomHisViewJson = e.getMessage();
		}
		
		return JSONObject.parseObject(incomHisViewJson);
		
	}
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/costRunJobPage", method = RequestMethod.GET)
	public String costRunJobPage(Model mode) throws Exception {

		String etlPath=ConfigInit.getConfigProperties("etlPath");
		String jobPath=ConfigInit.getConfigProperties("jobPath");
		
		mode.addAttribute("etlPath", etlPath);
		mode.addAttribute("jobPath", jobPath);
		
		return "hrp/cost/coshisview/costRunJob";
	}
	
	@RequestMapping(value = "/hrp/cost/costincome/costhisview/costRunJob", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> costRunJob(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if(mapVo.get("etl_path")==null || mapVo.get("etl_path").equals("")){
			return JSONObject.parseObject("{\"error\":\"ETL目录路径为空！\"}");
		}
		
		if(mapVo.get("job_path")==null || mapVo.get("job_path").equals("")){
			return JSONObject.parseObject("{\"error\":\"job目录路径为空！\"}");
		}
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		try {
			String etlPath=mapVo.get("etl_path").toString();
			String jobPath=mapVo.get("job_path").toString();
			String viewCode=mapVo.get("view_code").toString();
			resMap = costIncomeHisViewLogService.costRunJob(etlPath,jobPath,viewCode);
			
		} catch (Exception e) {
			resMap.put("error", StringTool.string2Json(e.getMessage()));
		}
		
		return resMap;
	}
}
