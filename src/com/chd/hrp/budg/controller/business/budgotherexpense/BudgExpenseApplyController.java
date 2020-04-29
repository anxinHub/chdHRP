/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.business.budgotherexpense;
 
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
import com.chd.hrp.budg.entity.BudgExpenseApply;
import com.chd.hrp.budg.service.base.budgdept.BudgDeptSetService;
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpApplyDetailService;
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpenseApplyService;
 
/**
 * 
 * @Description: 费用申报
 * @Table: BUDG_EXPENSE_APPLY
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgExpenseApplyController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgExpenseApplyController.class);

	// 引入Service服务
	@Resource(name = "budgExpenseApplyService")
	private final BudgExpenseApplyService budgExpenseApplyService = null;
	
	@Resource(name = "budgExpApplyDetailService")
	private final BudgExpApplyDetailService budgExpApplyDetailService = null;
	@Resource(name = "budgNoManagerService")
	private final BudgNoManagerService budgNoManagerService = null;

	@Resource(name = "budgDeptSetService")
	private final BudgDeptSetService budgDeptSetService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyMainPage", method = RequestMethod.GET)
	public String budgExpenseApplyMainPage(Model mode) throws Exception {
		return "hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyAddPage", method = RequestMethod.GET)
	public String budgExpenseApplyAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String emp_id = SessionManager.getEmpCode();
		mapVo.put("emp_id", emp_id != null ? emp_id : "");
		
		//当前用户对应职工及职工所属科室
		Map<String,Object> emp = budgExpenseApplyService.queryEmpDictByCode(mapVo);
		
		mode.addAllAttributes(emp);
		
		return "hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyAdd";
	}

	/**
	 * @Description 添加数据 费用申报
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/addBudgExpenseApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgExpenseApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		mapVo.put("make_date", new Date());
		
		String budgExpenseApplyJson = budgExpenseApplyService.add(mapVo);

		return JSONObject.parseObject(budgExpenseApplyJson);

	}

	/**
	 * @Description 更新跳转页面 费用申报
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyUpdatePage", method = RequestMethod.GET)
	public String budgExpenseApplyUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		BudgExpenseApply budgExpenseApply = budgExpenseApplyService.queryByCode(mapVo);
		mode.addAttribute(budgExpenseApply);
		return "hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyUpdate";
	}
	
	/**
	 * @Description 更新数据 费用申报
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/updateBudgExpenseApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgExpenseApply(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String budgExpenseApplyJson = budgExpenseApplyService.update(mapVo);

		return JSONObject.parseObject(budgExpenseApplyJson);
	}

	
	/**
	 * @Description 删除数据  费用申报
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/deleteBudgExpenseApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgExpenseApply(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("apply_id", ids[4]);

			listVo.add(mapVo);

		}

		String budgExpenseApplyJson = budgExpenseApplyService.deleteBatch(listVo);

		return JSONObject.parseObject(budgExpenseApplyJson);

	}

	/**
	 * @Description 查询数据  费用申报
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/queryBudgExpenseApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgExpenseApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("user_id", SessionManager.getUserId());
		String budgExpenseApplyList = budgExpenseApplyService.query(getPage(mapVo));

		return JSONObject.parseObject(budgExpenseApplyList);

	}
	
	/**
	 * @Description 查询数据  费用申报明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/queryBudgExpApplyDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgExpApplyDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgExpApplyDelList = budgExpenseApplyService.queryBudgExpApplyDetail(mapVo);

		return JSONObject.parseObject(budgExpApplyDelList);

	}
	/**
	 * @Description 查询数据  状态
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>v
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/queryBudgExpenseApplyState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgExpenseApplyState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		List<String> list= budgExpenseApplyService.queryBudgExpenseApplyState(getPage(mapVo));
		
		if(list.size() == 0){
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}else{
			String  str = "" ;
			for(String item : list){
				str += item +"," ;
			}
			return JSONObject.parseObject("{\"apply_id\":\""+str+"\",\"state\":\"false\"}");
		}
	}
		
	
	/**
	 * 审核、销审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/auditOrUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditOrUnAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("apply_id", ids[1]);
			mapVo.put("state",ids[2]);
			//审核
			switch(ids[2]){
				case "01":
					mapVo.put("checker","");
					mapVo.put("check_date","");
					break;
				case "02":
					mapVo.put("checker",SessionManager.getUserId());
					mapVo.put("check_date",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
					break;
				 
			}
			listVo.add(mapVo);			
		}
		String matOrderMain = budgExpenseApplyService.auditOrUnAudit(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
	
	/**
	 * 审核、销审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/affiOrUnAffi", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> affiOrUnAffi(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("apply_id", ids[1]);
			mapVo.put("state",ids[2]);
			//审核
			switch(ids[2]){
				case "02":
					mapVo.put("affi_emp","");
					mapVo.put("affi_date","");
					break;
				case "03":
					mapVo.put("affi_emp",SessionManager.getUserId());
					mapVo.put("affi_date",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
					break;
				 
			}
			listVo.add(mapVo);			
		}
		String matOrderMain = budgExpenseApplyService.affiOrUnAffi(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
	/**
	 * 支出项目 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/queryBudgPaymentItem", method = RequestMethod.POST)
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
		return budgExpenseApplyService.queryBudgPaymentItem(mapVo);

	}
	
	/**
	 * 申报人员 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/queryEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		return budgExpenseApplyService.queryEmpDict(mapVo);

	}
	@RequestMapping(value="/hrp/budg/business/budgotherexpense/expenseapply/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate1(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","支出预算其他费用申报.xls");
	    
	    return null; 
	 }
	@RequestMapping(value="/hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyImportPage", method = RequestMethod.GET)  
	 public String budgExpenseApplyImportPage(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
		return "hrp/budg/business/budgotherexpense/expenseapply/budgExpenseApplyImport";			
	 }
	 /**
		 * @Description 
		 * 导入数据 预算科目与会计科目对应关系表
		 * @param  plupload
		 * @param  request
		 * @param  response
		 * @param  mode
		 * @return String
		 * @throws IOException
		*/
		 @RequestMapping(value="/hrp/budg/business/budgotherexpense/expenseapply/readBudgExpenseApplyFiles",method = RequestMethod.POST)  
		 public String readBudgSubjChargeKindFiles(Plupload plupload,HttpServletRequest request,
	   		HttpServletResponse response,Model mode) throws IOException { 
				 
			List<String> list_err = new ArrayList<String>();
			
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			try {
				String group_id=SessionManager.getGroupId();
				String hos_id=SessionManager.getHosId();
				String copy_code=SessionManager.getCopyCode();
				for (int i = 1; i < list.size(); i++) {
					
					StringBuffer err_sb = new StringBuffer();
										
					String temp[] = list.get(i);// 行
					Map<String, Object> mapVo = new HashMap<String, Object>();
					mapVo.put("group_id", group_id);
					mapVo.put("hos_id", hos_id);
					mapVo.put("copy_code", copy_code);
					
					for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])&& temp[3].equals(error[3])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
					mapVo.put("table_name", "费用申报");
					mapVo.put("prefixe", "FYSB");
					mapVo.put("table_code", "BUDG_EXPENSE_APPLY");
					String apply_id = budgNoManagerService.getBillNOSeqNo(mapVo);
					budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_EXPENSE_APPLY");
						mapVo.put("apply_id", apply_id);
						if (StringTool.isNotBlank(temp[0])) {
							
							mapVo.put("budg_year", temp[0]);
							
						} else {
							
							err_sb.append("预算年度为空;");
							
						}
						
						if (StringTool.isNotBlank(temp[1])) {
							Map<String, Object> map_dept = new HashMap<String, Object>();
							map_dept.put("group_id", group_id);
							map_dept.put("hos_id", hos_id);
							map_dept.put("copy_code", copy_code);
							map_dept.put("dept_code", temp[1]);
							
							Map<String,Object> dept  = budgExpenseApplyService.queryBudgDeptByCode(map_dept);
							
							if(dept!=null) {
								mapVo.put("apply_dept", dept.get("dept_id"));
							}else {
								err_sb.append("科目编码不存在;");
							}
			    		
						} else {
							
							err_sb.append("科目编码为空;");
							
						}
						
						if (StringTool.isNotBlank(temp[2])) {
							Map<String, Object> map_emp = new HashMap<String, Object>();
							map_emp.put("group_id", group_id);
							map_emp.put("hos_id", hos_id);
							map_emp.put("copy_code", copy_code);
							map_emp.put("emp_code", temp[2]);
							
							Map<String,Object> emp  = budgExpenseApplyService.queryBudgEmpByCode(map_emp);
							
							if(emp!=null) {
								mapVo.put("apply_emp", emp.get("emp_id"));
							}else {
								err_sb.append("申报人员编码为空;");
							}
							
						} else {
							
							err_sb.append("申报人员编码为空;");
							
						}
						if (StringTool.isNotBlank(temp[3])) {
							
							Map<String, Object> map_item = new HashMap<String, Object>();
							map_item.put("group_id", group_id);
							map_item.put("hos_id", hos_id);
							map_item.put("copy_code", copy_code);
							map_item.put("payment_item_code", temp[3]);
							
							Map<String,Object> item  = budgExpenseApplyService.queryBudgPaymentItemByCode(map_item);
							
							if(item!=null) {
								mapVo.put("payment_item_id",item.get("payment_item_id"));
								mapVo.put("payment_item_no",item.get("payment_item_no"));
								mapVo.put("payment_item_code",item.get("payment_item_code"));
								mapVo.put("payment_item_name",item.get("payment_item_name"));
							}else {
								err_sb.append("申报人员编码为空;");
							}
							
						} else {
							
							err_sb.append("支出项目编码为空;");
							
						}
						
						if (StringTool.isNotBlank(temp[4])) {
							
							mapVo.put("budg_amount", temp[4]);
							
							 
						} else {
							
							err_sb.append("预算金额为空;");
							
						}
						
						mapVo.put("state", "01");
						mapVo.put("make_date", DateUtil.getCurrenDate("yyyy-MM-dd"));
						
					 
					if (err_sb.toString().length() > 0) {
												
						list_err.add(err_sb.toString());
						
					} else {
						
						addList.add(mapVo);
						
						
					}
					
				}
				budgExpenseApplyService.addBatch(addList);
				
				//处理明显数据 12个月均摊
				/*
				double i_amount=0;
				double amount_sum=0;
				double amount_avg=0;
				for (Map<String, Object> map : addList) {
					amount_sum=Double.parseDouble(map.get("budg_amount").toString());
					BigDecimal bi1 = new BigDecimal(map.get("budg_amount").toString());
					BigDecimal bi2 = new BigDecimal("12");
					BigDecimal divide = bi1.divide(bi2, 2, RoundingMode.HALF_UP);
					amount_avg=divide.doubleValue();
					for (int i = 1; i <= 12; i++) {
						
						Map<String, Object> mk = new HashMap<String, Object>();
						mk.put("group_id", group_id);
						mk.put("hos_id", hos_id);
						mk.put("copy_code", copy_code);
						mk.put("apply_id", map.get("apply_id"));
						mk.put("budg_year", map.get("budg_year"));
						mk.put("reson", " ");
						
						if(i<10) {
							mk.put("month", "0"+i);
							mk.put("amount", amount_avg);
						}else {
							mk.put("month", i);
							if(i==12) {
								mk.put("amount", NumberUtil.sub(amount_sum, i_amount, 4));
							}else {
								mk.put("amount", amount_avg);
							}
						}
						budgExpApplyDetailService.add(mk);
						i_amount=NumberUtil.add(i_amount, amount_avg, 2);
					}
					
				}
				*/
			 
			} catch (DataAccessException e) {
				list_err.add("导入系统出错");
				
			}
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
			
	   } 
	/**
	 * 归口科室 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/expenseapply/queryBudgCostDutyDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCostDutyDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgExpenseApplyService.queryBudgCostDutyDept(mapVo);

	}

}
