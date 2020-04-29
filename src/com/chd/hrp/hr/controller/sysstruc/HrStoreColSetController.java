/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.controller.sysstruc;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.sysstruc.HrStoreColSetService;


/**
 * 
 * @Description:
 * 
 * @Table: HR_STORE_COL_SET
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrstorecolset")
public class HrStoreColSetController extends BaseController {

	private static Logger logger = Logger.getLogger(HrStoreColSetController.class);

	// 引入Service服务
	@Resource(name = "hrStoreColSetService")
	private final HrStoreColSetService hrStoreColSetService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrStoreColSetMainPage", method = RequestMethod.GET)
	public String hrStoreColSetMainPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrstorecolset/hrStoreColSetMain";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrStoreColSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrStoreColSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        if(StringUtil.isBlank(mapVo.get("tab_code").toString())){
        	return "{\"error\":\"请赋数据权限!\"}";
        }else{
		return hrStoreColSetService.query(mapVo);
        }
	}

	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrStoreColSet", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrStoreColSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("store_type_code") == null || mapVo.get("tab_code") == null){
			return "{\"error\":\"请求参数不完整!\"}";
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			String reJson = hrStoreColSetService.saveHrStoreColSet(mapVo);
			return reJson;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	//批量同步页面跳转
	@RequestMapping(value = "/hrStoreColSetBatchAddPage", method = RequestMethod.GET)
	public String hrStoreColSetBatchAddPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrstorecolset/hrStoreColSetBatchAdd";
	}
	
	//批量同步
	@RequestMapping(value = "/addHrStoreColSetBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrStoreColSetBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("store_type_code") == null || mapVo.get("end_store_type_code") == null){
			return JSONObject.parseObject("{\"error\":\"请求参数不完整!\"}");
		}else if(mapVo.get("store_type_code").toString().equals(mapVo.get("end_store_type_code").toString())){
			JSONObject.parseObject("{\"error\":\"档案库和同步档案库不能相同!\"}");
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			String reJson = hrStoreColSetService.addHrStoreColSetBatch(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
