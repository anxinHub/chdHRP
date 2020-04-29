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
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.entity.transfer.HrDeptTransfer;
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultManageService;

/**
 * 考勤结果上报
 * @table HR_ATTEND_RESULT_MANAGE
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/resultmanage")
public class HrAttendResultManageController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HrAttendResultManageController.class);
	
	@Resource(name = "hrAttendResultManageService")
	private final HrAttendResultManageService hrAttendResultManageService = null;
	
	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendResultManageMainPage", method = RequestMethod.GET)
	public String hrAttendResultManageMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("dept_id",mapVo.get("dept_id"));
		return "hrp/hr/attendancemanagement/resultmanage/hrAttendResultManageMain";

	}
	
	/**
	 * 休假页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendResultManageXjPage", method = RequestMethod.GET)
	public String hrAttendResultManageXjPage(Model mode) throws Exception {
		
		return "hrp/hr/attendancemanagement/resultmanage/hrAttendResultManageXj";
	}
	
	/**
	 * 加班页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendResultManageJbPage", method = RequestMethod.GET)
	public String hrAttendResultManageJbPage(Model mode) throws Exception {
		
		return "hrp/hr/attendancemanagement/resultmanage/hrAttendResultManageJb";
	}
	
	
	/**
	 * 查询表头
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendResultManageHead", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAttendResultManageHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultManageService.queryResultManageHead(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
	/**
	 * 主页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendResultManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAttendResultManage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String resultJson = null;
		
		try {
			
			resultJson = hrAttendResultManageService.queryResultManage(getPage(mapVo));
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			resultJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(resultJson);
	}
	
	/**
	 * 休假记录查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendResultManageXj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAttendResultManageXj(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String resultJson = null;
		
		try {
			
			resultJson = hrAttendResultManageService.queryResultManageXj(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			resultJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(resultJson);
	}
	
	/**
	 * 加班记录查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendResultManageJb", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAttendResultManageJb(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String resultJson = null;
		
		try {
			
			resultJson = hrAttendResultManageService.queryResultManageJb(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			resultJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(resultJson);
	}
	
	
	/**
	 * 生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addBatchAttendResultManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addBatchAttendResultManage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultManageService.addBatchAttendResult(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
	
	/**
	 * 删除
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteBatchAttendResultManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteBatchAttendResultManage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultManageService.deleteResultManage(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
	
	/**
	 * 上报
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/submitAttendResultManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> submitAttendResultManage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultManageService.submitOrUnSubmitResultManage(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
	
	/**
	 * 取消上报
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unSubmitAttendResultManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> unSubmitAttendResultManage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultManageService.submitOrUnSubmitResultManage(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
	
	/**
	 * 审核
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditAttendResultManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> auditAttendResultManage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultManageService.checkOrUnCheckResultManage(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
	
	/**
	 * 消审
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unAuditAttendResultManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> unAuditAttendResultManage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultManageService.checkOrUnCheckResultManage(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
	
	/**
	 * 导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importAttendResultManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> importAttendResultManage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendResultManageService.importResultManage(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
}
