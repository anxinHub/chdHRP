
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.controller.base.budgybinfor;
import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.hrp.budg.entity.BudgYbTypeDict;
import com.chd.hrp.budg.service.base.budgybinfor.BudgYBTypeService;

/**
 * 
 * @Description:
 * 医保类型
 * @Table:
 * budg_yb_type
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class BudgYBTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgYBTypeController.class);
	
	//引入Service服务
	@Resource(name = "budgYBTypeService")
	private final BudgYBTypeService budgYBTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybtype/budgYBtypeMainPage", method = RequestMethod.GET)
	public String budgybtypeMainPage(Model mode) throws Exception {
		
		return "hrp/budg/base/budgybinfor/budgybtype/budgYBTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybtype/budgYBTypeAddPage", method = RequestMethod.GET)
	public String budgYBTypeAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgybinfor/budgybtype/budgYBTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医保类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybtype/addBudgYBType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgYBType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("insurance_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("insurance_name").toString()));
       
		String budgybtype = budgYBTypeService.add(mapVo);

		return JSONObject.parseObject(budgybtype);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 医保类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybtype/budgYBTypeUpdatePage", method = RequestMethod.GET)
	public String budgYBTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> budgYBTypeMap = new HashMap<String,Object>();
		budgYBTypeMap = budgYBTypeService.queryByCode(mapVo);	
		mode.addAttribute("budgYBTypeMap", budgYBTypeMap);
		
		return "hrp/budg/base/budgybinfor/budgybtype/budgYBTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医保类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybtype/updateBudgYBType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgYBType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("insurance_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("insurance_name").toString()));
		
		String budgybtypeJson = budgYBTypeService.update(mapVo);
		
		return JSONObject.parseObject(budgybtypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医保类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybtype/deleteBudgYBType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgYBType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("insurance_code", ids[3]);
				
				listVo.add(mapVo);
			}
			String budgybtypeJson = budgYBTypeService.deleteBatch(listVo);
			
			return JSONObject.parseObject(budgybtypeJson);	
	}
	
	/**
	 * @Description 
	 * 查询数据 医保类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybtype/queryBudgYBType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgYBType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("insurance_code") != null){
			mapVo.put("insurance_code", String.valueOf(mapVo.get("insurance_code")).toUpperCase());
		}
		
		String budgybtype = budgYBTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgybtype);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 医保类型
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybtype/budgYBTypeImportPage", method = RequestMethod.GET)
	public String budgYBTypeImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgybinfor/budgybtype/budgYBTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医保类型
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/budg/base/budgybinfor/budgybtype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","医保类型维护模版.xls");
	    
	    return null; 
	 }
	
	 @RequestMapping(value="/hrp/budg/base/budgybinfor/budgybtype/readBudgYBTypeFiles",method = RequestMethod.POST)  
	    public String readDeptGroupDictFiles(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				 
			List<BudgYbTypeDict> list_err = new ArrayList<BudgYbTypeDict>();
			
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			try {
				for (int i = 1; i < list.size(); i++) {
					
					StringBuffer err_sb = new StringBuffer();
					
					BudgYbTypeDict ybType = new BudgYbTypeDict();
					
					String temp[] = list.get(i);// 行
					Map<String, Object> mapVo = new HashMap<String, Object>();
					mapVo.put("group_id", SessionManager.getGroupId());
					mapVo.put("hos_id", SessionManager.getHosId());
					mapVo.put("copy_code", SessionManager.getCopyCode());
					
					for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])){
							err_sb.append("第"+i+"行数据与第 "+j+"行医保类型编码重复;");
						}
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行医保类型名称重复;");
						}	
					}
						if (StringTool.isNotBlank(temp[0])) {
							
							ybType.setInsurance_code(temp[0]);
									
			    		mapVo.put("insurance_code", temp[0]);
			    		
						} else {
							
							err_sb.append("医保类型编码为空;");
							
						}
						
						if (StringTool.isNotBlank(temp[1])) {
							
							ybType.setInsurance_name(temp[1]);
									
			    		mapVo.put("insurance_name", temp[1]);
			    		
						} else {
							
							err_sb.append("医保类型名称为空 ;");
							
						}
						
						if (StringTool.isNotBlank(temp[2])) {
							
							ybType.setInsurance_nature(temp[2]);
									
							mapVo.put("insurance_nature", temp[2]);
							
							int count  = budgYBTypeService.quryYBTypeNatureExist(mapVo);
							if(count == 0){
								err_sb.append("医保类型性质编码不存在;");
							}
						} else {
							
							err_sb.append("医保类型性质为空 ;");
							
						}
						
						if (StringTool.isNotBlank(temp[3])) {
							
							ybType.setIs_stop(Integer.valueOf(temp[3]));
									
			    		mapVo.put("is_stop", temp[3]);
			    		
						} else {
							
							err_sb.append("是否停用（0:不停用 1:停用）为空 ;");
							
						}
						
					List<BudgYbTypeDict> data_exc_extis = budgYBTypeService.queryByCode(mapVo);
					
					if (data_exc_extis != null) {
						
						err_sb.append("医保类型编码已被占用;");
						
					}
					
					int count = budgYBTypeService.queryNameExist(mapVo);
					
					if(count > 0){
						err_sb.append("医保类型名称已被占用;");
					}
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(String.valueOf(mapVo.get("insurance_name"))));
					mapVo.put("wbx_code", StringTool.toWuBi(String.valueOf(mapVo.get("insurance_name"))));
					if (err_sb.toString().length() > 0) {
						
						ybType.setError_type(err_sb.toString());
						
						list_err.add(ybType);
						
					} else {
					  
						addList.add(mapVo);
						
					}
					
				}
				
				if(list_err.size() == 0){
					String budgybtype = budgYBTypeService.addBatch(addList);
				}
				
			} catch (DataAccessException e) {
				
				BudgYbTypeDict data_exc = new BudgYbTypeDict();
				
				data_exc.setError_type("导入系统出错");
				
				list_err.add(data_exc);
				
			}
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
			
	    }  
	/**
	 * @Description 
	 * 批量添加数据 导入
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgybtype/addBatchBudgYBType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgYBType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{		
		List<BudgYbTypeDict> list_err = new ArrayList<BudgYbTypeDict>();
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
				BudgYbTypeDict budgYBTypt = new BudgYbTypeDict();			
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());						
					if (StringTool.isNotBlank(jsonObj.get("insurance_code"))) {						
						budgYBTypt.setInsurance_code(String.valueOf(jsonObj.get("insurance_code")));			
						mapVo.put("insurance_code", jsonObj.get("insurance_code"));		    		
					} else {						
						err_sb.append("医保类型编码为空！  ");						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("insurance_name"))) {						
						budgYBTypt.setInsurance_name(String.valueOf(jsonObj.get("insurance_name")));											
						mapVo.put("insurance_name", jsonObj.get("insurance_name"));		    		
					} else {						
						err_sb.append("医保类型名称为空！  ");						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {						
						budgYBTypt.setIs_stop(Integer.valueOf(String.valueOf(jsonObj.get("is_stop"))));											
						mapVo.put("is_stop", jsonObj.get("is_stop"));		    		
					} else {					
						err_sb.append("是否停用（0:不停用 1:停用）为空  ");						
					}
					
				List<BudgYbTypeDict> data_exc_extis = budgYBTypeService.queryByCode(mapVo);				
				if (data_exc_extis.size()>0) {				
					err_sb.append("编码或名称重复！");				
				}
				
				if (err_sb.toString().length() > 0) {					
					budgYBTypt.setError_type(err_sb.toString());					
					list_err.add(budgYBTypt);				
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("insurance_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("insurance_name").toString()));			  
					String dataJson = budgYBTypeService.add(mapVo);
				}				
			}			
		} catch (DataAccessException e) {			
			BudgYbTypeDict data_exc = new BudgYbTypeDict();			
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

