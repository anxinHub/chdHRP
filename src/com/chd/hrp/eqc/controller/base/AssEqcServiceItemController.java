
/*
 *
 */
 package com.chd.hrp.eqc.controller.base;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.eqc.service.base.AssEqcServiceItemService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 01服务项目定义表 ASS_EQCServiceItem Controller实现类
*/
@Controller
public class AssEqcServiceItemController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqcServiceItemController.class);
	
	//引入Service服务
	@Resource(name = "assEqcServiceItemService")
	private final AssEqcServiceItemService assEqcServiceItemService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServiceItemMainPage", method = RequestMethod.GET)
	public String assEqcServiceItemMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcserviceitem/assEqcServiceItemMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServiceItemAddPage", method = RequestMethod.GET)
	public String assEqcServiceItemAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcserviceitem/assEqcServiceItemAdd";

	}

	/**
	 * @Description 
	 * 添加数据 01服务项目定义表 ASS_EQCServiceItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqcServiceItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savwAssEqcServiceItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("charge_kind_id", ids[0])   ;
			mapVo.put("unit_code", ids[1]);
			mapVo.put("price", ids[2]);
			mapVo.put("remark", ids[3]);
			mapVo.put("import_flag", ids[4]);
			mapVo.put("min_minutes", ids[5]);
			mapVo.put("minutes_per_times", ids[6]);
			mapVo.put("max_minutes", ids[7]);
			mapVo.put("rowNo", ids[8]);// 行号
			mapVo.put("flag", ids[9]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 0);
			mapVo.put("update_date", DateUtil.getCurrenDate("yyyy-MM-dd"));
			mapVo.put("ex_desc", null);
			
			listVo.add(mapVo);
	      
	    }
       
		String assEqcServiceItemJson = assEqcServiceItemService.save(listVo);

		return JSONObject.parseObject(assEqcServiceItemJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 01服务项目定义表 ASS_EQCServiceItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServiceItemUpdatePage", method = RequestMethod.GET)
	public String assEqcServiceItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> assEqcServiceItem = new HashMap<String, Object>();
    
		assEqcServiceItem = assEqcServiceItemService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqcServiceItem.get("group_id"));
		mode.addAttribute("hos_id", assEqcServiceItem.get("hos_id"));
		mode.addAttribute("copy_code", assEqcServiceItem.get("copy_code"));
		mode.addAttribute("charge_kind_id", assEqcServiceItem.get("charge_kind_id"));
		mode.addAttribute("unit_code", assEqcServiceItem.get("unit_code"));
		mode.addAttribute("price", assEqcServiceItem.get("price"));
		mode.addAttribute("remark", assEqcServiceItem.get("remark"));
		mode.addAttribute("import_flag", assEqcServiceItem.get("import_flag"));
		mode.addAttribute("min_minutes", assEqcServiceItem.get("min_minutes"));
		mode.addAttribute("minutes_per_times", assEqcServiceItem.get("minutes_per_times"));
		mode.addAttribute("max_minutes", assEqcServiceItem.get("max_minutes"));
		mode.addAttribute("invalid_flag", assEqcServiceItem.get("invalid_flag"));
		mode.addAttribute("update_date", assEqcServiceItem.get("update_date"));
		mode.addAttribute("ex_desc", assEqcServiceItem.get("ex_desc"));
		
		return "hrp/eqc/base/asseqcserviceitem/assEqcServiceItemUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 01服务项目定义表 ASS_EQCServiceItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateassEqcServiceItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateassEqcServiceItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqcServiceItemJson = assEqcServiceItemService.update(mapVo);
		
		return JSONObject.parseObject(assEqcServiceItemJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 01服务项目定义表 ASS_EQCServiceItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqcServiceItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqcServiceItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("charge_kind_id", ids[0]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String assEqcServiceItemJson = assEqcServiceItemService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assEqcServiceItemJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 01服务项目定义表 ASS_EQCServiceItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqcServiceItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqcServiceItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqcServiceItem = assEqcServiceItemService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqcServiceItem);
		
	}
	
    
}

