/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.cost.entity.CostDeptNature;
import com.chd.hrp.cost.serviceImpl.CostDeptNatureServiceImpl;

/**
* @Title. @Description.
* 成本习性(01 固定 02 变动)


* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostDeptNatureController extends BaseController{
	private static Logger logger = Logger.getLogger(CostDeptNatureController.class);
	
	
	@Resource(name = "costDeptNatureService")
	private final CostDeptNatureServiceImpl costDeptNatureService = null;
   
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	/**
	*成本习性(01 固定 02 变动)

<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costdeptnature/costDeptNatureMainPage", method = RequestMethod.GET)
	public String costDeptNatureMainPage(Model mode) throws Exception {

		return "hrp/cost/costdeptnature/costDeptNatureMain";

	}
	/**
	*成本习性(01 固定 02 变动)

<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costdeptnature/costDeptNatureAddPage", method = RequestMethod.GET)
	public String costDeptNatureAddPage(Model mode) throws Exception {

		return "hrp/cost/costdeptnature/costDeptNatureAdd";

	}
	/**
	*成本习性(01 固定 02 变动)

<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costdeptnature/addCostDeptNature", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称nature_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("nature_name").toString()));
		//根据名称nature_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("nature_name").toString()));
		String costDeptNatureJson = costDeptNatureService.addCostDeptNature(mapVo);

		return JSONObject.parseObject(costDeptNatureJson);
		
	}
	/**
	*成本习性(01 固定 02 变动)

<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costdeptnature/queryCostDeptNature", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costDeptNature = costDeptNatureService.queryCostDeptNature(getPage(mapVo));

		return JSONObject.parseObject(costDeptNature);
		
	}
	/**
	*成本习性(01 固定 02 变动)

<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costdeptnature/deleteCostDeptNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostDeptNature(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_dept_nature", ids[0]);
            
			if(Strings.isNotBlank(str)){
				retErrot = "{\"error\":\"删除失败，选择的成本习性被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
				}
				if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
				}
				if(mapVo.get("copy_code") == null){
		        mapVo.put("copy_code", SessionManager.getCopyCode());
				}
			mapVo.put("nature_id",ids[0]); 
            listVo.add(mapVo);
        }
		String costDeptNatureJson = costDeptNatureService.deleteBatchCostDeptNature(listVo);
	   return JSONObject.parseObject(costDeptNatureJson);
			
	}
	
	/**
	*成本习性(01 固定 02 变动)

<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costdeptnature/costDeptNatureUpdatePage", method = RequestMethod.GET)
	
	public String costDeptNatureUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        CostDeptNature costDeptNature = new CostDeptNature();
		costDeptNature = costDeptNatureService.queryCostDeptNatureByCode(mapVo);
		mode.addAttribute("nature_id", costDeptNature.getNature_id());
		mode.addAttribute("nature_code", costDeptNature.getNature_code());
		mode.addAttribute("nature_name", costDeptNature.getNature_name());
		mode.addAttribute("spell_code", costDeptNature.getSpell_code());
		mode.addAttribute("wbx_code", costDeptNature.getWbx_code());
		return "hrp/cost/costdeptnature/costDeptNatureUpdate";
	}
	/**
	*成本习性(01 固定 02 变动)

<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costdeptnature/updateCostDeptNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称nature_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("nature_name").toString()));
		//根据名称nature_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("nature_name").toString()));
		
		String costDeptNatureJson = costDeptNatureService.updateCostDeptNature(mapVo);
		
		return JSONObject.parseObject(costDeptNatureJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costdeptnature/costDeptNatureImportPage", method = RequestMethod.GET)
	public String costDeptNatureImportPage(Model mode) throws Exception {

		return "hrp/cost/costdeptnature/costDeptNatureImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costdeptnature/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","成本习性.xls");
	    return null; 
	 }
	 
	/**
	*成本习性(01 固定 02 变动)

<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costdeptnature/readCostDeptNatureFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostDeptNature> list_err = new ArrayList<CostDeptNature>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostDeptNature costDeptNature = new CostDeptNature();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					if (StringUtils.isNotEmpty(temp[0])) {
						
						costDeptNature.setNature_code(temp[0]);
						
						mapVo.put("nature_code", temp[0]);
					} else {
						err_sb.append("成本习性编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costDeptNature.setNature_name(temp[1]);
						
						mapVo.put("nature_name", temp[1]);
					} else {
						err_sb.append("成本习性名称为空  ");
					}
				CostDeptNature data_exc_extis = costDeptNatureService.queryCostDeptNatureByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costDeptNature.setError_type(err_sb.toString());
					
					list_err.add(costDeptNature);
				} else {
						
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("nature_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("nature_name").toString()));
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostDeptNature data_exc = new CostDeptNature();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costDeptNatureService.addBatchCostDeptNature(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*成本习性(01 固定 02 变动)

<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costdeptnature/addBatchCostDeptNature", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostDeptNature(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostDeptNature> list_err = new ArrayList<CostDeptNature>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			StringBuffer err_sb = new StringBuffer();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			mapVo.put("nature_code", jsonObj.get("nature_code"));
			
			mapVo.put("nature_name", jsonObj.get("nature_name"));
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("nature_name").toString()));
			
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("nature_name").toString()));
			
		   
				CostDeptNature data_exc_extis = costDeptNatureService.queryCostDeptNatureByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				
				CostDeptNature costDeptNature = new CostDeptNature();
				
				if (err_sb.toString().length() > 0) {
					
					costDeptNature.setNature_code(mapVo.get("nature_code").toString());
					
					costDeptNature.setNature_name(mapVo.get("nature_name").toString());
					
					costDeptNature.setError_type(err_sb.toString());
					
					list_err.add(costDeptNature);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costDeptNatureService.addBatchCostDeptNature(list_batch);
				
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

