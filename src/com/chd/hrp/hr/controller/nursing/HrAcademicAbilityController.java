package com.chd.hrp.hr.controller.nursing;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrAcademicAbilityService;

/**
 * 学术能力
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrAcademicAbilityController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAcademicAbilityController.class);

	// 引入Service服务
	@Resource(name = "hrAcademicAbilityService")
	private final HrAcademicAbilityService hrAcademicAbilityService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAcademicAbilityMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/academicability/academicAbilityMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAcademicAbilityPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/nursing/academicability/academicAbilityAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAcademicAbility", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAcademicAbility(@RequestParam Map<String, Object> paramVo, Model mode) throws Exception {
		
		String hrJson;
		try {
			hrJson = hrAcademicAbilityService.addAcademicAbility(paramVo);
		} catch (Exception e) {
			hrJson = e.getMessage();
		}
		return JSONObject.parseObject(hrJson);

	}

	
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAcademicAbility", method = RequestMethod.POST)
	@ResponseBody

	public String deleteAcademicAbility(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrAcademicAbility> listVo = JSONArray.parseArray(paramVo, HrAcademicAbility.class);
		try {
			boolean falg = true;
			List<HrAcademicAbility> entityList = new ArrayList<HrAcademicAbility>();
			
			for (HrAcademicAbility hrAcademicAbility : listVo) {
				
				if (hrAcademicAbility.getState() != null && hrAcademicAbility.getState()!=0) {
					falg = false;
					continue;
				}
				
				if(hrAcademicAbility.getYear() == null || "".equals(hrAcademicAbility.getYear())){
					continue;
				}
				
				if(hrAcademicAbility.getEmp_id() == null || "".equals(hrAcademicAbility.getEmp_id())){
					continue;
				}
				
				entityList.add(hrAcademicAbility);
			}
			
			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}
			
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			return hrAcademicAbilityService.deleteAcademicAbility(entityList);

		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAcademicAbility", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAcademicAbility(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id",Integer.parseInt(SessionManager.getGroupId()) );
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", Integer.parseInt(SessionManager.getHosId()));
		}

		if (mapVo.get("dept_code") !=null && StringUtils.isNotBlank(mapVo.get("dept_code").toString())) {
			String dept_id_no = mapVo.get("dept_code").toString();
			mapVo.put("dept_no", dept_id_no.split("@")[0]);
		}
		
		String stationTransef = hrAcademicAbilityService.queryAcademicAbility(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmAcademicAbility", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmAcademicAbility(@RequestParam String paramVo, Model mode) throws Exception {
		String msg = "";
		try {
			List<HrAcademicAbility> listVo = JSONArray.parseArray(paramVo, HrAcademicAbility.class);

			for (HrAcademicAbility hrAcademicAbility : listVo) {
				if (hrAcademicAbility.getState() != 1) {
					hrAcademicAbility.setState(1);
					hrAcademicAbility.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAcademicAbility.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				} else {
					msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
					return JSONObject.parseObject(msg);
				}
			}
			msg = hrAcademicAbilityService.confirmAcademicAbilityBatch(listVo);
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
	@RequestMapping(value = "/reConfirmAcademicAbility", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmAcademicAbility(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String msg = "";
			List<HrAcademicAbility> listVo = JSONArray.parseArray(paramVo, HrAcademicAbility.class);

			for (HrAcademicAbility hrAcademicAbility : listVo) {
				if (hrAcademicAbility.getState() != 0) {
					hrAcademicAbility.setState(0);
					hrAcademicAbility.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAcademicAbility.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
					return JSONObject.parseObject(msg);
				}
			}
			msg = hrAcademicAbilityService.reConfirmAcademicAbilityBatch(listVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
/*
* 导入数据
*/
@RequestMapping(value = "/importAcademic", method = RequestMethod.POST)
@ResponseBody
public String importAcademic(@RequestParam Map<String, Object> mapVo, Model mode,
HttpServletRequest request) throws Exception {
String reJson = hrAcademicAbilityService.importAcademic(mapVo);
return reJson;
}

}
