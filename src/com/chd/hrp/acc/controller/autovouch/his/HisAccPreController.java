/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller.autovouch.his;

import java.util.HashMap;
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
import com.chd.hrp.acc.service.autovouch.his.HisAccPreService;

/**
 * @Title. @Description. 出纳账
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccPreController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccPreController.class);

	@Resource(name = "hisAccPreService")
	private final HisAccPreService hisAccPreService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/accpre/hisAccPreMainPage", method = RequestMethod.GET)
	public String hisAccPreMainPage(Model mode) throws Exception {
		
		Map<String, Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("his_table", "acc_pre_i");
		int type=hisAccPreService.queryHisAccType(mapVo);
		mode.addAttribute("type", type);
		return "hrp/acc/autovouch/his/accpre/hisAccPreMain";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/accpre/hisAccPreDetailMainPage", method = RequestMethod.GET)
	public String hisAccPreDetailMainPage(Model mode) throws Exception {

		Map<String, Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("his_table", "acc_pre_detail_i");
		int type=hisAccPreService.queryHisAccType(mapVo);
		mode.addAttribute("type", type);
		
		return "hrp/acc/autovouch/his/accpre/hisAccPreDetailMain";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/accpre/viewHisAccPreDetailPage", method = RequestMethod.GET)
	public String viewHisAccPreDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("rep_no", mapVo.get("rep_no"));
		
		mode.addAttribute("charge_code_equal", mapVo.get("charge_code_equal"));
		
		mode.addAttribute("charge_date", mapVo.get("charge_date"));
		mode.addAttribute("io_type", mapVo.get("io_type"));
		
		return "hrp/acc/autovouch/his/accpre/viewHisAccPreDetail";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/accpre/queryHisAccPre", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccPre(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		String str ="";
		
		if("0".equals(mapVo.get("io_type").toString())){
			
			str = hisAccPreService.queryHisAccPreO(getPage(mapVo));
			
		}else{
			
			str = hisAccPreService.queryHisAccPreI(getPage(mapVo));
			
		}

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/accpre/queryHisAccPreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccPreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		String str ="";
		
		if("0".equals(mapVo.get("io_type").toString())){
			
			str = hisAccPreService.queryHisAccPreDetailO(getPage(mapVo));
			
		}else{
			
			str = hisAccPreService.queryHisAccPreDetailI(getPage(mapVo));
			
		}

		return JSONObject.parseObject(str);

	}

}
