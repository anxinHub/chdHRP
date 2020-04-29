package com.chd.hrp.hr.controller.transfer;

import java.util.ArrayList;
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
import com.chd.hrp.hr.entity.transfer.HrStationTransfer;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.transfer.HrStationTransferService;

/**
 * 调岗管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/transfer/stationtransfer")
@SuppressWarnings("unused")
public class HrStationTransferController extends BaseController {

	private static Logger logger = Logger.getLogger(HrStationTransferController.class);

	// 引入Service服务
	@Resource(name = "hrStationTransferService")
	private final HrStationTransferService hrStationTransferService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrStationTransferMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/transfer/stationtransfer/stationTransferMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrStationTransferPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/transfer/stationtransfer/stationTransferAdd";

	}
	
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrStationTransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrStationTransfer(@RequestParam Map<String, Object> mapVo, Model mode)
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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		try {
			String hosEmpKindJson = hrStationTransferService.addHrStationTransfer(mapVo);

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
	@RequestMapping(value = "/updateHrStationTransferPage", method = RequestMethod.GET)
	public String updateHrStationTransferPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrStationTransfer hrStationTransfer = new HrStationTransfer();

		hrStationTransfer = hrStationTransferService.queryByCodeStationTransfer(mapVo);
		return "hrp/hr/transfer/stationtransfer/stationTransferUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrStationTransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrStationTransfer(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		try {
			String hosEmpKindJson = hrStationTransferService.updateHrStationTransfer(mapVo);

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
	@RequestMapping(value = "/deleteHrStationTransfer", method = RequestMethod.POST)
	@ResponseBody

	public String deleteHrStationTransfer(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrStationTransfer> listVo = JSONArray.parseArray(paramVo, HrStationTransfer.class);
		try {
			for (HrStationTransfer hrStationTransfer : listVo) {
				/*
				 * str = str + hrBaseService.isExistsDataByTable("HR_DUTY",
				 * hrDuty.getKind_code())== null ? "" :
				 * hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code());
				 */
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}

			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的调岗管理被以下业务使用：【" + str.substring(0, str.length() - 1)
						+ "】。\",\"state\":\"false\"}");
			}
			// hrStationTransferService.deleteHrStationTransfer(listVo);
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
	@RequestMapping(value = "/queryHrStationTransfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrStationTransfer(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrStationTransferService.queryHrStationTransfer(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditStationTransfer", method = RequestMethod.POST)
	@ResponseBody
	public String auditStationTransfer(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		try {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			String msg = hrStationTransferService.auditStationTransfer(listVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * @Description 销审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reAuditStationTransfer", method = RequestMethod.POST)
	@ResponseBody
	public String reAuditStationTransfer(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		try {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			String msg = hrStationTransferService.reAuditStationTransfer(listVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}
}
