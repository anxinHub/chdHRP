package com.chd.hrp.acc.controller.vouch;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.ftp.Base64;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.superVouch.SuperVouchMain;
import com.chd.hrp.acc.service.paper.AccPaperTypeService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.sys.service.ModStartService;

/** 
 * @Title. @Description. 超级凭证Controller 
 * @Author: Perry
 * @Version: 1.0
 */ 
@Controller
@RequestMapping(value="/hrp/acc/accvouch/superVouch")
public class SuperVouchController extends BaseController{

	private static Logger logger = Logger.getLogger(SuperVouchController.class);
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	@Resource(name = "accPaperTypeService")
	private final AccPaperTypeService accPaperTypeService = null;
	
	@Resource(name = "modStartService")
	private final ModStartService modStartService = null; 
	
	/**
	 * 凭证制单-主页面
	*/
	@RequestMapping(value = "/superVouchMainPage", method = RequestMethod.GET)
	public String superVouchMainPage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		String vouchId=reMap.get("vouch_id")==null?"":reMap.get("vouch_id").toString();
		String openType=reMap.get("openType")==null?"newVouch":reMap.get("openType").toString();
		
		reMap.put("group_id", SessionManager.getGroupId());
		reMap.put("hos_id", SessionManager.getHosId());
		reMap.put("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		
/*		//取会计启用年度
		reMap.put("mod_code", "01");
		ModStart modStart=new ModStart();
		modStart=modStartService.queryModStartByCode(reMap);
		if(modStart.getStart_year()==null || modStart.getStart_month()==null || modStart.getStart_year().equals("") || modStart.getStart_month().equals("")){
			return "redirect:../../mod/modMainPage.do?isCheck=false";
		}
		*/
		//凭证日期最小日期
		String accYearMonth=MyConfig.getCurAccYearMonth();
		accYearMonth=accYearMonth.substring(0,4)+"-"+accYearMonth.substring(4,6)+"-01";
		mode.addAttribute("minDate",accYearMonth);
		mode.addAttribute("openType",openType);
		if(reMap.containsKey("openBusiType")){
			mode.addAttribute("isParentQuery", "1");
		}
		
		//凭证制单所有参数
		String paraList=superVouchService.queryVouchParaList(reMap);
		//logger.debug(paraList);
		mode.addAttribute("paraList", paraList);
		
		
		//查询凭证主表
		SuperVouchMain svm=new SuperVouchMain();
		//自动凭证
		if(reMap.get("openType")!=null && reMap.get("openType").equals("autoVouch")){
			mode.addAttribute("superVouchMain", svm);
			return "hrp/acc/accvouch/superVouch/main";
			
		}
		
		if(!vouchId.equals("")){
			svm=superVouchService.querySuperVouchByMain(reMap);
		}
		
		if(svm==null)svm=new SuperVouchMain();
		
		if(svm.getVouch_id()==null || svm.getVouch_id().equals("") || vouchId.equals("")){
			//没有查询到凭证				
			svm.setCreate_user(Long.parseLong(SessionManager.getUserId()));
			svm.setCreate_name(SessionManager.getUserName());
			svm.setState("1");
			
			//根据系统参数029，取最大凭证日期
			reMap.put("p029", MyConfig.getSysPara("029"));
			String vochDate=superVouchService.queryMaxVouchDate(reMap);
			svm.setVouch_date(vochDate);
			
		}else{
			if(!svm.getHos_id().equals(SessionManager.getHosId()) || !svm.getCopy_code().equals(SessionManager.getCopyCode())){
				//因为账簿可以跨账套查询，如果该凭证的的医院或者账套不等于当前系统的，凭证不可以编辑
				mode.addAttribute("is_other_sys", 1);
			}
		}
	
		mode.addAttribute("superVouchMain", svm);
		return "hrp/acc/accvouch/superVouch/main";

	}
	
	/**
	 * 凭证制单-分录页面，通过iframe跳转
	*/
	@RequestMapping(value = "/vouchPage", method = RequestMethod.GET)
	public String vouchPage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		//查询会计科目
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
				
