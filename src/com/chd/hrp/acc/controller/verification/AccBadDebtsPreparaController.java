package com.chd.hrp.acc.controller.verification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccBudgLeder;
import com.chd.hrp.acc.service.termend.AccTermendTemplateService;
import com.chd.hrp.acc.serviceImpl.verification.AccBudgLederServiceImpl;

@Controller
public class AccBadDebtsPreparaController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBadDebtsPreparaController.class);
	
	@Resource(name = "accBudgLederService")
	private final AccBudgLederServiceImpl accBudgLederService = null;
	
	@Resource(name = "accTermendTemplateService")
	private final AccTermendTemplateService accTermendTemplateService = null;
	
	/**
	 * 坏账提取 
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/accBadDebtsPreparaMainPage", method = RequestMethod.GET)
	public String accBadDebtsPreparaMainPage(Model mode) throws Exception {
		mode.addAttribute("yearMonth", MyConfig.getAccYearMonth().getCurYearMonthAcc());
		return "hrp/acc/accbaddebtsprepara/accBadDebtsPreparaMain";
	}
	
	/**
	 * 坏账计提
	 * 模板查询
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/queryAccBadDebts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBadDebts(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("template_type_code") == null) {
			mapVo.put("template_type_code", "A0101");
		}
		String accTemplateData = accTermendTemplateService.queryAccTermendTemplate(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 坏账计提
	 * 模板保存
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/saveAccBadDebts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBadDebts(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		if (mapVo.get("template_type_code") == null) {
			mapVo.put("template_type_code", "A0101");
		}
		if (mapVo.get("template_type_name") == null) {
			mapVo.put("template_type_name", "坏账计提");
		}
		
		String msg = "";
		if (mapVo.get("template_id") == null || "".equals(mapVo.get("template_id"))) {
			msg = accTermendTemplateService.addAccTermendTemplate(getPage(mapVo));
		}else{
			msg = accTermendTemplateService.updateAccTermendTemplate(getPage(mapVo));
		}
		

		return JSONObject.parseObject(msg);
	}
	
	/**
	 * 坏账计提
	 * 模板删除
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/deleteAccBadDebts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBadDebts(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("template_id", id);// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			listVo.add(mapVo);
		}
		String msg = accTermendTemplateService.deleteBatchAccTermendTemplate(listVo);

		return JSONObject.parseObject(msg);
	}
	
	/**
	 * 坏账计提
	 * 模板明细查询
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/queryAccTermendTemplateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTermendTemplateDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accDetailData = accTermendTemplateService.queryAccTermendTemplateDetail(mapVo);
		return JSONObject.parseObject(accDetailData);
	}
	
	/**
	 * 坏账提取 
	 * 科目设置
	 * 页面跳转
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/accTermendTemplateSubjPage", method = RequestMethod.GET)
	public String accTermendTemplateSubjPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("subjParms", mapVo);
		return "hrp/acc/accbaddebtsprepara/accBadDebtsSubjSets";
	}
	
	/**
	 * 坏账提取 
	 * 应收科目
	 * 科目查询
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/queryAllSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryAllSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String	subj = accBudgLederService.queryAllSubj(mapVo);

		return subj;
	}
	
	/**
	 * 坏账提取
	 * 科目查询
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/queryAccTermendTemplateSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTermendTemplateSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		String accSubj = accTermendTemplateService.queryAccTermendTemplateSubj(getPage(mapVo));

		return JSONObject.parseObject(accSubj);
	}
	
	
	/**
	 * 坏账提取  主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbaddebtsprepara/queryBadDebtsPrepara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBadDebtsPrepara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        if(!"".equals(mapVo.get("subj_code"))){
        	mapVo.put("subj_code", mapVo.get("subj_code").toString().replaceAll(";", ",").replaceAll(" ", ""));
        }
        String result = accBudgLederService.queryAccBudgLederBySubjCode(getPage(mapVo));
		return JSONObject.parseObject(result);
		
	}
	
	/**
	 * 坏账提取   页面跳转 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbaddebtsprepara/accbadDebtsMethodMain", method = RequestMethod.GET)
	public String accbadDebtsMethodMain(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		//查看当前会计期间是否有数据
		int rows = accBudgLederService.queryBudgLederCounts(mapVo);
		AccBudgLeder accBudgLeder = new AccBudgLeder();
		String subj_code="";
		if(rows > 0){
			//如果当前会计期间有数据，则取当月的数据
			accBudgLeder = accBudgLederService.queryBadSubjBean(mapVo);
		}else{
			//如果当前会计期间没有数据，则取上一个会计期间的数据，判断当前月是否是01月  01月上一个会计期间为act_year-1  acc_month=12
			String acc_month = mapVo.get("acc_month").toString();
			if(acc_month.equals("01")){
				mapVo.put("acc_year","");
				mapVo.put("acc_month", "12");
			}else{
				int month =Integer.parseInt(mapVo.get("acc_month").toString());
				month = month - 1;
				if( String.valueOf(month).length() >1){
					mapVo.put("acc_month", String.valueOf(month));
				}else{
					mapVo.put("acc_month", "0"+String.valueOf(month));
				}
			}
			accBudgLeder = accBudgLederService.queryBadSubjBean(mapVo);
		}
		
		/*查看当前会计期间是否结账*/
		int flag = accBudgLederService.queryAccMonthIsAcc(mapVo);
		/*应收科目的期末余额和*/
			if(!"".equals(mapVo.get("subj_code"))){
				subj_code=mapVo.get("subj_code").toString().replaceAll(";", ",").replaceAll(" ", "");
	        	mode.addAttribute("subj_code", mapVo.get("subj_code").toString().replaceAll(";", ",").replaceAll(" ", ""));
	        }
			subj_code = "("+subj_code+")";
			mapVo.put("subj_code", subj_code);
			
		double end_os = accBudgLederService.queryRecevieSubjEndOs(mapVo);
		/*选择的应收科目在当前会计期间是否计提过坏账*/
		//String subj_badCode = accBudgLederService.queryRecevieSubjJtIsOrNot(mapVo);
		/*选择的应收科目在当前会计期间是否记账*/
		int subj_accFlag  = accBudgLederService.queryRecevieSubjIsAcc(mapVo);
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		if(accBudgLeder != null){
			mode.addAttribute("range_type",accBudgLeder.getRange_type());
			mode.addAttribute("range_pro",accBudgLeder.getRange_pro());
		}
		
		mode.addAttribute("acc_flag", flag);
		mode.addAttribute("subj_endOs", end_os);
		//mode.addAttribute("subj_badCode", subj_badCode); 
		mode.addAttribute("subj_accFlag", subj_accFlag);
			
		return "hrp/acc/accbaddebtsprepara/accbadDebtsMethodMain";
	}
	
	//坏账计提
	@RequestMapping(value = "/hrp/acc/accbaddebtsprepara/accbadDebtsMethodSure", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> accbadDebtsMethodSure(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result="";
        
		return JSONObject.parseObject(result);
		
	}
		
	/**
	 * 坏账提取科目 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbaddebtsprepara/queryBadSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBadSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("acc_year", SessionManager.getAcctYear());
		mapVo.put("copy_nature", SessionManager.getCopyNature());
		
		//查看当前会计期间是否有数据
		int rows = accBudgLederService.queryBudgLederCounts(mapVo);
		
		String subj ="";
		
		if(rows > 0){
			//如果当前会计期间有数据，则取当月的数据
			 subj = accBudgLederService.queryBadSubj(mapVo);
		}else{
			//如果当前会计期间没有数据，则取上一个会计期间的数据，判断当前月是否是01月  01月上一个会计期间为act_year-1  acc_month=12
			String acc_month = mapVo.get("acc_month").toString();
			if("01".equals(acc_month)){
				mapVo.put("acc_year","");
				mapVo.put("acc_month", "12");
			}else{
				int month =Integer.parseInt(mapVo.get("acc_month").toString());
				month = month - 1;
				if( String.valueOf(month).length() >1){
					mapVo.put("acc_month", String.valueOf(month));
				}else{
					mapVo.put("acc_month", "0"+String.valueOf(month));
				}
			}
			subj = accBudgLederService.queryBadSubj(mapVo);
		}
		
		return subj;
		
	}
	
	/**
	 * 管理费用科目
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbaddebtsprepara/queryManageSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryManageSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("acc_year", SessionManager.getAcctYear());
		mapVo.put("copy_nature", SessionManager.getCopyNature());
		
		//查看当前会计期间是否有数据
		int rows = accBudgLederService.queryBudgLederCounts(mapVo);
		String subj ="";
		if(rows > 0){
			//如果当前会计期间有数据，则取当月的数据
			 subj = accBudgLederService.queryManageSubj(mapVo);
		}else{
			//如果当前会计期间没有数据，则取上一个会计期间的数据，判断当前月是否是01月  01月上一个会计期间为act_year-1  acc_month=12
			String acc_month = mapVo.get("acc_month").toString();
			if("01".equals(acc_month)){
				mapVo.put("acc_year","");
				mapVo.put("acc_month", "12");
			}else{
				int month =Integer.parseInt(mapVo.get("acc_month").toString());
				month = month - 1;
				if( String.valueOf(month).length() >1){
					mapVo.put("acc_month", String.valueOf(month));
				}else{
					mapVo.put("acc_month", "0"+String.valueOf(month));
				}
			}
			subj = accBudgLederService.queryManageSubj(mapVo);
		}
		return subj;
	}
	
	
	
	/**
	 * 坏账计提生成凭证 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbaddebtsprepara/addAccBadDebtsExtract", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addAccBadDebtsExtract(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		String accVouch;
		try {
			accVouch = accBudgLederService.addAccBadDebtsExtract(mapVo);
		} catch (Exception e) {
			accVouch = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(accVouch);
		
	}	
	
	/**
	 *   撤销计提
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbaddebtsprepara/delBadDebtsCancel", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject delBadDebtsCancel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		if(!"".equals(mapVo.get("subj_code"))){
	        mapVo.put("subj_code", mapVo.get("subj_code").toString().replaceAll(";", "-").replaceAll(" ", ""));
	    }
		StringBuffer vouch_id = new StringBuffer();
		vouch_id.append("");
		//获取勾选的vouch_id
		JSONArray json = JSONArray.parseArray((String)mapVo.get("bad_data"));
		Iterator it = json.iterator();
		while(it.hasNext()){
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			vouch_id.append(jsonObj.get("vouch_id").toString()+"-");
			
		}
		
		
		mapVo.put("vouch_id", vouch_id.substring(0,vouch_id.length()-1));

		String	msgStr = accBudgLederService.delBadDebtsCancel(mapVo);
			
		return JSONObject.parseObject(msgStr);
	}
	
	/**
	 * 凭证维护<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/accBadDebtsVouchPage", method = RequestMethod.GET)
	public String accBadDebtsVouchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("template_type_code", mapVo.get("template_type_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		return "hrp/acc/accbaddebtsprepara/accBadDebtsVouch";
	}
	
	/**
	 * 期末处理<BR>
	 * 凭证维护
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/queryBadDebtsVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBadDebtsVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		//格式化日期
		if (mapVo.get("create_date_b") != null && !"".equals(mapVo.get("create_date_b").toString())) {
			mapVo.put("create_date_b", DateUtil.stringToDate(mapVo.get("create_date_b").toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("create_date_e") != null && !"".equals(mapVo.get("create_date_e").toString())) {
			mapVo.put("create_date_e", DateUtil.stringToDate(mapVo.get("create_date_e").toString(), "yyyy-MM-dd"));
		}
		
		String accSubj = accTermendTemplateService.queryAccTermendTemplateVouch(getPage(mapVo));

		return JSONObject.parseObject(accSubj);
	}
	


	/**
	 * 科室比例设置<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/accBadDeptPage", method = RequestMethod.GET)
	public String accBadDeptPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("template_id", mapVo.get("template_id"));
		mode.addAttribute("year_month", mapVo.get("year_month"));
		return "hrp/acc/accbaddebtsprepara/accBadDebtsDeptSets";
	}

	/**
	 * 坏账准备计提<BR>
	 * 科室比例查询
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/queryAccBadDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBadDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		String templateDept = accBudgLederService.queryAccBadDept(getPage(mapVo));
		return JSONObject.parseObject(templateDept);
	}

	/**
	 * 坏账准备计提<BR>
	 * 科室比例保存
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/saveAccBadDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBadDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("template_id") == null || "".equals(mapVo.get("template_id"))) {
			return JSONObject.parseObject("{\"error\":\"请先保存模板\"}");
		}
		String accVouch = accBudgLederService.saveAccBadDept(getPage(mapVo));

		return JSONObject.parseObject(accVouch);
	}

	/**
	 * 坏账准备计提<BR>
	 * 提取科室收入
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/saveAccBadGetDeptIncom", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBadGetDeptIncom(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("template_id") == null || "".equals(mapVo.get("template_id"))) {
			return JSONObject.parseObject("{\"error\":\"请先保存模板\"}");
		}
		String accVouch = accBudgLederService.saveAccBadGetDeptIncom(getPage(mapVo));

		return JSONObject.parseObject(accVouch);
	}

	/**
	 * 坏账准备计提(财务)<BR>
	 * 提取科室收入
	 */
	@RequestMapping(value = "hrp/acc/accbaddebtsprepara/saveAccBadGetDeptIncomAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBadGetDeptIncomAcc(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null || "".equals(mapVo.get("acc_year"))) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		if ((mapVo.get("template_id") == null) || ("".equals(mapVo.get("template_id")))) {
			return JSONObject.parseObject("{\"error\":\"请先保存模板\"}");
		}
		String accVouch = accBudgLederService.saveAccBadGetDeptIncomAcc(getPage(mapVo));

		return JSONObject.parseObject(accVouch);
	}
}
