/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.tell;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccBankCheck;
import com.chd.hrp.acc.entity.AccPayType;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.service.commonbuilder.AccPayTypeService;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.acc.serviceImpl.tell.AccBankCheckServiceImpl;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.service.ModStartService;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;

/**
* @Title. @Description.
* 单位未达账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccBankCheckController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBankCheckController.class);
	
	
	@Resource(name = "accBankCheckService")
	private final AccBankCheckServiceImpl accBankCheckService = null;
	
	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;
	
	@Resource(name = "accSubjService")
	private final AccSubjService accSubjService = null;
	
	@Resource(name = "accPayTypeService")
	private final AccPayTypeService accPayTypeService = null; 
	
   
	/**
	*单位未达账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accbankcheck/accBankCheckMainPage", method = RequestMethod.GET)
	public String accBankCheckMainPage(Model mode,Map<String, Object> mapVo) throws Exception {
		
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
			
			mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"-"+yearMonth.substring(4, 6)+"-01");
			
			/*mapVo.put("mod_code", "0101");
			
			ModStart modStart = modStartService.queryModStartByCode(mapVo);
			
			if(modStart.getStart_year() !=null || modStart.getStart_month() != null){
				
			    mode.addAttribute("startYearMonthEnd", DateUtil.getLastYear_Month(modStart.getStart_year() + modStart.getStart_month()));
			
		     	mapVo.put("startYearMonth",modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");
		     	
			}*/
			
			
		return "hrp/acc/accbankcheck/accBankCheckMain";

	}
	/**
	*单位未达账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbankcheck/accBankCheckAddPage", method = RequestMethod.GET)
	public String accBankCheckAddPage(Model mode,Map<String, Object> mapVo) throws Exception {
		
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
			
			mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"-"+yearMonth.substring(4, 6)+"-01");
			
			/*mapVo.put("mod_code", "0101");
			
			ModStart modStart = modStartService.queryModStartByCode(mapVo);
			
			mapVo.put("startYearMonth",modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");*/

		return "hrp/acc/accbankcheck/accBankCheckAdd";

	}
	
	@RequestMapping(value = "/hrp/acc/accbankcheck/accBankCheckInstallPage", method = RequestMethod.GET)
	public String accBankCheckInstallPage(Model mode,@RequestParam Map<String, Object> mapVo) throws Exception {
		String subj_name = URLDecoder.decode(mapVo.get("subj_name").toString(), "UTF-8");//解决get请求中文乱码
		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("subj_name", subj_name);
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		return "hrp/acc/accbankcheck/accBankCheckInstall";

	}
	
	//下载导入模版
	@RequestMapping(value="/hrp/acc/accbankcheck/downTemplate", method = RequestMethod.GET)  
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
				
		String downLoadPath = ctxPath + "单位未达账.xls";
				
		ExcelWriter.exportExcel(new File(downLoadPath), list);
				
		printTemplate(request, response, "acc\\downTemplate", "单位未达账.xls");

		return null; 
	}
	
	//导入页面跳转	
	@RequestMapping(value = "/hrp/acc/accbankcheck/accBankCheckImportPage", method = RequestMethod.GET)
	public String accBankCheckImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/accbankcheck/accBankCheckImport";
	}
	
	/**
	*单位未达账<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accbankcheck/addAccBankCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccBankCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		mapVo.put("mod_code", "0101");
		
		if("".equals(mapVo.get("pay_type_code").toString())){
			
			mapVo.put("pay_type_code", "");
		}
		
		String accBankCheckJson = accBankCheckService.addAccBankCheck(mapVo);

		return JSONObject.parseObject(accBankCheckJson);
		
	}
	/**
	*单位未达账<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accbankcheck/queryAccBankCheck", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccBankCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("is_init", "1");
		String accBankCheck = accBankCheckService.queryAccBankCheck(getPage(mapVo));

		return JSONObject.parseObject(accBankCheck);
		
	}
	
	/**
	*单位未达账<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accbankcheck/deleteAccBankCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBankCheck(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String accBankCheckJson = accBankCheckService.deleteBatchAccBankCheck(listVo);
	   return JSONObject.parseObject(accBankCheckJson);
			
	}
	
	/**
	*单位未达账<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accbankcheck/accBankCheckUpdatePage", method = RequestMethod.GET)
	
	public String accBankCheckUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
        AccBankCheck accBankCheck = new AccBankCheck();
		accBankCheck = accBankCheckService.queryAccBankCheckByCode(mapVo);
		mode.addAttribute("bank_id", accBankCheck.getBank_id());
		mode.addAttribute("group_id", accBankCheck.getGroup_id());
		mode.addAttribute("hos_id", accBankCheck.getHos_id());
		mode.addAttribute("copy_code", accBankCheck.getCopy_code());
		mode.addAttribute("acc_year", accBankCheck.getAcc_year());
		mode.addAttribute("subj_code", accBankCheck.getSubj_code());
		mode.addAttribute("subj_name", accBankCheck.getSubj_name());
		mode.addAttribute("summary", accBankCheck.getSummary());
		mode.addAttribute("debit", accBankCheck.getDebit());
		mode.addAttribute("credit", accBankCheck.getCredit());
		mode.addAttribute("check_no", accBankCheck.getCheck_no());
		mode.addAttribute("occur_date", sdf.format(accBankCheck.getOccur_date()));
		mode.addAttribute("pay_type_code", accBankCheck.getPay_type_code());
		mode.addAttribute("pay_name", accBankCheck.getPay_name());
		mode.addAttribute("is_check", accBankCheck.getIs_check());
		
		/*mapVo.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(mapVo);
		
		mode.addAttribute("startYearMonth",modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");*/
		
		String yearMonth = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthCash();
		
		mapVo.put("startYearMonth", yearMonth.substring(0,4)+"-"+yearMonth.substring(4,6)+"-01");
		
		
		return "hrp/acc/accbankcheck/accBankCheckUpdate";
	}
	/**
	*单位未达账<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accbankcheck/updateAccBankCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
	        mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		mapVo.put("check_user", SessionManager.getUserId());
		
		mapVo.put("check_date", sdf.format(new Date()));
		
		mapVo.put("mod_code", "0101");
		
		String accBankCheckJson = accBankCheckService.updateAccBankCheck(mapVo);
		
		return JSONObject.parseObject(accBankCheckJson);
	}
	
	/**
	*单位未达账<BR>
	*导入
	*/
	@RequestMapping(value = "/hrp/acc/accbankcheck/importAccBankCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccBankCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null; 
		
		try {
			//可以与银行对账单共用导入方法，传参区分业务
			mapVo.put("is_init", 1);
			mapVo.put("is_import", 0);
			retMap = accBankCheckService.importAccBankCheck(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	

	@RequestMapping(value = "/hrp/acc/accbankcheck/queryAccTellDayByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTellDayByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
				mapVo.put("group_id", SessionManager.getGroupId());
			
			}
			
			if(mapVo.get("hos_id") == null){
			
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
	        
				mapVo.put("copy_code", SessionManager.getCopyCode());
			
			}
			
		String accBankCheckJson = accBankCheckService.queryAccTellDayByCode(mapVo);
		
		return JSONObject.parseObject(accBankCheckJson);
	}
	
	@RequestMapping(value = "/hrp/acc/accbankcheck/addAccBankCheckInstall", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccBankCheckInstall(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
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
			
	        mapVo.put("acc_year",SessionManager.getAcctYear());
		
		}
		
		mapVo.put("occur_date",sdf.format(new Date()));
		
		try{
			String accBankCheckJson = accBankCheckService.addAccBankCheckInstall(mapVo);
	
			return JSONObject.parseObject(accBankCheckJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
		
	}
	
	@RequestMapping(value = "/hrp/acc/accbankcheck/queryInstallMoney", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInstallMoney(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String accBankCheck = accBankCheckService.queryAccBankCheckCode(mapVo);

		return JSONObject.parseObject(accBankCheck);
		
	}

	/**
	*单位未达账<BR>
	*导入
	*/
	@RequestMapping(value="/hrp/acc/accbankcheck/readAccBankCheckFiles",method = RequestMethod.POST)  
    public String readAccBankCheckFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException {
		
		List<String> list_err = new ArrayList<String>(); 
		
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		StringBuffer err_sb = new StringBuffer();
		
		for(int i = 1;i<list.size();i++){
			
			
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
			
			if (StringUtils.isNotEmpty(temp[0])) {
				
				String reg = "\\d+";
				
				boolean flag = temp[0].toString().matches(reg);
		
				if(flag == false){
					
					err_sb.append("第"+i+"行"+"科目编码必须是数字  ");
					
				}else{
					
					mapVo.put("subj_code", temp[0]);
					
					AccSubj accSubj = accSubjService.queryAccSubjCode(mapVo);
					
					if(accSubj != null){
						
						mapVo.put("subj_id", accSubj.getSubj_id());
						
					}else{
						
						err_sb.append("第"+i+"行"+"科目编码不存在  ");
					}
				}
			} else {
				
				err_sb.append("第"+i+"行"+"科目编码为空  ");
				
			}
			
			if (StringUtils.isNotEmpty(temp[1])) {//判断日期是否为空
				
				String reg = "[\\d]{4}[-][\\d]{2}-[\\d]{2}";
				
				boolean flag = temp[1].toString().matches(reg);
				
				
				if(flag){
					
					String mod_start_yearMonth = null;
					
					mapVo.put("mod_code", "0101");
					
					ModStart modStart = modStartService.queryModStartByCode(mapVo);
					
					if(modStart.getStart_year()==null && modStart.getStart_month()==null)
					{
						err_sb.append("第"+i+"行"+"出纳账管理系统没有启用,不能进行添加操作");
						
					}else{
						
						mod_start_yearMonth = modStart.getStart_year()+"-"+(Integer.parseInt(modStart.getStart_month())-1);
					}
					
					String[] occur_date = null;
					
					String occ_yearMonth =null;
					
					occur_date = temp[1].toString().split("-");
					
					occ_yearMonth = occur_date[0] +"-"+ occur_date[1];
					
					Date mod_start_year_month = null;
					
					Date occur_year_month = null;
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
					
					try {
						
						mod_start_year_month = df.parse(mod_start_yearMonth);
						
						occur_year_month = df.parse(occ_yearMonth);
						
						if(occur_year_month.getTime() > mod_start_year_month.getTime()){
							
							err_sb.append("第"+i+"行"+"业务日期必须小于出纳账管理系统启用时间 ");
						}
					} catch (Exception e) {
						// TODO: handle exception
						logger.error(e.getMessage());
						
						return "{\"error\":\"系统异常,请联系管理员.\"}";
					}
					
				}else{
					
					err_sb.append("第"+i+"行"+"业务日期格式错误 ");
				}
				
				if(flag == false){//判断日期格式是否正确
					
					err_sb.append("第"+i+"行"+"业务日期格式错误 ");
				}

				mapVo.put("occur_date", temp[1]);
				
			} else {
				
				err_sb.append("第"+i+"行"+"业务日期为空  ");
				
			}
			
			if (StringUtils.isNotEmpty(temp[2])) {
				
				String reg = "\\d{1,20}";
				
				AccPayType accPayType = null ; 
				
				boolean flag = temp[0].toString().matches(reg);
				
				if(flag == false){
					
					err_sb.append("第"+i+"行"+"结算方式编码格式错误  ");
				}else{

					mapVo.put("pay_code", temp[2]);
					
					accPayType = accPayTypeService.queryAccPayTypeByCode(mapVo);
				}
				
				if(accPayType == null){
					
					err_sb.append("第"+i+"行"+"结算方式不存在  ");
				}
				
				mapVo.put("pay_type_code",accPayType.getPay_code());
				
			} else {
				
				mapVo.put("pay_type_code","");
			}
			
			if (StringUtils.isNotEmpty(temp[3])) {
				
				mapVo.put("check_no", temp[3]);
				
			}else {
				
				mapVo.put("check_no","");
			}
			
			if (StringUtils.isNotEmpty(temp[4])) {
				
				mapVo.put("summary", temp[4]);
				
			} else {
				
				mapVo.put("summary","");;
				
			}
			
			if (ExcelReader.validExceLColunm(temp,5)) {
				
				String reg = "[\\d]+(\\.\\d+)?";
				
				boolean flag = temp[5].toString().matches(reg);
				
				if(flag == false){
					
					err_sb.append("第"+i+"行"+"借方金额格式错误  ");
				}
				
				mapVo.put("debit", temp[5]);
				
			}else {
				
				mapVo.put("debit",0.00);
			}
			
			if (ExcelReader.validExceLColunm(temp,6)) {
				
				String reg = "[\\d]+[\\.]?[\\d]{0,3}";
				
				boolean flag = temp[6].toString().matches(reg);
				
				if(flag == false){
					
					err_sb.append("第"+i+"行"+"贷方金额格式错误  ");
				}else{
					
					mapVo.put("credit", temp[6]);
				}
				
			}else {
				
				mapVo.put("credit", 0.00);
				
			}
			
			mapVo.put("bal", 0);
			mapVo.put("debit_w", 0);
			mapVo.put("credit_w", 0);
			mapVo.put("bal_w", "");
			mapVo.put("business_no", "");
			mapVo.put("is_check",0);
			mapVo.put("check_user", "");
			mapVo.put("check_date", "");
			mapVo.put("is_init", 1);
			mapVo.put("is_import", 1);
			maplist.add(mapVo);
			
			if(err_sb.toString().length() > 0 ){
				
				list_err.add(err_sb.toString());
				
				break;
			}
		}
		
		if(list_err.size() <= 0 ){
			if(!maplist.isEmpty()) {
				accBankCheckService.addBatchAccBankCheck(maplist);
			} else {
				err_sb.append("文本中无可用数据");
			}
		}
		
		response.getWriter().print(ChdJson.toJson(err_sb.toString()));
		
		return null;
	}
}

