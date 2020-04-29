
/*
 *
 */package com.chd.hrp.htcg.controller.info;
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
import com.chd.hrp.htcg.entity.info.HtcgRecipeType;
import com.chd.hrp.htcg.service.info.HtcgRecipeTypeService;

/**
 * 
 * @Title.   医嘱分类维护
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgRecipeTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgRecipeTypeController.class);
	
	
	@Resource(name = "htcgRecipeTypeService")
	private final HtcgRecipeTypeService htcgRecipeTypeService = null;
    
	
	/**
	 * MAIN页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/info/recipetype/htcgRecipeTypeMainPage", method = RequestMethod.GET)
	public String htcgRecipeTypeMainPage(Model mode) throws Exception {

		return "hrp/htcg/info/recipetype/htcgRecipeTypeMain";

	}

	/**
	 * 添加页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/info/recipetype/htcgRecipeTypeAddPage", method = RequestMethod.GET)
	public String recipeTypeAddPage(Model mode) throws Exception {

		return "hrp/htcg/info/recipetype/htcgRecipeTypeAdd";

	}
	
	/**
	 * 保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/info/recipetype/addHtcgRecipeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgRecipeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String recipeTypeJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("recipe_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("recipe_type_name").toString()));
		
		try {
			
			recipeTypeJson = htcgRecipeTypeService.addHtcgRecipeType(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			recipeTypeJson = e.getMessage();
		}


		return JSONObject.parseObject(recipeTypeJson);
		
	}

	/**
	 * 查询信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/info/recipetype/queryHtcgRecipeType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgRecipeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String recipeType = htcgRecipeTypeService.queryHtcgRecipeType(getPage(mapVo));

		return JSONObject.parseObject(recipeType);
		
	}
	
	/**
	 * 查询
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/info/recipetype/deleteHtcgRecipeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgRecipeType(@RequestParam(value = "ParamVo", required = true) String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("recipe_type_code", ids[3]);
            listVo.add(mapVo);
        }
		
		String recipeTypeJson  = "";
		try {
			recipeTypeJson = htcgRecipeTypeService.deleteBatchHtcgRecipeType(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			recipeTypeJson = e.getMessage();
		}
	   return JSONObject.parseObject(recipeTypeJson);
			
	}
	
	
	/**
	 * 修改页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/info/recipetype/htcgRecipeTypeUpdatePage", method = RequestMethod.GET)
	
	public String htcgRecipeTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		HtcgRecipeType htcgRecipeType = htcgRecipeTypeService.queryHtcgRecipeTypeByCode(mapVo);
		mode.addAttribute("group_id", htcgRecipeType.getGroup_id());
		mode.addAttribute("hos_id", htcgRecipeType.getHos_id());
		mode.addAttribute("copy_code", htcgRecipeType.getCopy_code());
		mode.addAttribute("recipe_type_code", htcgRecipeType.getRecipe_type_code());
		mode.addAttribute("recipe_type_name", htcgRecipeType.getRecipe_type_name());
		mode.addAttribute("spell_code", htcgRecipeType.getSpell_code());
		mode.addAttribute("wbx_code", htcgRecipeType.getWbx_code());
		mode.addAttribute("is_stop", htcgRecipeType.getIs_stop());
		
		return "hrp/htcg/info/recipetype/htcgRecipeTypeUpdate";
	}
		
	/**
	 * 修改保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/info/recipetype/updateHtcgRecipeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgRecipeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("recipe_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("recipe_type_name").toString()));
		
		String recipeTypeJson = "";
		
		try {
			recipeTypeJson = htcgRecipeTypeService.updateHtcgRecipeType(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			recipeTypeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(recipeTypeJson);
	}
	
	/**
	 * 导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/htcg/info/recipetype/importHtcgRecipeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgRecipeType(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		String  recipeTypeJson = "";
		
		try {
			
			recipeTypeJson = htcgRecipeTypeService.importHtcgRecipeType(mapVo);
		}
		catch (Exception e) {
			recipeTypeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(recipeTypeJson);

	}

}

