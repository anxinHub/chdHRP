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
import com.chd.hrp.ass.entity.change.AssChangeHouse;
import com.chd.hrp.ass.service.change.AssChangeDetailHouseService;
import com.chd.hrp.ass.service.change.AssChangeHouseService;

/**
 * 
 * @Description: 050805 资产原值变动(房屋及建筑物)
 * @Table: ASS_Change_HOUSE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssChangeHouseController extends BaseController {

	private static Logger logger = Logger.getLogger(AssChangeHouseController.class);

	// 引入Service服务
	@Resource(name = "assChangeHouseService")
	private final AssChangeHouseService assChangeHouseService = null;

	@Resource(name = "assChangeDetailHouseService")
	private final AssChangeDetailHouseService assChangeDetailHouseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/assChangeHouseMainPage", method = RequestMethod.GET)
	public String assChangeHouseMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05061",MyConfig.getSysPara("05061"));
		
		return "hrp/ass/asshouse/asschange/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/assChangeHouseAddPage", method = RequestMethod.GET)
	public String assChangeHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asschange/add";

	}

	/**
	 * @Description 添加数据 050805 资产原值变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/saveAssChangeHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());
		
		//DateUtil.dateToString(mapVo.get("create_date"), "yyyyMMdd");
		//String[] ye=(mapVo.get("create_date").toString()).split("-");
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

		AssChangeHouse assChangeHouse = assChangeHouseService.queryByCode(mapVo);

		if (assChangeHouse != null) {
			if (assChangeHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeHouseJson = assChangeHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChangeHouseJson);

	}

	@RequestMapping(value = "/hrp/ass/asshouse/asschange/updateConfirmChangeHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChangeHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeHouse assChangeHouse = assChangeHouseService.queryByCode(mapVo);

			if (assChangeHouse.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assChangeHouseService.updateConfirm(listVo);
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
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/assChangeHouseUpdatePage", method = RequestMethod.GET)
	public String assChangeHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeHouse assChangeHouse = new AssChangeHouse();

		assChangeHouse = assChangeHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChangeHouse.getGroup_id());
		mode.addAttribute("hos_id", assChangeHouse.getHos_id());
		mode.addAttribute("copy_code", assChangeHouse.getCopy_code());
		mode.addAttribute("change_no", assChangeHouse.getChange_no());
		mode.addAttribute("ven_id", assChangeHouse.getVen_id());
		mode.addAttribute("ven_no", assChangeHouse.getVen_no());
		mode.addAttribute("ven_code", assChangeHouse.getVen_code());
		mode.addAttribute("ven_name", assChangeHouse.getVen_name());
		mode.addAttribute("create_emp", assChangeHouse.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assChangeHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChangeHouse.getAudit_emp());
		mode.addAttribute("change_date", assChangeHouse.getChange_date());
		mode.addAttribute("state", assChangeHouse.getState());
		mode.addAttribute("note", assChangeHouse.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05061",MyConfig.getSysPara("05061"));
		
		return "hrp/ass/asshouse/asschange/update";
	}

	/**
	 * @Description 删除数据 050805 资产原值变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/deleteAssChangeHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeHouse assChangeHouse = assChangeHouseService.queryByCode(mapVo);

			if (assChangeHouse != null) {
				if (assChangeHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeHouseJson = assChangeHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeHouseJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/queryAssChangeHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeHouse = assChangeHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeHouse);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/deleteAssChangeDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeDetailHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeHouse assChangeHouse = assChangeHouseService.queryByCode(mapVo);

			if (assChangeHouse != null) {
				if (assChangeHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetailHouseJson = assChangeDetailHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailHouseJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/queryAssChangeDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeDetailHouse = assChangeDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeDetailHouse);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/deleteAssChangeSourceHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeSourceHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeHouse assChangeHouse = assChangeHouseService.queryByCode(mapVo);

			if (assChangeHouse != null) {
				if (assChangeHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourceHouseJson = assChangeHouseService.deleteAssChangeSourceHouse(listVo);

		return JSONObject.parseObject(assChangeSourceHouseJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/queryAssChangeSourceHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeSourceHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assChangeSourceHouse = assChangeHouseService.queryAssChangeSourceHouse(getPage(mapVo));

		return JSONObject.parseObject(assChangeSourceHouse);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asschange/saveAssChangeSourceHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeSourceHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeHouse assChangeHouse = assChangeHouseService.queryByCode(mapVo);

		if (assChangeHouse != null) {
			if (assChangeHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeSourceHouseJson = assChangeHouseService.saveAssChangeSourceHouse(mapVo);

		return JSONObject.parseObject(assChangeSourceHouseJson);

	}
	
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/asshouse/asschange/queryAssChangeHouseStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssChangeHouseStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assChangeHouseService.queryAssChangeHouseStates(mapVo);

if(list.size() == 0){

return JSONObject.parseObject("{\"state\":\"true\"}");

}else{

String  str = "" ;
for(String item : list){
	
	str += item +"," ;
}

return JSONObject.parseObject("{\"error\":\"变动单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}
}
