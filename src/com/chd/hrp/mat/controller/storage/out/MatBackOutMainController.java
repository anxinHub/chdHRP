/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.out;
 
import java.io.IOException; 
import java.util.*;

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
import com.chd.hrp.mat.entity.MatOutDetail;
import com.chd.hrp.mat.entity.MatOutMain;
import com.chd.hrp.mat.service.storage.out.MatOutBackMainService; 
import com.sun.xml.internal.stream.Entity;

/**
 * @Description: MAT_OUT_MAIN
 * @Table: MAT_OUT_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatBackOutMainController extends BaseController {

	private static Logger logger = Logger.getLogger(MatBackOutMainController.class);

	// 引入Service服务
	@Resource(name = "matOutBackMainService")
	private final MatOutBackMainService matOutBackMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/matOutBackMainMainPage", method = RequestMethod.GET)
	public String matOutBackMainMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04015", MyConfig.getSysPara("04015"));
		mode.addAttribute("p04018", MyConfig.getSysPara("04018"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		
		return "hrp/mat/storage/out/outback/matOutBackMainMain";

	}
 

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/matOutBackMainAddPage", method = RequestMethod.GET)
	public String matOutBackMainAddPage(Model mode) throws Exception {
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04043", MyConfig.getSysPara("04043"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/storage/out/outback/matOutBackMainAdd";
	}

	/**
	 * @Description 添加数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/addMatOutBackMain", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatOutBackMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			matJson = matOutBackMainService.add(mapVo);
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
	@RequestMapping(value = "/hrp/mat/storage/out/outback/matOutBackMainUpdatePage", method = RequestMethod.GET)
	public String matOutBackMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

		Map<String, Object> matOutMain = matOutBackMainService.queryMatOutBackMainByCode(mapVo);

		if (matOutMain == null) {

			return "hrp/mat/storage/out/outback/matOutMainAdd";

		}

		if (matOutMain.get("out_date") != null && !"".equals(matOutMain.get("out_date").toString())) {
			matOutMain.put("out_date", DateUtil.dateToString((Date) matOutMain.get("out_date"), "yyyy-MM-dd"));
		}

		if (matOutMain.get("app_date") != null && !"".equals(matOutMain.get("app_date").toString())) {
			matOutMain.put("app_date", DateUtil.dateToString((Date) matOutMain.get("app_date"), "yyyy-MM-dd HH:mm:ss"));
		}
		mode.addAttribute("matOutMain", matOutMain);

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
		
		return "hrp/mat/storage/out/outback/matOutBackMainUpdate";
	}

	/**
	 * @Description 加载明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/queryMatOutDetailById", method = RequestMethod.POST)
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

		String matOutDetail = matOutBackMainService.queryMatOutBackDetailByOutId(mapVo);

		return JSONObject.parseObject(matOutDetail);
	}

	/**
	 * @Description 加载明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/queryMatOutDetailByCode", method = RequestMethod.POST)
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

		String matOutDetail = matOutBackMainService.queryMatOutBackDetailByCode(mapVo);

		return JSONObject.parseObject(matOutDetail);
	}

	/**
	 * @Description 更新数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/updateMatOutBackMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatOutBackMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			matJson = matOutBackMainService.update(mapVo);
		}
		catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 更新数据 MAT_OUT_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/addOrUpdateMatOutMain", method = RequestMethod.POST)
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
				matJson = matOutBackMainService.addOrUpdate(detailVo);
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
	@RequestMapping(value = "/hrp/mat/storage/out/outback/deleteMatOutBackMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatOutBackMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			matJson = matOutBackMainService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/storage/out/outback/auditMatOutBackMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatOutBackMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			matJson = matOutBackMainService.auditMatOutBackMain(listVo);
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
	@RequestMapping(value = "/hrp/mat/storage/out/outback/confirmOutMatOutBackMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmOutMatOutBackMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String date =DateUtil.getSysDate();//获取服务器当前日期

		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		String user_id = SessionManager.getUserId();

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			

			if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString())){
				 date = ids[4];
			}

			
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			
			mapVo.put("year", year);
			mapVo.put("month", month);
			mapVo.put("out_id", ids[3]);
			mapVo.put("state", 3);
			mapVo.put("confirmer", user_id);
			mapVo.put("confirm_date", date);

			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matOutBackMainService.confirmOutMatOutBackMain(listVo);
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
	@RequestMapping(value = "/hrp/mat/storage/out/outback/unAuditMatOutBackMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatOutBackMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			matJson = matOutBackMainService.unAuditMatOutBackMain(listVo);
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
	@RequestMapping(value = "/hrp/mat/storage/out/outback/copyMatOutBackMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMatOutBackMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			matJson = matOutBackMainService.copyMatOutBackMain(listVo);
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
	@RequestMapping(value = "/hrp/mat/storage/out/outback/queryMatOutBackMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutBackMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			matOutMain =  matOutBackMainService.queryOutBackDetails(getPage(mapVo));
		}else{
			matOutMain =  matOutBackMainService.queryMatOutBackMain(getPage(mapVo));
		}
		
		return JSONObject.parseObject(matOutMain);

	}
 
	/**
	 * @Description 获取上一张出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/queryMatOutBackBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutBackBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matJson = matOutBackMainService.queryMatOutBackMainBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 获取下一张出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/queryMatOutBackNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutBackNextNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matJson = matOutBackMainService.queryMatOutBackMainBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 导入跳转页面 MAT_OUT_MAIN
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/outback/matOutMainImportPage", method = RequestMethod.GET)
	public String matOutMainImportPage(Model mode) throws Exception {

		return "hrp/mat/storage/out/outback/matOutMainImport";

	}

	/**
	 * @Description 下载导入模版 MAT_OUT_MAIN
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/storage/out/outback/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate", "MAT_OUT_MAIN.xls");

		return null;
	}

	

	/**
	 * @Description 入库模板打印（包含主从表）
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/hrp/mat/storage/out/outback/queryMatOutByPrintTemlate", method = RequestMethod.POST)
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
			reJson = matOutBackMainService.queryMatOutByPrintTemlate(mapVo);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(reJson);
	}
*/ 
}
