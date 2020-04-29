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
import com.chd.hrp.htcg.entity.info.HtcgIcd10TypeDict;
import com.chd.hrp.htcg.service.info.HtcgIcd10TypeDictService;

@Controller
public class HtcgIcd10TypeDictController extends BaseController {
	
	public static Logger logger = Logger.getLogger(HtcgIcd10TypeDictController.class);
	
	@Resource(name = "htcgIcd10TypeDictService")
	private final HtcgIcd10TypeDictService htcgIcd10TypeDictService = null;
	
	@RequestMapping(value ="/hrp/htcg/info/icd10TypeDict/htcgIcd10TypeDictMainPage",method=RequestMethod.GET)
	public String htcgIcd10TypeDictMainPage(Model model)throws Exception{
		return  "hrp/htcg/info/icd10TypeDict/htcgIcd10TypeDictMain";
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htcg/info/icd10TypeDict/htcgIcd10TypeDictAddPage", method = RequestMethod.GET)
	public String htcgIcd10TypeDictAddPage(Model mode) throws Exception {

		return "hrp/htcg/info/icd10TypeDict/htcgIcd10TypeDictAdd";

	}
	
	
	@RequestMapping(value = "/hrp/htcg/info/icd10TypeDict/addHtcgIcd10TypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgIcd10TypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String htcgIcd10TypeDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("icd10_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("icd10_type_name").toString()));
		try {
		
			 htcgIcd10TypeDictJson = htcgIcd10TypeDictService.addHtcgIcd10TypeDict(mapVo);
			 
		} catch (Exception e) {
			// TODO: handle exception
			htcgIcd10TypeDictJson = e.getMessage();
		}
		 
		return JSONObject.parseObject(htcgIcd10TypeDictJson);	
	}
	
	@RequestMapping(value="/hrp/htcg/info/icd10TypeDict/queryHtcgIcd10TypeDict",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryHtcgIcd10TypeDict(@RequestParam Map<String,Object> mapVo,Model model) throws Exception{

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String result =htcgIcd10TypeDictService.queryHtcgIcd10TypeDict(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	

	
	
	//删除
	@RequestMapping(value = "/hrp/htcg/info/icd10TypeDict/deleteHtcgIcd10TypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgIcd10TypeDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String htcgIcd10TypeDictJson = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("icd10_type_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		
		try {
			
			htcgIcd10TypeDictJson = htcgIcd10TypeDictService.deleteBatchHtcgIcd10TypeDict(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			htcgIcd10TypeDictJson = e.getMessage();
		}

	      return JSONObject.parseObject(htcgIcd10TypeDictJson);		
	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/info/icd10TypeDict/htcgIcd10TypeDictUpdatePage", method = RequestMethod.GET)
	
	public String htcgIcd10TypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {
		
        HtcgIcd10TypeDict icd10TypeDict=htcgIcd10TypeDictService.queryHtcgIcd10TypeDictDicByCode(mapVo);
	    model.addAttribute("group_id",icd10TypeDict.getGroup_id());
	    model.addAttribute("hos_id",icd10TypeDict.getHos_id());
	    model.addAttribute("copy_code",icd10TypeDict.getCopy_code());
        model.addAttribute("icd10_nature_code",icd10TypeDict.getIcd10_nature_code());
        model.addAttribute("icd10_nature_name",icd10TypeDict.getIcd10_nature_name());
        model.addAttribute("icd10_type_code",icd10TypeDict.getIcd10_type_code());
        model.addAttribute("icd10_type_name",icd10TypeDict.getIcd10_type_name());
        model.addAttribute("spell_code",icd10TypeDict.getSpell_code());
        model.addAttribute("wbx_code",icd10TypeDict.getWbx_code());
        model.addAttribute("is_stop",icd10TypeDict.getIs_stop());
		return "hrp/htcg/info/icd10TypeDict/htcgIcd10TypeDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/info/icd10TypeDict/updateHtcgIcd10TypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgIcd10TypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcgIcd10TypeDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("icd10_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("icd10_type_name").toString()));
		
		try {
			
			htcgIcd10TypeDictJson = htcgIcd10TypeDictService.updateHtcgIcd10TypeDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			htcgIcd10TypeDictJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcgIcd10TypeDictJson);
	}
	
	@RequestMapping(value = "/hrp/htcg/info/icd10TypeDict/importHtcgIcd10TypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgIcd10TypeDict(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String htcgIcd10TypeDictJson ="";
		
		try {

			 htcgIcd10TypeDictJson = htcgIcd10TypeDictService.impIcd10TypeDictReadFiles(mapVo);

		}
		catch (Exception e) {

			htcgIcd10TypeDictJson = e.getMessage();
		}
		return JSONObject.parseObject(htcgIcd10TypeDictJson);
	}	 
}
