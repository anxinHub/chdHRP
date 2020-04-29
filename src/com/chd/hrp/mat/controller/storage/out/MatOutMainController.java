/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.out; 

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
import com.chd.hrp.mat.entity.MatOutMain;
import com.chd.hrp.mat.service.requrie.dept.MatRequirePlanService;
import com.chd.hrp.mat.service.storage.out.MatOutMainService;

/**
 * @Description: MAT_OUT_MAIN
 * @Table: MAT_OUT_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatOutMainController extends BaseController {

	private static Logger logger = Logger.getLogger(MatOutMainController.class);

	// 引入Service服务
	@Resource(name = "matOutMainService")
	private final MatOutMainService matOutMainService = null;

	//引入Service服务
	@Resource(name = "matRequirePlanService")
	private final MatRequirePlanService matRequirePlanService = null;
		
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainMainPage", method = RequestMethod.GET)
	public String matOutMainMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04015", MyConfig.getSysPara("04015"));
		mode.addAttribute("p04018", MyConfig.getSysPara("04018"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));

		return "hrp/mat/storage/out/outlibrary/matOutMainMain";

	}

	/**
	 * @Description 选择材料页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainFifoPage", method = RequestMethod.GET)
	public String matOutMainFifoPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/storage/out/outlibrary/matOutMainFifo";

	}

	/**
	 * @Description 配套页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainMatchedPage", method = RequestMethod.GET)
	public String matOutMainMatchedPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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

		return "hrp/mat/storage/out/outlibrary/matOutMainMatch";
	}

	/**
	 * @Description 历史导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainHistoryPage", method = RequestMethod.GET)
	public String matOutMainHistoryPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_text", mapVo.get("dept_text"));

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/storage/out/outlibrary/matOutMainHistory";
	}

	/**
	 * @Description 定向页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainDirPage", method = RequestMethod.GET)
	public String matOutMainDirPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/storage/out/outlibrary/matOutMainDir";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainAddPage", method = RequestMethod.GET)
	public String matOutMainAddPage(Model mode) throws Exception {
		mode.addAttribute("user_id", SessionManager.getUserId());

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04043", MyConfig.getSysPara("04043"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/storage/out/outlibrary/matOutMainAdd";
	}

	/**
	 * @Description 添加数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/addMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matJson;
		try {
			matJson = matOutMainService.add(mapVo);
		}
		catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 更新跳转页面 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainUpdatePage", method = RequestMethod.GET)
	public String matOutMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

		Map<String, Object> matOutMain = matOutMainService.queryMatOutMainByCode(mapVo);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04015", MyConfig.getSysPara("04015"));
		mode.addAttribute("p04018", MyConfig.getSysPara("04018"));
		mode.addAttribute("p04042", MyConfig.getSysPara("04042"));
		mode.addAttribute("p04043", MyConfig.getSysPara("04043"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("p04076", MyConfig.getSysPara("04076"));

		if (matOutMain == null) {

			return "hrp/mat/storage/out/outlibrary/matOutMainAdd";

		}

		if (matOutMain.get("out_date") != null && !"".equals(matOutMain.get("out_date").toString())) {
			matOutMain.put("out_date", DateUtil.dateToString((Date) matOutMain.get("out_date"), "yyyy-MM-dd"));
		}

		if (matOutMain.get("app_date") != null && !"".equals(matOutMain.get("app_date").toString())) {
			matOutMain.put("app_date", DateUtil.dateToString((Date) matOutMain.get("app_date"), "yyyy-MM-dd HH:mm:ss"));
		}
		mode.addAttribute("matOutMain", matOutMain);
		
		Integer is_apply  = matOutMainService.queryMatOutMainIsApply(mapVo);
		if(is_apply>0){
			mode.addAttribute("is_apply", "1");
		}else{
			mode.addAttribute("is_apply", "0");
		}
		
		
		return "hrp/mat/storage/out/outlibrary/matOutMainUpdate";
	}

	/**
	 * @Description 加载明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutDetailById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutDetail = matOutMainService.queryMatOutDetailByOutId(mapVo);

		return JSONObject.parseObject(matOutDetail);
	}
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutDetail = matOutMainService.queryMatOutDetailByCode(mapVo);

		return JSONObject.parseObject(matOutDetail);
	}

	/**
	 * @Description 更新数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/updateMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matJson;
		try {
			matJson = matOutMainService.update(mapVo);
		}
		catch (Exception e) {
			matJson = e.getMessage();
		}
		
		/*if(matJson.contains("true")){
			matOutMainService.updateMatApplyOutRela(mapVo);
		}*/
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 更新数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/addOrUpdateMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String matJson = "";

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
				matJson = matOutMainService.addOrUpdate(detailVo);
			}
			catch (Exception e) {
				matJson = e.getMessage();
			}
		}
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 删除数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/deleteMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String matJson;
		try {
			matJson = matOutMainService.deleteBatch(listVo);
		}
		catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 审核数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/auditMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String matJson;
		try {
			matJson = matOutMainService.auditMatOutMain(listVo);
		}
		catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/confirmOutMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmOutMatOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String user_id = SessionManager.getUserId();
		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String date=DateUtil.getSysDate();//获取服务器当前日期

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString()) ){
				date = ids[4];
			}
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			
			
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("year", year);
			mapVo.put("month", month);
			mapVo.put("out_id", ids[3]);
			mapVo.put("state", 3);
			mapVo.put("confirmer", user_id);
			mapVo.put("confirm_date",  date);

			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matOutMainService.confirmOutMatOutMain(listVo);
		}
		catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 消审数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/unAuditMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String matJson;
		try {
			matJson = matOutMainService.unAuditMatOutMain(listVo);
		}
		catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 复制数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/copyMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMatOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String matJson;
		try {
			matJson = matOutMainService.copyMatOutMain(listVo);
		}
		catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 冲账数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/balanceMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> balanceMatOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String matJson;
		try {
			matJson = matOutMainService.offsetMatOutMain(listVo);
		}
		catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 查询数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		//由于xml中已经to_char （之前我查询的日期是2017-08-11 的日期，查询不到2017-08-11 的数据，必须日期选择为2017-08-12才会查询到）
		// 转换时间格式
		/*if (mapVo.get("out_date_start") != null && !"".equals(String.valueOf(mapVo.get("out_date_start")))) {
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
		}*/

		// 查询时 登录用户只能查询其有数据权限（部门权限、仓库权限）的 相关数据
		if (mapVo.get("dept_id") == "" || mapVo.get("dept_id") == null) {

			mapVo.put("dept_id", "00"); // 当 dept_id == 00 时 根据用户 部门权限 限定 申请科室
		}

		if (mapVo.get("store_id") == "" || mapVo.get("store_id") == null) {

			mapVo.put("store_id", "00"); // 当 store_id == 00 时 根据用户 仓库权限 限定 响应仓库
		}
		
		String matOutMain = "";
		if(mapVo.get("show_detail").equals("1")){
			matOutMain =  matOutMainService.queryOutDetails(getPage(mapVo));
		}else{
			matOutMain =  matOutMainService.queryMatOutMain(getPage(mapVo));
		}
		
		return JSONObject.parseObject(matOutMain);

	}

	/**
	 * @Description 查询数据 MAT_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatInMainByIsDir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInMainByIsDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutMain = matOutMainService.queryMatInMainByIsDir(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);
	}

	/**
	 * @Description 查询数据 MAT_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatDeptMatchByMatchd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptMatchByMatchd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutMain = matOutMainService.queryMatDeptMatchByMatchd(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);

	}

	/**
	 * @Description 查询数据 MAT_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatInDetailByIsDir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInDetailByIsDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutMain = matOutMainService.queryMatInDetailByIsDir(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);

	}

	/**
	 * @Description 查询数据 MAT_OUT_Detail
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutDetailHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutDetailHistory(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutDetailHistory = matOutMainService.queryMatOutDetailHistory(getPage(mapVo));

		return JSONObject.parseObject(matOutDetailHistory);

	}

	/**
	 * @Description 查询数据 mat_inv_hold
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutMainByInvHold", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutMainByInvHold(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutMain = matOutMainService.queryMatOutMainByInvHold(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);
	}

	/**
	 * @Description 查询数据 mat_fifo_balance
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutMainByFifoBalances", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutMainByFifoBalances(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutMain = matOutMainService.queryMatOutMainByFifoBalance(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);
	}
	
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutMainByFifoBalance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutMainByFifoBalance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutMain = matOutMainService.queryMatOutMainByFifoBalanceOld(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);
	}

	/**
	 * @Description 配套导入材料结果集
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutDetailByMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutDetailByMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matMsg = matOutMainService.queryMatOutDetailByMatchNew(mapVo);

		return JSONObject.parseObject(matMsg);
	}

	/**
	 * @Description 定向出库材料结果集
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutDetailByIsDir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutDetailByIsDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matMsg = matOutMainService.queryMatOutDetailByIsDir(mapVo);

		return JSONObject.parseObject(matMsg);
	}

	/**
	 * @Description 获取上一张出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matJson = matOutMainService.queryMatOutMainBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 获取下一张出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutNextNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matJson = matOutMainService.queryMatOutMainBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 导入跳转页面 MAT_OUT_MAIN
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainImportPage", method = RequestMethod.GET)
	public String matOutMainImportPage(Model mode) throws Exception {

		return "hrp/mat/storage/out/outlibrary/matOutMainImport";

	}

	/**
	 * @Description 下载导入模版 MAT_OUT_MAIN
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/storage/out/outlibrary/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate", "MAT_OUT_MAIN.xls");

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
	@RequestMapping(value = "/hrp/mat/matoutmain/readMatOutMainFiles", method = RequestMethod.POST)
	public String readMatOutMainFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<MatOutMain> list_err = new ArrayList<MatOutMain>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				MatOutMain matOutMain = new MatOutMain();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					matOutMain.setOut_id(Long.valueOf(temp[3]));
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

					matOutMain.setStore_id(Long.valueOf(temp[8]));
					mapVo.put("store_id", temp[8]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					matOutMain.setStore_no(Long.valueOf(temp[9]));
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

					matOutMain.setOut_date(DateUtil.stringToDate(temp[11]));
					mapVo.put("out_date", temp[11]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					matOutMain.setDept_id(Long.valueOf(temp[12]));
					mapVo.put("dept_id", temp[12]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					matOutMain.setDept_no(Long.valueOf(temp[13]));
					mapVo.put("dept_no", temp[13]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					matOutMain.setDept_emp(Long.valueOf(temp[14]));
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

					matOutMain.setProj_id(Integer.valueOf(temp[16]));
					mapVo.put("proj_id", temp[16]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[17])) {

					matOutMain.setMaker(Long.valueOf(temp[17]));
					mapVo.put("maker", temp[17]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[18])) {

					matOutMain.setChecker(Long.valueOf(temp[18]));
					mapVo.put("checker", temp[18]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[19])) {

					matOutMain.setCheck_date(DateUtil.stringToDate(temp[19]));
					mapVo.put("check_date", temp[19]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[20])) {

					matOutMain.setConfirmer(Long.valueOf(temp[20]));
					mapVo.put("confirmer", temp[20]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[21])) {

					matOutMain.setConfirm_date(DateUtil.stringToDate(temp[21]));
					mapVo.put("confirm_date", temp[21]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[22])) {

					matOutMain.setState(Integer.valueOf(temp[22]));
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

					matOutMain.setIs_dir(Integer.valueOf(temp[24]));
					mapVo.put("is_dir", temp[24]);

				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(temp[25])) {

					mapVo.put("his_flag", temp[25]);

				} else {

					err_sb.append("为空  ");

				}

				MatOutMain data_exc_extis = matOutMainService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					matOutMain.setError_type(err_sb.toString());

					list_err.add(matOutMain);

				} else {

					String dataJson = matOutMainService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			MatOutMain data_exc = new MatOutMain();

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
	@RequestMapping(value = "/hrp/mat/matoutmain/addBatchMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMatOutMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<MatOutMain> list_err = new ArrayList<MatOutMain>();

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

				MatOutMain matOutMain = new MatOutMain();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("out_id"))) {

					matOutMain.setOut_id(Long.valueOf((String) jsonObj.get("out_id")));
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

					matOutMain.setStore_id(Long.valueOf((String) jsonObj.get("store_id")));
					mapVo.put("store_id", jsonObj.get("store_id"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("store_no"))) {

					matOutMain.setStore_no(Long.valueOf((String) jsonObj.get("store_no")));
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

					matOutMain.setOut_date(DateUtil.stringToDate((String) jsonObj.get("out_date")));
					mapVo.put("out_date", jsonObj.get("out_date"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {

					matOutMain.setDept_id(Long.valueOf((String) jsonObj.get("dept_id")));
					mapVo.put("dept_id", jsonObj.get("dept_id"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {

					matOutMain.setDept_no(Long.valueOf((String) jsonObj.get("dept_no")));
					mapVo.put("dept_no", jsonObj.get("dept_no"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dept_emp"))) {

					matOutMain.setDept_emp(Long.valueOf((String) jsonObj.get("dept_emp")));
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

					matOutMain.setProj_id(Integer.valueOf((String) jsonObj.get("proj_id")));
					mapVo.put("proj_id", jsonObj.get("proj_id"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("maker"))) {

					matOutMain.setMaker(Long.valueOf((String) jsonObj.get("maker")));
					mapVo.put("maker", jsonObj.get("maker"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("checker"))) {

					matOutMain.setChecker(Long.valueOf((String) jsonObj.get("checker")));
					mapVo.put("checker", jsonObj.get("checker"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("check_date"))) {

					matOutMain.setCheck_date(DateUtil.stringToDate((String) jsonObj.get("check_date")));
					mapVo.put("check_date", jsonObj.get("check_date"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("confirmer"))) {

					matOutMain.setConfirmer(Long.valueOf((String) jsonObj.get("confirmer")));
					mapVo.put("confirmer", jsonObj.get("confirmer"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("confirm_date"))) {

					matOutMain.setConfirm_date(DateUtil.stringToDate((String) jsonObj.get("confirm_date")));
					mapVo.put("confirm_date", jsonObj.get("confirm_date"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					matOutMain.setState(Integer.valueOf((String) jsonObj.get("state")));
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

					matOutMain.setIs_dir(Integer.valueOf((String) jsonObj.get("is_dir")));
					mapVo.put("is_dir", jsonObj.get("is_dir"));
				} else {

					err_sb.append("为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("his_flag"))) {

					mapVo.put("his_flag", jsonObj.get("his_flag"));
				} else {

					err_sb.append("为空  ");

				}

				MatOutMain data_exc_extis = matOutMainService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					matOutMain.setError_type(err_sb.toString());

					list_err.add(matOutMain);

				} else {

					String dataJson = matOutMainService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			MatOutMain data_exc = new MatOutMain();

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
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/storageOutPrintSetPage", method = RequestMethod.GET)
	public String storageOutPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo != null && mapVo.size() > 0) {
			for (Map.Entry<String, Object> entry : mapVo.entrySet()) {
				mode.addAttribute(entry.getKey(), entry.getValue());
			}
		}
		//System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
		return "redirect:../../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}

	/**
	 * @Description 入库模板打印（包含主从表）
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			reJson = matOutMainService.queryMatOutByPrintTemlate(mapVo);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(reJson);
	}
*/
	/**
	 * @Description 合并冲账数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/mergeBalanceMatOutMain", method = RequestMethod.GET)
	public String mergeBalanceMatOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String out_id = "";
		String out_no = "";
        
		String out_ids = mapVo.get("out_ids").toString();
		Map<String, Object> matOutMain = new HashMap<String, Object>();
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
		String matOutDetail = matOutMainService.mergeOffsetMatOutMain(getPage(mapVo));
		
		matOutMain = matOutMainService.queryMatOutMainByCode(mapVo);
		if("11".equals(matOutMain.get("bus_type_code").toString()) || "17".equals(matOutMain.get("bus_type_code").toString())
		 || "19".equals(matOutMain.get("bus_type_code").toString()) || "23".equals(matOutMain.get("bus_type_code").toString())
		){
			matOutMain.put("bus_type_code", matOutMain.get("bus_type_code").toString());
		}else{
			matOutMain.put("bus_type_code", "21");
		}
		
		matOutMain.put("brief", "冲账"+out_no.substring(0, out_no.length() - 1));
		mode.addAttribute("matOutMain", matOutMain);
		mode.addAttribute("matOutDetail", matOutDetail);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04015", MyConfig.getSysPara("04015"));

		return "hrp/mat/storage/out/outlibrary/matOutMainOffset";

	}

	/**
	 * @Description 合并汇总冲账查询明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/mergeOffsetMatOutMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mergeOffsetMatOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutMain = matOutMainService.mergeOffsetMatOutMain(getPage(mapVo));

		return JSONObject.parseObject(matOutMain);
	}

	/**
	 * @Description 科室申领导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainApplyImpPage", method = RequestMethod.GET)
	public String matOutMainApplyImpPage(Model mode) throws Exception {

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/out/outlibrary/matOutMainApplyImp";
	}

	/**
	 * @Description 查询科室申领主表数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatApplyMainOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatApplyMainOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matOutDetail = matOutMainService.queryMatApplyMainOut(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);
	}

	/**
	 * @Description 查询科室申领明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatApplyDetailOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatApplyDetailOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matOutDetail = matOutMainService.queryMatApplyDetailOut(mapVo);

		return JSONObject.parseObject(matOutDetail);
	}

	/**
	 * @Description 根据科室申领查询出库单数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutByOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutByOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			Map<String, Object> matOutMain = matOutMainService.queryOutMainByAppCollect(mapVo);
			if(matOutMain.get("error") != null){
				retMap.putAll(matOutMain);
				return retMap;
			}
			//根据参数is_back判断是科室领用还是科室退库
			if(mapVo.get("is_back") != null && !"1".equals(mapVo.get("is_back"))){
				matOutMain.put("bus_type_code", "21");//科室退库
			}else{
				matOutMain.put("bus_type_code", "3");//科室领用
			}
			if(matOutMain.get("url") == null){
				matOutMain.put("url", "../applycheck/addMatOutMainByApp");
			}
			//出库日期
			if(matOutMain.get("out_date") != null && !"".equals(matOutMain.get("out_date"))){
				matOutMain.put("out_date", DateUtil.dateToString((Date)matOutMain.get("out_date"), "yyyy-MM-dd"));
			}
			
			String matOutDetail = matOutMainService.queryOutDetailByAppCollect(mapVo);
			if(matOutDetail.indexOf("error") > 0){
				retMap.putAll(JSONObject.parseObject(matOutDetail));
				return retMap;
			}
			
			retMap.put("state", "true");
			retMap.put("matOutMain", matOutMain);
			retMap.put("matOutDetail", matOutDetail);
		} catch (Exception e) {
			
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}

	/**
	 * @Description 
	 * 申领单汇总生成出库单跳转添加页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainAddByOther", method = RequestMethod.GET)
	public String matOutMainAddByOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04043", MyConfig.getSysPara("04043"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "/hrp/mat/storage/out/outlibrary/matOutMainAddByOther";
	}
	
	/**
	 * @Description 选择材料页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainChoiceInvPage", method = RequestMethod.GET)
	public String matOutMainChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/storage/out/outlibrary/matOutMainChoiceInv";
	}

	/**
	 * @Description 选择材料查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatInvBatchList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvBatchList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matOutDetail = matOutMainService.queryMatInvBatchList(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);
	}
	
	/**
	 * @Description 选择材料返回List
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryOutInvListByChoiceInv", method = RequestMethod.POST)
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
		
		String matOutDetail;
		try {
			matOutDetail = matOutMainService.queryOutInvListByChoiceInv(listVo);
		} catch (Exception e) {
			matOutDetail = e.getMessage();
		}

		return JSONObject.parseObject(matOutDetail);
	}
	/**
	 * @Description 
	 * 入出库查询-材料出库存数量明细查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/invOutDetailPage", method = RequestMethod.GET)
	public String invOutDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		String invOutDetail = matOutMainService.queryInvOutDetail(mapVo);
		
		
		mode.addAttribute("invOutDetail", JSONObject.parseObject(invOutDetail));

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/out/outlibrary/invOutDetail";
	}
	
	/**
	 * @Description 科室需求计划导入
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainDeptRequireImpPage", method = RequestMethod.GET)
	public String matOutMainDeptRequireImpPage(Model mode) throws Exception {

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/out/outlibrary/matOutMainDeptRequireImp";
	}

	/**
	 * @Description 查询科室申领主表数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatDeptRequireData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequireData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date").toString())){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date").toString())){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		String matOutDetail = matOutMainService.queryMatDeptRequireData(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);
	}
	
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatDeptRequireDataDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequireDataDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		
		String matOutDetail = matOutMainService.queryMatDeptRequireDataDetail(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);
	}


	/**
	 * @Description 根据科室需求计划查询出库单数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatOutByDeptReq", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutByDeptReq(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			Map<String, Object> matOutMain = matOutMainService.queryOutMainByDeptReqCollect(mapVo);
			if(matOutMain.get("error") != null){
				retMap.putAll(matOutMain);
				return retMap;
			}
			//根据参数is_back判断是科室领用还是科室退库
			if(mapVo.get("is_back") != null && !"1".equals(mapVo.get("is_back"))){
				matOutMain.put("bus_type_code", "21");//科室退库
			}else{
				matOutMain.put("bus_type_code", "3");//科室领用
			}
			if(matOutMain.get("url") == null){
				matOutMain.put("url", "addMatOutMainByDeptReq");
			}
			//出库日期
			if(matOutMain.get("out_date") != null && !"".equals(matOutMain.get("out_date"))){
				matOutMain.put("out_date", DateUtil.dateToString((Date)matOutMain.get("out_date"), "yyyy-MM-dd"));
			}
			
			String matOutDetail = matOutMainService.queryOutDetailByDeptReqCollect(mapVo);
			if(matOutDetail.indexOf("error") > 0){
				retMap.putAll(JSONObject.parseObject(matOutDetail));
				return retMap;
			}
			
			retMap.put("state", "true");
			retMap.put("matOutMain", matOutMain);
			retMap.put("matOutDetail", matOutDetail);
		} catch (Exception e) {
			
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
	
	/**
	 * 科室需求计划生成出库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainAddByDeptReq", method = RequestMethod.GET)
	public String matOutMainAddByDeptReq(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		mode.addAttribute("mainMap",mapVo);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04043", MyConfig.getSysPara("04043"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "/hrp/mat/storage/out/outlibrary/matOutMainAddByDeptReq";
	}
	/**
	 * 组装明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatDeptRequireDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequireDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String matJson;
		try {
			matJson = matOutMainService.queryOutDetailByDeptReqCollect(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * 保存出库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/addMatOutMainByDeptReq", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatOutMainByDeptReq(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		if(mapVo.get("out_date") == null || "".equals(mapVo.get("out_date").toString())){
			return JSONObject.parseObject("{\"error\":\"编制日期不能为空！\"}");
		}
		
		//制单人
		mapVo.put("maker", SessionManager.getUserId());
		//处理日期和期间
		String date = (String) mapVo.get("out_date");
		mapVo.put("out_date", DateUtil.stringToDate(date, "yyyy-MM-dd"));
		mapVo.put("year", date.substring(0, 4));
		mapVo.put("month", date.substring(5, 7));
		mapVo.put("day", date.substring(8, 10));  //用于生成单号
		mapVo.put("out_date", DateUtil.dateToString((Date)mapVo.get("out_date"), "yyyy-MM-dd"));
		
		//状态：新建
		mapVo.put("state", 1); 
		String matJson;
		try {
			matJson =  matOutMainService.addOutByDeptReq(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	//
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/updateMatDeptReqCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDeptReqCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("req_id", ids[3]);
			map.put("req_detail_id", ids[4]);
			listVo.add(map);
		}

		String matJson;
		try {
			matJson = matOutMainService.updateMatDeptReqCloseInv(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	/**
	 * 科室需求计划导入 查看主表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/matOutMainDeptReqUpdatePage", method = RequestMethod.GET)
	public String matOutMainDeptReqUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matDeptMain =  matRequirePlanService.queryByCode(mapVo);
		
		mode.addAttribute("matDeptMain", matDeptMain);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04032", MyConfig.getSysPara("04032"));
		
		return "hrp/mat/storage/out/outlibrary/matOutMainDeptReqUpdate";
	}
	/**
	 * 科室需求计划导入 查看明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outlibrary/queryMatDeptReqDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptReqDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		if(mapVo.get("acc_year") == null){
			mapVo.put("acc_year", SessionManager.getAcctYear());   
		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());   
		}
	    
		String matRequireMainJson = matRequirePlanService.queryDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matRequireMainJson); 
		
		//return "hrp/mat/require/plan/matDeptRequriedPlanUpdate";
	}
}
