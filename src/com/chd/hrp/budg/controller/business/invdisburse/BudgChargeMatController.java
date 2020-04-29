/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.invdisburse;
import java.io.IOException;
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

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgChargeMat;
import com.chd.hrp.budg.service.business.invdisburse.BudgChargeMatService;
import com.chd.hrp.budg.service.common.BudgCommonInfoService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_CHARGE_MAT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgChargeMatController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgChargeMatController.class);
	
	//引入Service服务
	@Resource(name = "budgChargeMatService")
	private final BudgChargeMatService budgChargeMatService = null;
	
	//引入Service服务(导入用 )
	@Resource(name = "budgCommonInfoService")
	private final BudgCommonInfoService budgCommonInfoService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/budgChargeMatMainPage", method = RequestMethod.GET)
	public String budgChargeMatMainPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgchargemat/budgChargeMatMain";

	}
	/**
	 * @Description 
	 * 批量调整页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/budgChargeMatUpdateAdjRatePage", method = RequestMethod.GET)
	public String budgChargeMatUpdateAdjRatePage(Model mode) throws Exception {
		
		return "hrp/budg/business/invdisburse/budgchargemat/budgChargeMatUpdateAdjRate";
		
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/budgChargeMatAddPage", method = RequestMethod.GET)
	public String budgChargeMatAddPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgchargemat/budgChargeMatAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/addBudgChargeMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgChargeMat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
	
		String budgChargeMatJson = budgChargeMatService.add(mapVo);

		return JSONObject.parseObject(budgChargeMatJson);
		
	}
	/**
	 * @Description 
	 * 生成  根据年度月份物资分类生成本年度支出预算数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/generateBudgChargeMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateBudgChargeMat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String budgChargeMatJson = budgChargeMatService.generateBudgChargeMat(mapVo);
		
		return JSONObject.parseObject(budgChargeMatJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/budgChargeMatUpdatePage", method = RequestMethod.GET)
	public String budgChargeMatUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> budgChargeMat = budgChargeMatService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgChargeMat.get("group_id"));
		mode.addAttribute("hos_id", budgChargeMat.get("hos_id"));
		mode.addAttribute("copy_code", budgChargeMat.get("copy_code"));
		mode.addAttribute("year", budgChargeMat.get("year"));
		mode.addAttribute("month", budgChargeMat.get("month"));
		mode.addAttribute("dept_id", budgChargeMat.get("dept_id"));
		mode.addAttribute("dept_name", budgChargeMat.get("dept_name"));
		mode.addAttribute("mat_type_id", budgChargeMat.get("mat_type_id"));
		mode.addAttribute("mat_type_name", budgChargeMat.get("mat_type_name"));
		mode.addAttribute("last_cost", budgChargeMat.get("last_cost"));
		mode.addAttribute("grow_rate", budgChargeMat.get("grow_rate"));
		mode.addAttribute("count_value", budgChargeMat.get("count_value"));
		mode.addAttribute("adj_rate", budgChargeMat.get("adj_rate"));
		mode.addAttribute("cost_budg", budgChargeMat.get("cost_budg"));
		mode.addAttribute("remark", budgChargeMat.get("remark"));
		
		return "hrp/budg/business/invdisburse/budgchargemat/budgChargeMatUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/updateBudgChargeMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgChargeMat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		String budgChargeMatJson = budgChargeMatService.update(mapVo);
		
		return JSONObject.parseObject(budgChargeMatJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/addOrUpdateBudgChargeMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgChargeMat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgChargeMatJson ="";
		

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
	  
		budgChargeMatJson = budgChargeMatService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgChargeMatJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/deleteBudgChargeMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgChargeMat(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("month", ids[4])   ;
				mapVo.put("dept_id", ids[5])   ;
				mapVo.put("mat_type_id", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgChargeMatJson = budgChargeMatService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgChargeMatJson);
			
	}
	
	/**
	 * @Description 
	 * 批量更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/budgChargeMatUpdateAdjRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> budgChargeMatUpdateAdjRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
	    	
			mapVo.put("year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("mat_type_id", ids[3]);
			
			double last_cost = Double.parseDouble(ids[4]);//上年同期支出
			int old_grow_rate = Integer.parseInt(ids[5]);//增长比例原值
			int old_adj_rate = Integer.parseInt(ids[6]);//调整比例原值
			double count_value = Double.parseDouble(ids[7]);//计算值
			
			int grow_rate = old_grow_rate;
					
			if(ids[8] != null && ids[8].length() > 0){
				grow_rate = Integer.parseInt(ids[8]);//增长比例页面传值
				count_value = last_cost * (100+grow_rate)/100;
			}
			
			mapVo.put("count_value", count_value);//计算值
			mapVo.put("grow_rate", grow_rate);//增长比例
			
			int adj_rate = old_adj_rate;
			
			if(ids[9] != null && ids[9].length() > 0){
				adj_rate = Integer.parseInt(ids[9]);//增长比例页面传值
			}
			
			mapVo.put("adj_rate", adj_rate);//调整比例
			
			
			//支出预算 = 计算值*(100+调整比例)/100
			mapVo.put("cost_budg", count_value * (100+adj_rate)/100 );
			if("-1".equals(ids[10])){
				mapVo.put("remark", "");
			}else{
				
				mapVo.put("remark", ids[10]);
			}
			
			listVo.add(mapVo);
      
		}
		String budgWageJson = budgChargeMatService.budgChargeMatUpdateAdjRate(listVo);
		
		return JSONObject.parseObject(budgWageJson);
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/queryBudgChargeMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeMat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgChargeMat = budgChargeMatService.query(getPage(mapVo));

		return JSONObject.parseObject(budgChargeMat);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/budgChargeMatImportPage", method = RequestMethod.GET)
	public String budgChargeMatImportPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgchargemat/budgChargeMatImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 tabledesc
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/invdisburse/budgchargemat/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\cost\\mat","科室收费材料预算编制.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/invdisburse/budgchargemat/readBudgChargeMatFiles",method = RequestMethod.POST)  
    public String readBudgChargeMatFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"}; 
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		 
		paraMap.put("hos_id", SessionManager.getHosId());   
		 
		paraMap.put("copy_code", SessionManager.getCopyCode());   
		
		//查询 科室基本信息(根据编码 匹配ID用)预算科室
		List<Map<String,Object>> deptList =  budgCommonInfoService.queryDeptData(paraMap) ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
	    		mapVo.put("group_id", SessionManager.getGroupId());   
				 
	    		mapVo.put("hos_id", SessionManager.getHosId());   
				 
	    		mapVo.put("copy_code", SessionManager.getCopyCode());   
	    		
	    		for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					
					if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])&& temp[4].equals(error[4])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
				 
				if (ExcelReader.validExceLColunm(temp, 0)) {
					
					map.put("year",temp[0]);
					
		    		mapVo.put("year", temp[0]);
				
				} else {
					
					err_sb.append("年度为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,1)) {
					
					map.put("month",temp[1]);
					
					if(!Arrays.asList(monthData).contains(temp[1])){
						
						err_sb.append("月份输入不合法（两位数字01-12）;");
						
					}else{
						
			    		mapVo.put("month", temp[1]);
						
					}
				
				} else {
					
					err_sb.append("月份为空 ;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,2)) {
					
					map.put("dept_code",temp[2]);
					map.put("dept_name", temp[3]);
					
					int flag = 0 ;
					
					for(Map<String,Object> item : deptList){
						
						if(temp[2].equals(item.get("dept_code"))){
							
							map.put("dept_id", String.valueOf(item.get("dept_id")));
				    		mapVo.put("dept_id", String.valueOf(item.get("dept_id")));
				    		flag = 1;
				    		
				    		break ;
						}
						
					}
					
					if(flag == 0){
						
						err_sb.append("科室编码输入错误,不存在该科室;");
					}
				
				} else {
					
					err_sb.append("科室编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,4)) {
					
					map.put("mat_type_code",temp[4]);
					map.put("mat_type_name", temp[5]);
					
					mapVo.put("mat_type_code",temp[4]);
					
					//查询 预算物质材料基本信息(根据编码 匹配ID用)
					Map<String,Object> typeMap = budgCommonInfoService.queryBudgMatType(mapVo) ;
					
						
					if(typeMap != null ){
						
						map.put("mat_type_id", String.valueOf(typeMap.get("mat_type_id")));
			    		mapVo.put("mat_type_id", String.valueOf(typeMap.get("mat_type_id")));
			    		
					}else{
						err_sb.append("物资类别编码输入错误,不存在该物资类别或输入年度该物资类别与预算科目对应关系不存在;");
					}
					
				} else {
					
					err_sb.append("物资类别编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,6)) {
					
					map.put("cost_budg",Double.valueOf(temp[6]));
		    		mapVo.put("cost_budg", temp[6]);
				
				} else {
					
					err_sb.append("金额为空;");
					
				}
				
				if (ExcelReader.validExceLColunm(temp,7)) {
					
					map.put("remark",temp[7]);
		    		mapVo.put("remark", temp[7]);
				
				} else {
					
					map.put("remark","");
		    		mapVo.put("remark", "");
					
				}
					 
				//校验数据 是否存在
				int count = budgChargeMatService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					map.put("error_type",err_sb.toString());
					
					list_err.add(map);
					
				} else {
					mapVo.put("last_cost", "") ;
					mapVo.put("grow_rate", "") ;
					mapVo.put("count_value", "") ;
					mapVo.put("adj_rate", "") ;
					addList.add(mapVo) ;
				}
				
			}
			
			if(list_err.size() == 0){
				
				if(addList.size() > 0 ){
					
					String dataJson = budgChargeMatService.addBatch(addList);
					
				}else{
					
					Map<String, Object> data_exc = new HashMap<String, Object>();
					
					data_exc.put("error_type","没有数据,导入失败!");
					
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
	 * 批量添加数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgchargemat/addBatchBudgChargeMat", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgChargeMat(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgChargeMat> list_err = new ArrayList<BudgChargeMat>();
		
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
			
			BudgChargeMat budgChargeMat = new BudgChargeMat();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgChargeMat.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgChargeMat.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgChargeMat.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("mat_type_id"))) {
						
					budgChargeMat.setMat_type_id(Long.valueOf((String)jsonObj.get("mat_type_id")));
		    		mapVo.put("mat_type_id", jsonObj.get("mat_type_id"));
		    		} else {
						
						err_sb.append("物资类别ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_cost"))) {
						
					budgChargeMat.setLast_cost(Double.valueOf((String)jsonObj.get("last_cost")));
		    		mapVo.put("last_cost", jsonObj.get("last_cost"));
		    		} else {
						
						err_sb.append("收入预算为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgChargeMat.setGrow_rate(Integer.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("收支配比为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgChargeMat.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("adj_rate"))) {
						
					budgChargeMat.setAdj_rate(Double.valueOf((String)jsonObj.get("adj_rate")));
		    		mapVo.put("adj_rate", jsonObj.get("adj_rate"));
		    		} else {
						
						err_sb.append("调整比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cost_budg"))) {
						
					budgChargeMat.setCost_budg(Double.valueOf((String)jsonObj.get("cost_budg")));
		    		mapVo.put("cost_budg", jsonObj.get("cost_budg"));
		    		} else {
						
						err_sb.append("支出预算为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgChargeMat.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgChargeMat data_exc_extis = budgChargeMatService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgChargeMat.setError_type(err_sb.toString());
					
					list_err.add(budgChargeMat);
					
				} else {
			  
					String dataJson = budgChargeMatService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgChargeMat data_exc = new BudgChargeMat();
			
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

