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
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailGeneral;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutGeneral;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailGeneralService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutGeneralService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单主表（一般设备）
 * @Table:
 * ASS_ALLOT_OUT_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssAllotOutGeneralController extends BaseController{

	private static Logger logger = Logger.getLogger(AssAllotOutGeneralController.class);

	// 引入Service服务
	@Resource(name = "assAllotOutGeneralService")
	private final AssAllotOutGeneralService assAllotOutGeneralService = null;

	@Resource(name = "assAllotOutDetailGeneralService")
	private final AssAllotOutDetailGeneralService assAllotOutDetailGeneralService = null;
	
	@Resource(name="assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService=null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/assAllotOutGeneralMainPage", method = RequestMethod.GET)
	public String assAllotOutGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05028",MyConfig.getSysPara("05028"));
		
		return "hrp/ass/assgeneral/assallotout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/assAllotOutGeneralAddPage", method = RequestMethod.GET)
	public String assAllotOutGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assallotout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/saveAssAllotOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssAllotOutGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssAllotOutGeneral assBackGeneral = assAllotOutGeneralService.queryByCode(mapVo);
		if(assBackGeneral != null){
			if(assBackGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assAllotOutGeneralJson = assAllotOutGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assAllotOutGeneralJson);

	}
	
	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/updateConfirmAllotOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAllotOutGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			mapVo.put("audit_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("audit_emp", SessionManager.getUserId());
			AssAllotOutGeneral assAllotOutGeneral = assAllotOutGeneralService.queryByCode(mapVo);
			if (assAllotOutGeneral.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assAllotOutGeneral.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			List<AssAllotOutDetailGeneral> list = assAllotOutDetailGeneralService.queryByAssAllotOutNo(mapVo);
			
			if(list != null && list.size() > 0){
				for(AssAllotOutDetailGeneral assBackDetailGeneral : list){
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailGeneral.getGroup_id());
					listCardMap.put("hos_id", assBackDetailGeneral.getHos_id());
					listCardMap.put("copy_code", assBackDetailGeneral.getCopy_code());
					listCardMap.put("dispose_type", 31);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailGeneral.getAss_card_no());
					AssCardGeneral assCardGeneral	=assCardGeneralService.queryByCode(listCardMap);
					if (assCardGeneral==null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardGeneral.getDispose_type()!=null && 31==assCardGeneral.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if(map.containsKey(assBackDetailGeneral.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailGeneral.getAss_card_no(), assBackDetailGeneral.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				flag = false;
				break;
			}

			
			
			if (assAllotOutGeneral.getState() == 2) {
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
			assInMainJson = assAllotOutGeneralService.updateAllotOutConfirm(listVo,listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/assAllotOutGeneralUpdatePage", method = RequestMethod.GET)
	public String assAllotOutGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssAllotOutGeneral assAllotOutGeneral = new AssAllotOutGeneral();

		assAllotOutGeneral = assAllotOutGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assAllotOutGeneral.getGroup_id());
		mode.addAttribute("hos_id", assAllotOutGeneral.getHos_id());
		mode.addAttribute("copy_code", assAllotOutGeneral.getCopy_code());
		mode.addAttribute("allot_out_no", assAllotOutGeneral.getAllot_out_no());
		mode.addAttribute("out_store_id", assAllotOutGeneral.getOut_store_id());
		mode.addAttribute("out_store_no", assAllotOutGeneral.getOut_store_no());
		mode.addAttribute("out_store_code", assAllotOutGeneral.getOut_store_code());
		mode.addAttribute("out_store_name", assAllotOutGeneral.getOut_store_name());
		mode.addAttribute("in_group_id", assAllotOutGeneral.getIn_group_id());
		mode.addAttribute("in_group_name", assAllotOutGeneral.getIn_group_name());
		mode.addAttribute("in_hos_id", assAllotOutGeneral.getIn_hos_id());
		mode.addAttribute("in_hos_name", assAllotOutGeneral.getIn_hos_name());
		mode.addAttribute("in_store_id", assAllotOutGeneral.getIn_store_id());
		mode.addAttribute("in_store_no", assAllotOutGeneral.getIn_store_no());
		mode.addAttribute("in_store_code", assAllotOutGeneral.getIn_store_code());
		mode.addAttribute("in_store_name", assAllotOutGeneral.getIn_store_name());
		mode.addAttribute("price", assAllotOutGeneral.getPrice());
		mode.addAttribute("add_depre", assAllotOutGeneral.getAdd_depre());
		mode.addAttribute("cur_money", assAllotOutGeneral.getCur_money());
		mode.addAttribute("fore_money", assAllotOutGeneral.getFore_money());
		mode.addAttribute("create_emp", assAllotOutGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotOutGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotOutGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotOutGeneral.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotOutGeneral.getAudit_emp_name());
		mode.addAttribute("audit_date", assAllotOutGeneral.getAudit_date());
		mode.addAttribute("state", assAllotOutGeneral.getState());
		mode.addAttribute("state_name", assAllotOutGeneral.getState_name());
		mode.addAttribute("note", assAllotOutGeneral.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05028",MyConfig.getSysPara("05028"));
		
		return "hrp/ass/assgeneral/assallotout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/deleteAssAllotOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssAllotOutGeneral assAllotOutGeneral = assAllotOutGeneralService.queryByCode(mapVo);
			if(assAllotOutGeneral != null){
				if(assAllotOutGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assAllotOutGeneralJson = assAllotOutGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/queryAssAllotOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutGeneral = null;

		if("0".equals(mapVo.get("show_detail").toString())){
			assAllotOutGeneral = assAllotOutGeneralService.query(getPage(mapVo));
		}else{
			assAllotOutGeneral = assAllotOutGeneralService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assAllotOutGeneral);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/deleteAssAllotOutDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutDetailGeneral(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

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
			AssAllotOutGeneral assAllotOutGeneral = assAllotOutGeneralService.queryByCode(mapVo);
			if(assAllotOutGeneral != null){
				if(assAllotOutGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assAllotOutDetailGeneralJson = assAllotOutDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutDetailGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/queryAssAllotOutDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutDetailGeneral = assAllotOutDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotOutDetailGeneral);

	}
	
	/**
	 * 一般设备 资产调剂出口库 出库单状态查询（打印校验数据用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotout/queryAssAllotOutGeneralState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutGeneralState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assAllotOutGeneralService.queryAssAllotOutGeneralState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"出库单【"+str+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
}

