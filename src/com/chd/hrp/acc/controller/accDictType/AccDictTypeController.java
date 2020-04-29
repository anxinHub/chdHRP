package com.chd.hrp.acc.controller.accDictType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.chd.hrp.acc.service.accDictType.AccDictTypeService;
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
import com.chd.hrp.acc.controller.AccElementAnalyzeController;
import com.chd.hrp.acc.service.AccElementAnalyzeService;
import com.chd.hrp.acc.service.accAly.AccAlyDupService;

@Controller
@RequestMapping(value="/hrp/acc/accdicttype")
public class AccDictTypeController  extends BaseController{

	private static Logger logger = Logger.getLogger(AccDictTypeController.class);
	
	@Resource(name = "accDictTypeService")
	private final AccDictTypeService accDictTypeService = null;
	
	/**
	 * @Description 
	 ** 主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/dictMainPage", method = RequestMethod.GET)
	public String dictMainPage(Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/accDictType/accDictTypeMain";
	}
	
	/**
	 * @Description 
	 ** 添加页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/dictAddPage", method = RequestMethod.GET)
	public String dictAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/accDictType/accDictTypeAdd";

	}
	
	/**
	 * @Description 
	 **修改页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/dictUpdatePage", method = RequestMethod.GET)
	public String dictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/accDictType/accDictTypeUpdate";
	}
	
	
	
	/**
	 * @Description 
	 ** 查询数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryDictType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDictTypeExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String result = null ; 
		result = accDictTypeService.queryDict(mapVo);
		return JSONObject.parseObject(result);
	}
	
	/**
	 * @Description 
	 ** 添加数据 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/addDictType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDictTypeExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String result = null ; 
		result = accDictTypeService.addDict(mapVo);
		return JSONObject.parseObject(result);
	}
	
	/**
	 * @Description 
	 ** 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteDictType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDictTypeExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {	
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Map<String, Object> mapVo=null;
		for ( String id: paramVo.split(",")) {				
			mapVo=new HashMap<String, Object>();				
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("dict_code", id);				
			listVo.add(mapVo);
		}
		
		String result = null ; 
		result = accDictTypeService.deleteBatch(listVo);
		return JSONObject.parseObject(result);
	}
	
	/**
	 * @Description 
	 **更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/updateDictType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDictTypeExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String result = null ; 
		result = accDictTypeService.updateDict(mapVo);
		return JSONObject.parseObject(result);
	}
	

}
