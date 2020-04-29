package com.chd.hrp.hr.controller.attendancemanagement.attendresult;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 考勤数据维护
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attendresult")
public class HrAttendResultController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAttendResultController.class);

	// 引入Service服务 
	@Resource(name = "hrAttendResultService")
	private final HrAttendResultService hrAttendResultService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendResultMainPage", method = RequestMethod.GET)
	public String hrAttendResultMainPage(Model mode) throws Exception {
		
		return "hrp/hr/attendancemanagement/attendresult/hrAttendResultMain";
	}
	
	/**
	 * 职工添加页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEmpPage", method = RequestMethod.GET)
	public String addEmpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("year_month", mapVo.get("year_month"));
		
		return "hrp/hr/attendancemanagement/attendresult/hrAttendResultAddEmp";
	}
	
	/**
	 * 职工添加页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendResultDetailPage", method = RequestMethod.GET)
	public String hrAttendResultDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/hr/attendancemanagement/attendresult/hrAttendResultDetail";
	}
	/**
	 * 快捷设置页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendResultShortCutPage", method = RequestMethod.GET)
	public String hrAttendResultShortCutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/hr/attendancemanagement/attendresult/hrAttendResultShortCutPage";
	}
	
	/**
	 * 批量修改页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendResultShortPLPage", method = RequestMethod.GET)
	public String hrAttendResultShortPLPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("data", mapVo.get("detailData"));
		return "hrp/hr/attendancemanagement/attendresult/hrAttendResultShortPLPage";
	}
	/**
	 * 查询考勤数据表头
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryAttendResultHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendResultHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultService.queryAttendResultHead(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendResult(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String stationTransef;
		try {
			
			stationTransef = hrAttendResultService.queryAttendResult(getPage(mapVo));
		} catch (Exception e) {
			
			stationTransef = e.getMessage();
		}

		return JSONObject.parseObject(stationTransef); 

	}

	/**
	 * @Description 查询明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendResultDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendResultDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String stationTransef;
		try {
			
			stationTransef = hrAttendResultService.queryAttendResultDetail(mapVo);
		} catch (Exception e) {
			
			stationTransef = e.getMessage();
		}

		return JSONObject.parseObject(stationTransef); 

	}
	
	/**
	 * 生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBatchAttendResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAttendResult(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultService.addBatchAttendResult(mapVo);
			retMap =hrAttendResultService.insertBatchDetailResult(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	
	/**
	 * 添加职工
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendResultEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAttendResultEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultService.addAttendResultEmp(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAttendResult(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendResultService.addAttendResult(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	
	/**
	 * @Description 保存明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAttendResultDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAttendResultDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendResultService.saveAttendResultDetail(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	
	/**
	 * @Description 保存快捷设置明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAttendResultShortCut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAttendResultShortCut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendResultService.saveAttendResultShortCut(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	/**
	 * @Description 保存批量设置明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveAttendResultShortPL", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAttendResultShortPL(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendResultService.saveAttendResultShortPL(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	/**
	 * 删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAttendResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAttendResult(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			
			retMap = hrAttendResultService.deleteAttendResult(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
	/**
	 * @Description 导入数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/importAttendResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAttendResult(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultService.importAttendResult(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
		
	}

	/**
	 * 导入排班
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPbForResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importPbForResult(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			
			retMap = hrAttendResultService.importPb(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}

	/**
	 * 导入加班
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importJbForResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importJbForResult(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			
			retMap = hrAttendResultService.importJb(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}

	/**
	 * 导入休假
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importXjForResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importXjForResult(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			
			retMap = hrAttendResultService.importXj(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	//
	/**
	 * 查询人员按部门下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEmpSelectResult", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpSelectResult(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if (mapVo.get("user_id") == null) {

			mapVo.put("user_id", SessionManager.getUserId());

		}

		String json = hrAttendResultService.queryEmpSelectResult(mapVo);
		return json;

	}
	
	/**
	 * 查询本部门人员
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendEmp(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String stationTransef;
		try {
			
			stationTransef = hrAttendResultService.queryAttendEmp(getPage(mapVo));
		} catch (Exception e) {
			
			stationTransef = e.getMessage();
		}

		return JSONObject.parseObject(stationTransef); 

	}
	
}
