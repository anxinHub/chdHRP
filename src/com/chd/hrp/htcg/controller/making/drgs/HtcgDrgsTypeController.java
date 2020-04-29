package com.chd.hrp.htcg.controller.making.drgs;
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
import com.chd.hrp.htcg.entity.making.drgs.HtcgDrgsType;
import com.chd.hrp.htcg.service.making.drgs.HtcgDrgsTypeService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgDrgsTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgDrgsTypeController.class);
	
	
	@Resource(name = "htcgDrgsTypeService")
	private final HtcgDrgsTypeService htcgDrgsTypeService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/drgstype/htcgDrgsTypeMainPage", method = RequestMethod.GET)
	public String htcgDrgsTypeMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/drgstype/htcgDrgsTypeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/making/drgstype/htcgDrgsTypeAddPage", method = RequestMethod.GET)
	public String htcgDrgsTypeAddPage(Model mode) throws Exception {

		return "hrp/htcg/making/drgstype/htcgDrgsTypeAdd";

	}
	 
	// 保存
	@RequestMapping(value = "/hrp/htcg/making/drgstype/addHtcgDrgsType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHtcgDrgsType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
 
		String drgsTypeJson ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drgs_type_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drgs_type_name").toString()));
        
		
		try{
			
			drgsTypeJson= htcgDrgsTypeService.addHtcgDrgsType(mapVo);
		
		}catch(Exception e){
			
			drgsTypeJson= e.getMessage();
			
		}
		return JSONObject.parseObject(drgsTypeJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/making/drgstype/queryHtcgDrgsType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgDrgsType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String drgsType = htcgDrgsTypeService.queryHtcgDrgsType(getPage(mapVo));

		return JSONObject.parseObject(drgsType);
		
	}
	//删除
	@RequestMapping(value = "/hrp/htcg/making/drgstype/deleteHtcgDrgsType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrgsType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String drgsTypeJson ;
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id",ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("drgs_type_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		try{
			drgsTypeJson = htcgDrgsTypeService.deleteBatchHtcgDrgsType(listVo);
			
		}catch(Exception e){
			
			drgsTypeJson= e.getMessage();
			
		}
	   return JSONObject.parseObject(drgsTypeJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/making/drgstype/htcgDrgsTypeUpdatePage", method = RequestMethod.GET)
	public String htcgDrgsTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgDrgsType drgsType =  htcgDrgsTypeService.queryHtcgDrgsTypeByCode(mapVo);
		mode.addAttribute("group_id", drgsType.getGroup_id());
		mode.addAttribute("hos_id", drgsType.getHos_id());
		mode.addAttribute("copy_code", drgsType.getCopy_code());
		mode.addAttribute("drgs_type_code", drgsType.getDrgs_type_code());
		mode.addAttribute("drgs_type_name", drgsType.getDrgs_type_name());
		mode.addAttribute("spell_code", drgsType.getSpell_code());
		mode.addAttribute("wbx_code", drgsType.getWbx_code());
		mode.addAttribute("drgs_type_note", drgsType.getDrgs_type_note());
		
		return "hrp/htcg/making/drgstype/htcgDrgsTypeUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/making/drgstype/updateHtcgDrgsType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgDrgsType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String drgsTypeJson ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drgs_type_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drgs_type_name").toString()));
		try{
			
		 drgsTypeJson = htcgDrgsTypeService.updateHtcgDrgsType(mapVo);
		
		}catch(Exception e){
			
			drgsTypeJson= e.getMessage();
			
		}
		return JSONObject.parseObject(drgsTypeJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/htcg/making/drgstype/importHtcgDrgsType", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> importHtcgDrgsType(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
 
		String drgsTypeJson ="";
		
		try{
			
		    drgsTypeJson = htcgDrgsTypeService.importHtcgDrgsType(mapVo);
		
		}catch(Exception e){
			
			drgsTypeJson= e.getMessage();
			
		}
		return JSONObject.parseObject(drgsTypeJson);
	}

}

