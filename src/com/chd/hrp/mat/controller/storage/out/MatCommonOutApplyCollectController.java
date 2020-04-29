/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.out;

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
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.mat.service.storage.out.MatCommonOutApplyCollectService;

/** 
 * 
 * @Description:  出库--科室申请
 * @Table: MAT_APPLY_MAIN
 * @Author: bell 
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatCommonOutApplyCollectController extends BaseController {

	private static Logger logger = Logger.getLogger(MatCommonOutApplyCollectController.class);

	// 引入Service服务
	@Resource(name = "matCommonOutApplyCollectService")
	private final MatCommonOutApplyCollectService matCommonOutApplyCollectService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/applyCollectPage", method = RequestMethod.GET)
	public String MatCommonOutApplyCollectPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String start_date = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("mod_code") == null) {
			mapVo.put("mod_code", "04");
		}
		start_date = matStorageInService.queryModeStartDate(mapVo);
		mode.addAttribute("start_date", start_date);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/storage/out/applycollect/applyCollect";
	}

	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/queryMatCommonOutApplyCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonOutApplyCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		String matIn = matCommonOutApplyCollectService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 汇总采购页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/applyInvCollectPage", method = RequestMethod.GET)
	public String MatCommonOutApplyInvCollectPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		return "hrp/mat/storage/out/applycollect/applyInvCollect";
	}

	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/queryMatCommonOutApplyInvCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonOutApplyInvCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String matIn = matCommonOutApplyCollectService.queryInvCollect(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}

	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/queryMatCommonOutApplyInvCollectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonOutApplyInvCollectDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String matIn = matCommonOutApplyCollectService.queryInvCollectDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 申请退回
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/backMatCommonOutApplyCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backMatCommonOutApplyCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			map.put("detail_id", ids[4]);
			map.put("state", 1);
			map.put("back_reason", back_reason);
			listVo.add(map);
		}
		
		String matJson;
		try {
			matJson = matCommonOutApplyCollectService.addByBack(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	

	/**
	 * @Description 
	 * 验证即时库存是否充足
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/checkMatCommonOutApplyCollectForAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkMatCommonOutApplyCollectForAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if("out".equals(mapVo.get("type"))){
			//生成出库单
			list = matCommonOutApplyCollectService.queryOutMain(mapVo);
			matJson = matCommonOutApplyCollectService.queryOutDetail(mapVo);
		}else if("affiOut".equals(mapVo.get("type"))){
			//生成代销出库单
			list = matCommonOutApplyCollectService.queryOutMain(mapVo);
			matJson = matCommonOutApplyCollectService.queryAffiOutDetail(mapVo);
		}else if("tran".equals(mapVo.get("type"))){
			//生成调拨单
			list = matCommonOutApplyCollectService.queryTranMain(mapVo);
			matJson = matCommonOutApplyCollectService.queryTranDetail(mapVo);
		}else if("affiTran".equals(mapVo.get("type"))){
			//生成代销调拨单
			list = matCommonOutApplyCollectService.queryTranMain(mapVo);
			matJson = matCommonOutApplyCollectService.queryAffiTranDetail(mapVo);
		}else{
			matJson = "{\"error\":\"未知业务请联系管理员\"}";
		}
		//判断是否有错误输出
		if(list.size() > 1){
			return JSONObject.parseObject("{\"error\":\"请选择同一科室、库房的材料！\"}");
		}
		if(matJson.indexOf("error")!=-1){
			return JSONObject.parseObject(matJson);
		}else{
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
	}

	/**
	 * @Description 查询科室管理的仓库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/existsMatCommonOutApplyCollectStoreManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> existsMatCommonOutApplyCollectStoreManage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String matJson = matCommonOutApplyCollectService.queryStoreByDept(mapVo);
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 生成出库单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/addOutPage", method = RequestMethod.GET)
	public String matCommonOutApplyCollectAddOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		List<Map<String, Object>> list = matCommonOutApplyCollectService.queryOutMain(mapVo);
		if(list.size() > 1){
			return "{\"error\":\"请选择同一科室、库房的材料！\"}";
		}
		Map<String, Object> matOutMain = list.get(0);
		
		String matOutDetail;
		if("out".equals(mapVo.get("type"))){
			matOutMain.put("bus_type_code", "3");  //转换主表业务类型
			matOutMain.put("is_com", 0);  //是否代销
			
			//生成出库单明细
			matOutDetail = matCommonOutApplyCollectService.queryOutDetail(mapVo);
		}else if("affiOut".equals(mapVo.get("type"))){
			matOutMain.put("bus_type_code", "28");  //转换主表业务类型
			matOutMain.put("is_com", 1);  //是否代销
			
			//生成代销出库单明细
			matOutDetail = matCommonOutApplyCollectService.queryAffiOutDetail(mapVo);
		}else{
			matOutDetail = "{\"error\":\"缺少参数【type】\"}";
		}
		//判断是否有错误输出
		if(matOutDetail.indexOf("error")!=-1){
			
			return matOutDetail;
		}
		
		//申请日期
		if(matOutMain.get("out_date") != null && !"".equals(matOutMain.get("out_date"))){
			matOutMain.put("out_date", DateUtil.dateToString((Date)matOutMain.get("out_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("matOutMain", matOutMain);
		mode.addAttribute("matOutDetail", matOutDetail);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "/hrp/mat/storage/out/applycollect/addOutByCollect";
	}

	/**
	 * @Description 
	 * 生成调拨单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/addTranPage", method = RequestMethod.GET)
	public String matCommonOutApplyCollectAddTranPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		/*20170606 hsy由于即墨存在二级库下管多个科室所有这个判断不能直接
		//查询申请科室是否是仓库的管理科室（即是否存在二级库科室库）
		String storeMsg = matCommonOutApplyCollectService.queryStoreByDept(mapVo);
		//判断是否有错误输出
		if(storeMsg.indexOf("error")!=-1){
			return storeMsg;
		}
		*/
		List<Map<String, Object>> list = matCommonOutApplyCollectService.queryTranMain(mapVo);
		if(list.size() > 1){
			return "{\"error\":\"请选择同一科室、库房的材料！\"}";
		}
		Map<String, Object> matTranMain = list.get(0);
		
		String matTranDetail;
		if("tran".equals(mapVo.get("type"))){
			matTranMain.put("is_com", 0);  //是否代销
			
			//生成出库单明细
			matTranDetail = matCommonOutApplyCollectService.queryTranDetail(mapVo);
		}else if("affiTran".equals(mapVo.get("type"))){
			matTranMain.put("is_com", 1);  //是否代销
			
			//生成代销出库单明细
			matTranDetail = matCommonOutApplyCollectService.queryAffiTranDetail(mapVo);
		}else{
			matTranDetail = "{\"error\":\"缺少参数【type】\"}";
		}
		//判断是否有错误输出
		if(matTranDetail.indexOf("error")!=-1){
			return matTranDetail;
		}
		
		//调拨日期
		if(matTranMain.get("tran_date") != null && !"".equals(matTranMain.get("tran_date"))){
			matTranMain.put("tran_date", DateUtil.dateToString((Date)matTranMain.get("tran_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matTranMain", matTranMain);
		mode.addAttribute("matTranDetail", matTranDetail);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "/hrp/mat/storage/out/applycollect/addTranByCollect";
	}
	
	/**
	 * @Description 
	 * 生成科室需求计划跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/addReqPage", method = RequestMethod.GET)
	public String matCommonOutApplyCollectAddReqPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		List<Map<String, Object>> list = matCommonOutApplyCollectService.queryReqMain(mapVo);
		if(list.size() > 1){
			return "/hrp/mat/storage/out/applycollect/errorMsg";
		}
		Map<String, Object> matReqMain = list.get(0);
		
		String matReqDetail = matCommonOutApplyCollectService.queryReqDetail(mapVo);
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
		
		mode.addAttribute("matReqMain", matReqMain);
		mode.addAttribute("matReqDetail", matReqDetail);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04032", MyConfig.getSysPara("04032"));
		
		return "/hrp/mat/storage/out/applycollect/addReqByCollect";
	}
	
	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/relaPage", method = RequestMethod.GET)
	public String matCommonOutApplyCollectRelaPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		return "hrp/mat/storage/out/applycollect/rela";
	}

	/**
	 * @Description 汇总生成科室需求计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/addMatReqByApplyCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatReqByApplyCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			matJson = matCommonOutApplyCollectService.addMatReqByApplyCollect(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 汇总生成采购计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/addMatPurByApplyCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPurByApplyCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			matJson = matCommonOutApplyCollectService.addMatPurByApplyCollect(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 按材料汇总页面--汇总生成采购计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/addMatPurByApplyInvCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPurByApplyInvCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			matJson = matCommonOutApplyCollectService.addMatPurByApplyInvCollect(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 查看关闭的材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/colseInvPage", method = RequestMethod.GET)
	public String colseInvPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		return "hrp/mat/storage/out/applycollect/colseInv";
	}
	/**
	 * 查看关闭的材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/queryMatApplyCloseInvInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatApplyCloseInvInfo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			matJson = matCommonOutApplyCollectService.queryMatApplyCloseInvInfo(getPage(mapVo));
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/updateMatApplyCancleCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatApplyCancleCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			matJson = matCommonOutApplyCollectService.updateMatApplyCancleCloseInv(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	
	@RequestMapping(value = "/hrp/mat/storage/out/applycollect/matPurByApplyCollectStorePage", method = RequestMethod.GET)
	public String matPurByApplyCollectStorePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("apply_ids", mapVo.get("apply_ids"));
		mode.addAttribute("detail_ids", mapVo.get("detail_ids"));
		
		return "hrp/mat/storage/out/applycollect/matPurByApplyCollectStore";

	}
}
