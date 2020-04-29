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
import com.chd.hrp.cost.entity.CostClinicWork;
import com.chd.hrp.cost.entity.CostOutAcctVouch;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostClinicWorkServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostOutAcctVouchServiceImpl;

/**
* @Title. @Description.
* 门诊工作量表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/ 
 

@Controller
public class CostClinicWorkController extends BaseController{
	private static Logger logger = Logger.getLogger(CostClinicWorkController.class);
	
	
	@Resource(name = "costClinicWorkService")
	private final CostClinicWorkServiceImpl costClinicWorkService = null;
	
	@Resource(name = "costOutAcctVouchService")
	private final CostOutAcctVouchServiceImpl costOutAcctVouchService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
   
   
	/**
	*门诊工作量表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costclinicwork/costClinicWorkMainPage", method = RequestMethod.GET)
	public String costClinicWorkMainPage(Model mode) throws Exception {

		return "hrp/cost/costclinicwork/costClinicWorkMain";

	}
	/**
	*门诊工作量表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costclinicwork/costClinicWorkAddPage", method = RequestMethod.GET)
	public String costClinicWorkAddPage(Model mode) throws Exception {

		return "hrp/cost/costclinicwork/costClinicWorkAdd";

	}
	/**
	*门诊工作量表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costclinicwork/addCostClinicWork", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostClinicWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costClinicWorkJson = costClinicWorkService.addCostClinicWork(mapVo);

		return JSONObject.parseObject(costClinicWorkJson);
		
	}
	/**
	*门诊工作量表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costclinicwork/queryCostClinicWork", method = RequestMethod.POST)
	@ResponseBody 
	
	public Map<String, Object> queryCostClinicWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String para_value = MyConfig.getSysPara("03001");
		
		mapVo.put("is_flag", para_value);  
		
		String costClinicWork = costClinicWorkService.queryCostClinicWork(getPage(mapVo));

		return JSONObject.parseObject(costClinicWork);
		
	}
	/**
	*门诊工作量表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costclinicwork/deleteCostClinicWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostClinicWork(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
			mapVo.put("patient_type_code",ids[7]); 
			
            listVo.add(mapVo);
        }
		String costClinicWorkJson = costClinicWorkService.deleteBatchCostClinicWork(listVo);
	   return JSONObject.parseObject(costClinicWorkJson);
			
	}
	
	/**
	*门诊工作量表<BR>
	*按月删除
	*/
	@RequestMapping(value = "/hrp/cost/costclinicwork/deleteMonthlyCostClinicWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMonthlyCostClinicWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			
	        String costClinicWorkJson;
	        
	        try {
	        	
	        	costClinicWorkJson = costClinicWorkService.deleteMonthlyCostClinicWork(mapVo);
	        	
			} catch (Exception e) {
				
				costClinicWorkJson = e.getMessage();
			}
		
	   return JSONObject.parseObject(costClinicWorkJson);
			
	}
	
	/**
	*门诊工作量表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costclinicwork/costClinicWorkUpdatePage", method = RequestMethod.GET)
	
	public String costClinicWorkUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostClinicWork costClinicWork = new CostClinicWork();
        
        String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
        
		costClinicWork = costClinicWorkService.queryCostClinicWorkByCode(mapVo);
		
		mode.addAttribute("group_id", costClinicWork.getGroup_id());
		
		mode.addAttribute("hos_id", costClinicWork.getHos_id());
		
		mode.addAttribute("copy_code", costClinicWork.getCopy_code());
		
		mode.addAttribute("year_month", costClinicWork.getAcc_year().toString() + costClinicWork.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costClinicWork.getAcc_year());
		mode.addAttribute("acc_month", costClinicWork.getAcc_month());
		
		mode.addAttribute("dept_id", costClinicWork.getDept_id());
		
		mode.addAttribute("dept_no", costClinicWork.getDept_no());
		
		mode.addAttribute("dept_code", costClinicWork.getDept_code());
		
		mode.addAttribute("dept_name", costClinicWork.getDept_name());
		
		mode.addAttribute("patient_type_code", costClinicWork.getPatient_type_code());
		
		mode.addAttribute("patient_name", costClinicWork.getPatient_name());
		
		mode.addAttribute("clinic_num", costClinicWork.getClinic_num());
		
		mode.addAttribute("operation_num", costClinicWork.getOperation_num());
		
		return "hrp/cost/costclinicwork/costClinicWorkUpdate";
	}
	/**
	*门诊工作量表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costclinicwork/updateCostClinicWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostClinicWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}  
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costClinicWorkJson = costClinicWorkService.updateCostClinicWork(mapVo);
		
		return JSONObject.parseObject(costClinicWorkJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costclinicwork/costClinicWorkImportPage", method = RequestMethod.GET)
	public String costClinicWorkImportPage(Model mode) throws Exception {

		return "hrp/cost/costclinicwork/costClinicWorkImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costclinicwork/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				
	    printTemplate(request,response,"cost\\基础设置","门诊工作量采集.xls");
		
	    return null; 
	 }
	 
	/**
	*门诊工作量表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costclinicwork/costClinicWorkImportPage",method = RequestMethod.POST)  
	@ResponseBody
	public String readChargeKindDictFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		try {
			
			String reJson=costClinicWorkService.readAssFinaDictFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
		
    }  
	/**
	*门诊工作量表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costclinicwork/addBatchCostClinicWork", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostClinicWork(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostClinicWork> list_err = new ArrayList<CostClinicWork>();
		
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
			
			mapVo.put("patient_type_code", jsonObj.get("patient_type_code"));
			
			mapVo.put("patient_name", jsonObj.get("patient_name"));
			
			mapVo.put("clinic_num", jsonObj.get("clinic_num"));
			
			mapVo.put("operation_num", jsonObj.get("operation_num"));
			
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
		
		byCodeMap.put("patient_code", mapVo.get("patient_type_code"));
		
		CostClinicWork costPatient = costClinicWorkService.queryCostPatientType(byCodeMap);
		
		if(costPatient == null ){
			
			err_sb.append("患者类别不存在 ");
			
		}
		   
				CostClinicWork data_exc_extis = costClinicWorkService.queryCostClinicWorkByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostClinicWork costClinicWork = new CostClinicWork();
				
				if (err_sb.toString().length() > 0) {
					costClinicWork.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costClinicWork.setAcc_year(mapVo.get("acc_year").toString());
					
					costClinicWork.setAcc_month(mapVo.get("acc_month").toString());
					
					costClinicWork.setDept_code(mapVo.get("dept_code").toString());
					
					costClinicWork.setDept_name(mapVo.get("dept_name").toString());
					
					costClinicWork.setPatient_type_code(mapVo.get("patient_type_code").toString());
					
					costClinicWork.setPatient_name(mapVo.get("patient_name").toString());
					
					costClinicWork.setClinic_num(Long.valueOf(mapVo.get("clinic_num").toString()));
					
					costClinicWork.setOperation_num(Long.valueOf(mapVo.get("operation_num").toString()));
					
					costClinicWork.setError_type(err_sb.toString());
					
					list_err.add(costClinicWork);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costClinicWorkService.addBatchCostClinicWork(list_batch);
				
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