		return "hrp/acc/accvouch/superVouch/vouch";
	
	}
	
	
	/**
	 * 页面没有加载科目和摘要时，通过ajax加载科目和摘要
	 * 凭证只有只读的权限时，不会加载科目和摘要
	*/
	@RequestMapping(value = "/accVouchDictList", method = RequestMethod.POST)
	@ResponseBody
	public String accVouchDictList(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		 
		//查询会计科目
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(!reMap.containsKey("acc_year")){
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}else{
			mapVo.put("acc_year",reMap.get("acc_year"));
		}
		//加载科目列表
		String dictList="[]";
		if(reMap.get("flag").equals("1")){
			dictList=superVouchService.queryAccVouchSubjDict(mapVo);
			
		}
		//加载摘要列表
		else if(reMap.get("flag").equals("2")){
			dictList=superVouchService.queryVouchSummaryDict(mapVo);
		}
		
		//logger.debug(dictList);
		if(dictList==null || dictList.equals("")){
			dictList="[]";
		}
		
		return dictList;
	
	}
	
	
	
	/**
	 * 查询最大凭证号<BR>
	 */
	@RequestMapping(value = "/queryMaxVouchNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMaxVouchNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String vouchDate=mapVo.get("vouch_date").toString();
		mapVo.put("acc_year", vouchDate.substring(0, 4));
		mapVo.put("acc_month", vouchDate.substring(5, 7));
		String reMaxVouchNo=superVouchService.queryMaxVouchNo(mapVo);
		if(reMaxVouchNo.indexOf("error")!=-1){
			return JSONObject.parseObject(reMaxVouchNo);
		}else{
			return JSONObject.parseObject("{\"vn\":\""+reMaxVouchNo+"\"}");
		}
	}
	
	
	/**
	 * 查询最大凭证日期<BR>
	 */
	@RequestMapping(value = "/queryMaxVouchDate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMaxVouchDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		return JSONObject.parseObject("{\"vouch_date\":\""+reMaxVouchDate+"\"}");
	}
	
	/**
	 *  根据系统参数010取凭证类型<BR>
	 */
	@RequestMapping(value = "/querySuperVouchTypeByPerm", method = RequestMethod.POST)
	@ResponseBody
	public String querySuperVouchTypeByPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String resJson=superVouchService.querySuperVouchTypeByPerm(mapVo);
		return resJson;
		
	}
	
	/**
	 *  凭证制单-取凭证审核流程
	 */
	@RequestMapping(value = "/queryVouchFlow", method = RequestMethod.POST)
	@ResponseBody
	public String queryVouchFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String resJson=superVouchService.queryVouchFlow(mapVo);
		return resJson;
		
	}
	
	
	/**
	 * 凭证制单-辅助核算页面
	 */
	@RequestMapping(value = "/accVouchCheckPage", method = RequestMethod.GET)
	public String accVouchCheckPage(@RequestParam Map<String, Object> mapVo, Model mode,HttpSession session) throws Exception {
		//加载辅助核算相关信息
		mode.addAttribute("id",mapVo.get("id"));
		mode.addAttribute("rowNumber",mapVo.get("rowNumber"));
		mode.addAttribute("cellNumber",mapVo.get("cellNumber"));
		mode.addAttribute("vouch_id", mapVo.get("vouch_id"));
		mode.addAttribute("detail_id", mapVo.get("detail_id"));
		mode.addAttribute("state", mapVo.get("state"));
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("debit", (String) mapVo.get("debit"));
		mode.addAttribute("credit", (String) mapVo.get("credit"));
		mode.addAttribute("coulmn_name",mapVo.get("coulmn_name"));
		
		String checkJson="[]";
		if(mapVo.get("is_check").equals("1")){
			//凭证制单-辅助核算-根据科目查询核算类型
			Map<String, Object> entityMap=new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			//entityMap.put("acc_year", SessionManager.getAcctYear());
			entityMap.put("subj_code", mapVo.get("subj_code"));
			if(mapVo.get("acc_year")==null || mapVo.get("acc_year").toString().equals("")){
				entityMap.put("acc_year", SessionManager.getAcctYear());
			}else{
				entityMap.put("acc_year", mapVo.get("acc_year"));
			}
			
			checkJson=superVouchService.queryVouchCheckType(entityMap);
		}

		mode.addAttribute("checkJson", checkJson);
		return "hrp/acc/accvouch/superVouch/vouchCheck";
		
	}
	
	
	/**
	 * 凭证制单-主页面辅助核算表单
	 */
	@RequestMapping(value = "/queryVouchCheckType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryVouchCheckType(@RequestParam Map<String, Object> mapVo) throws Exception {
		
		String checkJson="[]";
		Map<String,Object> reMap=new HashMap<String, Object>();
		try {
			//凭证制单-辅助核算-根据科目查询核算类型
			Map<String, Object> entityMap=new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			//entityMap.put("acc_year", SessionManager.getAcctYear());
			entityMap.put("subj_code", mapVo.get("subj_code"));
			if(mapVo.get("acc_year")==null || mapVo.get("acc_year").toString().equals("")){
				entityMap.put("acc_year", SessionManager.getAcctYear());
			}else{
				entityMap.put("acc_year", mapVo.get("acc_year"));
			}
			
			checkJson=superVouchService.queryVouchCheckType(entityMap);
			reMap.put("data", checkJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reMap.put("error", e.getMessage());
		}
		return reMap;
		
	}
	
	
	/**
	 * 凭证制单-辅助核算导入页面
	 */
	@RequestMapping(value = "/importCheckPage", method = RequestMethod.GET)
	public String importCheckPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/accvouch/superVouch/importCheck";
	}
	
	/**
	 * 凭证制单-辅助核算导入
	 */
	@RequestMapping(value = "/importCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			mapVo.put("is_imp", "1");
			
			String reJson=superVouchService.importCheck(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
	}
	
	/**
	 * 加载辅助核算下拉字典，通过ajax加载
	 * 凭证只有只读的权限时，不会加载 
	*/ 
	@RequestMapping(value = "/accCheckDictList", method = RequestMethod.POST)
	@ResponseBody
	public String accCheckDictList(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		//查询会计科目
		reMap.put("group_id", SessionManager.getGroupId());
		reMap.put("hos_id", SessionManager.getHosId());
		reMap.put("copy_code", SessionManager.getCopyCode());
		reMap.put("acc_year", SessionManager.getAcctYear());
		//reMap.put("cash_dire", reMap.get("cash_dire"));
		
		List<Map<String,Object>> dictList=new ArrayList<Map<String,Object>>();
		if(reMap.get("column_check")!=null && reMap.get("column_check").toString().equalsIgnoreCase("check2")){
			//职工辅助核算，显示部门名称
			reMap.put("is_show","emp_dept_name");
		}
		
		if(reMap.get("column_check")!=null && reMap.get("column_check").toString().equalsIgnoreCase("check3")){
			//项目辅助核算，显示职工名称
			reMap.put("is_show","proj_emp_name");
		}
		
		
		dictList=superVouchService.queryVouchCheckItemDict(reMap);
		String reJson="[]";
		if(dictList!=null && dictList.size()>0){
			reJson=JSON.toJSONString(dictList);
		}
		
		return reJson;
	
	}
	
	/**
	 * 凭证保存
	 * @param mapVo
	 * @param mode
	 */
	@RequestMapping(value = "/saveSuperVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSuperVouch(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		String reJson=null;
		
		if(mapVo==null || mapVo.size()==0){
			reJson="{\"error\":\"没有数据要保存。\"}";
		}
		
		if(!mapVo.get("vouchId").toString().equals("")){
			//p005=0:不允许修改他们凭证
			if(mapVo.get("p005").toString().equals("0") && !mapVo.get("createUser").toString().equals(SessionManager.getUserId())){
					reJson="{\"error\":\"没有数据要保存。\"}";
			}
			
			//不是草稿凭证、新建凭证
			if(!mapVo.get("state").toString().equals("-1") || !mapVo.get("state").toString().equals("1")){
				reJson="{\"error\":\"没有数据要保存。\"}";
			}
			
		}
		
		try {
			reJson=superVouchService.saveSuperVouch(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	//删除凭证操作：删除凭证相关联的表
	@RequestMapping(value = "/deleteSuperVouchByVouchId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSuperVouchByVouchId(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		String res="";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		res=superVouchService.deleteSuperVouchByVouchId(mapVo);
		if(res!=null && res.equals("ok")){
			res="{\"msg\":\"删除成功。\",\"state\":\"true\"}";
		}else{
			res="{\"error\":\""+res+"\",\"state\":\"false\"}";
		}
		return JSONObject.parseObject(res);
			
	}
	
	
	//凭证加载：根据vouch_id查询凭证明细表
	@RequestMapping(value = "/querySuperVouchByDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperVouchByDetail(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		String reJson=null;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//reJson=superVouchService.querySuperVouchByDetail(mapVo);
		
		if(mapVo.get("acc_year")==null || mapVo.get("acc_year").toString().equals("")){
			mapVo.putIfAbsent("acc_year", SessionManager.getAcctYear());
		}
		
		try {
			//凭证一次性加载：根据vouch_id查询凭证明细表、辅助核算表、现金流量表
			reJson=superVouchService.querySuperVouchByDetailCheckCash(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"state\":false,\"msg\":\"凭证加载失败！\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	//凭证跳转：根据凭证号、凭证日期，查询凭证明细表
	@RequestMapping(value = "/querySuperVouchByJump", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperVouchByJump(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		String reJson=null;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			reJson=superVouchService.querySuperVouchByJump(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\"凭证加载失败！\"}";
		}
		
		return JSONObject.parseObject(reJson);
		
	}
	
	
	//根据凭证流程操作签字、审核、记账
	@RequestMapping(value = "/updateVouchStateByFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateVouchStateByFlow(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		String reJson=null;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		reJson=superVouchService.updateVouchStateByFlow(mapVo);
		return JSONObject.parseObject(reJson);
		
	}
	
	
	/**
	 * 凭证制单-现金流量页面
	 */
	@RequestMapping(value = "/accVouchCashPage", method = RequestMethod.GET)
	public String accVouchCashPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//加载现金流量相关信息
		mode.addAttribute("id",mapVo.get("id"));
		mode.addAttribute("rowNumber",mapVo.get("rowNumber"));
		mode.addAttribute("cellNumber",mapVo.get("cellNumber"));
		mode.addAttribute("vouch_id", mapVo.get("vouch_id"));
		mode.addAttribute("detail_id", mapVo.get("detail_id"));
		mode.addAttribute("state", mapVo.get("state"));
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("debit", (String) mapVo.get("debit"));
		mode.addAttribute("credit", (String) mapVo.get("credit"));
		mode.addAttribute("coulmn_name",mapVo.get("coulmn_name"));
				
		return "hrp/acc/accvouch/superVouch/vouchCash";
	}
	
	//凭证现金流量加载：根据vouch_detail_id查询现金流量标注表
	@RequestMapping(value = "/querySuperVouchByCash", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperVouchByCash(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		String reJson=null;
		reJson=superVouchService.querySuperVouchByCash(mapVo);
		return JSONObject.parseObject(reJson);
		
	}
	
	//凭证差异标注加载：根据vouch_id查询差异标注表
	@RequestMapping(value = "/querySuperVouchByDiff", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperVouchByDiff(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		String reJson=null;
		reJson=superVouchService.querySuperVouchByDiff(mapVo);
		return JSONObject.parseObject("{"+reJson+"}");
		
	}
	

	//打印凭证-单张凭证打印
	@RequestMapping(value = "/querySuperVouchPrint", method = RequestMethod.POST)//,produces = "text/html;charset=UTF-8"
	@ResponseBody
	public Map<String, Object> querySuperVouchPrint(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=superVouchService.querySuperVouchPrint(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}	
	
	//批量打印凭证-返回json
	@RequestMapping(value = "/querySuperVouchPrintBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperVouchPrintBatch(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=superVouchService.querySuperVouchPrintBatch(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
		
	/**
	 * 点击凭证分录，查询科目余额 
	 * @param mapVo
	 * @param mode
	 */
	@RequestMapping(value = "/querySuperVouchSubjBalance", method = RequestMethod.POST)
	@ResponseBody
	public String querySuperVouchSubjBalance(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", mapVo.get("vouch_date").toString().substring(0,4));
		mapVo.put("acc_month", mapVo.get("vouch_date").toString().substring(5,7));
		
		String subjBal=superVouchService.querySuperVouchSubjBalance(mapVo);
		return subjBal;
	}
	
	
	/**
	 * 点击辅助核算，查询余额 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySuperVouchCheckBalance", method = RequestMethod.POST)
	@ResponseBody
	public String querySuperVouchCheckBalance(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", mapVo.get("vouch_date").toString().substring(0,4));
		mapVo.put("acc_month", mapVo.get("vouch_date").toString().substring(5,7));
		
		String subjBal=superVouchService.querySuperVouchCheckBalance(mapVo);
		return subjBal;
	}
	
	
	//凭证制单-选择科目树页面
	@RequestMapping(value = "/subjTreePage", method = RequestMethod.GET)
	public String accSubjSelectorPage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		return "hrp/acc/accvouch/superVouch/subjTree";
	}
	
	//凭证制单-选择科目树
	@RequestMapping(value = "/queryAccVcouchSubjTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVcouchSubjTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=superVouchService.queryAccVcouchSubjTree(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		
		return JSONObject.parseObject(reJson);
		
	}
	 
	//复制凭证
	@RequestMapping(value = "/copySuperVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  copyAccVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			
			String accVouchJson = superVouchService.copySuperVouch(mapVo);
			
			return JSONObject.parseObject(accVouchJson);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
		
	}
	
	//凭证制单-保存凭证模板页面
	@RequestMapping(value = "/saveTemplatePage", method = RequestMethod.GET)
	public String saveTemplatePage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		return "hrp/acc/accvouch/superVouch/saveTemplate";
	}
	
	//保存凭证模板
	@RequestMapping(value = "/insertAccVouchTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  insertAccVouchTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		try{
			
			String accVouchJson = superVouchService.insertAccVouchTemplate(mapVo);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
		
	}
	
	//凭证制单-取凭证模板页面
	@RequestMapping(value = "/getTemplatePage", method = RequestMethod.GET)
	public String getTemplatePage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		return "hrp/acc/accvouch/superVouch/getTemplate";
	}
	
	
	//修改凭证模板主表
	@RequestMapping(value = "/queryAccTemplateMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  queryAccTemplateMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		try{
			String accVouchJson = superVouchService.queryAccTemplateMain(mapVo);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
		
	}
	
	//取模板页面-删除模板
	@RequestMapping(value = "/deleteAccVouchTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  deleteAccVouchTemplate(@RequestParam(value="tempCode[]") List<String> tempCode, Model mode) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		String tempCodeStr=tempCode.toString().replace("[", "").replace("]", "").replace(",", "','").replaceAll(" ", "");
		map.put("template_code","'"+tempCodeStr+"'");
		
		
		try{
			String accVouchJson = superVouchService.deleteAccVouchTemplate(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
		
	}
	
	
	//取模板页面-修改模板
	@RequestMapping(value = "/updateAccTemplateMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  updateAccTemplateMain(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		try{
			String accVouchJson = superVouchService.updateAccTemplateMain(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
		
	}
	
	
	//取模板页面-置顶
	@RequestMapping(value = "/updateAccTemplateMainBySort", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  updateAccTemplateMainBySort(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		try{
			String accVouchJson = superVouchService.updateAccTemplateMainBySort(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
		
	}
	
	//取模板
	@RequestMapping(value = "/queryAccTemplateVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  queryAccTemplateVouch(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		if(map.get("vouch_date")==null || map.get("vouch_date").toString().equals("")){
			map.put("acc_year", SessionManager.getAcctYear());
		}else{
			map.put("acc_year", map.get("vouch_date").toString().substring(0, 4));
		}
		
		try{
			String accVouchJson = superVouchService.queryAccTemplateVouch(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
	}
	
	//凭证制单-摘要维护页面
	@RequestMapping(value = "/getSummaryPage", method = RequestMethod.GET)
	public String getSummaryPage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		return "hrp/acc/accvouch/superVouch/getSummary";
	}
	
	//摘要维护页面-查询摘要
	@RequestMapping(value = "/queryAccVouchSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  queryAccVouchSummary(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		try{
			String accVouchJson = superVouchService.queryAccVouchSummary(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
	}
	
	//摘要维护页面-删除凭证摘要模板
	@RequestMapping(value = "/deleteAccVouchSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  deleteAccVouchSummary(@RequestParam(value="sidArry[]") List<String> sidArry, Model mode) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		String sidStr=sidArry.toString().replace("[", "").replace("]", "").replaceAll(" ", "");
		map.put("sidStr",sidStr);
		
		
		try{
			String accVouchJson = superVouchService.deleteAccVouchSummary(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
		
	}
	
	//存摘要
	@RequestMapping(value = "/saveAccVouchSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  saveAccVouchSummary(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		try{
			String accVouchJson = superVouchService.saveAccVouchSummary(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
	}
	
	//摘要维护页面-修改摘要
	@RequestMapping(value = "/updateAccVouchSummaryBySid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  updateAccVouchSummaryBySid(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		try{
			String accVouchJson = superVouchService.updateAccVouchSummaryBySid(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
	}
	
	//摘要维护页面-置顶
	@RequestMapping(value = "/updateAccVouchSummaryBySort", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  updateAccVouchSummaryBySort(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		try{
			String accVouchJson = superVouchService.updateAccVouchSummaryBySort(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
	}
	
	//上传附件
	@RequestMapping(value = "/upLoadPage", method = RequestMethod.GET)
	public String upLoadPage(@RequestParam Map<String, Object> reMap,Model mode,String vouch_id) throws Exception {
		mode.addAttribute("vouch_id", vouch_id);
		return "hrp/acc/accvouch/superVouch/upLoad";
	}
	//上传附件添加
	@RequestMapping(value = "/accFileAddPage", method = RequestMethod.GET)
	public String accFileAddPage(@RequestParam Map<String, Object> reMap,Model mode,String vouch_id) throws Exception {
		mode.addAttribute("vouch_id", vouch_id);
		return "hrp/acc/accvouch/superVouch/accFileAdd";
	}
	//查询
	@RequestMapping(value = "/queryAccFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccFile(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accFile = "";
		
		accFile = superVouchService.queryFile(getPage(mapVo));
		
		return JSONObject.parseObject(accFile);

	}
	
	//保存附件
	@RequestMapping(value = "/saveAccFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccFile(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("att_path");
		String accFileJson = "";
		try {
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			
			mapVo.put("group_id", group_id);
			mapVo.put("hos_id", hos_id);
			mapVo.put("copy_code", copy_code);
			mapVo.put("create_user", SessionManager.getUserId());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=df.format(new Date());
			
			mapVo.put("create_date", DateUtil.stringToDate(date, "yyyy-MM-dd HH:mm:ss"));
			
			String[] dd=date.split("-");
			String year=dd[0];
			String month=dd[1];
			
			String basePath = "acc";
			String loadPath="upLoad";
			String accFilePath = "vouchFile"+"/"+year+"/"+month;
			String filePath = loadPath+"/"+group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+accFilePath ;
			String oldFileName = new String(file.getOriginalFilename());//a.txt
			/*
			String ext=oldFileName.substring(oldFileName.lastIndexOf("."),oldFileName.length());
			String newFileName=UUIDLong.absStringUUID()+ext;
			*/
			
	        Base64 b6 = new Base64();  
	        String newFileName=b6.getBase64(oldFileName);
	        
			mapVo.put("att_path", filePath + "/" + newFileName);
			
			mapVo.put("att_name_o", oldFileName);
			mapVo.put("att_name_n", newFileName);
			
			long attSize=(file.getSize())/1024;
			mapVo.put("att_size", attSize);
			mapVo.put("file", file);
			mapVo.put("filePath", filePath);
			mapVo.put("newFileName", newFileName);
			
			accFileJson = superVouchService.addAccFile(mapVo,request,response);
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(accFileJson);
	}
	
	//下载附件
	@RequestMapping(value = "/downAccFile", method = RequestMethod.GET)
	public String downAccFile(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		superVouchService.downloadFile(response, mapVo);
		return null;
	}
	
	//删除附件
	@RequestMapping(value = "/deleteAccFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccFile(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("vouch_id", ids[3]);
			mapVo.put("att_name_n", ids[4]);
			mapVo.put("att_path", ids[5]);
		
			listVo.add(mapVo);
		}
		try {
			
			String accFileJson = "";
			
			accFileJson = superVouchService.deleteBatch(listVo);
			
			JSONObject json = JSONObject.parseObject(accFileJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(accFileJson);
			}
			
			//accFileJson = superVouchService.deleteFile(listVo);

			return JSONObject.parseObject(accFileJson);
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	//查询自动凭证数据
	@RequestMapping(value = "/queryAutoVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAutoVouch(@RequestParam Map<String, Object> map, Model mode)throws Exception {

		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		if(map.get("acc_year")==null || map.get("acc_year").toString().equals("")){
			map.putIfAbsent("acc_year", SessionManager.getAcctYear());
		}
		
		try{
			String accVouchJson = superVouchService.queryAutoVouch(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}

	}
	
	//预算会计页面
	@RequestMapping(value = "/vouchBudgPage", method = RequestMethod.GET)
	public String vouchBudgPage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		return "hrp/acc/accvouch/superVouch/vouchBudg";
	}
	
	//查询平行记账模板
	@RequestMapping(value = "/queryAccBudgTpTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBudgTpTree(@RequestParam Map<String, Object> map, Model mode)throws Exception {

		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
	
		try{
			String accVouchJson = superVouchService.queryAccBudgTpTree(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}

	}
	
	//根据财务会计科目查询预算会计科目
	@RequestMapping(value = "/queryBudgSubjByAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgSubjByAcc(@RequestParam Map<String, Object> map, Model mode)throws Exception {

		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
	
		try{
			String accVouchJson = superVouchService.queryBudgSubjByAcc(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
	}
	
	
	//更新科目对照
	@RequestMapping(value = "/updateBudgSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgSubj(@RequestParam Map<String, Object> map, Model mode)throws Exception {

		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
	
		try{
			String accVouchJson = superVouchService.updateBudgSubj(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
	}
	
	
	/**
	 * 凭证制单-财务&预算页面
	 */
	@RequestMapping(value = "/vouchKindPage", method = RequestMethod.GET)
	public String vouchKindPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		return "hrp/acc/accvouch/superVouch/vouchKind";
	}
	
	/**
	 * 凭证制单-差异标注页面
	 */
	@RequestMapping(value = "/vouchDiffPage", method = RequestMethod.GET)
	public String vouchDiffPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		return "hrp/acc/accvouch/superVouch/vouchDiff";
	}
	
	//差异标注
	@RequestMapping(value = "/updateAccVouchDiffAuto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccVouchDiffAuto(@RequestParam Map<String, Object> map, Model mode)throws Exception {

		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("create_user", SessionManager.getUserId());
	
		try{
			String accVouchJson = superVouchService.updateAccVouchDiffAuto(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
	}
	
	//差异手工标注
	@RequestMapping(value = "/updateAccVouchDiffSg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccVouchDiffSg(@RequestParam Map<String, Object> map, Model mode)throws Exception {

		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("create_user", SessionManager.getUserId());
	
		try{
			String accVouchJson = superVouchService.updateAccVouchDiffSg(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
	}
	
	//查询页面，凭证说明页面
	@RequestMapping(value = "/vouchDiffNotePage", method = RequestMethod.GET)
	public String vouchDiffNotePage(@RequestParam Map<String, Object> reMap,Model mode,String vouch_id) throws Exception {
		mode.addAttribute("vouch_id", vouch_id);
		return "hrp/acc/accvouch/query/vouchDiffNote";
	}
		
}
