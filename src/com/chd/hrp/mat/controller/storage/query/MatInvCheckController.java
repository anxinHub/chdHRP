/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.query;

import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.serviceImpl.AccYearMonthServiceImpl;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.mat.service.storage.out.MatOutMainService;
import com.chd.hrp.mat.service.storage.query.MatInDetailService;
import com.chd.hrp.mat.service.storage.query.MatInvCheckService;
import com.chd.hrp.sup.service.SupDeliveryMainService;

/**
 * 
 * @Description: 材料验收统计
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatInvCheckController extends BaseController { 

	//引入日志
	private static Logger logger = Logger.getLogger(MatInvCheckController.class);
	//引入Service服务
	@Resource(name = "matInvCheckService")
	private final MatInvCheckService matInvCheckService = null;
	
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	
	/**
	 * @Description 材料验收明细查询 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matInvCheckDetailPage", method = RequestMethod.GET)
	public String matStockDetailPage(Model mode) throws Exception {
		return "hrp/mat/storage/query/matInvCheckDetail";
	}
	/**
	 * @Description 材料验收明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatInvCheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvCheckDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		

		String queryMatInvCheckDetail = matInvCheckService.queryMatInvCheckDetail(getPage(mapVo));
		return JSONObject.parseObject(queryMatInvCheckDetail);

	}
	/**
	 * 查询入库单的物资类别
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/mat/queryMatTypeByRead", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypeByRead(@RequestParam Map<String, Object> mapVo, Model mode) {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatInvCheckSelect = matInvCheckService.queryMatType(mapVo);
		return hrpMatInvCheckSelect;
	}
	
	/**
	 * 查看入库单明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matInvCheckDetailByIdPage", method = RequestMethod.GET)
	public String matInvCheckDetailByIdPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String, Object> matInMain = matStorageInService.queryByCode(mapVo);
		
		if(matInMain.get("in_date") != null && !"".equals(matInMain.get("in_date"))){
			matInMain.put("in_date", DateUtil.dateToString((Date)matInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("check_date") != null && !"".equals(matInMain.get("check_date"))){
			matInMain.put("check_date", DateUtil.dateToString((Date)matInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("confirm_date") != null && !"".equals(matInMain.get("confirm_date"))){
			matInMain.put("confirm_date", DateUtil.dateToString((Date)matInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("bill_date") != null && !"".equals(matInMain.get("bill_date"))){
			matInMain.put("bill_date", DateUtil.dateToString((Date)matInMain.get("bill_date"), "yyyy-MM-dd"));
		}
		
		matInMain.put("bill_no", matInMain.get("bill_no"));
		
		mode.addAttribute("matInMain", matInMain);
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("in_id", mapVo.get("in_id"));
		mode.addAttribute("in_no", mapVo.get("in_no"));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/storage/query/matInvCheckDetailById";
	}
}
