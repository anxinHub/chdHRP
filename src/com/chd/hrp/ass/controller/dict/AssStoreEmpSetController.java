/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.dict;
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
import com.chd.hrp.mat.entity.MatStoreEmp;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreEmpSetServiceImpl;
/**
 *  
 * @Description:
 * 仓库采购员对应关系设置
 * @Table:
 * MAT_STORE_EMP_MAP 
 */
 

@Controller
public class AssStoreEmpSetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssStoreEmpSetController.class);
	
	//引入Service服务
 	@Resource(name = "matStoreEmpSetService")
	private final MatStoreEmpSetServiceImpl matStoreEmpSetService = null; 
 
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
 	@RequestMapping(value = "/hrp/ass/assstoreempset/assStoreEmpSetMainPage", method = RequestMethod.GET)
	public String assStoreEmpSetMainPage(Model mode) throws Exception {

		return "hrp/ass/assstoreempset/assStoreEmpSetMain";

	} 

     /**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/ 
 	@RequestMapping(value = "/hrp/ass/assstoreempset/assStoreEmpSetAddPage", method = RequestMethod.GET)
	public String assStoreEmpSetAddPage(Model mode) throws Exception {

		return "hrp/ass/assstoreempset/assStoreEmpSetAdd";

	} 
	 
     /**
	 * @Description 
	 * 添加数据 仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
 	@RequestMapping(value = "/hrp/ass/assstoreempset/addAssStoreEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssStoreEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String matStoreEmpSetJson = matStoreEmpSetService.addMatStoreEmpSet(mapVo);

		return JSONObject.parseObject(matStoreEmpSetJson);
		
	} 
     /**
	 * @Description 
	 * 更新跳转页面 仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
 	@RequestMapping(value = "/hrp/ass/assstoreempset/assStoreEmpSetUpdatePage", method = RequestMethod.GET)
	public String assStoreEmpSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		MatStoreEmp matStoreEmp =  matStoreEmpSetService.queryMatStoreEmpSetByCode(mapVo);
		
		mode.addAttribute("group_id", matStoreEmp.getGroup_id());
		mode.addAttribute("hos_id", matStoreEmp.getHos_id());
		mode.addAttribute("copy_code", matStoreEmp.getCopy_code());
		mode.addAttribute("store_id", matStoreEmp.getStore_id());
		mode.addAttribute("store_code", matStoreEmp.getStore_code());
		mode.addAttribute("store_name", matStoreEmp.getStore_name());
		mode.addAttribute("emp_id", matStoreEmp.getEmp_id());
		mode.addAttribute("emp_code", matStoreEmp.getEmp_code());
		mode.addAttribute("emp_name", matStoreEmp.getEmp_name());
		
		return "hrp/ass/assstoreempset/assStoreEmpSetUpdate";
	} 
		
     /**
	 * @Description 
	 * 更新数据  仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
	 @RequestMapping(value = "/hrp/ass/assstoreempset/updateAssStoreEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssStoreEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
	  
		String matStoreEmpSetJson = matStoreEmpSetService.updateMatStoreEmpSet(mapVo);
		
		return JSONObject.parseObject(matStoreEmpSetJson);
	} 
	 
	
	 /**
	 * @Description 
	 * 删除数据 仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
    @RequestMapping(value = "/hrp/ass/assstoreempset/deleteAssStoreEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssStoreEmp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
 
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {		
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids = id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0]);

				mapVo.put("hos_id", ids[1]);

				mapVo.put("copy_code", ids[2]);
				
				mapVo.put("store_id", ids[3]);
				
				mapVo.put("emp_id", ids[4]);
				
				listVo.add(mapVo);
			}
			
		String matStoreEmpSetJson = matStoreEmpSetService.deleteBatchMatStoreEmpSet(listVo);	
		
		return JSONObject.parseObject(matStoreEmpSetJson);		
	} 
	
	  /**
	 * @Description 
	 * 查询数据 仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
	 @RequestMapping(value = "/hrp/ass/assstoreempset/queryStoreEmpAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreEmpAss(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
 
		String matStoreEmpSet = matStoreEmpSetService.queryMatStoreEmpSet(getPage(mapVo));

		return JSONObject.parseObject(matStoreEmpSet);
		
	} 
 
 
}
