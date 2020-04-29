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
import com.chd.hrp.htc.entity.task.projectcost.HtcWorkRear;
import com.chd.hrp.htc.service.task.projectcost.HtcWorkRearService;

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
public class HtcWorkRearController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcWorkRearController.class);
	
	
	@Resource(name = "htcWorkRearService")
	private final HtcWorkRearService htcWorkRearService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/workrear/htcWorkRearMainPage", method = RequestMethod.GET)
	public String workRearMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/workrear/htcWorkRearMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/projectcost/workrear/htcWorkRearAddPage", method = RequestMethod.GET)
	public String workRearAddPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/workrear/htcWorkRearAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/workrear/addHtcWorkRear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addWorkRear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String workRearJson = "";
		try {
			workRearJson = htcWorkRearService.addHtcWorkRear(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workRearJson = e.getMessage();
		}

		return JSONObject.parseObject(workRearJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/workrear/queryHtcWorkRear", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryWorkRear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String workRear = htcWorkRearService.queryHtcWorkRear(getPage(mapVo));

		return JSONObject.parseObject(workRear);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/workrear/deleteHtcWorkRear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteWorkRear(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("plan_code", ids[4]);
			mapVo.put("proj_dept_no", ids[5]);
			mapVo.put("proj_dept_id", ids[6]);
			mapVo.put("work_code", ids[7]);
			listVo.add(mapVo);
        }
		String workRearJson = "";
		try {
			workRearJson = htcWorkRearService.deleteBatchHtcWorkRear(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workRearJson = "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcWorkRear\"}";
		}
		/*String bonusItemDictJson = bonusItemDictService.deleteBonusItemDictById(ParamVo);*/
		return JSONObject.parseObject(workRearJson);		
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/workrear/htcWorkRearUpdatePage", method = RequestMethod.GET)
	
	public String workRearUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		HtcWorkRear htcWorkRear = htcWorkRearService.queryHtcWorkRearByCode(mapVo);
		mode.addAttribute("group_id", htcWorkRear.getGroup_id());
		mode.addAttribute("hos_id", htcWorkRear.getHos_id());
		mode.addAttribute("copy_code", htcWorkRear.getCopy_code());
		mode.addAttribute("acc_year", htcWorkRear.getAcc_year());
		mode.addAttribute("plan_code", htcWorkRear.getPlan_code());
		mode.addAttribute("plan_name", htcWorkRear.getPlan_name());
		mode.addAttribute("proj_dept_no", htcWorkRear.getProj_dept_no());
		mode.addAttribute("proj_dept_id", htcWorkRear.getProj_dept_id());
		mode.addAttribute("proj_dept_code", htcWorkRear.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcWorkRear.getProj_dept_name());
		mode.addAttribute("work_code", htcWorkRear.getWork_code());
		mode.addAttribute("work_name", htcWorkRear.getWork_name());
		mode.addAttribute("rear", htcWorkRear.getRear());
		return "hrp/htc/task/projectcost/workrear/htcWorkRearUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/workrear/updateHtcWorkRear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWorkRear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String workRearJson = "";
		try {
			workRearJson = htcWorkRearService.updateHtcWorkRear(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workRearJson = "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 updateHtcWorkRear\"}";
		}
		
		return JSONObject.parseObject(workRearJson);
	}

}

