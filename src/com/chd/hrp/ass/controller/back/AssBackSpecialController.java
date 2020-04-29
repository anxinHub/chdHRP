

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
import com.chd.hrp.ass.entity.back.AssBackDetailSpecial;
import com.chd.hrp.ass.entity.back.AssBackSpecial;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.entity.in.AssInMainSpecial;
import com.chd.hrp.ass.service.back.AssBackDetailSpecialService;
import com.chd.hrp.ass.service.back.AssBackSpecialService;
import com.chd.hrp.ass.service.card.AssCardSpecialService;
import com.chd.hrp.ass.service.in.AssInDetailSpecialService;
import com.chd.hrp.ass.service.in.AssInMainSpecialService;

/**
 * 
 * @Description: 050701 资产退货单主表(专用设备)
 * @Table: ASS_BACK_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssBackSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBackSpecialController.class);

	// 引入Service服务
	@Resource(name = "assBackSpecialService")
	private final AssBackSpecialService assBackSpecialService = null;
	
	@Resource(name = "assBackDetailSpecialService")
	private final AssBackDetailSpecialService assBackDetailSpecialService = null;
	
	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;
	
	@Resource(name = "assInMainSpecialService")
	private final AssInMainSpecialService assInMainSpecialService = null;
	
	@Resource(name = "assInDetailSpecialService")
	private final AssInDetailSpecialService assInDetailSpecialService = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/assBackSpecialMainPage", method = RequestMethod.GET)
	public String assBackSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05015",MyConfig.getSysPara("05015"));
		
		return "hrp/ass/assspecial/assback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/assBackSpecialAddPage", method = RequestMethod.GET)
	public String assBackSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05018",MyConfig.getSysPara("05018"));
		
		return "hrp/ass/assspecial/assback/add";

	}
	/**
	 * 引入采购入库
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/assBackMainSpecialPage", method = RequestMethod.GET)
	public String assBackMainSpecialPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		
		mode.addAttribute("ven_id", mapVo.get("ven_id"));
		mode.addAttribute("ven_no", mapVo.get("ven_no"));
		mode.addAttribute("ven_code", mapVo.get("ven_code"));
		mode.addAttribute("ven_name", mapVo.get("ven_name"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assspecial/assback/assImportmain";
	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/assback/assBackMainSpecialBatchPage", method = RequestMethod.GET)
	public String assBackMainSpecialBatchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		return "hrp/ass/assspecial/assback/assBatchmain";
	}
	
	/**
	 * 保存入库明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/addAssBackSpecialImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackSpecialImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		
		String assBackSpecial = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		assBackSpecial = assBackSpecialService.assImportBackIn(mapVo);
		
		
		return JSONObject.parseObject(assBackSpecial);
		
	}
	
	/**
	 * 主页面店家引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/assViewBackPage", method = RequestMethod.GET)
	public String assViewBackPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("ass_back_no", mapVo.get("ass_back_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assspecial/assback/assSpecialViewBack";
	}
	
	
	/**
	 * @Description 添加数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/saveAssBackMainSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssBackSpecial assBackSpecial = assBackSpecialService.queryByCode(mapVo);
		if(assBackSpecial != null){
			if(assBackSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackSpecialJson = assBackSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackSpecialJson);

	}
	
	
	// 点击引入查询
	@RequestMapping(value = "/hrp/ass/assspecial/assback/queryAssBackInMainSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackInMainSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assApplyDept = assInMainSpecialService.queryAssBackInMainSpecial(getPage(mapVo));

		return JSONObject.parseObject(assApplyDept);

	}
	
	/**
	 * @Description 查询数据 资产入库主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/queryAssInMainSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMainSpecial  = assInMainSpecialService.queryInMap(getPage(mapVo));
			 

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	/**
	 * @Description 更新跳转页面 资产入库主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/assInMainSpecialUpdatePage", method = RequestMethod.GET)
	public String assInMainSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInMainSpecial assInMainSpecial = new AssInMainSpecial();

		assInMainSpecial = assInMainSpecialService.queryByCode(mapVo);

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
		
		return "hrp/ass/assspecial/assback/assImportUpate";
	}
	
	/**
	 * @Description 查询数据 资产入库明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/queryAssInDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInDetailSpecial = assInDetailSpecialService.query(getPage(mapVo));
		return JSONObject.parseObject(assInDetailSpecial);
	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/assback/queryAssInCardSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInCardSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardSpecialService.query(getPage(mapVo));
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
	
	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/updateConfirmBackSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			
			List<Map<String, Object>> list1 = assBackDetailSpecialService.queryByInit(mapVo);
			if(list1.size() == 0 ){
				return JSONObject.parseObject("{\"warn\":\"单据不存在卡片,不能退库! \"}");
			}
			
			
			AssBackSpecial assBackSpecial = assBackSpecialService.queryByCode(mapVo);
			if (assBackSpecial.getState() == 2) {
				continue;
			}
			
			if(DateUtil.compareDate(assBackSpecial.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			List<AssBackDetailSpecial> list = assBackDetailSpecialService.queryByAssBackNo(mapVo);
			
			if(list != null && list.size() > 0){
				for(AssBackDetailSpecial assBackDetailSpecial : list){
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assBackDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assBackDetailSpecial.getCopy_code());
					listCardMap.put("dispose_type", 41);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailSpecial.getAss_card_no());
					AssCardSpecial assCard = assCardSpecialService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if(assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackDetailSpecial.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackDetailSpecial.getAss_card_no(), assBackDetailSpecial.getAss_card_no());
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
			assInMainJson = assBackSpecialService.updateBackConfirm(listVo,listCardVo);
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
	@RequestMapping(value = "/hrp/ass/assspecial/assback/assBackSpecialUpdatePage", method = RequestMethod.GET)
	public String assBackSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackSpecial assBackSpecial = new AssBackSpecial();

		assBackSpecial = assBackSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackSpecial.getGroup_id());
		mode.addAttribute("hos_id", assBackSpecial.getHos_id());
		mode.addAttribute("copy_code", assBackSpecial.getCopy_code());
		mode.addAttribute("ass_back_no", assBackSpecial.getAss_back_no());
		mode.addAttribute("store_id", assBackSpecial.getStore_id());
		mode.addAttribute("store_no", assBackSpecial.getStore_no());
		mode.addAttribute("ven_id", assBackSpecial.getVen_id());
		mode.addAttribute("ven_no", assBackSpecial.getVen_no());
		mode.addAttribute("store_code", assBackSpecial.getStore_code());
		mode.addAttribute("store_name", assBackSpecial.getStore_name());
		mode.addAttribute("ven_code", assBackSpecial.getVen_code());
		mode.addAttribute("ven_name", assBackSpecial.getVen_name());
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
		mode.addAttribute("invoice_no", assBackSpecial.getInvoice_no());
		mode.addAttribute("invoice_date", assBackSpecial.getInvoice_date());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05015",MyConfig.getSysPara("05015"));
		
		return "hrp/ass/assspecial/assback/update";
	}


	/**
	 * @Description 删除数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/deleteAssBackMainSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackSpecial assBackSpecial = assBackSpecialService.queryByCode(mapVo);
			if(assBackSpecial != null){
				if(assBackSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackSpecialJson = assBackSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackSpecialJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/queryAssBackMainSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assBackSpecial = null;

		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackSpecial = assBackSpecialService.query(getPage(mapVo));
			
		}else{
			assBackSpecial = assBackSpecialService.queryDetails(getPage(mapVo));
		}

		return JSONObject.parseObject(assBackSpecial);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/assback/deleteAssBackDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackDetailSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssBackSpecial assBackSpecial = assBackSpecialService.queryByCode(mapVo);
				if(assBackSpecial != null){
					if(assBackSpecial.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assBackDetailSpecialJson = assBackDetailSpecialService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assBackDetailSpecialJson);
			
	}
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/assback/queryAssBackDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailSpecial = assBackDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);
		
	}
	
	/**
	 * 退货单 状态查询  （打印校验数据专用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assback/queryAssBackSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assBackSpecialService.queryAssBackSpecialState(mapVo);
		
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
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/assback/updateAssBackMainSpecialBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBackMainSpecialBillNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = assBackSpecialService.updateAssBackMainBillNo(mapVo);

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	
}
