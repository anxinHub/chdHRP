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
import com.chd.hrp.sys.entity.ProjLevel;
import com.chd.hrp.sys.serviceImpl.ProjLevelServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class ProjLevelController extends BaseController{
	private static Logger logger = Logger.getLogger(ProjLevelController.class);
	
	
	@Resource(name = "projLevelService")
	private final ProjLevelServiceImpl projLevelService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/projlevel/projLevelMainPage", method = RequestMethod.GET)
	public String projLevelMainPage(Model mode) throws Exception {

		return "hrp/sys/projlevel/projLevelMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/projlevel/projLevelAddPage", method = RequestMethod.GET)
	public String projLevelAddPage(Model mode) throws Exception {

		return "hrp/sys/projlevel/projLevelAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/projlevel/addProjLevel", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addProjLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String projLevelJson = projLevelService.addProjLevel(mapVo);

		return JSONObject.parseObject(projLevelJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/projlevel/queryProjLevel", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryProjLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String projLevel = projLevelService.queryProjLevel(getPage(mapVo));

		return JSONObject.parseObject(projLevel);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/projlevel/deleteProjLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProjLevel(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("level_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String projLevelJson = projLevelService.deleteBatchProjLevel(listVo);
	   return JSONObject.parseObject(projLevelJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/projlevel/projLevelUpdatePage", method = RequestMethod.GET)
	
	public String projLevelUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        ProjLevel projLevel = new ProjLevel();
		projLevel = projLevelService.queryProjLevelByCode(mapVo);
		mode.addAttribute("group_id", projLevel.getGroup_id());
		mode.addAttribute("hos_id", projLevel.getHos_id());
		mode.addAttribute("level_code", projLevel.getLevel_code());
		mode.addAttribute("level_name", projLevel.getLevel_name());
		mode.addAttribute("spell_code", projLevel.getSpell_code());
		mode.addAttribute("wbx_code", projLevel.getWbx_code());
		mode.addAttribute("is_stop", projLevel.getIs_stop());
		mode.addAttribute("note", projLevel.getNote());
		
		return "hrp/sys/projlevel/projLevelUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/projlevel/updateProjLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProjLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projLevelJson = projLevelService.updateProjLevel(mapVo);
		
		return JSONObject.parseObject(projLevelJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/projlevel/importProjLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importProjLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projLevelJson = projLevelService.importProjLevel(mapVo);
		
		return JSONObject.parseObject(projLevelJson);
	}

}

