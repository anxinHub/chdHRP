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
import com.chd.hrp.cost.entity.CostBonusCostRela;
import com.chd.hrp.cost.entity.CostBonusItemArrt;
import com.chd.hrp.cost.entity.CostEmpKindBonusItemSet;
import com.chd.hrp.cost.entity.CostEmpTypeAttr;
import com.chd.hrp.cost.service.CostBonusCostRelaService;
import com.chd.hrp.cost.serviceImpl.CostBonusItemArrtServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostEmpKindBonusItemSetServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostEmpTypeAttrServiceImpl;

/**
* @Title. @Description.
* 成本_职工分类奖金项配置表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostEmpKindBonusItemSetController extends BaseController{
	private static Logger logger = Logger.getLogger(CostEmpKindBonusItemSetController.class);
	
	
	@Resource(name = "costEmpKindBonusItemSetService")
	private final CostEmpKindBonusItemSetServiceImpl costEmpKindBonusItemSetService = null;
	
	@Resource(name = "costBonusItemArrtService")
	private final CostBonusItemArrtServiceImpl costBonusItemArrtService = null;
	
	@Resource(name = "costEmpTypeAttrService")
	private final CostEmpTypeAttrServiceImpl costEmpTypeAttrService = null;
   
	@Resource(name = "costBonusCostRelaService")
	private final CostBonusCostRelaService costBonusCostRelaService = null;
   
	/**
	*成本_职工分类奖金项配置表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/costEmpKindBonusItemSetMainPage", method = RequestMethod.GET)
	public String costEmpKindBonusItemSetMainPage(Model mode) throws Exception {

		return "hrp/cost/costempkindbonusitemset/costEmpKindBonusItemSetMain";

	}
	/**
	*成本_职工分类奖金项配置表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/costEmpKindBonusItemSetAddPage", method = RequestMethod.GET)
	public String costEmpKindBonusItemSetAddPage(Model mode) throws Exception {

		return "hrp/cost/costempkindbonusitemset/costEmpKindBonusItemSetAdd";

	}
	/**
	*成本_职工分类奖金项配置表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/addCostEmpKindBonusItemSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostEmpKindBonusItemSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
        
        String [] ids = mapVo.get("bonus_code").toString().split(";");
        
        for (String bonus_id : ids) {
        	
        	Map<String, Object> map=new HashMap<String, Object>();
        	
        	map.put("group_id", SessionManager.getGroupId());
        	
        	map.put("hos_id", SessionManager.getHosId());
        	
        	map.put("copy_code", SessionManager.getCopyCode());
        	
        	map.put("emp_kind_code", mapVo.get("emp_kind_code").toString());
        	
        	map.put("bonus_item_code", bonus_id);
        	
        	listVo.add(map);
			
		}
		
		String costEmpKindBonusItemSetJson = costEmpKindBonusItemSetService.addBatchCostEmpKindBonusItemSet(listVo);

		return JSONObject.parseObject(costEmpKindBonusItemSetJson);
		
	}
	/**
	*成本_职工分类奖金项配置表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/queryCostEmpKindBonusItemSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostEmpKindBonusItemSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costEmpKindBonusItemSet = costEmpKindBonusItemSetService.queryCostEmpKindBonusItemSet(getPage(mapVo));

		return JSONObject.parseObject(costEmpKindBonusItemSet);
		
	}
	/**
	*成本_职工分类奖金项配置表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/deleteCostEmpKindBonusItemSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostEmpKindBonusItemSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		boolean flag = true;
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("emp_kind_code",ids[3]);   
			mapVo.put("bonus_item_code",ids[4]); 
			
			CostBonusCostRela CostBonusCostRela = costBonusCostRelaService.queryCostBonusCostRelaByCode(mapVo);
			
			if(CostBonusCostRela != null){
				
				flag = false;
				
				break;
			}
			
            listVo.add(mapVo);
        }
		
		if(flag == false){
			
			return JSONObject.parseObject("{\"error\":\"奖金项目与成本关系已使用.不许删除! \"}");
			
		}
		
		String costEmpKindBonusItemSetJson = costEmpKindBonusItemSetService.deleteBatchCostEmpKindBonusItemSet(listVo);
	   return JSONObject.parseObject(costEmpKindBonusItemSetJson);
			
	}
	
	/**
	*成本_职工分类奖金项配置表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/costEmpKindBonusItemSetUpdatePage", method = RequestMethod.GET)
	
	public String costEmpKindBonusItemSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostEmpKindBonusItemSet costEmpKindBonusItemSet = new CostEmpKindBonusItemSet();
		costEmpKindBonusItemSet = costEmpKindBonusItemSetService.queryCostEmpKindBonusItemSetByCode(mapVo);
		mode.addAttribute("group_id", costEmpKindBonusItemSet.getGroup_id());
		mode.addAttribute("hos_id", costEmpKindBonusItemSet.getHos_id());
		mode.addAttribute("copy_code", costEmpKindBonusItemSet.getCopy_code());
		mode.addAttribute("emp_kind_code", costEmpKindBonusItemSet.getEmp_kind_code());
		mode.addAttribute("emp_kind_name", costEmpKindBonusItemSet.getEmp_kind_name());
		mode.addAttribute("bonus_item_code", costEmpKindBonusItemSet.getBonus_item_code());
		mode.addAttribute("bonus_item_name", costEmpKindBonusItemSet.getBonus_item_name());
		return "hrp/cost/costempkindbonusitemset/costEmpKindBonusItemSetUpdate";
	}
	/**
	*成本_职工分类奖金项配置表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/updateCostEmpKindBonusItemSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostEmpKindBonusItemSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costEmpKindBonusItemSetJson = costEmpKindBonusItemSetService.updateCostEmpKindBonusItemSet(mapVo);
		
		return JSONObject.parseObject(costEmpKindBonusItemSetJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/costEmpKindBonusItemSetImportPage", method = RequestMethod.GET)
	public String costEmpKindBonusItemSetImportPage(Model mode) throws Exception {

		return "hrp/cost/costempkindbonusitemset/costEmpKindBonusItemSetImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costempkindbonusitemset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","职工分类奖金项配置表.xls");
	    return null; 
	 }
	 
	/**
	*成本_职工分类奖金项配置表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costempkindbonusitemset/readCostEmpKindBonusItemSetFiles",method = RequestMethod.POST)  
	public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostEmpKindBonusItemSet> list_err = new ArrayList<CostEmpKindBonusItemSet>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostEmpKindBonusItemSet costEmpKindBonusItemSet = new CostEmpKindBonusItemSet();
				
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
						
						costEmpKindBonusItemSet.setEmp_kind_code(temp[0]);
						
						mapVo.put("emp_kind_code", temp[0]);
					} else {
						err_sb.append("职工分类为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costEmpKindBonusItemSet.setBonus_item_code(temp[2]);
						
						mapVo.put("bonus_item_code", temp[2]);
					} else {
						err_sb.append("奖金项编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {

						costEmpKindBonusItemSet.setEmp_kind_name(temp[1]);
						
					}
					if (StringUtils.isNotEmpty(temp[3])) {

						costEmpKindBonusItemSet.setBonus_item_name(temp[3]);
						
					}
					CostEmpKindBonusItemSet data_exc_extis = costEmpKindBonusItemSetService.queryCostEmpKindBonusItemSetByCode(mapVo);
					
					CostBonusItemArrt costBonusItemArrt = costBonusItemArrtService.queryCostBonusItemArrtByCode(mapVo);
					
					CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
					
					if (data_exc_extis != null) {
						err_sb.append("编码已经存在！ ");
					}
					
					if (costEmpTypeAttr == null) {
						err_sb.append("职工分类编码不存在！ ");
					}
					
					if (costBonusItemArrt == null) {
						err_sb.append("奖金项编码不存在！ ");
					}
				if (err_sb.toString().length() > 0) {
					
					costEmpKindBonusItemSet.setError_type(err_sb.toString());
					
					list_err.add(costEmpKindBonusItemSet);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostEmpKindBonusItemSet data_exc = new CostEmpKindBonusItemSet();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costEmpKindBonusItemSetService.addBatchCostEmpKindBonusItemSet(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*成本_职工分类奖金项配置表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/addBatchCostEmpKindBonusItemSet", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostEmpKindBonusItemSet(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostEmpKindBonusItemSet> list_err = new ArrayList<CostEmpKindBonusItemSet>();
		
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
			
			mapVo.put("bonus_item_code", jsonObj.get("bonus_item_code"));
			
			mapVo.put("bonus_item_name", jsonObj.get("bonus_item_name"));
			
		   
				CostEmpKindBonusItemSet data_exc_extis = costEmpKindBonusItemSetService.queryCostEmpKindBonusItemSetByCode(mapVo);
				
				CostBonusItemArrt costBonusItemArrt = costBonusItemArrtService.queryCostBonusItemArrtByCode(mapVo);
				
				CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				if (costEmpTypeAttr == null) {
					err_sb.append("职工分类编码不存在！ ");
				}
				
				if (costBonusItemArrt == null) {
					err_sb.append("奖金项编码不存在！ ");
				}
				
				CostEmpKindBonusItemSet costEmpKindBonusItemSet = new CostEmpKindBonusItemSet();
				
				if (err_sb.toString().length() > 0) {
					
					costEmpKindBonusItemSet.setEmp_kind_code(mapVo.get("emp_kind_code").toString());
					
					costEmpKindBonusItemSet.setEmp_kind_name(mapVo.get("emp_kind_name").toString());
					
					costEmpKindBonusItemSet.setBonus_item_code(mapVo.get("bonus_item_code").toString());
					
					costEmpKindBonusItemSet.setBonus_item_name(mapVo.get("bonus_item_name").toString());
					
					
					costEmpKindBonusItemSet.setError_type(err_sb.toString());
					
					list_err.add(costEmpKindBonusItemSet);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costEmpKindBonusItemSetService.addBatchCostEmpKindBonusItemSet(list_batch);
				
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
	
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/queryCostEmpBonusList", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostEmpBonusList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costBonusSchemeSet = costEmpKindBonusItemSetService.queryCostEmpBonusList(mapVo);

		return costBonusSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/queryCostEmpBonusMap", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostEmpBonusMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costBonusSchemeSet = costEmpKindBonusItemSetService.queryCostEmpBonusMap(mapVo);

		return costBonusSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/queryCostEmpBonusItemList", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostEmpBonusItemList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costBonusSchemeSet = costEmpKindBonusItemSetService.queryCostEmpBonusItemList(mapVo);

		return costBonusSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costempkindbonusitemset/queryCostEmpBonusItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostEmpBonusItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costBonusSchemeSet = costEmpKindBonusItemSetService.queryCostEmpBonusItem(mapVo);

		return costBonusSchemeSet;
		
	}
}

