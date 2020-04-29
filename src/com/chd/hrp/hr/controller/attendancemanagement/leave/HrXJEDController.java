package com.chd.hrp.hr.controller.attendancemanagement.leave;

import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.attendancemanagement.leave.HrXJEDService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 休假额度设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrXJEDController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrXJEDController.class);

	// 引入Service服务
	@Resource(name = "hrXJEDService")
	private final HrXJEDService hrXJEDService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrXJEDMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/leave/xjed/xJEDMainPage";

	}

	/**
	 * 添加页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addXJEDPage", method = RequestMethod.GET)
	public String stationAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		return "hrp/hr/attendancemanagement/leave/xjed/xJEDAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addXJED", method = RequestMethod.POST)
	@ResponseBody
	public String addXJED(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			String hosEmpKindJson = hrXJEDService.addXJED(mapVo);
			return hosEmpKindJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveXJED", method = RequestMethod.POST)
	@ResponseBody
	public String saveXJED(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String hosEmpKindJson = hrXJEDService.saveXJED(mapVo);
			return hosEmpKindJson;
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
	@RequestMapping(value = "/queryXJED", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryXJED(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("dept_source", MyConfig.getSysPara("06103"));
			String stationTransef = hrXJEDService.queryXJED(getPage(mapVo));
			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 考勤分类下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryAttendCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrXJEDService.queryAttendCode(mapVo);
		return json;

	}

	/**
	 * @Description 查询动态表头休假类型
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String stationTransef = hrXJEDService.queryAttendItem(mapVo);
		return JSONObject.parseObject(stationTransef);

	}
	
	/**
	 * @Description 查询动态表头休假类型个人控制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryItemGeRen", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryItemGeRen(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String stationTransef = hrXJEDService.queryItemGeRen(mapVo);
		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importXJED", method = RequestMethod.POST)
	@ResponseBody
	public String importXJED(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			String reJson = hrXJEDService.importXJED(mapVo);
			return reJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 初始化人员
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/importEmp", method = RequestMethod.POST)
	@ResponseBody
	public String importEmp(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			String reJson = hrXJEDService.importEmp(mapVo);
			return reJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 批量添加页面增加员工
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEmpByDeptId", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpByDeptId(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			String reJson = hrXJEDService.queryEmpByDeptId(mapVo);
			return reJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 修改全院控制
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateXjedByItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateXjedByItem(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {
			retMap = hrXJEDService.updateXjedByItem(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
}
