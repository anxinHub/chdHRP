
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
import com.chd.hrp.eqc.service.xymanage.AssEqUsedResourceService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 19其他设备月度资源消耗 ASS_EQUsedResource Controller实现类
*/
@Controller
public class AssEqUsedResourceController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqUsedResourceController.class);
	
	//引入Service服务
	@Resource(name = "assEqUsedResourceService")
	private final AssEqUsedResourceService assEqUsedResourceService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUsedResourceMainPage", method = RequestMethod.GET)
	public String assEqUsedResourceMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequsedresource/assEqUsedResourceMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUsedResourceAddPage", method = RequestMethod.GET)
	public String assEqUsedResourceAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequsedresource/assEqUsedResourceAdd";

	}

	/**
	 * @Description 
	 * 添加数据 15其他设备月度资源消耗 ASS_EQUsedResource
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqUsedResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqUsedResource(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
            
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1]);
			mapVo.put("analysis_code", ids[2]);
			mapVo.put("oresource_code", ids[3]);
			mapVo.put("price", ids[4]);
			mapVo.put("unit_code", ids[5]);
			mapVo.put("quantity", ids[6]);
			mapVo.put("amount", ids[7]);
			mapVo.put("is_input_flag", ids[8]);
			mapVo.put("status", ids[9]);
			mapVo.put("remark", ids[10]);
			mapVo.put("rowNo", ids[11]);// 行号
			mapVo.put("flag", ids[12]);// 添加 修改标识
			
			listVo.add(mapVo);
	      
	    }
		String assEqUsedResourceJson = assEqUsedResourceService.save(listVo);

		return JSONObject.parseObject(assEqUsedResourceJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 15其他设备月度资源消耗 ASS_EQUsedResource
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUsedResourceUpdatePage", method = RequestMethod.GET)
	public String assEqUsedResourceUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> assEqUsedResource = new HashMap<String, Object>();
    
		assEqUsedResource = assEqUsedResourceService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqUsedResource.get("group_id"));
		mode.addAttribute("hos_id", assEqUsedResource.get("hos_id"));
		mode.addAttribute("copy_code", assEqUsedResource.get("copy_code"));
		mode.addAttribute("year", assEqUsedResource.get("year"));
		mode.addAttribute("month", assEqUsedResource.get("month"));
		mode.addAttribute("analysis_code", assEqUsedResource.get("analysis_code"));
		mode.addAttribute("dept_code", assEqUsedResource.get("dept_code"));
		mode.addAttribute("oresource_code", assEqUsedResource.get("oresource_code"));
		mode.addAttribute("price", assEqUsedResource.get("price"));
		mode.addAttribute("unit_code", assEqUsedResource.get("unit_code"));
		mode.addAttribute("quantity", assEqUsedResource.get("quantity"));
		mode.addAttribute("amount", assEqUsedResource.get("amount"));
		mode.addAttribute("remark", assEqUsedResource.get("remark"));
		mode.addAttribute("is_input_flag", assEqUsedResource.get("is_input_flag"));
		mode.addAttribute("sale_amount", assEqUsedResource.get("sale_amount"));
		mode.addAttribute("add_date", assEqUsedResource.get("add_date"));
		mode.addAttribute("add_time", assEqUsedResource.get("add_time"));
		mode.addAttribute("add_user", assEqUsedResource.get("add_user"));
		mode.addAttribute("update_date", assEqUsedResource.get("update_date"));
		mode.addAttribute("update_time", assEqUsedResource.get("update_time"));
		mode.addAttribute("update_user", assEqUsedResource.get("update_user"));
		mode.addAttribute("status", assEqUsedResource.get("status"));
		mode.addAttribute("submit_date", assEqUsedResource.get("submit_date"));
		mode.addAttribute("submit_time", assEqUsedResource.get("submit_time"));
		mode.addAttribute("submit_user", assEqUsedResource.get("submit_user"));
		mode.addAttribute("audit_date", assEqUsedResource.get("audit_date"));
		mode.addAttribute("audit_time", assEqUsedResource.get("audit_time"));
		mode.addAttribute("audit_user", assEqUsedResource.get("audit_user"));
		mode.addAttribute("cancel_date", assEqUsedResource.get("cancel_date"));
		mode.addAttribute("cancel_time", assEqUsedResource.get("cancel_time"));
		mode.addAttribute("cancel_user", assEqUsedResource.get("cancel_user"));
		
		return "hrp/eqc/xymanage/assequsedresource/assEqUsedResourceUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 15其他设备月度资源消耗 ASS_EQUsedResource
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqUsedResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqUsedResource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assEqUsedResourceJson = assEqUsedResourceService.update(mapVo);
		
		return JSONObject.parseObject(assEqUsedResourceJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 15其他设备月度资源消耗 ASS_EQUsedResource
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqUsedResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqUsedResource(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("analysis_code", ids[2])   ;
			mapVo.put("oresource_code", ids[3]);
				
			listVo.add(mapVo);
	      
	    }
	    
		String assEqUsedResourceJson = assEqUsedResourceService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqUsedResourceJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 15其他设备月度资源消耗 ASS_EQUsedResource
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqUsedResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqUsedResource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUsedResource = assEqUsedResourceService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqUsedResource);
		
	}
	
    
}

