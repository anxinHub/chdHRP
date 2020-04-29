/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
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
import com.chd.hrp.med.entity.MedStoreEmp;
import com.chd.hrp.med.entity.MedStoreSet;
import com.chd.hrp.med.serviceImpl.info.basic.MedStoreEmpSetServiceImpl;
/**
 *  
 * @Description:
 * 仓库采购员对应关系设置
 * @Table:
 * MED_STORE_EMP_MAP 
 */
 

@Controller
public class MedStoreEmpSetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedStoreEmpSetController.class);
	
	//引入Service服务
 	@Resource(name = "medStoreEmpSetService")
	private final MedStoreEmpSetServiceImpl medStoreEmpSetService = null; 
 
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
 	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreempset/medStoreEmpSetMainPage", method = RequestMethod.GET)
	public String medStoreEmpSetMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/storeset/medstoreempset/medStoreEmpSetMain";

	} 

     /**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/ 
 	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreempset/medStoreEmpSetAddPage", method = RequestMethod.GET)
	public String medStoreEmpSetAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/storeset/medstoreempset/medStoreEmpSetAdd";

	} 
	 
     /**
	 * @Description 
	 * 添加数据 仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
 	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreempset/addMedStoreEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String medStoreEmpSetJson = medStoreEmpSetService.addMedStoreEmpSet(mapVo);

		return JSONObject.parseObject(medStoreEmpSetJson);
		
	} 
     /**
	 * @Description 
	 * 更新跳转页面 仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
 	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreempset/medStoreEmpSetUpdatePage", method = RequestMethod.GET)
	public String medStoreEmpSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		MedStoreEmp medStoreEmp =  medStoreEmpSetService.queryMedStoreEmpSetByCode(mapVo);
		
		mode.addAttribute("group_id", medStoreEmp.getGroup_id());
		mode.addAttribute("hos_id", medStoreEmp.getHos_id());
		mode.addAttribute("copy_code", medStoreEmp.getCopy_code());
		mode.addAttribute("store_id", medStoreEmp.getStore_id());
		mode.addAttribute("store_code", medStoreEmp.getStore_code());
		mode.addAttribute("store_name", medStoreEmp.getStore_name());
		mode.addAttribute("emp_id", medStoreEmp.getEmp_id());
		mode.addAttribute("emp_code", medStoreEmp.getEmp_code());
		mode.addAttribute("emp_name", medStoreEmp.getEmp_name());
		
		return "hrp/med/info/basic/storeset/medstoreempset/medStoreEmpSetUpdate";
	} 
		
     /**
	 * @Description 
	 * 更新数据  仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
	 @RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreempset/updateMedStoreEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStoreEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
	  
		String medStoreEmpSetJson = medStoreEmpSetService.updateMedStoreEmpSet(mapVo);
		
		return JSONObject.parseObject(medStoreEmpSetJson);
	} 
	 
	
	 /**
	 * @Description 
	 * 删除数据 仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
    @RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreempset/deleteMedStoreEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedStoreEmp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
 
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
			
		String medStoreEmpSetJson = medStoreEmpSetService.deleteBatchMedStoreEmpSet(listVo);	
		
		return JSONObject.parseObject(medStoreEmpSetJson);		
	} 
	
	  /**
	 * @Description 
	 * 查询数据 仓库采购员对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/ 
	 @RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreempset/queryStoreEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
 
		String medStoreEmpSet = medStoreEmpSetService.queryMedStoreEmpSet(mapVo);

		return JSONObject.parseObject(medStoreEmpSet);
		
	} 
 
 
}

