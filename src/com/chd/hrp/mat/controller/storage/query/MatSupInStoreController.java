/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.query;

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
import com.chd.hrp.mat.service.storage.query.MatSupInStoreService;

/**
 * 
 * @Description: 物流统计查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatSupInStoreController extends BaseController {

	private static Logger logger = Logger.getLogger(MatInDetailController.class);
	

	//引入Service服务
	@Resource(name = "matSupInStoreService")
	private final MatSupInStoreService matSupInStoreService = null;
	
	
	

	/**
	 * @Description 供应商汇总主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matSupInStorePage", method = RequestMethod.GET)
	public String matSupInStorePage(Model mode) throws Exception {

		return "hrp/mat/storage/query/matSupInStore";
	}
	
	
	/**
	 * @Description 供应商采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatSupInStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSupInStore(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {


			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String matInSupCount = matSupInStoreService.queryMatSupInStore(mapVo);

		return JSONObject.parseObject(matInSupCount);

	}
	
	
	/**
	 * @Description 供应商月报汇总主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matSupInStoreMonPage", method = RequestMethod.GET)
	public String matSupInStoreMonPage(Model mode) throws Exception {

		return "hrp/mat/storage/query/matSupInStoreMon";
	}
	
	
	
	/**
	 * @Description 供应商明细主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matSupInStoreDetailPage", method = RequestMethod.GET)
	public String matSupInStoreDetailPage(Model mode) throws Exception {

		return "hrp/mat/storage/query/matSupInStoreDetail";
	}
	
	
	/**
	 * @Description 供应商明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatSupInStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSupInStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String retJson = matSupInStoreService.queryMatSupInStoreDetail(mapVo);

		return JSONObject.parseObject(retJson);
	}
}
