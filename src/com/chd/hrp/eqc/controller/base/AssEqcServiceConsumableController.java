
/*
 *
 */
 package com.chd.hrp.eqc.controller.base;
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
import com.chd.hrp.eqc.service.base.AssEqcServiceConsumableService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 06服务项目消耗表 ASS_EQCServiceConsumable Controller实现类
*/
@Controller
public class AssEqcServiceConsumableController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqcServiceConsumableController.class);
	
	//引入Service服务
	@Resource(name = "assEqcServiceConsumableService")
	private final AssEqcServiceConsumableService assEqcServiceConsumableService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServiceConsumableMainPage", method = RequestMethod.GET)
	public String assEqcServiceConsumableMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcserviceconsumable/assEqcServiceConsumableMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServiceConsumableAddPage", method = RequestMethod.GET)
	public String assEqcServiceConsumableAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcserviceconsumable/assEqcServiceConsumableAdd";

	}

	/**
	 * @Description 
	 * 添加数据 06服务项目消耗表 ASS_EQCServiceConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqcServiceConsumable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqcServiceConsumable(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("charge_kind_id", ids[0])   ;
			mapVo.put("consum_code", ids[1])   ;
			mapVo.put("quantity", ids[2]);
			mapVo.put("quantity_type", ids[3]);
			mapVo.put("month_stat_flag", ids[4]);
			mapVo.put("cycle_num", ids[5]);
			mapVo.put("cycle_nuit", ids[6]);
			mapVo.put("type_name", ids[7]);
			mapVo.put("rowNo", ids[8]);// 行号
			mapVo.put("flag", ids[9]);// 添加 修改标识
			
			listVo.add(mapVo);
	      
	    }
       
		String assEqcServiceConsumableJson = assEqcServiceConsumableService.save(listVo);

		return JSONObject.parseObject(assEqcServiceConsumableJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 06服务项目消耗表 ASS_EQCServiceConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServiceConsumableUpdatePage", method = RequestMethod.GET)
	public String assEqcServiceConsumableUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqcServiceConsumable = new HashMap<String, Object>();
    
		assEqcServiceConsumable = assEqcServiceConsumableService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqcServiceConsumable.get("group_id"));
		mode.addAttribute("hos_id", assEqcServiceConsumable.get("hos_id"));
		mode.addAttribute("copy_code", assEqcServiceConsumable.get("copy_code"));
		mode.addAttribute("charge_kind_id", assEqcServiceConsumable.get("charge_kind_id"));
		mode.addAttribute("consum_code", assEqcServiceConsumable.get("consum_code"));
		mode.addAttribute("quantity", assEqcServiceConsumable.get("quantity"));
		mode.addAttribute("quantity_type", assEqcServiceConsumable.get("quantity_type"));
		mode.addAttribute("month_stat_flag", assEqcServiceConsumable.get("month_stat_flag"));
		mode.addAttribute("cycle_num", assEqcServiceConsumable.get("cycle_num"));
		mode.addAttribute("cycle_nuit", assEqcServiceConsumable.get("cycle_nuit"));
		mode.addAttribute("type_name", assEqcServiceConsumable.get("type_name"));
		
		return "hrp/eqc/base/asseqcserviceconsumable/assEqcServiceConsumableUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 06服务项目消耗表 ASS_EQCServiceConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqcServiceConsumable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqcServiceConsumable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqcServiceConsumableJson = assEqcServiceConsumableService.update(mapVo);
		
		return JSONObject.parseObject(assEqcServiceConsumableJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 06服务项目消耗表 ASS_EQCServiceConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqcServiceConsumable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqcServiceConsumable(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("charge_kind_id", ids[0])   ;
				mapVo.put("consum_code", ids[1]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String assEqcServiceConsumableJson = assEqcServiceConsumableService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assEqcServiceConsumableJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 06服务项目消耗表 ASS_EQCServiceConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqcServiceConsumable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqcServiceConsumable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqcServiceConsumable = assEqcServiceConsumableService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqcServiceConsumable);
		
	}
	
    
}

