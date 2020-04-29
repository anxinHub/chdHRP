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
import com.chd.hrp.ass.entity.back.rest.AssBackRestDetailGeneral;
import com.chd.hrp.ass.entity.back.rest.AssBackRestGeneral;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.service.back.rest.AssBackRestDetailGeneralService;
import com.chd.hrp.ass.service.back.rest.AssBackRestGeneralService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
/**
 * 
 * @Description:
 * 050701 资产其他退货单主表(一般设备)
 * @Table:
 * ASS_BACK_REST_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssBackRestGeneralController extends BaseController{

	private static Logger logger = Logger.getLogger(AssBackRestGeneralController.class);

	// 引入Service服务
	@Resource(name = "assBackRestGeneralService")
	private final AssBackRestGeneralService assBackRestGeneralService = null;
	
	@Resource(name = "assBackRestDetailGeneralService")
	private final AssBackRestDetailGeneralService assBackRestDetailGeneralService = null;
	
	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/assBackRestGeneralMainPage", method = RequestMethod.GET)
	public String assBackRestGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05027",MyConfig.getSysPara("05027"));
		
		return "hrp/ass/assgeneral/assrestback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/assBackRestGeneralAddPage", method = RequestMethod.GET)
	public String assBackRestGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assrestback/add";

	}

	/**
	 * @Description 添加数据 050701 资产退货单主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/saveAssBackRestMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackRestMainGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssBackRestGeneral assBackRestGeneral = assBackRestGeneralService.queryByCode(mapVo);
		if(assBackRestGeneral != null){
			if(assBackRestGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackGeneralJson = assBackRestGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackGeneralJson);

	}
	
	
	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/updateConfirmBackRestGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmBackRestGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackRestGeneral assBackRestGeneral = assBackRestGeneralService.queryByCode(mapVo);
			if (assBackRestGeneral.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assBackRestGeneral.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			List<AssBackRestDetailGeneral> list = assBackRestDetailGeneralService.queryByAssBackNo(mapVo);
			
			if(list != null && list.size() > 0){
				for(AssBackRestDetailGeneral assBackRestDetailGeneral : list){
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackRestDetailGeneral.getGroup_id());
					listCardMap.put("hos_id", assBackRestDetailGeneral.getHos_id());
					listCardMap.put("copy_code", assBackRestDetailGeneral.getCopy_code());
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("dispose_type", 41);
					listCardMap.put("ass_card_no", assBackRestDetailGeneral.getAss_card_no());
					AssCardGeneral assCard = assCardGeneralService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if(assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackRestDetailGeneral.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackRestDetailGeneral.getAss_card_no(), assBackRestDetailGeneral.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				flag = false;
				break;
			}

		
			
			if (assBackRestGeneral.getState() == 2) {
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
			assInMainJson = assBackRestGeneralService.updateBackConfirm(listVo,listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	

	/**
	 * @Description 更新跳转页面 050701 资产退货单主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/assBackRestGeneralUpdatePage", method = RequestMethod.GET)
	public String assBackRestGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackRestGeneral assBackGeneral = new AssBackRestGeneral();

		assBackGeneral = assBackRestGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackGeneral.getGroup_id());
		mode.addAttribute("hos_id", assBackGeneral.getHos_id());
		mode.addAttribute("copy_code", assBackGeneral.getCopy_code());
		mode.addAttribute("ass_back_no", assBackGeneral.getAss_back_no());
		mode.addAttribute("store_id", assBackGeneral.getStore_id());
		mode.addAttribute("store_no", assBackGeneral.getStore_no());
		mode.addAttribute("store_code", assBackGeneral.getStore_code());
		mode.addAttribute("store_name", assBackGeneral.getStore_name());
		mode.addAttribute("bus_type_code", assBackGeneral.getBus_type_code());
		mode.addAttribute("bus_type_name", assBackGeneral.getBus_type_name());
		mode.addAttribute("back_money", assBackGeneral.getBack_money());
		mode.addAttribute("note", assBackGeneral.getNote());
		mode.addAttribute("create_emp", assBackGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assBackGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("back_date", DateUtil.dateToString(assBackGeneral.getBack_date(), "yyyy-MM-dd"));
		mode.addAttribute("confirm_emp", assBackGeneral.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assBackGeneral.getConfirm_emp_name());
		mode.addAttribute("state", assBackGeneral.getState());
		mode.addAttribute("state_name", assBackGeneral.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05027",MyConfig.getSysPara("05027"));
		
		return "hrp/ass/assgeneral/assrestback/update";
	}


	/**
	 * @Description 删除数据 050701 资产退货单主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/deleteAssBackRestMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackRestGeneral assBackRestGeneral = assBackRestGeneralService.queryByCode(mapVo);
			if(assBackRestGeneral != null){
				if(assBackRestGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackGeneralJson = assBackRestGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackGeneralJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/queryAssBackRestMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackGeneral = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackGeneral = assBackRestGeneralService.query(getPage(mapVo));
			
		}else{

			assBackGeneral = assBackRestGeneralService.queryDetails(getPage(mapVo));
			
		}
			

		return JSONObject.parseObject(assBackGeneral);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/deleteAssBackRestDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestDetailGeneral(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssBackRestGeneral assBackRestGeneral = assBackRestGeneralService.queryByCode(mapVo);
				if(assBackRestGeneral != null){
					if(assBackRestGeneral.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assBackDetailGeneralJson = assBackRestDetailGeneralService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assBackDetailGeneralJson);
			
	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/queryAssBackRestDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailGeneral = assBackRestDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailGeneral);
		
	}
	
	/**
	 * 一般设备 其他退货 退货单状态查询 (批量打印 校验数据用)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestback/queryAssBackRestGeneralState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestGeneralState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assBackRestGeneralService.queryAssBackRestGeneralState(mapVo);
		
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

