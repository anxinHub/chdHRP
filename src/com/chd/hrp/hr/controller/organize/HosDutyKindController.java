package com.chd.hrp.hr.controller.organize;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.orangnize.HosDutyKind;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HosDutyKindService;

/**
 * 岗位分类
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/duty")
public class HosDutyKindController  extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosDutyKindController.class);

	// 引入Service服务
	@Resource(name = "hosDutyKindService")
	private final HosDutyKindService hosDutyKindService = null;
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/dutyKindMainPage", method = RequestMethod.GET)
	public String stationKindMainPage(Model mode) throws Exception {
		return "hrp/hr/organize/dutykind/dutyKindMainPage";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/dutyKindAddPage", method = RequestMethod.GET)
	public String dutyKindAddPage(Model mode) throws Exception {
		return "hrp/hr/organize/dutykind/dutyKindAdd";
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrDutyKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrStationKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));
		
		String hosEmpKindJson = hosDutyKindService.add(mapVo);

		return JSONObject.parseObject(hosEmpKindJson);

	}

	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDutyKindPage", method = RequestMethod.GET)
	public String hosEmpKindUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HosDutyKind hrStationKind = new HosDutyKind();

		hrStationKind = hosDutyKindService.queryByCode(mapVo);

		if ( hrStationKind!=null) {
			
			mode.addAttribute("group_id", hrStationKind.getGroup_id());
			mode.addAttribute("hos_id", hrStationKind.getHos_id());
			mode.addAttribute("kind_code", hrStationKind.getKind_code());
			mode.addAttribute("kind_name", hrStationKind.getKind_name());
			mode.addAttribute("is_stop", hrStationKind.getIs_stop());
			mode.addAttribute("note", hrStationKind.getNote());
		}
		return "hrp/hr/organize/dutykind/dutyKindUpdate";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrDutyKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrStationKind(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));

			String hosEmpKindJson = hosDutyKindService.updateHrStationKind(mapVo);

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
	@RequestMapping(value = "/deleteHrDutyKind", method = RequestMethod.POST)
	@ResponseBody

	public String deleteHrStationKind(@RequestParam String paramVo, Model mode)
			throws Exception {
		String str = "";
		List<HosDutyKind> listVo = JSONArray.parseArray(paramVo, HosDutyKind.class);
		try {
	/*		for (HosDutyKind hrStationKind : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HOS_DUTY_KIND", hrStationKind.getKind_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HOS_DUTY_KIND", hrStationKind.getKind_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}*/
			List<HosDutyKind> falg=hosDutyKindService.queryListDutyKind(listVo);
			if (falg.size()>0) {
				return ("{\"error\":\"删除失败，选择的职务等级被以下业务使用：【职务信息】。\",\"state\":\"false\"}");
			}
			return  hosDutyKindService.deleteBatchStationKind(listVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrDutyKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrStationKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hosEmpKind = hosDutyKindService.queryHrStationKind(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}
//
	/**
	 * @Description 查询数据(左侧菜单) 岗位定义
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpKindTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHosEmpKindTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hosDutyKindService.queryHosEmpKindTree(mapVo);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateSK", method = RequestMethod.POST)
	@ResponseBody
	public String importDateSK(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hosDutyKindService.importDate(mapVo);
		return reJson;
	}
}
