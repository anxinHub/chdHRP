/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgcharge;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgChargeStandGetWay;
import com.chd.hrp.budg.service.base.budgcharge.BudgChargeStandGetWayService;
/**
 * 
 * @Description:
 * 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
 * @Table:
 * BUDG_CHARGE_STAND_GET_WAY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgChargeStandGetWayController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgChargeStandGetWayController.class);
	
	//引入Service服务
	@Resource(name = "budgChargeStandGetWayService")
	private final BudgChargeStandGetWayService budgChargeStandGetWayService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayMainPage", method = RequestMethod.GET)
	public String budgChargeStandGetWayMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayAddPage", method = RequestMethod.GET)
	public String budgChargeStandGetWayAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayAdd";

	}

	/**
	 * @Description 
	 * 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/addBudgChargeStandGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgChargeStandGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgChargeStandGetWayJson = null;
		
		try {
			
			budgChargeStandGetWayJson = budgChargeStandGetWayService.add(mapVo);

		} catch (Exception e) {

			budgChargeStandGetWayJson = e.getMessage() ;
			
		}
		
		
		return JSONObject.parseObject(budgChargeStandGetWayJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayUpdatePage", method = RequestMethod.GET)
	public String budgChargeStandGetWayUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		Map<String,Object> budgChargeStandGetWay  = budgChargeStandGetWayService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgChargeStandGetWay.get("group_id"));
		mode.addAttribute("hos_id", budgChargeStandGetWay.get("hos_id"));
		mode.addAttribute("copy_code", budgChargeStandGetWay.get("copy_code"));
		mode.addAttribute("charge_stand_code", budgChargeStandGetWay.get("charge_stand_code"));
		mode.addAttribute("charge_stand_name", budgChargeStandGetWay.get("charge_stand_name"));
		mode.addAttribute("charge_stan_nature", budgChargeStandGetWay.get("charge_stan_nature"));
		mode.addAttribute("charge_stan_nature_name", budgChargeStandGetWay.get("charge_stan_nature_name"));
		mode.addAttribute("charge_stan_describe", budgChargeStandGetWay.get("charge_stan_describe"));
		mode.addAttribute("get_value_way", budgChargeStandGetWay.get("get_value_way"));
		mode.addAttribute("get_value_way_name", budgChargeStandGetWay.get("get_value_way_name"));
		mode.addAttribute("formula_id", budgChargeStandGetWay.get("formula_id"));
		mode.addAttribute("formula_name", budgChargeStandGetWay.get("formula_name"));
		mode.addAttribute("formula_ca", budgChargeStandGetWay.get("formula_ca"));
		mode.addAttribute("fun_id", budgChargeStandGetWay.get("fun_id"));
		mode.addAttribute("fun_name", budgChargeStandGetWay.get("fun_name"));
		mode.addAttribute("fun_method_chs", budgChargeStandGetWay.get("fun_method_chs"));
		
		return "hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/updateBudgChargeStandGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgChargeStandGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgChargeStandGetWayJson = null;
		
		try {
			
			budgChargeStandGetWayJson = budgChargeStandGetWayService.update(mapVo);
			
		} catch (Exception e) {
			
			budgChargeStandGetWayJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(budgChargeStandGetWayJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/addOrUpdateBudgChargeStandGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgChargeStandGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgChargeStandGetWayJson ="";
		

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
	  
		budgChargeStandGetWayJson = budgChargeStandGetWayService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgChargeStandGetWayJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/deleteBudgChargeStandGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgChargeStandGetWay(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("charge_stand_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgChargeStandGetWayJson = null ;
		try {
			
			budgChargeStandGetWayJson = budgChargeStandGetWayService.deleteBatch(listVo); 
			
		} catch (Exception e) {
			
			budgChargeStandGetWayJson = e.getMessage() ;

		}
				
			
	  return JSONObject.parseObject(budgChargeStandGetWayJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/queryBudgChargeStandGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeStandGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgChargeStandGetWay = budgChargeStandGetWayService.query(getPage(mapVo));

		return JSONObject.parseObject(budgChargeStandGetWay);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayImportPage", method = RequestMethod.GET)
	public String budgChargeStandGetWayImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcharge/stanget/budgChargeStandGetWayImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgcharge/stanget/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","费用标注取值方法维护.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgcharge/stanget/readBudgChargeStandGetWayFiles",method = RequestMethod.POST)  
    public String readBudgChargeStandGetWayFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgChargeStandGetWay> list_err = new ArrayList<BudgChargeStandGetWay>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgChargeStandGetWay budgChargeStandGetWay = new BudgChargeStandGetWay();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgChargeStandGetWay.setCharge_stand_code(temp[3]);
		    		mapVo.put("charge_stand_code", temp[3]);
					
					} else {
						
						err_sb.append("费用标准编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgChargeStandGetWay.setGet_value_way(temp[4]);
		    		mapVo.put("get_value_way", temp[4]);
					
					} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgChargeStandGetWay.setFormula_id(temp[5]);
		    		mapVo.put("formula_id", temp[5]);
					
					} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgChargeStandGetWay.setFun_id(temp[6]);
		    		mapVo.put("fun_id", temp[6]);
					
					} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					 
					
				Map<String,Object> data_exc_extis = budgChargeStandGetWayService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgChargeStandGetWay.setError_type(err_sb.toString());
					
					list_err.add(budgChargeStandGetWay);
					
				} else {
			  
					String dataJson = budgChargeStandGetWayService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgChargeStandGetWay data_exc = new BudgChargeStandGetWay();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 取值方法（GET_VALUE_WAY） 01手工录入  02取值函数 03计算公式
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/stanget/addBatchBudgChargeStandGetWay", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgChargeStandGetWay(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgChargeStandGetWay> list_err = new ArrayList<BudgChargeStandGetWay>();
		
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
			
			BudgChargeStandGetWay budgChargeStandGetWay = new BudgChargeStandGetWay();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("charge_stand_code"))) {
						
					budgChargeStandGetWay.setCharge_stand_code((String)jsonObj.get("charge_stand_code"));
		    		mapVo.put("charge_stand_code", jsonObj.get("charge_stand_code"));
		    		} else {
						
						err_sb.append("费用标准编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("get_value_way"))) {
						
					budgChargeStandGetWay.setGet_value_way((String)jsonObj.get("get_value_way"));
		    		mapVo.put("get_value_way", jsonObj.get("get_value_way"));
		    		} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("formula_id"))) {
						
					budgChargeStandGetWay.setFormula_id((String)jsonObj.get("formula_id"));
		    		mapVo.put("formula_id", jsonObj.get("formula_id"));
		    		} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_id"))) {
						
					budgChargeStandGetWay.setFun_id((String)jsonObj.get("fun_id"));
		    		mapVo.put("fun_id", jsonObj.get("fun_id"));
		    		} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					
					
				BudgChargeStandGetWay data_exc_extis = budgChargeStandGetWayService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgChargeStandGetWay.setError_type(err_sb.toString());
					
					list_err.add(budgChargeStandGetWay);
					
				} else {
			  
					String dataJson = budgChargeStandGetWayService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgChargeStandGetWay data_exc = new BudgChargeStandGetWay();
			
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

