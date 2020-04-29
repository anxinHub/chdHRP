/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.in.rest;

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
import com.chd.hrp.ass.entity.in.rest.AssInRestMainLand;
import com.chd.hrp.ass.entity.in.rest.AssInRestMainLand;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.in.rest.AssInRestDetailLandService;
import com.chd.hrp.ass.service.in.rest.AssInRestMainLandService;
import com.chd.hrp.ass.service.in.rest.AssInRestMainLandService;

/**
 * 
 * @Description: 050701 资产其他入账主表(土地)
 * @Table: ASS_IN_REST_MAIN_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssInRestMainLandController extends BaseController {

	private static Logger logger = Logger.getLogger(AssInRestMainLandController.class);

	// 引入Service服务
	@Resource(name = "assInRestMainLandService")
	private final AssInRestMainLandService assInRestMainLandService = null;
	@Resource(name = "assInRestDetailLandService")
	private final AssInRestDetailLandService assInDetailLandService = null;
	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/assInRestMainLandMainPage", method = RequestMethod.GET)
	public String assInRestMainLandMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05066",MyConfig.getSysPara("05066"));
		
		return "hrp/ass/assland/assrestin/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/assInRestMainLandAddPage", method = RequestMethod.GET)
	public String assInRestMainLandAddPage(Model mode) throws Exception {
		mode.addAttribute("ass_nature", "03");
		return "hrp/ass/asscard/assInCardAdd";

	}

	/**
	 * @Description 添加数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/addAssInRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssInRestMainLand(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assInRestMainLandJson = assInRestMainLandService.add(mapVo);

		return JSONObject.parseObject(assInRestMainLandJson);

	}

	/**
	 * @Description 更新跳转页面 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/assInRestMainLandUpdatePage", method = RequestMethod.GET)
	public String assInRestMainLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssInRestMainLand assInRestMainLand = new AssInRestMainLand();

		assInRestMainLand = assInRestMainLandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInRestMainLand.getGroup_id());
		mode.addAttribute("hos_id", assInRestMainLand.getHos_id());
		mode.addAttribute("copy_code", assInRestMainLand.getCopy_code());
		mode.addAttribute("ass_in_no", assInRestMainLand.getAss_in_no());
		mode.addAttribute("bus_type_code", assInRestMainLand.getBus_type_code());
		mode.addAttribute("in_money", assInRestMainLand.getIn_money());
		mode.addAttribute("note", assInRestMainLand.getNote());
		mode.addAttribute("create_emp", assInRestMainLand.getCreate_emp());
		mode.addAttribute("create_date", assInRestMainLand.getCreate_date());
		mode.addAttribute("in_date", assInRestMainLand.getIn_date());
		mode.addAttribute("confirm_emp", assInRestMainLand.getConfirm_emp());
		mode.addAttribute("state", assInRestMainLand.getState());

		return "hrp/ass/assland/assrestin/update";
	}

	/**
	 * @Description 更新数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/updateAssInRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssInRestMainLand(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assInRestMainLandJson = assInRestMainLandService.update(mapVo);

		return JSONObject.parseObject(assInRestMainLandJson);
	}

	/**
	 * @Description 更新数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/addOrUpdateAssInRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssInRestMainLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assInRestMainLandJson = "";

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

			assInRestMainLandJson = assInRestMainLandService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(assInRestMainLandJson);
	}

	/**
	 * @Description 删除数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/deleteAssInRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInRestMainLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssInRestMainLand assInRestMainLand = assInRestMainLandService.queryByCode(mapVo);

			if (assInRestMainLand.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);
		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assInRestMainLandJson = assInRestMainLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assInRestMainLandJson);

	}

	/**
	 * @Description 查询数据 资产入库主表(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/queryAssInRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInRestMainLand(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		String assInRestMainLand = null; 
		
		if("0".equals(mapVo.get("show_detail").toString())){
		
			assInRestMainLand = assInRestMainLandService.query(getPage(mapVo));

		}else{
			
			assInRestMainLand = assInRestMainLandService.queryDetails(getPage(mapVo));
			
		}	
			
		return JSONObject.parseObject(assInRestMainLand);

	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/updateConfirmInRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInRestMainLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInRestMainJson = "";
		for (String vdata : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = vdata.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);

			AssInRestMainLand assInRestMainLand = assInRestMainLandService.queryByCode(mapVo);
			if (DateUtil.compareDate(assInRestMainLand.getCreate_date(), new Date())) {
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
					Integer dept_house=assInRestMainLandService.queryBydept(MapCard);
					Integer dept_r_house=assInRestMainLandService.queryByRdept(MapCard);
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
					if (verification(temp.get("BUS_TYPE_NAME"), card.get("bus_type_name"))) {
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

			AssInRestMainLand assInRestMainLand = assInRestMainLandService.queryByCode(mapVo);// 主表
			if (assInRestMainLand.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}

		try {
			assInRestMainJson = assInRestMainLandService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInRestMainJson);
	}

	private boolean verification(Object obj1, Object obj2) {
		// true为不相等
		if (NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))) {
			int number1 = obj1 == null || obj1.equals("") ? 0 : Integer.parseInt(String.valueOf(obj1));
			int number2 = obj2 == null || obj2.equals("") ? 0 : Integer.parseInt(String.valueOf(obj2));
			if (number1 != number2) {
				return true;
			} else {
				return false;
			}
		}
		if (obj1 == null && obj2 == null) {
			return false;
		}
		if (obj1 == null && obj2 != null) {
			return true;
		}
		if (obj1 != null && obj2 == null) {
			return true;
		}
		if (obj1 != null && obj2 != null) {
			if (!obj1.equals(obj2)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @Description 导入跳转页面 资产入库主表(土地)
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestin/assInRestMainLandImportPage", method = RequestMethod.GET)
	public String assInRestMainLandImportPage(Model mode) throws Exception {

		return "hrp/ass/assland/assrestin/assInRestMainLandImport";

	}

	/**
	 * @Description 下载导入模版 资产入库主表(土地)
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assland/assrestin/downTemplate", method = RequestMethod.GET)
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
	@RequestMapping(value = "/hrp/ass/assland/assrestin/readAssInRestMainLandFiles", method = RequestMethod.POST)
	public String readAssInRestMainLandFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssInRestMainLand> list_err = new ArrayList<AssInRestMainLand>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssInRestMainLand assInRestMainLand = new AssInRestMainLand();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assInRestMainLand.setAss_in_no(temp[3]);
					mapVo.put("ass_in_no", temp[3]);

				} else {

					err_sb.append("业务单号为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assInRestMainLand.setBus_type_code(temp[4]);
					mapVo.put("bus_type_code", temp[4]);

				} else {

					err_sb.append("业务类型为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assInRestMainLand.setIn_money(Double.valueOf(temp[7]));
					mapVo.put("in_money", temp[7]);

				} else {

					err_sb.append("入库金额为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assInRestMainLand.setNote(temp[8]);
					mapVo.put("note", temp[8]);

				} else {

					err_sb.append("备注为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assInRestMainLand.setCreate_emp(Long.valueOf(temp[9]));
					mapVo.put("create_emp", temp[9]);

				} else {

					err_sb.append("制单人为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assInRestMainLand.setCreate_date(DateUtil.stringToDate(temp[10]));
					mapVo.put("create_date", temp[10]);

				} else {

					err_sb.append("制单日期为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assInRestMainLand.setIn_date(DateUtil.stringToDate(temp[11]));
					mapVo.put("in_date", temp[11]);

				} else {

					err_sb.append("入账日期为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assInRestMainLand.setConfirm_emp(Long.valueOf(temp[12]));
					mapVo.put("confirm_emp", temp[12]);

				} else {

					err_sb.append("确认人为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assInRestMainLand.setState(Integer.valueOf(temp[13]));
					mapVo.put("state", temp[13]);

				} else {

					err_sb.append("0:新建 1:审核 2:确认为空  ");

				}

				AssInRestMainLand data_exc_extis = assInRestMainLandService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assInRestMainLand.setError_type(err_sb.toString());

					list_err.add(assInRestMainLand);

				} else {

					String dataJson = assInRestMainLandService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssInRestMainLand data_exc = new AssInRestMainLand();

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
	@RequestMapping(value = "/hrp/ass/assland/assrestin/addBatchAssInRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssInRestMainLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssInRestMainLand> list_err = new ArrayList<AssInRestMainLand>();

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

				AssInRestMainLand assInRestMainLand = new AssInRestMainLand();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("ass_in_no"))) {

					assInRestMainLand.setAss_in_no((String) jsonObj.get("ass_in_no"));
					mapVo.put("ass_in_no", jsonObj.get("ass_in_no"));
				} else {

					err_sb.append("业务单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {

					assInRestMainLand.setBus_type_code((String) jsonObj.get("bus_type_code"));
					mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
				} else {

					err_sb.append("业务类型为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("in_money"))) {

					assInRestMainLand.setIn_money(Double.valueOf((String) jsonObj.get("in_money")));
					mapVo.put("in_money", jsonObj.get("in_money"));
				} else {

					err_sb.append("入库金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					assInRestMainLand.setNote((String) jsonObj.get("note"));
					mapVo.put("note", jsonObj.get("note"));
				} else {

					err_sb.append("备注为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {

					assInRestMainLand.setCreate_emp(Long.valueOf((String) jsonObj.get("create_emp")));
					mapVo.put("create_emp", jsonObj.get("create_emp"));
				} else {

					err_sb.append("制单人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_date"))) {

					assInRestMainLand.setCreate_date(DateUtil.stringToDate((String) jsonObj.get("create_date")));
					mapVo.put("create_date", jsonObj.get("create_date"));
				} else {

					err_sb.append("制单日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("in_date"))) {

					assInRestMainLand.setIn_date(DateUtil.stringToDate((String) jsonObj.get("in_date")));
					mapVo.put("in_date", jsonObj.get("in_date"));
				} else {

					err_sb.append("入账日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("confirm_emp"))) {

					assInRestMainLand.setConfirm_emp(Long.valueOf((String) jsonObj.get("confirm_emp")));
					mapVo.put("confirm_emp", jsonObj.get("confirm_emp"));
				} else {

					err_sb.append("确认人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					assInRestMainLand.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("0:新建 1:审核 2:确认为空  ");

				}

				AssInRestMainLand data_exc_extis = assInRestMainLandService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assInRestMainLand.setError_type(err_sb.toString());

					list_err.add(assInRestMainLand);

				} else {

					String dataJson = assInRestMainLandService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssInRestMainLand data_exc = new AssInRestMainLand();

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
@RequestMapping(value = "/hrp/ass/assland/assrestin/queryAssInRestMainLandStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssInRestMainLandStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assInRestMainLandService.queryAssInRestMainLandStates(mapVo);

if(list.size() == 0){

return JSONObject.parseObject("{\"state\":\"true\"}");

}else{

String  str = "" ;
for(String item : list){
	
	str += item +"," ;
}

return JSONObject.parseObject("{\"error\":\"入账单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}

}
