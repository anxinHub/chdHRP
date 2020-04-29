package com.chd.hrp.acc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.acc.entity.TmpAccLedger;
import com.chd.hrp.acc.entity.TmpAccSubj;
import com.chd.hrp.acc.service.AccNewOldJoinService;

/**
 * 新旧制度衔接
 * 
 * @author yang
 *
 */
@Controller
@RequestMapping(value = "/hrp/acc/join")
public class AccNewOldJoinController extends BaseController {

	private static Logger logger = Logger.getLogger(AccNewOldJoinController.class);

	@Resource(name = "accNewOldJoinService")
	private final AccNewOldJoinService accNewOldJoinService = null;

	/**
	 * 主页面
	 */
	@RequestMapping(value = "accNewOldJoinMainPage", method = RequestMethod.GET)
	public String mainPage() throws Exception {
		return "hrp/acc/newOldJoin/accNewOldJoinMain";
	}
	
	/**
	 * 会计科目编辑页（添加）
	 */
	@RequestMapping(value = "tmpAccSubjAdd", method = RequestMethod.GET)
	public String subjAdd(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		if(paramMap.get("caseStr") == null || paramMap.get("caseStr").toString() == ""){
			throw new SysException("参数不完整！");
		}
		
		if("oldSubj".equals(paramMap.get("caseStr").toString()) 
			|| "newSubj".equals(paramMap.get("caseStr").toString())){
			mode.addAttribute("caseStr", paramMap.get("caseStr").toString());
			return "hrp/acc/newOldJoin/tmpAccSubjEdit";
		}
		throw new SysException("参数不正确！");
	}
	
	/**
	 * 会计科目编辑页（修改）
	 */
	@RequestMapping(value = "tmpAccSubjUpdate", method = RequestMethod.GET)
	public String subjUpdate(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		if(paramMap.get("caseStr") == null || paramMap.get("caseStr").toString() == ""){
			throw new SysException("参数不完整！");
		}
		
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		boolean flag = false;
		mode.addAttribute("caseStr", paramMap.get("caseStr").toString());
		TmpAccSubj subj = null;
		if("oldSubj".equals(paramMap.get("caseStr").toString()) ){
			flag = true;
			subj = accNewOldJoinService.getTmpAccSubjOld(paramMap);
		}else if("newSubj".equals(paramMap.get("caseStr").toString())){
			flag = true;
			subj = accNewOldJoinService.getTmpAccSubjNew(paramMap);
		}
		mode.addAttribute("tmpAccSubj", subj);
		if(flag){
			return "hrp/acc/newOldJoin/tmpAccSubjEdit";
		}
		throw new SysException("参数不正确！");
	}
	
	/**
	 * 会计科目余额添加页面
	 */
	@RequestMapping(value = "openTmpAccLedgerAdd", method = RequestMethod.GET)
	public String ledgerAddPage(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		if(paramMap.get("caseStr") == null || paramMap.get("caseStr").toString() == ""){
			throw new SysException("参数不完整！");
		}
		mode.addAttribute("caseStr", paramMap.get("caseStr").toString());
		return "hrp/acc/newOldJoin/tmpAccLedgerEdit";
	}
	
