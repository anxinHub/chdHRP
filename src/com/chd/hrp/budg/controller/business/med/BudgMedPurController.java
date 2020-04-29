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
import com.chd.hrp.budg.service.business.med.BudgMedPurService;

/**
 * 
 * @Description: 材料采购预算编制
 * @Table: BUDG_MAT_PUR
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgMedPurController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgMedPurController.class);

	// 引入Service服务
	@Resource(name = "budgMedPurService")
	private final BudgMedPurService budgMedPurService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/budgMedPurMainPage", method = RequestMethod.GET)
	public String budgMedPurMainPage(Model mode) throws Exception {
		return "hrp/budg/business/med/budgmedpur/budgMedPurMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/budgMedPurAddPage", method = RequestMethod.GET)
	public String budgMedPurAddPage(Model mode) throws Exception {
		return "hrp/budg/business/med/budgmedpur/budgMedPurAdd";
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/saveBudgMedPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgMedPur(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("pur_budg", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("rowNo", ids[5]);
			mapVo.put("flag", ids[6]);
			
			listVo.add(mapVo);
	    }
		
		String budgMedPurJson = null ;
		try {
			
			budgMedPurJson = budgMedPurService.saveBudgMedPur(listVo);
			
		} catch (Exception e) {
			
			budgMedPurJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgMedPurJson);
			
	}
	
	/**
	 * @Description 添加数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/addBudgMedPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMedPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgMedPurJson = budgMedPurService.add(mapVo);

		return JSONObject.parseObject(budgMedPurJson);

	}

	/**
	 * @Description 更新跳转页面 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/budgMedPurUpdatePage", method = RequestMethod.GET)
	public String budgMedPurUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> budgChargeMat = budgMedPurService.queryByCode(mapVo);
		mode.addAllAttributes(budgChargeMat);
		return "hrp/budg/business/med/budgmedpur/budgMedPurUpdate";
	}

	/**
	 * @Description 更新数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/updateBudgMedPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedPur(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgMedPurJson = budgMedPurService.update(mapVo);

		return JSONObject.parseObject(budgMedPurJson);
	}

	/**
	 * @Description 更新数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/addOrUpdateBudgMedPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMedPur(@RequestParam(value="ParamVo") String paramVo, Model mode)
			throws Exception {

		String budgMedPurJson = "";
		
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
			budgMedPurJson = budgMedPurService.addOrUpdateBudgMedPur(listVo);
		} catch (Exception e) {
			budgMedPurJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgMedPurJson);
	}

	/**
	 * @Description 删除数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/deleteBudgMedPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMedPur(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

		String budgMedPurJson = budgMedPurService.deleteBatch(listVo);

		return JSONObject.parseObject(budgMedPurJson);

	}

	/**
	 * @Description 查询数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/queryBudgMedPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMedPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgMedPur = budgMedPurService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedPur);

	}

	/**
	 * @Description 导入跳转页面 科室材料支出预算
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/budgMedPurImportPage", method = RequestMethod.GET)
	public String budgMedPurImportPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgmat/budgMedPurImport";

	}

	/**
	 * 物资分类 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/queryBudgMedPurTypeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMedPurTypeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return budgMedPurService.queryBudgMedTypeSubj(mapVo);

	}
	
	/**
	 * 生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/generateBudgMedPur", method = RequestMethod.POST)
	@ResponseBody
	public String generateBudgMedPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return budgMedPurService.collectBudgMed(mapVo);
	}
	
	/**
	 * @Description 
	 * 根据 预算年度 、 月份 、药品分类  查询其支出预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/budgmedpur/queryCostBudg", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostBudg(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String str  = budgMedPurService.queryCostBudg(mapVo) ;

		return str;

	}
	
}
