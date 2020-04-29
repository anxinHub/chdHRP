/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller.autovouch;

import java.text.SimpleDateFormat;
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
import com.chd.base.jdbc.ConfigInit;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.autovouch.AccAutoHisLogMainService;

/**
 * @Title. @Description. 出纳账
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccAutoHisLogMainController extends BaseController {
	private static Logger logger = Logger.getLogger(AccAutoHisLogMainController.class);

	@Resource(name = "accAutoHisLogMainService")
	private final AccAutoHisLogMainService accAutoHisLogMainService = null;
	
	@RequestMapping(value = "/hrp/acc/autovouch/hisview/accAutoHisViewLogMainPage", method = RequestMethod.GET)
	public String accAutoHisViewLogMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/hisview/accAutoHisViewLogMain";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/hisview/queryAccHisViewLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccHisViewLog(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

		    String str = accAutoHisLogMainService.queryAccHisViewLog(getPage(mapVo));

		    return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/hisview/accAutoHisViewSettingPage", method = RequestMethod.GET)
	public String accAutoHisViewSettingPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/hisview/accAutoHisViewSetting";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/hisview/queryAutoHisViewSetting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAutoHisViewSetting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

		    String str = accAutoHisLogMainService.queryAutoHisViewSetting(getPage(mapVo));

		    return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/hisview/updateDatePage", method = RequestMethod.GET)
	public String updateDatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("his_log_code",mapVo.get("his_log_code") );
		
		Map<String, Object> accHis = accAutoHisLogMainService.queryByCode(mapVo);
		
		mode.addAttribute("accHis", accHis);
		
		return "hrp/acc/autovouch/hisview/updateDate";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/hisview/deleteAccHisViewLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccHisViewLog(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("his_log_code", ids[3]);
			mapVo.put("his_log_date", ids[4]);
			listVo.add(mapVo);
		}
		
		String accJson;
		try {
			accJson = accAutoHisLogMainService.deleteBatch(listVo);
		} catch (Exception e) {
			accJson = e.getMessage();
		}

		return JSONObject.parseObject(accJson);
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/hisview/updateAccAutoHisView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccAutoHisView(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        
        mapVo.put("his_log_date", sdf.format(d));
		
		String accJson;
		try {
			accJson = accAutoHisLogMainService.update(mapVo);
		} catch (Exception e) {
			accJson = e.getMessage();
		}
		
		return JSONObject.parseObject(accJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/hisview/updateDateAccAutoHisView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDateAccAutoHisView(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accJson;
		try {
			accJson = accAutoHisLogMainService.updateDate(mapVo);
		} catch (Exception e) {
			accJson = e.getMessage();
		}
		
		return JSONObject.parseObject(accJson);
		
	}
	
	
	@RequestMapping(value = "/hrp/acc/autovouch/hisview/runJobPage", method = RequestMethod.GET)
	public String runJobPage(Model mode) throws Exception {

		String etlPath=ConfigInit.getConfigProperties("etlPath");
		String jobPath=ConfigInit.getConfigProperties("jobPath");
		
		mode.addAttribute("etlPath", etlPath);
		mode.addAttribute("jobPath", jobPath);
		
		return "hrp/acc/autovouch/hisview/runJob";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/hisview/runJob", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> runJob(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			resMap = accAutoHisLogMainService.runJob(etlPath,jobPath,viewCode);
			
		} catch (Exception e) {
			resMap.put("error", StringTool.string2Json(e.getMessage()));
		}
		
		return resMap;
	}

}
