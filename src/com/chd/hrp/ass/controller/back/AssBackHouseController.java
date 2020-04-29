/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.back;
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
import com.chd.hrp.ass.entity.back.AssBackDetailHouse;
import com.chd.hrp.ass.entity.back.AssBackHouse;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.service.back.AssBackDetailHouseService;
import com.chd.hrp.ass.service.back.AssBackHouseService;
import com.chd.hrp.ass.service.card.AssCardHouseService;
/**
 * 
 * @Description:
 * 050701 资产退货单主表(房屋及建筑物)
 * @Table:
 * ASS_BACK_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssBackHouseController extends BaseController{

	private static Logger logger = Logger.getLogger(AssBackHouseController.class);

	// 引入Service服务
	@Resource(name = "assBackHouseService")
	private final AssBackHouseService assBackHouseService = null;
	
	@Resource(name = "assBackDetailHouseService")
	private final AssBackDetailHouseService assBackDetailHouseService = null;
	
	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assback/assBackHouseMainPage", method = RequestMethod.GET)
	public String assBackHouseMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05055",MyConfig.getSysPara("05055"));
		
		return "hrp/ass/asshouse/assback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assback/assBackHouseAddPage", method = RequestMethod.GET)
	public String assBackHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05055",MyConfig.getSysPara("05055"));
		
		return "hrp/ass/asshouse/assback/add";

	}
	

	/**
	 * @Description 添加数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assback/saveAssBackMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssBackHouse assBackHouse = assBackHouseService.queryByCode(mapVo);
		if(assBackHouse != null){
			if(assBackHouse.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackHouseJson = assBackHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackHouseJson);

	}
	
	
	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assback/updateConfirmBackHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			mapVo.put("back_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("dispose_type", 41);
			
			mapVo.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("confirm_emp", SessionManager.getUserId());
			AssBackHouse assBackHouse = assBackHouseService.queryByCode(mapVo);
			if (assBackHouse.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assBackHouse.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			List<AssBackDetailHouse> list = assBackDetailHouseService.queryByAssBackNo(mapVo);
			
			if(list != null && list.size() > 0){
				for(AssBackDetailHouse assBackDetailHouse : list){
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailHouse.getGroup_id());
					listCardMap.put("hos_id", assBackDetailHouse.getHos_id());
					listCardMap.put("copy_code", assBackDetailHouse.getCopy_code());
					listCardMap.put("dispose_type", 41);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailHouse.getAss_card_no());
					AssCardHouse assCard = assCardHouseService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if(assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackDetailHouse.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackDetailHouse.getAss_card_no(), assBackDetailHouse.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				flag = false;
				break;
			}
			
			if (assBackHouse.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能退库确认! \"}");
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复退货! \"}");
		}
		
		try {
			assInMainJson = assBackHouseService.updateBackConfirm(listVo,listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	

	/**
	 * @Description 更新跳转页面 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assback/assBackHouseUpdatePage", method = RequestMethod.GET)
	public String assBackHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackHouse assBackHouse = new AssBackHouse();

		assBackHouse = assBackHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackHouse.getGroup_id());
		mode.addAttribute("hos_id", assBackHouse.getHos_id());
		mode.addAttribute("copy_code", assBackHouse.getCopy_code());
		mode.addAttribute("ass_back_no", assBackHouse.getAss_back_no());
	
		mode.addAttribute("ven_id", assBackHouse.getVen_id());
		mode.addAttribute("ven_no", assBackHouse.getVen_no());
		mode.addAttribute("ven_code", assBackHouse.getVen_code());
		mode.addAttribute("ven_name", assBackHouse.getVen_name());
		mode.addAttribute("back_money", assBackHouse.getBack_money());
		mode.addAttribute("note", assBackHouse.getNote());
		mode.addAttribute("create_emp", assBackHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assBackHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("back_date", DateUtil.dateToString(assBackHouse.getBack_date(), "yyyy-MM-dd"));
		mode.addAttribute("confirm_emp", assBackHouse.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assBackHouse.getConfirm_emp_name());
		mode.addAttribute("state", assBackHouse.getState());
		mode.addAttribute("state_name", assBackHouse.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05055",MyConfig.getSysPara("05055"));
		
		return "hrp/ass/asshouse/assback/update";
	}


	/**
	 * @Description 删除数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assback/deleteAssBackMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackHouse assBackHouse = assBackHouseService.queryByCode(mapVo);
			if(assBackHouse != null){
				if(assBackHouse.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackHouseJson = assBackHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackHouseJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assback/queryAssBackMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackHouse = null; 
		
		if("0".equals(mapVo.get("show_detail").toString())){
		
			assBackHouse = assBackHouseService.query(getPage(mapVo));

		}else{
			
			assBackHouse = assBackHouseService.queryDetails(getPage(mapVo));
			
		}
		return JSONObject.parseObject(assBackHouse);

	}
	
	
	@RequestMapping(value = "/hrp/ass/asshouse/assback/deleteAssBackDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackDetailHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("ass_back_no", ids[3])   ;
				mapVo.put("ass_card_no", ids[4]);
				AssBackHouse assBackHouse = assBackHouseService.queryByCode(mapVo);
				if(assBackHouse != null){
					if(assBackHouse.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assBackDetailHouseJson = assBackDetailHouseService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assBackDetailHouseJson);
			
	}
	
	
	@RequestMapping(value = "/hrp/ass/asshouse/assback/queryAssBackDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailHouse = assBackDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailHouse);
		
	}
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/asshouse/assback/queryAssBackHouseStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssBackHouseStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assBackHouseService.queryAssBackHouseStates(mapVo);

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

