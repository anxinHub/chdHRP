/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.query;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.mat.service.storage.out.MatOutMainService;
import com.chd.hrp.mat.service.storage.query.MatInSupBillSumService;
import com.chd.hrp.sup.service.SupDeliveryMainService;

/**
 * 
 * @Description: 物流统计查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatInSupBillSumController extends BaseController {

	private static Logger logger = Logger.getLogger(MatInSupBillSumController.class);
	

	//引入Service服务
	@Resource(name = "matInSupBillSumService")
	private final MatInSupBillSumService matInSupBillSumService = null;
	
	
	
	/**
	 * @Description 
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matInSupBillSumPage", method = RequestMethod.GET)
	public String matInSupBillSumPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/storage/query/matInSupBillSum";
	}
	/**
	 * 主表查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatInSupBillSum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInSupBillSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryStockDetail = matInSupBillSumService.queryMatInSupBillSum(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
		
		
}
