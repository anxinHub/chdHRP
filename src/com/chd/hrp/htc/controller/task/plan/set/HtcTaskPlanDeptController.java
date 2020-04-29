package com.chd.hrp.htc.controller.task.plan.set;

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
import com.chd.hrp.htc.service.task.plan.set.HtcTaskPlanDeptService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcTaskPlanDeptController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcTaskPlanDeptController.class);

	  @Resource(name = "htcTaskPlanDeptService")
	  private final HtcTaskPlanDeptService htcTaskPlanDeptService = null;

	// 查询方案中已经存在的核算科室
		@RequestMapping(value = "/hrp/htc/task/plan/set/queryHtcTaskPlanDeptByPlanSetOk", method = RequestMethod.POST)
		@ResponseBody
		public String queryHtcTaskPlanDeptByPlanSetOk(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			String depts = htcTaskPlanDeptService.queryHtcTaskPlanDeptByPlanSetOk(mapVo);
			return depts;
		}

		// 查询方案中不存在的核算科室
		@RequestMapping(value = "/hrp/htc/task/plan/set/queryHtcTaskPlanDeptByPlanSetNO", method = RequestMethod.POST)
		@ResponseBody
		public String queryHtcTaskPlanDeptByPlanSetNO(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			String depts = htcTaskPlanDeptService.queryHtcTaskPlanDeptByPlanSetNO(mapVo);
			return depts;
		}
		
		// 保存
		@RequestMapping(value = "/hrp/htc/task/plan/set/addHtcTaskPlanDept", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addHtcTaskPlanDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			
			String planDeptJson = "";
			
			try {
				
				planDeptJson = htcTaskPlanDeptService.addHtcTaskPlanDept(mapVo);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return JSONObject.parseObject(planDeptJson);

		}
}
