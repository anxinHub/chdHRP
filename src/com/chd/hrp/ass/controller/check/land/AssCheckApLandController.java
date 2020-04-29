/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.check.land;
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
import com.chd.hrp.ass.entity.check.land.AssCheckApLand;
import com.chd.hrp.ass.service.check.land.AssCheckApDetailLandService;
import com.chd.hrp.ass.service.check.land.AssCheckApLandService;
/**
 * 
 * @Description:
 * 051101 盘盈处置申请单(土地)
 * @Table:
 * ASS_CHECK_AP_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssCheckApLandController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssCheckApLandController.class);
	
	//引入Service服务
	@Resource(name = "assCheckApLandService")
	private final AssCheckApLandService assCheckApLandService = null;
	
	@Resource(name = "assCheckApDetailLandService")
	private final AssCheckApDetailLandService assCheckApDetailLandService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/assCheckApLandMainPage", method = RequestMethod.GET)
	public String assCheckApLandMainPage(Model mode) throws Exception {

		return "hrp/ass/assland/asscheck/checkapply/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/assCheckApLandAddPage", method = RequestMethod.GET)
	public String assCheckApLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asscheck/checkapply/add";

	}

	/**
	 * @Description 
	 * 添加数据 051101 盘盈处置申请单(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/saveAssCheckApLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckApLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssCheckApLand assCheckApLand = assCheckApLandService.queryByCode(mapVo);
		if(assCheckApLand != null && assCheckApLand.getState() > 0){
			return JSONObject.parseObject("{\"warn\":\"已申报确认的数据不能保存! \"}");
		}
		
       
		String assCheckApLandJson = assCheckApLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckApLandJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 051101 盘盈处置申请单(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/assCheckApLandUpdatePage", method = RequestMethod.GET)
	public String assCheckApLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssCheckApLand assCheckApLand = new AssCheckApLand();
    
		assCheckApLand = assCheckApLandService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assCheckApLand.getGroup_id());
		mode.addAttribute("hos_id", assCheckApLand.getHos_id());
		mode.addAttribute("copy_code", assCheckApLand.getCopy_code());
		mode.addAttribute("check_ap_no", assCheckApLand.getCheck_ap_no());
		mode.addAttribute("summary", assCheckApLand.getSummary());
		mode.addAttribute("state", assCheckApLand.getState());
		mode.addAttribute("create_emp", assCheckApLand.getCreate_emp());
		mode.addAttribute("create_date", assCheckApLand.getCreate_date());
		mode.addAttribute("audit_emp", assCheckApLand.getAudit_emp());
		mode.addAttribute("apply_date", assCheckApLand.getApply_date());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asscheck/checkapply/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 051101 盘盈处置申请单(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/updateAssCheckApLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckApLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assCheckApLandJson = assCheckApLandService.update(mapVo);
		
		return JSONObject.parseObject(assCheckApLandJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 051101 盘盈处置申请单(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/addOrUpdateAssCheckApLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssCheckApLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assCheckApLandJson ="";
		

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
	  
		assCheckApLandJson = assCheckApLandService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assCheckApLandJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 051101 盘盈处置申请单(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/deleteAssCheckApLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckApLand(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String assCheckApLandJson = assCheckApLandService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assCheckApLandJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 051101 盘盈处置申请单(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/queryAssCheckApLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckApLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String assCheckApLand = assCheckApLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckApLand);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 051101 盘盈处置申请单(土地)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/assCheckApLandImportPage", method = RequestMethod.GET)
	public String assCheckApLandImportPage(Model mode) throws Exception {

		return "hrp/ass/assland/asscheck/checkapply/assCheckApLandImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 051101 盘盈处置申请单(土地)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/ass/assland/check/checkapply/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"sys\\downTemplate","051101 盘盈处置申请单(土地).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 051101 盘盈处置申请单(土地)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assland/check/checkapply/readAssCheckApLandFiles",method = RequestMethod.POST)  
    public String readAssCheckApLandFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssCheckApLand> list_err = new ArrayList<AssCheckApLand>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssCheckApLand assCheckApLand = new AssCheckApLand();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assCheckApLand.setCheck_ap_no(temp[3]);
		    		mapVo.put("check_ap_no", temp[3]);
					
					} else {
						
						err_sb.append("盘盈申报单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assCheckApLand.setSummary(temp[4]);
		    		mapVo.put("summary", temp[4]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assCheckApLand.setState(Integer.valueOf(temp[5]));
		    		mapVo.put("state", temp[5]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assCheckApLand.setCreate_emp(Long.valueOf(temp[6]));
		    		mapVo.put("create_emp", temp[6]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assCheckApLand.setCreate_date(DateUtil.stringToDate(temp[7]));
		    		mapVo.put("create_date", temp[7]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assCheckApLand.setAudit_emp(Long.valueOf(temp[8]));
		    		mapVo.put("audit_emp", temp[8]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assCheckApLand.setApply_date(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("apply_date", temp[9]);
					
					} else {
						
						err_sb.append("申报日期为空  ");
						
					}
					 
					
				AssCheckApLand data_exc_extis = assCheckApLandService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assCheckApLand.setError_type(err_sb.toString());
					
					list_err.add(assCheckApLand);
					
				} else {
			  
					String dataJson = assCheckApLandService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssCheckApLand data_exc = new AssCheckApLand();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 051101 盘盈处置申请单(土地)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/addBatchAssCheckApLand", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssCheckApLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssCheckApLand> list_err = new ArrayList<AssCheckApLand>();
		
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
			
			AssCheckApLand assCheckApLand = new AssCheckApLand();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("check_ap_no"))) {
						
					assCheckApLand.setCheck_ap_no((String)jsonObj.get("check_ap_no"));
		    		mapVo.put("check_ap_no", jsonObj.get("check_ap_no"));
		    		} else {
						
						err_sb.append("盘盈申报单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("summary"))) {
						
					assCheckApLand.setSummary((String)jsonObj.get("summary"));
		    		mapVo.put("summary", jsonObj.get("summary"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assCheckApLand.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assCheckApLand.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assCheckApLand.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {
						
					assCheckApLand.setAudit_emp(Long.valueOf((String)jsonObj.get("audit_emp")));
		    		mapVo.put("audit_emp", jsonObj.get("audit_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("apply_date"))) {
						
					assCheckApLand.setApply_date(DateUtil.stringToDate((String)jsonObj.get("apply_date")));
		    		mapVo.put("apply_date", jsonObj.get("apply_date"));
		    		} else {
						
						err_sb.append("申报日期为空  ");
						
					}
					
					
				AssCheckApLand data_exc_extis = assCheckApLandService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assCheckApLand.setError_type(err_sb.toString());
					
					list_err.add(assCheckApLand);
					
				} else {
			  
					String dataJson = assCheckApLandService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssCheckApLand data_exc = new AssCheckApLand();
			
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
	 * @Description 删除数据 051101 盘盈处置申请单明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/deleteAssCheckApDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckApDetailLand(@RequestParam(value = "ParamVo") String paramVo,
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
			AssCheckApLand assCheckApLand = assCheckApLandService.queryByCode(mapVo);
			
			if(assCheckApLand!=null && assCheckApLand.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已申报确认的数据不能保存! \"}");
			}

			listVo.add(mapVo);

		}

		
		String assCheckApDetailLandJson = assCheckApDetailLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckApDetailLandJson);

	}

	/**
	 * @Description 查询数据 051101 盘盈处置申请明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/queryAssCheckApDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckApDetailLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCheckApDetailLand = assCheckApDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckApDetailLand);

	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/checkapply/updateConfirmCheckApLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmCheckApLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssCheckApLand assCheckApLand = assCheckApLandService.queryByCode(mapVo);
			if(assCheckApLand == null || assCheckApLand.getState() == 2){
				continue;
			}
			
			listVo.add(mapVo);

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assChkAlandJson = assCheckApLandService.updateConfirmChkAland(listVo);

		return JSONObject.parseObject(assChkAlandJson);

	}
	
    
}

