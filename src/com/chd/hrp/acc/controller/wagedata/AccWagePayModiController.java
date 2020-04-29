/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wagedata;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWagePayModiServiceImpl;

/**
* @Title. @Description.
* 工资数据
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWagePayModiController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWagePayModiController.class);
	
	
	@Resource(name = "accWagePayModiService")
	private final AccWagePayModiServiceImpl accWagePayModiService = null;
   
    
	/**
	 * 【工资管理-工资数据-工资调整】主页面
	 */
	@RequestMapping(value = "/hrp/acc/accwagepaymodi/accWagePayModiMainPage", method = RequestMethod.GET)
	public String accWagePayModiMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));

		return "hrp/acc/accwagepaymodi/accWagePayModiMain";
	}
	
	/**
	 * 【工资管理-工资数据-工资调整】：工资调整页面
	 */
	@RequestMapping(value = "/hrp/acc/accwagepaymodi/accWagePayModiAddPage", method = RequestMethod.GET)
	public String accWagePayModiAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("wage_item_code", mapVo.get("wage_item_code"));

		mode.addAttribute("wage_item_name", mapVo.get("wage_item_name"));

		mode.addAttribute("acc_year", mapVo.get("year_month"));

		mode.addAttribute("wage_code", mapVo.get("wage_code"));

		mode.addAttribute("wage_name", mapVo.get("wage_name"));

		mode.addAttribute("wage_year_month", MyConfig
				.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode())
				.getCurYearMonthWage());

		return "hrp/acc/accwagepaymodi/accWagePayModiAdd";

	}
	
	/**
	 * 【工资管理-工资数据-工资调整】：工资调整页面-确定保存
	 */
	@RequestMapping(value = "/hrp/acc/accwagepaymodi/addAccWagePayModi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccWagePayModi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("rate") == null) {

			mapVo.put("rate", "");

		}

		mapVo.put("user_id", SessionManager.getUserId());

		mapVo.put("create_date", sdf.format(new Date()));

		String accWagePayModiJson = accWagePayModiService.addAccWagePayModi(mapVo);

		return JSONObject.parseObject(accWagePayModiJson);

	}

	/**
	 * 【工资管理-工资数据-工资调整】：右侧表格查询
	 */
	@RequestMapping(value = "/hrp/acc/accwagepaymodi/queryAccWagePayModi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWagePayModi(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String accWagePayModi = accWagePayModiService.queryAccWagePayModi(getPage(mapVo));
		return JSONObject.parseObject(accWagePayModi);
	}
	
	/**
	*工资数据<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwagepaymodi/deleteAccWagePayModi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWagePayModi(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("t", id);//实际实体类变量
           
            listVo.add(mapVo);
        }
		
		String accWagePayModiJson = accWagePayModiService.deleteBatchAccWagePayModi(listVo);
	   
		return JSONObject.parseObject(accWagePayModiJson);
			
	}
	
	/**
	*工资数据<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwagepaymodi/accWagePayModiUpdatePage", method = RequestMethod.GET)
	
	public String accWagePayModiUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        //AccWagePayModi accWagePayModi = new AccWagePayModi();
		/*AccWagePayModi = AccWagePayModiService.queryAccWagePayModiByCode(mapVo);
		mode.addAttribute("para_code", AccWagePayModi.getPara_code());
		mode.addAttribute("para_name", AccWagePayModi.getPara_name());
		mode.addAttribute("group_id", AccWagePayModi.getGroup_id());
		mode.addAttribute("hos_id", AccWagePayModi.getHos_id());
		mode.addAttribute("copy_code", AccWagePayModi.getCopy_code());
		mode.addAttribute("mod_code", AccWagePayModi.getMod_code());
		mode.addAttribute("para_type", AccWagePayModi.getPara_type());
		mode.addAttribute("para_json", AccWagePayModi.getPara_json());
		mode.addAttribute("para_value", AccWagePayModi.getPara_value());
		mode.addAttribute("note", AccWagePayModi.getNote());
		mode.addAttribute("is_stop", AccWagePayModi.getIs_stop());*/
		return "hrp/acc/accwagepaymodi/accWagePayModiUpdate";
	}
	/**
	*工资数据<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwagepaymodi/updateAccWagePayModi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWagePayModi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWagePayModiJson = accWagePayModiService.updateAccWagePayModi(mapVo);
		
		return JSONObject.parseObject(accWagePayModiJson);
	}
	/**
	*工资数据<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accwagepaymodi/importAccWagePayModi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWagePayModi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accWagePayModiJson = accWagePayModiService.importAccWagePayModi(mapVo);
		
		return JSONObject.parseObject(accWagePayModiJson);
	}
	
}

