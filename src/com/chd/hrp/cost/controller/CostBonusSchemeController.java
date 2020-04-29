/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.util.*;
import javax.annotation.Resource;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostBonusScheme;
import com.chd.hrp.cost.serviceImpl.CostBonusSchemeServiceImpl;

/**
* @Title. @Description.
* 职工奖金查询方案
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostBonusSchemeController extends BaseController{
	private static Logger logger = Logger.getLogger(CostBonusSchemeController.class);
	
	
	@Resource(name = "costBonusSchemeService")
	private final CostBonusSchemeServiceImpl costBonusSchemeService = null;
   
   
	/**
	*职工奖金查询方案<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costbonusscheme/costBonusSchemeMainPage", method = RequestMethod.GET)
	public String costBonusSchemeMainPage(Model mode) throws Exception {

		return "hrp/cost/costbonusscheme/costBonusSchemeMain";

	}
	/**
	*职工奖金查询方案<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costbonusscheme/costBonusSchemeAddPage", method = RequestMethod.GET)
	public String costBonusSchemeAddPage(Model mode) throws Exception {

		return "hrp/cost/costbonusscheme/costBonusSchemeAdd";

	}
	/**
	*职工奖金查询方案<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costbonusscheme/addCostBonusScheme", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostBonusScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
		
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
		String costBonusSchemeJson = costBonusSchemeService.addCostBonusScheme(mapVo);

		return JSONObject.parseObject(costBonusSchemeJson);
		
	}
	/**
	*职工奖金查询方案<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costbonusscheme/queryCostBonusScheme", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostBonusScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costBonusScheme = costBonusSchemeService.queryCostBonusScheme(getPage(mapVo));

		return JSONObject.parseObject(costBonusScheme);
		
	}
	/**
	*职工奖金查询方案<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costbonusscheme/deleteCostBonusScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostBonusScheme(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("scheme_id",ids[0]); 
			
            listVo.add(mapVo);
        }
		String costBonusSchemeJson = costBonusSchemeService.deleteBatchCostBonusScheme(listVo);
	   return JSONObject.parseObject(costBonusSchemeJson);
			
	}
	
	/**
	*职工奖金查询方案<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costbonusscheme/costBonusSchemeUpdatePage", method = RequestMethod.GET)
	
	public String costBonusSchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostBonusScheme costBonusScheme = new CostBonusScheme();
		costBonusScheme = costBonusSchemeService.queryCostBonusSchemeByCode(mapVo);
		mode.addAttribute("scheme_id", costBonusScheme.getScheme_id());
		
		mode.addAttribute("scheme_name", costBonusScheme.getScheme_name());
		
		mode.addAttribute("group_id", costBonusScheme.getGroup_id());
		
		mode.addAttribute("hos_id", costBonusScheme.getHos_id());
		
		mode.addAttribute("copy_code", costBonusScheme.getCopy_code());
		
		mode.addAttribute("spell_code", costBonusScheme.getSpell_code());
		
		mode.addAttribute("wbx_code", costBonusScheme.getWbx_code());
		
		return "hrp/cost/costbonusscheme/costBonusSchemeUpdate";
	}
	/**
	*职工奖金查询方案<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costbonusscheme/updateCostBonusScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostBonusScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
		
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
		
		String costBonusSchemeJson = costBonusSchemeService.updateCostBonusScheme(mapVo);
		
		return JSONObject.parseObject(costBonusSchemeJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costbonusscheme/costBonusSchemeImportPage", method = RequestMethod.GET)
	public String costBonusSchemeImportPage(Model mode) throws Exception {

		return "hrp/cost/costbonusscheme/costBonusSchemeImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costbonusscheme/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				
	    printTemplate(request,response,"cost\\基础设置","职工奖金查询方案.xls");
		
	    return null; 
	 }
	 
	/**
	*职工奖金查询方案<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costbonusscheme/readCostBonusSchemeFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostBonusScheme> list_err = new ArrayList<CostBonusScheme>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostBonusScheme costBonusScheme = new CostBonusScheme();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					if (StringUtils.isNotEmpty(temp[0])) {
						
						costBonusScheme.setScheme_id(Long.valueOf(temp[0]));
						
						mapVo.put("scheme_id", temp[0]);
					} else {
						err_sb.append("方案ID为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costBonusScheme.setScheme_name(temp[1]);
						
						mapVo.put("scheme_name", temp[1]);
					} else {
						err_sb.append("方案名称为空  ");
					}
					if (mapVo.get("group_id") == null) {
						
						mapVo.put("group_id", SessionManager.getGroupId());
					}
					if (mapVo.get("hos_id") == null) {
						
						mapVo.put("hos_id", SessionManager.getHosId());
					}
					if (mapVo.get("copy_code") == null) {
						
						mapVo.put("copy_code", SessionManager.getCopyCode());
					}
				CostBonusScheme data_exc_extis = costBonusSchemeService.queryCostBonusSchemeByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costBonusScheme.setError_type(err_sb.toString());
					
					list_err.add(costBonusScheme);
				} else {
						
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostBonusScheme data_exc = new CostBonusScheme();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costBonusSchemeService.addBatchCostBonusScheme(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*职工奖金查询方案<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costbonusscheme/addBatchCostBonusScheme", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostBonusScheme(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostBonusScheme> list_err = new ArrayList<CostBonusScheme>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			StringBuffer err_sb = new StringBuffer();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			mapVo.put("scheme_id", jsonObj.get("scheme_id"));
			
			mapVo.put("scheme_name", jsonObj.get("scheme_name"));
			
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
			
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
			
		   
				CostBonusScheme data_exc_extis = costBonusSchemeService.queryCostBonusSchemeByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostBonusScheme costBonusScheme = new CostBonusScheme();
				
				if (err_sb.toString().length() > 0) {
					costBonusScheme.setScheme_id(Long.valueOf(mapVo.get("scheme_id").toString()));
					
					costBonusScheme.setScheme_name(mapVo.get("scheme_name").toString());
					
					
					costBonusScheme.setError_type(err_sb.toString());
					
					list_err.add(costBonusScheme);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costBonusSchemeService.addBatchCostBonusScheme(list_batch);
				
			}
		} catch (DataAccessException e) {
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
    }
}

