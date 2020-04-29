/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.affi.out;

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
import com.chd.hrp.med.service.affi.out.MedAffiOutCommonService;


/**
 * 
 * @Description:  代销出库
 * @Table: MED_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedAffiOutCommonController extends BaseController {

	private static Logger logger = Logger.getLogger(MedAffiOutCommonController.class);

	// 引入Service服务
	@Resource(name = "medAffiOutCommonService")
	private final MedAffiOutCommonService medAffiOutCommonService = null;
	
	/**
	 * @Description 代销出库--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/medAffiOutCommonMainPage", method = RequestMethod.GET)
	public String medAffiOutCommonMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08015", MyConfig.getSysPara("08015"));
		mode.addAttribute("p08024", MyConfig.getSysPara("08024"));
		
		return "hrp/med/affi/out/medAffiOutCommonMain";
	}
	
	/**
	 * 代销出库--主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutCommon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String medAffiOut = "";
		if(mapVo.get("show_detail").equals("1")){
			medAffiOut =  medAffiOutCommonService.queryDetails(getPage(mapVo));
		}else{
			medAffiOut =  medAffiOutCommonService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(medAffiOut);
	}
	
	/**
	 * 代销出库--添加页面  跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/medAffiOutCommonAddPage", method = RequestMethod.GET)
	public String medAffiOutCommonAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/out/medAffiOutCommonAdd";
	}
	
	/**
	 * @Description 
	 * 代销出库  添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/addMedAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedAffiOutCommon(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medJson;
		
		try {
			medJson = medAffiOutCommonService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		

	}
	/**
	 * 代销出库--历史导入
	 * medAffiOutCommonHisImpPage
	 */
	@RequestMapping(value = "/hrp/med/affi/out/medAffiOutCommonHisImpPage", method = RequestMethod.GET)
	public String medAffiOutCommonHisImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/out/medAffiOutCommonHisImp";
	}
	
	/**
	 * 代销出库--历史导入 查询明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutDetailHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutDetailHistory(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medJson = null;//medAffiOutCommonService.queryMedAffiOutDetailHistory(mapVo);

		return JSONObject.parseObject(medJson);

	}
	/**
	 * 代销出库--配套导入页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/medAffiOutCommonMatchImpPage", method = RequestMethod.GET)
	public String medAffiOutCommonMatchImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		//mode.addAttribute("store_text", mapVo.get("store_text"));
		//mode.addAttribute("store_text", mapVo.get("store_text"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/out/medAffiOutCommonMatchImp";
	}
	
	/**
	 * 配套入库 查询明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutMatchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutMatchDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medJson = medAffiOutCommonService.queryMedAffiOutMatchDetail(mapVo);

		return JSONObject.parseObject(medJson);

	}
	/**
	 * 代销出库 配套导入--组装明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutDetailByMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutDetailByMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medMsg = medAffiOutCommonService.queryMedAffiOutDetailByMatch(mapVo);

		return JSONObject.parseObject(medMsg);
	}
	
	
	/**
	 * @Description 选择药品   查询数据 med_fifo_balance
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutByFifoBalance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutByFifoBalance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String medOutMain = medAffiOutCommonService.queryMedAffiOutByFifoBalance(getPage(mapVo));
		return JSONObject.parseObject(medOutMain);

	}
	
	
	/**
	 * @Description 选择药品页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/medMedAffiOutInvFifoPage", method = RequestMethod.GET)
	public String medMedAffiOutInvFifoPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {
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

		//String medAfiiOutFifo = medAffiOutCommonService.queryMedAffiOutByInvHold(getPage(mapVo));
		//mode.addAttribute("inv_id", paraArray[0]);
		mode.addAttribute("store_id", paras);
		//mode.addAttribute("medAffiOutFifo", JSONObject.parseObject(medAfiiOutFifo));

		return "hrp/med/affi/out/medMedAffiOutInvFifo";

	}
	
	/**
	 * 代销出库查询药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutByInvHold", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutByInvHold(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String medOutMain = medAffiOutCommonService.queryMedAffiOutByInvHold(getPage(mapVo));
		return JSONObject.parseObject(medOutMain);

	}
	
	/**
	 * 代销出库--整单出库页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/medAffiOutCommonWholeOutPage", method = RequestMethod.GET)
	public String medAffiOutCommonWholeOutPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/out/medAffiOutCommonWholeOut";
	}
	
	/**
	 * 代销出库--整单出库查询主表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutWholeMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutWholeMain(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		String medJson = medAffiOutCommonService.queryAffiInMain(getPage(mapVo));

		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * 代销出库--整单出库查询明细药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutWholeInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutWholeInv(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medAffiOutCommonService.queryAffiInInv(getPage(mapVo));

		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * 代销出库--整单出库组装明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutDetailByIsDir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutDetailByIsDir(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medAffiOutCommonService.queryAffiInWholeDetail(mapVo);

		return JSONObject.parseObject(medJson);

	}
	/**
	 * 代销出库--修改页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/medAffiOutCommonUpdatePage", method = RequestMethod.GET)
	public String medAffiOutCommonUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		Map<String, Object> medAffiOutMain = medAffiOutCommonService.queryByCode(mapVo);
		mapVo.put("bus_type_code", medAffiOutMain.get("bus_type_code"));
		Map<String, Object> medAffiOutId = medAffiOutCommonService.queryAffiOutIds(mapVo);
		
		if(medAffiOutMain.get("store_id") != null){
			mode.addAttribute("store_id", medAffiOutMain.get("store_id").toString()+","+medAffiOutMain.get("store_no").toString());
			mode.addAttribute("store_code", medAffiOutMain.get("store_code").toString()+" "+medAffiOutMain.get("store_name").toString());
		}
		if( medAffiOutMain.get("dept_id") != null){
			mode.addAttribute("dept_id", medAffiOutMain.get("dept_id").toString()+","+medAffiOutMain.get("dept_no").toString());
			mode.addAttribute("dept_code", medAffiOutMain.get("dept_code").toString()+" "+medAffiOutMain.get("dept_name").toString());
			
		}
		if( medAffiOutMain.get("dept_emp") != null){
			mode.addAttribute("dept_emp", medAffiOutMain.get("dept_emp").toString()+","+medAffiOutMain.get("emp_no").toString());
			mode.addAttribute("dept_emp_code", medAffiOutMain.get("dept_emp_code").toString()+" "+medAffiOutMain.get("dept_emp_name").toString());
		}
		
		mode.addAttribute("out_id", medAffiOutMain.get("out_id"));
		mode.addAttribute("out_no", medAffiOutMain.get("out_no"));
		mode.addAttribute("state", medAffiOutMain.get("state"));
		mode.addAttribute("group_id", medAffiOutMain.get("group_id"));
		mode.addAttribute("hos_id", medAffiOutMain.get("hos_id"));
		mode.addAttribute("copy_code", medAffiOutMain.get("copy_code"));
		mode.addAttribute("is_dir", medAffiOutMain.get("is_dir"));
		mode.addAttribute("out_date", medAffiOutMain.get("out_date"));
		mode.addAttribute("brief", medAffiOutMain.get("brief"));
		mode.addAttribute("out_idUp", medAffiOutId.get("out_idUp"));
		mode.addAttribute("out_idNext", medAffiOutId.get("out_idNext"));
		mode.addAttribute("use_code", medAffiOutMain.get("use_code"));
		mode.addAttribute("proj_id", medAffiOutMain.get("proj_id"));
		mode.addAttribute("bus_type_code", medAffiOutMain.get("bus_type_code"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08015", MyConfig.getSysPara("08015"));
		mode.addAttribute("p08024", MyConfig.getSysPara("08024"));
		mode.addAttribute("p08042", MyConfig.getSysPara("08042"));
		
		return "hrp/med/affi/out/medAffiOutCommonUpdate";

	}
	
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutDetaill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutDetaill(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medOutDetail = medAffiOutCommonService.queryAffiOutDetailById(getPage(mapVo));

		return JSONObject.parseObject(medOutDetail);

	}
	
	
	
	/**
	 * @Description 修改页面：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutCommonDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutCommonDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medAffiOutCommonService.queryByCodeDetail(mapVo);

		return JSONObject.parseObject(medJson);

	}
	/**
	 * 代销出库--修改
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/updateMedAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedAffiOutCommon(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
			medJson = medAffiOutCommonService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);

	}
	/**
	 * 审核 代销出库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/auditMedAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditMedAffiOutCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medAffiOutCommonService.auditMedAffiOutCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * 取消审核 代销出库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/unAuditMedAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unAuditMedAffiOutCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medAffiOutCommonService.unAuditMedAffiOutCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * 删除 代销出库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/deleteMedAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> deleteMedAffiOutCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medAffiOutCommonService.deleteMedAffiOutCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * 复制  出库单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/copyMedAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> copyMedAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medAffiOutCommonService.copyMedAffiOutCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * 冲销  出库单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/offsetMedAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> offsetMedAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medAffiOutCommonService.offsetMedAffiOutCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
		
	/**
	 * 出库确认  单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/confirmMedAffiOutCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> confirmMedAffiOutCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String user_id = SessionManager.getUserId();
		String medJson;
		try {
			String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
			String year = dates[0];
			String month = dates[1];
			
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
				mapVo.put("confirm_date", date);
				mapVo.put("year", year);
				mapVo.put("month", month);
				
				listVo.add(mapVo);
			}
		
			medJson = medAffiOutCommonService.confirmMedAffiOutCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/out/affiOutPrintSetPage", method = RequestMethod.GET)
	public String affiOutPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}
             System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/out/queryMedAffiOutByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=medAffiOutCommonService.medAffiOutCommonService(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 添加页面选择药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/medAffiOutMainChoiceInvPage", method = RequestMethod.GET)
	public String medAffiOutMainChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/affi/out/medAffiOutMainChoiceInv";
	}
	
	/**
	 * 选择药品页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryAffiOutInvBatchList", method = RequestMethod.POST)
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

		String medOutDetail = medAffiOutCommonService.queryMedAffiInvBatchList(getPage(mapVo));

		return JSONObject.parseObject(medOutDetail);
	}
	
	/**
	 * @Description 选择药品返回List
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/out/queryAffiOutInvListByChoiceInv", method = RequestMethod.POST)
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
		
		String medOutDetail;
		try {
			medOutDetail = medAffiOutCommonService.queryAffiOutInvListByChoiceInv(listVo);
		} catch (Exception e) {
			medOutDetail = e.getMessage();
		}

		return JSONObject.parseObject(medOutDetail);
	}
}
