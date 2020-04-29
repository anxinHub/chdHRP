
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
import com.chd.base.util.StringTool;
import com.chd.hrp.eqc.service.xymanage.AssEqUseConsumableItemService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 14设备使用消耗资源记录 ASS_EQUseConsumableItem Controller实现类
*/
@Controller
public class AssEqUseConsumableItemController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqUseConsumableItemController.class);
	
	//引入Service服务
	@Resource(name = "assEqUseConsumableItemService")
	private final AssEqUseConsumableItemService assEqUseConsumableItemService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseConsumableItemMainPage", method = RequestMethod.GET)
	public String assEqUseConsumableItemMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequseconsumableitem/assEqUseConsumableItemMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseConsumableItemAddPage", method = RequestMethod.GET)
	public String assEqUseConsumableItemAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequseconsumableitem/assEqUseConsumableItemAdd";

	}

	/**
	 * @Description 
	 * 添加数据 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqUseConsumableItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqUseConsumableItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("uci_userecorddr", ids[14]);// 添加 修改标识
			mapVo.put("analysis_code", ids[0])   ;
			mapVo.put("charge_kind_id", ids[1]);
			mapVo.put("consum_code", ids[2]);
			mapVo.put("dept_code", ids[11]);
			mapVo.put("unit_code", ids[3]);			
			mapVo.put("quantity", ids[4]);
			mapVo.put("price", ids[5]);
			mapVo.put("amount", ids[6]);			
			mapVo.put("quantity_type", ids[7]);
			mapVo.put("relative_qty", ids[8]);
			mapVo.put("pay_price", ids[9]);			
			mapVo.put("pay_amount", ids[10]);
			
			if(StringTool.isNotBlank(ids[12])){
				mapVo.put("use_date", DateUtil.stringToDate(ids[12], "yyyy-MM-dd"));
			}else{
				mapVo.put("use_date", null);
			}			
			//mapVo.put("rowNo", ids[13]);// 行号
			
			
			//页面端没有项 字段处理
			mapVo.put("add_date", DateUtil.getCurrenDate("yyyy-MM-dd"));
			mapVo.put("cancel_date", null);
			mapVo.put("month_str", null);
			mapVo.put("sum_by_month_flag", 1);
			
			listVo.add(mapVo);
	      
	    }
       
		String assEqUseConsumableItemJson = assEqUseConsumableItemService.save(listVo);

		return JSONObject.parseObject(assEqUseConsumableItemJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseConsumableItemUpdatePage", method = RequestMethod.GET)
	public String assEqUseConsumableItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> assEqUseConsumableItem = new HashMap<String, Object>();
    
		assEqUseConsumableItem = assEqUseConsumableItemService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqUseConsumableItem.get("group_id"));
		mode.addAttribute("hos_id", assEqUseConsumableItem.get("hos_id"));
		mode.addAttribute("copy_code", assEqUseConsumableItem.get("copy_code"));
		mode.addAttribute("uci_userecorddr", assEqUseConsumableItem.get("uci_userecorddr"));
		mode.addAttribute("analysis_code", assEqUseConsumableItem.get("analysis_code"));
		mode.addAttribute("charge_kind_id", assEqUseConsumableItem.get("charge_kind_id"));
		mode.addAttribute("consum_code", assEqUseConsumableItem.get("consum_code"));
		mode.addAttribute("dept_code", assEqUseConsumableItem.get("dept_code"));
		mode.addAttribute("unit_code", assEqUseConsumableItem.get("unit_code"));
		mode.addAttribute("quantity", assEqUseConsumableItem.get("quantity"));
		mode.addAttribute("price", assEqUseConsumableItem.get("price"));
		mode.addAttribute("amount", assEqUseConsumableItem.get("amount"));
		mode.addAttribute("quantity_type", assEqUseConsumableItem.get("quantity_type"));
		mode.addAttribute("relative_qty", assEqUseConsumableItem.get("relative_qty"));
		mode.addAttribute("add_date	", assEqUseConsumableItem.get("add_date	"));
		mode.addAttribute("use_date", assEqUseConsumableItem.get("use_date"));
		mode.addAttribute("cancel_date", assEqUseConsumableItem.get("cancel_date"));
		mode.addAttribute("month_str", assEqUseConsumableItem.get("month_str"));
		mode.addAttribute("sum_by_month_flag", assEqUseConsumableItem.get("sum_by_month_flag"));
		mode.addAttribute("pay_price", assEqUseConsumableItem.get("pay_price"));
		mode.addAttribute("pay_amount", assEqUseConsumableItem.get("pay_amount"));
		
		return "hrp/eqc/xymanage/assequseconsumableitem/assEqUseConsumableItemUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqUseConsumableItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqUseConsumableItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseConsumableItemJson = assEqUseConsumableItemService.update(mapVo);
		
		return JSONObject.parseObject(assEqUseConsumableItemJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqUseConsumableItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqUseConsumableItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
	
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("uci_userecorddr", ids[0]);
			
			listVo.add(mapVo);
	      
	    }
	    
		String assEqUseConsumableItemJson = assEqUseConsumableItemService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqUseConsumableItemJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqUseConsumableItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqUseConsumableItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseConsumableItem = assEqUseConsumableItemService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqUseConsumableItem);
		
	}
	
    
}

