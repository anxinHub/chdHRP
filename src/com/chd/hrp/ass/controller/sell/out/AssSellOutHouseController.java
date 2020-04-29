/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.sell.out;

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
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailHouse;
import com.chd.hrp.ass.entity.sell.out.AssSellOutHouse;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailHouseService;
import com.chd.hrp.ass.service.sell.out.AssSellOutHouseService;

/**
 * 
 * @Description: 050901 资产有偿调拨出库单主表（房屋及建筑物）
 * @Table: ASS_SELL_OUT_HOUSE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssSellOutHouseController extends BaseController {

	private static Logger logger = Logger.getLogger(AssSellOutHouseController.class);

	// 引入Service服务
	@Resource(name = "assSellOutHouseService")
	private final AssSellOutHouseService assSellOutHouseService = null;

	@Resource(name = "assSellOutDetailHouseService")
	private final AssSellOutDetailHouseService assSellOutDetailHouseService = null;
	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellout/assSellOutHouseMainPage", method = RequestMethod.GET)
	public String assSellOutHouseMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05059",MyConfig.getSysPara("05059"));
		
		return "hrp/ass/asshouse/asssellout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellout/assSellOutHouseAddPage", method = RequestMethod.GET)
	public String assSellOutHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asssellout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellout/saveAssSellOutHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssSellOutHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		} 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		AssSellOutHouse assBackHouse = assSellOutHouseService.queryByCode(mapVo);
		if (assBackHouse != null) {
			if (assBackHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assSellOutHouseJson = assSellOutHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellOutHouseJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellout/updateConfirmSellOutHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellOutHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String assInMainJson = "";
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("sell_out_no", ids[3]);

			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("audit_emp", SessionManager.getUserId());

			AssSellOutHouse assSellOutHouse = assSellOutHouseService.queryByCode(mapVo);
			if (DateUtil.compareDate(assSellOutHouse.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssSellOutDetailHouse> list = assSellOutDetailHouseService.queryByAssSellOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssSellOutDetailHouse assBackDetailHouse : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailHouse.getGroup_id());
					listCardMap.put("hos_id", assBackDetailHouse.getHos_id());
					listCardMap.put("copy_code", assBackDetailHouse.getCopy_code());
					listCardMap.put("dispose_type", 32);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("dispose_income", assBackDetailHouse.getDispose_income() == null ? "0"
							: assBackDetailHouse.getDispose_income());
					listCardMap.put("dispose_tax",
							assBackDetailHouse.getDispose_tax() == null ? "0" : assBackDetailHouse.getDispose_tax());
					listCardMap.put("ass_card_no", assBackDetailHouse.getAss_card_no());
					AssCardHouse assCardHouse = assCardHouseService.queryByCode(listCardMap);
					if (assCardHouse == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardHouse.getDispose_type() != null && 32 == assCardHouse.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailHouse.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailHouse.getAss_card_no(), assBackDetailHouse.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assSellOutHouse.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能退库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复退货! \"}");
		}

		try {
			assInMainJson = assSellOutHouseService.updateSellOutConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellout/assSellOutHouseUpdatePage", method = RequestMethod.GET)
	public String assSellOutHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssSellOutHouse assSellOutHouse = new AssSellOutHouse();

		assSellOutHouse = assSellOutHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assSellOutHouse.getGroup_id());
		mode.addAttribute("hos_id", assSellOutHouse.getHos_id());
		mode.addAttribute("copy_code", assSellOutHouse.getCopy_code());
		mode.addAttribute("sell_out_no", assSellOutHouse.getSell_out_no());
		mode.addAttribute("in_group_id", assSellOutHouse.getIn_group_id());
		mode.addAttribute("in_group_name", assSellOutHouse.getIn_group_name());
		mode.addAttribute("in_hos_id", assSellOutHouse.getIn_hos_id());
		mode.addAttribute("in_hos_name", assSellOutHouse.getIn_hos_name());
		mode.addAttribute("price", assSellOutHouse.getPrice());
		mode.addAttribute("add_depre", assSellOutHouse.getAdd_depre());
		mode.addAttribute("cur_money", assSellOutHouse.getCur_money());
		mode.addAttribute("fore_money", assSellOutHouse.getFore_money());
		mode.addAttribute("sell_price", assSellOutHouse.getSell_price());
		mode.addAttribute("dispose_income", assSellOutHouse.getDispose_income());
		mode.addAttribute("dispose_tax", assSellOutHouse.getDispose_tax());
		mode.addAttribute("create_emp", assSellOutHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellOutHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellOutHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellOutHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellOutHouse.getAudit_emp_name());
		mode.addAttribute("audit_date", assSellOutHouse.getAudit_date());
		mode.addAttribute("state", assSellOutHouse.getState());
		mode.addAttribute("state_name", assSellOutHouse.getState_name());
		mode.addAttribute("note", assSellOutHouse.getNote());
		mode.addAttribute("bus_type_code", assSellOutHouse.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellOutHouse.getBus_type_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05059",MyConfig.getSysPara("05059"));
		
		return "hrp/ass/asshouse/asssellout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellout/deleteAssSellOutHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("sell_out_no", ids[3]);

			AssSellOutHouse assSellOutHouse = assSellOutHouseService.queryByCode(mapVo);
			if (assSellOutHouse != null) {
				if (assSellOutHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assSellOutHouseJson = assSellOutHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutHouseJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellout/queryAssSellOutHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutHouse = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellOutHouse = assSellOutHouseService.query(getPage(mapVo));
		}else{
			assSellOutHouse = assSellOutHouseService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellOutHouse);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellout/deleteAssSellOutDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutDetailHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("Sell_out_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			AssSellOutHouse assSellOutHouse = assSellOutHouseService.queryByCode(mapVo);
			if (assSellOutHouse != null) {
				if (assSellOutHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assSellOutDetailHouseJson = assSellOutDetailHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutDetailHouseJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellout/queryAssSellOutDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailHouse = assSellOutDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailHouse);

	}
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/asshouse/asssellout/queryAssSellOutStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssSellOutStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assSellOutHouseService.queryAssSellOutStates(mapVo);

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
