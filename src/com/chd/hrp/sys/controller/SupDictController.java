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
import com.chd.hrp.sys.entity.SupDict;
import com.chd.hrp.sys.serviceImpl.SupDictServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class SupDictController extends BaseController{
	private static Logger logger = Logger.getLogger(SupDictController.class);
	 
	
	@Resource(name = "supDictService")
	private final SupDictServiceImpl supDictService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/supdict/supDictMainPage", method = RequestMethod.GET)
	public String supDictMainPage(Model mode) throws Exception {

		return "hrp/sys/supdict/supDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/supdict/supDictAddPage", method = RequestMethod.GET)
	public String supDictAddPage(Model mode) throws Exception {

		return "hrp/sys/supdict/supDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/supdict/addSupDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String supDictJson = supDictService.addSupDict(mapVo);

		return JSONObject.parseObject(supDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/supdict/querySupDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> querySupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String supDict = supDictService.querySupDict(getPage(mapVo));

		return JSONObject.parseObject(supDict);
		
	}
	
	/**
	 * 【供应商信息-供应商维护】：主查询
	 */
	@RequestMapping(value = "/hrp/sys/supdict/querySupDictList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupDictList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		String supDict = supDictService.querySupDictList(getPage(mapVo));
		return JSONObject.parseObject(supDict);
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/supdict/deleteSupDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSupDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String supDictJson = supDictService.deleteBatchSupDict(listVo);
	   return JSONObject.parseObject(supDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/supdict/supDictUpdatePage", method = RequestMethod.GET)
	
	public String supDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        SupDict supDict = new SupDict();
		supDict = supDictService.querySupDictByCode(mapVo);
		mode.addAttribute("sup_no", supDict.getSup_no());
		mode.addAttribute("group_id", supDict.getGroup_id());
		mode.addAttribute("hos_id", supDict.getHos_id());
		mode.addAttribute("sup_id", supDict.getSup_id());
		mode.addAttribute("sup_code", supDict.getSup_code());
		mode.addAttribute("sup_name", supDict.getSup_name());
		mode.addAttribute("user_code", supDict.getUser_code());
		mode.addAttribute("create_date", supDict.getCreate_date());
		mode.addAttribute("note", supDict.getNote());
		mode.addAttribute("is_stop", supDict.getIs_stop());
		mode.addAttribute("is_delivery", supDict.getIs_delivery());
		
		return "hrp/sys/supdict/supDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/supdict/updateSupDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String supDictJson = supDictService.updateSupDict(mapVo);
		
		return JSONObject.parseObject(supDictJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/supdict/importSupDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String supDictJson = supDictService.importSupDict(mapVo);
		
		return JSONObject.parseObject(supDictJson);
	}

}

