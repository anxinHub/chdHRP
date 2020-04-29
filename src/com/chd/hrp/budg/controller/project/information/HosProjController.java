/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.project.information;
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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.HosProj;
import com.chd.hrp.budg.service.project.information.HosProjService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * HOS_PROJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class HosProjController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HosProjController.class);
	
	//引入Service服务
	@Resource(name = "hosProjService")
	private final HosProjService hosProjService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/hosProjMainPage", method = RequestMethod.GET)
	public String hosProjMainPage(Model mode) throws Exception {

		return "hrp/budg/project/information/hosProjMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/hosProjAddPage", method = RequestMethod.GET)
	public String hosProjAddPage(Model mode) throws Exception {

		return "hrp/budg/project/information/hosProjAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/addHosProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHosProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
    	mapVo.put("acct_year", SessionManager.getAcctYear());
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("proj_name").toString()));
       
		String hosProjJson = hosProjService.add(mapVo);

		return JSONObject.parseObject(hosProjJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/hosProjUpdatePage", method = RequestMethod.GET)
	public String hosProjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        mapVo.put("acct_year", SessionManager.getAcctYear());
        
		Map<String,Object> mapdata = hosProjService.queryByCode(mapVo);
		
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		
		mode.addAttribute("group_id", mapdata.get("group_id"));
		
		mode.addAttribute("hos_id", mapdata.get("hos_id"));
		
		mode.addAttribute("proj_id", mapdata.get("proj_id"));
		
		mode.addAttribute("proj_code", mapdata.get("proj_code"));
		
		mode.addAttribute("proj_name", mapdata.get("proj_name"));
		
		mode.addAttribute("type_code", mapdata.get("type_code"));
		
		mode.addAttribute("type_name", mapdata.get("type_name"));
		
		mode.addAttribute("level_code", mapdata.get("level_code"));
		
		mode.addAttribute("level_name", mapdata.get("level_name"));
		
		mode.addAttribute("proj_simple", mapdata.get("proj_simple"));
		
		mode.addAttribute("use_code", mapdata.get("use_code"));
		
		mode.addAttribute("use_name", mapdata.get("use_name"));
		
		mode.addAttribute("con_emp_id", mapdata.get("con_emp_id"));
		
		mode.addAttribute("con_emp_name", mapdata.get("con_emp_name"));
		
		mode.addAttribute("con_phone", mapdata.get("con_phone"));
		
		mode.addAttribute("acc_emp_id", mapdata.get("acc_emp_id"));
		
		mode.addAttribute("acc_emp_name", mapdata.get("acc_emp_name"));
		
		mode.addAttribute("acc_phone", mapdata.get("acc_phone"));
		
		mode.addAttribute("dept_id", mapdata.get("dept_id"));
		
		mode.addAttribute("dept_name", mapdata.get("dept_name"));
		
		mode.addAttribute("app_emp_id", mapdata.get("app_emp_id"));
		
		mode.addAttribute("app_emp_name", mapdata.get("app_emp_name"));
		
		if(!"".equals(mapdata.get("app_date")) && mapdata.get("app_date")!=null )
		{
		mode.addAttribute("app_date", dateFormater.format(mapdata.get("app_date")) );
		}
		
		mode.addAttribute("app_phone", mapdata.get("app_phone"));
		
		mode.addAttribute("email", mapdata.get("email"));
		
		mode.addAttribute("note", mapdata.get("note"));
		
		if(!"".equals(mapdata.get("set_up_date")) && mapdata.get("set_up_date")!=null )
		{
		mode.addAttribute("set_up_date", dateFormater.format(mapdata.get("set_up_date")));
		}
		
		if(!"".equals(mapdata.get("complete_date")) && mapdata.get("complete_date")!=null )
		{
		mode.addAttribute("complete_date", dateFormater.format(mapdata.get("complete_date")));
		}
		
		if(!"".equals(mapdata.get("pay_end_date")) && mapdata.get("pay_end_date")!=null )
		{
		mode.addAttribute("pay_end_date", dateFormater.format(mapdata.get("pay_end_date")));
		}
		
		if(!"".equals(mapdata.get("sespend_date")) && mapdata.get("sespend_date")!=null )
		{
		mode.addAttribute("sespend_date", dateFormater.format(mapdata.get("sespend_date")));
		}
		
		mode.addAttribute("proj_state", mapdata.get("proj_state"));
		
		mode.addAttribute("value_code", mapdata.get("value_code"));
		
		mode.addAttribute("value_name", mapdata.get("value_name"));
		
		mode.addAttribute("is_stop", mapdata.get("is_stop"));
		
		mode.addAttribute("is_carry", mapdata.get("is_carry"));
		
		return "hrp/budg/project/information/hosProjUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/updateHosProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHosProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   
	  
		String hosProjJson = hosProjService.update(mapVo);
		
		return JSONObject.parseObject(hosProjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/addOrUpdateHosProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateHosProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String hosProjJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
		
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("").toString()));
	  
		hosProjJson = hosProjService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(hosProjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/deleteHosProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHosProj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("proj_id", ids[2]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String hosProjJson = hosProjService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(hosProjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/queryHosProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		String hosProj = hosProjService.query(getPage(mapVo));

		return JSONObject.parseObject(hosProj);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/hosProjImportPage", method = RequestMethod.GET)
	public String hosProjImportPage(Model mode) throws Exception {

		return "hrp/budg/project/information/hosProjImport";

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
	 @RequestMapping(value="hrp/budg/project/information/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\project","项目信息.xls");
	    
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
	@RequestMapping(value="/hrp/budg/project/information/readHosProjFiles",method = RequestMethod.POST)  
    public String readHosProjFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<HosProj> list_err = new ArrayList<HosProj>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				HosProj hosProj = new HosProj();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					hosProj.setProj_code(temp[0]);
		    		mapVo.put("proj_code", temp[0]);
					
					} else {
						
						err_sb.append("项目编码为空 ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					hosProj.setProj_name(temp[1]);
		    		mapVo.put("proj_name", temp[1]);
					
					} else {
						
						err_sb.append("项目名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					hosProj.setType_code(temp[2]);
		    		mapVo.put("type_code", temp[2]);
					
					} else {
						
						err_sb.append("项目类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {						
						hosProj.setProj_simple(temp[3]);
			    		mapVo.put("proj_simple", temp[3]);					
					} else {						
						err_sb.append("项目简称为空  ");						
					}
					if (StringTool.isNotBlank(temp[4])) {						
						mapVo.put("level_code", temp[4]);						
						} 
					if (StringTool.isNotBlank(temp[5])) {						
						mapVo.put("use_code", temp[5]);					
					} 
					 
					if (StringTool.isNotBlank(temp[6])) {						
						mapVo.put("con_emp_id", temp[6]);					
					} 
					if (StringTool.isNotBlank(temp[7])) {						
						mapVo.put("con_phone", temp[7]);						
					} 
					if (StringTool.isNotBlank(temp[8])) {						
						mapVo.put("acc_emp_id", temp[8]);						
					} 
					if (StringTool.isNotBlank(temp[9])) {						
						mapVo.put("acc_phone", temp[9]);						
					} 
					if (StringTool.isNotBlank(temp[10])) {						
						mapVo.put("app_emp_id", temp[10]);						
					} 
					if (StringTool.isNotBlank(temp[11])) {						
						mapVo.put("dept_id", temp[11]);						
					} 
					if (StringTool.isNotBlank(temp[12])) {						
						mapVo.put("app_date", temp[12]);						
					} 
					if (StringTool.isNotBlank(temp[13])) {						
						mapVo.put("app_phone", temp[13]);						
					} 
					if (StringTool.isNotBlank(temp[14])) {						
						mapVo.put("email", temp[14]);						
					} 
					if (StringTool.isNotBlank(temp[15])) {						
						mapVo.put("note", temp[15]);						
					} 
					if (StringTool.isNotBlank(temp[16])) {						
						mapVo.put("set_up_date", temp[16]);						
					} 
					if (StringTool.isNotBlank(temp[17])) {						
						mapVo.put("complete_date", temp[17]);						
					} 
					if (StringTool.isNotBlank(temp[18])) {						
						mapVo.put("pay_end_date", temp[18]);						
					} 
					if (StringTool.isNotBlank(temp[19])) {						
						mapVo.put("sespend_date", temp[19]);						
					} 
					 
					if (StringTool.isNotBlank(temp[20])) {	
						mapVo.put("proj_state", temp[20]);					
					} else {						
						err_sb.append("项目状态为空  ");						
					}
					 
					if (StringTool.isNotBlank(temp[21])) {
						
					hosProj.setIs_stop(Integer.parseInt(temp[21]));
		    		mapVo.put("is_stop", temp[21]);
					
					} else {
						
						err_sb.append("项目是否停用为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[22])) {
						
						mapVo.put("is_carry", temp[22]);
						
					} else {
							
							err_sb.append("项目是否结转为空  ");
							
					}
					  
					 
					
				HosProj data_exc_extis = hosProjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					hosProj.setError_type(err_sb.toString());
					
					list_err.add(hosProj);
					
				} else {
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("proj_name").toString()));
			  
					String dataJson = hosProjService.add(mapVo);
					System.out.println(dataJson);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			HosProj data_exc = new HosProj();
			
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
	@RequestMapping(value = "/hrp/budg/project/information/addBatchHosProj", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchHosProj(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<HosProj> list_err = new ArrayList<HosProj>();
		
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
			
			HosProj hosProj = new HosProj();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					if (StringTool.isNotBlank(jsonObj.get("proj_id"))) {
						
					hosProj.setProj_id(Double.valueOf((String)jsonObj.get("proj_id")));
		    		mapVo.put("proj_id", jsonObj.get("proj_id"));
		    		} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_code"))) {
						
					hosProj.setProj_code((String)jsonObj.get("proj_code"));
		    		mapVo.put("proj_code", jsonObj.get("proj_code"));
		    		} else {
						
						err_sb.append("项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					hosProj.setType_code((String)jsonObj.get("type_code"));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_name"))) {
						
					hosProj.setProj_name((String)jsonObj.get("proj_name"));
		    		mapVo.put("proj_name", jsonObj.get("proj_name"));
		    		} else {
						
						err_sb.append("项目名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_simple"))) {
						
					hosProj.setProj_simple((String)jsonObj.get("proj_simple"));
		    		mapVo.put("proj_simple", jsonObj.get("proj_simple"));
		    		} else {
						
						err_sb.append("项目简称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sort_code"))) {
						
					hosProj.setSort_code(Long.valueOf((String)jsonObj.get("sort_code")));
		    		mapVo.put("sort_code", jsonObj.get("sort_code"));
		    		} else {
						
						err_sb.append("排序号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					hosProj.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					hosProj.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					hosProj.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					hosProj.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					
				HosProj data_exc_extis = hosProjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					hosProj.setError_type(err_sb.toString());
					
					list_err.add(hosProj);
					
				} else {
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = hosProjService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			HosProj data_exc = new HosProj();
			
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
	 * 项目解题主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/information/budgNote", method = RequestMethod.GET)
	public String budgNoteMainPage(Model mode) throws Exception {

		return "hrp/budg/project/information/budgNoteMain";

	}
	
	
	/**
	 * @Description 
	 * 解题 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/information/endHosProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> endHosProj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id",SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("complete_per", SessionManager.getUserId())   ;
			mapVo.put("complete_date", time)   ;
			mapVo.put("proj_state", ids[3]);
			mapVo.put("proj_id", ids[4]);
			
			listVo.add(mapVo);
			
		}
		
		String budgProjSetUpJson = hosProjService.endHosProj(listVo);
		
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	/**
	 * @Description 
	 * 解题 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/information/escEndHosProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> escEndHosProj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id",SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("complete_per", SessionManager.getUserId())   ;
			mapVo.put("complete_date", time)   ;
			mapVo.put("proj_state", ids[3]);
			mapVo.put("proj_id", ids[4]);
			
			listVo.add(mapVo);
			
		}
		
		String budgProjSetUpJson = hosProjService.escEndProj(listVo);
		
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	
	/**
	 * @Description 
	 * 解题 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/information/suspendHosProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> suspendHosProj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id",SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("complete_per", SessionManager.getUserId())   ;
			mapVo.put("complete_date", time)   ;
			mapVo.put("proj_state", ids[3]);
			mapVo.put("proj_id", ids[4]);
			
			listVo.add(mapVo);
			
		}
		
		String budgProjSetUpJson = hosProjService.suspendHosProj(listVo);
		
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	/**
	 * @Description 
	 * 解题 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/information/escSuspendHosProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> escSuspendHosProj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id",SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("complete_per", SessionManager.getUserId())   ;
			mapVo.put("complete_date", time)   ;
			mapVo.put("proj_state", ids[3]);
			mapVo.put("proj_id", ids[4]);
			
			listVo.add(mapVo);
			
		}
		
		String budgProjSetUpJson = hosProjService.escSuspendHosProj(listVo);
		
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	
    
}

