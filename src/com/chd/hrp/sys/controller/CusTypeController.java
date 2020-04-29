/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.util.*;
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
import com.chd.hrp.sys.entity.CusType;
import com.chd.hrp.sys.serviceImpl.CusTypeServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CusTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(CusTypeController.class);
	
	
	@Resource(name = "cusTypeService")
	private final CusTypeServiceImpl cusTypeService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/custype/cusTypeMainPage", method = RequestMethod.GET)
	public String cusTypeMainPage(Model mode) throws Exception {

		return "hrp/sys/custype/cusTypeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/custype/cusTypeAddPage", method = RequestMethod.GET)
	public String cusTypeAddPage(Model mode) throws Exception {

		return "hrp/sys/custype/cusTypeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/custype/addCusType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String cusTypeJson = cusTypeService.addCusType(mapVo);

		return JSONObject.parseObject(cusTypeJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/custype/queryCusType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String cusType = cusTypeService.queryCusType(getPage(mapVo));

		return JSONObject.parseObject(cusType);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/custype/deleteCusType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCusType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("type_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String cusTypeJson = cusTypeService.deleteBatchCusType(listVo);
	   return JSONObject.parseObject(cusTypeJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/custype/cusTypeUpdatePage", method = RequestMethod.GET)
	
	public String cusTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        CusType cusType = new CusType();
		cusType = cusTypeService.queryCusTypeByCode(mapVo);
		mode.addAttribute("group_id", cusType.getGroup_id());
		mode.addAttribute("hos_id", cusType.getHos_id());
		mode.addAttribute("type_code", cusType.getType_code());
		mode.addAttribute("type_name", cusType.getType_name());
		mode.addAttribute("spell_code", cusType.getSpell_code());
		mode.addAttribute("wbx_code", cusType.getWbx_code());
		mode.addAttribute("is_stop", cusType.getIs_stop());
		mode.addAttribute("note", cusType.getNote());
		
		return "hrp/sys/custype/cusTypeUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/custype/updateCusType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String cusTypeJson = cusTypeService.updateCusType(mapVo);
		
		return JSONObject.parseObject(cusTypeJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/custype/importCusType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importCusType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String cusTypeJson = cusTypeService.importCusType(mapVo);
		
		return JSONObject.parseObject(cusTypeJson);
	}

}

