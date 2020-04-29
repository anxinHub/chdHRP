package com.chd.hrp.hr.controller.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrOfficialHoliday;
import com.chd.hrp.hr.service.attendancemanagement.attend.HrOfficialHolidayService;

/**
 * 法定节假日设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrOfficialHolidayController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrOfficialHolidayController.class);

	// 引入Service服务
	@Resource(name = "hrOfficialHolidayService")
	private final HrOfficialHolidayService hrOfficialHolidayService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrOfficialHolidayMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/attend/officialholiday/officialHolidayMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOfficialHolidayPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/attendancemanagement/attend/officialholiday/officialHolidayAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOfficialHoliday", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOfficialHoliday(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String hosEmpKindJson = hrOfficialHolidayService.addOfficialHoliday(mapVo);

		return JSONObject.parseObject(hosEmpKindJson);

	}



	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteOfficialHoliday", method = RequestMethod.POST)
	@ResponseBody

	public String deleteOfficialHoliday(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		 List<HrOfficialHoliday> listVo = JSONArray.parseArray(paramVo, HrOfficialHoliday.class);
		try {
			for (HrOfficialHoliday hrOfficialHoliday : listVo) {
				/*str = str + hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code());*/
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的法定节假日被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			return hrOfficialHolidayService.deleteOfficialHoliday(listVo);

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
	@RequestMapping(value = "/queryOfficialHoliday", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOfficialHoliday(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
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
			String stationTransef = hrOfficialHolidayService.queryOfficialHoliday(getPage(mapVo));

			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}


}
