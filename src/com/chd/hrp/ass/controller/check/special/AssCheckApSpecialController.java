/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.check.special;
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
import com.chd.hrp.ass.entity.check.special.AssCheckApDetailSpecial;
import com.chd.hrp.ass.entity.check.special.AssCheckApSpecial;
import com.chd.hrp.ass.entity.check.special.AssChkAdetailSpecial;
import com.chd.hrp.ass.entity.check.special.AssChkAspecial;
import com.chd.hrp.ass.service.check.special.AssCheckApDetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssCheckApSpecialService;
/**
 * 
 * @Description:
 * 051101 盘盈处置申请单(专用设备)
 * @Table:
 * ASS_CHECK_AP_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssCheckApSpecialController extends BaseController{     
	
	private static Logger logger = Logger.getLogger(AssCheckApSpecialController.class);
	
	//引入Service服务
	@Resource(name = "assCheckApSpecialService")
	private final AssCheckApSpecialService assCheckApSpecialService = null;
	
	@Resource(name = "assCheckApDetailSpecialService")
	private final AssCheckApDetailSpecialService assCheckApDetailSpecialService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/assCheckApSpecialMainPage", method = RequestMethod.GET)
	public String assCheckApSpecialMainPage(Model mode) throws Exception {

		return "hrp/ass/assspecial/asscheck/checkapply/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/assCheckApSpecialAddPage", method = RequestMethod.GET)
	public String assCheckApSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asscheck/checkapply/add";

	}

	/**
	 * @Description 
	 * 添加数据 051101 盘盈处置申请单(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/saveAssCheckApSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckApSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssCheckApSpecial assCheckApSpecial = assCheckApSpecialService.queryByCode(mapVo);
		if(assCheckApSpecial != null && assCheckApSpecial.getState() > 0){
			return JSONObject.parseObject("{\"warn\":\"已申报确认的数据不能保存! \"}");
		}
		
       
		String assCheckApSpecialJson = assCheckApSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckApSpecialJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 051101 盘盈处置申请单(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/assCheckApSpecialUpdatePage", method = RequestMethod.GET)
	public String assCheckApSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssCheckApSpecial assCheckApSpecial = new AssCheckApSpecial();
    
		assCheckApSpecial = assCheckApSpecialService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assCheckApSpecial.getGroup_id());
		mode.addAttribute("hos_id", assCheckApSpecial.getHos_id());
		mode.addAttribute("copy_code", assCheckApSpecial.getCopy_code());
		mode.addAttribute("check_ap_no", assCheckApSpecial.getCheck_ap_no());
		mode.addAttribute("summary", assCheckApSpecial.getSummary());
		mode.addAttribute("state", assCheckApSpecial.getState());
		mode.addAttribute("create_emp", assCheckApSpecial.getCreate_emp());
		mode.addAttribute("create_date", assCheckApSpecial.getCreate_date());
		mode.addAttribute("audit_emp", assCheckApSpecial.getAudit_emp());
		mode.addAttribute("apply_date", assCheckApSpecial.getApply_date());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asscheck/checkapply/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 051101 盘盈处置申请单(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/updateAssCheckApSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckApSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assCheckApSpecialJson = assCheckApSpecialService.update(mapVo);
		
		return JSONObject.parseObject(assCheckApSpecialJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 051101 盘盈处置申请单(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/addOrUpdateAssCheckApSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssCheckApSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assCheckApSpecialJson ="";
		

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
	  
		assCheckApSpecialJson = assCheckApSpecialService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assCheckApSpecialJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 051101 盘盈处置申请单(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/deleteAssCheckApSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckApSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("check_ap_no", ids[3]);
				
				AssCheckApSpecial	assCheckApSpecial = assCheckApSpecialService.queryByCode(mapVo);
				
				if(assCheckApSpecial.getState() > 0){
					flag = false;
					break;
				}
				
	      listVo.add(mapVo);
	      
	    }
			
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已申报确认的数据不能删除! \"}");
		}
	    
		String assCheckApSpecialJson = assCheckApSpecialService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assCheckApSpecialJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 051101 盘盈处置申请单(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/queryAssCheckApSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckApSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String assCheckApSpecial = assCheckApSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckApSpecial);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 051101 盘盈处置申请单(专用设备)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/assCheckApSpecialImportPage", method = RequestMethod.GET)
	public String assCheckApSpecialImportPage(Model mode) throws Exception {

		return "hrp/ass/assspecial/asscheck/checkapply/assCheckApSpecialImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 051101 盘盈处置申请单(专用设备)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/ass/assspecial/check/checkapply/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"sys\\downTemplate","051101 盘盈处置申请单(专用设备).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 051101 盘盈处置申请单(专用设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assspecial/check/checkapply/readAssCheckApSpecialFiles",method = RequestMethod.POST)  
    public String readAssCheckApSpecialFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssCheckApSpecial> list_err = new ArrayList<AssCheckApSpecial>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssCheckApSpecial assCheckApSpecial = new AssCheckApSpecial();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assCheckApSpecial.setCheck_ap_no(temp[3]);
		    		mapVo.put("check_ap_no", temp[3]);
					
					} else {
						
						err_sb.append("盘盈申报单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assCheckApSpecial.setSummary(temp[4]);
		    		mapVo.put("summary", temp[4]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assCheckApSpecial.setState(Integer.valueOf(temp[5]));
		    		mapVo.put("state", temp[5]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assCheckApSpecial.setCreate_emp(Long.valueOf(temp[6]));
		    		mapVo.put("create_emp", temp[6]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assCheckApSpecial.setCreate_date(DateUtil.stringToDate(temp[7]));
		    		mapVo.put("create_date", temp[7]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assCheckApSpecial.setAudit_emp(Long.valueOf(temp[8]));
		    		mapVo.put("audit_emp", temp[8]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assCheckApSpecial.setApply_date(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("apply_date", temp[9]);
					
					} else {
						
						err_sb.append("申报日期为空  ");
						
					}
					 
					
				AssCheckApSpecial data_exc_extis = assCheckApSpecialService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assCheckApSpecial.setError_type(err_sb.toString());
					
					list_err.add(assCheckApSpecial);
					
				} else {
			  
					String dataJson = assCheckApSpecialService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssCheckApSpecial data_exc = new AssCheckApSpecial();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 051101 盘盈处置申请单(专用设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/addBatchAssCheckApSpecial", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssCheckApSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssCheckApSpecial> list_err = new ArrayList<AssCheckApSpecial>();
		
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
			
			AssCheckApSpecial assCheckApSpecial = new AssCheckApSpecial();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("check_ap_no"))) {
						
					assCheckApSpecial.setCheck_ap_no((String)jsonObj.get("check_ap_no"));
		    		mapVo.put("check_ap_no", jsonObj.get("check_ap_no"));
		    		} else {
						
						err_sb.append("盘盈申报单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("summary"))) {
						
					assCheckApSpecial.setSummary((String)jsonObj.get("summary"));
		    		mapVo.put("summary", jsonObj.get("summary"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assCheckApSpecial.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assCheckApSpecial.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assCheckApSpecial.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {
						
					assCheckApSpecial.setAudit_emp(Long.valueOf((String)jsonObj.get("audit_emp")));
		    		mapVo.put("audit_emp", jsonObj.get("audit_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("apply_date"))) {
						
					assCheckApSpecial.setApply_date(DateUtil.stringToDate((String)jsonObj.get("apply_date")));
		    		mapVo.put("apply_date", jsonObj.get("apply_date"));
		    		} else {
						
						err_sb.append("申报日期为空  ");
						
					}
					
					
				AssCheckApSpecial data_exc_extis = assCheckApSpecialService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assCheckApSpecial.setError_type(err_sb.toString());
					
					list_err.add(assCheckApSpecial);
					
				} else {
			  
					String dataJson = assCheckApSpecialService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssCheckApSpecial data_exc = new AssCheckApSpecial();
			
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
	 * @Description 删除数据 051101 盘盈处置申请单明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/deleteAssCheckApDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckApDetailSpecial(@RequestParam(value = "ParamVo") String paramVo,
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
			AssCheckApSpecial assCheckApSpecial = assCheckApSpecialService.queryByCode(mapVo);
			
			if(assCheckApSpecial!=null && assCheckApSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已申报确认的数据不能保存! \"}");
			}

			listVo.add(mapVo);

		}

		
		String assCheckApDetailSpecialJson = assCheckApDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckApDetailSpecialJson);

	}

	/**
	 * @Description 查询数据 051101 盘盈处置申请明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/queryAssCheckApDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckApDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCheckApDetailSpecial = assCheckApDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckApDetailSpecial);

	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkapply/updateConfirmCheckApSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmCheckApSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssCheckApSpecial assCheckApSpecial = assCheckApSpecialService.queryByCode(mapVo);
			if(assCheckApSpecial == null || assCheckApSpecial.getState() == 2){
				continue;
			}
			
			listVo.add(mapVo);

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assChkAspecialJson = assCheckApSpecialService.updateConfirmChkAspecial(listVo);

		return JSONObject.parseObject(assChkAspecialJson);

	}
	
    
}

