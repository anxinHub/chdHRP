/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
import com.chd.hrp.hr.service.medicalmanagement.HrEmpTechExecService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_TECH_EXEC 技术准入开展情况
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/technologicalmanagement")
public class HrEmpTechExecController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrEmpTechExecController.class);
	
	//引入Service服务
	@Resource(name = "hrEmpTechExecService")
	private final HrEmpTechExecService hrEmpTechExecService = null;
   
	/**
	 * @Description 添加数据技术准入开展情况
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTechExec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTechExec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String hosEmpKindJson = hrEmpTechExecService.addTechExec(mapVo);

		return JSONObject.parseObject(hosEmpKindJson);

	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/updateHrEmpTechExec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrEmpTechExec(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		try {
			String hrEmpTechExecJson = hrEmpTechExecService.updateHrEmpTechExec(mapVo);

			return JSONObject.parseObject(hrEmpTechExecJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	 /**
     * 删除技术准入开展情况
     * @param paramVo
     * @param mode
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/deleteHrTechExec", method = RequestMethod.POST)
	@ResponseBody

	public String deleteHrTechExec(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
	boolean falg = true;
	List<HrEmpTechExec> listVo = JSONArray.parseArray(paramVo, HrEmpTechExec.class);

try {
	/*for (HrEmpTechExec hrDrugPerm : listVo) {
		hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
		hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
	}*/
	
	
	  return hrEmpTechExecService.deleteHrTechExec(listVo);
	
			
} catch (Exception e) {
	return "{\"error\":\"" + e.getMessage() + "\"}";
}}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryHrEmpTechExec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrEmpTechExec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String hrEmpTechExec = hrEmpTechExecService.query(getPage(mapVo));

		return JSONObject.parseObject(hrEmpTechExec);
		
	}
	
 
  
    
}

