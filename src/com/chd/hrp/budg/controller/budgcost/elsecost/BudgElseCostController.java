/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcost.elsecost;
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
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgElseCost;
import com.chd.hrp.budg.service.budgcost.eslecost.BudgElseCostService;
/**
 * 
 * @Description:
 * 其他支出预算
 * @Table:
 * BUDG_ELSE_INCOME
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgElseCostController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgElseCostController.class);
	
	//引入Service服务
	@Resource(name = "budgElseCostService")
	private final BudgElseCostService budgElseCostService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/budg/budgcost/elsecost/compilation/budgElseCostMainPage", method = RequestMethod.GET)
	public String budgElseCostMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/elsecost/compilation/budgElseCostMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/budgElseCostAddPage", method = RequestMethod.GET)
	public String budgElseCostAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/elsecost/compilation/budgElseCostAdd";

	}
	
	/**
	 * @Description 
	 * 批量设置页面 跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/budgElseCostUpdateBatchPage", method = RequestMethod.GET)
	public String budgElseCostUpdateBatchPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/elsecost/compilation/budgElseCostUpdateBatch";

	}
	
	
	/**
	 * @Description 
	 * 生成数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/addBudgElseCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgElseCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgElseCostJson = budgElseCostService.addBudgElseCost(mapVo);

		return JSONObject.parseObject(budgElseCostJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/budgElseCostUpdatePage", method = RequestMethod.GET)
	public String budgElseCostUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		Map<String,Object> budgElseCost = new HashMap<String,Object>();
    
		budgElseCost = budgElseCostService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgElseCost.get("group_id"));
		mode.addAttribute("hos_id", budgElseCost.get("hos_id"));
		mode.addAttribute("copy_code", budgElseCost.get("copy_code"));
		mode.addAttribute("budg_year", budgElseCost.get("budg_year"));
		mode.addAttribute("month", budgElseCost.get("month"));
		mode.addAttribute("subj_code", budgElseCost.get("subj_code"));
		mode.addAttribute("amount", budgElseCost.get("amount"));
		mode.addAttribute("remark", budgElseCost.get("remark"));
		
		return "hrp/budg/budgcost/elsecost/compilation/budgElseCostUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/updateBudgElseCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgElseCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
		String budgElseCostJson = budgElseCostService.update(mapVo);
		
		return JSONObject.parseObject(budgElseCostJson);
	}
	
	
	/**
	 * @Description 
	 * 批量设置  更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/updateBatchBudgElseCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchBudgElseCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
				
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());   
			mapVo.put("hos_id", SessionManager.getHosId());   
			mapVo.put("copy_code", SessionManager.getCopyCode());  
			mapVo.put("budg_year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("subj_code", ids[2]);
			mapVo.put("last_cost", ids[3]);
			
			if("-1".equals(ids[4])){
				
				mapVo.put("grow_rate", "");
				
			}else{
				
				mapVo.put("grow_rate", ids[4]);
				
				mapVo.put("budg_value", Double.parseDouble(ids[3])*(1+Double.parseDouble(ids[4])/100)) ;
			}
			
			if("-1".equals(ids[5])){
				
				mapVo.put("grow_value", "");
				
			}else{
				
				mapVo.put("grow_value", ids[5]);
				
				mapVo.put("budg_value", Double.parseDouble(ids[3])+ Double.parseDouble(ids[5])) ;
			}
			
			if("-1".equals(ids[6])){
				
				mapVo.put("remark", "");
				
			}else{
				
				mapVo.put("remark", ids[6]);
			}
			
			
			listVo.add(mapVo);
	    }
		
		String budgElseCostJson = null ;
		
		try {
			
			budgElseCostJson = budgElseCostService.updateBatch(listVo);
			
		} catch (Exception e) {
				
			budgElseCostJson = e.getMessage();

		}
		
		return JSONObject.parseObject(budgElseCostJson);
	}
	
	/**
	 * ***************暂时不用此方法*******************
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/addOrUpdateBudgElseCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgElseCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgElseCostJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());   
	
			detailVo.put("hos_id", SessionManager.getHosId());   
	
			detailVo.put("copy_code", SessionManager.getCopyCode());   
		  
	  
			budgElseCostJson = budgElseCostService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgElseCostJson);
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/saveBudgElseCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgElseCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
				
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());   
			mapVo.put("hos_id", SessionManager.getHosId());   
			mapVo.put("copy_code", SessionManager.getCopyCode());  
			mapVo.put("budg_year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("subj_code", ids[2]);
			mapVo.put("last_cost", ids[3]);
			mapVo.put("grow_rate", ids[4]);
			mapVo.put("grow_value", ids[5]);
			mapVo.put("budg_value", ids[6]);
			mapVo.put("remark", ids[7]);
			mapVo.put("rowNo", ids[8]);
			mapVo.put("flag", ids[9]);//添加、修改标识
			
			listVo.add(mapVo);
	    }
		
		String budgElseCostJson = null ;
		try {
			
			budgElseCostJson = budgElseCostService.saveBudgElseCost(listVo);
			
		} catch (Exception e) {
			
			budgElseCostJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgElseCostJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/deleteBudgElseCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgElseCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3]);
				mapVo.put("month", ids[4]);
				mapVo.put("subj_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgElseCostJson = budgElseCostService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgElseCostJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/queryBudgElseCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgElseCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if( mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
        
		
		String budgElseCost = budgElseCostService.query(getPage(mapVo));

		return JSONObject.parseObject(budgElseCost);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/budgElseCostImportPage", method = RequestMethod.GET)
	public String budgElseCostImportPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/elsecost/compilation/budgElseCostImport";

	}
	/**
	 * @Description 
	 * 下载  其他支出预算导入模版        
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgcost/elsecost/compilation/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","其他支出预算字典模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入  其他支出预算      
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgcost/elsecost/compilation/readBudgElseCostFiles",method = RequestMethod.POST)  
    public String readBudgElseCostFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgElseCost> list_err = new ArrayList<BudgElseCost>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgElseCost budgElseCost = new BudgElseCost();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【预算科目编码】重复;");
						}
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【预算科目名称】重复;");
						}	
					}
					if (StringTool.isNotBlank(temp[0])) {
						
						budgElseCost.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgElseCost.setMonth(temp[1]);
			    		mapVo.put("month", temp[1]);
			    		
			    		
			    		if(!Arrays.asList(monthData).contains(temp[1]) ){
			    			
			    			err_sb.append("月份输入不合法（必须两位数字）");
			    		}
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgElseCost.setSubj_code(temp[2]);
						
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgElseCostService.querySubjCodeExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("预算科目编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("预算科目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgElseCost.setGrow_rate(Double.valueOf(temp[3]));
			    		mapVo.put("grow_rate", temp[3]);
					
					} else {
						
						err_sb.append("金额为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgElseCost.setRemark(temp[4]);
						mapVo.put("remark", temp[4]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
				if (err_sb.toString().length() > 0) {
					
					budgElseCost.setError_type(err_sb.toString());
					
					list_err.add(budgElseCost);
					
				} else {
					
				  addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgElseCostService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgElseCost data_exc = new BudgElseCost();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
    
	
	/**
	 * 支出预算科目下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/queryBudgCostSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCostSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String code = budgElseCostService.queryBudgCostSubj(mapVo);
		return code;

	}
	
	/**
	 * 根据填写数据 查询 上年支出
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/compilation/setLastCost", method = RequestMethod.POST)
	@ResponseBody
	public String setLastCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		// 年度计算
		Integer budg_year = Integer.parseInt(String.valueOf(mapVo.get("budg_year"))) -1;
		
		mapVo.put("budg_year", budg_year) ;
		
		String code = budgElseCostService.setLastCost(mapVo);
		return code;

	}
}

