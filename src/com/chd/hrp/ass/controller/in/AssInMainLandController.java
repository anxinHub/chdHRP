/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.in;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.in.AssInMainLand;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.in.AssInDetailLandService;
import com.chd.hrp.ass.service.in.AssInMainLandService;

/**
 * 
 * @Description: 资产入库主表(专用设备)
 * @Table: ASS_IN_MAIN_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssInMainLandController extends BaseController {

	private static Logger logger = Logger.getLogger(AssInMainLandController.class);

	// 引入Service服务
	@Resource(name = "assInMainLandService")
	private final AssInMainLandService assInMainLandService = null;
	@Resource(name = "assInDetailLandService")
	private final AssInDetailLandService assInDetailLandService = null;
	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/assInMainLandMainPage", method = RequestMethod.GET)
	public String assInMainLandMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05064",MyConfig.getSysPara("05064"));
		
		return "hrp/ass/assland/assin/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/assInMainLandAddPage", method = RequestMethod.GET)
	public String assInMainLandAddPage(Model mode) throws Exception {
		mode.addAttribute("ass_nature", "06");
		return "hrp/ass/asscard/assInCardAdd";

	}

	/**
	 * @Description 添加数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/addAssInMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssInMainLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		String assInMainLandJson = assInMainLandService.add(mapVo);

		return JSONObject.parseObject(assInMainLandJson);

	}

	/**
	 * @Description 更新跳转页面 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/assInMainLandUpdatePage", method = RequestMethod.GET)
	public String assInMainLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		AssInMainLand assInMainLand = new AssInMainLand();

		assInMainLand = assInMainLandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInMainLand.getGroup_id());
		mode.addAttribute("hos_id", assInMainLand.getHos_id());
		mode.addAttribute("copy_code", assInMainLand.getCopy_code());
		mode.addAttribute("ass_in_no", assInMainLand.getAss_in_no());
		mode.addAttribute("bus_type_code", assInMainLand.getBus_type_code());
		mode.addAttribute("ven_id", assInMainLand.getVen_id());
		mode.addAttribute("ven_no", assInMainLand.getVen_no());
		mode.addAttribute("in_money", assInMainLand.getIn_money());
		mode.addAttribute("note", assInMainLand.getNote());
		mode.addAttribute("create_emp", assInMainLand.getCreate_emp());
		mode.addAttribute("create_date", assInMainLand.getCreate_date());
		mode.addAttribute("in_date", assInMainLand.getIn_date());
		mode.addAttribute("confirm_emp", assInMainLand.getConfirm_emp());
		mode.addAttribute("state", assInMainLand.getState());

		return "hrp/ass/assland/assin/update";
	}

	/**
	 * @Description 更新数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/updateAssInMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssInMainLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String assInMainLandJson = assInMainLandService.update(mapVo);

		return JSONObject.parseObject(assInMainLandJson);
	}

	/**
	 * @Description 更新数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/addOrUpdateAssInMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssInMainLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assInMainLandJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}

			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}

			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}

			assInMainLandJson = assInMainLandService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(assInMainLandJson);
	}

	/**
	 * @Description 删除数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/deleteAssInMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInMainLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);

			AssInMainLand assInMainLand = assInMainLandService.queryByCode(mapVo);

			if (assInMainLand.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);
		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assInMainLandJson = assInMainLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assInMainLandJson);

	}

	/**
	 * @Description 查询数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/queryAssInMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		
		String assInMainLand = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assInMainLand = assInMainLandService.query(getPage(mapVo));
	
		}else{
		
			assInMainLand = assInMainLandService.queryDetails(getPage(mapVo));
			
		}

		return JSONObject.parseObject(assInMainLand);

	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/updateConfirmInMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";
		for (String vdata : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = vdata.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);

			AssInMainLand assInMainLand = assInMainLandService.queryByCode(mapVo);
			if (DateUtil.compareDate(assInMainLand.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<Map<String, Object>> list = assInDetailLandService.queryByInit(mapVo);// 资产明细数据

			if (list.size() == 0) {
				return JSONObject.parseObject("{\"warn\":\"单据不存在资产,不能入库! \"}");
			}
			for (Map<String, Object> temp : list) {

				Map<String, Object> mapExists = new HashMap<String, Object>();

				mapExists.put("group_id", temp.get("group_id"));

				mapExists.put("hos_id", temp.get("hos_id"));

				mapExists.put("copy_code", temp.get("copy_code"));

				mapExists.put("ass_in_no", temp.get("ass_in_no"));

				mapExists.put("ass_id", temp.get("ass_id"));

				mapExists.put("ass_no", temp.get("ass_no"));

				Integer use_state = assCardLandService.queryCardExistsByAssInNo(mapExists);// 通过资产

				if (use_state == 0) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}

				if (use_state != Integer.parseInt(temp.get("in_amount").toString())) {
					return JSONObject.parseObject("{\"warn\":\"卡片和资产数量不匹配,不能入库! \"}");
				}

				List<Map<String, Object>> cardList = assCardLandService.queryAssCardByAssInNo(mapExists);
				for(Map<String, Object> cardno : cardList){
					Map<String, Object> MapCard= new HashMap<String, Object>();
					
					MapCard.put("ass_card_no", cardno.get("ass_card_no"));
					MapCard.put("group_id", cardno.get("group_id"));
					MapCard.put("hos_id", cardno.get("hos_id"));
					MapCard.put("copy_code", cardno.get("copy_code"));
					Integer dept_house=assInMainLandService.queryBydept(MapCard);
					Integer dept_r_house=assInMainLandService.queryByRdept(MapCard);
					if (dept_house == 0 || dept_r_house==0) {
						return JSONObject.parseObject("{\"warn\":\"卡片没有维护使用科室,不能入库! \"}");
					}
				}

				
				
				
				
				
				
				
				
				
				
				
				
				boolean assFlag = true,

						priceFlag = true,

						venFlag = true;

				for (Map<String, Object> card : cardList) {
					if (verification(temp.get("ass_id"), card.get("ass_id"))) {
						assFlag = false;
						break;
					}
					//if (verification(temp.get("price"), card.get("price"))) {
						//priceFlag = false;
						//break;
					//}
					if (verification(temp.get("VEN_ID"), card.get("ven_id"))) {
						venFlag = false;
						break;
					}
				}
				if (!assFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
				}
				if (!priceFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产单价和卡片原值不匹配的资产,不能入库! \"}");
				}
				if (!venFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在供应商不匹配的资产,不能入库! \"}");
				}
			}

		}

		for (String data : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = data.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_in_no", ids[3]);

			mapVo.put("in_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("confirm_emp", SessionManager.getUserId());

			AssInMainLand assInMainLand = assInMainLandService.queryByCode(mapVo);// 主表
			if (assInMainLand.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}

		try {
			assInMainJson = assInMainLandService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	private boolean verification(Object obj1, Object obj2) {
		// true为不相等
		if(NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))){
			
			BigDecimal bd1 = new BigDecimal(obj1.toString());
			
			BigDecimal bd2 = new BigDecimal(obj2.toString());
			
			
			if(bd1.compareTo(bd2) != 0){
				return true;
			}else{
				return false;
			}
		}
		if(obj1 == null && obj2 == null){
			return false;
		}
		if(obj1 == null && obj2 != null){
			return true;
		}
		if(obj1 != null && obj2 == null){
			return true;
		}
		if(obj1 != null && obj2 != null){
			if(!obj1.equals(obj2)){
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}
	}

	/**
	 * @Description 导入跳转页面 资产入库主表(土地)
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/assInMainLandImportPage", method = RequestMethod.GET)
	public String assInMainLandImportPage(Model mode) throws Exception {

		return "hrp/ass/assland/assin/assInMainLandImport";

	}

	/**
	 * @Description 下载导入模版 资产入库主表(土地)
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assland/assin/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "sys\\downTemplate", "资产入库主表(土地).xls");

		return null;
	}

	/**
	 * @Description 导入数据 资产入库主表(土地)
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/readAssInMainLandFiles", method = RequestMethod.POST)
	public String readAssInMainLandFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssInMainLand> list_err = new ArrayList<AssInMainLand>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssInMainLand assInMainLand = new AssInMainLand();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assInMainLand.setAss_in_no(temp[3]);
					mapVo.put("ass_in_no", temp[3]);

				} else {

					err_sb.append("业务单号为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assInMainLand.setBus_type_code(temp[4]);
					mapVo.put("bus_type_code", temp[4]);

				} else {

					err_sb.append("业务类型为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assInMainLand.setVen_id(Long.valueOf(temp[5]));
					mapVo.put("ven_id", temp[5]);

				} else {

					err_sb.append("开发商ID为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assInMainLand.setVen_no(Long.valueOf(temp[6]));
					mapVo.put("ven_no", temp[6]);

				} else {

					err_sb.append("开发商变更ID为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assInMainLand.setIn_money(Double.valueOf(temp[7]));
					mapVo.put("in_money", temp[7]);

				} else {

					err_sb.append("入库金额为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assInMainLand.setNote(temp[8]);
					mapVo.put("note", temp[8]);

				} else {

					err_sb.append("备注为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assInMainLand.setCreate_emp(Long.valueOf(temp[9]));
					mapVo.put("create_emp", temp[9]);

				} else {

					err_sb.append("制单人为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assInMainLand.setCreate_date(DateUtil.stringToDate(temp[10]));
					mapVo.put("create_date", temp[10]);

				} else {

					err_sb.append("制单日期为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assInMainLand.setIn_date(DateUtil.stringToDate(temp[11]));
					mapVo.put("in_date", temp[11]);

				} else {

					err_sb.append("入账日期为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assInMainLand.setConfirm_emp(Long.valueOf(temp[12]));
					mapVo.put("confirm_emp", temp[12]);

				} else {

					err_sb.append("确认人为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assInMainLand.setState(Integer.valueOf(temp[13]));
					mapVo.put("state", temp[13]);

				} else {

					err_sb.append("0:新建 1:审核 2:确认为空  ");

				}

				AssInMainLand data_exc_extis = assInMainLandService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assInMainLand.setError_type(err_sb.toString());

					list_err.add(assInMainLand);

				} else {

					String dataJson = assInMainLandService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssInMainLand data_exc = new AssInMainLand();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 资产入库主表(土地)
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assland/assin/addBatchAssInMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssInMainLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssInMainLand> list_err = new ArrayList<AssInMainLand>();

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

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AssInMainLand assInMainLand = new AssInMainLand();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("ass_in_no"))) {

					assInMainLand.setAss_in_no((String) jsonObj.get("ass_in_no"));
					mapVo.put("ass_in_no", jsonObj.get("ass_in_no"));
				} else {

					err_sb.append("业务单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {

					assInMainLand.setBus_type_code((String) jsonObj.get("bus_type_code"));
					mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
				} else {

					err_sb.append("业务类型为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {

					assInMainLand.setVen_id(Long.valueOf((String) jsonObj.get("ven_id")));
					mapVo.put("ven_id", jsonObj.get("ven_id"));
				} else {

					err_sb.append("开发商ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {

					assInMainLand.setVen_no(Long.valueOf((String) jsonObj.get("ven_no")));
					mapVo.put("ven_no", jsonObj.get("ven_no"));
				} else {

					err_sb.append("开发商变更ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("in_money"))) {

					assInMainLand.setIn_money(Double.valueOf((String) jsonObj.get("in_money")));
					mapVo.put("in_money", jsonObj.get("in_money"));
				} else {

					err_sb.append("入库金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					assInMainLand.setNote((String) jsonObj.get("note"));
					mapVo.put("note", jsonObj.get("note"));
				} else {

					err_sb.append("备注为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {

					assInMainLand.setCreate_emp(Long.valueOf((String) jsonObj.get("create_emp")));
					mapVo.put("create_emp", jsonObj.get("create_emp"));
				} else {

					err_sb.append("制单人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_date"))) {

					assInMainLand.setCreate_date(DateUtil.stringToDate((String) jsonObj.get("create_date")));
					mapVo.put("create_date", jsonObj.get("create_date"));
				} else {

					err_sb.append("制单日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("in_date"))) {

					assInMainLand.setIn_date(DateUtil.stringToDate((String) jsonObj.get("in_date")));
					mapVo.put("in_date", jsonObj.get("in_date"));
				} else {

					err_sb.append("入账日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("confirm_emp"))) {

					assInMainLand.setConfirm_emp(Long.valueOf((String) jsonObj.get("confirm_emp")));
					mapVo.put("confirm_emp", jsonObj.get("confirm_emp"));
				} else {

					err_sb.append("确认人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					assInMainLand.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("0:新建 1:审核 2:确认为空  ");

				}

				AssInMainLand data_exc_extis = assInMainLandService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assInMainLand.setError_type(err_sb.toString());

					list_err.add(assInMainLand);

				} else {

					String dataJson = assInMainLandService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssInMainLand data_exc = new AssInMainLand();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/assland/assin/queryAssInMainLandStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssInMainLandStates(@RequestParam Map<String, Object> mapVo, Model mode)
	throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assInMainLandService.queryAssInMainLandStates(mapVo);

if(list.size() == 0){
	
	return JSONObject.parseObject("{\"state\":\"true\"}");
	
}else{
	
	String  str = "" ;
	for(String item : list){
		
		str += item +"," ;
	}
	
	return JSONObject.parseObject("{\"error\":\"盘点单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}

}
