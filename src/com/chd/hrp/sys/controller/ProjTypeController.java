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
import com.chd.hrp.sys.entity.ProjType;
import com.chd.hrp.sys.serviceImpl.ProjTypeServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class ProjTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(ProjTypeController.class);
	
	
	@Resource(name = "projTypeService")
	private final ProjTypeServiceImpl projTypeService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/projtype/projTypeMainPage", method = RequestMethod.GET)
	public String projTypeMainPage(Model mode) throws Exception {

		return "hrp/sys/projtype/projTypeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/projtype/projTypeAddPage", method = RequestMethod.GET)
	public String projTypeAddPage(Model mode) throws Exception {

		return "hrp/sys/projtype/projTypeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/projtype/addProjType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addProjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String projTypeJson = projTypeService.addProjType(mapVo);

		return JSONObject.parseObject(projTypeJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/projtype/queryProjType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryProjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String projType = projTypeService.queryProjType(getPage(mapVo));

		return JSONObject.parseObject(projType);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/projtype/deleteProjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProjType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("type_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String projTypeJson = projTypeService.deleteBatchProjType(listVo);
	   return JSONObject.parseObject(projTypeJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/projtype/projTypeUpdatePage", method = RequestMethod.GET)
	
	public String projTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        ProjType projType = new ProjType();
		projType = projTypeService.queryProjTypeByCode(mapVo);
		mode.addAttribute("group_id", projType.getGroup_id());
		mode.addAttribute("hos_id", projType.getHos_id());
		mode.addAttribute("type_code", projType.getType_code());
		mode.addAttribute("type_name", projType.getType_name());
		mode.addAttribute("spell_code", projType.getSpell_code());
		mode.addAttribute("wbx_code", projType.getWbx_code());
		mode.addAttribute("is_stop", projType.getIs_stop());
		mode.addAttribute("note", projType.getNote());
		
		return "hrp/sys/projtype/projTypeUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/projtype/updateProjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projTypeJson = projTypeService.updateProjType(mapVo);
		
		return JSONObject.parseObject(projTypeJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/projtype/importProjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importProjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projTypeJson = projTypeService.importProjType(mapVo);
		
		return JSONObject.parseObject(projTypeJson);
	}

}

