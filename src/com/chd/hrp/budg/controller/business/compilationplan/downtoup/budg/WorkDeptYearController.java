/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationplan.downtoup.budg;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptYearService;
/**
 * 
 * @Description:
 * 科室年度业务预算
 * @Table:
 * BUDG_WORK_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class WorkDeptYearController extends BaseController{
	
	private static Logger logger = Logger.getLogger(WorkDeptYearController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkDeptYearService")
	private final BudgWorkDeptYearService budgWorkDeptYearService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/workdeptyear/budgWorkDeptYearMainPage", method = RequestMethod.GET)
	public String budgWorkDeptYearMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/deptyearbudg/workdeptyear/budgWorkDeptYearMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 科室年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/workdeptyear/queryBudgWorkDeptYearDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptYearDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgWorkDeptYear = budgWorkDeptYearService.queryBudgWorkDeptYearDown(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptYear);
		
	}
	
	/**
	 * @Description 
	 * 提交,撤回,取消审核
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/workdeptyear/reviewOrUnreview", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reviewOrUnreview(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("year", ids[3])   ;
			mapVo.put("index_code", ids[4])   ;
			mapVo.put("index_name", ids[5])   ;
			mapVo.put("dept_id", ids[6]);
			mapVo.put("dept_name", ids[7]);
			if(!"-1".equals(ids[8])){
				mapVo.put("state", ids[8]);
			}else{
				mapVo.put("state", "");
			}
			mapVo.put("flag", ids[9]);
			
			listVo.add(mapVo);
	    }
	    
		String budgWorkDeptYearJson = budgWorkDeptYearService.reviewOrUnreview(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptYearJson);
			
	}
	
	/**
	 * @Description 
	 * 审核不通过页面跳转
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/workdeptyear/disPassReasonPage", method = RequestMethod.GET)
	public String disPassReasonPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/uptodown/deptyearbudg/disPassReasonPage";

	}
	
	/**
	 * @Description 
	 * 审核(通过,不通过)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/workdeptyear/passOrDisPass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> passOrDisPass(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("year", ids[3])   ;
			mapVo.put("index_code", ids[4])   ;
			mapVo.put("index_name", ids[5])   ;
			mapVo.put("dept_id", ids[6]);
			mapVo.put("dept_name", ids[7]);
			mapVo.put("refer_value", ids[8]);
			mapVo.put("reason", ids[9]);
			mapVo.put("state", ids[10]);
			//mapVo.put("flag", ids[11]);
			
			listVo.add(mapVo);
	    }
	    
		String budgWorkDeptYearJson = budgWorkDeptYearService.passOrDisPassDown(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptYearJson);
			
	}
	/**
	 * @Description 
	 * 保存数据 科室年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/workdeptyear/saveBudgWorkDeptYearUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgWorkDeptYearUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("last_year_workload", ids[3]);
			mapVo.put("grow_rate", ids[4]);
			mapVo.put("grow_value", ids[5]);
			mapVo.put("resolve_rate", ids[6]);
			mapVo.put("count_value", ids[7]);
			mapVo.put("budg_value", ids[8]);
			mapVo.put("remark", ids[9]);
			mapVo.put("refer_value", ids[10]);
			mapVo.put("reason", ids[11]);
			mapVo.put("state", ids[12]);
			
			mapVo.put("rowNo", ids[13]);
			mapVo.put("flag", ids[14]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgWorkDeptYearJson = null ;
		
		try {
			
			budgWorkDeptYearJson = budgWorkDeptYearService.save(listVo);
			
		} catch (Exception e) {
			
			budgWorkDeptYearJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgWorkDeptYearJson);
	}
}

