
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
import com.chd.hrp.eqc.service.xymanage.AssEqEquipConsumableService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 10其他设备月资源消耗标准定义 ASS_EQEquipConsumable Controller实现类
*/
@Controller
public class AssEqEquipConsumableController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqEquipConsumableController.class);
	
	//引入Service服务
	@Resource(name = "assEqEquipConsumableService")
	private final AssEqEquipConsumableService assEqEquipConsumableService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqEquipConsumableMainPage", method = RequestMethod.GET)
	public String assEqEquipConsumableMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqequipconsumable/assEqEquipConsumableMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqEquipConsumableAddPage", method = RequestMethod.GET)
	public String assEqEquipConsumableAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqequipconsumable/assEqEquipConsumableAdd";

	}

	/**
	 * @Description 
	 * 添加数据 10其他设备月资源消耗标准定义 ASS_EQEquipConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqEquipConsumable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqEquipConsumable(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("analysis_code", ids[0])   ;
			mapVo.put("oresource_code", ids[1]);
			mapVo.put("price", ids[2]);
			mapVo.put("unit_code", ids[3]);
			mapVo.put("quantity", ids[4]);
			mapVo.put("amount", ids[5]);
			mapVo.put("remark", ids[6]);
			mapVo.put("rowNo", ids[7]);// 行号
			mapVo.put("flag", ids[8]);// 添加 修改标识
			
			
			listVo.add(mapVo);
	      
	    }
       
		String assEqEquipConsumableJson = assEqEquipConsumableService.save(listVo);

		return JSONObject.parseObject(assEqEquipConsumableJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 10其他设备月资源消耗标准定义 ASS_EQEquipConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqEquipConsumableUpdatePage", method = RequestMethod.GET)
	public String assEqEquipConsumableUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		Map<String,Object> assEqEquipConsumable = new HashMap<String, Object>();
    
		assEqEquipConsumable = assEqEquipConsumableService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqEquipConsumable.get("group_id"));
		mode.addAttribute("hos_id", assEqEquipConsumable.get("hos_id"));
		mode.addAttribute("copy_code", assEqEquipConsumable.get("copy_code"));
		mode.addAttribute("analysis_code", assEqEquipConsumable.get("analysis_code"));
		mode.addAttribute("oresource_code", assEqEquipConsumable.get("oresource_code"));
		mode.addAttribute("price", assEqEquipConsumable.get("price"));
		mode.addAttribute("unit_code", assEqEquipConsumable.get("unit_code"));
		mode.addAttribute("quantity", assEqEquipConsumable.get("quantity"));
		mode.addAttribute("amount", assEqEquipConsumable.get("amount"));
		mode.addAttribute("remark", assEqEquipConsumable.get("remark"));
		
		return "hrp/eqc/xymanage/asseqequipconsumable/assEqEquipConsumableUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 10其他设备月资源消耗标准定义 ASS_EQEquipConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqEquipConsumable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqEquipConsumable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		String assEqEquipConsumableJson = assEqEquipConsumableService.update(mapVo);
		
		return JSONObject.parseObject(assEqEquipConsumableJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 10其他设备月资源消耗标准定义 ASS_EQEquipConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqEquipConsumable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqEquipConsumable(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("analysis_code", ids[0])   ;
			mapVo.put("oresource_code", ids[1]);
				
			listVo.add(mapVo);
	      
	    }
	    
		String assEqEquipConsumableJson = assEqEquipConsumableService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqEquipConsumableJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 10其他设备月资源消耗标准定义 ASS_EQEquipConsumable
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqEquipConsumable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqEquipConsumable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		String assEqEquipConsumable = assEqEquipConsumableService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqEquipConsumable);
		
	}
	
    
}

