package com.chd.hrp.acc.controller.single;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.single.AccGztService;

/**
 * 单独页-个人工资条查询
 * @author yang
 *
 */
@Controller
@RequestMapping(value = "/hrp/acc/single")
public class AccGztController extends BaseController {

	private static Logger logger = Logger.getLogger(AccGztController.class);
	
	@Resource(name = "accGztService")
	private final AccGztService accGztService = null;
	
	/**
	 * 取工资条的职工基础信息
	 */
	@RequestMapping(value = "getGztEmpBaseInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getGztEmpBaseInfo(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			String reJson = accGztService.getGztEmpBaseInfo(paraMap);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"查询职工信息失败.\", \"state\":\"false\""
					+ ",\"user_name\":\""+SessionManager.getUserName()+"\""
					+ ",\"user_id\":\""+SessionManager.getUserId()+"\""
					+ ",\"user_code\":\""+SessionManager.getUserCode()+"\"}");
		}
	}
	
	/**
	 * 取工资条方案的工资项
	 */
	@RequestMapping(value = "queryGztWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGztWageItem(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			String reJson = accGztService.queryGztWageItem(paraMap);
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"查询工资项目失败.\", \"state\":\"false\"}");
		}
	}
	
	/**
	 * 取工资条工资项的明细（金额）
	 */
	@RequestMapping(value = "queryEmpGztDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpGztDetail(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception{
		try{
			String yearMonth = paraMap.get("year_month").toString();
			paraMap.put("acc_year", yearMonth.split("\\.")[0]);
			paraMap.put("acc_month", yearMonth.split("\\.")[1]);
			paraMap.put("group_id", SessionManager.getGroupId());
			paraMap.put("hos_id", SessionManager.getHosId());
			
			boolean isGrant = accGztService.queryGzIsGrantByMonth(paraMap);// 指定的月是否有工资数据
			String reJson;
			if(isGrant){
				reJson = accGztService.queryGztDetail(paraMap);// 查询工资明细
			}else{
				reJson = "{\"warn\":\""+ paraMap.get("year_month").toString() + "，没有工资数据.\",\"state\":\"false\"}";
			}
			return JSONObject.parseObject(reJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"查询工资数据失败.\", \"state\":\"false\"}");
		}
	}
	
}
