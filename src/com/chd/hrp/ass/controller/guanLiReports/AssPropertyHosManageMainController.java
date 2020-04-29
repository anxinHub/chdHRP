package com.chd.hrp.ass.controller.guanLiReports;

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
import com.chd.hrp.ass.service.guanLiReports.AssPropertyHosMainService;
/**
 * 
 * @author 资产汇总   全院   (内部管理)
 *
 */
@Controller
public class AssPropertyHosManageMainController extends BaseController {
	

	
	private static Logger logger = Logger.getLogger(AssPropertyHosManageMainController.class);
	
	//引入Service服务
	@Resource(name = "assPropertyHosMainService")
	private final AssPropertyHosMainService assPropertyHosMainService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/assPropertyHosManageMainPage", method = RequestMethod.GET)
	public String assPropertyHosManageMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assPropertyHosManageMain";

	}

	/**
	 * @Description 
	 * 查询数据 050301 购置计划单
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssPropertyHosManageMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPropertyHosManageMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("is_stop", "0");
		
		/**
		 * 判断当前选中月份为一月份时  折旧年月应是 去年的12月份  
		 */
		 
		 
			if (mapVo.get("depre_month").toString().substring(4, 6).equals("01")){
				
				String year = mapVo.get("depre_month").toString().substring(0, 4);
				
				String month = mapVo.get("depre_month").toString().substring(4, 6);
				
				int depre_year = Integer.parseInt(year)-1; 
				 
				mapVo.put("depre_month",depre_year+"12");
				 
			}else {
				
			mapVo.put("depre_month", 0+Integer.parseInt((String) mapVo.get("depre_month"))-1);
		
			}
	 
		String assPlanDept = assPropertyHosMainService.queryAssPropertyHosMainManage(getPage(mapVo));

		return JSONObject.parseObject(assPlanDept);
		
	}
 
	
}
