/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.business.med;

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
import com.chd.hrp.budg.service.business.med.BudgMedPurExeService;

/**
 * 
 * @Description: 药品采购预算编制
 * @Table: BUDG_MED_PUR
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgMedPurExeController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgMedPurExeController.class);

	// 引入Service服务
	@Resource(name = "budgMedPurExeService")
	private final BudgMedPurExeService budgMedPurExeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/budgMedPurExeMainPage", method = RequestMethod.GET)
	public String budgMedPurExeMainPage(Model mode) throws Exception {
		return "hrp/budg/business/med/budgmedpurexe/budgMedPurExeMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/budgMedPurExeAddPage", method = RequestMethod.GET)
	public String budgMedPurExeAddPage(Model mode) throws Exception {
		return "hrp/budg/business/med/budgmedpurexe/budgMedPurExeAdd";
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/saveBudgMedPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgMedPurExe(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("med_type_id", ids[2]);
			mapVo.put("pur_amount", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("rowNo", ids[5]);
			mapVo.put("flag", ids[6]);
			
			listVo.add(mapVo);
	    }
		
		String budgMedPurExeJson = null ;
		try {
			
			budgMedPurExeJson = budgMedPurExeService.saveBudgMedPurExe(listVo);
			
		} catch (Exception e) {
			
			budgMedPurExeJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgMedPurExeJson);
			
	}
	
	/**
	 * @Description 添加数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/addBudgMedPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMedPurExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgMedPurExeJson = budgMedPurExeService.add(mapVo);

		return JSONObject.parseObject(budgMedPurExeJson);

	}

	/**
	 * @Description 更新跳转页面 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/budgMedPurExeUpdatePage", method = RequestMethod.GET)
	public String budgMedPurExeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> budgChargeMat = budgMedPurExeService.queryByCode(mapVo);
		mode.addAllAttributes(budgChargeMat);
		return "hrp/budg/business/med/budgmedpurexe/budgMedPurExeUpdate";
	}

	/**
	 * @Description 更新数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/updateBudgMedPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedPurExe(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgMedPurExeJson = budgMedPurExeService.update(mapVo);

		return JSONObject.parseObject(budgMedPurExeJson);
	}

	/**
	 * @Description 更新数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/addOrUpdateBudgMedPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMedPurExe(@RequestParam(value="ParamVo") String paramVo, Model mode)
			throws Exception {

		String budgMedPurExeJson = "";
		
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
				mapVo.put("med_type_id", ids[2]);
				
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
			budgMedPurExeJson = budgMedPurExeService.addOrUpdateBudgMedPurExe(listVo);
		} catch (Exception e) {
			budgMedPurExeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgMedPurExeJson);
	}

	/**
	 * @Description 删除数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/deleteBudgMedPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMedPurExe(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("med_type_id", ids[5]);

			listVo.add(mapVo);

		}

		String budgMedPurExeJson = budgMedPurExeService.deleteBatch(listVo);

		return JSONObject.parseObject(budgMedPurExeJson);

	}

	/**
	 * @Description 查询数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/queryBudgMedPurExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMedPurExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgMedPurExe = budgMedPurExeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedPurExe);

	}

	/**
	 * @Description 导入跳转页面 科室材料支出预算
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/budgMedPurExeImportPage", method = RequestMethod.GET)
	public String budgMedPurExeImportPage(Model mode) throws Exception {

		return "hrp/budg/business/med/budgmedpurexe/budgMedPurExeImport";

	}

	/**
	 * 物资分类 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpurexe/queryBudgMedTypeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMedTypeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return budgMedPurExeService.queryBudgMedTypeSubj(mapVo);

	}
}
