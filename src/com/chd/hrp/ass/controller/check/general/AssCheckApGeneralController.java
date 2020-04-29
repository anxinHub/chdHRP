/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.check.general;
import java.io.IOException;
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
import com.chd.hrp.ass.entity.check.general.AssCheckApGeneral;
import com.chd.hrp.ass.entity.check.general.AssChkAdetailGeneral;
import com.chd.hrp.ass.entity.check.general.AssChkAGeneral;
import com.chd.hrp.ass.service.check.general.AssCheckApDetailGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckApGeneralService;
/**
 * 
 * @Description:
 * 051101 盘盈处置申请单(一般设备)
 * @Table:
 * ASS_CHECK_AP_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssCheckApGeneralController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssCheckApGeneralController.class);
	
	//引入Service服务
	@Resource(name = "assCheckApGeneralService")
	private final AssCheckApGeneralService assCheckApGeneralService = null;
	
	@Resource(name = "assCheckApDetailGeneralService")
	private final AssCheckApDetailGeneralService assCheckApDetailGeneralService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/assCheckApGeneralMainPage", method = RequestMethod.GET)
	public String assCheckApGeneralMainPage(Model mode) throws Exception {

		return "hrp/ass/assgeneral/asscheck/checkapply/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/assCheckApGeneralAddPage", method = RequestMethod.GET)
	public String assCheckApGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asscheck/checkapply/add";

	}

	/**
	 * @Description 
	 * 添加数据 051101 盘盈处置申请单(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/saveAssCheckApGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckApGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
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
		
		AssCheckApGeneral assCheckApGeneral = assCheckApGeneralService.queryByCode(mapVo);
		if(assCheckApGeneral != null && assCheckApGeneral.getState() > 0){
			return JSONObject.parseObject("{\"warn\":\"已申报确认的数据不能保存! \"}");
		}
		
       
		String assCheckApGeneralJson = assCheckApGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckApGeneralJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 051101 盘盈处置申请单(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/assCheckApGeneralUpdatePage", method = RequestMethod.GET)
	public String assCheckApGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssCheckApGeneral assCheckApGeneral = new AssCheckApGeneral();
    
		assCheckApGeneral = assCheckApGeneralService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assCheckApGeneral.getGroup_id());
		mode.addAttribute("hos_id", assCheckApGeneral.getHos_id());
		mode.addAttribute("copy_code", assCheckApGeneral.getCopy_code());
		mode.addAttribute("check_ap_no", assCheckApGeneral.getCheck_ap_no());
		mode.addAttribute("summary", assCheckApGeneral.getSummary());
		mode.addAttribute("state", assCheckApGeneral.getState());
		mode.addAttribute("create_emp", assCheckApGeneral.getCreate_emp());
		mode.addAttribute("create_date", assCheckApGeneral.getCreate_date());
		mode.addAttribute("audit_emp", assCheckApGeneral.getAudit_emp());
		mode.addAttribute("apply_date", assCheckApGeneral.getApply_date());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asscheck/checkapply/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 051101 盘盈处置申请单(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/updateAssCheckApGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckApGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assCheckApGeneralJson = assCheckApGeneralService.update(mapVo);
		
		return JSONObject.parseObject(assCheckApGeneralJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 051101 盘盈处置申请单(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/addOrUpdateAssCheckApGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssCheckApGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assCheckApGeneralJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		assCheckApGeneralJson = assCheckApGeneralService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assCheckApGeneralJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 051101 盘盈处置申请单(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/deleteAssCheckApGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckApGeneral(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("check_ap_no", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String assCheckApGeneralJson = assCheckApGeneralService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assCheckApGeneralJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 051101 盘盈处置申请单(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/queryAssCheckApGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckApGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String assCheckApGeneral = assCheckApGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckApGeneral);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 051101 盘盈处置申请单(一般设备)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/assCheckApGeneralImportPage", method = RequestMethod.GET)
	public String assCheckApGeneralImportPage(Model mode) throws Exception {

		return "hrp/ass/assgeneral/asscheck/checkapply/assCheckApGeneralImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 051101 盘盈处置申请单(一般设备)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/ass/assgeneral/check/checkapply/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"sys\\downTemplate","051101 盘盈处置申请单(一般设备).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 051101 盘盈处置申请单(一般设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assgeneral/check/checkapply/readAssCheckApGeneralFiles",method = RequestMethod.POST)  
    public String readAssCheckApGeneralFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssCheckApGeneral> list_err = new ArrayList<AssCheckApGeneral>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssCheckApGeneral assCheckApGeneral = new AssCheckApGeneral();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assCheckApGeneral.setCheck_ap_no(temp[3]);
		    		mapVo.put("check_ap_no", temp[3]);
					
					} else {
						
						err_sb.append("盘盈申报单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assCheckApGeneral.setSummary(temp[4]);
		    		mapVo.put("summary", temp[4]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assCheckApGeneral.setState(Integer.valueOf(temp[5]));
		    		mapVo.put("state", temp[5]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assCheckApGeneral.setCreate_emp(Long.valueOf(temp[6]));
		    		mapVo.put("create_emp", temp[6]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assCheckApGeneral.setCreate_date(DateUtil.stringToDate(temp[7]));
		    		mapVo.put("create_date", temp[7]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assCheckApGeneral.setAudit_emp(Long.valueOf(temp[8]));
		    		mapVo.put("audit_emp", temp[8]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assCheckApGeneral.setApply_date(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("apply_date", temp[9]);
					
					} else {
						
						err_sb.append("申报日期为空  ");
						
					}
					 
					
				AssCheckApGeneral data_exc_extis = assCheckApGeneralService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assCheckApGeneral.setError_type(err_sb.toString());
					
					list_err.add(assCheckApGeneral);
					
				} else {
			  
					String dataJson = assCheckApGeneralService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssCheckApGeneral data_exc = new AssCheckApGeneral();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 051101 盘盈处置申请单(一般设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/addBatchAssCheckApGeneral", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssCheckApGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssCheckApGeneral> list_err = new ArrayList<AssCheckApGeneral>();
		
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
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			AssCheckApGeneral assCheckApGeneral = new AssCheckApGeneral();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("check_ap_no"))) {
						
					assCheckApGeneral.setCheck_ap_no((String)jsonObj.get("check_ap_no"));
		    		mapVo.put("check_ap_no", jsonObj.get("check_ap_no"));
		    		} else {
						
						err_sb.append("盘盈申报单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("summary"))) {
						
					assCheckApGeneral.setSummary((String)jsonObj.get("summary"));
		    		mapVo.put("summary", jsonObj.get("summary"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assCheckApGeneral.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assCheckApGeneral.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assCheckApGeneral.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {
						
					assCheckApGeneral.setAudit_emp(Long.valueOf((String)jsonObj.get("audit_emp")));
		    		mapVo.put("audit_emp", jsonObj.get("audit_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("apply_date"))) {
						
					assCheckApGeneral.setApply_date(DateUtil.stringToDate((String)jsonObj.get("apply_date")));
		    		mapVo.put("apply_date", jsonObj.get("apply_date"));
		    		} else {
						
						err_sb.append("申报日期为空  ");
						
					}
					
					
				AssCheckApGeneral data_exc_extis = assCheckApGeneralService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assCheckApGeneral.setError_type(err_sb.toString());
					
					list_err.add(assCheckApGeneral);
					
				} else {
			  
					String dataJson = assCheckApGeneralService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssCheckApGeneral data_exc = new AssCheckApGeneral();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	/**
	 * @Description 删除数据 051101 盘盈处置申请单明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/deleteAssCheckApDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckApDetailGeneral(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_ap_no", ids[3]);
			mapVo.put("ass_id", ids[4]);
			mapVo.put("ass_no", ids[5]);
			AssCheckApGeneral assCheckApGeneral = assCheckApGeneralService.queryByCode(mapVo);
			
			if(assCheckApGeneral!=null && assCheckApGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已申报确认的数据不能保存! \"}");
			}

			listVo.add(mapVo);

		}

		
		String assCheckApDetailGeneralJson = assCheckApDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckApDetailGeneralJson);

	}

	/**
	 * @Description 查询数据 051101 盘盈处置申请明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/queryAssCheckApDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckApDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCheckApDetailGeneral = assCheckApDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckApDetailGeneral);

	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏申报(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/check/checkapply/updateConfirmCheckApGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmCheckApGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_ap_no", ids[3]);
			mapVo.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssCheckApGeneral assCheckApGeneral = assCheckApGeneralService.queryByCode(mapVo);
			if(assCheckApGeneral == null || assCheckApGeneral.getState() > 0){
				continue;
			}
			
			listVo.add(mapVo);

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assChkAGeneralJson = assCheckApGeneralService.updateConfirmChkAGeneral(listVo);

		return JSONObject.parseObject(assChkAGeneralJson);

	}
	
    
}

