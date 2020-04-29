/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.joda.time.DateTime;
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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.acc.entity.AccBankCheck;
import com.chd.hrp.acc.entity.AccPayType;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.acc.service.commonbuilder.AccPayTypeService;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.acc.serviceImpl.AccInitialBalanceCalibrationServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccYearMonthServiceImpl;
import com.chd.hrp.acc.serviceImpl.tell.AccBankAccountServiceImpl;
import com.chd.hrp.acc.serviceImpl.tell.AccBankCheckServiceImpl;
import com.chd.hrp.acc.serviceImpl.tell.AccBankCheckTellServiceImpl;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;

/**
* @Title. @Description.
* 出纳账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

@Controller
public class AccBankTellController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBankTellController.class);
	
	@Resource(name = "accBankCheckService")
	private final AccBankCheckServiceImpl accBankCheckService = null;
	
	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;
	
	@Resource(name = "accYearMonthService")
	private final AccYearMonthServiceImpl accYearMonthService = null;
	
	@Resource(name = "accBankCheckTellService")
	private final AccBankCheckTellServiceImpl accBankCheckTellService = null;
	
	@Resource(name = "accInitialBalanceCalibrationService")
	private final AccInitialBalanceCalibrationServiceImpl accInitialBalanceCalibrationService = null;
	
	@Resource(name = "accBankAccountService")
	private final AccBankAccountServiceImpl accBankAccountService = null;
	
	@Resource(name = "accSubjService")
	private final AccSubjService accSubjService = null;
	
	@Resource(name = "accPayTypeService")
	private final AccPayTypeService accPayTypeService = null;
	
	@Resource(name = "accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper = null;
	
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accbanktell/accBankTellMainPage", method = RequestMethod.GET)
	public String accBankTellMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accbanktell/accBankTellMain";

	}
	
	@RequestMapping(value = "/hrp/acc/accbanktell/accBankTellImportPage", method = RequestMethod.GET)
	public String accBankTellImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accbanktell/accBankTellImport";

	}
	
	
	/**
	*银行对账：银行对账单<BR>
	*查询银行对账单
	*/
	@RequestMapping(value = "/hrp/acc/accbanktell/queryBankTell", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBankTell(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		mapVo.put("acc_year", mapVo.get("begin_date").toString().substring(0,4));
		//调用存储过程执行查询
		String accBankCheckTell = accBankCheckTellService.collectAccBankCheckTell(getPage(mapVo));
		//普通查询
		//String accBankCheckTell = accBankCheckTellService.queryAccBankCheckTell(getPage(mapVo));

		return JSONObject.parseObject(accBankCheckTell);
		
	}
	
	/**
	*银行对账：银行对账单<BR>
	*打印银行对账单
	*/
	/*@RequestMapping(value = "/hrp/acc/accbanktell/queryBankTellPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBankTellPrint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		mapVo.put("acc_year", mapVo.get("begin_date").toString().substring(0,4));
		
		String accBankCheckTell = accBankCheckTellService.queryAccBankCheckTellPrint(mapVo);

		return JSONObject.parseObject(accBankCheckTell);
		
	}*/
	
	
	/**
	*银行对账：银行对账单<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbanktell/accBankTellAddPage", method = RequestMethod.GET)
	public String accBankCheckTellAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("cash_flag", "0");
		
		List<AccYearMonth> accYearMonthList = accYearMonthService.queryAccTellPeriod(mapVo);
		
		if(accYearMonthList.size()>0){
			
			mode.addAttribute("min_date", accYearMonthList.get(0).getBegin_date());
			
			mode.addAttribute("max_date", accYearMonthList.get(accYearMonthList.size()-1).getEnd_date());
			
		}
		
		return "hrp/acc/accbanktell/accBankCheckTellAdd";

	}	
	
	/**
	 * 银行对账：添加银行对账单
	 * */
	@RequestMapping(value = "/hrp/acc/accbanktell/addAccBankTell", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccBankCheckTell(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		if(mapVo.get("acc_year") == null){
			
	        mapVo.put("acc_year", mapVo.get("occur_date").toString().substring(0, 4));
		}
		
		
		mapVo.put("check_user", SessionManager.getUserId());
		
		mapVo.put("check_date", sdf.format(new Date()));
		
		mapVo.put("mod_code", "0101");
		
		String accBankCheckJson = accBankCheckTellService.addAccBankCheckTell(mapVo);

		return JSONObject.parseObject(accBankCheckJson);
		
	}
	
	
	/**
	*银行对账：银行对账单<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accbanktell/deleteAccBankTell", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBankCheckTell(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			if(mapVo.get("group_id") == null){
				
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
			}
            mapVo.put("bank_id", id);//实际实体类变量
            
            listVo.add(mapVo);
        }
		String accBankCheckJson = accBankCheckTellService.deleteBatchAccBankCheckTell(listVo);
		
	   return JSONObject.parseObject(accBankCheckJson);
			
	}
	
	/**
	*银行对账：银行对账单<BR>
	*修改
	*维护页面跳转
	*/
	//修改页面
	@RequestMapping(value = "/hrp/acc/accbanktell/accBankTellUpdatePage", method = RequestMethod.GET)
	public String accBankTellUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception{
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		}
		
		AccBankCheck accBankCheck = accBankCheckService.queryAccBankCheckByCode(mapVo);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("group_id",accBankCheck.getGroup_id());
		mode.addAttribute("hos_id", accBankCheck.getHos_id());
		mode.addAttribute("copy_code",accBankCheck.getCopy_code());
		mode.addAttribute("bank_id", accBankCheck.getBank_id());
		mode.addAttribute("subj_id", accBankCheck.getSubj_id());
		mode.addAttribute("subj_code", accBankCheck.getSubj_code());
		mode.addAttribute("subj_name", accBankCheck.getSubj_name());
		mode.addAttribute("occur_date", sdf.format(accBankCheck.getOccur_date()));
		mode.addAttribute("pay_type_code", accBankCheck.getPay_type_code());
		mode.addAttribute("pay_name", accBankCheck.getPay_name());
		mode.addAttribute("check_no", accBankCheck.getCheck_no());
		mode.addAttribute("summary", accBankCheck.getSummary());
		mode.addAttribute("debit", accBankCheck.getDebit());
		mode.addAttribute("credit", accBankCheck.getCredit());
		mode.addAttribute("check_user", accBankCheck.getCheck_user());
		
		return "hrp/acc/accbanktell/accBankTellUpdate";
	}
	
	
	/**
	*银行对账：银行对账单<BR>
	*修改
	*/
	@RequestMapping(value = "/hrp/acc/accbanktell/updateAccBankTell", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> updateAccBankTell(@RequestParam Map<String, Object> mapVo, Model mode){
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		}
		
		String accBankCheckJson = accBankCheckTellService.updateAccBankCheckTell(mapVo);
		
		return JSONObject.parseObject(accBankCheckJson);
	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	// 查询页面
	@RequestMapping(value = "/hrp/acc/accbanktell/collectBalanceAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBalanceAdjust(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		mapVo.put("mod_code", "01");
		
		mapVo.put("para_code", "018");
		
		String initialBalanceCalibration = accInitialBalanceCalibrationService.collectBalanceAdjust(mapVo);

		return JSONObject.parseObject(initialBalanceCalibration);
	}
	
	@RequestMapping(value = "/hrp/acc/accbanktell/queryAccBankTellAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankTellAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accBankAccount = accBankAccountService.queryAccBankAccount(getPage(mapVo));

		return JSONObject.parseObject(accBankAccount);
		
	}
	
	
	
	/**
	 * @Description 
	 * 下载导入模版 051602 付款主表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/acc/accbanktell/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"acc\\downTemplate","银行对账单.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 051602 付款主表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/acc/accbanktell/readAccBankTellFiles",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> readAssPayMainFiles(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request) throws IOException { 
		Map<String, Object> retMap = null;
		try {
			//可以与单位未达账共用导入方法，传参区分业务
			mapVo.put("is_init", 0);
			mapVo.put("is_import", 0);
			retMap = accBankCheckService.importAccBankCheck(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		} 
		
		return retMap;
    }  
   /**
	 * @Description 
	 * 批量添加数据 051602 付款主表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/acc/accbanktell/addBatchAccBankTellMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssPayMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AccBankCheck> list_err = new ArrayList<AccBankCheck>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
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
			
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
				StringBuffer err_sb = new StringBuffer();
			
				AccBankCheck accBankCheck = new AccBankCheck();
			
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
						accBankCheck.setSubj_code((String)jsonObj.get("subj_code"));
						accBankCheck.setSubj_name((String)jsonObj.get("subj_name"));
						
						Map<String, Object> subjMapVo = new HashMap<String, Object>();
						
						subjMapVo.put("group_id", SessionManager.getGroupId());   
						 
						subjMapVo.put("hos_id", SessionManager.getHosId());   
						 
						subjMapVo.put("copy_code", SessionManager.getCopyCode());   
			    			
						subjMapVo.put("acc_year", SessionManager.getAcctYear());
						
						subjMapVo.put("subj_code", (String)jsonObj.get("subj_code"));
		    			
		    			AccSubj accSubj = accSubjService.queryAccSubjByCode(subjMapVo);
		    			
		    			if(accSubj != null){
		    				mapVo.put("subj_id", accSubj.getSubj_id());
		    			}else{
		    				err_sb.append("科目不存在 ");
		    			}
					
					} else {
						
						err_sb.append("科目为空  ");
						
					}
						
					
					if (StringTool.isNotBlank(jsonObj.get("summary"))) {
						
						accBankCheck.setSummary((String)jsonObj.get("summary"));
						mapVo.put("summary", jsonObj.get("summary"));
		    		}
					
					if (StringTool.isNotBlank(jsonObj.get("debit"))) {
						
						accBankCheck.setDebit(Double.parseDouble((String)jsonObj.get("ass_month")));
						mapVo.put("debit", jsonObj.get("debit"));
		    		} else {
						
						err_sb.append("借方金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("credit"))) {
						
						accBankCheck.setCredit(Double.parseDouble((String)jsonObj.get("credit")));
						mapVo.put("credit", jsonObj.get("credit"));
		    		} else {
						
						err_sb.append("贷方金额为空 ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bal"))) {
						
						accBankCheck.setBal(Double.parseDouble((String)jsonObj.get("bal")));
						mapVo.put("bal", jsonObj.get("bal"));
		    		} else {
						
						err_sb.append("金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_no"))) {
						
						accBankCheck.setCheck_no((String)jsonObj.get("check_no"));
		    			mapVo.put("check_no", jsonObj.get("check_no"));
		    		}
					
					if (StringTool.isNotBlank(jsonObj.get("business_no"))) {
						
						accBankCheck.setBusiness_no((String)jsonObj.get("business_no"));
						mapVo.put("business_no", jsonObj.get("business_no"));
		    		} 
					
					if (StringTool.isNotBlank(jsonObj.get("occur_date"))) {
						
						accBankCheck.setOccur_date(DateUtil.stringToDate((String)jsonObj.get("occur_date")));
						mapVo.put("occur_date", jsonObj.get("occur_date"));
		    		} 
					
					if (StringTool.isNotBlank(jsonObj.get("pay_type_code"))) {
						accBankCheck.setPay_type_code((String)jsonObj.get("pay_type_code"));
						accBankCheck.setPay_type_name((String)jsonObj.get("pay_type_name"));
						Map<String, Object> payMapVo = new HashMap<String, Object>();
						
						payMapVo.put("group_id", SessionManager.getGroupId());   
						 
						payMapVo.put("hos_id", SessionManager.getHosId());   
						 
						payMapVo.put("copy_code", SessionManager.getCopyCode());   
			    			
						payMapVo.put("pay_code", (String)jsonObj.get("pay_type_code"));
						
						AccPayType accPayType = accPayTypeService.queryAccPayTypeByCode(payMapVo);
						
						if(accPayType != null){
							mapVo.put("pay_type_code", accPayType.getPay_code());
		    			}else{
		    				err_sb.append("结算方式不存在 ");
		    			}
		    		}
					
			
				if (err_sb.toString().length() > 0) {
					
					accBankCheck.setError_type(err_sb.toString());
					
					list_err.add(accBankCheck);
					
				} else {
			  
					String dataJson = accBankCheckTellService.addAccBankCheckTell(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AccBankCheck data_exc = new AccBankCheck();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	public static Date fromDoubleToDateTime(double OADate) 
	{
	    long num = (long) ((OADate * 86400000.0) + ((OADate >= 0.0) ? 0.5 : -0.5));
	    if (num < 0L) {
	        num -= (num % 0x5265c00L) * 2L;
	    }
	    num += 0x3680b5e1fc00L;
	    num -=  62135596800000L;

	    return new Date(num);
	}
}

