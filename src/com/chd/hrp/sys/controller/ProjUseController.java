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
import com.chd.hrp.sys.entity.ProjUse;
import com.chd.hrp.sys.serviceImpl.ProjUseServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class ProjUseController extends BaseController{
	private static Logger logger = Logger.getLogger(ProjUseController.class);
	
	
	@Resource(name = "projUseService")
	private final ProjUseServiceImpl projUseService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/projuse/projUseMainPage", method = RequestMethod.GET)
	public String projUseMainPage(Model mode) throws Exception {

		return "hrp/sys/projuse/projUseMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/projuse/projUseAddPage", method = RequestMethod.GET)
	public String projUseAddPage(Model mode) throws Exception {

		return "hrp/sys/projuse/projUseAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/projuse/addProjUse", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addProjUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String projUseJson = projUseService.addProjUse(mapVo);

		return JSONObject.parseObject(projUseJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/projuse/queryProjUse", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryProjUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String projUse = projUseService.queryProjUse(getPage(mapVo));

		return JSONObject.parseObject(projUse);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/projuse/deleteProjUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProjUse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("use_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String projUseJson = projUseService.deleteBatchProjUse(listVo);
	   return JSONObject.parseObject(projUseJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/projuse/projUseUpdatePage", method = RequestMethod.GET)
	
	public String projUseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        ProjUse projUse = new ProjUse();
		projUse = projUseService.queryProjUseByCode(mapVo);
		mode.addAttribute("group_id", projUse.getGroup_id());
		mode.addAttribute("hos_id", projUse.getHos_id());
		mode.addAttribute("use_code", projUse.getUse_code());
		mode.addAttribute("use_name", projUse.getUse_name());
		mode.addAttribute("spell_code", projUse.getSpell_code());
		mode.addAttribute("wbx_code", projUse.getWbx_code());
		mode.addAttribute("is_stop", projUse.getIs_stop());
		mode.addAttribute("note", projUse.getNote());
		
		return "hrp/sys/projuse/projUseUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/projuse/updateProjUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProjUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projUseJson = projUseService.updateProjUse(mapVo);
		
		return JSONObject.parseObject(projUseJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/projuse/importProjUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importProjUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projUseJson = projUseService.importProjUse(mapVo);
		
		return JSONObject.parseObject(projUseJson);
	}

}

