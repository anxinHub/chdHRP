package com.chd.hrp.ass.controller.asstendcheck;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.service.asstendcheck.AssTendCheckService;

/**
 * @Description: 定标审核
 */

@Controller
public class AssTendCheckController extends BaseController {

	private static Logger logger = Logger.getLogger(AssTendCheckController.class);

	// 引入Service服务
	@Resource(name = "assTendCheckService")
	private final AssTendCheckService assTendCheckService = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/ass/asstendcheck/assTendCheckPage", method = RequestMethod.GET)
	public String assTendCheckPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05008",MyConfig.getSysPara("05008"));
		return "hrp/ass/asstendcheck/assTendCheck";

	}
	
	@RequestMapping(value = "/hrp/ass/asstendcheck/queryAssTrendCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTrendCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assDefinedQuery = null;
		try {
			assDefinedQuery = assTendCheckService.queryAssTendCheck(getPage(mapVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return JSONObject.parseObject(assDefinedQuery);
		
	}
	/**
	 * 保存
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstendcheck/saveAssTendCheckInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssTendCheckInfo(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assTendCheckJson = "";

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("\\|");
			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("bid_id", ids[0]);

			mapVo.put("ven_id", ids[1].split("@")[0]);
			
			mapVo.put("ven_no", ids[1].split("@")[1]);
			
			mapVo.put("bid_calibratedate", DateUtil.stringToDate(DateUtil.jsDateToString(ids[2], "yyyy-MM-dd"), "yyyy-MM-dd"));
			
			mapVo.put("bid_winflag", "1");
			

			listVo.add(mapVo);
			
		}

		try {
			assTendCheckJson = assTendCheckService.updateAssTendCheckInfo(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assTendCheckJson);

	}
	
	/**
	 * 删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstendcheck/deleteAssTrendCheckInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTrendCheckInfo(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assTendCheckJson = "";

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("bid_id", id);

			mapVo.put("ven_id", null);
			
			mapVo.put("ven_no", null);
			
			mapVo.put("bid_calibratedate", null);
			
			mapVo.put("bid_winflag", "0");

			listVo.add(mapVo);
			
		}

		try {
			assTendCheckJson = assTendCheckService.updateAssTendCheckInfo(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assTendCheckJson);

	}
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstendcheck/sendAssTrend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendAssTrend(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assTendCheckJson = "";

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("bid_id", ids[0]);

			mapVo.put("bid_state", ids[1]);
			
			mapVo.put("bid_checkdate", null);

			listVo.add(mapVo);
			
		}

		try {
			assTendCheckJson = assTendCheckService.updateAssTendCheckState(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assTendCheckJson);

	}
	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstendcheck/unSendAssTrend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unSendAssTrend(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assTendCheckJson = "";

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("bid_id", ids[0]);

			mapVo.put("bid_state", ids[1]);
			
			mapVo.put("bid_checkdate", null);

			listVo.add(mapVo);
			
		}

		try {
			assTendCheckJson = assTendCheckService.updateAssTendCheckState(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assTendCheckJson);

	}
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstendcheck/auditAssTrend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAssTrend(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assTendCheckJson = "";

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("bid_id", ids[0]);

			mapVo.put("bid_state", ids[1]);
			
			mapVo.put("bid_checkdate", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));

			listVo.add(mapVo);
			
		}

		try {
			assTendCheckJson = assTendCheckService.updateAssTendCheckState(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assTendCheckJson);

	}
	/**
	 * @Description 销审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstendcheck/unAuditAssTrend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditAssTrend(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assTendCheckJson = "";

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("bid_id", ids[0]);

			mapVo.put("bid_state", ids[1]);
			
			mapVo.put("bid_checkdate", null);

			listVo.add(mapVo);
			
		}

		try {
			assTendCheckJson = assTendCheckService.updateAssTendCheckState(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assTendCheckJson);

	}
	
	
	
	/**
	 * 招标信息页面
	 * @param detail_id
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstendcheck/assBidUpdatePage", method = RequestMethod.GET)
	public String assBidUpdatePage( @RequestParam Map<String, Object> mapVo,Model mode)
			throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("rowindex",mapVo.get("rowindex"));
		return "hrp/ass/asstend/assTendUpdate";
	}
	
	
}
