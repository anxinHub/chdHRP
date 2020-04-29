/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.back.rest;
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
import com.chd.hrp.ass.entity.back.rest.AssBackRestDetailLand;
import com.chd.hrp.ass.entity.back.rest.AssBackRestLand;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.service.back.rest.AssBackRestDetailLandService;
import com.chd.hrp.ass.service.back.rest.AssBackRestLandService;
import com.chd.hrp.ass.service.card.AssCardLandService;
/**
 * 
 * @Description:
 * 050701 资产其他退账单主表(土地)
 * @Table:
 * ASS_BACK_REST_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssBackRestLandController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssBackRestLandController.class);
	
	// 引入Service服务
	@Resource(name = "assBackRestLandService")
	private final AssBackRestLandService assBackRestLandService = null;

	@Resource(name = "assBackRestDetailLandService")
	private final AssBackRestDetailLandService assBackRestDetailLandService = null;
	
	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestback/assBackRestLandMainPage", method = RequestMethod.GET)
	public String assBackRestLandMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05067",MyConfig.getSysPara("05067"));
		
		return "hrp/ass/assland/assrestback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestback/assBackRestLandAddPage", method = RequestMethod.GET)
	public String assBackRestLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assrestback/add";

	}

	/**
	 * @Description 添加数据 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestback/saveAssBackRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackRestMainLand(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssBackRestLand assBackRestLand = assBackRestLandService.queryByCode(mapVo);
		if (assBackRestLand != null) {
			if (assBackRestLand.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackLandJson = assBackRestLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackLandJson);

	}

	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestback/updateConfirmBackRestLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmBackRestLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			mapVo.put("ass_back_no", ids[3]);

			mapVo.put("dispose_type", 41);

			mapVo.put("back_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("confirm_emp", SessionManager.getUserId());
			AssBackRestLand assBackRestLand = assBackRestLandService.queryByCode(mapVo);
			if (DateUtil.compareDate(assBackRestLand.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssBackRestDetailLand> list = assBackRestDetailLandService.queryByAssBackNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssBackRestDetailLand assBackRestDetailLand : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackRestDetailLand.getGroup_id());
					listCardMap.put("hos_id", assBackRestDetailLand.getHos_id());
					listCardMap.put("copy_code", assBackRestDetailLand.getCopy_code());
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("dispose_type", 41);
					listCardMap.put("ass_card_no", assBackRestDetailLand.getAss_card_no());
					AssCardLand assCard = assCardLandService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if (assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackRestDetailLand.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackRestDetailLand.getAss_card_no(), assBackRestDetailLand.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assBackRestLand.getState() == 2) {
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
			assInMainJson = assBackRestLandService.updateBackConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestback/assBackRestLandUpdatePage", method = RequestMethod.GET)
	public String assBackRestLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackRestLand assBackLand = new AssBackRestLand();

		assBackLand = assBackRestLandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackLand.getGroup_id());
		mode.addAttribute("hos_id", assBackLand.getHos_id());
		mode.addAttribute("copy_code", assBackLand.getCopy_code());
		mode.addAttribute("ass_back_no", assBackLand.getAss_back_no());
		mode.addAttribute("bus_type_code", assBackLand.getBus_type_code());
		mode.addAttribute("bus_type_name", assBackLand.getBus_type_name());
		mode.addAttribute("back_money", assBackLand.getBack_money());
		mode.addAttribute("note", assBackLand.getNote());
		mode.addAttribute("create_emp", assBackLand.getCreate_emp());
		mode.addAttribute("create_emp_name", assBackLand.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackLand.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("back_date", DateUtil.dateToString(assBackLand.getBack_date(), "yyyy-MM-dd"));
		mode.addAttribute("confirm_emp", assBackLand.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assBackLand.getConfirm_emp_name());
		mode.addAttribute("state", assBackLand.getState());
		mode.addAttribute("state_name", assBackLand.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05067",MyConfig.getSysPara("05067"));
		
		return "hrp/ass/assland/assrestback/update";
	}

	/**
	 * @Description 删除数据 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestback/deleteAssBackRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_back_no", ids[3]);

			AssBackRestLand assBackRestLand = assBackRestLandService.queryByCode(mapVo);
			if (assBackRestLand != null) {
				if (assBackRestLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackLandJson = assBackRestLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackLandJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assrestback/queryAssBackRestMainLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackLand = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			 assBackLand = assBackRestLandService.query(getPage(mapVo));
			
		}else{
			
			 assBackLand = assBackRestLandService.queryDetails(getPage(mapVo));
			
		}
		

		return JSONObject.parseObject(assBackLand);

	}

	@RequestMapping(value = "/hrp/ass/assland/assrestback/deleteAssBackRestDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestDetailLand(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_back_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			AssBackRestLand assBackRestLand = assBackRestLandService.queryByCode(mapVo);
			if (assBackRestLand != null) {
				if (assBackRestLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);
		}

		String assBackDetailLandJson = assBackRestDetailLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackDetailLandJson);

	}

	@RequestMapping(value = "/hrp/ass/assland/assrestback/queryAssBackRestDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestDetailLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackDetailLand = assBackRestDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailLand);

	}
	
	
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/assland/assrestback/queryAssBackRestLandStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssBackRestLandStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assBackRestLandService.queryAssBackRestLandStates(mapVo);

if(list.size() == 0){

return JSONObject.parseObject("{\"state\":\"true\"}");

}else{

String  str = "" ;
for(String item : list){
	
	str += item +"," ;
}

return JSONObject.parseObject("{\"error\":\"退货单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}
}

