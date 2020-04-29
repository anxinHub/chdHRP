/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
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
import com.chd.hrp.acc.entity.AccStoreAttr;
import com.chd.hrp.acc.serviceImpl.AccStoreAttrServiceImpl;

/**
* @Title. @Description.
* 库房字典属性表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccStoreAttrController extends BaseController{
	private static Logger logger = Logger.getLogger(AccStoreAttrController.class);
	
	
	@Resource(name = "accStoreAttrService")
	private final AccStoreAttrServiceImpl accStoreAttrService = null;
   
   
	/**
	*库房字典属性表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accstoreattr/accStoreAttrMainPage", method = RequestMethod.GET)
	public String accStoreAttrMainPage(Model mode) throws Exception {

		return "hrp/acc/accstoreattr/accStoreAttrMain";

	}
	/**
	*库房字典属性表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accstoreattr/accStoreAttrAddPage", method = RequestMethod.GET)
	public String accStoreAttrAddPage(Model mode) throws Exception {

		return "hrp/acc/accstoreattr/accStoreAttrAdd";

	}
	/**
	*库房字典属性表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accstoreattr/addAccStoreAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccStoreAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		String accStoreAttrJson = accStoreAttrService.addAccStoreAttr(mapVo);

		return JSONObject.parseObject(accStoreAttrJson);
		
	}
	/**
	*库房字典属性表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accstoreattr/queryAccStoreAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccStoreAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		String accStoreAttr = accStoreAttrService.queryAccStoreAttr(getPage(mapVo));

		return JSONObject.parseObject(accStoreAttr);
		
	}
	/**
	*库房字典属性表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accstoreattr/deleteAccStoreAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccStoreAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accStoreAttrJson = accStoreAttrService.deleteBatchAccStoreAttr(listVo);
	   return JSONObject.parseObject(accStoreAttrJson);
			
	}
	
	/**
	*库房字典属性表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accstoreattr/accStoreAttrUpdatePage", method = RequestMethod.GET)
	
	public String accStoreAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
        AccStoreAttr accStoreAttr = new AccStoreAttr();
		accStoreAttr = accStoreAttrService.queryAccStoreAttrByCode(mapVo);
		mode.addAttribute("store_id", accStoreAttr.getStore_id());
		mode.addAttribute("group_id", accStoreAttr.getGroup_id());
		mode.addAttribute("hos_id", accStoreAttr.getHos_id());
		mode.addAttribute("dept_id", accStoreAttr.getDept_id());
		mode.addAttribute("dept_name", accStoreAttr.getDept_name());
		mode.addAttribute("is_proc", accStoreAttr.getIs_proc());
		mode.addAttribute("mobile", accStoreAttr.getMobile());
		mode.addAttribute("head_emp_id", accStoreAttr.getHead_emp_id());
		mode.addAttribute("acc_emp_id", accStoreAttr.getAcc_emp_id());
		mode.addAttribute("safe_emp_id", accStoreAttr.getSafe_emp_id());
		mode.addAttribute("proc_emp_id", accStoreAttr.getProc_emp_id());
		
		mode.addAttribute("head_emp_name", accStoreAttr.getHead_emp_name());
		mode.addAttribute("acc_emp_name", accStoreAttr.getAcc_emp_name());
		mode.addAttribute("safe_emp_name", accStoreAttr.getSafe_emp_name());
		mode.addAttribute("proc_emp_name", accStoreAttr.getProc_emp_name());
		mode.addAttribute("address", accStoreAttr.getAddress());
		mode.addAttribute("note", accStoreAttr.getNote());
		return "hrp/acc/accstoreattr/accStoreAttrUpdate";
	}
	/**
	*库房字典属性表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accstoreattr/updateAccStoreAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccStoreAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String accStoreAttrJson = accStoreAttrService.updateAccStoreAttr(mapVo);
		
		return JSONObject.parseObject(accStoreAttrJson);
	}
	/**
	*库房字典属性表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accstoreattr/importAccStoreAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccStoreAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accStoreAttrJson = accStoreAttrService.importAccStoreAttr(mapVo);
		
		return JSONObject.parseObject(accStoreAttrJson);
	}

}

