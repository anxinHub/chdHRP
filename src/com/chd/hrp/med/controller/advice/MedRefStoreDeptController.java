package com.chd.hrp.med.controller.advice;

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
import com.chd.hrp.med.service.advice.MedRefStoreDeptService;
import com.chd.hrp.med.serviceImpl.info.basic.MedStoreServiceImpl;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;

@Controller
public class MedRefStoreDeptController extends BaseController{
	private static Logger logger = Logger.getLogger(MedRefStoreDeptController.class);
	
	@Resource(name = "medRefStoreDeptService")
	private final MedRefStoreDeptService medRefStoreDeptService = null;
	
	@Resource(name = "medStoreService")
	private final MedStoreServiceImpl medStoreService = null;
	
	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;
	
	
	//主页面跳转
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/medRefStoreDeptMainPage", method = RequestMethod.GET)
	public String medRefStoreDeptMainPage(Model mode) throws Exception {

		return "hrp/med/advice/writeoffsetting/medRefStoreDeptMain";
	}
	
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/medRefStoreDeptSetPage", method = RequestMethod.GET)
	public String medRefStoreDeptSetPage(Model mode,String store_id) throws Exception {
		mode.addAttribute("store_id", store_id);
		return "hrp/med/advice/writeoffsetting/medRefStoreDeptSet";
	}
	
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/queryMedStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){	
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());        
		}
		if(mapVo.get("acct_year") == null){			
			mapVo.put("acct_year", SessionManager.getAcctYear());       
		}
		if(mapVo.get("user_id") == null){			
			mapVo.put("user_id", SessionManager.getUserId());       
		}
		
		String medStore = medStoreService.query(getPage(mapVo));

		return JSONObject.parseObject(medStore);
	}
	
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String deptDict = medRefStoreDeptService.queryMedRefStoreDeptByStore(getPage(mapVo));

		return JSONObject.parseObject(deptDict);
		
	}
	
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/queryDeptDictSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryDeptDictSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String deptDict = deptDictService.queryDeptDict(getPage(mapVo));

		return JSONObject.parseObject(deptDict);
		
	}
	
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/addMedRefStoreDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedRefStoreDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String medRefStoreDeptJson = null;
		
		try {
			
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				if(mapVo.get("group_id") == null){
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if(mapVo.get("hos_id") == null){
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if(mapVo.get("copy_code") == null){
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				
				mapVo.put("dept_id", id.split("@")[0]);
				
				mapVo.put("store_id", id.split("@")[1]);
				
				listVo.add(mapVo);
			}
			
			medRefStoreDeptJson = medRefStoreDeptService.addBatchMedRefStoreDept(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			medRefStoreDeptJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
			
		}
		
		return JSONObject.parseObject(medRefStoreDeptJson);
		
	}
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/deleteMedRefStoreDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedRefStoreDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			mapVo.put("dept_id", id.split("@")[0]);
			
			mapVo.put("store_id", id.split("@")[1]);
			
			listVo.add(mapVo);
		}
		
		String medRefStoreDeptJson = medRefStoreDeptService.deleteBatchMedRefStoreDept(listVo);

		return JSONObject.parseObject(medRefStoreDeptJson);
	}
	
}
