/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.storage.out;

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
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.in.MedStorageInService;
import com.chd.hrp.med.service.storage.out.MedCommonOutApplyCollectService;

/**
 * 
 * @Description:  出库--科室申请
 * @Table: MED_APPLY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedCommonOutApplyCollectController extends BaseController {

	private static Logger logger = Logger.getLogger(MedCommonOutApplyCollectController.class);

	// 引入Service服务
	@Resource(name = "medCommonOutApplyCollectService")
	private final MedCommonOutApplyCollectService medCommonOutApplyCollectService = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	@Resource(name = "medStorageInService")
	private final MedStorageInService medStorageInService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/applyCollectPage", method = RequestMethod.GET)
	public String MedCommonOutApplyCollectPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			mapVo.put("mod_code", "08");
		}
		start_date = medStorageInService.queryModeStartDate(mapVo);
		mode.addAttribute("start_date", start_date);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/out/applycollect/applyCollect";
	}

	/**
	 * @Description 查询数据  出库--科室申请
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/queryMedCommonOutApplyCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonOutApplyCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		String medIn = medCommonOutApplyCollectService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 申请退回
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/backMedCommonOutApplyCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backMedCommonOutApplyCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medCommonOutApplyCollectService.addByBack(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	

	/**
	 * @Description 
	 * 验证即时库存是否充足
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/checkMedCommonOutApplyCollectForAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkMedCommonOutApplyCollectForAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if("out".equals(mapVo.get("type"))){
			//生成出库单
			list = medCommonOutApplyCollectService.queryOutMain(mapVo);
			medJson = medCommonOutApplyCollectService.queryOutDetail(mapVo);
		}else if("affiOut".equals(mapVo.get("type"))){
			//生成代销出库单
			list = medCommonOutApplyCollectService.queryOutMain(mapVo);
			medJson = medCommonOutApplyCollectService.queryAffiOutDetail(mapVo);
		}else if("tran".equals(mapVo.get("type"))){
			//生成调拨单
			list = medCommonOutApplyCollectService.queryTranMain(mapVo);
			medJson = medCommonOutApplyCollectService.queryTranDetail(mapVo);
		}else if("affiTran".equals(mapVo.get("type"))){
			//生成代销调拨单
			list = medCommonOutApplyCollectService.queryTranMain(mapVo);
			medJson = medCommonOutApplyCollectService.queryAffiTranDetail(mapVo);
		}else{
			medJson = "{\"error\":\"未知业务请联系管理员\"}";
		}
		//判断是否有错误输出
		if(list.size() > 1){
			return JSONObject.parseObject("{\"error\":\"请选择同一科室、库房的药品！\"}");
		}
		if(medJson.indexOf("error")!=-1){
			return JSONObject.parseObject(medJson);
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
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/existsMedCommonOutApplyCollectStoreManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> existsMedCommonOutApplyCollectStoreManage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String medJson = medCommonOutApplyCollectService.queryStoreByDept(mapVo);
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 生成出库单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/addOutPage", method = RequestMethod.GET)
	public String medCommonOutApplyCollectAddOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		List<Map<String, Object>> list = medCommonOutApplyCollectService.queryOutMain(mapVo);
		if(list.size() > 1){
			return "{\"error\":\"请选择同一科室、库房的药品！\"}";
		}
		Map<String, Object> medOutMain = list.get(0);
		
		String medOutDetail;
		if("out".equals(mapVo.get("type"))){
			medOutMain.put("bus_type_code", "3");  //转换主表业务类型
			medOutMain.put("is_com", 0);  //是否代销
			
			//生成出库单明细
			medOutDetail = medCommonOutApplyCollectService.queryOutDetail(mapVo);
		}else if("affiOut".equals(mapVo.get("type"))){
			medOutMain.put("bus_type_code", "28");  //转换主表业务类型
			medOutMain.put("is_com", 1);  //是否代销
			
			//生成代销出库单明细
			medOutDetail = medCommonOutApplyCollectService.queryAffiOutDetail(mapVo);
		}else{
			medOutDetail = "{\"error\":\"缺少参数【type】\"}";
		}
		//判断是否有错误输出
		if(medOutDetail.indexOf("error")!=-1){
			
			return medOutDetail;
		}
		
		//申请日期
		if(medOutMain.get("out_date") != null && !"".equals(medOutMain.get("out_date"))){
			medOutMain.put("out_date", DateUtil.dateToString((Date)medOutMain.get("out_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("medOutMain", medOutMain);
		mode.addAttribute("medOutDetail", medOutDetail);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "/hrp/med/storage/out/applycollect/addOutByCollect";
	}

	/**
	 * @Description 
	 * 生成调拨单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/addTranPage", method = RequestMethod.GET)
	public String medCommonOutApplyCollectAddTranPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String storeMsg = medCommonOutApplyCollectService.queryStoreByDept(mapVo);
		//判断是否有错误输出
		if(storeMsg.indexOf("error")!=-1){
			return storeMsg;
		}
		*/
		List<Map<String, Object>> list = medCommonOutApplyCollectService.queryTranMain(mapVo);
		if(list.size() > 1){
			return "{\"error\":\"请选择同一科室、库房的药品！\"}";
		}
		Map<String, Object> medTranMain = list.get(0);
		
		String medTranDetail;
		if("tran".equals(mapVo.get("type"))){
			medTranMain.put("is_com", 0);  //是否代销
			
			//生成出库单明细
			medTranDetail = medCommonOutApplyCollectService.queryTranDetail(mapVo);
		}else if("affiTran".equals(mapVo.get("type"))){
			medTranMain.put("is_com", 1);  //是否代销
			
			//生成代销出库单明细
			medTranDetail = medCommonOutApplyCollectService.queryAffiTranDetail(mapVo);
		}else{
			medTranDetail = "{\"error\":\"缺少参数【type】\"}";
		}
		//判断是否有错误输出
		if(medTranDetail.indexOf("error")!=-1){
			return medTranDetail;
		}
		
		//调拨日期
		if(medTranMain.get("tran_date") != null && !"".equals(medTranMain.get("tran_date"))){
			medTranMain.put("tran_date", DateUtil.dateToString((Date)medTranMain.get("tran_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("medTranMain", medTranMain);
		mode.addAttribute("medTranDetail", medTranDetail);
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "/hrp/med/storage/out/applycollect/addTranByCollect";
	}
	
	/**
	 * @Description 
	 * 生成科室需求计划跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/addReqPage", method = RequestMethod.GET)
	public String medCommonOutApplyCollectAddReqPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		List<Map<String, Object>> list = medCommonOutApplyCollectService.queryReqMain(mapVo);
		if(list.size() > 1){
			return "/hrp/med/storage/out/applycollect/errorMsg";
		}
		Map<String, Object> medReqMain = list.get(0);
		
		String medReqDetail = medCommonOutApplyCollectService.queryReqDetail(mapVo);
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
		
		mode.addAttribute("medReqMain", medReqMain);
		mode.addAttribute("medReqDetail", medReqDetail);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08032", MyConfig.getSysPara("08032"));
		
		return "/hrp/med/storage/out/applycollect/addReqByCollect";
	}
	
	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/relaPage", method = RequestMethod.GET)
	public String medCommonOutApplyCollectRelaPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		return "hrp/med/storage/out/applycollect/rela";
	}

	/**
	 * @Description 汇总生成科室需求计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/addMedReqByApplyCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedReqByApplyCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medCommonOutApplyCollectService.addMedReqByApplyCollect(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 汇总生成采购计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/addMedPurByApplyCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedPurByApplyCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medCommonOutApplyCollectService.addMedPurByApplyCollect(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	/**
	 * 查看关闭的药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/colseInvPage", method = RequestMethod.GET)
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
		mode.addAttribute("medApplyRela", mapVo);
		
		return "hrp/med/storage/out/applycollect/colseInv";
	}
	/**
	 * 查看关闭的药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/queryMedApplyCloseInvInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedApplyCloseInvInfo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medCommonOutApplyCollectService.queryMedApplyCloseInvInfo(getPage(mapVo));
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	
	@RequestMapping(value = "/hrp/med/storage/out/applycollect/updateMedApplyCancleCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedApplyCancleCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			medJson = medCommonOutApplyCollectService.updateMedApplyCancleCloseInv(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
}
