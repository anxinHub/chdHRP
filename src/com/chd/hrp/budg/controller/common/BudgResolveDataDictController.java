/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgFunPara;
import com.chd.hrp.budg.entity.BudgResolveDataDict;
import com.chd.hrp.budg.service.common.BudgCommonInfoService;
import com.chd.hrp.budg.service.common.BudgResolveDataDictService;
/**
 * 
 * @Description:
 * 自定义分解系数
 * @Table:
 * budg_resolve_data_dict
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgResolveDataDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgResolveDataDictController.class);
	
	//引入Service服务
	@Resource(name = "budgResolveDataDictService")
	private final BudgResolveDataDictService budgResolveDataDictService = null;
	@Resource(name = "budgCommonInfoService")
	private final BudgCommonInfoService budgCommonInfoService = null ;//导入用  （预算基本信息）
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/budgResolveDataDictMainPage", method = RequestMethod.GET)
	public String budgResolveDataDictMainPage(Model mode) throws Exception {

		return "hrp/budg/common/budgresolvedatadict/budgResolveDataDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/budgResolveDataDictAddPage", method = RequestMethod.GET)
	public String budgResolveDataDictAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("budg_level", mapVo.get("budg_level"));
		
		return "hrp/budg/common/budgresolvedatadict/budgResolveDataDictAdd";

	}
	
	/**
	 * @Description 
	 * 引用自定义分解系数页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/budgResolveDataPage", method = RequestMethod.GET)
	public String budgResolveDataPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("budg_level", mapVo.get("budg_level"));
		
		return "hrp/budg/common/budgresolvedatadict/budgResolveData";

	}
	/**
	 * @Description 
	 * 添加数据 自定义分解系数
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/addBudgResolveDataDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgResolveDataDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    	
		String resolveDataJson = null ;
		try {
			resolveDataJson = budgResolveDataDictService.add(mapVo);
		} catch (Exception e) {
			resolveDataJson =e.getMessage() ;
		}
		return JSONObject.parseObject(resolveDataJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 自定义分解系数
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/budgResolveDataDictUpdatePage", method = RequestMethod.GET)
	public String ResolveDataDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		mapVo.put("hos_id", SessionManager.getHosId());   
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		
		BudgResolveDataDict resolveDataDict = new BudgResolveDataDict();
    
		resolveDataDict = budgResolveDataDictService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", resolveDataDict.getGroup_id());
		mode.addAttribute("hos_id", resolveDataDict.getHos_id());
		mode.addAttribute("copy_code", resolveDataDict.getCopy_code());
		mode.addAttribute("budg_level", resolveDataDict.getBudg_level());
		mode.addAttribute("resolve_data_code", resolveDataDict.getResolve_data_code());
		mode.addAttribute("resolve_data_name", resolveDataDict.getResolve_data_name());
		
		return "hrp/budg/common/budgresolvedatadict/budgResolveDataDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 自定义分解系数
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/updateBudgResolveDataDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgResolveDataDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		mapVo.put("hos_id", SessionManager.getHosId());   
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		String resolveDataJson = null ;
		try {
			resolveDataJson = budgResolveDataDictService.update(mapVo);
		} catch (Exception e) {
			resolveDataJson =e.getMessage() ;
		}
		
		return JSONObject.parseObject(resolveDataJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 自定义分解系数
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/addOrUpdateBudgResolveDataDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgResolveDataDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String resolveDataJson ="";
		
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
	  
		resolveDataJson = budgResolveDataDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(resolveDataJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 自定义分解系数
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/deleteBudgResolveDataDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgResolveDataDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_level", ids[3])   ;
				mapVo.put("resolve_data_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String resolveDataJson = null ;
		
		try {
			
			resolveDataJson = budgResolveDataDictService.deleteBatch(listVo);
		} catch (Exception e) {
			
			resolveDataJson = e.getMessage() ;
		}
				
			
		return JSONObject.parseObject(resolveDataJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据 自定义分解系数 明细数据(医院月、科室年、科室月)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/deleteBudgResolveData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgResolveData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_level", ids[3])   ;
				mapVo.put("resolve_data_code", ids[4]);
				
				if("02".equals(ids[3])){
					mapVo.put("month", ids[5]);
				}else if("03".equals(ids[3])){
					mapVo.put("dept_id", ids[5]);
				}else if("04".equals(ids[3])){
					mapVo.put("month", ids[5]);
					mapVo.put("dept_id", ids[6]);
				}
				
	      listVo.add(mapVo);
	      
	    }
	    
		String resolveDataJson = null ;
		
		try {
			
			resolveDataJson = budgResolveDataDictService.deleteBudgResolveData(listVo);
		} catch (Exception e) {
			
			resolveDataJson = e.getMessage() ;
		}
				
			
		return JSONObject.parseObject(resolveDataJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 自定义分解系数
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/queryBudgResolveDataDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgResolveDataDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String funPara = budgResolveDataDictService.query(getPage(mapVo));

		return JSONObject.parseObject(funPara);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 自定义分解系数明细数据( 医院月、科室年、科室月)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/queryBudgResolveData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgResolveData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String funPara = budgResolveDataDictService.queryBudgResolveData(getPage(mapVo));

		return JSONObject.parseObject(funPara);
		
	}
  /**
	 * @Description 
	 * 导入跳转页面 自定义分解系数
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgresolvedatadict/budgResolveDataDictImportPage", method = RequestMethod.GET)
	public String budgResolveDataDictImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("budg_level", mapVo.get("budg_level"));
		
		return "hrp/budg/common/budgresolvedatadict/budgResolveDataDictImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 自定义分解系数
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/common/budgresolvedatadict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,HttpServletResponse response,
			 @RequestParam Map<String, Object> mapVo,Model mode) throws IOException { 
	    String budg_level = String.valueOf(mapVo.get("budg_level"));
	    
	    if("02".equals(budg_level)){
	    	printTemplate(request,response,"budg\\common","自定义分解系数(医院月).xls");
	    }else if("03".equals(budg_level)){
	    	printTemplate(request,response,"budg\\common","自定义分解系数(科室年).xls");
	    }else if("04".equals(budg_level)){
	    	printTemplate(request,response,"budg\\common","自定义分解系数(科室月).xls");
	    }
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 自定义分解系数
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/common/budgresolvedatadict/readBudgResolveDataDictFiles",method = RequestMethod.POST)  
    public String readBudgResolveDataDictFiles(Plupload plupload,HttpServletRequest request,HttpServletResponse response,
    		@RequestParam Map<String, Object> map,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		
		String budg_level = String.valueOf(map.get("budg_level"));
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());
		paraMap.put("hos_id", SessionManager.getHosId());
		paraMap.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String,Object>> deptList = budgCommonInfoService.queryDeptData(paraMap) ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> errMap = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
		    		mapVo.put("hos_id", SessionManager.getHosId());   
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					if("02".equals(budg_level)){
						if (ExcelReader.validExceLColunm(temp, 0)) {
							errMap.put("month", temp[0]);
							if(Arrays.asList(monthData).contains(temp[0])){
								
								mapVo.put("month", temp[0]);
								
							}else{
								err_sb.append("月份输入不合法(两位有效数字(01-12);");
							}
						
						} else {
							
							err_sb.append("月份为空;");
							
						}
						 
						if (ExcelReader.validExceLColunm(temp, 1)) {
							
							errMap.put("value", temp[1]);
				    		mapVo.put("value", temp[1]);
						
						} else {
							err_sb.append("数值为空  ");
						}
					}else if("03".equals(budg_level)){
						if (ExcelReader.validExceLColunm(temp, 0)) {
							
							errMap.put("dept_code", temp[0]);
							
							int flag = 0 ;
							
							for(Map<String,Object> item : deptList){
								
								if(temp[0].equals(item.get("dept_code"))){
								
									mapVo.put("dept_id", item.get("dept_id"));
									mapVo.put("dept_code", temp[0]);
									
									flag = 1 ;
									break ;
								}
							}
							
							if(flag == 0){
								err_sb.append("科室编码不存在或已停用,科室编码输入错误;");
							}
						
						} else {
							
							err_sb.append("科室编码为空;");
							
						}
						if (ExcelReader.validExceLColunm(temp, 1)) {
							
							errMap.put("dept_name", temp[1]);
				    		mapVo.put("dept_name", temp[1]);
						
						} else {
							
							err_sb.append("科室名称为空  ");
						}
						if (ExcelReader.validExceLColunm(temp, 2)) {
							
							errMap.put("value", temp[2]);
				    		mapVo.put("value", temp[2]);
						
						} else {
							err_sb.append("数值为空  ");
						}
					}else if("04".equals(budg_level)){
						if (ExcelReader.validExceLColunm(temp, 0)) {
							errMap.put("month", temp[0]);
							if(Arrays.asList(monthData).contains(temp[0])){
								
								mapVo.put("month", temp[0]);
								
							}else{
								err_sb.append("月份输入不合法(两位有效数字(01-12);");
							}
						
						} else {
							
							err_sb.append("月份为空;");
							
						}
						if (ExcelReader.validExceLColunm(temp, 1)) {
							
							errMap.put("dept_code", temp[1]);
							
							int flag = 0 ;
							
							for(Map<String,Object> item : deptList){
								
								if(temp[1].equals(item.get("dept_code"))){
								
									mapVo.put("dept_id", item.get("dept_id"));
									mapVo.put("dept_code", temp[1]);
									
									flag = 1 ;
									break ;
								}
							}
							
							if(flag == 0){
								err_sb.append("科室编码不存在或已停用,科室编码输入错误;");
							}
						
						} else {
							
							err_sb.append("科室编码为空;");
							
						}
						if (ExcelReader.validExceLColunm(temp, 2)) {
							
							errMap.put("dept_name", temp[2]);
				    		mapVo.put("dept_name", temp[2]);
						
						} else {
							
							err_sb.append("科室名称为空  ");
						}
						if (ExcelReader.validExceLColunm(temp, 3)) {
							
							errMap.put("value", temp[3]);
				    		mapVo.put("value", temp[3]);
						
						} else {
							err_sb.append("数值为空  ");
						}
					}
					
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				} else {
					
					returnList.add(mapVo) ;
					
				}
			}
			
			if(list_err.size() == 0){
				if(returnList.size() > 0){
					
					String json = ChdJson.toJson(returnList, returnList.size()) ;
					
					response.getWriter().print(json.substring(0,json.length()-1)+",\"state\":\"true\"}");
					
				}else{
					
					Map<String,Object> data_exc = new HashMap<String,Object>();
					
					data_exc.put("error_type","导入失败,没有导入数据");
					
					list_err.add(data_exc);
					
					String json = ChdJson.toJson(list_err, list_err.size()) ;
					
					response.getWriter().print(json.substring(0,json.length()-1)+",\"state\":\"false\"}");
				}
				
			}else{
				
				String json = ChdJson.toJson(list_err, list_err.size()) ;
				
				response.getWriter().print(json.substring(0,json.length()-1)+",\"state\":\"false\"}");
			}
			
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
			String json = ChdJson.toJson(list_err, list_err.size()) ;
			
			response.getWriter().print(json.substring(0,json.length()-1)+",\"state\":\"false\"}");
		}
		
		return null;
		
    }  
    
}

