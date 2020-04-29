package com.chd.hrp.hr.controller.organize;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.chd.hrp.hr.entity.orangnize.HosStation;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HosStationInfoService;



/**
 * 
 * @author Administrator
 *岗位设立
 */
@Controller
@RequestMapping(value = "/hrp/hr/organize/station")
public class HosStationInfoController  extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosStationInfoController.class);
	
	// 引入Service服务
	@Resource(name = "hosStationInfoService")
	private final HosStationInfoService hosStationInfoService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/stationInfoMainPage", method = RequestMethod.GET)
	public String stationInfoMainPage(Model mode) throws Exception {

		return "hrp/hr/organize/stationinfo/stationInfoMainPage";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/stationInfoAddPage", method = RequestMethod.GET)
	public String stationInfoAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		/*if(!"".equals(mapVo.get("dept_code").toString())){
			Map<String,Object> deptMap = hosStationInfoService.queryDeptInfo(mapVo);
			mode.addAttribute("dept_id", deptMap.get("dept_id"));
			mode.addAttribute("dept_no", deptMap.get("dept_no"));
		}else{*/
			mode.addAttribute("dept_id", "");
			mode.addAttribute("dept_no", "");
		//}
		
		return "hrp/hr/organize/stationinfo/stationInfoAdd";
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStationInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addStationInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("station_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("station_name").toString()));
		
		String hosEmpKindJson = hosStationInfoService.addStationInfo(mapVo);

		return JSONObject.parseObject(hosEmpKindJson);

	}

	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/stationInfoUpdatePage", method = RequestMethod.GET)
	public String stationInfoUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		HosStation hrStation = hosStationInfoService.queryByCode(mapVo);

		if (hrStation != null) {
			mode.addAttribute("group_id", hrStation.getGroup_id());
			mode.addAttribute("hos_id", hrStation.getHos_id());
			mode.addAttribute("station_code", hrStation.getStation_code());
			mode.addAttribute("station_name", hrStation.getStation_name());
			if( hrStation.getDept_id() !=0 ) {
				mode.addAttribute("dept_id", hrStation.getDept_no()+"@"+hrStation.getDept_id());
				
			}else {
				mode.addAttribute("dept_id", "none");
			}
			
			mode.addAttribute("dept_code", hrStation.getDept_code());
			mode.addAttribute("dept_name", hrStation.getDept_name());
			mode.addAttribute("duty_code", hrStation.getDuty_code());
			mode.addAttribute("duty_name", hrStation.getDuty_name());
			mode.addAttribute("station_level_code", hrStation.getStation_level_code());
			mode.addAttribute("station_level_name", hrStation.getStation_level_name());
			mode.addAttribute("is_stop", hrStation.getIs_stop());
			mode.addAttribute("note", hrStation.getNote());
		}

		return "hrp/hr/organize/stationinfo/stationInfoUpdate";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStationInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStationInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("station_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("station_name").toString()));
		try {
			String hosEmpKindJson = hosStationInfoService.updateStationInfo(mapVo);

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
	@RequestMapping(value = "/deleteStationInfo", method = RequestMethod.POST)
	@ResponseBody
	public String deleteStationInfo(@RequestParam String paramVo, Model mode) throws Exception {

		String str = "";
		boolean falg = true;
		List<HosStation> listVo = JSONArray.parseArray(paramVo, HosStation.class);
		try {
			for (HosStation hrStation : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HOS_STATION", hrStation.getStation_code()) == null ? "" : hrBaseService.isExistsDataByTable(
						"HOS_STATION", hrStation.getStation_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}

			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的岗位定义被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}");
			}
			return hosStationInfoService.deleteBatchStationInfo(listVo);

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
	@RequestMapping(value = "/queryStationInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStationInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String station = hosStationInfoService.queryStationInfo(getPage(mapVo));

		return JSONObject.parseObject(station);

	}

	/**
	 * @Description 查询数据(左侧菜单) 岗位定义
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStationInfoTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryStationDefTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hosStationInfoService.queryStationInfoTree(mapVo);

	}

	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDate", method = RequestMethod.POST)
	@ResponseBody
	public String importDate(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request) throws Exception {
		mapVo.put("user_id", SessionManager.getUserId());
		
		String reJson = hosStationInfoService.importDate(mapVo);
		return reJson;
	}
}