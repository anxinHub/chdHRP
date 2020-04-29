/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.ass.controller.dict;
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
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AssStoreDictController extends BaseController{
	private static Logger logger = Logger.getLogger(AssStoreDictController.class);
	
	
	@Resource(name = "storeDictService")
	private final StoreDictServiceImpl storeDictService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/ass/storedict/assstoreDictMainPage", method = RequestMethod.GET)
	public String storeDictMainPage(Model mode) throws Exception {

		return "hrp/ass/storedict/storeDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/ass/storedict/assstoreDictAddPage", method = RequestMethod.GET)
	public String storeDictAddPage(Model mode) throws Exception {

		return "hrp/ass/storedict/storeDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/ass/storedict/assaddStoreDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String storeDictJson = storeDictService.addStoreDict(mapVo);

		return JSONObject.parseObject(storeDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/ass/storedict/assqueryStoreDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String storeDict = storeDictService.queryStoreDict(getPage(mapVo));

		return JSONObject.parseObject(storeDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/ass/storedict/assdeleteStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStoreDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String storeDictJson = storeDictService.deleteBatchStoreDict(listVo);
	   return JSONObject.parseObject(storeDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/ass/storedict/assstoreDictUpdatePage", method = RequestMethod.GET)
	
	public String storeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        StoreDict storeDict = new StoreDict();
		storeDict = storeDictService.queryStoreDictByCode(mapVo);
		mode.addAttribute("store_no", storeDict.getStore_no());
		mode.addAttribute("group_id", storeDict.getGroup_id());
		mode.addAttribute("hos_id", storeDict.getHos_id());
		mode.addAttribute("store_id", storeDict.getStore_id());
		mode.addAttribute("store_code", storeDict.getStore_code());
		mode.addAttribute("store_name", storeDict.getStore_name());
		mode.addAttribute("user_code", storeDict.getUser_code());
		mode.addAttribute("create_date", storeDict.getCreate_date());
		mode.addAttribute("note", storeDict.getNote());
		mode.addAttribute("is_stop", storeDict.getIs_stop());
		
		return "hrp/ass/storedict/storeDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/ass/storedict/assupdateStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String storeDictJson = storeDictService.updateStoreDict(mapVo);
		
		return JSONObject.parseObject(storeDictJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/ass/storedict/assimportStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String storeDictJson = storeDictService.importStoreDict(mapVo);
		
		return JSONObject.parseObject(storeDictJson);
	}

}

