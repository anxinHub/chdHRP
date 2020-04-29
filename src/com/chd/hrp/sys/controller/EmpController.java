/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
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
import org.nutz.lang.Strings;
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
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.Emp;
import com.chd.hrp.sys.entity.EmpKind;
import com.chd.hrp.sys.service.DeptDictService;
import com.chd.hrp.sys.service.EmpKindService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.EmpDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.EmpServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

@Controller
public class EmpController extends BaseController{
	private static Logger logger = Logger.getLogger(EmpController.class);
	
	
	@Resource(name = "empService")
	private final EmpServiceImpl empService = null;
	
	@Resource(name = "empService")
	private final EmpServiceImpl empServiceImpl = null;
	
	@Resource(name = "empDictService")
	private final EmpDictServiceImpl empDictService = null;
	
	//科室变更
	@Resource(name = "deptDictService")
	private final DeptDictService deptDictService = null;
   //职工分类
	@Resource(name = "empKindService")
	private final EmpKindService  empKindService = null;
	
	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
    
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/emp/empMainPage", method = RequestMethod.GET)
	public String empMainPage(Model mode) throws Exception {

		return "hrp/sys/emp/empMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/emp/empAddPage", method = RequestMethod.GET)
	public String empAddPage(Model mode) throws Exception {

		return "hrp/sys/emp/empAdd";

	}
	
	//变更页面
	@RequestMapping(value = "/hrp/sys/emp/empChangePage", method = RequestMethod.GET)
	public String empChangePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		Emp emp = new Emp();
		emp = empService.queryEmpByCode(mapVo);
		mode.addAttribute("group_id", emp.getGroup_id());
		mode.addAttribute("hos_id", emp.getHos_id());
		mode.addAttribute("emp_id", emp.getEmp_id());
		mode.addAttribute("emp_no", emp.getEmp_no());
		mode.addAttribute("emp_code", emp.getEmp_code());
		mode.addAttribute("emp_name", emp.getEmp_name());
		mode.addAttribute("dept_no", emp.getDept_no());
		mode.addAttribute("dept_name", emp.getDept_name());
		mode.addAttribute("dept_code", emp.getDept_code());
		mode.addAttribute("dept_id", emp.getDept_id());
		mode.addAttribute("kind_code", emp.getKind_code());
		mode.addAttribute("kind_name", emp.getKind_name());
		mode.addAttribute("sort_code", emp.getSort_code());
		mode.addAttribute("spell_code", emp.getSpell_code());
		mode.addAttribute("wbx_code", emp.getWbx_code());
		mode.addAttribute("is_stop", emp.getIs_stop());
		mode.addAttribute("note", emp.getNote());
		return "hrp/sys/emp/empChange";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/emp/addEmp", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String empJson = empService.addEmp(mapVo);

