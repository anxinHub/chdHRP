package com.chd.hrp.htc.controller.task.readydata;
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
import com.chd.hrp.htc.service.task.readydata.HtcTitleCostEveService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcTitleCostEveController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcTitleCostEveController.class);
	
	
	@Resource(name = "htcTitleCostEveService")
	private final HtcTitleCostEveService htcTitleCostEveService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/readydata/titlecosteve/htcTitleCostEveMainPage", method = RequestMethod.GET)
	public String titleCostEveMainPage(Model mode) throws Exception {

		return "hrp/htc/task/readydata/titlecosteve/htcTitleCostEveMain";

	}


	// 平均工资测算
	@RequestMapping(value = "/hrp/htc/task/readydata/titlecosteve/averageHtcWageReckon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> averageWageReckon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcTitleCostEveJson = "";
		try {
			htcTitleCostEveJson = htcTitleCostEveService.averageHtcWageReckon(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcTitleCostEveJson = e.getMessage();
		}

		return JSONObject.parseObject(htcTitleCostEveJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/readydata/titlecosteve/queryHtcTitleCostEve", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryTitleCostEve(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcTitleCostEveJson = htcTitleCostEveService.queryHtcTitleCostEve(getPage(mapVo));
		return JSONObject.parseObject(htcTitleCostEveJson);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/task/readydata/titlecosteve/deleteHtcTitleCostEve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteTitleCostEve(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("acc_year", ids[3]);
            mapVo.put("plan_code", ids[4]);
            mapVo.put("proj_dept_no", ids[5]);
            mapVo.put("proj_dept_id", ids[6]);
            mapVo.put("title_code", ids[7]);
            mapVo.put("cost_item_no", ids[8]);
            mapVo.put("cost_item_id", ids[9]);
            listVo.add(mapVo);
        }
		String htcTitleCostEveJson = "";
		try {
			htcTitleCostEveJson = htcTitleCostEveService.deleteBatchHtcTitleCostEve(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcTitleCostEveJson = e.getMessage();
		}
		return JSONObject.parseObject(htcTitleCostEveJson);	
	}
}

