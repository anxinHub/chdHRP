
/*
 *
 */
 package com.chd.hrp.eqc.controller.xymanage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.eqc.service.xymanage.AssEqUseRecordDService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 21设备使用记录-服务细项 ASS_EQ_Use_RecordD Controller实现类
*/
@Controller
public class AssEqUseRecordDController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqUseRecordDController.class);
	
	//引入Service服务
	@Resource(name = "assEqUseRecordDService")
	private final AssEqUseRecordDService assEqUseRecordDService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseRecordDMainPage", method = RequestMethod.GET)
	public String assEqUseRecordDMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequserecordd/assEqUseRecordDMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseRecordDAddPage", method = RequestMethod.GET)
	public String assEqUseRecordDAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequserecordd/assEqUseRecordDAdd";

	}

	/**
	 * @Description 
	 * 添加数据 21设备使用记录-服务细项 ASS_EQ_Use_RecordD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqUseRecordD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqUseRecordD(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("analysis_code", ids[0])   ;
			mapVo.put("ex_id", ids[1]);
			mapVo.put("use_date", DateUtil.stringToDate(ids[2], "yyyy-MM-dd"));
			mapVo.put("start_time", DateUtil.stringToDate(ids[3], "yyyy-MM-dd"));
			if(StringTool.isNotBlank(ids[4])){
				mapVo.put("end_date", DateUtil.stringToDate(ids[4], "yyyy-MM-dd"));
			}else{
				mapVo.put("end_date", null);
			}
			if(StringTool.isNotBlank(ids[5])){
				mapVo.put("end_time", DateUtil.stringToDate(ids[5], "yyyy-MM-dd"));
			}else{
				mapVo.put("end_time", null);
			}
			
			mapVo.put("work_load_num", ids[6]);
			mapVo.put("unit_code", ids[7]);
			mapVo.put("dept_code", ids[8]);
			mapVo.put("patient_id", ids[9]);
			mapVo.put("patient_name", ids[10]);
			
			mapVo.put("price", ids[11]);
			mapVo.put("total_fee", ids[12]);
			mapVo.put("year", ids[13]);
			mapVo.put("month", ids[14]);
			
			mapVo.put("charge_item_id", ids[15]);
			mapVo.put("busi_data_source_code", ids[16]);
			mapVo.put("alone_pay_num", ids[17]);
			mapVo.put("is_input_flag", ids[18]);
			mapVo.put("status", ids[19]);
			mapVo.put("exposure_num", ids[20]);
			mapVo.put("remark", ids[21]);
			mapVo.put("rowNo", ids[22]);// 行号
			mapVo.put("ur_rowid", ids[23]);// 添加 修改标识
			
			mapVo.put("patient_sex", null);
			mapVo.put("patient_age", null);
			mapVo.put("invalid_flag", 1);
			mapVo.put("doctor_order_id", null);
			mapVo.put("operator", null);
			mapVo.put("positive_flag", null);
			mapVo.put("sample_no", null);
			mapVo.put("start_date", null);
			mapVo.put("other_info", null);
			
			
			
			listVo.add(mapVo);
	      
	    }
       
		String assEqUseRecordDJson = assEqUseRecordDService.save(listVo);

		return JSONObject.parseObject(assEqUseRecordDJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 21设备使用记录-服务细项 ASS_EQ_Use_RecordD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseRecordDUpdatePage", method = RequestMethod.GET)
	public String assEqUseRecordDUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> assEqUseRecordD = new HashMap<String, Object>();
    
		assEqUseRecordD = assEqUseRecordDService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqUseRecordD.get("group_id"));
		mode.addAttribute("hos_id", assEqUseRecordD.get("hos_id"));
		mode.addAttribute("copy_code", assEqUseRecordD.get("copy_code"));
		mode.addAttribute("ur_rowid", assEqUseRecordD.get("ur_rowid"));
		mode.addAttribute("analysis_code", assEqUseRecordD.get("analysis_code"));
		mode.addAttribute("use_date", assEqUseRecordD.get("use_date"));
		mode.addAttribute("start_time", assEqUseRecordD.get("start_time"));
		mode.addAttribute("end_date", assEqUseRecordD.get("end_date"));
		mode.addAttribute("end_time", assEqUseRecordD.get("end_time"));
		mode.addAttribute("work_load_num", assEqUseRecordD.get("work_load_num"));
		mode.addAttribute("unit_code", assEqUseRecordD.get("unit_code"));
		mode.addAttribute("dept_code", assEqUseRecordD.get("dept_code"));
		mode.addAttribute("patient_id", assEqUseRecordD.get("patient_id"));
		mode.addAttribute("patient_sex", assEqUseRecordD.get("patient_sex"));
		mode.addAttribute("patient_age", assEqUseRecordD.get("patient_age"));
		mode.addAttribute("patient_name", assEqUseRecordD.get("patient_name"));
		mode.addAttribute("price", assEqUseRecordD.get("price"));
		mode.addAttribute("total_fee", assEqUseRecordD.get("total_fee"));
		mode.addAttribute("alone_pay_num", assEqUseRecordD.get("alone_pay_num"));
		mode.addAttribute("year", assEqUseRecordD.get("year"));
		mode.addAttribute("month", assEqUseRecordD.get("month"));
		mode.addAttribute("charge_item_id", assEqUseRecordD.get("charge_item_id"));
		mode.addAttribute("busi_data_source_code", assEqUseRecordD.get("busi_data_source_code"));
		mode.addAttribute("ex_id", assEqUseRecordD.get("ex_id"));
		mode.addAttribute("is_input_flag", assEqUseRecordD.get("is_input_flag"));
		mode.addAttribute("status", assEqUseRecordD.get("status"));
		mode.addAttribute("invalid_flag", assEqUseRecordD.get("invalid_flag"));
		mode.addAttribute("remark", assEqUseRecordD.get("remark"));
		mode.addAttribute("doctor_order_id", assEqUseRecordD.get("doctor_order_id"));
		mode.addAttribute("operator", assEqUseRecordD.get("operator"));
		mode.addAttribute("positive_flag", assEqUseRecordD.get("positive_flag"));
		mode.addAttribute("sample_no", assEqUseRecordD.get("sample_no"));
		mode.addAttribute("exposure_num", assEqUseRecordD.get("exposure_num"));
		mode.addAttribute("start_date", assEqUseRecordD.get("start_date"));
		mode.addAttribute("other_info", assEqUseRecordD.get("other_info"));
		mode.addAttribute("add_user", assEqUseRecordD.get("add_user"));
		mode.addAttribute("add_date", assEqUseRecordD.get("add_date"));
		mode.addAttribute("add_time", assEqUseRecordD.get("add_time"));
		mode.addAttribute("update_user", assEqUseRecordD.get("update_user"));
		mode.addAttribute("update_date", assEqUseRecordD.get("update_date"));
		mode.addAttribute("update_time", assEqUseRecordD.get("update_time"));
		mode.addAttribute("cancel_date", assEqUseRecordD.get("cancel_date"));
		mode.addAttribute("cancel_time", assEqUseRecordD.get("cancel_time"));
		mode.addAttribute("cancel_user", assEqUseRecordD.get("cancel_user"));
		mode.addAttribute("submit_date", assEqUseRecordD.get("submit_date"));
		mode.addAttribute("submit_time", assEqUseRecordD.get("submit_time"));
		mode.addAttribute("submit_user", assEqUseRecordD.get("submit_user"));
		mode.addAttribute("audit_date", assEqUseRecordD.get("audit_date"));
		mode.addAttribute("audit_time", assEqUseRecordD.get("audit_time"));
		mode.addAttribute("audit_user", assEqUseRecordD.get("audit_user"));
		
		return "hrp/eqc/xymanage/assequserecordd/assEqUseRecordDUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 21设备使用记录-服务细项 ASS_EQ_Use_RecordD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqUseRecordD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqUseRecordD(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseRecordDJson = assEqUseRecordDService.update(mapVo);
		
		return JSONObject.parseObject(assEqUseRecordDJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 21设备使用记录-服务细项 ASS_EQ_Use_RecordD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqUseRecordD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqUseRecordD(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
					
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("ur_rowid", ids[0]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String assEqUseRecordDJson = assEqUseRecordDService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assEqUseRecordDJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 21设备使用记录-服务细项 ASS_EQ_Use_RecordD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqUseRecordD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqUseRecordD(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseRecordD = assEqUseRecordDService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqUseRecordD);
		
	}
	
    
}

