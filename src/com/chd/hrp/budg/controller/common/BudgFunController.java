/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
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
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgFun;
import com.chd.hrp.budg.service.common.BudgFunParaService;
import com.chd.hrp.budg.service.common.BudgFunService;
/**
 * 
 * @Description:
 * 预算相关函数表
 * @Table:
 * BUDG_FUN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgFunController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgFunController.class);
	
	//引入Service服务
	@Resource(name = "budgFunService")
	private final BudgFunService budgFunService = null;
	
	@Resource(name = "budgFunParaService")
	private final BudgFunParaService budgFunParaService = null;
	
	
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/budgFunMainPage", method = RequestMethod.GET)
	public String budgFunMainPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfun/budgFunMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/budgFunAddPage", method = RequestMethod.GET)
	public String budgFunAddPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfun/budgFunAdd";

	}
	
	/**
	 * 选择 取值函数页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/budgFunPage", method = RequestMethod.GET)
	public String budgFunPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("index_code", mapVo.get("index_code")) ;
		
		mode.addAttribute("index_type_code", mapVo.get("index_type_code")) ;
		
		return "hrp/budg/common/budgfun/budgFun";

	}
	
	/**
	 * 业务预算  选择 取值函数页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/budgFunBusinessPage", method = RequestMethod.GET)
	public String budgFunBusinessPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("index_code", mapVo.get("index_code")) ;
		
		mode.addAttribute("budg_year", mapVo.get("budg_year")) ;
		
		mode.addAttribute("budg_level", mapVo.get("budg_level")) ;
		
		mode.addAttribute("index_type_code", mapVo.get("index_type_code")) ;
		
		return "hrp/budg/common/budgfun/budgFunBusiness";

	}
	
	/**
	 * 收入预算  选择 取值函数页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/budgFunIncomePage", method = RequestMethod.GET)
	public String budgFunIncomePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("index_code", mapVo.get("index_code")) ;
		
		mode.addAttribute("budg_year", mapVo.get("budg_year")) ;
		
		//mode.addAttribute("budg_level", mapVo.get("budg_level")) ;
		
		mode.addAttribute("index_type_code", mapVo.get("index_type_code")) ;
		
		return "hrp/budg/common/budgfun/budgFunIncome";

	}
	
	/**
	 * @Description 
	 * 添加数据 预算相关函数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/addBudgFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgFunJson = null ;
		
		try {
			
			budgFunJson = budgFunService.add(mapVo);
			
		} catch (Exception e) {
			
			budgFunJson = e.getMessage();
		}
		return JSONObject.parseObject(budgFunJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 预算相关函数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/budgFunUpdatePage", method = RequestMethod.GET)
	public String budgFunUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		BudgFun budgFun =  budgFunService.queryByCode(mapVo);
		
		
		mode.addAttribute("group_id", budgFun.getGroup_id());
		mode.addAttribute("hos_id", budgFun.getHos_id());
		mode.addAttribute("copy_code", budgFun.getCopy_code());
		mode.addAttribute("fun_code", budgFun.getFun_code());
		mode.addAttribute("fun_name", budgFun.getFun_name());
		mode.addAttribute("type_code", budgFun.getType_code());
		mode.addAttribute("type_name", budgFun.getType_name());
		mode.addAttribute("fun_method_chs", budgFun.getFun_method_chs());
		mode.addAttribute("fun_method_eng", budgFun.getFun_method_eng());
		mode.addAttribute("fun_note", budgFun.getFun_note());
		mode.addAttribute("spell_code", budgFun.getSpell_code());
		mode.addAttribute("wbx_code", budgFun.getWbx_code());
		mode.addAttribute("is_pre", budgFun.getIs_pre());
		mode.addAttribute("is_rec", budgFun.getIs_rec());
		mode.addAttribute("is_stop", budgFun.getIs_stop());
		mode.addAttribute("prc_name", budgFun.getPrc_name());
		if(budgFun.getFun_sql() != null){
			String fun_sql = new String(budgFun.getFun_sql(),"UTF-8");
			mode.addAttribute("fun_sql", fun_sql.replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		}else{
			mode.addAttribute("fun_sql", "");
		}
		mode.addAttribute("pkg_name", budgFun.getPkg_name());
		
		return "hrp/budg/common/budgfun/budgFunUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 预算相关函数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/updateBudgFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fun_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("fun_name").toString()));
		  
	  
		String budgFunJson =  null;
		try {
			
			budgFunJson = budgFunService.update(mapVo);
			
		} catch (Exception e) {
			
			budgFunJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(budgFunJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 预算相关函数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/addOrUpdateBudgFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgFunJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("").toString()));
	  
		budgFunJson = budgFunService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgFunJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 预算相关函数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/deleteBudgFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgFun(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("fun_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgFunJson = budgFunService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgFunJson);
			
	}
	
	/**
	 * 删除 函数参数数据
	 * @param ParamVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/deleteBudgFunParaByFunCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgFunParaByFunCode(@RequestParam(value = "ParamVo") String ParamVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : ParamVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("fun_code", ids[3]);
			
			mapVo.put("para_code", ids[4]);

			listVo.add(mapVo);

		}
		String prmFunJson = budgFunParaService.deleteBatch(listVo);

		return JSONObject.parseObject(prmFunJson);

	}
	
	/**
	 * @Description 
	 * 查询数据 预算相关函数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/queryBudgFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgFun = budgFunService.query(getPage(mapVo));

		return JSONObject.parseObject(budgFun);
		
	}
	
	/**
	 * @Description 
	 * 根据 函数编码 查询其参数
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/queryBudgFunParaByFunCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgFunParaByFunCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgFun = budgFunService.queryBudgFunParaByFunCode(getPage(mapVo));

		return JSONObject.parseObject(budgFun);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 预算相关函数表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/budgFunImportPage", method = RequestMethod.GET)
	public String budgFunImportPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfun/budgFunImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 预算相关函数表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/common/budgfun/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","预算相关函数表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 预算相关函数表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/common/budgfun/readBudgFunFiles",method = RequestMethod.POST)  
    public String readBudgFunFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgFun> list_err = new ArrayList<BudgFun>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgFun budgFun = new BudgFun();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgFun.setFun_code(temp[3]);
		    		mapVo.put("fun_code", temp[3]);
					
					} else {
						
						err_sb.append("函数编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgFun.setFun_name(temp[4]);
		    		mapVo.put("fun_name", temp[4]);
					
					} else {
						
						err_sb.append("函数名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgFun.setType_code(temp[5]);
		    		mapVo.put("type_code", temp[5]);
					
					} else {
						
						err_sb.append("函数分类编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgFun.setFun_method_chs(temp[6]);
		    		mapVo.put("fun_method_chs", temp[6]);
					
					} else {
						
						err_sb.append("取值函数(中文)为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgFun.setFun_method_eng(temp[7]);
		    		mapVo.put("fun_method_eng", temp[7]);
					
					} else {
						
						err_sb.append("取值函数(英文)为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgFun.setFun_note(temp[8]);
		    		mapVo.put("fun_note", temp[8]);
					
					} else {
						
						err_sb.append("函数说明为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgFun.setSpell_code(temp[9]);
		    		mapVo.put("spell_code", temp[9]);
					
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgFun.setWbx_code(temp[10]);
		    		mapVo.put("wbx_code", temp[10]);
					
					} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgFun.setIs_pre(Integer.valueOf(temp[11]));
		    		mapVo.put("is_pre", temp[11]);
					
					} else {
						
						err_sb.append("0：否 1:是为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgFun.setIs_rec(Integer.valueOf(temp[12]));
		    		mapVo.put("is_rec", temp[12]);
					
					} else {
						
						err_sb.append("0：否 1:是为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					budgFun.setIs_stop(Integer.valueOf(temp[13]));
		    		mapVo.put("is_stop", temp[13]);
					
					} else {
						
						err_sb.append("0：否 1:是为空  ");
						
					}
					 
					
				Map<String,Object> data_exc_extis = budgFunService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgFun.setError_type(err_sb.toString());
					
					list_err.add(budgFun);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = budgFunService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgFun data_exc = new BudgFun();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 预算相关函数表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/addBatchBudgFun", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgFun(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgFun> list_err = new ArrayList<BudgFun>();
		
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
			
			BudgFun budgFun = new BudgFun();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("fun_code"))) {
						
					budgFun.setFun_code((String)jsonObj.get("fun_code"));
		    		mapVo.put("fun_code", jsonObj.get("fun_code"));
		    		} else {
						
						err_sb.append("函数编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_name"))) {
						
					budgFun.setFun_name((String)jsonObj.get("fun_name"));
		    		mapVo.put("fun_name", jsonObj.get("fun_name"));
		    		} else {
						
						err_sb.append("函数名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					budgFun.setType_code((String)jsonObj.get("type_code"));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("函数分类编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_method_chs"))) {
						
					budgFun.setFun_method_chs((String)jsonObj.get("fun_method_chs"));
		    		mapVo.put("fun_method_chs", jsonObj.get("fun_method_chs"));
		    		} else {
						
						err_sb.append("取值函数(中文)为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_method_eng"))) {
						
					budgFun.setFun_method_eng((String)jsonObj.get("fun_method_eng"));
		    		mapVo.put("fun_method_eng", jsonObj.get("fun_method_eng"));
		    		} else {
						
						err_sb.append("取值函数(英文)为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_note"))) {
						
					budgFun.setFun_note((String)jsonObj.get("fun_note"));
		    		mapVo.put("fun_note", jsonObj.get("fun_note"));
		    		} else {
						
						err_sb.append("函数说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					budgFun.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					budgFun.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_pre"))) {
						
					budgFun.setIs_pre(Integer.valueOf((String)jsonObj.get("is_pre")));
		    		mapVo.put("is_pre", jsonObj.get("is_pre"));
		    		} else {
						
						err_sb.append("0：否 1:是为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_rec"))) {
						
					budgFun.setIs_rec(Integer.valueOf((String)jsonObj.get("is_rec")));
		    		mapVo.put("is_rec", jsonObj.get("is_rec"));
		    		} else {
						
						err_sb.append("0：否 1:是为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					budgFun.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("0：否 1:是为空  ");
						
					}
					
					
					Map<String,Object> data_exc_extis = budgFunService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgFun.setError_type(err_sb.toString());
					
					list_err.add(budgFun);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = budgFunService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgFun data_exc = new BudgFun();
			
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
	 * 函数类型树
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/queryFunTypeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFunTypeTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgFun = budgFunService.queryFunTypeTree(getPage(mapVo));

		return JSONObject.parseObject(budgFun);
		
	}
    
	/**
	 * 函数参数 部件类型 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/queryBudgComType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String fun = budgFunService.queryBudgComType(mapVo);
		return fun;

	}
	
	/**
	 * 重新加载 存储过程
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/initBudgFunProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initBudgFunProc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String prmFun = budgFunService.initBudgFunProc(mapVo);

		return JSONObject.parseObject(prmFun);

	}
	
	
	/**
	 * 参数 及其部件类型 信息查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/queryComTypePara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryComTypePara(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode()); 

		String comTypePara = budgFunService.queryComTypePara(mapVo);

		return JSONObject.parseObject(comTypePara);

	}
	
	/**
	 * 函数参数下拉框 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/queryBudgFunParaByDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmFunParaByDict(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		return budgFunService.queryBudgFunParaByDict(mapVo);

	}
	
	/**
	 * 存储 预算指标函数参数栈数据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgfun/saveBudgIndexStack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgIndexStack(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= JsonListMapUtil.getListMap(paramVo);
		
		for ( Map<String,Object> map: listVo) {
			
			//表的主键
			map.put("group_id", SessionManager.getGroupId())   ;
			map.put("hos_id", SessionManager.getHosId())   ;
			map.put("copy_code", SessionManager.getCopyCode())   ;
	    }
	    
		String budgFunJson  = null ;
		
		try {
			
			budgFunJson = budgFunService.saveBudgIndexStack(listVo);
			
		} catch (Exception e) {
			
			budgFunJson = e.getMessage();
		}
		
			
	  return JSONObject.parseObject(budgFunJson);
			
	}
}

