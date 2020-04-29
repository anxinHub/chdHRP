/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller.baseData;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccCheckItemType;
import com.chd.hrp.sys.service.baseData.SysAccCheckItemTypeService;

/**
* @Title. @Description.
* 核算类
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class SysAccCheckItemTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(SysAccCheckItemTypeController.class);
	
	
	@Resource(name = "sysAccCheckItemTypeService")
	private final SysAccCheckItemTypeService sysAccCheckItemTypeService = null;
   
    
	/**
	*核算分类<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/sys/baseData/accCheckItemTypeAddPage", method = RequestMethod.GET)
	public String accCheckItemTypeAddPage(Model mode,String check_type_id, String check_type_name) throws Exception {
		mode.addAttribute("check_type_id", check_type_id);
		mode.addAttribute("check_type_name", check_type_name);
		return "hrp/sys/baseData/acccheckitem/accCheckItemTypeAdd";

	}
	/**
	*核算分类<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/sys/baseData/addAccCheckItemType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccCheckItemType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", "0");
		String accCheckTypeJson = sysAccCheckItemTypeService.addAccCheckItemType(mapVo);

		return JSONObject.parseObject(accCheckTypeJson);
		
	}
	/**
	*核算分类<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/sys/baseData/queryAccCheckItemType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccCheckItemType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		String accCheckItemType = sysAccCheckItemTypeService.queryAccCheckItemType(getPage(mapVo));

		return JSONObject.parseObject(accCheckItemType);
		
	}
	/**
	*核算分类<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/sys/baseData/deleteAccCheckItemType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCheckItemType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("copy_code", id.split("@")[2]);
            mapVo.put("type_code", id.split("@")[3]);
            mapVo.put("check_type_id", id.split("@")[4]);
            listVo.add(mapVo);
        }
		String accCheckItemTypeJson = sysAccCheckItemTypeService.deleteAccCheckItemType(listVo);
	   return JSONObject.parseObject(accCheckItemTypeJson);
			
	}
	
	/**
	*核算分类<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/sys/baseData/accCheckItemTypeUpdatePage", method = RequestMethod.GET)
	
	public String accCheckItemTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccCheckItemType accCheckItemType = new AccCheckItemType();
        accCheckItemType = sysAccCheckItemTypeService.queryAccCheckItemTypeById(mapVo);
		mode.addAttribute("check_type_id", accCheckItemType.getCheck_type_id());
		mode.addAttribute("check_type_name", accCheckItemType.getCheck_type_name());
		mode.addAttribute("group_id", accCheckItemType.getGroup_id());
		mode.addAttribute("hos_id", accCheckItemType.getHos_id());
		mode.addAttribute("copy_code", accCheckItemType.getCopy_code());
		mode.addAttribute("type_code", accCheckItemType.getType_code());
		mode.addAttribute("type_name", accCheckItemType.getType_name());
		mode.addAttribute("sort_code", accCheckItemType.getSort_code());
		mode.addAttribute("spell_code", accCheckItemType.getSpell_code());
		mode.addAttribute("wbx_code", accCheckItemType.getWbx_code());
		mode.addAttribute("is_stop", accCheckItemType.getIs_stop());
		
		return "hrp/sys/baseData/acccheckitem/accCheckItemTypeUpdate";
	}
	/**
	*核算分类<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/sys/baseData/updateAccCheckItemType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCheckItemType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
	   
	   
		String accCheckItemTypeJson = sysAccCheckItemTypeService.updateAccCheckItemType(mapVo);
		
		return JSONObject.parseObject(accCheckItemTypeJson);
	}
	/**
	*核算分类<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/sys/baseData/importAccCheckItemType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccCheckItemType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accCheckItemTypeJson = sysAccCheckItemTypeService.importAccCheckItemType(mapVo);
		
		return JSONObject.parseObject(accCheckItemTypeJson);
	}
}

