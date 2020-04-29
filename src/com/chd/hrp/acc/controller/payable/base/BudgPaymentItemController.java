/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.controller.payable.base;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.payable.BudgBorrApplyDet;
import com.chd.hrp.acc.entity.payable.BudgBorrBegainDet;
import com.chd.hrp.acc.entity.payable.BudgBorrReturnDet;
import com.chd.hrp.acc.entity.payable.BudgPaymentApplyDet;
import com.chd.hrp.acc.entity.payable.BudgPaymentItem;
import com.chd.hrp.acc.service.payable.base.BudgPaymentItemService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrApplyDetService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrBegainDetService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrReturnDetService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyDetService;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;
/**
 * 
 * @Description:
 * 控制方式
 * @Table:
 * BUDG_PAYMENT_ITEM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgPaymentItemController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgPaymentItemController.class);
	
	//引入Service服务
	@Resource(name = "budgPaymentItemService")
	private final BudgPaymentItemService budgPaymentItemService = null;
	
	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;
	
	@Resource(name = "budgBorrApplyDetService")
	private final BudgBorrApplyDetService budgBorrApplyDetService = null;
	
	@Resource(name = "budgBorrBegainDetService")
	private final BudgBorrBegainDetService budgBorrBegainDetService = null;
	
	@Resource(name = "budgBorrReturnDetService")
	private final BudgBorrReturnDetService budgBorrReturnDetService = null;
	
	@Resource(name = "budgPaymentApplyDetService")
	private final BudgPaymentApplyDetService budgPaymentApplyDetService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/base/paymentitem/budgPaymentItemMainPage", method = RequestMethod.GET)
	public String budgPaymentItemMainPage(Model mode) throws Exception {

		return "hrp/acc/payable/base/paymentitem/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/base/paymentitem/budgPaymentItemAddPage", method = RequestMethod.GET)
	public String budgPaymentItemAddPage(Model mode) throws Exception {

		return "hrp/acc/payable/base/paymentitem/add";

	}

	/**
	 * @Description 
	 * 添加数据 控制方式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/base/paymentitem/addBudgPaymentItem", method = RequestMethod.POST)
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
        
        String payment_item_code = (String)mapVo.get("payment_item_code");//支出项目编码
        
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
        
        if(maxNumMap.containsKey(payment_item_code.length())){//编码匹配
        	 int item_level = maxNumMap.get(payment_item_code.length());
        	 mapVo.put("item_level", item_level);
        	 if(item_level == 1){
        		 mapVo.put("super_code", "top");
        		 mapVo.put("item_name_all", mapVo.get("payment_item_name"));
        	 }else{
        		 int super_level =  item_level - 1;//上级级次
            	 int subPosition = position.get(super_level);//上级级次位置
            	 String super_code = payment_item_code.substring(0,subPosition);//截取上级编码
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
	
	@RequestMapping(value = "/hrp/acc/payable/base/paymentitem/showRules", method = RequestMethod.POST)
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
	 * 更新跳转页面 控制方式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/base/paymentitem/budgPaymentItemUpdatePage", method = RequestMethod.GET)
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
		mode.addAttribute("payment_item_no", budgPaymentItem.getPayment_item_no());
		mode.addAttribute("payment_item_code", budgPaymentItem.getPayment_item_code());
		mode.addAttribute("payment_item_name", budgPaymentItem.getPayment_item_name());
		mode.addAttribute("item_name_all", budgPaymentItem.getItem_name_all());
		mode.addAttribute("super_code", budgPaymentItem.getSuper_code());
		mode.addAttribute("item_level", budgPaymentItem.getItem_level());
		mode.addAttribute("is_last", budgPaymentItem.getIs_last());
		mode.addAttribute("payment_item_nature", budgPaymentItem.getPayment_item_nature());
		mode.addAttribute("payment_item_nature_name", budgPaymentItem.getPayment_item_nature_name());
		mode.addAttribute("is_manage", budgPaymentItem.getIs_manage());
		mode.addAttribute("control_way", budgPaymentItem.getControl_way());
		mode.addAttribute("control_way_name", budgPaymentItem.getControl_way_name());
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
		mode.addAttribute("acc_subj_education_name", budgPaymentItem.getAcc_subj_education_name());
		
		mode.addAttribute("acc_subj_scientific", budgPaymentItem.getAcc_subj_scientific());
		mode.addAttribute("acc_subj_scientific_code", budgPaymentItem.getAcc_subj_scientific_code());
		mode.addAttribute("acc_subj_scientific_name", budgPaymentItem.getAcc_subj_scientific_name());
		
		mode.addAttribute("acc_subj_financial", budgPaymentItem.getAcc_subj_financial());
		mode.addAttribute("acc_subj_financial_code", budgPaymentItem.getAcc_subj_financial_code());
		mode.addAttribute("acc_subj_financial_name", budgPaymentItem.getAcc_subj_financial_name());
		
		return "hrp/acc/payable/base/paymentitem/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 控制方式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/base/paymentitem/updateBudgPaymentItem", method = RequestMethod.POST)
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
		
		String rules = getRules(mapVo);
        String payment_item_code = (String)mapVo.get("payment_item_code");//支出项目编码
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
        
        if(maxNumMap.containsKey(payment_item_code.length())){//编码匹配
        	 int item_level = maxNumMap.get(payment_item_code.length());
        	 mapVo.put("item_level", item_level);
        	 if(item_level == 1){
        		 mapVo.put("super_code", "top");
        		 mapVo.put("item_name_all", mapVo.get("payment_item_name"));
        	 }else{
        		 int super_level =  item_level - 1;//上级级次
            	 int subPosition = position.get(super_level);//上级级次位置
            	 String super_code = payment_item_code.substring(0,subPosition);//截取上级编码
        		 mapVo.put("super_code", super_code);
        	 }
        }else{
        	return JSONObject.parseObject("{\"error\":\"添加失败，不符合编码规则，请重新输入！\"}");
        }
        
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("payment_item_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("payment_item_name").toString()));
	  
		String budgPaymentItemJson = budgPaymentItemService.update(mapVo);
		
		return JSONObject.parseObject(budgPaymentItemJson);
	}
	
	
	/**
	 * @Description 
	 * 删除数据 控制方式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/base/paymentitem/deleteBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgPaymentItem(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		boolean flag = true;
		String msg = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("payment_item_id", ids[3]);
				
				List<BudgBorrApplyDet> applyList = (List<BudgBorrApplyDet>)budgBorrApplyDetService.queryExists(mapVo);
				
				List<BudgBorrBegainDet> begainList = (List<BudgBorrBegainDet>)budgBorrBegainDetService.queryExists(mapVo);
				
				List<BudgBorrReturnDet> returnList = (List<BudgBorrReturnDet>)budgBorrReturnDetService.queryExists(mapVo);
				
				List<BudgPaymentApplyDet> paymenApplytList = (List<BudgPaymentApplyDet>)budgPaymentApplyDetService.queryExists(mapVo);
				
				if(applyList.size() > 0){
					msg = msg + ids[4]+" "+ids[5]+" 已被借款单引用,不能删除!";
					flag = false;
					break;
				}
				
				if(begainList.size() > 0){
					msg = msg + ids[4]+" "+ids[5]+" 已被期初借款引用,不能删除!";
					flag = false;
					break;
				}
				
				if(returnList.size() > 0){
					msg = msg + ids[4]+" "+ids[5]+" 已被退款单引用,不能删除!";
					flag = false;
					break;
				}
				
				if(paymenApplytList.size() > 0){
					msg = msg + ids[4]+" "+ids[5]+" 已被报销单引用,不能删除!";
					flag = false;
					break;
				}
				
				listVo.add(mapVo);
	    }
	    
			if(flag){
				String budgPaymentItemJson = budgPaymentItemService.deleteBatch(listVo);
				
				  return JSONObject.parseObject(budgPaymentItemJson);
			}else{
				return JSONObject.parseObject("{\"error\":\""+msg+"\"}");
			}
		
			
	}
	
	/**
	 * @Description 
	 * 查询数据 控制方式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/base/paymentitem/queryBudgPaymentItem", method = RequestMethod.POST)
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
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String budgPaymentItem = budgPaymentItemService.query(mapVo);

		return JSONObject.parseObject(budgPaymentItem);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 支出项目
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/base/paymentitem/payMentItemImportPage", method = RequestMethod.GET)
	public String budgCostSubjImportPage(Model mode) throws Exception {

		return "hrp/acc/payable/base/paymentitem/import";

	}
	/**
	 * @Description 
	 * 下载导入模版 支出项目
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/acc/payable/base/paymentitem/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"acc\\base","支出项目模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 支出性质
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/acc/payable/base/paymentitem/readPaymentItemFiles",method = RequestMethod.POST)  
    public String readBudgPaymentItemFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgPaymentItem> list_err = new ArrayList<BudgPaymentItem>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
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
					if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
				}
				
				Long payment_item_id = budgPaymentItemService.queryPaymentItemSequence();
				Long payment_item_no = budgPaymentItemService.queryPaymentItemDictSequence();
				
				mapVo.put("payment_item_id", payment_item_id);
				mapVo.put("payment_item_no", payment_item_no);
				
				if (StringTool.isNotBlank(temp[0])) {
					
					budgPaymentItem.setPayment_item_code(String.valueOf(temp[0]));		
					mapVo.put("payment_item_code", temp[0]);
	    		
				} else {
					
					err_sb.append("项目编码为空  ");
					
				}
				
				if (StringTool.isNotBlank(temp[1])) {
					
					budgPaymentItem.setPayment_item_name(String.valueOf(temp[1]));	
					mapVo.put("payment_item_name", temp[1]);
	    		
				} else {
					
					err_sb.append("项目名称为空  ");
					
				}
				
				if (StringTool.isNotBlank(temp[2])) {
					
					budgPaymentItem.setItem_name_all(String.valueOf(temp[2]));		
					mapVo.put("item_name_all", temp[2]);
	    		
				} else {
					
					err_sb.append("项目全称为空  ");
					
				}
				
				if (StringTool.isNotBlank(temp[3])) {
					
					budgPaymentItem.setPayment_item_nature(String.valueOf(temp[3]));	
					
					mapVo.put("payment_item_nature", temp[3]);
					
				} else {
					
					err_sb.append("项目性质为空  ");
					
				}
				
				if (StringTool.isNotBlank(temp[4])) {
					
					budgPaymentItem.setItem_level(Integer.valueOf(temp[4]));
					
					mapVo.put("item_level", temp[4]);
					
				} else {
					
					err_sb.append("项目级次为空  ");
					
				}
				
				if (StringTool.isNotBlank(temp[5])) {
					
				budgPaymentItem.setIs_last(Integer.valueOf(temp[5]));
							
	    		mapVo.put("is_last", temp[5]);
	    		
				} else {
					
					err_sb.append("是否末级为空  ");
					
				}
				
				if (StringTool.isNotBlank(temp[6])) {
					
					budgPaymentItem.setControl_way(String.valueOf(temp[6]));		
					mapVo.put("control_way", temp[6]);
	    		
				} else {
					
					err_sb.append("控制方式为空  ");
					
				}
				
				if (StringTool.isNotBlank(temp[7])) {
					
					budgPaymentItem.setIs_manage(Integer.parseInt(String.valueOf(temp[7])));		
					mapVo.put("is_manage", temp[7]);
					
				} else {
					
					err_sb.append("是否管理费为空  ");
					
				}
				if (StringTool.isNotBlank(temp[8])) {
					
					budgPaymentItem.setIs_stop(Integer.parseInt(String.valueOf(temp[8])));		
					mapVo.put("is_stop", temp[8]);
					
				} else {
					
					err_sb.append("是否停用为空  ");
					
				}
				String rules= new String();
				try {
					rules = getRules(mapVo);
					
					if(rules == null || rules == ""){
						err_sb.append("支出项目编码规则未设置！");
						
					}else{
						
						String payment_item_code = (String)mapVo.get("payment_item_code");
						String [] ruless  = rules.split("-");
						Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
						    
					    Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
					    
					    int super_num = 0;
					    
					    for(int j = 0; j < ruless.length; j++){
					    	int num = Integer.parseInt(ruless[j].replace(" ", ""));
					    	super_num += num;
					    	maxNumMap.put(super_num, j + 1);
					    	position.put(j + 1, super_num);
					    }
					    
					    if(maxNumMap.containsKey(payment_item_code.length())){//编码匹配
					    	 int item_level = maxNumMap.get(payment_item_code.length());
					    	 mapVo.put("item_level", item_level);
					    	 if(item_level == 1){
					    		 mapVo.put("super_code", "0");
					    	 }else{
					    		 int super_level =  item_level - 1;//上级级次
					        	 int subPosition = position.get(super_level);//上级级次位置
					        	 String super_code = payment_item_code.substring(0,subPosition);//截取上级编码
					        	 mapVo.put("super_code", super_code);
					    	 }
					    }else{
					    	err_sb.append("不符合编码规则！");
					    }
					}
				} catch (Exception e) {
					e.printStackTrace();
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
				  
				  mapVo.put("acc_subj_manage", "");
				  mapVo.put("acc_subj_medical", "");
				  mapVo.put("acc_subj_education", "");
				  mapVo.put("acc_subj_scientific", "");
				  mapVo.put("acc_subj_financial", "");
				  
				  mapVo.put("is_fresh",1);
				  
				  addList.add(mapVo);
				}
			}
			
			if(list_err.size() == 0){
				String dataJson = budgPaymentItemService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			
			BudgPaymentItem data_exc = new BudgPaymentItem();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
	
}

