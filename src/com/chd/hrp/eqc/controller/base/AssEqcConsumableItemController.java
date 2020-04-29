
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
import com.chd.hrp.eqc.service.base.AssEqcConsumableItemService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 05服务消耗资源项定义表 ASS_EQCConsumableItem Controller实现类
*/
@Controller
public class AssEqcConsumableItemController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqcConsumableItemController.class);
	
	//引入Service服务
	@Resource(name = "assEqcConsumableItemService")
	private final AssEqcConsumableItemService assEqcConsumableItemService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcConsumableItemMainPage", method = RequestMethod.GET)
	public String assEqcConsumableItemMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcconsumableitem/assEqcConsumableItemMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcConsumableItemAddPage", method = RequestMethod.GET)
	public String assEqcConsumableItemAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcconsumableitem/assEqcConsumableItemAdd";

	}

	/**
	 * @Description 
	 * 添加数据 05服务消耗资源项定义表 ASS_EQCConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqcConsumableItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqcConsumableItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("consum_code", ids[0])   ;
			mapVo.put("consum_desc", ids[1]);
			mapVo.put("price", ids[2]);
			mapVo.put("unit_code", ids[3]);
			mapVo.put("is_seperate_fee", ids[4]);
			mapVo.put("pay_price", ids[5]);
			mapVo.put("rowNo", ids[6]);// 行号
			mapVo.put("flag", ids[7]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 0);
			mapVo.put("consum_type", "01"); //01：普通服务消耗资源，02服务公共消耗资源
			
			listVo.add(mapVo);
	      
	    }
       
		String assEqcConsumableItemJson = assEqcConsumableItemService.save(listVo);

		return JSONObject.parseObject(assEqcConsumableItemJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 05服务消耗资源项定义表 ASS_EQCConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcConsumableItemUpdatePage", method = RequestMethod.GET)
	public String assEqcConsumableItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqcConsumableItem = new HashMap<String, Object>();
    
		assEqcConsumableItem = assEqcConsumableItemService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqcConsumableItem.get("group_id"));
		mode.addAttribute("hos_id", assEqcConsumableItem.get("hos_id"));
		mode.addAttribute("copy_code", assEqcConsumableItem.get("copy_code"));
		mode.addAttribute("consum_code", assEqcConsumableItem.get("consum_code"));
		mode.addAttribute("consum_desc", assEqcConsumableItem.get("consum_desc"));
		mode.addAttribute("price", assEqcConsumableItem.get("price"));
		mode.addAttribute("unit_code", assEqcConsumableItem.get("unit_code"));
		mode.addAttribute("invalid_flag", assEqcConsumableItem.get("invalid_flag"));
		mode.addAttribute("is_seperate_fee", assEqcConsumableItem.get("is_seperate_fee"));
		mode.addAttribute("pay_price", assEqcConsumableItem.get("pay_price"));
		
		return "hrp/eqc/base/asseqcconsumableitem/assEqcConsumableItemUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 05服务消耗资源项定义表 ASS_EQCConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqcconsumableitem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqcconsumableitem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqcConsumableItemJson = assEqcConsumableItemService.update(mapVo);
		
		return JSONObject.parseObject(assEqcConsumableItemJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 05服务消耗资源项定义表 ASS_EQCConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqcConsumableItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqcConsumableItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("consum_code", ids[0]);
				
			listVo.add(mapVo);
	      
	    }
	    
		String assEqcConsumableItemJson = assEqcConsumableItemService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqcConsumableItemJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 05服务消耗资源项定义表 ASS_EQCConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqcConsumableItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqcConsumableItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqcConsumableItem = assEqcConsumableItemService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqcConsumableItem);
		
	}
	
    
}

