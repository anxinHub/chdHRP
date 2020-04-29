/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.adjust;
import java.io.File;
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
import com.chd.hrp.budg.entity.BudgMedIncomeAdj;
import com.chd.hrp.budg.service.budgincome.adjust.BudgIncomeAdjService;
import com.chd.hrp.budg.service.budgincome.check.BudgIncomeCheckService;
import com.chd.hrp.budg.service.business.budgeworkadjust.BudgWorkAdjService;
import com.chd.hrp.budg.service.business.budgeworkcheck.BudgWorkCheckService;
import com.chd.hrp.budg.serviceImpl.common.BudgNoRulesServiceImpl;
/**
 * 
 * @Description:
 *收入预算调整
 * @Table:
 * BUDG_MED_INCOME_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedIncomeAdjController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedIncomeAdjController.class);
   
	//引入Service服务
	@Resource(name = "budgIncomeAdjService")
	private final BudgIncomeAdjService budgIncomeAdjService = null;
	
	@Resource(name="budgNoRulesService")
    private final BudgNoRulesServiceImpl  budgNoRulesService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/budgIncomeAdjMainPage", method = RequestMethod.GET)
	public String budgMedIncomeAdjMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/adjust/budgIncomeAdjMain";

	}
	
	/**
	 * @Description 
	 * 业务预算调整申请添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/budgIncomeAdjAddPage", method = RequestMethod.GET)
	public String budgIncomeAdjAddPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		Map<String,Object> dataMap = budgIncomeAdjService.queryMaxCheckData(mapVo);
		
		mode.addAttribute("budg_year", dataMap.get("budg_year"));
		mode.addAttribute("before_adj_code", dataMap.get("before_adj_code"));
		mode.addAttribute("issue_data",dataMap.get("issue_data"));
		
		
		return "hrp/budg/budgincome/adjust/budgIncomeAdjAdd";

	}

	/**
	 * @Description 
	 * 业务预算调整申请  添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/addBudgIncomeAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgIncomeAdj(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		String budgIncomeAdjJson = "";
		try {
			//页面跳转前查询调整表中是否均已下达 
			int count = budgIncomeAdjService.queryBcState(mapVo);
			if(count > 0 ){
				return JSONObject.parseObject("{\"error\":\"预算审批未下达，不可使用该调整!\",\"state\":\"false\"}");
			}
			//构建 生成单号 参数 Map
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table_code", "BUDG_MED_INCOME_ADJ");
			map.put("table_name", "医疗收入预算调整");
			map.put("budg_year",mapVo.get("budg_year"));
			
			Map<String,Object> noMap = budgNoRulesService.getBudgNextNo(map) ;
			
			if("true".equals(noMap.get("state"))){
				
				mapVo.put("adj_code",noMap.get("noCode").toString().replace(" ", ""));
				
			}else{
				return noMap ;
			}
			
			mapVo.put("maker", SessionManager.getUserId());
			mapVo.put("make_data",DateUtil.getCurrenDate("yyyy-MM-dd"));
			mapVo.put("checker","");
			mapVo.put("check_date","");	
			mapVo.put("state","01");	
			
			
			budgIncomeAdjJson = budgIncomeAdjService.add(mapVo);
			//如果添加失败 则返回失败信息  不再执行文件上传
			if(!"true".equals(JSONObject.parseObject(budgIncomeAdjJson).get("state"))){
				return JSONObject.parseObject(budgIncomeAdjJson);
			}
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("adj_file");
			
			if(mapVo.get("adj_file") != null && !"".equals(mapVo.get("adj_file"))){
				String basePath = "budg";
				String group_id = mapVo.get("group_id").toString();
				String hos_id = mapVo.get("hos_id").toString();
				String copy_code = mapVo.get("copy_code").toString();
				String budgFilePath = "incomefile";
				String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
				
				String fileName = new String(file.getOriginalFilename());
				
				budgIncomeAdjJson = budgIncomeAdjService.importFile(mapVo,file,request,response,filePath);
				
				if(budgIncomeAdjJson.equals("error")){
					return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
				}
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(budgIncomeAdjJson);
		
	}
	
    /**
	 * @Description 
	 * 更新页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/budgIncomeAdjUpdatePage", method = RequestMethod.GET)
	public String budgIncomeAdjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		Map<String,Object> map =  budgIncomeAdjService.queryByCode(mapVo);

		mode.addAttribute("before_adj_code", map.get("before_adj_code"));
		mode.addAttribute("issue_data",map.get("issue_data"));
		mode.addAttribute("budg_year", map.get("budg_year"));
		
		mode.addAttribute("group_id", map.get("group_id"));
		mode.addAttribute("hos_id", map.get("hos_id"));
		mode.addAttribute("copy_code", map.get("copy_code"));
		
		mode.addAttribute("adj_code", map.get("adj_code"));
		mode.addAttribute("adj_file", map.get("adj_file"));
		mode.addAttribute("adj_remark", map.get("adj_remark"));
		mode.addAttribute("state", map.get("state"));
		mode.addAttribute("maker", map.get("maker"));
		mode.addAttribute("make_name", map.get("make_name"));
		mode.addAttribute("make_data",  DateUtil.dateToString(DateUtil.stringToDate(String.valueOf(map.get("make_data")), "yyyy-MM-dd"),"yyyy-MM-dd"));
		mode.addAttribute("checker", map.get("checker"));
		mode.addAttribute("check_name", map.get("check_name"));
		if(map.get("check_date") == null){
			mode.addAttribute("check_date", "");
		}else{
			mode.addAttribute("check_date", DateUtil.dateToString(DateUtil.stringToDate(String.valueOf(map.get("check_date")), "yyyy-MM-dd"),"yyyy-MM-dd"));
		}
		mode.addAttribute("last_check_code", map.get("last_check_code"));
		
		return "hrp/budg/budgincome/adjust/budgIncomeAdjUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/updateBudgIncomeAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgIncomeAdj(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String budgIncomeAdjJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());   

			mapVo.put("hos_id", SessionManager.getHosId());   

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			//先更新数据 再根据数据内容更新文件
			budgIncomeAdjJson = budgIncomeAdjService.update(mapVo);
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("upFile");
			
			if(mapVo.get("adj_file") != null && !"".equals(mapVo.get("adj_file"))){
				String basePath = "budg";
				String group_id = mapVo.get("group_id").toString();
				String hos_id = mapVo.get("hos_id").toString();
				String copy_code = mapVo.get("copy_code").toString();
				String budgFilePath = "incomefile";
				String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
				
				String fileName = new String(file.getOriginalFilename());
				
				budgIncomeAdjJson = budgIncomeAdjService.importFile(mapVo,file,request,response,filePath);
				
				if(budgIncomeAdjJson.equals("error")){
					return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
				}
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(budgIncomeAdjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/deleteBudgIncomeAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgIncomeAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String budgIncomeAdjJson = "";
		Map<String, Object> mapVo=new HashMap<String, Object>();
		Map<String, Object> fileMap=new HashMap<String, Object>();
	    try {
			for ( String id: paramVo.split(",")) {
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("adj_code", ids[4]);
				if(!"-1".equals(ids[5])){
					String basePath = "budg";
					String group_id = mapVo.get("group_id").toString();
					String hos_id = mapVo.get("hos_id").toString();
					String copy_code = mapVo.get("copy_code").toString();
					String budgFilePath = "incomefile";
					String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
					mapVo.put("file_url", filePath);
					mapVo.put("adj_file", ids[5]);
					fileMap.putAll(mapVo);
				}else{
					mapVo.put("adj_file", "");
				}
		    }
			//先删掉数据  再删文件
			budgIncomeAdjJson = budgIncomeAdjService.deleteData(mapVo,fileMap);
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
			
	  return JSONObject.parseObject(budgIncomeAdjJson);
			
	}
	
	/**
	 * @Description 
	 * 下载文件
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/downMedIncomeAdjFile", method = RequestMethod.GET)
	@ResponseBody
	public String downMedIncomeAdjFile(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		mapVo.put("group_id", SessionManager.getGroupId());   
		mapVo.put("hos_id", SessionManager.getHosId());   
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String basePath = "budg";
		String group_id = mapVo.get("group_id").toString();
		String hos_id = mapVo.get("hos_id").toString();
		String copy_code = mapVo.get("copy_code").toString();
		String budgFilePath = "incomefile";
		String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
		mapVo.put("file_url", filePath);
		
		String downJson = budgIncomeAdjService.downloadFile(response, mapVo);
		return downJson;
	}
	
	
	/**
	 * @Description 
	 * 查询数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/queryBudgIncomeAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgIncomeAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgIncomeAdj = budgIncomeAdjService.query(getPage(mapVo));

		return JSONObject.parseObject(budgIncomeAdj);
		
	}
	
	/**
	 * 业务预算调整申请 审核 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/auditBudgIncomeAdj", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditBudgIncomeAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();			
		
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("year", ids[0]);
			mapVo.put("adj_code", ids[1]);
			mapVo.put("state",ids[2]);
			mapVo.put("checker",SessionManager.getUserId());
			mapVo.put("check_date",DateUtil.getCurrenDate("yyyy-MM-dd"));
				
			listVo.add(mapVo);			

		}
		
		String matOrderMain = "";
		try {
			
			matOrderMain=budgIncomeAdjService.auditBudgIncomeAdj(listVo);
			
		} catch (Exception e) {
			matOrderMain = e.getMessage();
		}
						
		return JSONObject.parseObject(matOrderMain);
	}
	/**
	 * 
	 * 
	 * 业务预算调整申请销审
     */
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/unAuditBudgIncomeAdj", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unAuditBudgIncomeAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			/**
			 * 表的主键
			 */
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("year", ids[0]);
			mapVo.put("adj_code", ids[1]);
			mapVo.put("last_check_code", ids[2]);
			mapVo.put("state",ids[3]);				
			mapVo.put("checker","");
			mapVo.put("check_date","");
			listVo.add(mapVo);			
		}
		String matOrderMain="";
		try{
			matOrderMain = budgIncomeAdjService.unAuditBudgIncomeAdj(listVo);					
		}catch(Exception e){
			matOrderMain=e.getMessage();
		}
		return JSONObject.parseObject(matOrderMain);
	}	

	/**
	 * @Description 
	 * 调整页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/adjLinkPage", method = RequestMethod.GET)
	public String adjustLinkPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("check_code", mapVo.get("check_code"));
		mode.addAttribute("bc_state", mapVo.get("bc_state"));
		
		return "hrp/budg/budgincome/adjust/adjLinkPage";
	}
	
	/**
	 * @Description 
	 * 查询医院的月数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/queryBudgIncomeAdjHosMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgIncomeAdjHosMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
	    mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("year") == null ){
			mapVo.put("year", SessionManager.getAcctYear());
		}
		
        
		String budgIncomeAdj = budgIncomeAdjService.queryBudgIncomeAdjHosMonth(getPage(mapVo));

		return JSONObject.parseObject(budgIncomeAdj);
		
	}
	
	/**
	 * @Description 
	 * 查询科室的月数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/queryBudgIncomeAdjDeptMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgIncomeAdjDeptMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		if(mapVo.get("year") == null ){
			mapVo.put("year", SessionManager.getAcctYear());
		}
        
		String budgIncomeAdj = budgIncomeAdjService.queryBudgIncomeAdjDeptMonth(getPage(mapVo));

		return JSONObject.parseObject(budgIncomeAdj);
		
	}
	/**
	 * @Description 
	 * 查询调整表中是否均已下达
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/queryBcState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBcState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		//页面跳转前查询调整表中是否均已下达 
		int count = budgIncomeAdjService.queryBcState(mapVo);
		if(count > 0 ){
			return JSONObject.parseObject("{\"error\":\"预算审批未下达，不可使用该调整!\",\"state\":\"false\"}");
		}else {
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
	}
	/**
	 * @Description 
	 * 页面跳转前查询审核表中是否存在当前预算年度数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/adjust/queryCheckDataExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCheckDataExists(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		//页面跳转前查询审核表中是否存在当前预算年度数据
		int count = budgIncomeAdjService.queryCheckDataExists(mapVo);
		if(count == 0 ){
			return JSONObject.parseObject("{\"error\":\"预算审批表中暂无当前预算年度数据，不可使用该调整!\",\"state\":\"false\"}");
		}else {
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
	}
}

