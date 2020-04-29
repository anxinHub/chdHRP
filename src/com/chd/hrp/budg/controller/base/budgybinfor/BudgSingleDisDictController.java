
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgSingleDiseaseDict;
import com.chd.hrp.budg.service.base.budgybinfor.BudgSingleDisDictService;
/**
 * 
 * @Description:
 * 医保单病种字典
 * @Table:
 * SINGLE_DISEASE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class BudgSingleDisDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgSingleDisDictController.class);
	
	//引入Service服务
	@Resource(name = "budgSingleDisDictService")
	private final BudgSingleDisDictService budgSingleDisDictService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgsingledisdict/budgSingleDisDictMainPage", method = RequestMethod.GET)
	public String budgSingleDisDictMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgybinfor/budgsingledisdict/budgSingleDisDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgsingledisdict/budgSingleDisDictAddPage", method = RequestMethod.GET)
	public String budgSingleDisDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgybinfor/budgsingledisdict/budgSingleDisDictAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医保单病种字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgsingledisdict/addBudgSingleDisDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addbudgsingledisdict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(String.valueOf(mapVo.get("disease_name"))));
		mapVo.put("wbx_code", StringTool.toWuBi(String.valueOf(mapVo.get("disease_name"))));
       
		String budgsingledisdictJson = budgSingleDisDictService.add(mapVo);

		return JSONObject.parseObject(budgsingledisdictJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 医保单病种字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgsingledisdict/budgSingleDisDictUpdatePage", method = RequestMethod.GET)
	public String budgSingleDisDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		
		Map<String, Object> budgSingleDisDict = new HashMap<String, Object>();
    
		budgSingleDisDict = budgSingleDisDictService.queryByCode(mapVo);
		
		mode.addAttribute("budgSingleDisDict", budgSingleDisDict);
		
		return "hrp/budg/base/budgybinfor/budgsingledisdict/budgSingleDisDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医保单病种字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgsingledisdict/updateBudgSingleDisDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgSingleDisDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("disease_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("disease_name").toString()));
		
		String budgsingledisdictJson = budgSingleDisDictService.update(mapVo);
		
		return JSONObject.parseObject(budgsingledisdictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医保单病种字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgsingledisdict/deleteBudgSingleDisDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgSingleDisDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {		
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("disease_code", ids[3]);
	
			listVo.add(mapVo);
	      
	    }
		String budgsingledisdictJson = budgSingleDisDictService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgsingledisdictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医保单病种字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgsingledisdict/queryBudgSingleDisDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgSingleDisDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		
		String budgsingledisdict = budgSingleDisDictService.query(getPage(mapVo));

		return JSONObject.parseObject(budgsingledisdict);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 医保单病种字典
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgsingledisdict/budgSingleDisDictImportPage", method = RequestMethod.GET)
	public String budgSingleDisDictImportPage(Model mode) throws Exception {
		return "hrp/budg/base/budgybinfor/budgsingledisdict/budgSingleDisDictImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医保单病种字典
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/budg/base/budgybinfor/budgsingledisdict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,HttpServletResponse response,Model mode) throws IOException { 	    			
	    printTemplate(request,response,"budg\\base","医保单病种字典模版.xls");	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医保单病种字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgybinfor/budgsingledisdict/readBudgSingleDisDictFiles",method = RequestMethod.POST)  
    public String readBudgSingleDisDictFiles(Plupload plupload,HttpServletRequest request,HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgSingleDiseaseDict> list_err = new ArrayList<BudgSingleDiseaseDict>();	
		List<String[]> list = UploadUtil.readFile(plupload, request, response);	
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {				
				StringBuffer err_sb = new StringBuffer();				
				BudgSingleDiseaseDict budgsingledisdict = new BudgSingleDiseaseDict();				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());	
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0])){
						err_sb.append("第"+i+"行数据与第 "+j+"行病种编码重复;");
					}
					if(temp[1].equals(error[1])){
						err_sb.append("第"+i+"行数据与第 "+j+"行病种名称重复;");
					}	
				}
				
				if (StringTool.isNotBlank(temp[0])) {						
					budgsingledisdict.setDisease_code(temp[0]);		
					mapVo.put("disease_code", temp[0]);	
					Map<String, Object> map = budgSingleDisDictService.queryByCode(mapVo);
					if(map != null){
						err_sb.append("病种编码已被占用;");
					}
				} else {						
					err_sb.append("病种编码为空;");						
				}
					
				if (StringTool.isNotBlank(temp[1])) {						
					budgsingledisdict.setDisease_name(temp[1]);		
					mapVo.put("disease_name", temp[1]);
					int count = budgSingleDisDictService.queryNameExist(mapVo);
					if(count > 0 ){
						err_sb.append("病种名称已被占用;");
					}
				} else {					
					err_sb.append("病种名称为空  ");						
				}
					
				if (StringTool.isNotBlank(temp[2])) {						
					budgsingledisdict.setIs_stop(Integer.valueOf(temp[2]));								
					mapVo.put("is_stop", temp[2]);		    		
				} else {						
					err_sb.append("是否停用为空  ");						
				}
					
				
				if (err_sb.toString().length() > 0) {					
					budgsingledisdict.setError_type(err_sb.toString());					
					list_err.add(budgsingledisdict);					
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(String.valueOf(mapVo.get("disease_name"))));
					mapVo.put("wbx_code", StringTool.toWuBi(String.valueOf(mapVo.get("disease_name"))));	
					addList.add(mapVo);  
										
				}				
			}
			if(list_err.size() == 0){
				String dataJson = budgSingleDisDictService.addBatch(addList);
			}
		} catch (DataAccessException e) {			
			BudgSingleDiseaseDict data_exc = new BudgSingleDiseaseDict();			
			data_exc.setError_type("导入系统出错");			
			list_err.add(data_exc);			
		}		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));		
		return null;		
    } 
   
    
}

