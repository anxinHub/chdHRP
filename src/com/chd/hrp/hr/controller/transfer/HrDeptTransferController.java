package com.chd.hrp.hr.controller.transfer;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.entity.nursing.HrNursingPromotion;
import com.chd.hrp.hr.entity.transfer.HrDeptTransfer;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.transfer.HrDeptTransferService;
/**
 * 部门调动
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/transfer/depttransfer")
public class HrDeptTransferController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrDeptTransferController.class);

	// 引入Service服务
	@Resource(name = "hrDeptTransferService")
	private final HrDeptTransferService hrDeptTransferService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrDeptTransferMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/transfer/depttransfer/deptTransferMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrDeptTransferPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/transfer/depttransfer/deptTransferAdd";

	}
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrDeptTransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrdepttransfer(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		try {
			String dept=mapVo.get("aft_dept_id").toString();
			mapVo.put("aft_dept_no", dept.split("@")[0]);
			mapVo.put("aft_dept_id", dept.split("@")[1]);
			String hosEmpKindJson = hrDeptTransferService.addHrDeptTransfer(mapVo);

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
	@RequestMapping(value = "/updateHrDeptTransferPage", method = RequestMethod.GET)
	public String updateHrDeptTransferPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrDeptTransfer hrdepttransfer = new HrDeptTransfer();

		hrdepttransfer = hrDeptTransferService.queryByCodeDeptTransfer(mapVo);
		mode.addAttribute("emp_name", hrdepttransfer.getEmp_name());
		mode.addAttribute("emp_id", hrdepttransfer.getEmp_id());
		mode.addAttribute("bef_dept_name", hrdepttransfer.getBef_dept_name());
		mode.addAttribute("adjust_code", hrdepttransfer.getAdjust_code());
		mode.addAttribute("adjust_reason", hrdepttransfer.getAdjust_reason());
		mode.addAttribute("aft_dept_name", hrdepttransfer.getAft_dept_name());
		mode.addAttribute("adjust_date", DateUtil.dateToString(hrdepttransfer.getAdjust_date(), "YYYY-MM-dd"));
		mode.addAttribute("photo", hrdepttransfer.getPhoto());
		mode.addAttribute("kind_name", hrdepttransfer.getKind_name());
		mode.addAttribute("aft_dept_id", hrdepttransfer.getAft_dept_no()+"@"+hrdepttransfer.getAft_dept_id());
		mode.addAttribute("adjust_state", hrdepttransfer.getAdjust_state());
		return "hrp/hr/transfer/depttransfer/deptTransferUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrDeptTransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrDeptTransfer(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		try {
			String dept=mapVo.get("aft_dept_id").toString();
			mapVo.put("aft_dept_no", dept.split("@")[0]);
			mapVo.put("aft_dept_id", dept.split("@")[1]);
			String hosEmpKindJson = hrDeptTransferService.updateHrDeptTransfer(mapVo);

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
	@RequestMapping(value = "/deleteHrDeptTransfer", method = RequestMethod.POST)
	@ResponseBody

	public String deleteHrdepttransfer(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			 return hrDeptTransferService.deleteHrDeptTransfer(mapVo);
			
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
	@RequestMapping(value = "/queryHrDeptTransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrdepttransfer(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String stationTransef = hrDeptTransferService.queryHrDeptTransfer(getPage(mapVo));
	
		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditdepttransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditdepttransfer(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			List<HrDeptTransfer> listVo = JSONArray.parseArray(paramVo, HrDeptTransfer.class);
			String msg = hrDeptTransferService.auditDeptTransfer(listVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	/**
	 * @Description 销审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reAuditdepttransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditdepttransfer(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			List<HrDeptTransfer> listVo = JSONArray.parseArray(paramVo, HrDeptTransfer.class);
			String msg = hrDeptTransferService.reAuditdepttransfer(listVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 查询人员详细信息
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosEmpDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String hrdeptnursing = hrDeptTransferService.queryHosEmpDept(mapVo);

		return hrdeptnursing;

	}

}
