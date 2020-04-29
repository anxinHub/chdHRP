package com.chd.hrp.htcg.controller.info;
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
import com.chd.hrp.htcg.entity.info.HtcgDrugTypeDict;
import com.chd.hrp.htcg.service.info.HtcgDrugTypeDictService;

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
public class HtcgDrugTypeDictController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgDrugTypeDictController.class);
	
	
	@Resource(name = "htcgDrugTypeDictService")
	private final HtcgDrugTypeDictService htcgDrugTypeDictService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugtypedict/htcgDrugTypeDictMainPage", method = RequestMethod.GET)
	public String htcgDrugTypeDictMainPage(Model mode) throws Exception {

		return "hrp/htcg/info/htcgdrugtypedict/htcgDrugTypeDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugtypedict/htcgDrugTypeDictAddPage", method = RequestMethod.GET)
	public String htcgDrugTypeDictAddPage(Model mode) throws Exception {

		return "hrp/htcg/info/htcgdrugtypedict/htcgDrugTypeDictAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugtypedict/addHtcgDrugTypeDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHtcgDrugTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drug_type_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drug_type_name").toString()));
        
		String htcgDrugTypeDictJson;
		try {
			
			htcgDrugTypeDictJson = htcgDrugTypeDictService.addHtcgDrugTypeDict(mapVo);
			
		} catch (Exception e) {
			
			htcgDrugTypeDictJson=e.getMessage();
		}
		
		return JSONObject.parseObject(htcgDrugTypeDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugtypedict/queryHtcgDrugTypeDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgDrugTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcgDrugTypeDict = htcgDrugTypeDictService.queryHtcgDrugTypeDict(getPage(mapVo));

		return JSONObject.parseObject(htcgDrugTypeDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugtypedict/deleteHtcgDrugTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrugTypeDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("drug_type_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String htcgDrugTypeDictJson;
		try {
			htcgDrugTypeDictJson = htcgDrugTypeDictService.deleteBatchHtcgDrugTypeDict(listVo);
		} catch (Exception e) {
			htcgDrugTypeDictJson=e.getMessage();
		}

	   return JSONObject.parseObject(htcgDrugTypeDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugtypedict/htcgDrugTypeDictUpdatePage", method = RequestMethod.GET)
	
	public String htcgDrugTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

        HtcgDrugTypeDict htcgDrugTypeDict = htcgDrugTypeDictService.queryHtcgDrugTypeDictByCode(mapVo);
		mode.addAttribute("group_id", htcgDrugTypeDict.getGroup_id());
		mode.addAttribute("hos_id", htcgDrugTypeDict.getHos_id());
		mode.addAttribute("copy_code", htcgDrugTypeDict.getCopy_code());
		mode.addAttribute("drug_type_code", htcgDrugTypeDict.getDrug_type_code());
		mode.addAttribute("drug_type_name", htcgDrugTypeDict.getDrug_type_name());
		mode.addAttribute("spell_code", htcgDrugTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", htcgDrugTypeDict.getWbx_code());
		mode.addAttribute("is_stop", htcgDrugTypeDict.getIs_stop());
		
		return "hrp/htcg/info/htcgdrugtypedict/htcgDrugTypeDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugtypedict/updateHtcgDrugTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgDrugTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drug_type_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drug_type_name").toString()));
        
		String htcgDrugTypeDictJson;
		try {
			htcgDrugTypeDictJson = htcgDrugTypeDictService.updateHtcgDrugTypeDict(mapVo);
		} catch (Exception e) {
			htcgDrugTypeDictJson=e.getMessage();
		}
		
		return JSONObject.parseObject(htcgDrugTypeDictJson);
	}
	
	/**
	 * 导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugtypedict/importHtcgDrugTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgRecipeType(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		String  htcgDrugTypeDictJson = "";
		
		try {
			
			htcgDrugTypeDictJson = htcgDrugTypeDictService.importHtcgDrugTypeDict(mapVo);
		}
		catch (Exception e) {
			htcgDrugTypeDictJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcgDrugTypeDictJson);

	}

}

