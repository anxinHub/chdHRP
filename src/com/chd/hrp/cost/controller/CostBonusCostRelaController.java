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
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostBonusCostRela;
import com.chd.hrp.cost.entity.CostBonusItemArrt;
import com.chd.hrp.cost.entity.CostEmpKindBonusItemSet;
import com.chd.hrp.cost.entity.CostEmpKindWageItemSet;
import com.chd.hrp.cost.entity.CostEmpTypeAttr;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.service.CostEmpKindBonusItemSetService;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.chd.hrp.cost.serviceImpl.CostBonusCostRelaServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostBonusItemArrtServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostEmpTypeAttrServiceImpl;

/**
* @Title. @Description.
* 奖金项与成本项目的对应关系
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostBonusCostRelaController extends BaseController{
	private static Logger logger = Logger.getLogger(CostBonusCostRelaController.class);
	
	
	@Resource(name = "costBonusCostRelaService")
	private final CostBonusCostRelaServiceImpl costBonusCostRelaService = null;
	
	@Resource(name = "costEmpTypeAttrService")
	private final CostEmpTypeAttrServiceImpl costEmpTypeAttrService = null;
	
	@Resource(name = "costBonusItemArrtService")
	private final CostBonusItemArrtServiceImpl costBonusItemArrtService = null;
	
	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoService costItemDictNoService = null;
	
	@Resource(name = "costEmpKindBonusItemSetService")
	private final CostEmpKindBonusItemSetService costEmpKindBonusItemSetService = null;
   
   
	/**
	*奖金项与成本项目的对应关系<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/costBonusCostRelaMainPage", method = RequestMethod.GET)
	public String costBonusCostRelaMainPage(Model mode) throws Exception {

		return "hrp/cost/costbonuscostrela/costBonusCostRelaMain";

	}
	/**
	*奖金项与成本项目的对应关系<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/costBonusCostRelaAddPage", method = RequestMethod.GET)
	public String costBonusCostRelaAddPage(Model mode) throws Exception {

		return "hrp/cost/costbonuscostrela/costBonusCostRelaAdd";

	}
	/**
	*奖金项与成本项目的对应关系<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/addCostBonusCostRela", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostBonusCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List list_batch = new ArrayList();
		
		List<String> list_bouns_item = new ArrayList<String>();
		
		String emp_kind_code = (String) mapVo.get("emp_kind_code");
		
		String bonus_item_code = (String) mapVo.get("bonus_item_code");
		
		String cost_item_id = (String) mapVo.get("cost_item_id");
		
		String cost_item_no = (String) mapVo.get("cost_item_no");
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		   if(entityMap.get("group_id") == null){
			  
			   entityMap.put("group_id", SessionManager.getGroupId());
			   
			}
		   
			if(entityMap.get("hos_id") == null){
				
				entityMap.put("hos_id", SessionManager.getHosId());
			
			}
			if(entityMap.get("copy_code") == null){
				
				entityMap.put("copy_code", SessionManager.getCopyCode());
	        
			}
			/*
			 *判断奖金项是否为空,如过为空选择职工分类下全部工资项 
			 * */
			  if(StringUtils.isNotEmpty(bonus_item_code)){
					
					String[] dept_split = bonus_item_code.split(";");
					
					for(int i =0 ; i<dept_split.length; i++){
						
						list_bouns_item.add(dept_split[i]);
						
					}
					
				}else{
					
					entityMap.put("emp_kind_code", emp_kind_code);
					
					List<CostEmpKindBonusItemSet> list = costEmpKindBonusItemSetService.queryCostBonusCostRelaByEmpKindCode(entityMap);
			 
					if(list.size() == 0 ){
						
						return JSONObject.parseObject("{\"error\":\"当前职工分类下没有奖金项\"}");
						
					}
					
					for(int i =0 ;i<list.size();i++){
						
						CostEmpKindBonusItemSet empKindBonusItem = list.get(i);
						
						list_bouns_item.add(empKindBonusItem.getBonus_item_code());
						
					}
				}
			  
			  
			  for (int i = 0; i < list_bouns_item.size(); i++) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("group_id", SessionManager.getGroupId());
	    			    
					map.put("hos_id", SessionManager.getHosId());
	    				
					map.put("copy_code", SessionManager.getCopyCode());
	    				
					map.put("acc_year", mapVo.get("acc_year").toString());
					
					map.put("acc_month", mapVo.get("acc_month").toString());
					
					map.put("emp_kind_code", emp_kind_code);
					
					map.put("bonus_item_code", list_bouns_item.get(i));
	    				
					map.put("cost_item_id", cost_item_id);
					
					map.put("cost_item_no", cost_item_no);
					
					list_batch.add(map);
					
				   }
			  
		String costBonusCostRelaJson = costBonusCostRelaService.addBatchCostBonusCostRela(list_batch);

		return JSONObject.parseObject(costBonusCostRelaJson);
		
	}
	/**
	*奖金项与成本项目的对应关系<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/queryCostBonusCostRela", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostBonusCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costBonusCostRela = costBonusCostRelaService.queryCostBonusCostRela(getPage(mapVo));

		return JSONObject.parseObject(costBonusCostRela);
		
	}
	/**
	*奖金项与成本项目的对应关系<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/deleteCostBonusCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostBonusCostRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("id",ids[0]);
			mapVo.put("group_id",ids[1]);   
			mapVo.put("hos_id",ids[2]);   
			mapVo.put("copy_code",ids[3]);   
			mapVo.put("acc_year",ids[4]);
			mapVo.put("acc_month",ids[5]);
			mapVo.put("emp_kind_code",ids[6]);   
			mapVo.put("bonus_item_code",ids[7]); 
            listVo.add(mapVo);
        }
		String costBonusCostRelaJson = costBonusCostRelaService.deleteBatchCostBonusCostRela(listVo);
	   return JSONObject.parseObject(costBonusCostRelaJson);
			
	}
	
	/**
	*奖金项与成本项目的对应关系<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/costBonusCostRelaUpdatePage", method = RequestMethod.GET)
	public String costBonusCostRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostBonusCostRela costBonusCostRela = new CostBonusCostRela();
		costBonusCostRela = costBonusCostRelaService.queryCostBonusCostRelaByCode(mapVo);
		mode.addAttribute("id", costBonusCostRela.getId());
		mode.addAttribute("group_id", costBonusCostRela.getGroup_id());
		mode.addAttribute("hos_id", costBonusCostRela.getHos_id());
		mode.addAttribute("copy_code", costBonusCostRela.getCopy_code());
		mode.addAttribute("year_month", costBonusCostRela.getAcc_year().toString() + costBonusCostRela.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costBonusCostRela.getAcc_year());
		
		mode.addAttribute("acc_month", costBonusCostRela.getAcc_month());
		mode.addAttribute("emp_kind_code", costBonusCostRela.getEmp_kind_code());
		mode.addAttribute("bonus_item_code", costBonusCostRela.getBonus_item_code());
		mode.addAttribute("emp_kind_name", costBonusCostRela.getEmp_kind_name());
		mode.addAttribute("bonus_item_name", costBonusCostRela.getBonus_item_name());
		mode.addAttribute("cost_item_id", costBonusCostRela.getCost_item_id());
		mode.addAttribute("cost_item_no", costBonusCostRela.getCost_item_no());
		mode.addAttribute("cost_item_name", costBonusCostRela.getCost_item_name());
		return "hrp/cost/costbonuscostrela/costBonusCostRelaUpdate";
	}
	/**
	*奖金项与成本项目的对应关系<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/updateCostBonusCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostBonusCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costBonusCostRelaJson = costBonusCostRelaService.updateCostBonusCostRela(mapVo);
		
		return JSONObject.parseObject(costBonusCostRelaJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/costBonusCostRelaImportPage", method = RequestMethod.GET)
	public String costBonusCostRelaImportPage(Model mode) throws Exception {

		return "hrp/cost/costbonuscostrela/costBonusCostRelaImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costbonuscostrela/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","奖金项与成本项目的对应关系.xls");
	    return null; 
	 }
	 
	/**
	*奖金项与成本项目的对应关系<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costbonuscostrela/readCostBonusCostRelaFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostBonusCostRela> list_err = new ArrayList<CostBonusCostRela>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostBonusCostRela costBonusCostRela = new CostBonusCostRela();
				
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
						
						String year_month = temp[0].toString();
						
						costBonusCostRela.setYear_month(temp[0]);
						
						costBonusCostRela.setAcc_year(year_month.substring(0, 4));
						
						costBonusCostRela.setAcc_month(year_month.substring(4, 6));

						mapVo.put("acc_year", year_month.substring(0, 4));
						
						mapVo.put("acc_month", year_month.substring(4, 6));

					} else {

						err_sb.append("统计年月为空  ");

					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costBonusCostRela.setEmp_kind_code(temp[1]);
						
						mapVo.put("emp_kind_code", temp[1]);
					} else {
						err_sb.append("职工分类为空  ");
					}
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costBonusCostRela.setBonus_item_code(temp[3]);
						
						mapVo.put("bonus_item_code", temp[3]);
					} else {
						err_sb.append("奖金项编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[5])) {
						
						//costBonusCostRela.setCost_item_code(temp[5]);
						
						mapVo.put("cost_item_code", temp[5]);
					} else {
						err_sb.append("成本项目编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costBonusCostRela.setEmp_kind_name(temp[2]);
					}
					if (StringUtils.isNotEmpty(temp[4])) {
						
						costBonusCostRela.setBonus_item_name(temp[4]);

					}
					if (StringUtils.isNotEmpty(temp[6])) {
						
						costBonusCostRela.setCost_item_name(temp[6]);
	
					} 
				CostBonusCostRela data_exc_extis = costBonusCostRelaService.queryCostBonusCostRelaByCode(mapVo);
				
				CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
				
				CostBonusItemArrt costBonusItemArrt = costBonusItemArrtService.queryCostBonusItemArrtByCode(mapVo);
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));
				
				byCodeMap.put("is_stop", "0");
				
				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_id", cidn.getCost_item_id());
					
					mapVo.put("cost_item_no", cidn.getCost_item_no());
					
					costBonusCostRela.setCost_item_code((String)mapVo.get("cost_item_code"));
					
				}else{
					
					costBonusCostRela.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					err_sb.append("成本项目不存在 ");
					
				}

				if (costEmpTypeAttr == null) {
					
					err_sb.append("职工分类编码不存在！ ");
					
				}
				
				if (costBonusItemArrt == null) {
					
					err_sb.append("奖金项编码不存在！ ");
					
				}
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					costBonusCostRela.setError_type(err_sb.toString());
					
					list_err.add(costBonusCostRela);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostBonusCostRela data_exc = new CostBonusCostRela();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costBonusCostRelaService.addBatchCostBonusCostRela(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*奖金项与成本项目的对应关系<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/addBatchCostBonusCostRela", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostBonusCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostBonusCostRela> list_err = new ArrayList<CostBonusCostRela>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			StringBuffer err_sb = new StringBuffer();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			//mapVo.put("id", jsonObj.get("id"));
			
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String year_month = String.valueOf(jsonObj.get("year_month"));
			
			mapVo.put("acc_year", year_month.substring(0, 4));
			
			mapVo.put("acc_month", year_month.substring(4, 6));
			
			mapVo.put("emp_kind_code", jsonObj.get("emp_kind_code"));
			
			mapVo.put("emp_kind_name", jsonObj.get("emp_kind_name"));
			
			mapVo.put("bonus_item_code", jsonObj.get("bonus_item_code"));
			
			mapVo.put("bonus_item_name", jsonObj.get("bonus_item_name"));
			
			mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));

			CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(mapVo);

			if(cidn != null ){
				
				mapVo.put("cost_item_id", cidn.getCost_item_id());
				
				mapVo.put("cost_item_no", cidn.getCost_item_no());
				
				
			}else{
				
				err_sb.append("成本项目不存在 ");
				
			}
			
			mapVo.put("cost_item_name", jsonObj.get("cost_item_name"));
			
			Map<String, Object> byCodeMap = new HashMap<String, Object>();

			byCodeMap.put("group_id", mapVo.get("group_id"));

			byCodeMap.put("hos_id", mapVo.get("hos_id"));

			byCodeMap.put("copy_code", mapVo.get("copy_code"));
			
			byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));
			
			byCodeMap.put("is_stop", "0");
			
		   
			CostBonusCostRela data_exc_extis = costBonusCostRelaService.queryCostBonusCostRelaByCode(mapVo);
			
			CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
			
			CostBonusItemArrt costBonusItemArrt = costBonusItemArrtService.queryCostBonusItemArrtByCode(mapVo);

			if (costEmpTypeAttr == null) {
				
				err_sb.append("职工分类编码不存在！ ");
				
			}
			
			if (costBonusItemArrt == null) {
				
				err_sb.append("奖金项编码不存在！ ");
				
			}
			
			if (data_exc_extis != null) {
				
				err_sb.append("编码已经存在！ ");
				
			}
				
				CostBonusCostRela costBonusCostRela = new CostBonusCostRela();
				
				if (err_sb.toString().length() > 0) {
					//costBonusCostRela.setId(Long.valueOf(mapVo.get("id").toString()));
					
					costBonusCostRela.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costBonusCostRela.setAcc_year(mapVo.get("acc_year").toString());
					
					costBonusCostRela.setAcc_month(mapVo.get("acc_month").toString());
					
					costBonusCostRela.setEmp_kind_code(mapVo.get("emp_kind_code").toString());
					
					costBonusCostRela.setEmp_kind_name(mapVo.get("emp_kind_name").toString());
					
					costBonusCostRela.setBonus_item_code(mapVo.get("bonus_item_code").toString());
					
					costBonusCostRela.setBonus_item_name(mapVo.get("bonus_item_name").toString());
					
					costBonusCostRela.setCost_item_code(mapVo.get("cost_item_code").toString());
					
					costBonusCostRela.setCost_item_name(mapVo.get("cost_item_name").toString());
			
					costBonusCostRela.setError_type(err_sb.toString());
					
					list_err.add(costBonusCostRela);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costBonusCostRelaService.addBatchCostBonusCostRela(list_batch);
				
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
	*2016/10/26 lxj 
	*奖金项与成本项目的对应关系<BR>
	*维护页面跳转
	*/
	// 继承页面
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/costBonusCostRelaExtendPage", method = RequestMethod.GET)
	public String costBonusCostRelaExtendPage(Model mode) throws Exception {

		return "hrp/cost/costbonuscostrela/costBonusCostRelaExtend";

	}
	
	/**
	*2016/10/26 lxj 
	*奖金项与成本项目的对应关系<BR>
	*继承
	*/
	@RequestMapping(value = "/hrp/cost/costbonuscostrela/extendCostBonusCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendCostBonusCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costBonusCostRelaJson = costBonusCostRelaService.extendCostBonusCostRela(mapVo);

		return JSONObject.parseObject(costBonusCostRelaJson);
		
	}
}

