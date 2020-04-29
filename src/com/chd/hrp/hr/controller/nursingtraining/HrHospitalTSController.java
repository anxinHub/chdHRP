package com.chd.hrp.hr.controller.nursingtraining;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.nursingtraining.HrHospitalTS;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursingtraining.HrHospitalTSService;

/**
 * 院内学习记录
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursingtraining")
public class HrHospitalTSController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrHospitalTSController.class);

	// 引入Service服务
	@Resource(name = "hrHospitalTSService")
	private final HrHospitalTSService hrHospitalTSService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrHospitalTSMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursingtraining/hospitalts/hospitalTSMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHospitalTSPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/nursingtraining/hospitalts/hospitalTSAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHospitalTS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHospitalTS(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

			String hosEmpKindJson = hrHospitalTSService.addHospitalTS(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHospitalTSPage", method = RequestMethod.GET)
	public String updateHrDeptnursingtrainingPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrHospitalTS hrdeptnursingtraining = new HrHospitalTS();

		hrdeptnursingtraining = hrHospitalTSService.queryByCodeHospitalTS(mapVo);
		return "hrp/hr/nursingtraining/hospitalts/hospitalTSUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHospitalTS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHospitalTS(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

			String hosEmpKindJson = hrHospitalTSService.updateHospitalTS(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHospitalTS", method = RequestMethod.POST)
	@ResponseBody

	public String deleteHospitalTS(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrHospitalTS> listVo = JSONArray.parseArray(paramVo, HrHospitalTS.class);
		try {
			for (HrHospitalTS hrHospitalTS : listVo) {
				/*str = str + hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code());*/
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			
			
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的院内学习记录被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			 hrHospitalTSService.deleteHospitalTS(listVo);
			return null;

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
	@RequestMapping(value = "/queryHospitalTS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHospitalTS(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrHospitalTSService.queryHospitalTS(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询数据(左侧菜单) 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHospitalTSTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHospitalTSTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrHospitalTSService.queryHospitalTSTree(mapVo);

	}
}
