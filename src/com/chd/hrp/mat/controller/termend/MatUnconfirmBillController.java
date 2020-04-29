/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.mat.controller.termend;


import java.util.HashMap;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.termend.MatUnconfirmBillService;


/**
 * 
 * @Description:  常备材料入库
 * @Table: MAT_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatUnconfirmBillController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatUnconfirmBillController.class);

	// 引入Service服务
	@Resource(name = "matUnconfirmBillService")
	private final MatUnconfirmBillService matUnconfirmBillService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/termend/unconfirm/matUnconfirmBillPage", method = RequestMethod.GET)
	public String MatUnconfirmMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> map = matCommonService.queryMatCurYearMonth(mapVo);
		
		mode.addAttribute("mat_year", map.get("acc_year"));
		mode.addAttribute("mat_month", map.get("acc_month"));
		mode.addAttribute("mat_begin_date", map.get("begin_date"));
		mode.addAttribute("mat_end_date", map.get("end_date"));

		return "hrp/mat/termend/unconfirm/matUnconfirmBill";
	}
	
	@RequestMapping(value = "/hrp/mat/termend/unconfirm/queryMatUnconfirmBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatUnconfirmBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//转换日期格式
		if(mapVo.get("createDateB") != null && !"".equals(mapVo.get("createDateB"))){
			mapVo.put("createDateB", DateUtil.stringToDate(mapVo.get("createDateB").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("createDateE") != null && !"".equals(mapVo.get("createDateE"))){
			mapVo.put("createDateE", DateUtil.stringToDate(mapVo.get("createDateE").toString(), "yyyy-MM-dd"));
		}
		
		String matUnBill = matUnconfirmBillService.query(getPage(mapVo));
		logger.info(matUnBill);
		
		return JSONObject.parseObject(matUnBill);
	}
	
	@RequestMapping(value = "/hrp/mat/termend/unconfirm/updateUnconfirmBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUnconfirmBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matJson = matUnconfirmBillService.updateUnconfirmBill(mapVo);

		return JSONObject.parseObject(matJson);
	}

}
