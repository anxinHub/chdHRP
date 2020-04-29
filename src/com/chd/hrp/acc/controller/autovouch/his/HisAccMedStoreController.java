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
import com.chd.hrp.acc.entity.autovouch.HisAccMedStore;
import com.chd.hrp.acc.service.autovouch.his.HisAccMedStoreService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccMedStoreController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccMedStoreController.class);

	@Resource(name = "hisAccMedStoreService")
	private final HisAccMedStoreService hisAccMedStoreService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstore/hisAccMedStoreMainPage", method = RequestMethod.GET)
	public String hisAccMedStoreMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedstore/hisAccMedStoreMain";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstore/hisAccMedStoreAddPage", method = RequestMethod.GET)
	public String hisAccMedStoreAddPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/his/hisaccmedstore/hisAccMedStoreAdd";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstore/updateHisAccMedStorePage", method = RequestMethod.GET)
	public String updateHisAccMedStorePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		HisAccMedStore apt = hisAccMedStoreService.queryHisAccMedStoreByCode(mapVo);

		mode.addAttribute("store_code" , apt.getStore_code());

		mode.addAttribute("store_name", apt.getStore_name());
		
		mode.addAttribute("store_flag", apt.getStore_flag());

		mode.addAttribute("group_id",apt.getGroup_id());
		
		mode.addAttribute("hos_id",apt.getHos_id());
		
		mode.addAttribute("copy_code",apt.getCopy_code());

		return "hrp/acc/autovouch/his/hisaccmedstore/hisAccMedStoreUpdate";

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstore/queryHisAccMedStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisAccMedStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = hisAccMedStoreService.queryHisAccMedStore(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstore/addHisAccMedStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisAccMedStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedStoreService.addHisAccMedStore(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstore/updateHisAccMedStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHisAccMedStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = hisAccMedStoreService.updateHisAccMedStore(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/his/hisaccmedstore/deleteHisAccMedStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHisAccMedStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", res[0]);//实际实体类变量
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[2]);
            
            mapVo.put("store_code", res[3]);
            
            //mapVo.put("store_flag", res[4]);
            
            listVo.add(mapVo);
        }
		
		String apt = hisAccMedStoreService.delHisAccMedStore(listVo);

		return JSONObject.parseObject(apt);

	}

}
