package com.chd.hrp.ass.controller.assremould.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldRGeneral;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldRdetailGeneral;
import com.chd.hrp.ass.service.assremould.general.AssRemouldADetailGeneralService;
import com.chd.hrp.ass.service.assremould.general.AssRemouldAGeneralService;
import com.chd.hrp.ass.service.assremould.general.AssRemouldRDetailGeneralService;
import com.chd.hrp.ass.service.assremould.general.AssRemouldRGeneralService;

@Controller
public class AssRemouldRGeneralController extends BaseController{
	
	
	//引入Service服务
	@Resource(name = "assRemouldRGeneralService")
	private final AssRemouldRGeneralService assRemouldRGeneralService = null;
	//引入Service服务
	@Resource(name = "assRemouldRDetailGeneralService")
	private final AssRemouldRDetailGeneralService assRemouldRDetailGeneralService= null;
	
	// 引入Service服务
	@Resource(name = "assRemouldAGeneralService")
	private final AssRemouldAGeneralService assRemouldAGeneralService = null;

	// 引入Service服务
	@Resource(name = "assRemouldADetailGeneralService")
	private final AssRemouldADetailGeneralService assRemouldADetailGeneralService = null;


	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/assRemouldRGeneralMainPage", method = RequestMethod.GET)
	public String assRemouldRGeneralMainPage(Model mode) throws Exception {

		return "hrp/ass/assgeneral/assremouldrgeneral/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/assRemouldRGeneralAddPage", method = RequestMethod.GET)
	public String assRemouldRGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assremouldrgeneral/add";

	}
	/**
	 * @Description 引入申请页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       mode.addAttribute("create_date", mapVo.get("create_date"));
			mode.addAttribute("note", mapVo.get("note"));
			mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		return "hrp/ass/assgeneral/assremouldrgeneral/Import";

	}
	/**
	 * @Description 查询改造申请数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/queryAssRemouldAspecial", method = RequestMethod.POST)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldAspecial = assRemouldAGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldAspecial);

	}
	
	/**
	 * @Description 查看申报信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/assRemouldAspecialUpdatePage", method = RequestMethod.GET)
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

		Map<String,Object> assRemouldAspecialMap = assRemouldAGeneralService.queryByCode(mapVo);

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
		
		return "hrp/ass/assgeneral/assremouldrgeneral/Viewupdate";
	}
	/**
	 * @Description 查询改造申请明细数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/queryAssRemouldADict", method = RequestMethod.POST)
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
		String assRemouldAspecial = assRemouldAGeneralService.queryAssRemouldADict(mapVo);

		return JSONObject.parseObject(assRemouldAspecial);

	}
	//查询明细到记录
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/addAssPlanDeptImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		
		/*mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("apply_no").toString();
		String car[] =carno.split(",");
		  final List<String> ids = new ArrayList<String>();  
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("apply_no", ids);
		
		String assBackDetailSpecial = assRemouldADetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);*/
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInGeneral = assRemouldRGeneralService.initAssCheckGeneral(mapVo);

		return JSONObject.parseObject(assAllotInGeneral);
	}
	
	
	/**
	 * @Description 追溯盘亏申请单跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/assViewSpecialPage", method = RequestMethod.GET)
	public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("record_no", mapVo.get("record_no"));
		return "/hrp/ass/assgeneral/assremouldrgeneral/assPlanViewApply";
	}
	//queryCheckApinassetsByImport
	// 追溯盘亏申请
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/queryCheckApSpecialByImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assApplyDept = assRemouldAGeneralService.queryAssApplyDeptByPlanDept(getPage(mapVo));

		return JSONObject.parseObject(assApplyDept);

	}
	/**
	 * @Description 
	 * 添加数据 050805 资产改造记录(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/addAssRemouldRGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldRGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
    	String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		String assRemouldRGeneralJson = assRemouldRGeneralService.add(mapVo);

		return JSONObject.parseObject(assRemouldRGeneralJson);
		
	}
	/**
	 * @Description 
	 * 添加数据 050805 资产改造记录(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/saveAssRemouldRSourceGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldRSourceGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assRemouldRGeneralJson = assRemouldRGeneralService.saveAssRemouldRSourceGeneral(mapVo);
		
		return JSONObject.parseObject(assRemouldRGeneralJson);
		
	}
	
	
	/**
	 * @Description 
	 * 变动确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/updateConfirmChangegeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAgeneralConfirmState(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
		AssRemouldRGeneral  assRemouldAgeneral=assRemouldRGeneralService.queryByCode(mapVo);
		if(assRemouldAgeneral == null || assRemouldAgeneral.getState() == 2){
			continue;
		}	
		if(DateUtil.compareDate(assRemouldAgeneral.getCreate_date(), new Date())){
			return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
		}
		//查询明细表
			List<AssRemouldRdetailGeneral> detailList = assRemouldRDetailGeneralService.queryByDisANo(mapVo);
			//明细表存在进行判断卡片
			if(detailList != null && detailList.size() > 0){
				for (AssRemouldRdetailGeneral detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("bus_type_code", assRemouldAgeneral.getBus_type_code());
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

		
		String assRemouldAgeneralJson = assRemouldRGeneralService.updateAssRemouldAgeneralConfirmState(listVo,listCardVo);
	
		return JSONObject.parseObject(assRemouldAgeneralJson);

	}
	
	
	
	
	
	
	
	
	
	
	
/**
	 * @Description 
	 * 更新跳转页面 050805 资产改造记录(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/assRemouldRGeneralUpdatePage", method = RequestMethod.GET)
	public String assRemouldRGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssRemouldRGeneral assRemouldRGeneral = new AssRemouldRGeneral();
    
		assRemouldRGeneral = assRemouldRGeneralService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRemouldRGeneral.getGroup_id());
		mode.addAttribute("hos_id", assRemouldRGeneral.getHos_id());
		mode.addAttribute("copy_code", assRemouldRGeneral.getCopy_code());
		mode.addAttribute("record_no", assRemouldRGeneral.getRecord_no());
		mode.addAttribute("bus_type_code", assRemouldRGeneral.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldRGeneral.getVen_id());
		mode.addAttribute("ven_no", assRemouldRGeneral.getVen_no());
		mode.addAttribute("sup_name", assRemouldRGeneral.getSup_name());
		mode.addAttribute("sup_code", assRemouldRGeneral.getSup_code());
		mode.addAttribute("create_emp", assRemouldRGeneral.getCreate_emp());
		mode.addAttribute("create_date", assRemouldRGeneral.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldRGeneral.getAudit_emp());
		mode.addAttribute("record_date", assRemouldRGeneral.getRecord_date());
		mode.addAttribute("state", assRemouldRGeneral.getState());
		mode.addAttribute("note", assRemouldRGeneral.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assremouldrgeneral/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 050805 资产改造记录(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/updateAssRemouldRGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldRGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRemouldRGeneralJson = assRemouldRGeneralService.update(mapVo);
		
		return JSONObject.parseObject(assRemouldRGeneralJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 050805 资产改造记录(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/saveAssRemouldRGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRemouldRGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRemouldRGeneralJson ="";
		

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
		
		assRemouldRGeneralJson = assRemouldRGeneralService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assRemouldRGeneralJson);
	}
	/**
	 * @Description 删除数据 050805 资金来源
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/deleteAssremouldddSourceGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssremouldddSourceGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssRemouldRGeneral assChangeSpecial = assRemouldRGeneralService.queryByCode(mapVo);

			if (assChangeSpecial != null) {
				if (assChangeSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourceSpecialJson = assRemouldRGeneralService.deleteAssSourceGeneral(listVo);

		return JSONObject.parseObject(assChangeSourceSpecialJson);

	}
	
	
	/**
	 * @Description 删除数据 明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/deleteAssRemouldDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldDetailGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssRemouldRGeneral assChangeSpecial = assRemouldRGeneralService.queryByCode(mapVo);

			if (assChangeSpecial != null) {
				if (assChangeSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetailSpecialJson = assRemouldRDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailSpecialJson);

	}
	
	
	
	
	/**
	 * @Description 
	 * 删除数据 050805 资产改造记录(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/deleteAssRemouldARGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldRGeneral(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2]);
				mapVo.put("record_no", ids[3]);
				AssRemouldRGeneral assRemouldAspecial = assRemouldRGeneralService.queryByCode(mapVo);
				if (assRemouldAspecial != null ) {
					if (assRemouldAspecial.getState() >0) {
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
	      listVo.add(mapVo);
	      
	    }
			}
			  String de=    assRemouldRGeneralService.deleteBatchAssApplyPlanMap(listVo);

		String assRemouldRGeneralJson = assRemouldRGeneralService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRemouldRGeneralJson);
			
	
	}
	/**
	 * @Description 
	 * 查询数据 050805 资产改造记录(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/queryAssRemouldRGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldRGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldRGeneral = assRemouldRGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldRGeneral);
		
	}
	/**
	 * @Description 
	 * 查询数据 050805 资产改造记录(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/queryAssRemouldRdetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldRdetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldRGeneral = assRemouldRGeneralService.queryAssRemouldRdetailGeneral(mapVo);
		
		return JSONObject.parseObject(assRemouldRGeneral);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造记录(一般设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/queryAssRemouldSourceGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldSourceGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldRGeneral = assRemouldRGeneralService.queryAssRemouldSourceGeneral(mapVo);
		
		return JSONObject.parseObject(assRemouldRGeneral);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 050805 资产改造记录(一般设备)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/assRemouldRGeneralImportPage", method = RequestMethod.GET)
	public String assRemouldRGeneralImportPage(Model mode) throws Exception {

		return "hrp/ass/assgeneral/assremouldrgeneral/assRemouldRGeneralImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 050805 资产改造记录(一般设备)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/assremould/assremouldrgeneral/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","050805 资产改造记录(一般设备).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050805 资产改造记录(一般设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assremould/assremouldrgeneral/readAssRemouldRGeneralFiles",method = RequestMethod.POST)  
    public String readAssRemouldRGeneralFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssRemouldRGeneral> list_err = new ArrayList<AssRemouldRGeneral>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssRemouldRGeneral assRemouldRGeneral = new AssRemouldRGeneral();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assRemouldRGeneral.setRecord_no(temp[3]);
		    		mapVo.put("record_no", temp[3]);
					
					} else {
						
						err_sb.append("改造记录单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assRemouldRGeneral.setBus_type_code(temp[4]);
		    		mapVo.put("bus_type_code", temp[4]);
					
					} else {
						
						err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assRemouldRGeneral.setVen_id(Long.valueOf(temp[5]));
		    		mapVo.put("ven_id", temp[5]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assRemouldRGeneral.setVen_no(Long.valueOf(temp[6]));
		    		mapVo.put("ven_no", temp[6]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assRemouldRGeneral.setCreate_emp(Long.valueOf(temp[7]));
		    		mapVo.put("create_emp", temp[7]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assRemouldRGeneral.setCreate_date(DateUtil.stringToDate(temp[8]));
		    		mapVo.put("create_date", temp[8]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assRemouldRGeneral.setAudit_emp(Long.valueOf(temp[9]));
		    		mapVo.put("audit_emp", temp[9]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					assRemouldRGeneral.setRecord_date(DateUtil.stringToDate(temp[10]));
		    		mapVo.put("record_date", temp[10]);
					
					} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					assRemouldRGeneral.setState(Integer.valueOf(temp[11]));
		    		mapVo.put("state", temp[11]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					assRemouldRGeneral.setNote(temp[12]);
		    		mapVo.put("note", temp[12]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					
				AssRemouldRGeneral data_exc_extis = assRemouldRGeneralService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRemouldRGeneral.setError_type(err_sb.toString());
					
					list_err.add(assRemouldRGeneral);
					
				} else {
			  
					String dataJson = assRemouldRGeneralService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRemouldRGeneral data_exc = new AssRemouldRGeneral();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050805 资产改造记录(一般设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrgeneral/addBatchAssRemouldRGeneral", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssRemouldRGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssRemouldRGeneral> list_err = new ArrayList<AssRemouldRGeneral>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			AssRemouldRGeneral assRemouldRGeneral = new AssRemouldRGeneral();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("record_no"))) {
						
					assRemouldRGeneral.setRecord_no((String)jsonObj.get("record_no"));
		    		mapVo.put("record_no", jsonObj.get("record_no"));
		    		} else {
						
						err_sb.append("改造记录单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {
						
					assRemouldRGeneral.setBus_type_code((String)jsonObj.get("bus_type_code"));
		    		mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
		    		} else {
						
						err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {
						
					assRemouldRGeneral.setVen_id(Long.valueOf((String)jsonObj.get("ven_id")));
		    		mapVo.put("ven_id", jsonObj.get("ven_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {
						
					assRemouldRGeneral.setVen_no(Long.valueOf((String)jsonObj.get("ven_no")));
		    		mapVo.put("ven_no", jsonObj.get("ven_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assRemouldRGeneral.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assRemouldRGeneral.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {
						
					assRemouldRGeneral.setAudit_emp(Long.valueOf((String)jsonObj.get("audit_emp")));
		    		mapVo.put("audit_emp", jsonObj.get("audit_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("record_date"))) {
						
					assRemouldRGeneral.setRecord_date(DateUtil.stringToDate((String)jsonObj.get("record_date")));
		    		mapVo.put("record_date", jsonObj.get("record_date"));
		    		} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assRemouldRGeneral.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					assRemouldRGeneral.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					
				AssRemouldRGeneral data_exc_extis = assRemouldRGeneralService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRemouldRGeneral.setError_type(err_sb.toString());
					
					list_err.add(assRemouldRGeneral);
					
				} else {
			  
					String dataJson = assRemouldRGeneralService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRemouldRGeneral data_exc = new AssRemouldRGeneral();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}
