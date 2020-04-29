/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.dict;
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
import com.chd.hrp.acc.service.commonbuilder.AccPayTypeService;
import com.chd.hrp.ass.service.dict.AssPaySourceService;
import com.chd.hrp.sys.service.SourceService;
/**
 * 
 * @Description:
 * 付款支付方式与资金来源对应关系
 * @Table:
 * ASS_PAY_SOURCE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssPaySourceController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssPaySourceController.class);
	
	//引入Service服务
	@Resource(name = "assPaySourceService")
	private final AssPaySourceService assPaySourceService = null;
	
	@Resource(name = "accPayTypeService")
	private final AccPayTypeService accPayTypeService = null;
	
	@Resource(name = "sourceService")
	private final SourceService sourceService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asspaysource/assPaySourceMainPage", method = RequestMethod.GET)
	public String assPaySourceMainPage(Model mode) throws Exception {

		return "hrp/ass/asspaysource/main";
	}
	
	@RequestMapping(value = "/hrp/ass/asspaysource/assPaySourceSettingPage", method = RequestMethod.GET)
	public String assPaySourceSettingPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("pay_code", mapVo.get("pay_code"));
		return "hrp/ass/asspaysource/setting";
	}


	/**
	 * @Description 
	 * 添加数据 付款支付方式与资金来源对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asspaysource/saveAssPaySourceMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssPaySourceMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
       
		String assPaySourceJson = assPaySourceService.add(mapVo);

		return JSONObject.parseObject(assPaySourceJson);
		
	}
	
	
	@RequestMapping(value = "/hrp/ass/asspaysource/queryAssPaySourceByPayType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPayType = accPayTypeService.queryAccPayType(getPage(mapVo));

		return JSONObject.parseObject(accPayType);
	}
	
	
	/**
	 * @Description 
	 * 删除数据 付款支付方式与资金来源对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asspaysource/deleteAssPaySourceMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPaySourceMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("pay_code", ids[3]);
				mapVo.put("source_id", ids[4]);
				
				listVo.add(mapVo);
	      
	    }
	    
		String assPaySourceJson = assPaySourceService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assPaySourceJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 付款支付方式与资金来源对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asspaysource/queryAssPaySourceBySource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPaySource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assPaySource = assPaySourceService.query(getPage(mapVo));

		return JSONObject.parseObject(assPaySource);
		
	}
	
	@RequestMapping(value = "/hrp/ass/asspaysource/queryAssPaySourceBySetting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPaySourceBySetting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assPaySource = sourceService.querySourceByAssPay(getPage(mapVo));

		return JSONObject.parseObject(assPaySource);
		
	}
}

