/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.basicfina;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgBasicFinaAid;
import com.chd.hrp.budg.service.budgincome.basicfina.BudgBasicFinaAidService;
/**
 * 
 * @Description:
 * 财政基本补助收入预算
 * @Table:
 * BUDG_BASIC_FINA_AID
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgBaicFinaAidController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgBaicFinaAidController.class);
	
	//引入Service服务
	@Resource(name = "budgBasicFinaAidService")
	private final BudgBasicFinaAidService budgBasicFinaAidService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/budgBasicFinaAidMainPage", method = RequestMethod.GET)
	public String budgBasicFinaAidMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/basicfina/aid/budgBasicFinaAidMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/budgBasicFinaAidAddPage", method = RequestMethod.GET)
	public String budgBasicFinaAidAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/basicfina/aid/budgBasicFinaAidAdd";

	}
	
	
	/**
	 * @Description 
	 * 添加数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/addBudgBasicFinaAid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgBasicFinaAid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgBasicFinaAidJson = budgBasicFinaAidService.add(mapVo);

		return JSONObject.parseObject(budgBasicFinaAidJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/budgBasicFinaAidUpdatePage", method = RequestMethod.GET)
	public String budgBasicFinaAidUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		Map<String,Object> budgBasicFinaAid = new HashMap<String,Object>();
    
		budgBasicFinaAid = budgBasicFinaAidService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgBasicFinaAid.get("group_id"));
		mode.addAttribute("hos_id", budgBasicFinaAid.get("hos_id"));
		mode.addAttribute("copy_code", budgBasicFinaAid.get("copy_code"));
		mode.addAttribute("budg_year", budgBasicFinaAid.get("budg_year"));
		mode.addAttribute("subj_code", budgBasicFinaAid.get("subj_code"));
		mode.addAttribute("budg_value", budgBasicFinaAid.get("budg_value"));
		mode.addAttribute("remark", budgBasicFinaAid.get("remark"));
		
		return "hrp/budg/budgincome/basicfina/aid/budgBasicFinaAidUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/updateBudgBasicFinaAid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgBasicFinaAid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
		String budgBasicFinaAidJson = budgBasicFinaAidService.update(mapVo);
		
		return JSONObject.parseObject(budgBasicFinaAidJson);
	}
	
	/**
	 * ***************暂时不用此方法*******************
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/addOrUpdateBudgBasicFinaAid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgBasicFinaAid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgBasicFinaAidJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());   
	
			detailVo.put("hos_id", SessionManager.getHosId());   
	
			detailVo.put("copy_code", SessionManager.getCopyCode());   
		  
	  
			budgBasicFinaAidJson = budgBasicFinaAidService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgBasicFinaAidJson);
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/saveBudgBasicFinaAid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgBasicFinaAid(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
				
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());   
			
			mapVo.put("hos_id", SessionManager.getHosId());   
	
			mapVo.put("copy_code", SessionManager.getCopyCode());  
			mapVo.put("budg_year", ids[0]);
			mapVo.put("subj_code", ids[1]);
			mapVo.put("budg_value", ids[2]);
			mapVo.put("remark", ids[3]);
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			mapVo.put("checker", "");
			
			mapVo.put("check_data", "");
			
			mapVo.put("bc_state", "01");
			
			listVo.add(mapVo);
	    }
		
		String budgBasicFinaAidJson = null ;
		try {
			
			budgBasicFinaAidJson = budgBasicFinaAidService.saveBudgBasicFinaAid(listVo);
			
		} catch (Exception e) {
			
			budgBasicFinaAidJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgBasicFinaAidJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/deleteBudgBasicFinaAid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBasicFinaAid(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3]);
				mapVo.put("subj_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgBasicFinaAidJson = budgBasicFinaAidService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgBasicFinaAidJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/queryBudgBasicFinaAid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBasicFinaAid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgBasicFinaAid = budgBasicFinaAidService.query(getPage(mapVo));

		return JSONObject.parseObject(budgBasicFinaAid);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/budgBasicFinaAidImportPage", method = RequestMethod.GET)
	public String budgBasicFinaAidImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/basicfina/aid/budgBasicFinaAidImport";

	}
	/**
	 * @Description 
	 * 下载  财政基本补助收入预算导入模版        
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/basicfina/aid/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","财政基本补助收入预算字典模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入  财政基本补助收入预算      
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/basicfina/aid/readBudgBasicFinaAidFiles",method = RequestMethod.POST)  
    public String readBudgBasicFinaAidFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgBasicFinaAid> list_err = new ArrayList<BudgBasicFinaAid>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgBasicFinaAid budgBasicFinaAid = new BudgBasicFinaAid();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【预算科目编码】重复;");
						}
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【预算科目名称】重复;");
						}	
					}
					if (StringTool.isNotBlank(temp[0])) {
						
						budgBasicFinaAid.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgBasicFinaAid.setSubj_code(temp[2]);
						
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgBasicFinaAidService.querySubjCodeExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("预算科目编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("预算科目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgBasicFinaAid.setBudg_value(Double.valueOf(temp[3]));
			    		mapVo.put("budg_value", temp[3]);
					
					} else {
						
						err_sb.append("金额为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgBasicFinaAid.setRemark(temp[4]);
						mapVo.put("remark", temp[4]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
				if (err_sb.toString().length() > 0) {
					
					budgBasicFinaAid.setError_type(err_sb.toString());
					
					list_err.add(budgBasicFinaAid);
					
				} else {
					
					mapVo.put("checker", "") ;
					
					mapVo.put("check_data", "");
					
					mapVo.put("bc_state", "01");
					
					addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgBasicFinaAidService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgBasicFinaAid data_exc = new BudgBasicFinaAid();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
    
	
	/**
	 * 收入预算科目下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/queryBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String code = budgBasicFinaAidService.queryBudgIncomeSubj(mapVo);
		return code;

	}
	
	/**
	 * 审核、销审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/auditOrUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditOrUnAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", ids[0]);
			mapVo.put("subj_code", ids[1]);
			mapVo.put("bc_state",ids[2]);
			//审核
			if("02".equals(ids[2])){
				
				mapVo.put("checker",SessionManager.getUserId());
				mapVo.put("check_data",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
				
			}else{//消审
				
				mapVo.put("checker","");
				mapVo.put("check_data","");
				
			}
			
			listVo.add(mapVo);			
		}
		String matOrderMain = budgBasicFinaAidService.auditOrUnAudit(listVo);		
		return JSONObject.parseObject(matOrderMain);
	}
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/aid/queryBudgBasicFinaAidState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgBasicFinaAidState(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			List<String> list= budgBasicFinaAidService.queryBudgBasicFinaAidState(getPage(mapVo));
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"state\":\"true\"}");
				
			}else{
				
				String  str = "" ;
				for(String item : list){
					
					str += item +"," ;
				}
				
				return JSONObject.parseObject("{\"subj_code\":\""+str+"\",\"state\":\"false\"}");
			}
			
	}
}

