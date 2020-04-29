/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
 package com.chd.hrp.budg.controller.base.budgybinfor;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.hrp.budg.entity.BudgYbSingleDisease;
import com.chd.hrp.budg.service.base.budgybinfor.BudgYbSingleDiseaseService;
/**
 * 
 * @Description:
 * 医保单病种维护
 * @Table:
 * BUDG_YB_SINGLE_DISEASE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgYbSingleDiseaseController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgYbSingleDiseaseController.class);
	
	//引入Service服务
	@Resource(name = "budgYbSingleDiseaseService")
	private final BudgYbSingleDiseaseService budgYbSingleDiseaseService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/budgYBSingleDisMainPage", method = RequestMethod.GET)
	public String budgYBSingleDisMainPage(Model mode) throws Exception {

		return "/hrp/budg/base/budgybinfor/budgybsingledisease/budgYbSingleDiseaseMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/budgYbSingleDiseaseAddPage", method = RequestMethod.GET)
	public String budgYbSingleDiseaseAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgybinfor/budgybsingledisease/budgYbSingleDiseaseAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医保单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/addBudgYbSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgYbSingleDisease(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgYbSingleDiseaseJson = budgYbSingleDiseaseService.add(mapVo);

		return JSONObject.parseObject(budgYbSingleDiseaseJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医保单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/budgYbSingleDiseaseUpdatePage", method = RequestMethod.GET)
	public String budgYbSingleDiseaseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgYbSingleDisease budgYbSingleDisease = new BudgYbSingleDisease();
    
		budgYbSingleDisease = budgYbSingleDiseaseService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgYbSingleDisease.getGroup_id());
		mode.addAttribute("hos_id", budgYbSingleDisease.getHos_id());
		mode.addAttribute("copy_code", budgYbSingleDisease.getCopy_code());
		mode.addAttribute("disease_code", budgYbSingleDisease.getDisease_code());
		mode.addAttribute("insurance_code", budgYbSingleDisease.getInsurance_code());
		
		return "hrp/budg/base/budgybinfor/budgybsingledisease/budgYbSingleDiseaseUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医保单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/updateBudgYbSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgYbSingleDisease(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgYbSingleDiseaseJson = budgYbSingleDiseaseService.update(mapVo);
		
		return JSONObject.parseObject(budgYbSingleDiseaseJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医保单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/addOrUpdateBudgYbSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgYbSingleDisease(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgYbSingleDiseaseJson ="";
		

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
	  
		budgYbSingleDiseaseJson = budgYbSingleDiseaseService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgYbSingleDiseaseJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医保单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/deleteBudgYbSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgYbSingleDisease(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("disease_code", ids[3])   ;
				mapVo.put("insurance_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgYbSingleDiseaseJson = budgYbSingleDiseaseService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgYbSingleDiseaseJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医保单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/queryBudgYbSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgYbSingleDisease(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgYbSingleDisease = budgYbSingleDiseaseService.query(getPage(mapVo));

		return JSONObject.parseObject(budgYbSingleDisease);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医保单病种维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/budgYbSingleDiseaseImportPage", method = RequestMethod.GET)
	public String budgYbSingleDiseaseImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgybinfor/budgybsingledisease/budgYbSingleDiseaseImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医保单病种维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgybinfor/budgybsingledisease/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","医保单病种维护模板.xls");
	   // printTemplate(request,response,"budg\\base","预算指标字典导入模板.xls");
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医保单病种维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/readBudgYbSingleDiseaseFiles", method = RequestMethod.POST)
	public String readBudgYbSingleDiseaseFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<BudgYbSingleDisease> list_err = new ArrayList<BudgYbSingleDisease>();
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				BudgYbSingleDisease budgYbSingleDisease = new BudgYbSingleDisease();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				for (int j = i + 1; j < list.size(); j++) {
					String error[] = list.get(j);
					if (temp[0].equals(error[0]) && temp[1].equals(error[1])) {
						err_sb.append("第" + i + "行数据与第 " + j + "行【病种编码 医保类型编码】重复;");
					}
				}
				if (StringTool.isNotBlank(temp[0])) {
					budgYbSingleDisease.setDisease_code(temp[0]);
					mapVo.put("disease_code", temp[0]);
					
				} else {
					err_sb.append("病种编码为空  ");
				}
				if (StringTool.isNotBlank(temp[1])) {

					budgYbSingleDisease.setInsurance_code(temp[1]);
					mapVo.put("insurance_code", temp[1]);
					
				} else {
					err_sb.append("医保类型编码为空  ");
				}
				if ((StringTool.isNotBlank(temp[0])) && (StringTool.isNotBlank(temp[1]))){
					BudgYbSingleDisease budgYbSingleDisease2=budgYbSingleDiseaseService.queryByCode(mapVo);
					if(budgYbSingleDisease2!=null){
						err_sb.append("病种编码与医保类型编码已被占用;");
					}
				}
				
				if (err_sb.toString().length() > 0) {

					budgYbSingleDisease.setError_type(err_sb.toString());

					list_err.add(budgYbSingleDisease);

				} else {
					addList.add(mapVo);
				}
			}
			if(list_err.size() == 0){
				String dataJson = budgYbSingleDiseaseService.addBatch(addList);
			}

		} catch (DataAccessException e) {

			BudgYbSingleDisease data_exc = new BudgYbSingleDisease();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

   /**
	 * @Description 
	 * 批量添加数据 医保单病种维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybsingledisease/addBatchBudgYbSingleDisease", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgYbSingleDisease(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgYbSingleDisease> list_err = new ArrayList<BudgYbSingleDisease>();
		
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
			
			BudgYbSingleDisease budgYbSingleDisease = new BudgYbSingleDisease();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("disease_code"))) {
						
					budgYbSingleDisease.setDisease_code((String)jsonObj.get("disease_code"));
		    		mapVo.put("disease_code", jsonObj.get("disease_code"));
		    		} else {
						
						err_sb.append("病种编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("insurance_code"))) {
						
					budgYbSingleDisease.setInsurance_code((String)jsonObj.get("insurance_code"));
		    		mapVo.put("insurance_code", jsonObj.get("insurance_code"));
		    		} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					
					
				BudgYbSingleDisease data_exc_extis = budgYbSingleDiseaseService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgYbSingleDisease.setError_type(err_sb.toString());
					
					list_err.add(budgYbSingleDisease);
					
				} else {
			  
					String dataJson = budgYbSingleDiseaseService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgYbSingleDisease data_exc = new BudgYbSingleDisease();
			
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

