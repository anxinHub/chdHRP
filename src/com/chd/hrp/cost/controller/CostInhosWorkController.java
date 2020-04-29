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
import com.chd.hrp.cost.entity.CostClinicWork;
import com.chd.hrp.cost.entity.CostInhosWork;
import com.chd.hrp.cost.entity.CostOutAcctVouch;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostClinicWorkServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostInhosWorkServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostOutAcctVouchServiceImpl;

/**
* @Title. @Description.
* 住院工作量表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0  
*/


@Controller
public class CostInhosWorkController extends BaseController{
	private static Logger logger = Logger.getLogger(CostInhosWorkController.class);
	
	
	@Resource(name = "costInhosWorkService")
	private final CostInhosWorkServiceImpl costInhosWorkService = null;
	
	@Resource(name = "costClinicWorkService")
	private final CostClinicWorkServiceImpl costClinicWorkService = null;
	
	@Resource(name = "costOutAcctVouchService")
	private final CostOutAcctVouchServiceImpl costOutAcctVouchService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
   
   
	/**
	*住院工作量表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costinhoswork/costInhosWorkMainPage", method = RequestMethod.GET)
	public String costInhosWorkMainPage(Model mode) throws Exception {

		return "hrp/cost/costinhoswork/costInhosWorkMain";

	}
	/**
	*住院工作量表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costinhoswork/costInhosWorkAddPage", method = RequestMethod.GET)
	public String costInhosWorkAddPage(Model mode) throws Exception {

		return "hrp/cost/costinhoswork/costInhosWorkAdd";

	}
	/**
	*住院工作量表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costinhoswork/addCostInhosWork", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostInhosWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costInhosWorkJson = costInhosWorkService.addCostInhosWork(mapVo);

		return JSONObject.parseObject(costInhosWorkJson);
		
	}
	/**
	*住院工作量表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costinhoswork/queryCostInhosWork", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostInhosWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String costInhosWork = costInhosWorkService.queryCostInhosWork(getPage(mapVo));

		return JSONObject.parseObject(costInhosWork);
		
	}
	/**
	*住院工作量表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costinhoswork/deleteCostInhosWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostInhosWork(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String costInhosWorkJson = costInhosWorkService.deleteBatchCostInhosWork(listVo);
	   return JSONObject.parseObject(costInhosWorkJson);
			
	}
	
	/**
	*住院工作量表<BR>
	*按月删除
	*/
	@RequestMapping(value = "/hrp/cost/costinhoswork/deleteMonthlyCostInhosWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMonthlyCostInhosWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String costInhosWorkJson ;
		
		   try {
			   
				 costInhosWorkJson = costInhosWorkService.deleteMonthlyCostInhosWork(mapVo);
	        	
			} catch (Exception e) {
				
				costInhosWorkJson = e.getMessage();
			}
	
	   return JSONObject.parseObject(costInhosWorkJson);
			
	}
	
