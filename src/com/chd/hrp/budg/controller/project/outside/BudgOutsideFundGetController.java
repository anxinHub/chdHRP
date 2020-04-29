/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.project.outside;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgOutsideFundGet;
import com.chd.hrp.budg.service.project.outside.BudgOutsideFundGetService;
/**
 * 
 * @Description:
 * 外拨经费到账
 * @Table:
 * BUDG_OUTSIDE_FUND_GET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgOutsideFundGetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgOutsideFundGetController.class);
	
	//引入Service服务
	@Resource(name = "budgOutsideFundGetService")
	private final BudgOutsideFundGetService budgOutsideFundGetService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/budgOutsideFundGetMainPage", method = RequestMethod.GET)
	public String budgOutsideFundGetMainPage(Model mode) throws Exception {

		return "hrp/budg/project/outside/budgOutsideFundGetMain";

	}


	/**
	 * @Description 
	 * 添加数据 外拨经费到账
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/addBudgOutsideFundGet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgOutsideFundGet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
    	mapVo.put("acct_year",Calendar.getInstance().get(Calendar.YEAR));
		
       
		String budgOutsideFundGetJson = budgOutsideFundGetService.addBudgOutsideFundGet(mapVo);

		return JSONObject.parseObject(budgOutsideFundGetJson);
		
	}
	/**
	 * @Description 
	 * 外拨资金到账确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/outside/confirmAddOrUpdateBudgOutsideFundGet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmAddOrUpdateBudgOutsideFundGet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year",Calendar.getInstance().get(Calendar.YEAR));
		
		String budgOutsideFundGetJson = budgOutsideFundGetService.confirmAddOrUpdateBudgOutsideFundGet(mapVo);
		
		return JSONObject.parseObject(budgOutsideFundGetJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 外拨经费到账
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/budgOutsideFundGetUpdatePage", method = RequestMethod.GET)
	public String budgOutsideFundGetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgOutsideFundGet budgOutsideFundGet = new BudgOutsideFundGet();
    
		budgOutsideFundGet = budgOutsideFundGetService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgOutsideFundGet.getGroup_id());
		mode.addAttribute("hos_id", budgOutsideFundGet.getHos_id());
		mode.addAttribute("copy_code", budgOutsideFundGet.getCopy_code());
		mode.addAttribute("record_id", budgOutsideFundGet.getRecord_id());
		mode.addAttribute("remark", budgOutsideFundGet.getRemark());
		mode.addAttribute("get_date", budgOutsideFundGet.getGet_date());
		mode.addAttribute("income_subj", budgOutsideFundGet.getIncome_subj());
		mode.addAttribute("proj_id", budgOutsideFundGet.getProj_id());
		mode.addAttribute("proj_no", budgOutsideFundGet.getProj_no());
		mode.addAttribute("source_id", budgOutsideFundGet.getSource_id());
		mode.addAttribute("get_amount", budgOutsideFundGet.getGet_amount());
		
		return "hrp/budg/project/outside/budgOutsideFundGetUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 外拨经费到账
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/updateBudgOutsideFundGet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgOutsideFundGet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgOutsideFundGetJson = budgOutsideFundGetService.updateBudgOutsideFundGet(mapVo);
		
		return JSONObject.parseObject(budgOutsideFundGetJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 外拨经费到账
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/addOrUpdateBudgOutsideFundGet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgOutsideFundGet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgOutsideFundGetJson ="";
		

		mapVo.put("group_id", SessionManager.getGroupId());   
		
		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
		
	  
		budgOutsideFundGetJson = budgOutsideFundGetService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(budgOutsideFundGetJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 外拨经费到账
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/deleteBudgOutsideFundGet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgOutsideFundGet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("record_id", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgOutsideFundGetJson = budgOutsideFundGetService.deleteBudgOutsideFundGet(listVo);
			
	  return JSONObject.parseObject(budgOutsideFundGetJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 外拨经费到账
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/queryBudgOutsideFundGet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgOutsideFundGet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
			
		mapVo.put("acc_year", Calendar.getInstance().get(Calendar.YEAR));
        
		String budgOutsideFundGet = budgOutsideFundGetService.queryBudgOutsideFundGet(getPage(mapVo));

		return JSONObject.parseObject(budgOutsideFundGet);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 外拨经费到账
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/budgOutsideFundGetImportPage", method = RequestMethod.GET)
	public String budgOutsideFundGetImportPage(Model mode) throws Exception {

		return "hrp/budg/project/outside/budgOutsideFundGetImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 外拨经费到账
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/project/outside/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","外拨经费到账.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 外拨经费到账
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/project/outside/readBudgOutsideFundGetFiles",method = RequestMethod.POST)  
    public String readBudgOutsideFundGetFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgOutsideFundGet> list_err = new ArrayList<BudgOutsideFundGet>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgOutsideFundGet budgOutsideFundGet = new BudgOutsideFundGet();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgOutsideFundGet.setRecord_id(Long.valueOf(temp[3]));
		    		mapVo.put("record_id", temp[3]);
					
					} else {
						
						err_sb.append("自增ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgOutsideFundGet.setRemark(temp[4]);
		    		mapVo.put("remark", temp[4]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgOutsideFundGet.setGet_date(DateUtil.stringToDate(temp[5]));
		    		mapVo.put("get_date", temp[5]);
					
					} else {
						
						err_sb.append("到账日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgOutsideFundGet.setIncome_subj(temp[6]);
		    		mapVo.put("income_subj", temp[6]);
					
					} else {
						
						err_sb.append("收入科目为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgOutsideFundGet.setProj_id(Long.valueOf(temp[7]));
		    		mapVo.put("proj_id", temp[7]);
					
					} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgOutsideFundGet.setProj_no(Long.valueOf(temp[8]));
		    		mapVo.put("proj_no", temp[8]);
					
					} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgOutsideFundGet.setSource_id(Long.valueOf(temp[9]));
		    		mapVo.put("source_id", temp[9]);
					
					} else {
						
						err_sb.append("资金来源ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgOutsideFundGet.setGet_amount(Double.valueOf(temp[10]));
		    		mapVo.put("get_amount", temp[10]);
					
					} else {
						
						err_sb.append("到账金额为空  ");
						
					}
					 
					
				BudgOutsideFundGet data_exc_extis = budgOutsideFundGetService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgOutsideFundGet.setError_type(err_sb.toString());
					
					list_err.add(budgOutsideFundGet);
					
				} else {
			  
					String dataJson = budgOutsideFundGetService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgOutsideFundGet data_exc = new BudgOutsideFundGet();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 外拨经费到账
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/addBatchBudgOutsideFundGet", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgOutsideFundGet(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgOutsideFundGet> list_err = new ArrayList<BudgOutsideFundGet>();
		
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
			
			BudgOutsideFundGet budgOutsideFundGet = new BudgOutsideFundGet();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("record_id"))) {
						
					budgOutsideFundGet.setRecord_id(Long.valueOf((String)jsonObj.get("record_id")));
		    		mapVo.put("record_id", jsonObj.get("record_id"));
		    		} else {
						
						err_sb.append("自增ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgOutsideFundGet.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("get_date"))) {
						
					budgOutsideFundGet.setGet_date(DateUtil.stringToDate((String)jsonObj.get("get_date")));
		    		mapVo.put("get_date", jsonObj.get("get_date"));
		    		} else {
						
						err_sb.append("到账日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("income_subj"))) {
						
					budgOutsideFundGet.setIncome_subj((String)jsonObj.get("income_subj"));
		    		mapVo.put("income_subj", jsonObj.get("income_subj"));
		    		} else {
						
						err_sb.append("收入科目为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_id"))) {
						
					budgOutsideFundGet.setProj_id(Long.valueOf((String)jsonObj.get("proj_id")));
		    		mapVo.put("proj_id", jsonObj.get("proj_id"));
		    		} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_no"))) {
						
					budgOutsideFundGet.setProj_no(Long.valueOf((String)jsonObj.get("proj_no")));
		    		mapVo.put("proj_no", jsonObj.get("proj_no"));
		    		} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("source_id"))) {
						
					budgOutsideFundGet.setSource_id(Long.valueOf((String)jsonObj.get("source_id")));
		    		mapVo.put("source_id", jsonObj.get("source_id"));
		    		} else {
						
						err_sb.append("资金来源ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("get_amount"))) {
						
					budgOutsideFundGet.setGet_amount(Double.valueOf((String)jsonObj.get("get_amount")));
		    		mapVo.put("get_amount", jsonObj.get("get_amount"));
		    		} else {
						
						err_sb.append("到账金额为空  ");
						
					}
					
					
				BudgOutsideFundGet data_exc_extis = budgOutsideFundGetService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgOutsideFundGet.setError_type(err_sb.toString());
					
					list_err.add(budgOutsideFundGet);
					
				} else {
			  
					String dataJson = budgOutsideFundGetService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgOutsideFundGet data_exc = new BudgOutsideFundGet();
			
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

