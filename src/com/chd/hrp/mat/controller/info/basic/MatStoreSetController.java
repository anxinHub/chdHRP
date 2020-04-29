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
import com.chd.hrp.mat.entity.MatStoreSet;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreDetailServiceImpl;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreSetServiceImpl;
/**
 *  
 * @Description:
 * 04108 虚仓仓库设置
 * @Table:
 * MAT_STORE_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatStoreSetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatStoreSetController.class);
	
	//引入Service服务
	@Resource(name = "matStoreSetService")
	private final MatStoreSetServiceImpl matStoreSetService = null;
	
	@Resource(name = "matStoreDetailService")
	private final MatStoreDetailServiceImpl matStoreDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/matStoreSetMainPage", method = RequestMethod.GET)
	public String matStoreSetMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/storeset/matstoreset/matStoreSetMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/matStoreSetAddPage", method = RequestMethod.GET)
	public String matStoreSetAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/storeset/matstoreset/matStoreSetAdd";

	}
	
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/matStoreDetailPage", method = RequestMethod.GET)
	public String matStoreDetailMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("set_id", mapVo.get("set_id"));
		//mode.addAttribute("set_name", new String(mapVo.get("set_name").toString().getBytes("iso-8859-1"),"UTF-8"));
		mode.addAttribute("set_name", mapVo.get("set_name"));
		return "hrp/mat/info/basic/storeset/matstoreset/matStoreDetail";

	}

	/**
	 * @Description 
	 * 添加数据 04108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/addMatStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String matStoreSetJson = matStoreSetService.addMatStoreSet(mapVo);

		return JSONObject.parseObject(matStoreSetJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/matStoreSetUpdatePage", method = RequestMethod.GET)
	public String matStoreSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		MatStoreSet matStoreSet = new MatStoreSet();
    
		matStoreSet = matStoreSetService.queryMatStoreSetByCode(mapVo);
		
		mode.addAttribute("group_id", matStoreSet.getGroup_id());
		mode.addAttribute("hos_id", matStoreSet.getHos_id());
		mode.addAttribute("set_id", matStoreSet.getSet_id());
		mode.addAttribute("set_code", matStoreSet.getSet_code());
		mode.addAttribute("set_name", matStoreSet.getSet_name());
		
		return "hrp/mat/info/basic/storeset/matstoreset/matStoreSetUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/updateMatStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		String matStoreSetJson = matStoreSetService.updateMatStoreSet(mapVo);
		
		return JSONObject.parseObject(matStoreSetJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 04108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/addOrUpdateMatStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matStoreSetJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		matStoreSetJson = matStoreSetService.addOrUpdateMatStoreSet(detailVo);
		
		}
		return JSONObject.parseObject(matStoreSetJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/deleteMatStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatStoreSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		String matStoreSetJson = matStoreSetService.deleteBatchMatStoreSet(listVo);	
		return JSONObject.parseObject(matStoreSetJson);		
	}
	
	/**
	 * @Description 
	 * 查询数据 04108 虚仓仓库设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/queryMatStoreSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		String matStoreSet = matStoreSetService.queryMatStoreSet(getPage(mapVo));

		return JSONObject.parseObject(matStoreSet);
		
	}
	/**
	 * 根据用户的数据权限，查询出有数据权限的库房列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/queryMatStoreData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("user_id") == null){
			
			mapVo.put("user_id", SessionManager.getUserId());
			
			}
		
		String matStoreSet = matStoreSetService.queryMatStoreData(getPage(mapVo));

		return JSONObject.parseObject(matStoreSet);
		
	}
	/**
	 * 保存仓库对应关系
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/addMatStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStoreDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
		String matStoreSetJson =null;
		matStoreSetJson = matStoreDetailService.deleteBatchMatStoreDetail(deleteList);
		
		if(listVo.size()>0){
			 matStoreSetJson = matStoreDetailService.addBatchMatStoreDetail(listVo);
		}
		
			
	  return JSONObject.parseObject(matStoreSetJson);
			
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04108 虚仓仓库设置
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/matStoreSetImportPage", method = RequestMethod.GET)
	public String matStoreSetImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/storeset/matstoreset/matStoreSetImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04108 虚仓仓库设置
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/info/basic/storeset/matstoreset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04108 虚仓仓库设置.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04108 虚仓仓库设置
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/basic/storeset/matstoreset/readMatStoreSetFiles",method = RequestMethod.POST)  
    public String readMatStoreSetFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatStoreSet> list_err = new ArrayList<MatStoreSet>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatStoreSet matStoreSet = new MatStoreSet();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
					if (StringTool.isNotBlank(temp[0])) {
						matStoreSet.setSet_code(String.valueOf(temp[0]));
						mapVo.put("set_code", temp[0]);
					
					} else {

						err_sb.append("编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						matStoreSet.setSet_name(String.valueOf(temp[1]));
						mapVo.put("set_name", temp[1]);
					
					} else {

						err_sb.append("名称为空  ");
						
					}
					 
					
				List<MatStoreSet> existlist = matStoreSetService.queryMatStoreSetByName(mapVo);
				
				if (existlist.size() >0) {
					
					err_sb.append("仓库名字或编码已存在，请重新输入！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStoreSet.setError_type(err_sb.toString());
					
					list_err.add(matStoreSet);
					
				} else {
			  
					String dataJson = matStoreSetService.addMatStoreSet(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStoreSet data_exc = new MatStoreSet();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04108 虚仓仓库设置
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/addBatchMatStoreSet", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatStoreSet(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatStoreSet> list_err = new ArrayList<MatStoreSet>();
		
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
			
			MatStoreSet matStoreSet = new MatStoreSet();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("set_name"))) {
						matStoreSet.setSet_name(String.valueOf(jsonObj.get("set_name")));
						mapVo.put("set_name", jsonObj.get("set_name"));
		    		} else {
						
						err_sb.append("名称为空  ");
						
					}
					
					List<MatStoreSet> existlist = matStoreSetService.queryMatStoreSetByName(mapVo);
					
					if (existlist.size() >0) {
						
						err_sb.append("仓库名字已存在，请重新输入！ ");
						
					}
				if (err_sb.toString().length() > 0) {
					
					matStoreSet.setError_type(err_sb.toString());
					
					list_err.add(matStoreSet);
					
				} else {
			  
					String dataJson = matStoreSetService.addMatStoreSet(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStoreSet data_exc = new MatStoreSet();
			
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
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/matStoreSetAccountPage", method = RequestMethod.GET)
	public String matStoreSetAccountPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("set_id", mapVo.get("set_id"));
		mode.addAttribute("set_code", mapVo.get("set_code"));
		mode.addAttribute("set_name", mapVo.get("set_name"));
		
		return "hrp/mat/info/basic/storeset/matstoreset/matStoreSetAccount";
	}

	/**
	 * 查询虚仓库房明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/queryMatStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matStoreSet = matStoreSetService.queryMatStoreDetail(getPage(mapVo));

		return JSONObject.parseObject(matStoreSet);
	}

	/**
	 * @Description 
	 * 保存虚仓对应仓库是否结账
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeset/matstoreset/saveMatStoreSetAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatStoreSetAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String matStoreSetJson = matStoreSetService.saveMatStoreSetAccount(mapVo);

		return JSONObject.parseObject(matStoreSetJson);
		
	}
    
}

