/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
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
import com.chd.hrp.med.entity.MedStoreDetail;
import com.chd.hrp.med.service.info.basic.MedStoreDetailService;
/**
 * 
 * @Description:
 * 08109 仓库对应关系明细表
 * @Table:
 * MED_STORE_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedStoreDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedStoreDetailController.class);
	
	//引入Service服务
	@Resource(name = "medStoreDetailService")
	private final MedStoreDetailService medStoreDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/medStoreDetailMainPage", method = RequestMethod.GET)
	public String medStoreDetailMainPage(Model mode) throws Exception {

		return "hrp/med/medstoredetail/medStoreDetailMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/medStoreDetailAddPage", method = RequestMethod.GET)
	public String medStoreDetailAddPage(Model mode) throws Exception {

		return "hrp/med/medstoredetail/medStoreDetailAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/addMedStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String medStoreDetailJson = medStoreDetailService.addMedStoreDetail(mapVo);

		return JSONObject.parseObject(medStoreDetailJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 08109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/medStoreDetailUpdatePage", method = RequestMethod.GET)
	public String medStoreDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MedStoreDetail medStoreDetail = new MedStoreDetail();
    
		medStoreDetail = medStoreDetailService.queryMedStoreDetailByCode(mapVo);
		
		mode.addAttribute("group_id", medStoreDetail.getGroup_id());
		mode.addAttribute("hos_id", medStoreDetail.getHos_id());
		mode.addAttribute("set_id", medStoreDetail.getSet_id());
		mode.addAttribute("store_id", medStoreDetail.getStore_id());
		
		return "hrp/med/medstoredetail/medStoreDetailUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/updateMedStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		String medStoreDetailJson = medStoreDetailService.updateMedStoreDetail(mapVo);
		
		return JSONObject.parseObject(medStoreDetailJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 08109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/addOrUpdateMedStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medStoreDetailJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		medStoreDetailJson = medStoreDetailService.addOrUpdateMedStoreDetail(detailVo);
		
		}
		return JSONObject.parseObject(medStoreDetailJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/deleteMedStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedStoreDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("set_id", ids[0])   ;
				mapVo.put("store_id", ids[1]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medStoreDetailJson = medStoreDetailService.deleteBatchMedStoreDetail(listVo);
			
	  return JSONObject.parseObject(medStoreDetailJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 08109 仓库对应关系明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/queryMedStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String medStoreDetail = medStoreDetailService.queryMedStoreDetail(getPage(mapVo));

		return JSONObject.parseObject(medStoreDetail);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 08109 仓库对应关系明细表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/medStoreDetailImportPage", method = RequestMethod.GET)
	public String medStoreDetailImportPage(Model mode) throws Exception {

		return "hrp/med/medstoredetail/medStoreDetailImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08109 仓库对应关系明细表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/medstoredetail/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08109 仓库对应关系明细表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08109 仓库对应关系明细表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/medstoredetail/readMedStoreDetailFiles",method = RequestMethod.POST)  
    public String readMedStoreDetailFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedStoreDetail> list_err = new ArrayList<MedStoreDetail>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedStoreDetail medStoreDetail = new MedStoreDetail();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					medStoreDetail.setSet_id(Long.valueOf(temp[2]));
		    		mapVo.put("set_id", temp[2]);
					
					} else {
						
						err_sb.append("设置ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medStoreDetail.setStore_id(Long.valueOf(temp[3]));
		    		mapVo.put("store_id", temp[3]);
					
					} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					 
					
				MedStoreDetail data_exc_extis = medStoreDetailService.queryMedStoreDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medStoreDetail.setError_type(err_sb.toString());
					
					list_err.add(medStoreDetail);
					
				} else {
			  
					String dataJson = medStoreDetailService.addMedStoreDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedStoreDetail data_exc = new MedStoreDetail();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08109 仓库对应关系明细表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/medstoredetail/addBatchMedStoreDetail", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedStoreDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedStoreDetail> list_err = new ArrayList<MedStoreDetail>();
		
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
			
			MedStoreDetail medStoreDetail = new MedStoreDetail();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					if (StringTool.isNotBlank(jsonObj.get("set_id"))) {
						
					medStoreDetail.setSet_id(Long.valueOf((String)jsonObj.get("set_id")));
		    		mapVo.put("set_id", jsonObj.get("set_id"));
		    		} else {
						
						err_sb.append("设置ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					medStoreDetail.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					
					
				MedStoreDetail data_exc_extis = medStoreDetailService.queryMedStoreDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medStoreDetail.setError_type(err_sb.toString());
					
					list_err.add(medStoreDetail);
					
				} else {
			  
					String dataJson = medStoreDetailService.addMedStoreDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedStoreDetail data_exc = new MedStoreDetail();
			
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

