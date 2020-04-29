/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.project.adjust;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.budg.entity.BudgProjAdj;
import com.chd.hrp.budg.service.project.adjust.BudgProjAdjDetailService;
import com.chd.hrp.budg.service.project.adjust.BudgProjAdjService;
import com.chd.hrp.budg.serviceImpl.common.BudgNoRulesServiceImpl;

/**
 * 项目预算调整
 * @Table:
 * BUDG_PROJ_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class budgProjAdjController extends BaseController{
	
	private static Logger logger = Logger.getLogger(budgProjAdjController.class);
	
	//引入Service服务
	@Resource(name = "budgProjAdjService")
	private final BudgProjAdjService budgProjAdjService = null;
	@Resource(name = "budgProjAdjDetailService")
	private final BudgProjAdjDetailService budgProjAdjDetailService = null;
	@Resource(name = "budgNoRulesService")
	private final BudgNoRulesServiceImpl budgNoRulesService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/budgProjAdjMainPage", method = RequestMethod.GET)
	public String budgProjAdjMainPage(Model mode) throws Exception {

		return "hrp/budg/project/adjust/budgProjAdjMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/budgProjAdjAddPage", method = RequestMethod.GET)
	public String budgProjAdjAddPage(Model mode) throws Exception {

		return "hrp/budg/project/adjust/budgProjAdjAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/addBudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgProjAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String budgProjAdjJson = "";
		
		try {
		//组装 生成 预算调整单号 参数Map
		Map<String,Object> mapForAdjCode  = new HashMap<String,Object>();

		mapVo.put("group_id", SessionManager.getGroupId())   ;
		
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		// 生成 预算调整单号
		if("系统生成".equals(mapVo.get("adj_code"))){
			mapForAdjCode.put("group_id", mapVo.get("group_id")) ;
			
			mapForAdjCode.put("hos_id", mapVo.get("hos_id")) ;
			
			mapForAdjCode.put("copy_code", mapVo.get("copy_code")) ;
			
			mapForAdjCode.put("table_code", "BUDG_PROJ_ADJ");
			
			Map<String,Object> codeMap = budgNoRulesService.getBudgNextNo(mapForAdjCode);
			
			if ("false".equals(codeMap.get("state"))){
				return codeMap;
			}else{
				mapVo.put("adj_code",codeMap.get("noCode"));
			}
			
		}
		
		//数据状态  01新建、02已提交、03已审核
		mapVo.put("state", "01");
		//新建状态下 制单人 制单日期   审核人  审核日期 均为空
		mapVo.put("maker", "");
		mapVo.put("make_date", "");
		mapVo.put("checker", "");
		mapVo.put("check_date", "");
			
		//事务控制  接收service层信息
    	
    		budgProjAdjJson = budgProjAdjService.add(mapVo);
		} catch (Exception e) {
			budgProjAdjJson = e.getMessage();
		}
    	return JSONObject.parseObject(budgProjAdjJson);
		
	}
	
	
	/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/budgProjAdjUpdatePage", method = RequestMethod.GET)
	public String budgProjAdjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String,Object> mapAdj  = new HashMap<String,Object>();
    
		mapAdj = budgProjAdjService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", mapAdj.get("group_id"));
		mode.addAttribute("hos_id", mapAdj.get("hos_id"));
		mode.addAttribute("copy_code", mapAdj.get("copy_code"));
		mode.addAttribute("budg_year", mapAdj.get("budg_year"));
		mode.addAttribute("adj_code", mapAdj.get("adj_code"));
		mode.addAttribute("proj_id", mapAdj.get("proj_id"));
		mode.addAttribute("proj_no", mapAdj.get("proj_no"));
		mode.addAttribute("remark", mapAdj.get("remark"));
		mode.addAttribute("file_url", mapAdj.get("file_url"));
		mode.addAttribute("maker", mapAdj.get("maker"));
		mode.addAttribute("make_date", mapAdj.get("make_date"));
		mode.addAttribute("checker", mapAdj.get("checker"));
		mode.addAttribute("check_date", mapAdj.get("check_date"));
		mode.addAttribute("state", mapAdj.get("state"));
		mode.addAttribute("state_name", mapAdj.get("state_name"));
		mode.addAttribute("che_emp_name", mapAdj.get("che_emp_name"));
		mode.addAttribute("con_emp_name", mapAdj.get("con_emp_name"));
		mode.addAttribute("emp_name", mapAdj.get("emp_name"));
		
		
		return "hrp/budg/project/adjust/budgProjAdjUpdate";
	}
	
	/**
	 * @Description 
	 * 修改页面查询明细数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/queryBudgProjAdjDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjAdjDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			String budgProjAdj = budgProjAdjService.queryBudgProjAdjDetail(getPage(mapVo));

			return JSONObject.parseObject(budgProjAdj);
		
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/updateBudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgProjAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		String budgProjAdjJson = budgProjAdjService.update(mapVo);
		
		return JSONObject.parseObject(budgProjAdjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/addOrUpdateBudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgProjAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgProjAdjJson ="";
		
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
	  
		budgProjAdjJson = budgProjAdjService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgProjAdjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/deleteBudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgProjAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("budg_year", ids[3])   ;
			mapVo.put("adj_code", ids[4])   ;
			mapVo.put("proj_id", ids[5]);
			
      listVo.add(mapVo);
      
    }
    
		String budgProjAdjJson = budgProjAdjService.deleteBatch(listVo);
		
		return JSONObject.parseObject(budgProjAdjJson);
			
	}
	
	/**
	 * @Description 
	 * 主页面查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/queryBudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			String budgProjAdj = budgProjAdjService.query(getPage(mapVo));

			return JSONObject.parseObject(budgProjAdj);
		
	}
	/**
	 * @Description 
	 * 查询调整添加页面信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/adjust/queryBudgProjAdjAdd", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgProjAdjAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		String budgProjAdj = budgProjAdjService.queryAdjAdd(mapVo);
		
		return budgProjAdj;
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/budgProjAdjImportPage", method = RequestMethod.GET)
	public String budgProjAdjImportPage(Model mode) throws Exception {

		return "hrp/budg/project/adjust/budgProjAdjImport";

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
	 @RequestMapping(value="hrp/budg/project/adjust/downTemplate", method = RequestMethod.GET)  
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
	@RequestMapping(value="/hrp/budg/project/adjust/readBudgProjAdjFiles",method = RequestMethod.POST)  
    public String readBudgProjAdjFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgProjAdj> list_err = new ArrayList<BudgProjAdj>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgProjAdj budgProjAdj = new BudgProjAdj();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgProjAdj.setAdj_code(temp[3]);
		    		mapVo.put("adj_code", temp[3]);
					
					} else {
						
						err_sb.append("调整单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgProjAdj.setProj_id(Long.valueOf(temp[4]));
		    		mapVo.put("proj_id", temp[4]);
					
					} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgProjAdj.setProj_no(Long.valueOf(temp[5]));
		    		mapVo.put("proj_no", temp[5]);
					
					} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgProjAdj.setRemark(temp[6]);
		    		mapVo.put("remark", temp[6]);
					
					} else {
						
						err_sb.append("调整说明为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgProjAdj.setFile_url(temp[7]);
		    		mapVo.put("file_url", temp[7]);
					
					} else {
						
						err_sb.append("文件路径为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgProjAdj.setMaker(Long.valueOf(temp[8]));
		    		mapVo.put("maker", temp[8]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgProjAdj.setMake_date(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("make_date", temp[9]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgProjAdj.setChecker(Long.valueOf(temp[10]));
		    		mapVo.put("checker", temp[10]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgProjAdj.setCheck_date(DateUtil.stringToDate(temp[11]));
		    		mapVo.put("check_date", temp[11]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgProjAdj.setState(temp[12]);
		    		mapVo.put("state", temp[12]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					
				BudgProjAdj data_exc_extis = budgProjAdjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgProjAdj.setError_type(err_sb.toString());
					
					list_err.add(budgProjAdj);
					
				} else {
			  
					String dataJson = budgProjAdjService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgProjAdj data_exc = new BudgProjAdj();
			
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
	@RequestMapping(value = "/hrp/budg/project/adjust/addBatchBudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgProjAdj(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgProjAdj> list_err = new ArrayList<BudgProjAdj>();
		
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
			
			BudgProjAdj budgProjAdj = new BudgProjAdj();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("adj_code"))) {
						
					budgProjAdj.setAdj_code((String)jsonObj.get("adj_code"));
		    		mapVo.put("adj_code", jsonObj.get("adj_code"));
		    		} else {
						
						err_sb.append("调整单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_id"))) {
						
					budgProjAdj.setProj_id(Long.valueOf((String)jsonObj.get("proj_id")));
		    		mapVo.put("proj_id", jsonObj.get("proj_id"));
		    		} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_no"))) {
						
					budgProjAdj.setProj_no(Long.valueOf((String)jsonObj.get("proj_no")));
		    		mapVo.put("proj_no", jsonObj.get("proj_no"));
		    		} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgProjAdj.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("调整说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("file_url"))) {
						
					budgProjAdj.setFile_url((String)jsonObj.get("file_url"));
		    		mapVo.put("file_url", jsonObj.get("file_url"));
		    		} else {
						
						err_sb.append("文件路径为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					budgProjAdj.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("make_date"))) {
						
					budgProjAdj.setMake_date(DateUtil.stringToDate((String)jsonObj.get("make_date")));
		    		mapVo.put("make_date", jsonObj.get("make_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					budgProjAdj.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					budgProjAdj.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					budgProjAdj.setState((String)jsonObj.get("state"));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
				BudgProjAdj data_exc_extis = budgProjAdjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgProjAdj.setError_type(err_sb.toString());
					
					list_err.add(budgProjAdj);
					
				} else {
			  
					String dataJson = budgProjAdjService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgProjAdj data_exc = new BudgProjAdj();
			
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
	 * 提交数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/adjust/submitbudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitbudgProjAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("budg_year", ids[3])   ;
			mapVo.put("adj_code", ids[4])   ;
			mapVo.put("proj_id", ids[5]);
	
			//新建或提交状态下  审核人 审核日期为空
			mapVo.put("checker", "");
			mapVo.put("check_date", "");
			listVo.add(mapVo);
		}
    
		String budgProjAdjJson = budgProjAdjService.updateSubmitState(listVo);
		
		return JSONObject.parseObject(budgProjAdjJson);
			
	}
	
	/**
	 * @Description 
	 * 撤回提交 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/adjust/recallbudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> recallbudgProjAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("budg_year", ids[3])   ;
			mapVo.put("adj_code", ids[4])   ;
			mapVo.put("proj_id", ids[5]);
	
			//新建或提交状态下  审核人 审核日期为空
			mapVo.put("checker", "");
			mapVo.put("check_date", "");
			listVo.add(mapVo);
		}
		
		String budgProjAdjJson = budgProjAdjService.updateRecallState(listVo);
		
		return JSONObject.parseObject(budgProjAdjJson);
		
	}
	
	
	/**
	 * 
	 * 审核
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/adjust/reviewbudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reviewbudgProjAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("budg_year", ids[3])   ;
			mapVo.put("adj_code", ids[4])   ;
			mapVo.put("proj_id", ids[5]);
			
			listVo.add(mapVo);
		}
		
		String budgProjAdjJson = budgProjAdjService.updateReviewState(listVo);
		
		return JSONObject.parseObject(budgProjAdjJson);
		
	}
	
	/**
	 * 
	 * 销审
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/adjust/cancelbudgProjAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelbudgProjAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("budg_year", ids[3])   ;
			mapVo.put("adj_code", ids[4])   ;
			mapVo.put("proj_id", ids[5]);
			
			listVo.add(mapVo);
		}
		
		String budgProjAdjJson = budgProjAdjService.updateCancelBatch(listVo);
		
		return JSONObject.parseObject(budgProjAdjJson);
		
	}
	
	
}

