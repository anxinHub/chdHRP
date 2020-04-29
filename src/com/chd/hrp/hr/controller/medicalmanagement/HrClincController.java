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
import com.chd.hrp.hr.entity.medicalmanagement.HrClinc;
import com.chd.hrp.hr.service.medicalmanagement.HrClincService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_Clinc 门诊能力
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/healthadministration/clinc")
public class HrClincController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrClincController.class);
	
	//引入Service服务
	@Resource(name = "hrClincService")
	private final HrClincService hrClincService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrClincMainPage", method = RequestMethod.GET)
	public String hrClincMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/clinc/clincMainPage";

	}


	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addClinc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrClinc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		try {

			String hrClincJson = hrClincService.addClinc(mapVo);

			return JSONObject.parseObject(hrClincJson);
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
	@RequestMapping(value = "/deleteClinc", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrClinc(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
			List<HrClinc> listVo = JSONArray.parseArray(paramVo, HrClinc.class);
		
		try {
			/*for (HrClinc hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Integer.parseInt(SessionManager.getGroupId().toString()));
				hrDrugPerm.setHos_id(Integer.parseInt(SessionManager.getHosId().toString()));
			}*/
			
			
			  return hrClincService.deleteClinc(listVo);
			
					
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
	@RequestMapping(value = "/queryClinc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrClinc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrClinc = hrClincService.queryClinc(getPage(mapVo));

		return JSONObject.parseObject(hrClinc);
		
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importClinc", method = RequestMethod.POST)
	@ResponseBody
	public String importClinc(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrClincService.importClinc(mapVo);
		return reJson;
	}
 
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmClinc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmClinc(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrClinc> listVo = JSONArray.parseArray(paramVo, HrClinc.class);
			for (HrClinc hrClinc : listVo) {
				hrClinc.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrClinc.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				HrClinc Clinc = hrClincService.queryByCode(hrClinc);
				if (Clinc != null) {
					if (hrClinc.getIs_commit() == 0) {
						hrClinc.setIs_commit(1);
						msg = hrClincService.confirmClinc(hrClinc);
					} else {
						msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
					}
				}
			}
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmClinc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmClinc(@RequestParam String paramVo, Model mode)
			throws Exception {

		String msg = "";
		List<HrClinc> listVo = JSONArray.parseArray(paramVo, HrClinc.class);
		try {
			for (HrClinc hrClinc : listVo) {
				if (hrClinc.getIs_commit() != 0) {
					hrClinc.setIs_commit(0);
					hrClinc.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrClinc.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrClincService.reConfirmClinc(hrClinc);
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
}

