/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.commonbuilder;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccCashItem;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCashItemServiceImpl;

/**
* @Title. @Description.
* 现金流量项目
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccCashItemController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCashItemController.class);
	
	
	@Resource(name = "accCashItemService")
	private final AccCashItemServiceImpl accCashItemService = null;
   
    
	/**
	*现金流量项目<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashitem/accCashItemMainPage", method = RequestMethod.GET)
	public String accCashItemMainPage(Model mode) throws Exception {

		return "hrp/acc/acccashitem/accCashItemMain";

	}
	/**
	*现金流量项目<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acccashitem/accCashItemAddPage", method = RequestMethod.GET)
	public String accCashItemAddPage(Model mode,String cash_type_id) throws Exception {
		mode.addAttribute("cash_type_id", cash_type_id);
		return "hrp/acc/acccashitem/accCashItemAdd";

	}
	
	/**
	*现金流量项目<BR>
	*继承数据页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashitem/accCashItemExtendPage", method = RequestMethod.GET)
	public String accCashItemExtendPage(Model mode) throws Exception {

		return "hrp/acc/acccashitem/accCashItemExtend";

	}
	
	/**
	*现金流量项目<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/acccashitem/addAccCashItem", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccCashItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cash_item_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cash_item_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String accCashItemJson = accCashItemService.addAccCashItem(mapVo);

		return JSONObject.parseObject(accCashItemJson);
		
	}
	/**
	*现金流量项目<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acccashitem/queryAccCashItem", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccCashItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if ("undefined".equals(mapVo.get("is_stop"))) {
			
			mapVo.put("is_stop", "");
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());
		String accCashItem = accCashItemService.queryAccCashItem(getPage(mapVo));

		return JSONObject.parseObject(accCashItem);
		
	}
	/**
	*现金流量项目<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acccashitem/deleteAccCashItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCashItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("cash_item_id", id.split("@")[0]);//实际实体类变量
			mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
			mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
            mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String accCashItemJson = accCashItemService.deleteBatchAccCashItem(listVo);
	   return JSONObject.parseObject(accCashItemJson);
			
	}
	
	/**
	*现金流量项目<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccashitem/accCashItemUpdatePage", method = RequestMethod.GET)
	
	public String accCashItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccCashItem accCashItem = new AccCashItem();
		accCashItem = accCashItemService.queryAccCashItemByCode(mapVo);
		mode.addAttribute("cash_item_id", accCashItem.getCash_item_id());
		mode.addAttribute("cash_type_id", accCashItem.getCash_type_id());
		mode.addAttribute("group_id", accCashItem.getGroup_id());
		mode.addAttribute("hos_id", accCashItem.getHos_id());
		mode.addAttribute("copy_code", accCashItem.getCopy_code());
		mode.addAttribute("cash_dire", accCashItem.getCash_dire());
		mode.addAttribute("cash_item_code", accCashItem.getCash_item_code());
		mode.addAttribute("cash_item_name", accCashItem.getCash_item_name());
		mode.addAttribute("spell_code", accCashItem.getSpell_code());
		mode.addAttribute("wbx_code", accCashItem.getWbx_code());
		mode.addAttribute("is_stop", accCashItem.getIs_stop());
		return "hrp/acc/acccashitem/accCashItemUpdate";
	}
	/**
	*现金流量项目<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/acccashitem/updateAccCashItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCashItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
	   
	   
	   
	   
	   
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cash_item_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cash_item_name").toString()));
	   
		String accCashItemJson = accCashItemService.updateAccCashItem(mapVo);
		
		return JSONObject.parseObject(accCashItemJson);
	}
	/**
	*现金流量项目<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashitem/importAccCashItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccCashItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accCashItemJson = accCashItemService.importAccCashItem(mapVo);
		
		return JSONObject.parseObject(accCashItemJson);
	}
	
	@RequestMapping(value = "/hrp/acc/acccashitem/addBatchAccCashItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccCashItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());

		String accCheckJson = accCashItemService.insertBatchAccCashItem(mapVo);

		return JSONObject.parseObject(accCheckJson);
		
	}

}

