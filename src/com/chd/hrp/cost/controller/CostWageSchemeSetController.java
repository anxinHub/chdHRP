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
import com.chd.hrp.cost.entity.CostWageScheme;
import com.chd.hrp.cost.entity.CostWageSchemeSet;
import com.chd.hrp.cost.serviceImpl.CostWageSchemeServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostWageSchemeSetServiceImpl;

/**
* @Title. @Description.
* 职工工资查询方案表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostWageSchemeSetController extends BaseController{
	private static Logger logger = Logger.getLogger(CostWageSchemeSetController.class);
	
	
	@Resource(name = "costWageSchemeSetService")
	private final CostWageSchemeSetServiceImpl costWageSchemeSetService = null;
	
	@Resource(name = "costWageSchemeService")
	private final CostWageSchemeServiceImpl costWageSchemeService = null;
   
   
	/**
	*职工工资查询方案表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costwageschemeset/costWageSchemeSetMainPage", method = RequestMethod.GET)
	public String costWageSchemeSetMainPage(Model mode) throws Exception {

		return "hrp/cost/costwageschemeset/costWageSchemeSetMain";

	}
	/**
	*职工工资查询方案表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costwageschemeset/costWageSchemeSetAddPage", method = RequestMethod.GET)
	public String costWageSchemeSetAddPage(Model mode) throws Exception {

		return "hrp/cost/costwageschemeset/costWageSchemeSetAdd";

	}
	/**
	*职工工资查询方案表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costwageschemeset/addCostWageSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostWageSchemeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		CostWageScheme costWageScheme =costWageSchemeService.CostWageSequence();
		
        List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
        
        String [] ids = mapVo.get("wage_code").toString().split(";");
        
        String costWageSchemeSetJson = "";
        
        for (String wage_id : ids) {
        	
        	Map<String, Object> map=new HashMap<String, Object>();
        	
        	map.put("group_id", SessionManager.getGroupId());
        	
        	map.put("hos_id", SessionManager.getHosId());
        	
        	map.put("copy_code", SessionManager.getCopyCode());
        	
        	map.put("scheme_id",  costWageScheme.getScheme_id());
        	
        	map.put("scheme_name", mapVo.get("scheme_name").toString());
        	
        	map.put("emp_kind_code", mapVo.get("emp_kind_code").toString());
        	
        	map.put("order_no", mapVo.get("order_no").toString());

        	map.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
    		
        	map.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
        	
        	map.put("wage_item_code", wage_id);
        	

        	listVo.add(map);
			
		}
		
		 costWageSchemeSetJson = costWageSchemeSetService.addBatchCostWageSchemeSet(listVo);

		return JSONObject.parseObject(costWageSchemeSetJson);
		
	}
	/**
	*职工工资查询方案表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costwageschemeset/queryCostWageSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostWageSchemeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costWageSchemeSet = costWageSchemeSetService.queryCostWageSchemeSet(getPage(mapVo));

		return JSONObject.parseObject(costWageSchemeSet);
		
	}
	
	
	@RequestMapping(value = "/hrp/cost/costwageschemeset/queryWageName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWageName(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costWageSchemeSet = costWageSchemeSetService.queryWageName(getPage(mapVo));

		return JSONObject.parseObject(costWageSchemeSet);
		
	}
	
	
	/**
	*职工工资查询方案表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costwageschemeset/deleteCostWageSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostWageSchemeSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("scheme_id",ids[0]);   
			
			mapVo.put("emp_kind_code",ids[1]);   
			
			mapVo.put("wage_item_code",ids[2]); 
			
            listVo.add(mapVo);
        }
		
		String costWageSchemeSetJson = costWageSchemeSetService.deleteBatchCostWageSchemeSet(listVo);
		
	   return JSONObject.parseObject(costWageSchemeSetJson);
			
	}
	
	/**
	*职工工资查询方案表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costwageschemeset/costWageSchemeSetUpdatePage", method = RequestMethod.GET)
	
	public String costWageSchemeSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostWageSchemeSet costWageSchemeSet = new CostWageSchemeSet();
    //    String para_value=costWageSchemeSet.
        
		costWageSchemeSet = costWageSchemeSetService.queryCostWageSchemeSetByCode(mapVo);
		mode.addAttribute("group_id", costWageSchemeSet.getGroup_id());
		mode.addAttribute("hos_id", costWageSchemeSet.getHos_id());
		mode.addAttribute("copy_code", costWageSchemeSet.getCopy_code());
		mode.addAttribute("scheme_id", costWageSchemeSet.getScheme_id());
		
		mode.addAttribute("scheme_name", costWageSchemeSet.getScheme_name());
		
		mode.addAttribute("group_id", costWageSchemeSet.getGroup_id());
		
		mode.addAttribute("hos_id", costWageSchemeSet.getHos_id());
		
		mode.addAttribute("copy_code", costWageSchemeSet.getCopy_code());
		
		mode.addAttribute("emp_kind_code", costWageSchemeSet.getEmp_kind_code().toString());
		
		mode.addAttribute("emp_kind_name", costWageSchemeSet.getEmp_kind_name());
		
		mode.addAttribute("wage_item_name", costWageSchemeSet.getWage_item_name());
		
		mode.addAttribute("wage_item_code", costWageSchemeSet.getWage_item_code());
		
		mode.addAttribute("wage_code", costWageSchemeSet.getWage_code());
		
		mode.addAttribute("order_no", costWageSchemeSet.getOrder_no());
		
		return "hrp/cost/costwageschemeset/costWageSchemeSetUpdate";
	}
	/**
	*职工工资查询方案表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costwageschemeset/updateCostWageSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostWageSchemeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
        
        String [] ids = mapVo.get("wage_code").toString().split(";");
        
        for (String wage_id : ids) {
        	
        	Map<String, Object> map=new HashMap<String, Object>();
        	
        	map.put("group_id", SessionManager.getGroupId());
        	
        	map.put("hos_id", SessionManager.getHosId());
        	
        	map.put("copy_code", SessionManager.getCopyCode());
        	
        	map.put("scheme_id",  mapVo.get("scheme_id").toString());
        	
        	map.put("scheme_name", mapVo.get("scheme_name").toString());
        	
        	map.put("emp_kind_code", mapVo.get("emp_kind_code").toString());
        	
        	map.put("order_no", mapVo.get("order_no").toString());

        	map.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
    		
        	map.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
        	
        	map.put("wage_item_code", wage_id);
        	
        	listVo.add(map);
			
		}
		
		String costWageSchemeSetJson = costWageSchemeSetService.updateBatchCostWageSchemeSet(listVo);
		
		return JSONObject.parseObject(costWageSchemeSetJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costwageschemeset/costWageSchemeSetImportPage", method = RequestMethod.GET)
	public String costWageSchemeSetImportPage(Model mode) throws Exception {

		return "hrp/cost/costwageschemeset/costWageSchemeSetImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costwageschemeset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","职工工资查询方案.xls");
	    return null; 
	 }
	 
	/**
	*职工工资查询方案配置表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costwageschemeset/readCostWageSchemeSetFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostWageSchemeSet> list_err = new ArrayList<CostWageSchemeSet>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostWageSchemeSet costWageSchemeSet = new CostWageSchemeSet();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					if (StringUtils.isNotEmpty(temp[0])) {
						
						costWageSchemeSet.setScheme_id(Long.valueOf(temp[0]));
						
						mapVo.put("scheme_id", temp[0]);
					} else {
						err_sb.append("方案ID为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costWageSchemeSet.setScheme_name(temp[1]);
						
						mapVo.put("scheme_name", temp[1]);
					} else {
						err_sb.append("方案名称为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costWageSchemeSet.setEmp_kind_code(temp[2]);
						
						mapVo.put("emp_kind_code", temp[2]);
					} else {
						err_sb.append("职工分类编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costWageSchemeSet.setEmp_kind_name(temp[3]);
						
						mapVo.put("emp_kind_name", temp[3]);
					} else {
						err_sb.append("职工分类名称为空  ");
					}
					if (StringUtils.isNotEmpty(temp[4])) {
						
						costWageSchemeSet.setWage_item_code(temp[4]);
						
						mapVo.put("wage_item_code", temp[4]);
					} else {
						err_sb.append("工资项编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[5])) {
						
						costWageSchemeSet.setWage_item_name(temp[5]);
						
						mapVo.put("wage_item_name", temp[5]);
					} else {
						err_sb.append("职工分类名称为空  ");
					}
					if (StringUtils.isNotEmpty(temp[6])) {
						
						costWageSchemeSet.setOrder_no(Integer.valueOf(temp[6]));
						
						mapVo.put("order_no", temp[6]);
					} else {
						err_sb.append("显示顺序为空  ");
					}
					
					mapVo.put("group_id", SessionManager.getGroupId());
		        	
					mapVo.put("hos_id", SessionManager.getHosId());
		        	
					mapVo.put("copy_code", SessionManager.getCopyCode());
		        	
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
		    		
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
					
				CostWageSchemeSet data_exc_extis = costWageSchemeSetService.queryCostWageSchemeSetByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costWageSchemeSet.setError_type(err_sb.toString());
					
					list_err.add(costWageSchemeSet);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostWageSchemeSet data_exc = new CostWageSchemeSet();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costWageSchemeSetService.addBatchCostWageSchemeSet(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*职工工资查询方案配置表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costwageschemeset/addBatchCostWageSchemeSet", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostWageSchemeSet(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostWageSchemeSet> list_err = new ArrayList<CostWageSchemeSet>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			StringBuffer err_sb = new StringBuffer();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			mapVo.put("scheme_id", jsonObj.get("scheme_id"));
			
			mapVo.put("scheme_name", jsonObj.get("scheme_name"));
			
			mapVo.put("emp_kind_code", jsonObj.get("emp_kind_code"));
			
			mapVo.put("emp_kind_name", jsonObj.get("emp_kind_name"));
			
			mapVo.put("wage_item_code", jsonObj.get("wage_item_code"));
			
			mapVo.put("wage_item_name", jsonObj.get("wage_item_name"));
			
			mapVo.put("order_no", jsonObj.get("order_no"));
		   
				CostWageSchemeSet data_exc_extis = costWageSchemeSetService.queryCostWageSchemeSetByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostWageSchemeSet costWageSchemeSet = new CostWageSchemeSet();
				
				if (err_sb.toString().length() > 0) {
					
					costWageSchemeSet.setScheme_id(Long.valueOf(mapVo.get("scheme_id").toString()));
					
					costWageSchemeSet.setScheme_name(mapVo.get("scheme_name").toString());
					
					costWageSchemeSet.setEmp_kind_code(mapVo.get("emp_kind_code").toString());
					
					costWageSchemeSet.setEmp_kind_name(mapVo.get("emp_kind_name").toString());
					
					costWageSchemeSet.setWage_item_code(mapVo.get("wage_item_code").toString());
					
					costWageSchemeSet.setWage_item_name(mapVo.get("wage_item_name").toString());
					
					costWageSchemeSet.setOrder_no(Integer.valueOf(mapVo.get("order_no").toString()));
					
					costWageSchemeSet.setError_type(err_sb.toString());
					
					list_err.add(costWageSchemeSet);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costWageSchemeSetService.addBatchCostWageSchemeSet(list_batch);
				
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
	
	/**
	*职工分类工资项配置查询<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costwageschemeset/queryCostWageList", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostWageList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costWageSchemeSet = costWageSchemeSetService.queryCostWageList(mapVo);

		return costWageSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costwageschemeset/queryCostWageMap", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostWageMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costWageSchemeSet = costWageSchemeSetService.queryCostWageMap(mapVo);

		return costWageSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costwageschemeset/queryCostWageItemList", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostWageItemList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costWageSchemeSet = costWageSchemeSetService.queryCostWageItemList(mapVo);

		return costWageSchemeSet;
		
	}
}

