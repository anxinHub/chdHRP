package com.chd.hrp.hr.controller.nursing;

import java.text.SimpleDateFormat;
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
import com.chd.hrp.hr.entity.nursing.HrEducationStudent;
import com.chd.hrp.hr.entity.nursing.HrInserviceEducation;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrInserviceEducationService;

/**
 * 年度在职教育培训
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrInserviceEducationController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrInserviceEducationController.class);

	// 引入Service服务
	@Resource(name = "hrInserviceEducationService")
	private final HrInserviceEducationService hrInserviceEducationService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrInserviceEducationMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/inserviceeducation/inserviceEducationMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInserviceEducationPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/nursing/inserviceeducation/inserviceEducationAdd";

	}
	/**
	 * 人员添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInserviceEducationPersonnelPage", method = RequestMethod.GET)
	public String addInserviceEducationPersonnelPage(Model mode) throws Exception {

		return "hrp/hr/nursing/inserviceeducation/inserviceEducationPersonnelPage";

	}
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addInserviceEducation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addInserviceEducation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
        
        String hrJson;
		try {
			hrJson = hrInserviceEducationService.addInserviceEducation(mapVo);
		} catch (Exception e) {
			hrJson = e.getMessage();
		}
		return JSONObject.parseObject(hrJson);

	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInserviceEducationPage", method = RequestMethod.GET)
	public String updateHrDeptnursingPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrInserviceEducation hrdeptnursing = new HrInserviceEducation();

		hrdeptnursing = hrInserviceEducationService.queryByCodeInserviceEducation(mapVo);
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("edu_date", a.format(hrdeptnursing.getEdu_date()));
		mode.addAttribute("teacher", hrdeptnursing.getTeacher());
		Map<String, Object> emp_map=hrInserviceEducationService.queryEmp(mapVo);
		mode.addAttribute("emp_id", emp_map.get("EMP_ID"));
		mode.addAttribute("emp_name", emp_map.get("EMP_NAME"));
		mode.addAttribute("classs_name", hrdeptnursing.getClasss_name());
		mode.addAttribute("hours", hrdeptnursing.getHours());
		mode.addAttribute("state", hrdeptnursing.getState());
		mode.addAttribute("place", hrdeptnursing.getPlace());
		
		return "hrp/hr/nursing/inserviceeducation/inserviceEducationUpdate";

	}

	/**
	 * @Description 查询学员
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEducationStudent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEducationStudent(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrInserviceEducationService.queryEducationStudent(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInserviceEducation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateInserviceEducation(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		String hrJson;
		try {
			hrJson = hrInserviceEducationService.updateInserviceEducation(mapVo);
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
	@RequestMapping(value = "/deleteInserviceEducation", method = RequestMethod.POST)
	@ResponseBody

	public String deleteInserviceEducation(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrInserviceEducation> listVo = JSONArray.parseArray(paramVo, HrInserviceEducation.class);
		try {
			for (HrInserviceEducation hrInserviceEducation : listVo) {
				/*str = str + hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code());*/
				if (hrInserviceEducation.getState()!=0) {
					falg = false;
					continue;
				}
			
			
			}
			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}
			
			return hrInserviceEducationService.deleteInserviceEducation(listVo);

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEducationStudent", method = RequestMethod.POST)
	@ResponseBody

	public String deleteEducationStudent(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrEducationStudent> listVo = JSONArray.parseArray(paramVo, HrEducationStudent.class);
		try {
			
			
			return hrInserviceEducationService.deleteEducationStudentBatc(listVo);

		} catch (Exception e) {
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
	@RequestMapping(value = "/queryInserviceEducation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInserviceEducation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String stationTransef = hrInserviceEducationService.queryInserviceEducation(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmInserviceEducation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmInserviceEducation(@RequestParam String paramVo, Model mode) throws Exception {
		String msg = "";
		try {
			List<HrInserviceEducation> listVo = JSONArray.parseArray(paramVo, HrInserviceEducation.class);
			if (listVo.size() > 0) {
				msg = hrInserviceEducationService.confirmBatchInserviceEducation(listVo);
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
	@RequestMapping(value = "/reConfirmInserviceEducation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmInserviceEducation(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String msg = "";
			List<HrInserviceEducation> listVo = JSONArray.parseArray(paramVo, HrInserviceEducation.class);
			if (listVo.size() > 0) {
				msg = hrInserviceEducationService.reConfirmBatchInserviceEducation(listVo);
			}
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
