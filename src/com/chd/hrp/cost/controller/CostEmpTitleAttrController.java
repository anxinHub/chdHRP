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
import com.chd.hrp.cost.entity.CostEmpTitleAttr;
import com.chd.hrp.cost.serviceImpl.CostEmpTitleAttrServiceImpl;

/**
* @Title. @Description.
* 成本_职工职称字典表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostEmpTitleAttrController extends BaseController{
	private static Logger logger = Logger.getLogger(CostEmpTitleAttrController.class);
	
	
	@Resource(name = "costEmpTitleAttrService")
	private final CostEmpTitleAttrServiceImpl costEmpTitleAttrService = null;
   
   
	/**
	*成本_职工职称字典表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costemptitleattr/costEmpTitleAttrMainPage", method = RequestMethod.GET)
	public String costEmpTitleAttrMainPage(Model mode) throws Exception {

		return "hrp/cost/costemptitleattr/costEmpTitleAttrMain";

	}
	/**
	*成本_职工职称字典表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costemptitleattr/costEmpTitleAttrAddPage", method = RequestMethod.GET)
	public String costEmpTitleAttrAddPage(Model mode) throws Exception {

		return "hrp/cost/costemptitleattr/costEmpTitleAttrAdd";

	}
	/**
	*成本_职工职称字典表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costemptitleattr/addCostEmpTitleAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostEmpTitleAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
		//根据名称name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
		String costEmpTitleAttrJson = costEmpTitleAttrService.addCostEmpTitleAttr(mapVo);

		return JSONObject.parseObject(costEmpTitleAttrJson);
		
	}
	/**
	*成本_职工职称字典表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costemptitleattr/queryCostEmpTitleAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostEmpTitleAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costEmpTitleAttr = costEmpTitleAttrService.queryCostEmpTitleAttr(getPage(mapVo));

		return JSONObject.parseObject(costEmpTitleAttr);
		
	}
	/**
	*成本_职工职称字典表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costemptitleattr/deleteCostEmpTitleAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostEmpTitleAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("title_code",ids[3]); 
            listVo.add(mapVo);
        }
		String costEmpTitleAttrJson = costEmpTitleAttrService.deleteBatchCostEmpTitleAttr(listVo);
	   return JSONObject.parseObject(costEmpTitleAttrJson);
			
	}
	
	/**
	*成本_职工职称字典表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costemptitleattr/costEmpTitleAttrUpdatePage", method = RequestMethod.GET)
	
	public String costEmpTitleAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostEmpTitleAttr costEmpTitleAttr = new CostEmpTitleAttr();
		costEmpTitleAttr = costEmpTitleAttrService.queryCostEmpTitleAttrByCode(mapVo);
		mode.addAttribute("group_id", costEmpTitleAttr.getGroup_id());
		mode.addAttribute("hos_id", costEmpTitleAttr.getHos_id());
		mode.addAttribute("copy_code", costEmpTitleAttr.getCopy_code());
		mode.addAttribute("title_code", costEmpTitleAttr.getTitle_code());
		mode.addAttribute("title_name", costEmpTitleAttr.getTitle_name());
		mode.addAttribute("note", costEmpTitleAttr.getNote());
		mode.addAttribute("spell_code", costEmpTitleAttr.getSpell_code());
		mode.addAttribute("wbx_code", costEmpTitleAttr.getWbx_code());
		return "hrp/cost/costemptitleattr/costEmpTitleAttrUpdate";
	}
	/**
	*成本_职工职称字典表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costemptitleattr/updateCostEmpTitleAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostEmpTitleAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
		//根据名称name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
		
		String costEmpTitleAttrJson = costEmpTitleAttrService.updateCostEmpTitleAttr(mapVo);
		
		return JSONObject.parseObject(costEmpTitleAttrJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costemptitleattr/costEmpTitleAttrImportPage", method = RequestMethod.GET)
	public String costEmpTitleAttrImportPage(Model mode) throws Exception {

		return "hrp/cost/costemptitleattr/costEmpTitleAttrImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costemptitleattr/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","职工职称.xls");
	    return null; 
	 }
	 
	/**
	*成本_职工职称字典表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costemptitleattr/readCostEmpTitleAttrFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostEmpTitleAttr> list_err = new ArrayList<CostEmpTitleAttr>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostEmpTitleAttr costEmpTitleAttr = new CostEmpTitleAttr();
				
				String temp[] = list.get(i);// 行
				
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
					if (StringUtils.isNotEmpty(temp[0])) {
						
						costEmpTitleAttr.setTitle_code(temp[0]);
						
						mapVo.put("title_code", temp[0]);
						
					} else {
						err_sb.append("职称编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costEmpTitleAttr.setTitle_name(temp[1]);
						
						mapVo.put("title_name", temp[1]);
					} else {
						err_sb.append("职称姓名为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costEmpTitleAttr.setNote(temp[2]);
						
						mapVo.put("note", temp[2]);
					} else {
						err_sb.append("备注为空  ");
					}
				CostEmpTitleAttr data_exc_extis = costEmpTitleAttrService.queryCostEmpTitleAttrByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costEmpTitleAttr.setError_type(err_sb.toString());
					
					list_err.add(costEmpTitleAttr);
				} else {
						
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostEmpTitleAttr data_exc = new CostEmpTitleAttr();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costEmpTitleAttrService.addBatchCostEmpTitleAttr(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*成本_职工职称字典表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costemptitleattr/addBatchCostEmpTitleAttr", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostEmpTitleAttr(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostEmpTitleAttr> list_err = new ArrayList<CostEmpTitleAttr>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			StringBuffer err_sb = new StringBuffer();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			mapVo.put("title_code", jsonObj.get("title_code"));
			
			mapVo.put("title_name", jsonObj.get("title_name"));
			
			mapVo.put("note", jsonObj.get("note"));
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
			
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
			
		   
				CostEmpTitleAttr data_exc_extis = costEmpTitleAttrService.queryCostEmpTitleAttrByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostEmpTitleAttr costEmpTitleAttr = new CostEmpTitleAttr();
				
				if (err_sb.toString().length() > 0) {
					costEmpTitleAttr.setTitle_code(mapVo.get("title_code").toString());
					
					costEmpTitleAttr.setTitle_name(mapVo.get("title_name").toString());
					
					costEmpTitleAttr.setNote(mapVo.get("note").toString());
					
					
					costEmpTitleAttr.setError_type(err_sb.toString());
					
					list_err.add(costEmpTitleAttr);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costEmpTitleAttrService.addBatchCostEmpTitleAttr(list_batch);
				
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

