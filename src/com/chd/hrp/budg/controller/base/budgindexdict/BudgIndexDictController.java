/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgindexdict;
import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.budg.entity.BudgIndexDict;
import com.chd.hrp.budg.service.base.budgindexdict.BudgIndexDictService;
/**
 * 
 * @Description:
 * 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

 * @Table:
 * BUDG_INDEX_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgIndexDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgIndexDictController.class);
	
	//引入Service服务
	@Resource(name = "budgIndexDictService")
	private final BudgIndexDictService budgIndexDictService = null;
   
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception 
	 */                     
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/budgIndexDictMainPage", method = RequestMethod.GET)
	public String budgIndexDictMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgindexdict/budgIndexDictMain";
	}
	

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/                       
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/budgIndexDictAddPage", method = RequestMethod.GET)
	public String budgIndexDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgindexdict/budgIndexDictAdd";

	}
	
/**
	 * @Description 
	 * 对应科室维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/                          
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/budgIndexDeptSetPage", method = RequestMethod.GET)
	public String budgIndexDeptSetPage(Model mode) throws Exception {

		return "hrp/budg/base/budgindexdict/budgIndexDeptSet";

	}
	/**
	 * @Description 
	 * 查询数据对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/queryBudgIndexDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgIndexDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgIndexDict = budgIndexDictService.queryBudgIndexDeptSet(getPage(mapVo));

		return JSONObject.parseObject(budgIndexDict);
		
	}
	
	
	/**
	 * @Description 
	 * 添加数据 基本指标对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/addBudgIndexDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgIndexDeptSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("index_code", ids[0]);
			mapVo.put("dept_id", ids[1]);
			
			listVo.add(mapVo);
      
    }
		
       
		String budgBasicIndexDeptSetJson = budgIndexDictService.addBudgIndexDeptSet(listVo);

		return  JSONObject.parseObject(budgBasicIndexDeptSetJson);
		
	}
	
	/**
	 * @Description 
	 * 添加数据 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/addBudgIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgIndexDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(String.valueOf(mapVo.get("index_name").toString())));
		   mapVo.put("wbx_code", StringTool.toWuBi(String.valueOf(mapVo.get("index_name").toString())));
       
		String budgIndexDictJson = budgIndexDictService.add(mapVo);

		return JSONObject.parseObject(budgIndexDictJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/budgIndexDictUpdatePage", method = RequestMethod.GET)
	public String budgIndexDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		      
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
		
		Map<String,Object> budgIndexDict = new HashMap<String,Object>();
		budgIndexDict = budgIndexDictService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgIndexDict.get("group_id"));
		mode.addAttribute("hos_id", budgIndexDict.get("hos_id"));
		mode.addAttribute("copy_code", budgIndexDict.get("copy_code"));
		mode.addAttribute("index_code", budgIndexDict.get("index_code"));
		mode.addAttribute("index_name", budgIndexDict.get("index_name"));
		mode.addAttribute("unit", budgIndexDict.get("unit"));
		mode.addAttribute("data_precision", budgIndexDict.get("data_precision"));
		mode.addAttribute("data_nature", budgIndexDict.get("data_nature"));
		mode.addAttribute("is_carried", budgIndexDict.get("is_carried"));
		mode.addAttribute("is_stop", budgIndexDict.get("is_stop"));
		mode.addAttribute("spell_code", budgIndexDict.get("spell_code"));
		mode.addAttribute("wbx_code", budgIndexDict.get("wbx_code"));
		
		return "hrp/budg/base/budgindexdict/budgIndexDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/updateBudgIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgIndexDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("index_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("index_name").toString()));
	  
		String budgIndexDictJson = budgIndexDictService.update(mapVo);
		
		return JSONObject.parseObject(budgIndexDictJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/addOrUpdateBudgIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgIndexDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgIndexDictJson ="";
		

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
	  
		budgIndexDictJson = budgIndexDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgIndexDictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/deleteBudgIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgIndexDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("index_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgIndexDictJson = budgIndexDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgIndexDictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/queryBudgIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgIndexDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgIndexDict = budgIndexDictService.query(getPage(mapVo));

		return JSONObject.parseObject(budgIndexDict);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/budgIndexDictImportPage", method = RequestMethod.GET)
	public String budgIndexDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgindexdict/budgIndexDictImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgindexdict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","预算指标字典导入模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgindexdict/readBudgIndexDictFiles",method = RequestMethod.POST)  
    public String readBudgIndexDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgIndexDict> list_err = new ArrayList<BudgIndexDict>();
		
		List<Map<String,Object>> addList=new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		 
		try {
			
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgIndexDict budgIndexDict = new BudgIndexDict();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		    		
		    		 for(int j=i +1;j<list.size();j++){
    		        	 String error[]=list.get(j);
    		        	 if(temp[0].equals(error[0])){
    		        		 err_sb.append("第"+i+"行数据与第 "+j+"行【指标编码】重复;");
    		        	 }
    		        	 if(temp[1].equals(error[1])){
    		        		 err_sb.append("第"+i+"行数据与第 "+j+"行【指标名称】重复;");
    		        	 }
    		         }
		    		 
		    		 if (StringTool.isNotBlank(temp[0])) {
							
	    				budgIndexDict.setIndex_code(temp[0]);
			    		mapVo.put("index_code", temp[0]);
		    		
			    		Map<String,Object> map = budgIndexDictService.queryByCode(mapVo);
			    		
			    		if(map != null){
			    			err_sb.append("指标编码已被占用;");
			    		}
					
					} else {
						
						err_sb.append("指标编码为空;");
						
					}
		    		if (StringTool.isNotBlank(temp[1])) {
							
		    			 budgIndexDict.setIndex_name(temp[1]);
		    			 mapVo.put("index_name", temp[1]);
			    		
		    			 int map  = budgIndexDictService.queryNameExist(mapVo);
				    		
		    			 if(map > 0 ){
			    			err_sb.append("指标名称已被占用;");
			    		}
				
					} else {
						
						err_sb.append("指标名称为空;");
						
					}
					if (StringTool.isNotBlank(temp[2])) {
						
						budgIndexDict.setUnit(temp[2]);
			    		
						mapVo.put("unit", temp[2]);
						
					}else{
						
						mapVo.put("unit", "");
					}
					if (StringTool.isNotBlank(temp[3])) {
						
						int isInteger=1;
						try{
							budgIndexDict.setData_precision(Integer.valueOf(temp[3]));
				    		mapVo.put("data_precision", temp[3]);
							isInteger=Integer.parseInt(temp[3])-1;
							if(isInteger<0){
								err_sb.append("数据精度【"+temp[3]+"】不是正整数！");
							}
						}catch(Exception e){
							err_sb.append("数据精度【"+temp[3]+"】不是正整数！");
						}
					
					} else {
						
						err_sb.append("数据精度为空  ");						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
						if("可累加".equals(temp[4])){
							
							budgIndexDict.setData_nature(temp[4]);
							
							mapVo.put("data_nature","01");
							
						}else if("状态值".equals(temp[4])){
							
							budgIndexDict.setData_nature(temp[4]);
							
							mapVo.put("data_nature","02");
							
						}else{
							
							budgIndexDict.setData_nature(temp[4]);
							
							err_sb.append("数据性质(只能填写'可累加'或'状态值' )");
							
						}
					
					} else {
						
						err_sb.append("数据性质为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
 
						if("是".equals(temp[5])){
							
							budgIndexDict.setIs_carried_name(temp[5]); 
							
							mapVo.put("is_carried","1");
							
						}else if("否".equals(temp[5])){
							
							budgIndexDict.setIs_carried_name(temp[5]); 
							
							mapVo.put("is_carried","0");
							
						}else{
							err_sb.append("是否结转(只能填写'是'或'否' )");
						}
					
					} else {
						
						err_sb.append("是否结转为空 ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
                        if("是".equals(temp[6])){
							
                        	budgIndexDict.setIs_stop_name(temp[6]);
							
                        	mapVo.put("is_stop","1");
							
						}else if("否".equals(temp[6])){
							
							budgIndexDict.setIs_stop_name(temp[6]);
							
							mapVo.put("is_stop","0");
							
						}else{
							err_sb.append("是否停用(只能填写'是'或'否' )");
						}
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					 
					if (err_sb.toString().length() > 0) {
						
						budgIndexDict.setError_type(err_sb.toString());
						
						list_err.add(budgIndexDict);
						
					} else {
					  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(String.valueOf(mapVo.get("index_name"))));
					  mapVo.put("wbx_code", StringTool.toWuBi(String.valueOf(mapVo.get("index_name"))));
					  
					  addList.add(mapVo);
					}
					
					 
			}
			if(list_err.size() == 0){
				String dataJson = budgIndexDictService.addBatch(addList);
			}
				
		} catch (DataAccessException e) {
			
			BudgIndexDict data_exc = new BudgIndexDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgindexdict/addBatchBudgIndexDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgIndexDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgIndexDict> list_err = new ArrayList<BudgIndexDict>();
		
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
			
			BudgIndexDict budgIndexDict = new BudgIndexDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgIndexDict.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_name"))) {
						
					budgIndexDict.setIndex_name((String)jsonObj.get("index_name"));
		    		mapVo.put("index_name", jsonObj.get("index_name"));
		    		} else {
						
						err_sb.append("指标名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("unit"))) {
						
					budgIndexDict.setUnit((String)jsonObj.get("unit"));
		    		mapVo.put("unit", jsonObj.get("unit"));
		    		} else {
						
						err_sb.append("代码组编码:unittype,为用户自定义项目为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("data_precision"))) {
						
					budgIndexDict.setData_precision(Integer.valueOf((String)jsonObj.get("data_precision")));
		    		mapVo.put("data_precision", jsonObj.get("data_precision"));
		    		} else {
						
						err_sb.append("数据精度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("data_nature"))) {
						
					budgIndexDict.setData_nature((String)jsonObj.get("data_nature"));
		    		mapVo.put("data_nature", jsonObj.get("data_nature"));
		    		} else {
						
						err_sb.append("数据性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_carried"))) {
						
					budgIndexDict.setIs_carried(Integer.valueOf((String)jsonObj.get("is_carried")));
		    		mapVo.put("is_carried", jsonObj.get("is_carried"));
		    		} else {
						
						err_sb.append("是否结转为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					budgIndexDict.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					budgIndexDict.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					budgIndexDict.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					
					Map<String,Object> data_exc_extis = budgIndexDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgIndexDict.setError_type(err_sb.toString());
					
					list_err.add(budgIndexDict);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("index_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("index_name").toString()));
			  
					String dataJson = budgIndexDictService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgIndexDict data_exc = new BudgIndexDict();
			
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

