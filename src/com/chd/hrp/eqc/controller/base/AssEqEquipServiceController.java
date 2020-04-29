
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.eqc.service.base.AssEqEquipServiceService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 08设备服务对照表 ASS_EQEquipService Controller实现类
*/
@Controller
public class AssEqEquipServiceController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqEquipServiceController.class);
	
	//引入Service服务
	@Resource(name = "assEqEquipServiceService")
	private final AssEqEquipServiceService assEqEquipServiceService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqEquipServiceMainPage", method = RequestMethod.GET)
	public String assEqEquipServiceMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqequipservice/assEqEquipServiceMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqEquipServiceAddPage", method = RequestMethod.GET)
	public String assEqEquipServiceAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqequipservice/assEqEquipServiceAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08设备服务对照表 ASS_EQEquipService
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqEquipService", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqEquipService(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("analysis_code", ids[0])   ;
			mapVo.put("charge_kind_id", ids[1]);
			mapVo.put("minutes_per_times", ids[2]);
			mapVo.put("min_minutes", ids[3]);
			mapVo.put("max_minutes", ids[4]);
			mapVo.put("remark", ids[5]);
			mapVo.put("rowNo", ids[6]);// 行号
			mapVo.put("flag", ids[7]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 0);
			
			listVo.add(mapVo);
	      
	    }
	        
		String assEqEquipServiceJson = assEqEquipServiceService.save(listVo);

		return JSONObject.parseObject(assEqEquipServiceJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 08设备服务对照表 ASS_EQEquipService
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqEquipServiceUpdatePage", method = RequestMethod.GET)
	public String assEqEquipServiceUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqEquipService = new HashMap<String, Object>();
    
		assEqEquipService = assEqEquipServiceService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqEquipService.get("group_id"));
		mode.addAttribute("hos_id", assEqEquipService.get("hos_id"));
		mode.addAttribute("copy_code", assEqEquipService.get("copy_code"));
		mode.addAttribute("analysis_code", assEqEquipService.get("analysis_code"));
		mode.addAttribute("charge_kind_id", assEqEquipService.get("charge_kind_id"));
		mode.addAttribute("min_minutes", assEqEquipService.get("min_minutes"));
		mode.addAttribute("minutes_per_times", assEqEquipService.get("minutes_per_times"));
		mode.addAttribute("max_minutes", assEqEquipService.get("max_minutes"));
		mode.addAttribute("remark", assEqEquipService.get("remark"));
		mode.addAttribute("invalid_flag", assEqEquipService.get("invalid_flag"));
		
		return "hrp/eqc/base/asseqequipservice/assEqEquipServiceUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08设备服务对照表 ASS_EQEquipService
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqEquipService", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqEquipService(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqEquipServiceJson = assEqEquipServiceService.update(mapVo);
		
		return JSONObject.parseObject(assEqEquipServiceJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08设备服务对照表 ASS_EQEquipService
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqEquipService", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqEquipService(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("analysis_code", ids[0])   ;
				mapVo.put("charge_kind_id", ids[1]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String assEqEquipServiceJson = assEqEquipServiceService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assEqEquipServiceJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 08设备服务对照表 ASS_EQEquipService
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqEquipService", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqEquipService(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqEquipService = assEqEquipServiceService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqEquipService);
		
	}
	
    
}

