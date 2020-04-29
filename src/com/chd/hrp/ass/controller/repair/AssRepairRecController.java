/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.repair;
import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.hrp.ass.entity.repair.AssRepairApply;
import com.chd.hrp.ass.entity.repair.AssRepairRec;
import com.chd.hrp.ass.service.repair.AssRepairApplyService;
import com.chd.hrp.ass.service.repair.AssRepairRecService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_REC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRepairRecController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRepairRecController.class);
	
	//引入Service服务
	@Resource(name = "assRepairRecService")
	private final AssRepairRecService assRepairRecService = null;
	
	//引入Service服务
	@Resource(name = "assRepairApplyService")
	private final AssRepairApplyService assRepairApplyService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairrec/assRepairRecMainPage", method = RequestMethod.GET)
	public String assRepairRecMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05080",MyConfig.getSysPara("05080"));
		
		return "hrp/ass/assrepairrec/assRepairRecMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairrec/assRepairRecAddPage", method = RequestMethod.GET)
	public String assRepairRecAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assrepairrec/assRepairRecAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairrec/addAssRepairRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRepairRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String assRepairRecJson = assRepairRecService.add(mapVo);

		return JSONObject.parseObject(assRepairRecJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairrec/assRepairRecUpdatePage", method = RequestMethod.GET)
	public String assRepairRecUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		//维修记录表
		AssRepairRec assRepairRec = new AssRepairRec();
				assRepairRec = assRepairRecService.queryByCode(mapVo);
		//申请
		AssRepairApply assRepairApply = new AssRepairApply();
		mapVo.put("apply_no", assRepairRec.getApply_no());
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
		mode.addAttribute("repair_dept_name", assRepairApply.getDept_name());
		mode.addAttribute("ass_name", assRepairApply.getAss_name());
		mode.addAttribute("apply_emp", assRepairApply.getApply_emp());
		mode.addAttribute("apply_emp_name", assRepairApply.getApply_name());
		mode.addAttribute("apply_date", DateUtil.dateToString(assRepairApply.getApply_date(),"yyyy-MM-dd"));
		mode.addAttribute("create_emp", assRepairApply.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assRepairApply.getCreate_date(),"yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assRepairApply.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(assRepairApply.getAudit_date(),"yyyy-MM-dd"));
		mode.addAttribute("sharp_degree", assRepairApply.getSharp_degree());
		mode.addAttribute("rep_phone", assRepairApply.getRep_phone());
		mode.addAttribute("rep_team_code", assRepairApply.getRep_team_code());
		mode.addAttribute("note", assRepairApply.getNote());
		mode.addAttribute("ass_card_no", assRepairApply.getAss_card_no());
		
		mode.addAttribute("is_inner",assRepairRec.getIs_inner());
		mode.addAttribute("repair_date",DateUtil.dateToString(assRepairRec.getRepair_date(),"yyyy-MM-dd"));
		mode.addAttribute("repair_engr",assRepairRec.getRepair_engr());
		mode.addAttribute("trouble_level",assRepairRec.getTrouble_level());
		mode.addAttribute("repair_level",assRepairRec.getRepair_level());
		mode.addAttribute("repair_hours",assRepairRec.getRepair_hours());
		mode.addAttribute("is_contract",assRepairRec.getIs_contract());
		mode.addAttribute("contract_no",assRepairRec.getContract_no());
		mode.addAttribute("repair_money",assRepairRec.getRepair_money());
		mode.addAttribute("other_money",assRepairRec.getOther_money());
		mode.addAttribute("repair_desc",assRepairRec.getRepair_desc());
		mode.addAttribute("repair_rec_no", assRepairRec.getRepair_rec_no());
		mode.addAttribute("state", assRepairRec.getState());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05080",MyConfig.getSysPara("05080"));
		
		return "hrp/ass/assrepairrec/assRepairRecUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairrec/updateAssRepairRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRepairRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRepairRecJson = assRepairRecService.update(mapVo);
		
		return JSONObject.parseObject(assRepairRecJson);
	}
	/**
	 * @Description 
	 * 审核单据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrec/auditAssRepairRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAssRepairRec(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("repair_rec_no", ids[3]);
					
		      listVo.add(mapVo);
		      
		    }
		String assRepairRecJson = assRepairRecService.auditAssRepairRec(listVo);
		
		  return JSONObject.parseObject(assRepairRecJson);
	}
	/**
	 * @Description 
	 * 单据消审 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrec/backAssRepairRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backAssRepairRec(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("repair_rec_no", ids[3]);
			
			listVo.add(mapVo);
			
		}
		String assRepairRecJson = assRepairRecService.backAssRepairRec(listVo);
		
		return JSONObject.parseObject(assRepairRecJson);
	}
	/**
	 * @Description 
	 * 维修完成确认 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrec/countAssRepairRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> countAssRepairRec(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("repair_rec_no", ids[3]);
			
			listVo.add(mapVo);
			
		}
		String assRepairRecJson = assRepairRecService.countAssRepairRec(listVo);
		
		return JSONObject.parseObject(assRepairRecJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairrec/addOrUpdateAssRepairRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRepairRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRepairRecJson ="";
		

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
	  
		assRepairRecJson = assRepairRecService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assRepairRecJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairrec/deleteAssRepairRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRepairRec(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("ass_year", ids[3])   ;
				mapVo.put("ass_month", ids[4])   ;
				mapVo.put("repair_rec_no", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String assRepairRecJson = assRepairRecService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRepairRecJson);
			
	}
	/**
	 * @Description 
	 * 删除数据明细表材料数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrec/deleteAssRepairRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRepairRecDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("repair_rec_no", ids[3]);
			mapVo.put("inv_code", ids[4]);
			
			listVo.add(mapVo);
			
		}
		
		String assRepairRecJson = assRepairRecService.deleteAssRepairRecDetail(listVo);
		
		return JSONObject.parseObject(assRepairRecJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairrec/queryAssRepairRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRepairRec = assRepairRecService.query(getPage(mapVo));

		return JSONObject.parseObject(assRepairRec);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrec/queryAssRepairRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairRecDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRepairRec = assRepairRecService.queryAssRepairRecDetail(mapVo);
		
		return JSONObject.parseObject(assRepairRec);
		
	}
	
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assrepairrec/assRepairRecImportPage", method = RequestMethod.GET)
	public String assRepairRecImportPage(Model mode) throws Exception {

		return "hrp/ass/assrepairrec/assRepairRecImport";

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
	 @RequestMapping(value="hrp/ass/assrepairrec/downTemplate", method = RequestMethod.GET)  
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
	@RequestMapping(value="/hrp/ass/assrepairrec/readAssRepairRecFiles",method = RequestMethod.POST)  
    public String readAssRepairRecFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssRepairRec> list_err = new ArrayList<AssRepairRec>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssRepairRec assRepairRec = new AssRepairRec();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assRepairRec.setAss_year(temp[3]);
		    		mapVo.put("ass_year", temp[3]);
					
					} else {
						
						err_sb.append("统计年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assRepairRec.setAss_month(temp[4]);
		    		mapVo.put("ass_month", temp[4]);
					
					} else {
						
						err_sb.append("统计月份为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assRepairRec.setRepair_rec_no(temp[5]);
		    		mapVo.put("repair_rec_no", temp[5]);
					
					} else {
						
						err_sb.append("维修记录号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assRepairRec.setRep_team_code(temp[6]);
		    		mapVo.put("rep_team_code", temp[6]);
					
					} else {
						
						err_sb.append("维修班组为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assRepairRec.setFixed_dept_id(Long.valueOf(temp[7]));
		    		mapVo.put("fixed_dept_id", temp[7]);
					
					} else {
						
						err_sb.append("维修部门ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assRepairRec.setFixed_dept_no(Long.valueOf(temp[8]));
		    		mapVo.put("fixed_dept_no", temp[8]);
					
					} else {
						
						err_sb.append("维修部门NO为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assRepairRec.setAss_nature(temp[9]);
		    		mapVo.put("ass_nature", temp[9]);
					
					} else {
						
						err_sb.append("资产性质为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					assRepairRec.setAss_name(temp[10]);
		    		mapVo.put("ass_name", temp[10]);
					
					} else {
						
						err_sb.append("资产名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					assRepairRec.setAss_card_no(temp[11]);
		    		mapVo.put("ass_card_no", temp[11]);
					
					} else {
						
						err_sb.append("资产卡片号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					assRepairRec.setApply_no(temp[12]);
		    		mapVo.put("apply_no", temp[12]);
					
					} else {
						
						err_sb.append("申请单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					assRepairRec.setIs_inner(Integer.valueOf(temp[13]));
		    		mapVo.put("is_inner", temp[13]);
					
					} else {
						
						err_sb.append("是否内部维修为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					assRepairRec.setTrouble_level(Integer.valueOf(temp[14]));
		    		mapVo.put("trouble_level", temp[14]);
					
					} else {
						
						err_sb.append("故障级别为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					assRepairRec.setRepair_level(Integer.valueOf(temp[15]));
		    		mapVo.put("repair_level", temp[15]);
					
					} else {
						
						err_sb.append("维修级别为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					assRepairRec.setRepair_date(DateUtil.stringToDate(temp[16]));
		    		mapVo.put("repair_date", temp[16]);
					
					} else {
						
						err_sb.append("维修日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					assRepairRec.setRepair_engr(temp[17]);
		    		mapVo.put("repair_engr", temp[17]);
					
					} else {
						
						err_sb.append("维修工程师为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					assRepairRec.setRepair_hours(Integer.valueOf(temp[18]));
		    		mapVo.put("repair_hours", temp[18]);
					
					} else {
						
						err_sb.append("维修工时为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					assRepairRec.setIs_contract(Integer.valueOf(temp[19]));
		    		mapVo.put("is_contract", temp[19]);
					
					} else {
						
						err_sb.append("是否合同为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[20])) {
						
					assRepairRec.setContract_no(temp[20]);
		    		mapVo.put("contract_no", temp[20]);
					
					} else {
						
						err_sb.append("合同号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[21])) {
						
					assRepairRec.setRepair_money(Double.valueOf(temp[21]));
		    		mapVo.put("repair_money", temp[21]);
					
					} else {
						
						err_sb.append("维修费用为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[22])) {
						
					assRepairRec.setOther_money(Double.valueOf(temp[22]));
		    		mapVo.put("other_money", temp[22]);
					
					} else {
						
						err_sb.append("其他费用为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[23])) {
						
					assRepairRec.setState(Integer.valueOf(temp[23]));
		    		mapVo.put("state", temp[23]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[24])) {
						
					assRepairRec.setCreate_emp(Long.valueOf(temp[24]));
		    		mapVo.put("create_emp", temp[24]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[25])) {
						
					assRepairRec.setCreate_date(DateUtil.stringToDate(temp[25]));
		    		mapVo.put("create_date", temp[25]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[26])) {
						
					assRepairRec.setAudit_emp(Long.valueOf(temp[26]));
		    		mapVo.put("audit_emp", temp[26]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[27])) {
						
					assRepairRec.setRepair_desc(temp[27]);
		    		mapVo.put("repair_desc", temp[27]);
					
					} else {
						
						err_sb.append("检修说明为空  ");
						
					}
					 
					
				AssRepairRec data_exc_extis = assRepairRecService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRepairRec.setError_type(err_sb.toString());
					
					list_err.add(assRepairRec);
					
				} else {
			  
					String dataJson = assRepairRecService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRepairRec data_exc = new AssRepairRec();
			
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
	@RequestMapping(value = "/hrp/ass/assrepairrec/addBatchAssRepairRec", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssRepairRec(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssRepairRec> list_err = new ArrayList<AssRepairRec>();
		
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
			
			AssRepairRec assRepairRec = new AssRepairRec();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("ass_year"))) {
						
					assRepairRec.setAss_year((String)jsonObj.get("ass_year"));
		    		mapVo.put("ass_year", jsonObj.get("ass_year"));
		    		} else {
						
						err_sb.append("统计年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_month"))) {
						
					assRepairRec.setAss_month((String)jsonObj.get("ass_month"));
		    		mapVo.put("ass_month", jsonObj.get("ass_month"));
		    		} else {
						
						err_sb.append("统计月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("repair_rec_no"))) {
						
					assRepairRec.setRepair_rec_no((String)jsonObj.get("repair_rec_no"));
		    		mapVo.put("repair_rec_no", jsonObj.get("repair_rec_no"));
		    		} else {
						
						err_sb.append("维修记录号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("rep_team_code"))) {
						
					assRepairRec.setRep_team_code((String)jsonObj.get("rep_team_code"));
		    		mapVo.put("rep_team_code", jsonObj.get("rep_team_code"));
		    		} else {
						
						err_sb.append("维修班组为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fixed_dept_id"))) {
						
					assRepairRec.setFixed_dept_id(Long.valueOf((String)jsonObj.get("fixed_dept_id")));
		    		mapVo.put("fixed_dept_id", jsonObj.get("fixed_dept_id"));
		    		} else {
						
						err_sb.append("维修部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fixed_dept_no"))) {
						
					assRepairRec.setFixed_dept_no(Long.valueOf((String)jsonObj.get("fixed_dept_no")));
		    		mapVo.put("fixed_dept_no", jsonObj.get("fixed_dept_no"));
		    		} else {
						
						err_sb.append("维修部门NO为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_nature"))) {
						
					assRepairRec.setAss_nature((String)jsonObj.get("ass_nature"));
		    		mapVo.put("ass_nature", jsonObj.get("ass_nature"));
		    		} else {
						
						err_sb.append("资产性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_name"))) {
						
					assRepairRec.setAss_name((String)jsonObj.get("ass_name"));
		    		mapVo.put("ass_name", jsonObj.get("ass_name"));
		    		} else {
						
						err_sb.append("资产名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_card_no"))) {
						
					assRepairRec.setAss_card_no((String)jsonObj.get("ass_card_no"));
		    		mapVo.put("ass_card_no", jsonObj.get("ass_card_no"));
		    		} else {
						
						err_sb.append("资产卡片号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("apply_no"))) {
						
					assRepairRec.setApply_no((String)jsonObj.get("apply_no"));
		    		mapVo.put("apply_no", jsonObj.get("apply_no"));
		    		} else {
						
						err_sb.append("申请单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_inner"))) {
						
					assRepairRec.setIs_inner(Integer.valueOf((String)jsonObj.get("is_inner")));
		    		mapVo.put("is_inner", jsonObj.get("is_inner"));
		    		} else {
						
						err_sb.append("是否内部维修为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("trouble_level"))) {
						
					assRepairRec.setTrouble_level(Integer.valueOf((String)jsonObj.get("trouble_level")));
		    		mapVo.put("trouble_level", jsonObj.get("trouble_level"));
		    		} else {
						
						err_sb.append("故障级别为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("repair_level"))) {
						
					assRepairRec.setRepair_level(Integer.valueOf((String)jsonObj.get("repair_level")));
		    		mapVo.put("repair_level", jsonObj.get("repair_level"));
		    		} else {
						
						err_sb.append("维修级别为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("repair_date"))) {
						
					assRepairRec.setRepair_date(DateUtil.stringToDate((String)jsonObj.get("repair_date")));
		    		mapVo.put("repair_date", jsonObj.get("repair_date"));
		    		} else {
						
						err_sb.append("维修日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("repair_engr"))) {
						
					assRepairRec.setRepair_engr((String)jsonObj.get("repair_engr"));
		    		mapVo.put("repair_engr", jsonObj.get("repair_engr"));
		    		} else {
						
						err_sb.append("维修工程师为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("repair_hours"))) {
						
					assRepairRec.setRepair_hours(Integer.valueOf((String)jsonObj.get("repair_hours")));
		    		mapVo.put("repair_hours", jsonObj.get("repair_hours"));
		    		} else {
						
						err_sb.append("维修工时为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_contract"))) {
						
					assRepairRec.setIs_contract(Integer.valueOf((String)jsonObj.get("is_contract")));
		    		mapVo.put("is_contract", jsonObj.get("is_contract"));
		    		} else {
						
						err_sb.append("是否合同为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("contract_no"))) {
						
					assRepairRec.setContract_no((String)jsonObj.get("contract_no"));
		    		mapVo.put("contract_no", jsonObj.get("contract_no"));
		    		} else {
						
						err_sb.append("合同号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("repair_money"))) {
						
					assRepairRec.setRepair_money(Double.valueOf((String)jsonObj.get("repair_money")));
		    		mapVo.put("repair_money", jsonObj.get("repair_money"));
		    		} else {
						
						err_sb.append("维修费用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("other_money"))) {
						
					assRepairRec.setOther_money(Double.valueOf((String)jsonObj.get("other_money")));
		    		mapVo.put("other_money", jsonObj.get("other_money"));
		    		} else {
						
						err_sb.append("其他费用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assRepairRec.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assRepairRec.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assRepairRec.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {
						
					assRepairRec.setAudit_emp(Long.valueOf((String)jsonObj.get("audit_emp")));
		    		mapVo.put("audit_emp", jsonObj.get("audit_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("repair_desc"))) {
						
					assRepairRec.setRepair_desc((String)jsonObj.get("repair_desc"));
		    		mapVo.put("repair_desc", jsonObj.get("repair_desc"));
		    		} else {
						
						err_sb.append("检修说明为空  ");
						
					}
					
					
				AssRepairRec data_exc_extis = assRepairRecService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRepairRec.setError_type(err_sb.toString());
					
					list_err.add(assRepairRec);
					
				} else {
			  
					String dataJson = assRepairRecService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRepairRec data_exc = new AssRepairRec();
			
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

