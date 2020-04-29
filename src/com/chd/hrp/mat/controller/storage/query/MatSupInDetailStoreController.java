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
import com.chd.hrp.mat.service.storage.query.MatSupInDetailStoreService;

/**
 * 
 * @Description: 物流统计查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatSupInDetailStoreController extends BaseController {

	private static Logger logger = Logger.getLogger(MatInDetailController.class);
	

	//引入Service服务
	@Resource(name = "matSupInDetailStoreService")
	private final MatSupInDetailStoreService matSupInDetailStoreService = null;
		

	/**
	 * @Description 供应商汇总主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matSupInDetailStorePage", method = RequestMethod.GET)
	public String matSupInStorePage(Model mode) throws Exception {

		return "hrp/mat/storage/query/matSupInDetailStore";
	}
	
	
	/**
	 * @Description 供应商采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatSupInDetailStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSupInDetailStore(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
		
		String matInSupCount = matSupInDetailStoreService.queryMatSupInDetailStore(mapVo);

		return JSONObject.parseObject(matInSupCount);

	}
	
	
}
