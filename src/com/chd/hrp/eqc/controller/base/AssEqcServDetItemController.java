
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
import com.chd.hrp.eqc.service.base.AssEqcServDetItemService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 02服务项目细项定义表 ASS_EQCServDetItem Controller实现类
*/
@Controller
public class AssEqcServDetItemController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqcServDetItemController.class);
	
	//引入Service服务
	@Resource(name = "assEqcServDetItemService")
	private final AssEqcServDetItemService assEqcServDetItemService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServDetItemMainPage", method = RequestMethod.GET)
	public String assEqcServDetItemMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcservdetitem/assEqcServDetItemMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServDetItemAddPage", method = RequestMethod.GET)
	public String assEqcServDetItemAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcservdetitem/assEqcServDetItemAdd";

	}

	/**
	 * @Description 
	 * 添加数据 02服务项目细项定义表 ASS_EQCServDetItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqcServDetItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqcServDetItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("charge_item_id", ids[0])   ;
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
       
		String assEqcServDetItemJson = assEqcServDetItemService.save(listVo);

		return JSONObject.parseObject(assEqcServDetItemJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 02服务项目细项定义表 ASS_EQCServDetItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServDetItemUpdatePage", method = RequestMethod.GET)
	public String assEqcServDetItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		Map<String,Object> assEqcServDetItem = new HashMap<String, Object>();
    
		assEqcServDetItem = assEqcServDetItemService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqcServDetItem.get("group_id"));
		mode.addAttribute("hos_id", assEqcServDetItem.get("hos_id"));
		mode.addAttribute("copy_code", assEqcServDetItem.get("copy_code"));
		mode.addAttribute("charge_item_id", assEqcServDetItem.get("charge_item_id"));
		mode.addAttribute("unit_code", assEqcServDetItem.get("unit_code"));
		mode.addAttribute("price", assEqcServDetItem.get("price"));
		mode.addAttribute("remark", assEqcServDetItem.get("remark"));
		mode.addAttribute("import_flag", assEqcServDetItem.get("import_flag"));
		mode.addAttribute("min_minutes", assEqcServDetItem.get("min_minutes"));
		mode.addAttribute("minutes_per_times", assEqcServDetItem.get("minutes_per_times"));
		mode.addAttribute("max_minutes", assEqcServDetItem.get("max_minutes"));
		mode.addAttribute("invalid_flag", assEqcServDetItem.get("invalid_flag"));
		mode.addAttribute("update_date", assEqcServDetItem.get("update_date"));
		mode.addAttribute("ex_desc", assEqcServDetItem.get("ex_desc"));
		
		return "hrp/eqc/base/asseqcservdetitem/assEqcServDetItemUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 02服务项目细项定义表 ASS_EQCServDetItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqcServDetItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqcServDetItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqcServDetItemJson = assEqcServDetItemService.update(mapVo);
		
		return JSONObject.parseObject(assEqcServDetItemJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 02服务项目细项定义表 ASS_EQCServDetItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqcServDetItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqcServDetItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("charge_item_id", ids[0]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String assEqcServDetItemJson = assEqcServDetItemService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assEqcServDetItemJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 02服务项目细项定义表 ASS_EQCServDetItem
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqcServDetItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqcServDetItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqcServDetItem = assEqcServDetItemService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqcServDetItem);
		
	}
	
    
}

