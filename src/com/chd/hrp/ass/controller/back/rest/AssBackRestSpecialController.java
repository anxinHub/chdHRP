

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
import com.chd.hrp.ass.entity.back.rest.AssBackRestDetailSpecial;
import com.chd.hrp.ass.entity.back.rest.AssBackRestSpecial;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.service.back.rest.AssBackRestDetailSpecialService;
import com.chd.hrp.ass.service.back.rest.AssBackRestSpecialService;
import com.chd.hrp.ass.service.card.AssCardSpecialService;


@Controller
public class AssBackRestSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBackRestSpecialController.class);

	// 引入Service服务
	@Resource(name = "assBackRestSpecialService")
	private final AssBackRestSpecialService assBackRestSpecialService = null;
	
	@Resource(name = "assBackRestDetailSpecialService")
	private final AssBackRestDetailSpecialService assBackRestDetailSpecialService = null;
	
	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/assBackRestSpecialMainPage", method = RequestMethod.GET)
	public String assBackRestSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05017",MyConfig.getSysPara("05017"));
		
		return "hrp/ass/assspecial/assrestback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/assBackRestSpecialAddPage", method = RequestMethod.GET)
	public String assBackRestSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/assrestback/add";

	}

	/**
	 * @Description 添加数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/saveAssBackRestMainSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackRestMainSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssBackRestSpecial assBackRestSpecial = assBackRestSpecialService.queryByCode(mapVo);
		if(assBackRestSpecial != null){
			if(assBackRestSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackSpecialJson = assBackRestSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackSpecialJson);

	}
	
	
	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/updateConfirmBackRestSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmBackRestSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			mapVo.put("back_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("confirm_emp", SessionManager.getUserId());
			AssBackRestSpecial assBackRestSpecial = assBackRestSpecialService.queryByCode(mapVo);
			if (assBackRestSpecial.getState() == 2) {
				continue;
			}
			
			if(DateUtil.compareDate(assBackRestSpecial.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			List<AssBackRestDetailSpecial> list = assBackRestDetailSpecialService.queryByAssBackNo(mapVo);
			
			if(list != null && list.size() > 0){
				for(AssBackRestDetailSpecial assBackRestDetailSpecial : list){
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackRestDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assBackRestDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assBackRestDetailSpecial.getCopy_code());
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("dispose_type", 41);
					listCardMap.put("ass_card_no", assBackRestDetailSpecial.getAss_card_no());
					AssCardSpecial assCard = assCardSpecialService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if(assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackRestDetailSpecial.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackRestDetailSpecial.getAss_card_no(), assBackRestDetailSpecial.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能退库确认! \"}");
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复退货! \"}");
		}
		
		try {
			assInMainJson = assBackRestSpecialService.updateBackConfirm(listVo,listCardVo);
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
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/assBackRestSpecialUpdatePage", method = RequestMethod.GET)
	public String assBackRestSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackRestSpecial assBackSpecial = new AssBackRestSpecial();

		assBackSpecial = assBackRestSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackSpecial.getGroup_id());
		mode.addAttribute("hos_id", assBackSpecial.getHos_id());
		mode.addAttribute("copy_code", assBackSpecial.getCopy_code());
		mode.addAttribute("ass_back_no", assBackSpecial.getAss_back_no());
		mode.addAttribute("store_id", assBackSpecial.getStore_id());
		mode.addAttribute("store_no", assBackSpecial.getStore_no());
		mode.addAttribute("store_code", assBackSpecial.getStore_code());
		mode.addAttribute("store_name", assBackSpecial.getStore_name());
		mode.addAttribute("bus_type_code", assBackSpecial.getBus_type_code());
		mode.addAttribute("bus_type_name", assBackSpecial.getBus_type_name());
		mode.addAttribute("back_money", assBackSpecial.getBack_money());
		mode.addAttribute("note", assBackSpecial.getNote());
		mode.addAttribute("create_emp", assBackSpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assBackSpecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackSpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("back_date", DateUtil.dateToString(assBackSpecial.getBack_date(), "yyyy-MM-dd"));
		mode.addAttribute("confirm_emp", assBackSpecial.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assBackSpecial.getConfirm_emp_name());
		mode.addAttribute("state", assBackSpecial.getState());
		mode.addAttribute("state_name", assBackSpecial.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05017",MyConfig.getSysPara("05017"));
		
		return "hrp/ass/assspecial/assrestback/update";
	}


	/**
	 * @Description 删除数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/deleteAssBackRestMainSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackRestSpecial assBackRestSpecial = assBackRestSpecialService.queryByCode(mapVo);
			if(assBackRestSpecial != null){
				if(assBackRestSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackSpecialJson = assBackRestSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackSpecialJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/queryAssBackRestMainSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackSpecial = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			 assBackSpecial = assBackRestSpecialService.query(getPage(mapVo));
			 
		}else{
			
			 assBackSpecial = assBackRestSpecialService.queryDetails(getPage(mapVo));
			
		}
		

		return JSONObject.parseObject(assBackSpecial);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/deleteAssBackRestDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestDetailSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssBackRestSpecial assBackRestSpecial = assBackRestSpecialService.queryByCode(mapVo);
				if(assBackRestSpecial != null){
					if(assBackRestSpecial.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assBackDetailSpecialJson = assBackRestDetailSpecialService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assBackDetailSpecialJson);
			
	}
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/queryAssBackRestDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailSpecial = assBackRestDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);
		
	}
	
	/**
	 * 专用设备 其他退货 退货单状态查询 (批量打印 校验数据用)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assrestback/queryAssBackRestSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assBackRestSpecialService.queryAssBackRestSpecialState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"退货单【"+str+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
}
