/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.allot.out;

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
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailHouse;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutHouse;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailHouseService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutHouseService;
import com.chd.hrp.ass.service.card.AssCardHouseService;

/**
 * 
 * @Description: 050901 资产无偿调拨出库单主表（房屋及建筑物）
 * @Table: ASS_ALLOT_OUT_HOUSE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssAllotOutHouseController extends BaseController {

	private static Logger logger = Logger.getLogger(AssAllotOutHouseController.class);

	// 引入Service服务
	@Resource(name = "assAllotOutHouseService")
	private final AssAllotOutHouseService assAllotOutHouseService = null;

	@Resource(name = "assAllotOutDetailHouseService")
	private final AssAllotOutDetailHouseService assAllotOutDetailHouseService = null;

	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotout/assAllotOutHouseMainPage", method = RequestMethod.GET)
	public String assAllotOutHouseMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05058",MyConfig.getSysPara("05058"));
		
		return "hrp/ass/asshouse/assallotout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotout/assAllotOutHouseAddPage", method = RequestMethod.GET)
	public String assAllotOutHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assallotout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotout/saveAssAllotOutHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssAllotOutHouse(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssAllotOutHouse assBackHouse = assAllotOutHouseService.queryByCode(mapVo);
		if (assBackHouse != null) {
			if (assBackHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assAllotOutHouseJson = assAllotOutHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assAllotOutHouseJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotout/updateConfirmAllotOutHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAllotOutHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			mapVo.put("allot_out_no", ids[3]);

			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("audit_emp", SessionManager.getUserId());
			AssAllotOutHouse assAllotOutHouse = assAllotOutHouseService.queryByCode(mapVo);
			if (assAllotOutHouse.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assAllotOutHouse.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssAllotOutDetailHouse> list = assAllotOutDetailHouseService.queryByAssAllotOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssAllotOutDetailHouse assBackDetailHouse : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailHouse.getGroup_id());
					listCardMap.put("hos_id", assBackDetailHouse.getHos_id());
					listCardMap.put("copy_code", assBackDetailHouse.getCopy_code());
					listCardMap.put("dispose_type", 31);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailHouse.getAss_card_no());
					AssCardHouse assCardHouse = assCardHouseService.queryByCode(listCardMap);
					if (assCardHouse == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardHouse.getDispose_type() != null && 31 == assCardHouse.getDispose_type()) {
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

			if (assAllotOutHouse.getState() == 2) {
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
			assInMainJson = assAllotOutHouseService.updateAllotOutConfirm(listVo, listCardVo);
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
	@RequestMapping(value = "/hrp/ass/asshouse/assallotout/assAllotOutHouseUpdatePage", method = RequestMethod.GET)
	public String assAllotOutHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssAllotOutHouse assAllotOutHouse = new AssAllotOutHouse();

		assAllotOutHouse = assAllotOutHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assAllotOutHouse.getGroup_id());
		mode.addAttribute("hos_id", assAllotOutHouse.getHos_id());
		mode.addAttribute("copy_code", assAllotOutHouse.getCopy_code());
		mode.addAttribute("allot_out_no", assAllotOutHouse.getAllot_out_no());
		mode.addAttribute("in_group_id", assAllotOutHouse.getIn_group_id());
		mode.addAttribute("in_group_name", assAllotOutHouse.getIn_group_name());
		mode.addAttribute("in_hos_id", assAllotOutHouse.getIn_hos_id());
		mode.addAttribute("in_hos_name", assAllotOutHouse.getIn_hos_name());
		mode.addAttribute("price", assAllotOutHouse.getPrice());
		mode.addAttribute("add_depre", assAllotOutHouse.getAdd_depre());
		mode.addAttribute("cur_money", assAllotOutHouse.getCur_money());
		mode.addAttribute("fore_money", assAllotOutHouse.getFore_money());
		mode.addAttribute("create_emp", assAllotOutHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotOutHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotOutHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotOutHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotOutHouse.getAudit_emp_name());
		mode.addAttribute("audit_date", assAllotOutHouse.getAudit_date());
		mode.addAttribute("state", assAllotOutHouse.getState());
		mode.addAttribute("state_name", assAllotOutHouse.getState_name());
		mode.addAttribute("note", assAllotOutHouse.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05059",MyConfig.getSysPara("05059"));
		
		return "hrp/ass/asshouse/assallotout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotout/deleteAssAllotOutHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("allot_out_no", ids[3]);

			AssAllotOutHouse assAllotOutHouse = assAllotOutHouseService.queryByCode(mapVo);
			if (assAllotOutHouse != null) {
				if (assAllotOutHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assAllotOutHouseJson = assAllotOutHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutHouseJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotout/queryAssAllotOutHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());


		String assAllotOutHouse = null;

		if("0".equals(mapVo.get("show_detail").toString())){
			assAllotOutHouse = assAllotOutHouseService.query(getPage(mapVo));
		}else{
			assAllotOutHouse = assAllotOutHouseService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assAllotOutHouse);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotout/deleteAssAllotOutDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutDetailHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("allot_out_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			AssAllotOutHouse assAllotOutHouse = assAllotOutHouseService.queryByCode(mapVo);
			if (assAllotOutHouse != null) {
				if (assAllotOutHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assAllotOutDetailHouseJson = assAllotOutDetailHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutDetailHouseJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assallotout/queryAssAllotOutDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutDetailHouse = assAllotOutDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotOutDetailHouse);

	}
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/asshouse/assallotout/queryAssAllotInHouseStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssAllotInHouseStates(@RequestParam Map<String, Object> mapVo, Model mode)
	throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assAllotOutHouseService.queryAssAllotInHouseStates(mapVo);

if(list.size() == 0){
	
	return JSONObject.parseObject("{\"state\":\"true\"}");
	
}else{
	
	String  str = "" ;
	for(String item : list){
		
		str += item +"," ;
	}
	
	return JSONObject.parseObject("{\"error\":\"出库单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}

}
