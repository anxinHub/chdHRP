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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostDeptArea;
import com.chd.hrp.cost.entity.CostOutAcctVouch;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostDeptAreaServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostOutAcctVouchServiceImpl;

/**
* @Title. @Description.
* 科室面积表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostDeptAreaController extends BaseController{
	private static Logger logger = Logger.getLogger(CostDeptAreaController.class);
	
	
	@Resource(name = "costDeptAreaService")
	private final CostDeptAreaServiceImpl costDeptAreaService = null;
   
	@Resource(name = "costOutAcctVouchService")
	private final CostOutAcctVouchServiceImpl costOutAcctVouchService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
   
	/**
	*科室面积表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costdeptarea/costDeptAreaMainPage", method = RequestMethod.GET)
	public String costDeptAreaMainPage(Model mode) throws Exception {

		return "hrp/cost/costdeptarea/costDeptAreaMain";

	}
	/**
	*科室面积表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costdeptarea/costDeptAreaAddPage", method = RequestMethod.GET)
	public String costDeptAreaAddPage(Model mode) throws Exception {

		return "hrp/cost/costdeptarea/costDeptAreaAdd";

	}
	/**
	*科室面积表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costdeptarea/addCostDeptArea", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostDeptArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costDeptAreaJson = costDeptAreaService.addCostDeptArea(mapVo);

		return JSONObject.parseObject(costDeptAreaJson);
		
	}
	/**
	*科室面积表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costdeptarea/queryCostDeptArea", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostDeptArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		 
		String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
    	
		String costDeptArea = costDeptAreaService.queryCostDeptArea(getPage(mapVo));

		return JSONObject.parseObject(costDeptArea);
		
	}
	/**
	*科室面积表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costdeptarea/deleteCostDeptArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostDeptArea(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			
			mapVo.put("hos_id",ids[1]);   
			
			mapVo.put("copy_code",ids[2]);   
			
			mapVo.put("acc_year",ids[3]);   
			mapVo.put("acc_month",ids[4]);   
			
			mapVo.put("dept_id",ids[5]);   
			
			mapVo.put("dept_no",ids[6]); 
			
            listVo.add(mapVo);
        }
		String costDeptAreaJson = costDeptAreaService.deleteBatchCostDeptArea(listVo);
	   return JSONObject.parseObject(costDeptAreaJson);
			
	}
	
	/**
	*科室面积表<BR>
	*按月删除
	*/
	@RequestMapping(value = "/hrp/cost/costdeptarea/deleteMonthlyCostDeptArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMonthlyCostDeptArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

        mapVo.put("copy_code", SessionManager.getCopyCode());
			
			
		String costDeptAreaJson;
	
        try {
			
        	costDeptAreaJson = costDeptAreaService.deleteMonthlyCostDeptArea(mapVo);
        	
		  } catch (Exception e) {
			// TODO: handle exception
			  costDeptAreaJson = e.getMessage();
		}
		
	   return JSONObject.parseObject(costDeptAreaJson);
			
	}
	
	/**
	*科室面积表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costdeptarea/costDeptAreaUpdatePage", method = RequestMethod.GET)
	
	public String costDeptAreaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostDeptArea costDeptArea = new CostDeptArea();
        
        String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
        
		costDeptArea = costDeptAreaService.queryCostDeptAreaByCode(mapVo);
		mode.addAttribute("group_id", costDeptArea.getGroup_id());
		
		mode.addAttribute("hos_id", costDeptArea.getHos_id());
		
		mode.addAttribute("copy_code", costDeptArea.getCopy_code());
		
		mode.addAttribute("year_month", costDeptArea.getAcc_year().toString() + costDeptArea.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costDeptArea.getAcc_year());
		mode.addAttribute("acc_month", costDeptArea.getAcc_month());
		
		mode.addAttribute("dept_id", costDeptArea.getDept_id());
		
		mode.addAttribute("dept_no", costDeptArea.getDept_no());
		
		mode.addAttribute("dept_code", costDeptArea.getDept_code());
		
		mode.addAttribute("dept_name", costDeptArea.getDept_name());
		
		mode.addAttribute("arear", costDeptArea.getArear());
		
		return "hrp/cost/costdeptarea/costDeptAreaUpdate";
	}
	/**
	*科室面积表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costdeptarea/updateCostDeptArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostDeptArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId()); 
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costDeptAreaJson = costDeptAreaService.updateCostDeptArea(mapVo);
		
		return JSONObject.parseObject(costDeptAreaJson);
	}
	
	
	/**
	*科室面积继承页面跳转<BR>
	*/
	@RequestMapping(value = "/hrp/cost/costdeptarea/costDeptAreaExtendMainPage", method = RequestMethod.GET)
	public String costDeptAreaExtendMainPage(Model mode) throws Exception {
		return "hrp/cost/costdeptarea/costDeptAreaExtendMain";
	}
	
	/**
	*科室面积继承<BR>
	*/
	@RequestMapping(value = "/hrp/cost/costdeptarea/extendCostDeptArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendCostDeptArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

        mapVo.put("copy_code", SessionManager.getCopyCode());
			
			
		String costDeptAreaJson;
	
        try {
			
        	costDeptAreaJson = costDeptAreaService.extendCostDeptArea(mapVo);
        	
		  } catch (Exception e) {
			// TODO: handle exception
			  costDeptAreaJson = e.getMessage();
		}
		
	   return JSONObject.parseObject(costDeptAreaJson);
			
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costdeptarea/costDeptAreaImportPage", method = RequestMethod.GET)
	public String costDeptAreaImportPage(Model mode) throws Exception {

		return "hrp/cost/costdeptarea/costDeptAreaImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costdeptarea/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				
	    printTemplate(request,response,"cost\\基础设置","科室面积采集.xls");
		
	    return null; 
	 }
	 
	/**
	*科室面积表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costdeptarea/costDeptAreaImportPage",method = RequestMethod.POST)  
	@ResponseBody
	public String readChargeKindDictFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		try {
			
			String reJson=costDeptAreaService.readAssFinaDictFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
			
		
    }  
	/**
	*科室面积表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costdeptarea/addBatchCostDeptArea", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostDeptArea(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostDeptArea> list_err = new ArrayList<CostDeptArea>();
		
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
			
			String year_month = String.valueOf(jsonObj.get("year_month"));
			
			mapVo.put("acc_year", year_month.substring(0, 4));
			
			mapVo.put("acc_month", year_month.substring(4, 6));
			
			mapVo.put("dept_code", jsonObj.get("dept_code"));
			
			mapVo.put("dept_name", jsonObj.get("dept_name"));
			
			mapVo.put("arear", jsonObj.get("arear"));
			
			Map<String, Object> byCodeMap = new HashMap<String, Object>();

			byCodeMap.put("group_id", mapVo.get("group_id"));

			byCodeMap.put("hos_id", mapVo.get("hos_id"));

			byCodeMap.put("copy_code", mapVo.get("copy_code"));
			
			byCodeMap.put("dept_code", mapVo.get("dept_code"));
			
			byCodeMap.put("is_stop", "0");
		
			CostOutAcctVouch costDept = costOutAcctVouchService.queryCostDeptByCode(byCodeMap);
			
			if(costDept != null ){
				
				mapVo.put("dept_id", costDept.getDept_id());
				
				mapVo.put("dept_no", costDept.getDept_no());
				
			}else{
				
				err_sb.append("科室不存在 ");
				
			}
		   
			if(costDept != null) {
				
				CostDeptArea data_exc_extis = costDeptAreaService.queryCostDeptAreaByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
			}
				
				CostDeptArea costDeptArea = new CostDeptArea();
				
				if (err_sb.toString().length() > 0) {
					costDeptArea.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costDeptArea.setAcc_year(mapVo.get("acc_year").toString());
					
					costDeptArea.setAcc_month(mapVo.get("acc_month").toString());
					
					costDeptArea.setDept_code(mapVo.get("dept_code").toString());
					
					costDeptArea.setDept_name(mapVo.get("dept_name").toString());
					
					costDeptArea.setArear(Double.valueOf(mapVo.get("arear").toString()));
					
					costDeptArea.setError_type(err_sb.toString());
					
					list_err.add(costDeptArea);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costDeptAreaService.addBatchCostDeptArea(list_batch);
				
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

