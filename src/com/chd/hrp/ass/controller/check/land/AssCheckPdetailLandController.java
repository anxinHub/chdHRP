/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.check.land;

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
import com.chd.hrp.ass.service.check.land.AssCheckPdetailLandService;

/**
 * @Description: 051101 科室盘盈登记明细_土地
 * @Table: ASS_CHECK_DP_DETAIL_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssCheckPdetailLandController extends BaseController {

	private static Logger logger = Logger.getLogger(AssCheckPdetailLandController.class);

	// 引入Service服务
	@Resource(name = "assCheckPdetailLandService")
	private final AssCheckPdetailLandService assCheckPdetailLandService = null;

	/**
	 * @Description 添加数据 051101 科室盘盈登记明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/saveAssCheckPdetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckDetailLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		String assCheckDetailLandJson = assCheckPdetailLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckDetailLandJson);

	}

	/**
	 * @Description 删除数据 051101 科室盘盈登记明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/deleteAssCheckPdetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckDetailLand(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_plan_no", ids[3]);
			mapVo.put("check_no", ids[4]);
			mapVo.put("ass_id", ids[5]);
			mapVo.put("ass_no", ids[6]);

			listVo.add(mapVo);

		}

		String assCheckDetailLandJson = assCheckPdetailLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckDetailLandJson);

	}

	/**
	 * @Description 查询数据 051101 科室盘盈登记明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/queryAssCheckPdetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckPdetailLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assCheckDetailLand = assCheckPdetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckDetailLand);

	}

}
