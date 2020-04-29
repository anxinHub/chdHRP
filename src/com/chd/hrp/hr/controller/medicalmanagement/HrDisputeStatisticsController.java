/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
import java.io.IOException;
import java.util.HashMap;
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
import com.chd.hrp.hr.service.medicalmanagement.HrDisputeStatisticsService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DisputeStatistics 赔款处理统计
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/medicalsafety")
public class HrDisputeStatisticsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrDisputeStatisticsController.class);
	
	//引入Service服务
	@Resource(name = "hrDisputeStatisticsService")
	private final HrDisputeStatisticsService hrDisputeStatisticsService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrDisputeStatisticsMainPage", method = RequestMethod.GET)
	public String hrDisputeStatisticsMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/disputestatistics/disputeStatisticsMainPage";

	}

	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryDisputeStatistics", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrDisputeStatistics(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrDisputeStatistics = hrDisputeStatisticsService.queryDisputeStatistics(getPage(mapVo));

		return JSONObject.parseObject(hrDisputeStatistics);
		
	}

	
}

