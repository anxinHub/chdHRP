/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcost.elsecost;
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
import com.chd.hrp.budg.entity.BudgElseCostCheck;
import com.chd.hrp.budg.service.budgcost.eslecost.BudgElseCostCheckService;
import com.chd.hrp.budg.serviceImpl.common.BudgNoRulesServiceImpl;
/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_ELSE_Cost_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgElseCostCheckController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgElseCostCheckController.class);
	
	//引入Service服务
	@Resource(name = "budgElseCostCheckService")
	private final BudgElseCostCheckService budgElseCostCheckService = null;
	@Resource(name="budgNoRulesService")
    private final BudgNoRulesServiceImpl  budgNoRulesService=null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/budgElseCostCheckMainPage", method = RequestMethod.GET)
	public String budgElseCostCheckMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/elsecost/check/budgElseCostCheckMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/budgElseCostCheckAddPage", method = RequestMethod.GET)
	public String budgElseCostCheckAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/elsecost/check/budgElseCostCheckAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/addBudgElseCostCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgElseCostCheck(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String budgElseCostCheckJson = null ;
		
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			
			//初始审批  校验数据是否存在
			
			if("01".equals(mapVo.get("check_type"))){
				
				int count = budgElseCostCheckService.queryDateExist(mapVo);

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
				int count = budgElseCostCheckService.queryInitDateExist(initMap) ;
				
				if(count == 0){
					
					return JSONObject.parseObject("{\"error\":\"该年度初始审批未下达,不允许重复添加调整审批单.\",\"state\":\"false\"}");
				}
				
				//根据参数 查询 是否存在未下达的审批表
				int countIssue = budgElseCostCheckService.queryIssueDateExist(initMap);
				
				if(countIssue > 0){
					
					return JSONObject.parseObject("{\"error\":\"存在未下达的审批表，不允许继续添加.\",\"state\":\"false\"}");
				}
			}
			
			
			//构建 取 审批单号 参数Map
			Map<String,Object> noMap = new HashMap<String,Object>();	
			
			noMap.put("table_code", "BUDG_ELSE_COST_CHECK");
			noMap.put("table_name", "其他支出预算审批");
			noMap.put("budg_year", mapVo.get("budg_year"));
			
			Map<String,Object> map = budgNoRulesService.getBudgNextNo(noMap) ;
			
			if("true".equals(map.get("state"))){
				//自动生成审批单号
				mapVo.put("check_code",map.get("noCode"));
				
			}else{
				
				return map ;
			}
			
			mapVo.put("maker", SessionManager.getUserId())   ;
			
			
			mapVo.put("make_data",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
			mapVo.put("checker", "");
			mapVo.put("check_data", "");
			mapVo.put("issue_data", "");
	       
			budgElseCostCheckJson = budgElseCostCheckService.add(mapVo);
			
		} catch (Exception e) {

			budgElseCostCheckJson = e.getMessage();
		}
			
		return JSONObject.parseObject(budgElseCostCheckJson);
		
	}
	
	/**
	 * @Description 
	 *业务预算审核申请修改
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/budgElseCostCheckUpdatePage", method = RequestMethod.GET)
	public String budgElseCostCheckUpdatePage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> map = budgElseCostCheckService.queryByCode(mapVo);
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
		return "/hrp/budg/budgcost/elsecost/check/budgElseCostCheckUpdate";	
		
	}
	
	
	/**
	 *  发送 、 召回 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/sendOrRecall", method = RequestMethod.POST)
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
		String matOrderMain = budgElseCostCheckService.updateBc_state(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
	/**
	 * 审核、销审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/auditOrUnAudit", method = RequestMethod.POST)
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
		String matOrderMain = budgElseCostCheckService.auditOrUnAudit(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
	/**
	 * 预算下达 、取消预算下达
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/cancelIssueOrIssue", method = RequestMethod.POST)
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

		String matOrderMain = budgElseCostCheckService.cancelIssueOrIssue(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/updateBudgElseCostCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgElseCostCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId()); 
		
		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgElseCostCheckJson = budgElseCostCheckService.update(mapVo);
		
		return JSONObject.parseObject(budgElseCostCheckJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/addOrUpdateBudgElseCostCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgElseCostCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgElseCostCheckJson ="";
		

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
	  
		budgElseCostCheckJson = budgElseCostCheckService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgElseCostCheckJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 审批类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/deleteBudgElseCostCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgElseCostCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());             	
	            	
		String budgElseCostCheckJson= budgElseCostCheckService.delete(mapVo);	
		
		return JSONObject.parseObject(budgElseCostCheckJson);

	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/queryBudgElseCostCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgElseCostCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgElseCostCheck = budgElseCostCheckService.query(getPage(mapVo));

		return JSONObject.parseObject(budgElseCostCheck);
		
	}
	
  /**
	 * @Description 
	 * 导入
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/budgElseCostCheckImportPage", method = RequestMethod.GET)
	public String budgElseCostCheckImportPage(Model mode) throws Exception {

		return "budg/budgcost/elsecost/check/budgElseCostCheckImport";

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
	 @RequestMapping(value="/hrp/budg/budgcost/elsecost/check/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","支出预算审批申请.xls");
	    
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
	@RequestMapping(value="/hrp/budg/budgcost/elsecost/check/readBudgElseCostCheckFiles",method = RequestMethod.POST)  
    public String readBudgElseCostCheckFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgElseCostCheck> list_err = new ArrayList<BudgElseCostCheck>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgElseCostCheck budgElseCostCheck = new BudgElseCostCheck();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgElseCostCheck.setBudg_year(temp[3]);
		    		mapVo.put("budg_year", temp[3]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgElseCostCheck.setCheck_code(temp[4]);
		    		mapVo.put("check_code", temp[4]);
					
					} else {
						
						err_sb.append("审批单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgElseCostCheck.setCheck_type(temp[5]);
		    		mapVo.put("check_type", temp[5]);
					
					} else {
						
						err_sb.append("审批类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgElseCostCheck.setRemark(temp[6]);
		    		mapVo.put("remark", temp[6]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgElseCostCheck.setMaker(Long.valueOf(temp[7]));
		    		mapVo.put("maker", temp[7]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgElseCostCheck.setMake_data(DateUtil.stringToDate(temp[8]));
		    		mapVo.put("make_data", temp[8]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgElseCostCheck.setChecker(Long.valueOf(temp[9]));
		    		mapVo.put("checker", temp[9]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgElseCostCheck.setCheck_data(DateUtil.stringToDate(temp[10]));
		    		mapVo.put("check_data", temp[10]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgElseCostCheck.setIssue_data(DateUtil.stringToDate(temp[11]));
		    		mapVo.put("issue_data", temp[11]);
					
					} else {
						
						err_sb.append("预算下达日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgElseCostCheck.setBc_state(temp[12]);
		    		mapVo.put("bc_state", temp[12]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					
				BudgElseCostCheck data_exc_extis = budgElseCostCheckService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgElseCostCheck.setError_type(err_sb.toString());
					
					list_err.add(budgElseCostCheck);
					
				} else {
			  
					String dataJson = budgElseCostCheckService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgElseCostCheck data_exc = new BudgElseCostCheck();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 审批类型
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/addBatchBudgElseCostCheck", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgElseCostCheck(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgElseCostCheck> list_err = new ArrayList<BudgElseCostCheck>();
		
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
			
			BudgElseCostCheck budgElseCostCheck = new BudgElseCostCheck();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgElseCostCheck.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_code"))) {
						
					budgElseCostCheck.setCheck_code((String)jsonObj.get("check_code"));
		    		mapVo.put("check_code", jsonObj.get("check_code"));
		    		} else {
						
						err_sb.append("审批单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_type"))) {
						
					budgElseCostCheck.setCheck_type((String)jsonObj.get("check_type"));
		    		mapVo.put("check_type", jsonObj.get("check_type"));
		    		} else {
						
						err_sb.append("审批类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgElseCostCheck.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					budgElseCostCheck.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("make_data"))) {
						
					budgElseCostCheck.setMake_data(DateUtil.stringToDate((String)jsonObj.get("make_data")));
		    		mapVo.put("make_data", jsonObj.get("make_data"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					budgElseCostCheck.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_data"))) {
						
					budgElseCostCheck.setCheck_data(DateUtil.stringToDate((String)jsonObj.get("check_data")));
		    		mapVo.put("check_data", jsonObj.get("check_data"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("issue_data"))) {
						
					budgElseCostCheck.setIssue_data(DateUtil.stringToDate((String)jsonObj.get("issue_data")));
		    		mapVo.put("issue_data", jsonObj.get("issue_data"));
		    		} else {
						
						err_sb.append("预算下达日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bc_state"))) {
						
					budgElseCostCheck.setBc_state((String)jsonObj.get("bc_state"));
		    		mapVo.put("bc_state", jsonObj.get("bc_state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
				BudgElseCostCheck data_exc_extis = budgElseCostCheckService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgElseCostCheck.setError_type(err_sb.toString());
					
					list_err.add(budgElseCostCheck);
					
				} else {
			  
					String dataJson = budgElseCostCheckService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgElseCostCheck data_exc = new BudgElseCostCheck();
			
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
	 * 其他预算  初始审批 查询（未下达）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/queryBudgElseCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgElseCost(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgElseCostCheck = budgElseCostCheckService.queryBudgElseCost(getPage(mapVo));
			return JSONObject.parseObject(budgElseCostCheck);
	}
	/**
	 * 其他预算  初始审批 查询（已下达）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/queryBudgElseCostCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgElseCostCopy(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgElseCostCheck = budgElseCostCheckService.queryBudgElseCostCopy(getPage(mapVo));
			return JSONObject.parseObject(budgElseCostCheck);
	}
	
	
	/**
	 * 其他支出预算调整审批 查询（未下达）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/queryBudgElseCostAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgElseCostAdjust(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgElseCostCheck = budgElseCostCheckService.queryBudgElseCostAdjust(getPage(mapVo));
			
			return JSONObject.parseObject(budgElseCostCheck);
	}
	
	/**
	 * 其他支出预算调整审批 查询（已下达）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/queryBudgElseCostAdjustCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgElseCostAdjustCopy(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			if(mapVo.get("budg_year") == null){
				
				mapVo.put("budg_year", SessionManager.getAcctYear());
	        
			}
			String budgElseCostCheck = budgElseCostCheckService.queryBudgElseCostAdjustCopy(getPage(mapVo));
			
			return JSONObject.parseObject(budgElseCostCheck);
	}
	
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/check/queryBudgElseCostCheckState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgElseCostCheckState(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			List<String> list= budgElseCostCheckService.queryBudgElseCostCheckState(mapVo);
			
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
	
	
	
}

