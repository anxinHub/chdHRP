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
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.bill.AssPreBillMainService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_PRE_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssPreBillMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssPreBillMainController.class);
	
	//引入Service服务
	@Resource(name = "assPreBillMainService")
	private final AssPreBillMainService assPreBillMainService = null;
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
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/assPreBillMainMainPage", method = RequestMethod.GET)
	public String assPreBillMainMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05084",MyConfig.getSysPara("05084"));
		
		return "hrp/ass/assbill/assPreBillMain/assPreBillMainMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/assPreBillMainAddPage", method = RequestMethod.GET)
	public String assPreBillMainAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assbill/assPreBillMain/assPreBillMainAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/addAssPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPreBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("create_emp", SessionManager.getUserId());
		mapVo.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
		String assPreBillMainJson = assPreBillMainService.add(mapVo);

		return JSONObject.parseObject(assPreBillMainJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/assPreBillMainUpdatePage", method = RequestMethod.GET)
	public String assPreBillMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssPreBillMain assPreBillMain = new AssPreBillMain();
    
		assPreBillMain = assPreBillMainService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assPreBillMain.getGroup_id());
		mode.addAttribute("hos_id", assPreBillMain.getHos_id());
		mode.addAttribute("copy_code", assPreBillMain.getCopy_code());
		mode.addAttribute("bill_no", assPreBillMain.getBill_no());
		mode.addAttribute("invoice_no", assPreBillMain.getInvoice_no());
		mode.addAttribute("bill_date",DateUtil.dateToString(assPreBillMain.getBill_date(),"yyyy-MM-dd"));
		mode.addAttribute("ven_id", assPreBillMain.getVen_id()+"@"+assPreBillMain.getVen_no());
		mode.addAttribute("ven_no", assPreBillMain.getVen_no());
		mode.addAttribute("pact_code", assPreBillMain.getPact_code());
		mode.addAttribute("bill_money", assPreBillMain.getBill_money());
		mode.addAttribute("state", assPreBillMain.getState());
		mode.addAttribute("create_emp", assPreBillMain.getCreate_emp());
		mode.addAttribute("create_date", assPreBillMain.getCreate_date());
		mode.addAttribute("audit_emp", assPreBillMain.getAudit_emp());
		mode.addAttribute("audit_date", assPreBillMain.getAudit_date());
		mode.addAttribute("note", assPreBillMain.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("add_05084",MyConfig.getSysPara("05084"));
		
		return "hrp/ass/assbill/assPreBillMain/assPreBillMainUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/updateAssPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssPreBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assPreBillMainJson = assPreBillMainService.update(mapVo);
		
		return JSONObject.parseObject(assPreBillMainJson);
	}
	
	/**
	 * @Description 
	 * 添加主表数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/saveAssPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssPreBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assPreBillMainJson ="";
		

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
		
		assPreBillMainJson = assPreBillMainService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assPreBillMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/deleteAssPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPreBillMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				String str = assBaseService.isExistsDataByTable("ASS_PRE_BILL_MAIN", ids[3]);

				if (Strings.isNotBlank(str)) {
					retErrot = "{\"error\":\"删除失败，选择的发票被以下业务使用：【" + str.substring(0, str.length() - 1)
							+ "】。\",\"state\":\"false\"}";
					return JSONObject.parseObject(retErrot);
				}
				AssPreBillMain assPreBillMain = assPreBillMainService.queryByCode(mapVo);
				
				if(assPreBillMain.getState() > 0){
					flag = false;
					break;
				}	
	      listVo.add(mapVo);
	      
	    }
			if(!flag){
				return JSONObject.parseObject("{\"warn\":\"已审核确认的数据不能删除! \"}");
			}

			
		String assPreBillMainJson = assPreBillMainService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assPreBillMainJson);
			
	}
	/**
	 * @Description 
	 * 审核数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/checkAssPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAssPreBillMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			AssPreBillMain assBackPrePayMain = assPreBillMainService.queryByCode(mapVo);
			int state = assBackPrePayMain.getState()==null? 0:assBackPrePayMain.getState();
			if(assBackPrePayMain == null ||  state > 0){
				continue;
			}
			
			listVo.add(mapVo);
			
		}
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}
		String assPreBillMainJson = assPreBillMainService.checkAssPreBillMain(listVo);
		
		return JSONObject.parseObject(assPreBillMainJson);
		
	}
	/**
	 * 取消确认
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/updateNotToExamineAssPreBillMain", method = RequestMethod.POST)
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
			AssPreBillMain assBackPrePayMain = assPreBillMainService.queryByCode(mapVo);
			int state = assBackPrePayMain.getState()==null? 0:assBackPrePayMain.getState();
			if(assBackPrePayMain.getState()!=2){
				continue;
			}
			
			listVo.add(mapVo);
			
		}
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复销审操作! \"}");
		}
		String assPreBillMainJson = assPreBillMainService.updateNotToExamineAssPreBillMain(listVo);
		
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
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/queryAssPreBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPreBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assPreBillMain = assPreBillMainService.query(getPage(mapVo));

		return JSONObject.parseObject(assPreBillMain);
		
	}
	/**
	 * @Description 
	 * 查询数据 发票明细数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/queryAssPreBilldetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPreBilldetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		String assPreBillMain = assPreBillMainService.queryAssPreBilldetail(mapVo);
		
		return JSONObject.parseObject(assPreBillMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/assPreBillMainImportPage", method = RequestMethod.GET)
	public String assPreBillMainImportPage(Model mode) throws Exception {

		return "hrp/budg/assprebillmain/assPreBillMainImport";

	}
    
	/**
	 * 预付发票登记状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/bill/assprebillmain/queryAssPreBillState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPreBillState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assPreBillMainService.queryAssPreBillState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"预付发票登记单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
	
}

