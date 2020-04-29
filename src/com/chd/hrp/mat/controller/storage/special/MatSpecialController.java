/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.special;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.ChdToken;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatInMain;
import com.chd.hrp.mat.entity.MatInResource;
import com.chd.hrp.mat.service.affi.out.MatAffiOutCommonService;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.mat.service.storage.special.MatSpecialService;

/**
 * 
 * @Description:  专购品
 * @Table: MAT_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(MatSpecialController.class);

	// 引入Service服务
	@Resource(name = "matSpecialService")
	private final MatSpecialService matSpecialService = null;
	
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	@Resource(name = "matAffiOutCommonService")
	private final MatAffiOutCommonService matAffiOutCommonService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/matSpecialMainPage", method = RequestMethod.GET)
	public String MatCommonInMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		
		return "hrp/mat/storage/special/matSpecialMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/special/matSpecialAddPage", method = RequestMethod.GET)
	public String matCommonInAddPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04030", MyConfig.getSysPara("04030"));
		mode.addAttribute("p04043", MyConfig.getSysPara("04043"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("p04082", MyConfig.getSysPara("04082"));
		return "hrp/mat/storage/special/matSpecialAdd";
	}
	/**
	 * 代销使用导入页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/matAffiImpOldPage", method = RequestMethod.GET)
	public String matAffiImpOldPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/special/matAffiImpOld";
	}
	/**
	 * 代销使用导入页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/matAffiImpPage", method = RequestMethod.GET)
	public String matAffiImpPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/storage/special/matAffiImp";
	}
	
	/**
	 * @Description 复制专购品单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/copyMatSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMatSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("special_id", ids[3]);
			mapVo.put("maker", SessionManager.getUserId());
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matSpecialService.copyMatSpecialBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 查询数据  专购品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSpecial(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
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
		
		if(mapVo.get("bus_type_code") != null && !"".equals(mapVo.get("bus_type_code"))){
			if("47".equals(mapVo.get("bus_type_code")) || "48".equals(mapVo.get("bus_type_code")) ){
				mapVo.put("bus_type_flag", 1);
			}
			
			if("49".equals(mapVo.get("bus_type_code")) || "50".equals(mapVo.get("bus_type_code")) ){
				mapVo.put("bus_type_flag", 2);
			}
		}
		
		String matSpecial = "";
		if(mapVo.get("show_detail").equals("1")){

			matSpecial =  matSpecialService.queryDetails(getPage(mapVo));
		}else{
			matSpecial =  matSpecialService.queryMatSpecial(getPage(mapVo));
		}
		

		return JSONObject.parseObject(matSpecial);
	}
	
	
	
	
	/**
	 * @Description 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatSpecialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSpecialDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detail = matSpecialService.queryMatSpecialDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatCommonInById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonInById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		return matSpecialService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  专购品
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/addMatSpecial", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatSpecial(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		String matJson =null ;
		try {
			matJson = matSpecialService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/special/updatePage", method = RequestMethod.GET)
	public String matCommonInUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> matInMain = matSpecialService.queryMatSpecialMainUpdate(mapVo);
		
		Map<String,Object> dateMap = matSpecialService.queryMatInOutData(mapVo);
		
		mapVo.put("in_id", dateMap.get("in_id"));
		
		MatInResource inResource = matStorageInService.queryMatInResource(mapVo);
		
		if(matInMain.get("make_date") != null && !"".equals(matInMain.get("make_date"))){
			matInMain.put("make_date", DateUtil.dateToString((Date)matInMain.get("make_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("check_date") != null && !"".equals(matInMain.get("check_date"))){
			matInMain.put("check_date", DateUtil.dateToString((Date)matInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("confirm_date") != null && !"".equals(matInMain.get("confirm_date"))){
			matInMain.put("confirm_date", DateUtil.dateToString((Date)matInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("bill_date") != null && !"".equals(matInMain.get("bill_date"))){
			matInMain.put("bill_date", DateUtil.dateToString((Date)matInMain.get("bill_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("money_sum", inResource.getSource_money());
		mode.addAttribute("matInMain", matInMain);
		mode.addAttribute("paras", matCommonService.queryMatParas());

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04030", MyConfig.getSysPara("04030"));
		mode.addAttribute("p04043", MyConfig.getSysPara("04043"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("p04076", MyConfig.getSysPara("04076"));
		mode.addAttribute("p04082", MyConfig.getSysPara("04082"));
		return "hrp/mat/storage/special/matSpecialUpdate";
	}
	
	/**
	 * 上一张
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatSpecialBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSpecialBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("flag", "-1");//-1 标识  上一张
		
		String dateMap = matSpecialService.queryMatSpecialBeforeOrNextNo(mapVo);
		
		return JSONObject.parseObject(dateMap);
	}
	/**
	 * 下一张
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatSpecialNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSpecialNextNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("flag", "1");//-1 标识  上一张
		
		String dateMap = matSpecialService.queryMatSpecialBeforeOrNextNo(mapVo);
		
		return JSONObject.parseObject(dateMap);
	}
	/**
	 * @Description 更新数据  专购品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/updateMatSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatSpecial(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		/*DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
			mapVo.put("make_date",df.parse(mapVo.get("make_date").toString()));
		}*/

		//处理发票日期
		if(mapVo.get("bill_date") != null && !"".equals(mapVo.get("bill_date"))){
			mapVo.put("bill_date", DateUtil.stringToDate(mapVo.get("bill_date").toString(), "yyyy-MM-dd"));
		}
		
		//入库编制时间
		mapVo.put("in_date",mapVo.get("make_date"));
		//即入即出  出库编制时间
		mapVo.put("out_date",mapVo.get("make_date"));
		String matJson =null ;
		try {
			matJson = matSpecialService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 删除数据  专购品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/deleteMatSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("special_id", ids[3]);
			mapVo.put("special_no", ids[4]);
			Map<String,Object> data = matSpecialService.queryMatInOutData(mapVo);
			mapVo.put("in_id", data.get("in_id"));
			mapVo.put("in_no", data.get("in_no"));
			mapVo.put("out_id", data.get("out_id"));
			mapVo.put("out_no", data.get("out_no"));
			listVo.add(mapVo);
		}
		String matInvJson = null ;
		try {
			matInvJson = matSpecialService.deleteBatch(listVo);
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matInvJson);
	}

	/**
	 * @Description 专购品  冲账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/offsetMatSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> offsetMatSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("special_id", ids[3]);
			mapVo.put("special_no", ids[4]);
			mapVo.put("in_id", ids[5]);
			mapVo.put("in_no", ids[6]);
			mapVo.put("out_id", ids[7]);
			mapVo.put("out_no", ids[8]);
			mapVo.put("maker", SessionManager.getUserId());
			listVo.add(mapVo);
		}
		String matInvJson = null ;
		try {
			matInvJson = matSpecialService.offsetMatSpecialBatch(listVo);
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}
		return JSONObject.parseObject(matInvJson);
	}

	/**
	 * 专购品出入库单审核、消审
	 * @Description 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/updateStateSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStateSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("special_id", ids[3]);
			mapVo.put("special_no", ids[4]);
			Map<String,Object> data = matSpecialService.queryMatInOutData(mapVo);
			mapVo.put("in_id", data.get("in_id"));
			mapVo.put("in_no", data.get("in_no"));
			mapVo.put("out_id", data.get("out_id"));
			mapVo.put("out_no", data.get("out_no"));
			mapVo.put("state", ids[6]);
			if(Integer.parseInt(ids[5].toString()) == 1){
				mapVo.put("checker", SessionManager.getUserId());
				mapVo.put("check_date", date);
			}
			if(Integer.parseInt(ids[5].toString()) == 2){
				mapVo.put("checker", "");
				mapVo.put("check_date", "");
			}
			listVo.add(mapVo);
		}
		// 专购品  审核、消审
		String specialJson = null ;
		try {
			specialJson = matSpecialService.updateState(listVo);
		} catch (Exception e) {
			specialJson = e.getMessage();
		}
		return JSONObject.parseObject(specialJson);
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/confirmMatSpecialOld", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatSpecialOld(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String matInvJson = null ;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("special_id", ids[3]);
				mapVo.put("special_no", ids[4]);
				mapVo.put("year", ids[5]);
				mapVo.put("month", ids[6]);
				mapVo.put("store_id", ids[7]);
				
				Map<String,Object> data = matSpecialService.queryMatInOutData(mapVo);
				mapVo.put("in_id", data.get("in_id"));
				mapVo.put("in_no", data.get("in_no"));
				mapVo.put("out_id", data.get("out_id"));
				mapVo.put("out_no", data.get("out_no"));
				
				//根据 专购品主表Id 查询 专购品明细
				List<Map<String,Object>> detailList = matSpecialService.querySpecialDetail(mapVo);
				for(Map<String,Object> item : detailList){
					Map<String, Object> invMap = new HashMap<String, Object>();
					invMap.putAll(mapVo);
					invMap.put("inv_id", item.get("inv_id"));
					invMap.put("batch_sn", item.get("batch_sn"));
					invMap.put("batch_no", item.get("batch_no"));
					invMap.put("bar_code", item.get("bar_code"));
					invMap.put("price", item.get("price"));
					invMap.put("sale_price", item.get("sale_price"));
					invMap.put("in_amount", item.get("amount"));
					invMap.put("in_money", item.get("amount_money"));
					invMap.put("in_sale_money", "0");
					invMap.put("out_amount", item.get("amount"));
					invMap.put("out_money", item.get("amount_money"));
					invMap.put("out_sale_money", "0");
					invMap.put("inva_date", item.get("inva_date"));
					invMap.put("disinfect_date", item.get("disinfect_date"));
					invMap.put("location_id", item.get("location_id"));
					invMap.put("left_amount", "0");
					invMap.put("left_money", "0");
					invMap.put("left_sale_money", "0");
					invMap.put("remove_zero_error", "0");
					invMap.put("sale_zero_error", "0");
					invMap.put("cur_amount", "0");
					invMap.put("cur_money", "0");
					
					invMap.put("state", 3);
					invMap.put("confirmer", SessionManager.getUserId());
					invMap.put("confirm_date", date);
					listVo.add(invMap);
				}
			}
		
			matInvJson = matSpecialService.confirmMatSpecialBatch(listVo);
		} catch (Exception e) {
			
			matInvJson = e.getMessage();
		}
		return JSONObject.parseObject(matInvJson);
	}

	/**
	 * @Description 专购品确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/confirmMatSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		//Date date = new Date();
		
		String user_id = SessionManager.getUserId();
		String matInvJson = null;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("special_id", ids[3]);
				mapVo.put("special_no", ids[4]);
				
				Map<String,Object> data = matSpecialService.queryMatInOutData(mapVo);
				mapVo.put("in_id", data.get("in_id"));
				mapVo.put("in_no", data.get("in_no"));
				mapVo.put("out_id", data.get("out_id"));
				mapVo.put("out_no", data.get("out_no"));
				
				mapVo.put("state", "3");
				mapVo.put("confirmer", user_id);
				mapVo.put("confirm_date", ids[5].toString());
				String[] dates = ids[5].split("-");
				mapVo.put("year", dates[0]);
				mapVo.put("month", dates[1]);
				listVo.add(mapVo);
			}
		
			matInvJson = matSpecialService.confirmMatSpecialBatch(listVo);
		} catch (Exception e) {
			
			matInvJson = e.getMessage();
		}
		return JSONObject.parseObject(matInvJson);
	}
	
	/**
	 * 代销使用生成
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/affiDateOld", method = RequestMethod.POST)
	@ResponseBody
	public String affiDateOld(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String affiStr = "";
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("out_id", ids[3]);
			mapVo.put("year", ids[4]);
			mapVo.put("month", ids[5]);
			mapVo.put("bus_type_code", "01");
			mapVo.put("store_id", ids[7]);
			mapVo.put("store_no", ids[8]);
			mapVo.put("brief", "代销使用生成");
			mapVo.put("dept_id", ids[10]);
			mapVo.put("dept_no", ids[11]);
			mapVo.put("dept_emp", ids[12]);
			mapVo.put("store_id", ids[13]);
			mapVo.put("out_no", ids[14]);
			mapVo.put("use_state", "1");
			String  detail = matSpecialService.queryMatAffiDetail(mapVo);
			List<Map<String,Object>> list = (List<Map<String, Object>>) JSONObject.parseObject(detail).get("Rows");
			
			String info = "" ;
			Map<String,String> supMap =new HashMap<String,String>();
			
			for(Map<String,Object> mapKey:list){
				if(mapKey.get("sup_id") != null ){
					supMap.put(String.valueOf(mapKey.get("sup_id")), String.valueOf(mapKey.get("sup_id")));
				}else{
					info += mapKey.get("inv_code") + "," ;
				}
			}
			if( info != "" ){
				return "{\"error\":\"代销使用生成失败!【出库单:"+mapVo.get("out_no")+"材料:"+info.substring(0,info.length()-1)+"】没有供应商.不能使用代销生成！\"}";
			}else{
				for(String key :supMap.keySet()){
					List<Map<String,Object>> detailData = new ArrayList<Map<String,Object>>();
					for(Map<String,Object> t : list){
						if(key.equals(String.valueOf(t.get("sup_id")))){
							detailData.add(t);
						}
					}

					mapVo.put("sup_id", key);
					mapVo.put("sup_no", detailData.get(0).get("sup_no"));
					mapVo.put("detailData", String.valueOf(detailData));
					mapVo.put("bus_type", "47");
					mapVo.put("special_no", "自动生成");
					
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					
					mapVo.put("make_date",df.format(new Date()));
					mapVo.put("maker", SessionManager.getUserId());
					
					try{
						affiStr = matSpecialService.add(mapVo);
						mapVo.put("affi_out_id", ids[3]);
						matSpecialService.updateAffiOutState(mapVo);
					}catch(Exception e){
						affiStr = e.getMessage();
					}
				}
			}
		}
		return affiStr;
	}
	
	/**
	 * 代销使用生成
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/affiDate", method = RequestMethod.POST)
	@ResponseBody
	public String affiDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String affiStr = "";
		
		try{
			affiStr = matSpecialService.addByAffiOut(mapVo);
		}catch(Exception e){
			affiStr = e.getMessage();
		}
		return affiStr;
	}
	
	//关闭材料
	@RequestMapping(value = "/hrp/mat/storage/special/addAffiRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAffiRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("affi_out_id", ids[3]);
			mapVo.put("affi_detail_id", ids[4]);
			mapVo.put("special_amount", ids[5]);
			mapVo.put("out_amount", ids[5]);
			mapVo.put("special_id", "0");
			mapVo.put("sp_detail_id", "0");
			
			listVo.add(mapVo);
		}
		// 专购品  审核、消审
		String specialJson = null ;
		try {
			specialJson = matSpecialService.addAffiRela(listVo);
		} catch (Exception e) {
			specialJson = e.getMessage();
		}
		return JSONObject.parseObject(specialJson);
	}

	/**
	 * @Description 
	 * 订单导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/special/orderImpPage", method = RequestMethod.GET)
	public String matCommonInorderImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("paras", matCommonService.queryMatParas());
		return "hrp/mat/storage/special/orderImp";
	}
	/**
	 * @Description 订单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if(mapVo.get("begin_order_date") != null && !"".equals(mapVo.get("begin_order_date"))){
			mapVo.put("begin_order_date", DateUtil.stringToDate(mapVo.get("begin_order_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_order_date") != null && !"".equals(mapVo.get("end_order_date"))){
			mapVo.put("end_order_date", DateUtil.stringToDate(mapVo.get("end_order_date").toString(), "yyyy-MM-dd"));
		}
		
		String matIn = matSpecialService.queryOrder(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 订单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOrderDetail(@RequestParam(value="ParamVo") String paramVo, Model mode)throws Exception {
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
		
		String matIn = matSpecialService.queryOrderDetail((listVo));
		
		return JSONObject.parseObject(matIn);
	}

	/**
	 * @Description 代销出库单 主数据  查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryAffiOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAffiOut(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matIn = matSpecialService.queryAffiOut(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 代销出库单 明细数据  查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatAffiDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matIn = matSpecialService.queryMatAffiDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}


	/**
	 * @Description 
	 * 协议导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/special/protocolImpPage", method = RequestMethod.GET)
	public String matCommonInprotocolImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("protocol_id", mapVo.get("protocol_id"));
		mode.addAttribute("protocol_text", mapVo.get("protocol_text"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/special/protocolImp";
	}
	
	/**
	 * 专购品供应商导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/sup_ImpPage", method = RequestMethod.GET)
	public String matCommonInsupImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		
		return "hrp/mat/storage/special/specialSupImp";
	}
	
	/**
	 * 请求后台解析
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/supImportToJson", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> matCommonInsupImp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		Map<String,Object> entityMap = new HashMap<String, Object>();
		
		if (entityMap.get("group_id") == null) {
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		if (entityMap.get("hos_id") == null) {
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		if (entityMap.get("copy_code") == null) {
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		entityMap.put("by_sup_inv", MyConfig.getSysPara("04021"));
		entityMap.put("sup_id", mapVo.get("sup_id"));
		entityMap.put("store_id", mapVo.get("store_id"));
		
		String impJson = matSpecialService.importMatCommonInsup(mapVo,entityMap);
		System.out.println("=================================" + impJson);
		return JSONObject.parseObject(impJson);
		
	}
	
	
	/**
	 * @Description 配套查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatCommonInProtocol", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonInProtocol(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matIn = matSpecialService.queryProtocol(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 导入跳转页面  专购品
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/importPage", method = RequestMethod.GET)
	public String matCommonInImportPage(Model mode) throws Exception {

		return "hrp/mat/storage/special/Import";

	}

	/**
	 * @Description 下载导入模版  专购品
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/storage/special/downTemplateMatCommonIn", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate","专购品导入.xlsx");

		return null;
	}

	/**
	 * @Description 导入数据 专购品入库@param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/readFiles", method = RequestMethod.POST)
	public String readMatCommonInFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<MatInMain> list_err = new ArrayList<MatInMain>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

		} catch (DataAccessException e) {

			MatInMain data_exc = new MatInMain();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 专购品
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/addBatchMatCommonIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMatCommonIn(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<MatInMain> list_err = new ArrayList<MatInMain>();

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
			MatInMain data_exc = new MatInMain();
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
	@RequestMapping(value = "/hrp/mat/storage/special/matSpecialPrintSetPage", method = RequestMethod.GET)
	public String matSpecialPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatSpecialByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSpecialByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=matSpecialService.queryMatSpecialByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * @Description 代销专购品生成查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatAffiSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiSpecial(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matSpecial = matSpecialService.queryMatAffiSpecial(getPage(mapVo));
		
		return JSONObject.parseObject(matSpecial);
	}

	/**
	 * @Description 
	 * 更新发票号页面跳转 
	 * @param mapVo
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/special/updateSpecialInvoicePage", method = RequestMethod.GET)
	public String updateSpecialInvoicePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("special_id", mapVo.get("special_id"));
		mode.addAttribute("in_id", mapVo.get("in_id"));
		
		return "hrp/mat/storage/special/updateInvoice";
	}
	
	/**
	 * @Description 发票补登
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/updateMatSpecialInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatSpecialInvoice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if(mapVo.get("bill_date") != null && !"".equals(mapVo.get("bill_date"))){
			mapVo.put("bill_date", DateUtil.stringToDate(mapVo.get("bill_date").toString(), "yyyy-MM-dd"));
		}
		
		String matSpecial = "";
		try {
			matSpecial = matSpecialService.updateMatSpecialInvoice(mapVo);
		} catch (Exception e) {
			
			matSpecial = e.getMessage();
		}
		
		return JSONObject.parseObject(matSpecial);
	}
	
	
	/**
	 * 批量添加材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/matSpecailInvBatchImpPage", method = RequestMethod.GET)
	public String matSpecailInvBatchImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		
		return "hrp/mat/storage/special/matSpecailInvBatchImp";

	}
	
	/**
	 * 批量添加查询材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatSpecailInvBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSpecailInvBatch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matIn = matSpecialService.queryMatSpecailInvBatch(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * 批量选择材料生成入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/special/queryMatSpecailInvDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSpecailInvDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = matSpecialService.queryMatSpecailInvDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
}
