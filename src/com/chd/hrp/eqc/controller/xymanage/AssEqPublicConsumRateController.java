
/*
 *
 */
 package com.chd.hrp.eqc.controller.xymanage;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.eqc.service.xymanage.AssEqPublicConsumRateService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 18设备公共消耗资源占比 ASS_EQPublicConsumRate Controller实现类
*/
@Controller
public class AssEqPublicConsumRateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqPublicConsumRateController.class);
	
	//引入Service服务
	@Resource(name = "assEqPublicConsumRateService")
	private final AssEqPublicConsumRateService assEqPublicConsumRateService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumRateMainPage", method = RequestMethod.GET)
	public String assEqPublicConsumRateMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqpublicconsumrate/assEqPublicConsumRateMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumRateAddPage", method = RequestMethod.GET)
	public String assEqPublicConsumRateAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqpublicconsumrate/assEqPublicConsumRateAdd";

	}

	/**
	 * @Description 
	 * 添加数据 18设备公共消耗资源占比 ASS_EQPublicConsumRate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqPublicConsumRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqPublicConsumRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("dept_code", ids[0])   ;
			mapVo.put("analysis_code", ids[1])   ;
			//mapVo.put("eo_eq_group", ids[2])   ;
			mapVo.put("consum_code", ids[2]);
			mapVo.put("rate", ids[3]);
			mapVo.put("from_date", DateUtil.stringToDate(ids[4], "yyyy-MM-dd"));
			mapVo.put("to_date", DateUtil.stringToDate(ids[5], "yyyy-MM-dd"));
			mapVo.put("remark", ids[6]);
			mapVo.put("rowNo", ids[7]);// 行号
			mapVo.put("flag", ids[8]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 1);
			
			listVo.add(mapVo);
	      
			     
	    }
	        
		String assEqdevicemapJson = assEqPublicConsumRateService.save(listVo);

		return JSONObject.parseObject(assEqdevicemapJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 18设备公共消耗资源占比 ASS_EQPublicConsumRate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumRateUpdatePage", method = RequestMethod.GET)
	public String assEqPublicConsumRateUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqPublicConsumRate = new HashMap<String, Object>();
    
		assEqPublicConsumRate = assEqPublicConsumRateService.queryByCode(mapVo);
		    
		mode.addAttribute("group_id", assEqPublicConsumRate.get("group_id"));
		mode.addAttribute("hos_id", assEqPublicConsumRate.get("hos_id"));
		mode.addAttribute("copy_code", assEqPublicConsumRate.get("copy_code"));
		mode.addAttribute("dept_code", assEqPublicConsumRate.get("dept_code"));
		mode.addAttribute("analysis_code", assEqPublicConsumRate.get("analysis_code"));
		//mode.addAttribute("eo_eq_group", assEqPublicConsumRate.get("eo_eq_group"));
		mode.addAttribute("consum_code", assEqPublicConsumRate.get("consum_code"));
		mode.addAttribute("rate", assEqPublicConsumRate.get("rate"));
		mode.addAttribute("from_date", assEqPublicConsumRate.get("from_date"));
		mode.addAttribute("to_date", assEqPublicConsumRate.get("to_date"));
		mode.addAttribute("invalid_flag", assEqPublicConsumRate.get("invalid_flag"));
		mode.addAttribute("remark", assEqPublicConsumRate.get("remark"));
		
		return "hrp/eqc/xymanage/asseqpublicconsumrate/assEqPublicConsumRateUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 18设备公共消耗资源占比 ASS_EQPublicConsumRate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqPublicConsumRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqPublicConsumRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqPublicConsumRateJson = assEqPublicConsumRateService.update(mapVo);
		
		return JSONObject.parseObject(assEqPublicConsumRateJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 18设备公共消耗资源占比 ASS_EQPublicConsumRate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqPublicConsumRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqPublicConsumRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("dept_code", ids[0])   ;
			mapVo.put("analysis_code", ids[1])   ;
			//mapVo.put("eo_eq_group", ids[2])   ;
			mapVo.put("consum_code", ids[2]);
			
			listVo.add(mapVo);
      
		}
	    
		String assEqPublicConsumRateJson = assEqPublicConsumRateService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqPublicConsumRateJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 18设备公共消耗资源占比 ASS_EQPublicConsumRate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqPublicConsumRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqPublicConsumRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqPublicConsumRate = assEqPublicConsumRateService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqPublicConsumRate);
		
	}
	
    
}

