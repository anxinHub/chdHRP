package com.chd.hrp.acc.controller.tell;

/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.entity.AccPayType;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccTell;
import com.chd.hrp.acc.entity.AccVouchCheck;
import com.chd.hrp.acc.service.AccParaService;
import com.chd.hrp.acc.service.commonbuilder.AccPayTypeService;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.acc.serviceImpl.AccParaServiceImpl;
import com.chd.hrp.acc.serviceImpl.tell.AccBankAccountServiceImpl;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;

/**
* @Title. @Description.
* 银行未达账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccBankAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBankAccountController.class);
   
	@Resource(name = "accBankAccountService")
	private final AccBankAccountServiceImpl accBankAccountService = null;
	
	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;
	
	@Resource(name = "accSubjService")
	private final AccSubjService accSubjService = null ;
	
	@Resource(name = "accPayTypeService")
	private final AccPayTypeService accPayTypeService = null; 
	
	@Resource(name = "accParaService")
	private final AccParaService accParaService = null;
	
	/**
	*银行未达账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/account/accBankAccountMainPage", method = RequestMethod.GET)
	public String accBankAccountMainPage(Model mode,Map<String, Object> mapVo) throws Exception {
		
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String yearMonth = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthCash();
			
			mapVo.put("yearMonth", yearMonth.substring(0,4)+"-"+yearMonth.substring(4,6)+"-01");
			
			/*mapVo.put("mod_code", "0101");
			
			ModStart modStart = modStartService.queryModStartByCode(mapVo);
			
			if(modStart.getStart_year() !=null || modStart.getStart_month() != null){
			
			mode.addAttribute("startYearMonthEnd", DateUtil.getLastYear_Month(modStart.getStart_year() + modStart.getStart_month()));
			
			mapVo.put("startYearMonth",modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");
			}*/

		return "hrp/acc/account/accBankAccountMain";

	}
	/**
	*银行未达账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/account/accBankAccountAddPage", method = RequestMethod.GET)
	public String accBankAccountAddPage(Model mode,Map<String, Object> mapVo) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String yearMonth = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthCash();
			
		mapVo.put("startYearMonth", yearMonth.substring(0,4)+"-"+yearMonth.substring(4,6)+"-01");
			
		/*mapVo.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(mapVo);
		
		mapVo.put("startYearMonth",modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");*/

		return "hrp/acc/account/accBankAccountAdd";

	}
	
	/**
	*银行未达账<BR>
	*初始余额页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/account/accBankAccountInstallPage", method = RequestMethod.GET)
	public String accBankAccountInstallPage(Model mode) throws Exception {

		return "hrp/acc/account/accBankAccountInstall";

	}
	
	@RequestMapping(value = "/hrp/acc/account/queryAccBankAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("mod_code", "01");
		
		mapVo.put("para_code", "018");
		
		 mapVo.put("is_init", "1");
		
		String accBankAccount = accBankAccountService.queryAccBankAccount(getPage(mapVo));

		return JSONObject.parseObject(accBankAccount);
		
	}
	
	@RequestMapping(value = "/hrp/acc/account/addAccBankAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccBankAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		mapVo.put("para_code", "018");
		
		String accBankCheckJson = accBankAccountService.addAccBankAccount(mapVo);

		return JSONObject.parseObject(accBankCheckJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/account/deleteAccBankAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBankAccount(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("bank_id", id.split("@")[0]);
			
			mapVo.put("group_id", id.split("@")[1]);
			
			mapVo.put("hos_id", id.split("@")[2]);
			
            mapVo.put("copy_code", id.split("@")[3]);
            
            listVo.add(mapVo);
        }
		
		String accBankCheckJson = accBankAccountService.deleteBatchAccBankAccount(listVo);
		
	   return JSONObject.parseObject(accBankCheckJson);
			
	}
	
	@RequestMapping(value = "/hrp/acc/account/accBankAccountUpdatePage", method = RequestMethod.GET)
	public String accBankAccountUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			map.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
			
			map.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			map.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("mod_code", "01");
		mapVo.put("para_code", "018");
			if("0".equals(MyConfig.getSysPara("018"))){
				mapVo.put("tell_id", mapVo.get("bank_id"));
				AccTell accTell = accBankAccountService.queryAccTellByCode(mapVo);
				mode.addAttribute("bank_id",  accTell.getTell_id());
				mode.addAttribute("group_id", accTell.getGroup_id());
				mode.addAttribute("hos_id", accTell.getHos_id());
				mode.addAttribute("copy_code", accTell.getCopy_code());
				mode.addAttribute("acc_year", accTell.getAcc_year());
				mode.addAttribute("subj_code", accTell.getCash_subj_code());
				mode.addAttribute("summary", accTell.getSummary());
				mode.addAttribute("debit", accTell.getDebit());
				mode.addAttribute("credit", accTell.getCredit());
				mode.addAttribute("check_no", accTell.getCheck_no());
				mode.addAttribute("occur_date", sdf.format(accTell.getOccur_date()));
				mode.addAttribute("pay_type_code", accTell.getPay_type_code());
				mode.addAttribute("subj_name", accTell.getSubj_name());
				mode.addAttribute("pay_name", accTell.getPay_name());
				mode.addAttribute("is_checks", accTell.getIs_checks());
			}else{
				mapVo.put("vouch_check_id", mapVo.get("bank_id"));
				AccVouchCheck accVouchCheck = accBankAccountService.queryAccVouchCheckByCode(mapVo);
				mode.addAttribute("bank_id", accVouchCheck.getVouch_check_id());
				mode.addAttribute("group_id", accVouchCheck.getGroup_id());
				mode.addAttribute("hos_id", accVouchCheck.getHos_id());
				mode.addAttribute("copy_code", accVouchCheck.getCopy_code());
				mode.addAttribute("acc_year", accVouchCheck.getAcc_year());
				mode.addAttribute("subj_code", accVouchCheck.getSubj_code());
				mode.addAttribute("summary", accVouchCheck.getSummary());
				mode.addAttribute("debit", accVouchCheck.getDebit());
				mode.addAttribute("credit", accVouchCheck.getCredit());
				mode.addAttribute("check_no", accVouchCheck.getCheck_no());
				mode.addAttribute("occur_date", sdf.format(accVouchCheck.getOccur_date()));
				mode.addAttribute("pay_type_code", accVouchCheck.getPay_type_code());

				mode.addAttribute("subj_code", accVouchCheck.getSubj_code());

				mode.addAttribute("subj_name", accVouchCheck.getSubj_name());
				mode.addAttribute("pay_name", accVouchCheck.getPay_name());
			}
		
		String yearMonth = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthCash();
		
		mapVo.put("startYearMonth", yearMonth.substring(0,4)+"-"+yearMonth.substring(4,6)+"-01");
		
		/*map.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(map);
		
		mode.addAttribute("startYearMonth",modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");*/
		
		return "hrp/acc/account/accBankAccountUpdate";
	}
	/**
	*银行对账单<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/account/updateAccBankAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("check_user", SessionManager.getUserId());
		
		mapVo.put("check_date", sdf.format(new Date()));
		
		mapVo.put("para_code", "018");
		
		mapVo.put("mod_code", "0101");
		
		String accBankCheckJson = accBankAccountService.updateAccBankAccount(mapVo);
		
		return JSONObject.parseObject(accBankCheckJson);
	}
	
	//下载导入模版
	@RequestMapping(value="/hrp/acc/account/downTemplate", method = RequestMethod.GET)  
	public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
				   HttpServletResponse response,Model mode) throws IOException { 
					
		List<List> list = new ArrayList();
					
		List<String> listdata = new ArrayList<String>();
					
		listdata.add("科目编码");
					
		listdata.add("业务日期");
					
		listdata.add("结算方式");
					
		listdata.add("票据号");
					
		listdata.add("摘要");
					
		listdata.add("借方金额");
					
		listdata.add("贷方金额");
					
		list.add(listdata);
					
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";
					
		String downLoadPath = ctxPath + "银行未达账.xls";
					
		ExcelWriter.exportExcel(new File(downLoadPath), list);
					
		printTemplate(request, response, "acc\\downTemplate", "银行未达账.xls");

		return null; 
	}
	
	//导入页面跳转	
	@RequestMapping(value = "/hrp/acc/account/accBankAccountImportPage", method = RequestMethod.GET)
	public String accBankAccountImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/account/accBankAccountImport";
	}
	
	/**
	*银行未达账<BR>
	*导入
	*/
	@RequestMapping(value="/hrp/acc/account/readAccBankAccountFiles",method = RequestMethod.POST)  
    public String readAccBankAccountFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException {
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		String accBankAccountJson = accBankAccountService.importAccBankAccount(list);
		
		response.getWriter().print(ChdJson.toJson(accBankAccountJson));
		
		return null;
	}
	
	/*查询银行未达账初始余额*/
	@RequestMapping(value = "/hrp/acc/account/selectAccBankBalInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccBankCheckInstall(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year",SessionManager.getAcctYear());
		
		try{
			String accBankCheckJson = accBankAccountService.selectAccBankBalInit(mapVo);
	
			return JSONObject.parseObject(accBankCheckJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
		
	}
	
}

