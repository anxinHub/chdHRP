/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagRecord;
import com.chd.hrp.hr.service.medicalmanagement.HrMeetDiagRecordService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_MeetDiagRecordRecord 全院大会诊记录
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/healthadministration/consultation")
public class HrMeetDiagRecController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrMeetDiagRecController.class);
	
	//引入Service服务
	@Resource(name = "hrMeetDiagRecordService")
	private final HrMeetDiagRecordService hrMeetDiagRecordService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrMeetDiagRecordMainPage", method = RequestMethod.GET)
	public String hrMeetDiagRecordRecordMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/meetdiagrecord/meetDiagRecMainPage";

	}


	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addMeetDiagRecord", method = RequestMethod.POST)
	@ResponseBody
	public String addHrMeetDiagRecordRecord(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
try{
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("is_commit", 0);
       
		String hrMeetDiagRecordRecordJson = hrMeetDiagRecordService.addMeetDiagRecord(mapVo);

		return hrMeetDiagRecordRecordJson;
}catch(Exception e) {
	return "{\"error\":\"" + e.getMessage() + "\"}";

	//throw new SysException(e.getMessage());
}
	}

	
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteMeetDiagRecord", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrMeetDiagRecordRecord(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
			List<HrMeetDiagRecord> listVo = JSONArray.parseArray(paramVo, HrMeetDiagRecord.class);
		
		try {
			/*for (HrMeetDiagRecord hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Integer.parseInt(SessionManager.getGroupId().toString()));
				hrDrugPerm.setHos_id(Integer.parseInt(SessionManager.getHosId().toString()));
			}*/
			
			
			  return hrMeetDiagRecordService.deleteMeetDiagRecord(listVo);
			
					
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryMeetDiagRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrMeetDiagRecord(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrMeetDiagRecordRecord = hrMeetDiagRecordService.queryMeetDiagRecord(getPage(mapVo));

		return JSONObject.parseObject(hrMeetDiagRecordRecord);
		
	}
	/**
	 * @Description 
	 * 查询大会诊
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryMeetDiagRecordApp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMeetDiagRecordApp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrMeetDiagRecordRecord = hrMeetDiagRecordService.queryMeetDiagApp(getPage(mapVo));

		return JSONObject.parseObject(hrMeetDiagRecordRecord);
		
	}
	
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmMeetDiagRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMeetDiagRecord(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg = "";
		try {
			List<HrMeetDiagRecord> listVo = JSONArray.parseArray(paramVo, HrMeetDiagRecord.class);

			for (HrMeetDiagRecord hrMeetDiagRecord : listVo) {
				if (hrMeetDiagRecord.getIs_commit() == 0) {
					hrMeetDiagRecord.setIs_commit(1);
					hrMeetDiagRecord.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrMeetDiagRecord.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrMeetDiagRecordService.confirmMeetDiagRecord(hrMeetDiagRecord);
				} else {
					msg = "{\"error\":\"提交失败！请勿重复提交！\",\"Is_commit\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmHrMeetDiagRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrMeetDiagRecord(@RequestParam String paramVo, Model mode)
			throws Exception {

		String msg = "";
		try {
			List<HrMeetDiagRecord> listVo = JSONArray.parseArray(paramVo, HrMeetDiagRecord.class);

			for (HrMeetDiagRecord hrMeetDiagRecord : listVo) {
				if (hrMeetDiagRecord.getIs_commit() == 1) {
					hrMeetDiagRecord.setIs_commit(0);
					hrMeetDiagRecord.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrMeetDiagRecord.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrMeetDiagRecordService.reConfirmMeetDiagRecord(hrMeetDiagRecord);
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
}

