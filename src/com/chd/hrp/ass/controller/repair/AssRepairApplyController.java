/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.repair;
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
import com.chd.hrp.ass.entity.maintain.AssMaintainPlan;
import com.chd.hrp.ass.entity.repair.AssRepairApply;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.repair.AssRepairApplyService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRepairApplyController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRepairApplyController.class);
	
	//引入Service服务
	@Resource(name = "assRepairApplyService")
	private final AssRepairApplyService assRepairApplyService = null;
	//引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/assRepairApplyMainPage", method = RequestMethod.GET)
	public String assRepairApplyMainPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapply/assRepairApplyMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/assRepairApplyAddPage", method = RequestMethod.GET)
	public String assRepairApplyAddPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapply/assRepairApplyAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/addAssRepairApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRepairApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get("05");
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		} 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) <= Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		String YearMonth=assBaseService.getLastAssYearMonth();
    	mapVo.put("ass_year", YearMonth.split("-")[0]);
    	mapVo.put("ass_month", YearMonth.split("-")[1]);
		
       
		String assRepairApplyJson = assRepairApplyService.add(mapVo);

		return JSONObject.parseObject(assRepairApplyJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/assRepairApplyUpdatePage", method = RequestMethod.GET)
	public String assRepairApplyUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssRepairApply assRepairApply = new AssRepairApply();
    
		assRepairApply = assRepairApplyService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRepairApply.getGroup_id());
		mode.addAttribute("hos_id", assRepairApply.getHos_id());
		mode.addAttribute("copy_code", assRepairApply.getCopy_code());
		mode.addAttribute("apply_no", assRepairApply.getApply_no());
		mode.addAttribute("ass_year", assRepairApply.getAss_year());
		mode.addAttribute("ass_month", assRepairApply.getAss_month());
		mode.addAttribute("ass_nature", assRepairApply.getAss_nature());
		mode.addAttribute("repair_dept_id", assRepairApply.getRepair_dept_id());
		mode.addAttribute("repair_dept_no", assRepairApply.getRepair_dept_no());
		mode.addAttribute("ass_name", assRepairApply.getAss_name());
		mode.addAttribute("apply_emp", assRepairApply.getApply_emp());
		mode.addAttribute("apply_date", DateUtil.dateToString(assRepairApply.getApply_date(),"yyyy-MM-dd"));
		mode.addAttribute("create_emp", assRepairApply.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assRepairApply.getCreate_date(),"yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assRepairApply.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(assRepairApply.getAudit_date(),"yyyy-MM-dd"));
		mode.addAttribute("state", assRepairApply.getState());
		mode.addAttribute("sharp_degree", assRepairApply.getSharp_degree());
		mode.addAttribute("rep_phone", assRepairApply.getRep_phone());
		mode.addAttribute("rep_team_code", assRepairApply.getRep_team_code());
		mode.addAttribute("note", assRepairApply.getNote());
		mode.addAttribute("ass_card_no", assRepairApply.getAss_card_no());
		
		return "hrp/ass/assrepairapply/assRepairApplyUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/updateAssRepairApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRepairApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get("05");
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		} 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) <= Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
		
		String assRepairApplyJson = assRepairApplyService.update(mapVo);
		
		return JSONObject.parseObject(assRepairApplyJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/addOrUpdateAssRepairApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRepairApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRepairApplyJson ="";
		

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
	  
		assRepairApplyJson = assRepairApplyService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assRepairApplyJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/deleteAssRepairApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRepairApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2]);
				mapVo.put("apply_no", ids[3]);
				
				AssRepairApply	assRepairApply=assRepairApplyService.queryByCode(mapVo);
				if (assRepairApply.getState() > 0) {
					flag = false;
					break;
				}
				listVo.add(mapVo);
	    }
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被审核! \"}");
		}
		try {
			String assRepairApplyJson = assRepairApplyService.deleteBatch(listVo);
			
			return JSONObject.parseObject(assRepairApplyJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	/**
	 * @Description 
	 * 审核数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapply/auditAssRepairApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAssRepairApply(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_no", ids[3]);
			AssRepairApply assRepairApply = assRepairApplyService.queryAssMaintainPlanByCode(mapVo);
			if (assRepairApply.getState() != 0) {
				flag = false;
				break;
			}

			mapVo.put("state", "1");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
			listVo.add(mapVo);
			
		}
		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核! \"}");

		}
		String assRepairApplyJson = assRepairApplyService.auditAssRepairApply(listVo);
		
		return JSONObject.parseObject(assRepairApplyJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/queryAssRepairApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRepairApply = assRepairApplyService.query(getPage(mapVo));

		return JSONObject.parseObject(assRepairApply);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/assRepairApplyImportPage", method = RequestMethod.GET)
	public String assRepairApplyImportPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapply/assRepairApplyImport";

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
	 @RequestMapping(value="hrp/ass/assrepairapply/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","tabledesc.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assrepairapply/readAssRepairApplyFiles",method = RequestMethod.POST)  
    public String readAssRepairApplyFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssRepairApply> list_err = new ArrayList<AssRepairApply>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssRepairApply assRepairApply = new AssRepairApply();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assRepairApply.setApply_no(temp[3]);
		    		mapVo.put("apply_no", temp[3]);
					
					} else {
						
						err_sb.append("申请单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assRepairApply.setAss_year(temp[4]);
		    		mapVo.put("ass_year", temp[4]);
					
					} else {
						
						err_sb.append("统计年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assRepairApply.setAss_month(temp[5]);
		    		mapVo.put("ass_month", temp[5]);
					
					} else {
						
						err_sb.append("统计月份为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assRepairApply.setAss_nature(temp[6]);
		    		mapVo.put("ass_nature", temp[6]);
					
					} else {
						
						err_sb.append("资产性质为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assRepairApply.setRepair_dept_id(Long.valueOf(temp[7]));
		    		mapVo.put("repair_dept_id", temp[7]);
					
					} else {
						
						err_sb.append("维修部门ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assRepairApply.setRepair_dept_no(Long.valueOf(temp[8]));
		    		mapVo.put("repair_dept_no", temp[8]);
					
					} else {
						
						err_sb.append("维修部门NO为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assRepairApply.setAss_name(temp[9]);
		    		mapVo.put("ass_name", temp[9]);
					
					} else {
						
						err_sb.append("资产名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					assRepairApply.setApply_emp(Long.valueOf(temp[10]));
		    		mapVo.put("apply_emp", temp[10]);
					
					} else {
						
						err_sb.append("申请人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					assRepairApply.setApply_date(DateUtil.stringToDate(temp[11]));
		    		mapVo.put("apply_date", temp[11]);
					
					} else {
						
						err_sb.append("申请日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					assRepairApply.setCreate_emp(Long.valueOf(temp[12]));
		    		mapVo.put("create_emp", temp[12]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					assRepairApply.setCreate_date(DateUtil.stringToDate(temp[13]));
		    		mapVo.put("create_date", temp[13]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					assRepairApply.setAudit_emp(Long.valueOf(temp[14]));
		    		mapVo.put("audit_emp", temp[14]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					assRepairApply.setAudit_date(DateUtil.stringToDate(temp[15]));
		    		mapVo.put("audit_date", temp[15]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					assRepairApply.setState(Integer.valueOf(temp[16]));
		    		mapVo.put("state", temp[16]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					assRepairApply.setSharp_degree(Integer.valueOf(temp[17]));
		    		mapVo.put("sharp_degree", temp[17]);
					
					} else {
						
						err_sb.append("紧急程度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					assRepairApply.setRep_phone(temp[18]);
		    		mapVo.put("rep_phone", temp[18]);
					
					} else {
						
						err_sb.append("报修人电话为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					assRepairApply.setRep_team_code(temp[19]);
		    		mapVo.put("rep_team_code", temp[19]);
					
					} else {
						
						err_sb.append("维修班组为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[20])) {
						
					assRepairApply.setNote(temp[20]);
		    		mapVo.put("note", temp[20]);
					
					} else {
						
						err_sb.append("故障内容为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[21])) {
						
					assRepairApply.setAss_card_no(temp[21]);
		    		mapVo.put("ass_card_no", temp[21]);
					
					} else {
						
						err_sb.append("资产卡片为空  ");
						
					}
					 
					
				AssRepairApply data_exc_extis = assRepairApplyService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRepairApply.setError_type(err_sb.toString());
					
					list_err.add(assRepairApply);
					
				} else {
			  
					String dataJson = assRepairApplyService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRepairApply data_exc = new AssRepairApply();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assrepairapply/addBatchAssRepairApply", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssRepairApply(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssRepairApply> list_err = new ArrayList<AssRepairApply>();
		
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
			
			AssRepairApply assRepairApply = new AssRepairApply();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("apply_no"))) {
						
					assRepairApply.setApply_no((String)jsonObj.get("apply_no"));
		    		mapVo.put("apply_no", jsonObj.get("apply_no"));
		    		} else {
						
						err_sb.append("申请单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_year"))) {
						
					assRepairApply.setAss_year((String)jsonObj.get("ass_year"));
		    		mapVo.put("ass_year", jsonObj.get("ass_year"));
		    		} else {
						
						err_sb.append("统计年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_month"))) {
						
					assRepairApply.setAss_month((String)jsonObj.get("ass_month"));
		    		mapVo.put("ass_month", jsonObj.get("ass_month"));
		    		} else {
						
						err_sb.append("统计月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_nature"))) {
						
					assRepairApply.setAss_nature((String)jsonObj.get("ass_nature"));
		    		mapVo.put("ass_nature", jsonObj.get("ass_nature"));
		    		} else {
						
						err_sb.append("资产性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("repair_dept_id"))) {
						
					assRepairApply.setRepair_dept_id(Long.valueOf((String)jsonObj.get("repair_dept_id")));
		    		mapVo.put("repair_dept_id", jsonObj.get("repair_dept_id"));
		    		} else {
						
						err_sb.append("维修部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("repair_dept_no"))) {
						
					assRepairApply.setRepair_dept_no(Long.valueOf((String)jsonObj.get("repair_dept_no")));
		    		mapVo.put("repair_dept_no", jsonObj.get("repair_dept_no"));
		    		} else {
						
						err_sb.append("维修部门NO为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_name"))) {
						
					assRepairApply.setAss_name((String)jsonObj.get("ass_name"));
		    		mapVo.put("ass_name", jsonObj.get("ass_name"));
		    		} else {
						
						err_sb.append("资产名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("apply_emp"))) {
						
					assRepairApply.setApply_emp(Long.valueOf((String)jsonObj.get("apply_emp")));
		    		mapVo.put("apply_emp", jsonObj.get("apply_emp"));
		    		} else {
						
						err_sb.append("申请人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("apply_date"))) {
						
					assRepairApply.setApply_date(DateUtil.stringToDate((String)jsonObj.get("apply_date")));
		    		mapVo.put("apply_date", jsonObj.get("apply_date"));
		    		} else {
						
						err_sb.append("申请日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assRepairApply.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assRepairApply.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {
						
					assRepairApply.setAudit_emp(Long.valueOf((String)jsonObj.get("audit_emp")));
		    		mapVo.put("audit_emp", jsonObj.get("audit_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_date"))) {
						
					assRepairApply.setAudit_date(DateUtil.stringToDate((String)jsonObj.get("audit_date")));
		    		mapVo.put("audit_date", jsonObj.get("audit_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assRepairApply.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sharp_degree"))) {
						
					assRepairApply.setSharp_degree(Integer.valueOf((String)jsonObj.get("sharp_degree")));
		    		mapVo.put("sharp_degree", jsonObj.get("sharp_degree"));
		    		} else {
						
						err_sb.append("紧急程度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("rep_phone"))) {
						
					assRepairApply.setRep_phone((String)jsonObj.get("rep_phone"));
		    		mapVo.put("rep_phone", jsonObj.get("rep_phone"));
		    		} else {
						
						err_sb.append("报修人电话为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("rep_team_code"))) {
						
					assRepairApply.setRep_team_code((String)jsonObj.get("rep_team_code"));
		    		mapVo.put("rep_team_code", jsonObj.get("rep_team_code"));
		    		} else {
						
						err_sb.append("维修班组为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					assRepairApply.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("故障内容为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_card_no"))) {
						
					assRepairApply.setAss_card_no((String)jsonObj.get("ass_card_no"));
		    		mapVo.put("ass_card_no", jsonObj.get("ass_card_no"));
		    		} else {
						
						err_sb.append("资产卡片为空  ");
						
					}
					
					
				AssRepairApply data_exc_extis = assRepairApplyService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRepairApply.setError_type(err_sb.toString());
					
					list_err.add(assRepairApply);
					
				} else {
			  
					String dataJson = assRepairApplyService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRepairApply data_exc = new AssRepairApply();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

