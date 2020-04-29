package com.chd.hrp.acc.controller.books.memorandumbook;

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
import com.chd.hrp.acc.service.books.memorandumbook.AccItialIncomeService;
import com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService;
@Controller
public class AccItialIncomeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccItialIncomeController.class);
	
	@Resource(name = "accItialIncomeService")
	private final AccItialIncomeService accItialIncomeService = null;
	
	@Resource(name = "accSubjLedgerService")
	private final AccSubjLedgerService accSubjLedgerService = null;
	
	/**
	*初始收入表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/accItialIncomeMainPage", method = RequestMethod.GET)
	public String accItialIncomeMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		//返回当前月
		int month = new Date().getMonth() + 1;
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", month < 10 ? "0"+month:month);
		return "hrp/acc/accfinancialsubsidy/accItialIncomeMain";
	}
	
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/accExpendFinancialMainPage", method = RequestMethod.GET)
	public String accExpendFinancialMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		//返回当前月
		int month = new Date().getMonth() + 1;
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", month < 10 ? "0"+month:month);
		return "hrp/acc/accfinancialsubsidy/accExpendFinancialMain";
	}
	
	/**
	*初始收入表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/accItialIncomeAddPage", method = RequestMethod.GET)
	public String accItialIncomeAddPage(Model mode) throws Exception {

		return "hrp/acc/accfinancialsubsidy/accItialIncomeAdd";

	}
	/**
	*支出维护表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/accItialExpendMainPage", method = RequestMethod.GET)
	public String accItialExpendMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		//返回当前月
		int month = new Date().getMonth() + 1;
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", month < 10 ? "0"+month:month);
		return "hrp/acc/accfinancialsubsidy/accItialExpendMain";
	}
	/**
	*支出维护表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/accItialExpendAddPage", method = RequestMethod.GET)
	public String accItialExpendAddPage(Model mode) throws Exception {

		return "hrp/acc/accfinancialsubsidy/accItialExpendAdd";

	}
	/**
	*初始收入表<BR>                            
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/addAccItialIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccItialIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
	        
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		//mapVo.put("sub_id","1");
		
		mapVo.put("debit", 0);
		
		mapVo.put("vouch_id", 0);
		
		mapVo.put("vouch_check_id",0);
		
		mapVo.put("create_user", SessionManager.getUserId());
		
		mapVo.put("create_date", new Date());
		
		mapVo.put("is_init", 0);
		
		mapVo.put("is_import", 0);
		
		String accMatchInitJson = accItialIncomeService.addAccItialIncome(mapVo);

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*支出维护表<BR>                            
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/addAccItialExpend", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccItialExpend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
	        
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		//mapVo.put("sub_id","1");
		
		mapVo.put("credit", 0);
		
		mapVo.put("vouch_id", "");
		
		mapVo.put("vouch_check_id", "");
		
		mapVo.put("create_user", SessionManager.getUserId());
		
		mapVo.put("create_date", new Date());
		
		mapVo.put("is_init", 0);
		
		mapVo.put("is_import", 0);
		
		String accMatchInitJson = accItialIncomeService.addAccItialIncome(mapVo);

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	
	/**
	*导入凭证信息<BR>                            
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/addBatchAccItialExpend", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addBatchAccItialExpend(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String [] res=id.split("@");
			
			mapVo.put("group_id", res[0]);
			
			mapVo.put("hos_id", res[1]);
			
			mapVo.put("copy_code", res[2]);
            
			mapVo.put("vouch_no", res[3]);
			
			mapVo.put("vouch_date", res[4]);
			
			mapVo.put("summary", res[5]);
			
			mapVo.put("subj_id", res[6]);
			
			mapVo.put("debit", res[7]);
			
			mapVo.put("user_id", res[8]);
				
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			mapVo.put("credit", 0);
				
			mapVo.put("vouch_id", "");
				
			mapVo.put("vouch_check_id", "");
				
			mapVo.put("create_user", SessionManager.getUserId());
				
			mapVo.put("create_date", new Date());
				
			mapVo.put("is_init", 0);
				
			mapVo.put("is_import", 0);
            
			listVo.add(mapVo);

		}
		
		String accMatchInitJson = accItialIncomeService.addBatchAccItialIncome(listVo);
	   
		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*初始收入表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/queryAccItialIncome", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccItialIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("subj_type_code") == null){
	        
			mapVo.put("subj_type_code", "04");
				
		}
		
		String accMatchInitJson = accItialIncomeService.queryAccItialIncome(getPage(mapVo));

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*初始收入表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/queryAccItialExpend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccItialExpend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("subj_type_code") == null){
	        
			mapVo.put("subj_type_code", "05");
				
		}
		
		String accMatchInitJson = accItialIncomeService.queryAccItialIncome(getPage(mapVo));

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*初始收入表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/deletBatchAccItialIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletBatchAccItialIncome(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String [] res=id.split("@");
			
			mapVo.put("group_id", res[0]);
			
			mapVo.put("hos_id", res[1]);
			
			mapVo.put("copy_code", res[2]);
            
			mapVo.put("sub_id", res[3]);//实际实体类变量
            
			listVo.add(mapVo);

		}
		
		String accMatchInitJson = accItialIncomeService.deleteBatchAccItialIncome(listVo);
	   
		return JSONObject.parseObject(accMatchInitJson);
			
	}
	
	/**
	*支出维护表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/deletBatchAccItialExpend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletBatchAccItialExpend(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String [] res=id.split("@");
			
			mapVo.put("group_id", res[0]);
			
			mapVo.put("hos_id", res[1]);
			
			mapVo.put("copy_code", res[2]);
            
			mapVo.put("sub_id", res[3]);//实际实体类变量
            
			listVo.add(mapVo);
       
		}
		
		String accMatchInitJson = accItialIncomeService.deleteBatchAccItialIncome(listVo);
	   
		return JSONObject.parseObject(accMatchInitJson);
			
	}
	
	/**
	*初始收入表<BR>
	*导入凭证页面
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/accVouchImportMainPage", method = RequestMethod.GET)
	public String accVouchImportMainPage(Model mode) throws Exception {

		return "hrp/acc/accfinancialsubsidy/accVouchImportMain";
	}
	
	/**
	*查询导入凭证需要的数据<BR>
	*导入凭证页面
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/queryAccVouchImport", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccVouchImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accMatchInitJson = accItialIncomeService.queryAccVouchImport(getPage(mapVo));

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/collectExpendFinchdancial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectExpendFinchdancial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
	        
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String accMatchInitJson = accSubjLedgerService.collectAccExpendFinancial(mapVo);

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	
}
