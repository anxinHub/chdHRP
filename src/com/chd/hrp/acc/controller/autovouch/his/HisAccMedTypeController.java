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
import com.chd.hrp.acc.entity.autovouch.HisAccMedType;
import com.chd.hrp.acc.entity.autovouch.HisAccMedType;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedTypeService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccMedTypeController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccMedTypeController.class);

	@Resource(name = "hisAccMedTypeService")
	private final HisAccMedTypeService hisAccMedTypeService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedtype/hisAccMedTypeMainPage", method = RequestMethod.GET)
	public String hisAccMedTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedtype/hisAccMedTypeMain";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedtype/hisAccMedTypeAddPage", method = RequestMethod.GET)
	public String hisAccMedTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedtype/hisAccMedTypeAdd";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedtype/updateHisAccMedTypePage", method = RequestMethod.GET)
	public String updateHisAccMedTypePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		HisAccMedType apt = hisAccMedTypeService.queryHisAccMedTypeByCode(mapVo);

		mode.addAttribute("med_type_code" , apt.getMed_type_code());

		mode.addAttribute("med_type_name", apt.getMed_type_name());
		
		mode.addAttribute("super_code", apt.getSuper_code());
		
		mode.addAttribute("type_level", apt.getType_level());
		
		mode.addAttribute("is_last", apt.getIs_last());

		mode.addAttribute("group_id",apt.getGroup_id());
		
		mode.addAttribute("hos_id",apt.getHos_id());
		
		mode.addAttribute("copy_code",apt.getCopy_code());

		return "hrp/acc/autovouch/his/hisaccmedtype/hisAccMedTypeUpdate";

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedtype/queryHisAccMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccMedType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = hisAccMedTypeService.queryHisAccMedType(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedtype/addHisAccMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisAccMedType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedTypeService.addHisAccMedType(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedtype/updateHisAccMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHisAccMedType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedTypeService.updateHisAccMedType(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedtype/deleteHisAccMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHisAccMedType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", res[0]);//实际实体类变量
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[2]);
            
            mapVo.put("med_type_code", res[3]);
            
            listVo.add(mapVo);
        }

		String apt = hisAccMedTypeService.delHisAccMedType(listVo);


		return JSONObject.parseObject(apt);

	}

}
