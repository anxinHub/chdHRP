/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.bill;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.bill.AssBackPreBillMain;
import com.chd.hrp.ass.entity.bill.AssPreBillMain;
import com.chd.hrp.ass.entity.prepay.AssBackPrePayMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.bill.AssBackPreBillMainService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_BACK_PRE_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssBackPreBillMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssBackPreBillMainController.class);
	
	//引入Service服务
	@Resource(name = "assBackPreBillMainService")
	private final AssBackPreBillMainService assBackPreBillMainService = null;
	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/assBackPreBillMainMainPage", method = RequestMethod.GET)
	public String assBackPreBillMainMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05084",MyConfig.getSysPara("05084"));
		
		return "hrp/ass/assbill/assbackprebillmain/assBackPreBillMainMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/assBackPreBillMainAddPage", method = RequestMethod.GET)
	public String assBackPreBillMainAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assbill/assbackprebillmain/assBackPreBillMainAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/addAssBackPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackPreBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
    	mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		mapVo.put("create_name", SessionManager.getUserId());
		mapVo.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
       
		String assBackPreBillMainJson = assBackPreBillMainService.add(mapVo);

		return JSONObject.parseObject(assBackPreBillMainJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/assBackPreBillMainUpdatePage", method = RequestMethod.GET)
	public String assBackPreBillMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		AssBackPreBillMain assBackPreBillMain = new AssBackPreBillMain();
    
		assBackPreBillMain = assBackPreBillMainService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assBackPreBillMain.getGroup_id());
		mode.addAttribute("hos_id", assBackPreBillMain.getHos_id());
		mode.addAttribute("copy_code", assBackPreBillMain.getCopy_code());
		mode.addAttribute("bill_no", assBackPreBillMain.getBill_no());
		mode.addAttribute("invoice_no", assBackPreBillMain.getInvoice_no());
		mode.addAttribute("bill_date",DateUtil.dateToString(assBackPreBillMain.getBill_date(),"yyyy-MM-dd"));
		mode.addAttribute("ven_id", assBackPreBillMain.getVen_id()+"@"+assBackPreBillMain.getVen_no());
		mode.addAttribute("ven_no", assBackPreBillMain.getVen_no());
		mode.addAttribute("pact_code", assBackPreBillMain.getPact_code());
		mode.addAttribute("bill_money", assBackPreBillMain.getBill_money());
		mode.addAttribute("state", assBackPreBillMain.getState());
		mode.addAttribute("state_name", assBackPreBillMain.getSup_name());
		mode.addAttribute("create_emp", assBackPreBillMain.getCreate_emp());
		mode.addAttribute("create_date", assBackPreBillMain.getCreate_date());
		mode.addAttribute("audit_emp", assBackPreBillMain.getAudit_emp());
		mode.addAttribute("audit_date", assBackPreBillMain.getAudit_date());
		mode.addAttribute("note", assBackPreBillMain.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05084",MyConfig.getSysPara("05084"));
		
		return "hrp/ass/assbill/assbackprebillmain/assBackPreBillMainUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/updateAssBackPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBackPreBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assBackPreBillMainJson = assBackPreBillMainService.update(mapVo);
		
		return JSONObject.parseObject(assBackPreBillMainJson);
	}
	
	/**
	 * @Description 
	 * 添加主表数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/saveAssBackPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackprebillmain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assBackPreBillMainJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		mapVo.put("create_emp", SessionManager.getUserId());
		mapVo.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		String yearmonth = mapVo.get("bill_date").toString().substring(0, 4) + mapVo.get("bill_date").toString().substring(5, 7);
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		}  
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
		assBackPreBillMainJson = assBackPreBillMainService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assBackPreBillMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/deleteAssBackPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackPreBillMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String retErrot = "";
		boolean flag = true;
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("bill_no", ids[3]);
				String str = assBaseService.isExistsDataByTable("ASS_BACK_PRE_BILL_DETAIL", ids[3]);

				if (Strings.isNotBlank(str)) {
					retErrot = "{\"error\":\"删除失败，选择的发票被以下业务使用：【" + str.substring(0, str.length() - 1)
							+ "】。\",\"state\":\"false\"}";
					return JSONObject.parseObject(retErrot);
				}
				AssBackPreBillMain assBackPreBill = assBackPreBillMainService.queryByCode(mapVo);
				if(assBackPreBill.getState() > 0){
					flag = false;
					break;
				}
				listVo.add(mapVo);
	      
	    }
	    
			if(!flag){
				return JSONObject.parseObject("{\"warn\":\"已经审核确定的不能删除! \"}");
			}
		String assBackPreBillMainJson = assBackPreBillMainService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assBackPreBillMainJson);
			
	}
	/**
	 * @Description 
	 * 审核数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/checkAssBackPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAssBackPreBillMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String retErrot = "";
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("bill_no", ids[3]);
			mapVo.put("state", 2);
			mapVo.put("audit_emp",SessionManager.getUserId());
			mapVo.put("audit_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			
			//判断是否确认如果确认则忽略
			AssBackPreBillMain assBackPrePayMain = assBackPreBillMainService.queryByCode(mapVo);
			if(assBackPrePayMain == null || assBackPrePayMain.getState() > 0){
				continue;
			}
			
			listVo.add(mapVo);
			
		}
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}
		String assBackPreBillMainJson = assBackPreBillMainService.checkAssBackPreBillMain(listVo);
		
		return JSONObject.parseObject(assBackPreBillMainJson);
		
	}
	
	/**
	 * 取消确认
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/updateNotToExamineAssBackPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamineAssPreBillMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String retErrot = "";
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("bill_no", ids[3]);
			mapVo.put("state", 0);
			mapVo.put("audit_emp","");
			mapVo.put("audit_date", "");
			//判断是否确认如果确认则忽略
			AssBackPreBillMain assBackPrePayMain = assBackPreBillMainService.queryByCode(mapVo);
			int state = assBackPrePayMain.getState()==null? 0:assBackPrePayMain.getState();
			if(assBackPrePayMain.getState()!=2){
				continue;
			}
			
			listVo.add(mapVo);
			
		}
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复销审操作! \"}");
		}
		String assPreBillMainJson = assBackPreBillMainService.updateNotToExamineAssPreBillMain(listVo);
		
		return JSONObject.parseObject(assPreBillMainJson);
		
	}
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/queryAssBackPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPreBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String assBackPreBillMain = assBackPreBillMainService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackPreBillMain);
		
	}
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/queryAssBackPreBilldetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPreBilldetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		if(mapVo.get("acct_year") == null){
			
			mapVo.put("acct_year", SessionManager.getAcctYear());
			
		}
		String assBackPreBillMain = assBackPreBillMainService.queryAssBackPreBilldetail(mapVo);
		
		return JSONObject.parseObject(assBackPreBillMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/assBackPreBillMainImportPage", method = RequestMethod.GET)
	public String assBackPreBillMainImportPage(Model mode) throws Exception {

		return "hrp/ass/bill/assbackprebillmain/assBackPreBillMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 tabledesc
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/bill/assbackprebillmain/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","tabledesc.xls");
	    
	    return null; 
	 }
    
	 
	 /**
		 * 预退发票登记状态查询
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/bill/assbackprebillmain/queryAssBackPreBillState", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssBackPreBillState(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			//入库单状态查询  （打印时校验数据专用）
			List<String> list = assBackPreBillMainService.queryAssBackPreBillState(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"state\":\"true\"}");
				
			}else{
				
				String  str = "" ;
				for(String item : list){
					
					str += item +"," ;
				}
				
				return JSONObject.parseObject("{\"error\":\"预退发票登记【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
			}
			
			
		}
		
	 
}

