/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.empbonus;
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
import com.chd.hrp.budg.entity.BudgEmpTypeAwards;
import com.chd.hrp.budg.service.empbonus.BudgEmpTypeAwardsService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_EMP_TYPE_AWARDS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgEmpTypeAwardsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgEmpTypeAwardsController.class);
	
	//引入Service服务
	@Resource(name = "budgEmpTypeAwardsService")
	private final BudgEmpTypeAwardsService budgEmpTypeAwardsService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/budgEmpTypeAwardsMainPage", method = RequestMethod.GET)
	public String budgEmpTypeAwardsMainPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgemptypeawards/budgEmpTypeAwardsMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/budgEmpTypeAwardsAddPage", method = RequestMethod.GET)
	public String budgEmpTypeAwardsAddPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgemptypeawards/budgEmpTypeAwardsAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/addBudgEmpTypeAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgEmpTypeAwards(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgEmpTypeAwardsJson = budgEmpTypeAwardsService.add(mapVo);

		return JSONObject.parseObject(budgEmpTypeAwardsJson);
		
	}
	/**
	 * @Description 
	 * 按年度生成奖金平均值
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/collectBudgYearEmpTypeAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgYearEmpTypeAwards(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		
		String budgEmpTypeAwardsJson = budgEmpTypeAwardsService.collectBudgYearEmpTypeAwards(mapVo);
		
		return JSONObject.parseObject(budgEmpTypeAwardsJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/budgEmpTypeAwardsUpdatePage", method = RequestMethod.GET)
	public String budgEmpTypeAwardsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgEmpTypeAwards budgEmpTypeAwards = new BudgEmpTypeAwards();
    
		budgEmpTypeAwards = budgEmpTypeAwardsService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgEmpTypeAwards.getGroup_id());
		mode.addAttribute("hos_id", budgEmpTypeAwards.getHos_id());
		mode.addAttribute("copy_code", budgEmpTypeAwards.getCopy_code());
		mode.addAttribute("year", budgEmpTypeAwards.getYear());
		mode.addAttribute("dept_id", budgEmpTypeAwards.getDept_id());
		mode.addAttribute("emp_type_code", budgEmpTypeAwards.getEmp_type_code());
		mode.addAttribute("awards_item_code", budgEmpTypeAwards.getAwards_item_code());
		mode.addAttribute("amount", budgEmpTypeAwards.getAmount());
		
		return "hrp/budg/empbonus/budgemptypeawards/budgEmpTypeAwardsUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/updateBudgEmpTypeAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgEmpTypeAwards(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgEmpTypeAwardsJson = budgEmpTypeAwardsService.update(mapVo);
		
		return JSONObject.parseObject(budgEmpTypeAwardsJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/addOrUpdateBudgEmpTypeAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgEmpTypeAwards(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgEmpTypeAwardsJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		budgEmpTypeAwardsJson = budgEmpTypeAwardsService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgEmpTypeAwardsJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/deleteBudgEmpTypeAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgEmpTypeAwards(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("dept_id", ids[4])   ;
				mapVo.put("emp_type_code", ids[5])   ;
				mapVo.put("awards_item_code", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgEmpTypeAwardsJson = budgEmpTypeAwardsService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgEmpTypeAwardsJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/queryBudgEmpTypeAwards", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgEmpTypeAwards(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgEmpTypeAwards = budgEmpTypeAwardsService.query(getPage(mapVo));

		return JSONObject.parseObject(budgEmpTypeAwards);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/budgEmpTypeAwardsImportPage", method = RequestMethod.GET)
	public String budgEmpTypeAwardsImportPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgemptypeawards/budgEmpTypeAwardsImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 tabledesc
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/empbonus/budgemptypeawards/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","tabledesc.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/empbonus/budgemptypeawards/readBudgEmpTypeAwardsFiles",method = RequestMethod.POST)  
    public String readBudgEmpTypeAwardsFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgEmpTypeAwards> list_err = new ArrayList<BudgEmpTypeAwards>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgEmpTypeAwards budgEmpTypeAwards = new BudgEmpTypeAwards();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgEmpTypeAwards.setYear(temp[3]);
		    		mapVo.put("year", temp[3]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgEmpTypeAwards.setDept_id(Long.valueOf(temp[4]));
		    		mapVo.put("dept_id", temp[4]);
					
					} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgEmpTypeAwards.setEmp_type_code(temp[5]);
		    		mapVo.put("emp_type_code", temp[5]);
					
					} else {
						
						err_sb.append("职工类别编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgEmpTypeAwards.setAwards_item_code(temp[6]);
		    		mapVo.put("awards_item_code", temp[6]);
					
					} else {
						
						err_sb.append("奖金项目编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgEmpTypeAwards.setAmount(Double.valueOf(temp[7]));
		    		mapVo.put("amount", temp[7]);
					
					} else {
						
						err_sb.append("金额为空  ");
						
					}
					 
					
				BudgEmpTypeAwards data_exc_extis = budgEmpTypeAwardsService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgEmpTypeAwards.setError_type(err_sb.toString());
					
					list_err.add(budgEmpTypeAwards);
					
				} else {
			  
					String dataJson = budgEmpTypeAwardsService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgEmpTypeAwards data_exc = new BudgEmpTypeAwards();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgemptypeawards/addBatchBudgEmpTypeAwards", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgEmpTypeAwards(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgEmpTypeAwards> list_err = new ArrayList<BudgEmpTypeAwards>();
		
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
			
			BudgEmpTypeAwards budgEmpTypeAwards = new BudgEmpTypeAwards();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgEmpTypeAwards.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgEmpTypeAwards.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("emp_type_code"))) {
						
					budgEmpTypeAwards.setEmp_type_code((String)jsonObj.get("emp_type_code"));
		    		mapVo.put("emp_type_code", jsonObj.get("emp_type_code"));
		    		} else {
						
						err_sb.append("职工类别编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("awards_item_code"))) {
						
					budgEmpTypeAwards.setAwards_item_code((String)jsonObj.get("awards_item_code"));
		    		mapVo.put("awards_item_code", jsonObj.get("awards_item_code"));
		    		} else {
						
						err_sb.append("奖金项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("amount"))) {
						
					budgEmpTypeAwards.setAmount(Double.valueOf((String)jsonObj.get("amount")));
		    		mapVo.put("amount", jsonObj.get("amount"));
		    		} else {
						
						err_sb.append("金额为空  ");
						
					}
					
					
				BudgEmpTypeAwards data_exc_extis = budgEmpTypeAwardsService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgEmpTypeAwards.setError_type(err_sb.toString());
					
					list_err.add(budgEmpTypeAwards);
					
				} else {
			  
					String dataJson = budgEmpTypeAwardsService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgEmpTypeAwards data_exc = new BudgEmpTypeAwards();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

