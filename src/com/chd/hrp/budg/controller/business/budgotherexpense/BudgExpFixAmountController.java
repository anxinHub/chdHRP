/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.business.budgotherexpense;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgExpFixAmount;
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpFixAmountService;
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpenseService;

/**
 * 
 * @Description: 费用定额
 * @Table: BUDG_EXP_FIX_AMOUNT
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgExpFixAmountController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgExpFixAmountController.class);

	// 引入Service服务
	@Resource(name = "budgExpFixAmountService")
	private final BudgExpFixAmountService budgExpFixAmountService = null;
	
	// 引入Service服务
	@Resource(name = "budgExpenseService")
	private final BudgExpenseService budgExpenseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountMainPage", method = RequestMethod.GET)
	public String budgExpFixAmountMainPage(Model mode) throws Exception {
		return "hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountAddPage", method = RequestMethod.GET)
	public String budgExpFixAmountAddPage(Model mode) throws Exception {
		return "hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountAdd";
	}

	/**
	 * @Description 添加数据 费用定额
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/addBudgExpFixAmount", method = RequestMethod.POST)
	@ResponseBody
	public String addBudgExpFixAmount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		return budgExpFixAmountService.addOrUpdate(mapVo);

	}

	/**
	 * @Description 更新跳转页面 费用定额
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountUpdatePage", method = RequestMethod.GET)
	public String budgExpFixAmountUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		Map<String,Object> budgExpFixAmount = budgExpFixAmountService.queryByCode(mapVo);
		mode.addAttribute("budgExpFixAmount", budgExpFixAmount);
		return "hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountUpdate";
	}
	
	/**
	 * @Description 更新数据 费用定额
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/updateBudgExpFixAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgExpFixAmount(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String budgExpFixAmountJson = budgExpFixAmountService.update(mapVo);

		return JSONObject.parseObject(budgExpFixAmountJson);
	}

	
	/**
	 * @Description 删除数据  费用定额
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/deleteBudgExpFixAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgExpFixAmount(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("dept_id", ids[4]);
			mapVo.put("payment_item_id", ids[5]);
			listVo.add(mapVo);

		}

		String budgExpFixAmountJson = budgExpFixAmountService.deleteBatch(listVo);

		return JSONObject.parseObject(budgExpFixAmountJson);

	}

	/**
	 * @Description 查询数据  费用定额
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/queryBudgExpFixAmount", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgExpFixAmount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return budgExpFixAmountService.query(getPage(mapVo));

	}
	
	/**
	 * @Description 查询预算科室数据  费用定额
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/queryBudgDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return budgExpFixAmountService.queryBudgDept(mapVo);

	}
	
	/**
	 * 支出项目 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/queryBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		return budgExpFixAmountService.queryBudgPaymentItem(mapVo);

	}
	
	/**
	 * 预算科室 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/queryBudgDeptSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgExpFixAmountService.queryBudgDeptSelect(mapVo);

	}
	
	/**
	 * @Description 
	 * 导入跳转页面 其他支出预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountImportPage", method = RequestMethod.GET)
	public String budgExpFixAmountImportPage(Model mode) throws Exception {

		return "hrp/budg/business/budgotherexpense/expfixamount/budgExpFixAmountImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 其他支出预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/business/budgotherexpense/expfixamount/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\cost\\elseexpense","费用定额设置.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 其他支出预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/budgotherexpense/expfixamount/readBudgExpFixAmountFiles",method = RequestMethod.POST)  
    public String readBudgExpFixAmountFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		paraMap.put("hos_id", SessionManager.getHosId());   
		paraMap.put("copy_code", SessionManager.getCopyCode());   
		
		//查询 科室基本信息(根据code 匹配ID)
		List<Map<String,Object>> deptData = budgExpenseService.queryDeptData(paraMap);
		
		//查询 支出项目基本信息(根据code 匹配ID)
		List<Map<String,Object>> payItemData = budgExpenseService.queryPayItemData(paraMap);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
	    		mapVo.put("group_id", SessionManager.getGroupId());   
	    		         
				 
	    		mapVo.put("hos_id", SessionManager.getHosId());   
	    		         
				 
	    		mapVo.put("copy_code", SessionManager.getCopyCode());   
	    		
	    		for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])
							&& temp[4].equals(error[4])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
	    		
				if (ExcelReader.validExceLColunm(temp,0)) {
					
					map.put("budg_year", temp[0]);
		    		mapVo.put("budg_year", temp[0]);
				
				} else {
					
					err_sb.append("预算年度为空  ");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,1)) {
					
					int flag = 0 ;
					for(Map<String,Object> item: deptData ){
						
						if(temp[1].equals(item.get("dept_code"))){
							
							mapVo.put("dept_id", item.get("dept_id"));
							
							mapVo.put("dept_no", item.get("dept_no"));
							
							flag = 1 ;
							
							break ;
						}
					}
					
					if(flag == 0){
						err_sb.append("部门编码不存在或已停用,输入错误;");
					}
					
					map.put("dept_code",temp[1]);
					map.put("dept_name",temp[2]);
				
				} else {
					
					err_sb.append("部门编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,3)) {
					
					int flag = 0 ;
					
					for(Map<String,Object> item : payItemData){
						
						if(temp[3].equals(item.get("payment_item_code"))){
							
							mapVo.put("payment_item_id", item.get("payment_item_id"));
							
							mapVo.put("payment_item_no", item.get("payment_item_no"));
							
							flag = 1;
							
							break ;
						}
					}
					
					if(flag == 0 ){
						err_sb.append("支出项目编码不存在或已停用,输入错误;");
					}
					
					map.put("payment_item_code",temp[3]);
					
					map.put("payment_item_name",temp[4]);
				
				} else {
					
					err_sb.append("支出项目编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,5)) {
					
					map.put("fix_amount",Double.valueOf(temp[5]));
		    		mapVo.put("fix_amount", temp[5]);
					
				} else {
					
					err_sb.append("定额支出为空;");
					
				}
				 
				//校验数据 是否存在
				int count = budgExpFixAmountService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					map.put("error_type",err_sb.toString());
					
					list_err.add(map);
					
				} else {
					
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				if(addList.size() > 0 ){
					
					String json = budgExpFixAmountService.addBatch(addList);
					
				}else{
					
					Map<String,Object> map = new HashMap<String,Object>();
					
					map.put("error_type", "没有数据，无法导入.");
					
					list_err.add(map);
					
				}
			}
			
		} catch (DataAccessException e) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("error_type","导入系统出错");
			
			list_err.add(map);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
	
}
