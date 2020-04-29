package com.chd.hrp.acc.controller.accAly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.accAly.AccAlyFacService;

@Controller
public class AccAlyFacController  extends BaseController{

	@Resource(name = "accAlyFacService")
	private final AccAlyFacService accAlyFacService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accAly/facMainPage", method = RequestMethod.GET)
	public String facMainPage(Model mode) throws Exception {
		return "hrp/acc/accaly/facMain";
	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accAly/facAddPage", method = RequestMethod.GET)
	public String facAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/accaly/facAdd";

	}
	
	@RequestMapping(value = "/hrp/acc/accAly/addAccAlyFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccAlyFac(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String result=null;
		String fac_code=mapVo.get("fac_code").toString();
		if(fac_code==null||fac_code.length()%2!=0){
			result= "{\"error\":\"无效的编码格式，请遵循2-2-2-2原则.\"}";
		}else{
			if(fac_code.length()==2){
				mapVo.put("super_code", "0");
			}else{
				mapVo.put("super_code", fac_code.substring(0, fac_code.length()-2));
			}
			mapVo.put("is_last", 1);
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
	    	mapVo.put("copy_code", SessionManager.getCopyCode());    
	    	result = accAlyFacService.add(mapVo);
		}
		return JSONObject.parseObject(result);
		
	}
	@RequestMapping(value = "/hrp/acc/accAly/facUpdatePage", method = RequestMethod.GET)
	public String dupUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> facMap= accAlyFacService.queryByUniqueness(mapVo);		
		mode.addAttribute("facMap", facMap);		
		return "hrp/acc/accaly/facUpdate";
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accAly/updateAccAlyFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccAlyFac(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String result =accAlyFacService.update(mapVo);
		
		return JSONObject.parseObject(result);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accAly/deleteAccAlyFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccAlyFac(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			Map<String, Object> mapVo=null;
			for ( String id: paramVo.split(",")) {				
				mapVo=new HashMap<String, Object>();				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("fac_code", id);				
				listVo.add(mapVo);
			}
	    
			String result =accAlyFacService.deleteBatch(listVo);		
			return JSONObject.parseObject(result);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accAly/queryAccAlyFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccAlyFac(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String result = accAlyFacService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
		
	}
}
