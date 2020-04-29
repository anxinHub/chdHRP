/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
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
import com.chd.hrp.cost.entity.CostBonusScheme;
import com.chd.hrp.cost.entity.CostBonusSchemeSet;
import com.chd.hrp.cost.serviceImpl.CostBonusSchemeServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostBonusSchemeSetServiceImpl;

/**
* @Title. @Description.
* 职工奖金查询方案表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostBonusSchemeSetController extends BaseController{
	private static Logger logger = Logger.getLogger(CostBonusSchemeSetController.class);
	
	
	@Resource(name = "costBonusSchemeSetService")
	private final CostBonusSchemeSetServiceImpl costBonusSchemeSetService = null;
   
	@Resource(name = "costBonusSchemeService")
	private final CostBonusSchemeServiceImpl costBonusSchemeService = null;
   
	/**
	*职工奖金查询方案表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/costBonusSchemeSetMainPage", method = RequestMethod.GET)
	public String costBonusSchemeSetMainPage(Model mode) throws Exception {

		return "hrp/cost/costbonusschemeset/costBonusSchemeSetMain";

	}
	/**
	*职工奖金查询方案表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/costBonusSchemeSetAddPage", method = RequestMethod.GET)
	public String costBonusSchemeSetAddPage(Model mode) throws Exception {

		return "hrp/cost/costbonusschemeset/costBonusSchemeSetAdd";

	}
	/**
	*职工奖金查询方案表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/addCostBonusSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostBonusSchemeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costBonusSchemeSetJson ="";
		CostBonusScheme costBonusScheme =costBonusSchemeService.CostBonusSequence();
		
        List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
        
        String [] ids = mapVo.get("bonus_code").toString().split(";");
        
        for (String bonus_id : ids) {
        	
        	Map<String, Object> map=new HashMap<String, Object>();
        	
        	map.put("group_id", SessionManager.getGroupId());
        	
        	map.put("hos_id", SessionManager.getHosId());
        	
        	map.put("copy_code", SessionManager.getCopyCode());
        	
        	map.put("scheme_id",  costBonusScheme.getScheme_id());
        	
        	map.put("scheme_name", mapVo.get("scheme_name").toString());
        	
        	map.put("emp_kind_code", mapVo.get("emp_kind_code").toString());
        	
        	map.put("order_no", mapVo.get("order_no").toString());

        	map.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
    		
        	map.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
        	
        	map.put("bonus_item_code", bonus_id);
        	
       	listVo.add(map);
        	costBonusSchemeSetJson = costBonusSchemeSetService.addBatchCostBonusSchemeSet(listVo);
			
		}
		
		

		return JSONObject.parseObject(costBonusSchemeSetJson);
		
	}
	/**
	*职工奖金查询方案表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/queryCostBonusSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostBonusSchemeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costBonusSchemeSet = costBonusSchemeSetService.queryCostBonusSchemeSet(getPage(mapVo));

		return JSONObject.parseObject(costBonusSchemeSet);
		
	}
	/**
	*职工奖金查询方案表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/deleteCostBonusSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostBonusSchemeSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();

			mapVo.put("scheme_id",ids[0]);   
			
			mapVo.put("emp_kind_code",ids[1]);   
			
			mapVo.put("bonus_item_code",ids[2]); 
			
            listVo.add(mapVo);
        }
		String costBonusSchemeSetJson = costBonusSchemeSetService.deleteBatchCostBonusSchemeSet(listVo);
	   return JSONObject.parseObject(costBonusSchemeSetJson);
			
	}
	
	/**
	*职工奖金查询方案表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/costBonusSchemeSetUpdatePage", method = RequestMethod.GET)
	
	public String costBonusSchemeSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostBonusSchemeSet costBonusSchemeSet = new CostBonusSchemeSet();
		costBonusSchemeSet = costBonusSchemeSetService.queryCostBonusSchemeSetByCode(mapVo);
		mode.addAttribute("scheme_id", costBonusSchemeSet.getScheme_id());
		mode.addAttribute("scheme_name", costBonusSchemeSet.getScheme_name());
		mode.addAttribute("group_id", costBonusSchemeSet.getGroup_id());
		mode.addAttribute("hos_id", costBonusSchemeSet.getHos_id());
		mode.addAttribute("copy_code", costBonusSchemeSet.getCopy_code());
		mode.addAttribute("emp_kind_code", costBonusSchemeSet.getEmp_kind_code());
		mode.addAttribute("emp_kind_name", costBonusSchemeSet.getEmp_kind_name());
		mode.addAttribute("bonus_item_name", costBonusSchemeSet.getBonus_item_name());
		mode.addAttribute("bonus_item_code", costBonusSchemeSet.getBonus_item_code());
		mode.addAttribute("bonus_code", costBonusSchemeSet.getBonus_code());
		mode.addAttribute("order_no", costBonusSchemeSet.getOrder_no());
		mode.addAttribute("spell_code", costBonusSchemeSet.getSpell_code());
		mode.addAttribute("wbx_code", costBonusSchemeSet.getWbx_code());
		return "hrp/cost/costbonusschemeset/costBonusSchemeSetUpdate";
	}
	/**
	*职工奖金查询方案表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/updateCostBonusSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostBonusSchemeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
        
        String [] ids = mapVo.get("bonus_code").toString().split(";");
        
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
        	
        	map.put("bonus_item_code", wage_id);
        	
        	listVo.add(map);
			
		}
		
		String costBonusSchemeSetJson = costBonusSchemeSetService.updateBatchCostBonusSchemeSet(listVo);
		
		return JSONObject.parseObject(costBonusSchemeSetJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/costBonusSchemeSetImportPage", method = RequestMethod.GET)
	public String costBonusSchemeSetImportPage(Model mode) throws Exception {

		return "hrp/cost/costbonusschemeset/costBonusSchemeSetImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costbonusschemeset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","职工奖金查询方案.xls");
	    return null; 
	 }
	 
	/**
	*职工奖金查询方案表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costbonusschemeset/readCostBonusSchemeSetFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
    	List<CostBonusSchemeSet> list2 = new ArrayList<CostBonusSchemeSet> ();  
    	List<String[]> list = UploadUtil.readFile(plupload, request, response);
    	List<CostBonusSchemeSet> errorList = new ArrayList<CostBonusSchemeSet>();
		for (int i = 1; i < list.size(); i++) {
			String temp[] = list.get(i);//行
			Map<String, Object> mapVo = new HashMap<String, Object>();
			//模版生成,根据不同字典处理业务
			if("".equals(temp[0]) || "".equals(temp[1])){
				CostBonusSchemeSet costBonusSchemeSet = new CostBonusSchemeSet();
				
					costBonusSchemeSet.setScheme_id(Long.getLong(temp[0]));
					costBonusSchemeSet.setScheme_name(temp[1]);
					costBonusSchemeSet.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					costBonusSchemeSet.setHos_id(Long.getLong(SessionManager.getHosId()));
					costBonusSchemeSet.setCopy_code(SessionManager.getCopyCode());
					costBonusSchemeSet.setEmp_kind_code(temp[5]);
					costBonusSchemeSet.setBonus_item_code(temp[6]);
					costBonusSchemeSet.setBonus_code(temp[7]);
					costBonusSchemeSet.setOrder_no(Integer.getInteger(temp[8]));
				costBonusSchemeSet.setError_type("数据表列存在空的数据列");
				errorList.add(costBonusSchemeSet);
			}else{
				//需要转换各别列 通过SessionManager 里面获取
					mapVo.put("scheme_id",temp[0]);
				
					mapVo.put("scheme_name",temp[1]);
				
					mapVo.put("group_id", SessionManager.getGroupId());
				
					mapVo.put("hos_id", SessionManager.getHosId());
				
					mapVo.put("copy_code", SessionManager.getCopyCode());
				
					mapVo.put("emp_kind_code",temp[5]);
				
					mapVo.put("bonus_item_code",temp[6]);
				
					mapVo.put("bonus_code",temp[7]);
				
					mapVo.put("order_no",temp[8]);
				
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
				
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
				
			}
			try {
				CostBonusSchemeSet data = new CostBonusSchemeSet();
				data = costBonusSchemeSetService.queryCostBonusSchemeSetByCode(mapVo);
					if(data != null){
					data.setScheme_id(Long.getLong(temp[0]));
					data.setScheme_name(temp[1]);
					data.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					data.setHos_id(Long.getLong(SessionManager.getHosId()));
					data.setCopy_code(SessionManager.getCopyCode());
					data.setEmp_kind_code(temp[5]);
					data.setBonus_item_code(temp[6]);
					data.setBonus_code(temp[7]);
					data.setOrder_no(Integer.getInteger(temp[8]));
					data.setError_type("数据在数据库中已存在");
					errorList.add(data);
					}else{
							if(i==list.size()-1){
							String dataJson = costBonusSchemeSetService.addCostBonusSchemeSet(mapVo);
							JSONObject json = JSONObject.parseObject(dataJson);
							String flag = String.valueOf(json.get("state"));
							if(!flag.equals("true")){
								CostBonusSchemeSet data_error = new CostBonusSchemeSet();
								data_error.setScheme_id(Long.getLong(temp[0]));
								data_error.setScheme_name(temp[1]);
								data_error.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_error.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_error.setCopy_code(SessionManager.getCopyCode());
								data_error.setEmp_kind_code(temp[5]);
								data_error.setBonus_item_code(temp[6]);
								data_error.setBonus_code(temp[7]);
								data_error.setOrder_no(Integer.getInteger(temp[8]));
								data_error.setError_type("导入失败");
								errorList.add(data_error);
								}
							}
					}
			} catch (DataAccessException e) {
				CostBonusSchemeSet data_exc = new CostBonusSchemeSet();
								data_exc.setScheme_id(Long.getLong(temp[0]));
								data_exc.setScheme_name(temp[1]);
								data_exc.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_exc.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_exc.setCopy_code(SessionManager.getCopyCode());
								data_exc.setEmp_kind_code(temp[5]);
								data_exc.setBonus_item_code(temp[6]);
								data_exc.setBonus_code(temp[7]);
								data_exc.setOrder_no(Integer.getInteger(temp[8]));
				data_exc.setError_type("导入系统出错");
				errorList.add(data_exc);
			}
		}
		list2.addAll(errorList);
		mode.addAttribute("resultsJson",  ChdJson.toJson(list2, list2.size()));
	    	return "hrp/cost/costbonusschemeset/costbonusschemesetImportMessage";
    }  
	/**
	*职工奖金查询方案表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/addBatchCostBonusSchemeSet", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostBonusSchemeSet(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("scheme_id",ids[0]);   
			mapVo.put("scheme_name",ids[1]);   
			mapVo.put("group_id",ids[2]);   
			mapVo.put("hos_id",ids[3]);   
			mapVo.put("copy_code",ids[4]);   
			mapVo.put("emp_kind_code",ids[5]);   
			mapVo.put("bonus_item_code",ids[6]);   
			mapVo.put("bonus_code",ids[7]);   
			mapVo.put("order_no",ids[8]);   
			mapVo.put("spell_code",ids[9]);   
			mapVo.put("wbx_code",ids[10]); 
            listVo.add(mapVo);
        }
		String costBonusSchemeSetJson = costBonusSchemeSetService.addBatchCostBonusSchemeSet(listVo);
	    return JSONObject.parseObject(costBonusSchemeSetJson);
    }
	
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/queryCostBonusList", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostBonusList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costBonusSchemeSet = costBonusSchemeSetService.queryCostBonusList(mapVo);

		return costBonusSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/queryCostBonusMap", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostBonusMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costBonusSchemeSet = costBonusSchemeSetService.queryCostBonusMap(mapVo);

		return costBonusSchemeSet;
		
	}
	
	@RequestMapping(value = "/hrp/cost/costbonusschemeset/queryCostBonusItemList", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostBonusItemList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costBonusSchemeSet = costBonusSchemeSetService.queryCostBonusItemList(mapVo);

		return costBonusSchemeSet;
		
	}
}

