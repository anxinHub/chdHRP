/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.toptodown.hosyearinbudg;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.ExcelWriterTo2007;
import com.chd.base.util.Plupload;
import com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg.BudgMedIncomeHosYearService;
import com.chd.hrp.hr.util.excel.ExcelUtils;

/**
 * 
 * @Description:
 * 医院年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedIncomeHosYearController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedIncomeHosYearController.class);
	
	//引入Service服务
	@Resource(name = "budgMedIncomeHosYearService")
	private final BudgMedIncomeHosYearService budgMedIncomeHosYearService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/medinbudg/medInHosYearMainPage", method = RequestMethod.GET)
	public String budgMedIncomeHosYearMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/medinbudg/budgMedIncomeHosYearMain";

	}

	/**
	 * @Description 
	 * 查询数据 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/medinbudg/queryBudgMedIncomeHosYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMedIncomeHosYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgMedIncomeHosYear = budgMedIncomeHosYearService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeHosYear);
		
	}
	
	
	/**
	 * @Description 
	 * 更新数据 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/medinbudg/updateBudgMedIncomeHosYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedIncomeHosYear(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("budg_value", ids[2]);
			mapVo.put("count_value", ids[3]);
			mapVo.put("remark", ids[4]);
		/*	mapVo.put("grow_rate", ids[5]);
			mapVo.put("grow_value", ids[6]);*/
			mapVo.put("last_year_income", ids[5]);
			
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
	  
		String budgMedIncomeHosYearJson = budgMedIncomeHosYearService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgMedIncomeHosYearJson);
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/medinbudg/sumDeptBudgValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sumDeptBudgValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgMedIncomeHosYear = budgMedIncomeHosYearService.sumDeptBudgValue(mapVo);

		return JSONObject.parseObject(budgMedIncomeHosYear);
		
	}
	
	//导出
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/hosyearinbudg/medinbudg/exportExcel")  
	public String exportExcel(@RequestParam Map<String, Object> mapVo, HttpServletResponse response,Model mode) throws IOException { 
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			List<String> header = new ArrayList<String>();
			header.add("预算年度");
			header.add("科目编码");
			header.add("科目名称");
			header.add("上年收入(元)");
			header.add("预算值");
			header.add("说明");
			
			List<List<String>> list = budgMedIncomeHosYearService.queryExportData(mapVo);
			String filename = "医院年度医疗收入预算.xlsx";
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			ExcelUtils.getInstance().exportObjects2Excel(list, header,response.getOutputStream());
			return null;
		} catch (Exception e) {
			return "{\"error\":\"导出失败\"}";
		}
	}
}

