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
import com.chd.hrp.budg.entity.BudgCostSubjType;
import com.chd.hrp.budg.serviceImpl.base.budgsubj.BudgCostSubjTypeServiceImpl;

/**
* @Title. @Description.
* 支出科目类别
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class BudgCostSubjTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(BudgCostSubjTypeController.class);
	
	
	@Resource(name = "budgCostSubjTypeService")
	private final BudgCostSubjTypeServiceImpl budgCostSubjTypeService = null;
   
    
	/**
	*支出科目类别<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubjtype/budgCostSubjTypeMainPage", method = RequestMethod.GET)
	public String budgCostSubjTypeMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgcostsubjtype/budgCostSubjTypeMain";

	}
	/**
	*支出科目类别<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubjtype/budgCostSubjTypeAddPage", method = RequestMethod.GET)
	public String budgCostSubjTypeAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgcostsubjtype/budgCostSubjTypeAdd";

	}
	/**
	*支出科目类别<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubjtype/addBudgCostSubjType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addBudgCostSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accSubjNatureJson = budgCostSubjTypeService.addBudgCostSubjType(mapVo);

		return JSONObject.parseObject(accSubjNatureJson);
		
	}
	/**
	*支出科目类别<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubjtype/queryBudgCostSubjType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryBudgCostSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String budgCostSubjType = budgCostSubjTypeService.queryBudgCostSubjType(getPage(mapVo));

		return JSONObject.parseObject(budgCostSubjType);
		
	}
	/**
	*支出科目类别<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubjtype/deleteBudgCostSubjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgCostSubjType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", id.split("@")[0]);
            mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("copy_code", id.split("@")[2]);
            mapVo.put("cost_subj_type_code", id.split("@")[3]);
            listVo.add(mapVo);
        }
		String subjTypeJson = budgCostSubjTypeService.deleteBatchBudgCostSubjType(listVo);
	   return JSONObject.parseObject(subjTypeJson);
			
	}
	
	/**
	*支出科目类别<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubjtype/budgCostSubjTypeUpdatePage", method = RequestMethod.GET)
	
	public String budgCostSubjTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		BudgCostSubjType costSubType = new BudgCostSubjType();
		costSubType= budgCostSubjTypeService.queryBudgCostSubjTypeByCode(mapVo);
		mode.addAttribute("group_id", costSubType.getGroup_id());
		mode.addAttribute("hos_id", costSubType.getHos_id());
		mode.addAttribute("copy_code", costSubType.getCopy_code());
		mode.addAttribute("cost_subj_type_code", costSubType.getCost_subj_type_code());
		mode.addAttribute("cost_subj_type_name", costSubType.getCost_subj_type_name());
		mode.addAttribute("is_stop", costSubType.getIs_stop());
		mode.addAttribute("is_fixed", costSubType.getIs_fixed());
		return "hrp/budg/base/budgsubj/budgcostsubjtype/budgCostSubjTypeUpdate";
	}
	/**
	*支出科目类别<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubjtype/updateBudgCostSubjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCostSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accSubjNatureJson = budgCostSubjTypeService.updateBudgCostSubjType(mapVo);
		
		return JSONObject.parseObject(accSubjNatureJson);
	}
	/**
	*支出科目类别<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubjtype/importBudgCostSubjType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importBudgCostSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accSubjNatureJson = budgCostSubjTypeService.importBudgCostSubjType(mapVo);
		
		return JSONObject.parseObject(accSubjNatureJson);
	}

}

