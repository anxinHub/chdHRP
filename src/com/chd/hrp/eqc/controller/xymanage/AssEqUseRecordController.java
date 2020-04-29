
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
import com.chd.hrp.eqc.service.xymanage.AssEqUseRecordService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 11设备使用记录 ASS_EQUseRecord Controller实现类
*/
@Controller
public class AssEqUseRecordController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqUseRecordController.class);
	
	//引入Service服务
	@Resource(name = "assEqUseRecordService")
	private final AssEqUseRecordService assEqUseRecordService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseRecordMainPage", method = RequestMethod.GET)
	public String assEqUseRecordMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequserecord/assEqUseRecordMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseRecordAddPage", method = RequestMethod.GET)
	public String assEqUseRecordAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequserecord/assEqUseRecordAdd";

	}

	/**
	 * @Description 
	 * 添加数据 11设备使用记录 ASS_EQUseRecord
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqUseRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqUseRecord(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			
			mapVo.put("charge_kind_id", ids[15]);
			//mapVo.put("charge_item_id", ids[16]);
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
			//mapVo.put("ex_id", null);
			mapVo.put("invalid_flag", 1);
			mapVo.put("doctor_order_id", null);
			mapVo.put("operator", null);
			mapVo.put("positive_flag", null);
			mapVo.put("sample_no", null);
			mapVo.put("start_date", null);
			mapVo.put("other_info", null);
			
			
			
			listVo.add(mapVo);
	      
	    }
       
		String assEqUseRecordJson = assEqUseRecordService.save(listVo);

		return JSONObject.parseObject(assEqUseRecordJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 11设备使用记录 ASS_EQUseRecord
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUseRecordUpdatePage", method = RequestMethod.GET)
	public String assEqUseRecordUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> assEqUseRecord = new HashMap<String, Object>();
    
		assEqUseRecord = assEqUseRecordService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqUseRecord.get("group_id"));
		mode.addAttribute("hos_id", assEqUseRecord.get("hos_id"));
		mode.addAttribute("copy_code", assEqUseRecord.get("copy_code"));
		mode.addAttribute("ur_rowid", assEqUseRecord.get("ur_rowid"));
		mode.addAttribute("analysis_code", assEqUseRecord.get("analysis_code"));
		//mode.addAttribute("ur_eq_group", assEqUseRecord.get("ur_eq_group"));
		mode.addAttribute("use_date", assEqUseRecord.get("use_date"));
		mode.addAttribute("start_time", assEqUseRecord.get("start_time"));
		mode.addAttribute("end_date", assEqUseRecord.get("end_date"));
		mode.addAttribute("end_time", assEqUseRecord.get("end_time"));
		mode.addAttribute("work_load_num", assEqUseRecord.get("work_load_num"));
		mode.addAttribute("unit_code", assEqUseRecord.get("unit_code"));
		mode.addAttribute("dept_code", assEqUseRecord.get("dept_code"));
		mode.addAttribute("patient_id", assEqUseRecord.get("patient_id"));
		mode.addAttribute("patient_sex", assEqUseRecord.get("patient_sex"));
		mode.addAttribute("patient_age", assEqUseRecord.get("patient_age"));
		mode.addAttribute("patient_name", assEqUseRecord.get("patient_name"));
		mode.addAttribute("price", assEqUseRecord.get("price"));
		mode.addAttribute("total_fee", assEqUseRecord.get("total_fee"));
		mode.addAttribute("alone_pay_num", assEqUseRecord.get("alone_pay_num"));
		mode.addAttribute("year", assEqUseRecord.get("year"));
		mode.addAttribute("month", assEqUseRecord.get("month"));
		mode.addAttribute("charge_kind_id", assEqUseRecord.get("charge_kind_id"));
		//mode.addAttribute("charge_item_id", assEqUseRecord.get("charge_item_id"));
		mode.addAttribute("busi_data_source_code", assEqUseRecord.get("busi_data_source_code"));
		mode.addAttribute("ex_id", assEqUseRecord.get("ex_id"));
		mode.addAttribute("is_input_flag", assEqUseRecord.get("is_input_flag"));
		mode.addAttribute("status", assEqUseRecord.get("status"));
		mode.addAttribute("invalid_flag", assEqUseRecord.get("invalid_flag"));
		mode.addAttribute("remark", assEqUseRecord.get("remark"));
		mode.addAttribute("doctor_order_id", assEqUseRecord.get("doctor_order_id"));
		mode.addAttribute("operator", assEqUseRecord.get("operator"));
		mode.addAttribute("positive_flag", assEqUseRecord.get("positive_flag"));
		mode.addAttribute("sample_no", assEqUseRecord.get("sample_no"));
		mode.addAttribute("exposure_num", assEqUseRecord.get("exposure_num"));
		mode.addAttribute("start_date", assEqUseRecord.get("start_date"));
		mode.addAttribute("other_info", assEqUseRecord.get("other_info"));
		mode.addAttribute("add_user", assEqUseRecord.get("add_user"));
		mode.addAttribute("add_date", assEqUseRecord.get("add_date"));
		mode.addAttribute("add_time", assEqUseRecord.get("add_time"));
		mode.addAttribute("update_user", assEqUseRecord.get("update_user"));
		mode.addAttribute("update_date", assEqUseRecord.get("update_date"));
		mode.addAttribute("update_time", assEqUseRecord.get("update_time"));
		mode.addAttribute("cancel_date", assEqUseRecord.get("cancel_date"));
		mode.addAttribute("cancel_time", assEqUseRecord.get("cancel_time"));
		mode.addAttribute("cancel_user", assEqUseRecord.get("cancel_user"));
		mode.addAttribute("submit_date", assEqUseRecord.get("submit_date"));
		mode.addAttribute("submit_time", assEqUseRecord.get("submit_time"));
		mode.addAttribute("submit_user", assEqUseRecord.get("submit_user"));
		mode.addAttribute("audit_date", assEqUseRecord.get("audit_date"));
		mode.addAttribute("audit_time", assEqUseRecord.get("audit_time"));
		mode.addAttribute("audit_user", assEqUseRecord.get("audit_user"));
		
		return "hrp/eqc/xymanage/assequserecord/assEqUseRecordUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 11设备使用记录 ASS_EQUseRecord
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqUseRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqUseRecord(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseRecordJson = assEqUseRecordService.update(mapVo);
		
		return JSONObject.parseObject(assEqUseRecordJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 11设备使用记录 ASS_EQUseRecord
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqUseRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqUseRecord(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
			String assEqUseRecordJson = assEqUseRecordService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assEqUseRecordJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 11设备使用记录 ASS_EQUseRecord
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqUseRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqUseRecord(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseRecord = assEqUseRecordService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqUseRecord);
		
	}
	
    
}

