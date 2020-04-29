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
import com.chd.hrp.acc.entity.autovouch.HisAccMedPayType;
import com.chd.hrp.acc.entity.autovouch.HisAccMedVen;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedVenService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccMedVenController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccMedVenController.class);

	@Resource(name = "hisAccMedVenService")
	private final HisAccMedVenService hisAccMedVenService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedven/hisAccMedVenMainPage", method = RequestMethod.GET)
	public String hisAccMedVenMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedven/hisAccMedVenMain";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedven/hisAccMedVenAddPage", method = RequestMethod.GET)
	public String hisAccMedVenAddPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedven/hisAccMedVenAdd";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedven/updateHisAccMedVenPage", method = RequestMethod.GET)
	public String updateHisAccMedVenPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		HisAccMedVen apt = hisAccMedVenService.queryHisAccMedVenByCode(mapVo);

		mode.addAttribute("ven_code" , apt.getVen_code());

		mode.addAttribute("ven_name", apt.getVen_name());

		mode.addAttribute("group_id",apt.getGroup_id());
		
		mode.addAttribute("hos_id",apt.getHos_id());
		
		mode.addAttribute("copy_code",apt.getCopy_code());

		return "hrp/acc/autovouch/his/hisaccmedven/hisAccMedVenUpdate";

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedven/queryHisAccMedVen", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccMedVen(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = hisAccMedVenService.queryHisAccMedVen(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedven/addHisAccMedVen", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisAccMedVen(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedVenService.addHisAccMedVen(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedven/updateHisAccMedVen", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHisAccMedVen(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedVenService.updateHisAccMedVen(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedven/deleteHisAccMedVen", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHisAccMedVen(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", res[0]);//实际实体类变量
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[2]);
            
            mapVo.put("ven_code", res[3]);
            
            listVo.add(mapVo);
        }
		String apt = hisAccMedVenService.delHisAccMedVen(listVo);


		return JSONObject.parseObject(apt);

	}

}
