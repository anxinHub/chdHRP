/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.entity.medicalmanagement.HrAdverseEvent;
import com.chd.hrp.hr.service.medicalmanagement.HrAdverseEventService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_AdverseEvent 不良事件登记表
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/medicalsafety")
public class HrAdverseEventController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrAdverseEventController.class);
	
	//引入Service服务
	@Resource(name = "hrAdverseEventService")
	private final HrAdverseEventService hrAdverseEventService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrAdverseEventMainPage", method = RequestMethod.GET)
	public String hrAdverseEventMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/adverseevent/adverseEventMainPage";

	}


	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addAdverseEvent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrAdverseEvent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}

			String hrAdverseEventJson = hrAdverseEventService.addAdverseEvent(mapVo);

			return JSONObject.parseObject(hrAdverseEventJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteAdverseEvent", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrAdverseEvent(@RequestParam String paramVo, Model mode) throws Exception {
		
		List<HrAdverseEvent> listVo = JSONArray.parseArray(paramVo, HrAdverseEvent.class);
		List<HrAdverseEvent> entityList = new ArrayList<HrAdverseEvent>();//重新组装List,用于组装正确的删除数据,避免误删除操作
		
		try {
			for (HrAdverseEvent hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
				
				//判断主键是否为空,避免误删数据
				if(hrDrugPerm.getOcc_date() == null || "".equals(hrDrugPerm.getOcc_date())){
					continue;
				}
				
				if(hrDrugPerm.getOcc_dept_id() == null || "".equals(hrDrugPerm.getOcc_dept_id())){
					continue;
				}
				
				if(hrDrugPerm.getEmp_id() == null || "".equals(hrDrugPerm.getEmp_id())){
					continue;
				}
				
				if(hrDrugPerm.getIn_hos_no() == null || "".equals(hrDrugPerm.getIn_hos_no())){
					continue;
				}
				
				entityList.add(hrDrugPerm);
			}
			
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			
			return hrAdverseEventService.deleteAdverseEvent(entityList);
			
					
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryAdverseEvent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrAdverseEvent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		if(mapVo.get("occ_date") != null && !"".equals(mapVo.get("occ_date"))){
			
			mapVo.put("occ_date", DateUtil.stringToDate(String.valueOf(mapVo.get("occ_date")), "yyyy-MM-dd"));
		}
		
		
		String hrAdverseEvent = hrAdverseEventService.queryAdverseEvent(getPage(mapVo));

		return JSONObject.parseObject(hrAdverseEvent);
		
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importAdverseEvent", method = RequestMethod.POST)
	@ResponseBody
	public String importAdverseEvent(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrAdverseEventService.importAdverseEvent(mapVo);
		return reJson;
	}
 
    
}

