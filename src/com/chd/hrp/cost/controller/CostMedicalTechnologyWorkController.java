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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.cost.entity.CostMedicalTechnologyWork;
import com.chd.hrp.cost.service.CostMedicalTechnologyWorkService;

/**
* @Title. @Description.
* 医技工作量
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostMedicalTechnologyWorkController extends BaseController{
	private static Logger logger = Logger.getLogger(CostMedicalTechnologyWorkController.class);
	
	
	@Resource(name = "costMedicalTechnologyWorkService")
	private final CostMedicalTechnologyWorkService costMedicalTechnologyWorkService = null;
	

	@RequestMapping(value = "/hrp/cost/costMedicalTechnologywork/costMedicalTechnologyWorkMainPage", method = RequestMethod.GET)
	public String costMedicalTechnologyWorkMainPage(Model mode) throws Exception {

		return "hrp/cost/costMedicalTechnologywork/costMedicalTechnologyWorkMain";

	}

	@RequestMapping(value = "/hrp/cost/costMedicalTechnologywork/costMedicalTechnologyWorkAddPage", method = RequestMethod.GET)
	public String costMedicalTechnologyWorkAddPage(Model mode) throws Exception {

		return "hrp/cost/costMedicalTechnologywork/costMedicalTechnologyWorkAdd";

	}

	@RequestMapping(value = "/hrp/cost/costMedicalTechnologywork/addCostMedicalTechnologyWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostMedicalTechnologyWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());

        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String costMedicalTechnologyWorkJson = "";
		
		try {
			
			costMedicalTechnologyWorkJson = costMedicalTechnologyWorkService.addCostMedicalTechnologyWork(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			costMedicalTechnologyWorkJson = e.getMessage();
		}

		return JSONObject.parseObject(costMedicalTechnologyWorkJson);
		
	}

	
	@RequestMapping(value = "/hrp/cost/costMedicalTechnologywork/queryCostMedicalTechnologyWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostMedicalTechnologyWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

        mapVo.put("copy_code", SessionManager.getCopyCode());
		
    	String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
		
		String costMedicalTechnologyWorkJson = costMedicalTechnologyWorkService.queryCostMedicalTechnologyWork(getPage(mapVo));

		return JSONObject.parseObject(costMedicalTechnologyWorkJson);
		
	}
	
	
	@RequestMapping(value = "/hrp/cost/costMedicalTechnologywork/deleteCostMedicalTechnologyWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostMedicalTechnologyWork(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String costMedicalTechnologyWorkJson  = "";
		try {
			costMedicalTechnologyWorkJson = costMedicalTechnologyWorkService.deleteBatchCostMedicalTechnologyWork(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			costMedicalTechnologyWorkJson = e.getMessage(); 
		}
	
	   return JSONObject.parseObject(costMedicalTechnologyWorkJson);
			
	}
	
	@RequestMapping(value = "/hrp/cost/costMedicalTechnologywork/costMedicalTechnologyWorkUpdatePage", method = RequestMethod.GET)
	public String costMedicalTechnologyWorkUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		  String para_value = MyConfig.getSysPara("03001");
			
	    	mapVo.put("is_flag", para_value);
        
    	CostMedicalTechnologyWork costMedicalTechnologyWork = costMedicalTechnologyWorkService.queryCostMedicalTechnologyWorkByCode(mapVo);
		
		mode.addAttribute("group_id", costMedicalTechnologyWork.getGroup_id());
		
		mode.addAttribute("hos_id", costMedicalTechnologyWork.getHos_id());
		
		mode.addAttribute("copy_code", costMedicalTechnologyWork.getCopy_code());
		
		mode.addAttribute("acc_year", costMedicalTechnologyWork.getAcc_year());
		
		mode.addAttribute("acc_month", costMedicalTechnologyWork.getAcc_month());
		
		mode.addAttribute("dept_id", costMedicalTechnologyWork.getDept_id());
		
		mode.addAttribute("dept_no", costMedicalTechnologyWork.getDept_no());
		
		mode.addAttribute("dept_code", costMedicalTechnologyWork.getDept_code());
		
		mode.addAttribute("dept_name", costMedicalTechnologyWork.getDept_name());
		
		mode.addAttribute("medical_num", costMedicalTechnologyWork.getMedical_num());
		
		return "hrp/cost/costMedicalTechnologywork/costMedicalTechnologyWorkUpdate";
	}
	/**
	*住院工作量表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costMedicalTechnologywork/updateCostMedicalTechnologyWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostMedicalTechnologyWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId()); 

        mapVo.put("copy_code", SessionManager.getCopyCode()); 
		
		String costMedicalTechnologyWorkJson = "";
		
		try {
			costMedicalTechnologyWorkJson = costMedicalTechnologyWorkService.updateCostMedicalTechnologyWork(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			costMedicalTechnologyWorkJson = e.getMessage();
		}
		
		return JSONObject.parseObject(costMedicalTechnologyWorkJson);
	}

	/**
	*<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costMedicalTechnologywork/costMedicalTechnologyWorkImportPage",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> costMedicalTechnologyWorkImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String costMedicalTechnologyWorkJson = "";
		 
		try {
			
			costMedicalTechnologyWorkJson=costMedicalTechnologyWorkService.ImpCostMedicalTechnologyWork(mapVo);
			
		} catch (Exception e) {
			
			costMedicalTechnologyWorkJson = e.getMessage();
		}
		return JSONObject.parseObject(costMedicalTechnologyWorkJson);
    }
}

