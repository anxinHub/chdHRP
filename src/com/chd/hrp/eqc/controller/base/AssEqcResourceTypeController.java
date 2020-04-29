
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
import com.chd.hrp.eqc.service.base.AssEqcResourceTypeService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 11其他资源消耗项定义 ASS_EQCRESOURCETYPE Controller实现类
*/
@Controller
public class AssEqcResourceTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqcResourceTypeController.class);
	
	//引入Service服务
	@Resource(name = "assEqcResourceTypeService")
	private final AssEqcResourceTypeService assEqcResourceTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcResourceTypeMainPage", method = RequestMethod.GET)
	public String assEqcResourceTypeMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcresourcetype/assEqcResourceTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcResourceTypeAddPage", method = RequestMethod.GET)
	public String assEqcResourceTypeAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcresourcetype/assEqcResourceTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 11其他资源消耗项定义 ASS_EQCRESOURCETYPE
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqcResourceType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqcResourceType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("oresource_code", ids[0])   ;
			mapVo.put("oresource_desc", ids[1]);
			mapVo.put("price_type", ids[2]);
			mapVo.put("price", ids[3]);
			mapVo.put("unit_code", ids[4]);
			mapVo.put("remark", ids[5]);
			mapVo.put("rowNo", ids[6]);// 行号
			mapVo.put("flag", ids[7]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 0);// 无效标志  不清楚干什么用  暂时赋0
			
			listVo.add(mapVo);
	      
	    }
       
		String assEqcResourceTypeJson = assEqcResourceTypeService.save(listVo);

		return JSONObject.parseObject(assEqcResourceTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 11其他资源消耗项定义 ASS_EQCRESOURCETYPE
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcResourceTypeUpdatePage", method = RequestMethod.GET)
	public String assEqcResourceTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> assEqcResourceType = new HashMap<String, Object>();
    
		assEqcResourceType = assEqcResourceTypeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqcResourceType.get("group_id"));
		mode.addAttribute("hos_id", assEqcResourceType.get("hos_id"));
		mode.addAttribute("copy_code", assEqcResourceType.get("copy_code"));
		mode.addAttribute("oresource_code", assEqcResourceType.get("oresource_code"));
		mode.addAttribute("oresource_desc", assEqcResourceType.get("oresource_desc"));
		mode.addAttribute("price", assEqcResourceType.get("price"));
		mode.addAttribute("unit_code", assEqcResourceType.get("unit_code"));
		mode.addAttribute("remark", assEqcResourceType.get("remark"));
		mode.addAttribute("invalid_flag", assEqcResourceType.get("invalid_flag"));
		
		return "hrp/eqc/base/asseqcresourcetype/assEqcResourceTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 11其他资源消耗项定义 ASS_EQCRESOURCETYPE
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqcResourceType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqcResourceType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqcResourceTypeJson = assEqcResourceTypeService.update(mapVo);
		
		return JSONObject.parseObject(assEqcResourceTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 11其他资源消耗项定义 ASS_EQCRESOURCETYPE
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqcResourceType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqcResourceType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("oresource_code", ids[0]);
				
				listVo.add(mapVo);
	      
	    }
	    
		String assEqcResourceTypeJson = assEqcResourceTypeService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqcResourceTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 11其他资源消耗项定义 ASS_EQCRESOURCETYPE
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqcResourceType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqcResourceType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqcResourceType = assEqcResourceTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqcResourceType);
		
	}
	
    
}

