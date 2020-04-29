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
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.serviceImpl.AccDeptAttrServiceImpl;

/**
* @Title. @Description.
* 部门字典属性表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccDeptAttrController extends BaseController{
	private static Logger logger = Logger.getLogger(AccDeptAttrController.class);
	
	
	@Resource(name = "accDeptAttrService")
	private final AccDeptAttrServiceImpl accDeptAttrService = null;
   
    
	/**
	*部门字典属性表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accdeptattr/accDeptAttrMainPage", method = RequestMethod.GET)
	public String accDeptAttrMainPage(Model mode) throws Exception {

		return "hrp/acc/accdeptattr/accDeptAttrMain";

	}
	/**
	*部门字典属性表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accdeptattr/accDeptAttrAddPage", method = RequestMethod.GET)
	public String accDeptAttrAddPage(Model mode) throws Exception {

		return "hrp/acc/accdeptattr/accDeptAttrAdd";

	}
	/**
	*部门字典属性表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accdeptattr/addAccDeptAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccDeptAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accDeptAttrJson = accDeptAttrService.addAccDeptAttr(mapVo);

		return JSONObject.parseObject(accDeptAttrJson);
		
	}
	/**
	*部门字典属性表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accdeptattr/queryAccDeptAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccDeptAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accDeptAttr = accDeptAttrService.queryAccDeptAttr(getPage(mapVo));

		return JSONObject.parseObject(accDeptAttr);
		
	}
	/**
	*部门字典属性表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accdeptattr/deleteAccDeptAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccDeptAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accDeptAttrJson = accDeptAttrService.deleteBatchAccDeptAttr(listVo);
	   return JSONObject.parseObject(accDeptAttrJson);
			
	}
	
	/**
	*部门字典属性表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accdeptattr/accDeptAttrUpdatePage", method = RequestMethod.GET)
	
	public String accDeptAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccDeptAttr accDeptAttr = new AccDeptAttr();
		accDeptAttr = accDeptAttrService.queryAccDeptAttrByCode(mapVo);
		mode.addAttribute("dept_no", accDeptAttr.getDept_no());
		mode.addAttribute("group_id", accDeptAttr.getGroup_id());
		mode.addAttribute("hos_id", accDeptAttr.getHos_id());
		mode.addAttribute("dept_id", accDeptAttr.getDept_id());
		mode.addAttribute("type_code", accDeptAttr.getType_code());
		mode.addAttribute("natur_code", accDeptAttr.getNatur_code());
		mode.addAttribute("out_code", accDeptAttr.getOut_code());
		mode.addAttribute("emp_id", accDeptAttr.getEmp_id());
		mode.addAttribute("is_manager", accDeptAttr.getIs_manager());
		mode.addAttribute("is_stock", accDeptAttr.getIs_stock());
		return "hrp/acc/accdeptattr/accDeptAttrUpdate";
	}
	/**
	*部门字典属性表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accdeptattr/updateAccDeptAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccDeptAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
		String accDeptAttrJson = accDeptAttrService.updateAccDeptAttr(mapVo);
		
		return JSONObject.parseObject(accDeptAttrJson);
	}
	/**
	*部门字典属性表<BR>
	*批量修改
	*/
	@RequestMapping(value = "/hrp/acc/accdeptattr/batchUpdateAccDeptAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchUpdateAccDeptAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String [] dept_ids = mapVo.get("dept_id").toString().split(",");
		
		for (String dept_id : dept_ids) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("dept_id", dept_id.split("@")[0]);
			
			map.put("group_id", SessionManager.getGroupId());
			
			map.put("hos_id", SessionManager.getHosId());
			
			if(!Strings.isEmpty(String.valueOf(mapVo.get("type_code")))){
				map.put("type_code", String.valueOf(mapVo.get("type_code")));
			}else{
				map.put("type_code", "");
			}
			
			if(!Strings.isEmpty(String.valueOf(mapVo.get("natur_code")))){
				map.put("natur_code", String.valueOf(mapVo.get("natur_code")));
			}else{
				map.put("natur_code", "");
			}
			
			if(!Strings.isEmpty(String.valueOf(mapVo.get("out_code")))){
				map.put("out_code", String.valueOf(mapVo.get("out_code")));
			}else{
				map.put("out_code", "");
			}
			
			if(!Strings.isEmpty(String.valueOf(mapVo.get("emp_id")))){
				map.put("emp_id", String.valueOf(mapVo.get("emp_id")));
			}else{
				map.put("emp_id", "");
			}
			
			map.put("is_manager", mapVo.get("is_manager").toString());
			
			map.put("is_stock", mapVo.get("is_stock").toString());
			
			listVo.add(map);
			
		}
	   
		String accDeptAttrJson = accDeptAttrService.updateBatchAccDeptAttr(listVo);
		
		return JSONObject.parseObject(accDeptAttrJson);
	}
	/**
	*部门字典属性表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accdeptattr/importAccDeptAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccDeptAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accDeptAttrJson = accDeptAttrService.importAccDeptAttr(mapVo);
		
		return JSONObject.parseObject(accDeptAttrJson);
	}

}

