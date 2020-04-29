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
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendExamineService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 考勤审核
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/resultmanage")
public class HrAttendExamineController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAttendExamineController.class);

	// 引入Service服务 
	@Resource(name = "hrAttendExamineService")
	private final HrAttendExamineService hrAttendExamineService = null;

	
	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendExamineMainPage", method = RequestMethod.GET)
	public String hrAttendExaminePage(Model mode) throws Exception {
		
		return "hrp/hr/attendancemanagement/resultmanage/hrAttendResultManageExamineMain";
	}
	
	
	
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendExamine(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String stationTransef;
		try {
			
			stationTransef = hrAttendExamineService.queryAttendExamine(getPage(mapVo));
		} catch (Exception e) {
			
			stationTransef = e.getMessage();
		}

		return JSONObject.parseObject(stationTransef); 

	}

	
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/submitAttendExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitAttendExamine(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendExamineService.submitAttendExamine(getPage(mapVo));
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap; 

	}

	/**
	 * @Description 取消审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/unSubmitAttendExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unSubmitAttendExamine(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendExamineService.unSubmitAttendExamine(getPage(mapVo));
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap; 

	}
	
	/**
	 * @Description 上报
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkAttendExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAttendExamine(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
  Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendExamineService.checkOrUnCheckResultExamine(getPage(mapVo));
		}  catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;

	}

	/**
	 * @Description 取消上报
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/unCheckAttendExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unCheckAttendExamine(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
  Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendExamineService.unCheckAttendExamine(getPage(mapVo));
		}  catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;

	}
	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmAttendExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmAttendExamine(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
  Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendExamineService.confirmAttendExamine(getPage(mapVo));
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap; 

	}
	/**
	 * @Description 取消确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/unConfirmAttendExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unConfirmAttendExamine(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
  Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendExamineService.confirmAttendExamine(getPage(mapVo));
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap; 

	}
	/**
	 * @Description 汇总
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/summaryAttendExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> summaryAttendExamine(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
  Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendExamineService.summaryAttendExamine(getPage(mapVo));
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
	@RequestMapping(value = "/deleteBatchAttendResultExamineManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteBatchAttendResultManage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		
		try {
			retMap = hrAttendExamineService.deleteBatchAttendResultExamineManage(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
}
