
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
import com.chd.hrp.eqc.service.base.AssEqDeviceMapService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 07设备对照表 ASS_EQDeviceMap Controller实现类
*/
@Controller
public class AssEqDeviceMapController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqDeviceMapController.class);
	
	//引入Service服务
	@Resource(name = "assEqDeviceMapService")
	private final AssEqDeviceMapService assEqDeviceMapService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqDeviceMapMainPage", method = RequestMethod.GET)
	public String assEqdevicemapMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqdevicemap/assEqDeviceMapMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqDeviceMapAddPage", method = RequestMethod.GET)
	public String assEqdevicemapAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqdevicemap/assEqDeviceMapAdd";

	}

	/**
	 * @Description 
	 * 添加数据 07设备对照表 ASS_EQDeviceMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqDeviceMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqDeviceMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("ass_card_no", ids[0])   ;
			mapVo.put("busi_data_source_code", ids[1]);
			mapVo.put("device_code", ids[2]);
			mapVo.put("device_desc", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("rowNo", ids[5]);// 行号
			mapVo.put("flag", ids[6]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 0);// 无效标志  不清楚干什么用  暂时赋0
			
			
			listVo.add(mapVo);
	      
	    }
	        
		String assEqdevicemapJson = assEqDeviceMapService.save(listVo);

		return JSONObject.parseObject(assEqdevicemapJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 07设备对照表 ASS_EQDeviceMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqDeviceMapUpdatePage", method = RequestMethod.GET)
	public String assEqdevicemapUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqdevicemap = new HashMap<String, Object>();
    
		assEqdevicemap = assEqDeviceMapService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqdevicemap.get("group_id"));
		mode.addAttribute("hos_id", assEqdevicemap.get("hos_id"));
		mode.addAttribute("copy_code", assEqdevicemap.get("copy_code"));
		mode.addAttribute("ass_card_no", assEqdevicemap.get("ass_card_no"));
		mode.addAttribute("busi_data_source_code", assEqdevicemap.get("busi_data_source_code"));
		mode.addAttribute("device_code", assEqdevicemap.get("device_code"));
		mode.addAttribute("device_desc", assEqdevicemap.get("device_desc"));
		mode.addAttribute("remark", assEqdevicemap.get("remark"));
		mode.addAttribute("invalid_flag", assEqdevicemap.get("invalid_flag"));
		
		return "hrp/eqc/base/asseqdevicemap/assEqDeviceMapUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 07设备对照表 ASS_EQDeviceMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqdevicemap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqdevicemap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqdevicemapJson = assEqDeviceMapService.update(mapVo);
		
		return JSONObject.parseObject(assEqdevicemapJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 07设备对照表 ASS_EQDeviceMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqDeviceMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqDeviceMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("ass_card_no", ids[0])   ;
				mapVo.put("busi_data_source_code", ids[1]);
				mapVo.put("device_code", ids[2]);
				
				listVo.add(mapVo);
	      
			}
	    
			String assEqdevicemapJson = assEqDeviceMapService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assEqdevicemapJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 07设备对照表 ASS_EQDeviceMap
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqDeviceMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqDeviceMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqdevicemap = assEqDeviceMapService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqdevicemap);
		
	}
	
    
}

