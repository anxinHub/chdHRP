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
import com.chd.hrp.hr.service.medicalmanagement.HrEmpTechRefService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_TECH_REF 技术准入相关联手术
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/technologicalmanagement")
public class HrEmpTechRefController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrEmpTechRefController.class);
	
	//引入Service服务
	@Resource(name = "hrEmpTechRefService")
	private final HrEmpTechRefService hrEmpTechRefService = null;
   
	/**
	 * @Description 添加数据技术准入相关联手术
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTechRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTechRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("state", 0);

		String hosEmpKindJson = hrEmpTechRefService.addTechRef(mapVo);

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
	@RequestMapping(value = "/updateHrEmpTechRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrEmpTechRef(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		try {
			String hrEmpTechRefJson = hrEmpTechRefService.updateHrEmpTechRef(mapVo);

			return JSONObject.parseObject(hrEmpTechRefJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	

	 /**
     * 删除技术准入相关联手术
     * @param paramVo
     * @param mode
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/deleteHrTechRef", method = RequestMethod.POST)
	@ResponseBody

	public String deleteHrTechRef(@RequestParam String paramVo, Model mode) throws Exception {String str = "";
	boolean falg = true;
	List<HrEmpTechExec> listVo = JSONArray.parseArray(paramVo, HrEmpTechExec.class);

try {
/*	for (HrEmpTechExec hrDrugPerm : listVo) {
		hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
		hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
	}*/
	
	
	  return hrEmpTechRefService.deleteHrTechRef(listVo);
	
			
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
	@RequestMapping(value = "/queryHrEmpTechRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrEmpTechRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrEmpTechRef = hrEmpTechRefService.queryHrEmpTechRef(getPage(mapVo));

		return JSONObject.parseObject(hrEmpTechRef);
		
	}
	
  
    
}

