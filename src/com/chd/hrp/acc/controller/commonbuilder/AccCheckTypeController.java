/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.commonbuilder;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCheckTypeServiceImpl;

/**
* @Title. @Description.
* 核算类
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccCheckTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCheckTypeController.class);
	
	
	@Resource(name = "accCheckTypeService")
	private final AccCheckTypeServiceImpl accCheckTypeService = null;
   
    
	/**
	*核算类<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accchecktype/accCheckTypeMainPage", method = RequestMethod.GET)
	public String accCheckTypeMainPage(Model mode) throws Exception {
		
		return "hrp/acc/acccheckitem/accCheckTypeMain";

	}
	/**
	*核算类<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accchecktype/accCheckTypeAddPage", method = RequestMethod.GET)
	public String accCheckTypeAddPage(Model mode) throws Exception {
		
		return "hrp/acc/acccheckitem/accCheckTypeAdd";

	}
	/**
	*核算类<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accchecktype/addAccCheckType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccCheckType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("check_type_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("check_type_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String accCheckTypeJson = accCheckTypeService.addAccCheckType(mapVo);

		return JSONObject.parseObject(accCheckTypeJson);
		
	}
	/**
	*核算类<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accchecktype/queryAccCheckType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccCheckType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accCheckType = accCheckTypeService.queryAccCheckType(getPage(mapVo));

		return JSONObject.parseObject(accCheckType);
		
	}
	/**
	*核算类<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accchecktype/deleteAccCheckType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCheckType(@RequestParam Map<String, Object> paramVo, Model mode) throws Exception {
		
//		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
//		for ( String id: paramVo.split(",")) {
//			Map<String, Object> mapVo=new HashMap<String, Object>();
//			mapVo.put("check_type_id", id.split("@")[0]);//实际实体类变量
//			mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
//			mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
//            mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
//            listVo.add(mapVo);
//        }
		String accCheckTypeJson = accCheckTypeService.deleteAccCheckType(paramVo);
	   return JSONObject.parseObject(accCheckTypeJson);
			
	}
	
	/**
	*核算类<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accchecktype/accCheckTypeUpdatePage", method = RequestMethod.GET)
	
	public String accCheckTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccCheckType accCheckType = new AccCheckType();
		accCheckType = accCheckTypeService.queryAccCheckTypeById(mapVo);
		mode.addAttribute("check_type_id", accCheckType.getCheck_type_id());
		mode.addAttribute("group_id", accCheckType.getGroup_id());
		mode.addAttribute("hos_id", accCheckType.getHos_id());
		mode.addAttribute("copy_code", accCheckType.getCopy_code());
		mode.addAttribute("check_type_code", accCheckType.getCheck_type_code());
		mode.addAttribute("check_type_name", accCheckType.getCheck_type_name());
		mode.addAttribute("sort_code", accCheckType.getSort_code());
		mode.addAttribute("spell_code", accCheckType.getSpell_code());
		mode.addAttribute("wbx_code", accCheckType.getWbx_code());
		mode.addAttribute("is_stop", accCheckType.getIs_stop());
		mode.addAttribute("is_sys", accCheckType.getIs_sys());
		mode.addAttribute("column_check", accCheckType.getColumn_check());
		mode.addAttribute("z_code", accCheckType.getZ_code());
		
		return "hrp/acc/acccheckitem/accCheckTypeUpdate";
	}
	/**
	*核算类<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accchecktype/updateAccCheckType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCheckType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("check_type_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("check_type_name").toString()));
	   
	   
		String accCheckTypeJson = accCheckTypeService.updateAccCheckType(mapVo);
		
		return JSONObject.parseObject(accCheckTypeJson);
	}
	/**
	*核算类<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accchecktype/importAccCheckType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccCheckType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accCheckTypeJson = accCheckTypeService.importAccCheckType(mapVo);
		
		return JSONObject.parseObject(accCheckTypeJson);
	}

	@RequestMapping(value = "/hrp/acc/accchecktype/queryAccCheckTypeByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCheckTypeByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String group = accCheckTypeService.queryAccCheckTypeByMenu(mapVo);

		return JSONObject.parseObject(group);
		
	}
}

