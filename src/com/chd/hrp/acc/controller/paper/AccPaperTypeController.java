package com.chd.hrp.acc.controller.paper;

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
import com.chd.hrp.acc.service.paper.AccPaperMainService;
import com.chd.hrp.acc.service.paper.AccPaperTypeService;

@Controller
public class AccPaperTypeController extends BaseController {
   
	private static Logger logger = Logger.getLogger(AccPaperTypeController.class);

	@Resource(name = "accPaperTypeService")
	private final AccPaperTypeService accPaperTypeService = null;
	
	@Resource(name = "accPaperMainService")
	private final AccPaperMainService accPaperMainService = null;

	/**
	 * 票据类型<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapertype/accPaperTypeMainPage", method = RequestMethod.GET)
	public String accPaperTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapertype/accPaperTypeMain";

	}
	
	
	/**
	 * 票据类型<BR>
	 *添加页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapertype/accPaperTypeAddPage", method = RequestMethod.GET)
	public String accPaperTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapertype/accPaperTypeAdd";

	}

	/**
	 * <BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapertype/addAccPaperType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccPaperType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));

		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("type_name").toString()));

		String addAccPaperTypeJson = accPaperTypeService.addAccPaperType(mapVo);

		return JSONObject.parseObject(addAccPaperTypeJson);

	}

	
	/**
	 * 修改页面<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapertype/updateAccPaperTypePage", method = RequestMethod.GET)
	public String updateAccPaperTypePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		Map<String, Object> paperTypeMap = accPaperTypeService.queryAccPaperTypeByCode(mapVo);

		mode.addAttribute("group_id", paperTypeMap.get("group_id"));
		mode.addAttribute("hos_id", paperTypeMap.get("hos_id"));
		mode.addAttribute("copy_code", paperTypeMap.get("copy_code"));
		mode.addAttribute("type_code", paperTypeMap.get("type_code"));
		mode.addAttribute("type_name", paperTypeMap.get("type_name"));
		mode.addAttribute("paper_prefix", paperTypeMap.get("paper_prefix"));
		mode.addAttribute("paper_clen", paperTypeMap.get("paper_clen"));
		mode.addAttribute("paper_zlen", paperTypeMap.get("paper_zlen"));
		mode.addAttribute("paper_way_type", paperTypeMap.get("paper_way_type"));
		mode.addAttribute("paper_way_type_name", paperTypeMap.get("paper_way_type_name"));
		mode.addAttribute("paper_use_type", paperTypeMap.get("paper_use_type"));
		mode.addAttribute("paper_use_type_name", paperTypeMap.get("paper_use_type_name"));
		mode.addAttribute("is_sd", paperTypeMap.get("is_sd"));
		mode.addAttribute("is_sd_name", paperTypeMap.get("is_sd_name"));
		mode.addAttribute("is_auto", paperTypeMap.get("is_auto"));
		mode.addAttribute("is_auto_name", paperTypeMap.get("is_auto_name"));
		mode.addAttribute("spell_code", paperTypeMap.get("spell_code"));
		mode.addAttribute("wbx_code", paperTypeMap.get("wbx_code"));
		mode.addAttribute("sort_code", paperTypeMap.get("sort_code"));
		mode.addAttribute("is_stop", paperTypeMap.get("is_stop"));
		mode.addAttribute("is_stop_name", paperTypeMap.get("is_stop_name"));
		mode.addAttribute("is_wb", paperTypeMap.get("is_wb"));
		mode.addAttribute("is_wb_name", paperTypeMap.get("is_wb_name"));
		mode.addAttribute("note", paperTypeMap.get("note"));

		return "hrp/acc/accpaper/accpapertype/accPaperTypeUpdate";

	}
	
	
	/**
	 * <BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/accpaper/accpapertype/updateAccPaperType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));

		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("type_name").toString()));

		String updateAccPaperTypeJson = accPaperTypeService.updateAccPaperType(mapVo);

		return JSONObject.parseObject(updateAccPaperTypeJson);
	}
	
	
	/**
	*<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accpaper/accpapertype/deleteBatchAccPaperType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAccPaperType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("type_code", ids[3]);
            
            mapVo.put("rownum", "rownum");
            
            Map<String, Object> existMap =  accPaperMainService.queryAccPaperMainByCode(mapVo);
            
            if(existMap!=null){
            	
            	flag = false;
            	
				break;
            }
            
            listVo.add(mapVo);
            
        }
		
	     if(flag == false){
			
			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被引用! \"}");
			
		}
		
		String deleteBatchAccPaperTypeJson = accPaperTypeService.deleteBatchAccPaperType(listVo);
	   
		return JSONObject.parseObject(deleteBatchAccPaperTypeJson);
			
	}
	
	
	/**
	 * <BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapertype/queryAccPaperType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String queryAccPaperTypeJson = accPaperTypeService.queryAccPaperType(getPage(mapVo));

		return JSONObject.parseObject(queryAccPaperTypeJson);

	}
	
}