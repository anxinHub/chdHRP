
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.controller.base.budgybinfor;
import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgYbPayModeSet;
import com.chd.hrp.budg.service.base.budgybinfor.BudgPayModeService;
/**
 * 
 * @Description:
 * 付费机制
 * @Table:
 * HEALTH_INSURANCE_PAY_MODE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class BudgPayModeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgPayModeController.class);
	
	//引入Service服务
	@Resource(name = "budgPayModeService")
	private final BudgPayModeService budgPayModeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgpaymode/budgPayModeMainPage", method = RequestMethod.GET)
	public String budgPayModeMainPage(Model mode) throws Exception {
		mode.addAttribute("budg_year", SessionManager.getAcctYear());
		return "hrp/budg/base/budgybinfor/budgpaymode/budgPayModeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgpaymode/budgPayModeAddPage", method = RequestMethod.GET)
	public String budgPayModeAddPage(Model mode) throws Exception {
		return "hrp/budg/base/budgybinfor/budgpaymode/budgPayModeAdd";
	}

	/**
	 * @Description 
	 * 添加数据 付费机制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgpaymode/addBudgPayMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgPayMode(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("insurance_code", ids[3]);
			mapVo.put("pay_mode_code", ids[4]);
			listVo.add(mapVo); 
	    }
		
		String BudgPayModeJson = budgPayModeService.addBatch(listVo);

		return JSONObject.parseObject(BudgPayModeJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 付费机制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgpaymode/budgPayModeUpdatePage", method = RequestMethod.GET)
	public String budgPayModeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		
		if(mapVo.get("budg_year") == null){	
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
    	Map<String,Object> budgPayMode = new HashMap<String,Object>();
    
    	budgPayMode = budgPayModeService.queryByCode(mapVo);
		
		mode.addAttribute("budgPayMode", budgPayMode);
		
		return "hrp/budg/base/budgybinfor/budgpaymode/budgPayModeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 付费机制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgpaymode/updateBudgPayMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgPayMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		
		String budgPayMode = budgPayModeService.update(mapVo);
		
		return JSONObject.parseObject(budgPayMode);
	}
	
	/**
	 * @Description 
	 * 删除数据 付费机制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgpaymode/deleteBudgPayMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgPayMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
	    
		String budgPayMode = budgPayModeService.delete(mapVo);
			
	  return JSONObject.parseObject(budgPayMode);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 付费机制
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgpaymode/queryBudgPayMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPayMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		
		if(mapVo.get("budg_year") == null){	
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String BudgPayMode = budgPayModeService.query(getPage(mapVo));
		return JSONObject.parseObject(BudgPayMode);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 付费机制
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgpaymode/budgPayModeImportPage", method = RequestMethod.GET)
	public String budgPayModeImportPage(Model mode) throws Exception {
		return "hrp/budg/base/budgybinfor/budgpaymode/budgPayModeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 付费机制
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/budg/base/budgybinfor/budgpaymode/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","医保付费机制设置模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 付费机制设置
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgybinfor/budgpaymode/readBudgPayModeSetFiles",method = RequestMethod.POST)  
    public String readBudgPayModeSetFiles(Plupload plupload,HttpServletRequest request,HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgYbPayModeSet> list_err = new ArrayList<BudgYbPayModeSet>();		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);	
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {				
				StringBuffer err_sb = new StringBuffer();				
				BudgYbPayModeSet BudgPayMode = new BudgYbPayModeSet();				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();				
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());	
				mapVo.put("copy_code", SessionManager.getCopyCode());						
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
				
				if (StringTool.isNotBlank(temp[0])) {						
					BudgPayMode.setInsurance_code(temp[0]);			
					mapVo.put("insurance_code", temp[0]);
					int count  = budgPayModeService.queryInsCodeExist(mapVo);
					if(count == 0){
						err_sb.append("医保类型编码不存在或已停用;");
					}
				} else {						
					err_sb.append("医保类型编码为空;");						
				}
				
				if (StringTool.isNotBlank(temp[1])) {						
					BudgPayMode.setPay_mode_code(temp[1]);			
					mapVo.put("pay_mode_code", temp[1]);
					int count  = budgPayModeService.queryPayModCodeExist(mapVo);
					if(count == 0){
						err_sb.append("付费机制编码不存在或已停用;");
					}
				} else {						
					err_sb.append("付费机制编码为空;");						
				}
				BudgYbPayModeSet data_exc_extis = budgPayModeService.queryByCode(mapVo);				
				if (data_exc_extis != null) {					
					err_sb.append("数据已经存在!");					
				}
				if (err_sb.toString().length() > 0) {					
					BudgPayMode.setError_type(err_sb.toString());					
					list_err.add(BudgPayMode);					
				} else {			  
						addList.add(mapVo);			
				}				
			}
			if(list_err.size() == 0){
				String dataJson = budgPayModeService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {			
			BudgYbPayModeSet data_exc = new BudgYbPayModeSet();			
			data_exc.setError_type("导入系统出错");			
			list_err.add(data_exc);			
		}		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));		
		return null;		
    }
	/**
	 * @Description 
	 * 生成 付费机制
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgpaymode/resetBudgPayMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetBudgPayMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){			
			mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){			
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());        
		}
		
		if(mapVo.get("budg_year") == null){			
			mapVo.put("budg_year", SessionManager.getAcctYear());      
		}
        
		String BudgPayModeJson = budgPayModeService.resetBudgePayMode(mapVo);
		return JSONObject.parseObject(BudgPayModeJson);
		
	}
    
}

