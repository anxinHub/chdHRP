/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.util.*;
import javax.annotation.Resource;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostDriDetail;
import com.chd.hrp.cost.serviceImpl.CostDriDetailServiceImpl;

/**
* @Title. @Description.
* 科室成本明细数据表_平级分摊
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostDriDetailController extends BaseController{
	private static Logger logger = Logger.getLogger(CostDriDetailController.class);
	
	
	@Resource(name = "costDriDetailService")
	private final CostDriDetailServiceImpl costDriDetailService = null;
   
   
	/**
	*科室成本明细数据表_平级分摊<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costdridetail/costDriDetailMainPage", method = RequestMethod.GET)
	public String costDriDetailMainPage(Model mode) throws Exception {

		return "hrp/cost/costdridetail/costDriDetailMain";

	}
	/**
	*科室成本明细数据表_平级分摊<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costdridetail/costDriDetailAddPage", method = RequestMethod.GET)
	public String costDriDetailAddPage(Model mode) throws Exception {

		return "hrp/cost/costdridetail/costDriDetailAdd";

	}
	/**
	*科室成本明细数据表_平级分摊<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costdridetail/addCostDriDetail", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostDriDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costDriDetailJson = costDriDetailService.addCostDriDetail(mapVo);

		return JSONObject.parseObject(costDriDetailJson);
		
	}
	/**
	*科室成本明细数据表_平级分摊<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costdridetail/queryCostDriDetail", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostDriDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costDriDetail = costDriDetailService.queryCostDriDetail(getPage(mapVo));

		return JSONObject.parseObject(costDriDetail);
		
	}

}

