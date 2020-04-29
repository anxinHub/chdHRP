/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.empbonus;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.chd.hrp.budg.service.empbonus.BudgAwardsService;
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
public class BudgAwardsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAwardsController.class);
	
	//引入Service服务
	@Resource(name = "budgAwardsService")
	private final BudgAwardsService budgAwardsService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/budgAwardsMainPage", method = RequestMethod.GET)
	public String budgAwardsMainPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgawards/budgAwardsMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						 
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/budgAwardsAddPage", method = RequestMethod.GET)
	public String budgAwardsAddPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgawards/budgAwardsAdjRate";

	}

	/**
	 * @Description 
	 * 添加数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/addBudgAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAwards(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
    	
		String budgAwardsJson = budgAwardsService.add(mapVo);

		return JSONObject.parseObject(budgAwardsJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/budgAwardsUpdatePage", method = RequestMethod.GET)
	public String budgAwardsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
    
		Map<String, Object> budgAwardsMap = budgAwardsService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgAwardsMap.get("group_id"));
		mode.addAttribute("hos_id", budgAwardsMap.get("hos_id"));
		mode.addAttribute("copy_code", budgAwardsMap.get("copy_code"));
		mode.addAttribute("awards_item_code", budgAwardsMap.get("awards_item_code"));
		
		return "hrp/budg/empbonus/budgawards/budgAwardsUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/updateBudgAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAwards(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("awards_item_code", ids[3]);
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
		String budgAwardsJson = budgAwardsService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgAwardsJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/addOrUpdateBudgAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAwards(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAwardsJson ="";
		
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
	  
		budgAwardsJson = budgAwardsService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAwardsJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/deleteBudgAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAwards(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("awards_item_code", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgAwardsJson = budgAwardsService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAwardsJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 工资变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/queryBudgAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAwards(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
       
		String budgAwards = budgAwardsService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAwards);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 工资变动
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/budgAwardsImportPage", method = RequestMethod.GET)
	public String budgAwardsImportPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgawards/budgAwardsImport";

	}
	
	/**
	 * @Description 
	 * 生成数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/generate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgAwardsJson = budgAwardsService.add(mapVo);
		
		return JSONObject.parseObject(budgAwardsJson);
		
	}
	
	//导入
	@RequestMapping(value = "/hrp/budg/empbonus/budgawards/importExcel", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = budgAwardsService.importExcel(mapVo);
		return reJson;
	}
	
}

