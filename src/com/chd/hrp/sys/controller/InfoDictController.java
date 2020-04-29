/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.util.*;
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
import com.chd.hrp.sys.entity.InfoDict;
import com.chd.hrp.sys.serviceImpl.InfoDictServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class InfoDictController extends BaseController{
	private static Logger logger = Logger.getLogger(InfoDictController.class);
	
	
	@Resource(name = "infoDictService")
	private final InfoDictServiceImpl infoDictService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/infodict/infoDictMainPage", method = RequestMethod.GET)
	public String infoDictMainPage(Model mode) throws Exception {

		return "hrp/sys/infodict/infoDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/infodict/infoDictAddPage", method = RequestMethod.GET)
	public String infoDictAddPage(Model mode) throws Exception {

		return "hrp/sys/infodict/infoDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/infodict/addInfoDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String infoDictJson = infoDictService.addInfoDict(mapVo);

		return JSONObject.parseObject(infoDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/infodict/queryInfoDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		String infoDict = infoDictService.queryInfoDict(getPage(mapVo));

		return JSONObject.parseObject(infoDict);
		
	}
	
	// 查询
	@RequestMapping(value = "/hrp/sys/infodict/queryHosInfoList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosInfoList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String infoDict = infoDictService.queryHosInfoList(getPage(mapVo));

		return JSONObject.parseObject(infoDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/infodict/deleteInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteInfoDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String infoDictJson = infoDictService.deleteBatchInfoDict(listVo);
	   return JSONObject.parseObject(infoDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/infodict/infoDictUpdatePage", method = RequestMethod.GET)
	
	public String infoDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        InfoDict infoDict = new InfoDict();
		infoDict = infoDictService.queryInfoDictByCode(mapVo);
		mode.addAttribute("hos_no", infoDict.getHos_no());
		mode.addAttribute("group_id", infoDict.getGroup_id());
		mode.addAttribute("hos_id", infoDict.getHos_id());
		mode.addAttribute("hos_code", infoDict.getHos_code());
		mode.addAttribute("hos_name", infoDict.getHos_name());
		mode.addAttribute("hos_simple", infoDict.getHos_simple());
		mode.addAttribute("user_code", infoDict.getUser_code());
		mode.addAttribute("create_date", infoDict.getCreate_date());
		mode.addAttribute("note", infoDict.getNote());
		mode.addAttribute("is_stop", infoDict.getIs_stop());
		
		return "hrp/sys/infodict/infoDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/infodict/updateInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String infoDictJson = infoDictService.updateInfoDict(mapVo);
		
		return JSONObject.parseObject(infoDictJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/infodict/importInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String infoDictJson = infoDictService.importInfoDict(mapVo);
		
		return JSONObject.parseObject(infoDictJson);
	}

}

