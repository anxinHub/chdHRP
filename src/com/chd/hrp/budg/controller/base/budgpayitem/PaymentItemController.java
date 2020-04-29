/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgpayitem;
import java.io.IOException;
import java.lang.reflect.Method;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.payable.BudgPaymentItem;
import com.chd.hrp.acc.service.payable.base.BudgPaymentItemService;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;
/**
 * 
 * @Description:
 * 支出项目
 * @Table:
 * BUDG_PAYMENT_ITEM
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
 

@Controller
public class PaymentItemController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PaymentItemController.class);
	
	//引入Service服务
	@Resource(name = "budgPaymentItemService")
	private final BudgPaymentItemService budgPaymentItemService = null;
	
	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/budgPaymentItemMainPage", method = RequestMethod.GET)
	public String budgPaymentItemMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgpayitem/payitemdict/budgPaymentItemMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/budgPaymentItemAddPage", method = RequestMethod.GET)
	public String budgPaymentItemAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgpayitem/payitemdict/budgPaymentItemAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/addBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
			String resultJson = "";
	        
	        String rules = getRules(mapVo);
	        
	        String subj_code = (String)mapVo.get("payment_item_code");//科目编码
	        
	        String [] ruless  = rules.split("-");
	               
	        
	        Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
	        
	        Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
	        
	        int super_num = 0;
	        
	        for(int i = 0; i < ruless.length; i++){
	        	int num = Integer.parseInt(ruless[i].replace(" ", ""));
	        	super_num += num;
	        	maxNumMap.put(super_num, i + 1);
	        	position.put(i + 1, super_num);
	        }
	        
	        if(maxNumMap.containsKey(subj_code.length())){//编码匹配
	        	 int subj_level = maxNumMap.get(subj_code.length());
	        	 mapVo.put("item_level", subj_level);
	        	 if(subj_level == 1){
	        		 mapVo.put("super_code", "top");
	        		 mapVo.put("item_name_all", mapVo.get("payment_item_name"));
	        	 }else{
	        		 int super_level =  subj_level - 1;//上级级次
	            	 int subPosition = position.get(super_level);//上级级次位置
	            	 String super_code = subj_code.substring(0,subPosition);//截取上级编码
	        		 mapVo.put("super_code", super_code);
	        	 }
	        	 
	        }else{
	        	resultJson =  "{\"error\":\"添加失败，不符合编码规则，请重新输入！\"}";
	        	return JSONObject.parseObject(resultJson);
	        }
			
	        mapVo.put("is_last", "1");
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("payment_item_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("payment_item_name").toString()));
	       
			resultJson = budgPaymentItemService.add(mapVo);

			return JSONObject.parseObject(resultJson);
		
	}
	
	public String getRules(@RequestParam Map<String, Object> mapVo) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	    
	    mapVo.put("mod_code", "02");
	        
	    mapVo.put("proj_code", "BUDG_PAYMENT_ITEM");
	   
	        
	    Rules rules = rulesService.queryRulesByCode(mapVo);
	    
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i = 1; i <= 10; i++){
	    	Method m = (Method) rules.getClass().getMethod(
					"get" + ("Level" + i));
	    	Object obj = m.invoke(rules,new Object[] {});
	    	if(i == 10){
	    		sb.append(obj.toString());
	    	}else{
	    		sb.append(obj.toString()+"-");
	    	}
			
	    }
		return sb.toString();
	}
	
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/showRules", method = RequestMethod.POST)
	@ResponseBody
	public String showRules(@RequestParam Map<String, Object> mapVo) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	    
	    mapVo.put("mod_code", "02");
	        
	    mapVo.put("proj_code", "BUDG_PAYMENT_ITEM");
	   
	        
	    Rules rules = rulesService.queryRulesByCode(mapVo);
	    
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i = 1; i <= 10; i++){
	    	Method m = (Method) rules.getClass().getMethod(
					"get" + ("Level" + i));
	    	Object obj = m.invoke(rules,new Object[] {});
	    	if(i == 10){
	    		sb.append(obj.toString());
	    	}else{
	    		sb.append(obj.toString()+"-");
	    	}
			
	    }
		return sb.toString();
	}
	
	
	/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/budgPaymentItemUpdatePage", method = RequestMethod.GET)
	public String budgPaymentItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		BudgPaymentItem budgPaymentItem = new BudgPaymentItem();
    
		budgPaymentItem = budgPaymentItemService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgPaymentItem.getGroup_id());
		mode.addAttribute("hos_id", budgPaymentItem.getHos_id());
		mode.addAttribute("copy_code", budgPaymentItem.getCopy_code());
		mode.addAttribute("payment_item_id", budgPaymentItem.getPayment_item_id());
		mode.addAttribute("payment_item_code", budgPaymentItem.getPayment_item_code());
		mode.addAttribute("payment_item_name", budgPaymentItem.getPayment_item_name());
		mode.addAttribute("item_name_all", budgPaymentItem.getItem_name_all());
		mode.addAttribute("super_code", budgPaymentItem.getSuper_code());
		mode.addAttribute("item_level", budgPaymentItem.getItem_level());
		mode.addAttribute("is_last", budgPaymentItem.getIs_last());
		mode.addAttribute("payment_item_nature", budgPaymentItem.getPayment_item_nature());
		mode.addAttribute("is_manage", budgPaymentItem.getIs_manage());
		mode.addAttribute("control_way", budgPaymentItem.getControl_way());
		mode.addAttribute("is_stop", budgPaymentItem.getIs_stop());
		mode.addAttribute("spell_code", budgPaymentItem.getSpell_code());
		mode.addAttribute("wbx_code", budgPaymentItem.getWbx_code());
		mode.addAttribute("acc_subj_manage", budgPaymentItem.getAcc_subj_manage());
		mode.addAttribute("acc_subj_manage_code", budgPaymentItem.getAcc_subj_manage_code());
		mode.addAttribute("acc_subj_manage_name", budgPaymentItem.getAcc_subj_manage_name());
		mode.addAttribute("acc_subj_medical", budgPaymentItem.getAcc_subj_medical());
		mode.addAttribute("acc_subj_medical_code", budgPaymentItem.getAcc_subj_medical_code());
		mode.addAttribute("acc_subj_medical_name", budgPaymentItem.getAcc_subj_medical_name());
		mode.addAttribute("acc_subj_education", budgPaymentItem.getAcc_subj_education());
		mode.addAttribute("acc_subj_education_code", budgPaymentItem.getAcc_subj_education_code());
		mode.addAttribute("acc_subj_education_code", budgPaymentItem.getAcc_subj_education_name());
		mode.addAttribute("acc_subj_scientific", budgPaymentItem.getAcc_subj_scientific());
		mode.addAttribute("acc_subj_scientific_code", budgPaymentItem.getAcc_subj_scientific_code());
		mode.addAttribute("acc_subj_scientific_name", budgPaymentItem.getAcc_subj_scientific_name());
		mode.addAttribute("acc_subj_financial", budgPaymentItem.getAcc_subj_financial());
		mode.addAttribute("acc_subj_financial_code", budgPaymentItem.getAcc_subj_financial_code());
		mode.addAttribute("acc_subj_financial_name", budgPaymentItem.getAcc_subj_financial_name());
		
		return "hrp/budg/base/budgpayitem/payitemdict/budgPaymentItemUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/updateBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("payment_item_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("payment_item_name").toString()));
	  
		String budgPaymentItemJson = budgPaymentItemService.update(mapVo);
		
		return JSONObject.parseObject(budgPaymentItemJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/addOrUpdateBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgPaymentItemJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("payment_item_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("payment_item_name").toString()));
		
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
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("").toString()));
	  
		budgPaymentItemJson = budgPaymentItemService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgPaymentItemJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/deleteBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgPaymentItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("payment_item_id", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgPaymentItemJson = budgPaymentItemService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgPaymentItemJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/queryBudgPaymentItem", method = RequestMethod.POST)
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
		
		String budgPaymentItem = budgPaymentItemService.query(getPage(mapVo));

		return JSONObject.parseObject(budgPaymentItem);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/budgPaymentItemImportPage", method = RequestMethod.GET)
	public String budgPaymentItemImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgpayitem/payitemdict/budgPaymentItemImport";

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
	 @RequestMapping(value="hrp/budg/base/budgpayitem/payitemdict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","支出项目字典模板.xls");
	    
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
	 * @throws Exception 
	*/
	@RequestMapping(value="/hrp/budg/base/budgpayitem/payitemdict/readBudgPaymentItemFiles",method = RequestMethod.POST)  
    public String readBudgPaymentItemFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws Exception { 
			 
		List<BudgPaymentItem> list_err = new ArrayList<BudgPaymentItem>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> map =  new HashMap<String,Object>();
		
		String[]  natureList = {"01","02","03","04","05"} ;
		
		String[]  controlList = {"01","02","03"} ;
		
		String[]  isOrNot = {"0","1"} ;
		
		String rules = getRules(map);
		
		String [] ruless  = rules.split("-");
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgPaymentItem budgPaymentItem = new BudgPaymentItem();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0])){
							err_sb.append("第"+i+"行数据与第 "+j+"行项目编码重复;");
						}
						
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行项目名称重复;");
						}
					} 
					
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
						budgPaymentItem.setPayment_item_code(temp[0]);
			    		mapVo.put("payment_item_code", temp[0]);
					
					} else {
						
						err_sb.append("项目编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
					budgPaymentItem.setPayment_item_name(temp[1]);
		    		mapVo.put("payment_item_name", temp[1]);
					
					} else {
						
						err_sb.append("项目名称为空;");
						
					}
					
					if(ExcelReader.validExceLColunm(temp,0) && ExcelReader.validExceLColunm(temp,1)){
			    		Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
			 	        
			 	        Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
			 	        
			 	        int super_num = 0;
			 	        
			 	        for(int m = 0; m < ruless.length; m++){
			 	        	int num = Integer.parseInt(ruless[m].replace(" ", ""));
			 	        	super_num += num;
			 	        	maxNumMap.put(super_num, m + 1);
			 	        	position.put(m + 1, super_num);
			 	        }
			 	        
			 	        if(maxNumMap.containsKey(temp[0].length())){//编码匹配
			 	        	 int item_level = maxNumMap.get(temp[0].length());
			 	        	 mapVo.put("item_level", item_level);
			 	        	 if(item_level == 1){
			 	        		 mapVo.put("super_code", "top");
			 	        		 //mapVo.put("item_name_all", mapVo.get("payment_item_name"));
			 	        	 }else{
			 	        		 int super_level =  item_level - 1;//上级级次
			 	            	 int subPosition = position.get(super_level);//上级级次位置
			 	            	 String super_code = temp[0].substring(0,subPosition);//截取上级编码
			 	        		 mapVo.put("super_code", super_code);
			 	        		/* mapVo.put("item_name_all", budgPaymentItemService.queryBySuperCode(mapVo));
			 	        		 
			 	        		 // 当item_level != 1 时 执行方法 budgPaymentItemService.queryBySuperCode(mapVo)
			 	        		 //会改变 项目编码 payment_item_code super_code 这里要重置  
						 	      mapVo.put("payment_item_code", temp[0]);
						 	      mapVo.put("super_code", super_code);*/
			 	        	 }
			 	        }else{
			 	        	err_sb.append("项目编码不符合编码规则:"+rules+"!");
			 	        }
			 	       
					}
					 
					if (ExcelReader.validExceLColunm(temp,2)) {
						budgPaymentItem.setItem_name_all(temp[2]);
					} 
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						budgPaymentItem.setIs_last(Integer.valueOf(temp[3]));
						
						if(Arrays.asList(isOrNot).contains(temp[3])){
							
							mapVo.put("is_last", temp[3]);
						}else{
							err_sb.append("是否末级填写不符合规则(0或1);");
						}
			    		
					
					} else {
						
						err_sb.append("是否末级为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgPaymentItem.setPayment_item_nature(temp[4]);
						if(Arrays.asList(natureList).contains(temp[4])){
				    		mapVo.put("payment_item_nature", temp[4]);
						}else{
							err_sb.append("支出项目性质编码不符合规则(01-05);");
						}
					
					} else {
						
						err_sb.append("支出项目性质为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						budgPaymentItem.setIs_manage(Integer.valueOf(temp[5]));
						if(Arrays.asList(isOrNot).contains(temp[5])){
							
							mapVo.put("is_manage", temp[5]);
						}else{
							err_sb.append("是否管理费填写不符合规则(0或1);");
						}
					
					} else {
						
						err_sb.append("是否管理费为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						budgPaymentItem.setControl_way(temp[6]);
						
						if(Arrays.asList(controlList).contains(temp[6])){
							
				    		mapVo.put("control_way", temp[6]);
						}else{
							err_sb.append("控制方式不符合规则(01-03);");
						}
					
					} else {
						
						err_sb.append("控制方式为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,7)) {
						
						budgPaymentItem.setIs_stop(Integer.valueOf(temp[7]));
						if(Arrays.asList(isOrNot).contains(temp[7])){
							
							mapVo.put("is_stop", temp[7]);
						}else{
							err_sb.append("是否停用填写不符合规则(0或1);");
						}
					
					} else {
						
						err_sb.append("是否停用为空;");
						
					}
					 
				BudgPaymentItem data_exc_extis = budgPaymentItemService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgPaymentItem.setError_type(err_sb.toString());
					
					list_err.add(budgPaymentItem);
					
				} else {
					
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("payment_item_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("payment_item_name").toString()));
				  
				  mapVo.put("acc_subj_manage", "");
				  mapVo.put("acc_subj_medical", "");
				  mapVo.put("acc_subj_education", "");
				  mapVo.put("acc_subj_scientific", "");
				  mapVo.put("acc_subj_financial", "");
				  
				  addList.add(mapVo);
				}
				
			}
			
			if(list_err.size() == 0){
				if(addList.size() > 0){
					for(Map<String,Object> item : addList){
						String dataJson = budgPaymentItemService.add(item);
					}
					Map<String, Object> mapVo1 = new HashMap<String, Object>();
					
		    		mapVo1.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo1.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo1.put("copy_code", SessionManager.getCopyCode()); 
		    		mapVo1.put("acc_year", SessionManager.getAcctYear()); 
					
					budgPaymentItemService.prcUpdateBudgItemALL(mapVo1);
				
				}else{
					BudgPaymentItem data_exc = new BudgPaymentItem();
					
					data_exc.setError_type("没有数据,导入失败!");
					
					list_err.add(data_exc);
				}
				
			}
		} catch (DataAccessException e) {
			
			BudgPaymentItem data_exc = new BudgPaymentItem();
			
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
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/payitemdict/addBatchBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgPaymentItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgPaymentItem> list_err = new ArrayList<BudgPaymentItem>();
		
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
			
			BudgPaymentItem budgPaymentItem = new BudgPaymentItem();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("payment_item_id"))) {
						
					budgPaymentItem.setPayment_item_id(Long.valueOf((String)jsonObj.get("payment_item_id")));
		    		mapVo.put("payment_item_id", jsonObj.get("payment_item_id"));
		    		} else {
						
						err_sb.append("支出项目ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("payment_item_code"))) {
						
					budgPaymentItem.setPayment_item_code((String)jsonObj.get("payment_item_code"));
		    		mapVo.put("payment_item_code", jsonObj.get("payment_item_code"));
		    		} else {
						
						err_sb.append("项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("payment_item_name"))) {
						
					budgPaymentItem.setPayment_item_name((String)jsonObj.get("payment_item_name"));
		    		mapVo.put("payment_item_name", jsonObj.get("payment_item_name"));
		    		} else {
						
						err_sb.append("项目名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("item_name_all"))) {
						
					budgPaymentItem.setItem_name_all((String)jsonObj.get("item_name_all"));
		    		mapVo.put("item_name_all", jsonObj.get("item_name_all"));
		    		} else {
						
						err_sb.append("项目全称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("super_code"))) {
						
					budgPaymentItem.setSuper_code(String.valueOf(jsonObj.get("super_code")));
		    		mapVo.put("super_code", jsonObj.get("super_code"));
		    		} else {
						
						err_sb.append("上级项目为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("item_level"))) {
						
					budgPaymentItem.setItem_level(Integer.valueOf((String)jsonObj.get("item_level")));
		    		mapVo.put("item_level", jsonObj.get("item_level"));
		    		} else {
						
						err_sb.append("项目级次为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_last"))) {
						
					budgPaymentItem.setIs_last(Integer.valueOf((String)jsonObj.get("is_last")));
		    		mapVo.put("is_last", jsonObj.get("is_last"));
		    		} else {
						
						err_sb.append("是否末级为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("payment_item_nature"))) {
						
					budgPaymentItem.setPayment_item_nature((String)jsonObj.get("payment_item_nature"));
		    		mapVo.put("payment_item_nature", jsonObj.get("payment_item_nature"));
		    		} else {
						
						err_sb.append("支出项目性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_manage"))) {
						
					budgPaymentItem.setIs_manage(Integer.valueOf((String)jsonObj.get("is_manage")));
		    		mapVo.put("is_manage", jsonObj.get("is_manage"));
		    		} else {
						
						err_sb.append("是否管理费为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("control_way"))) {
						
					budgPaymentItem.setControl_way((String)jsonObj.get("control_way"));
		    		mapVo.put("control_way", jsonObj.get("control_way"));
		    		} else {
						
						err_sb.append("控制方式为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					budgPaymentItem.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					budgPaymentItem.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					budgPaymentItem.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acc_subj_manage"))) {
						
					budgPaymentItem.setAcc_subj_manage(Integer.valueOf((String)jsonObj.get("acc_subj_manage")));
		    		mapVo.put("acc_subj_manage", jsonObj.get("acc_subj_manage"));
		    		} else {
						
						err_sb.append("管理支出对应会计科目为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acc_subj_medical"))) {
						
					budgPaymentItem.setAcc_subj_medical(Integer.valueOf((String)jsonObj.get("acc_subj_medical")));
		    		mapVo.put("acc_subj_medical", jsonObj.get("acc_subj_medical"));
		    		} else {
						
						err_sb.append("医疗支出对应会计科目为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acc_subj_education"))) {
						
					budgPaymentItem.setAcc_subj_education(Integer.valueOf((String)jsonObj.get("acc_subj_education")));
		    		mapVo.put("acc_subj_education", jsonObj.get("acc_subj_education"));
		    		} else {
						
						err_sb.append("教学支出对应会计科目为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acc_subj_scientific"))) {
						
					budgPaymentItem.setAcc_subj_scientific(Integer.valueOf((String)jsonObj.get("acc_subj_scientific")));
		    		mapVo.put("acc_subj_scientific", jsonObj.get("acc_subj_scientific"));
		    		} else {
						
						err_sb.append("科研支出对应会计科目为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acc_subj_financial"))) {
						
					budgPaymentItem.setAcc_subj_financial(Integer.valueOf((String)jsonObj.get("acc_subj_financial")));
		    		mapVo.put("acc_subj_financial", jsonObj.get("acc_subj_financial"));
		    		} else {
						
						err_sb.append("财政支出对应会计科目为空  ");
						
					}
					
					
				BudgPaymentItem data_exc_extis = budgPaymentItemService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgPaymentItem.setError_type(err_sb.toString());
					
					list_err.add(budgPaymentItem);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("payment_item_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("payment_item_name").toString()));
			  
					String dataJson = budgPaymentItemService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgPaymentItem data_exc = new BudgPaymentItem();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

