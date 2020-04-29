/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.budg.controller.base.budgsubj;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.entity.BudgIncomeSubjType;
import com.chd.hrp.budg.serviceImpl.base.budgsubj.BudgIncomeSubjTypeServiceImpl;

/**
* @Title. @Description.
* 科目性质
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class BudgIncomeSubjTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(BudgIncomeSubjTypeController.class);
	
	
	@Resource(name = "budgIncomeSubjTypeService")
	private final BudgIncomeSubjTypeServiceImpl budgIncomeSubjTypeService = null;
   
    
	/**
	*收入科目类别<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubjtype/budgIncomeSubjTypeMainPage", method = RequestMethod.GET)
	public String accSubjNatureMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgincomesubjtype/budgIncomeSubjTypeMain";

	}
	/**
	*收入科目类别<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubjtype/budgIncomeSubjTypeAddPage", method = RequestMethod.GET)
	public String accSubjNatureAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgincomesubjtype/budgIncomeSubjTypeAdd";

	}
	/**
	*收入科目类别<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubjtype/addBudgIncomeSubjType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addBudgIncomeSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accSubjNatureJson = budgIncomeSubjTypeService.addBudgIncomeSubjType(mapVo);

		return JSONObject.parseObject(accSubjNatureJson);
		
	}
	/**
	*收入科目类别<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubjtype/queryBudgIncomeSubjType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryBudgIncomeSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String budgIncomeSubjType = budgIncomeSubjTypeService.queryBudgIncomeSubjType(getPage(mapVo));

		return JSONObject.parseObject(budgIncomeSubjType);
		
	}
	/**
	*收入科目类别<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubjtype/deleteBudgIncomeSubjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgIncomeSubjType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", id.split("@")[0]);
            mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("copy_code", id.split("@")[2]);
            mapVo.put("income_subj_type_code", id.split("@")[3]);
            listVo.add(mapVo);
        }
		String subjTypeJson = budgIncomeSubjTypeService.deleteBatchBudgIncomeSubjType(listVo);
	   return JSONObject.parseObject(subjTypeJson);
			
	}
	
	/**
	*收入科目类别<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubjtype/budgIncomeSubjTypeUpdatePage", method = RequestMethod.GET)
	
	public String budgIncomeSubjTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		BudgIncomeSubjType incomeSubType = new BudgIncomeSubjType();
		incomeSubType= budgIncomeSubjTypeService.queryBudgIncomeSubjTypeByCode(mapVo);
		mode.addAttribute("group_id", incomeSubType.getGroup_id());
		mode.addAttribute("hos_id", incomeSubType.getHos_id());
		mode.addAttribute("copy_code", incomeSubType.getCopy_code());
		mode.addAttribute("income_subj_type_code", incomeSubType.getIncome_subj_type_code());
		mode.addAttribute("income_subj_type_name", incomeSubType.getIncome_subj_type_name());
		mode.addAttribute("is_stop", incomeSubType.getIs_stop());
		mode.addAttribute("is_fixed", incomeSubType.getIs_fixed());
		return "hrp/budg/base/budgsubj/budgincomesubjtype/budgIncomeSubjTypeUpdate";
	}
	/**
	*收入科目类别<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubjtype/updateBudgIncomeSubjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgIncomeSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accSubjNatureJson = budgIncomeSubjTypeService.updateBudgIncomeSubjType(mapVo);
		
		return JSONObject.parseObject(accSubjNatureJson);
	}
	/**
	*收入科目类别<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubjtype/importBudgIncomeSubjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importBudgIncomeSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accSubjNatureJson = budgIncomeSubjTypeService.importBudgIncomeSubjType(mapVo);
		
		return JSONObject.parseObject(accSubjNatureJson);
	}

}

