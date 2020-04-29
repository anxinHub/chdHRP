/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.intangible;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgAssCard;
import com.chd.hrp.budg.service.business.fixedassets.BudgAssCardService;
import com.chd.hrp.budg.service.business.intangible.BudgAssCardIntangibleService;
/**
 * 
 * @Description:
 * 
 * @Table:
 *无形资产
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgAssCardIntangbleController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAssCardIntangbleController.class);
	
	//引入Service服务
	@Resource(name = "budgAssCardIntangibleService")
	private final BudgAssCardIntangibleService budgAssCardIntangibleService = null;
	
	//引入Service服务
	@Resource(name = "budgAssCardService")
	private final BudgAssCardService budgAssCardService = null;
	
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/budgAssCardactualityIntangibleMainPage", method = RequestMethod.GET)
	public String budgAssCardactualityIntangibleMainPage(Model mode) throws Exception {

		return "hrp/budg/business/intangible/budgasscard/budgAssCardIntangbleMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/budgAssCardAddPage", method = RequestMethod.GET)
	public String budgAssCardAddPage(Model mode) throws Exception {

		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		
		return "hrp/budg/business/intangible/budgasscard/budgAssCardIntangibleAdd";

	}

	/**
	 * @Description 
	 * 添加数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/addBudgInAssCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgInAssCard(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
    	mapVo.put("acct_year", SessionManager.getAcctYear());
		
       
		String budgAssCardJson = budgAssCardIntangibleService.add(mapVo);

		return JSONObject.parseObject(budgAssCardJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/budgAssCardUpdatePage", method = RequestMethod.GET)
	public String budgAssCardUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
    
		Map<String,Object> budgAssCard= budgAssCardIntangibleService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgAssCard.get("group_id"));
		mode.addAttribute("hos_id", budgAssCard.get("hos_id"));
		mode.addAttribute("copy_code", budgAssCard.get("copy_code"));
		mode.addAttribute("ass_card_no", budgAssCard.get("ass_card_no"));
		mode.addAttribute("ass_card_no", budgAssCard.get("ass_card_no"));
		mode.addAttribute("ass_id", budgAssCard.get("ass_id"));
		mode.addAttribute("ass_name", budgAssCard.get("ass_name"));
		mode.addAttribute("naturs_code", budgAssCard.get("naturs_code"));
		mode.addAttribute("naturs_name", budgAssCard.get("naturs_name"));
		mode.addAttribute("dept_id", budgAssCard.get("dept_id"));
		mode.addAttribute("dept_name", budgAssCard.get("dept_name"));
		mode.addAttribute("use_state", budgAssCard.get("use_state"));
		mode.addAttribute("state_name", budgAssCard.get("state_name"));
		mode.addAttribute("is_dept", budgAssCard.get("is_dept"));
		mode.addAttribute("store_id", budgAssCard.get("store_id"));
		mode.addAttribute("store_name", budgAssCard.get("store_name"));
		mode.addAttribute("is_throw", budgAssCard.get("is_throw"));
		mode.addAttribute("in_date", budgAssCard.get("in_date"));
		mode.addAttribute("note", budgAssCard.get("note"));
		mode.addAttribute("is_depr", budgAssCard.get("is_depr"));
		mode.addAttribute("depr_method", budgAssCard.get("depr_method"));
		mode.addAttribute("ass_depre_name", budgAssCard.get("ass_depre_name"));
		mode.addAttribute("acc_depre_amount", budgAssCard.get("acc_depre_amount"));
		mode.addAttribute("price", budgAssCard.get("price"));
		mode.addAttribute("depre_money", budgAssCard.get("depre_money"));
		mode.addAttribute("cur_money", budgAssCard.get("cur_money"));
		mode.addAttribute("fore_money", budgAssCard.get("fore_money"));
		mode.addAttribute("add_depre_month", budgAssCard.get("add_depre_month"));
		mode.addAttribute("last_depr_year", budgAssCard.get("last_depr_year"));
		mode.addAttribute("last_depr_month", budgAssCard.get("last_depr_month"));
		
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		
		return "hrp/budg/business/intangible/budgasscard/budgAssCardIntangibleUpdate";
	}
	/**
	 * @Description 
	 * 查询数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/queryBudgAssCardSourceBySourceId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAssCardSourceBySourceId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		String budgAssCard = budgAssCardIntangibleService.queryBudgAssCardSourceBySourceId(mapVo);

		return JSONObject.parseObject(budgAssCard);
	}
	/**
	 * @Description 
	 * 更新数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/updateBudgAssCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAssCard(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgAssCardJson = budgAssCardIntangibleService.update(mapVo);
		
		return JSONObject.parseObject(budgAssCardJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/addOrUpdateBudgAssCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAssCard(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAssCardJson ="";
		

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
	  
		budgAssCardJson = budgAssCardIntangibleService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAssCardJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/deleteBudgAssCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAssCard(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("ass_card_no", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgAssCardJson = budgAssCardIntangibleService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAssCardJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/queryBudgAssCardIntangible", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAssCardIntangible(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgAssCard = budgAssCardIntangibleService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAssCard);
		
	}
	
	/**
	 *@Description 
	 * 导入跳转页面  无形资产现状
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/budgAssIntangibleCardImportPage", method = RequestMethod.GET)
	public String budgAssCardImportPage(Model mode) throws Exception {

		return "hrp/budg/business/intangible/budgasscard/budgAssIntangibleCardImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 无形资产现状
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/intangible/budgasscard/downTemplate1", method = RequestMethod.GET)  
	 public String downTemplate1(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\cost\\ass","无形资产现状.xls");
	    
	    return null; 
	 }
	 
	 /**
	 * @Description 
	 * 导入跳转页面  无形资产现状——资金来源
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/budgAssIntangibleCardSourceImportPage", method = RequestMethod.GET)
	public String budgAssIntangibleCardSourceImportPage(Model mode) throws Exception {

		return "hrp/budg/business/intangible/budgasscard/budgAssIntangibleCardSourceImport";

	}
	/**
	 * @Description 
	 * 下载导入模版  无形资产现状——资金来源
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/intangible/budgasscard/downTemplate2", method = RequestMethod.GET)  
	 public String downTemplate2(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\cost\\ass","无形资产现状_资金来源.xls");
	    
	    return null; 
	 }
 
	 /**
	 * @Description 
	 * 导入数据 无形资产现状
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/intangible/budgasscard/readBudgAssCardFiles",method = RequestMethod.POST)  
    public String readBudgAssCardFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		//月份
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		//使用状态
		String[] useState = {"0","1","2","3","4","5","6","7","8"} ;
		//状态
		String[] isNO = {"0","1"} ;
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		 
		paraMap.put("hos_id", SessionManager.getHosId());   
		 
		paraMap.put("copy_code", SessionManager.getCopyCode());  
		
		//查询 资产基本信息(根据code 匹配ID用)
		List<Map<String,Object>> assData = budgAssCardService.queryAssDictData(paraMap);
		
		//查询 科室基本信息(根据code 匹配ID用)
		List<Map<String,Object>> deptData = budgAssCardService.queryDeptData(paraMap);
		
		//查询 仓库基本信息(根据code 匹配ID用)
		List<Map<String,Object>> storeData = budgAssCardService.queryStoreData(paraMap);
		
		//查询 资产折旧方法基本信息(校验资产折旧方法用)
		List<String> deprMethod = budgAssCardService.queryDeprMethod(paraMap);
		
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		        
		    		mapVo.put("naturs_code", "05");//资产性质 05 无形资产
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[3].equals(error[3])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
						map.put("ass_card_no",temp[0]);
			    		mapVo.put("ass_card_no", temp[0]);
					
					} else {
						
						err_sb.append("卡片编号为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
						int flag = 0 ;
						for(Map<String,Object> item: assData){
							
							if(temp[1].equals(item.get("ass_code"))){
								
								mapVo.put("ass_id", item.get("ass_id"));
								
								flag = 1 ;
								
								break ;
							}
							
						}
						map.put("ass_code",temp[1]);
						map.put("ass_name",temp[2]);
						
						if(flag == 0 ){
							err_sb.append("资产编码不存在或已停用,输入错误;");
						}
						
					} else {
						
						err_sb.append("资产编码为空;");
						
					}
					 
					 
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						int flag = 0 ;
						for(Map<String,Object> item: deptData){
							
							if(temp[3].equals(item.get("dept_code"))){
								
								mapVo.put("dept_id", item.get("dept_id"));
								
								flag = 1 ;
								
								break ;
							}
							
						}
						
						map.put("dept_code",temp[3]);
						map.put("dept_name",temp[4]);
						
						if(flag == 0 ){
							err_sb.append("管理部门编码不存在或已停用,输入错误;");
						}
					
					} else {
						
						err_sb.append("管理部门编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						if(Arrays.asList(useState).contains(temp[5])){
							
							mapVo.put("use_state",Long.valueOf(temp[5]));
							
						}else{
							
							err_sb.append("使用状态输入不合法(0-8);");
						}
						
			    		map.put("use_state", temp[5]);
					
					} else {
						
						err_sb.append("使用状态为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						if(Arrays.asList(isNO).contains(temp[6])){
							
							mapVo.put("is_dept",Integer.valueOf(temp[6]));
						}else{
							err_sb.append("在用状态输入不合法(0-1);");
						}
						
			    		map.put("is_dept", temp[6]);
					
					} else {
						
						err_sb.append("在用状态为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,7)) {
						
						int flag = 0 ;
						
						for(Map<String,Object> item : storeData){
							if(temp[7].equals(item.get("store_code"))){
								
								mapVo.put("store_id",item.get("store_id"));
								
								flag = 1 ;
								
								break ;
							}
							
						}
						
						if(flag ==0){
							err_sb.append("仓库编码不存在或已停用,输入错误;");
						}
			    		map.put("store_code", temp[7]);
			    		
			    		map.put("store_name", temp[8]);
					
					} else {
						
						err_sb.append("仓库编码为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,9)) {
						
						if(Arrays.asList(isNO).contains(temp[9])){
							
							mapVo.put("is_throw",Integer.valueOf(temp[9]));
							
						}else{
							
							err_sb.append("是否投放输入不合法(0-1);");
							
						}
						
			    		map.put("is_throw", temp[9]);
					
					} else {
						
						err_sb.append("是否投放为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,10)) {
						
						try {
							
							mapVo.put("in_date", df.parse(temp[10]));
							
						} catch (Exception e) {
							err_sb.append("入库日期填写格式错误(yyyyMMdd);");
						}
						
						map.put("in_date",temp[10]);
					
					} else {
						
						err_sb.append("入库日期为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,11)) {
						
						map.put("note",temp[11]);
			    		mapVo.put("note", temp[11]);
					
					} else {
						
						map.put("note","");
			    		mapVo.put("note", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,12)) {
						
						if(Arrays.asList(isNO).contains(temp[12])){
							
							mapVo.put("is_depr",Integer.valueOf(temp[12]));
							
						}else{
							
							err_sb.append("是否折旧输入不合法(0-1);");
							
						}
						
			    		map.put("is_depr", temp[12]);
					
					} else {
						
						err_sb.append("是否折旧为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,13)) {
						
						if(deprMethod.contains(temp[13])){
							
							mapVo.put("depr_method", temp[13]);
							
						}else{
							
							err_sb.append("折旧方法不存在或已停用,输入错误;");
							
						}
						
						map.put("depr_method",temp[13]);
			    		
					
					} else {
						
						err_sb.append("折旧方法为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,14)) {
						
						map.put("acc_depre_amount",Integer.valueOf(temp[14]));
			    		mapVo.put("acc_depre_amount", temp[14]);
					
					} else {
						err_sb.append("计提年数为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,15)) {
						
						map.put("price",Double.valueOf(temp[15]));
			    		mapVo.put("price", temp[15]);
					
					} else {
						
						err_sb.append("资产原值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,16)) {
						
						map.put("depre_money",Double.valueOf(temp[16]));
			    		mapVo.put("depre_money", temp[16]);
					
					} else {
						
						err_sb.append("累计折旧为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,17)) {
						
						map.put("cur_money",Double.valueOf(temp[17]));
			    		mapVo.put("cur_money", temp[17]);
					
					} else {
						
						err_sb.append("资产净值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,18)) {
						
						map.put("fore_money",Double.valueOf(temp[18]));
			    		mapVo.put("fore_money", temp[18]);
					
					} else {
						
						err_sb.append("预留残值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,19)) {
						
						map.put("add_depre_month",Integer.valueOf(temp[19]));
			    		mapVo.put("add_depre_month", temp[19]);
					
					} else {
						
						err_sb.append("累计折旧月份为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,20)) {
						
						map.put("last_depr_year",temp[20]);
			    		mapVo.put("last_depr_year", temp[20]);
						
					} else {
						
						err_sb.append("上次折旧年为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,21)) {
						
						if(Arrays.asList(monthData).contains(temp[21])){
							
							mapVo.put("last_depr_month", temp[21]);
							
						}else{
							err_sb.append("上次折旧月填写错误(必须两位数字【01至12】);");
						}
						map.put("last_depr_month",temp[21]);
					
					} else {
						
						err_sb.append("上次折旧月为空;");
						
					}
					 
					
				int count = budgAssCardIntangibleService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					map.put("error_type",err_sb.toString());
					
					list_err.add(map);
					
				} else {
			  
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				if(addList.size() > 0){
					
					String dataJson = budgAssCardIntangibleService.addBatch(addList);
					
				}else{
					
					Map<String, Object> data_exc = new HashMap<String, Object>();
					
					data_exc.put("error_type","没有导入数据,导入失败。");
					
					list_err.add(data_exc);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			Map<String, Object> data_exc = new HashMap<String, Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
	
	 /**
	 * @Description 
	 * 导入数据  无形资产现状——资金来源
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/intangible/budgasscard/readBudgAssCardSourceFiles",method = RequestMethod.POST)  
    public String readBudgAssCardSourceFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		 
		paraMap.put("hos_id", SessionManager.getHosId());   
		 
		paraMap.put("copy_code", SessionManager.getCopyCode());  
		
		
		//查询 资金来源基本信息(根据code 匹配ID用)
		List<Map<String,Object>> sourceData = budgAssCardService.querySourceData(paraMap);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());  
		    		
		    		mapVo.put("naturs_code", "05");//资产性质 05 无形资产
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
						map.put("ass_card_no",temp[0]);
			    		mapVo.put("ass_card_no", temp[0]);
					
					} else {
						
						err_sb.append("卡片编号为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						int flag = 0 ;
						
						for(Map<String,Object> item : sourceData){
							if(temp[1].equals(item.get("source_code"))){
								
								mapVo.put("source_id", item.get("source_id"));
								
								flag = 1 ;
								
								break ;
							}
						}
						
						if(flag == 0){
							err_sb.append("资金来源编号不存在或已停用,输入错误;");
						}
						map.put("source_code",temp[1]);
						map.put("source_name",temp[2]);
			    		
					
					} else {
						
						err_sb.append("资金来源编号为空;");
						
					} 
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						map.put("price",Double.valueOf(temp[3]));
			    		mapVo.put("price", temp[3]);
					
					} else {
						
						err_sb.append("卡片原值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						map.put("depre_money",Double.valueOf(temp[4]));
			    		mapVo.put("depre_money", temp[4]);
					
					} else {
						
						mapVo.put("depre_money", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						map.put("cur_money",Double.valueOf(temp[5]));
			    		mapVo.put("cur_money", temp[5]);
					
					} else {
						
						mapVo.put("cur_money", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						map.put("fore_money",Double.valueOf(temp[6]));
			    		mapVo.put("fore_money", temp[6]);
					
					} else {
						
						mapVo.put("fore_money", "");
						
					}
					 
				int count = budgAssCardService.queryDataExistSource(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					map.put("error_type",err_sb.toString());
					
					list_err.add(map);
					
				} else {
			  
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				if(addList.size() > 0){
					//批量添加 固定资产现状——资金来源数据
					String dataJson = budgAssCardIntangibleService.addBudgAssCardSource(addList);
					
				}else{
					
					Map<String, Object> data_exc = new HashMap<String, Object>();
					
					data_exc.put("error_type","没有导入数据,导入失败。");
					
					list_err.add(data_exc);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			Map<String, Object> data_exc = new HashMap<String, Object>();
			
			data_exc.put("error_type","导入系统出错");
			
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
	@RequestMapping(value = "/hrp/budg/business/intangible/budgasscard/addBatchBudgAssCard", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgAssCard(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgAssCard> list_err = new ArrayList<BudgAssCard>();
		
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
			
			BudgAssCard budgAssCard = new BudgAssCard();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("ass_card_no"))) {
						
					budgAssCard.setAss_card_no((String)jsonObj.get("ass_card_no"));
		    		mapVo.put("ass_card_no", jsonObj.get("ass_card_no"));
		    		} else {
						
						err_sb.append("卡片编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_id"))) {
						
					budgAssCard.setAss_id(Long.valueOf((String)jsonObj.get("ass_id")));
		    		mapVo.put("ass_id", jsonObj.get("ass_id"));
		    		} else {
						
						err_sb.append("资产ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("naturs_code"))) {
						
					budgAssCard.setNaturs_code((String)jsonObj.get("naturs_code"));
		    		mapVo.put("naturs_code", jsonObj.get("naturs_code"));
		    		} else {
						
						err_sb.append("资产性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgAssCard.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("管理部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("use_state"))) {
						
					budgAssCard.setUse_state(Long.valueOf((String)jsonObj.get("use_state")));
		    		mapVo.put("use_state", jsonObj.get("use_state"));
		    		} else {
						
						err_sb.append("使用状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_dept"))) {
						
					budgAssCard.setIs_dept(Integer.valueOf((String)jsonObj.get("is_dept")));
		    		mapVo.put("is_dept", jsonObj.get("is_dept"));
		    		} else {
						
						err_sb.append("1：在用 0：在库为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					budgAssCard.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_throw"))) {
						
					budgAssCard.setIs_throw(Integer.valueOf((String)jsonObj.get("is_throw")));
		    		mapVo.put("is_throw", jsonObj.get("is_throw"));
		    		} else {
						
						err_sb.append("是否投放为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_date"))) {
						
					budgAssCard.setIn_date(DateUtil.stringToDate((String)jsonObj.get("in_date")));
		    		mapVo.put("in_date", jsonObj.get("in_date"));
		    		} else {
						
						err_sb.append("入库日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					budgAssCard.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_depr"))) {
						
					budgAssCard.setIs_depr(Integer.valueOf((String)jsonObj.get("is_depr")));
		    		mapVo.put("is_depr", jsonObj.get("is_depr"));
		    		} else {
						
						err_sb.append("是否折旧为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("depr_method"))) {
						
					budgAssCard.setDepr_method((String)jsonObj.get("depr_method"));
		    		mapVo.put("depr_method", jsonObj.get("depr_method"));
		    		} else {
						
						err_sb.append("折旧方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acc_depre_amount"))) {
						
					budgAssCard.setAcc_depre_amount(Integer.valueOf((String)jsonObj.get("acc_depre_amount")));
		    		mapVo.put("acc_depre_amount", jsonObj.get("acc_depre_amount"));
		    		} else {
						
						err_sb.append("计提年数为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("price"))) {
						
					budgAssCard.setPrice(Double.valueOf((String)jsonObj.get("price")));
		    		mapVo.put("price", jsonObj.get("price"));
		    		} else {
						
						err_sb.append("资产原值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("depre_money"))) {
						
					budgAssCard.setDepre_money(Double.valueOf((String)jsonObj.get("depre_money")));
		    		mapVo.put("depre_money", jsonObj.get("depre_money"));
		    		} else {
						
						err_sb.append("累计折旧为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cur_money"))) {
						
					budgAssCard.setCur_money(Double.valueOf((String)jsonObj.get("cur_money")));
		    		mapVo.put("cur_money", jsonObj.get("cur_money"));
		    		} else {
						
						err_sb.append("资产净值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fore_money"))) {
						
					budgAssCard.setFore_money(Double.valueOf((String)jsonObj.get("fore_money")));
		    		mapVo.put("fore_money", jsonObj.get("fore_money"));
		    		} else {
						
						err_sb.append("预留残值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("add_depre_month"))) {
						
					budgAssCard.setAdd_depre_month(Integer.valueOf((String)jsonObj.get("add_depre_month")));
		    		mapVo.put("add_depre_month", jsonObj.get("add_depre_month"));
		    		} else {
						
						err_sb.append("累计折旧月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_depr_year"))) {
						
					budgAssCard.setLast_depr_year((String)jsonObj.get("last_depr_year"));
		    		mapVo.put("last_depr_year", jsonObj.get("last_depr_year"));
		    		} else {
						
						err_sb.append("上次折旧年为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_depr_month"))) {
						
					budgAssCard.setLast_depr_month((String)jsonObj.get("last_depr_month"));
		    		mapVo.put("last_depr_month", jsonObj.get("last_depr_month"));
		    		} else {
						
						err_sb.append("上次折旧月为空  ");
						
					}
					
					
				BudgAssCard data_exc_extis = budgAssCardIntangibleService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAssCard.setError_type(err_sb.toString());
					
					list_err.add(budgAssCard);
					
				} else {
			  
					String dataJson = budgAssCardIntangibleService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAssCard data_exc = new BudgAssCard();
			
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

