/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcash;
import java.io.IOException;
import java.util.*;
import javax.annotation.Resource;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.entity.BudgCashPlan;
import com.chd.hrp.budg.service.budgcash.BudgCashPlanService;


/**
 * 
 * @Description:
 * 资金计划
 * @Table:
 * BUDG_CASH_IN_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgCashPlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgCashPlanController.class);
	
	//引入Service服务
	@Resource(name = "budgCashPlanService")
	private final BudgCashPlanService budgCashPlanService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/budgCashPlanMainPage", method = RequestMethod.GET)
	public String BudgCashPlanMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashplan/budgCashPlanMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/budgCashPlanAddPage", method = RequestMethod.GET)
	public String BudgCashPlanAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashplan/budgCashPlanAdd";

	}

	/**
	 * @Description 
	 * 添加数据 资金计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/addBudgCashPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgCashPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String budgCashPlanJson = "";
		
		//组装 生成 预算调整单号 参数Map
		Map<String,Object> mapForAdjCode  = new HashMap<String,Object>();
				
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		try {
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				
				mapVo.put("budg_year", ids[0])   ;
				mapVo.put("month", ids[1])   ;
				mapVo.put("cash_item_id", ids[2]);
				mapVo.put("event", ids[3]);
				mapVo.put("plan_code", ids[4]);
				if("-1".equals(ids[5])){
					mapVo.put("remark", "");
				}else{
					mapVo.put("remark", ids[5]);
				}
				if("-1".equals(ids[6])){
					mapVo.put("amount", "");
				}else{
					mapVo.put("amount", ids[6]);
				}
				mapVo.put("cash_item_name", ids[7]);
				//其余字段处理
				mapVo.put("maker", SessionManager.getUserId());//制单人为当前用户
				
				Date date = DateUtil.getCurrenDate("yyyy-MM-dd");;//制单日期为当前时间
				mapVo.put("make_date", date);
				
				mapVo.put("checker", "");//新添加数据  审核人 为空
				mapVo.put("check_date", "");//新添加数据   审核日期为空
				
				mapVo.put("state", "01");//添加时  单据状态为新建
				
				listVo.add(mapVo);
			}
			
			budgCashPlanJson = budgCashPlanService.addBatch(listVo);
			
		} catch (Exception e) {
			
			budgCashPlanJson = e.getMessage();
		}

		return JSONObject.parseObject(budgCashPlanJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 资金计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/budgCashPlanUpdatePage", method = RequestMethod.GET)
	public String budgCashPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		BudgCashPlan BudgCashPlan = new BudgCashPlan();
    
		BudgCashPlan = budgCashPlanService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", BudgCashPlan.getGroup_id());
		mode.addAttribute("hos_id", BudgCashPlan.getHos_id());
		mode.addAttribute("copy_code", BudgCashPlan.getCopy_code());
		mode.addAttribute("budg_year", BudgCashPlan.getBudg_year());
		mode.addAttribute("plan_code", BudgCashPlan.getPlan_code());
		mode.addAttribute("event", BudgCashPlan.getEvent());
		mode.addAttribute("remark", BudgCashPlan.getRemark());
		mode.addAttribute("state", BudgCashPlan.getState());
		
		return "hrp/budg/budgcash/budgcashplan/budgCashPlanUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 资金计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/updateBudgCashPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCashPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		String budgCashPlanJson = "";
		
		//组装 生成 预算调整单号 参数Map
		Map<String,Object> mapForAdjCode  = new HashMap<String,Object>();
				
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		try {
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				
				mapVo.put("budg_year", ids[0])   ;
				mapVo.put("month", ids[1])   ;
				mapVo.put("cash_item_id", ids[2]);
				mapVo.put("event", ids[3]);
				mapVo.put("plan_code", ids[4]);
				if("-1".equals(ids[5])){
					mapVo.put("remark", "");
				}else{
					mapVo.put("remark", ids[5]);
				}
				if("-1".equals(ids[6])){
					mapVo.put("amount", "");
				}else{
					mapVo.put("amount", ids[6]);
				}
				mapVo.put("cash_item_name", ids[7]);
				
				listVo.add(mapVo);
			}
			
			budgCashPlanJson = budgCashPlanService.updateBatch(listVo);
			
		} catch (Exception e) {
			
			budgCashPlanJson = e.getMessage();
		}

		return JSONObject.parseObject(budgCashPlanJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 资金计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/addOrUpdateBudgCashPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgCashPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String BudgCashPlanJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
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
	  
		BudgCashPlanJson = budgCashPlanService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(BudgCashPlanJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 资金计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/deleteBudgCashPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgCashPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("plan_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String BudgCashPlanJson = budgCashPlanService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(BudgCashPlanJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 资金计划
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/queryBudgCashPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCashPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String BudgCashPlan = budgCashPlanService.query(getPage(mapVo));

		return JSONObject.parseObject(BudgCashPlan);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 资金计划明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/queryBudgCashPlanDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCashPlanDet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String BudgCashPlanDet = budgCashPlanService.queryBudgCashPlanDet(getPage(mapVo));
		
		return JSONObject.parseObject(BudgCashPlanDet);
		
	}
	
   /**
	 * @Description 
	 * 批量添加数据 资金计划
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/addBatchBudgCashPlan", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgCashPlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgCashPlan> list_err = new ArrayList<BudgCashPlan>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		Iterator it = json.iterator();
		
		try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgCashPlan BudgCashPlan = new BudgCashPlan();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
				
			BudgCashPlan.setBudg_year((String)jsonObj.get("budg_year"));
    		mapVo.put("budg_year", jsonObj.get("budg_year"));
    		} else {
				
				err_sb.append("预算年度为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("plan_code"))) {
				
			BudgCashPlan.setPlan_code((String)jsonObj.get("plan_code"));
    		mapVo.put("plan_code", jsonObj.get("plan_code"));
    		} else {
				
				err_sb.append("资金计划单号为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("event"))) {
				
			BudgCashPlan.setEvent((String)jsonObj.get("event"));
    		mapVo.put("event", jsonObj.get("event"));
    		} else {
				
				err_sb.append("资金事项为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("remark"))) {
				
			BudgCashPlan.setRemark((String)jsonObj.get("remark"));
    		mapVo.put("remark", jsonObj.get("remark"));
    		} else {
				
				err_sb.append("说明为空  ");
				
			}
					
			BudgCashPlan data_exc_extis = budgCashPlanService.queryByCode(mapVo);
			
			if (data_exc_extis != null) {
				
				err_sb.append("编码已经存在！ ");
				
			}
			if (err_sb.toString().length() > 0) {
				
				BudgCashPlan.setError_type(err_sb.toString());
				
				list_err.add(BudgCashPlan);
				
			} else {
		  
				String dataJson = budgCashPlanService.add(mapVo);
				
			}
			
		}
			
		} catch (DataAccessException e) {
			
			BudgCashPlan data_exc = new BudgCashPlan();
			
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
	 * 查询数据 现金流量项目下拉框
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/queryCashItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryCashItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgCashBegin = budgCashPlanService.queryCashItem(mapVo);
		
		return budgCashBegin;
		
	}
	
	/**
	 * 
	 * 审核
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/reviewbudgCashPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reviewbudgCashPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0])   ;
			mapVo.put("plan_code", ids[1]) ;
			
			listVo.add(mapVo);
		}
		
		String BudgCashPlan = budgCashPlanService.updateReviewState(listVo);
		
		return JSONObject.parseObject(BudgCashPlan);
		
	}
	
	/**
	 * 
	 * 销审
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/cancelbudgCashPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelbudgCashPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0])   ;
			mapVo.put("plan_code", ids[1]) ;
			
			listVo.add(mapVo);
		}
		
		String BudgCashPlan = budgCashPlanService.updateCancelBatch(listVo);
		
		return JSONObject.parseObject(BudgCashPlan);
		
	}
	
	/**
	 * @Description 
	 * 导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/importBudgCashPlanPage", method = RequestMethod.GET)
	public String budgHosBasicIndexImportPage(Model mode) throws Exception {

		return "/hrp/budg/budgcash/budgcashplan/budgCashPlanImport";

	}
	
	
	/**
	 * 最新导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashplan/importBudgCashPlan", method = RequestMethod.POST)
	@ResponseBody
	public String importBudgCashPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgCashPlanService.importBudgCashPlan(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
}

