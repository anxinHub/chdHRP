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
import com.chd.hrp.med.entity.MedInMain;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.out.MedCommonOutApplyCheckService;

/**
 * 
 * @Description:  出库--科室申请
 * @Table: MED_APPLY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedCommonOutApplyCheckController extends BaseController {

	private static Logger logger = Logger.getLogger(MedCommonOutApplyCheckController.class);

	// 引入Service服务
	@Resource(name = "medCommonOutApplyCheckService")
	private final MedCommonOutApplyCheckService medCommonOutApplyCheckService = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/mainPage", method = RequestMethod.GET)
	public String MedCommonOutApplyMainPage(Model mode) throws Exception {

		return "hrp/med/storage/out/applycheck/main";
	}

	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/queryMedCommonOutApplyCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonOutApplyCheck(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		//只能查看已发送的单据
		if (mapVo.get("app_state") == null) {
			mapVo.put("app_state", 1);
		}
		//转换日期格式 
		if(mapVo.get("begin_app_date") != null && !"".equals(mapVo.get("begin_app_date"))){
			mapVo.put("begin_app_date", DateUtil.stringToDate(mapVo.get("begin_app_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(mapVo.get("end_app_date") != null && !"".equals(mapVo.get("end_app_date"))){
			mapVo.put("end_app_date", DateUtil.stringToDate(mapVo.get("end_app_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		} 
		if(mapVo.get("begin_send_date") != null && !"".equals(mapVo.get("begin_send_date"))){
			mapVo.put("begin_send_date", DateUtil.stringToDate(mapVo.get("begin_send_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(mapVo.get("end_send_date") != null && !"".equals(mapVo.get("end_send_date"))){
			mapVo.put("end_send_date", DateUtil.stringToDate(mapVo.get("end_send_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		//查询时  登录用户只能查询其有数据权限（部门权限、仓库权限）的 相关数据
		if(mapVo.get("dept_id") == "" || mapVo.get("dept_id") == null){
			
			mapVo.put("dept_id", "00"); //当 dept_id == 00 时  根据用户 部门权限 限定 申请科室
		}
		
		if(mapVo.get("store_id") == "" || mapVo.get("store_id") == null){
			
			mapVo.put("store_id", "00"); //当 store_id == 00 时  根据用户 仓库权限 限定 响应仓库
		}
		String medIn = medCommonOutApplyCheckService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/queryMedCommonOutApplyCheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonOutApplyCheckDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = medCommonOutApplyCheckService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/queryMedCommonOutApplyCheckById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonOutApplyCheckById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return medCommonOutApplyCheckService.queryByCode(mapVo);
	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/updatePage", method = RequestMethod.GET)
	public String medCommonOutApplyCheckUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> medApplyMain = medCommonOutApplyCheckService.queryByCode(mapVo);
		
		//申请日期
		if(medApplyMain.get("app_date") != null && !"".equals(medApplyMain.get("app_date"))){
			medApplyMain.put("app_date", DateUtil.dateToString((Date)medApplyMain.get("app_date"), "yyyy-MM-dd HH:mm:ss"));
		}
		//制单日期
		if(medApplyMain.get("make_date") != null && !"".equals(medApplyMain.get("make_date"))){
			medApplyMain.put("make_date", DateUtil.dateToString((Date)medApplyMain.get("make_date"), "yyyy-MM-dd"));
		}
		//审核日期
		if(medApplyMain.get("check_date") != null && !"".equals(medApplyMain.get("check_date"))){
			medApplyMain.put("check_date", DateUtil.dateToString((Date)medApplyMain.get("check_date"), "yyyy-MM-dd"));
		}
		//响应日期
		if(medApplyMain.get("res_date") != null && !"".equals(medApplyMain.get("res_date"))){
			medApplyMain.put("res_date", DateUtil.dateToString((Date)medApplyMain.get("res_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("medApplyMain", medApplyMain);
		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/out/applycheck/update";
	}
	
	/**
	 * @Description 申请退回
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/backMedCommonOutApplyCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backMedCommonOutApplyCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String back_reason = mapVo.get("back_reason").toString();
		for ( String id: String.valueOf(mapVo.get("ids")).split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("apply_id", ids[3]);
			map.put("state", 1);
			map.put("back_reason", back_reason);
			listVo.add(map);
		}

		String medJson;
		try {
			medJson = medCommonOutApplyCheckService.updateBackMedCommonOutApplyCheckBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 药品关闭
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/updateMedCommonOutApplyCheckCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedCommonOutApplyCheckCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("apply_id", ids[3]);
			map.put("detail_id", ids[4]);
			listVo.add(map);
		}

		String medJson;
		try {
			medJson = medCommonOutApplyCheckService.updateMedCommonOutApplyCheckCloseInv(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 批量生成出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addOutMedCommonOutApplyCheckBatch", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addOutMedCommonOutApplyCheckBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("apply_id", ids[3]);
			map.put("user_id", SessionManager.getUserId());
			map.put("show_history", MyConfig.getSysPara("03001"));
			listVo.add(map);
		}

		String medJson;
		try {
			medJson = medCommonOutApplyCheckService.addOutBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 汇总生成出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addOutMedCommonOutApplyCheckCollect", method = RequestMethod.GET)
	public String addOutMedCommonOutApplyCheckCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: mapVo.get("para").toString().split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("apply_id", ids[3]);
			//用于查询货位
			map.put("store_id", ids[4]);
			listVo.add(map);
		}
		
		
		Map<String, Object> medOutMain = medCommonOutApplyCheckService.queryOutMainByCollect(listVo); 
		
		//转换主表业务类型
		medOutMain.put("bus_type_code", "3");
		//申请日期
		if(medOutMain.get("out_date") != null && !"".equals(medOutMain.get("out_date"))){
			medOutMain.put("out_date", DateUtil.dateToString((Date)medOutMain.get("out_date"), "yyyy-MM-dd"));
		} 
		 
		//页面形式--添加or修改
		medOutMain.put("is_add", 1);
		
		String medOutDetail;
		try {
			medOutDetail = medCommonOutApplyCheckService.queryOutDetailByCollect(listVo);
		} catch (Exception e) {
			medOutDetail = e.getMessage();
		}
		mode.addAttribute("medOutMain", medOutMain);
		mode.addAttribute("medOutDetail", medOutDetail);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "/hrp/med/storage/out/applycheck/addCollectOut";
	}

	/**
	 * @Description 打开出库单添加页面并自动填充数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/openAddOut", method = RequestMethod.GET)
	public String openAddOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("medOutMain", mapVo.get("medOutMain"));
		mode.addAttribute("medOutDetail", mapVo.get("medOutDetail"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "/hrp/med/storage/out/applycheck/addOut";
	}

	/**
	 * @Description 
	 * 验证即时库存是否充足
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/checkMedCommonOutApplyCheckForAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkMedCommonOutApplyCheckForAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String medJson = "";
		if("out".equals(mapVo.get("type"))){
			//生成出库单
			medJson = medCommonOutApplyCheckService.queryOutDetail(mapVo);
		}else if("affiOut".equals(mapVo.get("type"))){
			//生成代销出库单
			medJson = medCommonOutApplyCheckService.queryAffiOutDetail(mapVo);
		}else if("tran".equals(mapVo.get("type"))){
			//生成调拨单
			medJson = medCommonOutApplyCheckService.queryTranDetail(mapVo);
		}else if("affiTran".equals(mapVo.get("type"))){
			//生成代销调拨单
			medJson = medCommonOutApplyCheckService.queryAffiTranDetail(mapVo);
		}else{
			medJson = "{\"error\":\"未知业务请联系管理员\"}";
		}
		//判断是否有错误输出
		if(medJson.indexOf("error")!=-1){
			return JSONObject.parseObject(medJson);
		}else{
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
	}

	/**
	 * @Description 
	 * 生成出库单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addOutPage", method = RequestMethod.GET)
	public String medCommonOutApplyCheckAddOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> medOutMain = medCommonOutApplyCheckService.queryOutMain(mapVo);
		
		String medOutDetail = medCommonOutApplyCheckService.queryOutDetail(mapVo);
		//判断是否有错误输出
		if(medOutDetail.indexOf("error")!=-1){
			
			return medOutDetail;
		}
		
		//转换主表业务类型
		medOutMain.put("bus_type_code", "3");
		//申请日期
		if(medOutMain.get("out_date") != null && !"".equals(medOutMain.get("out_date"))){
			medOutMain.put("out_date", DateUtil.dateToString((Date)medOutMain.get("out_date"), "yyyy-MM-dd"));
		}
		//申请日期
	   if(medOutMain.get("app_date") != null && !"".equals(medOutMain.get("app_date"))){
			 medOutMain.put("app_date", DateUtil.dateToString((Date)medOutMain.get("app_date"), "yyyy-MM-dd HH:mm:ss "));
		 }
		//页面形式--添加or修改
		medOutMain.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("medOutMain", medOutMain);
		mode.addAttribute("medOutDetail", medOutDetail);
		return "/hrp/med/storage/out/applycheck/addOut";
	}


	/**
	 * @Description 保存出库单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addMedOutMainByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedOutMainByApp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		//状态：新建
		mapVo.put("state", 1); 
		String medJson;
		try {
			medJson =  medCommonOutApplyCheckService.addOut(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}

	/**
	 * @Description 
	 * 生成代销出库单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addAffiOutPage", method = RequestMethod.GET)
	public String medCommonOutApplyCheckAddAffiOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> medAffiOut = medCommonOutApplyCheckService.queryOutMain(mapVo);
		
		String medAffiOutDetail = medCommonOutApplyCheckService.queryAffiOutDetail(mapVo);
		//判断是否有错误输出
		if(medAffiOutDetail.indexOf("error")!=-1){
			return medAffiOutDetail;
		}
		
		//转换主表业务类型
		medAffiOut.put("bus_type_code", "28");
		//申请日期
		if(medAffiOut.get("out_date") != null && !"".equals(medAffiOut.get("out_date"))){
			medAffiOut.put("out_date", DateUtil.dateToString((Date)medAffiOut.get("out_date"), "yyyy-MM-dd"));
		}
		//页面形式--添加or修改
		medAffiOut.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("medAffiOut", medAffiOut);
		mode.addAttribute("medAffiOutDetail", medAffiOutDetail);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "/hrp/med/storage/out/applycheck/addAffiOut";
	}
	
	/**
	 * @Description 保存代销出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addMedAffiOutByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedAffiOutByApp(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
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
		//状态：新建
		mapVo.put("state", 1);

		String medJson;
		try {
			medJson = medCommonOutApplyCheckService.addAffiOut(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 查询科室管理的仓库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/existsMedCommonOutApplyCheckStoreManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> existsMedCommonOutApplyCheckStoreManage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String medJson = medCommonOutApplyCheckService.queryStoreByDept(mapVo);
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 生成调拨单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addTranPage", method = RequestMethod.GET)
	public String medCommonOutApplyCheckAddTranPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		//查询申请科室是否是仓库的管理科室（即是否存在二级库科室库）
		String storeMsg = medCommonOutApplyCheckService.queryStoreByDept(mapVo);
		//判断是否有错误输出
		if(storeMsg.indexOf("error")!=-1){
			return storeMsg;
		}

		Map<String, Object> medTranMain = medCommonOutApplyCheckService.queryTranMain(mapVo);
		
		String medTranDetail = medCommonOutApplyCheckService.queryTranDetail(mapVo);
		//判断是否有错误输出
		if(medTranDetail.indexOf("error")!=-1){
			return medTranDetail;
		}
		
		//调拨日期
		if(medTranMain.get("tran_date") != null && !"".equals(medTranMain.get("tran_date"))){
			medTranMain.put("tran_date", DateUtil.dateToString((Date)medTranMain.get("tran_date"), "yyyy-MM-dd"));
		}
		//页面形式--添加or修改
		medTranMain.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("medTranMain", medTranMain);
		mode.addAttribute("medTranDetail", medTranDetail);
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "/hrp/med/storage/out/applycheck/addTran";
	}

	/**
	 * @Description 保存调拨单据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addMedTranMainByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedTranMainByApp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("tran_date") == null || "".equals(mapVo.get("tran_date").toString())){
			return JSONObject.parseObject("{\"error\":\"编制日期不能为空！\"}");
		}
		
		//制单人
		mapVo.put("maker", SessionManager.getUserId());
		//处理日期和期间
		String date = (String) mapVo.get("tran_date");
		mapVo.put("tran_date", DateUtil.stringToDate(date, "yyyy-MM-dd"));
		mapVo.put("year", date.substring(0, 4));
		mapVo.put("month", date.substring(5, 7));
		mapVo.put("day", date.substring(8, 10));  //用于生成单号
		//状态：新建
		mapVo.put("state", 1);
		if (mapVo.get("in_store_id")!=null) {
			mapVo.put("store_id", mapVo.get("in_store_id"));
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyCheckService.addTran(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
		
	}

	/**
	 * @Description 
	 * 生成代销调拨单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addAffiTranPage", method = RequestMethod.GET)
	public String medCommonOutApplyCheckAddAffiTranPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		//查询申请科室是否是仓库的管理科室（即是否存在二级库科室库）
		String storeMsg = medCommonOutApplyCheckService.queryStoreByDept(mapVo);
		//判断是否有错误输出
		if(storeMsg.indexOf("error")!=-1){
			return storeMsg;
		}

		Map<String, Object> medAffiTran = medCommonOutApplyCheckService.queryTranMain(mapVo);
		
		String medAffiTranDetail = medCommonOutApplyCheckService.queryAffiTranDetail(mapVo);
		//判断是否有错误输出
		if(medAffiTranDetail.indexOf("error")!=-1){
			return medAffiTranDetail;
		}
		
		//调拨日期
		if(medAffiTran.get("tran_date") != null && !"".equals(medAffiTran.get("tran_date"))){
			medAffiTran.put("tran_date", DateUtil.dateToString((Date)medAffiTran.get("tran_date"), "yyyy-MM-dd"));
		}
		//页面形式--添加or修改
		medAffiTran.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("medAffiTran", medAffiTran);
		mode.addAttribute("medAffiTranDetail", medAffiTranDetail);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "/hrp/med/storage/out/applycheck/addAffiTran";
	}

	/**
	 * @Description 保存代销调拨单
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addMedAffiTranMainByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedAffiTranMainByApp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acct_year") == null){
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		if(mapVo.get("tran_date") == null || "".equals(mapVo.get("tran_date").toString())){
			return JSONObject.parseObject("{\"error\":\"编制日期不能为空！\"}");
		}
		
		//制单人
		mapVo.put("maker", SessionManager.getUserId());
		//处理日期和期间
		String date = (String) mapVo.get("tran_date");
		mapVo.put("tran_date", DateUtil.stringToDate(date, "yyyy-MM-dd"));
		mapVo.put("year", date.substring(0, 4));
		mapVo.put("month", date.substring(5, 7));
		mapVo.put("day", date.substring(8, 10));  //用于生成单号
		//状态：新建
		mapVo.put("state", 1);

		String medJson;
		try {
			medJson = medCommonOutApplyCheckService.addAffiTran(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}	
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 生成科室需求计划跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addReqPage", method = RequestMethod.GET)
	public String medCommonOutApplyCheckAddReqPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> medReqMain = medCommonOutApplyCheckService.queryReqMain(mapVo);
		
		String medReqDetail = medCommonOutApplyCheckService.queryReqDetail(mapVo);
		//判断是否有错误输出
		if(medReqDetail.indexOf("error")!=-1){
			return medReqDetail;
		}
		
		//编制日期
		if(medReqMain.get("make_date") != null && !"".equals(medReqMain.get("make_date"))){
			medReqMain.put("make_date", DateUtil.dateToString((Date)medReqMain.get("make_date"), "yyyy-MM-dd"));
		}
		//需求日期
		if(medReqMain.get("rdate") != null && !"".equals(medReqMain.get("rdate"))){
			medReqMain.put("rdate", DateUtil.dateToString((Date)medReqMain.get("rdate"), "yyyy-MM-dd"));
		}
		//页面形式--添加or修改
		medReqMain.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("medReqMain", medReqMain);
		mode.addAttribute("medReqDetail", medReqDetail);
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "/hrp/med/storage/out/applycheck/addReq";
	}
	/**
	 * @Description 保存科室需求计划单据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addMedRequriedMainByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedRequriedMainByApp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if(mapVo.get("make_date") == null || "".equals(mapVo.get("make_date").toString())){
			return JSONObject.parseObject("{\"error\":\"编制日期不能为空\",\"state\":\"false\"}");
		}
		
		//制单人
		mapVo.put("maker", SessionManager.getUserId());
		//处理日期和期间
		String date = (String) mapVo.get("make_date");
		mapVo.put("year", date.substring(0, 4));
		mapVo.put("month", date.substring(5, 7));
		mapVo.put("day", date.substring(8, 10));  //用于生成单号
		//状态：提交
		mapVo.put("state", 2);

		String medJson;
		try {
			medJson = medCommonOutApplyCheckService.addReq(mapVo);
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
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/relaPage", method = RequestMethod.GET)
	public String medCommonOutApplyCheckRelaPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mode.addAttribute("medApplyRela", mapVo);
		
		return "hrp/med/storage/out/applycheck/rela";
	}

	/**
	 * @Description 查询数据  对应单据列表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/queryMedApplyRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedApplyRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medIn = medCommonOutApplyCheckService.queryMedApplyRela(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}

	/**
	 * @Description 删除数据  对应单据列表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/deleteMedApplyRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedApplyRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_id", ids[3]);
			mapVo.put("rela_type", ids[4]);
			mapVo.put("rela_id", ids[5]);
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medCommonOutApplyCheckService.deleteMedApplyRela(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 查看对应单据跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/relaOutPage", method = RequestMethod.GET)
	public String medCommonOutApplyCheckRelaOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", mapVo.get("group_id"));
		entityMap.put("hos_id", mapVo.get("hos_id"));
		entityMap.put("copy_code", mapVo.get("copy_code"));
		
		Map<String, Object> mainMap;
		
		if("1".equals(String.valueOf(mapVo.get("rela_type")))){
			entityMap.put("out_id", mapVo.get("rela_id"));
			entityMap.put("store_id", mapVo.get("store_id"));
			
			mainMap = medCommonOutApplyCheckService.queryRelaOutMainByCode(entityMap);
			if(mainMap.get("out_date") != null && !"".equals(mainMap.get("out_date").toString())){
				mainMap.put("out_date", DateUtil.dateToString((Date)mainMap.get("out_date"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("medOutMain", mainMap);
			mode.addAttribute("medOutDetail", medCommonOutApplyCheckService.queryRelaOutDetailByCode(entityMap));
			return "/hrp/med/storage/out/applycheck/addOut";
			
		}else if("2".equals(String.valueOf(mapVo.get("rela_type")))){
			entityMap.put("tran_id", mapVo.get("rela_id"));
			entityMap.put("store_id", mapVo.get("store_id"));
			
			mainMap = medCommonOutApplyCheckService.queryRelaTranMainByCode(entityMap);
			if(mainMap.get("tran_date") != null && !"".equals(mainMap.get("tran_date").toString())){
				mainMap.put("tran_date", DateUtil.dateToString((Date)mainMap.get("tran_date"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("medTranMain", mainMap);
			mode.addAttribute("medTranDetail", medCommonOutApplyCheckService.queryRelaTranDetailByCode(entityMap));
			
			mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
			mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
			
			return "/hrp/med/storage/out/applycheck/addTran";
			
		}else if("3".equals(String.valueOf(mapVo.get("rela_type")))){
			entityMap.put("out_id", mapVo.get("rela_id"));
			entityMap.put("store_id", mapVo.get("store_id"));
			
			mainMap = medCommonOutApplyCheckService.queryRelaAffiOutMainByCode(entityMap);
			if(mainMap.get("out_date") != null && !"".equals(mainMap.get("out_date").toString())){
				mainMap.put("out_date", DateUtil.dateToString((Date)mainMap.get("out_date"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("medAffiOut", mainMap);
			mode.addAttribute("medAffiOutDetail", medCommonOutApplyCheckService.queryRelaAffiOutDetailByCode(entityMap));
			return "/hrp/med/storage/out/applycheck/addAffiOut";
			
		}else if("4".equals(String.valueOf(mapVo.get("rela_type")))){
			entityMap.put("tran_id", mapVo.get("rela_id"));
			entityMap.put("store_id", mapVo.get("store_id"));
			
			mainMap = medCommonOutApplyCheckService.queryRelaAffiTranMainByCode(entityMap);
			if(mainMap.get("tran_date") != null && !"".equals(mainMap.get("tran_date").toString())){
				mainMap.put("tran_date", DateUtil.dateToString((Date)mainMap.get("tran_date"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("medAffiTran", mainMap);
			mode.addAttribute("medAffiTranDetail", medCommonOutApplyCheckService.queryRelaAffiTranDetailByCode(entityMap));
			return "/hrp/med/storage/out/applycheck/addAffiTran";
			
		}else if("5".equals(String.valueOf(mapVo.get("rela_type")))){
			entityMap.put("req_id", mapVo.get("rela_id"));
			
			mainMap = medCommonOutApplyCheckService.queryRelaReqMainByCode(entityMap);
			if(mainMap.get("make_date") != null && !"".equals(mainMap.get("make_date").toString())){
				mainMap.put("make_date", DateUtil.dateToString((Date)mainMap.get("make_date"), "yyyy-MM-dd"));
			}
			if(mainMap.get("rdate") != null && !"".equals(mainMap.get("rdate").toString())){
				mainMap.put("rdate", DateUtil.dateToString((Date)mainMap.get("rdate"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("medReqMain", mainMap);
			mode.addAttribute("medReqDetail", medCommonOutApplyCheckService.queryRelaReqDetailByCode(entityMap));
			return "/hrp/med/storage/out/applycheck/addReq";
			
		}
		return "";
	}
	
	/**
	 * @Description 下载导入模版  出库--科室申请
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/storage/out/applycheck/downTemplate", method = RequestMethod.GET)
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
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/readFiles", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/addBatchMedCommonOutApply", method = RequestMethod.POST)
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
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/storageOutPrintSetPage", method = RequestMethod.GET)
	public String storageOutPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/queryMedOutByPrintTemlate", method = RequestMethod.POST)
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
		System.out.println("=============="+mapVo.get("p_num"));
		String reJson=null;
		try {
			reJson=medCommonOutApplyCheckService.queryMedOutByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycheck/queryMedOutRequirelExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutRequirelExists(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
		List<Map<String, Object>>	list=medCommonOutApplyCheckService.queryMedOutRequirelExists(mapVo);
			if(list!=null && list.size()>0){
				reJson= "{\"flag\":\"1\",\"state\":\"true\"}";
			}else{
				reJson= "{\"flag\":\"0\",\"state\":\"true\"}";
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	
}
