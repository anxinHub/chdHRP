/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
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
import com.chd.hrp.med.service.info.basic.MedOutUseService;
/**
 * 
 * @Description:
 * MED_OUT_USE
 * @Table:
 * MED_OUT_USE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedOutUseController extends BaseController{
	private static Logger logger = Logger.getLogger(MedOutUseController.class);
	
	
	@Resource(name = "medOutUseService")
	private final MedOutUseService medOutUseService = null;
   
    
	/**
	 * 领用用途主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/outuse/medOutUseMainPage", method = RequestMethod.GET)
	public String medOutUseMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/outuse/medOutUseMain";

	}
	/**
	 * 领用用途--添加页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/outuse/medOutUseAddPage", method = RequestMethod.GET)
	public String medOutUseAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/outuse/medOutUseAdd";

	}
	
	/**
	 * 领用用途--保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/outuse/addMedOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("use_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("use_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
		String medOutUseJson = medOutUseService.add(mapVo);

		return JSONObject.parseObject(medOutUseJson);
		
	}
	/**
	 * 领用用途--查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/outuse/queryMedOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
			
		String medOutUse = medOutUseService.query(mapVo);
		return JSONObject.parseObject(medOutUse);
		
	}
	
	/**
	 * 领用用途--删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/outuse/deleteMedOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedOutUse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("use_code", id.split("@")[0]);
			mapVo.put("group_id", id.split("@")[1]);
			mapVo.put("hos_id", id.split("@")[2]);
			mapVo.put("copy_code", id.split("@")[3]);
            //mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String medOutUseJson = medOutUseService.deleteBatch(listVo);
	   return JSONObject.parseObject(medOutUseJson);
			
	}
	
	/**
	 * 领用用途--更新页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/outuse/medOutUseUpdatePage", method = RequestMethod.GET)
	
	public String medOutUseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> medOutUse = medOutUseService.queryByCode(mapVo);
		mode.addAttribute("medOutUse", medOutUse);
		
		return "hrp/med/info/basic/outuse/medOutUseUpdate";
	}
	
	/**
	 * 领用用途--更新
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/outuse/updateMedOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatemedOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("use_name").toString()));	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("use_name").toString()));
	   
		String medOutUseJson = medOutUseService.update(mapVo);
		
		return JSONObject.parseObject(medOutUseJson);
	}
	
	/**
	 * 领用用途--导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/outuse/importMedOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importmedOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medOutUseJson =null;// medOutUseService.import(mapVo);
		
		return JSONObject.parseObject(medOutUseJson);
	}
	
	
}

