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
import com.chd.hrp.acc.service.autovouch.his.HisAccMedPayTypeService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccMedPayTypeController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccMedPayTypeController.class);

	@Resource(name = "hisAccMedPayTypeService")
	private final HisAccMedPayTypeService hisAccMedPayTypeService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytype/hisAccMedPayTypeMainPage", method = RequestMethod.GET)
	public String hisAccMedPayTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedpaytype/hisAccMedPayTypeMain";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytype/hisAccMedPayTypeAddPage", method = RequestMethod.GET)
	public String hisAccMedPayTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedpaytype/hisAccMedPayTypeAdd";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytype/queryHisAccMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccMedPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = hisAccMedPayTypeService.queryHisAccMedPayType(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytype/addHisAccMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisAccMedPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedPayTypeService.addHisAccMedPayType(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytype/updateHisAccMedPayTypePage", method = RequestMethod.GET)
	public String updateHisAccMedPayTypePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		HisAccMedPayType apt = hisAccMedPayTypeService.queryHisAccMedPayTypeByCode(mapVo);

		mode.addAttribute("pay_type_code" , apt.getPay_type_code());

		mode.addAttribute("pay_type_name", apt.getPay_type_name());

		mode.addAttribute("group_id",apt.getGroup_id());
		
		mode.addAttribute("hos_id",apt.getHos_id());
		
		mode.addAttribute("copy_code",apt.getCopy_code());

		return "hrp/acc/autovouch/his/hisaccmedpaytype/hisAccMedPayTypeUpdate";

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytype/updateHisAccMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHisAccMedPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedPayTypeService.updateHisAccMedPayType(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedpaytype/deleteHisAccMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHisAccMedPayType(@RequestParam(value="ParamVo") String paramVo,Model mode) throws Exception {

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

		String apt = hisAccMedPayTypeService.delHisAccMedPayType(listVo);


		return JSONObject.parseObject(apt);

	}

}
