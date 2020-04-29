/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller.vouch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.service.commonbuilder.AccCashItemService;
import com.chd.hrp.acc.service.commonbuilder.AccPayTypeService;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.acc.service.vouch.AccVouchCheckService;
import com.chd.hrp.acc.service.vouch.AccVouchDetailService;
import com.chd.hrp.acc.service.vouch.AccVouchService;
import com.chd.hrp.acc.service.vouch.AccVouchSummaryService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.acc.serviceImpl.AccParaServiceImpl;

/**
 * @Title. @Description. 凭证主表
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.comAa
 * @Version: 1.0
 */

@Controller
public class AccVouchController extends BaseController {

	private static Logger logger = Logger.getLogger(AccVouchController.class);

	@Resource(name = "accVouchService")
	private final AccVouchService accVouchService = null;

	@Resource(name = "accVouchDetailService")
	private final AccVouchDetailService accVouchDetailService = null;

	@Resource(name = "accVouchSummaryService")
	private final AccVouchSummaryService accVouchSummaryService = null;

	@Resource(name = "accCashItemService")
	private final AccCashItemService accCashItemService = null;

	@Resource(name = "accVouchCheckService")
	private final AccVouchCheckService accVouchCheckService = null;

	@Resource(name = "accSubjService")
	private final AccSubjService accSubjService = null;

	@Resource(name = "accPayTypeService")
	private final AccPayTypeService accPayTypeService = null;

	@Resource(name = "accParaService")
	private final AccParaServiceImpl accParaService = null;

	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	// ---------------------------------------------------------------------------------------跳转到凭证制单----------------------------------------------------------------------------------------
	

	// ---------------------------------------------------------------------------------------跳转到凭证制单---------------------------------------------------------------------------------------

	
	
