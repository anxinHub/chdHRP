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
import com.chd.hrp.htcg.entity.info.HtcgAnestTypeDict;
import com.chd.hrp.htcg.service.info.HtcgAnestTypeDictService;

@Controller
public class HtcgAnestTypeDictController extends BaseController {
	
	public static Logger logger = Logger.getLogger(HtcgAnestTypeDictController.class);
	
	@Resource(name = "htcgAnestTypeDictService")
	private final HtcgAnestTypeDictService htcgAnestTypeDictService = null;
	
	@RequestMapping(value ="/hrp/htcg/info/anestTypeDict/htcgAnestTypeDictMainPage",method=RequestMethod.GET)
	public String htcgAnestTypeDictMainPage(Model model)throws Exception{
		return  "hrp/htcg/info/anestTypeDict/htcgAnestTypeDictMain";
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htcg/info/anestTypeDict/htcgAnestTypeDictAddPage", method = RequestMethod.GET)
	public String htcgAnestTypeDictAddPage(Model mode) throws Exception {

		return "hrp/htcg/info/anestTypeDict/htcgAnestTypeDictAdd";

	}
	
	@RequestMapping(value = "/hrp/htcg/info/anestTypeDict/addHtcgAnestTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgAnestTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
     
		String htcgAnestTypeDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("anest_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("anest_type_name").toString()));

		try {
			  htcgAnestTypeDictJson = htcgAnestTypeDictService.addHtcgAnestTypeDict(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcgAnestTypeDictJson = e.getMessage();
		}
	   
		return JSONObject.parseObject(htcgAnestTypeDictJson);	
	}
	
	
	@RequestMapping(value="/hrp/htcg/info/anestTypeDict/queryHtcgAnestTypeDict",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryHtcgAnestTypeDict(@RequestParam Map<String,Object> mapVo,Model model) throws Exception{

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String htcgAnestTypeDictJson =htcgAnestTypeDictService.queryHtcgAnestTypeDict(getPage(mapVo));
        
		return JSONObject.parseObject(htcgAnestTypeDictJson);
	}
	
	
	//删除
	@RequestMapping(value = "/hrp/htcg/info/anestTypeDict/deleteHtcgAnestTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgAnestTypeDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String htcgAnestTypeDictJson = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id",ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code",ids[2]);
            mapVo.put("anest_type_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		
		try {
			
			htcgAnestTypeDictJson = htcgAnestTypeDictService.deleteBatchHtcgAnestTypeDict(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			htcgAnestTypeDictJson = e.getMessage();
		}

	   return JSONObject.parseObject(htcgAnestTypeDictJson);		
	}
		
   // 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/info/anestTypeDict/htcgAnestTypeDictUpdatePage", method = RequestMethod.GET)
	
	public String htcgAnestTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

        HtcgAnestTypeDict htcganestTypeDict = htcgAnestTypeDictService.queryHtcgAnestTypeDictByCode(mapVo);
		mode.addAttribute("group_id", htcganestTypeDict.getGroup_id());
		mode.addAttribute("hos_id", htcganestTypeDict.getHos_id());
		mode.addAttribute("copy_code", htcganestTypeDict.getCopy_code());
		mode.addAttribute("anest_type_code", htcganestTypeDict.getAnest_type_code());
		mode.addAttribute("anest_type_name", htcganestTypeDict.getAnest_type_name());
		mode.addAttribute("spell_code", htcganestTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", htcganestTypeDict.getWbx_code());
		mode.addAttribute("is_stop", htcganestTypeDict.getIs_stop());
		return "hrp/htcg/info/anestTypeDict/htcgAnestTypeDictUpdate";
	}
			
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/info/anestTypeDict/updateHtcgAnestTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgAnestTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcgAnestTypeDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("anest_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("anest_type_name").toString()));
		
		try {
			
			htcgAnestTypeDictJson = htcgAnestTypeDictService.updateHtcgAnestTypeDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			htcgAnestTypeDictJson = e.getMessage();
	
		}

		  return JSONObject.parseObject(htcgAnestTypeDictJson);
	}
		
	@RequestMapping(value = "/hrp/htcg/info/anestTypeDict/importHtcgAnestTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgAnestTypeDict(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		String  htcgAnestTypeDictJson = "";
		try {

			htcgAnestTypeDictJson = htcgAnestTypeDictService.importHtcgAnestTypeDict(mapVo);

		}
		catch (Exception e) {

			htcgAnestTypeDictJson = e.getMessage();

		}
		 return JSONObject.parseObject(htcgAnestTypeDictJson);
	}
		 
}
