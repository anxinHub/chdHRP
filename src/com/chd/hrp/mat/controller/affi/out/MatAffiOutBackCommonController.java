/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.affi.out;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.service.affi.out.MatAffiOutBackCommonService; 


/**
 * 
 * @Description:  代销退库
 * @Table: MAT_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAffiOutBackCommonController extends BaseController {

	private static Logger logger = Logger.getLogger(MatAffiOutBackCommonController.class);

	// 引入Service服务
	@Resource(name = "matAffiOutBackCommonService")
	private final MatAffiOutBackCommonService matAffiOutBackCommonService = null;
	
	/**
	 * @Description 代销出库--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/matAffiOutBackCommonMainPage", method = RequestMethod.GET)
	public String matAffiOutBackCommonMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04015", MyConfig.getSysPara("04015"));
		mode.addAttribute("p04024", MyConfig.getSysPara("04024"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("hos_name", SessionManager.getHosName());
		
		return "hrp/mat/affi/outback/matAffiOutBackCommonMain";
	}
	
	/**
	 * 代销出库--主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/queryMatAffiOutBackCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutBackCommon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		// 查询时 登录用户只能查询其有数据权限（部门权限、仓库权限）的 相关数据
		/*if (mapVo.get("dept_id") == "" || mapVo.get("dept_id") == null) {
			mapVo.put("dept_id", "00"); // 当 dept_id == 00 时 根据用户 部门权限 限定 申请科室
		}

		if (mapVo.get("store_id") == "" || mapVo.get("store_id") == null) {
			mapVo.put("store_id", "00"); // 当 store_id == 00 时 根据用户 仓库权限 限定 响应仓库
		}*/
		
		String matAffiOut = "";
		if(mapVo.get("show_detail").equals("1")){
			matAffiOut =  matAffiOutBackCommonService.queryDetails(getPage(mapVo));
		}else{
			matAffiOut =  matAffiOutBackCommonService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(matAffiOut);
	}
	
	/**
	 * 代销出库--添加页面  跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/matAffiOutBackCommonAddPage", method = RequestMethod.GET)
	public String matAffiOutBackCommonAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/affi/outback/matAffiOutBackCommonAdd";
	}
	
	/**
	 * @Description 
	 * 代销出库  添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/addMatAffiOutBackCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatAffiOutBackCommon(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		mapVo.put("is_dir", "0");
		
		String matJson;
		
		try {
			matJson = matAffiOutBackCommonService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		

	} 
 
	/**
	 * 代销出库--修改页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/matAffiOutBackCommonUpdatePage", method = RequestMethod.GET)
	public String matAffiOutBackCommonUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> matAffiOutMain = matAffiOutBackCommonService.queryByCode(mapVo);
		mapVo.put("bus_type_code", matAffiOutMain.get("bus_type_code"));
		Map<String, Object> matAffiOutId = matAffiOutBackCommonService.queryAffiOutBackIds(mapVo);
		
		if(matAffiOutMain.get("store_id") != null){
			mode.addAttribute("store_id", matAffiOutMain.get("store_id").toString()+","+matAffiOutMain.get("store_no").toString());
			mode.addAttribute("store_code", matAffiOutMain.get("store_code").toString()+" "+matAffiOutMain.get("store_name").toString());
		}
		if( matAffiOutMain.get("dept_id") != null){
			mode.addAttribute("dept_id", matAffiOutMain.get("dept_id").toString()+","+matAffiOutMain.get("dept_no").toString());
			mode.addAttribute("dept_code", matAffiOutMain.get("dept_code").toString()+" "+matAffiOutMain.get("dept_name").toString());
			
		}
		if( matAffiOutMain.get("dept_emp") != null){
			mode.addAttribute("dept_emp", matAffiOutMain.get("dept_emp").toString()+","+matAffiOutMain.get("emp_no").toString());
			mode.addAttribute("dept_emp_code", matAffiOutMain.get("dept_emp_code").toString()+" "+matAffiOutMain.get("dept_emp_name").toString());
		}
		
		mode.addAttribute("out_id", matAffiOutMain.get("out_id"));
		mode.addAttribute("out_no", matAffiOutMain.get("out_no"));
		mode.addAttribute("state", matAffiOutMain.get("state"));
		mode.addAttribute("group_id", matAffiOutMain.get("group_id"));
		mode.addAttribute("hos_id", matAffiOutMain.get("hos_id"));
		mode.addAttribute("copy_code", matAffiOutMain.get("copy_code"));
		mode.addAttribute("is_dir", matAffiOutMain.get("is_dir"));
		mode.addAttribute("out_date", matAffiOutMain.get("out_date"));
		mode.addAttribute("brief", matAffiOutMain.get("brief"));
		mode.addAttribute("out_idUp", matAffiOutId.get("out_idUp"));
		mode.addAttribute("out_idNext", matAffiOutId.get("out_idNext"));
		mode.addAttribute("use_code", matAffiOutMain.get("use_code"));
		mode.addAttribute("proj_id", matAffiOutMain.get("proj_id"));
		mode.addAttribute("bus_type_code", matAffiOutMain.get("bus_type_code"));
		
		Integer is_apply  = matAffiOutBackCommonService.queryMatAffiOutBackIsApply(mapVo);
		if(is_apply>0){
			mode.addAttribute("is_apply", "1");
		}else{
			mode.addAttribute("is_apply", "0");
		}
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04015", MyConfig.getSysPara("04015"));
		mode.addAttribute("p04024", MyConfig.getSysPara("04024"));
		mode.addAttribute("p04042", MyConfig.getSysPara("04042"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		return "hrp/mat/affi/outback/matAffiOutBackCommonUpdate";

	}
	
	@RequestMapping(value = "/hrp/mat/affi/outback/queryMatAffiOutBackDetaillById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutBackDetaillById(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String matOutDetail = matAffiOutBackCommonService.queryAffiOutBackDetailById(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);

	}
	
	@RequestMapping(value = "/hrp/mat/affi/outback/queryMatAffiOutBackDetaillByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutBackDetaillByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String matOutDetail = matAffiOutBackCommonService.queryAffiOutBackDetailByCode(getPage(mapVo));

		return JSONObject.parseObject(matOutDetail);

	}
	
	
	
	/**
	 * @Description 修改页面：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/queryMatAffiOutBackCommonDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutCommonDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matAffiOutBackCommonService.queryBackByCodeDetail(mapVo);

		return JSONObject.parseObject(matJson);

	}
	/**
	 * 代销出库--修改
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/updateMatAffiOutBackCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatAffiOutBackCommon(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson;
		try {
			matJson = matAffiOutBackCommonService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		/*if(matJson.contains("true")){
			MatAffiOutBackCommonService.updateMatAffiOutRela(mapVo);
		}*/
		return JSONObject.parseObject(matJson);

	}
	/**
	 * 审核 代销出库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/auditMatAffiOutBackCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditMatAffiOutBackCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiOutBackCommonService.auditMatAffiOutBackCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 取消审核 代销出库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/unAuditMatAffiOutBackCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unAuditMatAffiOutBackCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiOutBackCommonService.unAuditMatAffiOutBackCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 删除 代销出库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/deleteMatAffiOutBackCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> deleteMatAffiOutBackCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			//用于删除与申请单之前的对应关系
			mapVo.put("rela_type", "3");
			mapVo.put("rela_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiOutBackCommonService.deleteMatAffiOutBackCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 复制  出库单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/copyMatAffiOutBackCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> copyMatAffiOutBackCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
	
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("maker", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			mapVo.put("out_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiOutBackCommonService.copyMatAffiOutBackCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
 
		
	/**
	 * 出库确认  单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/outback/confirmMatAffiOutBackCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> confirmMatAffiOutBackCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String confirm_date=DateUtil.getSysDate();//获取服务器当前日期
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
//		Date date = new Date();
		String user_id = SessionManager.getUserId();
		String matJson;
		try {
			/*String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
			String year = dates[0];
			String month = dates[1];*/
			
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString())){
					confirm_date = ids[4];
				}
				String[] dates = confirm_date.split("-");
				String year = dates[0];
				String month = dates[1];
				
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("out_id", ids[3]);
				mapVo.put("confirmer", user_id);
				mapVo.put("state", 3);    //入库确认状态
				mapVo.put("confirm_date", confirm_date);
				mapVo.put("year", year);
				mapVo.put("month", month);
				
				listVo.add(mapVo);
			}
		
			matJson = matAffiOutBackCommonService.confirmMatAffiOutBackCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/outback/affiOutBackPrintSetPage", method = RequestMethod.GET)
	public String affiOutBackPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/affi/outback/queryMatAffiOutBackByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutBackByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{	
			mapVo.put("p_num", 0);	
		} 
		String reJson=null;
		try {
			reJson=matAffiOutBackCommonService.matAffiOutBackCommonService(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	 
}
