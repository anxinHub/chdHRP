/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcost.empplan;
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
import com.chd.hrp.budg.entity.BudgAddEmpPlan;
import com.chd.hrp.budg.service.budgcost.empplan.BudgAddEmpDetailService;
import com.chd.hrp.budg.service.budgcost.empplan.BudgAddEmpPlanService;
import com.chd.hrp.budg.serviceImpl.common.BudgNoRulesServiceImpl;
/**
 * 
 * @Description:
 * 状态（STATE），“01新建、02已审核”
计划类型（PLAN_TYPE)：01年度计划02追加计划
 * @Table:
 * BUDG_ADD_EMP_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgAddEmpPlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAddEmpPlanController.class);
	
	//引入Service服务
	@Resource(name = "budgAddEmpPlanService")
	private final BudgAddEmpPlanService budgAddEmpPlanService = null;
	
	//引入Service服务
	@Resource(name = "budgAddEmpDetailService")
	private final BudgAddEmpDetailService budgAddEmpDetailService = null;
	
	@Resource(name = "budgNoRulesService")
	private final BudgNoRulesServiceImpl budgNoRulesService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/budgAddEmpPlanMainPage", method = RequestMethod.GET)
	public String budgAddEmpPlanMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/empplan/budgaddempplan/budgAddEmpPlanMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/budgAddEmpPlanAddPage", method = RequestMethod.GET)
	public String budgAddEmpPlanAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/empplan/budgaddempplan/budgAddEmpPlanAdd";

	}

	/**
	 * @Description 
	 * 添加数据 状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/addBudgAddEmpPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAddEmpPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String budgAddEmpPlanJson = "";
		
		try {
			//组装 生成 预算调整单号 参数Map
			Map<String,Object> mapForAdjCode  = new HashMap<String,Object>();
	
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			// 生成 预算调整单号
			if("系统生成".equals(mapVo.get("plan_code"))){
				mapForAdjCode.put("group_id", mapVo.get("group_id")) ;
				
				mapForAdjCode.put("hos_id", mapVo.get("hos_id")) ;
				
				mapForAdjCode.put("copy_code", mapVo.get("copy_code")) ;
				
				mapForAdjCode.put("budg_year", mapVo.get("plan_year")) ;
				
				mapForAdjCode.put("table_code", "BUDG_ADD_EMP_PLAN");
				
				Map<String,Object> codeMap = budgNoRulesService.getBudgNextNo(mapForAdjCode);
				
				if ("false".equals(codeMap.get("state"))){
					return codeMap;
				}else{
					mapVo.put("plan_code",codeMap.get("noCode"));
				}
				
			}
			//新建时 制单人为当前用户  日期为当前日期
			mapVo.put("maker", SessionManager.getUserId());
			//获取当前日期  Date类型
			Date date = DateUtil.getCurrenDate("yyyy-MM-dd");
			mapVo.put("make_date", date);
			//数据状态  01新建、02已审核
			mapVo.put("state", "01");
			//新建状态下 审核人  审核日期 均为空
			mapVo.put("checker", "");
			mapVo.put("check_date", "");
				
			//事务控制  接收service层信息
			budgAddEmpPlanJson = budgAddEmpPlanService.add(mapVo);
			
		} catch (Exception e) {
			budgAddEmpPlanJson = e.getMessage();
		}
    	return JSONObject.parseObject(budgAddEmpPlanJson);
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/budgAddEmpPlanUpdatePage", method = RequestMethod.GET)
	public String budgAddEmpPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());

        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> dataMap  = budgAddEmpPlanService.queryDataByCode(mapVo);
		
		mode.addAttribute("group_id", dataMap.get("group_id"));
		mode.addAttribute("hos_id", dataMap.get("hos_id"));
		mode.addAttribute("copy_code", dataMap.get("copy_code"));
		mode.addAttribute("plan_code",dataMap.get("plan_code"));
		mode.addAttribute("plan_year",dataMap.get("plan_year"));
		mode.addAttribute("plan_type", dataMap.get("plan_type"));
		mode.addAttribute("plan_type_name", dataMap.get("plan_type_name"));
		mode.addAttribute("num", dataMap.get("num"));
		mode.addAttribute("explain", dataMap.get("explain"));
		mode.addAttribute("maker", dataMap.get("maker"));
		mode.addAttribute("maker_name", dataMap.get("maker_name"));
		mode.addAttribute("make_date", dataMap.get("make_date"));
		mode.addAttribute("checker", dataMap.get("checker"));
		mode.addAttribute("checker_name", dataMap.get("checker_name"));
		mode.addAttribute("check_date", dataMap.get("check_date"));
		mode.addAttribute("state", dataMap.get("state"));
		mode.addAttribute("state_name", dataMap.get("state_name"));
		
		return "hrp/budg/budgcost/empplan/budgaddempplan/budgAddEmpPlanUpdate";
	}
	
	/**
	 * @Description 
	 * 修改页面查询明细数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/queryBudgAddEmpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjAdjDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			String budgAddEmpDetail = budgAddEmpDetailService.query(getPage(mapVo));

			return JSONObject.parseObject(budgAddEmpDetail);
		
	}
	
	/**
	 * @Description 
	 * 更新数据 状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/updateBudgAddEmpPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAddEmpPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());  
		
		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgAddEmpPlanJson = budgAddEmpPlanService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(budgAddEmpPlanJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/addOrUpdateBudgAddEmpPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAddEmpPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAddEmpPlanJson ="";
		

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
	  
		budgAddEmpPlanJson = budgAddEmpPlanService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAddEmpPlanJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/deleteBudgAddEmpPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAddEmpPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("plan_code", ids[3])   ;
				mapVo.put("plan_year", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgAddEmpPlanJson = budgAddEmpPlanService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAddEmpPlanJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/queryBudgAddEmpPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAddEmpPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgAddEmpPlan = budgAddEmpPlanService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAddEmpPlan);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/budgAddEmpPlanImportPage", method = RequestMethod.GET)
	public String budgAddEmpPlanImportPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/empplan/budgaddempplan/budgAddEmpPlanImport";

	}
	
	/**
	 * @Description 
	 * 导入数据 状态（STATE），“01新建、02已审核”
	 * 计划类型（PLAN_TYPE)：01年度计划02追加计划
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgcost/empplan/readBudgAddEmpPlanFiles",method = RequestMethod.POST)  
    public String readBudgAddEmpPlanFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgAddEmpPlan> list_err = new ArrayList<BudgAddEmpPlan>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgAddEmpPlan budgAddEmpPlan = new BudgAddEmpPlan();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgAddEmpPlan.setPlan_code(temp[3]);
		    		mapVo.put("plan_code", temp[3]);
					
					} else {
						
						err_sb.append("计划单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgAddEmpPlan.setPlan_year(temp[4]);
		    		mapVo.put("plan_year", temp[4]);
					
					} else {
						
						err_sb.append("计划年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgAddEmpPlan.setPlan_type(temp[5]);
		    		mapVo.put("plan_type", temp[5]);
					
					} else {
						
						err_sb.append("计划类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgAddEmpPlan.setNum(Integer.valueOf(temp[6]));
		    		mapVo.put("num", temp[6]);
					
					} else {
						
						err_sb.append("增员人数为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgAddEmpPlan.setExplain(temp[7]);
		    		mapVo.put("explain", temp[7]);
					
					} else {
						
						err_sb.append("申报说明为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgAddEmpPlan.setMaker(Long.valueOf(temp[8]));
		    		mapVo.put("maker", temp[8]);
					
					} else {
						
						err_sb.append("申报人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgAddEmpPlan.setMake_date(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("make_date", temp[9]);
					
					} else {
						
						err_sb.append("申报日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgAddEmpPlan.setChecker(Long.valueOf(temp[10]));
		    		mapVo.put("checker", temp[10]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgAddEmpPlan.setCheck_date(DateUtil.stringToDate(temp[11]));
		    		mapVo.put("check_date", temp[11]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgAddEmpPlan.setState(temp[12]);
		    		mapVo.put("state", temp[12]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					
				BudgAddEmpPlan data_exc_extis = budgAddEmpPlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAddEmpPlan.setError_type(err_sb.toString());
					
					list_err.add(budgAddEmpPlan);
					
				} else {
			  
					String dataJson = budgAddEmpPlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAddEmpPlan data_exc = new BudgAddEmpPlan();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
	/**
	 * 
	 * 审核
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/reviewbudgAddEmpPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reviewbudgAddEmpPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("plan_code", ids[3])   ;
			mapVo.put("plan_year", ids[4]);
			
			listVo.add(mapVo);
		}
		
		String budgAddEmpPlanJson = budgAddEmpPlanService.updateReviewState(listVo);
		
		return JSONObject.parseObject(budgAddEmpPlanJson);
		
	}
	
	/**
	 * 
	 * 销审
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/cancelbudgAddEmpPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelbudgAddEmpPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("plan_code", ids[3])   ;
			mapVo.put("plan_year", ids[4]);
			
			listVo.add(mapVo);
		}
		
		String budgAddEmpPlanJson = budgAddEmpPlanService.updateCancelBatch(listVo);
		
		return JSONObject.parseObject(budgAddEmpPlanJson);
		
	}
	
	
    
	/**
	 * 需求科室下拉框
	 * @param mapVo 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/empplan/queryBudgHosDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgHosDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String empType = budgAddEmpPlanService.queryBudgHosDept(mapVo);
		return empType;
	}
	
	
}

