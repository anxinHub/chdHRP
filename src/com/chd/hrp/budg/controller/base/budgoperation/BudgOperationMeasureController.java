/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgoperation;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgOperationMeasure;
import com.chd.hrp.budg.service.base.budgoperation.BudgOperationMeasureService;
/**
 * 
 * @Description:
 * 运营尺度
 * @Table:
 * BUDG_OPERATION_MEASURE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgOperationMeasureController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgOperationMeasureController.class);
	
	//引入Service服务
	@Resource(name = "budgOperationMeasureService")
	private final BudgOperationMeasureService budgOperationMeasureService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/budgOperationMeasureMainPage", method = RequestMethod.GET)
	public String budgOperationMeasureMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgoperation/budgOperationMeasureMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/budgOperationMeasureAddPage", method = RequestMethod.GET)
	public String budgOperationMeasureAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgoperation/budgOperationMeasureAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/saveBudgOperationMeasure", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgOperationMeasure(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
				
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());   
			
			mapVo.put("hos_id", SessionManager.getHosId());   
	
			mapVo.put("copy_code", SessionManager.getCopyCode());  
			mapVo.put("measure_code", ids[0])   ;
			mapVo.put("measure_name", ids[1])   ;
			mapVo.put("is_stop", ids[2]);
			mapVo.put("rowNo", ids[3]);
			mapVo.put("flag", ids[4]);
			
			listVo.add(mapVo);
	    }
		
		String budgMedPurExeJson = null ;
		try {
			
			budgMedPurExeJson = budgOperationMeasureService.saveBudgOperationMeasure(listVo);
			
		} catch (Exception e) {
			
			budgMedPurExeJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgMedPurExeJson);
			
	}
	
	/**
	 * @Description 
	 * 添加数据 运营尺度
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/addBudgOperationMeasure", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgOperationMeasure(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String budgOperationMeasureJson = budgOperationMeasureService.add(mapVo);

		return JSONObject.parseObject(budgOperationMeasureJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 运营尺度
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/budgOperationMeasureUpdatePage", method = RequestMethod.GET)
	public String budgOperationMeasureUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgOperationMeasure budgOperationMeasure = new BudgOperationMeasure();
    
		budgOperationMeasure = budgOperationMeasureService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgOperationMeasure.getGroup_id());
		mode.addAttribute("hos_id", budgOperationMeasure.getHos_id());
		mode.addAttribute("copy_code", budgOperationMeasure.getCopy_code());
		mode.addAttribute("measure_code", budgOperationMeasure.getMeasure_code());
		mode.addAttribute("measure_name", budgOperationMeasure.getMeasure_name());
		mode.addAttribute("is_stop", budgOperationMeasure.getIs_stop());
		
		return "hrp/budg/base/budgoperation/budgOperationMeasureUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 运营尺度
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/updateBudgOperationMeasure", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgOperationMeasure(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {			
			Map<String, Object> mapVo=new HashMap<String, Object>();			
			String[] ids=id.split("@");
			//表的主键			
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])  ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("measure_code", ids[3])   ;
			mapVo.put("measure_name", ids[4])   ;
			mapVo.put("is_stop", ids[5]);
			listVo.add(mapVo);
		}     
		String budgOperationMeasureJson = budgOperationMeasureService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgOperationMeasureJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 运营尺度
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/addOrUpdateBudgOperationMeasure", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgOperationMeasure(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgOperationMeasureJson ="";
		

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
	  
		budgOperationMeasureJson = budgOperationMeasureService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgOperationMeasureJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 运营尺度
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/deleteBudgOperationMeasure", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgOperationMeasure(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("measure_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgOperationMeasureJson = budgOperationMeasureService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgOperationMeasureJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 运营尺度
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/queryBudgOperationMeasure", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgOperationMeasure(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgOperationMeasure = budgOperationMeasureService.query(getPage(mapVo));

		return JSONObject.parseObject(budgOperationMeasure);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 运营尺度
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/budgOperationMeasureImportPage", method = RequestMethod.GET)
	public String budgOperationMeasureImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgoperation/budgOperationMeasureImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 运营尺度
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgoperation/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","运营尺度维护模板.xlsx");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 运营尺度
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgoperation/readBudgOperationMeasureFiles",method = RequestMethod.POST)  
    public String readBudgOperationMeasureFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgOperationMeasure> list_err = new ArrayList<BudgOperationMeasure>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgOperationMeasure budgOperationMeasure = new BudgOperationMeasure();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for (int j = i + 1; j < list.size(); j++) {
						String error[] = list.get(j);
						if (temp[0].equals(error[0])) {
							err_sb.append("第" + i + "行数据与第 " + j + "行【指标编码】重复;");
						}
						if (temp[1].equals(error[1])) {
							err_sb.append("第" + i + "行数据与第 " + j + "行【指标名称】重复;");
						}
					}
					if (StringTool.isNotBlank(temp[0])) {
						
					budgOperationMeasure.setMeasure_code(temp[0]);
				
		    		mapVo.put("measure_code", temp[0]);
		    		Map<String,Object> map =budgOperationMeasureService.queryByCode(mapVo);
		    		if(map!=null){
						err_sb.append("运营尺度编码已被占用;");
					}
					} else {
						
						err_sb.append("运营尺度编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					budgOperationMeasure.setMeasure_name(temp[1]);
		    		mapVo.put("measure_name", temp[1]);
		    		int count  =budgOperationMeasureService.queryNameExist(mapVo);
		    		
		    		if(count > 0){
						err_sb.append("运营尺度名称已被占用;");
					}
					} else {
						
					err_sb.append("运营尺度名称为空  ");
					
					}
		       if (StringTool.isNotBlank(temp[2])) {
						
                        if("是".equals(temp[2])){
							
                        	budgOperationMeasure.setIs_stop_name(temp[2]);
							
                        	mapVo.put("is_stop","1");
							
						}else if("否".equals(temp[2])){
							
							budgOperationMeasure.setIs_stop_name(temp[2]);
							
							mapVo.put("is_stop","0");
							
						}
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					if (err_sb.toString().length() > 0) {

						budgOperationMeasure.setError_type(err_sb.toString());

						list_err.add(budgOperationMeasure);

					} else {

						addList.add(mapVo);

					}
			}
			if(list_err.size() == 0){
				String dataJson = budgOperationMeasureService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			
			BudgOperationMeasure data_exc = new BudgOperationMeasure();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 运营尺度
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgoperation/addBatchBudgOperationMeasure", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgOperationMeasure(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgOperationMeasure> list_err = new ArrayList<BudgOperationMeasure>();
		
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
			
			BudgOperationMeasure budgOperationMeasure = new BudgOperationMeasure();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("measure_code"))) {
						
					budgOperationMeasure.setMeasure_code((String)jsonObj.get("measure_code"));
		    		mapVo.put("measure_code", jsonObj.get("measure_code"));
		    		} else {
						
						err_sb.append("运营尺度编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("measure_name"))) {
						
					budgOperationMeasure.setMeasure_name((String)jsonObj.get("measure_name"));
		    		mapVo.put("measure_name", jsonObj.get("measure_name"));
		    		} else {
						
						err_sb.append("运营尺度名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					budgOperationMeasure.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					
				BudgOperationMeasure data_exc_extis = budgOperationMeasureService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgOperationMeasure.setError_type(err_sb.toString());
					
					list_err.add(budgOperationMeasure);
					
				} else {
			  
					String dataJson = budgOperationMeasureService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgOperationMeasure data_exc = new BudgOperationMeasure();
			
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

