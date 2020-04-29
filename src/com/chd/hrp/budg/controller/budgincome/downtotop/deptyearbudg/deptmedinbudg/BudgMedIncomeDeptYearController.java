/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.downtotop.deptyearbudg.deptmedinbudg;
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
import com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg.BudgMedIncomeDeptYearService;


/**
 * 
 * @Description:
 * 科室年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedIncomeDeptYearController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedIncomeDeptYearController.class);
	
	//引入Service服务
	@Resource(name = "budgMedIncomeDeptYearService")
	private final BudgMedIncomeDeptYearService budgMedIncomeDeptYearService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/deptmedinbudg/deptYearMedInPage", method = RequestMethod.GET)
	public String budgMedIncomeDeptYearMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/medinbudg/budgMedIncomeDeptYearMain";

	}

	/**
	 * @Description 
	 * 查询数据 科室年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/deptmedinbudg/queryBudgMedIncomeDeptYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMedIncomeDeptYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMedIncomeDeptYear = budgMedIncomeDeptYearService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeDeptYear);
		
	}
	
	
	/**
	 * @Description 
	 * 更新数据 科室年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/deptmedinbudg/updateBudgMedIncomeDeptYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedIncomeDeptYear(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("budg_value", ids[3]);
			mapVo.put("count_value", ids[4]);
			mapVo.put("remark", ids[5]);
			mapVo.put("last_year_income", ids[6]);
			mapVo.put("refer_value", ids[7]);
			mapVo.put("state", ids[8]);
			if("-1".equals(ids[9])){
				mapVo.put("reason", "");
			}else{
				mapVo.put("reason", ids[9]);
			}
			
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
	  
		String budgMedIncomeDeptYearJson = budgMedIncomeDeptYearService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgMedIncomeDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 提交,撤回,取消审核
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/deptmedinbudg/reviewOrUnreview", method = RequestMethod.POST)
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
			mapVo.put("subj_code", ids[4])   ;
			mapVo.put("subj_name", ids[5])   ;
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
	    
		String budgWorkDeptYearJson = budgMedIncomeDeptYearService.reviewOrUnreview(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptYearJson);
			
	}
	
	/**
	 * @Description 
	 * 审核不通过页面跳转
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/deptmedinbudg/disPassReasonPage", method = RequestMethod.GET)
	public String disPassReasonPage(Model mode) throws Exception {
				
		return "hrp/budg/budgincome/downtotop/deptyearbudg/medinbudg/disPassReasonPage";

	}
	
	/**
	 * @Description 
	 * 审核(通过,不通过)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/deptmedinbudg/passOrDisPass", method = RequestMethod.POST)
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
			mapVo.put("subj_code", ids[4])   ;
			mapVo.put("subj_name", ids[5])   ;
			mapVo.put("dept_id", ids[6]);
			mapVo.put("dept_name", ids[7]);
			mapVo.put("refer_value", ids[8]);
			mapVo.put("reason", ids[9]);
			mapVo.put("state", ids[10]);
			//mapVo.put("flag", ids[11]);
			
			listVo.add(mapVo);
	    }
	    
		String budgWorkDeptYearJson = budgMedIncomeDeptYearService.passOrDisPassDown(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptYearJson);
			
	}
}

