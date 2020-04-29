
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
import com.chd.hrp.eqc.service.xymanage.AssEqUseConsumableItemMService;

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
public class AssEqUseConsumableItemMController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqUseConsumableItemMController.class);
	
	//引入Service服务
	@Resource(name = "assEqUseConsumableItemMService")
	private final AssEqUseConsumableItemMService assEqUseConsumableItemMService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseConsumableItemMMainPage", method = RequestMethod.GET)
	public String assEqUseConsumableItemMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequseconsumableItemdetail/assEqUseConsumableItemMMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseConsumableItemMAddPage", method = RequestMethod.GET)
	public String assEqUseConsumableItemAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequseconsumableItemdetail/assEqUseConsumableItemMAdd";

	}

	/**
	 * @Description 
	 * 添加数据 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqUseConsumableItemM", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqUseConsumableItemM(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("charge_item_id", ids[1]);
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
       
		String assEqUseConsumableItemMJson = assEqUseConsumableItemMService.save(listVo);

		return JSONObject.parseObject(assEqUseConsumableItemMJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseConsumableItemMUpdatePage", method = RequestMethod.GET)
	public String assEqUseConsumableItemMUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> assEqUseConsumableItemM = new HashMap<String, Object>();
    
		assEqUseConsumableItemM = assEqUseConsumableItemMService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqUseConsumableItemM.get("group_id"));
		mode.addAttribute("hos_id", assEqUseConsumableItemM.get("hos_id"));
		mode.addAttribute("copy_code", assEqUseConsumableItemM.get("copy_code"));
		mode.addAttribute("uci_userecorddr", assEqUseConsumableItemM.get("uci_userecorddr"));
		mode.addAttribute("analysis_code", assEqUseConsumableItemM.get("analysis_code"));
		mode.addAttribute("charge_item_id", assEqUseConsumableItemM.get("charge_item_id"));
		mode.addAttribute("consum_code", assEqUseConsumableItemM.get("consum_code"));
		mode.addAttribute("dept_code", assEqUseConsumableItemM.get("dept_code"));
		mode.addAttribute("unit_code", assEqUseConsumableItemM.get("unit_code"));
		mode.addAttribute("quantity", assEqUseConsumableItemM.get("quantity"));
		mode.addAttribute("price", assEqUseConsumableItemM.get("price"));
		mode.addAttribute("amount", assEqUseConsumableItemM.get("amount"));
		mode.addAttribute("quantity_type", assEqUseConsumableItemM.get("quantity_type"));
		mode.addAttribute("relative_qty", assEqUseConsumableItemM.get("relative_qty"));
		mode.addAttribute("add_date", assEqUseConsumableItemM.get("add_date"));
		mode.addAttribute("use_date", assEqUseConsumableItemM.get("use_date"));
		mode.addAttribute("cancel_date", assEqUseConsumableItemM.get("cancel_date"));
		mode.addAttribute("month_str", assEqUseConsumableItemM.get("month_str"));
		mode.addAttribute("sum_by_month_flag", assEqUseConsumableItemM.get("sum_by_month_flag"));
		mode.addAttribute("pay_price", assEqUseConsumableItemM.get("pay_price"));
		mode.addAttribute("pay_amount", assEqUseConsumableItemM.get("pay_amount"));
		
		return "hrp/eqc/xymanage/assequseconsumableItemdetail/assEqUseConsumableItemMUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqUseConsumableItemM", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqUseConsumableItemM(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseConsumableItemMJson = assEqUseConsumableItemMService.update(mapVo);
		
		return JSONObject.parseObject(assEqUseConsumableItemMJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqUseConsumableItemM", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqUseConsumableItemM(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String assEqUseConsumableItemMJson = assEqUseConsumableItemMService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqUseConsumableItemMJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 14设备使用消耗资源记录 ASS_EQUseConsumableItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqUseConsumableItemM", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqUseConsumableItemM(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseConsumableItemM = assEqUseConsumableItemMService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqUseConsumableItemM);
		
	}
	
    
}

