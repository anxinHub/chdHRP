/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller.autovouch.his;

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
import com.chd.hrp.acc.entity.autovouch.HisAccAssDept;
import com.chd.hrp.acc.service.autovouch.his.HisAccAssDeptService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccAssDeptController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccAssDeptController.class);

	@Resource(name = "hisAccAssDeptService")
	private final HisAccAssDeptService hisAccAssDeptService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdept/hisAccAssDeptMainPage", method = RequestMethod.GET)
	public String hisAccAssDeptMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccassdept/main";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdept/hisAccAssDeptAddPage", method = RequestMethod.GET)
	public String hisAccAssDeptAddPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccassdept/add";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdept/updateHisAccAssDeptPage", method = RequestMethod.GET)
	public String updateHisAccAssDeptPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		HisAccAssDept apt = hisAccAssDeptService.queryHisAccAssDeptByCode(mapVo);

		mode.addAttribute("dept_code" , apt.getDept_code());

		mode.addAttribute("dept_name", apt.getDept_name());

		mode.addAttribute("group_id",apt.getGroup_id());
		
		mode.addAttribute("hos_id",apt.getHos_id());
		
		mode.addAttribute("copy_code",apt.getCopy_code());

		return "hrp/acc/autovouch/his/hisaccassdept/hisAccAssDeptUpdate";

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdept/queryHisAccAssDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccAssDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = hisAccAssDeptService.queryHisAccAssDept(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdept/addHisAccAssDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisAccAssDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}

		String str = hisAccAssDeptService.addHisAccAssDept(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdept/updateHisAccAssDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHisAccAssDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}

		String str = hisAccAssDeptService.updateHisAccAssDept(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccassdept/deleteHisAccAssDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHisAccAssDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", res[0]);//实际实体类变量
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[2]);
            
            mapVo.put("dept_code", res[3]);
            
            listVo.add(mapVo);
        }
		
		String apt = hisAccAssDeptService.delHisAccAssDept(listVo);

		return JSONObject.parseObject(apt);

	}

}
