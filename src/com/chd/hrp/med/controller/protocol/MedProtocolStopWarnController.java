/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.protocol;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.entity.MedProtocolFile;
import com.chd.hrp.med.serviceImpl.protocol.MedProtocolDetailServiceImpl;
import com.chd.hrp.med.serviceImpl.protocol.MedProtocolFileServiceImpl;
import com.chd.hrp.med.serviceImpl.protocol.MedProtocolMainServiceImpl;
/**
 * 
 * @Description:
 * @Table:
 * MED_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedProtocolStopWarnController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedProtocolStopWarnController.class);
	
	//引入Service服务
	@Resource(name = "medProtocolMainService")
	private final MedProtocolMainServiceImpl medProtocolMainService = null;
	
	@Resource(name = "medProtocolDetailService")
	private final MedProtocolDetailServiceImpl medProtocolDetailService = null;
	
	@Resource(name = "medProtocolFileService")
	private final MedProtocolFileServiceImpl medProtocolFileService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolstopwarn/medProtocolStopWarnMainPage", method = RequestMethod.GET)
	public String medProtocolMainMainPage(Model mode) throws Exception {

		return "hrp/med/protocol/medprotocolstopwarn/medProtocolStopWarnMain";

	}

	@RequestMapping(value = "/hrp/med/protocol/medprotocolstopwarn/medInvDetailPage", method = RequestMethod.GET)
	public String medInvDetailPage(Model mode) throws Exception {

		return "hrp/med/protocol/medprotocolmain/medInvDetail";
	}

/**
	 * @Description 
	 * 更新跳转页面 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolstopwarn/medProtocolStopWarnUpdatePage", method = RequestMethod.GET)
	public String medProtocolMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap = medProtocolMainService.queryMedProtocolMainUnit(mapVo);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("group_id", resultMap.get("group_id"));
		mode.addAttribute("hos_id", resultMap.get("hos_id"));
		mode.addAttribute("copy_code", resultMap.get("copy_code"));
		mode.addAttribute("protocol_id", resultMap.get("protocol_id"));
		mode.addAttribute("protocol_code", resultMap.get("protocol_code"));
		mode.addAttribute("original_no", resultMap.get("original_no"));
		mode.addAttribute("type_id", resultMap.get("type_id"));
		mode.addAttribute("type_code", resultMap.get("type_code"));
		mode.addAttribute("type_name", resultMap.get("type_name"));
		mode.addAttribute("protocol_name", resultMap.get("protocol_name"));
		mode.addAttribute("sign_date", df.format(resultMap.get("sign_date")));
		mode.addAttribute("sup_no", resultMap.get("sup_no"));
		mode.addAttribute("sup_id", resultMap.get("sup_id"));
		mode.addAttribute("sup_code", resultMap.get("sup_code"));
		mode.addAttribute("sup_name", resultMap.get("sup_name"));
		mode.addAttribute("is_bid", resultMap.get("is_bid"));
		mode.addAttribute("start_date", df.format(resultMap.get("start_date")));
		mode.addAttribute("end_date", df.format(resultMap.get("end_date")));
		mode.addAttribute("dept_no", resultMap.get("dept_no"));
		mode.addAttribute("dept_id", resultMap.get("dept_id"));
		mode.addAttribute("dept_code", resultMap.get("dept_code"));
		mode.addAttribute("dept_name", resultMap.get("dept_name"));
		mode.addAttribute("addr", resultMap.get("addr"));
		mode.addAttribute("first_man", resultMap.get("first_man"));
		mode.addAttribute("emp_no", resultMap.get("emp_no"));
		mode.addAttribute("emp_code", resultMap.get("emp_code"));
		mode.addAttribute("emp_name", resultMap.get("emp_name"));
		mode.addAttribute("second_man", resultMap.get("second_man"));
		mode.addAttribute("second_phone", resultMap.get("second_phone"));
		mode.addAttribute("contract_detail", resultMap.get("contract_detail"));
		mode.addAttribute("service_detail", resultMap.get("service_detail"));
		mode.addAttribute("create_emp", resultMap.get("create_emp"));
		mode.addAttribute("check_emp", resultMap.get("check_emp"));
		mode.addAttribute("confirm_emp", resultMap.get("confirm_emp"));
		mode.addAttribute("state", resultMap.get("state"));
		mode.addAttribute("spell", resultMap.get("spell"));
		mode.addAttribute("is_init", resultMap.get("is_init"));
		
		return "hrp/med/protocol/medprotocolstopwarn/medProtocolStopWarnUpdate";
	}
	/**
	 * 查看协议文档
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolstopwarn/medProtocolFilePage", method = RequestMethod.GET)
	public String medProtocolFilePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MedProtocolFile file = new MedProtocolFile();
		
		file = medProtocolFileService.queryMedProtocolFileByCode(mapVo);
		
		mode.addAttribute("group_id", file.getGroup_id());
		mode.addAttribute("hos_id", file.getHos_id());
		mode.addAttribute("copy_code", file.getCopy_code());
		mode.addAttribute("protocol_id", file.getProtocol_id());
		mode.addAttribute("file_id", file.getFile_id());
		mode.addAttribute("type_code", file.getType_code());
		mode.addAttribute("file_code", file.getFile_code());
		mode.addAttribute("file_name", file.getFile_name());
		mode.addAttribute("at_local", file.getAt_local());
		mode.addAttribute("dept_id", file.getDept_id());
		mode.addAttribute("mana_emp", file.getMana_emp());
		mode.addAttribute("file_path", file.getFile_path());
		
		return "hrp/med/protocol/medprotocolmain/medProtocolFile";
	}
	/**
	 * @Description 
	 * 更新数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolstopwarn/updateMedProtocolStopWarn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedProtocolInt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medProtocolMainJson = medProtocolMainService.updateMedProtocolMain(mapVo);
		
		return JSONObject.parseObject(medProtocolMainJson);
	}
	/**
	 * @Description 
	 * 更新数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolstopwarn/addOrUpdateMedProtocolStopWarn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedProtocolStopWarn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medProtocolMainJson ="";
		
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
	  
		medProtocolMainJson = medProtocolMainService.addOrUpdateMedProtocolMain(detailVo);
		
		}
		return JSONObject.parseObject(medProtocolMainJson);
	}
	
	/**
	 * @Description 
	 * 查询数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolstopwarn/queryMedProtocolStopWarn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedProtocolStopWarn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		mapVo.put("warn", 1);
		String medProtocolMain = medProtocolMainService.queryMedProtocolMainInfo(getPage(mapVo));

		return JSONObject.parseObject(medProtocolMain);
		
	}
	
}

