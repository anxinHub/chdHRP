package com.chd.hrp.htcg.controller.making;
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
import com.chd.hrp.htcg.entity.making.HtcgIcd10;
import com.chd.hrp.htcg.service.making.HtcgIcd10Service;

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
public class HtcgIcd10Controller extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgIcd10Controller.class);
	
	
	@Resource(name = "htcgIcd10Service")
	private final HtcgIcd10Service htcgIcd10Service = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/icd10/htcgIcd10MainPage", method = RequestMethod.GET)
	public String htcgIcd10MainPage(Model mode) throws Exception {

		return "hrp/htcg/making/icd10/htcgIcd10Main";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/making/icd10/htcgIcd10AddPage", method = RequestMethod.GET)
	public String htcgIcd10AddPage(Model mode) throws Exception {

		return "hrp/htcg/making/icd10/htcgIcd10Add";

	}
	 
	// 保存
	@RequestMapping(value = "/hrp/htcg/making/icd10/addHtcgIcd10", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgIcd10(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String icd10Json ;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("icd10_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("icd10_name").toString()));
		try{
		 
			icd10Json = htcgIcd10Service.addHtcgIcd10(mapVo);
		
		}catch(Exception e){
			
			icd10Json= e.getMessage();
			
		}
		return JSONObject.parseObject(icd10Json);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/making/icd10/queryHtcgIcd10", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgIcd10(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String icd10Json ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			
			icd10Json = htcgIcd10Service.queryHtcgIcd10(getPage(mapVo));
		
		}catch(Exception e){
			
			icd10Json= e.getMessage();
			
		}

		return JSONObject.parseObject(icd10Json);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/making/icd10/deleteHtcgIcd10", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgIcd10(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String icd10Json ;
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("icd10_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		
		try{
			
			icd10Json = htcgIcd10Service.deleteBatchHtcgIcd10(listVo);
		
		}catch(Exception e){
			
			icd10Json= e.getMessage();
			
		}
		
	   return JSONObject.parseObject(icd10Json);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/making/icd10/htcgIcd10UpdatePage", method = RequestMethod.GET)
	public String htcgIcd10UpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgIcd10 icd10 =  htcgIcd10Service.queryHtcgIcd10ByCode(mapVo);
		mode.addAttribute("group_id", icd10.getGroup_id());
		mode.addAttribute("hos_id", icd10.getHos_id());
		mode.addAttribute("icd10_code", icd10.getIcd10_code());
		mode.addAttribute("icd10_name", icd10.getIcd10_name());
		mode.addAttribute("spell_code", icd10.getSpell_code());
		mode.addAttribute("wbx_code", icd10.getWbx_code());
		mode.addAttribute("icd10_note", icd10.getIcd10_note());
		return "hrp/htcg/making/icd10/htcgIcd10Update";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/making/icd10/updateHtcgIcd10", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgIcd10(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String icd10Json ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("icd10_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("icd10_name").toString()));
		try{
			
			icd10Json = htcgIcd10Service.updateHtcgIcd10(mapVo);
			
		}catch(Exception e){
			
			icd10Json= e.getMessage();
			
		}
		
		return JSONObject.parseObject(icd10Json);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/htcg/making/icd10/importHtcgIcd10", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> htcgIcd10ImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
 
		String icd10Json ;
		try {
			icd10Json = htcgIcd10Service.importHtcgIcd10(mapVo);
		}
		catch (Exception e) {
			icd10Json= e.getMessage();
		}
		return JSONObject.parseObject(icd10Json);
	}
	 

}

