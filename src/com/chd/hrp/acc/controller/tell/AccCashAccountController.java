package com.chd.hrp.acc.controller.tell;

/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccCashierType;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccTell;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.acc.serviceImpl.AccYearMonthServiceImpl;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCashierTypeServiceImpl;
import com.chd.hrp.acc.serviceImpl.tell.AccBankByAccountServiceImpl;
import com.chd.hrp.acc.serviceImpl.tell.AccCashAccountServiceImpl;
import com.chd.hrp.acc.serviceImpl.tell.AccTellServiceImpl;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;

/**
* @Title. @Description.
* 出纳账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccCashAccountController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccCashAccountController.class);
   
	@Resource(name = "accTellService")
	private final AccTellServiceImpl accTellService = null;
    
	@Resource(name = "accYearMonthService")
	private final AccYearMonthServiceImpl accYearMonthService = null;
	
	@Resource(name = "accBankByAccountService")
	private final AccBankByAccountServiceImpl accBankByAccountService = null;
	
	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;
	
	@Resource(name = "accCashAccountService")
	private final AccCashAccountServiceImpl accCashAccountService = null;
	
	@Resource(name="accSubjService")
	private final AccSubjService accSubjService = null;
	
	@Resource(name="accCashierTypeService")
	private final AccCashierTypeServiceImpl accCashierTypeService = null;
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acctell/accCashAccountMainPage", method = RequestMethod.GET)
	public String accCashAccountMainPage(Model mode) throws Exception {
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		entityMap.put("cash_flag", "0");
		
		List<AccYearMonth> accYearMonthList = accYearMonthService.queryAccTellPeriod(entityMap);
		
		if(accYearMonthList.size() > 0 ){
			
			mode.addAttribute("begin_date", accYearMonthList.get(0).getBegin_date());
			
			mode.addAttribute("end_date", accYearMonthList.get(accYearMonthList.size()-1).getEnd_date());
			
		}
		/*entityMap.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(entityMap);
		
		if(modStart != null){
			
			mode.addAttribute("modStartTime", modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");
		}*/
		
		String yearMonth = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getMinDate();
		
		mode.addAttribute("modStartTime", yearMonth.substring(0,4)+"-"+yearMonth.substring(4,6)+"-01");
		
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		mode.addAttribute("a030", MyConfig.getSysPara("030"));
		
		return "hrp/acc/acctell/accCashAccountMain";

	}
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acctell/accCashAccountAddPage", method = RequestMethod.GET)
	public String accCashAccountAddPage(Model mode) throws Exception {
		mode.addAttribute("a030", MyConfig.getSysPara("030"));
		return "hrp/acc/acctell/accCashAccountAdd";

	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acctell/accCashAccountInstallPage", method = RequestMethod.GET)
	public String accCashAccountPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accCashAccountInstall";

	}
	
	
	/**
	*出纳账<BR>  现金出纳账
	*摘要维护页面跳转
	*/ 
	@RequestMapping(value = "/hrp/acc/acctell/accTellSummaryPage", method = RequestMethod.GET)
	public String accTellSummaryPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accTellSummary";

	}
	
	/**
	*出纳账<BR>  银行出纳账
	*摘要维护页面跳转
	*/ 
	@RequestMapping(value = "/hrp/acc/acctell/accTellBankSummaryPage", method = RequestMethod.GET)
	public String accTellBankSummaryPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accTellSummary";

	}
	
	
	
	/**
	*现金出纳账<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acctell/queryAccCashAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCashAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
			mapVo.put("mod_code", "0101");
			
		String accCashAccount = accTellService.queryCashAccountByCode(getPage(mapVo));

		return JSONObject.parseObject(accCashAccount);
		
	}
	
	/**
	*现金出纳账<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acctell/queryAccCashAccountPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCashAccountPrint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
			mapVo.put("mod_code", "0101");
			
		String accCashAccount = accTellService.queryAccCashAccountPrint(mapVo);

		return JSONObject.parseObject(accCashAccount);
		
	}
	
	/**
	*现金出纳账<BR>
	*修改确认状态页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccCashAccountStatePage", method = RequestMethod.GET)
	public String updateAccCashAccountStatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		mode.addAttribute("con_date", sdf.format(new Date()));
		
		return "hrp/acc/acctell/accCashAccountStateUpdate";
	}
	
	/**
	*现金出纳账<BR>
	*修改确认状态
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccCashAccountState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCashAccountState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
			
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			for (String id: paramVo.split(",")) {
				
				String[] ids = id.split("@");
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
	            
	            mapVo.put("group_id", SessionManager.getGroupId());
	            
	            mapVo.put("hos_id", SessionManager.getHosId());
	            
	            mapVo.put("copy_code", SessionManager.getCopyCode());
	            
	            mapVo.put("tell_id", ids[0]);//实际实体类变量
	            
	            mapVo.put("con_date", ids[1]);//实际实体类变量
	            
	            mapVo.put("con_user", SessionManager.getUserId());//实际实体类变量
	            
	            mapVo.put("is_con", '1');//实际实体类变量
	            
	            listVo.add(mapVo);
	        }
			
			String accCashAccount = accCashAccountService.updateBatchAccTell(listVo);
			
		
		return JSONObject.parseObject(accCashAccount);
	}
	
	/**
	*现金出纳账<BR>
	*修改取消
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccCashAccountCancelState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCashAccountCancelState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
			
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			for (String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
	            
	            mapVo.put("group_id", SessionManager.getGroupId());
	            
	            mapVo.put("hos_id", SessionManager.getHosId());
	            
	            mapVo.put("copy_code", SessionManager.getCopyCode());
	            
	            mapVo.put("tell_id", id);//实际实体类变量
	            
	            mapVo.put("cancel_con_date", "cancel_con_date");//实际实体类变量
	            
	            mapVo.put("cancel_con_user", "cancel_con_user");//实际实体类变量
	            
	            mapVo.put("is_con", "0");//实际实体类变量
	            
	            listVo.add(mapVo);
	        }
			
			
			String accCashAccount = accCashAccountService.updateBatchAccTell(listVo);
			
		
		return JSONObject.parseObject(accCashAccount);
	}
	
	
	/**
	*现金出纳账<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acctell/addAccCashAccountPage", method = RequestMethod.GET)
	public String addAccCashAccountPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			mapVo.put("mod_code", "0101");
			
			ModStart modStart = modStartService.queryModStartByCode(mapVo);
			
			mode.addAttribute("modStartTime", modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");
			
			mode.addAttribute("modStartTime", modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");
			
            /*插入中使用*/
			mode.addAttribute("tell_number", mapVo.get("tell_number"));
			
			mode.addAttribute("occur_date", mapVo.get("occur_date"));
		
		return "hrp/acc/acctell/accCashAccountAdd";
	}
	
	/**
	*现金出纳账<BR>
	*添加
	*/
	@RequestMapping(value = "/hrp/acc/acctell/addAccCashAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccCashAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			mapVo.put("create_date", sdf.format(new Date()));
			
			mapVo.put("acc_year", mapVo.get("occur_date").toString().substring(0, 4));
			
			mapVo.put("create_user", SessionManager.getUserId());
		
			if(!"".equals(mapVo.get("tell_number").toString())){
				
				mapVo.put("subj_nature_code", "02");
				
				accCashAccountService.updateAccTellMaxTellNumber(mapVo);
				
			}
			
			String tell_type_code=mapVo.get("tell_type_code").toString();
			
			String accCashAccount =	accCashAccountService.addAccCashAccount(mapVo);
			
			if("1".equals(mapVo.get("subj_code"))){
				
				if(!"".equals(mapVo.get("other_subj_code").toString())){
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("group_id", SessionManager.getGroupId());
					
					map.put("hos_id", SessionManager.getHosId());
					
					map.put("copy_id", SessionManager.getCopyCode());
					
					map.put("acc_year", mapVo.get("occur_date").toString().substring(0, 4));
					
					map.put("subj_code", mapVo.get("other_subj_code"));
					
					map.put("subj_nature_code", "03");
					
					AccSubj accSubj =  accSubjService.queryAccSubjNature(map);
					
					if(accSubj!=null){
						
						String cash_subj_code = mapVo.get("cash_subj_code").toString();
						
						String other_subj_code = mapVo.get("other_subj_code").toString();
						
						mapVo.put("cash_subj_code", other_subj_code);
						
						mapVo.put("other_subj_code", cash_subj_code);
						
						mapVo.put("tell_type_code", tell_type_code.split(" ")[0]);
						
						mapVo.put("kind_code", tell_type_code.split(" ")[1]);
						
						mapVo.put("tell_number", "");
						
						List<AccCashierType> list=accCashierTypeService.queryAccCashierTypeByKindCode(mapVo);
						
						mapVo.put("tell_type_code", list.get(0).getTell_type_code()+" "+tell_type_code.split(" ")[1]);
						
						accBankByAccountService.addAccBankByAccount(mapVo);
					}
				}
				
			}
		
		return JSONObject.parseObject(accCashAccount);
	}
	
	@RequestMapping(value = "/hrp/acc/acctell/addBatchAccCashAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccCashAccount(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String accCashAccount = accCashAccountService.addBatchAccCashAccount(paramVo);
		
		return JSONObject.parseObject(accCashAccount);
	}
	
	/**
	*现金出纳账<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acctell/deleteAccCashAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCashAccount(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String group_id = SessionManager.getGroupId();
		
		String hos_id = SessionManager.getHosId();
		
		String copy_code = SessionManager.getCopyCode();
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("tell_id", ids[0]);//实际实体类变量
            
            mapVo.put("occur_date", ids[1]);//实际实体类变量
            
            mapVo.put("tell_number", ids[2]);
            
            mapVo.put("tell_type_code", ids[3]);
            
            mapVo.put("cash_subj_code", ids[4]);
            
            mapVo.put("group_id", group_id);
            
            mapVo.put("hos_id", hos_id);
            
            mapVo.put("copy_code", copy_code);
            
            listVo.add(mapVo);
        }
		
		String accCashAccount = accCashAccountService.deleteBatchAccCashAccount(listVo);
		
		return JSONObject.parseObject(accCashAccount);
	}
	
	/**
	*现金出纳账<BR>
	*修改 维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccCashAccountPage", method = RequestMethod.GET)
	public String updateAccCashAccountPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			 
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			AccTell accTell = accTellService.queryAccTellByCode(mapVo);
			
			mode.addAttribute("group_id",accTell.getGroup_id());
			mode.addAttribute("hos_id", accTell.getHos_id());
			mode.addAttribute("copy_code", accTell.getCopy_code());
			mode.addAttribute("tell_id", accTell.getTell_id());
			mode.addAttribute("subj_id",accTell.getSubj_id());
			mode.addAttribute("subj_code",accTell.getCash_subj_code());
			mode.addAttribute("subj_name",accTell.getSubj_name());
			mode.addAttribute("other_subj_id", accTell.getOther_subj_id());
			mode.addAttribute("other_subj_code",accTell.getOther_subj_code());
			mode.addAttribute("other_subj_name",accTell.getOther_subj_name());
			mode.addAttribute("create_date", sdf.format(accTell.getCreate_date()));
			mode.addAttribute("summary", accTell.getSummary());
			mode.addAttribute("att_num", accTell.getAtt_num());
			mode.addAttribute("check_no",accTell.getCheck_no());
			mode.addAttribute("other_subj_id",accTell.getOther_subj_id());
			mode.addAttribute("debit", accTell.getDebit());
			mode.addAttribute("credit", accTell.getCredit());
			mode.addAttribute("tell_number", accTell.getTell_number());
		
		return "hrp/acc/acctell/accCashAccountUpdate";
	}
	
	/**
	*现金出纳账<BR>
	*修改
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccCashAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCashAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
/*			mapVo.put("acc_year", mapVo.get("create_date").toString().substring(0, 4));
			
			mapVo.put("create_user", SessionManager.getUserId());*/
			
			String accCashAccount = accCashAccountService.updateAccCashAccount(mapVo);
			
	      /*  if("1".equals(mapVo.get("subj_id"))){
				
				if(!"".equals(mapVo.get("other_subj_id").toString())){
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("group_id", SessionManager.getGroupId());
					
					map.put("hos_id", SessionManager.getHosId());
					
					map.put("copy_id", SessionManager.getCopyCode());
					
					map.put("acc_year", SessionManager.getAcctYear());
					
					map.put("subj_id", mapVo.get("other_subj_id"));
					
					map.put("subj_nature_code", "03");
					
					AccSubj accSubj =  accSubjService.queryAccSubjNature(map);
					
					if(accSubj!=null){
						
						mapVo.remove("tell_id");
						
						String cash_subj_id = mapVo.get("cash_subj_id").toString();
						
						String other_subj_id = mapVo.get("other_subj_id").toString();
						
						System.out.println("mapVo = " + mapVo);
						
						mapVo.put("cash_subj_id", other_subj_id);
						
						mapVo.put("other_subj_id", cash_subj_id);
						
						accBankByAccountService.addAccBankByAccount(mapVo);
					}
				}
				
			}*/
		
		return JSONObject.parseObject(accCashAccount);
	}
	
	//下载导入模版
	@RequestMapping(value="/hrp/acc/acctell/downTemplate", method = RequestMethod.GET)  
	public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
		    	HttpServletResponse response,Model mode) throws IOException { 
			
		List<List> list = new ArrayList();
			
		List<String> listdata = new ArrayList<String>();
			
		listdata.add("出纳类型");
		
		listdata.add("科目编码");
			
		listdata.add("出纳日期");
			
		listdata.add("摘要");
			
		listdata.add("附件张数");
			
		listdata.add("票据号");
			
		listdata.add("对方科目");
			
		listdata.add("收入金额");
			
		listdata.add("支出金额");
			
		list.add(listdata);
			
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
			
		String downLoadPath = ctxPath + "现金出纳账.xls";
			
		ExcelWriter.exportExcel(new File(downLoadPath), list);
			
		printTemplate(request, response, "acc\\downTemplate", "现金出纳账.xls");

		return null; 
	}

	//导入页面跳转	
	@RequestMapping(value = "/hrp/acc/acctell/accCashAccountImportPage", method = RequestMethod.GET)
	public String accCashAccountImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/acctell/accCashAccountImport";
	}
	
	/**
	 * 现金出纳账<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/acc/acctell/readAccCashAccountFiles", method = RequestMethod.POST)
	public String readAccCashAccountFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {
		StringBuffer err_sb = new StringBuffer();
		List<String> list_err = new ArrayList<String>();
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		try {
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			if (list.isEmpty()) {
				response.getWriter().print(ChdJson.toJson("文件中无数据"));
				return null;
			}
			
			// 填充空值，防空指针异常
			int length = list.get(0).length;// 列数
			for (int i = 1; i < list.size(); i++) {
				int b = 0;
				String temp[] = list.get(i);// 行数据
				if (temp.length < length) {
					String temp2[] = new String[length];
					for (int j = 0; j < length; j++) {
						if (j < temp.length) {
							if (StringUtils.isEmpty(temp[j])) {
								b++;
							}
							temp2[j] = temp[j];
						} else {
							temp2[j] = "";
						}
					}
					temp = temp2;
				}

				if (b != temp.length) {
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
					if (mapVo.get("acc_year") == null) {
						mapVo.put("acc_year", SessionManager.getAcctYear());
					}
					
					// 出纳类型
					if (StringUtils.isNotEmpty(temp[0])) {
						mapVo.put("tell_type_name", temp[0]);
						AccCashierType accCashierType = accCashierTypeService.queryAccCashierTypeByCode(mapVo);
						if (accCashierType != null) {
//							mapVo.put("tell_type_code", 
//									accCashierType.getTell_type_code() + "" + accCashierType.getTell_type_name());
							mapVo.put("tell_type_code", accCashierType.getTell_type_code());
						} else {
							err_sb.append("第" + i + "行" + "出纳类型不存在  ");
						}
					} else {
						err_sb.append("第" + i + "行" + "出纳类型为空  ");
					}

					// 科目编码
					if (StringUtils.isNotEmpty(temp[1])) {
						String reg = "\\d+";
						boolean flag = temp[1].toString().matches(reg);
						if (flag == false) {
							err_sb.append("第" + i + "行" + "科目编码必须是数字  ");
						} else {
							mapVo.put("subj_code", temp[1]);
							AccSubj accSubj = accSubjService.queryAccSubjCode(mapVo);
							if (accSubj != null) {
								// 判断当前excle的科目是不是末级，如若不判断会提示导入成功，但是查询不到
								if (accSubj.getIs_last() == 0) {
									err_sb.append("第" + i + "行" + "科目不是末级科目 ");
								} else {
									mapVo.put("cash_subj_code", accSubj.getSubj_code());
								}
							} else {
								err_sb.append("第" + i + "行" + "科目编码不存在  ");
							}
						}
					} else {
						err_sb.append("第" + i + "行" + "科目编码为空  ");
					}

					// 出纳日期
					if (StringUtils.isNotEmpty(temp[2])) {// 判断日期是否为空
						String reg = "[\\d]{4}[-][\\d]{2}-[\\d]{2}";
						boolean flag = temp[2].toString().matches(reg);
						if (flag == false) {// 判断日期格式是否正确
							err_sb.append("第" + i + "行" + "日期格式错误 ");
						} else {
							Calendar cal = Calendar.getInstance();
							Integer curYear = cal.get(Calendar.YEAR);
							Integer curMonth = cal.get(Calendar.MONTH) + 1;
							String month = "";
							if (curMonth > 0 && curMonth <= 9) {
								month = "0" + curMonth;
							} else {
								month = curMonth.toString();
							}
							String curYearMonth = curYear + "-" + month;
							String createDate = temp[2].toString().substring(0, 7);
							if (!createDate.equals(curYearMonth)) {// 判断是否是当前年月
								err_sb.append("第" + i + "行" + "出纳日期不是当前年月  ");
							}
							mapVo.put("occur_date", temp[2]);
						}
					} else {
						err_sb.append("第" + i + "行" + "出纳日期为空  ");
					}

					// 摘要
					if (StringUtils.isNotEmpty(temp[3])) {
						mapVo.put("summary", temp[3]);
					} else {
						err_sb.append("第" + i + "行" + "摘要为空 ");
					}

					// 附件张数
					if (StringUtils.isNotEmpty(temp[4])) {
						String reg = "\\d{1,19}";
						boolean flag = temp[4].toString().matches(reg);
						if (flag == false) {
							err_sb.append("第" + i + "行" + "附件张数格式错误  ");
						} else {
							mapVo.put("att_num", temp[4]);
						}
					} else {
						err_sb.append("第" + i + "行" + "附件张数为空  ");
					}

					// 票据号
					if (StringUtils.isNotEmpty(temp[5])) {
						mapVo.put("check_no", temp[5]);
					} else {
						mapVo.put("check_no", "");
					}

					// 对方科目
					if (StringUtils.isNotEmpty(temp[6])) {
						String reg = "\\d+";
						boolean flag = temp[6].toString().matches(reg);
						if (flag == false) {
							err_sb.append("第" + i + "行" + "对方科目编码必须是数字  ");
						} else {
							mapVo.put("subj_code", temp[6]);
							AccSubj accSubj = accSubjService.queryAccSubjCode(mapVo);
							if (accSubj != null) {
								mapVo.put("other_subj_code", accSubj.getSubj_code());
							} else {
								err_sb.append("第" + i + "行" + "对方科目不存在  ");
							}
						}
					} else {
						mapVo.put("other_subj_code", "");
					}

					// 收入金额
					if (StringUtils.isNotEmpty(temp[7])) {
						String reg = "[\\d]+(\\.\\d+)?";
						boolean flag = temp[7].toString().matches(reg);
						if (flag == false) {
							err_sb.append("第" + i + "行" + "收入金额格式错误  ");
						}
						mapVo.put("debit", temp[7]);
					} else {
						err_sb.append("第" + i + "行" + "收入金额为空  ");
					}

					// 支出金额
					if (ExcelReader.validExceLColunm(temp, 8)) {
						if (StringUtils.isNotEmpty(temp[8])) {
							String reg = "[\\d]+[\\.]?[\\d]{0,3}";
							boolean flag = temp[8].toString().matches(reg);
							if (flag == false) {
								err_sb.append("第" + i + "行" + "支出金额格式错误  ");
							} else {
								mapVo.put("credit", temp[8]);
							}
						} else {
							err_sb.append("第" + i + "行" + "支出金额为空  ");
						}
					} else {
						mapVo.put("credit", 0);
					}

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					mapVo.put("debit_w", 0);
					mapVo.put("credit_w", 0);
					mapVo.put("create_date", sdf.format(new Date()));
					mapVo.put("pay_type_code", "");
					mapVo.put("create_user", SessionManager.getUserId());
					mapVo.put("con_user", "");
					mapVo.put("con_date", "");
					mapVo.put("state", 0);
					mapVo.put("check_user", "");
					mapVo.put("check_date", "");
					mapVo.put("is_init", 0);
					mapVo.put("vouch_check_id", "");
					mapVo.put("vouch_detail_id", "");
					mapVo.put("vouch_id", "");
					mapVo.put("is_import", 2);
					mapVo.put("is_con", 0);
					mapVo.put("busi_type", "");
					mapVo.put("business_no", "");
					mapVo.put("tell_number", "");
					mapVo.put("tell_id", accCashAccountService.queryAccTellNextSeq());
					// maplist.add(mapVo);

					if (err_sb.toString().length() > 0) {
						list_err.add(err_sb.toString());
					} else {
						maplist.add(mapVo);
						// accCashAccountService.addAccCashAccount(mapVo);
					}
				} else {
					break;
				}
			}

//			if (list_err.size() > 0) {
//				response.getWriter().print(ChdJson.toJson(list_err.toString()));
//			} else {
				if (maplist.size() > 0) {
					accCashAccountService.addBatchAccCash(maplist);
				}
//			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			err_sb = new StringBuffer();
			err_sb.append("导入系统错误!");
			list_err.add(err_sb.toString());

		}
		response.getWriter().print(ChdJson.toJson(err_sb.toString()));
		return null;
	}
	
	/**
	*现金出纳账<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acctell/addAccTellVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccTellVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//mapVo.get("ParamVo").toString(),mapVo.get("vouch_type_code").toString()
		String accCashAccount = accCashAccountService.addAccTellVouch(mapVo);
		
		return JSONObject.parseObject(accCashAccount);
		
	}
	
	/**
	*现金出纳账<BR>
	*摘要字典查询
	*/
	@RequestMapping(value = "/hrp/acc/acctell/queryAccTellSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTellSummary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			mapVo.put("hos_id", SessionManager.getHosId());
			 
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
			
			mapVo.put("user_id", SessionManager.getUserId()); 
				
		    mapVo.put("summary",mapVo.get("summary").toString().toUpperCase()); 
			 
		String accTellSummary = accTellService.queryAccTellSummary(getPage(mapVo));

		return JSONObject.parseObject(accTellSummary);
		
	}
	
	/**
	*现金出纳账<BR>
	*摘要字典添加
	*/
	@RequestMapping(value = "/hrp/acc/acctell/addAccTellSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccTellSummary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		 
		mapVo.put("hos_id", SessionManager.getHosId());
		 
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		
		mapVo.put("user_id", SessionManager.getUserId()); 
		 
	    String accTellSummary = accTellService.addAccTellSummary(mapVo);

		return JSONObject.parseObject(accTellSummary);  
		
	}
	 
	/**
	*现金出纳账<BR>
	*摘要字典  删除
	*/ 
    @RequestMapping(value = "/hrp/acc/acctell/deleteAccTellSummary", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> deleteAccTellSummary(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
    	
		String group_id = SessionManager.getGroupId();
		
		String hos_id = SessionManager.getHosId();
		
		String copy_code = SessionManager.getCopyCode();
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		 
		for ( String id: paramVo.split(",")) {
			
			String[] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("sid", ids[0]);//实际实体类变量
            
            mapVo.put("user_id", ids[1]);//实际实体类变量        
            
            mapVo.put("group_id", group_id);
            
            mapVo.put("hos_id", hos_id);
            
            mapVo.put("copy_code", copy_code);
            
            listVo.add(mapVo);
        }
		
		 String accTellSummary = accTellService.deleteBatchAccTellSummary(listVo);

			return JSONObject.parseObject(accTellSummary);  
	}
	
	/**
	*现金出纳账<BR>
	*摘要字典修改
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccTellSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccTellSummary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
			mapVo.put("group_id", SessionManager.getGroupId()); 
			
			mapVo.put("hos_id", SessionManager.getHosId());
			 
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	          
	        String accTellSummary = accTellService.updateAccTellSummary(mapVo);

			return JSONObject.parseObject(accTellSummary);  
	}
	
	/**
	 * @Description 下载导入模版  出纳账管理 摘要字典 
	 */
	@RequestMapping(value = "/hrp/acc/acctell/downTemplateSummary", method = RequestMethod.GET)
	public String downTemplateSummary(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

			List<List> list = new ArrayList<List>();
			
			List<String> listdata = new ArrayList<String>();
			
			listdata.add("摘要");
			 
			list.add(listdata);
			
			String ctxPath = request.getSession().getServletContext().getRealPath("/")
					+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
			
			String downLoadPath = ctxPath + "摘要字典维护.xlsx";
			
			ExcelWriter.exportExcel(new File(downLoadPath), list);
			
			printTemplate(request, response, "acc\\downTemplate", "摘要字典维护.xlsx");
	
			return null; 
	}
	
	
	
	/**
	 * @Description 导入跳转页面    出纳账管理 摘要字典 
	 */
	@RequestMapping(value = "/hrp/acc/acctell/importAccTellSummary", method = RequestMethod.POST)
	@ResponseBody
	public String importAccTellSummary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson= accTellService.importAccTellSummary(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	@RequestMapping(value = "/hrp/acc/acctell/queryAccTellVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTellVouch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String accCashAccount = accCashAccountService.queryAccTellVouchState(paramVo);
		
		return JSONObject.parseObject(accCashAccount);
		
	}
	
	/**
	*现金出纳账<BR>
	*手工选择凭证类型页面
	*/
	@RequestMapping(value = "/hrp/acc/acctell/chooseAccVouchTypePage", method = RequestMethod.GET)
	public String chooseAccVouchTypePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		
		mode.addAttribute("tell_id", mapVo.get("tell_id"));
		
		return "hrp/acc/acctell/chooseAccVouchTypeMain";
	}
	 
}

