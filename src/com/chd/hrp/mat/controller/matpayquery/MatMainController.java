/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.matpayquery;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.matpayquery.MatMainService;
import com.chd.hrp.sup.service.SupDeliveryMainService;

/**
 * 
 * @Description:  常备材料入库
 * @Table: MAT_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatMainController extends BaseController {

	private static Logger logger = Logger.getLogger(MatMainController.class);

	// 引入Service服务
	@Resource(name = "matMainService")
	private final MatMainService matStorageInService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	// 引入Service服务
	@Resource(name = "supDeliveryMainService")
	private final SupDeliveryMainService supDeliveryMainService = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/matMainPage", method = RequestMethod.GET)
	public String matMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));

		return "hrp/mat/matpayquery/matMain";
	}
	
	/**
	 * @Description 查询数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/queryMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matIn = matStorageInService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
}
