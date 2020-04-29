/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.tell;
import java.io.File;
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

import org.apache.commons.lang.StringUtils;
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
public class AccBankByAccountController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccBankByAccountController.class);
	
	@Resource(name = "accBankByAccountService")
	private final AccBankByAccountServiceImpl accBankByAccountService = null;
	
	@Resource(name = "accCashAccountService")
	private final AccCashAccountServiceImpl accCashAccountService = null;
   
    @Resource(name = "accTellService")
    private final AccTellServiceImpl accTellService = null;
    
    @Resource(name = "modStartService")
    private ModStartServiceImpl modStartService = null;
    
    @Resource(name = "accYearMonthService")
    private final AccYearMonthServiceImpl accYearMonthService = null;
    
    @Resource(name="accSubjService")
	private final AccSubjService accSubjService = null;
    
	@Resource(name="accCashierTypeService")
	private final AccCashierTypeServiceImpl accCashierTypeService = null;
	
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acctell/accBankByAccountMainPage", method = RequestMethod.GET)
	public String accBankByAccountMainPage(Model mode) throws Exception {
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		entityMap.put("cash_flag", "0");
		
		List<AccYearMonth> accYearMonthList = accYearMonthService.queryAccTellPeriod(entityMap);
		
		if(accYearMonthList.size() > 0){
			
			mode.addAttribute("begin_date", accYearMonthList.get(0).getBegin_date());
			
			mode.addAttribute("end_date", accYearMonthList.get(accYearMonthList.size()-1).getEnd_date());
			
		}
		/*entityMap.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(entityMap);
		
		if(modStart != null ){
			
			mode.addAttribute("modStartTime", modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");
		}*/
		String yearMonth = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getMinDate();
		
		mode.addAttribute("modStartTime", yearMonth.substring(0,4)+"-"+yearMonth.substring(4,6)+"-01");
		
		mode.addAttribute("a030", MyConfig.getSysPara("030"));
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		
		return "hrp/acc/acctell/accBankByAccountMain";

	}
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acctell/accBankByAccountAddPage", method = RequestMethod.GET)
	public String accBankByAccountAddPage(Model mode) throws Exception {
		mode.addAttribute("a030", MyConfig.getSysPara("030"));
		return "hrp/acc/acctell/accBankByAccountAdd";

	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acctell/accBankByAccountInstallPage", method = RequestMethod.GET)
	public String accBankByAccountPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accBankByAccountInstall";

	}
	
	/**
	*银行出纳账<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acctell/queryAccBankByAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankByAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
			
			String accBankByAccount = accTellService.queryCashAccountByCode(getPage(mapVo));

		return JSONObject.parseObject(accBankByAccount);
		
	}
	
	/**
	*银行出纳账<BR>
	*打印
	*/
	@RequestMapping(value = "/hrp/acc/acctell/queryAccBankByAccountPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankByAccountPrint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
			
			String accBankByAccount = accTellService.queryAccBankByAccountPrint(mapVo);

		return JSONObject.parseObject(accBankByAccount);
		
	}
	
	/**
	*银行出纳账<BR>
	*修改确认状态页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccBankByAccountStatePage", method = RequestMethod.GET)
	public String updateAccCashAccountStatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		mode.addAttribute("con_date", sdf.format(new Date()));
		
		return "hrp/acc/acctell/accBankByAccountStateUpdate";
	}
	
	/**
	*银行出纳账<BR>
	*修改确认状态
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccBankByAccountState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankByAccountState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		

		
		String accBankByAccount = accBankByAccountService.updateBatchAccTell(listVo);
			
		
		return JSONObject.parseObject(accBankByAccount);
	}
	
	/**
	*银行出纳账<BR>
	*修改确认状态
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccBankByAccountCancelState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankByAccountCancelState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		

		
		String accBankByAccount = accBankByAccountService.updateBatchAccTell(listVo);
			
		
		return JSONObject.parseObject(accBankByAccount);
	}
	
	/**
	*银行出纳账<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acctell/addAccBankByAccountPage", method = RequestMethod.GET)
	public String addAccBankByAccountPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			
			
            /*插入流水号中使用*/
			mode.addAttribute("tell_number", mapVo.get("tell_number"));
			
			mode.addAttribute("occur_date", mapVo.get("occur_date"));
		
		return "hrp/acc/acctell/accBankByAccountAdd";
	}
	
	/**
	*银行出纳账<BR>
	*添加
	*/
	@RequestMapping(value = "/hrp/acc/acctell/addAccBankByAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccBankByAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
				mapVo.put("subj_nature_code", "03");
				accBankByAccountService.updateAccTellMaxTellNumber(mapVo);
			}
			
			String tell_type_code=mapVo.get("tell_type_code").toString();
			
	    	String accBankByAccount = accBankByAccountService.addAccBankByAccount(mapVo);
		
	    	if("1".equals(mapVo.get("subj_code"))){
			 
			 if(!"".equals(mapVo.get("other_subj_code").toString())){
				 
	              Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("group_id", SessionManager.getGroupId());
					
					map.put("hos_id", SessionManager.getHosId());
					
					map.put("copy_code", SessionManager.getCopyCode());
					
					map.put("acc_year", SessionManager.getAcctYear());
					
					map.put("subj_code", mapVo.get("other_subj_code"));
					
					map.put("subj_nature_code", "02");
					
					AccSubj accSubj =  accSubjService.queryAccSubjNature(map);
					
					if(accSubj !=null){
						
						String cash_subj_code = mapVo.get("cash_subj_code").toString();
						
						String other_subj_code = mapVo.get("other_subj_code").toString();
						
						mapVo.put("cash_subj_code", other_subj_code);
						
						mapVo.put("other_subj_code", cash_subj_code);
						
						mapVo.put("tell_type_code", tell_type_code.split(" ")[0]);
						
						mapVo.put("kind_code", tell_type_code.split(" ")[1]);
						
						mapVo.put("tell_number", "");
						
						List<AccCashierType> list=accCashierTypeService.queryAccCashierTypeByKindCode(mapVo);
						
						mapVo.put("tell_type_code", list.get(0).getTell_type_code()+" "+tell_type_code.split(" ")[1]);
						
						accCashAccountService.addAccCashAccount(mapVo);
					}
			 }
			
		}
		
		return JSONObject.parseObject(accBankByAccount);
	}
	
	/**
	*银行出纳账<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acctell/deleteAccBankByAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBankByAccount(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String group_id = SessionManager.getGroupId();
		
		String hos_id = SessionManager.getHosId();
		
		String copy_code = SessionManager.getCopyCode();
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids = id.split("@");
			
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
		
		String accCashAccount = accBankByAccountService.deleteBatchAccBankByAccount(listVo);
		
		return JSONObject.parseObject(accCashAccount);
	}
	
	
	/**
	*银行出纳账<BR>
	*修改 维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccBankByAccountPage", method = RequestMethod.GET)
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
			
			AccTell accTell = accBankByAccountService.queryAccBankByAccountByCode(mapVo);
			
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
			mode.addAttribute("debit", accTell.getDebit());
			mode.addAttribute("credit", accTell.getCredit());
			mode.addAttribute("tell_number", accTell.getTell_number());
			
		
		return "hrp/acc/acctell/accBankByAccountUpdate";
	}
	
	/**
	*银行出纳账<BR>
	*修改AccTell
	*/
	@RequestMapping(value = "/hrp/acc/acctell/updateAccBankByAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankByAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			String accBankByAccount = accBankByAccountService.updateAccBankByAccount(mapVo);
		
		return JSONObject.parseObject(accBankByAccount);
	}
	
	//下载导入模版
	@RequestMapping(value="/hrp/acc/acctell/downBankByAccountTemplate", method = RequestMethod.GET)  
	public String downBankByAccountTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
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
				
		String downLoadPath = ctxPath + "银行出纳账.xls";
				
		ExcelWriter.exportExcel(new File(downLoadPath), list);
				
		printTemplate(request, response, "acc\\downTemplate", "银行出纳账.xls");

		return null; 
	}
	
	//导入页面跳转	
	@RequestMapping(value = "/hrp/acc/acctell/accBankByAccountImportPage", method = RequestMethod.GET)
	public String accBankByAccountImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/acctell/accBankByAccountImport";
	}
	
	/**
	 * 银行出纳账<BR>
	 * 导入
	 */
	@RequestMapping(value="/hrp/acc/acctell/readAccBankByAccounttFiles",method = RequestMethod.POST)  
    public String readAccCashAccountFiles(Plupload plupload,HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {
		List<String> list_err = new ArrayList<String>();
//		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		StringBuffer err_sb = new StringBuffer();

		for (int i = 1; i < list.size(); i++) {
			String temp[] = list.get(i);// 行
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
			if (ExcelReader.validExceLColunm(temp, 0) && StringUtils.isNotEmpty(temp[0])) {
				mapVo.put("tell_type_name", temp[0]);
				AccCashierType accCashierType = accCashierTypeService.queryAccCashierTypeByCode(mapVo);
				if (accCashierType != null) {
					mapVo.put("tell_type_code",
							accCashierType.getTell_type_code() + " " + accCashierType.getTell_type_name());
				} else {
					err_sb.append("第" + i + "行" + "出纳类型不存在  ");
				}
			} else {
				err_sb.append("第" + i + "行" + "出纳类型为空  ");
			}

			// 科目编码
			if (ExcelReader.validExceLColunm(temp, 1) && StringUtils.isNotEmpty(temp[1])) {
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
			if (ExcelReader.validExceLColunm(temp, 2) && StringUtils.isNotEmpty(temp[2])) {// 判断日期是否为空
				String reg = "[\\d]{4}[-][\\d]{2}-[\\d]{2}";
				boolean flag = temp[2].toString().matches(reg);
				if (flag == false) {// 判断日期格式是否正确
					err_sb.append("第" + i + "行" + "日期格式错误 ");
				} else {
//					Calendar cal = Calendar.getInstance();
//					Integer curYear = cal.get(Calendar.YEAR);
//					Integer curMonth = cal.get(Calendar.MONTH) + 1;
//					String month = "";
//					if (curMonth > 0 && curMonth <= 9) {
//						month = "0" + curMonth;
//					} else {
//						month = curMonth.toString();
//					}
//
//					String curYearMonth = curYear + "-" + month;
//					String createDate = temp[2].toString().substring(0, 7);
//					if (!createDate.equals(curYearMonth)) {// 判断是否是当前年月
//						err_sb.append("第" + i + "行" + "出纳日期不是当前年月 ");
//					}

					mapVo.put("occur_date", temp[2]);
				}
			} else {
				err_sb.append("第" + i + "行" + "出纳日期为空  ");
			}

			// 摘要
			if (ExcelReader.validExceLColunm(temp, 3) && StringUtils.isNotEmpty(temp[3])) {
				mapVo.put("summary", temp[3]);
			} else {
				err_sb.append("第" + i + "行" + "摘要为空 ");
			}

			// 附件张数
			if (ExcelReader.validExceLColunm(temp, 4) && StringUtils.isNotEmpty(temp[4])) {
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
			if (ExcelReader.validExceLColunm(temp, 5) && StringUtils.isNotEmpty(temp[5])) {
				mapVo.put("check_no", temp[5]);
			} else {
				mapVo.put("check_no", "");
			}

			// 对方科目
			if (ExcelReader.validExceLColunm(temp, 6) && StringUtils.isNotEmpty(temp[6])) {
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
				mapVo.put("other_subj_id", "");
			}

			// 收入金额
			if (ExcelReader.validExceLColunm(temp, 7)) {
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
			} else {
				mapVo.put("debit", 0);
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
			// maplist.add(mapVo);

			if (err_sb.toString().length() > 0) {
				list_err.add(err_sb.toString());
			} else {
				accBankByAccountService.addAccBankByAccount(mapVo);
			}
		}

//		if (list_err.size() <= 0) {
//			accTellService.addBatchAccTell(maplist);
//		}

		response.getWriter().print(ChdJson.toJson(err_sb.toString()));
		return null;
	}
	
	@RequestMapping(value = "/hrp/acc/acctell/addBatchAccBankAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccBankAccount(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String accCashAccount = accBankByAccountService.addBatchAccBankAccount(paramVo);
		
		return JSONObject.parseObject(accCashAccount);
	}
}

