package com.chd.hrp.ass.controller.assremould.other;

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
import com.chd.hrp.ass.entity.assremould.AssRemouldRspecial;
import com.chd.hrp.ass.entity.assremould.other.AssRemouldROther;
import com.chd.hrp.ass.entity.assremould.other.AssRemouldRdetailOther;
import com.chd.hrp.ass.service.assremould.other.AssRemouldADetailOtherService;
import com.chd.hrp.ass.service.assremould.other.AssRemouldAOtherService;
import com.chd.hrp.ass.service.assremould.other.AssRemouldRDetailOtherService;
import com.chd.hrp.ass.service.assremould.other.AssRemouldROtherService;

@Controller
public class AssRemouldROtherController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRemouldROtherController.class);
	
	//引入Service服务
	@Resource(name = "assRemouldROtherService")
	private final AssRemouldROtherService assRemouldROtherService = null;
	//引入Service服务
	@Resource(name = "assRemouldRDetailOtherService")
	private final AssRemouldRDetailOtherService assRemouldRDetailOtherService= null;
	
	// 引入Service服务
	@Resource(name = "assRemouldAOtherService")
	private final AssRemouldAOtherService assRemouldAOtherService = null;

	// 引入Service服务
	@Resource(name = "assRemouldADetailOtherService")
	private final AssRemouldADetailOtherService assRemouldADetailOtherService = null;

	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/assRemouldROtherMainPage", method = RequestMethod.GET)
	public String assRemouldROtherMainPage(Model mode) throws Exception {

		return "hrp/ass/assother/assremouldrother/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/assRemouldROtherAddPage", method = RequestMethod.GET)
	public String assRemouldROtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assremouldrother/add";

	}
	/**
	 * @Description 引入申请页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       mode.addAttribute("create_date", mapVo.get("create_date"));
			mode.addAttribute("note", mapVo.get("note"));
			mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		return "hrp/ass/assother/assremouldrother/Import";

	}
	/**
	 * @Description 查询改造申请数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/queryAssRemouldAspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldAspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String assRemouldAspecial = assRemouldAOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldAspecial);

	}
	
	/**
	 * @Description 查看申报信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/assRemouldAspecialUpdatePage", method = RequestMethod.GET)
	public String assRemouldAspecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		Map<String,Object> assRemouldAspecialMap = assRemouldAOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assRemouldAspecialMap.get("group_id"));
		mode.addAttribute("hos_id", assRemouldAspecialMap.get("hos_id"));
		mode.addAttribute("copy_code", assRemouldAspecialMap.get("copy_code"));
		mode.addAttribute("apply_no", assRemouldAspecialMap.get("apply_no"));
		mode.addAttribute("bus_type_code", assRemouldAspecialMap.get("bus_type_code"));
		mode.addAttribute("bus_type_name", assRemouldAspecialMap.get("bus_type_name"));
		mode.addAttribute("ven_id", assRemouldAspecialMap.get("ven_id"));
		mode.addAttribute("ven_no", assRemouldAspecialMap.get("ven_no"));
		mode.addAttribute("sup_code", assRemouldAspecialMap.get("sup_code"));
		mode.addAttribute("sup_name", assRemouldAspecialMap.get("sup_name"));
		mode.addAttribute("create_emp", assRemouldAspecialMap.get("create_emp"));
		mode.addAttribute("create_date", assRemouldAspecialMap.get("create_date"));
		mode.addAttribute("create_emp_name", assRemouldAspecialMap.get("create_emp_name"));
		mode.addAttribute("audit_emp_name", assRemouldAspecialMap.get("audit_emp_name"));
		mode.addAttribute("audit_emp", assRemouldAspecialMap.get("audit_emp"));
		mode.addAttribute("change_date", assRemouldAspecialMap.get("change_date"));
		mode.addAttribute("state", assRemouldAspecialMap.get("state"));
		mode.addAttribute("note", assRemouldAspecialMap.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assremouldrother/Viewupdate";
	}
	/**
	 * @Description 查询改造申请明细数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/queryAssRemouldADict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldADict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldAspecial = assRemouldAOtherService.queryAssRemouldADict(mapVo);

		return JSONObject.parseObject(assRemouldAspecial);

	}
	//查询明细到记录
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/addAssPlanDeptImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		/*
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("apply_no").toString();
		String car[] =carno.split(",");
		  final List<String> ids = new ArrayList<String>();  
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("apply_no", ids);
		
        
		String assBackDetailSpecial = assRemouldADetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);*/
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInGeneral = assRemouldROtherService.initAssCheckother(mapVo);

		return JSONObject.parseObject(assAllotInGeneral);
	}
	
	
	/**
	 * @Description 追溯盘亏申请单跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrother/assViewSpecialPage", method = RequestMethod.GET)
	public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("record_no", mapVo.get("record_no"));
		return "/hrp/ass/assother/assremouldrother/assPlanViewApply";
	}
	//queryCheckApSpecialByImport
	// 追溯盘亏申请
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrother/queryCheckApSpecialByImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assApplyDept = assRemouldAOtherService.queryAssApplyDeptByPlanDept(getPage(mapVo));

		return JSONObject.parseObject(assApplyDept);

	}
	/**
	 * @Description 
	 * 添加数据 050805 资产改造记录(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/addAssRemouldROther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldROther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
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
		mapVo.put("create_emp", SessionManager.getUserId());
       
		String assRemouldROtherJson = assRemouldROtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assRemouldROtherJson);
		
	}
	/**
	 * @Description 
	 * 添加数据 050805 资产改造记录(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/saveAssRemouldRSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldRSourceOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assRemouldROtherJson = assRemouldROtherService.saveAssRemouldRSourceOther(mapVo);
		
		return JSONObject.parseObject(assRemouldROtherJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 050805 资产改造记录(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/assRemouldROtherUpdatePage", method = RequestMethod.GET)
	public String assRemouldROtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		AssRemouldROther assRemouldROther = new AssRemouldROther();
    
		assRemouldROther = assRemouldROtherService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRemouldROther.getGroup_id());
		mode.addAttribute("hos_id", assRemouldROther.getHos_id());
		mode.addAttribute("copy_code", assRemouldROther.getCopy_code());
		mode.addAttribute("record_no", assRemouldROther.getRecord_no());
		mode.addAttribute("bus_type_code", assRemouldROther.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldROther.getVen_id());
		mode.addAttribute("ven_no", assRemouldROther.getVen_no());
		mode.addAttribute("sup_code", assRemouldROther.getSup_code());
		mode.addAttribute("sup_name", assRemouldROther.getSup_name());
		mode.addAttribute("create_emp", assRemouldROther.getCreate_emp());
		mode.addAttribute("create_date", assRemouldROther.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldROther.getAudit_emp());
		mode.addAttribute("record_date", assRemouldROther.getRecord_date());
		mode.addAttribute("state", assRemouldROther.getState());
		mode.addAttribute("note", assRemouldROther.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assremouldrother/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 050805 资产改造记录(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/updateAssRemouldROther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldROther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRemouldROtherJson = assRemouldROtherService.update(mapVo);
		
		return JSONObject.parseObject(assRemouldROtherJson);
	}
	/**
	 * @Description 
	 * 变动确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/updateConfirmChangeOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAspecialConfirmState(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			mapVo.put("record_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
//			mapVo.put("apply_no", ids[4]);
		//查询主表是否存在
		AssRemouldROther  assRemouldAspecial=assRemouldROtherService.queryByCode(mapVo);
		if(assRemouldAspecial == null || assRemouldAspecial.getState() == 2){
			continue;
		}	
		if(DateUtil.compareDate(assRemouldAspecial.getCreate_date(), new Date())){
			return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
		}
		//查询明细表
			List<AssRemouldRdetailOther> detailList = assRemouldRDetailOtherService.queryByDisANo(mapVo);
			//明细表存在进行判断卡片
			if(detailList != null && detailList.size() > 0){
				for (AssRemouldRdetailOther detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("bus_type_code", assRemouldAspecial.getBus_type_code());
					listCardMap.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if(map.containsKey(detail.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				//如果明细表不存在返回没有数据
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置确认! \"}");
			}
			listVo.add(mapVo);

		}
		//判断listVo是否有数据
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		
		String assRemouldAspecialJson = assRemouldROtherService.updateAssRemouldAOtherConfirmState(listVo,listCardVo);
	
		return JSONObject.parseObject(assRemouldAspecialJson);

	}
	
	/**
	 * @Description 
	 * 更新数据 050805 资产改造记录(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/saveAssRemouldROther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRemouldROther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRemouldROtherJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		assRemouldROtherJson = assRemouldROtherService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assRemouldROtherJson);
	}
	
	/**
	 * @Description 删除数据 050805 资产明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/deleteAssRemouldDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldDetailOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);

			AssRemouldROther assChangeSpecial = assRemouldROtherService.queryByCode(mapVo);

			if (assChangeSpecial != null) {
				if (assChangeSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
		String assChangeDetailSpecialJson = assRemouldRDetailOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailSpecialJson);

	}
	
	/**
	 * @Description 删除数据 050805 资产资金来源(其他固定设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/deleteAssremoulddSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssremoulddSourceOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			mapVo.put("source_id", ids[5]);

			AssRemouldRspecial assChangeSpecial = assRemouldROtherService.queryByCode(mapVo);

			if (assChangeSpecial != null) {
				if (assChangeSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourceSpecialJson = assRemouldROtherService.deleteAssSourceOther(listVo);

		return JSONObject.parseObject(assChangeSourceSpecialJson);

	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造记录(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/deleteAssRemouldROther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldROther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2]);
				mapVo.put("record_no", ids[3]);
				AssRemouldROther assRemouldAspecial = assRemouldROtherService.queryByCode(mapVo);
				if (assRemouldAspecial != null ) {
					if (assRemouldAspecial.getState() >0) {
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
					
				}
				
	      listVo.add(mapVo);
	      
	    }
			  String de=    assRemouldROtherService.deleteBatchAssApplyPlanMap(listVo);

		String assRemouldROtherJson = assRemouldROtherService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRemouldROtherJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造记录(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/queryAssRemouldROther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldROther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		if(mapVo.get("create_emp") == null){
			
			mapVo.put("create_emp", SessionManager.getUserId());
			}
		
		String assRemouldROther = assRemouldROtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldROther);
		
	}
	/**
	 * @Description 
	 * 查询数据 050805 资产改造记录(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/queryAssRemouldRdetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldRdetailOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		if(mapVo.get("acct_year") == null){
			
			mapVo.put("acct_year", SessionManager.getAcctYear());
			
		}
		String assRemouldROther = assRemouldROtherService.queryAssRemouldRdetailOther(mapVo);
		
		return JSONObject.parseObject(assRemouldROther);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造记录(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrOther/queryAssRemouldSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldSourceOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		if(mapVo.get("acct_year") == null){
			
			mapVo.put("acct_year", SessionManager.getAcctYear());
			
		}
		String assRemouldROther = assRemouldROtherService.queryAssRemouldSourceOther(mapVo);
		
		return JSONObject.parseObject(assRemouldROther);
		
	}
    
}
