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
import com.chd.hrp.sys.entity.CusDict;
import com.chd.hrp.sys.serviceImpl.CusDictServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CusDictController extends BaseController{
	private static Logger logger = Logger.getLogger(CusDictController.class);
	
	
	@Resource(name = "cusDictService")
	private final CusDictServiceImpl cusDictService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/cusdict/cusDictMainPage", method = RequestMethod.GET)
	public String cusDictMainPage(Model mode) throws Exception {

		return "hrp/sys/cusdict/cusDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/cusdict/cusDictAddPage", method = RequestMethod.GET)
	public String cusDictAddPage(Model mode) throws Exception {

		return "hrp/sys/cusdict/cusDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/cusdict/addCusDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCusDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String cusDictJson = cusDictService.addCusDict(mapVo);

		return JSONObject.parseObject(cusDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/cusdict/queryCusDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCusDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String cusDict = cusDictService.queryCusDict(getPage(mapVo));

		return JSONObject.parseObject(cusDict);
	}
		
	/**
	 * 【客户信息-客户维护】：主查询
	 */
	@RequestMapping(value = "/hrp/sys/cusdict/queryCusDictList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCusDictList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String cusDict = cusDictService.queryCusDictList(getPage(mapVo));
		return JSONObject.parseObject(cusDict);
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/cusdict/deleteCusDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCusDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String cusDictJson = cusDictService.deleteBatchCusDict(listVo);
	   return JSONObject.parseObject(cusDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/cusdict/cusDictUpdatePage", method = RequestMethod.GET)
	
	public String cusDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        CusDict cusDict = new CusDict();
		cusDict = cusDictService.queryCusDictByCode(mapVo);
		mode.addAttribute("cus_no", cusDict.getCus_no());
		mode.addAttribute("group_id", cusDict.getGroup_id());
		mode.addAttribute("hos_id", cusDict.getHos_id());
		mode.addAttribute("cus_id", cusDict.getCus_id());
		mode.addAttribute("cus_code", cusDict.getCus_code());
		mode.addAttribute("cus_name", cusDict.getCus_name());
		mode.addAttribute("user_code", cusDict.getUser_code());
		mode.addAttribute("create_date", cusDict.getCreate_date());
		mode.addAttribute("note", cusDict.getNote());
		mode.addAttribute("is_stop", cusDict.getIs_stop());
		
		return "hrp/sys/cusdict/cusDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/cusdict/updateCusDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCusDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String cusDictJson = cusDictService.updateCusDict(mapVo);
		
		return JSONObject.parseObject(cusDictJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/cusdict/importCusDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importCusDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String cusDictJson = cusDictService.importCusDict(mapVo);
		
		return JSONObject.parseObject(cusDictJson);
	}

}

