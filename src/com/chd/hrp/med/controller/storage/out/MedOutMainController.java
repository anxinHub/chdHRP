/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.storage.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.ChdToken;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedOutMain;
import com.chd.hrp.med.service.storage.out.MedOutMainService;

/**
 * @Description: MED_OUT_MAIN
 * @Table: MED_OUT_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedOutMainController extends BaseController {

	private static Logger logger = Logger.getLogger(MedOutMainController.class);

	// 引入Service服务
	@Resource(name = "medOutMainService")
	private final MedOutMainService medOutMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainMainPage", method = RequestMethod.GET)
	public String medOutMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08015", MyConfig.getSysPara("08015"));
		mode.addAttribute("p08018", MyConfig.getSysPara("08018"));
		
		mode.addAttribute("p08044", MyConfig.getSysPara("08044"));

		return "hrp/med/storage/out/outlibrary/medOutMainMain";

	}

	/**
	 * @Description 选择药品页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainFifoPage", method = RequestMethod.GET)
	public String medOutMainFifoPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (paras != null && !"".equals(paras)) {
			String[] paraArray = paras.split("@");
			if (!"null".equals(paraArray[0])) {
				mode.addAttribute("store_id", paraArray[0]);
			}

			if (!"null".equals(paraArray[1])) {
				mode.addAttribute("out_id", paraArray[1]);
			}

			if (!"null".equals(paraArray[2])) {
				mode.addAttribute("store_text", paraArray[2]);
			}
			if (!"null".equals(paraArray[3])) {

				mode.addAttribute("rows", paraArray[3]);

			}
		}
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/out/outlibrary/medOutMainFifo";

	}

	/**
	 * @Description 配套页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainMatchedPage", method = RequestMethod.GET)
	public String medOutMainMatchedPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (paras != null && !"".equals(paras)) {
			String[] paraArray = paras.split("@");
			if (!"null".equals(paraArray[0])) {
				mode.addAttribute("dept_id", paraArray[0]);
			}
			if (!"null".equals(paraArray[0])) {
				mode.addAttribute("dept_text", paraArray[1]);
			}
			if (!"null".equals(paraArray[1])) {
				mode.addAttribute("store_id", paraArray[2]);
			}
			
			if (!"null".equals(paraArray[2])) {
				mode.addAttribute("store_text", paraArray[3]);
			}
		}

		return "hrp/med/storage/out/outlibrary/medOutMainMatch";
	}

	/**
	 * @Description 历史导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainHistoryPage", method = RequestMethod.GET)
	public String medOutMainHistoryPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_text", mapVo.get("dept_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/out/outlibrary/medOutMainHistory";
	}

	/**
	 * @Description 定向页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainDirPage", method = RequestMethod.GET)
	public String medOutMainDirPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (paras != null && !"".equals(paras)) {
			String[] paraArray = paras.split("@");
			if (!"null".equals(paraArray[0])) {
				mode.addAttribute("dept_id", paraArray[0]);
			}

			if (!"null".equals(paraArray[1])) {
				mode.addAttribute("store_id", paraArray[1]);
			}
		}
		
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/out/outlibrary/medOutMainDir";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainAddPage", method = RequestMethod.GET)
	public String medOutMainAddPage(Model mode) throws Exception {
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08043", MyConfig.getSysPara("08043"));
		mode.addAttribute("p08044", MyConfig.getSysPara("08044"));
		
		return "hrp/med/storage/out/outlibrary/medOutMainAdd";
	}

	/**
	 * @Description 添加数据 MED_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/addMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		mapVo.put("maker", SessionManager.getUserId());

		String medJson;
		try {
			medJson = medOutMainService.add(mapVo);
		}
		catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}

	/**
	 * @Description 更新跳转页面 MED_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainUpdatePage", method = RequestMethod.GET)
	public String medOutMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		Map<String, Object> medOutMain = medOutMainService.queryMedOutMainByCode(mapVo);

		if (medOutMain == null) {
			return "hrp/med/storage/out/outlibrary/medOutMainAdd";

		}

		if (medOutMain.get("out_date") != null && !"".equals(medOutMain.get("out_date").toString())) {
			medOutMain.put("out_date", DateUtil.dateToString((Date) medOutMain.get("out_date"), "yyyy-MM-dd"));
		}

		if (medOutMain.get("app_date") != null && !"".equals(medOutMain.get("app_date").toString())) {
			medOutMain.put("app_date", DateUtil.dateToString((Date) medOutMain.get("app_date"), "yyyy-MM-dd HH:mm:ss"));
		}
		mode.addAttribute("medOutMain", medOutMain);
		
		Integer is_apply  = medOutMainService.queryMedOutMainIsApply(mapVo);
		if(is_apply>0){
			mode.addAttribute("is_apply", "1");
		}else{
			mode.addAttribute("is_apply", "0");
		}
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08015", MyConfig.getSysPara("08015"));
		mode.addAttribute("p08018", MyConfig.getSysPara("08018"));
		mode.addAttribute("p08042", MyConfig.getSysPara("08042"));
		
		mode.addAttribute("p08043", MyConfig.getSysPara("08043"));
		mode.addAttribute("p08044", MyConfig.getSysPara("08044"));
		
		
		return "hrp/med/storage/out/outlibrary/medOutMainUpdate";
	}

	/**
	 * @Description 加载明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutDetailById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String medOutDetail = medOutMainService.queryMedOutDetailByOutId(mapVo);

		return JSONObject.parseObject(medOutDetail);
	}

	/**
	 * @Description 更新数据 MED_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/updateMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medJson;
		try {
			medJson = medOutMainService.update(mapVo);
		}
		catch (Exception e) {
			medJson = e.getMessage();
		}
		
		/*if(medJson.contains("true")){
			medOutMainService.updateMedApplyOutRela(mapVo);
		}*/
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 更新数据 MED_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/addOrUpdateMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String medJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}

			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}

			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}

			try {
				medJson = medOutMainService.addOrUpdate(detailVo);
			}
			catch (Exception e) {
				medJson = e.getMessage();
			}
		}
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 删除数据 MED_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/deleteMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("out_id", ids[3]);
			// 用于删除与申请单之前的对应关系
			mapVo.put("rela_type", "1");
			mapVo.put("rela_id", ids[3]);

			listVo.add(mapVo);

		}

		String medJson;
		try {
			medJson = medOutMainService.deleteBatch(listVo);
		}
		catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}

	/**
	 * @Description 审核数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/auditMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMedOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("out_id", ids[3]);

			mapVo.put("checker", SessionManager.getUserId());

			mapVo.put("check_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));

			listVo.add(mapVo);

		}

		String medJson;
		try {
			medJson = medOutMainService.auditMedOutMain(listVo);
		}
		catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/confirmOutMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmOutMedOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String date = DateUtil.dateToString(new Date(), "yyyy/MM/dd");
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String user_id = SessionManager.getUserId();

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("year", year);
			mapVo.put("month", month);
			mapVo.put("out_id", ids[3]);
			mapVo.put("state", 3);
			mapVo.put("confirmer", user_id);
			mapVo.put("confirm_date", date);

			listVo.add(mapVo);
		}

		String medJson;
		try {
			medJson = medOutMainService.confirmOutMedOutMain(listVo);
		}
		catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 消审数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/unAuditMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMedOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("out_id", ids[3]);

			mapVo.put("checker", "");

			mapVo.put("check_date", "");

			listVo.add(mapVo);

		}

		String medJson;
		try {
			medJson = medOutMainService.unAuditMedOutMain(listVo);
		}
		catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}

	/**
	 * @Description 复制数据 MED_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/copyMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMedOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("out_id", ids[3]);

			mapVo.put("maker", SessionManager.getUserId());

			mapVo.put("make_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));

			listVo.add(mapVo);

		}

		String medJson;
		try {
			medJson = medOutMainService.copyMedOutMain(listVo);
		}
		catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}

	/**
	 * @Description 冲账数据 MED_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/balanceMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> balanceMedOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("out_id", ids[3]);

			mapVo.put("maker", SessionManager.getUserId());

			mapVo.put("make_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));

			listVo.add(mapVo);

		}

		String medJson;
		try {
			medJson = medOutMainService.offsetMedOutMain(listVo);
		}
		catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}

	/**
	 * @Description 查询数据 MED_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		// 转换时间格式
		if (mapVo.get("out_date_start") != null && !"".equals(String.valueOf(mapVo.get("out_date_start")))) {
			mapVo.put("out_date_start", DateUtil.stringToDate(String.valueOf(mapVo.get("out_date_start")), "yyyy-MM-dd"));
		}
		if (mapVo.get("out_date_end") != null && !"".equals(String.valueOf(mapVo.get("out_date_end")))) {
			mapVo.put("out_date_end", DateUtil.stringToDate(String.valueOf(mapVo.get("out_date_end")), "yyyy-MM-dd"));
		}

		if (mapVo.get("confirm_date_start") != null && !"".equals(String.valueOf(mapVo.get("confirm_date_start")))) {
			mapVo.put("confirm_date_start", DateUtil.stringToDate(String.valueOf(mapVo.get("confirm_date_start")), "yyyy-MM-dd"));
		}
		if (mapVo.get("confirm_date_end") != null && !"".equals(String.valueOf(mapVo.get("confirm_date_end")))) {
			mapVo.put("confirm_date_end", DateUtil.stringToDate(String.valueOf(mapVo.get("confirm_date_end")), "yyyy-MM-dd"));
		}

		// 查询时 登录用户只能查询其有数据权限（部门权限、仓库权限）的 相关数据
		if (mapVo.get("dept_id") == "" || mapVo.get("dept_id") == null) {

			mapVo.put("dept_id", "00"); // 当 dept_id == 00 时 根据用户 部门权限 限定 申请科室
		}

		if (mapVo.get("store_id") == "" || mapVo.get("store_id") == null) {

			mapVo.put("store_id", "00"); // 当 store_id == 00 时 根据用户 仓库权限 限定 响应仓库
		}
		
		String medOutMain = "";
		if(mapVo.get("show_detail").equals("1")){
			medOutMain =  medOutMainService.queryOutDetails(getPage(mapVo));
		}else{
			medOutMain =  medOutMainService.queryMedOutMain(getPage(mapVo));
		}
		
		return JSONObject.parseObject(medOutMain);

	}

	/**
	 * @Description 查询数据 MED_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedInMainByIsDir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInMainByIsDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		// 转换日期
		if (mapVo.get("in_date_start") != null && !"".equals(mapVo.get("in_date_start").toString())) {
			mapVo.put("in_date_start", DateUtil.stringToDate(mapVo.get("in_date_start").toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("in_date_end") != null && !"".equals(mapVo.get("in_date_end").toString())) {
			mapVo.put("in_date_end", DateUtil.stringToDate(mapVo.get("in_date_end").toString(), "yyyy-MM-dd"));
		}

		String medOutMain = medOutMainService.queryMedInMainByIsDir(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);
	}

	/**
	 * @Description 查询数据 MED_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedDeptMatchByMatchd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptMatchByMatchd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String medOutMain = medOutMainService.queryMedDeptMatchByMatchd(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);

	}

	/**
	 * @Description 查询数据 MED_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedInDetailByIsDir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInDetailByIsDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String medOutMain = medOutMainService.queryMedInDetailByIsDir(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);

	}

	/**
	 * @Description 查询数据 MED_OUT_Detail
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutDetailHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutDetailHistory(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		// 时间格式转换
		if (mapVo.get("begin_out_date") != null && !"".equals(String.valueOf(mapVo.get("begin_out_date")))) {
			mapVo.put("begin_out_date", DateUtil.stringToDate(String.valueOf(mapVo.get("begin_out_date")), "yyyy-MM-dd"));
		}
		if (mapVo.get("end_out_date") != null && !"".equals(String.valueOf(mapVo.get("end_out_date")))) {
			mapVo.put("end_out_date", DateUtil.stringToDate(String.valueOf(mapVo.get("end_out_date")), "yyyy-MM-dd"));
		}

		String medOutDetailHistory = medOutMainService.queryMedOutDetailHistory(getPage(mapVo));

		return JSONObject.parseObject(medOutDetailHistory);

	}

	/**
	 * @Description 查询数据 med_inv_hold
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutMainByInvHold", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutMainByInvHold(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String medOutMain = medOutMainService.queryMedOutMainByInvHold(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);
	}

	/**
	 * @Description 查询数据 med_fifo_balance
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutMainByFifoBalances", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutMainByFifoBalances(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		mapVo.put("store_id", mapVo.get("store_id"));

		String medOutMain = medOutMainService.queryMedOutMainByFifoBalance(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);
	}
	
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutMainByFifoBalance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutMainByFifoBalance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		mapVo.put("inv_id", mapVo.get("inv_id"));
		mapVo.put("inv_no", mapVo.get("inv_no"));
		mapVo.put("store_id", mapVo.get("store_id"));

		String medOutMain = medOutMainService.queryMedOutMainByFifoBalanceOld(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);
	}

	/**
	 * @Description 配套导入药品结果集
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutDetailByMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutDetailByMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String medMsg = medOutMainService.queryMedOutDetailByMatch(mapVo);

		return JSONObject.parseObject(medMsg);
	}

	/**
	 * @Description 定向出库药品结果集
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutDetailByIsDir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutDetailByIsDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String medMsg = medOutMainService.queryMedOutDetailByIsDir(mapVo);

		return JSONObject.parseObject(medMsg);
	}

	/**
	 * @Description 获取上一张出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("type", "before");

		String medJson = medOutMainService.queryMedOutMainBeforeOrNext(mapVo);

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 获取下一张出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutNextNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("type", "next");

		String medJson = medOutMainService.queryMedOutMainBeforeOrNext(mapVo);

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 导入跳转页面 MED_OUT_MAIN
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainImportPage", method = RequestMethod.GET)
	public String medOutMainImportPage(Model mode) throws Exception {

		return "hrp/med/storage/out/outlibrary/medOutMainImport";

	}

	/**
	 * @Description 下载导入模版 MED_OUT_MAIN
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/storage/out/outlibrary/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate", "MED_OUT_MAIN.xls");

		return null;
	}

	/**
	 * @Description 导入数据 IN_NO:每个库房每个月从SS-YYYYMM0001开始编号 ss库号 状态：0:验收;
	 *              1:未审核；2审核；3入库确认；4财务记帐。
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/medoutmain/readMedOutMainFiles", method = RequestMethod.POST)
	public String readMedOutMainFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<MedOutMain> list_err = new ArrayList<MedOutMain>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				MedOutMain medOutMain = new MedOutMain();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					medOutMain.setOut_id(Long.valueOf(temp[3]));
					mapVo.put("out_id", temp[3]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					mapVo.put("out_no", temp[4]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					mapVo.put("year", temp[5]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					mapVo.put("month", temp[6]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					mapVo.put("bus_type_code", temp[7]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					medOutMain.setStore_id(Long.valueOf(temp[8]));
					mapVo.put("store_id", temp[8]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					medOutMain.setStore_no(Long.valueOf(temp[9]));
					mapVo.put("store_no", temp[9]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					mapVo.put("brief", temp[10]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					medOutMain.setOut_date(DateUtil.stringToDate(temp[11]));
					mapVo.put("out_date", temp[11]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					medOutMain.setDept_id(Long.valueOf(temp[12]));
					mapVo.put("dept_id", temp[12]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					medOutMain.setDept_no(Long.valueOf(temp[13]));
					mapVo.put("dept_no", temp[13]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					medOutMain.setDept_emp(Long.valueOf(temp[14]));
					mapVo.put("dept_emp", temp[14]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					mapVo.put("use_code", temp[15]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[16])) {

					medOutMain.setProj_id(Integer.valueOf(temp[16]));
					mapVo.put("proj_id", temp[16]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[17])) {

					medOutMain.setMaker(Long.valueOf(temp[17]));
					mapVo.put("maker", temp[17]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[18])) {

					medOutMain.setChecker(Long.valueOf(temp[18]));
					mapVo.put("checker", temp[18]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[19])) {

					medOutMain.setCheck_date(DateUtil.stringToDate(temp[19]));
					mapVo.put("check_date", temp[19]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[20])) {

					medOutMain.setConfirmer(Long.valueOf(temp[20]));
					mapVo.put("confirmer", temp[20]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[21])) {

					medOutMain.setConfirm_date(DateUtil.stringToDate(temp[21]));
					mapVo.put("confirm_date", temp[21]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[22])) {

					medOutMain.setState(Integer.valueOf(temp[22]));
					mapVo.put("state", temp[22]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[23])) {

					mapVo.put("company", temp[23]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[24])) {

					medOutMain.setIs_dir(Integer.valueOf(temp[24]));
					mapVo.put("is_dir", temp[24]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[25])) {

					mapVo.put("his_flag", temp[25]);

				} else {

					err_sb.append("为空  ");

				}

				MedOutMain data_exc_extis = medOutMainService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					medOutMain.setError_type(err_sb.toString());

					list_err.add(medOutMain);

				} else {

					String dataJson = medOutMainService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			MedOutMain data_exc = new MedOutMain();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 IN_NO:每个库房每个月从SS-YYYYMM0001开始编号 ss库号 状态：0:验收;
	 *              1:未审核；2审核；3入库确认；4财务记帐。
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/medoutmain/addBatchMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<MedOutMain> list_err = new ArrayList<MedOutMain>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				MedOutMain medOutMain = new MedOutMain();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("out_id"))) {

					medOutMain.setOut_id(Long.valueOf((String) jsonObj.get("out_id")));
					mapVo.put("out_id", jsonObj.get("out_id"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("out_no"))) {

					mapVo.put("out_no", jsonObj.get("out_no"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("year"))) {

					mapVo.put("year", jsonObj.get("year"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("month"))) {

					mapVo.put("month", jsonObj.get("month"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {

					mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("store_id"))) {

					medOutMain.setStore_id(Long.valueOf((String) jsonObj.get("store_id")));
					mapVo.put("store_id", jsonObj.get("store_id"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("store_no"))) {

					medOutMain.setStore_no(Long.valueOf((String) jsonObj.get("store_no")));
					mapVo.put("store_no", jsonObj.get("store_no"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("brief"))) {

					mapVo.put("brief", jsonObj.get("brief"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("out_date"))) {

					medOutMain.setOut_date(DateUtil.stringToDate((String) jsonObj.get("out_date")));
					mapVo.put("out_date", jsonObj.get("out_date"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {

					medOutMain.setDept_id(Long.valueOf((String) jsonObj.get("dept_id")));
					mapVo.put("dept_id", jsonObj.get("dept_id"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {

					medOutMain.setDept_no(Long.valueOf((String) jsonObj.get("dept_no")));
					mapVo.put("dept_no", jsonObj.get("dept_no"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dept_emp"))) {

					medOutMain.setDept_emp(Long.valueOf((String) jsonObj.get("dept_emp")));
					mapVo.put("dept_emp", jsonObj.get("dept_emp"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("use_code"))) {

					mapVo.put("use_code", jsonObj.get("use_code"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("proj_id"))) {

					medOutMain.setProj_id(Integer.valueOf((String) jsonObj.get("proj_id")));
					mapVo.put("proj_id", jsonObj.get("proj_id"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("maker"))) {

					medOutMain.setMaker(Long.valueOf((String) jsonObj.get("maker")));
					mapVo.put("maker", jsonObj.get("maker"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("checker"))) {

					medOutMain.setChecker(Long.valueOf((String) jsonObj.get("checker")));
					mapVo.put("checker", jsonObj.get("checker"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("check_date"))) {

					medOutMain.setCheck_date(DateUtil.stringToDate((String) jsonObj.get("check_date")));
					mapVo.put("check_date", jsonObj.get("check_date"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("confirmer"))) {

					medOutMain.setConfirmer(Long.valueOf((String) jsonObj.get("confirmer")));
					mapVo.put("confirmer", jsonObj.get("confirmer"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("confirm_date"))) {

					medOutMain.setConfirm_date(DateUtil.stringToDate((String) jsonObj.get("confirm_date")));
					mapVo.put("confirm_date", jsonObj.get("confirm_date"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					medOutMain.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("company"))) {

					mapVo.put("company", jsonObj.get("company"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_dir"))) {

					medOutMain.setIs_dir(Integer.valueOf((String) jsonObj.get("is_dir")));
					mapVo.put("is_dir", jsonObj.get("is_dir"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("his_flag"))) {

					mapVo.put("his_flag", jsonObj.get("his_flag"));
				} else {

					err_sb.append("为空  ");

				}

				MedOutMain data_exc_extis = medOutMainService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					medOutMain.setError_type(err_sb.toString());

					list_err.add(medOutMain);

				} else {

					String dataJson = medOutMainService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			MedOutMain data_exc = new MedOutMain();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

	/**
	 * @Description 打印模板页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/storageOutPrintSetPage", method = RequestMethod.GET)
	public String storageOutPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo != null && mapVo.size() > 0) {
			for (Map.Entry<String, Object> entry : mapVo.entrySet()) {
				mode.addAttribute(entry.getKey(), entry.getValue());
			}
		}
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
		return "redirect:../../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}

	/**
	 * @Description 入库模板打印（包含主从表）
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedOutByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}

		String reJson = null;
		try {
			reJson = medOutMainService.queryMedOutByPrintTemlate(mapVo);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(reJson);
	}
*/
	/**
	 * @Description 合并冲账数据 MED_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/mergeBalanceMedOutMain", method = RequestMethod.GET)
	public String mergeBalanceMedOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String out_id = "";
		String out_no = "";
        
		String out_ids = mapVo.get("out_ids").toString();
		Map<String, Object> medOutMain = new HashMap<String, Object>();
		for (String id : out_ids.split(",")) {
			String[] v=id.split("@");
			// 表的主键
			out_id = out_id + v[0] + ",";
			out_no = out_no + v[1] + ",";
			mapVo.put("out_id", v[0]);
		}
		
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		mapVo.put("out_ids", "(" + out_id.substring(0, out_id.length() - 1) + ")");
		String medOutDetail = medOutMainService.mergeOffsetMedOutMain(getPage(mapVo));
		
		medOutMain = medOutMainService.queryMedOutMainByCode(mapVo);
		if("11".equals(medOutMain.get("bus_type_code").toString()) || "17".equals(medOutMain.get("bus_type_code").toString())
		 || "19".equals(medOutMain.get("bus_type_code").toString()) || "23".equals(medOutMain.get("bus_type_code").toString())
		){
			medOutMain.put("bus_type_code", medOutMain.get("bus_type_code").toString());
		}else{
			medOutMain.put("bus_type_code", "21");
		}
		
		medOutMain.put("brief", "冲账"+out_no.substring(0, out_no.length() - 1));
		mode.addAttribute("medOutMain", medOutMain);
		mode.addAttribute("medOutDetail", medOutDetail);
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08015", MyConfig.getSysPara("08015"));

		return "hrp/med/storage/out/outlibrary/medOutMainOffset";

	}

	/**
	 * @Description 合并汇总冲账查询明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/mergeOffsetMedOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mergeOffsetMedOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		// 转换日期
		if (mapVo.get("in_date_start") != null && !"".equals(mapVo.get("in_date_start").toString())) {
			mapVo.put("in_date_start", DateUtil.stringToDate(mapVo.get("in_date_start").toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("in_date_end") != null && !"".equals(mapVo.get("in_date_end").toString())) {
			mapVo.put("in_date_end", DateUtil.stringToDate(mapVo.get("in_date_end").toString(), "yyyy-MM-dd"));
		}

		String medOutMain = medOutMainService.mergeOffsetMedOutMain(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);
	}

	/**
	 * @Description 科室申领导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainApplyImpPage", method = RequestMethod.GET)
	public String medOutMainApplyImpPage(Model mode) throws Exception {
		
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/out/outlibrary/medOutMainApplyImp";
	}

	/**
	 * @Description 查询科室申领主表数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedApplyMainOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedApplyMainOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		//时间格式转换
		if(mapVo.get("begin_app_date") != null && !"".equals(mapVo.get("begin_app_date").toString())){
			mapVo.put("begin_app_date", DateUtil.stringToDate(mapVo.get("begin_app_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_app_date") != null && !"".equals(mapVo.get("end_app_date").toString())){
			mapVo.put("end_app_date", DateUtil.stringToDate(mapVo.get("end_app_date").toString(), "yyyy-MM-dd"));
		}
		
		String medOutDetail = medOutMainService.queryMedApplyMainOut(getPage(mapVo));

		return JSONObject.parseObject(medOutDetail);
	}

	/**
	 * @Description 查询科室申领明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedApplyDetailOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedApplyDetailOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		String medOutDetail = medOutMainService.queryMedApplyDetailOut(mapVo);

		return JSONObject.parseObject(medOutDetail);
	}

	/**
	 * @Description 
	 * 申领单汇总生成出库单跳转添加页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainAddByOther", method = RequestMethod.GET)
	public String medOutMainAddByOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object>medOutMain = medOutMainService.queryOutMainByAppCollect(mapVo);
		
		//根据参数is_back判断是科室领用还是科室退库
		if(mapVo.get("is_back") != null && !"1".equals(mapVo.get("is_back"))){
			medOutMain.put("bus_type_code", "21");//科室退库
		}else{
			medOutMain.put("bus_type_code", "3");//科室领用
		}
		if(medOutMain.get("url") == null){
			medOutMain.put("url", "../applycheck/addMedOutMainByApp");
		}
		//出库日期
		if(medOutMain.get("out_date") != null && !"".equals(medOutMain.get("out_date"))){
			medOutMain.put("out_date", DateUtil.dateToString((Date)medOutMain.get("out_date"), "yyyy-MM-dd"));
		}
		
		String medOutDetail;
		try {
			medOutDetail = medOutMainService.queryOutDetailByAppCollect(mapVo);
		} catch (Exception e) {
			medOutDetail = e.getMessage();
		}
		mode.addAttribute("medOutMain", medOutMain);
		mode.addAttribute("medOutDetail", medOutDetail);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08043", MyConfig.getSysPara("08043"));
		
		return "/hrp/med/storage/out/outlibrary/medOutMainAddByOther";
	}
	
	/**
	 * @Description 选择药品页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/medOutMainChoiceInvPage", method = RequestMethod.GET)
	public String medOutMainChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/out/outlibrary/medOutMainChoiceInv";
	}

	/**
	 * @Description 选择药品查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryMedInvBatchList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvBatchList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medOutDetail = medOutMainService.queryMedInvBatchList(getPage(mapVo));

		return JSONObject.parseObject(medOutDetail);
	}
	
	/**
	 * @Description 选择药品返回List
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/outlibrary/queryOutInvListByChoiceInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOutInvListByChoiceInv(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("store_id", ids[3]);
			mapVo.put("inv_id", ids[4]);
			mapVo.put("inv_code", ids[5]);
			mapVo.put("inv_name", ids[6]);
			mapVo.put("batch_no", ids[7]);
			mapVo.put("amount", ids[8]);

			listVo.add(mapVo);
		}
		
		String medOutDetail;
		try {
			medOutDetail = medOutMainService.queryOutInvListByChoiceInv(listVo);
		} catch (Exception e) {
			medOutDetail = e.getMessage();
		}

		return JSONObject.parseObject(medOutDetail);
	}
}