	/**
	*住院工作量表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costinhoswork/costInhosWorkUpdatePage", method = RequestMethod.GET)
	
	public String costInhosWorkUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostInhosWork costInhosWork = new CostInhosWork();
        
	    String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
        
		costInhosWork = costInhosWorkService.queryCostInhosWorkByCode(mapVo);
		mode.addAttribute("group_id", costInhosWork.getGroup_id());
		
		mode.addAttribute("hos_id", costInhosWork.getHos_id());
		
		mode.addAttribute("copy_code", costInhosWork.getCopy_code());
		
		mode.addAttribute("year_month", costInhosWork.getAcc_year().toString() + costInhosWork.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costInhosWork.getAcc_year());
		mode.addAttribute("acc_month", costInhosWork.getAcc_month());
		
		mode.addAttribute("dept_id", costInhosWork.getDept_id());
		
		mode.addAttribute("dept_no", costInhosWork.getDept_no());
		
		mode.addAttribute("dept_code", costInhosWork.getDept_code());
		
		mode.addAttribute("dept_name", costInhosWork.getDept_name());
		
		mode.addAttribute("patient_type_code", costInhosWork.getPatient_type_code());
		
		mode.addAttribute("patient_name", costInhosWork.getPatient_name());
		
		mode.addAttribute("in_hos_num", costInhosWork.getIn_hos_num());
		
		mode.addAttribute("out_hos_num", costInhosWork.getOut_hos_num());
		
		mode.addAttribute("set_bed_num", costInhosWork.getSet_bed_num());
		
		mode.addAttribute("open_bed_num", costInhosWork.getOpen_bed_num());
		
		mode.addAttribute("bed_use_rate", costInhosWork.getBed_use_rate());
		
		mode.addAttribute("bed_use_day_num", costInhosWork.getBed_use_day_num());
		mode.addAttribute("bed_out_day_num", costInhosWork.getBed_out_day_num());
		
		return "hrp/cost/costinhoswork/costInhosWorkUpdate";
	}
	/**
	*住院工作量表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costinhoswork/updateCostInhosWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostInhosWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costInhosWorkJson = costInhosWorkService.updateCostInhosWork(mapVo);
		
		return JSONObject.parseObject(costInhosWorkJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costinhoswork/costInhosWorkImportPage", method = RequestMethod.GET)
	public String costInhosWorkImportPage(Model mode) throws Exception {

		return "hrp/cost/costinhoswork/costInhosWorkImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costinhoswork/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				
	    printTemplate(request,response,"cost\\基础设置","住院工作量表.xls");
		
	    return null; 
	 }
	 
	/**
	*住院工作量表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costinhoswork/costInhosWorkImportPage",method = RequestMethod.POST)  
	@ResponseBody
	public String readChargeKindDictFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		try {
			
			String reJson=costInhosWorkService.readAssFinaDictFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		} 
			
    }  
	/**
	*住院工作量表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costinhoswork/addBatchCostInhosWork", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostInhosWork(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostInhosWork> list_err = new ArrayList<CostInhosWork>();
		
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
			
			mapVo.put("in_hos_num", jsonObj.get("in_hos_num"));
			
			mapVo.put("out_hos_num", jsonObj.get("out_hos_num"));
			
			mapVo.put("set_bed_num", jsonObj.get("set_bed_num"));
			
			mapVo.put("open_bed_num", jsonObj.get("open_bed_num"));
			
			mapVo.put("bed_use_rate", jsonObj.get("bed_use_rate"));
			
			mapVo.put("bed_use_day_num", jsonObj.get("bed_use_day_num"));

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
		   
				CostInhosWork data_exc_extis = costInhosWorkService.queryCostInhosWorkByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostInhosWork costInhosWork = new CostInhosWork();
				
				if (err_sb.toString().length() > 0) {
					costInhosWork.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costInhosWork.setAcc_year(mapVo.get("acc_year").toString());
					
					costInhosWork.setAcc_month(mapVo.get("acc_month").toString());
					
					costInhosWork.setDept_code(mapVo.get("dept_code").toString());
					
					costInhosWork.setDept_name(mapVo.get("dept_name").toString());
					
					costInhosWork.setPatient_type_code(mapVo.get("patient_type_code").toString());
					
					costInhosWork.setPatient_name(mapVo.get("patient_name").toString());
					
					costInhosWork.setIn_hos_num(Long.valueOf(mapVo.get("in_hos_num").toString()));
					
					costInhosWork.setOut_hos_num(Long.valueOf(mapVo.get("out_hos_num").toString()));
					
					costInhosWork.setSet_bed_num(Long.valueOf(mapVo.get("set_bed_num").toString()));
					
					costInhosWork.setOpen_bed_num(Long.valueOf(mapVo.get("open_bed_num").toString()));
					
					costInhosWork.setBed_use_rate(Double.valueOf(mapVo.get("bed_use_rate").toString()));
					
					costInhosWork.setBed_use_day_num(Long.valueOf(mapVo.get("bed_use_day_num").toString()));
					
					costInhosWork.setError_type(err_sb.toString());
					
					list_err.add(costInhosWork);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costInhosWorkService.addBatchCostInhosWork(list_batch);
				
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

