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
import com.chd.hrp.mat.entity.MatInMain;
import com.chd.hrp.mat.entity.MatOutMain;
import com.chd.hrp.mat.service.affi.out.MatAffiOutCommonService;
import com.chd.hrp.mat.service.affi.tran.MatAffiTranMainService;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.requrie.dept.MatRequirePlanService;
import com.chd.hrp.mat.service.storage.out.MatCommonOutApplyCheckService;
import com.chd.hrp.mat.service.storage.out.MatCommonOutApplyService;
import com.chd.hrp.mat.service.storage.out.MatOutMainService;
import com.chd.hrp.mat.service.storage.tran.MatTranMainService;

/**
 * 
 * @Description:  出库--科室申请
 * @Table: MAT_APPLY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */ 

@Controller
public class MatCommonOutApplyCheckController extends BaseController {

	private static Logger logger = Logger.getLogger(MatCommonOutApplyCheckController.class);

	// 引入Service服务
	@Resource(name = "matCommonOutApplyCheckService")
	private final MatCommonOutApplyCheckService matCommonOutApplyCheckService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null; 
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/mainPage", method = RequestMethod.GET)
	public String MatCommonOutApplyMainPage(Model mode) throws Exception {

		mode.addAttribute("p04013", MyConfig.getSysPara("04013"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		
		return "hrp/mat/storage/out/applycheck/main";
	}

	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/queryMatCommonOutApplyCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonOutApplyCheck(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		String matIn = matCommonOutApplyCheckService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	

	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/queryMatCommonOutApplyCheckDetailN", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonOutApplyCheckDetailN(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		String matIn = matCommonOutApplyCheckService.queryDetailN(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/queryMatCommonOutApplyCheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonOutApplyCheckDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matCommonOutApplyCheckService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/queryMatCommonOutApplyCheckById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonOutApplyCheckById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return matCommonOutApplyCheckService.queryByCode(mapVo);
	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/updatePage", method = RequestMethod.GET)
	public String matCommonOutApplyCheckUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matApplyMain = matCommonOutApplyCheckService.queryByCode(mapVo);
		
		//申请日期
		if(matApplyMain.get("app_date") != null && !"".equals(matApplyMain.get("app_date"))){
			matApplyMain.put("app_date", DateUtil.dateToString((Date)matApplyMain.get("app_date"), "yyyy-MM-dd HH:mm:ss"));
		}
		//制单日期
		if(matApplyMain.get("make_date") != null && !"".equals(matApplyMain.get("make_date"))){
			matApplyMain.put("make_date", DateUtil.dateToString((Date)matApplyMain.get("make_date"), "yyyy-MM-dd"));
		}
		//审核日期
		if(matApplyMain.get("check_date") != null && !"".equals(matApplyMain.get("check_date"))){
			matApplyMain.put("check_date", DateUtil.dateToString((Date)matApplyMain.get("check_date"), "yyyy-MM-dd"));
		}
		//响应日期
		if(matApplyMain.get("res_date") != null && !"".equals(matApplyMain.get("res_date"))){
			matApplyMain.put("res_date", DateUtil.dateToString((Date)matApplyMain.get("res_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matApplyMain", matApplyMain);
		mode.addAttribute("paras", matCommonService.queryMatParas());

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		
		return "hrp/mat/storage/out/applycheck/update";
	}
	
	/**
	 * @Description 申请退回
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/backMatCommonOutApplyCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backMatCommonOutApplyCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		String matJson;
		try {
			matJson = matCommonOutApplyCheckService.updateBackMatCommonOutApplyCheckBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 材料关闭
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/updateMatCommonOutApplyCheckCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatCommonOutApplyCheckCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String matJson;
		try {
			matJson = matCommonOutApplyCheckService.updateMatCommonOutApplyCheckCloseInv(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 批量生成出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addOutMatCommonOutApplyCheckBatch", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addOutMatCommonOutApplyCheckBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String matJson;
		try {
			matJson = matCommonOutApplyCheckService.addOutBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 汇总生成出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addOutMatCommonOutApplyCheckCollect", method = RequestMethod.GET)
	public String addOutMatCommonOutApplyCheckCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		
		Map<String, Object> matOutMain = matCommonOutApplyCheckService.queryOutMainByCollect(listVo); 
		
		//转换主表业务类型
		matOutMain.put("bus_type_code", "3");
		//申请日期
		if(matOutMain.get("out_date") != null && !"".equals(matOutMain.get("out_date"))){
			matOutMain.put("out_date", DateUtil.dateToString((Date)matOutMain.get("out_date"), "yyyy-MM-dd"));
		} 
		 
		//页面形式--添加or修改
		matOutMain.put("is_add", 1);
		
		String matOutDetail;
		try {
			matOutDetail = matCommonOutApplyCheckService.queryOutDetailByCollect(listVo);
		} catch (Exception e) {
			matOutDetail = e.getMessage();
		}
		mode.addAttribute("matOutMain", matOutMain);
		mode.addAttribute("matOutDetail", matOutDetail);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "/hrp/mat/storage/out/applycheck/addCollectOut";
	}

	/**
	 * @Description 打开出库单添加页面并自动填充数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/openAddOut", method = RequestMethod.GET)
	public String openAddOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("matOutMain", mapVo.get("matOutMain"));
		mode.addAttribute("matOutDetail", mapVo.get("matOutDetail"));
		return "/hrp/mat/storage/out/applycheck/addOut";
	}

	/**
	 * @Description 
	 * 验证即时库存是否充足
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/checkMatCommonOutApplyCheckForAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkMatCommonOutApplyCheckForAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String matJson = "";
		if("out".equals(mapVo.get("type"))){
			//生成出库单
			matJson = matCommonOutApplyCheckService.queryOutDetail(mapVo);
		}else if("affiOut".equals(mapVo.get("type"))){
			//生成代销出库单
			matJson = matCommonOutApplyCheckService.queryAffiOutDetail(mapVo);
		}else if("tran".equals(mapVo.get("type"))){
			//生成调拨单
			matJson = matCommonOutApplyCheckService.queryTranDetail(mapVo);
		}else if("affiTran".equals(mapVo.get("type"))){
			//生成代销调拨单
			matJson = matCommonOutApplyCheckService.queryAffiTranDetail(mapVo);
		}else{
			matJson = "{\"error\":\"未知业务请联系管理员\"}";
		}
		//判断是否有错误输出
		if(matJson.indexOf("error")!=-1){
			return JSONObject.parseObject(matJson);
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
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addOutPage", method = RequestMethod.GET)
	public String matCommonOutApplyCheckAddOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matOutMain = matCommonOutApplyCheckService.queryOutMain(mapVo);
		
		String matOutDetail = matCommonOutApplyCheckService.queryOutDetail(mapVo);
		//判断是否有错误输出
		if(matOutDetail.indexOf("error")!=-1){
			
			return matOutDetail;
		}
		
		//转换主表业务类型
		matOutMain.put("bus_type_code", "3");
		//申请日期
		if(matOutMain.get("out_date") != null && !"".equals(matOutMain.get("out_date"))){
			matOutMain.put("out_date", DateUtil.dateToString((Date)matOutMain.get("out_date"), "yyyy-MM-dd"));
		}
		//申请日期
	   if(matOutMain.get("app_date") != null && !"".equals(matOutMain.get("app_date"))){
			 matOutMain.put("app_date", DateUtil.dateToString((Date)matOutMain.get("app_date"), "yyyy-MM-dd HH:mm:ss "));
		 }
		//页面形式--添加or修改
		matOutMain.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("matOutMain", matOutMain);
		mode.addAttribute("matOutDetail", matOutDetail);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "/hrp/mat/storage/out/applycheck/addOut";
	}


	/**
	 * @Description 保存出库单据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addMatOutMainByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatOutMainByApp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String matJson;
		try {
			matJson =  matCommonOutApplyCheckService.addOut(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 
	 * 生成代销出库单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addAffiOutPage", method = RequestMethod.GET)
	public String matCommonOutApplyCheckAddAffiOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matAffiOut = matCommonOutApplyCheckService.queryOutMain(mapVo);
		
		String matAffiOutDetail = matCommonOutApplyCheckService.queryAffiOutDetail(mapVo);
		//判断是否有错误输出
		if(matAffiOutDetail.indexOf("error")!=-1){
			return matAffiOutDetail;
		}
		
		//转换主表业务类型
		matAffiOut.put("bus_type_code", "28");
		//申请日期
		if(matAffiOut.get("out_date") != null && !"".equals(matAffiOut.get("out_date"))){
			matAffiOut.put("out_date", DateUtil.dateToString((Date)matAffiOut.get("out_date"), "yyyy-MM-dd"));
		}
		//页面形式--添加or修改
		matAffiOut.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("matAffiOut", matAffiOut);
		mode.addAttribute("matAffiOutDetail", matAffiOutDetail);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "/hrp/mat/storage/out/applycheck/addAffiOut";
	}
	
	/**
	 * @Description 保存代销出库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addMatAffiOutByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatAffiOutByApp(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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

		String matJson;
		try {
			matJson = matCommonOutApplyCheckService.addAffiOut(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 查询科室管理的仓库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/existsMatCommonOutApplyCheckStoreManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> existsMatCommonOutApplyCheckStoreManage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String matJson = matCommonOutApplyCheckService.queryStoreByDept(mapVo);
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 生成调拨单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addTranPage", method = RequestMethod.GET)
	public String matCommonOutApplyCheckAddTranPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String storeMsg = matCommonOutApplyCheckService.queryStoreByDept(mapVo);
		//判断是否有错误输出
		if(storeMsg.indexOf("error")!=-1){
			return storeMsg;
		}

		Map<String, Object> matTranMain = matCommonOutApplyCheckService.queryTranMain(mapVo);
		
		String matTranDetail = matCommonOutApplyCheckService.queryTranDetail(mapVo);
		//判断是否有错误输出
		if(matTranDetail.indexOf("error")!=-1){
			return matTranDetail;
		}
		
		//调拨日期
		if(matTranMain.get("tran_date") != null && !"".equals(matTranMain.get("tran_date"))){
			matTranMain.put("tran_date", DateUtil.dateToString((Date)matTranMain.get("tran_date"), "yyyy-MM-dd"));
		}
		//页面形式--添加or修改
		matTranMain.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("matTranMain", matTranMain);
		mode.addAttribute("matTranDetail", matTranDetail);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "/hrp/mat/storage/out/applycheck/addTran";
	}

	/**
	 * @Description 保存调拨单据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addMatTranMainByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatTranMainByApp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matJson;
		try {
			matJson = matCommonOutApplyCheckService.addTran(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
		
	}

	/**
	 * @Description 
	 * 生成代销调拨单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addAffiTranPage", method = RequestMethod.GET)
	public String matCommonOutApplyCheckAddAffiTranPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String storeMsg = matCommonOutApplyCheckService.queryStoreByDept(mapVo);
		//判断是否有错误输出
		if(storeMsg.indexOf("error")!=-1){
			return storeMsg;
		}

		Map<String, Object> matAffiTran = matCommonOutApplyCheckService.queryTranMain(mapVo);
		
		String matAffiTranDetail = matCommonOutApplyCheckService.queryAffiTranDetail(mapVo);
		//判断是否有错误输出
		if(matAffiTranDetail.indexOf("error")!=-1){
			return matAffiTranDetail;
		}
		
		//调拨日期
		if(matAffiTran.get("tran_date") != null && !"".equals(matAffiTran.get("tran_date"))){
			matAffiTran.put("tran_date", DateUtil.dateToString((Date)matAffiTran.get("tran_date"), "yyyy-MM-dd"));
		}
		//页面形式--添加or修改
		matAffiTran.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("matAffiTran", matAffiTran);
		mode.addAttribute("matAffiTranDetail", matAffiTranDetail);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "/hrp/mat/storage/out/applycheck/addAffiTran";
	}

	/**
	 * @Description 保存代销调拨单
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addMatAffiTranMainByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatAffiTranMainByApp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matJson;
		try {
			matJson = matCommonOutApplyCheckService.addAffiTran(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}	
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 生成科室需求计划跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addReqPage", method = RequestMethod.GET)
	public String matCommonOutApplyCheckAddReqPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matReqMain = matCommonOutApplyCheckService.queryReqMain(mapVo);
		
		String matReqDetail = matCommonOutApplyCheckService.queryReqDetail(mapVo);
		//判断是否有错误输出
		if(matReqDetail.indexOf("error")!=-1){
			return matReqDetail;
		}
		
		//编制日期
		if(matReqMain.get("make_date") != null && !"".equals(matReqMain.get("make_date"))){
			matReqMain.put("make_date", DateUtil.dateToString((Date)matReqMain.get("make_date"), "yyyy-MM-dd"));
		}
		//需求日期
		if(matReqMain.get("rdate") != null && !"".equals(matReqMain.get("rdate"))){
			matReqMain.put("rdate", DateUtil.dateToString((Date)matReqMain.get("rdate"), "yyyy-MM-dd"));
		}
		//页面形式--添加or修改
		matReqMain.put("is_add", mapVo.get("is_add"));
		mode.addAttribute("matReqMain", matReqMain);
		mode.addAttribute("matReqDetail", matReqDetail);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "/hrp/mat/storage/out/applycheck/addReq";
	}
	/**
	 * @Description 保存科室需求计划单据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addMatRequriedMainByApp", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatRequriedMainByApp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

		String matJson;
		try {
			matJson = matCommonOutApplyCheckService.addReq(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}	

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/relaPage", method = RequestMethod.GET)
	public String matCommonOutApplyCheckRelaPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mode.addAttribute("matApplyRela", mapVo);
		
		return "hrp/mat/storage/out/applycheck/rela";
	}

	/**
	 * @Description 查询数据  对应单据列表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/queryMatApplyRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatApplyRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matIn = matCommonOutApplyCheckService.queryMatApplyRela(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}

	/**
	 * @Description 删除数据  对应单据列表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/deleteMatApplyRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatApplyRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String matJson;
		try {
			matJson = matCommonOutApplyCheckService.deleteMatApplyRela(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 查看对应单据跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/relaOutPage", method = RequestMethod.GET)
	public String matCommonOutApplyCheckRelaOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			
			mainMap = matCommonOutApplyCheckService.queryRelaOutMainByCode(entityMap);
			if(mainMap.get("out_date") != null && !"".equals(mainMap.get("out_date").toString())){
				mainMap.put("out_date", DateUtil.dateToString((Date)mainMap.get("out_date"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("matOutMain", mainMap);
			mode.addAttribute("matOutDetail", matCommonOutApplyCheckService.queryRelaOutDetailByCode(entityMap));
			return "/hrp/mat/storage/out/applycheck/addOut";
			
		}else if("2".equals(String.valueOf(mapVo.get("rela_type")))){
			entityMap.put("tran_id", mapVo.get("rela_id"));
			entityMap.put("store_id", mapVo.get("store_id"));
			
			mainMap = matCommonOutApplyCheckService.queryRelaTranMainByCode(entityMap);
			if(mainMap.get("tran_date") != null && !"".equals(mainMap.get("tran_date").toString())){
				mainMap.put("tran_date", DateUtil.dateToString((Date)mainMap.get("tran_date"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("matTranMain", mainMap);
			mode.addAttribute("matTranDetail", matCommonOutApplyCheckService.queryRelaTranDetailByCode(entityMap));
			return "/hrp/mat/storage/out/applycheck/addTran";
			
		}else if("3".equals(String.valueOf(mapVo.get("rela_type")))){
			entityMap.put("out_id", mapVo.get("rela_id"));
			entityMap.put("store_id", mapVo.get("store_id"));
			
			mainMap = matCommonOutApplyCheckService.queryRelaAffiOutMainByCode(entityMap);
			if(mainMap.get("out_date") != null && !"".equals(mainMap.get("out_date").toString())){
				mainMap.put("out_date", DateUtil.dateToString((Date)mainMap.get("out_date"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("matAffiOut", mainMap);
			mode.addAttribute("matAffiOutDetail", matCommonOutApplyCheckService.queryRelaAffiOutDetailByCode(entityMap));
			return "/hrp/mat/storage/out/applycheck/addAffiOut";
			
		}else if("4".equals(String.valueOf(mapVo.get("rela_type")))){
			entityMap.put("tran_id", mapVo.get("rela_id"));
			entityMap.put("store_id", mapVo.get("store_id"));
			
			mainMap = matCommonOutApplyCheckService.queryRelaAffiTranMainByCode(entityMap);
			if(mainMap.get("tran_date") != null && !"".equals(mainMap.get("tran_date").toString())){
				mainMap.put("tran_date", DateUtil.dateToString((Date)mainMap.get("tran_date"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("matAffiTran", mainMap);
			mode.addAttribute("matAffiTranDetail", matCommonOutApplyCheckService.queryRelaAffiTranDetailByCode(entityMap));
			return "/hrp/mat/storage/out/applycheck/addAffiTran";
			
		}else if("5".equals(String.valueOf(mapVo.get("rela_type")))){
			entityMap.put("req_id", mapVo.get("rela_id"));
			
			mainMap = matCommonOutApplyCheckService.queryRelaReqMainByCode(entityMap);
			if(mainMap.get("make_date") != null && !"".equals(mainMap.get("make_date").toString())){
				mainMap.put("make_date", DateUtil.dateToString((Date)mainMap.get("make_date"), "yyyy-MM-dd"));
			}
			if(mainMap.get("rdate") != null && !"".equals(mainMap.get("rdate").toString())){
				mainMap.put("rdate", DateUtil.dateToString((Date)mainMap.get("rdate"), "yyyy-MM-dd"));
			}
			mainMap.put("is_add", "0");
			mode.addAttribute("matReqMain", mainMap);
			mode.addAttribute("matReqDetail", matCommonOutApplyCheckService.queryRelaReqDetailByCode(entityMap));
			return "/hrp/mat/storage/out/applycheck/addReq";
			
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
	@RequestMapping(value = "hrp/mat/storage/out/applycheck/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate",
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
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/readFiles", method = RequestMethod.POST)
	public String readMatCommonOutApplyFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<MatInMain> list_err = new ArrayList<MatInMain>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

		} catch (DataAccessException e) {

			MatInMain data_exc = new MatInMain();

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
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/addBatchMatCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMatCommonOutApply(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<MatInMain> list_err = new ArrayList<MatInMain>();

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
			MatInMain data_exc = new MatInMain();
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
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/storageOutPrintSetPage", method = RequestMethod.GET)
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
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/queryMatOutByPrintTemlate", method = RequestMethod.POST)
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
		//System.out.println("=============="+mapVo.get("p_num"));
		String reJson=null;
		try {
			reJson=matCommonOutApplyCheckService.queryMatOutByPrintTemlate(mapVo);
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
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/queryMatOutRequirelExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutRequirelExists(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
		List<Map<String, Object>>	list=matCommonOutApplyCheckService.queryMatOutRequirelExists(mapVo);
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
	
	
	/**
	 * @Description 科室申领审核作废
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycheck/nullifyMatCommonOutApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> nullifyMatCommonOutApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		/*Date date = new Date();
		String user_id = SessionManager.getUserId();*/
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_id", ids[3]);
			mapVo.put("state", 4);
			/*mapVo.put("sender", user_id);
			mapVo.put("send_date", date);*/
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matCommonOutApplyCheckService.updateNullifyMatCommonOutApplyBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	
}
