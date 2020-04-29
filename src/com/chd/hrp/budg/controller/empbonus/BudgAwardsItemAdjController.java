/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.empbonus;
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
import com.chd.hrp.budg.entity.BudgAwardsItemAdj;
import com.chd.hrp.budg.service.empbonus.BudgAwardsItemAdjService;
/**
 * 
 * @Description:
 * 奖金变动
 * @Table:
 * BUDG_AWARDS_ITEM_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class  BudgAwardsItemAdjController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAwardsItemAdjController.class);
	
	//引入Service服务
	@Resource(name = "budgAwardsItemAdjService")
	private final BudgAwardsItemAdjService budgAwardsItemAdjService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawardsitemadj/budgAwardsItemAdjMainPage", method = RequestMethod.GET)
	public String budgAwardsItemAdjMainPage(Model mode) throws Exception {
				
		return "hrp/budg/empbonus/budgawardsitemadj/budgAwardsItemAdjMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawardsitemadj/budgAwardsItemAdjAddPage", method = RequestMethod.GET)
	public String budgAwardsItemAdjAddPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgawardsitemadj/budgAwardsItemAdjAdd";

	}

	/**
	 * @Description 
	 * 添加数据 奖金变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawardsitemadj/addBudgAwardsItemAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAwardsItemAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//添加数据使用
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
    	
    	String[] empCodes = mapVo.get("emp_type_code").toString().split(";");
    	
    	//移除集合中原来拼接的emp_type_code 
    	mapVo.remove("emp_type_code");
    	
    	for (String emp_type_code : empCodes) {
    		//定义map  封装数据参数
    		Map<String, Object> map=new HashMap<String, Object>();
    		
    		map.putAll(mapVo);
    		//将切割后的单个emp_type_code  分别放入map中  并加入list集合中
			map.put("emp_type_code", emp_type_code);
			
			listVo.add(map);
		}
    	
		String budgAwardsItemAdjJson = budgAwardsItemAdjService.addBatch(listVo);

		return JSONObject.parseObject(budgAwardsItemAdjJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 奖金变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawardsitemadj/budgAwardsItemAdjUpdatePage", method = RequestMethod.GET)
	public String budgAwardsItemAdjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		BudgAwardsItemAdj budgAwardsItemAdj = new BudgAwardsItemAdj();
    
		budgAwardsItemAdj = budgAwardsItemAdjService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgAwardsItemAdj.getGroup_id());
		mode.addAttribute("hos_id", budgAwardsItemAdj.getHos_id());
		mode.addAttribute("copy_code", budgAwardsItemAdj.getCopy_code());
		mode.addAttribute("awards_item_code", budgAwardsItemAdj.getAwards_item_code());
		
		return "hrp/budg/empbonus/budgawardsitemadj/budgAwardsItemAdjUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 奖金变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawardsitemadj/updateBudgAwardsItemAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAwardsItemAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
	    	
			mapVo.put("budg_year", ids[0]);
			mapVo.put("awards_item_code", ids[1]);
			mapVo.put("emp_type_code", ids[2]);
			mapVo.put("adj_amount", ids[3]);
			mapVo.put("adj_rate", ids[4]);
			mapVo.put("adj_month", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("remark", "");
			}else{
				
				mapVo.put("remark", ids[6]);
			}
			
			listVo.add(mapVo);
      
    }
		String budgAwardsItemAdjJson = budgAwardsItemAdjService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgAwardsItemAdjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 奖金变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawardsitemadj/addOrUpdateBudgAwardsItemAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAwardsItemAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAwardsItemAdjJson ="";
		
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
	  
		budgAwardsItemAdjJson = budgAwardsItemAdjService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAwardsItemAdjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 奖金变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawardsitemadj/deleteBudgAwardsItemAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAwardsItemAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("emp_type_code", ids[4])   ;
				mapVo.put("awards_item_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgAwardsItemAdjJson = budgAwardsItemAdjService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAwardsItemAdjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 奖金变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawardsitemadj/queryBudgAwardsItemAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAwardsItemAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
       
		String budgAwardsItemAdj = budgAwardsItemAdjService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAwardsItemAdj);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 奖金变动
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgawardsitemadj/budgAwardsItemAdjImportPage", method = RequestMethod.GET)
	public String budgAwardsItemAdjImportPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgawardsitemadj/budgAwardsItemAdjImport";

	}
	
}

