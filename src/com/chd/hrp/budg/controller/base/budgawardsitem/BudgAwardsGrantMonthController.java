/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgawardsitem;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgAwardsGrantMonth;
import com.chd.hrp.budg.entity.BudgAwardsItemDict;
import com.chd.hrp.budg.service.base.budgawardsitem.BudgAwardsGrantMonthService;
import com.chd.hrp.budg.service.base.budgawardsitem.BudgAwardsItemDictService;
/**
 * 
 * @Description:
 * 开始年度和结束年度，只有两个取值为本年、上年。
 * @Table:
 * BUDG_AWARDS_GRANT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgAwardsGrantMonthController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAwardsGrantMonthController.class);
	
	//引入Service服务
	@Resource(name = "budgAwardsGrantMonthService")
	private final BudgAwardsGrantMonthService budgAwardsGrantMonthService = null;
	
	@Resource(name = "budgAwardsItemDictService")
	private final BudgAwardsItemDictService budgAwardsItemDictService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/budgAwardsGrantMonthMainPage", method = RequestMethod.GET)
	public String budgAwardsGrantMonthMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsgrantmonth/budgAwardsGrantMonthMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/budgAwardsGrantMonthAddPage", method = RequestMethod.GET)
	public String budgAwardsGrantMonthAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsgrantmonth/budgAwardsGrantMonthAdd";

	}

	/**
	 * @Description 
	 * 添加数据 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/addBudgAwardsGrantMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAwardsGrantMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgAwardsGrantMonthJson = budgAwardsGrantMonthService.add(mapVo);

		return JSONObject.parseObject(budgAwardsGrantMonthJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/budgAwardsGrantMonthUpdatePage", method = RequestMethod.GET)
	public String budgAwardsGrantMonthUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgAwardsGrantMonth budgAwardsGrantMonth = new BudgAwardsGrantMonth();
    
		budgAwardsGrantMonth = budgAwardsGrantMonthService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgAwardsGrantMonth.getGroup_id());
		mode.addAttribute("hos_id", budgAwardsGrantMonth.getHos_id());
		mode.addAttribute("copy_code", budgAwardsGrantMonth.getCopy_code());
		mode.addAttribute("awards_item_code", budgAwardsGrantMonth.getAwards_item_code());
		mode.addAttribute("id", budgAwardsGrantMonth.getId());
		mode.addAttribute("grant_month", budgAwardsGrantMonth.getGrant_month());
		mode.addAttribute("start_year", budgAwardsGrantMonth.getStart_year());
		mode.addAttribute("start_month", budgAwardsGrantMonth.getStart_month());
		mode.addAttribute("end_year", budgAwardsGrantMonth.getEnd_year());
		mode.addAttribute("end_month", budgAwardsGrantMonth.getEnd_month());
		
		return "hrp/budg/base/budgawardsitem/awardsgrantmonth/budgAwardsGrantMonthUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/updateBudgAwardsGrantMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAwardsGrantMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgAwardsGrantMonthJson = budgAwardsGrantMonthService.update(mapVo);
		
		return JSONObject.parseObject(budgAwardsGrantMonthJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/addOrUpdateBudgAwardsGrantMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAwardsGrantMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAwardsGrantMonthJson ="";
		

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
	  
		budgAwardsGrantMonthJson = budgAwardsGrantMonthService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAwardsGrantMonthJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/deleteBudgAwardsGrantMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAwardsGrantMonth(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("awards_item_code", ids[3])   ;
				mapVo.put("id", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgAwardsGrantMonthJson = budgAwardsGrantMonthService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAwardsGrantMonthJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/queryBudgAwardsGrantMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAwardsGrantMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgAwardsGrantMonth = budgAwardsGrantMonthService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAwardsGrantMonth);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/budgAwardsGrantMonthImportPage", method = RequestMethod.GET)
	public String budgAwardsGrantMonthImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsgrantmonth/budgAwardsGrantMonthImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgawardsitem/awardsgrantmonth/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","奖金发放月维护模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgawardsitem/awardsgrantmonth/readBudgAwardsGrantMonthFiles",method = RequestMethod.POST)  
    public String readBudgAwardsGrantMonthFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgAwardsGrantMonth> list_err = new ArrayList<BudgAwardsGrantMonth>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		String[] monthList = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		
		String[] yearList = {"0","1"} ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgAwardsGrantMonth budgAwardsGrantMonth = new BudgAwardsGrantMonth();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
   						String error[] = list.get(j);
   						if(temp[0].equals(error[0]) && temp[1].equals(error[1]) ){
   							err_sb.append("第"+i+"行数据与第 "+j+"行奖金项目编码、序号重复;");
   						}
   					}
		    		
					 
					if (StringTool.isNotBlank(temp[0])) {
						
						budgAwardsGrantMonth.setAwards_item_code(temp[0]);
			    		mapVo.put("awards_item_code", temp[0]);
			    		
			    		mapVo.put("is_stop", "0");
			    		BudgAwardsItemDict code  = budgAwardsItemDictService.queryByCode(mapVo);
			    		
			    		if(code == null ){
			    			err_sb.append("奖金项目编码不存在或已停用;");
			    		}
		    		
					
					} else {
						
						err_sb.append("奖金项目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					budgAwardsGrantMonth.setId(Long.valueOf(temp[1]));
		    		mapVo.put("id", temp[1]);
					
					} else {
						
						err_sb.append("序号为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						if(!Arrays.asList(monthList).contains(temp[2])){
							err_sb.append("发放月份输入格式不正确(必须两位数字);");
						}
						
						budgAwardsGrantMonth.setGrant_month(temp[2]);
			    		mapVo.put("grant_month", temp[2]);
					
					} else {
						
						err_sb.append("发放月份为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						if(!Arrays.asList(yearList).contains(temp[3])){
							
							err_sb.append("开始年度输入格式不正确(0：上一年，1：本年);");
							
						}
						
						budgAwardsGrantMonth.setStart_year(temp[3]);
			    		mapVo.put("start_year", temp[3]);
					
					} else {
						
						err_sb.append("为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
						if(!Arrays.asList(monthList).contains(temp[4])){
							err_sb.append("开始月份输入格式不正确(必须两位数字);");
						}
						
						budgAwardsGrantMonth.setStart_month(temp[4]);
			    		mapVo.put("start_month", temp[4]);
						
					} else {
						
						err_sb.append("开始月份为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
						if(!Arrays.asList(yearList).contains(temp[5])){
							err_sb.append("结束年度输入格式不正确(0：上一年，1：本年);");
						}
						budgAwardsGrantMonth.setEnd_year(temp[5]);
			    		mapVo.put("end_year", temp[5]);
					
					} else {
						
						err_sb.append("结束年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
						if(!Arrays.asList(monthList).contains(temp[6])){
							err_sb.append("结束月份输入格式不正确(必须两位数字);");
						} 
						
						budgAwardsGrantMonth.setEnd_month(temp[6]);
			    		mapVo.put("end_month", temp[6]);
					
					} else {
						
						err_sb.append("结束月份为空;");
						
					}
					 
					
				BudgAwardsGrantMonth data_exc_extis = budgAwardsGrantMonthService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("该奖金项目编码序号已被占用");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAwardsGrantMonth.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsGrantMonth);
					
				} else {
					
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				String dataJson = budgAwardsGrantMonthService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgAwardsGrantMonth data_exc = new BudgAwardsGrantMonth();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 开始年度和结束年度，只有两个取值为本年、上年。
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsgrantmonth/addBatchBudgAwardsGrantMonth", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgAwardsGrantMonth(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgAwardsGrantMonth> list_err = new ArrayList<BudgAwardsGrantMonth>();
		
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
			
			BudgAwardsGrantMonth budgAwardsGrantMonth = new BudgAwardsGrantMonth();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("awards_item_code"))) {
						
					budgAwardsGrantMonth.setAwards_item_code((String)jsonObj.get("awards_item_code"));
		    		mapVo.put("awards_item_code", jsonObj.get("awards_item_code"));
		    		} else {
						
						err_sb.append("奖金项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("id"))) {
						
					budgAwardsGrantMonth.setId(Long.valueOf((String)jsonObj.get("id")));
		    		mapVo.put("id", jsonObj.get("id"));
		    		} else {
						
						err_sb.append("序号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grant_month"))) {
						
					budgAwardsGrantMonth.setGrant_month((String)jsonObj.get("grant_month"));
		    		mapVo.put("grant_month", jsonObj.get("grant_month"));
		    		} else {
						
						err_sb.append("发放月为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("start_year"))) {
						
					budgAwardsGrantMonth.setStart_year((String)jsonObj.get("start_year"));
		    		mapVo.put("start_year", jsonObj.get("start_year"));
		    		} else {
						
						err_sb.append("开始年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("start_month"))) {
						
					budgAwardsGrantMonth.setStart_month((String)jsonObj.get("start_month"));
		    		mapVo.put("start_month", jsonObj.get("start_month"));
		    		} else {
						
						err_sb.append("开始月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("end_year"))) {
						
					budgAwardsGrantMonth.setEnd_year((String)jsonObj.get("end_year"));
		    		mapVo.put("end_year", jsonObj.get("end_year"));
		    		} else {
						
						err_sb.append("结束年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("end_month"))) {
						
					budgAwardsGrantMonth.setEnd_month((String)jsonObj.get("end_month"));
		    		mapVo.put("end_month", jsonObj.get("end_month"));
		    		} else {
						
						err_sb.append("结束月份为空  ");
						
					}
					
					
				BudgAwardsGrantMonth data_exc_extis = budgAwardsGrantMonthService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAwardsGrantMonth.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsGrantMonth);
					
				} else {
			  
					String dataJson = budgAwardsGrantMonthService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAwardsGrantMonth data_exc = new BudgAwardsGrantMonth();
			
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

