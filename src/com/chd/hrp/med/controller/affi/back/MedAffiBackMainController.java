/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.affi.back;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedInMain;
import com.chd.hrp.med.service.affi.back.MedAffiBackService;
import com.chd.hrp.med.service.affi.in.MedAffiInCommonService;
import com.chd.hrp.med.service.base.MedCommonService;

/**
 * 
 * @Description:  常备药品退货
 * @Table: MED_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */ 

@Controller
public class MedAffiBackMainController extends BaseController {

	private static Logger logger = Logger.getLogger(MedAffiBackMainController.class);

	// 引入Service服务
	@Resource(name = "medAffiBackService")
	private final MedAffiBackService medAffiBackService = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	// 引入Service服务
	@Resource(name = "medAffiInCommonService")
	private final MedAffiInCommonService medAffiInCommonService = null;
		
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/mainPage", method = RequestMethod.GET)
	public String MedAffiBackMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08023", MyConfig.getSysPara("08023"));

		return "hrp/med/affi/back/medAffiBackMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/back/medAffiBackAddPage", method = RequestMethod.GET)
	public String medAffiBackAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));

		return "hrp/med/affi/back/medAffiBackAdd";
	}

	/**
	 * @Description 查询数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/queryMedAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiBack(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		//转换日期格式
		if(mapVo.get("begin_in_date") != null && !"".equals(mapVo.get("begin_in_date"))){
			mapVo.put("begin_in_date", DateUtil.stringToDate(mapVo.get("begin_in_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_in_date") != null && !"".equals(mapVo.get("end_in_date"))){
			mapVo.put("end_in_date", DateUtil.stringToDate(mapVo.get("end_in_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("begin_confirm_date") != null && !"".equals(mapVo.get("begin_confirm_date"))){
			mapVo.put("begin_confirm_date", DateUtil.stringToDate(mapVo.get("begin_confirm_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_confirm_date") != null && !"".equals(mapVo.get("end_confirm_date"))){
			mapVo.put("end_confirm_date", DateUtil.stringToDate(mapVo.get("end_confirm_date").toString(), "yyyy-MM-dd"));
		}
		//只能查询退库
		mapVo.put("bus_type_code", 29);
		String medIn = medAffiBackService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 查询数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/queryMedAffiBackDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiBackDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		
		String detail = medAffiBackService.queryAffiDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/queryMedAffiBackById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiBackById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}

		return medAffiBackService.queryByCode(mapVo);
	}

	/**
	 * @Description 选择药品页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/medAffiBackInvListPage", method = RequestMethod.GET)
	public String medAffiBackInvListPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("store_id", paras.split(",")[0]);
		
		//String invList = medAffiBackService.queryMedStorageBackInvHold(getPage(mapVo));
		
		mode.addAttribute("store_id", paras);
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/affi/back/medAffiBackInvList";

	}

	/**
	 * @Description 添加数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/addMedAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedAffiBack(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medJson;
		try {
			medJson = medAffiBackService.add(mapVo);
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
	@RequestMapping(value = "/hrp/med/affi/back/medAffiBackUpdatePage", method = RequestMethod.GET)
	public String medAffiBackUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}

		Map<String, Object> medAffiInMain = medAffiBackService.queryByCode(mapVo);
		
		if(medAffiInMain.get("in_date") != null && !"".equals(medAffiInMain.get("in_date"))){
			medAffiInMain.put("in_date", DateUtil.dateToString((Date)medAffiInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(medAffiInMain.get("check_date") != null && !"".equals(medAffiInMain.get("check_date"))){
			medAffiInMain.put("check_date", DateUtil.dateToString((Date)medAffiInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(medAffiInMain.get("confirm_date") != null && !"".equals(medAffiInMain.get("confirm_date"))){
			medAffiInMain.put("confirm_date", DateUtil.dateToString((Date)medAffiInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		
		mapVo.put("bus_type_code", medAffiInMain.get("bus_type_code"));
		
		Map<String, Object> medAffiInId = medAffiInCommonService.queryMedAffiInIds(mapVo);
		mode.addAttribute("up", medAffiInId.get("in_idUp"));
		mode.addAttribute("next", medAffiInId.get("in_idNext"));
		
		mode.addAttribute("medAffiInMain", medAffiInMain);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08023", MyConfig.getSysPara("08023"));
		
		return "hrp/med/affi/back/medAffiBackUpdate";
	}

	/**
	 * @Description 更新数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/updateMedAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedAffiBack(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson;
		try {
			medJson = medAffiBackService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}

	/**
	 * @Description 删除数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/deleteMedAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedAffiBack(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			medJson = medAffiBackService.deleteBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}

	/**
	 * @Description 复制退库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/copyMedAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMedAffiBack(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("maker", SessionManager.getUserId());
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medAffiBackService.copyMedAffiBackBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}

	/**
	 * @Description 退库单审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/auditMedAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMedAffiBack(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//状态：2 审核
		mapVo.put("state", 2);
		//审核人：当前用户
		mapVo.put("checker", SessionManager.getUserId());
		//审核时间：当前系统时间
		mapVo.put("check_date", new Date());
		
		String medJson;
		try {
			medJson = medAffiBackService.auditMedAffiBack(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}

	/**
	 * @Description 退库单消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/unAuditMedAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMedAffiBack(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//状态：1 未审核
		mapVo.put("state", 1);
		//审核人：置空
		mapVo.put("checker", "");
		//审核时间：置空
		mapVo.put("check_date", "");
		
		String medJson;
		try {
			medJson = medAffiBackService.unAuditMedAffiBack(mapVo);
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
	@RequestMapping(value = "/hrp/med/affi/back/confirmMedAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMedAffiBack(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		mapVo.put("confirm_date", new Date());
		
		String medJson  = medAffiBackService.confirmMedAffiBack(mapVo);
		return JSONObject.parseObject(medJson);
		/*try {
			medJson = medAffiBackService.confirmMedAffiBack(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);*/
		
	}

	/**
	 * @Description 退库单批量审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/auditMedAffiBackBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMedAffiBackBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			//System.out.println(mapVo.get("in_id"));
			mapVo.put("state", 2);
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("check_date", date);
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medAffiBackService.auditMedAffiBackBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}

	/**
	 * @Description 退库单批量消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/unAuditMedAffiBackBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMedAffiBackBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("state", 1);
			mapVo.put("checker", "");
			mapVo.put("check_date", "");
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medAffiBackService.unAuditMedAffiBackBatch(listVo);
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
	@RequestMapping(value = "/hrp/med/affi/back/confirmMedAffiBackBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMedAffiBackBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("confirm_date", date);
			listVo.add(mapVo);
		}
		
		String medJson = medAffiBackService.confirmMedAffiBackBatch(listVo);
		
		return JSONObject.parseObject(medJson);
		/*String medJson;
		try {
			medJson = medAffiBackService.confirmMedAffiBackBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);*/
		
	}

	/**
	 * @Description 获取上一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/queryMedAffiInBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("type", "before");
		
		String medJson = medAffiBackService.queryMedAffiInBeforeOrNext(mapVo);

		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 获取下一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/queryMedAffiInNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInNextNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("type", "next");
		
		String medJson = medAffiBackService.queryMedAffiInBeforeOrNext(mapVo);

		return JSONObject.parseObject(medJson);
	}
	/**
	 * @Description 
	 * 入库单导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/back/medAffiBackInImpPage", method = RequestMethod.GET)
	public String medAffiBackInImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text").toString());
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text").toString());
		
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/back/medAffiBackInImp";
	}
	/**
	 * @Description 入库单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/queryMedAffiBackIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiBackIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		//转换日期格式
		if(mapVo.get("begin_in_date") != null && !"".equals(mapVo.get("begin_in_date"))){
			mapVo.put("begin_in_date", DateUtil.stringToDate(mapVo.get("begin_in_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_in_date") != null && !"".equals(mapVo.get("end_in_date"))){
			mapVo.put("end_in_date", DateUtil.stringToDate(mapVo.get("end_in_date").toString(), "yyyy-MM-dd"));
		}
		
		String medIn = medAffiBackService.queryAffiIn(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 入库单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/queryMedAffiBackInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiBackInDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		
		String medIn = medAffiBackService.queryAffiInDetail(mapVo);
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 入库单导入组装退货明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/queryMedAffiBackDetailByImp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiBackDetailByImp(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		String medIn = medAffiBackService.queryBackDetailByImp(mapVo);
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 导入跳转页面  常备药品入库
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/back/importPage", method = RequestMethod.GET)
	public String medAffiBackImportPage(Model mode) throws Exception {

		return "hrp/med/affi/back/Import";

	}

	/**
	 * @Description 下载导入模版  常备药品入库
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/affi/back/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate",
				"常备药品入库.xls");

		return null;
	}

	/**
	 * @Description 导入数据 常备药品入库@param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/affi/back/readFiles", method = RequestMethod.POST)
	public String readMedAffiBackFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<MedInMain> list_err = new ArrayList<MedInMain>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

		} catch (DataAccessException e) {

			MedInMain data_exc = new MedInMain();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 常备药品入库
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/affi/back/addBatchMedAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedAffiBack( @RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<MedInMain> list_err = new ArrayList<MedInMain>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		Iterator it = json.iterator();

		try {

		} catch (DataAccessException e) {
			MedInMain data_exc = new MedInMain();
			list_err.add(data_exc);
			return JSONObject
					.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));
		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/back/affiBackPrintSetPage", method = RequestMethod.GET)
	public String affiBackPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/med/affi/back/queryMedAffiBackByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiBackByPrintTsemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=medAffiInCommonService.queryMedAffiInByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	@RequestMapping(value="/hrp/med/affi/back/queryAllMedbySupId", method = RequestMethod.POST)
	@ResponseBody
	public JSON queryAllMedbySupId(@RequestParam Map<String, Object> mapVo, Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String AllMed =medAffiInCommonService.queryAllMedbySupId(mapVo);
		return JSONObject.parseObject(AllMed);
		
	}
}
