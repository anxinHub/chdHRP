/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcost.budgwage;
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
import com.chd.hrp.budg.service.budgcost.budgwage.BudgWageService;
/**
 * 
 * @Description:
 * 工资变动
 * @Table:
 * BUDG_WAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWageController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWageController.class);
	
	//引入Service服务
	@Resource(name = "budgWageService")
	private final BudgWageService budgWageService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/budgWageMainPage", method = RequestMethod.GET)
	public String budgWageMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/budgwage/budgWageMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						 
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/budgWageAddPage", method = RequestMethod.GET)
	public String budgWageAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/budgwage/budgWageAdjRate";

	}

	/**
	 * @Description 
	 * 添加数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/addBudgWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
    	
		String budgWageJson = budgWageService.add(mapVo);

		return JSONObject.parseObject(budgWageJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/budgWageUpdatePage", method = RequestMethod.GET)
	public String budgWageUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
    
		Map<String, Object> budgWageMap = budgWageService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWageMap.get("group_id"));
		mode.addAttribute("hos_id", budgWageMap.get("hos_id"));
		mode.addAttribute("copy_code", budgWageMap.get("copy_code"));
		mode.addAttribute("wage_item_code", budgWageMap.get("wage_item_code"));
		
		return "hrp/budg/budgcost/budgwage/budgWageUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/updateBudgWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWage(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
	    	
			mapVo.put("budg_year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("wage_item_code", ids[3]);
			mapVo.put("count_value", ids[4]);
			mapVo.put("adj_rate", ids[5]);
			//支出预算 = 计算值*(100+调整比例)/100
			mapVo.put("cost_budg", Double.parseDouble(ids[4]) * (100+Integer.parseInt(ids[5]))/100 );
			if("-1".equals(ids[6])){
				mapVo.put("remark", "");
			}else{
				
				mapVo.put("remark", ids[6]);
			}
			
			listVo.add(mapVo);
      
    }
		String budgWageJson = budgWageService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWageJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/addOrUpdateBudgWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWageJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
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
	  
		budgWageJson = budgWageService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWageJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/deleteBudgWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWage(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("month", ids[4])   ;
				mapVo.put("dept_id", ids[5])   ;
				mapVo.put("wage_item_code", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWageJson = budgWageService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWageJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/queryBudgWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
       
		String budgWage = budgWageService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWage);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 工资变动
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/budgWageImportPage", method = RequestMethod.GET)
	public String budgWageImportPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/budgwage/budgWageImport";

	}
	
	/**
	 * @Description 
	 * 生成数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/budgwage/generate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgWageJson = budgWageService.add(mapVo);
		
		return JSONObject.parseObject(budgWageJson);
		
	}
}

