
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
import com.chd.hrp.eqc.service.base.AssEqConsumableMapService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
*  09服务消耗资源对照 ASS_EQConsumableMap Controller实现类
*/
@Controller
public class AssEqConsumableMapController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqConsumableMapController.class);
	
	//引入Service服务
	@Resource(name = "assEqConsumableMapService")
	private final AssEqConsumableMapService assEqConsumableMapService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqConsumableMapMainPage", method = RequestMethod.GET)
	public String assEqConsumableMapMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqconsumablemap/assEqConsumableMapMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqConsumableMapAddPage", method = RequestMethod.GET)
	public String assEqConsumableMapAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqconsumablemap/assEqConsumableMapAdd";

	}

	/**
	 * @Description 
	 * 添加数据 09服务消耗资源对照 ASS_EQConsumableMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqConsumableMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssEqConsumableMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("consum_code", ids[0])   ;
			mapVo.put("busi_data_source_code", ids[1]);
			mapVo.put("resources_code", ids[2]);
			mapVo.put("resources_desc", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("rowNo", ids[5]);// 行号
			mapVo.put("flag", ids[6]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 0);// 无效标志  不清楚干什么用  暂时赋0
			
			
			listVo.add(mapVo);
	      
	    }
	        
		String assEqConsumableMapJson = assEqConsumableMapService.save(listVo);

		return JSONObject.parseObject(assEqConsumableMapJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 09服务消耗资源对照 ASS_EQConsumableMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/AssEqConsumableMapUpdatePage", method = RequestMethod.GET)
	public String AssEqConsumableMapUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqConsumableMap = new HashMap<String, Object>();
    
		assEqConsumableMap = assEqConsumableMapService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqConsumableMap.get("group_id"));
		mode.addAttribute("hos_id", assEqConsumableMap.get("hos_id"));
		mode.addAttribute("copy_code", assEqConsumableMap.get("copy_code"));
		mode.addAttribute("consum_code", assEqConsumableMap.get("consum_code"));
		mode.addAttribute("busi_data_source_code", assEqConsumableMap.get("busi_data_source_code"));
		mode.addAttribute("resources_code", assEqConsumableMap.get("resources_code"));
		mode.addAttribute("resources_desc", assEqConsumableMap.get("resources_desc"));
		mode.addAttribute("remark", assEqConsumableMap.get("remark"));
		mode.addAttribute("invalid_flag", assEqConsumableMap.get("invalid_flag"));
		
		return "hrp/eqc/base/asseqconsumablemap/AssEqConsumableMapUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 09服务消耗资源对照 ASS_EQConsumableMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqConsumableMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqConsumableMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqConsumableMapJson = assEqConsumableMapService.update(mapVo);
		
		return JSONObject.parseObject(assEqConsumableMapJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 09服务消耗资源对照 ASS_EQConsumableMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqConsumableMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqConsumableMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("consum_code", ids[0])   ;
				mapVo.put("busi_data_source_code", ids[1])   ;
				mapVo.put("resources_code", ids[2]);
				
				listVo.add(mapVo);
	      
			}
	    
			String assEqConsumableMapJson = assEqConsumableMapService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assEqConsumableMapJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 09服务消耗资源对照 ASS_EQConsumableMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqConsumableMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqConsumableMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String AssEqConsumableMap = assEqConsumableMapService.query(getPage(mapVo));

		return JSONObject.parseObject(AssEqConsumableMap);
		
	}
	
    
}

