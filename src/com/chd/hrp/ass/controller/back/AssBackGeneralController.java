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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.back.AssBackDetailGeneral;
import com.chd.hrp.ass.entity.back.AssBackGeneral;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.in.AssInMainGeneral;
import com.chd.hrp.ass.service.back.AssBackDetailGeneralService;
import com.chd.hrp.ass.service.back.AssBackGeneralService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.in.AssInDetailGeneralService;
import com.chd.hrp.ass.service.in.AssInMainGeneralService;
/**
 * 
 * @Description:
 * 050701 资产退货单主表(一般设备)
 * @Table:
 * ASS_BACK_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssBackGeneralController extends BaseController{ 

	private static Logger logger = Logger.getLogger(AssBackGeneralController.class);

	// 引入Service服务
	@Resource(name = "assBackGeneralService")
	private final AssBackGeneralService assBackGeneralService = null;
	
	@Resource(name = "assBackDetailGeneralService")
	private final AssBackDetailGeneralService assBackDetailGeneralService = null;
	
	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;
	
	@Resource(name = "assInMainGeneralService")
	private final AssInMainGeneralService assInMainGeneralService = null;
	
	@Resource(name = "assInDetailGeneralService")
	private final AssInDetailGeneralService assInDetailGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/assBackGeneralMainPage", method = RequestMethod.GET)
	public String assBackGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05025",MyConfig.getSysPara("05025"));
		
		return "hrp/ass/assgeneral/assback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/assBackGeneralAddPage", method = RequestMethod.GET)
	public String assBackGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05025",MyConfig.getSysPara("05025"));
		
		return "hrp/ass/assgeneral/assback/add";

	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/assBackMainGeneralBatchPage", method = RequestMethod.GET)
	public String assBackMainGeneralBatchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		
		mode.addAttribute("ven_id", mapVo.get("ven_id"));
		mode.addAttribute("ven_no", mapVo.get("ven_no"));
		mode.addAttribute("ven_code", mapVo.get("ven_code"));
		mode.addAttribute("ven_name", mapVo.get("ven_name"));
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assback/assBatchmain";
	}

	/**
	 * 引入采购入库
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/assBackMainGeneralPage", method = RequestMethod.GET)
	public String assBackMainGeneralPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		
		mode.addAttribute("ven_id", mapVo.get("ven_id"));
		mode.addAttribute("ven_no", mapVo.get("ven_no"));
		mode.addAttribute("ven_code", mapVo.get("ven_code"));
		mode.addAttribute("ven_name", mapVo.get("ven_name"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assgeneral/assback/assImportmain";
	}
	
	/**
	 * 主页面店家引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/assViewBackPage", method = RequestMethod.GET)
	public String assViewBackPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("ass_back_no", mapVo.get("ass_back_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assgeneral/assback/assGeneralViewBack";
	}
	
	/**
	 * @Description 查询数据 资产入库主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/queryAssInMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMainSpecial  = assInMainGeneralService.queryInMap(getPage(mapVo));
			 

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	/**
	 * 保存入库明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/addAssBackGeneralImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackGeneralImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		
		String assBackGeneral = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		assBackGeneral = assBackGeneralService.assImportBackIn(mapVo);
		return JSONObject.parseObject(assBackGeneral);
		
	}
	
	/**
	 * @Description 更新跳转页面 资产入库主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/assInMainGeneralUpdatePage", method = RequestMethod.GET)
	public String assInMainGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInMainGeneral assInMainSpecial = new AssInMainGeneral();

		assInMainSpecial = assInMainGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInMainSpecial.getGroup_id());
		mode.addAttribute("hos_id", assInMainSpecial.getHos_id());
		mode.addAttribute("copy_code", assInMainSpecial.getCopy_code());
		mode.addAttribute("ass_in_no", assInMainSpecial.getAss_in_no());
		mode.addAttribute("store_id", assInMainSpecial.getStore_id());
		mode.addAttribute("store_no", assInMainSpecial.getStore_no());
		mode.addAttribute("ven_id", assInMainSpecial.getVen_id());
		mode.addAttribute("ven_no", assInMainSpecial.getVen_no());
		mode.addAttribute("purc_emp", assInMainSpecial.getPurc_emp());
		mode.addAttribute("dept_id", assInMainSpecial.getDept_id());
		mode.addAttribute("dept_no", assInMainSpecial.getDept_no());
		mode.addAttribute("ass_purpose", assInMainSpecial.getAss_purpose());
		mode.addAttribute("proj_id", assInMainSpecial.getProj_id());
		mode.addAttribute("proj_no", assInMainSpecial.getProj_no());
		mode.addAttribute("store_code", assInMainSpecial.getStore_code());
		mode.addAttribute("store_name", assInMainSpecial.getStore_name());
		mode.addAttribute("ven_code", assInMainSpecial.getVen_code());
		mode.addAttribute("ven_name", assInMainSpecial.getVen_name());
		mode.addAttribute("purc_emp_name", assInMainSpecial.getPurc_emp_name());
		mode.addAttribute("dept_code", assInMainSpecial.getDept_code());
		mode.addAttribute("dept_name", assInMainSpecial.getDept_name());
		mode.addAttribute("ass_purpose_name", assInMainSpecial.getAss_purpose_name());
		mode.addAttribute("proj_code", assInMainSpecial.getProj_code());
		mode.addAttribute("proj_name", assInMainSpecial.getProj_name());
		mode.addAttribute("in_money", assInMainSpecial.getIn_money());
		mode.addAttribute("note", assInMainSpecial.getNote());
		mode.addAttribute("create_emp", assInMainSpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assInMainSpecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assInMainSpecial.getCreate_date(),"yyyy-MM-dd"));
		mode.addAttribute("in_date", assInMainSpecial.getIn_date());
		mode.addAttribute("confirm_emp", assInMainSpecial.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assInMainSpecial.getConfirm_emp_name());
		mode.addAttribute("state", assInMainSpecial.getState());
		mode.addAttribute("state_name", assInMainSpecial.getState_name());
		mode.addAttribute("invoice_no", assInMainSpecial.getInvoice_no());
		mode.addAttribute("invoice_date", assInMainSpecial.getInvoice_date());
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		
		return "hrp/ass/assgeneral/assback/assImportUpate";
	}
	
	/**
	 * @Description 查询数据 资产入库明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/queryAssInDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInDetailSpecial = assInDetailGeneralService.query(getPage(mapVo));
		return JSONObject.parseObject(assInDetailSpecial);
	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/queryAssInCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardGeneralService.query(getPage(mapVo));
		JSONObject json = JSONObject.parseObject(assCard);
		JSONArray jsonarray = JSONArray.parseArray(json.get("Rows").toString());
		for(int i = 0 ; i < jsonarray.size(); i ++){
			JSONObject job = jsonarray.getJSONObject(i); 
			if(job.get("ass_card_no").equals("合计")){
				jsonarray.remove(i);
			}
		}
		json.put("Rows", jsonarray);
		return JSONObject.parseObject(json.toString());
	}
	
	
	// 点击引入查询
		@RequestMapping(value = "/hrp/ass/assgeneral/assback/queryAssBackInMainGeneral", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssBackInMainGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assInMainGeneralService.queryAssBackInMainGeneral(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
		
	
	
	/**
	 * @Description 添加数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/saveAssBackMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssBackGeneral assBackGeneral = assBackGeneralService.queryByCode(mapVo);
		if(assBackGeneral != null){
			if(assBackGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackGeneralJson = assBackGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackGeneralJson);

	}
	
	
	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/updateConfirmBackGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			List<Map<String, Object>> list1 = assBackDetailGeneralService.queryByInit(mapVo);
			if(list1.size() == 0 ){
				return JSONObject.parseObject("{\"warn\":\"单据不存在卡片,不能退库! \"}");
			}
			
			
			AssBackGeneral assBackGeneral = assBackGeneralService.queryByCode(mapVo);
			if (assBackGeneral.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assBackGeneral.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			List<AssBackDetailGeneral> list = assBackDetailGeneralService.queryByAssBackNo(mapVo);
			
			if(list != null && list.size() > 0){
				for(AssBackDetailGeneral assBackDetailGeneral : list){
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailGeneral.getGroup_id());
					listCardMap.put("hos_id", assBackDetailGeneral.getHos_id());
					listCardMap.put("copy_code", assBackDetailGeneral.getCopy_code());
					listCardMap.put("dispose_type", 41);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailGeneral.getAss_card_no());
					AssCardGeneral assCard = assCardGeneralService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if(assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackDetailGeneral.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackDetailGeneral.getAss_card_no(), assBackDetailGeneral.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				flag = false;
				break;
			}
			
			if (assBackGeneral.getState() == 2) {
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
			assInMainJson = assBackGeneralService.updateBackConfirm(listVo,listCardVo);
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
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/assBackGeneralUpdatePage", method = RequestMethod.GET)
	public String assBackGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackGeneral assBackGeneral = new AssBackGeneral();

		assBackGeneral = assBackGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackGeneral.getGroup_id());
		mode.addAttribute("hos_id", assBackGeneral.getHos_id());
		mode.addAttribute("copy_code", assBackGeneral.getCopy_code());
		mode.addAttribute("ass_back_no", assBackGeneral.getAss_back_no());
		mode.addAttribute("store_id", assBackGeneral.getStore_id());
		mode.addAttribute("store_no", assBackGeneral.getStore_no());
		mode.addAttribute("ven_id", assBackGeneral.getVen_id());
		mode.addAttribute("ven_no", assBackGeneral.getVen_no());
		mode.addAttribute("store_code", assBackGeneral.getStore_code());
		mode.addAttribute("store_name", assBackGeneral.getStore_name());
		mode.addAttribute("ven_code", assBackGeneral.getVen_code());
		mode.addAttribute("ven_name", assBackGeneral.getVen_name());
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
		mode.addAttribute("invoice_no", assBackGeneral.getInvoice_no());
		mode.addAttribute("invoice_date", assBackGeneral.getInvoice_date());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05025",MyConfig.getSysPara("05025"));
		
		return "hrp/ass/assgeneral/assback/update";
	}


	/**
	 * @Description 删除数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/deleteAssBackMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackGeneral assBackGeneral = assBackGeneralService.queryByCode(mapVo);
			if(assBackGeneral != null){
				if(assBackGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackGeneralJson = assBackGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackGeneralJson);

	}
	

	/**
	 * @Description 查询数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/queryAssBackMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackGeneral = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			 assBackGeneral = assBackGeneralService.query(getPage(mapVo));
			
		}else{
			 assBackGeneral = assBackGeneralService.queryDetails(getPage(mapVo));
		}
		

		return JSONObject.parseObject(assBackGeneral);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/deleteAssBackDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackDetailGeneral(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssBackGeneral assBackGeneral = assBackGeneralService.queryByCode(mapVo);
				if(assBackGeneral != null){
					if(assBackGeneral.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assBackDetailGeneralJson = assBackDetailGeneralService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assBackDetailGeneralJson);
			
	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/queryAssBackDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailGeneral = assBackDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailGeneral);
		
	}
	
	/**
	 * 一般设备 采购退货   退货单状态查询 （打印时校验数据专用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/queryAssBackGeneralState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackGeneralState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//一般设备 采购退货   退货单状态查询  （打印时校验数据专用）
		List<String> list = assBackGeneralService.queryAssBackGeneralState(mapVo);
		
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
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assback/updateAssBackMainGeneralBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBackMainGeneralBillNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = assBackGeneralService.updateAssBackMainBillNo(mapVo);

		return JSONObject.parseObject(assInMainSpecial);
	}
}

