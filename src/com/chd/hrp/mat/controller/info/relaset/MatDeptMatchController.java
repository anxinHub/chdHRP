/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.relaset;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatDeptMatch;
import com.chd.hrp.mat.service.info.relaset.MatDeptMatchService;
import com.chd.hrp.mat.serviceImpl.info.relaset.MatDeptMatchServiceImpl;
/**
 * 
 * @Description:
 * 04114 物资科室配套表
 * @Table:
 * MAT_DEPT_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatDeptMatchController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatDeptMatchController.class);
	
	//引入Service服务
	@Resource(name = "matDeptMatchService")
	private final MatDeptMatchService matDeptMatchService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/matDeptMatchMainPage", method = RequestMethod.GET)
	public String matDeptMatchMainPage(Model mode) throws Exception {

		return "hrp/mat/info/relaset/deptmatch/matDeptMatchMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/matDeptMatchAddPage", method = RequestMethod.GET)
	public String matDeptMatchAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p04032", MyConfig.getSysPara("04032"));

		return "hrp/mat/info/relaset/deptmatch/matDeptMatchAdd";

	}
	
	/**
	 * @Description 选择材料页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/relaset/matDeptMatchChoiceInvPage", method = RequestMethod.GET)
	public String matDeptMatchChoiceInvPage(Model mode) throws Exception {
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/info/relaset/deptmatch/matDeptMatchChoiceInv";
	}
	
	//组装科室需求计划
	@RequestMapping(value = "/hrp/mat/info/relaset/queryDeptInvData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptInvData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
					
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
					
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
					
		String detailData = matDeptMatchService.queryDeptInvData(mapVo);
					
		return JSONObject.parseObject(detailData);
					
	}

	/**
	 * @Description 
	 * 添加数据 04114 物资科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/addMatDeptMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatDeptMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if("系统生成".equals(mapVo.get("dept_match_code"))){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String date = df.format(new Date());
			String year = date.split("-")[0];
			String month = date.split("-")[1];
			mapVo.put("table_code", "MAT_DEPT_MATCH");
			mapVo.put("prefixe", "");
			mapVo.put("year", year);
			mapVo.put("month", month);
			String dept_match_code= matDeptMatchService.getMatNextNo(mapVo);
			mapVo.put("dept_match_code", dept_match_code);
		}
		
		String matJson;
		try {
			matJson = matDeptMatchService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 04114 物资科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/matDeptMatchUpdatePage", method = RequestMethod.GET)
	public String matDeptMatchUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("store_id") ==null || "null".equals(mapVo.get("store_id").toString())||"".equals(mapVo.get("store_id"))){
			mapVo.put("store_id", "");
		}
		
		
		MatDeptMatch matDeptMatch = new MatDeptMatch();
		matDeptMatch = matDeptMatchService.queryByCode(mapVo);
		mode.addAttribute("dept_match_id", matDeptMatch.getDept_match_id());
		mode.addAttribute("dept_match_code", matDeptMatch.getDept_match_code());
		mode.addAttribute("dept_match_name", matDeptMatch.getDept_match_name());
		
		if(!"".equals(mapVo.get("store_id")) && mapVo.get("store_id")!=null){
			mode.addAttribute("store_id", matDeptMatch.getStore_id());
			mode.addAttribute("store_no", matDeptMatch.getStore_no());
			mode.addAttribute("store_code", matDeptMatch.getStore_code());
			mode.addAttribute("store_name", matDeptMatch.getStore_name());
		}else{
			mode.addAttribute("store_id","");
			mode.addAttribute("store_no",  "");
			mode.addAttribute("store_code","");
			mode.addAttribute("store_name",  "");
		}
		
		mode.addAttribute("dept_id", matDeptMatch.getDept_id());
		mode.addAttribute("dept_no", matDeptMatch.getDept_no());
		mode.addAttribute("dept_code", matDeptMatch.getDept_code());
		mode.addAttribute("dept_name", matDeptMatch.getDept_name());
		
		mode.addAttribute("p04032", MyConfig.getSysPara("04032"));
		
		return "hrp/mat/info/relaset/deptmatch/matDeptMatchUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04114 物资科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/updateMatDeptMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDeptMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String matJson;
		try {
			matJson = matDeptMatchService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	
	@RequestMapping(value = "/hrp/mat/info/relaset/deleteMatDeptMatchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDeptMatchDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("dept_match_id", ids[0]);
				mapVo.put("inv_id", ids[1]);
				listVo.add(mapVo);
	      
			}
		String matDeptMatchJson = matDeptMatchService.deleteBatchDetail(listVo);
	  return JSONObject.parseObject(matDeptMatchJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据 04114 物资科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/deleteMatDeptMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDeptMatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("dept_match_id", ids[3]);
				listVo.add(mapVo);
	      
			}
			
		String matJson;
		try {
			matJson = matDeptMatchService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
	  return JSONObject.parseObject(matJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 04114 物资科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/queryMatDeptMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("user_id") == null){
			
			mapVo.put("user_id", SessionManager.getUserId());
			
		}
		
		String matDeptMatch = matDeptMatchService.query(getPage(mapVo));

		return JSONObject.parseObject(matDeptMatch);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04114 物资科室配套表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/matDeptMatchImportPage", method = RequestMethod.GET)
	public String matDeptMatchImportPage(Model mode) throws Exception {

		return "hrp/mat/info/relaset/deptmatch/matDeptMatchImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04114 物资科室配套表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/mat/info/relaset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04114 物资科室配套表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04114 物资科室配套表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/relaset/readMatDeptMatchFiles",method = RequestMethod.POST)  
    public String readMatDeptMatchFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatDeptMatch> list_err = new ArrayList<MatDeptMatch>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatDeptMatch matDeptMatch = new MatDeptMatch();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matDeptMatch.setDept_match_id(Long.valueOf(temp[3]));
		    		mapVo.put("store_match_id", temp[3]);
					
					} else {
						
						err_sb.append("配套表ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
		    		mapVo.put("store_match_name", temp[4]);
					
					} else {
						
						err_sb.append("配套表名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matDeptMatch.setStore_id(Long.valueOf(temp[5]));
		    		mapVo.put("store_id", temp[5]);
					
					} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					matDeptMatch.setDept_id(Long.valueOf(temp[6]));
		    		mapVo.put("dept_id", temp[6]);
					
					} else {
						
						err_sb.append("科室ID为空  ");
						
					}
					 
					
				MatDeptMatch data_exc_extis = matDeptMatchService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matDeptMatch.setError_type(err_sb.toString());
					
					list_err.add(matDeptMatch);
					
				} else {
			  
					String dataJson = matDeptMatchService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatDeptMatch data_exc = new MatDeptMatch();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04114 物资科室配套表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/addBatchMatDeptMatch", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatDeptMatch(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatDeptMatch> list_err = new ArrayList<MatDeptMatch>();
		
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
					
					MatDeptMatch matDeptMatch = new MatDeptMatch();
					
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("store_match_id"))) {
								
						matDeptMatch.setDept_match_id(Long.valueOf((String)jsonObj.get("store_match_id")));
				    		
						mapVo.put("store_match_id", jsonObj.get("store_match_id"));
				    		
					} else {
								
						err_sb.append("配套表ID为空  ");
								
					}
							
					if (StringTool.isNotBlank(jsonObj.get("store_match_name"))) {
								
				    	mapVo.put("store_match_name", jsonObj.get("store_match_name"));
				    		
					} else {
								
						err_sb.append("配套表名称为空  ");
								
					}
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
								
						matDeptMatch.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
				    
						mapVo.put("store_id", jsonObj.get("store_id"));
				    		
					} else {
								
						err_sb.append("仓库ID为空  ");
								
					}
							
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
								
						matDeptMatch.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
				    
						mapVo.put("dept_id", jsonObj.get("dept_id"));
				    		
					} else {
								
						err_sb.append("科室ID为空  ");
								
					}
							
							
					MatDeptMatch data_exc_extis = matDeptMatchService.queryByCode(mapVo);
						
					if (data_exc_extis != null) {
							
						err_sb.append("编码已经存在！ ");
							
					}
					if (err_sb.toString().length() > 0) {
							
						matDeptMatch.setError_type(err_sb.toString());
							
						list_err.add(matDeptMatch);
							
					} else {
					  
						String dataJson = matDeptMatchService.add(mapVo);
							
					}
				}
		} catch (DataAccessException e) {
			
			MatDeptMatch data_exc = new MatDeptMatch();
			
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
	 * 物资科室配套表<BR> 查询 材料明细
	 * @param entityMap
	 * @return List<MatStoreMatch>
	 * @throws DataAccessException
	*/
	@RequestMapping(value = "/hrp/mat/info/relaset/queryMatDeptDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatDeptDetailByCode(@RequestParam Map<String,Object> mapVo){
		

	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String matDeptMatchJson = matDeptMatchService.queryMdmDetailByCode(getPage(mapVo));
		
		return JSONObject.parseObject(matDeptMatchJson);
	}
	
}

