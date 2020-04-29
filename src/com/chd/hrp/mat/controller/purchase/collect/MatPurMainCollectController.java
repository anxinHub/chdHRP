package com.chd.hrp.mat.controller.purchase.collect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.purchase.collect.MatPurMainCollectService;

/**
 * 
 * @Description:
 * 04113 统购采购计划汇总
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatPurMainCollectController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatPurMainCollectController.class);
	
	@Resource(name = "matPurMainCollectService")
	private final MatPurMainCollectService matPurMainCollectService = null;
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//主页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/collect/matPurMainCollectMainPage", method = RequestMethod.GET)
	public String matPurMainCollectMainPage(Model mode) throws Exception {

		return "hrp/mat/purchase/collect/matPurMainCollectMain"; 

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//汇总生成采购计划采购明细页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/collect/matPurMainCollectDetailPage", method = RequestMethod.GET)
	public String matPurMainCollectDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> matPurMainMap = matPurMainCollectService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", matPurMainMap.get("group_id"));
		mode.addAttribute("hos_id", matPurMainMap.get("hos_id"));
		mode.addAttribute("copy_code",matPurMainMap.get("copy_code"));
		mode.addAttribute("pur_id", matPurMainMap.get("pur_id"));
		mode.addAttribute("pur_code", matPurMainMap.get("pur_code"));
		mode.addAttribute("dept_id", matPurMainMap.get("dept_id"));
		mode.addAttribute("dept_no",matPurMainMap.get("dept_no"));
		mode.addAttribute("dept_name", matPurMainMap.get("dept_name"));
		mode.addAttribute("make_date", matPurMainMap.get("make_date"));
		mode.addAttribute("pur_type", matPurMainMap.get("pur_type"));
		mode.addAttribute("brif", matPurMainMap.get("brif"));
		mode.addAttribute("pur_hos_id",matPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_name", matPurMainMap.get("pur_hos_name"));
		mode.addAttribute("req_hos_id", matPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_name", matPurMainMap.get("req_hos_name"));
		mode.addAttribute("pay_hos_id", matPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_name", matPurMainMap.get("pay_hos_name"));
		
		return "hrp/mat/purchase/collect/matPurMainCollectDetail";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//按采购计划汇总
	@RequestMapping(value = "/hrp/mat/purchase/collect/prodMatPurMainCollectPage", method = RequestMethod.GET)
	public String prodMatPurMainCollectPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("pur_hos_id",mapVo.get("pur_hos_id"));
		mode.addAttribute("pur_hos_name",mapVo.get("pur_hos_name"));
		
		return "hrp/mat/purchase/collect/matPurMainCollectProd";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//请购计划单明细
	@RequestMapping(value = "/hrp/mat/purchase/collect/matPurReqDetailPage", method = RequestMethod.GET)
	public String matPurReqDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> matPurMainMap = matPurMainCollectService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", matPurMainMap.get("group_id"));
		mode.addAttribute("hos_id", matPurMainMap.get("hos_id"));
		mode.addAttribute("copy_code",matPurMainMap.get("copy_code"));
		mode.addAttribute("pur_id", matPurMainMap.get("pur_id"));
		mode.addAttribute("pur_code", matPurMainMap.get("pur_code"));
		mode.addAttribute("dept_id", matPurMainMap.get("dept_id"));
		mode.addAttribute("dept_no",matPurMainMap.get("dept_no"));
		mode.addAttribute("dept_name", matPurMainMap.get("dept_name"));
		mode.addAttribute("make_date", matPurMainMap.get("make_date"));
		mode.addAttribute("pur_hos_id",matPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_name", matPurMainMap.get("pur_hos_name"));
		mode.addAttribute("req_hos_id", matPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_name", matPurMainMap.get("req_hos_name"));
		mode.addAttribute("pay_hos_id", matPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_name", matPurMainMap.get("pay_hos_name"));
		
		return "hrp/mat/purchase/collect/matPurReqDetail";

	}
	
	
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//材料当前库存明细页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/collect/matInvCurAmountDetailPage", method = RequestMethod.GET)
	public String matInvCurAmountDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		
		return "hrp/mat/purchase/collect/matInvCurAmountDetail";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//采购计划数量明细
	@RequestMapping(value = "/hrp/mat/purchase/collect/matPurMainAmountDetailPage", method = RequestMethod.GET)
	public String matPurMainAmountDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("pur_rela",mapVo.get("paraObj").toString());
		
		mode.addAttribute("rowindex", mapVo.get("rowindex").toString());
		
		JSONArray pur_rela_json = JSONArray.parseArray((String)mapVo.get("paraObj"));
		
		Iterator iterator = pur_rela_json.iterator();
		
		while (iterator.hasNext()) {
			
			JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
			
			mode.addAttribute("inv_id", jsonObj.get("inv_id"));
			
			mode.addAttribute("pur_id", jsonObj.get("pur_id"));
			
			mode.addAttribute("total_id", jsonObj.get("total_id"));
			
		}
		
		return "hrp/mat/purchase/collect/matPurMainAmountDetail";

	}	
	
	/**
	 * @Description 
	 * 统购采购计划汇总 查询采购计划
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/collect/queryMatPurMainCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		String matPurMainCollectJson = matPurMainCollectService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matPurMainCollectJson);

	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总 按主表ID查询采购明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/collect/queryMatPurDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatPurDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurMainCollectJson = matPurMainCollectService.queryMatPurDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matPurMainCollectJson);

	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总 查看材料当前库存明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/collect/queryMatInvCurAmountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatInvCurAmountDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurMainCollectJson = matPurMainCollectService.queryMatInvCurAmountDetail(mapVo);
		
		return JSONObject.parseObject(matPurMainCollectJson);

	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划汇总
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@RequestMapping(value = "/hrp/mat/purchase/collect/queryMatPurMainByCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatPurMainByCollect(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurMainCollectJson = matPurMainCollectService.queryMatPurMainByCollect(mapVo);
		
		return JSONObject.parseObject(matPurMainCollectJson);

	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>汇总生成采购计划
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@RequestMapping(value = "/hrp/mat/purchase/collect/collectMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> collectMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String paramVo = mapVo.get("Param").toString();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> map=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				map.put("group_id", ids[0])   ;
				map.put("hos_id", ids[1])   ;
				map.put("copy_code", ids[2])   ;
				map.put("pur_id", ids[3]);
				map.put("pur_hos_id", ids[4]);
				map.put("req_hos_id", ids[5]);
				map.put("pay_hos_id", ids[6]);
				
	      listVo.add(map);
	    }
		
		String matPurMainCollectJson = matPurMainCollectService.genByCollectMatPurMain(listVo);
		
		return JSONObject.parseObject(matPurMainCollectJson);
	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总 汇总生成采购计划明细页面 更新
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/collect/addOrUpdateMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOrUpdateMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurDetailJson = matPurMainCollectService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(matPurDetailJson);

	}
	
	
	/**
	 * @Description 
	 * 统购采购计划汇总 采购计划数量明细页面 查询
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/collect/queryMatPurMainAmountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatPurMainAmountDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurMainAmountJson = matPurMainCollectService.queryMatPurMainAmountDetail(mapVo);
		
		return JSONObject.parseObject(matPurMainAmountJson);

	}
}
