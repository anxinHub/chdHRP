/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.affi.out;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.service.affi.out.MatAffiOutCommonService;


/**
 * 
 * @Description:  代销出库 
 * @Table: MAT_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAffiOutCommonController extends BaseController {

	private static Logger logger = Logger.getLogger(MatAffiOutCommonController.class);

	// 引入Service服务
	@Resource(name = "matAffiOutCommonService")
	private final MatAffiOutCommonService matAffiOutCommonService = null;
	
	/**
	 * @Description 代销出库--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/matAffiOutCommonMainPage", method = RequestMethod.GET)
	public String matAffiOutCommonMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04015", MyConfig.getSysPara("04015"));
		mode.addAttribute("p04024", MyConfig.getSysPara("04024"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("hos_name", SessionManager.getHosName());
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		return "hrp/mat/affi/out/matAffiOutCommonMain";
	}
	
	/**
	 * 代销出库--主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutCommon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		// 查询时 登录用户只能查询其有数据权限（部门权限、仓库权限）的 相关数据
		/*if (mapVo.get("dept_id") == "" || mapVo.get("dept_id") == null) {
			mapVo.put("dept_id", "00"); // 当 dept_id == 00 时 根据用户 部门权限 限定 申请科室
		}

		if (mapVo.get("store_id") == "" || mapVo.get("store_id") == null) {
			mapVo.put("store_id", "00"); // 当 store_id == 00 时 根据用户 仓库权限 限定 响应仓库
		}*/
		
		String matAffiOut = "";
		if(mapVo.get("show_detail").equals("1")){
			matAffiOut =  matAffiOutCommonService.queryDetails(getPage(mapVo));
		}else{
			matAffiOut =  matAffiOutCommonService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(matAffiOut);
	}
	
	/**
	 * 代销出库--添加页面  跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/matAffiOutCommonAddPage", method = RequestMethod.GET)
	public String matAffiOutCommonAddPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/affi/out/matAffiOutCommonAdd";
	}
	
	/**
	 * @Description 
	 * 代销出库  添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/addMatAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatAffiOutCommon(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("maker") == null) {
			mapVo.put("maker", SessionManager.getUserId());
		}
		mapVo.put("is_dir", "0");
		
		String matJson;
		
		try {
			matJson = matAffiOutCommonService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		

	}
	/**
	 * 代销出库--历史导入
	 * matAffiOutCommonHisImpPage
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/matAffiOutCommonHisImpPage", method = RequestMethod.GET)
	public String matAffiOutCommonHisImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/out/matAffiOutCommonHisImp";
	}
	
	/**
	 * 代销出库--历史导入 查询明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutDetailHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutDetailHistory(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String matJson = null;//matAffiOutCommonService.queryMatAffiOutDetailHistory(mapVo);

		return JSONObject.parseObject(matJson);

	}
	/**
	 * 代销出库--配套导入页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/matAffiOutCommonMatchImpPage", method = RequestMethod.GET)
	public String matAffiOutCommonMatchImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/out/matAffiOutCommonMatchImp";
	}
	
	/**
	 * 配套入库 查询明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutMatchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutMatchDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String matJson = matAffiOutCommonService.queryMatAffiOutMatchDetail(mapVo);

		return JSONObject.parseObject(matJson);

	}
	/**
	 * 代销出库 配套导入--组装明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutDetailByMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutDetailByMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matMsg = matAffiOutCommonService.queryMatAffiOutDetailByMatch(mapVo);

		return JSONObject.parseObject(matMsg);
	}
	
	
	/**
	 * @Description 选择材料   查询数据 mat_fifo_balance
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutByFifoBalance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutByFifoBalance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("inv_id", mapVo.get("inv_id"));
		mapVo.put("inv_no", mapVo.get("inv_no"));
		mapVo.put("store_id", mapVo.get("store_id"));
		String matOutMain = matAffiOutCommonService.queryMatAffiOutByFifoBalance(getPage(mapVo));
		return JSONObject.parseObject(matOutMain);

	}
	
	
	/**
	 * @Description 选择材料页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/matMatAffiOutInvFifoPage", method = RequestMethod.GET)
	public String matMatAffiOutInvFifoPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {
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
		
		mapVo.put("store_id", paras.split(",")[0]);
		//String[] paraArray = paras.split("@");
		//mapVo.put("inv_id", paraArray[0].split(",")[0]);
		//mapVo.put("inv_no", paraArray[0].split(",")[1]);
		//mapVo.put("store_id", paraArray[1].split(",")[0]);

		//String matAfiiOutFifo = matAffiOutCommonService.queryMatAffiOutByInvHold(getPage(mapVo));
		//mode.addAttribute("inv_id", paraArray[0]);
		mode.addAttribute("store_id", paras);
		//mode.addAttribute("matAffiOutFifo", JSONObject.parseObject(matAfiiOutFifo));

		return "hrp/mat/affi/out/matMatAffiOutInvFifo";

	}
	
	/**
	 * 代销出库查询材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutByInvHold", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutByInvHold(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("inv_id", mapVo.get("inv_id"));
		mapVo.put("inv_no", mapVo.get("inv_no"));
		mapVo.put("store_id", mapVo.get("store_id"));
		String matOutMain = matAffiOutCommonService.queryMatAffiOutByInvHold(getPage(mapVo));
		return JSONObject.parseObject(matOutMain);

	}
	
	/**
	 * 代销出库--整单出库页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/matAffiOutCommonWholeOutPage", method = RequestMethod.GET)
	public String matAffiOutCommonWholeOutPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_text", mapVo.get("dept_text"));

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/out/matAffiOutCommonWholeOut";
	}
	
	/**
	 * 代销出库--整单出库查询主表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutWholeMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutWholeMain(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		//转换日期
		if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date").toString())){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null&& !"".equals(mapVo.get("end_date").toString())){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}
		String matJson = matAffiOutCommonService.queryAffiInMain(getPage(mapVo));

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * 代销出库--整单出库查询明细材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutWholeInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutWholeInv(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matAffiOutCommonService.queryAffiInInv(getPage(mapVo));

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * 代销出库--整单出库组装明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutDetailByIsDir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutDetailByIsDir(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matAffiOutCommonService.queryAffiInWholeDetail(mapVo);

		return JSONObject.parseObject(matJson);

	}
	/**
	 * 代销出库--修改页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/matAffiOutCommonUpdatePage", method = RequestMethod.GET)
	public String matAffiOutCommonUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		Map<String, Object> matAffiOutMain = matAffiOutCommonService.queryByCode(mapVo);
		mapVo.put("bus_type_code", matAffiOutMain.get("bus_type_code"));
		Map<String, Object> matAffiOutId = matAffiOutCommonService.queryAffiOutIds(mapVo);
		
		if(matAffiOutMain.get("store_id") != null){
			mode.addAttribute("store_id", matAffiOutMain.get("store_id").toString()+","+matAffiOutMain.get("store_no").toString());
			mode.addAttribute("store_code", matAffiOutMain.get("store_code").toString()+" "+matAffiOutMain.get("store_name").toString());
		}
		if( matAffiOutMain.get("dept_id") != null){
			mode.addAttribute("dept_id", matAffiOutMain.get("dept_id").toString()+","+matAffiOutMain.get("dept_no").toString());
			mode.addAttribute("dept_code", matAffiOutMain.get("dept_code").toString()+" "+matAffiOutMain.get("dept_name").toString());
			
		}
		if( matAffiOutMain.get("dept_emp") != null){
			mode.addAttribute("dept_emp", matAffiOutMain.get("dept_emp").toString()+","+matAffiOutMain.get("emp_no").toString());
			mode.addAttribute("dept_emp_code", matAffiOutMain.get("dept_emp_code").toString()+" "+matAffiOutMain.get("dept_emp_name").toString());
		}
		
		mode.addAttribute("out_id", matAffiOutMain.get("out_id"));
		mode.addAttribute("out_no", matAffiOutMain.get("out_no"));
		mode.addAttribute("state", matAffiOutMain.get("state"));
		mode.addAttribute("group_id", matAffiOutMain.get("group_id"));
		mode.addAttribute("hos_id", matAffiOutMain.get("hos_id"));
		mode.addAttribute("copy_code", matAffiOutMain.get("copy_code"));
		mode.addAttribute("is_dir", matAffiOutMain.get("is_dir"));
		mode.addAttribute("out_date", matAffiOutMain.get("out_date"));
		mode.addAttribute("brief", matAffiOutMain.get("brief"));
		mode.addAttribute("out_idUp", matAffiOutId.get("out_idUp"));
		mode.addAttribute("out_idNext", matAffiOutId.get("out_idNext"));
		mode.addAttribute("use_code", matAffiOutMain.get("use_code"));
		mode.addAttribute("proj_id", matAffiOutMain.get("proj_id"));
		mode.addAttribute("bus_type_code", matAffiOutMain.get("bus_type_code"));
		
		Integer is_apply  = matAffiOutCommonService.queryMatAffiOutIsApply(mapVo);
		if(is_apply>0){
			mode.addAttribute("is_apply", "1");
		}else{
			mode.addAttribute("is_apply", "0");
		}
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04015", MyConfig.getSysPara("04015"));
		mode.addAttribute("p04024", MyConfig.getSysPara("04024"));
		mode.addAttribute("p04042", MyConfig.getSysPara("04042"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		return "hrp/mat/affi/out/matAffiOutCommonUpdate";

	}
	
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutDetaillById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutDetaillById(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String matOutDetail = matAffiOutCommonService.queryAffiOutDetailById(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);

	}
	
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutDetaillByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutDetaillByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String matOutDetail = matAffiOutCommonService.queryAffiOutDetailByCode(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);

	}
	
	
	
	/**
	 * @Description 修改页面：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutCommonDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutCommonDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matAffiOutCommonService.queryByCodeDetail(mapVo);

		return JSONObject.parseObject(matJson);

	}
	/**
	 * 代销出库--修改
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/updateMatAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatAffiOutCommon(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
			matJson = matAffiOutCommonService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		/*if(matJson.contains("true")){
			matAffiOutCommonService.updateMatAffiOutRela(mapVo);
		}*/
		return JSONObject.parseObject(matJson);

	}
	/**
	 * 审核 代销出库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/auditMatAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditMatAffiOutCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiOutCommonService.auditMatAffiOutCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 取消审核 代销出库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/unAuditMatAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unAuditMatAffiOutCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiOutCommonService.unAuditMatAffiOutCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 删除 代销出库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/deleteMatAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> deleteMatAffiOutCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			//用于删除与申请单之前的对应关系
			mapVo.put("rela_type", "3");
			mapVo.put("rela_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiOutCommonService.deleteMatAffiOutCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 复制  出库单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/copyMatAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> copyMatAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
	
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("maker", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			mapVo.put("out_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiOutCommonService.copyMatAffiOutCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 冲销  出库单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/offsetMatAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> offsetMatAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			mapVo.put("out_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			/*mapVo.put("make_date", date);
			
			if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
				mapVo.put("make_date", DateUtil.DateToString(mapVo.get("make_date").toString(), "yyyy-MM-dd"));
			}*/
			
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiOutCommonService.offsetMatAffiOutCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
		
	/**
	 * 出库确认  单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/confirmMatAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> confirmMatAffiOutCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String confirm_date=DateUtil.getSysDate();//获取服务器当前日期

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String user_id = SessionManager.getUserId();
		String matJson;
		try {
			
			
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("out_id", ids[3]);
				mapVo.put("confirmer", user_id);
				mapVo.put("state", 3);    //入库确认状态
				




				if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString())){
					confirm_date = ids[4];
				}

				String[] dates = confirm_date.split("-");
				String year = dates[0];
				String month = dates[1];
				
				mapVo.put("confirm_date", confirm_date);
				mapVo.put("year", year);
				mapVo.put("month", month);
				
				listVo.add(mapVo);
			}
		
			matJson = matAffiOutCommonService.confirmMatAffiOutCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/out/affiOutPrintSetPage", method = RequestMethod.GET)
	public String affiOutPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}
             //System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/out/queryMatAffiOutByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=matAffiOutCommonService.matAffiOutCommonService(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 添加页面选择材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/matAffiOutMainChoiceInvPage", method = RequestMethod.GET)
	public String matAffiOutMainChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/affi/out/matAffiOutMainChoiceInv";
	}
	
	/**
	 * 选择材料页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryAffiOutInvBatchList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAffiOutInvBatchList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matOutDetail = matAffiOutCommonService.queryMatAffiInvBatchList(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);
	}
	
	/**
	 * @Description 选择材料返回List
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/out/queryAffiOutInvListByChoiceInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAffiOutInvListByChoiceInv(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			matOutDetail = matAffiOutCommonService.queryAffiOutInvListByChoiceInv(listVo);
		} catch (Exception e) {
			matOutDetail = e.getMessage();
		}

		return JSONObject.parseObject(matOutDetail);
	}
}
