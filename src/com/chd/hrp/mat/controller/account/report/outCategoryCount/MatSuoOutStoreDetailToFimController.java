package com.chd.hrp.mat.controller.account.report.outCategoryCount;

import java.text.ParseException;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportDeptCountToFimService;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatSuoOutStoreDetailService;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatSuoOutStoreDetailToFimService;

/**
 * @Description: 出库分类统计:科室统计
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatSuoOutStoreDetailToFimController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatSuoOutStoreDetailToFimController.class);
	
	@Resource(name = "matSuoOutStoreDetailToFimService")
	private final MatSuoOutStoreDetailToFimService  matSuoOutStoreDetailToFimService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/MatSuoOutStoreDetailToFimPage", method = RequestMethod.GET)
	public String matDeptCountPage(Model mode) throws Exception {

		return "hrp/mat/account/report/outCategoryCount/MatSuoOutStoreDetailToFim";
	}
	
	/**
	 * @Description 
	 * @param mode科室统计 查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/queryMatSuoOutStoreDetailToFim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatSuoOutStoreDetailToFim(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAccountReportDeptCountJson = matSuoOutStoreDetailToFimService.queryMatSuoOutStoreDetailToFim(getPage(mapVo));
		
		return JSONObject.parseObject(matAccountReportDeptCountJson);
	}
	
	@RequestMapping(value="/hrp/mat/account/report/outCategoryCount/queryOccurOutMatFimTypeDictForHead",method=RequestMethod.POST)
	@ResponseBody
	public String queryOccurOutMatFimTypeDictForHead(@RequestParam Map<String, Object> mapVo,Model mdl) throws ParseException{
		if(mapVo.get("group_id")==null){mapVo.put("group_id", SessionManager.getGroupId());}
		if(mapVo.get("hos_id")==null){mapVo.put("hos_id", SessionManager.getHosId());}
		if(mapVo.get("copy_code")==null){mapVo.put("copy_code", SessionManager.getCopyCode());}
		if(mapVo.get("user_id")==null){mapVo.put("user_id", SessionManager.getUserId());}
		
		return matSuoOutStoreDetailToFimService.queryOccurOutMatFimTypeDictForHead(mapVo);
	}
	
	
}
