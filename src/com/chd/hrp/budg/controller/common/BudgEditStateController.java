/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
import java.util.*;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.service.common.BudgEditStateService;
/**
 * 
 * @Description:
 * 预算编辑状态管理 
 * @Table:
 * BUDG_EDIT_STATE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgEditStateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgEditStateController.class);
	
	//引入Service服务
	@Resource(name = "budgEditStateService")
	private final BudgEditStateService budgEditStateService = null;
   

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/addBudgEditState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgEditState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("year") == null){
			mapVo.put("year", SessionManager.getAcctYear());
		}
		
       
		String editStateJson = budgEditStateService.add(mapVo);

		return JSONObject.parseObject(editStateJson);
		
	}

		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/updateBudgEditState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgEditState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String editStateJson = budgEditStateService.update(mapVo);
		
		return JSONObject.parseObject(editStateJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/addOrUpdateBudgEditState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgEditState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String editStateJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		editStateJson = budgEditStateService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(editStateJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/deleteBudgEditState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgEditState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String editStateJson = budgEditStateService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(editStateJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/queryBudgEditState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgEditState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String editStateJson = budgEditStateService.query(getPage(mapVo));

		return JSONObject.parseObject(editStateJson);
		
	}
    
	/**
	 * @Description 
	 * 查询 业务预算可编辑标记 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/queryWorkFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWorkFlag(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String editStateJson = budgEditStateService.queryWorkFlag(mapVo);

		return JSONObject.parseObject(editStateJson);
		
	}
	
	/**
	 * @Description 
	 * 查询 医疗收入可编辑标记
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/queryMedIncomeFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedIncomeFlag(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String editStateJson = budgEditStateService.queryMedIncomeFlag(mapVo);

		return JSONObject.parseObject(editStateJson);
		
	}
	
	/**
	 * @Description 
	 * 查询 其他收入可编辑标记
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/queryElseIncomeFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryElseIncomeFlag(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String editStateJson = budgEditStateService.queryElseIncomeFlag(mapVo);

		return JSONObject.parseObject(editStateJson);
		
	}
	
	/**
	 * @Description 
	 * 查询 医疗支出可编辑标记 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/queryMedCostFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCostFlag(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String editStateJson = budgEditStateService.queryMedCostFlag(mapVo);

		return JSONObject.parseObject(editStateJson);
		
	}
	
	/**
	 * @Description 
	 * 查询 其他支出可编辑标记
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/queryElseCostFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryElseCostFlag(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String editStateJson = budgEditStateService.queryElseCostFlag(mapVo);

		return JSONObject.parseObject(editStateJson);
		
	}
	
	/**
	 * @Description 
	 * 查询 材料采购可编辑标记
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/queryMatPurFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurFlag(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String editStateJson = budgEditStateService.queryMatPurFlag(mapVo);

		return JSONObject.parseObject(editStateJson);
		
	}
	
	/**
	 * @Description 
	 * 查询 药品采购可编辑标记
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgeditstate/queryMedPurFlag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurFlag(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String editStateJson = budgEditStateService.queryMedPurFlag(mapVo);

		return JSONObject.parseObject(editStateJson);
		
	}
	
}

