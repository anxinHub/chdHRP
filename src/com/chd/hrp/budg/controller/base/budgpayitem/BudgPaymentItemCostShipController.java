/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgpayitem;
import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.budg.entity.BudgPaymentItemCostShip;
import com.chd.hrp.budg.service.base.budgpayitem.BudgPaymentItemCostShipService;
import com.chd.hrp.budg.service.base.budgwage.BudgWageItemCostShipService;
/**
 * 
 * @Description:
 * 支出项目与支出预算科目对应关系
 * @Table:
 * BUDG_PAYMENT_ITEM_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgPaymentItemCostShipController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgPaymentItemCostShipController.class);
	
	//引入Service服务
	@Resource(name = "budgPaymentItemCostShipService")
	private final BudgPaymentItemCostShipService budgPaymentItemCostShipService = null;
	
	//引入Service服务
	@Resource(name = "budgWageItemCostShipService")
	private final BudgWageItemCostShipService budgWageItemCostShipService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/budgPaymentItemCostShipMainPage", method = RequestMethod.GET)
	public String budgPaymentItemCostShipMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgpayitem/paymentitemcostship/budgPaymentItemCostShipMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/budgPaymentItemCostShipAddPage", method = RequestMethod.GET)
	public String budgPaymentItemCostShipAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgpayitem/paymentitemcostship/budgPaymentItemCostShipAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/addBudgPaymentItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgPaymentItemCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
       
		String budgPaymentItemCostShipJson = budgPaymentItemCostShipService.add(mapVo);

		return JSONObject.parseObject(budgPaymentItemCostShipJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/budgPaymentItemCostShipUpdatePage", method = RequestMethod.GET)
	public String budgPaymentItemCostShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgPaymentItemCostShip budgPaymentItemCostShip = new BudgPaymentItemCostShip();
    
		budgPaymentItemCostShip = budgPaymentItemCostShipService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgPaymentItemCostShip.getGroup_id());
		mode.addAttribute("hos_id", budgPaymentItemCostShip.getHos_id());
		mode.addAttribute("copy_code", budgPaymentItemCostShip.getCopy_code());
		mode.addAttribute("budg_year", budgPaymentItemCostShip.getBudg_year());
		mode.addAttribute("payment_item_id", budgPaymentItemCostShip.getPayment_item_id());
		mode.addAttribute("fund_nature", budgPaymentItemCostShip.getFund_nature());
		mode.addAttribute("subj_code", budgPaymentItemCostShip.getSubj_code());
		
		return "hrp/budg/base/budgpayitem/paymentitemcostship/budgPaymentItemCostShipUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/updateBudgPaymentItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgPaymentItemCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgPaymentItemCostShipJson = budgPaymentItemCostShipService.update(mapVo);
		
		return JSONObject.parseObject(budgPaymentItemCostShipJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/addOrUpdateBudgPaymentItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgPaymentItemCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgPaymentItemCostShipJson ="";
		

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
	  
		budgPaymentItemCostShipJson = budgPaymentItemCostShipService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgPaymentItemCostShipJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/deleteBudgPaymentItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgPaymentItemCostShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("payment_item_id", ids[4])   ;
				mapVo.put("fund_nature", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgPaymentItemCostShipJson = budgPaymentItemCostShipService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgPaymentItemCostShipJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/queryBudgPaymentItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPaymentItemCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgPaymentItemCostShip = budgPaymentItemCostShipService.query(getPage(mapVo));

		return JSONObject.parseObject(budgPaymentItemCostShip);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/budgPaymentItemCostShipImportPage", method = RequestMethod.GET)
	public String budgPaymentItemCostShipImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgpayitem/paymentitemcostship/budgPaymentItemCostShipImport";

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
	 @RequestMapping(value="hrp/budg/base/budgpayitem/paymentitemcostship/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","支出项目与预算支出科目对应关系模板.xls");
	    
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
	@RequestMapping(value="/hrp/budg/base/budgpayitem/paymentitemcostship/readBudgPaymentItemCostShipFiles",method = RequestMethod.POST)  
    public String readBudgPaymentItemCostShipFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgPaymentItemCostShip> list_err = new ArrayList<BudgPaymentItemCostShip>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] sourceList  = {"1","2","3","4"};
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgPaymentItemCostShip budgPaymentItemCostShip = new BudgPaymentItemCostShip();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行预算年度、支出项目、资金性质相同 ,多个预算支出科目 不允许对应相同的预算年度、支出项目、资金性质;");
						}
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])&& temp[3].equals(error[3])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					} 
					if (StringTool.isNotBlank(temp[0])) {
						
						budgPaymentItemCostShip.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgPaymentItemCostShip.setPayment_item_code(temp[1]);
						
			    		mapVo.put("payment_item_code", temp[1]);
		    		
			    		Map<String,Object> count = budgPaymentItemCostShipService.queryItemCodeExist(mapVo);
			    		
			    		if( count != null ){
			    			
			    			mapVo.put("payment_item_id", count.get("payment_item_id"));
			    			
			    		}else{
			    			
			    			err_sb.append("支出项目编码不存在或已停用;");
			    		}
					
					} else {
						
						err_sb.append("支出项目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						if(Arrays.asList(sourceList).contains(temp[2])){
							
							budgPaymentItemCostShip.setFund_nature(temp[2]);
				    		mapVo.put("fund_nature", temp[2]);
						}else{
							err_sb.append("资金性质编码不符合规则(1-4)");
						}
					
					} else {
						
						err_sb.append("资金性质编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgPaymentItemCostShip.setSubj_code(temp[3]);
			    		mapVo.put("subj_code", temp[3]);
			    		
			    		int count = budgWageItemCostShipService.queryCostSubjByCode(mapVo);
						if(count == 0 ){
							err_sb.append("该年度支出预算科目不是末级科目或不存在;");
						}
					
					} else {
						
						err_sb.append("支出预算科目编码为空;");
						
					}
					 
					
				BudgPaymentItemCostShip data_exc_extis = budgPaymentItemCostShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgPaymentItemCostShip.setError_type(err_sb.toString());
					
					list_err.add(budgPaymentItemCostShip);
					
				} else {
			  
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgPaymentItemCostShipService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			
			BudgPaymentItemCostShip data_exc = new BudgPaymentItemCostShip();
			
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
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/addBatchBudgPaymentItemCostShip", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgPaymentItemCostShip(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgPaymentItemCostShip> list_err = new ArrayList<BudgPaymentItemCostShip>();
		
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
			
			BudgPaymentItemCostShip budgPaymentItemCostShip = new BudgPaymentItemCostShip();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgPaymentItemCostShip.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("payment_item_id"))) {
						
					budgPaymentItemCostShip.setPayment_item_id(Long.valueOf((String)jsonObj.get("payment_item_id")));
		    		mapVo.put("payment_item_id", jsonObj.get("payment_item_id"));
		    		} else {
						
						err_sb.append("支出项目ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fund_nature"))) {
						
					budgPaymentItemCostShip.setFund_nature((String)jsonObj.get("fund_nature"));
		    		mapVo.put("fund_nature", jsonObj.get("fund_nature"));
		    		} else {
						
						err_sb.append("资金性质编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgPaymentItemCostShip.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					
				BudgPaymentItemCostShip data_exc_extis = budgPaymentItemCostShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgPaymentItemCostShip.setError_type(err_sb.toString());
					
					list_err.add(budgPaymentItemCostShip);
					
				} else {
			  
					String dataJson = budgPaymentItemCostShipService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgPaymentItemCostShip data_exc = new BudgPaymentItemCostShip();
			
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
	 * 支出项目下拉框 （添加页面用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/paymentitemcostship/queryPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgPaymentItemCostShipService.queryPaymentItem(mapVo);
		return item;

	}
    
}

