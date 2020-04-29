package com.chd.hrp.budg.controller.business.purchase;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.service.business.purchase.ExecuteService;
import com.chd.hrp.budg.service.common.BudgCommonInfoService;
/**
 *资产采购预算执行
 * @author Administrator
 *
 */
@Controller
public class ExecuteController extends BaseController{

	private static Logger logger = Logger.getLogger(ExecuteController.class);
	
	//引入Serviece服务
	@Resource(name="executeService")
	private final ExecuteService executeService = null;
	/**
	 * 预算 基础信息 Serviece服务
	 */
	@Resource(name="budgCommonInfoService")
	private final BudgCommonInfoService budgCommonInfoService = null;
		
	/**
	 * 主页面跳转
	 */
	@RequestMapping(value = "/hrp/budg/business/purchase/assexecute/queryExecuteMain", method = RequestMethod.GET)
	public String queryExecuteMain(Model mode) throws Exception {

		return "hrp/budg/business/purchase/execute/executeMain";

	}
	
	/**
	 * 导入页面跳转
	 */
	@RequestMapping(value = "/hrp/budg/business/purchase/assexecute/budgAssPurExecuteImportPage", method = RequestMethod.GET)
	public String budgAssPurExecuteImportPage(Model mode) throws Exception {

		return "hrp/budg/business/purchase/execute/budgAssPurExecuteImport";

	}
	
	/**
	 * 查询数据
	 */
	@RequestMapping(value = "/hrp/budg/business/purchase/assexecute/queryExecuteMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExecuteMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgCashFlowBegin = executeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgCashFlowBegin);
		
	}
	
	/**
	 * 查询资金来源下拉框 
	 */
	@RequestMapping(value = "/hrp/budg/business/purchase/assexecute/querySourceName", method = RequestMethod.POST)
	@ResponseBody
	public String querySourceName(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgCashFlowBegin = executeService.querySourceName(mapVo);

		return budgCashFlowBegin;
		
	}
	
