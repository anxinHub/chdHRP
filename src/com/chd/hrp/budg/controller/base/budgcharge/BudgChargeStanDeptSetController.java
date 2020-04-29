/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgcharge;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgChargeStanDeptSet;
import com.chd.hrp.budg.service.base.budgcharge.BudgChargeStanDeptSetService;
/**
 * 
 * @Description:
 * 费用标准对应科室维护
 * @Table:
 * BUDG_CHARGE_STAN_DEPT_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgChargeStanDeptSetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgChargeStanDeptSetController.class);
	
	//引入Service服务
	@Resource(name = "budgChargeStanDeptSetService")
	private final BudgChargeStanDeptSetService budgChargeStanDeptSetService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/budgChargeStanDeptSetMainPage", method = RequestMethod.GET)
	public String budgChargeStanDeptSetMainPage(Model mode) throws Exception {

		return "hrp/budg/budgchargestandeptset/budgChargeStanDeptSetMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/budgChargeStanDeptSetAddPage", method = RequestMethod.GET)
	public String budgChargeStanDeptSetAddPage(Model mode) throws Exception {

		return "hrp/budg/budgchargestandeptset/budgChargeStanDeptSetAdd";

	}

	/**
	 * @Description 
	 * 添加数据 费用标准对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/addBudgChargeStanDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgChargeStanDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgChargeStanDeptSetJson = budgChargeStanDeptSetService.add(mapVo);

		return JSONObject.parseObject(budgChargeStanDeptSetJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 费用标准对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/budgChargeStanDeptSetUpdatePage", method = RequestMethod.GET)
	public String budgChargeStanDeptSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgChargeStanDeptSet budgChargeStanDeptSet = new BudgChargeStanDeptSet();
    
		budgChargeStanDeptSet = budgChargeStanDeptSetService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgChargeStanDeptSet.getGroup_id());
		mode.addAttribute("hos_id", budgChargeStanDeptSet.getHos_id());
		mode.addAttribute("copy_code", budgChargeStanDeptSet.getCopy_code());
		mode.addAttribute("charge_stan_code", budgChargeStanDeptSet.getCharge_stan_code());
		mode.addAttribute("dept_id", budgChargeStanDeptSet.getDept_id());
		
		return "hrp/budg/budgchargestandeptset/budgChargeStanDeptSetUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 费用标准对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/updateBudgChargeStanDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgChargeStanDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgChargeStanDeptSetJson = budgChargeStanDeptSetService.update(mapVo);
		
		return JSONObject.parseObject(budgChargeStanDeptSetJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 费用标准对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/addOrUpdateBudgChargeStanDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgChargeStanDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgChargeStanDeptSetJson ="";
		

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
	  
		budgChargeStanDeptSetJson = budgChargeStanDeptSetService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgChargeStanDeptSetJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 费用标准对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/deleteBudgChargeStanDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgChargeStanDeptSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("charge_stan_code", ids[3])   ;
				mapVo.put("dept_id", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgChargeStanDeptSetJson = budgChargeStanDeptSetService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgChargeStanDeptSetJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 费用标准对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/queryBudgChargeStanDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeStanDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgChargeStanDeptSet = budgChargeStanDeptSetService.query(getPage(mapVo));

		return JSONObject.parseObject(budgChargeStanDeptSet);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 费用标准对应科室维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/budgChargeStanDeptSetImportPage", method = RequestMethod.GET)
	public String budgChargeStanDeptSetImportPage(Model mode) throws Exception {

		return "hrp/budg/budgchargestandeptset/budgChargeStanDeptSetImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 费用标准对应科室维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgchargestandeptset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","费用标准对应科室维护.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 费用标准对应科室维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgchargestandeptset/readBudgChargeStanDeptSetFiles",method = RequestMethod.POST)  
    public String readBudgChargeStanDeptSetFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgChargeStanDeptSet> list_err = new ArrayList<BudgChargeStanDeptSet>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgChargeStanDeptSet budgChargeStanDeptSet = new BudgChargeStanDeptSet();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgChargeStanDeptSet.setCharge_stan_code(temp[3]);
		    		mapVo.put("charge_stan_code", temp[3]);
					
					} else {
						
						err_sb.append("费用标准编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgChargeStanDeptSet.setDept_id(Long.valueOf(temp[4]));
		    		mapVo.put("dept_id", temp[4]);
					
					} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					 
					
				BudgChargeStanDeptSet data_exc_extis = budgChargeStanDeptSetService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgChargeStanDeptSet.setError_type(err_sb.toString());
					
					list_err.add(budgChargeStanDeptSet);
					
				} else {
			  
					String dataJson = budgChargeStanDeptSetService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgChargeStanDeptSet data_exc = new BudgChargeStanDeptSet();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 费用标准对应科室维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgchargestandeptset/addBatchBudgChargeStanDeptSet", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgChargeStanDeptSet(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgChargeStanDeptSet> list_err = new ArrayList<BudgChargeStanDeptSet>();
		
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
			
			BudgChargeStanDeptSet budgChargeStanDeptSet = new BudgChargeStanDeptSet();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("charge_stan_code"))) {
						
					budgChargeStanDeptSet.setCharge_stan_code((String)jsonObj.get("charge_stan_code"));
		    		mapVo.put("charge_stan_code", jsonObj.get("charge_stan_code"));
		    		} else {
						
						err_sb.append("费用标准编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgChargeStanDeptSet.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					
				BudgChargeStanDeptSet data_exc_extis = budgChargeStanDeptSetService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgChargeStanDeptSet.setError_type(err_sb.toString());
					
					list_err.add(budgChargeStanDeptSet);
					
				} else {
			  
					String dataJson = budgChargeStanDeptSetService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgChargeStanDeptSet data_exc = new BudgChargeStanDeptSet();
			
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

