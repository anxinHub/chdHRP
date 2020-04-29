package com.chd.hrp.htc.controller.task.projectcost;

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
import com.chd.hrp.htc.entity.task.projectcost.HtcResCauseSet;
import com.chd.hrp.htc.service.task.projectcost.HtcResCauseSetService;

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
public class HtcResCauseSetController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcResCauseSetController.class);

	@Resource(name = "htcResCauseSetService")
	private final HtcResCauseSetService htcResCauseSetService = null;
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/rescauseset/htcResCauseSetMainPage", method = RequestMethod.GET)
	public String resCauseSetMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/rescauseset/htcResCauseSetMain";

	}

	// 初始化资源动因维护关系
	@RequestMapping(value = "/hrp/htc/task/projectcost/rescauseset/initHtcResCauseSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initResCauseSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcResCauseSetJson = "";
		try {
			htcResCauseSetJson = htcResCauseSetService.initHtcResCauseSet(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcResCauseSetJson = e.getMessage();
		}
		return JSONObject.parseObject(htcResCauseSetJson);
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/rescauseset/queryHtcResCauseSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResCauseSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String resCauseSet = htcResCauseSetService.queryHtcResCauseSet(getPage(mapVo));
		
		return JSONObject.parseObject(resCauseSet);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/rescauseset/htcResCauseSetBatchUpdatePage", method = RequestMethod.GET)
	public String resCauseSetBatchUpdatePage(@RequestParam Map<String, Object> mapVo, @RequestParam(value = "ParamVo") String paramVo,Model mode) throws Exception {
		mode.addAttribute("paramVo", paramVo);
		return "hrp/htc/task/projectcost/rescauseset/htcResCauseSetBatchUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/rescauseset/updateBatchHtcResCauseSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchHtcResCauseSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	    List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
	    String paramVo = (String) mapVo.get("paramVo");
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("acc_year", ids[3]);
			map.put("plan_code", ids[4]);
			map.put("proj_dept_no", ids[5]);
			map.put("proj_dept_id", ids[6]);
			map.put("cost_type_id", ids[7]);
			map.put("dir_res_code", mapVo.get("dir_res_code"));
			map.put("pub_res_code", mapVo.get("pub_res_code"));
			map.put("man_res_code", mapVo.get("man_res_code"));
			map.put("ass_res_code", mapVo.get("ass_res_code"));
			listVo.add(map);
    	}
		String htcResCauseSetJson = null;
		try {
			htcResCauseSetJson = htcResCauseSetService.updateBatchHtcResCauseSet(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcResCauseSetJson = e.getMessage();
		}
		return JSONObject.parseObject(htcResCauseSetJson);
	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/rescauseset/deleteHtcResCauseSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteResCauseSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("plan_code", ids[4]);
			mapVo.put("proj_dept_no", ids[5]);
			mapVo.put("proj_dept_id", ids[6]);
			mapVo.put("cost_type_id", ids[7]);
			listVo.add(mapVo);
		}
		String htcResCauseSetJson = "";
		try {
			htcResCauseSetJson = htcResCauseSetService.deleteBatchHtcResCauseSet(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.parseObject(htcResCauseSetJson);

	}
}