	/**
	 * 会计科目余额编辑页
	 */
	@RequestMapping(value = "openTmpAccLedgerEdit", method = RequestMethod.GET)
	public String ledgerUpdatePage(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		if(paramMap.get("caseStr") == null || paramMap.get("caseStr").toString() == ""){
			throw new SysException("参数不完整！");
		}
		
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("caseStr", paramMap.get("caseStr").toString());
		boolean flag = false;
		TmpAccLedger ledger = null;
		TmpAccSubj subj = null;
		if("oldLedger".equals(paramMap.get("caseStr").toString()) ){
			flag = true;
			ledger = accNewOldJoinService.getTmpAccLedgerOld(paramMap);
			subj = accNewOldJoinService.getTmpAccSubjOld(paramMap);
		}else if("newLedger".equals(paramMap.get("caseStr").toString())){
			flag = true;
			ledger = accNewOldJoinService.getTmpAccLedgerNew(paramMap);
			subj = accNewOldJoinService.getTmpAccSubjNew(paramMap);
		}
		mode.addAttribute("tmpAccLedger", ledger);
		mode.addAttribute("tmpAccSubj", subj);
		if(flag){
			return "hrp/acc/newOldJoin/tmpAccLedgerEdit";
		}
		throw new SysException("参数不正确！");
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	// 旧科目
	/**
	 * 查旧科目
	 */
	@RequestMapping(value = "queryAccSubjOld", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjOld(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		String json = accNewOldJoinService.queryAccSubjOld(getPage(paramMap));
		return JSONObject.parseObject(json);
	}
	
	/**
	 * 从excel导入旧科目
	 */
	@RequestMapping(value = "importAccSubjOld", method = RequestMethod.POST)
	@ResponseBody
	public String importAccSubjOld(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return accNewOldJoinService.importAccSubjOld(paramMap);
	}
	
//	/**
//	 * 导出旧科目
//	 */
//	@RequestMapping(value = "exportAccSubjOld", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> exportAccSubjOld(@RequestParam Map<String, Object> paramMap, Model mode)
//			throws Exception {
//		
//		return null;
//	}
	
	/**
	 * 导入系统数据
	 */
	@RequestMapping(value = "importSystemAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importSystemAccSubjOld(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		String recode = "";
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		paramMap.put("acc_year", SessionManager.getAcctYear());
		try {
			if(paramMap.get("caseStr").equals("oldSubj")){			//旧科目
				recode = accNewOldJoinService.importSystemAccSubjOldOldSubj(paramMap);
			}else if(paramMap.get("caseStr").equals("oldLedger")){	//旧余额
				recode = accNewOldJoinService.importSystemAccSubjOldLedger(paramMap);
			}
//			else if(paramMap.get("caseStr").equals("newSubj")){	//新科目
//				recode = accNewOldJoinService.importSystemAccSubjNewSubj(paramMap);
//			}else if(paramMap.get("caseStr").equals("newLedger")){	//新余额
//				return null;
//			}
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		return JSON.parseObject(recode);
	}
	
	/**
	 * 删除旧科目
	 */
	@RequestMapping(value = "deleteAccSubjOld", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccSubjOld(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		try{
			String json = accNewOldJoinService.deleteAccSubjOld(paramMap);
			return JSONObject.parseObject(json);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 添加科目
	 */
	@RequestMapping(value = "addTmpAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTmpAccSubj(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try{
			String caseStr = null;
			if(paramMap.get("caseStr") != null && paramMap.get("caseStr").toString() != ""){
				caseStr = paramMap.get("caseStr").toString();
			}else{
				return JSONObject.parseObject("{\"error\":\"添加失败，缺少条件参数.\",\"state\":\"false\"}");
			}
			
			paramMap.put("group_id", SessionManager.getGroupId());
			paramMap.put("hos_id", SessionManager.getHosId());
			paramMap.put("copy_code", SessionManager.getCopyCode());
			String json = null;
			if("oldSubj".equals(caseStr)){
				json = accNewOldJoinService.addTmpAccSubjOld(paramMap);
			}else if("newSubj".equals(caseStr)){
				json = accNewOldJoinService.addTmpAccSubjNew(paramMap);
			}else{
				return JSONObject.parseObject("{\"error\":\"添加失败，条件参数不合法.\",\"state\":\"false\"}");
			}
			return JSONObject.parseObject(json);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 更新科目
	 */
	@RequestMapping(value = "updateTmpAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTmpAccSubj(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try{
			String caseStr = null;
			if(paramMap.get("caseStr") != null && paramMap.get("caseStr").toString() != ""){
				caseStr = paramMap.get("caseStr").toString();
			}else{
				return JSONObject.parseObject("{\"error\":\"添加失败，缺少条件参数.\",\"state\":\"false\"}");
			}
			
			paramMap.put("group_id", SessionManager.getGroupId());
			paramMap.put("hos_id", SessionManager.getHosId());
			paramMap.put("copy_code", SessionManager.getCopyCode());
			String json = null;
			if("oldSubj".equals(caseStr)){
				json = accNewOldJoinService.updateTmpAccSubjOld(paramMap);
			}else if("newSubj".equals(caseStr)){
				json = accNewOldJoinService.updateTmpAccSubjNew(paramMap);
			}else{
				return JSONObject.parseObject("{\"error\":\"添加失败，条件参数不合法.\",\"state\":\"false\"}");
			}
			return JSONObject.parseObject(json);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
	}
	
//	/**
//	 * 转换
//	 */
//	@RequestMapping(value = "converSubjNewOld", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> converSubjNewOld(@RequestParam Map<String, Object> paramMap, Model mode)
//			throws Exception {
//		
//		return null;
//	}
	
	/**
	 * 新科目重新生成
	 */
	@RequestMapping(value = "regenerateAccSubjOld", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> regenerateAccSubjOld(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		String str = accNewOldJoinService.regenerateAccSubjOld();
		return JSON.parseObject(str);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 旧余额
	/**
	 * 查旧余额
	 */
	@RequestMapping(value = "queryAccLedgerOld", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccLedgerOld(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		String json = accNewOldJoinService.queryAccLedgerOld(getPage(paramMap));
		return JSONObject.parseObject(json);
	}
	
	/**
	 * Excel导入旧余额
	 */
	@RequestMapping(value = "importAccLedgerOld", method = RequestMethod.POST)
	@ResponseBody
	public String importAccLedgerOld(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return accNewOldJoinService.importAccLedgerOld(paramMap);
	}
	
//	/**
//	 * 导出旧余额
//	 */
//	@RequestMapping(value = "exportAccLedgerOld", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> exportAccLedgerOld(@RequestParam Map<String, Object> paramMap, Model mode)
//			throws Exception {
//		
//		return null;
//	}
	
	/**
	 * 删除旧余额
	 */
	@RequestMapping(value = "deleteAccLedgerOld", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccLedgerOld(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		try{
			String json = accNewOldJoinService.deleteAccLedgerOld(paramMap);
			return JSONObject.parseObject(json);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
	}
	
//	/**
//	 * 转换
//	 */
//	@RequestMapping(value = "converLedgerNewOld", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> converLedgerNewOld(@RequestParam Map<String, Object> paramMap, Model mode)
//			throws Exception {
//		
//		return null;
//	}
	
	/**
	 * 从旧余额生成新余额
	 */
	@RequestMapping(value = "regenerateAccSubjnewLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> regenerateAccSubjnewLedger(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return JSON.parseObject(accNewOldJoinService.regenerateAccSubjnewLedger());
	}
	
	/**
	 * 辅助核算表头
	 */
	@RequestMapping(value = "getSubjCheckTitle", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getSubjCheckTitle(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception {
		if(paramMap.get("caseStr").toString().equals("oldLedger")){
			paramMap.put("table_name_subj", "tmp_acc_subj_old");
		}else if(paramMap.get("caseStr").toString().equals("newLedger")){
			paramMap.put("table_name_subj", "tmp_acc_subj_new");
		}
		return accNewOldJoinService.getSubjCheckTitle(paramMap);
	}
	
	/**
	 * 查询旧科目辅助核算
	 */
	@RequestMapping(value = "queryLedgerCheckListOld", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryLedgerCheckListOld(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception {
		paramMap.put("table_name", "tmp_acc_subj_old");
		paramMap.put("table_name_check", "tmp_acc_ledger_check_old");
		String json = accNewOldJoinService.queryLedgerCheckList(paramMap);
		return JSONObject.parseObject(json);
	}
	
	/**
	 * 保存余额
	 */
	@RequestMapping(value = "addTmpAccLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTmpAccLedger(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception {
		String caseStr = paramMap.get("caseStr").toString();
		if("oldLedger".equals(caseStr)){
			paramMap.put("table_name", "tmp_acc_ledger_old");
			paramMap.put("table_name_subj", "tmp_acc_subj_old");
			paramMap.put("table_name_check", "tmp_acc_ledger_check_old");
		}else if("newLedger".equals(caseStr)){
			paramMap.put("table_name", "tmp_acc_ledger_new");
			paramMap.put("table_name_subj", "tmp_acc_subj_new");
			paramMap.put("table_name_check", "tmp_acc_ledger_check_new");
		}
		String json = accNewOldJoinService.saveTmpAccLedger(paramMap);
		return JSONObject.parseObject(json);
	}
	
	/**
	 * 辅助核算的excel导入(旧)
	 */
	@RequestMapping(value = "impTmpAccLedgerCheckOldByExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> impTmpAccLedgerCheckOldByExcel(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		Map<String, Object> table = new HashMap<String, Object>();
		table.put("table_name_check", "tmp_acc_ledger_check_old");
		table.put("table_name_subj", "tmp_acc_subj_old");
		table.put("table_name_ledger", "tmp_acc_ledger_old");
		paramMap.put("table", table);
		String json = accNewOldJoinService.impTmpAccLedgerCheckByExcel(paramMap);
		return JSONObject.parseObject(json);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 新科目
	/**
	 * 查新科目
	 */
	@RequestMapping(value = "queryAccSubjNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjNew(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		String json = accNewOldJoinService.queryAccSubjNew(getPage(paramMap));
		return JSONObject.parseObject(json);
	}
	
	/**
	 * 从excel导入新科目
	 */
	@RequestMapping(value = "importAccSubjNew", method = RequestMethod.POST)
	@ResponseBody
	public String importAccSubjNew(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return accNewOldJoinService.importAccSubjNew(paramMap);
	}
	
//	/**
//	 * 导出新科目
//	 */
//	@RequestMapping(value = "exportAccSubjNew", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> exportAccSubjNew(@RequestParam Map<String, Object> paramMap, Model mode)
//			throws Exception {
//		
//		return null;
//	}
	
	/**
	 * 删除新科目
	 */
	@RequestMapping(value = "deleteAccSubjNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccSubjNew(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		try{
			String json = accNewOldJoinService.deleteAccSubjNew(paramMap);
			return JSONObject.parseObject(json);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 新余额
	/**
	 * 查新余额
	 */
	@RequestMapping(value = "queryAccLedgerNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccLedgerNew(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		String json = accNewOldJoinService.queryAccLedgerNew(getPage(paramMap));
		return JSONObject.parseObject(json);
	}
	
	/**
	 * 删除新余额
	 */
	@RequestMapping(value = "deleteAccLedgerNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccLedgerNew(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		try{
			String json = accNewOldJoinService.deleteAccLedgerNew(paramMap);
			return JSONObject.parseObject(json);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 查询新科目辅助核算
	 */
	@RequestMapping(value = "queryLedgerCheckListNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryLedgerCheckListNew(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception {
		paramMap.put("table_name", "tmp_acc_subj_new");
		paramMap.put("table_name_check", "tmp_acc_ledger_check_new");
		String json = accNewOldJoinService.queryLedgerCheckList(paramMap);
		return JSONObject.parseObject(json);
	}
	
	/**
	 * 从excel导入新余额
	 */
	@RequestMapping(value = "importAccLedgerNew", method = RequestMethod.POST)
	@ResponseBody
	public String importAccLedgerNew(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return accNewOldJoinService.importAccLedgerNew(paramMap);
	}
	
	/**
	 * 辅助核算的excel导入(新)
	 */
	@RequestMapping(value = "impTmpAccLedgerCheckNewByExcel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> impTmpAccLedgerCheckNewByExcel(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		Map<String, Object> table = new HashMap<String, Object>();
		table.put("table_name_check", "tmp_acc_ledger_check_new");
		table.put("table_name_subj", "tmp_acc_subj_new");
		table.put("table_name_ledger", "tmp_acc_ledger_new");
		paramMap.put("table", table);
		String json = accNewOldJoinService.impTmpAccLedgerCheckByExcel(paramMap);
		return JSONObject.parseObject(json);
	}
	
//	/**
//	 * 导出新余额
//	 */
//	@RequestMapping(value = "exportAccLedgerNew", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> exportAccLedgerNew(@RequestParam Map<String, Object> paramMap, Model mode)
//			throws Exception {
//		
//		return null;
//	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 对应关系
	/**
	 * 对应关系
	 */
	@RequestMapping(value = "queryAccSubjMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjMap(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		String json = accNewOldJoinService.queryAccSubjMap(getPage(paramMap));
		return JSONObject.parseObject(json);
	}
	
	/**
	 * 重新生成科目映射关系
	 */
	@RequestMapping(value = "regenerateAccSubjnewOldMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> regenerateAccSubjnewOldMap(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return JSON.parseObject(accNewOldJoinService.regenerateAccSubjnewOldMap());
	}
	
	/**
	 * 正式载入新科目
	 */
	@RequestMapping(value = "addOfficiallyNewSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOfficiallyNewSubj(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		return JSON.parseObject(accNewOldJoinService.addOfficiallyNewSubj(paramMap));
	}
	
	/**
	 * 对照修改页面跳转
	 */
	@RequestMapping(value = "AccTranMapUpdatePage", method = RequestMethod.GET)
	public String AccTranMapUpdatePage(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		mode.addAttribute("subj_code_old",paramMap.get("subj_code_old"));
		mode.addAttribute("subj_code_new",paramMap.get("subj_code_new"));
		mode.addAttribute("subj_code_new_b",paramMap.get("subj_code_new_b"));
		return "hrp/acc/newOldJoin/AccTranMapUpdate";
	}

	/**
	 * 正式载入新余额与辅助核算
	 */
	@RequestMapping(value = "ledgerAndCheckIntoSys", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ledgerAndCheckIntoSys(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		return JSON.parseObject(accNewOldJoinService.ledgerAndCheckIntoSys(paramMap));
	}
	
	/**
	 * 查新余额是否已经存在系统中
	 */
	@RequestMapping(value = "queryNewLedgerExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryNewLedgerExists(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		return JSON.parseObject(accNewOldJoinService.queryNewLedgerExists(paramMap));
	}
	
	/**
	 * 对照修改页面元科目下拉框
	 */
	@RequestMapping(value = "querySubjcodeoldSelect", method = RequestMethod.POST)
	@ResponseBody
	public String querySubj_code_oldSelect(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return JSON.toJSONString(accNewOldJoinService.querySubjCodeOldSelect());
	}
	
	/**
	 * 对照修改页面新科目下拉框
	 */
	@RequestMapping(value = "querySubjcodenewSelect", method = RequestMethod.POST)
	@ResponseBody
	public String querySubj_code_newSelect(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return JSON.toJSONString(accNewOldJoinService.querySubjcodenewSelect(paramMap));
	}
	
	/**
	 * 对照修改
	 */
	@RequestMapping(value = "updateTranMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTranMap(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return JSON.parseObject(accNewOldJoinService.updateTranMap(paramMap));
	}
	
	/**
	 * 对照修改
	 */
	@RequestMapping(value = "deleteAccNewOldMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccNewOldMap(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return JSON.parseObject(accNewOldJoinService.deleteAccNewOldMap(paramMap));
	}
	
	/**
	 * 科目映射关系excel导入
	 */
	@RequestMapping(value = "importAccTranMap", method = RequestMethod.POST)
	@ResponseBody
	public String importAccTranMap(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		return accNewOldJoinService.importAccTranMap(paramMap);
	}
	
}
