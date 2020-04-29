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
import com.chd.hrp.med.service.storage.query.MedSupInStoreService;
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
public class MedSupInStoreController extends BaseController {

	private static Logger logger = Logger.getLogger(MedInDetailController.class);
	

	//引入Service服务
	@Resource(name = "medSupInStoreService")
	private final MedSupInStoreService medSupInStoreService = null;
	
	
	

	/**
	 * @Description 供应商汇总主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medSupInStorePage", method = RequestMethod.GET)
	public String medSupInStorePage(Model mode) throws Exception {

		return "hrp/med/storage/query/medSupInStore";
	}
	
	
	/**
	 * @Description 供应商采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedSupInStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedSupInStore(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {


			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String medInSupCount = medSupInStoreService.queryMedSupInStore(mapVo);

		return JSONObject.parseObject(medInSupCount);

	}
	
	
}
