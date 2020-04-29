/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wagedata;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.service.wagedata.AccEmpWageItemService;

/**
* @Title. @Description.
* 工资数据
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccEmpWageItemController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccEmpWageItemController.class);
	
	@Resource(name = "accEmpWageItemService")
	private final AccEmpWageItemService accEmpWageItemService = null;
   
	/**
	 * 【工资管理-工资发放-职工工资条】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accempwageitem/accEmpWageItemMainPage", method = RequestMethod.GET)
	public String accEmpWageItemMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accempwageitem/accEmpWageItemMain";
	}
	
	/**
	*工资数据<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accempwageitem/accEmpWageItemAddPage", method = RequestMethod.GET)
	public String accEmpWageItemAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accempwageitem/accEmpWageItemAdd";

	}
	
	@RequestMapping(value = "/hrp/acc/accempwageitem/accEmpWageItemSchemeUpdate", method = RequestMethod.GET)
	public String accEmpWageItemSchemeUpdate(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accempwageitem/accEmpWageItemSchemeUpdate";

	}
	
	/**
	*工资数据<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accempwageitem/queryAccEmpWageItem", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccEmpWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("emp_code") != null){
			//用来转换大写
			mapVo.put("emp_code",mapVo.get("emp_code").toString().toUpperCase());
		}
		
        String accWagePay = accEmpWageItemService.queryAccEmpWageItem(getPage(mapVo));

		return JSONObject.parseObject(accWagePay);
		
	}
	
	/**
	*工资数据<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accempwageitem/accEmpWageItemUpdatePage", method = RequestMethod.GET)
	
	public String accEmpWageItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        //AccWagePay accWagePay = new AccWagePay();
		/*AccWagePay = AccWagePayService.queryAccWagePayByCode(mapVo);
		mode.addAttribute("para_code", AccWagePay.getPara_code());
		mode.addAttribute("para_name", AccWagePay.getPara_name());
		mode.addAttribute("group_id", AccWagePay.getGroup_id());
		mode.addAttribute("hos_id", AccWagePay.getHos_id());
		mode.addAttribute("copy_code", AccWagePay.getCopy_code());
		mode.addAttribute("mod_code", AccWagePay.getMod_code());
		mode.addAttribute("para_type", AccWagePay.getPara_type());
		mode.addAttribute("para_json", AccWagePay.getPara_json());
		mode.addAttribute("para_value", AccWagePay.getPara_value());
		mode.addAttribute("note", AccWagePay.getNote());
		mode.addAttribute("is_stop", AccWagePay.getIs_stop());*/
		return "hrp/acc/accempwageitem/accEmpWageItemUpdate";
	}
	
	@RequestMapping(value = "/hrp/acc/accempwageitem/accEmpWageSchemeUpdate", method = RequestMethod.GET)
	public String accEmpWageSchemeUpdate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        //AccWageScheme accWageScheme = new AccWageScheme();
		/*AccWageScheme = AccWageSchemeService.queryAccWageSchemeByCode(mapVo);
		mode.addAttribute("para_code", AccWageScheme.getPara_code());
		mode.addAttribute("para_name", AccWageScheme.getPara_name());
		mode.addAttribute("group_id", AccWageScheme.getGroup_id());
		mode.addAttribute("hos_id", AccWageScheme.getHos_id());
		mode.addAttribute("copy_code", AccWageScheme.getCopy_code());
		mode.addAttribute("mod_code", AccWageScheme.getMod_code());
		mode.addAttribute("para_type", AccWageScheme.getPara_type());
		mode.addAttribute("para_json", AccWageScheme.getPara_json());
		mode.addAttribute("para_value", AccWageScheme.getPara_value());
		mode.addAttribute("note", AccWageScheme.getNote());
		mode.addAttribute("is_stop", AccWageScheme.getIs_stop());*/
		return "hrp/acc/accempwageitem/accEmpWageSchemeUpdate";
	}
	/**
	*工资数据<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accempwageitem/updateEmpAccWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEmpAccWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWagePayJson = accEmpWageItemService.updateAccEmpWageItem(mapVo);
		
		return JSONObject.parseObject(accWagePayJson);
	}
	
	/**
	 * 工资条打印
	 * @throws IOException 
	 */
	@RequestMapping(value = "/hrp/acc/accempwageitem/gongZiTiaoPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> gongZiTiaoPrint(@RequestParam Map<String, Object> paraMap, Model mode,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		if (paraMap == null || paraMap.get("page_url") == null || paraMap.get("page_url").equals("")) {
			return JSONObject.parseObject("{\"error\":\"没有参数[page_url]\"}");
		}
		if (paraMap == null || paraMap.get("columnNum") == null || paraMap.get("columnNum").equals("")) {
			paraMap.put("columnNum", 0);
		}
		
		if (paraMap.get("group_id") == null) {
			paraMap.put("group_id", SessionManager.getGroupId());
		}
		if (paraMap.get("hos_id") == null) {
			paraMap.put("hos_id", SessionManager.getHosId());
		}
		if (paraMap.get("copy_code") == null) {
			paraMap.put("copy_code", SessionManager.getCopyCode());
		}
		if (paraMap.get("mod_code") == null) {
			paraMap.put("mod_code", SessionManager.getModCode());
		}
		if (paraMap.get("user_id") == null) {
			paraMap.put("user_id", SessionManager.getUserId());
		}
		
		ServletContext servletContext= request.getServletContext();
		String docPath = "/PageOffice/doc/grid/temp";
		String toFilePath = servletContext.getRealPath(docPath);
		String fromFilePath = servletContext.getRealPath("/PageOffice/base/form/test.xlsx");

		String uuid = UUIDLong.absStringUUID();
		String tempFileName = uuid + ".xlsx";// 2003版65536行,256列; 2007版及以后是1048576行,16384列
		toFilePath = toFilePath + "\\" + tempFileName;
		
		paraMap.put("toFilePath", toFilePath);
		paraMap.put("fromFilePath", fromFilePath);
		
		accEmpWageItemService.generatePayrollExcelData(paraMap);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("temp_file_name", docPath + "/" + tempFileName);
		resMap.put("group_id", SessionManager.getGroupId());
		resMap.put("hos_id", SessionManager.getHosId());
		resMap.put("hos_copy", SessionManager.getCopyCode());
		resMap.put("page_url", paraMap.get("page_url"));
		resMap.put("mod_code", SessionManager.getModCode());
		resMap.put("user_id", SessionManager.getUserId());
		return resMap;
	}
	
}

