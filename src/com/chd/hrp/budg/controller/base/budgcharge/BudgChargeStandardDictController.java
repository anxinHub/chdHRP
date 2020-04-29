/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgcharge;
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
import com.chd.hrp.budg.entity.BudgChargeStandardDict;
import com.chd.hrp.budg.service.base.budgcharge.BudgChargeStanDeptSetService;
import com.chd.hrp.budg.service.base.budgcharge.BudgChargeStandardDictService;
/**
 * 
 * @Description:
 * 是否停用（IS_STOP):0否，1是
费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室
 * @Table:
 * BUDG_CHARGE_STANDARD_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgChargeStandardDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgChargeStandardDictController.class);
	
	//引入Service服务
	@Resource(name = "budgChargeStandardDictService")
	private final BudgChargeStandardDictService budgChargeStandardDictService = null;
	
	//引入Service服务
	@Resource(name = "budgChargeStanDeptSetService")
	private final BudgChargeStanDeptSetService budgChargeStanDeptSetService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/budgChargeStanDictMainPage", method = RequestMethod.GET)
	public String budgChargeStanDictMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcharge/standict/budgChargeStanDictMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/budgChargeStanDictAddPage", method = RequestMethod.GET)
	public String budgChargeStanDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcharge/standict/budgChargeStanDictAdd";

	}
	
	/**
	 * @Description 
	 * 对应科室维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/budgChargeStanDeptSetPage", method = RequestMethod.GET)
	public String budgChargeStanDeptSetPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcharge/standict/budgChargeStanDeptSet";

	}
	
	/**
	 * @Description 
	 * 添加数据  是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/addBudgChargeStanDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgChargeStanDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(String.valueOf(mapVo.get("charge_stan_name"))));
		mapVo.put("wbx_code", StringTool.toWuBi(String.valueOf(mapVo.get("charge_stan_name"))));
       
		String budgChargeStanDictJson = budgChargeStandardDictService.add(mapVo);

		return JSONObject.parseObject(budgChargeStanDictJson);
		
	}
	
	/**
	 * 添加  费用标准对应科室数据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/addChargeStanDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addChargeStanDeptSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("dept_id", ids[0]);
			mapVo.put("charge_stan_code", ids[1]);
				
	      listVo.add(mapVo);
	      
	    }
		//构建删除之前费用标准对应科室数据 Map
		Map<String,Object> delMap= new HashMap<String,Object>();
		delMap.put("group_id", listVo.get(0).get("group_id"));
		delMap.put("hos_id", listVo.get(0).get("hos_id"));
		delMap.put("copy_code", listVo.get(0).get("copy_code"));
		delMap.put("charge_stan_code", listVo.get(0).get("charge_stan_code"));
		//删除之前费用标准对应科室数据
		budgChargeStanDeptSetService.delete(delMap);
		
		//重新添加 费用标准对应科室数据
		String chargeStanDeptSetJson = budgChargeStanDeptSetService.addBatch(listVo);

		return JSONObject.parseObject(chargeStanDeptSetJson);
		
	}
	
	
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/deleteChargeStanDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteChargeStanDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgChargeStanDictJson = budgChargeStanDeptSetService.delete(mapVo);

		return JSONObject.parseObject(budgChargeStanDictJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面  是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/budgChargeStanDictUpdatePage", method = RequestMethod.GET)
	public String budgChargeStanDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		Map<String,Object> budgChargeStanDict = new HashMap<String,Object>();
    
		budgChargeStanDict = budgChargeStandardDictService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgChargeStanDict.get("group_id"));
		mode.addAttribute("hos_id", budgChargeStanDict.get("hos_id"));
		mode.addAttribute("copy_code", budgChargeStanDict.get("copy_code"));
		mode.addAttribute("charge_stan_code", budgChargeStanDict.get("charge_stan_code"));
		mode.addAttribute("charge_stan_name", budgChargeStanDict.get("charge_stan_name"));
		mode.addAttribute("charge_stan_nature", budgChargeStanDict.get("charge_stan_nature"));
		mode.addAttribute("charge_stan_nature_name", budgChargeStanDict.get("charge_stan_nature_name"));
		mode.addAttribute("charge_stan_describe", budgChargeStanDict.get("charge_stan_describe"));
		mode.addAttribute("unit", budgChargeStanDict.get("unit"));
		mode.addAttribute("data_precision", budgChargeStanDict.get("data_precision"));
		mode.addAttribute("is_stop", budgChargeStanDict.get("is_stop"));
		mode.addAttribute("spell_code", budgChargeStanDict.get("spell_code"));
		mode.addAttribute("wbx_code", budgChargeStanDict.get("wbx_code"));
		
		return "hrp/budg/base/budgcharge/standict/budgChargeStanDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据  是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/updateBudgChargeStanDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgChargeStanDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		String budgChargeStanDictJson = budgChargeStandardDictService.update(mapVo);
		
		return JSONObject.parseObject(budgChargeStanDictJson);
	}
	
	/**
	 * ***************暂时不用此方法*******************
	 * @Description 
	 * 更新数据  是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/addOrUpdateBudgChargeStanDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgChargeStanDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgChargeStanDictJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		
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
	  
		budgChargeStanDictJson = budgChargeStandardDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgChargeStanDictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据  是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/deleteBudgChargeStanDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgChargeStanDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("charge_stan_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgChargeStanDictJson = budgChargeStandardDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgChargeStanDictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据  是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/queryBudgChargeStanDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeStanDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgChargeStanDict = budgChargeStandardDictService.query(getPage(mapVo));

		return JSONObject.parseObject(budgChargeStanDict);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/budgChargeStanDictImportPage", method = RequestMethod.GET)
	public String budgChargeStanDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcharge/standict/budgChargeStanDictImport";

	}
	/**
	 * @Description 
	 * 下载  费用标准导入模版     是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>   
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgcharge/standict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","费用标准字典模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入  费用标准    是否停用（IS_STOP):0否，1是 ; 费用标准性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室<BR>  
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgcharge/standict/readBudgChargeStanDictFiles",method = RequestMethod.POST)  
    public String readBudgChargeStanDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgChargeStandardDict> list_err = new ArrayList<BudgChargeStandardDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgChargeStandardDict budgChargeStanDict = new BudgChargeStandardDict();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【费用标准编码】重复;");
						}
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【费用标准名称】重复;");
						}	
					}
					if (StringTool.isNotBlank(temp[0])) {
						
						budgChargeStanDict.setCharge_stan_code(temp[0]);
			    		mapVo.put("charge_stan_code", temp[0]);
		    		
			    		Map<String, Object> map = budgChargeStandardDictService.queryByCode(mapVo);
			    		
			    		if(map != null){
			    			err_sb.append("费用标准编码已被占用;");
			    		}
					
					} else {
						
						err_sb.append("费用标准编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgChargeStanDict.setCharge_stan_name(temp[1]);
			    		mapVo.put("charge_stan_name", temp[1]);
		    		
			    		int count = budgChargeStandardDictService.queryNameExist(mapVo);
			    		
			    		if(count > 0 ){
			    			err_sb.append("费用标准名称已被占用;");
			    		}
					
					} else {
						
						err_sb.append("费用标准名称为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgChargeStanDict.setCharge_stan_nature(temp[2]);
						
			    		mapVo.put("charge_stan_nature", temp[2]);
			    		
			    		int count = budgChargeStandardDictService.queryStanNatureExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("费用标准性质编码不存在(01:医院 02:科室);");
			    		}
					
					} else {
						
						err_sb.append("费用标准性质为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						int isInteger=1;
						try{
							budgChargeStanDict.setData_precision(Integer.valueOf(temp[3]));
							mapVo.put("data_precision", temp[3]);
							isInteger=Integer.parseInt(temp[3])-1;
							if(isInteger<0){
								err_sb.append("数据精度【"+temp[3]+"】不是正整数！");
							}
						}catch(Exception e){
							err_sb.append("数据精度【"+temp[3]+"】不是正整数！");
						}
					
					} else {					
						err_sb.append("数据精度为空;");		
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgChargeStanDict.setIs_stop(Integer.valueOf(temp[4]));
		    		mapVo.put("is_stop", temp[4]);
					
					} else {
						
						err_sb.append("是否停用为空 ;");
						
					}
					if (ExcelReader.validExceLColunm(temp, 5)) {
						
						budgChargeStanDict.setUnit(temp[5]);
			    		mapVo.put("unit", temp[5]);
						
					}else{
						mapVo.put("unit", "");
					}
					
					if (ExcelReader.validExceLColunm(temp, 6)) {
						
						budgChargeStanDict.setCharge_stan_describe(temp[6]);
						
			    		mapVo.put("charge_stan_describe", temp[6]);
					
					}else{
						mapVo.put("charge_stan_describe", "");
					} 
					 
				if (err_sb.toString().length() > 0) {
					
					budgChargeStanDict.setError_type(err_sb.toString());
					
					list_err.add(budgChargeStanDict);
					
				} else {
					
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(String.valueOf(mapVo.get("charge_stan_name"))));
				  mapVo.put("wbx_code", StringTool.toWuBi(String.valueOf(mapVo.get("charge_stan_name"))));
				  
				  addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgChargeStandardDictService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgChargeStandardDict data_exc = new BudgChargeStandardDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
    
	/**
	 * 查询预算科室
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcharge/standict/queryChargeStanDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChargeStanDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgChargeStanDict = budgChargeStandardDictService.queryChargeStanDeptSet(getPage(mapVo));

		return JSONObject.parseObject(budgChargeStanDict);
		
	}
}

