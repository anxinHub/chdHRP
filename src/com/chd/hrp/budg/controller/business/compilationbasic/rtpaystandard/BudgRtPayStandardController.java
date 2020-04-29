/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.rtpaystandard;
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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgRtPayStandard;
import com.chd.hrp.budg.service.business.compilationbasic.rtpaystandard.BudgRtPayStandardService;
/**
 * 
 * @Description:
 * 人头付费标准维护
 * @Table:
 * BUDG_RT_PAY_STANDARD
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgRtPayStandardController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgRtPayStandardController.class);
	
	//引入Service服务
	@Resource(name = "budgRtPayStandardService")
	private final BudgRtPayStandardService budgRtPayStandardService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/budgRtPayStandardMainPage", method = RequestMethod.GET)
	public String budgRtPayStandardMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/rtpaystandard/budgRtPayStandardMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/budgRtPayStandardAddPage", method = RequestMethod.GET)
	public String budgRtPayStandardAddPage(Model mode) throws Exception {

		return "/hrp/budg/business/compilationbasic/rtpaystandard/budgRtPayStandardAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 人头付费标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/saveBudgRtPayStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgRtPayStandard(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("insurance_code", ids[1]);
			mapVo.put("outpatient_charge", ids[2]);
			mapVo.put("day_bed_charge", ids[3]);
			mapVo.put("o_workload_budg", ids[4]);
			mapVo.put("i_workload_budg", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[6]);
			}
			
			mapVo.put("rowNo", ids[7]);
			mapVo.put("flag", ids[8]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgWorkDeptDbzJson = null ;
		
		try {
			
			budgWorkDeptDbzJson = budgRtPayStandardService.save(listVo);
			
		} catch (Exception e) {
			
			budgWorkDeptDbzJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgWorkDeptDbzJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 人头付费标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/addBudgRtPayStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgRtPayStandard(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgRtPayStandardJson = budgRtPayStandardService.add(mapVo);

		return JSONObject.parseObject(budgRtPayStandardJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 人头付费标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/budgRtPayStandardUpdatePage", method = RequestMethod.GET)
	public String budgRtPayStandardUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("year") == null){
        mapVo.put("year", SessionManager.getAcctYear());
		}
		
		BudgRtPayStandard budgRtPayStandard = new BudgRtPayStandard();
    
		budgRtPayStandard = budgRtPayStandardService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgRtPayStandard.getGroup_id());
		mode.addAttribute("hos_id", budgRtPayStandard.getHos_id());
		mode.addAttribute("copy_code", budgRtPayStandard.getCopy_code());
		mode.addAttribute("year", budgRtPayStandard.getYear());
		mode.addAttribute("insurance_code", budgRtPayStandard.getInsurance_code());
		mode.addAttribute("outpatient_charge", budgRtPayStandard.getOutpatient_charge());
		mode.addAttribute("day_bed_charge", budgRtPayStandard.getDay_bed_charge());
		mode.addAttribute("o_workload_budg", budgRtPayStandard.getO_workload_budg());
		mode.addAttribute("i_workload_budg", budgRtPayStandard.getI_workload_budg());
		mode.addAttribute("remark", budgRtPayStandard.getRemark());
		
		return "hrp/budg/business/compilationbasic/rtpaystandard/budgRtPayStandardUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 人头付费标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/updateBudgRtPayStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgRtPayStandard(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();			
			String[] ids=id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0]);
			mapVo.put("insurance_code", ids[1]);
			mapVo.put("outpatient_charge", ids[2]);
			mapVo.put("day_bed_charge", ids[3]);
			mapVo.put("o_workload_budg", ids[4]);
			mapVo.put("i_workload_budg", ids[5]);
			
			if("-1".equals(ids[6])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[6]);
			}
		  listVo.add(mapVo);	      
		}            

	  
		String budgRtPayStandardJson = budgRtPayStandardService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgRtPayStandardJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 人头付费标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/addOrUpdateBudgRtPayStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgRtPayStandard(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgRtPayStandardJson ="";
		

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
	  
		budgRtPayStandardJson = budgRtPayStandardService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgRtPayStandardJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 人头付费标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/deleteBudgRtPayStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgRtPayStandard(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("insurance_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgRtPayStandardJson = budgRtPayStandardService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgRtPayStandardJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 人头付费标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/queryBudgRtPayStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgRtPayStandard(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgRtPayStandard = budgRtPayStandardService.query(getPage(mapVo));

		return JSONObject.parseObject(budgRtPayStandard);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 人头付费标准维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/budgRtPayStandardImportPage", method = RequestMethod.GET)
	public String budgRtPayStandardImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/rtpaystandard/budgRtPayStandardImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 人头付费标准维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/business/compilationbasic/rtpaystandard/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","人头付费标准维护模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 人头付费标准维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/rtpaystandard/readBudgRtPayStandardFiles",method = RequestMethod.POST)  
    public String readBudgRtPayStandardFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgRtPayStandard> list_err = new ArrayList<BudgRtPayStandard>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgRtPayStandard budgRtPayStandard = new BudgRtPayStandard();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					}
		    		
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
						budgRtPayStandard.setYear(temp[0]);
						
						mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						budgRtPayStandard.setInsurance_code(temp[1]);
						
						mapVo.put("insurance_code", temp[1]);
						
						mapVo.put("pay_mode_code", "RT");
						
						int count  = budgRtPayStandardService.queryInsuranceCodeExist(mapVo);
						
						if(count == 0){
							
							err_sb.append("医保类型不存在或其付费机制不是按人头付费");
						}
					
					} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						budgRtPayStandard.setOutpatient_charge(Double.valueOf(temp[2]));
						
			    		mapVo.put("outpatient_charge", temp[2]);
					
					} else {
						
						err_sb.append("门诊均次费用为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						budgRtPayStandard.setDay_bed_charge(Double.valueOf(temp[3]));
						
			    		mapVo.put("day_bed_charge", temp[3]);
					
					} else {
						
						err_sb.append("床日均次费用为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 4)) {
						
						budgRtPayStandard.setO_workload_budg(Long.valueOf(temp[4]));
						
			    		mapVo.put("o_workload_budg", temp[4]);
					
					} else {
						
						err_sb.append("门诊业务量预算为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 5)) {
						
						budgRtPayStandard.setI_workload_budg(Long.valueOf(temp[5]));
						
						mapVo.put("i_workload_budg", temp[5]);
					
					} else {
						
						err_sb.append("床日业务量预算为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 6)) {
						
						budgRtPayStandard.setRemark(temp[6]);
						mapVo.put("remark", temp[6]);
					
					} else {
						
						budgRtPayStandard.setRemark("");
						mapVo.put("remark", "");
						
					}
					 
					
				int count   = budgRtPayStandardService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgRtPayStandard.setError_type(err_sb.toString());
					
					list_err.add(budgRtPayStandard);
					
				} else {
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgRtPayStandardService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			
			BudgRtPayStandard data_exc = new BudgRtPayStandard();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 人头付费标准维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/addBatchBudgRtPayStandard", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgRtPayStandard(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgRtPayStandard> list_err = new ArrayList<BudgRtPayStandard>();
		
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
			
			BudgRtPayStandard budgRtPayStandard = new BudgRtPayStandard();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgRtPayStandard.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("insurance_code"))) {
						
					budgRtPayStandard.setInsurance_code((String)jsonObj.get("insurance_code"));
		    		mapVo.put("insurance_code", jsonObj.get("insurance_code"));
		    		} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("outpatient_charge"))) {
						
					budgRtPayStandard.setOutpatient_charge(Double.valueOf((String)jsonObj.get("outpatient_charge")));
		    		mapVo.put("outpatient_charge", jsonObj.get("outpatient_charge"));
		    		} else {
						
						err_sb.append("门诊均次费用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("day_bed_charge"))) {
						
					budgRtPayStandard.setDay_bed_charge(Double.valueOf((String)jsonObj.get("day_bed_charge")));
		    		mapVo.put("day_bed_charge", jsonObj.get("day_bed_charge"));
		    		} else {
						
						err_sb.append("床日均次费用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("o_workload_budg"))) {
						
					budgRtPayStandard.setO_workload_budg(Long.valueOf((String)jsonObj.get("o_workload_budg")));
		    		mapVo.put("o_workload_budg", jsonObj.get("o_workload_budg"));
		    		} else {
						
						err_sb.append("门诊业务量预算为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("i_workload_budg"))) {
						
					budgRtPayStandard.setI_workload_budg(Long.valueOf((String)jsonObj.get("i_workload_budg")));
		    		mapVo.put("i_workload_budg", jsonObj.get("i_workload_budg"));
		    		} else {
						
						err_sb.append("床日业务量预算为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgRtPayStandard.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					
				BudgRtPayStandard data_exc_extis = budgRtPayStandardService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgRtPayStandard.setError_type(err_sb.toString());
					
					list_err.add(budgRtPayStandard);
					
				} else {
			  
					String dataJson = budgRtPayStandardService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgRtPayStandard data_exc = new BudgRtPayStandard();
			
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
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		mapVo.put("pay_mode_code", "RT")   ;//RT:按人头付费
		
		//查询生成数据
		List<Map<String,Object>> list = budgRtPayStandardService.queryData(mapVo);
		
		for(Map<String,Object> item : list){
			
			item.put("outpatient_charge","");
			item.put("day_bed_charge","");
			item.put("o_workload_budg","");
			item.put("i_workload_budg","");
			item.put("remark","");
		}
			
	
		String budgRtPayStandard =null;
		
		if(list.size() > 0){
			
			budgRtPayStandard = budgRtPayStandardService.addBatch(list);
			
		}else{
			
			budgRtPayStandard = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
			
		}
    
		return JSONObject.parseObject(budgRtPayStandard);	
	} 
	
	/**
	 * 医保类型下拉框 主页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/queryBudgYBTY", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBTY(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String ybType = budgRtPayStandardService.queryBudgYBTY(mapVo);
		
		return ybType;

	}
	
	
	/**
	 * @Description 
	 * 最新导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/rtpaystandard/budgRTPaystandardImportNewPage", method = RequestMethod.POST)
	@ResponseBody
	public String budgRTPaystandardImportNewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgRtPayStandardService.budgRTPaystandardImportNewPage(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	
	
	
}

