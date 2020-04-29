/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.business.invdisburse;

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
import com.chd.hrp.budg.service.business.invdisburse.BudgMatPurExeService;

/**
 * 
 * @Description: 材料采购预算执行
 * @Table: BUDG_MAT_PUR_EXE
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgMatPurExeController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgMatPurExeController.class);

	// 引入Service服务
	@Resource(name = "budgMatPurExeService")
	private final BudgMatPurExeService budgMatPurExeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/budgMatPurExeMainPage", method = RequestMethod.GET)
	public String budgMatPurExeMainPage(Model mode) throws Exception {
		return "hrp/budg/business/invdisburse/budgmatpurexe/budgMatPurExeMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/budgMatPurExeAddPage", method = RequestMethod.GET)
	public String budgMatPurExeAddPage(Model mode) throws Exception {
		return "hrp/budg/business/invdisburse/budgmatpurexe/budgMatPurExeAdd";
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/saveBudgMatPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgMatPurExe(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
				
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());   
			
			mapVo.put("hos_id", SessionManager.getHosId());   
	
			mapVo.put("copy_code", SessionManager.getCopyCode());  
			mapVo.put("year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("mat_type_id", ids[2]);
			mapVo.put("pur_amount", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("rowNo", ids[5]);
			mapVo.put("flag", ids[6]);
			
			listVo.add(mapVo);
	    }
		
		String budgMatPurJson = null ;
		try {
			
			budgMatPurJson = budgMatPurExeService.saveBudgMatPurExe(listVo);
			
		} catch (Exception e) {
			
			budgMatPurJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgMatPurJson);
			
	}

	/**
	 * @Description 添加数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/addBudgMatPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMatPurExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgMatPurExeJson = budgMatPurExeService.add(mapVo);

		return JSONObject.parseObject(budgMatPurExeJson);

	}

	/**
	 * @Description 更新跳转页面 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/budgMatPurExeUpdatePage", method = RequestMethod.GET)
	public String budgMatPurExeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> budgChargeMat = budgMatPurExeService.queryByCode(mapVo);
		mode.addAllAttributes(budgChargeMat);
		return "hrp/budg/business/invdisburse/budgmatpur/budgMatPurExeUpdate";
	}

	/**
	 * @Description 更新数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/updateBudgMatPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMatPurExe(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgMatPurExeJson = budgMatPurExeService.update(mapVo);

		return JSONObject.parseObject(budgMatPurExeJson);
	}

	/**
	 * @Description 更新数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/addOrUpdateBudgMatPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMatPurExe(@RequestParam(value="ParamVo") String paramVo, Model mode)
			throws Exception {

		String budgMatPurExeJson = "";
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		try {
			
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("year", ids[0]);
				mapVo.put("month", ids[1]);
				mapVo.put("mat_type_id", ids[2]);
				
				if("-1".equals(ids[3])){
					mapVo.put("pur_amount", 0);
				}else{
					mapVo.put("pur_amount", ids[3]);
				}
				if("-1".equals(ids[4])){
					mapVo.put("remark", "");
				}else{
					mapVo.put("remark", ids[4]);
				}
				
				listVo.add(mapVo);
			}
			budgMatPurExeJson = budgMatPurExeService.addOrUpdateBudgMatPurExe(listVo);
		} catch (Exception e) {
			budgMatPurExeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgMatPurExeJson);
	}

	/**
	 * @Description 删除数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/deleteBudgMatPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMatPurExe(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("year", ids[3]);
			mapVo.put("month", ids[4]);
			mapVo.put("mat_type_id", ids[5]);

			listVo.add(mapVo);

		}

		String budgMatPurExeJson = budgMatPurExeService.deleteBatch(listVo);

		return JSONObject.parseObject(budgMatPurExeJson);

	}

	/**
	 * @Description 查询数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/queryBudgMatPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMatPurExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgMatPurExe = budgMatPurExeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMatPurExe);

	}

	/**
	 * @Description 导入跳转页面 科室材料支出预算
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/budgMatPurExeImportPage", method = RequestMethod.GET)
	public String budgMatPurExeImportPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgmatpurexe/budgMatPurExeImport";

	}

	/**
	 * 物资分类 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgmatpurexe/queryBudgMatTypeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMatTypeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return budgMatPurExeService.queryBudgMatTypeSubj(mapVo);

	}
}
