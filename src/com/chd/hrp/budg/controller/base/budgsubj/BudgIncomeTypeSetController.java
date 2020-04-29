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
import com.chd.hrp.budg.entity.BudgIncomeTypeSet;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeTypeSetService;

/**
* @Title. @Description.
* 科目性质
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class BudgIncomeTypeSetController extends BaseController{
	private static Logger logger = Logger.getLogger(BudgIncomeTypeSetController.class);
	
	
	@Resource(name = "budgIncomeTypeSetService")
	private final BudgIncomeTypeSetService budgIncomeTypeSetService = null;
   
    
	/**
	*收入预算科目类别维护<BR>
	*维护主页面跳转
	*/
	
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/budgIncomeTypeSetMainPage", method = RequestMethod.GET)
	public String budgIncomeTypeSetMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgincometypeset/budgIncomeTypeSetMain";

	}
	/**
	*收入预算科目类别维护<BR>
	*维护添加页面跳转
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/budgIncomeTypeSetAddPage", method = RequestMethod.GET)
	public String budgIncomeTypeSetAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgincometypeset/budgIncomeTypeSetAdd";

	}
	/**
	 *收入预算科目类别维护<BR>
	 *保存
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/saveBudgIncomeTypeSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> saveBudgIncomeTypeSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("budg_year", id.split("@")[0]);
			mapVo.put("type_code", id.split("@")[1]);
			mapVo.put("type_name", id.split("@")[2]);
			mapVo.put("subj_code", id.split("@")[3]);
			mapVo.put("subj_name", id.split("@")[4]);
			mapVo.put("type_code_old", id.split("@")[5]);
			mapVo.put("subj_code_old", id.split("@")[6]);
			
			mapVo.put("rowNo", id.split("@")[7]);
			mapVo.put("flag", id.split("@")[8]);
			
			listVo.add(mapVo);
		}
		String typeSetJson = budgIncomeTypeSetService.save(listVo);
		
		return JSONObject.parseObject(typeSetJson);
		
	}
	/**
	*收入预算科目类别维护<BR>
	*添加
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/addBudgIncomeTypeSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addBudgIncomeTypeSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("type_code", id.split("@")[0]);
            mapVo.put("subj_code", id.split("@")[1]);
            mapVo.put("budg_year", id.split("@")[2]);
            mapVo.put("type_name", id.split("@")[3]);
            mapVo.put("subj_name", id.split("@")[4]);
            
            int count = budgIncomeTypeSetService.queryIsExist(mapVo);
            if(count >0 ){
            	return JSONObject.parseObject("{\"msg\":\"数据  科目类别名称:"+mapVo.get("type_name")+"  收入预算科目名称:" +mapVo.get("subj_name")+"已经存在.无需设置.\"}"); 
            }else{
            	listVo.add(mapVo);
            }
            
        }
		String typeSetJson = budgIncomeTypeSetService.addBatchBudgIncomeTypeSet(listVo);

		return JSONObject.parseObject(typeSetJson);
		
	}
	/**
	*收入预算科目类别维护<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/queryBudgIncomeTypeSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryBudgIncomeTypeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("budg_year") == null){
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		String budgIncomeTypeset = budgIncomeTypeSetService.queryBudgIncomeTypeSet(getPage(mapVo));

		return JSONObject.parseObject(budgIncomeTypeset);
		
	}
	/**
	*收入预算科目类别维护<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/deleteBudgIncomeTypeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgIncomeTypeSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", id.split("@")[0]);
            mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("copy_code", id.split("@")[2]);
            mapVo.put("budg_year", id.split("@")[3]);
            mapVo.put("type_code", id.split("@")[4]);
            mapVo.put("subj_code", id.split("@")[5]);
            listVo.add(mapVo);
        }
		String subjTypeJson = budgIncomeTypeSetService.deleteBatchBudgIncomeTypeSet(listVo);
	   return JSONObject.parseObject(subjTypeJson);
			
	}
	
	/**
	*收入预算科目类别维护<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/budgIncomeTypeSetUpdatePage", method = RequestMethod.GET)
	
	public String budgIncomeTypeSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		BudgIncomeTypeSet typeSet = new BudgIncomeTypeSet();
		typeSet= budgIncomeTypeSetService.queryBudgIncomeTypeSetByCode(mapVo);
		mode.addAttribute("group_id", typeSet.getGroup_id());
		mode.addAttribute("hos_id", typeSet.getHos_id());
		mode.addAttribute("copy_code", typeSet.getCopy_code());
		mode.addAttribute("budg_year", typeSet.getBudg_year());
		mode.addAttribute("type_code", typeSet.getType_code());
		mode.addAttribute("type_name", typeSet.getType_name());
		mode.addAttribute("subj_code", typeSet.getSubj_code());
		mode.addAttribute("subj_name", typeSet.getSubj_name());
		return "hrp/budg/base/budgsubj/budgincometypeset/budgIncomeTypeSetUpdate";
	}
	/**
	*收入预算科目类别维护<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/updateBudgIncomeTypeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgIncomeTypeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accSubjNatureJson = budgIncomeTypeSetService.updateBudgIncomeTypeSet(mapVo);
		
		return JSONObject.parseObject(accSubjNatureJson);
	}
	/**
	*收入预算科目类别维护<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/importBudgIncomeTypeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importBudgIncomeTypeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accSubjNatureJson = budgIncomeTypeSetService.importBudgIncomeTypeSet(mapVo);
		
		return JSONObject.parseObject(accSubjNatureJson);
	}
	/**
	 * 收入预算科目下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/queryBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		return budgIncomeTypeSetService.queryBudgIncomeSubj(getPage(mapVo));

	}
	
	/**
	 * 收入预算科目类别下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincometypeset/queryBudgIncomeSubjType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeSubjType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgIncomeTypeSetService.queryBudgIncomeSubjType(getPage(mapVo));

	}
	
	//queryBudgSysDict
}

