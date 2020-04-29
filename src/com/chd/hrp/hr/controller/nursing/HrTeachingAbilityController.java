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
import com.chd.hrp.hr.entity.nursing.HrTeachingAbility;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrTeachingAbilityService;

/**
 * 教学能力
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrTeachingAbilityController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrTeachingAbilityController.class);

	// 引入Service服务
	@Resource(name = "hrTeachingAbilityService")
	private final HrTeachingAbilityService hrTeachingAbilityService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTeachingAbilityMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/teachingability/teachingAbilityMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTeachingAbilityPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/nursing/teachingability/teachingAbilityAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTeachingAbility", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTeachingAbility(@RequestParam Map<String, Object> paramVo, Model mode) throws Exception {
		
		String hrJson;
		try {
			hrJson = hrTeachingAbilityService.addTeachingAbility(paramVo);
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
	@RequestMapping(value = "/deleteTeachingAbility", method = RequestMethod.POST)
	@ResponseBody
	public String deleteTeachingAbility(@RequestParam String paramVo, Model mode) throws Exception {
		boolean falg = true;
		List<HrTeachingAbility> listVo = JSONArray.parseArray(paramVo, HrTeachingAbility.class);
		// 重新组装List,用于组装正确的删除数据,避免误删除操作
		List<HrTeachingAbility> entityList = new ArrayList<HrTeachingAbility>();
		try {
			for (HrTeachingAbility hrTeachingAbility : listVo) {
				hrTeachingAbility.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrTeachingAbility.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				if (hrTeachingAbility.getState() != null && hrTeachingAbility.getState() != 0) {
					falg = false;
					continue;
				}
				// 判断主键是否为空,避免误删数据
				if (hrTeachingAbility.getTeach_date() == null || hrTeachingAbility.getEmp_id() == null) {
					continue;
				}
				if ("".equals(hrTeachingAbility.getTeach_date())
						|| "".equals(String.valueOf(hrTeachingAbility.getEmp_id()))) {
					continue;
				}
				entityList.add(hrTeachingAbility);
			}

			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}

			if (entityList.size() == 0) {
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}

			return hrTeachingAbilityService.deleteTeachingAbility(entityList);
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
	@RequestMapping(value = "/queryTeachingAbility", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTeachingAbility(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("dept_code") !=null && StringUtils.isNotBlank(mapVo.get("dept_code").toString())) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_no", dept_id_no.split("@")[0]);
		}
		if (mapVo.get("teach_begin_date") !=null && StringUtils.isNotBlank(mapVo.get("teach_begin_date").toString())) {
			String teach_begin_dates=mapVo.get("teach_begin_date").toString().replace("-", "");
			mapVo.put("teach_begin_date", teach_begin_dates);
		}
		if(mapVo.get("teach_end_date") !=null && StringUtils.isNotBlank(mapVo.get("teach_end_date").toString())){
			String teach_end_dates=mapVo.get("teach_end_date").toString().replace("-", "");
			mapVo.put("teach_end_date", teach_end_dates);
		}
		String stationTransef = hrTeachingAbilityService.queryTeachingAbility(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询教学中类下拉框
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTeachType", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryTeachType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json =hrTeachingAbilityService.queryTeachType(mapVo);
		return json;
	}
	/**
	 * 查询教学种类下拉表格
	 */
	@RequestMapping(value = "/queryHrTeachType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrTeachType(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String stationTransef = hrTeachingAbilityService.queryHrTeachType(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/*
	* 导入数据
	*/
	@RequestMapping(value = "/importTeach", method = RequestMethod.POST)
	@ResponseBody
	public String importTeach(@RequestParam Map<String, Object> mapVo, Model mode,
	HttpServletRequest request) throws Exception {
	String reJson = hrTeachingAbilityService.importTeach(mapVo);
	return reJson;
	}
}
