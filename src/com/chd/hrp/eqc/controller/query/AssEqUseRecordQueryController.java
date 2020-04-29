
/*
 *
 */
 package com.chd.hrp.eqc.controller.query;
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
import com.chd.hrp.eqc.service.query.AssEqUseRecordQueryService;
import com.chd.hrp.eqc.service.xymanage.AssEqUseRecordDService;

/**
* 使用记录查询-服务细项 // ASS_EQ_Use_RecordD Controller实现类
* 使用记录查询-服务项目 // ASS_EQ_Use_RecordD Controller实现类
*/
@Controller
public class AssEqUseRecordQueryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqUseRecordQueryController.class);
	
	//引入Service服务
	@Resource(name = "assEqUseRecordQueryService")
	private final AssEqUseRecordQueryService assEqUseRecordQueryService = null;
   
	/**
	 * @Description 
	 * 页面跳转 使用记录查询-服务项目  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/query/assEqUseRecordMainPage", method = RequestMethod.GET)
	public String assEqUseRecordMainPage(Model mode) throws Exception {
		return "hrp/eqc/query/assequserecordquery/assEqUseRecordMain";
	}
	/**
	 * 页面跳转 使用记录查询-服务细项
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/query/assEqUseRecordDetailMainPage", method = RequestMethod.GET)
	public String assEqUseRecordDetailMainPage(Model mode) throws Exception {
		return "hrp/eqc/query/assequserecordquery/assEqUseRecordDetailMain";
	}
	
	
	/**
	 * @Description 
	 * 查询数据  21使用记录查询-服务项目 ASS_EQ_Use_RecordD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/query/queryEqUseMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEqUseMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseRecordD = assEqUseRecordQueryService.queryEqUseMain(getPage(mapVo));

		return JSONObject.parseObject(assEqUseRecordD);
	}
	/**
	 * @Description 
	 * 查询数据  21使用记录查询-服务细项 ASS_EQ_Use_RecordD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/query/queryEqUseDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEqUseDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqUseRecordD = assEqUseRecordQueryService.queryEqUseDetail(getPage(mapVo));
		
		return JSONObject.parseObject(assEqUseRecordD);
	}
	
    
}

