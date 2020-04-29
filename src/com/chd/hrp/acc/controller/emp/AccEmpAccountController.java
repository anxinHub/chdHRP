/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.emp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.Plupload;
import com.chd.hrp.acc.entity.AccEmpAccount;
import com.chd.hrp.acc.serviceImpl.emp.AccEmpAccountServiceImpl;
import com.chd.hrp.acc.serviceImpl.emp.AccWageTypeServiceImpl;
import com.chd.hrp.sys.serviceImpl.EmpDictServiceImpl;

/**
* @Title. @Description.
* 职工账号
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccEmpAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(AccEmpAccountController.class);
	
	
	@Resource(name = "accEmpAccountService")
	private final AccEmpAccountServiceImpl accEmpAccountService = null;
   
	@Resource(name = "empDictService")
	private final EmpDictServiceImpl empDictService = null;
	
	@Resource(name = "accWageTypeService")
	private final AccWageTypeServiceImpl accWageTypeService = null;
	
	/**
	 * 【工资管理-账户信息-职工账号】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accempaccount/accEmpAccountMainPage", method = RequestMethod.GET)
	public String accEmpAccountMainPage(Model mode) throws Exception {
		return "hrp/acc/accempaccount/accEmpAccountMain";
	}
	
	/**
	*职工账号<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accempaccount/accEmpAccountAddPage", method = RequestMethod.GET)
	public String accEmpAccountAddPage(Model mode) throws Exception {

		return "hrp/acc/accempaccount/accEmpAccountAdd";

	}
	
	/**
	*职工账号<BR>
	*维护页面跳转
	*/
	//查询未维护账号的职工页面
	@RequestMapping(value = "/hrp/acc/accempaccount/accEmpAccountIndexPage", method = RequestMethod.GET)
	public String accEmpAccountIndexPage(Model mode) throws Exception {

		return "hrp/acc/accempaccount/accEmpAccountIndex";

	}
	
	/**
	*职工账号<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accempaccount/addAccEmpAccount", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccEmpAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
		String accEmpAccountJson = accEmpAccountService.addAccEmpAccount(mapVo);

		return JSONObject.parseObject(accEmpAccountJson);
		
	}
	/**
	*职工账号<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accempaccount/queryAccEmpAccount", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccEmpAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("emp_name")!= null){
		//用来转换大写
		mapVo.put("emp_name",mapVo.get("emp_name").toString().toUpperCase());
		}
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
        String accEmpAccount = accEmpAccountService.queryAccEmpAccount(getPage(mapVo));

		return JSONObject.parseObject(accEmpAccount);
		
	}
	/**
	*未维护账号的职工<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accempaccount/queryAccEmpAccountIndex", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccEmpAccountIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
        
        String accEmpAccount = accEmpAccountService.queryAccEmpAccountIndex(getPage(mapVo));

		return JSONObject.parseObject(accEmpAccount);
		
	}
	/**
	*职工账号<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accempaccount/deleteAccEmpAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccEmpAccount(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("account_id", res[0]);
            
            mapVo.put("group_id", res[1]);
            
            mapVo.put("hos_id", res[2]);
            
            mapVo.put("copy_code", res[3]); 
           
            listVo.add(mapVo);
        }
		
		String accEmpAccountJson = accEmpAccountService.deleteBatchAccEmpAccount(listVo);
	   
		return JSONObject.parseObject(accEmpAccountJson);
			
	}
	
	/**
	*职工账号<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accempaccount/accEmpAccountUpdatePage", method = RequestMethod.GET)
	
	public String accEmpAccountUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
      
		AccEmpAccount accEmpAccount = new AccEmpAccount();
		
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
        
		accEmpAccount = accEmpAccountService.queryAccEmpAccountByCode(mapVo);
		
		mode.addAttribute("group_id", accEmpAccount.getGroup_id());
		
		mode.addAttribute("hos_id", accEmpAccount.getHos_id());
		
		mode.addAttribute("copy_code", accEmpAccount.getCopy_code());
		
		mode.addAttribute("account_id", accEmpAccount.getAccount_id());
		
		mode.addAttribute("emp_id", accEmpAccount.getEmp_id());
		
		mode.addAttribute("emp_no", accEmpAccount.getEmp_no());
		
		mode.addAttribute("emp_code", accEmpAccount.getEmp_code());
		
		mode.addAttribute("emp_name", accEmpAccount.getEmp_name());
		
		mode.addAttribute("account_code", accEmpAccount.getAccount_code());
		
		mode.addAttribute("account_name", accEmpAccount.getAccount_name());
		
		mode.addAttribute("is_number", accEmpAccount.getId_number());
		
		mode.addAttribute("type_id", accEmpAccount.getType_id());
		
		mode.addAttribute("type_name", accEmpAccount.getType_name());
		
		mode.addAttribute("kind_code", accEmpAccount.getKind_code());
		
		mode.addAttribute("dept_id", accEmpAccount.getDept_id());
		
		mode.addAttribute("dept_no", accEmpAccount.getDept_no());
		
		mode.addAttribute("dept_code", accEmpAccount.getDept_code());
		
		mode.addAttribute("dept_name", accEmpAccount.getDept_name());
		
		mode.addAttribute("note", accEmpAccount.getNote());
		
		mode.addAttribute("is_stop", accEmpAccount.getIs_stop());
		
		mode.addAttribute("emp_bank", accEmpAccount.getEmp_bank());
		
		mode.addAttribute("emp_arear", accEmpAccount.getEmp_arear());
		
		mode.addAttribute("is_bank_same", accEmpAccount.getIs_bank_same());
		
		mode.addAttribute("is_city_same", accEmpAccount.getIs_city_same());
		
		return "hrp/acc/accempaccount/accEmpAccountUpdate";
	}
	/**
	*职工账号<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accempaccount/updateAccEmpAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccEmpAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				}
		
		String accEmpAccountJson = accEmpAccountService.updateAccEmpAccount(mapVo);
		
		return JSONObject.parseObject(accEmpAccountJson);
	}
	
	/**
	*职工账号<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accempaccount/accEmpAccountUpdateIndex", method = RequestMethod.GET)
	public String accEmpAccountUpdateIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
       
		AccEmpAccount accEmpAccount = new AccEmpAccount();
        
        	if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
        
		accEmpAccount = accEmpAccountService.queryAccEmpAccountIndexByCode(mapVo);
		
		mode.addAttribute("group_id", accEmpAccount.getGroup_id());
		
		mode.addAttribute("hos_id", accEmpAccount.getHos_id());
		
		mode.addAttribute("copy_code", accEmpAccount.getCopy_code());
		
		mode.addAttribute("emp_id", accEmpAccount.getEmp_id());
		
		mode.addAttribute("emp_no", accEmpAccount.getEmp_no());
		
		mode.addAttribute("emp_code", accEmpAccount.getEmp_code());
		
		mode.addAttribute("emp_name", accEmpAccount.getEmp_name());
		
		mode.addAttribute("is_number", accEmpAccount.getId_number());
		
		mode.addAttribute("kind_code", accEmpAccount.getKind_code());
		
		mode.addAttribute("dept_id", accEmpAccount.getDept_id());
		
		mode.addAttribute("dept_no", accEmpAccount.getDept_no());
		
		mode.addAttribute("dept_code", accEmpAccount.getDept_code());
		
		mode.addAttribute("dept_name", accEmpAccount.getDept_name());
		
		return "hrp/acc/accempaccount/accEmpAccountUpdateIndex";
	}
	
	//下载导入模版
	@RequestMapping(value="/hrp/acc/accempaccount/downTemplateEmpAccount", method = RequestMethod.GET)  
	 public String downTemplateEmpAccount(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
		
		printTemplate(request, response, "acc"+File.separator+"downTemplate", "职工账号.xlsx");

		return null; 
	 }
	
	
	/**
	 * 【工资管理-账户信息-职工账号】：主页面-导入页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accempaccount/accEmpAccountImportPage", method = RequestMethod.GET)
	public String accEmpAccountImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/accempaccount/accEmpAccountImport";
	}
	
	/**
	 * 【工资管理-账户信息-职工账号】：主页面-导入页面-确定导入
	 */
	@RequestMapping(value = "/hrp/acc/accempaccount/importAccEmpAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccEmpAccount(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String importJson = null;
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}

			importJson = accEmpAccountService.importAccEmpAccount(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			importJson = e.getMessage();
		}
		return JSONObject.parseObject(importJson);
	}
	
	
	
	
	/*@RequestMapping(value="/hrp/acc/accempaccount/readAccEmpAccount",method = RequestMethod.POST)  
    public String readAccEmpAccount(@RequestParam Map<String, Object> entityMap,Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,String rules,Model mode) throws IOException { 
			
		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		
		try {
			
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					if (entityMap.get("group_id") == null) {
						
						mapVo.put("group_id", SessionManager.getGroupId());
						
					}else{
						
						mapVo.put("group_id", entityMap.get("group_id"));
						
					}
					
					if (entityMap.get("hos_id") == null) {
						
						mapVo.put("hos_id", SessionManager.getHosId());
						
					}else{
						
						mapVo.put("hos_id", entityMap.get("hos_id"));
						
					}
					
					if (entityMap.get("copy_code") == null) {
						
						mapVo.put("copy_code", SessionManager.getCopyCode());
					
					}else{
						
						mapVo.put("copy_code", entityMap.get("copy_code"));
						
					}
					
					if (ExcelReader.validExceLColunm(temp,0)) {
						
						entityMap.put("emp_code", temp[0]);
						
						mapVo.put("emp_code", temp[0]);
						
					} else {
						
						err_sb.append("职工编码为空  ");
						
					}
					
					EmpDict empDict = empDictService.queryEmpDictByCode(mapVo);
					
					if(empDict!= null){
						
						entityMap.put("emp_id", empDict.getEmp_id());
						
						entityMap.put("emp_no", empDict.getEmp_no());
						
						mapVo.put("emp_id", empDict.getEmp_id());
						
						mapVo.put("emp_no", empDict.getEmp_no());
						
						if(!"".equals(empDict.getDept_id()) && null != empDict.getDept_id()){
							
							entityMap.put("dept_id", empDict.getDept_id());
							
							mapVo.put("dept_id", empDict.getDept_id());
							
							entityMap.put("dept_no", empDict.getDept_no());
							
							mapVo.put("dept_no", empDict.getDept_no());
							
						}else{
							
							err_sb.append("此职工所属部门未做维护  ");
							
						}
						
					}else {
						
						err_sb.append("此职工不存在  ");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						entityMap.put("account_name", temp[2]);
						
						mapVo.put("account_name", temp[2]);
						
					} else {
						
						err_sb.append("账户名为空  ");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						entityMap.put("account_code", temp[3]);
						
						mapVo.put("account_code", temp[3]);
						
					} else {
						
						err_sb.append("工资号为空  ");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						entityMap.put("type_code", temp[4]);
						
						mapVo.put("type_code", temp[4]);
						
					} else {
						
						err_sb.append("账户类别编码为空  ");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						entityMap.put("type_name", temp[5]);
						
						mapVo.put("type_name", temp[5]);
						
					} else {
						
						entityMap.put("type_name", "");
						
						mapVo.put("type_name","");
						
					}
					
					
					AccWageType accWageType= accWageTypeService.queryAccWageTypeByCode(mapVo);
					
					if(accWageType!= null){
						
						entityMap.put("type_id", accWageType.getType_id());
						
						mapVo.put("type_id", accWageType.getType_id());
						
					}else {
						
						err_sb.append("此账户类别不存在  ");
						
					}
						
					mapVo.put("is_stop", "0");
						
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						entityMap.put("note", temp[6]);
						
						mapVo.put("note", temp[6]);
						
					} else {
						
						entityMap.put("note", "");
						
						mapVo.put("note", "");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,7)) {
						
						entityMap.put("emp_bank", temp[7]);
						
						mapVo.put("emp_bank", temp[7]);
						
					} else {
						
						entityMap.put("emp_bank", "");
						
						mapVo.put("emp_bank", "");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,8)) {
						
						entityMap.put("emp_arear", temp[7]);
						
						mapVo.put("emp_arear", temp[7]);
						
					} else {
						
						entityMap.put("emp_arear", "");
						
						mapVo.put("emp_arear", "");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,9)) {
						
						if("是".equals(temp[9])){
							
							entityMap.put("is_bank_same", "1");
							
							mapVo.put("is_bank_same", "1");
							
						}else{
							
							entityMap.put("is_bank_same", "0");
							
							mapVo.put("is_bank_same", "0");
							
						}
						
					} else {
						
						entityMap.put("is_bank_same", "0");
						
						mapVo.put("is_bank_same", "0");
						
					}

					if (ExcelReader.validExceLColunm(temp,10)) {
						
						if("是".equals(temp[10])){
							
							entityMap.put("is_city_same", "1");
							
							mapVo.put("is_city_same", "1");
							
						}else{
							
							entityMap.put("is_city_same", "0");
							
							mapVo.put("is_city_same", "0");
							
						}
						
					} else {
						
						entityMap.put("is_city_same", "0");
						
						mapVo.put("is_city_same", "0");
						
					}

				if(err_sb.toString().length() == 0){
						
					AccWagePay data_exc_extis = accWagePayService.queryAccWagePayByCode(mapVo);
					
					if (data_exc_extis != null) {
						
						err_sb.append("此条数据已经存在！ ");
					
					}
				
				}
				
				mapList.add(mapVo);
				
				if(err_sb.toString().length() > 0){
					
					mapVo.put("error_type",err_sb.toString());
					
					list_err.add(mapVo);
					
				}
				
			}
			
			if(list_err.size()<=0){
				
				accEmpAccountService.addBatchAccEmpAccount(mapList);
				
			}
			
		} catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			Map<String,Object> data_exc = new HashMap<String, Object>();
			
			data_exc.put("error_type","导入系统出错  ");
			
			list_err.add(data_exc);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    } */
	
	@RequestMapping(value = "/hrp/acc/accempaccount/queryAccEmpAccountCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEmpAccountCount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
		String accEmpAccountJson = accEmpAccountService.queryAccEmpAccountCount(mapVo);
		
		return JSONObject.parseObject(accEmpAccountJson);
	}
	
	@RequestMapping(value = "/hrp/acc/accempaccount/updateAccEmpAccountCountMain", method = RequestMethod.GET)
	
	public String updateAccEmpAccountCountMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
      
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));
		
		return "hrp/acc/accempaccount/updateAccEmpAccountCountMain";
	}
	
	@RequestMapping(value = "/hrp/acc/accempaccount/queryAccEmpAccountByInter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEmpAccountByInter(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
        
        String accEmpAccount = accEmpAccountService.queryAccEmpAccountByInter(getPage(mapVo));

		return JSONObject.parseObject(accEmpAccount);
		
	}
	
}

