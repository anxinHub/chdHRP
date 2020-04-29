/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.project.budgcontrol;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgProjApply;
import com.chd.hrp.budg.service.project.budgcontrol.BudgProjApplyService;
/**
 * 
 * @Description:
 * 项目预算申报
 * @Table:
 * BUDG_PROJ_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgProjApplyController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgProjApplyController.class);
	
	//引入Service服务
	@Resource(name = "budgProjApplyService")
	private final BudgProjApplyService budgProjApplyService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/budgProjApplyMainPage", method = RequestMethod.GET)
	public String budgProjApplyMainPage(Model mode) throws Exception {
		
		 	Map<String, Object> mapVo = new HashMap<String,Object>();
		
			mapVo.put("group_id", SessionManager.getGroupId());
		
			mapVo.put("hos_id", SessionManager.getHosId());
		
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		

			// 查询 期初项目预算记账BUDG_PROJ_BEGAIN_MARK 状态    为1记账时才可进行预算申报。

			String mark = budgProjApplyService.queryBegainMark(mapVo) ;
			
			mode.addAttribute("mark", mark);
		
		return "hrp/budg/project/apply/budgProjApplyMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/budgProjApplyAddPage", method = RequestMethod.GET)
	public String budgProjApplyAddPage(Model mode) throws Exception {

		return "hrp/budg/project/apply/budgProjApplyAdd";

	}
	
	/**
	 * @Description 
	 * 支出项目设置页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/budgProjPayItemPage", method = RequestMethod.GET)
	public String budgProjPayItemPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
				
		mode.addAttribute("codeData", mapVo.get("codeData"));

		return "hrp/budg/project/apply/budgPaymentItemMain";

	}
	
	/**
	 * @Description 
	 * 预算分解设置页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/budgProjApplyResolvePage", method = RequestMethod.GET)
	public String budgProjApplyResolvePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		
		
		mode.addAttribute("source_id", mapVo.get("source_id"));
		
		mode.addAttribute("apply_amount", mapVo.get("apply_amount"));
		
		mode.addAttribute("proj_id", mapVo.get("proj_id"));
		
		mode.addAttribute("proj_no", mapVo.get("proj_no"));
		
		mode.addAttribute("rowindex", mapVo.get("rowindex"));
		
		if( mapVo.get("apply_code") != null ){
			
			mode.addAttribute("apply_code", mapVo.get("apply_code"));
		}
		
		
		return "hrp/budg/project/apply/budgProjApplyResolveMain";

	}
	
	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/addBudgProjApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgProjApply(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    	
		String budgProjApplyJson = null ;
       
		try {
			
			budgProjApplyJson = budgProjApplyService.add(mapVo);
			
			//如果添加失败 则返回失败信息  不再执行文件上传
			if(!"true".equals(JSONObject.parseObject(budgProjApplyJson).get("state"))){
				return JSONObject.parseObject(budgProjApplyJson);
			}
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("file_url");
			if(mapVo.get("file_url") != null && !"".equals(mapVo.get("file_url"))){
				String basePath = "budg";
				String group_id = mapVo.get("group_id").toString();
				String hos_id = mapVo.get("hos_id").toString();
				String copy_code = mapVo.get("copy_code").toString();
				String budgFilePath = "projfile";
				String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
				
				String fileName = new String(file.getOriginalFilename());
				
				budgProjApplyJson = budgProjApplyService.importFile(mapVo,file,request,response,filePath);
				
				if(budgProjApplyJson.equals("error")){
					return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
				}
			}
		} catch (Exception e) {
			
			budgProjApplyJson = e.getMessage();
		}

		return JSONObject.parseObject(budgProjApplyJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/budgProjApplyUpdatePage", method = RequestMethod.GET)
	public String budgProjApplyUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String, Object> map = new HashMap<String, Object>();
    
		map = budgProjApplyService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", map.get("group_id"));
		mode.addAttribute("hos_id", map.get("hos_id"));
		mode.addAttribute("copy_code", map.get("copy_code"));
		mode.addAttribute("budg_year", map.get("budg_year"));
		mode.addAttribute("apply_code", map.get("apply_code"));
		mode.addAttribute("proj_id", map.get("proj_id"));
		mode.addAttribute("proj_no", map.get("proj_no"));
		mode.addAttribute("apply_type", map.get("apply_type"));
		mode.addAttribute("remark", map.get("remark"));
		mode.addAttribute("file_url", map.get("file_url"));
		mode.addAttribute("apply_amount", map.get("apply_amount"));
		mode.addAttribute("maker", map.get("maker"));
		mode.addAttribute("maker_name", map.get("maker_name"));
		mode.addAttribute("make_date", df.format(df.parse(String.valueOf(map.get("make_date")))));
		mode.addAttribute("checker", map.get("checker"));
		mode.addAttribute("checker_name", map.get("checker_name"));
		if(map.get("check_date") != null){
			mode.addAttribute("check_date", df.format(df.parse(String.valueOf(map.get("check_date")))));
		}else{
			mode.addAttribute("check_date","");
		}
		
		mode.addAttribute("state", map.get("state"));
		
		return "hrp/budg/project/apply/budgProjApplyUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/updateBudgProjApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgProjApply(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		mapVo.put("hos_id", SessionManager.getHosId());   
		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgProjApplyJson = null ;
		
		try {
			budgProjApplyJson = budgProjApplyService.update(mapVo);
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("upFile");
			
			if(mapVo.get("file_url") != null && !"".equals(mapVo.get("file_url"))){
				String basePath = "budg";
				String group_id = mapVo.get("group_id").toString();
				String hos_id = mapVo.get("hos_id").toString();
				String copy_code = mapVo.get("copy_code").toString();
				String budgFilePath = "projfile";
				String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
				
				String fileName = new String(file.getOriginalFilename());
				
				budgProjApplyJson = budgProjApplyService.importFile(mapVo,file,request,response,filePath);
				
				if(budgProjApplyJson.equals("error")){
					return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
				}
			}
			
		} catch (Exception e) {

			budgProjApplyJson = e.getMessage() ;

		}
				
		
		return JSONObject.parseObject(budgProjApplyJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/addOrUpdateBudgProjApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgProjApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgProjApplyJson ="";
		

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
	  
		budgProjApplyJson = budgProjApplyService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgProjApplyJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/deleteBudgProjApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgProjApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		String budgProjApplyJson = null ;
		
		try {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			Map<String, Object> fileMap=new HashMap<String, Object>();
			for ( String id: paramVo.split(",")) {
					String[] ids=id.split("@");
					//表的主键
					mapVo.put("group_id", ids[0])   ;
					mapVo.put("hos_id", ids[1])   ;
					mapVo.put("copy_code", ids[2])   ;
					mapVo.put("budg_year", ids[3])   ;
					mapVo.put("apply_code", ids[4])   ;
					mapVo.put("proj_id", ids[5]);
					if(!"-1".equals(ids[6])){
						String basePath = "budg";
						String group_id = mapVo.get("group_id").toString();
						String hos_id = mapVo.get("hos_id").toString();
						String copy_code = mapVo.get("copy_code").toString();
						String budgFilePath = "projfile";
						String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
						mapVo.put("filePath", filePath);
						mapVo.put("file_url", ids[6]);
						fileMap.putAll(mapVo);
					}else{
						mapVo.put("file_url", "");
					}
					
					budgProjApplyJson = budgProjApplyService.deleteData(mapVo,fileMap);
		    }
			
			
		} catch (Exception e) {

			budgProjApplyJson = e.getMessage();

		}
				
			
	  return JSONObject.parseObject(budgProjApplyJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/queryBudgProjApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjApply = budgProjApplyService.query(getPage(mapVo));

		return JSONObject.parseObject(budgProjApply);
		
	}
	
  /**
	 * @Description 
	 * 导入项目预算分解数据 跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/budgPaymentItemImportPage", method = RequestMethod.GET)
	public String budgPaymentItemImportPage(Model mode) throws Exception {

		return "hrp/budg/project/apply/budgPaymentItemImport";

	}
	
	/**
	 * @Description 导入项目预算分解数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/apply/importPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public String importPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgProjApplyService.importPaymentItem(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	 /**
		 * @Description 
		 * 导入跳转页面 
		 * @param  mode
		 * @return String
		 * @throws Exception
		*/
		@RequestMapping(value = "/hrp/budg/project/apply/budgProjApplyImportPage", method = RequestMethod.GET)
		public String budgProjApplyImportPage(Model mode) throws Exception {

			return "hrp/budg/project/apply/budgProjApplyImport";

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
	 @RequestMapping(value="hrp/budg/project/apply/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","项目预算申报申报.xls");
	    
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
	@RequestMapping(value="/hrp/budg/project/apply/readBudgProjApplyFiles",method = RequestMethod.POST)  
    public String readBudgProjApplyFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgProjApply> list_err = new ArrayList<BudgProjApply>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgProjApply budgProjApply = new BudgProjApply();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgProjApply.setBudg_year(temp[3]);
		    		mapVo.put("budg_year", temp[3]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgProjApply.setApply_code(temp[4]);
		    		mapVo.put("apply_code", temp[4]);
					
					} else {
						
						err_sb.append("申报单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgProjApply.setProj_id(Long.valueOf(temp[5]));
		    		mapVo.put("proj_id", temp[5]);
					
					} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgProjApply.setProj_no(Long.valueOf(temp[6]));
		    		mapVo.put("proj_no", temp[6]);
					
					} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgProjApply.setApply_type(temp[7]);
		    		mapVo.put("apply_type", temp[7]);
					
					} else {
						
						err_sb.append("申报类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgProjApply.setRemark(temp[8]);
		    		mapVo.put("remark", temp[8]);
					
					} else {
						
						err_sb.append("申报说明为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgProjApply.setFile_url(temp[9]);
		    		mapVo.put("file_url", temp[9]);
					
					} else {
						
						err_sb.append("文件路径为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgProjApply.setApply_amount(Double.valueOf(temp[10]));
		    		mapVo.put("apply_amount", temp[10]);
					
					} else {
						
						err_sb.append("申报金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgProjApply.setMaker(Long.valueOf(temp[11]));
		    		mapVo.put("maker", temp[11]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgProjApply.setMake_date(DateUtil.stringToDate(temp[12]));
		    		mapVo.put("make_date", temp[12]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					budgProjApply.setChecker(Long.valueOf(temp[13]));
		    		mapVo.put("checker", temp[13]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					budgProjApply.setCheck_date(DateUtil.stringToDate(temp[14]));
		    		mapVo.put("check_date", temp[14]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					budgProjApply.setState(temp[15]);
		    		mapVo.put("state", temp[15]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					
				BudgProjApply data_exc_extis = budgProjApplyService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgProjApply.setError_type(err_sb.toString());
					
					list_err.add(budgProjApply);
					
				} else {
			  
					String dataJson = budgProjApplyService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgProjApply data_exc = new BudgProjApply();
			
			data_exc.setError_type("导入系统出错");
			
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
	@RequestMapping(value = "/hrp/budg/project/apply/addBatchBudgProjApply", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgProjApply(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgProjApply> list_err = new ArrayList<BudgProjApply>();
		
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
			
			BudgProjApply budgProjApply = new BudgProjApply();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgProjApply.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("apply_code"))) {
						
					budgProjApply.setApply_code((String)jsonObj.get("apply_code"));
		    		mapVo.put("apply_code", jsonObj.get("apply_code"));
		    		} else {
						
						err_sb.append("申报单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_id"))) {
						
					budgProjApply.setProj_id(Long.valueOf((String)jsonObj.get("proj_id")));
		    		mapVo.put("proj_id", jsonObj.get("proj_id"));
		    		} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_no"))) {
						
					budgProjApply.setProj_no(Long.valueOf((String)jsonObj.get("proj_no")));
		    		mapVo.put("proj_no", jsonObj.get("proj_no"));
		    		} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("apply_type"))) {
						
					budgProjApply.setApply_type((String)jsonObj.get("apply_type"));
		    		mapVo.put("apply_type", jsonObj.get("apply_type"));
		    		} else {
						
						err_sb.append("申报类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgProjApply.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("申报说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("file_url"))) {
						
					budgProjApply.setFile_url((String)jsonObj.get("file_url"));
		    		mapVo.put("file_url", jsonObj.get("file_url"));
		    		} else {
						
						err_sb.append("文件路径为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("apply_amount"))) {
						
					budgProjApply.setApply_amount(Double.valueOf((String)jsonObj.get("apply_amount")));
		    		mapVo.put("apply_amount", jsonObj.get("apply_amount"));
		    		} else {
						
						err_sb.append("申报金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					budgProjApply.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("make_date"))) {
						
					budgProjApply.setMake_date(DateUtil.stringToDate((String)jsonObj.get("make_date")));
		    		mapVo.put("make_date", jsonObj.get("make_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					budgProjApply.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					budgProjApply.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					budgProjApply.setState((String)jsonObj.get("state"));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
				BudgProjApply data_exc_extis = budgProjApplyService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgProjApply.setError_type(err_sb.toString());
					
					list_err.add(budgProjApply);
					
				} else {
			  
					String dataJson = budgProjApplyService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgProjApply data_exc = new BudgProjApply();
			
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
	 * 查询 支出项目数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/queryBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgProjApply = budgProjApplyService.queryBudgPaymentItem(getPage(mapVo));

		return JSONObject.parseObject(budgProjApply);
		
	}
	
	/**
	 * @Description 
	 * 查询 BUDG_PROJ_APPLY_SOURCE  项目预算申报明细（按资金来源）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/queryBudgProjApplySource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjApplySource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		if(mapVo.get("budg_year") == null){
			
	        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String budgProjApplySource = budgProjApplyService.queryBudgProjApplySource(getPage(mapVo));

		return JSONObject.parseObject(budgProjApplySource);
		
	}
	
	/**
	 * @Description 
	 * 查询 BUDG_PROJ_APPLY_RESOLVE  项目预算分解 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/apply/queryBudgProjApplyResolve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjApplyResolve(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		if(mapVo.get("budg_year") == null){
			
	        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String budgProjApplySource = budgProjApplyService.queryBudgProjApplyResolve(getPage(mapVo));

		return JSONObject.parseObject(budgProjApplySource);
		
	}
	
	/**
	 *  提交 撤回 修改 数据状态
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/apply/updateBudgProjApplyState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgProjApplyState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("apply_code", ids[4])   ;
				mapVo.put("proj_id", ids[5]);
				
				mapVo.put("state", ids[6]);
				
				if("02".equals(ids[6])){
					
					DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
					
					mapVo.put("maker", SessionManager.getUserId());
					
					mapVo.put("make_date", df.format(new Date()));
				}
				
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgProjApplyJson = null ;
		
		try {
			
			budgProjApplyJson = budgProjApplyService.updateBudgProjApplyState(listVo);
			
		} catch (Exception e) {

			budgProjApplyJson = e.getMessage();

		}
				
			
	  return JSONObject.parseObject(budgProjApplyJson);
			
	}
	
	/**
	 *  审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/apply/auditBudgProjApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBudgProjApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("apply_code", ids[4])   ;
				mapVo.put("proj_id", ids[5]);
				
				mapVo.put("state", ids[6]);
				
				DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
				
				mapVo.put("checker", SessionManager.getUserId());
				
				mapVo.put("check_date", df.format(new Date()));
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgProjApplyJson = null ;
		
		try {
			
			budgProjApplyJson = budgProjApplyService.auditBudgProjApply(listVo);
			
		} catch (Exception e) {

			budgProjApplyJson = e.getMessage();

		}
				
			
	  return JSONObject.parseObject(budgProjApplyJson);
			
	}
	/**
	 * 消审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/apply/reAuditBudgProjApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditBudgProjApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("apply_code", ids[4])   ;
				mapVo.put("proj_id", ids[5]);
				
				mapVo.put("state", ids[6]);
				
				mapVo.put("checker", "");
				
				mapVo.put("check_date", "");
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgProjApplyJson = null ;
		
		try {
			
			budgProjApplyJson = budgProjApplyService.reAuditBudgProjApply(listVo);
			
		} catch (Exception e) {

			budgProjApplyJson = e.getMessage();

		}
				
			
	  return JSONObject.parseObject(budgProjApplyJson);
			
	}
	
}

