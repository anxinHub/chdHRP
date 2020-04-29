package com.chd.hrp.med.controller.purchase.collect;

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
import com.chd.hrp.med.service.purchase.collect.MedPurMainCollectService;

/**
 * 
 * @Description:
 * 08113 统购采购计划汇总
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedPurMainCollectController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedPurMainCollectController.class);
	
	@Resource(name = "medPurMainCollectService")
	private final MedPurMainCollectService medPurMainCollectService = null;
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//主页面跳转
	@RequestMapping(value = "/hrp/med/purchase/collect/medPurMainCollectMainPage", method = RequestMethod.GET)
	public String medPurMainCollectMainPage(Model mode) throws Exception {

		return "hrp/med/purchase/collect/medPurMainCollectMain"; 

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//汇总生成采购计划采购明细页面跳转
	@RequestMapping(value = "/hrp/med/purchase/collect/medPurMainCollectDetailPage", method = RequestMethod.GET)
	public String medPurMainCollectDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> medPurMainMap = medPurMainCollectService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", medPurMainMap.get("group_id"));
		mode.addAttribute("hos_id", medPurMainMap.get("hos_id"));
		mode.addAttribute("copy_code",medPurMainMap.get("copy_code"));
		mode.addAttribute("pur_id", medPurMainMap.get("pur_id"));
		mode.addAttribute("pur_code", medPurMainMap.get("pur_code"));
		mode.addAttribute("dept_id", medPurMainMap.get("dept_id"));
		mode.addAttribute("dept_no",medPurMainMap.get("dept_no"));
		mode.addAttribute("dept_name", medPurMainMap.get("dept_name"));
		mode.addAttribute("make_date", medPurMainMap.get("make_date"));
		mode.addAttribute("pur_type", medPurMainMap.get("pur_type"));
		mode.addAttribute("brif", medPurMainMap.get("brif"));
		mode.addAttribute("pur_hos_id",medPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_name", medPurMainMap.get("pur_hos_name"));
		mode.addAttribute("req_hos_id", medPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_name", medPurMainMap.get("req_hos_name"));
		mode.addAttribute("pay_hos_id", medPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_name", medPurMainMap.get("pay_hos_name"));
		
		return "hrp/med/purchase/collect/medPurMainCollectDetail";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//按采购计划汇总
	@RequestMapping(value = "/hrp/med/purchase/collect/prodMedPurMainCollectPage", method = RequestMethod.GET)
	public String prodMedPurMainCollectPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("pur_hos_id",mapVo.get("pur_hos_id"));
		mode.addAttribute("pur_hos_name",mapVo.get("pur_hos_name"));
		
		return "hrp/med/purchase/collect/medPurMainCollectProd";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//请购计划单明细
	@RequestMapping(value = "/hrp/med/purchase/collect/medPurReqDetailPage", method = RequestMethod.GET)
	public String medPurReqDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> medPurMainMap = medPurMainCollectService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", medPurMainMap.get("group_id"));
		mode.addAttribute("hos_id", medPurMainMap.get("hos_id"));
		mode.addAttribute("copy_code",medPurMainMap.get("copy_code"));
		mode.addAttribute("pur_id", medPurMainMap.get("pur_id"));
		mode.addAttribute("pur_code", medPurMainMap.get("pur_code"));
		mode.addAttribute("dept_id", medPurMainMap.get("dept_id"));
		mode.addAttribute("dept_no",medPurMainMap.get("dept_no"));
		mode.addAttribute("dept_name", medPurMainMap.get("dept_name"));
		mode.addAttribute("make_date", medPurMainMap.get("make_date"));
		mode.addAttribute("pur_hos_id",medPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_name", medPurMainMap.get("pur_hos_name"));
		mode.addAttribute("req_hos_id", medPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_name", medPurMainMap.get("req_hos_name"));
		mode.addAttribute("pay_hos_id", medPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_name", medPurMainMap.get("pay_hos_name"));
		
		return "hrp/med/purchase/collect/medPurReqDetail";

	}
	
	
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//药品当前库存明细页面跳转
	@RequestMapping(value = "/hrp/med/purchase/collect/medInvCurAmountDetailPage", method = RequestMethod.GET)
	public String medInvCurAmountDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		
		return "hrp/med/purchase/collect/medInvCurAmountDetail";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//采购计划数量明细
	@RequestMapping(value = "/hrp/med/purchase/collect/medPurMainAmountDetailPage", method = RequestMethod.GET)
	public String medPurMainAmountDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		return "hrp/med/purchase/collect/medPurMainAmountDetail";

	}	
	
	/**
	 * @Description 
	 * 统购采购计划汇总 查询采购计划
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/collect/queryMedPurMainCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
		String medPurMainCollectJson = medPurMainCollectService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medPurMainCollectJson);

	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总 按主表ID查询采购明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/collect/queryMedPurDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedPurDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurMainCollectJson = medPurMainCollectService.queryMedPurDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medPurMainCollectJson);

	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总 查看药品当前库存明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/collect/queryMedInvCurAmountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedInvCurAmountDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurMainCollectJson = medPurMainCollectService.queryMedInvCurAmountDetail(mapVo);
		
		return JSONObject.parseObject(medPurMainCollectJson);

	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按采购计划汇总
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@RequestMapping(value = "/hrp/med/purchase/collect/queryMedPurMainByCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedPurMainByCollect(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurMainCollectJson = medPurMainCollectService.queryMedPurMainByCollect(mapVo);
		
		return JSONObject.parseObject(medPurMainCollectJson);

	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>汇总生成采购计划
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@RequestMapping(value = "/hrp/med/purchase/collect/collectMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> collectMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
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
		
		String medPurMainCollectJson = medPurMainCollectService.genByCollectMedPurMain(listVo);
		
		return JSONObject.parseObject(medPurMainCollectJson);
	}
	
	/**
	 * @Description 
	 * 统购采购计划汇总 汇总生成采购计划明细页面 更新
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/collect/addOrUpdateMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOrUpdateMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurDetailJson = medPurMainCollectService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(medPurDetailJson);

	}
	
	
	/**
	 * @Description 
	 * 统购采购计划汇总 采购计划数量明细页面 查询
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/collect/queryMedPurMainAmountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedPurMainAmountDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurMainAmountJson = medPurMainCollectService.queryMedPurMainAmountDetail(mapVo);
		
		return JSONObject.parseObject(medPurMainAmountJson);

	}
}
