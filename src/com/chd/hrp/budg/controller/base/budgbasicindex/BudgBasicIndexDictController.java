/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgbasicindex;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgBasicIndexDict;
import com.chd.hrp.budg.service.base.budgbasicindex.BudgBasicIndexDictService;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
/**
 * 
 * @Description:
 * 基本指标
 * @Table:
 * BUDG_BASIC_INDEX_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgBasicIndexDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgBasicIndexDictController.class);
	
	//引入Service服务
	@Resource(name = "budgBasicIndexDictService")
	private final BudgBasicIndexDictService budgBasicIndexDictService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDictMainPage", method = RequestMethod.GET)
	public String budgBasicIndexDictMainPage(Model mode) throws Exception {

		return "/hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDictMain";

	}

	/**
	 * @Description 
	 * 对应科室维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDictSet", method = RequestMethod.GET)
	public String budgBasicIndexDictSet(Model mode) throws Exception {

		return "/hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDeptSet";

	}
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDictAddPage", method = RequestMethod.GET)
	public String budgBasicIndexDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDictAdd";

	}
	/**
	 * @Description 
	 * 维护科室页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/saveBudgBasicIndexDeptSetAdd", method = RequestMethod.GET)
	public String saveBudgBasicIndexDeptSetAdd(Model mode) throws Exception {

		return "hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDeptSetAdd";

	}
	/**
	 * @Description 
	 * 添加数据  基本指标
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/addBudgBasicIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgBasicIndexDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("index_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("index_name").toString()));
       
		String budgBasicIndexDictJson = budgBasicIndexDictService.add(mapVo);

		return JSONObject.parseObject(budgBasicIndexDictJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDictUpdatePage", method = RequestMethod.GET)
	public String budgBasicIndexDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgBasicIndexDict budgBasicIndexDict =  budgBasicIndexDictService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgBasicIndexDict.getGroup_id());
		mode.addAttribute("hos_id", budgBasicIndexDict.getHos_id());
		mode.addAttribute("copy_code", budgBasicIndexDict.getCopy_code());
		mode.addAttribute("index_code", budgBasicIndexDict.getIndex_code());
		mode.addAttribute("index_name", budgBasicIndexDict.getIndex_name());
		mode.addAttribute("index_nature", budgBasicIndexDict.getIndex_nature());
		mode.addAttribute("index_describe", budgBasicIndexDict.getIndex_describe());
		mode.addAttribute("unit", budgBasicIndexDict.getUnit());
		mode.addAttribute("data_precision", budgBasicIndexDict.getData_precision());
		mode.addAttribute("is_stop", budgBasicIndexDict.getIs_stop());
		mode.addAttribute("spell_code", budgBasicIndexDict.getSpell_code());
		mode.addAttribute("wbx_code", budgBasicIndexDict.getWbx_code());
		
		return "hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/updateBudgBasicIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgBasicIndexDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
	  
		String budgBasicIndexDictJson = budgBasicIndexDictService.update(mapVo);
		
		return JSONObject.parseObject(budgBasicIndexDictJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgbasicindexdict/addOrUpdateBudgBasicIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgBasicIndexDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgBasicIndexDictJson ="";
		

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
	  
		budgBasicIndexDictJson = budgBasicIndexDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgBasicIndexDictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/deleteBudgBasicIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBasicIndexDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgBasicIndexDictJson = budgBasicIndexDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgBasicIndexDictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/queryBudgBasicIndexDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBasicIndexDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgBasicIndexDict = budgBasicIndexDictService.query(getPage(mapVo));

		return JSONObject.parseObject(budgBasicIndexDict);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDictImportPage", method = RequestMethod.GET)
	public String budgBasicIndexDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgbasicindex/basicindexdict/budgBasicIndexDictImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgbasicindex/basicindexdict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","基本指标模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/

	@RequestMapping(value="/hrp/budg/base/budgbasicindex/basicindexdict/readBudgBasicIndexDictFiles",method = RequestMethod.POST)  
    public String readBudgBasicIndexDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgBasicIndexDict> list_err = new ArrayList<BudgBasicIndexDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] natureList = {"01","02"};
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgBasicIndexDict budgBasicIndexDict = new BudgBasicIndexDict();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【指标编码】重复;");
						}
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【指标名称】重复;");
						}	
					}        
					 
					if (StringTool.isNotBlank(temp[0])) {
						
						budgBasicIndexDict.setIndex_code(temp[0]);
						mapVo.put("index_code", temp[0]);
						BudgBasicIndexDict  map = budgBasicIndexDictService.queryByCode(mapVo);
						if(map != null ){
							err_sb.append("指标编码已被占用;");					
						}
					
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						budgBasicIndexDict.setIndex_name(temp[1]);
						
						mapVo.put("index_name", temp[1]);	
						
						int count = budgBasicIndexDictService.queryNameExist(mapVo);
						
			    		if(count>0){
			    			err_sb.append("指标名称已被占用;");
			    		}
					} else {
						
						err_sb.append("指标名称为空  ");						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						budgBasicIndexDict.setIndex_nature(temp[2]);
						
						mapVo.put("index_nature", temp[2]);
						
						if(!Arrays.asList(natureList).contains(temp[2])){
							
							err_sb.append("指标性质不符合规则(01:医院，02:科室);");												
						}
			    		
					} else {						
						err_sb.append("指标性质为空  ");						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 3)) {
						int rowIndex=1;
						try{
							budgBasicIndexDict.setData_precision(Integer.valueOf(temp[3]));
							mapVo.put("data_precision", temp[3]);
							rowIndex=Integer.parseInt(temp[3])-1;
							if(rowIndex<0){
								err_sb.append("数据精度【"+temp[3]+"】不是正整数！");
							}
						}catch(Exception e){
							err_sb.append("数据精度【"+temp[3]+"】不是正整数！");
						}			
						
					} else {						
						err_sb.append("数据精度为空  ");						
					}
					
					if (ExcelReader.validExceLColunm(temp, 4)) {
						
						budgBasicIndexDict.setIs_stop(Integer.valueOf(temp[4]));
						
			    		mapVo.put("is_stop", temp[4]);
			    		
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					if (ExcelReader.validExceLColunm(temp, 5)) {
						
						budgBasicIndexDict.setUnit(temp[5]);
						
			    		mapVo.put("unit", temp[5]);
					
					} else {						
						mapVo.put("unit", "");					
					}
					if (ExcelReader.validExceLColunm(temp, 6)) {
						
						budgBasicIndexDict.setIndex_describe(temp[6]);
						
			    		mapVo.put("index_describe", temp[6]);
						
					} else {
						
						mapVo.put("index_describe", "");						
					}
					
					
				BudgBasicIndexDict data_exc_extis = budgBasicIndexDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgBasicIndexDict.setError_type(err_sb.toString());
					
					list_err.add(budgBasicIndexDict);
					
				} else {
				  
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("index_name").toString()));
				 
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("index_name").toString()));			  
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				String dataJson = budgBasicIndexDictService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgBasicIndexDict data_exc = new BudgBasicIndexDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		if(list_err.size() == 0){
			String dataJson = budgBasicIndexDictService.addBatch(addList);
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 是否停用（IS_STOP):0否，1是
指标性质（INDEX_NATURE）取自系统字典表：01医院02科室

	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgbasicindexdict/addBatchBudgBasicIndexDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgBasicIndexDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgBasicIndexDict> list_err = new ArrayList<BudgBasicIndexDict>();
		
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
			
			BudgBasicIndexDict budgBasicIndexDict = new BudgBasicIndexDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgBasicIndexDict.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_name"))) {
						
					budgBasicIndexDict.setIndex_name((String)jsonObj.get("index_name"));
		    		mapVo.put("index_name", jsonObj.get("index_name"));
		    		} else {
						
						err_sb.append("指标名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_nature"))) {
						
					budgBasicIndexDict.setIndex_nature((String)jsonObj.get("index_nature"));
		    		mapVo.put("index_nature", jsonObj.get("index_nature"));
		    		} else {
						
						err_sb.append("指标性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_describe"))) {
						
					budgBasicIndexDict.setIndex_describe((String)jsonObj.get("index_describe"));
		    		mapVo.put("index_describe", jsonObj.get("index_describe"));
		    		} else {
						
						err_sb.append("指标描述为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("unit"))) {
						
					budgBasicIndexDict.setUnit((String)jsonObj.get("unit"));
		    		mapVo.put("unit", jsonObj.get("unit"));
		    		} else {
						
						err_sb.append("代码组编码:unittype,为用户自定义项目为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("data_precision"))) {
						
					budgBasicIndexDict.setData_precision(Integer.valueOf((String)jsonObj.get("data_precision")));
		    		mapVo.put("data_precision", jsonObj.get("data_precision"));
		    		} else {
						
						err_sb.append("数据精度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					budgBasicIndexDict.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					budgBasicIndexDict.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					budgBasicIndexDict.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					
				BudgBasicIndexDict data_exc_extis = budgBasicIndexDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgBasicIndexDict.setError_type(err_sb.toString());
					
					list_err.add(budgBasicIndexDict);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = budgBasicIndexDictService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgBasicIndexDict data_exc = new BudgBasicIndexDict();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	/**
	 * @Description 
	 * 查询数据 基本指标对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/queryBudgBasicIndexDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBasicIndexDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgBasicIndexDeptSet = budgBasicIndexDictService.queryBudgBasicIndexDeptSet(getPage(mapVo));

		return JSONObject.parseObject(budgBasicIndexDeptSet);
		
	}
	/**
	 * @Description 
	 * 添加数据 基本指标对应科室维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexdict/addBudgBasicIndexDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgBasicIndexDeptSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
		String budgBasicIndexDeptSetJson = "";
		try {
			
			budgBasicIndexDeptSetJson = budgBasicIndexDictService.addBudgBasicIndexDeptSet(listVo);
			
		} catch (Exception e) {
			
			budgBasicIndexDeptSetJson = e.getMessage();
			
		}

		return JSONObject.parseObject(budgBasicIndexDeptSetJson);
		
	}
	
}

