/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.medincheck;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
import com.chd.hrp.budg.entity.BudgMedIncomeCheck;
import com.chd.hrp.budg.service.budgincome.check.BudgIncomeCheckService;
import com.chd.hrp.budg.serviceImpl.common.BudgNoRulesServiceImpl;
/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_MED_INCOME_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedIncomeCheckController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedIncomeCheckController.class);
	
	//引入Service服务
	@Resource(name = "budgIncomeCheckService")
	private final BudgIncomeCheckService budgIncomeCheckService = null;
	@Resource(name="budgNoRulesService")
    private final BudgNoRulesServiceImpl  budgNoRulesService=null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/budgIncomeCheckMainPage", method = RequestMethod.GET)
	public String budgIncomeCheckMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/check/budgIncomeCheckMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/budgIncomeCheckAddPage", method = RequestMethod.GET)
	public String budgIncomeCheckAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/check/budgIncomeCheckAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/addBudgIncomeCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgIncomeCheck(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String budgIncomeCheckJson = null ;
		
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			//初始审批  校验数据是否存在
			
			if("01".equals(mapVo.get("check_type"))){
				
				int count = budgIncomeCheckService.queryDateExist(mapVo);

				if (count > 0 ) {

					return JSONObject.parseObject("{\"error\":\"该年度初始审批已添加,不能重复添加.\",\"state\":\"false\"}");

				}
				
			}else{
				//构建 查询 添加 审批单年度 初始审批单 是否已下达  参数Map
				
				Map<String,Object> initMap = new HashMap<String,Object>();
				
				initMap.put("group_id", mapVo.get("group_id")) ;
				
				initMap.put("hos_id", mapVo.get("hos_id")) ;
				
				initMap.put("copy_code", mapVo.get("copy_code")) ;
				
				initMap.put("budg_year", mapVo.get("budg_year")) ;
				
				initMap.put("check_type", "01") ;//审批类型  初始审批 
				
				initMap.put("bc_state", "04") ;//状态  04 已下达
				
				// 根据参数 查询 初始审批单是否已下达 
				int count = budgIncomeCheckService.queryInitDateExist(initMap) ;
				
				if(count == 0){
					
					return JSONObject.parseObject("{\"error\":\"该年度初始审批未下达,不允许重复添加调整审批单.\",\"state\":\"false\"}");
				}
				
				//根据参数 查询 是否存在未下达的审批表
				int countIssue = budgIncomeCheckService.queryIssueDateExist(initMap);
				
				if(countIssue > 0){
					
					return JSONObject.parseObject("{\"error\":\"存在未下达的审批表，不允许继续添加.\",\"state\":\"false\"}");
				}
			}
			//构建 取 审批单号 参数Map
			Map<String,Object> noMap = new HashMap<String,Object>();	
			
			noMap.put("table_code", "BUDG_MED_INCOME_CHECK");
			noMap.put("table_name", "医疗收入预算审批");
			noMap.put("budg_year", mapVo.get("budg_year"));
			
			Map<String,Object> map = budgNoRulesService.getBudgNextNo(noMap) ;
			
			if("true".equals(map.get("state"))){
				//自动生成审批单号
				mapVo.put("check_code",map.get("noCode").toString().replace(" ", ""));
				
			}else{
				
				return map ;
			}
			
			mapVo.put("maker", SessionManager.getUserId())   ;
			
			
			mapVo.put("make_data",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
			mapVo.put("checker", "");
			mapVo.put("check_data", "");
			mapVo.put("issue_data", "");
	       
			budgIncomeCheckJson = budgIncomeCheckService.add(mapVo);
			
		} catch (Exception e) {

			budgIncomeCheckJson = e.getMessage();
		}
			
		return JSONObject.parseObject(budgIncomeCheckJson);
		
	}
	
	/**
	 * *查询医院信息、查询医院科室信息、查询年度信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryDeptInformation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryDeptInformation(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgIncomeCheck = budgIncomeCheckService.queryDeptInformation(getPage(mapVo));
			return JSONObject.parseObject(budgIncomeCheck);
	}
	
	/**
	 * 调整单号查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgAdjFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgAdjFile(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
			mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgIncomeCheck = budgIncomeCheckService.queryBudgAdjFile(getPage(mapVo));
			return JSONObject.parseObject(budgIncomeCheck);
	}
	/**
	 * @Description 
	 *收入预算审核申请修改
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/budgIncomeCheckUpdatePage", method = RequestMethod.GET)
	public String budgIncomeCheckUpdatePage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String,Object> map = budgIncomeCheckService.queryByCode(mapVo);
		
		// 根据所传预算年度  查询 医疗收入预算编制模式
		String income_budg_mode = budgIncomeCheckService.queryIncomeBudgMode(mapVo);
		
		mode.addAttribute("group_id",map.get("group_id"));
		mode.addAttribute("hos_id",map.get("hos_id"));
		mode.addAttribute("copy_code",map.get("copy_code"));
		mode.addAttribute("budg_year", map.get("budg_year"));
		mode.addAttribute("check_code", map.get("check_code"));
		mode.addAttribute("check_type", map.get("check_type"));
		mode.addAttribute("remark", map.get("remark"));
		mode.addAttribute("maker", map.get("maker"));
		mode.addAttribute("maker_name", map.get("maker_name"));
		mode.addAttribute("make_data", df.format(df.parse(String.valueOf(map.get("make_data")))));
		mode.addAttribute("checker", map.get("checker"));
		mode.addAttribute("check_name", map.get("check_name"));
		if(map.get("check_data") != null){
			mode.addAttribute("check_data", df.format(df.parse(String.valueOf(map.get("check_data")))));
		}else{
			mode.addAttribute("check_data","");
		}
		if(map.get("issue_data") != null){
			mode.addAttribute("issue_data", df.format(df.parse(String.valueOf(map.get("issue_data")))));
		}else{
			mode.addAttribute("issue_data","");
		}
		mode.addAttribute("bc_state", map.get("bc_state"));
		
		mode.addAttribute("income_budg_mode", income_budg_mode);
		
		return "/hrp/budg/budgincome/check/budgIncomeCheckUpdate";	
		
	}
	
	
	/**
	 *  发送 、 召回 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/sendOrRecall", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> sendOrRecall(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("check_code", ids[1]);
				
			mapVo.put("bc_state",ids[2]);
			listVo.add(mapVo);			

		}
		String stateJson = null ;
		
		try {
			
			stateJson = budgIncomeCheckService.updateBc_state(listVo);		
			
		} catch (Exception e) {
			
			stateJson = e.getMessage();
		}
				
		return JSONObject.parseObject(stateJson);
	}
	/**
	 * 审核、销审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/auditOrUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditOrUnAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("check_code", ids[1]);
			mapVo.put("bc_state",ids[2]);
			//审核
			if("03".equals(ids[2])){
				
				mapVo.put("checker",SessionManager.getUserId());
				mapVo.put("check_data",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
				
			}else{//消审
				
				mapVo.put("checker","");
				mapVo.put("check_data","");
				
			}
			
			listVo.add(mapVo);			
		}
		String matOrderMain = budgIncomeCheckService.auditOrUnAudit(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
	/**
	 * 预算下达 、取消预算下达
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/cancelIssueOrIssue", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> cancelIssueOrIssue(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("check_code", ids[1]);
			mapVo.put("bc_state",ids[2]);
		    if(ids[2].toString().equals("04")){
		    	
		    	mapVo.put("issue_data",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
		    	
		    }else{
		    	
		    	mapVo.put("issue_data","");
		    	
		    }
			
			
			listVo.add(mapVo);			
			}

		String stateJosn = null ;
		
		try {
			
			stateJosn = budgIncomeCheckService.cancelIssueOrIssue(listVo);
			
		} catch (Exception e) {
			
			stateJosn = e.getMessage() ;
		}
		
				
		return JSONObject.parseObject(stateJosn);
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/updateBudgIncomeCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgIncomeCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgIncomeCheckJson = budgIncomeCheckService.update(mapVo);
		
		return JSONObject.parseObject(budgIncomeCheckJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/addOrUpdateBudgIncomeCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgIncomeCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgIncomeCheckJson ="";
		

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
	  
		budgIncomeCheckJson = budgIncomeCheckService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgIncomeCheckJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 审批类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/deleteBudgIncomeCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgIncomeCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());            	
	    
		String budgIncomeCheckJson = budgIncomeCheckService.delete(mapVo);
		
		return JSONObject.parseObject(budgIncomeCheckJson);

	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgIncomeCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgIncomeCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		
		String budgIncomeCheck = budgIncomeCheckService.query(getPage(mapVo));

		return JSONObject.parseObject(budgIncomeCheck);
		
	}
	
  /**
	 * @Description 
	 * 导入
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/budgIncomeCheckImportPage", method = RequestMethod.GET)
	public String budgIncomeCheckImportPage(Model mode) throws Exception {

		return "budg/budgincome/check/budgIncomeCheckImport";

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
	 @RequestMapping(value="/hrp/budg/budgincome/check/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","收入预算审批申请.xls");
	    
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
	@RequestMapping(value="/hrp/budg/budgincome/check/readBudgIncomeCheckFiles",method = RequestMethod.POST)  
    public String readBudgIncomeCheckFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedIncomeCheck> list_err = new ArrayList<BudgMedIncomeCheck>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedIncomeCheck budgMedIncomeCheck = new BudgMedIncomeCheck();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgMedIncomeCheck.setBudg_year(temp[3]);
		    		mapVo.put("budg_year", temp[3]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgMedIncomeCheck.setCheck_code(temp[4]);
		    		mapVo.put("check_code", temp[4]);
					
					} else {
						
						err_sb.append("审批单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgMedIncomeCheck.setCheck_type(temp[5]);
		    		mapVo.put("check_type", temp[5]);
					
					} else {
						
						err_sb.append("审批类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgMedIncomeCheck.setRemark(temp[7]);
		    		mapVo.put("remark", temp[7]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgMedIncomeCheck.setMaker(Long.valueOf(temp[8]));
		    		mapVo.put("maker", temp[8]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgMedIncomeCheck.setMake_data(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("make_data", temp[9]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgMedIncomeCheck.setChecker(Long.valueOf(temp[10]));
		    		mapVo.put("checker", temp[10]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgMedIncomeCheck.setCheck_data(DateUtil.stringToDate(temp[11]));
		    		mapVo.put("check_data", temp[11]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgMedIncomeCheck.setIssue_data(DateUtil.stringToDate(temp[12]));
		    		mapVo.put("issue_data", temp[12]);
					
					} else {
						
						err_sb.append("预算下达日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					budgMedIncomeCheck.setBc_state(temp[13]);
		    		mapVo.put("bc_state", temp[13]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					
				BudgMedIncomeCheck data_exc_extis = budgIncomeCheckService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeCheck.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeCheck);
					
				} else {
			  
					String dataJson = budgIncomeCheckService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeCheck data_exc = new BudgMedIncomeCheck();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 审批类型（CHECK_TYPE）取自系统字典表：01初始审批、02调整审批
     * 预算审批状态（BC_STATE）取自系统字典表：“01新建、02已发送、03已审核、04已下达、05批中”，其中05审批中暂不使用
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/check/addBatchBudgIncomeCheck", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgIncomeCheck(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeCheck> list_err = new ArrayList<BudgMedIncomeCheck>();
		
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
			
			BudgMedIncomeCheck budgIncomeCheck = new BudgMedIncomeCheck();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgIncomeCheck.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_code"))) {
						
					budgIncomeCheck.setCheck_code((String)jsonObj.get("check_code"));
		    		mapVo.put("check_code", jsonObj.get("check_code"));
		    		} else {
						
						err_sb.append("审批单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_type"))) {
						
					budgIncomeCheck.setCheck_type((String)jsonObj.get("check_type"));
		    		mapVo.put("check_type", jsonObj.get("check_type"));
		    		} else {
						
						err_sb.append("审批类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgIncomeCheck.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					budgIncomeCheck.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("make_data"))) {
						
					budgIncomeCheck.setMake_data(DateUtil.stringToDate((String)jsonObj.get("make_data")));
		    		mapVo.put("make_data", jsonObj.get("make_data"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					budgIncomeCheck.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_data"))) {
						
					budgIncomeCheck.setCheck_data(DateUtil.stringToDate((String)jsonObj.get("check_data")));
		    		mapVo.put("check_data", jsonObj.get("check_data"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("issue_data"))) {
						
					budgIncomeCheck.setIssue_data(DateUtil.stringToDate((String)jsonObj.get("issue_data")));
		    		mapVo.put("issue_data", jsonObj.get("issue_data"));
		    		} else {
						
						err_sb.append("预算下达日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bc_state"))) {
						
					budgIncomeCheck.setBc_state((String)jsonObj.get("bc_state"));
		    		mapVo.put("bc_state", jsonObj.get("bc_state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
				BudgMedIncomeCheck data_exc_extis = budgIncomeCheckService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgIncomeCheck.setError_type(err_sb.toString());
					
					list_err.add(budgIncomeCheck);
					
				} else {
			  
					String dataJson = budgIncomeCheckService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeCheck data_exc = new BudgMedIncomeCheck();
			
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
	 * 医院医疗收入预算  初始审批 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgMedIncomeHosMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMedIncomeHosMonth(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("year") == null){
				
				mapVo.put("year", SessionManager.getAcctYear());
	        
			}
			String budgIncomeCheck = budgIncomeCheckService.queryBudgMedIncomeHosMonth(getPage(mapVo));
			return JSONObject.parseObject(budgIncomeCheck);
	}
	
	
	/**
	 * 医院医疗收入 调整审批 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgMedIncomeHosMonthAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMedIncomeHosMonthAdjust(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("year") == null){
				
				mapVo.put("year", SessionManager.getAcctYear());
	        
			}
			String budgIncomeCheck = budgIncomeCheckService.queryBudgMedIncomeHosMonthAdjust(getPage(mapVo));
			
			return JSONObject.parseObject(budgIncomeCheck);
	}
	
	/**
	 * 科室医疗收入 初始审批  查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgMedIncomeDeptMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMedIncomeDeptMonth(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("year") == null){
				
				mapVo.put("year", SessionManager.getAcctYear());
	        
			}
			String budgIncomeCheck = budgIncomeCheckService.queryBudgMedIncomeDeptMonth(getPage(mapVo));
			return JSONObject.parseObject(budgIncomeCheck);
	}
	
	/**
	 * 科室医疗收入 调整审批  查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgMedIncomeCheckDeptMonthAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMedIncomeCheckDeptMonthAdjust(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("year") == null){
				
				mapVo.put("year", SessionManager.getAcctYear());
	        
			}
			String budgIncomeCheck = budgIncomeCheckService.queryBudgMedIncomeCheckDeptMonthAdjust(getPage(mapVo));
			return JSONObject.parseObject(budgIncomeCheck);
	}
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgMedIncomeCheckState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMedIncomeCheckState(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			List<String> list= budgIncomeCheckService.queryBudgMedIncomeCheckState(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"state\":\"true\"}");
				
			}else{
				
				String  str = "" ;
				for(String item : list){
					
					str += item +"," ;
				}
				
				return JSONObject.parseObject("{\"check_code\":\""+str+"\",\"state\":\"false\"}");
			}
			
	}
	
	/**
	 * 查询医院明细   初始审核 (修改页面 已下达)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgMedIncomeHosMonthCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMedIncomeHosMonthCopy(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgMedIncomeCheck = budgIncomeCheckService.queryBudgMedIncomeHosMonthCopy(getPage(mapVo));
		return JSONObject.parseObject(budgMedIncomeCheck);
	}
	
	/**
	 * 查询医院明细   调整审核 (修改页面 已下达)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgMedIncomeHosMonthAdjustCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMedIncomeHosMonthAdjustCopy(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("year") == null){
				
				mapVo.put("year", SessionManager.getAcctYear());
	        
			}
			String budgMedIncomeCheck = budgIncomeCheckService.queryBudgMedIncomeHosMonthAdjustCopy(getPage(mapVo));
			
			return JSONObject.parseObject(budgMedIncomeCheck);
	}
	
	/**
	 * 查询科室明细  调整审核 (修改页面 未下达)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgMedIncomeCheckDeptMonthAdjustCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMedIncomeCheckDeptMonthAdjustCopy(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
        
		}
		
		String budgMedIncomeCheck = budgIncomeCheckService.queryBudgMedIncomeCheckDeptMonthAdjustCopy(getPage(mapVo));
		
		return JSONObject.parseObject(budgMedIncomeCheck);
	}
	
	
	/**
	 * 查询科室明细  初始审核 (修改页面 已下达)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/queryBudgMedIncomeDeptMonthCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgMedIncomeDeptMonthCopy(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("year") == null){
				
				mapVo.put("year", SessionManager.getAcctYear());
	        
			}
			String budgMedIncomeCheck = budgIncomeCheckService.queryBudgMedIncomeDeptMonthCopy(getPage(mapVo));
			return JSONObject.parseObject(budgMedIncomeCheck);
	}
	
	/**
	 * 更新医院意见
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/updateHosSuggest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateHosSuggest(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			String suggest = budgIncomeCheckService.updateHosSuggest(mapVo);
			
			return JSONObject.parseObject(suggest);
	}
	
	/**
	 * 更新科室意见
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/check/updateDeptSuggest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateDeptSuggest(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			String suggest = budgIncomeCheckService.updateDeptSuggest(mapVo);
			
			return JSONObject.parseObject(suggest);
	}
    
}

