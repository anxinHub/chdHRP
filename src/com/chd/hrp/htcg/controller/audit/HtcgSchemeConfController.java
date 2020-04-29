package com.chd.hrp.htcg.controller.audit;
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
import com.chd.hrp.htcg.service.audit.HtcgSchemeConfService;



@Controller
public class HtcgSchemeConfController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemeConfController.class);
	@Resource(name="htcgSchemeConfService")
	private final HtcgSchemeConfService htcgSchemeConfService = null;

    
    @RequestMapping(value="/hrp/htcg/audit/schemeConf/htcgSchemeConfMainPage",method=RequestMethod.GET)
    public String htcgSchemeConfMainPage(Model model) throws Exception{
    	return "hrp/htcg/audit/schemeConf/htcgSchemeConfMain";
    }
    
	@RequestMapping(value = "/hrp/htcg/audit/schemeConf/initHtcgSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgSchemeConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcgSchemeConfJson  = "";
		try {
			htcgSchemeConfJson = htcgSchemeConfService.initHtcgSchemeConf(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcgSchemeConfJson = e.getMessage();
		}
		return JSONObject.parseObject(htcgSchemeConfJson);

	}
	
    
    @RequestMapping(value="/hrp/htcg/audit/schemeConf/queryHtcgSchemeConf",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryHtcgSchemeConf(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
    	mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
 		String htcDrugDict = htcgSchemeConfService.queryHtcgSchemeConf(getPage(mapVo));
 		return JSONObject.parseObject(htcDrugDict);
    }
    
    @RequestMapping(value="/hrp/htcg/audit/schemeConf/deleteHtcgSchemeConf",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteHtcgSchemeConf(@RequestParam(value="ParamVo") String paramVo,Model model) throws Exception{
    	String htcgSchemeConfJson;
    	try {
   		
    		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
    		
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
			  	mapVo.put("acc_year", ids[3]);
			  	mapVo.put("acc_month", ids[4]);
			  	mapVo.put("scheme_code", ids[5]);
			  	mapVo.put("scheme_seq_no", ids[6]);
			  	mapVo.put("period_type_code", ids[7]);
				listVo.add(mapVo);
			}
			
			htcgSchemeConfJson = htcgSchemeConfService.deleteBatchHtcgSchemeConf(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			htcgSchemeConfJson = e.getMessage();
		}
    	
    	   return JSONObject.parseObject(htcgSchemeConfJson);
    }
    
    
    
   
    
}
