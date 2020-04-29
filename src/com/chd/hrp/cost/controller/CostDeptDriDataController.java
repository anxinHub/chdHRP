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
import com.chd.hrp.cost.entity.CostDeptDriData;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostDeptDriDataServiceImpl;

/**
* @Title. @Description.
* 科室直接成本表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostDeptDriDataController extends BaseController{
	private static Logger logger = Logger.getLogger(CostDeptDriDataController.class);
	
	
	@Resource(name = "costDeptDriDataService")
	private final CostDeptDriDataServiceImpl costDeptDriDataService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
   
   
	/**
	*科室直接成本表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costdeptdridata/costDeptDriDataMainPage", method = RequestMethod.GET)
	public String costDeptDriDataMainPage(Model mode) throws Exception {

		return "hrp/cost/costdeptdridata/costDeptDriDataMain";

	}
	/**
	*科室直接成本表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costdeptdridata/costDeptDriDataAddPage", method = RequestMethod.GET)
	public String costDeptDriDataAddPage(Model mode) throws Exception {

		return "hrp/cost/costdeptdridata/costDeptDriDataAdd";

	}
	/**
	*科室直接成本表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costdeptdridata/addCostDeptDriData", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostDeptDriData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costDeptDriDataJson = costDeptDriDataService.addCostDeptDriData(mapVo);

		return JSONObject.parseObject(costDeptDriDataJson);
		
	}
	/**
	*科室直接成本表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costdeptdridata/queryCostDeptDriData", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostDeptDriData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
		mapVo.put("is_flag", para_value); 
		
		String costDeptDriData = costDeptDriDataService.queryCostDeptDriData(getPage(mapVo));

		return JSONObject.parseObject(costDeptDriData);
		
	}
	/**
	*科室直接成本表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costdeptdridata/deleteCostDeptDriData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostDeptDriData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			
			mapVo.put("hos_id",ids[1]);   
			
			mapVo.put("copy_code",ids[2]);   
			
			mapVo.put("year_month",ids[3]);   
			
			mapVo.put("dept_id",ids[4]);   
			
			mapVo.put("dept_no",ids[5]);   
			
			mapVo.put("cost_item_id",ids[6]);   
			
			mapVo.put("cost_item_no",ids[7]);   
			
			mapVo.put("source_id",ids[8]); 
			
            listVo.add(mapVo);
        }
		String costDeptDriDataJson = costDeptDriDataService.deleteBatchCostDeptDriData(listVo);
	   return JSONObject.parseObject(costDeptDriDataJson);
			
	}
	
	/**
	*科室直接成本表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costdeptdridata/costDeptDriDataUpdatePage", method = RequestMethod.GET)
	
	public String costDeptDriDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostDeptDriData costDeptDriData = new CostDeptDriData();
		costDeptDriData = costDeptDriDataService.queryCostDeptDriDataByCode(mapVo);
		mode.addAttribute("group_id", costDeptDriData.getGroup_id());
		
		mode.addAttribute("hos_id", costDeptDriData.getHos_id());
		
		mode.addAttribute("copy_code", costDeptDriData.getCopy_code());
		
		mode.addAttribute("year_month", costDeptDriData.getYear_month());
		
		mode.addAttribute("dept_id", costDeptDriData.getDept_id());
		
		mode.addAttribute("dept_no", costDeptDriData.getDept_no());
		
		mode.addAttribute("cost_item_id", costDeptDriData.getCost_item_id());
		
		mode.addAttribute("cost_item_no", costDeptDriData.getCost_item_no());
		
		mode.addAttribute("source_id", costDeptDriData.getSource_id());
		
		mode.addAttribute("dir_amount", costDeptDriData.getDir_amount());
		
		return "hrp/cost/costdeptdridata/costDeptDriDataUpdate";
	}
	/**
	*科室直接成本表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costdeptdridata/updateCostDeptDriData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostDeptDriData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costDeptDriDataJson = costDeptDriDataService.updateCostDeptDriData(mapVo);
		
		return JSONObject.parseObject(costDeptDriDataJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costdeptdridata/costDeptDriDataImportPage", method = RequestMethod.GET)
	public String costDeptDriDataImportPage(Model mode) throws Exception {

		return "hrp/cost/costdeptdridata/costDeptDriDataImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costdeptdridata/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				
	    printTemplate(request,response,"cost\\对应目录","对应字典.xls");
		
	    return null; 
	 }
	 
	/**
	*科室直接成本表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costdeptdridata/readCostDeptDriDataFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostDeptDriData> list_err = new ArrayList<CostDeptDriData>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostDeptDriData costDeptDriData = new CostDeptDriData();
				
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
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costDeptDriData.setYear_month(temp[3]);
						
						mapVo.put("year_month", temp[3]);
					} else {
						err_sb.append("统计年月为空  ");
					}
					if (StringUtils.isNotEmpty(temp[4])) {
						
						costDeptDriData.setDept_id(Long.valueOf(temp[4]));
						
						mapVo.put("dept_id", temp[4]);
					} else {
						err_sb.append("科室ID为空  ");
					}
					if (StringUtils.isNotEmpty(temp[5])) {
						
						costDeptDriData.setDept_no(Long.valueOf(temp[5]));
						
						mapVo.put("dept_no", temp[5]);
					} else {
						err_sb.append("科室变更ID为空  ");
					}
					if (StringUtils.isNotEmpty(temp[6])) {
						
						costDeptDriData.setCost_item_id(Long.valueOf(temp[6]));
						
						mapVo.put("cost_item_id", temp[6]);
					} else {
						err_sb.append("成本项目ID为空  ");
					}
					if (StringUtils.isNotEmpty(temp[7])) {
						
						costDeptDriData.setCost_item_no(Long.valueOf(temp[7]));
						
						mapVo.put("cost_item_no", temp[7]);
					} else {
						err_sb.append("成本项目变更ID为空  ");
					}
					if (StringUtils.isNotEmpty(temp[8])) {
						
						costDeptDriData.setSource_id(Long.valueOf(temp[8]));
						
						mapVo.put("source_id", temp[8]);
					} else {
						err_sb.append("资金来源为空  ");
					}
					if (StringUtils.isNotEmpty(temp[9])) {
						
						costDeptDriData.setDir_amount(Double.valueOf(temp[9]));
						
						mapVo.put("dir_amount", temp[9]);
					} else {
						err_sb.append("直接成本为空  ");
					}
				CostDeptDriData data_exc_extis = costDeptDriDataService.queryCostDeptDriDataByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costDeptDriData.setError_type(err_sb.toString());
					
					list_err.add(costDeptDriData);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostDeptDriData data_exc = new CostDeptDriData();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costDeptDriDataService.addBatchCostDeptDriData(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*科室直接成本表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costdeptdridata/addBatchCostDeptDriData", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostDeptDriData(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostDeptDriData> list_err = new ArrayList<CostDeptDriData>();
		
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
			
			mapVo.put("year_month", jsonObj.get("year_month"));
			
			mapVo.put("dept_id", jsonObj.get("dept_id"));
			
			mapVo.put("dept_no", jsonObj.get("dept_no"));
			
			mapVo.put("cost_item_id", jsonObj.get("cost_item_id"));
			
			mapVo.put("cost_item_no", jsonObj.get("cost_item_no"));
			
			mapVo.put("source_id", jsonObj.get("source_id"));
			
			mapVo.put("dir_amount", jsonObj.get("dir_amount"));
			
		   
				CostDeptDriData data_exc_extis = costDeptDriDataService.queryCostDeptDriDataByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostDeptDriData costDeptDriData = new CostDeptDriData();
				
				if (err_sb.toString().length() > 0) {
					costDeptDriData.setYear_month(mapVo.get("year_month").toString());
					
					costDeptDriData.setDept_id(Long.valueOf(mapVo.get("dept_id").toString()));
					
					costDeptDriData.setDept_no(Long.valueOf(mapVo.get("dept_no").toString()));
					
					costDeptDriData.setCost_item_id(Long.valueOf(mapVo.get("cost_item_id").toString()));
					
					costDeptDriData.setCost_item_no(Long.valueOf(mapVo.get("cost_item_no").toString()));
					
					costDeptDriData.setSource_id(Long.valueOf(mapVo.get("source_id").toString()));
					
					costDeptDriData.setDir_amount(Double.valueOf(mapVo.get("dir_amount").toString()));

					costDeptDriData.setError_type(err_sb.toString());
					
					list_err.add(costDeptDriData);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costDeptDriDataService.addBatchCostDeptDriData(list_batch);
				
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

