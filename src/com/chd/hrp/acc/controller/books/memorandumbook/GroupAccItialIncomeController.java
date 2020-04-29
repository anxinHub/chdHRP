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
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccItialIncomeService;
import com.chd.hrp.acc.service.books.subjaccount.GroupAccSubjLedgerService;
@Controller
public class GroupAccItialIncomeController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccItialIncomeController.class);
	
	@Resource(name = "groupAccItialIncomeService")
	private final GroupAccItialIncomeService groupAccItialIncomeService = null;
	
	@Resource(name = "groupAccSubjLedgerService")
	private final GroupAccSubjLedgerService groupAccSubjLedgerService = null;
	
	/**
	*初始收入表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/groupAccItialIncomeMainPage", method = RequestMethod.GET)
	public String accItialIncomeMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/accfinancialsubsidy/group/groupAccItialIncomeMain";
	}
	
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/groupAccExpendFinancialMainPage", method = RequestMethod.GET)
	public String accExpendFinancialMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/accfinancialsubsidy/group/groupAccExpendFinancialMain";
	}
	
	/**
	*初始收入表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/groupAccItialIncomeAddPage", method = RequestMethod.GET)
	public String accItialIncomeAddPage(Model mode) throws Exception {

		return "hrp/acc/accfinancialsubsidy/group/groupAccItialIncomeAdd";

	}
	/**
	*支出维护表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/groupAccItialExpendMainPage", method = RequestMethod.GET)
	public String accItialExpendMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/accfinancialsubsidy/group/groupAccItialExpendMain";
	}
	/**
	*支出维护表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/groupAccItialExpendAddPage", method = RequestMethod.GET)
	public String accItialExpendAddPage(Model mode) throws Exception {

		return "hrp/acc/accfinancialsubsidy/group/groupAccItialExpendAdd";

	}
	/**
	*初始收入表<BR>                            
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/addGroupAccItialIncome", method = RequestMethod.POST)
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
		
		String accMatchInitJson = groupAccItialIncomeService.addGroupAccItialIncome(mapVo);

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*支出维护表<BR>                            
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/addGroupAccItialExpend", method = RequestMethod.POST)
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
		
		String accMatchInitJson = groupAccItialIncomeService.addGroupAccItialIncome(mapVo);

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	
	/**
	*导入凭证信息<BR>                            
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/addBatchGroupAccItialExpend", method = RequestMethod.POST)
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
		
		String accMatchInitJson = groupAccItialIncomeService.addBatchGroupAccItialIncome(listVo);
	   
		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*初始收入表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/queryGroupAccItialIncome", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccItialIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("subj_type_code") == null){
	        
			mapVo.put("subj_type_code", "04");
				
		}
		
		String accMatchInitJson = groupAccItialIncomeService.queryGroupAccItialIncome(getPage(mapVo));

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*初始收入表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/queryGroupAccItialExpend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccItialExpend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("subj_type_code") == null){
	        
			mapVo.put("subj_type_code", "05");
				
		}
		
		String accMatchInitJson = groupAccItialIncomeService.queryGroupAccItialIncome(getPage(mapVo));

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*初始收入表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/deletBatchGroupAccItialIncome", method = RequestMethod.POST)
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
		
		String accMatchInitJson = groupAccItialIncomeService.deleteBatchGroupAccItialIncome(listVo);
	   
		return JSONObject.parseObject(accMatchInitJson);
			
	}
	
	/**
	*支出维护表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/deletBatchGroupAccItialExpend", method = RequestMethod.POST)
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
		
		String accMatchInitJson = groupAccItialIncomeService.deleteBatchGroupAccItialIncome(listVo);
	   
		return JSONObject.parseObject(accMatchInitJson);
			
	}
	
	/**
	*初始收入表<BR>
	*导入凭证页面
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/groupAccVouchImportMainPage", method = RequestMethod.GET)
	public String accVouchImportMainPage(Model mode) throws Exception {

		return "hrp/acc/accfinancialsubsidy/group/groupAccVouchImportMain";
	}
	
	/**
	*查询导入凭证需要的数据<BR>
	*导入凭证页面
	*/
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/queryGroupAccVouchImport", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccVouchImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accMatchInitJson = groupAccItialIncomeService.queryGroupAccVouchImport(getPage(mapVo));

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accfinancialsubsidy/group/groupCollectExpendFinchdancial", method = RequestMethod.POST)
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
		
		String accMatchInitJson = groupAccSubjLedgerService.collectGroupAccExpendFinancial(mapVo);

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	
}