	/**
	 * @Description 
	 * 添加或修改数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/purchase/assexecute/addOrUpdateBudgAssPurExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAssPurExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String executeJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());   
	
			detailVo.put("hos_id", SessionManager.getHosId());   
	
			detailVo.put("copy_code", SessionManager.getCopyCode());   
		  
	  
			executeJson = executeService.addOrUpdate(detailVo);
		
		}
		
		return JSONObject.parseObject(executeJson);
	}	
	
	
	/**
	 * @Description 
	 * 保存 数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/purchase/assexecute/saveBudgAssPurExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgAssPurExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String budgCashFlowBeginJson = "";
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		try {
			
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("budg_year", ids[0]);
				mapVo.put("month", ids[1]);
				mapVo.put("source_id", ids[2]);
				mapVo.put("ass_type_id", ids[3]);
				mapVo.put("pur_amount", ids[4]);
				mapVo.put("remark", ids[5]);
				mapVo.put("rowNo", ids[6]);
				mapVo.put("flag", ids[7]);
				
				listVo.add(mapVo);
			}
			budgCashFlowBeginJson = executeService.save(listVo);
		} catch (Exception e) {
			budgCashFlowBeginJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgCashFlowBeginJson);
	}	
	
	/**
	 * @Description 删除数据 科室材料支出预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/purchase/assexecute/deleteBudgAssPurExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAssPurExecute(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("budg_year", ids[3]);
			mapVo.put("month", ids[4]);
			mapVo.put("source_id", ids[5]);
			mapVo.put("ass_type_id", ids[6]);

			listVo.add(mapVo);

		}

		String budgMedPurExeJson = executeService.deleteBatch(listVo);

		return JSONObject.parseObject(budgMedPurExeJson);

	}
	
	/**
	 * @Description 
	 * 下载导入模版 科室业务执行数据
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/business/purchase/assexecute/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\pur\\ass","固定资产采购预算执行模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 固定资产采购预算执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	
	@RequestMapping(value="/hrp/budg/business/purchase/assexecute/readBudgAssPurExecute",method = RequestMethod.POST)  
    public String readBudgAssPurExecute(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		
		Map<String,Object> paraMap = new  HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		paraMap.put("hos_id", SessionManager.getHosId());  						 
		paraMap.put("copy_code", SessionManager.getCopyCode());  
		
		List<Map<String,Object>> sourceInfo  = budgCommonInfoService.querySourceInfo(paraMap);
		
		List<Map<String,Object>> assTypeInfo  = budgCommonInfoService.queryAssType(paraMap);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> errMap = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
	    		    mapVo.put("hos_id", SessionManager.getHosId());  						 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])&&temp[1].equals(error[1])&&temp[2].equals(error[2])&&temp[3].equals(error[3])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					}
		
					if (ExcelReader.validExceLColunm(temp, 0)) {					
						errMap.put("budg_year",temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					} else {
						
						err_sb.append("年度为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp,1)) {					
						errMap.put("month",temp[1]);
						if(Arrays.asList(monthData).contains(temp[1])){
							mapVo.put("month", temp[1]);
						}else{
							
							err_sb.append("月份输入不合法(两位有效数字:01-12);");
							
						}
			    		
					} else {
						
						err_sb.append("月份为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						errMap.put("source_code",temp[2]);
						
						int flag = 0 ;
						
						for(Map<String,Object> item : sourceInfo){
							
							if(temp[2].equals(item.get("source_code"))){
								
								mapVo.put("source_id", temp[2]);
								
								flag = 1 ;
								break ;
								
							}
						}
						
						
						if(flag == 0){
							
							err_sb.append("资金来源编码不存在或已停用;");
						}
						
					} else {
						
						err_sb.append("资金来源编码为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp, 3)) {					
						errMap.put("source_name",temp[3]);
			    		mapVo.put("source_name", temp[3]);
					} else {
						
						err_sb.append("资金来源名称为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						int flag = 0 ;
						
						for(Map<String,Object> item : assTypeInfo){
							
							if(temp[4].equals(item.get("ass_type_code"))){
								
								mapVo.put("ass_type_code", temp[4]);
								mapVo.put("ass_type_id", item.get("ass_type_id"));
								
								flag = 1;
								
								break ;
								
							}
						}
						
						errMap.put("ass_type_code",temp[3]);
						
						if(flag == 0 ){
							err_sb.append("资产分类不存在或已停用;");
						}
			    		
					}else{
						
						err_sb.append("资产分类编码为空;");
					}
					
					if (ExcelReader.validExceLColunm(temp, 5)) {					
						errMap.put("ass_type_name",temp[5]);
			    		mapVo.put("ass_type_name", temp[5]);
					} else {
						
						err_sb.append("资产分类名称为空;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,6)) {
						errMap.put("pur_amount",Double.parseDouble(temp[6]));	
			    		mapVo.put("pur_amount", temp[6]);
						
					}else{
						err_sb.append("采购金额为空;");
					}
					if (ExcelReader.validExceLColunm(temp, 7)) {
						
						errMap.put("remark",temp[7]);	
						
			    		mapVo.put("remark", temp[7]);
						
					}else{
						mapVo.put("remark", "");
					}
					
					if(mapVo.get("month") != null && mapVo.get("source_id") != null && mapVo.get("ass_type_id") != null){
						int count = executeService.queryDataExist(mapVo);
						
						if (count > 0 ) {
							
							err_sb.append("数据已经存在！ ");
							
						}
					}
					
					
					if(err_sb.length() >  0){
						
						errMap.put("error_type",err_sb.toString()); 
						
						list_err.add(errMap);
						
					}else{
						addList.add(mapVo);
					}
			
			}
			if(list_err.size() == 0){
				
				if(addList.size()>0){
					
					String dataJson = executeService.addBatch(addList);
					
				}else{
					Map<String,Object> budgWorkHosExecute = new HashMap<String,Object>();
					
					budgWorkHosExecute.put("error_type","没有导入数据,导入失败!");
					
		            list_err.add(budgWorkHosExecute);
				}
			}
		} catch (DataAccessException e) {
			
			Map<String,Object> budgWorkHosExecute = new HashMap<String,Object>();
			
			budgWorkHosExecute.put("error_type","导入系统出错");
			
            list_err.add(budgWorkHosExecute);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	
	
}
