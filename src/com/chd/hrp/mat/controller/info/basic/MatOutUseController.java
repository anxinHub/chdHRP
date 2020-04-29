/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
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
import com.chd.hrp.mat.service.info.basic.MatOutUseService;
/**
 * 
 * @Description:
 * MAT_OUT_USE
 * @Table:
 * MAT_OUT_USE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatOutUseController extends BaseController{
	private static Logger logger = Logger.getLogger(MatOutUseController.class);
	
	
	@Resource(name = "matOutUseService")
	private final MatOutUseService matOutUseService = null;
   
    
	/**
	 * 领用用途主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/outuse/matOutUseMainPage", method = RequestMethod.GET)
	public String matOutUseMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/outuse/matOutUseMain";

	}
	/**
	 * 领用用途--添加页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/outuse/matOutUseAddPage", method = RequestMethod.GET)
	public String matOutUseAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/outuse/matOutUseAdd";

	}
	
	/**
	 * 领用用途--保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/outuse/addMatOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("use_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("use_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
		String matOutUseJson = matOutUseService.add(mapVo);

		return JSONObject.parseObject(matOutUseJson);
		
	}
	/**
	 * 领用用途--查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/outuse/queryMatOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
			
		String matOutUse = matOutUseService.query(getPage(mapVo));
		return JSONObject.parseObject(matOutUse);
		
	}
	
	/**
	 * 领用用途--删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/outuse/deleteMatOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatOutUse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String matOutUseJson = matOutUseService.deleteBatch(listVo);
	   return JSONObject.parseObject(matOutUseJson);
			
	}
	
	/**
	 * 领用用途--更新页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/outuse/matOutUseUpdatePage", method = RequestMethod.GET)
	
	public String matOutUseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> matOutUse = matOutUseService.queryByCode(mapVo);
		mode.addAttribute("matOutUse", matOutUse);
		
		return "hrp/mat/info/basic/outuse/matOutUseUpdate";
	}
	
	/**
	 * 领用用途--更新
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/outuse/updateMatOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatematOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	   
		String matOutUseJson = matOutUseService.update(mapVo);
		
		return JSONObject.parseObject(matOutUseJson);
	}
	
	/**
	 * 领用用途--导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/outuse/importMatOutUse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importmatOutUse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matOutUseJson =null;// matOutUseService.import(mapVo);
		
		return JSONObject.parseObject(matOutUseJson);
	}
	
	
}

