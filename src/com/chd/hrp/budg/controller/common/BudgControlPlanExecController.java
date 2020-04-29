/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
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
import com.chd.hrp.budg.service.common.BudgControlPlanExecService;
import com.chd.hrp.budg.service.control.BudgControlBorrowService;
import com.chd.hrp.budg.service.control.BudgControlPaymentService;

import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * 
 * @Description:
 * 预算控制主表 预算控制明细 用来对控制环节进行预算控制处理过程
 * @Table:
 * BUDG_C_MAIN BUDG_C_DET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgControlPlanExecController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgControlPlanExecController.class);
	
   
	//引入Service服务
	@Resource(name = "budgControlPlanExecService")
	private final BudgControlPlanExecService budgControlPlanExecService = null;
	@Resource(name = "budgControlBorrowService")
	private BudgControlBorrowService budgControlBorrowService;
	@Resource(name = "budgControlPaymentService")
	private BudgControlPaymentService budgControlPaymentService;
	
	/**
	 * @Description 
	 * 查询  计算过程查看数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/queryControlExecProcess", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryControlExecProcess(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("year", SessionManager.getAcctYear());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式	
		String oper_date =df.format(new Date());// new Date()为获取当前系统时间
        mapVo.put("oper_date", oper_date); //为获取当前系统时间
        Map<String, Object> dateMap = queryOperDateBeginEndDate(mapVo);
        
        mapVo.put("month_begin_date",dateMap.get("MONTH_BEGIN_DATE").toString());
        mapVo.put("month_end_date",dateMap.get("MONTH_END_DATE").toString());
        mapVo.put("year_begin_date",dateMap.get("YEAR_BEGIN_DATE").toString());
        mapVo.put("year_end_date",dateMap.get("YEAR_END_DATE").toString());
        mapVo.put("budg_year",dateMap.get("BUDG_YEAR").toString());
        mapVo.put("budg_month",dateMap.get("BUDG_MONTH").toString());
		
		Map<String, Object> budgControlPlanExecStr =JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");;
		String work_link_code =mapVo.get("work_link_code").toString();
		
		if ("MAT_OUT".equals(work_link_code)) {
			/**
			 * 科室领用出库环节控制  预算支出控制过程查看 返回消息查询
			 */
			budgControlPlanExecStr= budgControlPlanExecService.queryControlExecMatOutProcess(mapVo);
			return budgControlPlanExecStr;
		}else if ("MAT_IN".equals(work_link_code)) {
			/**
			 * 采购入库环节控制  预算支出控制过程查看 返回消息查询
			 */
			budgControlPlanExecStr= budgControlPlanExecService.queryControlExecMatInProcess(mapVo);
			return budgControlPlanExecStr;
		}else if ("MAT_APPLY".equals(work_link_code)) {
			/**
			 * 科室申领环节控制  预算支出控制过程查看 返回消息查询
			 */
			budgControlPlanExecStr= budgControlPlanExecService.queryControlExecMatApplyProcess(mapVo);
			return budgControlPlanExecStr;
		}else if ("ASS_IN".equals(work_link_code)) {
			/**
			 * 资产入库确认环节控制  预算支出控制过程查看 返回消息查询
			 */
			budgControlPlanExecStr= budgControlPlanExecService.queryControlExecAssInProcess(mapVo);
			return budgControlPlanExecStr;
		}else if ("MAT_PUR".equals(work_link_code)) {
			/**
			 * 采购计划 环节控制  预算支出控制过程查看 返回消息查询
			 */
			budgControlPlanExecStr= budgControlPlanExecService.queryControlExecMatPurProcess(mapVo);
			return budgControlPlanExecStr;
		}else if ("MAT_ORDER".equals(work_link_code)) {
			/**
			 * 采购订单 环节控制  预算支出控制过程查看 返回消息查询
			 */
			budgControlPlanExecStr= budgControlPlanExecService.queryControlExecMatOrderProcess(mapVo);
			return budgControlPlanExecStr;
		}else if ("BUDG_BORR".equals(work_link_code)) {
			 String r=budgControlBorrowService.control(mapVo);
			 return JSONObject.parseObject(r);

		}else if("BUDG_PAYMEN".equals(work_link_code)){
			String r=budgControlPaymentService.control(mapVo);
			return JSONObject.parseObject(r);
		}
		if ("ACC_VOUCH".equals(work_link_code)) {
			/**
			 * 凭证管理 环节控制  预算支出控制过程查看 返回消息查询
			 */
			budgControlPlanExecStr= budgControlPlanExecService.queryControlExecAccVouchProcess(mapVo);
			return budgControlPlanExecStr;
		}
		return JSONObject.parseObject("{\"work_msg\":\"不控制.\",\"work_flag\":\"1\"}");
	    //JSONObject.parseObject();
		
	}
	/**
	 * 根据操作日期,返回对应预算年、月及各自启始，结束日期
	 */
	public Map<String, Object> queryOperDateBeginEndDate(Map<String, Object> mapVo) throws Exception {
		Map<String, Object> dateMap =null;
		dateMap = budgControlPlanExecService.queryOperDateBeginEndDate(mapVo);
		return dateMap;
		
	}
}

