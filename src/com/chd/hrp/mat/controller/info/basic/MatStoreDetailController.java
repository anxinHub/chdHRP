/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
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
import com.chd.hrp.mat.entity.MatStoreDetail;
import com.chd.hrp.mat.service.info.basic.MatStoreDetailService;
/**
 * 
 * @Description:
 * 04109 仓库对应关系明细表
 * @Table:
 * MAT_STORE_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatStoreDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatStoreDetailController.class);
	
	//引入Service服务
	@Resource(name = "matStoreDetailService")
	private final MatStoreDetailService matStoreDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/matStoreDetailMainPage", method = RequestMethod.GET)
	public String matStoreDetailMainPage(Model mode) throws Exception {

		return "hrp/mat/matstoredetail/matStoreDetailMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/matStoreDetailAddPage", method = RequestMethod.GET)
	public String matStoreDetailAddPage(Model mode) throws Exception {

		return "hrp/mat/matstoredetail/matStoreDetailAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/addMatStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String matStoreDetailJson = matStoreDetailService.addMatStoreDetail(mapVo);

		return JSONObject.parseObject(matStoreDetailJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/matStoreDetailUpdatePage", method = RequestMethod.GET)
	public String matStoreDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MatStoreDetail matStoreDetail = new MatStoreDetail();
    
		matStoreDetail = matStoreDetailService.queryMatStoreDetailByCode(mapVo);
		
		mode.addAttribute("group_id", matStoreDetail.getGroup_id());
		mode.addAttribute("hos_id", matStoreDetail.getHos_id());
		mode.addAttribute("set_id", matStoreDetail.getSet_id());
		mode.addAttribute("store_id", matStoreDetail.getStore_id());
		
		return "hrp/mat/matstoredetail/matStoreDetailUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/updateMatStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		String matStoreDetailJson = matStoreDetailService.updateMatStoreDetail(mapVo);
		
		return JSONObject.parseObject(matStoreDetailJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 04109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/addOrUpdateMatStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matStoreDetailJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		matStoreDetailJson = matStoreDetailService.addOrUpdateMatStoreDetail(detailVo);
		
		}
		return JSONObject.parseObject(matStoreDetailJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/deleteMatStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatStoreDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("set_id", ids[0])   ;
				mapVo.put("store_id", ids[1]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String matStoreDetailJson = matStoreDetailService.deleteBatchMatStoreDetail(listVo);
			
	  return JSONObject.parseObject(matStoreDetailJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 04109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/queryMatStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String matStoreDetail = matStoreDetailService.queryMatStoreDetail(getPage(mapVo));

		return JSONObject.parseObject(matStoreDetail);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04109 仓库对应关系明细表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/matStoreDetailImportPage", method = RequestMethod.GET)
	public String matStoreDetailImportPage(Model mode) throws Exception {

		return "hrp/mat/matstoredetail/matStoreDetailImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04109 仓库对应关系明细表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/matstoredetail/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04109 仓库对应关系明细表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04109 仓库对应关系明细表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/matstoredetail/readMatStoreDetailFiles",method = RequestMethod.POST)  
    public String readMatStoreDetailFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatStoreDetail> list_err = new ArrayList<MatStoreDetail>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatStoreDetail matStoreDetail = new MatStoreDetail();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					matStoreDetail.setSet_id(Long.valueOf(temp[2]));
		    		mapVo.put("set_id", temp[2]);
					
					} else {
						
						err_sb.append("设置ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matStoreDetail.setStore_id(Long.valueOf(temp[3]));
		    		mapVo.put("store_id", temp[3]);
					
					} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					 
					
				MatStoreDetail data_exc_extis = matStoreDetailService.queryMatStoreDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStoreDetail.setError_type(err_sb.toString());
					
					list_err.add(matStoreDetail);
					
				} else {
			  
					String dataJson = matStoreDetailService.addMatStoreDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStoreDetail data_exc = new MatStoreDetail();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04109 仓库对应关系明细表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/matstoredetail/addBatchMatStoreDetail", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatStoreDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatStoreDetail> list_err = new ArrayList<MatStoreDetail>();
		
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
			
			MatStoreDetail matStoreDetail = new MatStoreDetail();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					if (StringTool.isNotBlank(jsonObj.get("set_id"))) {
						
					matStoreDetail.setSet_id(Long.valueOf((String)jsonObj.get("set_id")));
		    		mapVo.put("set_id", jsonObj.get("set_id"));
		    		} else {
						
						err_sb.append("设置ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					matStoreDetail.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					
					
				MatStoreDetail data_exc_extis = matStoreDetailService.queryMatStoreDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStoreDetail.setError_type(err_sb.toString());
					
					list_err.add(matStoreDetail);
					
				} else {
			  
					String dataJson = matStoreDetailService.addMatStoreDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStoreDetail data_exc = new MatStoreDetail();
			
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

