/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.storage.query;

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
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.serviceImpl.AccYearMonthServiceImpl;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.in.MedStorageInService;
import com.chd.hrp.med.service.storage.out.MedOutMainService;
import com.chd.hrp.med.service.storage.query.MedInDetailService;
import com.chd.hrp.med.service.storage.query.MedInvCheckService;
import com.chd.hrp.sup.service.SupDeliveryMainService;

/**
 * 
 * @Description: 药品验收统计
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedInvCheckController extends BaseController {

	//引入日志
	private static Logger logger = Logger.getLogger(MedInvCheckController.class);
	//引入Service服务
	@Resource(name = "medInvCheckService")
	private final MedInvCheckService medInvCheckService = null;
	/**
	 * @Description 药品验收明细查询 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medInvCheckDetailPage", method = RequestMethod.GET)
	public String medStockDetailPage(Model mode) throws Exception {
		return "hrp/med/storage/query/medInvCheckDetail";
	}
	/**
	 * @Description 药品验收明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedInvCheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvCheckDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		

		String queryMedInvCheckDetail = medInvCheckService.queryMedInvCheckDetail(getPage(mapVo));
		return JSONObject.parseObject(queryMedInvCheckDetail);

	}
	/**
	 * 查询入库单的药品类别
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/med/queryMedTypeByRead", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedTypeByRead(@RequestParam Map<String, Object> mapVo, Model mode) {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedInvCheckSelect = medInvCheckService.queryMedType(mapVo);
		return hrpMedInvCheckSelect;
	}
}
