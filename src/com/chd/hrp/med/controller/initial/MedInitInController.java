﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.initial;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.dict.AssDepreMethodDict;
import com.chd.hrp.ass.entity.dict.AssDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.entity.dict.AssUsageDict;
import com.chd.hrp.med.entity.MedFifoBalance;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.initial.MedInitInService;
import com.chd.hrp.sys.entity.FacDict;
import com.chd.hrp.sys.entity.SupDict;
import com.chd.hrp.sys.entity.Unit;

/**
 * 
 * @Description:  常备药品期初入库
 * @Table: MED_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedInitInController extends BaseController {

	private static Logger logger = Logger.getLogger(MedInitInController.class);

	// 引入Service服务
	@Resource(name = "medInitInService")
	private final MedInitInService medInitInService = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/mainPage", method = RequestMethod.GET)
	public String MedInitInMainPage(Model mode) throws Exception {
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08011", MyConfig.getSysPara("08011"));
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		mode.addAttribute("p08045", MyConfig.getSysPara("08045"));
		mode.addAttribute("p08047", MyConfig.getSysPara("08047"));

		return "hrp/med/initial/in/main";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/initial/in/addPage", method = RequestMethod.GET)
	public String medInitInAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));
		
		return "hrp/med/initial/in/add";
	}

	/**
	 * @Description 查询数据  常备药品期初入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/queryMedInitIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInitIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//只能查询bus_type_code = 1 的期初数据
		mapVo.put("bus_type_code", 1);
		//转换日期格式
		if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date"))){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date"))){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		String medMsg = medInitInService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medMsg);
	}
	
	/**
	 * @Description 查询数据  常备药品期初入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/queryMedInitInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInitInDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detail = medInitInService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/queryMedInitInById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInitInById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//转换日期格式
		if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date"))){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date"))){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}

		return medInitInService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  常备药品期初入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/addMedInitIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInitIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("maker") == null) {
			mapVo.put("maker", SessionManager.getUserId());
		}
		if(mapVo.get("in_date") == null || "".equals(mapVo.get("in_date"))){
			return JSONObject.parseObject("{\"error\":\"入库日期不能为空\",\"state\":\"false\"}");
		}
		//截取期间
		String[] date = mapVo.get("in_date").toString().split("-");
		mapVo.put("year", date[0]);
		mapVo.put("month", date[1]);
		mapVo.put("day", date[2]);  //用于生成单号
		//转换日期格式
		if(mapVo.get("in_date") != null && !"".equals(mapVo.get("in_date"))){
			mapVo.put("in_date", DateUtil.stringToDate(mapVo.get("in_date").toString(), "yyyy-MM-dd"));
		}

		String medJson;
		try {
			medJson = medInitInService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);

	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/initial/in/updatePage", method = RequestMethod.GET)
	public String medInitInUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> medInMain = medInitInService.queryByCode(mapVo);
		
		if(medInMain.get("in_date") != null && !"".equals(medInMain.get("in_date"))){
			medInMain.put("in_date", DateUtil.dateToString((Date)medInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(medInMain.get("check_date") != null && !"".equals(medInMain.get("check_date"))){
			medInMain.put("check_date", DateUtil.dateToString((Date)medInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(medInMain.get("conform_date") != null && !"".equals(medInMain.get("conform_date"))){
			medInMain.put("conform_date", DateUtil.dateToString((Date)medInMain.get("conform_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("medInMain", medInMain);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));
		
		mode.addAttribute("p08045", MyConfig.getSysPara("08045"));
		mode.addAttribute("p08047", MyConfig.getSysPara("08047"));
		
		
		//物流系统参数
		mode.addAttribute("paras", medCommonService.queryMedParas());
		return "hrp/med/initial/in/update";
	}

	/**
	 * @Description 更新数据  常备药品期初入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/updateMedInitIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInitIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("in_date") == null || "".equals(mapVo.get("in_date"))){
			return JSONObject.parseObject("{\"error\":\"入库日期不能为空\",\"state\":\"false\"}");
		}
		//截取期间
		String[] date = mapVo.get("in_date").toString().split("-");
		mapVo.put("year", date[0]);
		mapVo.put("month", date[1]);
		mapVo.put("day", date[2]);  //用于生成单号
		
		//转换日期格式
		if(mapVo.get("in_date") != null && !"".equals(mapVo.get("in_date"))){
			mapVo.put("in_date", DateUtil.stringToDate(mapVo.get("in_date").toString(), "yyyy-MM-dd"));
		}

		String medJson;
		try {
			medJson = medInitInService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 删除数据  常备药品期初入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/deleteMedInitIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInitIn(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			listVo.add(mapVo);
		}

		String medJson;
		try {
			medJson = medInitInService.deleteBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/confirmMedInitIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMedInitIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//状态：3 已确认
		mapVo.put("state", 1);
		//确认人：当前系统用户
		mapVo.put("confirmer", SessionManager.getUserId());
		//确认时间：当前系统时间
		//mapVo.put("confirm_date", new Date());

		String medJson;
		try {
			medJson = medInitInService.confirmMedInitIn(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 批量入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/confirmMedInitInBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMedInitInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("state", 3);
			mapVo.put("confirmer", SessionManager.getUserId());
			mapVo.put("confirm_date", ids[4]);
			listVo.add(mapVo);
		}

		String medJson;
		try {
			medJson = medInitInService.confirmMedInitInBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 下载导入模版  耐用品库房期初登记表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/initial/in/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate",
				"期初导入模板.xls");

		return null;
	}
	
	/**
	 * @Description 导入跳转页面 
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/in/medInitInImprotPage", method = RequestMethod.GET)
	public String medInitInImprotPage(Model mode) throws Exception {

		return "hrp/med/initial/in/import";

	}
	/**
	 * @Description 导入数据
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/initial/in/readMedInMainFiles", method = RequestMethod.POST)
	public String readMedInMainFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		//取出所有药品信息并转换成Map<药品编码, 药品信息>
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			//获取数据
			List<Map<String, Object>> invList = medInitInService.queryInvListForImport(mapVo);
			Map<String, Map<String, Object>> invMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> inv : invList){
				if(ChdJson.validateJSON(String.valueOf(inv.get("inv_id"))) 
						&& ChdJson.validateJSON(String.valueOf(inv.get("inv_no")))
						&& ChdJson.validateJSON(String.valueOf(inv.get("inv_code")))){
					
					invMap.put(inv.get("inv_code").toString(), inv);
				}
			}
			
			//取出所有药品信息并转换成Map<货位编码, 货位信息>
			List<Map<String, Object>> locationList = medInitInService.queryLocationListForImport(mapVo);
			Map<String, Map<String, Object>> locationMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> location : locationList){
				if(ChdJson.validateJSON(String.valueOf(location.get("location_id")))
						&& ChdJson.validateJSON(String.valueOf(location.get("location_name")))
						&& ChdJson.validateJSON(String.valueOf(location.get("location_code")))){
					
					locationMap.put(location.get("location_code").toString(), location);
				}
			}
			boolean flag = true;
			int len = 0;
			String[] data = null;
			//System.out.println("-------------------test111");
			//组装数据
	        for (String[] tmp : list) {
	        	if(flag){
	        		flag = false;
	        		len = tmp.length;
	        		data = new String[len];
	        		continue;
	        	}
	        	 
	        	for(int i = 0; i< tmp.length; i++){
	        		data[i] = tmp[i];
	        	}
	        	Map<String, Object> detailMap = new HashMap<String, Object>();
	        	if(data[0] != null && !"".equals(data[0])){
		        	if(invMap.get(data[0]) != null){
			            detailMap.put("inv_id", invMap.get(data[0]).get("inv_id").toString());//药品ID
			            detailMap.put("inv_no", invMap.get(data[0]).get("inv_no").toString());//药品变更号
			            detailMap.put("is_bar", invMap.get(data[0]).get("is_bar").toString());//是否条码
			            detailMap.put("is_quality", invMap.get(data[0]).get("is_quality").toString());//是否有效期
			            detailMap.put("is_disinfect", invMap.get(data[0]).get("is_disinfect"));//是否保质期
		        	}
		        	detailMap.put("inv_code", data[0]);
		        	detailMap.put("inv_name", data[1]);
		        	detailMap.put("inv_model", data[2]);
		        	detailMap.put("unit_name", data[3]);
		        	detailMap.put("amount", data[4]);
		        	detailMap.put("price", data[5]);
		        	detailMap.put("amount_money", data[6]);
		        	detailMap.put("batch_no", data[7]);
		        	detailMap.put("batch_sn", data[8]);
		        	detailMap.put("inva_date", data[9] == null ? null : data[9].replace("/", "-"));
		        	//detailMap.put("disinfect_date", data[10] == null ? null : data[10].replace("/", "-"));
		        	detailMap.put("sn", data[10]);
		        	detailMap.put("location_code", data[11]);
		        	
		        	if(locationMap.get(data[11]) != null){
		        		
			            detailMap.put("location_id", locationMap.get(data[11]).get("location_id").toString());//货位ID
			            detailMap.put("location_name", locationMap.get(data[11]).get("location_code").toString() + " " + locationMap.get(data[11]).get("location_name").toString());//货位显示内容
		        	}
		        	//detailMap.put("sell_price", data[13]);
		        	//detailMap.put("sell_money", data[14]);
		        	detailList.add(detailMap);
	        	}
            }
        }
        catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }

		response.getWriter().print(JSONObject.parseObject(ChdJson.toJson(detailList)));
		return null;
	}

	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/initial/in/storageInPrintSetPageQc", method = RequestMethod.GET)
	public String storageInPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/initial/in/queryMedInByPrintTemlateQc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("p_num") != null && mapVo.get("p_num").equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		} 
		String reJson=null;
		try {
			reJson=medInitInService.queryMedInByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
}