		return JSONObject.parseObject(empJson);
		
	}
	
	@RequestMapping(value = "/hrp/sys/emp/addEmpDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String dictType = mapVo.get("dictType").toString();
		if(dictType.equals("0")){
			empService.updateEmpCode(mapVo);
		}else{
			empService.updateEmpName(mapVo);
		}
		
		String empJson = empDictService.addEmpDict(mapVo);

		return JSONObject.parseObject(empJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/emp/queryEmp", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		if(mapVo.get("emp_code") != null){
			//用来转换大写
			mapVo.put("emp_code",mapVo.get("emp_code").toString().toUpperCase());
		}
		
		String emp = empService.queryEmp(getPage(mapVo));

		return JSONObject.parseObject(emp);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/emp/deleteEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEmp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String empJson = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("emp_id", id.split("@")[2]);
			mapVo.put("emp_code", id.split("@")[3]);
		    listVo.add(mapVo);
        }
		  empJson = empService.deleteBatchEmp(listVo);
		
	   return JSONObject.parseObject(empJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/emp/empUpdatePage", method = RequestMethod.GET)
	
	public String empUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Emp emp = new Emp();
		emp = empService.queryEmpByCode(mapVo);
		mode.addAttribute("group_id", emp.getGroup_id());
		mode.addAttribute("hos_id", emp.getHos_id());
		mode.addAttribute("emp_id", emp.getEmp_id());
		mode.addAttribute("emp_no", emp.getEmp_no());
		mode.addAttribute("emp_code", emp.getEmp_code());
		mode.addAttribute("emp_name", emp.getEmp_name());
		mode.addAttribute("dept_no", emp.getDept_no());
		mode.addAttribute("dept_name", emp.getDept_name());
		mode.addAttribute("dept_id", emp.getDept_id());
		mode.addAttribute("kind_code", emp.getKind_code());
		mode.addAttribute("kind_name", emp.getKind_name());
		mode.addAttribute("sort_code", emp.getSort_code());
		mode.addAttribute("spell_code", emp.getSpell_code());
		mode.addAttribute("wbx_code", emp.getWbx_code());
		mode.addAttribute("is_stop", emp.getIs_stop());
		mode.addAttribute("is_disable", emp.getIs_disable());
		mode.addAttribute("note", emp.getNote());
		
		return "hrp/sys/emp/empUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/emp/updateEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String empJson = empService.updateEmp(mapVo);
		
		return JSONObject.parseObject(empJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/emp/importEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String empJson = null;
		
		try {
			
			empJson = empService.importEmp(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			empJson = e.getMessage();
			
			logger.error(e.getMessage(),e);
		}
		
		
		return JSONObject.parseObject(empJson);
	}
	// 导入页面跳转
	@RequestMapping(value = "/hrp/sys/emp/sysEmpImportPage", method = RequestMethod.GET)
	public String costItemDictImportPage(Model mode) throws Exception {

		return "hrp/sys/emp/empImport";

	}
		
		
		// 下载导入模版
		@RequestMapping(value = "hrp/sys/emp/downTemplate", method = RequestMethod.GET)
		public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
			printTemplate(request, response, "sys\\医院信息", "职工维护.xlsx");
			return null;
		}
		
		
		/**
		 * 导入职工学历<BR>
		 * 
		 */
		@RequestMapping(value = "/hrp/sys/emp/readSysEmpFiles", method = RequestMethod.POST)
		public String readSysEmpFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
			
			Map<String, Object> entityMap = new HashMap<String, Object>();

			entityMap.put("group_id", SessionManager.getGroupId());
				
			entityMap.put("hos_id", SessionManager.getHosId());
				
			entityMap.put("copy_code", SessionManager.getCopyCode());

			// 添加编码规则判断
			entityMap.put("proj_code", "HOS_EMP");
			
			entityMap.put("mod_code", "00");

			Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
				
				//Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
				
				Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

				//String rules_v = rules.get("rules_view").toString();
				
				//String s_view = Strings.removeFirst(rules_v);
			
			
			List<Emp> list_err = new ArrayList<Emp>();
			
			List<Map<String, Object>> aList = new ArrayList<Map<String,Object>>();
			
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			
			try {
				for (int i = 1; i < list.size(); i++) {
					
					StringBuffer err_sb = new StringBuffer();
					
					Emp emp = new Emp();
					
					String temp[] = list.get(i);// 行
					
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

					if (ExcelReader.validExceLColunm(temp,0)) {
						
						Object emp_code = temp[0].length();
						
						if (emp_code !=  rules_level_length.get(1)) {
							
							err_sb.append("编码不符合要求,请重新添加！");
						}
						
						mapVo.put("emp_code", temp[0]);
						emp.setEmp_code(temp[0]);
						
					} else {
						
						err_sb.append("职工编码为空！");
					}

					if (ExcelReader.validExceLColunm(temp,1)) {
						
						mapVo.put("emp_name", temp[1]);
						
						emp.setEmp_name(temp[1]);
						
					} else {

						err_sb.append("职工名称不能为空!");
					}
					
		             if (ExcelReader.validExceLColunm(temp,2)) {
		            	 
		            	 Map<String, Object> deptMap = new HashMap<String, Object>();
							
		    			 deptMap.put("group_id", SessionManager.getGroupId());
		    					
		    			 deptMap.put("hos_id", SessionManager.getHosId());
		    					
		    			 deptMap.put("copy_code", SessionManager.getCopyCode());
		    			 
		    			 deptMap.put("dept_code", temp[2]);
		    					
		    			 DeptDict dept_dict = deptDictService.queryDeptDictByCode(deptMap);
						/*
						 * 根据科室编码查询ID NO
						 * */
		            		   
		            		  if(dept_dict != null ){
		            			  
		            			  mapVo.put("dept_id", dept_dict.getDept_id());
		            			  
		            			  mapVo.put("dept_no", dept_dict.getDept_no());
		            			  
		            		  }else{
		            			  
		            			  err_sb.append("科室编码不存在！");
		            		  }
		            		  
	            			  emp.setDept_code(temp[2].toString());
						    
					} else {

						err_sb.append("科室编码不能为空！");
					}
		             
				
	               if (ExcelReader.validExceLColunm(temp,3)) {
	            	   
						emp.setDept_name(temp[3].toString());
						
					} else {
						err_sb.append("科室名称不能为空！");
					}
	               
	               
	               if (ExcelReader.validExceLColunm(temp,4)) {
	            	   
	            	   Map<String, Object> kindMap = new HashMap<String, Object>();
						
	            	   kindMap.put("group_id", SessionManager.getGroupId());
		    					
	            	   kindMap.put("hos_id", SessionManager.getHosId());
		    			 
	            	   kindMap.put("kind_code", temp[4]);
	            	   
	            	   EmpKind empKind = empKindService.queryEmpKindByCode(kindMap);
	            	   
	            	   if(empKind != null){
	            		   
	            		   mapVo.put("kind_code", temp[4]);
	            		   
	            	   }else {
	            		   
	            		   err_sb.append("职工分类不存在！");
	            	   }
	            	   
	            	   emp.setKind_code(temp[4].toString());
	         						
	         		} else {

	         			 emp.setKind_code("");
	         			 mapVo.put("kind_code", "");

	         						
	         		}
	               
	           	/* 备注默认可以为空 */
					if (ExcelReader.validExceLColunm(temp,5)) {

						mapVo.put("note", temp[5]);
						
						emp.setNote(temp[5]);

					} else {

						mapVo.put("note", "");
						
						emp.setNote("");
					}
	               
                    if (ExcelReader.validExceLColunm(temp,6)) {
	            	   
                    	if("是".equals(temp[6])){
                    		
                    		mapVo.put("is_stop", "1");
                        	
      	            	   emp.setIs_stop(1);
                    		
                    	}else if("否".equals(temp[6])){
                    		
                    		mapVo.put("is_stop", "0");
                        	
       	            	   emp.setIs_stop(0);
                    		
                    	}else{
                    		
                    		mapVo.put("is_stop", temp[6]);
                        	
     	            	   emp.setIs_stop(Integer.parseInt(temp[6].toString()));
                    		
                    	}
	         						
	         		} else {
	         			
	         			mapVo.put("is_stop", "0");
	         						
	         		}
                    
                    if (ExcelReader.validExceLColunm(temp,7)) {
	            	   
                    	if("是".equals(temp[7])){
                    		
                    		mapVo.put("is_disable", "1");
                        	
      	            	   emp.setIs_disable(1);
                    		
                    	}else if("否".equals(temp[7])){
                    		
                    		mapVo.put("is_disable", "0");
                        	
       	            	   emp.setIs_disable(0);
                    		
                    	}else{
                    		
                    		mapVo.put("is_disable", temp[7]);
                        	
     	            	   emp.setIs_disable(Integer.parseInt(temp[7].toString()));
                    		
                    	}
	         						
	         		} else {
	         			
	         			//err_sb.append("是否启用不能为空！");
	         			mapVo.put("is_disable", "0");
	         						
	         		}
                    
                    Emp emp1 =  empService.queryEmpByCode(mapVo);
                    
                    if(emp1 !=null){
                    	
                    	err_sb.append("编码重复");
                    }
                    
                	Map<String, Object> utilMap = new HashMap<String, Object>();
                	
            		utilMap.put("group_id", entityMap.get("group_id"));
            		
            		utilMap.put("hos_id", entityMap.get("hos_id"));
            		
            		utilMap.put("copy_code", "");
            		
            		utilMap.put("field_table", "HOS_EMP");
            		
            		utilMap.put("field_key1", "");
            		
            		utilMap.put("field_value1", "");
            		
            		utilMap.put("field_key2", "");
            		
            		utilMap.put("field_value2", "");
            		
            		utilMap.remove("field_key2");
            		
            		utilMap.put("field_sort", "sort_code");
            		
            		int count = sysFunUtilMapper.querySysMaxSort(utilMap);
            		
            		mapVo.put("sort_code", count);
            		
                    
	               
					if (err_sb.toString().length() > 0) {
						
						emp.setError_type(err_sb.toString());
						
						list_err.add(emp);
						
					} else {
						
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_name").toString()));
						
						empServiceImpl.addImportEmp(mapVo);
										
					}		
				}
						
			}
			
			catch (DataAccessException e) {
				
				Emp data_exc = new Emp();
				
				data_exc.setError_type("导入系统出错");
				
				list_err.add(data_exc);
			}
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
		}
		
		
		/**
		 * 
		 * 批量添加
		 */
		@RequestMapping(value = "/hrp/sys/emp/addBatchSysEmp", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addBatchSysEmp(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
			
			Map<String, Object> entityMap = new HashMap<String, Object>();

			entityMap.put("group_id", SessionManager.getGroupId());
				
			entityMap.put("hos_id", SessionManager.getHosId());
				
			entityMap.put("copy_code", SessionManager.getCopyCode());

			// 添加编码规则判断
			entityMap.put("proj_code", "HOS_EMP");
			
			entityMap.put("mod_code", "00");

			Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
				
		    Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
				
		    Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

			String rules_v = rules.get("rules_view").toString();
				
			String s_view = Strings.removeFirst(rules_v);
			
			List<Emp> list_err = new ArrayList<Emp>();
			
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
			
			String s = null;
			
			Iterator it = json.iterator();
			
			try {
				
				while (it.hasNext()) {
					
					StringBuffer err_sb = new StringBuffer();
					
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					Emp  emp = new Emp();
					
					mapVo.put("emp_code", jsonObj.get("emp_code").toString());
					
					Object emp_code = mapVo.get("emp_code").toString().length();
					
					if (emp_code !=  rules_level_length.get(1)) {
						
						err_sb.append("编码不符合要求,请重新添加！");
					}
					
					mapVo.put("emp_name", jsonObj.get("emp_name").toString());
					
					mapVo.put("dept_code", jsonObj.get("dept_code").toString());
					
					mapVo.put("dept_name", jsonObj.get("dept_name").toString());
					
					mapVo.put("kind_code", jsonObj.get("kind_code").toString());
					
					mapVo.put("note", jsonObj.get("note").toString());
					
					mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));
					
					DeptDict dept_dict = deptDictService.queryDeptDictByCode(mapVo);
					
					if(dept_dict == null){
						
						err_sb.append("科室编码不存在！");
						
					}else{
						
						mapVo.put("dept_id", dept_dict.getDept_id());
						
						mapVo.put("dept_no", dept_dict.getDept_no());
					}
					
					  EmpKind empKind = empKindService.queryEmpKindByCode(mapVo);
	            	   
	            	   if(empKind == null){
	            		   
	            		   mapVo.put("kind_code", "");
	            		   
	            	   }else {
	            		   
	            		   mapVo.put("kind_code", empKind.getKind_code());
	            	   }
	            	   
	            		Map<String, Object> utilMap = new HashMap<String, Object>();
	                	
	            		utilMap.put("group_id", entityMap.get("group_id"));
	            		
	            		utilMap.put("hos_id", entityMap.get("hos_id"));
	            		
	            		utilMap.put("copy_code", "");
	            		
	            		utilMap.put("field_table", "HOS_EMP");
	            		
	            		utilMap.put("field_key1", "");
	            		
	            		utilMap.put("field_value1", "");
	            		
	            		utilMap.put("field_key2", "");
	            		
	            		utilMap.put("field_value2", "");
	            		
	            		utilMap.remove("field_key2");
	            		
	            		utilMap.put("field_sort", "sort_code");
	            		
	            		int count = sysFunUtilMapper.querySysMaxSort(utilMap);
	            		
	            		mapVo.put("sort_code", count);
	            		
                    Emp emp1 =  empService.queryEmpByCode(mapVo);
                    
                    if(emp1 !=null){
                    	
                    	err_sb.append("编码重复");
                    }
					
					if (err_sb.toString().length() > 0) {
						
						emp.setEmp_code(jsonObj.get("emp_code").toString());
						
						emp.setEmp_name(jsonObj.get("emp_name").toString());
						
						emp.setDept_code(jsonObj.get("dept_code").toString());
						
						emp.setDept_name(jsonObj.get("dept_name").toString());
						
						emp.setKind_code(jsonObj.get("kind_code").toString());
						
						emp.setNote(jsonObj.get("note").toString());
						
						emp.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
						
						emp.setError_type(err_sb.toString());
						
						list_err.add(emp);
						
					} else {
						
	                    mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_name").toString()));
						
						empServiceImpl.addImportEmp(mapVo);

					}
				}
			}
			catch (DataAccessException e) {
				
				return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
				
			}
			
			if (list_err.size() > 0) {
				
				return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));
				
			} else {
				
				return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
				
			}
		}
		
		@RequestMapping(value="/hrp/sys/emp/queryEmpChangeRemark", method=RequestMethod.POST)
		  @ResponseBody
		  public Map<String, Object> queryEmpChangeRemark(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception
		  {
		    if (mapVo.get("emp_code") != null)
		    {
		      mapVo.put("emp_code", mapVo.get("emp_code").toString().toUpperCase());
		    }

		    String emp = empService.queryEmpChangeRemark(mapVo);

		    return JSONObject.parseObject(emp);
		  }
}

