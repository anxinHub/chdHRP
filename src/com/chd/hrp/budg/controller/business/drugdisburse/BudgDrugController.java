/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.business.drugdisburse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgDrug;
import com.chd.hrp.budg.service.business.drugdisburse.BudgDrugService;

/**
 * 
 * @Description: 科室药品支出预算编制
 * @Table: BUDG_DRUG
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgDrugController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgDrugController.class);

	// 引入Service服务
	@Resource(name = "budgDrugService")
	private final BudgDrugService budgDrugService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/budgDrugMainPage", method = RequestMethod.GET)
	public String budgDrugMainPage(Model mode) throws Exception {
		return "hrp/budg/business/drugdisburse/budgdrug/budgDrugMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/budgDrugAddPage", method = RequestMethod.GET)
	public String budgDrugAddPage(Model mode) throws Exception {
		return "hrp/budg/business/drugdisburse/budgdrug/budgDrugAdd";
	}
	
	/**
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/saveBudgDrug", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgDrug(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("budg_year", ids[0])   ;
			mapVo.put("month", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("med_type_id", ids[3]);
			mapVo.put("last_cost", ids[4]);
			mapVo.put("grow_rate", ids[5]);
			mapVo.put("count_value", ids[6]);
			mapVo.put("adj_rate", ids[7]);
			mapVo.put("cost_budg", ids[8]);
			mapVo.put("remark", ids[9]);
			mapVo.put("rowNo", ids[10]) ;
			mapVo.put("flag", ids[11]) ;
			listVo.add(mapVo);
		}
		
       
		String budgHosIndependentSubjJson = null ;
		
		try {
			
			budgHosIndependentSubjJson = budgDrugService.save(listVo);

		} catch (Exception e) {
			
			budgHosIndependentSubjJson = e.getMessage() ;
		}
		
		return JSONObject.parseObject(budgHosIndependentSubjJson);
		
	}
	
	/**
	 * @Description 添加数据 科室药品支出预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/addBudgDrug", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDrug(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String budgDrugJson = budgDrugService.add(mapVo);

		return JSONObject.parseObject(budgDrugJson);

	}

	/**
	 * @Description 更新跳转页面 科室药品支出预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/budgDrugUpdatePage", method = RequestMethod.GET)
	public String budgDrugUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		Map<String, Object> budgChargeMat = budgDrugService.queryByCode(mapVo);
		mode.addAllAttributes(budgChargeMat);
		return "hrp/budg/business/drugdisburse/budgdrug/budgDrugUpdate";
	}

	/**
	 * @Description 更新数据 科室药品支出预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/updateBudgDrug", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDrug(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String budgDrugJson = budgDrugService.update(mapVo);

		return JSONObject.parseObject(budgDrugJson);
	}

	/**
	 * @Description 更新数据 科室药品支出预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/addOrUpdateBudgDrug", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDrug(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String budgDrugJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		budgDrugJson = budgDrugService.addOrUpdate(mapVo);

		return JSONObject.parseObject(budgDrugJson);
	}

	/**
	 * @Description 删除数据 科室药品支出预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/deleteBudgDrug", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDrug(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("budg_year", ids[3]);
			mapVo.put("month", ids[4]);
			mapVo.put("dept_id", ids[5]);
			mapVo.put("med_type_id", ids[6]);

			listVo.add(mapVo);

		}

		String budgDrugJson = budgDrugService.deleteBatch(listVo);

		return JSONObject.parseObject(budgDrugJson);

	}

	/**
	 * @Description 查询数据 科室药品支出预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/queryBudgDrug", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDrug(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String budgDrug = budgDrugService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDrug);

	}

	/**
	 * @Description 导入跳转页面 科室药品支出预算编制
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/budgDrugImportPage", method = RequestMethod.GET)
	public String budgDrugImportPage(Model mode) throws Exception {

		return "hrp/budg/business/drugdisburse/budgdrug/budgDrugImport";

	}

	/**
	 * @Description 下载导入模版 科室药品支出预算编制
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/business/drugdisburse/budgdrug/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\downTemplate", "科室药品支出预算编制.xls");

		return null;
	}

	/**
	 * @Description 导入数据 科室药品支出预算编制
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/readBudgDrugFiles", method = RequestMethod.POST)
	public String readBudgDrugFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<BudgDrug> list_err = new ArrayList<BudgDrug>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				BudgDrug budgDrug = new BudgDrug();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					budgDrug.setBudg_year(temp[3]);
					mapVo.put("year", temp[3]);

				} else {

					err_sb.append("年度为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					budgDrug.setMonth(temp[4]);
					mapVo.put("month", temp[4]);

				} else {

					err_sb.append("月为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					budgDrug.setDept_id(Long.valueOf(temp[5]));
					mapVo.put("dept_id", temp[5]);

				} else {

					err_sb.append("部门ID为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					budgDrug.setMed_type_id(Long.valueOf(temp[6]));
					mapVo.put("mat_type_id", temp[6]);

				} else {

					err_sb.append("物资类别ID为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					budgDrug.setCost_budg(Double.valueOf(temp[10]));
					mapVo.put("cost_budg", temp[10]);

				} else {

					err_sb.append("");

				}
				if (StringTool.isNotBlank(temp[11])) {

					budgDrug.setRemark(temp[11]);
					mapVo.put("remark", temp[11]);

				} else {

					err_sb.append("");

				}

				int count = budgDrugService.queryDataExist(mapVo);

				if (count > 0 ) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					budgDrug.setError_type(err_sb.toString());

					list_err.add(budgDrug);

				} else {

					budgDrugService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			BudgDrug data_exc = new BudgDrug();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 科室药品支出预算编制
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/addBatchBudgDrug", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchBudgDrug(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<BudgDrug> list_err = new ArrayList<BudgDrug>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				BudgDrug budgDrug = new BudgDrug();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("year"))) {

					budgDrug.setBudg_year((String) jsonObj.get("year"));
					mapVo.put("year", jsonObj.get("year"));
				} else {

					err_sb.append("年度为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("month"))) {

					budgDrug.setMonth((String) jsonObj.get("month"));
					mapVo.put("month", jsonObj.get("month"));
				} else {

					err_sb.append("月为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {

					budgDrug.setDept_id(Long.valueOf((String) jsonObj.get("dept_id")));
					mapVo.put("dept_id", jsonObj.get("dept_id"));
				} else {

					err_sb.append("部门ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("mat_type_id"))) {

					budgDrug.setMed_type_id(Long.valueOf((String) jsonObj.get("mat_type_id")));
					mapVo.put("mat_type_id", jsonObj.get("mat_type_id"));
				} else {

					err_sb.append("物资类别ID为空  ");

				}

				

				int count = budgDrugService.queryDataExist(mapVo);

				if (count > 0 ) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					budgDrug.setError_type(err_sb.toString());

					list_err.add(budgDrug);

				} else {

					budgDrugService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			BudgDrug data_exc = new BudgDrug();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}
	
	/**
	 * 科室名称 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/queryHosDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgDrugService.queryHosDeptDict(mapVo);

	}
	
	/**
	 * 药品分类 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/queryBudgMedTypeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMedTypeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgDrugService.queryBudgMedTypeSubj(mapVo);

	}
	
	/**
	 * 生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/generateBudgDrug", method = RequestMethod.POST)
	@ResponseBody
	public String generateBudgDrug(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgDrugService.copyBudgDrug(mapVo);
	}*/
	
	/**
	 * @Description 
	 * 生成  根据年度月份药品分类生成本年度支出预算数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/generateBudgDrug", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateBudgDrug(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String budgChargeMatJson = budgDrugService.generateBudgDrug(mapVo);
		
		return JSONObject.parseObject(budgChargeMatJson);
		
	}
	
	/**
	 * @Description 
	 * 批量调整页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/budgDrugUpdateAdjRatePage", method = RequestMethod.GET)
	public String budgChargeMatUpdateAdjRatePage(Model mode) throws Exception {
		
		return "hrp/budg/business/drugdisburse/budgdrug/budgDrugUpdateAdjRate";
		
	}
	
	/**
	 * @Description 
	 * 批量更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/budgDrugUpdateAdjRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> budgDrugUpdateAdjRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
			mapVo.put("med_type_id", ids[3]);
			
			double last_cost = Double.parseDouble(ids[4]);//上年同期支出
			int old_grow_rate = Integer.parseInt(ids[5]);//增长比例原值
			int old_adj_rate = Integer.parseInt(ids[6]);//调整比例原值
			double count_value = Double.parseDouble(ids[7]);//计算值
			
			int grow_rate = old_grow_rate;
			
			if(ids[8] != null && ids[8].length() > 0){
				grow_rate = Integer.parseInt(ids[8]);//增长比例页面传值
				count_value = last_cost * (100+grow_rate)/100;
			}
			
			mapVo.put("count_value", count_value);//计算值
			mapVo.put("grow_rate", grow_rate);//增长比例
			
			int adj_rate = old_adj_rate;
			
			if(ids[9] != null && ids[9].length() > 0){
				adj_rate = Integer.parseInt(ids[9]);//增长比例页面传值
			}
			
			mapVo.put("adj_rate", adj_rate);//调整比例
			
			
			//支出预算 = 计算值*(100+调整比例)/100
			mapVo.put("cost_budg", count_value * (100+adj_rate)/100 );
			if("-1".equals(ids[10])){
				mapVo.put("remark", "");
			}else{
				
				mapVo.put("remark", ids[10]);
			}
			
			listVo.add(mapVo);
      
    }
		String budgWageJson = budgDrugService.budgDrugUpdateAdjRate(listVo);
		
		return JSONObject.parseObject(budgWageJson);
	}
	
	/**
	 * @Description 
	 * 根据 参数  查询收入预算、上年收入、上年同期支出  计算收入预算增长比例和计算值用
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrug/queryLastCostAndRate", method = RequestMethod.POST)
	@ResponseBody
	public String queryLastCostAndRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String jsonData = budgDrugService.queryLastCostAndRate(mapVo);

		return jsonData;

	}
}
