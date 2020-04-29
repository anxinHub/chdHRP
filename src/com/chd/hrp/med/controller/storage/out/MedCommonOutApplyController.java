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
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedInMain;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.out.MedCommonOutApplyService;

/**
 * 
 * @Description:  出库--科室申请
 * @Table: MED_APPLY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedCommonOutApplyController extends BaseController {

	private static Logger logger = Logger.getLogger(MedCommonOutApplyController.class);

	// 引入Service服务
	@Resource(name = "medCommonOutApplyService")
	private final MedCommonOutApplyService medCommonOutApplyService = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/mainPage", method = RequestMethod.GET)
	public String MedCommonOutApplyMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005")); 
		mode.addAttribute("p08018", MyConfig.getSysPara("08018")); 

		return "hrp/med/storage/out/apply/main";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/apply/addPage", method = RequestMethod.GET)
	public String medCommonOutApplyAddPage(Model mode) throws Exception {
       
		
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_code", SessionManager.getUserCode());
		mode.addAttribute("user_name", SessionManager.getUserName());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("user_id", SessionManager.getUserId());
		mode.addAttribute("user_msg", medCommonService.getLoginUserMsg(map));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08016", MyConfig.getSysPara("08016"));
		mode.addAttribute("p08043", MyConfig.getSysPara("08043"));
		
		return "hrp/med/storage/out/apply/add";
	}

	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/queryMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonOutApply(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		//只能查询未发送的单据
		//mapVo.put("app_state", "0");
		//转换日期格式
		if(mapVo.get("begin_app_date") != null && !"".equals(mapVo.get("begin_app_date"))){
			mapVo.put("begin_app_date", DateUtil.stringToDate(mapVo.get("begin_app_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(mapVo.get("end_app_date") != null && !"".equals(mapVo.get("end_app_date"))){
			mapVo.put("end_app_date", DateUtil.stringToDate(mapVo.get("end_app_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		//查询时  登录用户只能查询其有数据权限（部门权限、仓库权限）的 相关数据
		if(mapVo.get("dept_id") == "" || mapVo.get("dept_id") == null){
			
			mapVo.put("dept_id", "00"); //当 dept_id == 00 时  根据用户 部门权限 限定 申请科室
		}
		
		if(mapVo.get("store_id") == "" || mapVo.get("store_id") == null){
			
			mapVo.put("store_id", "00"); //当 store_id == 00 时  根据用户 仓库权限 限定 响应仓库
		}
		
		
		String medIn ;
		if(mapVo.get("show_detail").equals("1")){
			medIn = medCommonOutApplyService.queryApplyDetails(getPage(mapVo));
		}else{
			medIn = medCommonOutApplyService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/queryMedCommonOutApplyDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonOutApplyDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		String detail = medCommonOutApplyService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/queryMedCommonOutApplyById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonOutApplyById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return medCommonOutApplyService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/addMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedCommonOutApply(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//制单人
		mapVo.put("maker", SessionManager.getUserId());
		//编制日期
		if(mapVo.get("app_date") == null || "".equals(mapVo.get("app_date"))){
			return JSONObject.parseObject("{\"error\":\"编制日期不能为空\",\"state\":\"false\"}");
		}
		
		//截取期间
		String[] date = mapVo.get("app_date").toString().split("-");
		mapVo.put("year", date[0]);
		mapVo.put("month", date[1]);
		
		//转换日期格式
		if(mapVo.get("app_date") != null && !"".equals(mapVo.get("app_date"))){
			mapVo.put("app_date", DateUtil.stringToDate(mapVo.get("app_date").toString(), "yyyy-MM-dd HH:mm:ss"));
		}
		//判断摘要是否为空   gaopei
		if(mapVo.get("brief") != null && !"".equals(mapVo.get("brief"))){
			mapVo.put("brief", mapVo.get("brief"));
		}else 
		{
			mapVo.put("brief", " ");
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/apply/updatePage", method = RequestMethod.GET)
	public String medCommonOutApplyUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> medApplyMain = medCommonOutApplyService.queryByCode(mapVo);
		
		//申请日期
		if(medApplyMain.get("app_date") != null && !"".equals(medApplyMain.get("app_date"))){
			medApplyMain.put("app_date", DateUtil.dateToString((Date)medApplyMain.get("app_date"), "yyyy-MM-dd HH:mm:ss"));
		}
		//审核日期
		if(medApplyMain.get("check_date") != null && !"".equals(medApplyMain.get("check_date"))){
			medApplyMain.put("check_date", DateUtil.dateToString((Date)medApplyMain.get("check_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("medApplyMain", medApplyMain);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08043", MyConfig.getSysPara("08043"));
		
		return "hrp/med/storage/out/apply/update";
	}

	/**
	 * @Description 更新数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/updateMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedCommonOutApply(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		//申请日期
		if(mapVo.get("app_date") != null && !"".equals(mapVo.get("app_date"))){
			mapVo.put("app_date", DateUtil.stringToDate(mapVo.get("app_date").toString(), "yyyy-MM-dd HH:mm:ss"));
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 删除数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/deleteMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedCommonOutApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyService.deleteBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 申请单发送
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/sendMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendMedCommonOutApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String user_id = SessionManager.getUserId();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_id", ids[3]);
			mapVo.put("state", 3);
			mapVo.put("sender", user_id);
			mapVo.put("send_date", date);
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyService.updateSendMedCommonOutApplyBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	/**
	 * @Description取消发送
	 * @param mapVo
	 * @param mode
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/backsendMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backsendMedCommonOutApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String user_id = SessionManager.getUserId();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_id", ids[3]);
			mapVo.put("state", 1);
			mapVo.put("sender", user_id);
			mapVo.put("send_date", date);
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyService.updatebackSendMedCommonOutApplyBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 申请单审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/auditMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMedCommonOutApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String user_id = SessionManager.getUserId();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_id", ids[3]);
			mapVo.put("state", 2);
			mapVo.put("checker", user_id);
			mapVo.put("check_date", date);
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyService.auditMedCommonOutApplyBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 申请单消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/unAuditMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMedCommonOutApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_id", ids[3]);
			mapVo.put("state", 1);
			mapVo.put("checker", "");
			mapVo.put("check_date", "");
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyService.unAuditMedCommonOutApplyBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 申请单作废
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/cancelMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelMedCommonOutApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String user_id = SessionManager.getUserId();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_id", ids[3]);
			mapVo.put("state", 0);
			mapVo.put("checker", user_id);
			mapVo.put("check_date", date);
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyService.failedMedCommonOutApplyBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 配套导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/apply/matchImpPage", method = RequestMethod.GET)
	public String medCommonOutApplymatchImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_text", mapVo.get("dept_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/out/apply/matchImp";
	}
	/**
	 * @Description 配套查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/queryMedCommonOutApplyMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonOutApplyMatch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medIn = medCommonOutApplyService.queryMatch(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 导入跳转页面  出库--科室申请
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/importPage", method = RequestMethod.GET)
	public String medCommonOutApplyImportPage(Model mode) throws Exception {

		return "hrp/med/storage/out/apply/Import";

	}

	/**
	 * @Description 下载导入模版  出库--科室申请
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/storage/out/apply/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate",
				"出库--科室申请.xls");

		return null;
	}

	/**
	 * @Description 导入数据 出库--科室申请@param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/readFiles", method = RequestMethod.POST)
	public String readMedCommonOutApplyFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<MedInMain> list_err = new ArrayList<MedInMain>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

		} catch (DataAccessException e) {

			MedInMain data_exc = new MedInMain();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 出库--科室申请
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/addBatchMedCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedCommonOutApply(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<MedInMain> list_err = new ArrayList<MedInMain>();

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

		} catch (DataAccessException e) {
			MedInMain data_exc = new MedInMain();
			list_err.add(data_exc);
			return JSONObject
					.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));
		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
	}
	
	/**
	 * 新增、修改操作 中途改变响应仓库时 查询询之前表格中已添加的药品 是否存在于当前选择的响应仓库中
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/queryStoreExistInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreExistInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("inv_id", ids[0]);
			mapVo.put("inv_name",ids[1]);
			mapVo.put("store_id",ids[2]);
			mapVo.put("inv_code",ids[3]);
			listVo.add(mapVo);
		}
		
		String str = medCommonOutApplyService.queryStoreExistInv(listVo);
		
		return JSONObject.parseObject(str);
	}
	
	/**
	 * @Description 选择药品页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/apply/medCommonOutApplyChoiceInvPage", method = RequestMethod.GET)
	public String medCommonOutApplyChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/out/apply/medCommonOutApplyChoiceInv";
	}
	
	//组装仓库需求计划
	@RequestMapping(value = "/hrp/med/storage/out/apply/queryStoreInvData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreInvData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
					
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
					
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
					
		String detailData = medCommonOutApplyService.queryStoreInvData(mapVo);
					
		return JSONObject.parseObject(detailData);
					
	}
}
