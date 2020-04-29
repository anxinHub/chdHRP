package com.chd.hrp.htcg.controller.collect;

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
import com.chd.hrp.htcg.service.collect.HtcgIntegratedQueryService;

@Controller
public class HtcgIntegratedQueryController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HtcgIntegratedQueryController.class);
	@Resource(name="htcgIntegratedQueryService")
	private final HtcgIntegratedQueryService htcgIntegratedQueryService=null;
    
    @RequestMapping(value="/hrp/htcg/collect/integratedQuery/htcgIntegratedQueryMainPage",method=RequestMethod.GET)
    public String htcgIntegratedQueryMainPage(Model model)throws Exception{
    	return "hrp/htcg/collect/integratedQuery/htcgIntegratedQueryMain";
    }
    @RequestMapping(value="/hrp/htcg/collect/integratedQuery/queryHtcgIntegratedQuery",method=RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> queryHtcgIntegratedQuery(@RequestParam Map<String, Object> mapVo,Model model)throws Exception{
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        String integratedQuery =htcgIntegratedQueryService.queryHtcgIntegratedQuery(getPage(mapVo));
  		return JSONObject.parseObject(integratedQuery);
    }
	
}
