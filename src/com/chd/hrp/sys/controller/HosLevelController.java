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
import com.chd.hrp.sys.entity.HosLevel;
import com.chd.hrp.sys.serviceImpl.HosLevelServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class HosLevelController extends BaseController{
	private static Logger logger = Logger.getLogger(HosLevelController.class);
	
	
	@Resource(name = "hosLevelService")
	private final HosLevelServiceImpl hosLevelService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/hoslevel/hosLevelMainPage", method = RequestMethod.GET)
	public String hosLevelMainPage(Model mode) throws Exception {

		return "hrp/sys/hoslevel/hosLevelMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/hoslevel/hosLevelAddPage", method = RequestMethod.GET)
	public String hosLevelAddPage(Model mode) throws Exception {

		return "hrp/sys/hoslevel/hosLevelAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/hoslevel/addHosLevel", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHosLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String hosLevelJson = hosLevelService.addHosLevel(mapVo);

		return JSONObject.parseObject(hosLevelJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/hoslevel/queryHosLevel", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHosLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String hosLevel = hosLevelService.queryHosLevel(getPage(mapVo));

		return JSONObject.parseObject(hosLevel);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/hoslevel/deleteHosLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHosLevel(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("level_code", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String hosLevelJson = hosLevelService.deleteBatchHosLevel(listVo);
	   return JSONObject.parseObject(hosLevelJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/hoslevel/hosLevelUpdatePage", method = RequestMethod.GET)
	
	public String hosLevelUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        HosLevel hosLevel = new HosLevel();
		hosLevel = hosLevelService.queryHosLevelByCode(mapVo);
		mode.addAttribute("level_code", hosLevel.getLevel_code());
		mode.addAttribute("level_name", hosLevel.getLevel_name());
		mode.addAttribute("is_stop", hosLevel.getIs_stop());
		mode.addAttribute("note", hosLevel.getNote());
		
		return "hrp/sys/hoslevel/hosLevelUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/hoslevel/updateHosLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHosLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String hosLevelJson = hosLevelService.updateHosLevel(mapVo);
		
		return JSONObject.parseObject(hosLevelJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/hoslevel/importHosLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHosLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String hosLevelJson = hosLevelService.importHosLevel(mapVo);
		
		return JSONObject.parseObject(hosLevelJson);
	}

}

