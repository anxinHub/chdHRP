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
import com.chd.hrp.cost.entity.CostEmpKindWageItemSet;
import com.chd.hrp.cost.entity.CostEmpTypeAttr;
import com.chd.hrp.cost.entity.CostWageItemArrt;
import com.chd.hrp.cost.serviceImpl.CostEmpKindWageItemSetServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostEmpTypeAttrServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostWageItemArrtServiceImpl;

/**
* @Title. @Description.
* 成本_职工分类工资项配置表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostEmpKindWageItemSetController extends BaseController{
	private static Logger logger = Logger.getLogger(CostEmpKindWageItemSetController.class);
	
	
	@Resource(name = "costEmpKindWageItemSetService")
	private final CostEmpKindWageItemSetServiceImpl costEmpKindWageItemSetService = null;
	
	@Resource(name = "costWageItemArrtService")
	private final CostWageItemArrtServiceImpl costWageItemArrtService = null;
	
	@Resource(name = "costEmpTypeAttrService")
	private final CostEmpTypeAttrServiceImpl costEmpTypeAttrService = null;
   
   
	/**
	*成本_职工分类工资项配置表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/costEmpKindWageItemSetMainPage", method = RequestMethod.GET)
	public String costEmpKindWageItemSetMainPage(Model mode) throws Exception {

		return "hrp/cost/costempkindwageitemset/costEmpKindWageItemSetMain";

	}
	/**
	*成本_职工分类工资项配置表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/costEmpKindWageItemSetAddPage", method = RequestMethod.GET)
	public String costEmpKindWageItemSetAddPage(Model mode) throws Exception {

		return "hrp/cost/costempkindwageitemset/costEmpKindWageItemSetAdd";

	}
	/**
	*成本_职工分类工资项配置表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/addCostEmpKindWageItemSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostEmpKindWageItemSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
        
        String [] ids = mapVo.get("wage_code").toString().split(";");
        
        for (String wage_id : ids) {
        	
        	Map<String, Object> map=new HashMap<String, Object>();
        	
        	map.put("group_id", SessionManager.getGroupId());
        	
        	map.put("hos_id", SessionManager.getHosId());
        	
        	map.put("copy_code", SessionManager.getCopyCode());
        	
        	map.put("emp_kind_code", mapVo.get("emp_kind_code").toString());
        	
        	map.put("wage_item_code", wage_id);
        	
        	listVo.add(map);
			
		}
		
		String costEmpKindWageItemSetJson = costEmpKindWageItemSetService.addBatchCostEmpKindWageItemSet(listVo);

		return JSONObject.parseObject(costEmpKindWageItemSetJson);
		
	}
	/**
	*成本_职工分类工资项配置表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/queryCostEmpKindWageItemSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostEmpKindWageItemSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costEmpKindWageItemSet = costEmpKindWageItemSetService.queryCostEmpKindWageItemSet(getPage(mapVo));

		return JSONObject.parseObject(costEmpKindWageItemSet);
		
	}
	/**
	*成本_职工分类工资项配置表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/deleteCostEmpKindWageItemSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostEmpKindWageItemSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("emp_kind_code",ids[3]);   
			mapVo.put("wage_item_code",ids[4]); 
            listVo.add(mapVo);
        }
		String costEmpKindWageItemSetJson = costEmpKindWageItemSetService.deleteBatchCostEmpKindWageItemSet(listVo);
	   return JSONObject.parseObject(costEmpKindWageItemSetJson);
			
	}
	
	/**
	*成本_职工分类工资项配置表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/costEmpKindWageItemSetUpdatePage", method = RequestMethod.GET)
	
	public String costEmpKindWageItemSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostEmpKindWageItemSet costEmpKindWageItemSet = new CostEmpKindWageItemSet();
		costEmpKindWageItemSet = costEmpKindWageItemSetService.queryCostEmpKindWageItemSetByCode(mapVo);
		mode.addAttribute("group_id", costEmpKindWageItemSet.getGroup_id());
		mode.addAttribute("hos_id", costEmpKindWageItemSet.getHos_id());
		mode.addAttribute("copy_code", costEmpKindWageItemSet.getCopy_code());
		mode.addAttribute("emp_kind_code", costEmpKindWageItemSet.getEmp_kind_code());
		mode.addAttribute("emp_kind_name", costEmpKindWageItemSet.getEmp_kind_name());
		mode.addAttribute("wage_item_code", costEmpKindWageItemSet.getWage_item_code());
		mode.addAttribute("wage_item_name", costEmpKindWageItemSet.getWage_item_name());
		return "hrp/cost/costempkindwageitemset/costEmpKindWageItemSetUpdate";
	}
	/**
	*成本_职工分类工资项配置表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/updateCostEmpKindWageItemSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostEmpKindWageItemSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
		
		String costEmpKindWageItemSetJson = costEmpKindWageItemSetService.updateCostEmpKindWageItemSet(mapVo);
		
		return JSONObject.parseObject(costEmpKindWageItemSetJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/costEmpKindWageItemSetImportPage", method = RequestMethod.GET)
	public String costEmpKindWageItemSetImportPage(Model mode) throws Exception {

		return "hrp/cost/costempkindwageitemset/costEmpKindWageItemSetImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costempkindwageitemset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","职工分类工资项配置表.xls");
	    return null; 
	 }
	 
	/**
	*成本_职工分类工资项配置表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costempkindwageitemset/readCostEmpKindWageItemSetFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostEmpKindWageItemSet> list_err = new ArrayList<CostEmpKindWageItemSet>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostEmpKindWageItemSet costEmpKindWageItemSet = new CostEmpKindWageItemSet();
				
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
						
						costEmpKindWageItemSet.setEmp_kind_code(temp[0]);
						
						mapVo.put("emp_kind_code", temp[0]);
					} else {
						err_sb.append("职工分类为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costEmpKindWageItemSet.setWage_item_code(temp[2]);
						
						mapVo.put("wage_item_code", temp[2]);
					} else {
						err_sb.append("工资项编码为空  ");
					}
					
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costEmpKindWageItemSet.setEmp_kind_name(temp[1]);

					} 
					
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costEmpKindWageItemSet.setWage_item_name(temp[3]);

					} 
					
					CostEmpKindWageItemSet data_exc_extis = costEmpKindWageItemSetService.queryCostEmpKindWageItemSetByCode(mapVo);
					
					CostWageItemArrt costWageItemArrt = costWageItemArrtService.queryCostWageItemArrtByCode(mapVo);
					
					CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
					
					//根据不同业务提示相关信息
					if (data_exc_extis != null) {
						err_sb.append("编码已经存在！ ");
					}
					
					if (costEmpTypeAttr == null) {
						err_sb.append("职工分类编码不存在！ ");
					}
					
					if (costWageItemArrt == null) {
						err_sb.append("工资项编码不存在！ ");
					}
					
				if (err_sb.toString().length() > 0) {
					
					costEmpKindWageItemSet.setError_type(err_sb.toString());
					
					list_err.add(costEmpKindWageItemSet);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostEmpKindWageItemSet data_exc = new CostEmpKindWageItemSet();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costEmpKindWageItemSetService.addBatchCostEmpKindWageItemSet(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*成本_职工分类工资项配置表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/addBatchCostEmpKindWageItemSet", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostEmpKindWageItemSet(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostEmpKindWageItemSet> list_err = new ArrayList<CostEmpKindWageItemSet>();
		
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
			
			mapVo.put("emp_kind_code", jsonObj.get("emp_kind_code"));
			
			mapVo.put("emp_kind_name", jsonObj.get("emp_kind_name"));
			
			mapVo.put("wage_item_code", jsonObj.get("wage_item_code"));
			
			mapVo.put("wage_item_name", jsonObj.get("wage_item_name"));
			
		   
				CostEmpKindWageItemSet data_exc_extis = costEmpKindWageItemSetService.queryCostEmpKindWageItemSetByCode(mapVo);
				
				CostWageItemArrt costWageItemArrt = costWageItemArrtService.queryCostWageItemArrtByCode(mapVo);
				
				CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
				
				//根据不同业务提示相关信息
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				if (costEmpTypeAttr == null) {
					err_sb.append("职工分类编码不存在！ ");
				}
				
				if (costWageItemArrt == null) {
					err_sb.append("工资项编码不存在！ ");
				}
				
				CostEmpKindWageItemSet costEmpKindWageItemSet = new CostEmpKindWageItemSet();
				
				if (err_sb.toString().length() > 0) {
					
					costEmpKindWageItemSet.setEmp_kind_code(mapVo.get("emp_kind_code").toString());
					
					costEmpKindWageItemSet.setEmp_kind_name(mapVo.get("emp_kind_name").toString());
					
					costEmpKindWageItemSet.setWage_item_code(mapVo.get("wage_item_code").toString());
					
					costEmpKindWageItemSet.setWage_item_name(mapVo.get("wage_item_name").toString());
					
					costEmpKindWageItemSet.setError_type(err_sb.toString());
					
					list_err.add(costEmpKindWageItemSet);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costEmpKindWageItemSetService.addBatchCostEmpKindWageItemSet(list_batch);
				
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
	
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/queryCostEmpWageList", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostEmpWageList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costWageSchemeSet = costEmpKindWageItemSetService.queryCostEmpWageList(mapVo);

		return costWageSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/queryCostEmpWageMap", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostEmpWageMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costWageSchemeSet = costEmpKindWageItemSetService.queryCostEmpWageMap(mapVo);

		return costWageSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/queryCostEmpWageItemList", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostEmpWageItemList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costWageSchemeSet = costEmpKindWageItemSetService.queryCostEmpWageItemList(mapVo);

		return costWageSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costempkindwageitemset/queryCostEmpWageItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostEmpWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costWageSchemeSet = costEmpKindWageItemSetService.queryCostEmpWageItem(mapVo);

		return costWageSchemeSet;
		
	}
}

