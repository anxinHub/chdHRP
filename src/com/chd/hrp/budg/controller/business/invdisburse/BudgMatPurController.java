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
import com.chd.hrp.budg.service.business.invdisburse.BudgMatPurService;

/**
 * 
 * @Description: 材料采购预算编制
 * @Table: BUDG_MAT_PUR
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgMatPurController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgMatPurController.class);

	// 引入Service服务
	@Resource(name = "budgMatPurService")
	private final BudgMatPurService budgMatPurService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/budgMatPurMainPage", method = RequestMethod.GET)
	public String budgMatPurMainPage(Model mode) throws Exception {
		return "hrp/budg/business/invdisburse/budgmatpur/budgMatPurMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/budgMatPurAddPage", method = RequestMethod.GET)
	public String budgMatPurAddPage(Model mode) throws Exception {
		return "hrp/budg/business/invdisburse/budgmatpur/budgMatPurAdd";
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/saveBudgMatPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgMatPur(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("pur_budg", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("rowNo", ids[5]);
			mapVo.put("flag", ids[6]);
			
			listVo.add(mapVo);
	    }
		
		String budgMatPurJson = null ;
		try {
			
			budgMatPurJson = budgMatPurService.saveBudgMatPur(listVo);
			
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
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/addBudgMatPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMatPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgMatPurJson = budgMatPurService.add(mapVo);

		return JSONObject.parseObject(budgMatPurJson);

	}

	/**
	 * @Description 更新跳转页面 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/budgMatPurUpdatePage", method = RequestMethod.GET)
	public String budgMatPurUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> budgChargeMat = budgMatPurService.queryByCode(mapVo);
		mode.addAllAttributes(budgChargeMat);
		return "hrp/budg/business/invdisburse/budgmatpur/budgMatPurUpdate";
	}

	/**
	 * @Description 更新数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/updateBudgMatPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMatPur(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgMatPurJson = budgMatPurService.update(mapVo);

		return JSONObject.parseObject(budgMatPurJson);
	}

	/**
	 * @Description 更新数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/addOrUpdateBudgMatPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMatPur(@RequestParam(value="ParamVo") String paramVo, Model mode)
			throws Exception {

		String budgMatPurJson = "";
		
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
					mapVo.put("cost_budg", 0);
				}else{
					mapVo.put("cost_budg", ids[3]);
				}
				if("-1".equals(ids[4])){
					mapVo.put("pur_budg", 0);
				}else{
					mapVo.put("pur_budg", ids[4]);
				}
				if("-1".equals(ids[5])){
					mapVo.put("remark", "");
				}else{
					mapVo.put("remark", ids[5]);
				}
				
				listVo.add(mapVo);
			}
			budgMatPurJson = budgMatPurService.addOrUpdateBudgMatPur(listVo);
		} catch (Exception e) {
			budgMatPurJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgMatPurJson);
	}

	/**
	 * @Description 删除数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/deleteBudgMatPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMatPur(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

		String budgMatPurJson = budgMatPurService.deleteBatch(listVo);

		return JSONObject.parseObject(budgMatPurJson);

	}

	/**
	 * @Description 查询数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/queryBudgMatPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMatPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgMatPur = budgMatPurService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMatPur);

	}

	/**
	 * @Description 导入跳转页面 科室材料支出预算
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/budgMatPurImportPage", method = RequestMethod.GET)
	public String budgMatPurImportPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgmat/budgMatPurImport";

	}

	/**
	 * 物资分类 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/queryBudgMatTypeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMatTypeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return budgMatPurService.queryBudgMatTypeSubj(mapVo);

	}
	
	/**
	 * 生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/generateBudgMatPur", method = RequestMethod.POST)
	@ResponseBody
	public String generateBudgMatPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return budgMatPurService.collectBudgMat(mapVo);
	}
	
	/**
	 * @Description 
	 * 根据 预算年度 、 月份 、物资分类  查询其支出预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgMatPur/queryCostBudg", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostBudg(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String str  = budgMatPurService.queryCostBudg(mapVo) ;

		return str;

	}
}
