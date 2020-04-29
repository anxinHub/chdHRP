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
import com.chd.hrp.acc.entity.autovouch.HisAccMedPayTypeRef;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedPayTypeRefService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccMedPayTypeRefController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccMedPayTypeRefController.class);

	@Resource(name = "hisAccMedPayTypeRefService")
	private final HisAccMedPayTypeRefService hisAccMedPayTypeRefService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytyperef/hisAccMedPayTypeRefMainPage", method = RequestMethod.GET)
	public String hisAccMedPayTypeRefMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedpaytyperef/hisAccMedPayTypeRefMain";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytyperef/hisAccMedPayTypeRefAddPage", method = RequestMethod.GET)
	public String hisAccMedPayTypeRefAddPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedpaytyperef/hisAccMedPayTypeRefAdd";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytyperef/queryHisAccMedPayTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccMedPayTypeRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = hisAccMedPayTypeRefService.queryHisAccMedPayTypeRef(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytyperef/addHisAccMedPayTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisAccMedPayTypeRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedPayTypeRefService.addHisAccMedPayTypeRef(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytyperef/updateHisAccMedPayTypeRefPage", method = RequestMethod.GET)
	public String updateHisAccMedPayTypeRefPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		HisAccMedPayTypeRef apt = hisAccMedPayTypeRefService.queryHisAccMedPayTypeRefByCode(mapVo);

		mode.addAttribute("pay_type_code" , apt.getPay_type_code());

		mode.addAttribute("pay_type_name", apt.getPay_type_name());

		mode.addAttribute("group_id",apt.getGroup_id());
		
		mode.addAttribute("hos_id",apt.getHos_id());
		
		mode.addAttribute("copy_code",apt.getCopy_code());

		return "hrp/acc/autovouch/his/hisaccmedpaytyperef/hisAccMedPayTypeRefUpdate";

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytyperef/updateHisAccMedPayTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHisAccMedPayTypeRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedPayTypeRefService.updateHisAccMedPayTypeRef(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytyperef/deleteHisAccMedPayTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHisAccMedPayTypeRef(@RequestParam(value="ParamVo") String paramVo,Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", res[0]);//实际实体类变量
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[2]);
            
            mapVo.put("pay_type_code", res[3]);
            
            listVo.add(mapVo);
        }

		String apt = hisAccMedPayTypeRefService.delHisAccMedPayTypeRef(listVo);


		return JSONObject.parseObject(apt);

	}

}
