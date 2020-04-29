/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.change;

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
import com.chd.hrp.ass.entity.change.AssChangeLand;
import com.chd.hrp.ass.service.change.AssChangeDetailLandService;
import com.chd.hrp.ass.service.change.AssChangeLandService;

/**
 * 
 * @Description: 050805 资产原值变动(土地)
 * @Table: ASS_Change_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssChangeLandController extends BaseController {

	private static Logger logger = Logger.getLogger(AssChangeLandController.class);

	// 引入Service服务
	@Resource(name = "assChangeLandService")
	private final AssChangeLandService assChangeLandService = null;

	@Resource(name = "assChangeDetailLandService")
	private final AssChangeDetailLandService assChangeDetailLandService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/assChangeLandMainPage", method = RequestMethod.GET)
	public String assChangeLandMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assland/asschange/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/assChangeLandAddPage", method = RequestMethod.GET)
	public String assChangeLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		mode.addAttribute("ass_05071",MyConfig.getSysPara("05071"));
		
		return "hrp/ass/assland/asschange/add";

	}

	/**
	 * @Description 添加数据 050805 资产原值变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/saveAssChangeLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssChangeLand assChangeLand = assChangeLandService.queryByCode(mapVo);

		if (assChangeLand != null) {
			if (assChangeLand.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeLandJson = assChangeLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChangeLandJson);

	}

	@RequestMapping(value = "/hrp/ass/assland/asschange/updateConfirmChangeLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChangeLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("change_no", ids[3]);
			mapVo.put("change_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());

			AssChangeLand assChangeLand = assChangeLandService.queryByCode(mapVo);

			if (assChangeLand.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assChangeLandService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050805 资产原值变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/assChangeLandUpdatePage", method = RequestMethod.GET)
	public String assChangeLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeLand assChangeLand = new AssChangeLand();

		assChangeLand = assChangeLandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChangeLand.getGroup_id());
		mode.addAttribute("hos_id", assChangeLand.getHos_id());
		mode.addAttribute("copy_code", assChangeLand.getCopy_code());
		mode.addAttribute("change_no", assChangeLand.getChange_no());
		mode.addAttribute("ven_id", assChangeLand.getVen_id());
		mode.addAttribute("ven_no", assChangeLand.getVen_no());
		mode.addAttribute("ven_code", assChangeLand.getVen_code());
		mode.addAttribute("ven_name", assChangeLand.getVen_name());
		mode.addAttribute("create_emp", assChangeLand.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assChangeLand.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChangeLand.getAudit_emp());
		mode.addAttribute("change_date", assChangeLand.getChange_date());
		mode.addAttribute("state", assChangeLand.getState());
		mode.addAttribute("note", assChangeLand.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05071",MyConfig.getSysPara("05071"));
		
		return "hrp/ass/assland/asschange/update";
	}

	/**
	 * @Description 删除数据 050805 资产原值变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/deleteAssChangeLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("change_no", ids[3]);

			AssChangeLand assChangeLand = assChangeLandService.queryByCode(mapVo);

			if (assChangeLand != null) {
				if (assChangeLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeLandJson = assChangeLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeLandJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/queryAssChangeLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeLand = assChangeLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeLand);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/deleteAssChangeDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeDetailLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("change_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);

			AssChangeLand assChangeLand = assChangeLandService.queryByCode(mapVo);

			if (assChangeLand != null) {
				if (assChangeLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetailLandJson = assChangeDetailLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailLandJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/queryAssChangeDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeDetailLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeDetailLand = assChangeDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeDetailLand);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/deleteAssChangeSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeSourceLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("change_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			mapVo.put("source_id", ids[5]);

			AssChangeLand assChangeLand = assChangeLandService.queryByCode(mapVo);

			if (assChangeLand != null) {
				if (assChangeLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourceLandJson = assChangeLandService.deleteAssChangeSourceLand(listVo);

		return JSONObject.parseObject(assChangeSourceLandJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/queryAssChangeSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeSourceLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assChangeSourceLand = assChangeLandService.queryAssChangeSourceLand(getPage(mapVo));

		return JSONObject.parseObject(assChangeSourceLand);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asschange/saveAssChangeSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeSourceLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeLand assChangeLand = assChangeLandService.queryByCode(mapVo);

		if (assChangeLand != null) {
			if (assChangeLand.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeSourceLandJson = assChangeLandService.saveAssChangeSourceLand(mapVo);

		return JSONObject.parseObject(assChangeSourceLandJson);

	}
	
	/**
 * 状态查询
 * @param mapVo
 * @param mode
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/hrp/ass/assland/asschange/queryAssChangeLandStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssChangeLandStates(@RequestParam Map<String, Object> mapVo, Model mode)
		throws Exception {

	mapVo.put("group_id", SessionManager.getGroupId());

	mapVo.put("hos_id", SessionManager.getHosId());

	mapVo.put("copy_code", SessionManager.getCopyCode());
	
	//入库单状态查询  （打印时校验数据专用）
	List<String> list = assChangeLandService.queryAssChangeLandStates(mapVo);
	
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
