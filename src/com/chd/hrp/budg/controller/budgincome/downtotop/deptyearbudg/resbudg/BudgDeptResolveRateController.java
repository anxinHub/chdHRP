/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.downtotop.deptyearbudg.resbudg;
import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.budg.entity.BudgDeptResolveRate;
import com.chd.hrp.budg.service.budgincome.budgdeptresolverate.BudgDeptResolveRateService;


/**
 * 
 * @Description:
 * 科室医疗收入科目分解比例维护
 * @Table:
 * BUDG_DEPT_RESOLVE_RATE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDeptResolveRateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDeptResolveRateController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptResolveRateService")
	private final BudgDeptResolveRateService budgDeptResolveRateService = null;
   
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/budgDeptResolveRateAddPage", method = RequestMethod.GET)
	public String budgDeptResolveRateAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/budgDeptResolveRateAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/addBudgDeptResolveRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDeptResolveRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgDeptResolveRateJson = budgDeptResolveRateService.add(mapVo);

		return JSONObject.parseObject(budgDeptResolveRateJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/budgDeptResolveRateUpdatePage", method = RequestMethod.GET)
	public String budgDeptResolveRateUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgDeptResolveRate budgDeptResolveRate = new BudgDeptResolveRate();
    
		budgDeptResolveRate = budgDeptResolveRateService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptResolveRate.getGroup_id());
		mode.addAttribute("hos_id", budgDeptResolveRate.getHos_id());
		mode.addAttribute("copy_code", budgDeptResolveRate.getCopy_code());
		mode.addAttribute("year", budgDeptResolveRate.getYear());
		mode.addAttribute("subj_code", budgDeptResolveRate.getSubj_code());
		mode.addAttribute("last_year_income", budgDeptResolveRate.getLast_year_income());
		mode.addAttribute("grow_rate", budgDeptResolveRate.getGrow_rate());
		mode.addAttribute("resolve_rate", budgDeptResolveRate.getResolve_rate());
		mode.addAttribute("remark", budgDeptResolveRate.getRemark());
		
		return "hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/budgDeptResolveRateUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/updateBudgDeptResolveRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDeptResolveRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgDeptResolveRateJson = budgDeptResolveRateService.update(mapVo);
		
		return JSONObject.parseObject(budgDeptResolveRateJson);
	}
	
	/**
	 * @Description 
	 * 批量更新数据 科室年度医疗预算分解预算分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/updateResolveRateDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateResolveDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
			mapVo.put("dept_id", ids[2]);//科室ID
			mapVo.put("last_year_income", ids[3]);
			mapVo.put("resolve_rate", ids[4]);
			mapVo.put("grow_rate", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("remark", "");
			}else{
				
				mapVo.put("remark", ids[6]);
			}
			
			mapVo.put("count_value", "");
			//mapVo.put("", "");
			
			listVo.add(mapVo);
		}
	  
		String budgDeptResolveRateJson = budgDeptResolveRateService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgDeptResolveRateJson);
	}
	
	
	/**
	 * @Description 
	 * 更新数据 科室医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/addOrUpdateBudgDeptResolveRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDeptResolveRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptResolveRateJson ="";
		

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
	  
		budgDeptResolveRateJson = budgDeptResolveRateService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptResolveRateJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/deleteBudgDeptResolveRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDeptResolveRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("subj_code", ids[4]);
				mapVo.put("dept_id", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDeptResolveRateJson = budgDeptResolveRateService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDeptResolveRateJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/queryResolveRateDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResolveRateDYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgDeptResolveRate = budgDeptResolveRateService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDeptResolveRate);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室医疗收入科目分解比例维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveRateDYInBudgImportPage", method = RequestMethod.GET)
	public String budgDeptResolveRateImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveRateDYInBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室医疗收入科目分解比例维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveRatedownTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\downtotop","科室医疗收入科目分解比例维护.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室医疗收入科目分解比例维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/readResolveRateDYInBudgFiles",method = RequestMethod.POST)  
    public String readResolveRateDYInBudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgDeptResolveRate> list_err = new ArrayList<BudgDeptResolveRate>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String ,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgDeptResolveRate budgDeptResolveRate = new BudgDeptResolveRate();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (ExcelReader.validExceLColunm(temp,0)) {
						
					budgDeptResolveRate.setYear(temp[0]);
		    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
					budgDeptResolveRate.setSubj_code(temp[1]);
		    		mapVo.put("subj_code", temp[1]);
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						budgDeptResolveRate.setDept_id(Long.valueOf(temp[2]));
			    		mapVo.put("dept_id", temp[2]);
						
					} else {
						
						err_sb.append("部门ID为空;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
					budgDeptResolveRate.setLast_year_income(Double.valueOf(temp[3]));
		    		mapVo.put("last_year_income", temp[3]);
					
					} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
					budgDeptResolveRate.setGrow_rate(Double.valueOf(temp[4]));
		    		mapVo.put("grow_rate", temp[4]);
					
					} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
					budgDeptResolveRate.setResolve_rate(Double.valueOf(temp[5]));
		    		mapVo.put("resolve_rate", temp[5]);
					
					} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
					budgDeptResolveRate.setRemark(temp[6]);
		    		mapVo.put("remark", temp[6]);
					
					} else {
						
						err_sb.append("说明为空  ");
						
					}
					 
					
				BudgDeptResolveRate data_exc_extis = budgDeptResolveRateService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptResolveRate.setError_type(err_sb.toString());
					
					list_err.add(budgDeptResolveRate);
					
				} 
				addList.add(mapVo);
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgDeptResolveRateService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgDeptResolveRate data_exc = new BudgDeptResolveRate();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室医疗收入科目分解比例维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/addBatchBudgDeptResolveRate", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDeptResolveRate(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDeptResolveRate> list_err = new ArrayList<BudgDeptResolveRate>();
		
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
			
			BudgDeptResolveRate budgDeptResolveRate = new BudgDeptResolveRate();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgDeptResolveRate.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgDeptResolveRate.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
						
					budgDeptResolveRate.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
		    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgDeptResolveRate.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_rate"))) {
						
					budgDeptResolveRate.setResolve_rate(Double.valueOf((String)jsonObj.get("resolve_rate")));
		    		mapVo.put("resolve_rate", jsonObj.get("resolve_rate"));
		    		} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgDeptResolveRate.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgDeptResolveRate data_exc_extis = budgDeptResolveRateService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptResolveRate.setError_type(err_sb.toString());
					
					list_err.add(budgDeptResolveRate);
					
				} else {
			  
					String dataJson = budgDeptResolveRateService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDeptResolveRate data_exc = new BudgDeptResolveRate();
			
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

