package com.chd.hrp.med.controller.purchase.make;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.med.service.order.init.MedOrderInitService;
import com.chd.hrp.med.service.purchase.check.MedPurMainCheckService;
import com.chd.hrp.med.service.purchase.make.MedPurMainService;
import com.chd.hrp.med.service.requrie.store.MedStoreRequirePlanService;



/**
 * 
 * @Description:
 * 08113 采购计划编制
 * @Table:
 * MED_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedPurMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedPurMainController.class);
	//引入Service服务
	@Resource(name = "medPurMainService")
	private final MedPurMainService medPurMainService = null ;
	
	@Resource(name = "medOrderInitService")
	private final MedOrderInitService medOrderInitService = null;
	
	@Resource(name = "medPurMainCheckService")
	private final MedPurMainCheckService medPurMainCheckService = null;
	
	@Resource(name = "medStoreRequirePlanService")
	private final MedStoreRequirePlanService medStoreRequirePlanService = null;
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//主页面跳转
	@RequestMapping(value = "/hrp/med/purchase/make/medPurMainMainPage", method = RequestMethod.GET)
	public String medPurMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08040", MyConfig.getSysPara("08040"));
		mode.addAttribute("p08046", MyConfig.getSysPara("08046"));
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));

		
		return "hrp/med/purchase/make/medPurMainMain";
	}
	
	/**
	 * @Description 
	 * 采购计划编制 查询
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String medPurMainJson = "";
		if(mapVo.get("show_detail").equals("1")){
			medPurMainJson =  medPurMainService.queryDetails(getPage(mapVo));
		}else{
			medPurMainJson =  medPurMainService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(medPurMainJson);

	}
	
	/**
	 * @Description 
	 * 采购计划 审核
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/checkMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		String paramVo = mapVo.get("paramVo").toString();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> map=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				map.put("group_id", ids[0]);
				map.put("hos_id", ids[1]);
				map.put("copy_code", ids[2]);
				map.put("pur_id", ids[3]);
				map.put("state", ids[4]);
				map.put("checker", SessionManager.getUserId());
				map.put("check_date", sdf.format(new Date()));
				listVo.add(map);
	    }
			
		String medPurMainCheckJson = medPurMainCheckService.checkMedPurMain(listVo);
		
		return JSONObject.parseObject(medPurMainCheckJson);
	}
	
	/**
	 * @Description 
	 * 采购计划 取消审核
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/cancelCheckMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> cancelCheckMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String paramVo = mapVo.get("paramVo").toString();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> map=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				map.put("group_id", ids[0]);
				map.put("hos_id", ids[1]);
				map.put("copy_code", ids[2]);
				map.put("pur_id", ids[3]);
				map.put("state", ids[4]);
				listVo.add(map);
	    }
			
		String medPurMainCheckJson = medPurMainCheckService.cancelCheckMedPurMain(listVo);
		
		return JSONObject.parseObject(medPurMainCheckJson);
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//添加页面跳转
	@RequestMapping(value = "/hrp/med/purchase/make/addMedPurMainPage", method = RequestMethod.GET)
	public String addMedPurMainPage(Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("req_hos_id", SessionManager.getHosId());
		mode.addAttribute("req_hos_code", SessionManager.getHosCode());
		mode.addAttribute("req_hos_name", SessionManager.getHosSimple());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08013", MyConfig.getSysPara("08013"));
		
		return "hrp/med/purchase/make/medPurMainAdd";
	}
	
	/**
	 * @Description 
	 * 引入仓库需求计划
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/medStoreDetailImpPage", method = RequestMethod.GET)
	public String medStoreDetailImpPage(Model mode) throws Exception {
		
		mode.addAttribute("p08031", MyConfig.getSysPara("08031"));
		
		return "hrp/med/purchase/make/medStoreDetailImp";
	}
	
	/**
	 * @Description 
	 * 引入科室需求计划
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/medDeptDetailImpPage", method = RequestMethod.GET)
	public String medDeptDetailImpPage(Model mode) throws Exception {
		
		mode.addAttribute("p08031", MyConfig.getSysPara("08031"));

		return "hrp/med/purchase/make/medDeptDetailImp";
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 安全库存
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/medDeptRequriedSafePage", method = RequestMethod.GET)
	public String medDeptRequriedSafePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		return "/hrp/med/purchase/make/medDeptRequriedPlanSafeImp";
	}

	/**
	 * 安全库存导入查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedPurMainSafe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurMainSafe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medRequireMainJson = medPurMainService.queryMedPurSafe(mapVo);

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//科室需求明细页面跳转
	@RequestMapping(value = "/hrp/med/purchase/make/medRequireDetail", method = RequestMethod.GET)
	public String medRequireDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> map = medPurMainService.queryMedRequireMainByCode(mapVo);
		
		mode.addAttribute("group_id", map.get("group_id"));
		mode.addAttribute("hos_id", map.get("hos_id"));
		mode.addAttribute("copy_code", map.get("copy_code"));
		mode.addAttribute("req_id", map.get("req_id"));
		mode.addAttribute("req_code", map.get("req_code"));
		mode.addAttribute("stock_id", map.get("stock_id"));
		mode.addAttribute("stock_no", map.get("stock_no"));
		mode.addAttribute("stock_code", map.get("store_code"));
		mode.addAttribute("stock_name", map.get("store_name"));
		mode.addAttribute("make_date", map.get("make_date"));
		
		return "hrp/med/purchase/make/medRequireDetail";

	}
	
	//仓库需求明细页面跳转
		@RequestMapping(value = "/hrp/med/purchase/make/medStoreRequireDetail", method = RequestMethod.GET)
		public String medStoreRequireDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			
			if(mapVo.get("group_id") == null){
		    	mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			//Map<String,Object> map = medPurMainService.queryMedRequireMainByCode(mapVo);
			
			Map<String,Object> map = medStoreRequirePlanService.queryByCode(mapVo);
			
			mode.addAttribute("group_id", map.get("group_id"));
			mode.addAttribute("hos_id", map.get("hos_id"));
			mode.addAttribute("copy_code", map.get("copy_code"));
			mode.addAttribute("req_id", map.get("req_id"));
			mode.addAttribute("req_code", map.get("req_code"));
			mode.addAttribute("stock_id", map.get("stock_id"));
			mode.addAttribute("stock_no", map.get("stock_no"));
			mode.addAttribute("stock_code", map.get("store_code"));
			mode.addAttribute("stock_name", map.get("store_name"));
			mode.addAttribute("make_date", map.get("make_date"));
			
			return "hrp/med/purchase/make/medStoreRequireDetail";

		}
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//药品当前库存明细页面跳转
	@RequestMapping(value = "/hrp/med/purchase/make/medInvCurAmountDetailPage", method = RequestMethod.GET)
	public String medInvCurAmountDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		return "hrp/med/purchase/make/medInvCurAmountDetail";
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//修改页面跳转
	@RequestMapping(value = "/hrp/med/purchase/make/updateMedPurMainPage", method = RequestMethod.GET)
	public String updateMedPurMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		Map<String,Object> medPurMainMap = medPurMainService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", medPurMainMap.get("group_id"));
		mode.addAttribute("hos_id", medPurMainMap.get("hos_id"));
		mode.addAttribute("copy_code",medPurMainMap.get("copy_code"));
		mode.addAttribute("pur_id", medPurMainMap.get("pur_id"));
		mode.addAttribute("pur_code", medPurMainMap.get("pur_code"));
		mode.addAttribute("dept_id", medPurMainMap.get("dept_id"));
		mode.addAttribute("dept_no",medPurMainMap.get("dept_no"));
		mode.addAttribute("dept_code",medPurMainMap.get("dept_code"));
		mode.addAttribute("dept_name", medPurMainMap.get("dept_name"));
		mode.addAttribute("make_date", medPurMainMap.get("make_date"));
		mode.addAttribute("arrive_date", medPurMainMap.get("arrive_date"));
		mode.addAttribute("pur_type", medPurMainMap.get("pur_type"));
		mode.addAttribute("brif", medPurMainMap.get("brif"));
		mode.addAttribute("come_from", medPurMainMap.get("come_from"));
		
		mode.addAttribute("pur_hos_id",medPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_no",medPurMainMap.get("pur_hos_no"));
		mode.addAttribute("pur_hos_code",medPurMainMap.get("pur_hos_code"));
		mode.addAttribute("pur_hos_name", medPurMainMap.get("pur_hos_name"));
		
		mode.addAttribute("req_hos_id", medPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_no", medPurMainMap.get("req_hos_no"));
		mode.addAttribute("req_hos_code", medPurMainMap.get("req_hos_code"));
		mode.addAttribute("req_hos_name", medPurMainMap.get("req_hos_name"));
		
		mode.addAttribute("pay_hos_id", medPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_no", medPurMainMap.get("pay_hos_no"));
		mode.addAttribute("pay_hos_code", medPurMainMap.get("pay_hos_code"));
		mode.addAttribute("pay_hos_name", medPurMainMap.get("pay_hos_name"));
		
		mode.addAttribute("is_dir", medPurMainMap.get("is_dir"));
		mode.addAttribute("dir_dept_id", medPurMainMap.get("dir_dept_id"));
		mode.addAttribute("dir_dept_no", medPurMainMap.get("dir_dept_no"));
		mode.addAttribute("dir_dept_code", medPurMainMap.get("dir_dept_code"));
		mode.addAttribute("dir_dept_name", medPurMainMap.get("dir_dept_name"));
		
		mode.addAttribute("state", mapVo.get("state"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08013", MyConfig.getSysPara("08013"));
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		mode.addAttribute("p08086", MyConfig.getSysPara("08086"));
		
		return "hrp/med/purchase/make/medPurMainUpdate";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//按需求计划生成 添加页面
	@RequestMapping(value = "/hrp/med/purchase/make/medRequireProdAddPage", method = RequestMethod.GET)
	public String medRequireProdAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("is_dir", mapVo.get("is_dir"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_id"));
		mode.addAttribute("is_dir", mapVo.get("is_dir"));
		mode.addAttribute("is_dept", mapVo.get("is_dept"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08013", MyConfig.getSysPara("08013"));
		
		return "hrp/med/purchase/make/medRequireProdAdd";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//计划数量明细页面跳转
	@RequestMapping(value = "/hrp/med/purchase/make/medRequireAmountDetailPage", method = RequestMethod.GET)
	public String medRequireAmountDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("req_rela",mapVo.get("req_rela").toString());
		mode.addAttribute("rowindex", mapVo.get("rowindex").toString());
		JSONArray req_rela_json = JSONArray.parseArray((String)mapVo.get("req_rela"));
		Iterator iterator = req_rela_json.iterator();
		while (iterator.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
			mode.addAttribute("inv_id", jsonObj.get("inv_id"));
			mode.addAttribute("req_id", jsonObj.get("req_id"));
		}
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/purchase/make/medRequireAmountDetail";

	}
	
	/**
	 * @Description 
	 * 采购计划编制 查看药品当前库存明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedInvCurAmountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedInvCurAmountDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurMainJson = medPurMainService.queryMedInvCurAmountDetail(mapVo);
		return JSONObject.parseObject(medPurMainJson);

	}
	
	/**
	 * @Description 
	 * 采购计划编制 查询计划数量明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedRequireAmountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedRequireAmountDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurMainJson = medPurMainService.queryMedRequireAmountDetail(mapVo);
		
		return JSONObject.parseObject(medPurMainJson);

	}
	
	
	
	/**
	 * @Description 
	 * 采购计划编制 添加
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/addMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson;
		try {
			medJson = medPurMainService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * @Description 
	 * 采购计划编制 删除
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/deleteMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String paramVo = mapVo.get("paramVo").toString();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> map=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				map.put("group_id", ids[0])   ;
				map.put("hos_id", ids[1])   ;
				map.put("copy_code", ids[2])   ;
				map.put("pur_id", ids[3]);
				map.put("come_from", ids[4]);
				// 用于删除与申请单之前的对应关系
				map.put("rela_type", "1");
				map.put("rela_id", ids[3]);
				listVo.add(map);
			}
			
			String medJson;
			try {
				medJson = medPurMainService.deleteBatch(listVo);
			} catch (Exception e) {
				medJson = e.getMessage();
			}
			
			return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * @Description 
	 * 采购计划编制 中止采购计划
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/updateMedPurPlanState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMedPurPlanState(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String paramVo = mapVo.get("paramVo").toString();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> map=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				map.put("group_id", ids[0])   ;
				map.put("hos_id", ids[1])   ;
				map.put("copy_code", ids[2])   ;
				map.put("pur_id", ids[3]);
				map.put("planState", ids[4]);
				listVo.add(map);
			}
			
			String medJson;
			try {
				medJson = medPurMainService.updateMedPurPlanState(listVo);
			} catch (Exception e) {
				medJson = e.getMessage();
			}
			
			return JSONObject.parseObject(medJson);	
	}
	
	/**
	 * @Description 
	 * 采购计划编制 按主表ID查询采购明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedPurDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedPurDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurDetailJson = medPurMainService.queryMedPurDetail(getPage(mapVo));
		return JSONObject.parseObject(medPurDetailJson);

	}
	
	/**
	 * @Description 
	 * 采购计划编制 更新主表和明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/updateMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson;
		try {
			medJson = medPurMainService.addOrUpdate(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * 查询科室需求计划主表
	 * @param entityMap
	 * @return Map
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedRequireMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedRequireMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medRequireMainJson = medPurMainService.queryMedRequireMain(getPage(mapVo));
		return JSONObject.parseObject(medRequireMainJson);

	}
	
	/**
	 * @Description 
	 * 按一个或多个科室计划单ID 查询科室计划单明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedRequireDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedRequireDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medRequireDetailJson = medPurMainService.queryMedRequireDetailByCode(getPage(mapVo));
		return JSONObject.parseObject(medRequireDetailJson);

	}
	
	
	/**
	 * @Description 
	 * 按需求计划生成 添加采购计划
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/addProdMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addProdMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medJson;
		try {
			medJson = medPurMainService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * @Description 
	 * 按科室需求计划单ID 查询科室计划单明细 
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedReqByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedReqByCode(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medRequireDetailJson = medPurMainService.queryMedReqByCode(getPage(mapVo));
		return JSONObject.parseObject(medRequireDetailJson);

	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/purMainMakePrintSetPage", method = RequestMethod.GET)
	public String purMainMakePrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedMakeByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedMakeByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}
		System.out.println("=============="+mapVo.get("p_num"));
		String reJson=null;
		try {
			reJson=medPurMainService.queryMedMakeByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * @Description 
	 * 采购模板打印（按供应商汇总明细打印） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedMakeByDetailPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedMakeByDetailPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String reJson=null;
		try {
			reJson=medPurMainService.queryMedMakeByDetailPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * @Description 
	 * 查询仓库需求计划
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedReqStoreMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedReqStoreMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medReqStoreMainJson = medPurMainService.queryMedReqStoreMain(getPage(mapVo));
		
		return JSONObject.parseObject(medReqStoreMainJson);

	}
	
	//组装仓库需求计划
	@RequestMapping(value = "/hrp/med/purchase/make/queryStoreCollectData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreCollectData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
			
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
			
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String detailData = medPurMainService.queryStoreCollectData(mapVo);
			
		return JSONObject.parseObject(detailData);
			
	}
	
	/**
	 * @Description 
	 * 查询科室需求计划
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedReqDeptMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedReqDeptMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medReqStoreMainJson = medPurMainService.queryMedReqDeptMain(getPage(mapVo));
		
		return JSONObject.parseObject(medReqStoreMainJson);

	}
	//组装仓库需求计划
	@RequestMapping(value = "/hrp/med/purchase/make/queryDeptCollectData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptCollectData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
				
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
				
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
				
		String detailData = medPurMainService.queryDeptCollectData(mapVo);
		//System.out.println("....."+detailData);
				
		return JSONObject.parseObject(detailData);
				
	}
	
	//查看供应商证件信息
	@RequestMapping(value = "/hrp/med/purchase/make/medPurSupMainPage", method = RequestMethod.GET)
	public String medPurSupMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		
		return "hrp/med/purchase/make/medPurSupMainPage";

	}
	
	/**
	 * 查看供应商信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedPurSupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedPurSupDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String medPurMainJson =  medPurMainService.querySupDetails(getPage(mapVo));
		
		
		return JSONObject.parseObject(medPurMainJson);

	}
	
	//组装明细数据
	@RequestMapping(value = "/hrp/med/purchase/make/queryPurDetailCollectData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPurDetailCollectData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
			
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String detailData = medPurMainService.queryPurDetailCollectData(mapVo);
			
		return JSONObject.parseObject(detailData);
			
	}
	/**
	 * 生成订单页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/addOrderPage", method = RequestMethod.GET)
	public String addOrderPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08007", MyConfig.getSysPara("08007"));
		mode.addAttribute("p08008", MyConfig.getSysPara("08008"));
		
		
		return "/hrp/med/purchase/make/addOrderPage";
	}
	
	/**
	 * 保存
	 * @param mapVo
	 * @param mode
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/addMedOrderInitByPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedOrderInitByPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("maker") == null) {
			mapVo.put("maker", SessionManager.getUserId());
		}
		if (mapVo.get("make_date") == null) {
			mapVo.put("make_date", new Date());
		}
		
		String medJson;
		
		try {
			medJson = medOrderInitService.addMedOrderInitByPur(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		if(medJson.contains("true")){
			mapVo.put("state", "2");
			mapVo.put("flag", "0");
			mapVo.put("whereSql"," and ot.pur_amount = pt.pur_amount ");
			
			medOrderInitService.updateMedPurState(mapVo);
		}
		
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * 关闭药品
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/updateMedPurCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPurCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("pur_id", ids[3]);
			map.put("pur_detail_id", ids[4]);
			listVo.add(map);
		}

		String medJson;
		try {
			medJson = medPurMainService.updateMedPurCloseInv(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * 查看已经关闭的药品列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/medPurMainCloseInvInfoPage", method = RequestMethod.GET)
	public String medPurMainCloseInvInfoPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/purchase/make/medPurMainCloseInvInfo";
	}
	/**
	 * 查看关闭的药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedPurCloseInvInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurCloseInvInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
			
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String detailData = medPurMainService.queryMedPurCloseInvInfo(mapVo);
			
		return JSONObject.parseObject(detailData);
			
	}
	/**
	 * 取消关闭药品
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/updateMedPurCancleCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPurCancleCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("pur_id", ids[3]);
			map.put("pur_detail_id", ids[4]);
			listVo.add(map);
		}

		String medJson;
		try {
			medJson = medPurMainService.updateMedPurCancleCloseInv(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//计划数量明细页面跳转
	@RequestMapping(value = "/hrp/med/purchase/make/medPurAmountRelaPage", method = RequestMethod.GET)
	public String medRequireAmountRelaPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("pur_id",mapVo.get("pur_id").toString());
		mode.addAttribute("pur_detail_id",mapVo.get("pur_detail_id").toString());
		mode.addAttribute("rowindex", mapVo.get("rowindex").toString());
		mode.addAttribute("inv_code", mapVo.get("inv_code").toString());
		mode.addAttribute("inv_name", mapVo.get("inv_name").toString());
		mode.addAttribute("state", mapVo.get("state").toString());
		
		
		mode.addAttribute("p08086", MyConfig.getSysPara("08086"));

		
		return "hrp/med/purchase/make/medPurAmountRela";
	}
	
	/**
	 * 查询采购数量来源
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/purchase/make/queryMedPurAmountRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurAmountRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String detailData = medPurMainService.queryMedPurAmountRela(mapVo);
			
		return JSONObject.parseObject(detailData);
	}
	
	/**
	 * @Description 
	 * 更新对应关系
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/updateMedPurAmountRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMedPurAmountRela(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String medJson;
		try {
			medJson = medPurMainService.updateMedPurAmountRela(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 根据安全库存生成采购计划
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/medPurPlanGenBySecuLimitPage", method = RequestMethod.GET)
	public String medPurPlanGenBySecuLimitPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		return "/hrp/med/purchase/make/medPurPlanGenBySecuLimit";
	}
	
	/**
	 * @Description 
	 * 业务处理 根据安全库存生成采购计划
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/medPurPlanGenBySecuLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> medPurPlanGenBySecuLimit(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String medJson;
		try {
			medJson = medPurMainService.addMedPurPlanBySecuLimit(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 
	 * 更新对应关系
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/make/deleteMedPurDetailInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedPurDetailInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("pur_id", ids[3]);
			map.put("pur_detail_id", ids[4]);
			listVo.add(map);
		}

		String medJson;
		try {
			medJson = medPurMainService.deleteMedPurDetailInv(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
}
