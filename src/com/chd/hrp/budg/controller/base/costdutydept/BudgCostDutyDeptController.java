/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.costdutydept;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgCostDutyDept;
import com.chd.hrp.budg.entity.BudgPaymentItemCostShip;
import com.chd.hrp.budg.service.base.budgwage.BudgWageItemCostShipService;
import com.chd.hrp.budg.service.base.costdutydept.BudgCostDutyDeptService;

/**
 * 
 * @Description: 支出预算归口设置 
 * @Table: BUDG_COST_DUTY_DEPT
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgCostDutyDeptController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgCostDutyDeptController.class);
	
	//引入Service服务
	@Resource(name = "budgCostDutyDeptService")
	private final BudgCostDutyDeptService budgCostDutyDeptService = null;
   
	@Resource(name = "budgWageItemCostShipService")
	private final BudgWageItemCostShipService budgWageItemCostShipService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/costdutydept/budgCostDutyDeptMainPage", method = RequestMethod.GET)
	public String budgCostDutyDeptMainPage(Model mode) throws Exception {
		return "/hrp/budg/base/costdutydept/budgCostDutyDeptMain";
	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/costdutydept/budgCostDutyDeptAddPage", method = RequestMethod.GET)
	public String budgCostDutyDeptAddPage(Model mode) throws Exception {
		return "/hrp/budg/base/costdutydept/budgCostDutyDeptAdd";
	}
	
	/**
	 * @Description 
	 * 更新页面跳转 
	 * @param  mapVo input
	 * @param  mode  output
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/costdutydept/budgCostDutyDeptUpdatePage", method = RequestMethod.GET)
	public String budgCostDutyDeptUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
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
		Map<String,Object> budgCostDutyDept = budgCostDutyDeptService.queryByCode(mapVo);
		mode.addAllAttributes(budgCostDutyDept);
		return "/hrp/budg/base/costdutydept/budgCostDutyDeptUpdate";
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/costdutydept/addBudgCostDutyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgCostDutyDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		return JSONObject.parseObject(budgCostDutyDeptService.addOrUpdate(mapVo));
	}
	
	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/costdutydept/updateBudgCostDutyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCostDutyDept(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		return JSONObject.parseObject(budgCostDutyDeptService.update(mapVo));
	}
	
	/**
	 * @Description 删除数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/costdutydept/deleteBudgCostDutyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgCostDutyDept(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("subj_code", ids[5]);
			mapVo.put("duty_dept_id", ids[6]);

			listVo.add(mapVo);

		}

		return JSONObject.parseObject(budgCostDutyDeptService.deleteBatch(listVo));

	}

	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/costdutydept/queryBudgCostDutyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCostDutyDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return JSONObject.parseObject(budgCostDutyDeptService.query(getPage(mapVo)));

	}
	
	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/costdutydept/queryAccDeptAttrData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccDeptAttrData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return JSONObject.parseObject(budgCostDutyDeptService.queryAccDeptAttrData(mapVo));

	}
	
	/**
	 * @Description 继承数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/costdutydept/extendBudgCostDutyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendBudgCostDutyDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return JSONObject.parseObject(budgCostDutyDeptService.copyBudgCostDutyDept(getPage(mapVo)));

	}
	
	/**
	 * 归口科室名称 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/costdutydept/queryAccDeptAttr", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccDeptAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgCostDutyDeptService.queryAccDeptAttr(mapVo);
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/costdutydept/budgCostDutyDeptImportPage", method = RequestMethod.GET)
	public String budgPaymentItemCostShipImportPage(Model mode) throws Exception {

		return "/hrp/budg/base/costdutydept/budgCostDutyDeptImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/base/costdutydept/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","支出预算归口设置模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/costdutydept/readBudgCostDutyDeptFiles",method = RequestMethod.POST)  
    public String readBudgCostDutyDeptFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgPaymentItemCostShip> list_err = new ArrayList<BudgPaymentItemCostShip>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgPaymentItemCostShip budgPaymentItemCostShip = new BudgPaymentItemCostShip();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])&& temp[3].equals(error[3])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					} 
					if (StringTool.isNotBlank(temp[0])) {
						
						budgPaymentItemCostShip.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgPaymentItemCostShip.setPayment_item_code(temp[1]);
						
			    		mapVo.put("dept_code", temp[1]);
		    		
			    		Map<String,Object> count = budgCostDutyDeptService.queryDeptExist(mapVo);
			    		
			    		if( count != null ){
			    			
			    			mapVo.put("dept_id", count.get("dept_id"));
			    			
			    		}else{
			    			
			    			err_sb.append("科室编码不存在或已停用;");
			    		}
					
					} else {
						
						err_sb.append("科室编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgPaymentItemCostShip.setSubj_code(temp[2]);
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgCostDutyDeptService.queryCostSubjByCode(mapVo);
						if(count == 0 ){
							err_sb.append("该年度支出预算科目不是末级科目或不存在;");
						}
					
					} else {
						
						err_sb.append("支出预算科目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgPaymentItemCostShip.setPayment_item_code(temp[3]);
						mapVo.put("duty_dept_code", temp[3]);
						
			    		Map<String,Object> count = budgCostDutyDeptService.queryAccDeptAttrExit(mapVo);
			    		
			    		if( count != null ){
			    			
			    			mapVo.put("duty_dept_id", count.get("dept_id"));
			    			
			    		}else{
			    			
			    			err_sb.append("归口科室编码不是职能科室或已停用;");
			    		}
					
					} else {
						
						err_sb.append("归口科室编码为空;");
						
					}
					 
					
				BudgCostDutyDept data_exc_extis = budgCostDutyDeptService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgPaymentItemCostShip.setError_type(err_sb.toString());
					
					list_err.add(budgPaymentItemCostShip);
					
				} else {
			  
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgCostDutyDeptService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			
			BudgPaymentItemCostShip data_exc = new BudgPaymentItemCostShip();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
	
}

