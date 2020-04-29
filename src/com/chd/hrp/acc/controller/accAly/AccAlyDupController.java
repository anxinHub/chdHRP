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
import com.chd.hrp.acc.service.accAly.AccAlyDupService;

@Controller
public class AccAlyDupController  extends BaseController{

	@Resource(name = "accAlyDupService")
	private final AccAlyDupService accAlyDupService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accAly/dupMainPage", method = RequestMethod.GET)
	public String dupMainPage(Model mode) throws Exception {
		return "hrp/acc/accaly/dupMain";
	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accAly/dupAddPage", method = RequestMethod.GET)
	public String dupAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/accaly/dupAdd";

	}
	
	@RequestMapping(value = "/hrp/acc/accAly/addAccAlyDup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccAlyDup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String result=null;
		String dup_code=mapVo.get("dup_code").toString();
		if(dup_code==null||dup_code.length()%2!=0){
			result= "{\"error\":\"无效的编码格式，请遵循2-2-2-2原则.\"}";
		}else{
			if(dup_code.length()==2){
				mapVo.put("super_code", "0");
			}else{
				mapVo.put("super_code", dup_code.substring(0, dup_code.length()-2));
			}
			mapVo.put("is_last", 1);
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
	    	mapVo.put("copy_code", SessionManager.getCopyCode());    
	    	result = accAlyDupService.add(mapVo);
		}
		return JSONObject.parseObject(result);
		
	}
	@RequestMapping(value = "/hrp/acc/accAly/dupUpdatePage", method = RequestMethod.GET)
	public String dupUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> dupMap= accAlyDupService.queryByUniqueness(mapVo);		
		mode.addAttribute("dupMap", dupMap);		
		return "hrp/acc/accaly/dupUpdate";
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accAly/updateAccAlyDup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccAlyDup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgPreExecuteJson =accAlyDupService.update(mapVo);
		
		return JSONObject.parseObject(budgPreExecuteJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accAly/deleteAccAlyDup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccAlyDup(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			Map<String, Object> mapVo=null;
			for ( String id: paramVo.split(",")) {				
				mapVo=new HashMap<String, Object>();				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("dup_code", id);				
				listVo.add(mapVo);
			}
	    
			String result =accAlyDupService.deleteBatch(listVo);		
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
	@RequestMapping(value = "/hrp/acc/accAly/queryAccAlyDup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccAlyDup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String result = accAlyDupService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
		
	}
	
	//报表制作主页面-弹出设置单元格页面
		@RequestMapping(value = "/hrp/acc/accAly/cellSetPage", method = RequestMethod.GET)
		public String cellSetPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			mode.addAttribute("mod_code", mapVo.get("mod_code"));
			return "hrp/acc/accaly/cellSet";
		}
}
