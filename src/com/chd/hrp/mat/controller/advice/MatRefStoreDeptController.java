package com.chd.hrp.mat.controller.advice;

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
import com.chd.hrp.mat.service.advice.MatRefStoreDeptService;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreServiceImpl;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;

@Controller
public class MatRefStoreDeptController extends BaseController{
	private static Logger logger = Logger.getLogger(MatRefStoreDeptController.class);
	
	@Resource(name = "matRefStoreDeptService")
	private final MatRefStoreDeptService matRefStoreDeptService = null;
	
	@Resource(name = "matStoreService")
	private final MatStoreServiceImpl matStoreService = null;
	
	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;
	
	
	//主页面跳转
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/matRefStoreDeptMainPage", method = RequestMethod.GET)
	public String matRefStoreDeptMainPage(Model mode) throws Exception {

		return "hrp/mat/advice/writeoffsetting/matRefStoreDeptMain";
	}
	
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/matRefStoreDeptSetPage", method = RequestMethod.GET)
	public String matRefStoreDeptSetPage(Model mode,String store_id) throws Exception {
		mode.addAttribute("store_id", store_id);
		return "hrp/mat/advice/writeoffsetting/matRefStoreDeptSet";
	}
	
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/queryMatStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String matStore = matStoreService.query(getPage(mapVo));

		return JSONObject.parseObject(matStore);
	}
	
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String deptDict = matRefStoreDeptService.queryMatRefStoreDeptByStore(getPage(mapVo));

		return JSONObject.parseObject(deptDict);
		
	}
	
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/queryDeptDictSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryDeptDictSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String deptDict = deptDictService.queryDeptDict(getPage(mapVo));

		return JSONObject.parseObject(deptDict);
		
	}
	
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/addMatRefStoreDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatRefStoreDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		
		String matRefStoreDeptJson = matRefStoreDeptService.addBatchMatRefStoreDept(listVo);

		return JSONObject.parseObject(matRefStoreDeptJson);
	}
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/deleteMatRefStoreDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatRefStoreDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		
		String matRefStoreDeptJson = matRefStoreDeptService.deleteBatchMatRefStoreDept(listVo);

		return JSONObject.parseObject(matRefStoreDeptJson);
	}
	
}
