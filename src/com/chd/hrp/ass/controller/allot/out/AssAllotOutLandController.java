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
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailLand;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutLand;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailLandService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutLandService;
import com.chd.hrp.ass.service.card.AssCardLandService;

/**
 * 
 * @Description: 050901 资产无偿调拨出库单主表(土地)
 * @Table: ASS_ALLOT_OUT_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssAllotOutLandController extends BaseController {

	private static Logger logger = Logger.getLogger(AssAllotOutLandController.class);

	// 引入Service服务
	@Resource(name = "assAllotOutLandService")
	private final AssAllotOutLandService assAllotOutLandService = null;

	@Resource(name = "assAllotOutDetailLandService")
	private final AssAllotOutDetailLandService assAllotOutDetailLandService = null;

	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assallotout/assAllotOutLandMainPage", method = RequestMethod.GET)
	public String assAllotOutLandMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05068",MyConfig.getSysPara("05068"));
		
		return "hrp/ass/assland/assallotout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assallotout/assAllotOutLandAddPage", method = RequestMethod.GET)
	public String assAllotOutLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assallotout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assallotout/saveAssAllotOutLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssAllotOutLand(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssAllotOutLand assBackLand = assAllotOutLandService.queryByCode(mapVo);
		if (assBackLand != null) {
			if (assBackLand.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assAllotOutLandJson = assAllotOutLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assAllotOutLandJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assallotout/updateConfirmAllotOutLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAllotOutLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssAllotOutLand assAllotOutLand = assAllotOutLandService.queryByCode(mapVo);
			if (DateUtil.compareDate(assAllotOutLand.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssAllotOutDetailLand> list = assAllotOutDetailLandService.queryByAssAllotOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssAllotOutDetailLand assBackDetailLand : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailLand.getGroup_id());
					listCardMap.put("hos_id", assBackDetailLand.getHos_id());
					listCardMap.put("copy_code", assBackDetailLand.getCopy_code());
					listCardMap.put("dispose_type", 31);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailLand.getAss_card_no());
					AssCardLand assCardLand = assCardLandService.queryByCode(listCardMap);
					if (assCardLand == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardLand.getDispose_type() != null && 31 == assCardLand.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailLand.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailLand.getAss_card_no(), assBackDetailLand.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assAllotOutLand.getState() == 2) {
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
			assInMainJson = assAllotOutLandService.updateAllotOutConfirm(listVo, listCardVo);
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
	@RequestMapping(value = "/hrp/ass/assland/assallotout/assAllotOutLandUpdatePage", method = RequestMethod.GET)
	public String assAllotOutLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssAllotOutLand assAllotOutLand = new AssAllotOutLand();

		assAllotOutLand = assAllotOutLandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assAllotOutLand.getGroup_id());
		mode.addAttribute("hos_id", assAllotOutLand.getHos_id());
		mode.addAttribute("copy_code", assAllotOutLand.getCopy_code());
		mode.addAttribute("allot_out_no", assAllotOutLand.getAllot_out_no());
		mode.addAttribute("in_group_id", assAllotOutLand.getIn_group_id());
		mode.addAttribute("in_group_name", assAllotOutLand.getIn_group_name());
		mode.addAttribute("in_hos_id", assAllotOutLand.getIn_hos_id());
		mode.addAttribute("in_hos_name", assAllotOutLand.getIn_hos_name());
		mode.addAttribute("price", assAllotOutLand.getPrice());
		mode.addAttribute("add_depre", assAllotOutLand.getAdd_depre());
		mode.addAttribute("cur_money", assAllotOutLand.getCur_money());
		mode.addAttribute("fore_money", assAllotOutLand.getFore_money());
		mode.addAttribute("create_emp", assAllotOutLand.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotOutLand.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotOutLand.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotOutLand.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotOutLand.getAudit_emp_name());
		mode.addAttribute("audit_date", assAllotOutLand.getAudit_date());
		mode.addAttribute("state", assAllotOutLand.getState());
		mode.addAttribute("state_name", assAllotOutLand.getState_name());
		mode.addAttribute("note", assAllotOutLand.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05059",MyConfig.getSysPara("05059"));
		
		return "hrp/ass/assland/assallotout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assallotout/deleteAssAllotOutLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssAllotOutLand assAllotOutLand = assAllotOutLandService.queryByCode(mapVo);
			if (assAllotOutLand != null) {
				if (assAllotOutLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assAllotOutLandJson = assAllotOutLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutLandJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assallotout/queryAssAllotOutLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		
		String assAllotOutLand = null;

		if("0".equals(mapVo.get("show_detail").toString())){
			assAllotOutLand = assAllotOutLandService.query(getPage(mapVo));
		}else{
			assAllotOutLand = assAllotOutLandService.queryDetails(getPage(mapVo));
		}
		

		return JSONObject.parseObject(assAllotOutLand);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assallotout/deleteAssAllotOutDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutDetailLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssAllotOutLand assAllotOutLand = assAllotOutLandService.queryByCode(mapVo);
			if (assAllotOutLand != null) {
				if (assAllotOutLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assAllotOutDetailLandJson = assAllotOutDetailLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutDetailLandJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assallotout/queryAssAllotOutDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutDetailLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutDetailLand = assAllotOutDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotOutDetailLand);

	}
	
	
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/assland/assallotout/queryAssAllotOutLandStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssAllotOutLandStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assAllotOutLandService.queryAssAllotOutLandStates(mapVo);

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
