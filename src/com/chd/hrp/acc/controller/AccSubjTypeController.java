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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.serviceImpl.AccSubjTypeServiceImpl;

/**
* @Title. @Description.
* 科目类别
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccSubjTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccSubjTypeController.class);
	
	
	@Resource(name = "accSubjTypeService")
	private final AccSubjTypeServiceImpl accSubjTypeService = null;
   
    
	/**
	*科目类别<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accsubjtype/accSubjTypeMainPage", method = RequestMethod.GET)
	public String accSubjTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/accsubjtype/accSubjTypeMain";

	}
	/**
	*科目类别<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accsubjtype/accSubjTypeAddPage", method = RequestMethod.GET)
	public String accSubjTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/accsubjtype/accSubjTypeAdd";

	}
	/**
	*科目类别<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accsubjtype/addAccSubjType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
				
		}
				
		if (mapVo.get("hos_id") == null) {
					
			mapVo.put("hos_id", SessionManager.getHosId());
				
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
				
		}
        
		String accSubjTypeJson = accSubjTypeService.addAccSubjType(mapVo);

		return JSONObject.parseObject(accSubjTypeJson);
		
	}
	/**
	*科目类别<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accsubjtype/queryAccSubjType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
				
		}
				
		if (mapVo.get("hos_id") == null) {
					
			mapVo.put("hos_id", SessionManager.getHosId());
				
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
				
		}
		
		String accSubjType = accSubjTypeService.queryAccSubjType(getPage(mapVo));

		return JSONObject.parseObject(accSubjType);
		
	}
	
	/**
	*科目类别<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accsubjtype/deleteAccSubjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccSubjType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String str = "";
		for ( String ids: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] id = ids.split("@");
            mapVo.put("group_id", id[0]);
            mapVo.put("hos_id", id[1]);
            mapVo.put("copy_code", id[2]);
            mapVo.put("subj_type_code", id[3]);
            int count  = accSubjTypeService.queryAccSubjTypeReferenced(mapVo);
            if(count > 0 ){
            	str += id[3]+"," ;
            }
            
            listVo.add(mapVo);
        }
		String accSubjTypeJson = "";
		if( str != "" ){
			accSubjTypeJson = "{\"error\":\"【科目类别编码:"+str.substring(0, str.length()-1)+"】已被引用,无法删除.\"}";
		}else{
			accSubjTypeJson = accSubjTypeService.deleteBatchAccSubjType(listVo);
		}
		 
	   return JSONObject.parseObject(accSubjTypeJson);
			
	}
	
	/**
	*科目类别<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsubjtype/accSubjTypeUpdatePage", method = RequestMethod.GET)
	
	public String accSubjTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccSubjType accSubjType = new AccSubjType();
		accSubjType = accSubjTypeService.queryAccSubjTypeByCode(mapVo);
		mode.addAttribute("subj_type_code", accSubjType.getSubj_type_code());
		mode.addAttribute("group_id", accSubjType.getGroup_id());
		mode.addAttribute("hos_id", accSubjType.getHos_id());
		mode.addAttribute("copy_code", accSubjType.getCopy_code());
		mode.addAttribute("subj_type_name", accSubjType.getSubj_type_name());
		mode.addAttribute("kind_code", accSubjType.getKind_code());
		/*mode.addAttribute("spell_code", accSubjType.getSpell_code());
		mode.addAttribute("wbx_code", accSubjType.getWbx_code());*/
		return "hrp/acc/accsubjtype/accSubjTypeUpdate";
	}
	/**
	*科目类别<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accsubjtype/updateAccSubjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
				
		}
				
		if (mapVo.get("hos_id") == null) {
					
			mapVo.put("hos_id", SessionManager.getHosId());
				
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
				
		}
	   
		String accSubjTypeJson = accSubjTypeService.updateAccSubjType(mapVo);
		
		return JSONObject.parseObject(accSubjTypeJson);
	}
	/**
	*科目类别<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accsubjtype/importAccSubjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accSubjTypeJson = accSubjTypeService.importAccSubjType(mapVo);
		
		return JSONObject.parseObject(accSubjTypeJson);
	}

}

