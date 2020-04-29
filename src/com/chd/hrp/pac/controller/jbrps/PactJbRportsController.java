package com.chd.hrp.pac.controller.jbrps;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.service.dict.AssNoDictService;
import com.chd.hrp.pac.service.jbrps.PactMainJBRtService;
/**
 * 
 * 
 * @author lh0225
 *固定资产降本报表
 */
@Controller
@RequestMapping(value = "/hrp/pac/jbrport/gdzcjbrp")
public class PactJbRportsController extends BaseController {
	@Resource(name = "pactMainJBRtService")
	private PactMainJBRtService pactMainJBRtService;
	
	@RequestMapping(value = "/pactJbRoportMainPage", method = RequestMethod.GET)
	public String pactJbRoportMainPage(Model mode)throws Exception {
		return "hrp/pac/jbrport/gdzcjbrp/pactjbrpActionMain";
	}
/**
 * 
 * @param mapVo
 *  @param mode
 * 查询功能
 * @author lh0225
 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAlteration", method = RequestMethod.POST)
	public Map<String, Object> queryPactAlteration(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		try{
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			if(mapVo.get("plan_year")!=null){
				String year = mapVo.get("plan_year").toString().substring(0, 4);
				mapVo.put("pac_year", year);
				
			}
			String query=pactMainJBRtService.queryPactAlteration(mapVo);
			return JSONObject.parseObject(query);
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
		
	}
	
	/**@author
	 * @param mapVo
	 * 
	 * 增加功能
	 * @param mode
	 * 
	 */
	
	@ResponseBody
	@RequestMapping(value = "/addJbRtMain" , method = RequestMethod.POST)
	
	public Map<String,Object  >addJbRtMain(@RequestParam Map<String,Object >mapVo,Model mode){
		
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String year = mapVo.get("plan_year").toString().substring(0, 4);
		mapVo.put("pac_year", year);
		try{
			//System.out.println(mapVo);
			String jbrpresult =pactMainJBRtService.add(mapVo);
			return JSONObject.parseObject(jbrpresult);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
			
		}
	}
	/**
	 * @author lh0225
	 * 修改
	 * 
	 */
	@ResponseBody
	@RequestMapping(value ="/updataJbRtMain" , method = RequestMethod.POST)
	public Map<String ,Object> updataJbRtMain(@RequestParam Map<String ,Object>mapVo,Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String year = mapVo.get("plan_year").toString().substring(0, 4);
		mapVo.put("pac_year", year);
		try{
			String jbrpresult = pactMainJBRtService.update(mapVo);
			return JSONObject.parseObject(jbrpresult);
			
		}catch (Exception e){
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
			
		}
		
	}
}
