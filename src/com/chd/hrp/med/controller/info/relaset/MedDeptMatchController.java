/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.relaset;
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
import com.chd.hrp.med.entity.MedDeptMatch;
import com.chd.hrp.med.service.info.relaset.MedDeptMatchService;
import com.chd.hrp.med.serviceImpl.info.relaset.MedDeptMatchServiceImpl;
/**
 * 
 * @Description:
 * 08114 药品科室配套表
 * @Table:
 * MED_DEPT_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedDeptMatchController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedDeptMatchController.class);
	
	//引入Service服务
	@Resource(name = "medDeptMatchService")
	private final MedDeptMatchService medDeptMatchService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/medDeptMatchMainPage", method = RequestMethod.GET)
	public String medDeptMatchMainPage(Model mode) throws Exception {

		return "hrp/med/info/relaset/deptmatch/medDeptMatchMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/medDeptMatchAddPage", method = RequestMethod.GET)
	public String medDeptMatchAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08032", MyConfig.getSysPara("08032"));

		return "hrp/med/info/relaset/deptmatch/medDeptMatchAdd";

	}
	
	/**
	 * @Description 选择材料页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/relaset/medDeptMatchChoiceInvPage", method = RequestMethod.GET)
	public String medDeptMatchChoiceInvPage(Model mode) throws Exception {
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/info/relaset/deptmatch/medDeptMatchChoiceInv";
	}
	
	//组装科室需求计划
	@RequestMapping(value = "/hrp/med/info/relaset/queryDeptInvData", method = RequestMethod.POST)
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
					
		String detailData = medDeptMatchService.queryDeptInvData(mapVo);
					
		return JSONObject.parseObject(detailData);
					
	}

	/**
	 * @Description 
	 * 添加数据 08114 药品科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/addMedDeptMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedDeptMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			mapVo.put("table_code", "MED_DEPT_MATCH");
			mapVo.put("prefixe", "");
			mapVo.put("year", year);
			mapVo.put("month", month);
			String dept_match_code= medDeptMatchService.getMedNextNo(mapVo);
			mapVo.put("dept_match_code", dept_match_code);
		}
		
		String medJson;
		try {
			medJson = medDeptMatchService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 08114 药品科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/medDeptMatchUpdatePage", method = RequestMethod.GET)
	public String medDeptMatchUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		
		MedDeptMatch medDeptMatch = new MedDeptMatch();
		medDeptMatch = medDeptMatchService.queryByCode(mapVo);
		mode.addAttribute("dept_match_id", medDeptMatch.getDept_match_id());
		mode.addAttribute("dept_match_code", medDeptMatch.getDept_match_code());
		mode.addAttribute("dept_match_name", medDeptMatch.getDept_match_name());
		
		if(!"".equals(mapVo.get("store_id")) && mapVo.get("store_id")!=null){
			mode.addAttribute("store_id", medDeptMatch.getStore_id());
			mode.addAttribute("store_no", medDeptMatch.getStore_no());
			mode.addAttribute("store_code", medDeptMatch.getStore_code());
			mode.addAttribute("store_name", medDeptMatch.getStore_name());
		}else{
			mode.addAttribute("store_id","");
			mode.addAttribute("store_no",  "");
			mode.addAttribute("store_code","");
			mode.addAttribute("store_name",  "");
		}
		
		mode.addAttribute("dept_id", medDeptMatch.getDept_id());
		mode.addAttribute("dept_no", medDeptMatch.getDept_no());
		mode.addAttribute("dept_code", medDeptMatch.getDept_code());
		mode.addAttribute("dept_name", medDeptMatch.getDept_name());
		
		
		mode.addAttribute("p08032", MyConfig.getSysPara("08032"));
		
		return "hrp/med/info/relaset/deptmatch/medDeptMatchUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08114 药品科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/updateMedDeptMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedDeptMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medJson;
		try {
			medJson = medDeptMatchService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	
	@RequestMapping(value = "/hrp/med/info/relaset/deleteMedDeptMatchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedDeptMatchDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		String medDeptMatchJson = medDeptMatchService.deleteBatchDetail(listVo);
	  return JSONObject.parseObject(medDeptMatchJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据 08114 药品科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/deleteMedDeptMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedDeptMatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
			
		String medJson;
		try {
			medJson = medDeptMatchService.deleteBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
	  return JSONObject.parseObject(medJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 08114 药品科室配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/queryMedDeptMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String medDeptMatch = medDeptMatchService.query(mapVo);

		return JSONObject.parseObject(medDeptMatch);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 08114 药品科室配套表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/medDeptMatchImportPage", method = RequestMethod.GET)
	public String medDeptMatchImportPage(Model mode) throws Exception {

		return "hrp/med/info/relaset/deptmatch/medDeptMatchImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08114 药品科室配套表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/med/info/relaset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08114 药品科室配套表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08114 药品科室配套表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/info/relaset/readMedDeptMatchFiles",method = RequestMethod.POST)  
    public String readMedDeptMatchFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedDeptMatch> list_err = new ArrayList<MedDeptMatch>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedDeptMatch medDeptMatch = new MedDeptMatch();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medDeptMatch.setDept_match_id(Long.valueOf(temp[3]));
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
						
					medDeptMatch.setStore_id(Long.valueOf(temp[5]));
		    		mapVo.put("store_id", temp[5]);
					
					} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medDeptMatch.setDept_id(Long.valueOf(temp[6]));
		    		mapVo.put("dept_id", temp[6]);
					
					} else {
						
						err_sb.append("科室ID为空  ");
						
					}
					 
					
				MedDeptMatch data_exc_extis = medDeptMatchService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medDeptMatch.setError_type(err_sb.toString());
					
					list_err.add(medDeptMatch);
					
				} else {
			  
					String dataJson = medDeptMatchService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedDeptMatch data_exc = new MedDeptMatch();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08114 药品科室配套表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/addBatchMedDeptMatch", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedDeptMatch(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedDeptMatch> list_err = new ArrayList<MedDeptMatch>();
		
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
					
					MedDeptMatch medDeptMatch = new MedDeptMatch();
					
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("store_match_id"))) {
								
						medDeptMatch.setDept_match_id(Long.valueOf((String)jsonObj.get("store_match_id")));
				    		
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
								
						medDeptMatch.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
				    
						mapVo.put("store_id", jsonObj.get("store_id"));
				    		
					} else {
								
						err_sb.append("仓库ID为空  ");
								
					}
							
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
								
						medDeptMatch.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
				    
						mapVo.put("dept_id", jsonObj.get("dept_id"));
				    		
					} else {
								
						err_sb.append("科室ID为空  ");
								
					}
							
							
					MedDeptMatch data_exc_extis = medDeptMatchService.queryByCode(mapVo);
						
					if (data_exc_extis != null) {
							
						err_sb.append("编码已经存在！ ");
							
					}
					if (err_sb.toString().length() > 0) {
							
						medDeptMatch.setError_type(err_sb.toString());
							
						list_err.add(medDeptMatch);
							
					} else {
					  
						String dataJson = medDeptMatchService.add(mapVo);
							
					}
				}
		} catch (DataAccessException e) {
			
			MedDeptMatch data_exc = new MedDeptMatch();
			
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
	 * 药品科室配套表<BR> 查询 材料明细
	 * @param entityMap
	 * @return List<MedStoreMatch>
	 * @throws DataAccessException
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/queryMedDeptDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedDeptDetailByCode(@RequestParam Map<String,Object> mapVo){
		

	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String medDeptMatchJson = medDeptMatchService.queryMdmDetailByCode(getPage(mapVo));
		
		return JSONObject.parseObject(medDeptMatchJson);
	}
	
}

