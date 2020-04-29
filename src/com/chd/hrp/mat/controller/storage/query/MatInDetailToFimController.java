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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.serviceImpl.AccYearMonthServiceImpl;
import com.chd.hrp.mat.service.storage.query.MatInDetailService;
import com.chd.hrp.mat.service.storage.query.MatInDetailToFimService;

/**
 * 
 * @Description: 物流统计查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatInDetailToFimController extends BaseController {

	private static Logger logger = Logger.getLogger(MatInDetailToFimController.class);
	

	//引入Service服务
	@Resource(name = "matInDetailToFimService")
	private final MatInDetailToFimService matInDetailToFimService = null;
	

	

	/**
	 * @Description 供应商汇总主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matInSupCountToFim", method = RequestMethod.GET)
	public String matInSupCountToFim(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/storage/query/matInSupCountToFim";
	}
	
	
	/**
	 * @Description 供应商采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatInSupCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInSupCount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matInSupCount = matInDetailToFimService.queryMatInSupCount(mapVo);

		return JSONObject.parseObject(matInSupCount);

	}
	
	// 供应商财务分类动态表头-只显示发生业务的财务分类
	@RequestMapping(value = "/hrp/mat/storage/query/queryOccurMatFimTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryOccurMatFimTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		if (mapVo.get("user_id") == null) {mapVo.put("user_id", SessionManager.getUserId());}
		
		String str = matInDetailToFimService.queryOccurMatFimTypeDict(mapVo); 
		return str;
	}
	
 
 
	
	
}
