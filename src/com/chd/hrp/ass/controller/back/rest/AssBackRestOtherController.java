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
import com.chd.hrp.ass.entity.back.rest.AssBackRestDetailOther;
import com.chd.hrp.ass.entity.back.rest.AssBackRestOther;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.service.back.rest.AssBackRestDetailOtherService;
import com.chd.hrp.ass.service.back.rest.AssBackRestOtherService;
import com.chd.hrp.ass.service.card.AssCardOtherService;
/**
 * 
 * @Description:
 * 050701 资产其他退货单主表(其他固定资产)
 * @Table:
 * ASS_BACK_REST_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssBackRestOtherController extends BaseController{

	private static Logger logger = Logger.getLogger(AssBackRestOtherController.class);

	// 引入Service服务
	@Resource(name = "assBackRestOtherService")
	private final AssBackRestOtherService assBackRestOtherService = null;
	
	@Resource(name = "assBackRestDetailOtherService")
	private final AssBackRestDetailOtherService assBackRestDetailOtherService = null;
	
	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestback/assBackRestOtherMainPage", method = RequestMethod.GET)
	public String assBackRestOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05037",MyConfig.getSysPara("05037"));
		
		return "hrp/ass/assother/assrestback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestback/assBackRestOtherAddPage", method = RequestMethod.GET)
	public String assBackRestOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assrestback/add";

	}

	/**
	 * @Description 添加数据 050701 资产退货单主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestback/saveAssBackRestMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackRestMainOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssBackRestOther assBackRestOther = assBackRestOtherService.queryByCode(mapVo);
		if(assBackRestOther != null){
			if(assBackRestOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackOtherJson = assBackRestOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackOtherJson);

	}
	
	
	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestback/updateConfirmBackRestOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmBackRestOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssBackRestOther assBackRestOther = assBackRestOtherService.queryByCode(mapVo);
			if (assBackRestOther.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assBackRestOther.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			List<AssBackRestDetailOther> list = assBackRestDetailOtherService.queryByAssBackNo(mapVo);
			
			if(list != null && list.size() > 0){
				for(AssBackRestDetailOther assBackRestDetailOther : list){
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackRestDetailOther.getGroup_id());
					listCardMap.put("hos_id", assBackRestDetailOther.getHos_id());
					listCardMap.put("copy_code", assBackRestDetailOther.getCopy_code());
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("dispose_type", 41);
					listCardMap.put("ass_card_no", assBackRestDetailOther.getAss_card_no());
					AssCardOther assCard = assCardOtherService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if(assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackRestDetailOther.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackRestDetailOther.getAss_card_no(), assBackRestDetailOther.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				flag = false;
				break;
			}
			
			if (assBackRestOther.getState() == 2) {
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
			assInMainJson = assBackRestOtherService.updateBackConfirm(listVo,listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	

	/**
	 * @Description 更新跳转页面 050701 资产退货单主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestback/assBackRestOtherUpdatePage", method = RequestMethod.GET)
	public String assBackRestOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackRestOther assBackOther = new AssBackRestOther();

		assBackOther = assBackRestOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackOther.getGroup_id());
		mode.addAttribute("hos_id", assBackOther.getHos_id());
		mode.addAttribute("copy_code", assBackOther.getCopy_code());
		mode.addAttribute("ass_back_no", assBackOther.getAss_back_no());
		mode.addAttribute("store_id", assBackOther.getStore_id());
		mode.addAttribute("store_no", assBackOther.getStore_no());
		mode.addAttribute("store_code", assBackOther.getStore_code());
		mode.addAttribute("store_name", assBackOther.getStore_name());
		mode.addAttribute("bus_type_code", assBackOther.getBus_type_code());
		mode.addAttribute("bus_type_name", assBackOther.getBus_type_name());
		mode.addAttribute("back_money", assBackOther.getBack_money());
		mode.addAttribute("note", assBackOther.getNote());
		mode.addAttribute("create_emp", assBackOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assBackOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("back_date", DateUtil.dateToString(assBackOther.getBack_date(), "yyyy-MM-dd"));
		mode.addAttribute("confirm_emp", assBackOther.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assBackOther.getConfirm_emp_name());
		mode.addAttribute("state", assBackOther.getState());
		mode.addAttribute("state_name", assBackOther.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05037",MyConfig.getSysPara("05037"));
		
		return "hrp/ass/assother/assrestback/update";
	}


	/**
	 * @Description 删除数据 050701 资产退货单主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestback/deleteAssBackRestMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackRestOther assBackRestOther = assBackRestOtherService.queryByCode(mapVo);
			if(assBackRestOther != null){
				if(assBackRestOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackOtherJson = assBackRestOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackOtherJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestback/queryAssBackRestMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackOther = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackOther = assBackRestOtherService.query(getPage(mapVo));
			
		}else{
			
			assBackOther = assBackRestOtherService.queryDetails(getPage(mapVo));
			
		}
		
			

		return JSONObject.parseObject(assBackOther);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assother/assrestback/deleteAssBackRestDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestDetailOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssBackRestOther assBackRestOther = assBackRestOtherService.queryByCode(mapVo);
				if(assBackRestOther != null){
					if(assBackRestOther.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assBackDetailOtherJson = assBackRestDetailOtherService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assBackDetailOtherJson);
			
	}
	
	
	@RequestMapping(value = "/hrp/ass/assother/assrestback/queryAssBackRestDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestDetailOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailOther = assBackRestDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailOther);
		
	}
	
	/**
	 * 其他固定资产 其他退货 退货单状态查询 (批量打印 校验数据用)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestback/queryAssBackRestOtherState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestOtherState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//其他固定资产 其他退货   退货单状态查询  （打印时校验数据专用）
		List<String> list = assBackRestOtherService.queryAssBackRestOtherState(mapVo);
		
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

