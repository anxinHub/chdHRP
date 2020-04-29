/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.invdisburse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgFreeMedMatCost;
import com.chd.hrp.budg.service.budgcost.empwagecost.BudgEmpWageTotalService;
import com.chd.hrp.budg.service.business.invdisburse.BudgChargeMatCostService;
import com.chd.hrp.budg.service.business.invdisburse.BudgFreeMedMatCostService;
import com.chd.hrp.budg.service.common.BudgCommonInfoService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_FREE_MED_MAT_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgFreeMedMatCostController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgFreeMedMatCostController.class);
	
	//引入Service服务
	@Resource(name = "budgFreeMedMatCostService")
	private final BudgFreeMedMatCostService budgFreeMedMatCostService = null;
	
	//引入Service服务(导入使用)
	@Resource(name = "budgEmpWageTotalService")
	private final BudgEmpWageTotalService budgEmpWageTotalService = null;
	
	//引入Service服务(导入使用)
	@Resource(name = "budgCommonInfoService")
	private final BudgCommonInfoService budgCommonInfoService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/budgFreeMedMatCostMainPage", method = RequestMethod.GET)
	public String budgFreeMedMatCostMainPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgfreemedmatcost/budgFreeMedMatCostMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/budgFreeMedMatCostAddPage", method = RequestMethod.GET)
	public String budgFreeMedMatCostAddPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgfreemedmatcost/budgFreeMedMatCostAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/addBudgFreeMedMatCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgFreeMedMatCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
    	mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
       
		String budgFreeMedMatCostJson = budgFreeMedMatCostService.add(mapVo);

		return JSONObject.parseObject(budgFreeMedMatCostJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/budgFreeMedMatCostUpdatePage", method = RequestMethod.GET)
	public String budgFreeMedMatCostUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
    
		Map<String,Object> budgFreeMedMatCostMap = budgFreeMedMatCostService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgFreeMedMatCostMap.get("group_id"));
		mode.addAttribute("hos_id", budgFreeMedMatCostMap.get("hos_id"));
		mode.addAttribute("copy_code", budgFreeMedMatCostMap.get("copy_code"));
		mode.addAttribute("year", budgFreeMedMatCostMap.get("year"));
		mode.addAttribute("month", budgFreeMedMatCostMap.get("month"));
		mode.addAttribute("dept_id", budgFreeMedMatCostMap.get("dept_id"));
		mode.addAttribute("dept_name", budgFreeMedMatCostMap.get("dept_name"));
		mode.addAttribute("nature", budgFreeMedMatCostMap.get("nature"));
		mode.addAttribute("value_name", budgFreeMedMatCostMap.get("value_name"));
		mode.addAttribute("mat_type_id", budgFreeMedMatCostMap.get("mat_type_id"));
		mode.addAttribute("mat_type_name", budgFreeMedMatCostMap.get("mat_type_name"));
		mode.addAttribute("amount", budgFreeMedMatCostMap.get("amount"));
		
		return "hrp/budg/business/invdisburse/budgfreemedmatcost/budgFreeMedMatCostUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/updateBudgFreeMedMatCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgFreeMedMatCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgFreeMedMatCostJson = budgFreeMedMatCostService.update(mapVo);
		
		return JSONObject.parseObject(budgFreeMedMatCostJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/addOrUpdateBudgFreeMedMatCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgFreeMedMatCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgFreeMedMatCostJson ="";
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
	  
		budgFreeMedMatCostJson = budgFreeMedMatCostService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(budgFreeMedMatCostJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/deleteBudgFreeMedMatCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgFreeMedMatCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("month", ids[4])   ;
				mapVo.put("dept_id", ids[5])   ;
				mapVo.put("nature", ids[6])   ;
				mapVo.put("mat_type_id", ids[7]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgFreeMedMatCostJson = budgFreeMedMatCostService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgFreeMedMatCostJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/queryBudgFreeMedMatCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgFreeMedMatCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String budgFreeMedMatCost = budgFreeMedMatCostService.query(getPage(mapVo));

		return JSONObject.parseObject(budgFreeMedMatCost);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/budgFreeMedMatCostImportPage", method = RequestMethod.GET)
	public String budgFreeMedMatCostImportPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgfreemedmatcost/budgFreeMedMatCostImport";

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
	 @RequestMapping(value="/hrp/budg/business/invdisburse/budgfreemedmatcost/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\cost\\mat","科室非收费医用材料支出.xls");
	    
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
	@RequestMapping(value="/hrp/budg/business/invdisburse/budgfreemedmatcost/readBudgFreeMedMatCostFiles",method = RequestMethod.POST)  
    public String readBudgFreeMedMatCostFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"}; 
		
		String[] natureData = {"01","02","03"}; 
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		 
		paraMap.put("hos_id", SessionManager.getHosId());   
		 
		paraMap.put("copy_code", SessionManager.getCopyCode());   
		
		//查询 科室基本信息(根据编码 匹配ID用) 预算科室
		List<Map<String,Object>> deptList =  budgCommonInfoService.queryDeptData(paraMap) ;
		
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
					
					if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])&& temp[4].equals(error[4])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
				 
	    		if (ExcelReader.validExceLColunm(temp, 0)) {
					
					map.put("year",temp[0]);
					
		    		mapVo.put("year", temp[0]);
				
				} else {
					
					err_sb.append("年度为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,1)) {
					
					map.put("month",temp[1]);
					
		    		
					if(!Arrays.asList(monthData).contains(temp[1])){
						
						err_sb.append("月份输入不合法（两位数字01-12）;");
						
					}else{
						
						mapVo.put("month", temp[1]);
					}
				
				} else {
					
					err_sb.append("月份为空 ;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,2)) {
					
					map.put("dept_code",temp[2]);
					map.put("dept_name", temp[3]);
					
					int flag = 0 ;
					
					for(Map<String,Object> item : deptList){
						
						if(temp[2].equals(item.get("dept_code"))){
							
							map.put("dept_id", String.valueOf(item.get("dept_id")));
				    		mapVo.put("dept_id", String.valueOf(item.get("dept_id")));
				    		flag = 1;
				    		
				    		break ;
						}
						
					}
					
					if(flag == 0){
						err_sb.append("科室编码输入错误,不存在该科室;");
					}
				
				} else {
					
					err_sb.append("科室编码为空;");
					
				}
				
				if (ExcelReader.validExceLColunm(temp,4)) {
					
					map.put("nature",temp[4]);
					
					if(!Arrays.asList(natureData).contains(temp[4])){
						
						err_sb.append("属性输入不合法(01:门诊,02:住院,03:检查.只输入代码即可);");
						
					}else{
						
			    		mapVo.put("nature", temp[4]);
					}
					
					
				} else {
					
					err_sb.append("属性为空;");
						
				}
				
				if (ExcelReader.validExceLColunm(temp,5)) {
					
					map.put("mat_type_code",temp[5]);
					map.put("mat_type_name", temp[6]);
					
					mapVo.put("mat_type_code",temp[5]);
					mapVo.put("no_medical","0");//医用
					//查询 预算物质材料基本信息(根据编码 匹配ID用)
					Map<String,Object> typeMap = budgCommonInfoService.queryBudgMatType(mapVo) ;
					
						
					if(typeMap != null ){
						
						map.put("mat_type_id", String.valueOf(typeMap.get("mat_type_id")));
			    		mapVo.put("mat_type_id", String.valueOf(typeMap.get("mat_type_id")));
			    		
					}else{
						err_sb.append("物资类别编码输入错误,不存在该物资类别或输入年度该物资类别与预算科目对应关系不存在;");
					}
					
				} else {
					
					err_sb.append("物资类别编码为空;");
					
				}
				 
				
				if (ExcelReader.validExceLColunm(temp,7)) {
					
					map.put("amount",Double.valueOf(temp[7]));
		    		mapVo.put("amount", temp[7]);
				
				} else {
					
					err_sb.append("金额为空;");
					
				}
				 
				int count = budgFreeMedMatCostService.queryDataExist(mapVo);
				
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
					
					String dataJson = budgFreeMedMatCostService.addBatch(addList);
					
				}else{
					
					Map<String, Object> data_exc = new HashMap<String, Object>();
					
					data_exc.put("error_type","没有数据,导入失败!");
					
					list_err.add(data_exc);
				}
				
			}
			
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/addBatchBudgFreeMedMatCost", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgFreeMedMatCost(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgFreeMedMatCost> list_err = new ArrayList<BudgFreeMedMatCost>();
		
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
			
			BudgFreeMedMatCost budgFreeMedMatCost = new BudgFreeMedMatCost();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgFreeMedMatCost.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgFreeMedMatCost.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgFreeMedMatCost.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("nature"))) {
						
					budgFreeMedMatCost.setNature((String)jsonObj.get("nature"));
		    		mapVo.put("nature", jsonObj.get("nature"));
		    		} else {
						
						err_sb.append("属性为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("mat_type_id"))) {
						
					budgFreeMedMatCost.setMat_type_id(Long.valueOf((String)jsonObj.get("mat_type_id")));
		    		mapVo.put("mat_type_id", jsonObj.get("mat_type_id"));
		    		} else {
						
						err_sb.append("物资类别ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("amount"))) {
						
					budgFreeMedMatCost.setAmount(Double.valueOf((String)jsonObj.get("amount")));
		    		mapVo.put("amount", jsonObj.get("amount"));
		    		} else {
						
						err_sb.append("金额为空  ");
						
					}
					
					
				BudgFreeMedMatCost data_exc_extis = budgFreeMedMatCostService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgFreeMedMatCost.setError_type(err_sb.toString());
					
					list_err.add(budgFreeMedMatCost);
					
				} else {
			  
					String dataJson = budgFreeMedMatCostService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgFreeMedMatCost data_exc = new BudgFreeMedMatCost();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
	/**
	 * @Description 
	 * 科室非收费医用材料支出 数据采集（系统内部数据采集）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgfreemedmatcost/collectData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgChargeMatCostJson = null ;
		try {
			
			budgChargeMatCostJson = budgFreeMedMatCostService.collectData(mapVo);
					
		} catch (Exception e) {

			budgChargeMatCostJson = e.getMessage() ;
		}
		
		return JSONObject.parseObject(budgChargeMatCostJson);
	}
}

