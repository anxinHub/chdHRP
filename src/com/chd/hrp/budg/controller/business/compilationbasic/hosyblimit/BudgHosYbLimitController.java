/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.hosyblimit;
import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgHosYbLimit;
import com.chd.hrp.budg.entity.BudgZeyfIncomeHis;
import com.chd.hrp.budg.service.business.compilationbasic.hosyblimit.BudgHosYbLimitService;
/**
 * 
 * @Description:
 * 医院医保额度控制
 * @Table:
 * BUDG_HOS_YB_LIMIT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgHosYbLimitController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgHosYbLimitController.class);
	
	//引入Service服务
	@Resource(name = "budgHosYbLimitService")
	private final BudgHosYbLimitService budgHosYbLimitService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/budgHosYBLimitMainPage", method = RequestMethod.GET)
	public String budgHosYbLimitMainPage(Model mode) throws Exception {
		return "hrp/budg/business/compilationbasic/hosyblimit/budgHosYbLimitMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/budgHosYbLimitAddPage", method = RequestMethod.GET)
	public String budgHosYbLimitAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosyblimit/budgHosYbLimitAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/saveBudgHosYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgHosYbLimit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("insurance_code", ids[1]);
			mapVo.put("pay_limit", ids[2]);
			mapVo.put("rate", ids[3]);
			if("-1".equals(ids[4])){
				mapVo.put("control_limit", "");
			}else{
				mapVo.put("control_limit", ids[4]);
			}
			if("-1".equals(ids[5])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[5]);
			}
			mapVo.put("rowNo", ids[6]);
			mapVo.put("flag", ids[7]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgHosYbLimitJson = null ;
		
		try {
			
			budgHosYbLimitJson = budgHosYbLimitService.save(listVo);
			
		} catch (Exception e) {
			
			budgHosYbLimitJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgHosYbLimitJson);
	}

	/**
	 * @Description 
	 * 添加数据 医院医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/addBudgHosYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgHosYbLimit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		      
		String budgHosYbLimitJson = budgHosYbLimitService.add(mapVo);

		return JSONObject.parseObject(budgHosYbLimitJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 医院医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/budgHosYbLimitUpdatePage", method = RequestMethod.GET)
	public String budgHosYbLimitUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgHosYbLimit budgHosYbLimit = new BudgHosYbLimit();
    
		budgHosYbLimit = budgHosYbLimitService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgHosYbLimit.getGroup_id());
		mode.addAttribute("hos_id", budgHosYbLimit.getHos_id());
		mode.addAttribute("copy_code", budgHosYbLimit.getCopy_code());
		mode.addAttribute("year", budgHosYbLimit.getYear());
		mode.addAttribute("insurance_code", budgHosYbLimit.getInsurance_code());
		mode.addAttribute("pay_limit", budgHosYbLimit.getPay_limit());
		mode.addAttribute("rate", budgHosYbLimit.getRate());
		mode.addAttribute("control_limit", budgHosYbLimit.getControl_limit());
		mode.addAttribute("remark", budgHosYbLimit.getRemark());
		
		return "hrp/budg/business/compilationbasic/hosyblimit/budgHosYbLimitUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/updateBudgHosYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgHosYbLimit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {			
			Map<String, Object> mapVo=new HashMap<String, Object>();			
			String[] ids=id.split("@");
			//表的主键			
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])  ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("year", ids[3])   ;
			mapVo.put("insurance_code", ids[4])   ;
			mapVo.put("pay_limit", ids[5]);
			mapVo.put("rate",ids[6]);
			mapVo.put("control_limit",ids[7]);
			if("-1".equals(ids[8])){
				mapVo.put("remark","");
			}else{
				mapVo.put("remark",ids[8]);
			}
			
	        listVo.add(mapVo);
	      
	    }            
	  
		String budgHosYbLimitJson = budgHosYbLimitService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgHosYbLimitJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/addOrUpdateBudgHosYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgHosYbLimit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgHosYbLimitJson ="";
		

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
	  
		budgHosYbLimitJson = budgHosYbLimitService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgHosYbLimitJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/deleteBudgHosYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgHosYbLimit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("insurance_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgHosYbLimitJson = budgHosYbLimitService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgHosYbLimitJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/queryBudgHosYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgHosYbLimit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgHosYbLimit = budgHosYbLimitService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosYbLimit);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院医保额度控制
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/budgHosYbLimitImportPage", method = RequestMethod.GET)
	public String budgHosYbLimitImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosyblimit/budgHosYbLimitImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院医保额度控制
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/hosyblimit/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","全院医保额度控制模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院医保额度控制
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/hosyblimit/readBudgHosYbLimitFiles",method = RequestMethod.POST)  
    public String readBudgHosYbLimitFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgHosYbLimit> list_err = new ArrayList<BudgHosYbLimit>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgHosYbLimit budgHosYbLimit = new BudgHosYbLimit();
				
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
					if (StringTool.isNotBlank(temp[0])) {
						
						budgHosYbLimit.setYear(temp[0]);
			    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						budgHosYbLimit.setInsurance_code(temp[1]);
						
			    		mapVo.put("insurance_code", temp[1]);
		    		
			    		int count = budgHosYbLimitService.queryInsuranceCodeExist(mapVo);
			    		
			    		if(count == 0 ){
			    			
			    			err_sb.append("医保类型编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("医保类型编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						budgHosYbLimit.setPay_limit(Double.valueOf(temp[2]));
						
			    		mapVo.put("pay_limit", temp[2]);
					
					} else {
						
						err_sb.append("本年支付额度为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						budgHosYbLimit.setRate(Double.valueOf(temp[3]));
						
			    		mapVo.put("rate", temp[3]);
					
					} else {
						
						err_sb.append("控线增量比率为空;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp, 2) && ExcelReader.validExceLColunm(temp, 3)) {
						
						
			    		mapVo.put("control_limit", Double.valueOf(temp[2])*(1+Double.valueOf(temp[3])/100));
					
					} 
					 
					if (ExcelReader.validExceLColunm(temp, 4)) {
						
						budgHosYbLimit.setRemark(temp[4]);
						
			    		mapVo.put("remark", temp[4]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					
					int count =  budgHosYbLimitService.queryDataExist(mapVo);
					
					if(count > 0){
						
						err_sb.append("数据已存在;");
						
					}
					
					if(err_sb.length() >  0){
						
						budgHosYbLimit.setError_type(err_sb.toString()); 
						
						list_err.add(budgHosYbLimit);
						
					}else{
						
						addList.add(mapVo);
					}
					
				}
			
				if(list_err.size() == 0){
					String dataJson = budgHosYbLimitService.addBatch(addList);
				}
				
			} catch (DataAccessException e) {
				
				BudgHosYbLimit budgHosYbLimit = new BudgHosYbLimit();
				
				budgHosYbLimit.setError_type("系统导入出错");
				
				list_err.add( budgHosYbLimit);
			}
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院医保额度控制
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/addBatchBudgHosYbLimit", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgHosYbLimit(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgHosYbLimit> list_err = new ArrayList<BudgHosYbLimit>();
		
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
			
			BudgHosYbLimit budgHosYbLimit = new BudgHosYbLimit();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgHosYbLimit.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("insurance_code"))) {
						
					budgHosYbLimit.setInsurance_code((String)jsonObj.get("insurance_code"));
		    		mapVo.put("insurance_code", jsonObj.get("insurance_code"));
		    		} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_limit"))) {
						
					budgHosYbLimit.setPay_limit(Double.valueOf((String)jsonObj.get("pay_limit")));
		    		mapVo.put("pay_limit", jsonObj.get("pay_limit"));
		    		} else {
						
						err_sb.append("本年支付额度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("rate"))) {
						
					budgHosYbLimit.setRate(Double.valueOf((String)jsonObj.get("rate")));
		    		mapVo.put("rate", jsonObj.get("rate"));
		    		} else {
						
						err_sb.append("控线增量比率为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("control_limit"))) {
						
					budgHosYbLimit.setControl_limit(Double.valueOf((String)jsonObj.get("control_limit")));
		    		mapVo.put("control_limit", jsonObj.get("control_limit"));
		    		} else {
						
						err_sb.append("本年控制额度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgHosYbLimit.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					
				BudgHosYbLimit data_exc_extis = budgHosYbLimitService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosYbLimit.setError_type(err_sb.toString());
					
					list_err.add(budgHosYbLimit);
					
				} else {
			  
					String dataJson = budgHosYbLimitService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgHosYbLimit data_exc = new BudgHosYbLimit();
			
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
	 * 增量生成  根据医保付费机制中付费机制为总额预付的医保类型生成。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	      
		List<Map<String, Object>> list= new ArrayList<Map<String,Object>>();
			
	       mapVo.put("group_id", SessionManager.getGroupId())   ;
	       mapVo.put("hos_id", SessionManager.getHosId())   ;
	       mapVo.put("copy_code", SessionManager.getCopyCode())   ;
	       
	       mapVo.put("pay_mode_code", "ZE")  ; // 付费机制 : ZE:总额付费  DB:单病种付费  RT:按人头付费
	       // 查询 医保付费机制中付费机制为总额预付的医保类型
	       List<Map<String, Object>> listVo = budgHosYbLimitService.queryBudgInsuranceCodeData(mapVo);
	       
	       for(Map<String,Object> item : listVo){
	    	   
	    	   item.put("year",mapVo.get("year"));
	    	   
	    	   int count = budgHosYbLimitService.queryDataExist(item) ;
	    	   
	    	   if(count == 0 ){
	    		   
	    		   item.put("pay_limit","");
	    		   item.put("rate","");
	    		   item.put("control_limit","");
	    		   item.put("remark","");
	    		   
			       list.add(item); 
	    	   }
	       }
	       String budgWorkHosExecuteJson = null ;
	       
			if(list.size() > 0 ){
				
				budgWorkHosExecuteJson = budgHosYbLimitService.addBatch(list);
				
			}else{
				
				budgWorkHosExecuteJson ="{\"error\":\"数据已全部生成，无需再次生成\"}";
			}
		   
          return JSONObject.parseObject(budgWorkHosExecuteJson);	
	} 
	
	
	/**
	 * 医保类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyblimit/queryBudgYBType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String ybType = budgHosYbLimitService.queryBudgYBType(mapVo);
		return ybType;

	}
}

