/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.repair;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.repair.AssRepairTeamDict;
import com.chd.hrp.ass.service.repair.AssRepairTeamDictService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_TEAM_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRepairTeamDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRepairTeamDictController.class);
	
	//引入Service服务
	@Resource(name = "assRepairTeamDictService")
	private final AssRepairTeamDictService assRepairTeamDictService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/assRepairTeamDictMainPage", method = RequestMethod.GET)
	public String assRepairTeamDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapply/assrepairteamdict/assRepairTeamDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/assRepairTeamDictAddPage", method = RequestMethod.GET)
	public String assRepairTeamDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapply/assrepairteamdict/assRepairTeamDictAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/addAssRepairTeamDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRepairTeamDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("rep_team_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("rep_team_name").toString()));
       
		String assRepairTeamDictJson = assRepairTeamDictService.add(mapVo);

		return JSONObject.parseObject(assRepairTeamDictJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/assRepairTeamDictUpdatePage", method = RequestMethod.GET)
	public String assRepairTeamDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssRepairTeamDict assRepairTeamDict = new AssRepairTeamDict();
    
		assRepairTeamDict = assRepairTeamDictService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRepairTeamDict.getGroup_id());
		mode.addAttribute("hos_id", assRepairTeamDict.getHos_id());
		mode.addAttribute("rep_team_code", assRepairTeamDict.getRep_team_code());
		mode.addAttribute("rep_team_name", assRepairTeamDict.getRep_team_name());
		mode.addAttribute("spell_code", assRepairTeamDict.getSpell_code());
		mode.addAttribute("wbx_code", assRepairTeamDict.getWbx_code());
		mode.addAttribute("is_stop", assRepairTeamDict.getIs_stop());
		
		return "hrp/ass/assrepairapply/assrepairteamdict/assRepairTeamDictUpdate";
	}
	/**
	 * @Description 
	 * 添加人员跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/assRepairUserAddPage", method = RequestMethod.GET)
	public String assRepairUserAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("rep_team_code", mapVo.get("rep_team_code"));
		
		return "hrp/ass/assrepairapply/assrepairteamdict/assRepairUserAdd";
	}
	/**
	 * @Description 
	 * 更新人员跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/assRepairUserUpdatePage", method = RequestMethod.GET)
	public String assRepairUserUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		Map<String,Object>map = assRepairTeamDictService.queryUserById(mapVo);
		mode.addAttribute("rep_team_code", map.get("REP_TEAM_CODE"));
		mode.addAttribute("rep_team_name", map.get("REP_TEAM_NAME"));
		mode.addAttribute("rep_user", map.get("REP_USER"));
		mode.addAttribute("usercode", map.get("USER_CODE"));
		mode.addAttribute("user_name", map.get("USER_NAME"));
		mode.addAttribute("phone1", map.get("PHONE1"));
		mode.addAttribute("phone2", map.get("PHONE2"));
		mode.addAttribute("sort_code", map.get("SORT_CODE"));
		
		return "hrp/ass/assrepairapply/assrepairteamdict/assRepairUserUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/updateAssRepairTeamDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRepairTeamDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("rep_team_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("rep_team_name").toString()));
	  
		String assRepairTeamDictJson = assRepairTeamDictService.update(mapVo);
		
		return JSONObject.parseObject(assRepairTeamDictJson);
	}
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/updateAssRepairUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRepairUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
			mapVo.put("group_id", SessionManager.getGroupId());   
		
			mapVo.put("hos_id", SessionManager.getHosId());   
			
			mapVo.put("copy_code", SessionManager.getCopyCode());   
			
		String assRepairTeamDictJson = assRepairTeamDictService.updateAssRepairUser(mapVo);
		
		return JSONObject.parseObject(assRepairTeamDictJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/addOrUpdateAssRepairTeamDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRepairTeamDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRepairTeamDictJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
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
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("").toString()));
	  
		assRepairTeamDictJson = assRepairTeamDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assRepairTeamDictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/deleteAssRepairTeamDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRepairTeamDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("rep_team_code", ids[2]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String assRepairTeamDictJson = assRepairTeamDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRepairTeamDictJson);
			
	}
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/deleteAssRepairUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRepairUser(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<String> list = Arrays.asList(paramVo.split(","));  
	 
		
		String assRepairTeamDictJson = assRepairTeamDictService.deleteAssRepairUser(list);
		
		return JSONObject.parseObject(assRepairTeamDictJson);
		
	}
	/**
	 * @Description 
	 * 添加人员数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/addRepairUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRepairUser(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			if("".equals(ids[1])){
				continue;
			}
			//表的主键
			mapVo.put("rep_team_code", ids[0]);
			mapVo.put("user_id", ids[1])   ;
			if(ids.length>2){
				mapVo.put("phone1", ids[2])   ;
				mapVo.put("phone2", ids[3])   ;
			}else{
				mapVo.put("phone1", "")   ;
				mapVo.put("phone2", "")   ;
			}
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			listVo.add(mapVo);
		}
		
		String assRepairTeamDictJson = assRepairTeamDictService.addRepairUser(listVo);
		
		return JSONObject.parseObject(assRepairTeamDictJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/queryAssRepairTeamDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairTeamDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRepairTeamDict = assRepairTeamDictService.query(getPage(mapVo));

		return JSONObject.parseObject(assRepairTeamDict);
		
	}
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/querySysUserNotExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysUserNotExists(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRepairTeamDict = assRepairTeamDictService.querySysUserNotExists(getPage(mapVo));
		
		return JSONObject.parseObject(assRepairTeamDict);
		
	}
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/queryAssRepTeamDeptMapByTeamCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepTeamDeptMapByTeamCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		String assRepairTeamDict = assRepairTeamDictService.queryAssRepTeamDeptMapByTeamCode(mapVo);
		
		return JSONObject.parseObject(assRepairTeamDict);
		
	}
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/queryRepUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRepUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		String assRepairTeamDict = assRepairTeamDictService.queryRepUser(getPage(mapVo));
		
		return JSONObject.parseObject(assRepairTeamDict);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/assRepairTeamDictImportPage", method = RequestMethod.GET)
	public String assRepairTeamDictImportPage(Model mode) throws Exception {

		return "hrp/ass/repair/assrepairteamdict/assRepairTeamDictImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 tabledesc
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/repair/assrepairteamdict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","tabledesc.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/repair/assrepairteamdict/readAssRepairTeamDictFiles",method = RequestMethod.POST)  
    public String readAssRepairTeamDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssRepairTeamDict> list_err = new ArrayList<AssRepairTeamDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssRepairTeamDict assRepairTeamDict = new AssRepairTeamDict();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					assRepairTeamDict.setRep_team_code(temp[2]);
		    		mapVo.put("rep_team_code", temp[2]);
					
					} else {
						
						err_sb.append("班组编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assRepairTeamDict.setRep_team_name(temp[3]);
		    		mapVo.put("rep_team_name", temp[3]);
					
					} else {
						
						err_sb.append("班组名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assRepairTeamDict.setSpell_code(temp[4]);
		    		mapVo.put("spell_code", temp[4]);
					
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assRepairTeamDict.setWbx_code(temp[5]);
		    		mapVo.put("wbx_code", temp[5]);
					
					} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assRepairTeamDict.setIs_stop(Integer.valueOf(temp[6]));
		    		mapVo.put("is_stop", temp[6]);
					
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					 
					
				AssRepairTeamDict data_exc_extis = assRepairTeamDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRepairTeamDict.setError_type(err_sb.toString());
					
					list_err.add(assRepairTeamDict);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = assRepairTeamDictService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRepairTeamDict data_exc = new AssRepairTeamDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/repair/assrepairteamdict/addBatchAssRepairTeamDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssRepairTeamDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssRepairTeamDict> list_err = new ArrayList<AssRepairTeamDict>();
		
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
			
			AssRepairTeamDict assRepairTeamDict = new AssRepairTeamDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					if (StringTool.isNotBlank(jsonObj.get("rep_team_code"))) {
						
					assRepairTeamDict.setRep_team_code((String)jsonObj.get("rep_team_code"));
		    		mapVo.put("rep_team_code", jsonObj.get("rep_team_code"));
		    		} else {
						
						err_sb.append("班组编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("rep_team_name"))) {
						
					assRepairTeamDict.setRep_team_name((String)jsonObj.get("rep_team_name"));
		    		mapVo.put("rep_team_name", jsonObj.get("rep_team_name"));
		    		} else {
						
						err_sb.append("班组名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					assRepairTeamDict.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					assRepairTeamDict.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					assRepairTeamDict.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					
				AssRepairTeamDict data_exc_extis = assRepairTeamDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRepairTeamDict.setError_type(err_sb.toString());
					
					list_err.add(assRepairTeamDict);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = assRepairTeamDictService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRepairTeamDict data_exc = new AssRepairTeamDict();
			
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

