package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.cost.entity.CostDoctorWork;
import com.chd.hrp.cost.entity.CostEmpTitleAttr;
import com.chd.hrp.cost.service.CostDoctorWorkService;

@Controller
public class CostDoctorWorkController  extends BaseController{

	private static Logger logger = Logger.getLogger(CostDoctorWorkController.class);
	
	@Resource(name = "costDoctorWorkService")
	private final CostDoctorWorkService costDoctorWorkService = null;

	/*
	 * 主页面跳转
	 * */
	@RequestMapping(value = "hrp/cost/costdoctorwork/costDoctorWorkMainPage", method = RequestMethod.GET)
	public String costDoctorWorkMainPage(Model mode) throws Exception {
		return "hrp/cost/costdoctorwork/costDoctorWorkMain";
	}
	
	/*
	 * 添加页面跳转
	 * */
	@RequestMapping(value = "/hrp/cost/costdoctorwork/costDoctorWorkAddPage", method = RequestMethod.GET)
	public String costDoctorWorkAddPage(Model mode) throws Exception {
		return "hrp/cost/costdoctorwork/costDoctorWorkAdd";
	}
	
	@RequestMapping(value = "/hrp/cost/costdoctorwork/addCostDoctorWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostDoctorWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String costEmpTitleAttrJson = "";
		try {
			
			costEmpTitleAttrJson = costDoctorWorkService.addCostDoctorWork(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			costEmpTitleAttrJson = e.getMessage();
		}
		return JSONObject.parseObject(costEmpTitleAttrJson);
	}
	
	/**
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costdoctorwork/queryCostDoctorWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDoctorWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String costDoctorWorkJson = costDoctorWorkService.queryCostDoctorWork(getPage(mapVo));
		return JSONObject.parseObject(costDoctorWorkJson);
		
	}
	
	/**
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costdoctorwork/deleteCostDoctorWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostDoctorWork(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("emp_id",ids[7]); 
			mapVo.put("emp_no",ids[8]); 
            listVo.add(mapVo);
        }
		String costDoctorWorkJson = "";
      try {
	      costDoctorWorkJson = costDoctorWorkService.deleteBatchCostDoctorWork(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			costDoctorWorkJson = e.getMessage();
		}
	   return JSONObject.parseObject(costDoctorWorkJson);
			
	}
	
	/**
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costdoctorwork/costDoctorWorkUpdatePage", method = RequestMethod.GET)
	public String costDoctorWorkUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        CostDoctorWork costDoctorWork = costDoctorWorkService.queryCostDoctorWorkByCode(mapVo);
        mode.addAttribute("group_id", costDoctorWork.getGroup_id());
        mode.addAttribute("hos_id", costDoctorWork.getHos_id());
        mode.addAttribute("copy_code", costDoctorWork.getCopy_code());
        mode.addAttribute("acc_year", costDoctorWork.getAcc_year());
        mode.addAttribute("acc_month", costDoctorWork.getAcc_month());
        mode.addAttribute("dept_id", costDoctorWork.getDept_id());
        mode.addAttribute("dept_no", costDoctorWork.getDept_no());
        mode.addAttribute("dept_code", costDoctorWork.getDept_code());
        mode.addAttribute("dept_name", costDoctorWork.getDept_name());
        mode.addAttribute("emp_id", costDoctorWork.getEmp_id());
        mode.addAttribute("emp_no", costDoctorWork.getEmp_no());
        mode.addAttribute("emp_code", costDoctorWork.getEmp_code());
        mode.addAttribute("emp_name", costDoctorWork.getEmp_name());
        mode.addAttribute("doctor_num", costDoctorWork.getDoctor_num());
		return "hrp/cost/costdoctorwork/costDoctorWorkUpdate";
	}
	
	/**
	*修改保存
	*/	
	@RequestMapping(value = "/hrp/cost/costdoctorwork/updateCostDoctorWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostDoctorWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		 String costDoctorWorkJson = "";
	      try {
		      costDoctorWorkJson = costDoctorWorkService.updateCostDoctorWork(mapVo);
			} catch (Exception e) {
				// TODO: handle exception
				costDoctorWorkJson = e.getMessage();
			}
	      
		return JSONObject.parseObject(costDoctorWorkJson);
	}
	
	/**
	*<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costdoctorwork/impCostDoctorWork",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impCostDoctorWork(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String costDoctorWorkJson = "";
		 
		try {
			
			costDoctorWorkJson=costDoctorWorkService.impCostDoctorWork(mapVo);
			
		} catch (Exception e) {
			
			costDoctorWorkJson = e.getMessage();
		}
		return JSONObject.parseObject(costDoctorWorkJson);
    }  
	
	
}