	// ---------------------------------------------------------------------摘要处理开始-------------------------------------------------------------------------------------------------
	/**
	 * 添加摘要<BR>
	 * 添加摘要页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accvouch/making/accVouchSummaryAddPage", method = RequestMethod.GET)
	public String accVouchSummaryAddPage(Model mode) throws Exception {

		return "hrp/acc/accvouchsummary/accVouchSummaryAdd";

	}

	/**
	 * 查询摘要
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/making/queryAccVouchSummary", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccVouchSummary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("user_id",SessionManager.getUserId());
		String accVouchSummary = accVouchSummaryService.queryAccVouchSummary(getPage(mapVo));
		
		return accVouchSummary;

	}

	/**
	 * 保存摘要<BR>
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/making/addAccVouchSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccVouchSummary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("user_id", SessionManager.getUserId());

		String addAccVouchSummaryJson = accVouchSummaryService.addAccVouchSummary(mapVo);

		return JSONObject.parseObject(addAccVouchSummaryJson);

	}

	// ---------------------------------------------------------------------------------------摘要处理结束----------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------现金流量标注----------------------------------------------------------------------------------------
	/**
	 * 跳转现金流量标注
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/making/accSubjCashOrCheckPage", method = RequestMethod.GET)
	public String accSubjCashOrCheckPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String subj_nature_code = (String) mapVo.get("subj_nature_code");

		String is_cash = (String) mapVo.get("is_cash");

		String is_check = (String) mapVo.get("is_check");

		String subj_dire = (String) mapVo.get("subj_dire");

		String subj_id = (String) mapVo.get("subj_id");

		String columId = (String) mapVo.get("__id");

		String grid_init = (String) mapVo.get("grid_init");

		//System.out.println("subj_dire=" + subj_dire);

		String summary = (String) mapVo.get("summary");

		if ("undefined".equals(summary) ||"".equals(summary)|| summary == null) {

			mode.addAttribute("summary", "");

		} else {

			mode.addAttribute("summary", summary);

		}

		mode.addAttribute("subj_nature_code", subj_nature_code);

		mode.addAttribute("is_cash", is_cash);

		mode.addAttribute("is_check", is_check);

		mode.addAttribute("subj_dire", subj_dire);

		mode.addAttribute("subj_id", subj_id);

		mode.addAttribute("__id", columId);

		mode.addAttribute("grid_init", grid_init);

		if ("1".equals(is_check)) {// is_check = 1 挂了辅助核算 则查询辅助核算表

			mapVo.put("subj_id", mapVo.get("subj_id"));

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

			mapVo.put("is_stop", "0");

			mapVo.put("is_last", "1");

			Map<String, String> columns_map = accVouchService.queryAccSubjWithCheckType(mapVo);

			String subjWithCheckType = columns_map.get("columns");

			String check_type = columns_map.get("check_type");

			mode.addAttribute("subjWithCheckType", subjWithCheckType);

			mode.addAttribute("check_type", check_type);

		}

		return "hrp/acc/accvouch/making/accSubjCashOrCheck";
	}

	// ---------------------------------------------------------------------------------------现金流量标注----------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------现金流量项目----------------------------------------------------------------------------------------
	/**
	 * 查詢现金流量项目<BR>
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/making/queryAccVouchCashItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccVouchCashItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		mapVo.put("is_stop", "0");

		String accVouchCashItem = accCashItemService.queryAccCashItemByVouch(getPage(mapVo));

		return accVouchCashItem;

	}

	// ---------------------------------------------------------------------------------------现金流量项目----------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------查询凭证辅助核算项----------------------------------------------------------------------------------------

	/**
	 * 查询凭证辅助核算项<BR>	 * 
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/making/queryAccSubjByCashOrCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjByCashOrCheck(@RequestParam(value = "subj_id") String subj_id, Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("subj_id", subj_id);
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		mapVo.put("is_stop", "0");
		mapVo.put("is_last", "1");

		// mapVo.put("sql", "is_cash ='1' or is_check='1'");
		String accVouchCheckJson = accSubjService.queryAccSubjByCashOrCheck(mapVo);
		return JSONObject.parseObject(accVouchCheckJson);

	}

	// ---------------------------------------------------------------------------------------查询凭证辅助核算项----------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------结算方式----------------------------------------------------------------------------------------
	/**
	 * 添加结算<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/making/queryAccVouchPayType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccVouchPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		mapVo.put("is_stop", "0");

		String accVouchPayType = accPayTypeService.queryAccPayTypeByVouch(getPage(mapVo));

		return accVouchPayType;

	}

	// ---------------------------------------------------------------------------------------结算方式----------------------------------------------------------------------------------------


	

	// 保存明细数据
	public Map<String,List<Map<String, Object>>> buildVouchDetail(Map<String, Object> mapVo) {
		
		Map<String,List<Map<String, Object>>> map_batch =new HashMap<String,List<Map<String, Object>>>();
		
		List<Map<String, Object>> list_vouch_detail_batch = new ArrayList<Map<String, Object>>();//存放明细
		
		List<Map<String, Object>> list_vouch_check_batch = new ArrayList<Map<String, Object>>();//存放辅助核算数据

		// 解析凭证辅助核算AND 现金流JSON
		JSONObject cash_check_data_json = JSONObject.parseObject((String) mapVo.get("cash_check_data"));

		// 解析该凭证分录有多少个凭证辅助核算类
		JSONObject check_type_json = JSONObject.parseObject((String) mapVo.get("check_data"));

		// 解析该凭证分录挂的辅助核算项 包括 subj_nature_code、 is_cash、 is_check
		JSONObject subj_data_json = JSONObject.parseObject((String) mapVo.get("subj_data"));
		
		JSONArray vouch_detail_json       =      JSONArray.parseArray((String) mapVo.get("vouch_detail_data"));
	
		Iterator vouch_detail_it = vouch_detail_json.iterator();

		try {

			while (vouch_detail_it.hasNext()) {
				
				//首先验证必须输入项

				Integer vouch_detail_id = accVouchDetailService.queryAccVouchDetailNextval(mapVo);

				Map<String, Object> mapDetailVo = defaultDetailValue();

				mapDetailVo.put("group_id", mapVo.get("group_id"));

				mapDetailVo.put("hos_id", mapVo.get("hos_id"));

				mapDetailVo.put("copy_code", mapVo.get("copy_code"));

				mapDetailVo.put("vouch_detail_id", vouch_detail_id);

				mapDetailVo.put("vouch_id", mapVo.get("vouch_id"));

				mapDetailVo.put("acc_year", mapVo.get("acc_year"));
				
				mapDetailVo.put("vouch_date", mapVo.get("vouch_date"));
				
				mapDetailVo.put("subj_id", mapVo.get("subj_id"));

				JSONObject jsonObj = JSONObject.parseObject(vouch_detail_it.next().toString());
				
				String subj_id =String.valueOf(jsonObj.get("subj_id"));
				
				String summary =String.valueOf(jsonObj.get("summary"));
				
				if(validateJSON(subj_id) && validateJSON(subj_id)){
					
					mapDetailVo.put("subj_id", jsonObj.get("subj_id"));

					mapDetailVo.put("vouch_row", jsonObj.get("__index"));

					mapDetailVo.put("summary", jsonObj.get("summary"));

					String debit =String.valueOf(jsonObj.get("debit"));
					
					String credit =String.valueOf(jsonObj.get("credit"));
					
					if(validateJSON(debit)){
						
						mapDetailVo.put("debit", jsonObj.get("debit"));
						
					}
					
					if(validateJSON(credit)){
						
						mapDetailVo.put("credit", jsonObj.get("credit"));
						
					}

					mapDetailVo.put("debit_w", 0);

					mapDetailVo.put("credit_w", 0);
					
					mapDetailVo.put("__id", jsonObj.get("__id"));
				
					String check_type_json_temp = String.valueOf(check_type_json.get(mapDetailVo.get("__id")));
					
					String cash_check_data_json_temp = String.valueOf(cash_check_data_json.get(mapDetailVo.get("__id")));
					
					String subj_data_json_temp = String.valueOf(subj_data_json.get(mapDetailVo.get("__id")));
					
					Map subj_data_map = getSubjData(subj_data_json_temp);
					
					if("03".equals(subj_data_map.get("subj_nature_code")) || "1".equals(subj_data_map.get("is_cash")) || "1".equals(subj_data_map.get("is_check"))){
						
						mapDetailVo.put("subj_data_json_temp", subj_data_json_temp);
						
						mapDetailVo.put("cash_check_data_json_temp", cash_check_data_json_temp);
						
						mapDetailVo.put("check_type_json_temp", check_type_json_temp);
						
					}

					list_vouch_check_batch.addAll(buildVouchCheck(mapDetailVo));
					
					list_vouch_detail_batch.add(mapDetailVo);
					
				}

			}
		} catch (DataAccessException e) {

		}
		
		map_batch.put("list_vouch_detail_batch", list_vouch_detail_batch);
		
		map_batch.put("list_vouch_check_batch", list_vouch_check_batch);
		
		return map_batch;
		// -------------------------------------凭证明细组织数据完成--------------------------------------------------

	}
	
	public boolean validateJSON(String str){
		
		if(str !=null  && !"null".equals(str) && !"".equals(str) && !"0".equals(str)){
			
			return true;
			
		}
		
		return false;
		
	}
	// 组织辅助核算数据
	public List<Map<String, Object>> buildVouchCheck(Map<String, Object> mapDetailVo){
		
		
		
		List<Map<String, Object>> list_vouch_check_batch = new ArrayList<Map<String, Object>>();//存放辅助核算数据

		String subj_data_json_temp = (String)mapDetailVo.get("subj_data_json_temp");
		
		String cash_check_data_json_temp = (String)mapDetailVo.get("cash_check_data_json_temp");
		
		String check_type_json_temp = (String)mapDetailVo.get("check_type_json_temp");
		
		Map subj_data_map = getSubjData(subj_data_json_temp);

		// 满足以上条件 一定存在弹窗数据  该科目存在需要弹出窗口的辅助核算项或者现金流量标注
		JSONArray cash_check_data_json_object = JSONArray.parseArray(cash_check_data_json_temp);

		Iterator cash_check_data_it = cash_check_data_json_object.iterator();

		while (cash_check_data_it.hasNext()) {

			Map<String, Object> mapCheckVo = defaultCheckValue();
			
			mapCheckVo.put("group_id", mapDetailVo.get("group_id"));

			mapCheckVo.put("hos_id", mapDetailVo.get("hos_id"));

			mapCheckVo.put("copy_code", mapDetailVo.get("copy_code"));

			mapCheckVo.put("vouch_detail_id", mapDetailVo.get("vouch_detail_id"));

			mapCheckVo.put("vouch_id", mapDetailVo.get("vouch_id"));

			mapCheckVo.put("acc_year", mapDetailVo.get("acc_year"));
			
			mapCheckVo.put("subj_id", mapDetailVo.get("subj_id"));
			
			mapCheckVo.put("is_init", 1);

			JSONObject cash_check_data_obj = JSONObject.parseObject(cash_check_data_it.next().toString());
			
			String summary = String.valueOf(cash_check_data_obj.get("summary"));
			
			if(validateJSON(summary) && validateJSON(summary)){
				
				mapCheckVo.put("summary", cash_check_data_obj.get("summary"));
				
				mapCheckVo.put("occur_date", mapDetailVo.get("vouch_date"));
				
				mapCheckVo.put("due_date", mapDetailVo.get("vouch_date"));

				String amount =String.valueOf(cash_check_data_obj.get("amount"));
				
				if(amount !=null  && amount!="null" && amount!="" && amount!="0"){
					
					mapCheckVo.put("debit", cash_check_data_obj.get("amount"));
					
				}

				if("03".equals(subj_data_map.get("subj_nature_code"))){//根据科目性质判断哪些数据需要录入
					
					mapCheckVo.put("pay_type_code", cash_check_data_obj.get("pay_code"));
					
					mapCheckVo.put("check_no", cash_check_data_obj.get("check_no"));
					
				}else if("04".equals(subj_data_map.get("subj_nature_code")) || "05".equals(subj_data_map.get("subj_nature_code")) ){
					
					mapCheckVo.put("con_no", cash_check_data_obj.get("con_no"));
					
				}
				
				if("1".equals(subj_data_map.get("is_check"))){//存在辅助核算的情况下
					
					Map<String, String> check_type_map = getCheckType(check_type_json_temp);
					
					for (String key : check_type_map.keySet()) {

						String[] keyArr = String.valueOf(cash_check_data_obj.get(key)).split("\\.");
						
						if("check7".equals(check_type_map.get(key))){
							
							mapCheckVo.put(check_type_map.get(key)+"_ID", keyArr[1]);
							
						}else{
							
							mapCheckVo.put(check_type_map.get(key)+"_ID", keyArr[0]);
							
							mapCheckVo.put(check_type_map.get(key)+"_NO", keyArr[1]);
						}

					}
				}
				
				if( "1".equals(subj_data_map.get("is_cash"))){//存在现金流量
					//调用流量标注
				}
				list_vouch_check_batch.add(mapCheckVo);
				
			}
			
		}
		
	


		
		return list_vouch_check_batch;
		
	}

	// 返回用用于保存的默认值
	public Map<String, Object> defaultDetailValue() {

		Map<String, Object> mapDetailVo = new HashMap<String, Object>();

		mapDetailVo.put("subj_id", 1);
		
		mapDetailVo.put("vouch_row", 0);
		
		mapDetailVo.put("summary", "");
		
		mapDetailVo.put("debit", 0);
		
		mapDetailVo.put("credit", 0);
		
		mapDetailVo.put("debit_w", 0);
		
		mapDetailVo.put("credit_w", 0);
		
		return mapDetailVo;
	}

		
	//返回用用于保存的默认值
	public Map<String,Object> defaultCheckValue(){
		
		Map<String, Object> mapCheckVo = new HashMap<String, Object>();

		mapCheckVo.put("debit", 0);
		
		mapCheckVo.put("credit", 0);
		
		mapCheckVo.put("con_no", "");
		
		mapCheckVo.put("check_no", "");
		
		mapCheckVo.put("business_no", "");
		
		mapCheckVo.put("occur_date", new Date());
		
		mapCheckVo.put("due_date", new Date());
		
		mapCheckVo.put("pay_type_code", "");
		
		mapCheckVo.put("debit_w", 0);
		
		mapCheckVo.put("credit_w", 0);
		
		mapCheckVo.put("is_init",0);
		
		mapCheckVo.put("old_check_id", 0);
		
		mapCheckVo.put("check1_id", 0);
		
		mapCheckVo.put("check1_no", 0);
		
		mapCheckVo.put("check2_id", 0);
		
		mapCheckVo.put("check2_no", 0);
		
		mapCheckVo.put("check3_id", 0);
		
		mapCheckVo.put("check3_no", 0);
		
		mapCheckVo.put("check4_id", 0);
		
		mapCheckVo.put("check4_no", 0);
		
		mapCheckVo.put("check5_id", 0);
		
		mapCheckVo.put("check5_no", 0);
		
		mapCheckVo.put("check6_id", 0);
		
		mapCheckVo.put("check6_no", 0);
		
		mapCheckVo.put("check7_id", 0);
		
		return mapCheckVo;
	}

	// 返回核算类数据 数据样例 ： dept_id.check1@emp_id.check2
	public Map<String, String> getCheckType(String str) {

		Map<String, String> map = new HashMap<String, String>();

		String[] strArr = str.split("@");// 数据样例 ： dept_id.check1@emp_id.check2

		for (int i = 0; i < strArr.length; i++) {

			String[] strArrSplit = strArr[i].split("\\.");

			map.put(strArrSplit[0].toLowerCase(), strArrSplit[1].toLowerCase());

		}

		return map;

	}
	
	// 返回核算类数据 数据样例 ： dept_id.check1@emp_id.check2
		public Map<String, String> getSubjData(String str) {

			Map<String, String> map = new HashMap<String, String>();

			String[] strArr = str.split("@");//数据类型subj_nature_code+"@"+is_cash+"@"+is_check;

			map.put("subj_nature_code", strArr[0]);
			
			map.put("is_cash", strArr[1]);
			
			map.put("is_check", strArr[2]);

			return map;

		}

	// ---------------------------------------------------------------------------------------保存凭证主表---------------------------------------------------------------------------------------

	/**
	 * 凭证查询<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/query/accVouchMainPage", method = RequestMethod.GET)
	public String accVouchMainPage(Model mode) throws Exception {
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		//String yearMonth=SessionManager.getSysYearMonth("acc_flag");
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		return "hrp/acc/accvouch/query/accVouchMain";

	}

	/**
	 * 凭证主表<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accvouch/query/accVouchAddPage", method = RequestMethod.GET)
	public String accVouchAddPage(Model mode) throws Exception {

		return "hrp/acc/accvouch/query/accVouchAdd";

	}

	/**
	 * 凭证主表<BR>
	 * 高级查询页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accvouch/query/accVouchSearchPage", method = RequestMethod.GET)
	public String accVouchSearchPage(Model mode) throws Exception {

		return "hrp/acc/accvouch/query/accVouchSearch";

	}

	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/query/queryAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String accVouch = null;
		
		try{
			if(	"1".equals(mapVo.get("pay_type_code"))){
				
				accVouch = accVouchService.queryAccVouch(getPage(mapVo));
			}else{
				
				accVouch = accVouchService.queryAccVouchPayTypeCode(getPage(mapVo));
			}
	
			return JSONObject.parseObject(accVouch);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}

	/**
	 * 凭证主表<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/query/deleteAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccVouch(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String res="";
		int i=0;
		for (String id : paramVo.split(",")) {
			mapVo.put("vouch_id", id);// 实际实体类变量
			res=superVouchService.deleteSuperVouchByVouchId(mapVo);
			if(res!=null && !res.equals("ok")){
				if(i==0)res="{\"error\":\""+res+"\",\"state\":\"false\"}";
				else res="{\"msg\":\""+res+"\",\"state\":\"true\"}";
				break;
			}
			i++;
		}
		if(res!=null && res.equals("ok")){
			res="{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		//String accVouchJson = accVouchService.deleteBatchAccVouch(listVo);
		return JSONObject.parseObject(res);

	}

	/**
	 * 凭证主表<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/query/accVouchUpdatePage", method = RequestMethod.GET)
	public String accVouchUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		AccVouch accVouch = new AccVouch();
		accVouch = accVouchService.queryAccVouchByCode(mapVo);
		mode.addAttribute("vouch_id", accVouch.getVouch_id());
		mode.addAttribute("group_id", accVouch.getGroup_id());
		mode.addAttribute("hos_id", accVouch.getHos_id());
		mode.addAttribute("copy_code", accVouch.getCopy_code());
		mode.addAttribute("acc_year", accVouch.getAcc_year());
		mode.addAttribute("acc_month", accVouch.getAcc_month());
		mode.addAttribute("vouch_type_code", accVouch.getVouch_type_code());
		mode.addAttribute("vouch_no", accVouch.getVouch_no());
		mode.addAttribute("vouch_date", accVouch.getVouch_date());
		mode.addAttribute("vouch_att_num", accVouch.getVouch_att_num());
		mode.addAttribute("vouch_id_cx", accVouch.getVouch_id_cx());
		mode.addAttribute("create_user", accVouch.getCreate_user());
		mode.addAttribute("create_date", accVouch.getCreate_date());
		mode.addAttribute("cash_user", accVouch.getCash_user());
		mode.addAttribute("cashe_date", accVouch.getCashe_date());
		mode.addAttribute("audit_user", accVouch.getAudit_user());
		mode.addAttribute("audit_date", accVouch.getAudit_date());
		mode.addAttribute("acc_user", accVouch.getAcc_user());
		mode.addAttribute("acc_date", accVouch.getAcc_date());
		mode.addAttribute("state", accVouch.getState());
		mode.addAttribute("note", accVouch.getNote());
		return "hrp/acc/accvouch/accVouchUpdate";
	}

	/**
	 * 凭证主表<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/query/updateAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accVouchJson = accVouchService.updateAccVouch(mapVo);

		return JSONObject.parseObject(accVouchJson);
	}

	/**
	 * 凭证主表<BR>
	 * 导入
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/query/importAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accVouchJson = accVouchService.importAccVouch(mapVo);

		return JSONObject.parseObject(accVouchJson);
	}

	/**
	 * 凭证主表<BR>
	 * 
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/query/cancleAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancleAccVouch(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			String [] res = id.split("@");
			
			mapVo.put("vouch_id", res[0]);// 实际实体类变量
			
			mapVo.put("state", "0");
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			mapVo.put("vouch_state", res[1]);
			
			mapVo.put("sign_flag", "2");
			
			listVo.add(mapVo);
		
		}
		
		try{
			String accVouchJson = accVouchService.updateBatchAccVouch(listVo);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		

	}

	@RequestMapping(value = "/hrp/acc/accvouch/query/recoverAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> recoverAccVouch(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("vouch_id", res[0]);// 实际实体类变量
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			mapVo.put("state","1");
			
			mapVo.put("vouch_state", res[1]);
			
			mapVo.put("sign_flag", "0");
			
			listVo.add(mapVo);
			
		
		}
		
		String accVouchJson = accVouchService.updateBatchAccVouch(listVo);
		
		return JSONObject.parseObject(accVouchJson);

	}
	
	/**
	 * 查詢凭证进行签字的前提条件<BR>
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/queryVouchState", method = RequestMethod.POST)
	@ResponseBody
	public String queryVouchState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accVouchCashItem = accVouchService.queryAccState(mapVo);

		return accVouchCashItem;

	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/queryAccVouchFlowByNodeId", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccVouchFlowByNodeId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		mapVo.put("node_id", "2");

		String accVouchCashItem = accVouchService.queryAccVouchFlowByNodeId(mapVo);

		return accVouchCashItem;

	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/query/redWriteoffAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  redWriteoffAccVouch(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		Map<String,Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("vouch_id",paramVo);
		
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
		
		try{
			
			String accVouchJson = accVouchDetailService.updateBatchAccVouchDetailOfMoney(mapVo);
			
			return JSONObject.parseObject(accVouchJson);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		
		}
		
	}
	
	/*@RequestMapping(value = "/hrp/acc/accvouch/query/blueWriteoffAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  blueWriteoffAccVouch(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			String [] res = id.split("@");
			
			mapVo.put("vouch_id", res[0]);// 实际实体类变量
			
			mapVo.put("state", "1");
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			mapVo.put("update_vouch", "debit=credit,credit=debit");
			
			listVo.add(mapVo);
		
		}
		
		String accVouchJson = accVouchDetailService.updateBatchAccVouchDetailOfMoney(listVo);
		
		return JSONObject.parseObject(accVouchJson);

	}*/

}
