/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgpur.matpur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.budg.service.budgpur.matpur.BudgMatPurAdjService;
import com.chd.hrp.budg.serviceImpl.common.BudgNoRulesServiceImpl;
/**
 * 
 * @Description:
 * 材料采购预算调整
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMatPurAdjController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMatPurAdjController.class);
	
	@Resource(name="budgMatPurAdjService")
	private final BudgMatPurAdjService  budgMatPurAdjService=null;
	
	@Resource(name="budgNoRulesService")
    private final BudgNoRulesServiceImpl  budgNoRulesService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/budgMatPurAdjMainPage", method = RequestMethod.GET)
	public String budgMedIncomeAdjMainPage(Model mode) throws Exception {

		return "hrp/budg/budgpur/mat/matpuradj/budgMatPurAdjMain";

	}
	
	/**
	 * @Description 
	 * 其他支出预算调整申请添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/budgMatPurAdjAddPage", method = RequestMethod.GET)
	public String budgMatPurAdjAddPage(Model mode) throws Exception {

		Map<String,Object> mapVo = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		Map<String,Object> dataMap = budgMatPurAdjService.queryMaxCheckData(mapVo);
		
		mode.addAttribute("budg_year", dataMap.get("budg_year"));
		mode.addAttribute("before_adj_code", dataMap.get("before_adj_code"));
		mode.addAttribute("issue_data",dataMap.get("issue_data"));
		
		
		return "hrp/budg/budgpur/mat/matpuradj/budgMatPurAdjAdd";

	}

	/**
	 * @Description 
	 * 其他支出预算调整申请  添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/addBudgMatPurAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMatPurAdj(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		String budgMatPurAdjJson = "";
		try {
			//页面跳转前查询调整表中是否均已下达 
			int count = budgMatPurAdjService.queryBcState(mapVo);
			if(count > 0 ){
				return JSONObject.parseObject("{\"error\":\"预算审批未下达，不可使用该调整!\",\"state\":\"false\"}");
			}
			//构建 生成单号 参数 Map
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("table_code", "BUDG_MAT_PUR_ADJ");
			map.put("table_name", "材料采购预算调整");
			map.put("budg_year",mapVo.get("budg_year"));
			
			Map<String,Object> noMap = budgNoRulesService.getBudgNextNo(map) ;
			
			if("true".equals(noMap.get("state"))){
				
				mapVo.put("adj_code",noMap.get("noCode"));
				
			}else{
				return noMap ;
			}
			
			mapVo.put("maker", SessionManager.getUserId());
			mapVo.put("make_data",DateUtil.getCurrenDate("yyyy-MM-dd"));
			mapVo.put("checker","");
			mapVo.put("check_date","");	
			mapVo.put("state","01");	
			
			budgMatPurAdjJson = budgMatPurAdjService.add(mapVo);
			//如果添加失败 则返回失败信息  不再执行文件上传
			if(!"true".equals(JSONObject.parseObject(budgMatPurAdjJson).get("state"))){
				return JSONObject.parseObject(budgMatPurAdjJson);
			}
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("adj_file");
			
			if(mapVo.get("adj_file") != null && !"".equals(mapVo.get("adj_file"))){
				String basePath = "budg";
				String group_id = mapVo.get("group_id").toString();
				String hos_id = mapVo.get("hos_id").toString();
				String copy_code = mapVo.get("copy_code").toString();
				String budgFilePath = "matfile";
				String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
				
				String fileName = new String(file.getOriginalFilename());
				
				budgMatPurAdjJson = budgMatPurAdjService.importFile(mapVo,file,request,response,filePath);
				
				if(budgMatPurAdjJson.equals("error")){
					return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
				}
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(budgMatPurAdjJson);
		
	}
	
    /**
	 * @Description 
	 * 更新页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/budgMatPurAdjUpdatePage", method = RequestMethod.GET)
	public String budgMatPurAdjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		Map<String,Object> map =  budgMatPurAdjService.queryByCode(mapVo);
		
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
		
		mode.addAttribute("last_check_code", map.get("before_adj_code"));
		
		return "hrp/budg/budgpur/mat/matpuradj/budgMatPurAdjUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/updateBudgMatPurAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMatPurAdj(@RequestParam Map<String, Object> mapVo,Model mode,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String budgMatPurAdjJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());   

			mapVo.put("hos_id", SessionManager.getHosId());   

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			//先更新数据 再根据数据内容更新文件
			budgMatPurAdjJson = budgMatPurAdjService.update(mapVo);
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("upFile");
			
			if(mapVo.get("adj_file") != null && !"".equals(mapVo.get("adj_file"))){
				String basePath = "budg";
				String group_id = mapVo.get("group_id").toString();
				String hos_id = mapVo.get("hos_id").toString();
				String copy_code = mapVo.get("copy_code").toString();
				String budgFilePath = "matfile";
				String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
				
				String fileName = new String(file.getOriginalFilename());
				
				budgMatPurAdjJson = budgMatPurAdjService.importFile(mapVo,file,request,response,filePath);
				
				if(budgMatPurAdjJson.equals("error")){
					return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
				}
			}
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(budgMatPurAdjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/deleteBudgMatPurAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMatPurAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String budgMatPurAdjJson = "";
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
					String budgFilePath = "matfile";
					String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
					mapVo.put("file_url", filePath);
					mapVo.put("adj_file", ids[5]);
					fileMap.putAll(mapVo);
				}else{
					mapVo.put("adj_file", "");
				}
		    }
			//先删掉数据  再删文件
			budgMatPurAdjJson = budgMatPurAdjService.deleteData(mapVo,fileMap);
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	  return JSONObject.parseObject(budgMatPurAdjJson);
			
	}
	
	/**
	 * @Description 
	 * 下载文件
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/downMatPurAdjFile", method = RequestMethod.GET)
	@ResponseBody
	public String downMatPurAdjFile(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		mapVo.put("group_id", SessionManager.getGroupId());   
		mapVo.put("hos_id", SessionManager.getHosId());   
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String basePath = "budg";
		String group_id = mapVo.get("group_id").toString();
		String hos_id = mapVo.get("hos_id").toString();
		String copy_code = mapVo.get("copy_code").toString();
		String budgFilePath = "matfile";
		String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+budgFilePath;
		mapVo.put("file_url", filePath);
		
		String downJson = budgMatPurAdjService.downloadFile(response, mapVo);
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
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/queryBudgMatPurAdj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMatPurAdj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgMatPurAdj = budgMatPurAdjService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMatPurAdj);
		
	}
	
	/**
	 * 其他支出预算调整申请 审核 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/auditBudgMatPurAdj", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditBudgMatPurAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {		
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
			
			matOrderMain=budgMatPurAdjService.auditBudgMatPurAdj(listVo);
			
		} catch (Exception e) {
			matOrderMain = e.getMessage();
		}
						
		return JSONObject.parseObject(matOrderMain);
	}
	/**
	 * 
	 * 
	 * 其他支出预算调整申请销审
     */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/unAuditBudgMatPurAdj", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unAuditBudgMatPurAdj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			matOrderMain = budgMatPurAdjService.unAuditBudgMatPurAdj(listVo);					
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
	
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/adjLinkPage", method = RequestMethod.GET)
	public String adjustLinkPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("budg_year", mapVo.get("budg_year"));
		mode.addAttribute("check_code", mapVo.get("check_code"));
		mode.addAttribute("bc_state", mapVo.get("bc_state"));
		return "hrp/budg/budgpur/mat/matpuradj/adjLinkPage";
	}
	
	/**
	 * @Description 
	 * 其他支出预算调整 状态查询（审核、销审 校验数据用 ）
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/queryBudgMatPurAdjState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMatPurAdjState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
	    mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null ){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		List<String> list= budgMatPurAdjService.queryBudgMatPurAdjState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"adj_code\":\""+str+"\",\"state\":\"false\"}");
		}
		
	}
	
	/**
	 * @Description 
	 * 查询调整表中是否均已下达
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/queryBcState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBcState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		//页面跳转前查询调整表中是否均已下达 
		int count = budgMatPurAdjService.queryBcState(mapVo);
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
	@RequestMapping(value = "/hrp/budg/budgpur/mat/matpuradj/queryCheckDataExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCheckDataExists(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		//页面跳转前查询审核表中是否存在当前预算年度数据
		int count = budgMatPurAdjService.queryCheckDataExists(mapVo);
		if(count == 0 ){
			return JSONObject.parseObject("{\"error\":\"预算审批表中暂无当前预算年度数据，不可使用该调整!\",\"state\":\"false\"}");
		}else {
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
	}

    
}

