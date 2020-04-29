/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.deptyblimit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
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
import com.chd.hrp.budg.entity.BudgDeptYbLimit;
import com.chd.hrp.budg.entity.BudgHosYbLimit;
import com.chd.hrp.budg.service.business.compilationbasic.deptybimint.BudgDeptYbLimitService;
import com.chd.hrp.budg.service.business.compilationbasic.hosyblimit.BudgHosYbLimitService;
/**
 * 
 * @Description:
 * 科室医保额度控制
 * @Table:
 * BUDG_DEPT_YB_LIMIT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDeptYbLimitController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDeptYbLimitController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptYbLimitService")
	private final BudgDeptYbLimitService budgDeptYbLimitService = null;
   
	@Resource(name = "budgHosYbLimitService")
	private final BudgHosYbLimitService budgHosYbLimitService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/budgDeptYBLimitMainPage", method = RequestMethod.GET)
	public String budgDeptYbLimitMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptyblimit/budgDeptYbLimitMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/budgDeptYbLimitAddPage", method = RequestMethod.GET)
	public String budgDeptYbLimitAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptyblimit/budgDeptYbLimitAdd";

	}
	/**
	 * @Description 
	 * 保存数据  科室医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/saveBudgDeptYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgDeptYbLimit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("dept_id", ids[1]);
			mapVo.put("insurance_code", ids[2]);
			mapVo.put("rate", ids[3]);
			if("-1".equals(ids[4])){
				mapVo.put("dept_control_limit", "");
			}else{
				mapVo.put("dept_control_limit", ids[4]);
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
		
	  
		String budgDeptYbLimitJson = null ;
		
		try {
			
			budgDeptYbLimitJson = budgDeptYbLimitService.save(listVo);
			
		} catch (Exception e) {
			
			budgDeptYbLimitJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgDeptYbLimitJson);
	}

	/**
	 * @Description 
	 * 添加数据 科室医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/addBudgDeptYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDeptYbLimit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgDeptYbLimitJson = budgDeptYbLimitService.add(mapVo);
		
		return JSONObject.parseObject(budgDeptYbLimitJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 科室医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/budgDeptYbLimitUpdatePage", method = RequestMethod.GET)
	public String budgDeptYbLimitUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("year") == null){
        mapVo.put("year", SessionManager.getAcctYear());
		}
		
		BudgDeptYbLimit budgDeptYbLimit = new BudgDeptYbLimit();
    
		budgDeptYbLimit = budgDeptYbLimitService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptYbLimit.getGroup_id());
		mode.addAttribute("hos_id", budgDeptYbLimit.getHos_id());
		mode.addAttribute("copy_code", budgDeptYbLimit.getCopy_code());
		mode.addAttribute("year", budgDeptYbLimit.getYear());
		mode.addAttribute("dept_id", budgDeptYbLimit.getDept_id());
		mode.addAttribute("insurance_code", budgDeptYbLimit.getInsurance_code());
		mode.addAttribute("rate", budgDeptYbLimit.getRate());
		mode.addAttribute("dept_control_limit", budgDeptYbLimit.getDept_control_limit());
		mode.addAttribute("remark", budgDeptYbLimit.getRemark());
		
		return "hrp/budg/business/compilationbasic/deptyblimit/budgDeptYbLimitUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/updateBudgDeptYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDeptYbLimit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("year", ids[3]);
			mapVo.put("dept_id", ids[4])   ;
			mapVo.put("insurance_code",ids[5]);
			mapVo.put("rate", ids[6]);
			mapVo.put("dept_control_limit", ids[7]);
			if("-1".equals(ids[8])){
				mapVo.put("remark","");
			}else{
				mapVo.put("remark",ids[8]);
			}
			listVo.add(mapVo);
	      
	    }             
	  
		String budgDeptYbLimitJson = budgDeptYbLimitService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgDeptYbLimitJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/addOrUpdateBudgDeptYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDeptYbLimit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptYbLimitJson ="";
		

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
	  
		budgDeptYbLimitJson = budgDeptYbLimitService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptYbLimitJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/deleteBudgDeptYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDeptYbLimit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("dept_id", ids[4])   ;
				mapVo.put("insurance_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDeptYbLimitJson = budgDeptYbLimitService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgDeptYbLimitJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室医保额度控制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/queryBudgDeptYbLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDeptYbLimit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		mapVo.put("lastYear", Integer.parseInt(String.valueOf(mapVo.get("year")))-1);
		
		
		String budgDeptYbLimit = budgDeptYbLimitService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDeptYbLimit);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室医保额度控制
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/budgDeptYbLimitImportPage", method = RequestMethod.GET)
	public String budgDeptYbLimitImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptyblimit/budgDeptYbLimitImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室医保额度控制
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/deptyblimit/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","科室医保额度控制模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室医保额度控制
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/deptyblimit/readBudgDeptYbLimitFiles",method = RequestMethod.POST)  
    public String readBudgDeptYbLimitFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String, Object> errMap = new HashMap<String, Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					}         
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
						errMap.put("year", temp[0]);
						mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
                    if (ExcelReader.validExceLColunm(temp, 1)) {						
						
						errMap.put("dept_code", temp[1]);
						
						mapVo.put("dept_code", temp[1]);
						
						Map<String,Object> map  = budgDeptYbLimitService.queryDeptID(mapVo);
						
						if(map != null){
							
							mapVo.put("dept_id", map.get("dept_id"));
							
						}else{
							
							err_sb.append("科室编码不存在或不是直接医疗科室;");
						}
					
					} else {
						
						err_sb.append("科室编码为空  ");
						
					}
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						errMap.put("insurance_code", temp[2]);
			    		mapVo.put("insurance_code", temp[2]);
		    		
			    		int count = budgDeptYbLimitService.queryInsuranceCodeExist(mapVo);
			    		
			    		if(count == 0 ){
			    			
			    			err_sb.append("医保类型编码不存在;");
			    			
			    		}
					
					} else {
						
						err_sb.append("医保类型编码为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						errMap.put("dept_control_limit", temp[3]);
						
						mapVo.put("dept_control_limit", temp[3]);
					
					} else {
						
						mapVo.put("dept_control_limit", "");
						
					}
					if (ExcelReader.validExceLColunm(temp, 4)) {
						
						errMap.put("remark", temp[4]);
						
						mapVo.put("remark", temp[4]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
					if(mapVo.get("dept_id") != null ){
						
						int count = budgDeptYbLimitService.queryDataExist(mapVo);
						
						if(count > 0 ){
							err_sb.append("数据已存在;");
						}
					}
					
					if(err_sb.length() >  0){
						
						errMap.put("error_type",err_sb.toString()); 
						
						list_err.add(errMap);
						
					}else{
						
						mapVo.put("rate", "");
						
						addList.add(mapVo);
					}
					
				}
				if(list_err.size() == 0){
					String dataJson = budgDeptYbLimitService.addBatch(addList);
				}
			} catch (DataAccessException e) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("error_type","系统导入出错");
				list_err.add( map);
			}
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
		
		
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室医保额度控制
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/addBatchBudgDeptYbLimit", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDeptYbLimit(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDeptYbLimit> list_err = new ArrayList<BudgDeptYbLimit>();
		
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
			
			BudgDeptYbLimit budgDeptYbLimit = new BudgDeptYbLimit();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgDeptYbLimit.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgDeptYbLimit.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("insurance_code"))) {
						
					budgDeptYbLimit.setInsurance_code((String)jsonObj.get("insurance_code"));
		    		mapVo.put("insurance_code", jsonObj.get("insurance_code"));
		    		} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("rate"))) {
						
					budgDeptYbLimit.setRate(Double.valueOf((String)jsonObj.get("rate")));
		    		mapVo.put("rate", jsonObj.get("rate"));
		    		} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_control_limit"))) {
						
					budgDeptYbLimit.setDept_control_limit(Double.valueOf((String)jsonObj.get("dept_control_limit")));
		    		mapVo.put("dept_control_limit", jsonObj.get("dept_control_limit"));
		    		} else {
						
						err_sb.append("科室医保额度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgDeptYbLimit.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					
				BudgDeptYbLimit data_exc_extis = budgDeptYbLimitService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptYbLimit.setError_type(err_sb.toString());
					
					list_err.add(budgDeptYbLimit);
					
				} else {
			  
					String dataJson = budgDeptYbLimitService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDeptYbLimit budgDeptYbLimit = new BudgDeptYbLimit();
			
			list_err.add(budgDeptYbLimit);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	
	/**
	 * 科室 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/queryBudgDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String dept = budgDeptYbLimitService.queryBudgDeptDict(mapVo);
		
		return dept;

	}
	
	
	/**
	 * 医保类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/queryBudgYBTY", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBTY(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String ybType = budgDeptYbLimitService.queryBudgYBTY(mapVo);
		
		return ybType;

	}
	
	/**
	 * 根据年度、科室、医保类型信息 查询 全院医保额度、上年医保收入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/qureyLastData", method = RequestMethod.POST)
	@ResponseBody
	public String qureyLastData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String lastData = budgDeptYbLimitService.qureyLastData(mapVo);
		
		return lastData;

	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		  
			
			/*按总额预付历史收入数据表中上年数据生成。
			生成时年度为查询条件中所选年度；
			科室、医保类型、上年医保收入取自总额预付历史收入数据表BUDG_ZEYF_INCOME_HIS；
			全院医保额度取自医院医保额度控制BUDG_HOS_YB_LIMIT中本年控制额度	*/
			
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			String budgWorkHosExecuteJson = null ;
			try {
				
				int count = budgDeptYbLimitService.addGenerate(mapVo);
				 
				
				if( count > 0){
					
					budgWorkHosExecuteJson ="{\"msg\":\"操作成功！\",\"state\":\"true\"}";
					
				}else{
					
					budgWorkHosExecuteJson ="{\"error\":\"数据已全部生成，无需再次生成\"}";
					
				}
				
			} catch (Exception e) {

				budgWorkHosExecuteJson = e.getMessage();
				
			}
			
			
			
		    
		  return JSONObject.parseObject(budgWorkHosExecuteJson);	
	}
	
	/**
	 * @Description 
	 * 历史数据比例分解
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/updateResolveRate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateResolveRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		  
			
			/*计算分解比例和科室医保额度。
				分解比例=上年医保收入/所有科室当前医保类型上年医保收入之和
				科室医保额度=全院医保本年控制额度*分解比例	*/
			
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			String budgWorkHosExecuteJson = null ;
			try {
				
				budgWorkHosExecuteJson = budgDeptYbLimitService.updateResolveRate(mapVo);
					
				
			} catch (Exception e) {

				budgWorkHosExecuteJson ="{\"error\":\"操作失败！\",\"state\":\"true\"}";
				//budgWorkHosExecuteJson=StringEscapeUtils.escapeJava(budgWorkHosExecuteJson);
				//budgWorkHosExecuteJson ="{\"error\":\"数据生成失败，"+e.getMessage()+"\"}";
				
			}
			//budgWorkHosExecuteJson ="{\"msg\":\"操作成功！\",\"state\":\"true\"}";
					    
			Map<String,Object> map= (Map<String,Object>)JSONObject.parseObject(budgWorkHosExecuteJson);	
			return map;
	}
	/**
	 * @Description 
	 * 最新导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptyblimit/budgDeptYbLimitImportNewPage", method = RequestMethod.POST)
	@ResponseBody
	public String budgDeptYbLimitImportNewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgDeptYbLimitService.budgDeptYbLimitImportNewPage(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	
}

