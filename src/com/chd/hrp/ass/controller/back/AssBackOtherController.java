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
import com.chd.hrp.ass.entity.back.AssBackDetailOther;
import com.chd.hrp.ass.entity.back.AssBackOther;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.in.AssInMainOther;
import com.chd.hrp.ass.service.back.AssBackDetailOtherService;
import com.chd.hrp.ass.service.back.AssBackOtherService;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.in.AssInDetailOtherService;
import com.chd.hrp.ass.service.in.AssInMainOtherService;
/**
 * 
 * @Description:
 * 050701 资产退货单主表(其他固定资产)
 * @Table:
 * ASS_BACK_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssBackOtherController extends BaseController{

	private static Logger logger = Logger.getLogger(AssBackOtherController.class);

	// 引入Service服务
	@Resource(name = "assBackOtherService")
	private final AssBackOtherService assBackOtherService = null;
	
	@Resource(name = "assBackDetailOtherService")
	private final AssBackDetailOtherService assBackDetailOtherService = null;
	
	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	@Resource(name = "assInMainOtherService")
	private final AssInMainOtherService assInMainOtherService = null;
	
	@Resource(name = "assInDetailOtherService")
	private final AssInDetailOtherService assInDetailOtherService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/assBackOtherMainPage", method = RequestMethod.GET)
	public String assBackOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05035",MyConfig.getSysPara("05035"));
		
		return "hrp/ass/assother/assback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/assBackOtherAddPage", method = RequestMethod.GET)
	public String assBackOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05035",MyConfig.getSysPara("05035"));
		
		return "hrp/ass/assother/assback/add";

	}
	
	@RequestMapping(value = "/hrp/ass/assother/assback/assBackMainOtherBatchPage", method = RequestMethod.GET)
	public String assBackMainOtherBatchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		return "hrp/ass/assother/assback/assBatchmain";
	}

	/**
	 * 引入采购入库
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/assBackMainOtherPage", method = RequestMethod.GET)
	public String assBackMainOtherPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		
		mode.addAttribute("ven_id", mapVo.get("ven_id"));
		mode.addAttribute("ven_no", mapVo.get("ven_no"));
		mode.addAttribute("ven_code", mapVo.get("ven_code"));
		mode.addAttribute("ven_name", mapVo.get("ven_name"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assother/assback/assImportmain";
	}
	
	/**
	 * 主页面店家引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/assViewBackPage", method = RequestMethod.GET)
	public String assViewBackPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("ass_back_no", mapVo.get("ass_back_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assother/assback/assOtherViewBack";
	}
	
	/**
	 * @Description 查询数据 资产入库主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/queryAssInMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMainSpecial  = assInMainOtherService.queryInMap(getPage(mapVo));
			 

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	/**
	 * 保存入库明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/addAssBackOtherImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackOtherImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		
		String assBackOther = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		assBackOther = assBackOtherService.assImportBackIn(mapVo);
		
		return JSONObject.parseObject(assBackOther);
		
	}
	
	/**
	 * @Description 更新跳转页面 资产入库主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/assInMainOtherUpdatePage", method = RequestMethod.GET)
	public String assInMainOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInMainOther assInMainSpecial = new AssInMainOther();

		assInMainSpecial = assInMainOtherService.queryByCode(mapVo);

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
		
		return "hrp/ass/assother/assback/assImportUpate";
	}
	
	/**
	 * @Description 查询数据 资产入库明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/queryAssInDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInDetailSpecial = assInDetailOtherService.query(getPage(mapVo));
		return JSONObject.parseObject(assInDetailSpecial);
	}
	
	
	@RequestMapping(value = "/hrp/ass/assother/assback/queryAssInCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardOtherService.query(getPage(mapVo));
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
		@RequestMapping(value = "/hrp/ass/assother/assback/queryAssBackInMainOther", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssBackInMainOther(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assInMainOtherService.queryAssBackInMainOther(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	
	
	/**
	 * @Description 添加数据 050701 资产退货单主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/saveAssBackMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssBackOther assBackOther = assBackOtherService.queryByCode(mapVo);
		if(assBackOther != null){
			if(assBackOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackOtherJson = assBackOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackOtherJson);

	}
	
	
	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/updateConfirmBackOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			
			List<Map<String, Object>> list1 = assBackDetailOtherService.queryByInit(mapVo);
			if(list1.size() == 0 ){
				return JSONObject.parseObject("{\"warn\":\"单据不存在卡片,不能退库! \"}");
			}
			
			
			AssBackOther assBackOther = assBackOtherService.queryByCode(mapVo);
			
			if (assBackOther.getState() == 2) {
				continue;
			}
			
			if(DateUtil.compareDate(assBackOther.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			List<AssBackDetailOther> list = assBackDetailOtherService.queryByAssBackNo(mapVo);
			
			if(list != null && list.size() > 0){
				for(AssBackDetailOther assBackDetailOther : list){
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailOther.getGroup_id());
					listCardMap.put("hos_id", assBackDetailOther.getHos_id());
					listCardMap.put("copy_code", assBackDetailOther.getCopy_code());
					listCardMap.put("dispose_type", 41);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailOther.getAss_card_no());
					AssCardOther assCard = assCardOtherService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if(assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackDetailOther.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackDetailOther.getAss_card_no(), assBackDetailOther.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				flag = false;
				break;
			}
			
			if (assBackOther.getState() == 2) {
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
			assInMainJson = assBackOtherService.updateBackConfirm(listVo,listCardVo);
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
	@RequestMapping(value = "/hrp/ass/assother/assback/assBackOtherUpdatePage", method = RequestMethod.GET)
	public String assBackOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackOther assBackOther = new AssBackOther();

		assBackOther = assBackOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackOther.getGroup_id());
		mode.addAttribute("hos_id", assBackOther.getHos_id());
		mode.addAttribute("copy_code", assBackOther.getCopy_code());
		mode.addAttribute("ass_back_no", assBackOther.getAss_back_no());
		mode.addAttribute("store_id", assBackOther.getStore_id());
		mode.addAttribute("store_no", assBackOther.getStore_no());
		mode.addAttribute("ven_id", assBackOther.getVen_id());
		mode.addAttribute("ven_no", assBackOther.getVen_no());
		mode.addAttribute("store_code", assBackOther.getStore_code());
		mode.addAttribute("store_name", assBackOther.getStore_name());
		mode.addAttribute("ven_code", assBackOther.getVen_code());
		mode.addAttribute("ven_name", assBackOther.getVen_name());
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
		mode.addAttribute("invoice_no", assBackOther.getInvoice_no());
		mode.addAttribute("invoice_date", assBackOther.getInvoice_date());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05035",MyConfig.getSysPara("05035"));
		
		return "hrp/ass/assother/assback/update";
	}


	/**
	 * @Description 删除数据 050701 资产退货单主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/deleteAssBackMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackOther assBackOther = assBackOtherService.queryByCode(mapVo);
			if(assBackOther != null){
				if(assBackOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackOtherJson = assBackOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackOtherJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/queryAssBackMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackOther = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackOther = assBackOtherService.query(getPage(mapVo));
			
		}else{
			
			assBackOther = assBackOtherService.queryDetails(getPage(mapVo));
			
		}
		

		return JSONObject.parseObject(assBackOther);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assother/assback/deleteAssBackDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackDetailOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssBackOther assBackOther = assBackOtherService.queryByCode(mapVo);
				if(assBackOther != null){
					if(assBackOther.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assBackDetailOtherJson = assBackDetailOtherService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assBackDetailOtherJson);
			
	}
	
	
	@RequestMapping(value = "/hrp/ass/assother/assback/queryAssBackDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackDetailOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailOther = assBackDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailOther);
		
	}
	
	/**
	 * 其他固定资产 采购退货   退货单状态查询 （打印时校验数据专用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assback/queryAssBackOtherState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackOtherState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//其他固定资产采购退货   退货单状态查询  （打印时校验数据专用）
		List<String> list = assBackOtherService.queryAssBackOtherState(mapVo);
		
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
	
	@RequestMapping(value = "/hrp/ass/assother/assback/updateAssBackMainOtherBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBackMainOtherBillNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = assBackOtherService.updateAssBackMainBillNo(mapVo);

		return JSONObject.parseObject(assInMainSpecial);
	}
}

