/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgFunPara;
import com.chd.hrp.budg.service.common.BudgFunParaService;
import com.chd.hrp.budg.serviceImpl.common.BudgFunParaServiceImpl;
/**
 * 
 * @Description:
 * 函数参数表
 * @Table:
 * FUN_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgFunParaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgFunParaController.class);
	
	//引入Service服务
	@Resource(name = "funParaService")
	private final BudgFunParaService funParaService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/budgFunParaMainPage", method = RequestMethod.GET)
	public String funParaMainPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfunpara/budgFunParaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/budgFunParaAddPage", method = RequestMethod.GET)
	public String budgFunParaAddPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfunpara/budgFunParaAdd";

	}

	/**
	 * @Description 
	 * 添加数据 函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/addBudgFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String funParaJson = funParaService.add(mapVo);

		return JSONObject.parseObject(funParaJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/budgFunParaUpdatePage", method = RequestMethod.GET)
	public String funParaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgFunPara funPara = new BudgFunPara();
    
		funPara = funParaService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", funPara.getGroup_id());
		mode.addAttribute("hos_id", funPara.getHos_id());
		mode.addAttribute("copy_code", funPara.getCopy_code());
		mode.addAttribute("fun_code", funPara.getFun_code());
		mode.addAttribute("para_code", funPara.getPara_code());
		mode.addAttribute("para_name", funPara.getPara_name());
		mode.addAttribute("stack_seq_no", funPara.getStack_seq_no());
		mode.addAttribute("com_type_code", funPara.getCom_type_code());
		
		return "hrp/budg/common/budgfunpara/budgFunParaUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/updateBudgFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String funParaJson = funParaService.update(mapVo);
		
		return JSONObject.parseObject(funParaJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/addOrUpdateBudgFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String funParaJson ="";
		
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
	  
		funParaJson = funParaService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(funParaJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/deleteBudgFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgFunPara(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("fun_code", ids[3])   ;
				mapVo.put("para_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String funParaJson = funParaService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(funParaJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/queryBudgFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String funPara = funParaService.query(getPage(mapVo));

		return JSONObject.parseObject(funPara);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 函数参数表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/budgFunParaImportPage", method = RequestMethod.GET)
	public String funParaImportPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfunpara/budgFunParaImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 函数参数表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/common/budgfunpara/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","函数参数表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 函数参数表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/common/budgfunpara/readBudgFunParaFiles",method = RequestMethod.POST)  
    public String readBudgFunParaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgFunPara> list_err = new ArrayList<BudgFunPara>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgFunPara funPara = new BudgFunPara();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					funPara.setFun_code(temp[3]);
		    		mapVo.put("fun_code", temp[3]);
					
					} else {
						
						err_sb.append("函数编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					funPara.setPara_code(temp[4]);
		    		mapVo.put("para_code", temp[4]);
					
					} else {
						
						err_sb.append("参数代码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					funPara.setPara_name(temp[5]);
		    		mapVo.put("para_name", temp[5]);
					
					} else {
						
						err_sb.append("参数名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					funPara.setStack_seq_no(Integer.valueOf(temp[6]));
		    		mapVo.put("stack_seq_no", temp[6]);
					
					} else {
						
						err_sb.append("参数栈序列为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					funPara.setCom_type_code(temp[7]);
		    		mapVo.put("com_type_code", temp[7]);
					
					} else {
						
						err_sb.append("部件类型为空  ");
						
					}
					 
					
					BudgFunPara data_exc_extis = funParaService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					funPara.setError_type(err_sb.toString());
					
					list_err.add(funPara);
					
				} else {
			  
					String dataJson = funParaService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgFunPara data_exc = new BudgFunPara();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 函数参数表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/addBatchBudgFunPara", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgFunPara(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgFunPara> list_err = new ArrayList<BudgFunPara>();
		
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
			
			BudgFunPara funPara = new BudgFunPara();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("fun_code"))) {
						
					funPara.setFun_code((String)jsonObj.get("fun_code"));
		    		mapVo.put("fun_code", jsonObj.get("fun_code"));
		    		} else {
						
						err_sb.append("函数编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("para_code"))) {
						
					funPara.setPara_code((String)jsonObj.get("para_code"));
		    		mapVo.put("para_code", jsonObj.get("para_code"));
		    		} else {
						
						err_sb.append("参数代码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("para_name"))) {
						
					funPara.setPara_name((String)jsonObj.get("para_name"));
		    		mapVo.put("para_name", jsonObj.get("para_name"));
		    		} else {
						
						err_sb.append("参数名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stack_seq_no"))) {
						
					funPara.setStack_seq_no(Integer.valueOf((String)jsonObj.get("stack_seq_no")));
		    		mapVo.put("stack_seq_no", jsonObj.get("stack_seq_no"));
		    		} else {
						
						err_sb.append("参数栈序列为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("com_type_code"))) {
						
					funPara.setCom_type_code((String)jsonObj.get("com_type_code"));
		    		mapVo.put("com_type_code", jsonObj.get("com_type_code"));
		    		} else {
						
						err_sb.append("部件类型为空  ");
						
					}
					
					
					BudgFunPara data_exc_extis = funParaService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					funPara.setError_type(err_sb.toString());
					
					list_err.add(funPara);
					
				} else {
			  
					String dataJson = funParaService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgFunPara data_exc = new BudgFunPara();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	// 查询
	@RequestMapping(value = "/hrp/budg/common/budgfunpara/queryComTypePara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryComTypePara(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode()); 

		String comTypePara = funParaService.queryComTypePara(mapVo);

		return JSONObject.parseObject(comTypePara);

	}
    
}

