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
import com.chd.hrp.med.entity.MedStoreSet;
import com.chd.hrp.med.serviceImpl.info.basic.MedStoreDetailServiceImpl;
import com.chd.hrp.med.serviceImpl.info.basic.MedStoreSetServiceImpl;
/**
 *  
 * @Description:
 * 08108 虚仓仓库设置
 * @Table:
 * MED_STORE_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedStoreSetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedStoreSetController.class);
	
	//引入Service服务
	@Resource(name = "medStoreSetService")
	private final MedStoreSetServiceImpl medStoreSetService = null;
	
	@Resource(name = "medStoreDetailService")
	private final MedStoreDetailServiceImpl medStoreDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/medStoreSetMainPage", method = RequestMethod.GET)
	public String medStoreSetMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/storeset/medstoreset/medStoreSetMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/medStoreSetAddPage", method = RequestMethod.GET)
	public String medStoreSetAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/storeset/medstoreset/medStoreSetAdd";

	}
	
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/medStoreDetailPage", method = RequestMethod.GET)
	public String medStoreDetailMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("set_id", mapVo.get("set_id"));
		//mode.addAttribute("set_name", new String(mapVo.get("set_name").toString().getBytes("iso-8859-1"),"UTF-8"));
		mode.addAttribute("set_name", mapVo.get("set_name"));
		return "hrp/med/info/basic/storeset/medstoreset/medStoreDetail";

	}

	/**
	 * @Description 
	 * 添加数据 08108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/addMedStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String medStoreSetJson = medStoreSetService.addMedStoreSet(mapVo);

		return JSONObject.parseObject(medStoreSetJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 08108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/medStoreSetUpdatePage", method = RequestMethod.GET)
	public String medStoreSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		MedStoreSet medStoreSet = new MedStoreSet();
    
		medStoreSet = medStoreSetService.queryMedStoreSetByCode(mapVo);
		
		mode.addAttribute("group_id", medStoreSet.getGroup_id());
		mode.addAttribute("hos_id", medStoreSet.getHos_id());
		mode.addAttribute("set_id", medStoreSet.getSet_id());
		mode.addAttribute("set_code", medStoreSet.getSet_code());
		mode.addAttribute("set_name", medStoreSet.getSet_name());
		
		return "hrp/med/info/basic/storeset/medstoreset/medStoreSetUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/updateMedStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		String medStoreSetJson = medStoreSetService.updateMedStoreSet(mapVo);
		
		return JSONObject.parseObject(medStoreSetJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 08108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/addOrUpdateMedStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medStoreSetJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		medStoreSetJson = medStoreSetService.addOrUpdateMedStoreSet(detailVo);
		
		}
		return JSONObject.parseObject(medStoreSetJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/deleteMedStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedStoreSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {		
				Map<String, Object> mapVo=new HashMap<String, Object>();
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("set_id", id);
				listVo.add(mapVo);
			}
		String medStoreSetJson = medStoreSetService.deleteBatchMedStoreSet(listVo);	
		return JSONObject.parseObject(medStoreSetJson);		
	}
	
	/**
	 * @Description 
	 * 查询数据 08108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/queryMedStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		String medStoreSet = medStoreSetService.queryMedStoreSet(mapVo);

		return JSONObject.parseObject(medStoreSet);
		
	}
	/**
	 * 根据用户的数据权限，查询出有数据权限的库房列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/queryMedStoreData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("user_id") == null){
			
			mapVo.put("user_id", SessionManager.getUserId());
			
			}
		
		String medStoreSet = medStoreSetService.queryMedStoreData(getPage(mapVo));

		return JSONObject.parseObject(medStoreSet);
		
	}
	/**
	 * 保存仓库对应关系
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/addMedStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> deleteList= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				Map<String, Object> deleteMap = new HashMap<String, Object>();
				String[] ids = id.split("@");
				//表的主键
				if("null".equals(ids[0])){
					deleteMap.put("set_id", ids[1]);
					deleteMap.put("group_id", SessionManager.getGroupId());
					deleteMap.put("hos_id", SessionManager.getHosId());
					deleteList.add(deleteMap);
				}else{
					mapVo.put("store_id", ids[0]);
					mapVo.put("set_id", ids[1]);
					mapVo.put("group_id", SessionManager.getGroupId());
					mapVo.put("hos_id", SessionManager.getHosId());
					listVo.add(mapVo);
					deleteMap.put("set_id", ids[1]);
					deleteMap.put("group_id", SessionManager.getGroupId());
					deleteMap.put("hos_id", SessionManager.getHosId());
					deleteList.add(deleteMap);
				}
				
				
	    }
			
		String medStoreSetJson =null;
		medStoreSetJson = medStoreDetailService.deleteBatchMedStoreDetail(deleteList);
		
		if(listVo.size()>0){
			 medStoreSetJson = medStoreDetailService.addBatchMedStoreDetail(listVo);
		}
		
			
	  return JSONObject.parseObject(medStoreSetJson);
			
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 08108 虚仓仓库设置
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/medStoreSetImportPage", method = RequestMethod.GET)
	public String medStoreSetImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/storeset/medstoreset/medStoreSetImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08108 虚仓仓库设置
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/storeset/medstoreset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08108 虚仓仓库设置.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08108 虚仓仓库设置
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/info/basic/storeset/medstoreset/readMedStoreSetFiles",method = RequestMethod.POST)  
    public String readMedStoreSetFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedStoreSet> list_err = new ArrayList<MedStoreSet>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		//查询所有虚仓,用于判断是否存在
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("group_id", SessionManager.getGroupId());   
		queryMap.put("hos_id", SessionManager.getHosId());   
		List<MedStoreSet> medStoreSetList = medStoreSetService.queryMedStoreSetList(queryMap);
		
		
		Map<String, Object> exitMap = new HashMap<String, Object>();
		if(medStoreSetList.size() > 0){
			for(MedStoreSet mss : medStoreSetList){
				exitMap.put(mss.getSet_code(),mss.getSet_code());
				exitMap.put(String.valueOf(mss.getSet_name()),mss.getSet_name());
			}
		}
		
		
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedStoreSet medStoreSet = new MedStoreSet();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
	    		mapVo.put("group_id", SessionManager.getGroupId());   
	    		mapVo.put("hos_id", SessionManager.getHosId());   
	    		
	    		String set_code = temp[0];
	    		String set_name = temp[1];
	    		
	    		if("null".equals(set_code) && "null".equals(set_name)){//空行
	    			continue;
	    		}
	    		
	    		if (StringTool.isNotBlank(set_code)) {
	    			if(exitMap.get(set_code) == null){
	    				medStoreSet.setSet_name(String.valueOf(set_code));
	    				mapVo.put("set_code",set_code);
	    			}else{
	    				err_sb.append("编码【"+set_code+"】已在数据库中存在  ");
	    			}
				
				} else {
					err_sb.append("编码为空  ");
				}
				 
				if (StringTool.isNotBlank(set_name)) {
					if(exitMap.get(set_name) == null){
						medStoreSet.setSet_name(String.valueOf(set_name));
						mapVo.put("set_name", set_name);
					}else{
						err_sb.append("名称【"+set_name+"】已在数据库中存在  ");
					}
				
				} else {
					err_sb.append("名称为空  ");
				}
					 
					
				if (err_sb.toString().length() > 0) {
					
					medStoreSet.setError_type(err_sb.toString());
					
					list_err.add(medStoreSet);
					
				} else {
			  
					String dataJson = medStoreSetService.addMedStoreSet(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedStoreSet data_exc = new MedStoreSet();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08108 虚仓仓库设置
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/addBatchMedStoreSet", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedStoreSet(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedStoreSet> list_err = new ArrayList<MedStoreSet>();
		
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
		
		//查询所有虚仓,用于判断是否存在
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("group_id", SessionManager.getGroupId());   
		queryMap.put("hos_id", SessionManager.getHosId());   
		List<MedStoreSet> medStoreSetList = medStoreSetService.queryMedStoreSetList(queryMap);
		
		
		Map<String, Object> exitMap = new HashMap<String, Object>();
		if(medStoreSetList.size() > 0){
			for(MedStoreSet mss : medStoreSetList){
				exitMap.put(mss.getSet_code(),mss.getSet_code());
				exitMap.put(String.valueOf(mss.getSet_name()),mss.getSet_name());
			}
		}
		
		Iterator it = json.iterator();
		
		try {
			
			while (it.hasNext()) {
				
				StringBuffer err_sb = new StringBuffer();
				MedStoreSet medStoreSet = new MedStoreSet();
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				String set_code = String.valueOf(jsonObj.get("set_code"));
	    		String set_name = String.valueOf(jsonObj.get("set_name"));
	    		
	    		if("null".equals(set_code) && "null".equals(set_name)){//空行
	    			continue;
	    		}
	    		
	    		if (StringTool.isNotBlank(set_code)) {
	    			if(exitMap.get(set_code) == null){
	    				medStoreSet.setSet_name(String.valueOf(set_code));
	    				mapVo.put("set_code",set_code);
	    			}else{
	    				err_sb.append("编码【"+set_code+"】已在数据库中存在  ");
	    			}
				
				} else {
					err_sb.append("编码为空  ");
				}
				 
				if (StringTool.isNotBlank(set_name)) {
					if(exitMap.get(set_name) == null){
						medStoreSet.setSet_name(String.valueOf(set_name));
						mapVo.put("set_name", set_name);
					}else{
						err_sb.append("名称【"+set_name+"】已在数据库中存在  ");
					}
				
				} else {
					err_sb.append("名称为空  ");
				}
					
				if (err_sb.toString().length() > 0) {
					medStoreSet.setError_type(err_sb.toString());
					list_err.add(medStoreSet);
					
				} else {
					String dataJson = medStoreSetService.addMedStoreSet(mapVo);
				}
			}
			
		} catch (DataAccessException e) {
			
			MedStoreSet data_exc = new MedStoreSet();
			
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
	 * 是否结账页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/medStoreSetAccountPage", method = RequestMethod.GET)
	public String medStoreSetAccountPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("set_id", mapVo.get("set_id"));
		mode.addAttribute("set_code", mapVo.get("set_code"));
		mode.addAttribute("set_name", mapVo.get("set_name"));
		
		return "hrp/med/info/basic/storeset/medstoreset/medStoreSetAccount";
	}

	/**
	 * 查询虚仓库房明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/queryMedStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medStoreSet = medStoreSetService.queryMedStoreDetail(getPage(mapVo));

		return JSONObject.parseObject(medStoreSet);
	}

	/**
	 * @Description 
	 * 保存虚仓对应仓库是否结账
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeset/medstoreset/saveMedStoreSetAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedStoreSetAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String medStoreSetJson = medStoreSetService.saveMedStoreSetAccount(mapVo);

		return JSONObject.parseObject(medStoreSetJson);
		
	}
    
}

