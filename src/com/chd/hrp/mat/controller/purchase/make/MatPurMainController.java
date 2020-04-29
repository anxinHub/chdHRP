package com.chd.hrp.mat.controller.purchase.make;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.service.order.init.MatOrderInitService;
import com.chd.hrp.mat.service.purchase.check.MatPurMainCheckService;
import com.chd.hrp.mat.service.purchase.make.MatPurMainService;
import com.chd.hrp.mat.service.requrie.store.MatStoreRequirePlanService;

/**
 *  
 * @Description:
 * 04113 采购计划编制
 * @Table:
 * MAT_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatPurMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatPurMainController.class);
	//引入Service服务
	@Resource(name = "matPurMainService")
	private final MatPurMainService matPurMainService = null ;
	
	@Resource(name = "matOrderInitService")
	private final MatOrderInitService matOrderInitService = null;
	
	@Resource(name = "matPurMainCheckService")
	private final MatPurMainCheckService matPurMainCheckService = null;
	
	@Resource(name = "matStoreRequirePlanService")
	private final MatStoreRequirePlanService matStoreRequirePlanService = null;
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//主页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/make/matPurMainMainPage", method = RequestMethod.GET)
	public String matPurMainMainPage(Model mode) throws Exception {

		mode.addAttribute("p04040", MyConfig.getSysPara("04040"));
		mode.addAttribute("p04046", MyConfig.getSysPara("04046"));
		mode.addAttribute("p04049", MyConfig.getSysPara("04049"));
		
		return "hrp/mat/purchase/make/matPurMainMain";
	}
	
	/**
	 * @Description 
	 * 采购计划编制 查询
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
		
		String matPurMainJson = "";
		if(mapVo.get("show_detail").equals("1")){
			matPurMainJson =  matPurMainService.queryDetails(getPage(mapVo));
		}else{
			matPurMainJson =  matPurMainService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(matPurMainJson);

	}
	
	/**
	 * @Description 
	 * 采购计划 审核
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/checkMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
			
		String matPurMainCheckJson = matPurMainCheckService.checkMatPurMain(listVo);
		
		return JSONObject.parseObject(matPurMainCheckJson);
	}
	
	/**
	 * @Description 
	 * 采购计划 取消审核
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/cancelCheckMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> cancelCheckMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
			
		String matPurMainCheckJson = matPurMainCheckService.cancelCheckMatPurMain(listVo);
		
		return JSONObject.parseObject(matPurMainCheckJson);
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//添加页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/make/addMatPurMainPage", method = RequestMethod.GET)
	public String addMatPurMainPage(Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("req_hos_id", SessionManager.getHosId());
		mode.addAttribute("req_hos_code", SessionManager.getHosCode());
		mode.addAttribute("req_hos_name", SessionManager.getHosSimple());

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04013", MyConfig.getSysPara("04013"));
		
		return "hrp/mat/purchase/make/matPurMainAdd";
	}
	
	/**
	 * @Description 
	 * 引入仓库需求计划
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/matStoreDetailImpPage", method = RequestMethod.GET)
	public String matStoreDetailImpPage(Model mode) throws Exception {

		mode.addAttribute("p04031", MyConfig.getSysPara("04031"));
		
		return "hrp/mat/purchase/make/matStoreDetailImp";
	}
	
	/**
	 * @Description 
	 * 引入科室需求计划
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/matDeptDetailImpPage", method = RequestMethod.GET)
	public String matDeptDetailImpPage(Model mode) throws Exception {
		
		mode.addAttribute("p04031", MyConfig.getSysPara("04031"));
		mode.addAttribute("p04077", MyConfig.getSysPara("04077"));
		
		return "hrp/mat/purchase/make/matDeptDetailImp";
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 安全库存
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/matDeptRequriedSafePage", method = RequestMethod.GET)
	public String matDeptRequriedSafePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		return "/hrp/mat/purchase/make/matDeptRequriedPlanSafeImp";
	}

	/**
	 * 安全库存导入查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatPurMainSafe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurMainSafe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matRequireMainJson = matPurMainService.queryMatPurSafe(mapVo);

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//仓库需求明细页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/make/matStoreRequireDetail", method = RequestMethod.GET)
	public String matStoreRequireDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		 
		Map<String,Object> map = matStoreRequirePlanService.queryByCode(mapVo);
		
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
		
		return "hrp/mat/purchase/make/matStoreRequireDetail";

	}
	
	//科室需求明细页面跳转
		@RequestMapping(value = "/hrp/mat/purchase/make/matRequireDetail", method = RequestMethod.GET)
		public String matRequireDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			
			if(mapVo.get("group_id") == null){
		    	mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			 
			Map<String,Object> map =  matPurMainService.queryMatRequireMainByCode(mapVo);
			
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
			
			return "hrp/mat/purchase/make/matRequireDetail";

		}
		
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//材料当前库存明细页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/make/matInvCurAmountDetailPage", method = RequestMethod.GET)
	public String matInvCurAmountDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		return "hrp/mat/purchase/make/matInvCurAmountDetail";
	}

	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//修改页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/make/updateMatPurMainPage", method = RequestMethod.GET)
	public String updateMatPurMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		Map<String,Object> matPurMainMap = matPurMainService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", matPurMainMap.get("group_id"));
		mode.addAttribute("hos_id", matPurMainMap.get("hos_id"));
		mode.addAttribute("copy_code",matPurMainMap.get("copy_code"));
		mode.addAttribute("pur_id", matPurMainMap.get("pur_id"));
		mode.addAttribute("pur_code", matPurMainMap.get("pur_code"));
		mode.addAttribute("dept_id", matPurMainMap.get("dept_id"));
		mode.addAttribute("dept_no",matPurMainMap.get("dept_no"));
		mode.addAttribute("dept_code",matPurMainMap.get("dept_code"));
		mode.addAttribute("dept_name", matPurMainMap.get("dept_name"));
		mode.addAttribute("store_id", matPurMainMap.get("store_id"));
		mode.addAttribute("store_no",matPurMainMap.get("store_no"));
		mode.addAttribute("store_code",matPurMainMap.get("store_code"));
		mode.addAttribute("store_name", matPurMainMap.get("store_name"));
		mode.addAttribute("make_date", matPurMainMap.get("make_date"));
		mode.addAttribute("arrive_date", matPurMainMap.get("arrive_date"));
		mode.addAttribute("pur_type", matPurMainMap.get("pur_type"));
		mode.addAttribute("brif", matPurMainMap.get("brif"));
		mode.addAttribute("come_from", matPurMainMap.get("come_from"));
		
		mode.addAttribute("pur_hos_id",matPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_no",matPurMainMap.get("pur_hos_no"));
		mode.addAttribute("pur_hos_code",matPurMainMap.get("pur_hos_code"));
		mode.addAttribute("pur_hos_name", matPurMainMap.get("pur_hos_name"));
		
		mode.addAttribute("req_hos_id", matPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_no", matPurMainMap.get("req_hos_no"));
		mode.addAttribute("req_hos_code", matPurMainMap.get("req_hos_code"));
		mode.addAttribute("req_hos_name", matPurMainMap.get("req_hos_name"));
		
		mode.addAttribute("pay_hos_id", matPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_no", matPurMainMap.get("pay_hos_no"));
		mode.addAttribute("pay_hos_code", matPurMainMap.get("pay_hos_code"));
		mode.addAttribute("pay_hos_name", matPurMainMap.get("pay_hos_name"));
		
		mode.addAttribute("is_dir", matPurMainMap.get("is_dir"));
		mode.addAttribute("dir_dept_id", matPurMainMap.get("dir_dept_id"));
		mode.addAttribute("dir_dept_no", matPurMainMap.get("dir_dept_no"));
		mode.addAttribute("dir_dept_code", matPurMainMap.get("dir_dept_code"));
		mode.addAttribute("dir_dept_name", matPurMainMap.get("dir_dept_name"));
		
		mode.addAttribute("state", mapVo.get("state"));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04013", MyConfig.getSysPara("04013"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		mode.addAttribute("p04046", MyConfig.getSysPara("04046"));
		
		return "hrp/mat/purchase/make/matPurMainUpdate";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//按需求计划生成 添加页面
	@RequestMapping(value = "/hrp/mat/purchase/make/matRequireProdAddPage", method = RequestMethod.GET)
	public String matRequireProdAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("is_dir", mapVo.get("is_dir"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		mode.addAttribute("is_dir", mapVo.get("is_dir"));
		mode.addAttribute("is_dept", mapVo.get("is_dept"));

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04013", MyConfig.getSysPara("04013"));
		
		return "hrp/mat/purchase/make/matRequireProdAdd";
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//计划数量明细页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/make/matRequireAmountDetailPage", method = RequestMethod.GET)
	public String matRequireAmountDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("req_rela",mapVo.get("req_rela").toString());
		mode.addAttribute("rowindex", mapVo.get("rowindex").toString());
		JSONArray req_rela_json = JSONArray.parseArray((String)mapVo.get("req_rela"));
		Iterator iterator = req_rela_json.iterator();
		while (iterator.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
			mode.addAttribute("inv_id", jsonObj.get("inv_id"));
			mode.addAttribute("req_id", jsonObj.get("req_id"));
		}
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/purchase/make/matRequireAmountDetail";

	}
	
	/**
	 * @Description 
	 * 采购计划编制 查看材料当前库存明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatInvCurAmountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatInvCurAmountDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurMainJson = matPurMainService.queryMatInvCurAmountDetail(mapVo);
		return JSONObject.parseObject(matPurMainJson);

	}
	
	/**
	 * @Description 
	 * 采购计划编制 查询计划数量明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatRequireAmountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatRequireAmountDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurMainJson = matPurMainService.queryMatRequireAmountDetail(mapVo);
		
		return JSONObject.parseObject(matPurMainJson);

	}
	
	
	
	/**
	 * @Description 
	 * 采购计划编制 添加
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/addMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson;
		try {
			matJson = matPurMainService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * @Description 
	 * 采购计划编制 删除
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/deleteMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
			
			String matJson;
			try {
				matJson = matPurMainService.deleteBatch(listVo);
			} catch (Exception e) {
				matJson = e.getMessage();
			}
			
			return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * @Description 
	 * 采购计划编制 中止采购计划
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/updateMatPurPlanState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMatPurPlanState(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
			
			String matJson;
			try {
				matJson = matPurMainService.updateMatPurPlanState(listVo);
			} catch (Exception e) {
				matJson = e.getMessage();
			}
			
			return JSONObject.parseObject(matJson);	
	}
	
	/**
	 * @Description 
	 * 采购计划编制 按主表ID查询采购明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatPurDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatPurDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurDetailJson = matPurMainService.queryMatPurDetail(getPage(mapVo));
		return JSONObject.parseObject(matPurDetailJson);

	}
	
	/**
	 * @Description 
	 * 采购计划编制 更新主表和明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/updateMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson;
		try {
			matJson = matPurMainService.addOrUpdate(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		if(matJson.contains("true")){
			matPurMainService.updateMatPurRelaTables(mapVo);
		}
		
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 查询科室需求计划主表
	 * @param entityMap
	 * @return Map
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatRequireMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatRequireMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matRequireMainJson = matPurMainService.queryMatRequireMain(getPage(mapVo));
		return JSONObject.parseObject(matRequireMainJson);

	}
	
	/**
	 * @Description 
	 * 按一个或多个科室计划单ID 查询科室计划单明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatRequireDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatRequireDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matRequireDetailJson = matPurMainService.queryMatRequireDetailByCode(getPage(mapVo));
		return JSONObject.parseObject(matRequireDetailJson);

	}
	
	
	/**
	 * @Description 
	 * 按需求计划生成 添加采购计划
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/addProdMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addProdMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matJson;
		try {
			matJson = matPurMainService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * @Description 
	 * 按科室需求计划单ID 查询科室计划单明细 
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatReqByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatReqByCode(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matRequireDetailJson = matPurMainService.queryMatReqByCode(getPage(mapVo));
		return JSONObject.parseObject(matRequireDetailJson);

	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/purMainMakePrintSetPage", method = RequestMethod.GET)
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
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatMakeByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatMakeByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}
		//System.out.println("=============="+mapVo.get("p_num"));
		String reJson=null;
		try {
			reJson=matPurMainService.queryMatMakeByPrintTemlate(mapVo);
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
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatMakeByDetailPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatMakeByDetailPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String reJson=null;
		try {
			reJson=matPurMainService.queryMatMakeByDetailPrintTemlate(mapVo);
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
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatReqStoreMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatReqStoreMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matReqStoreMainJson = matPurMainService.queryMatReqStoreMain(getPage(mapVo));
		
		return JSONObject.parseObject(matReqStoreMainJson);

	}
	
	//组装仓库需求计划
	@RequestMapping(value = "/hrp/mat/purchase/make/queryStoreCollectData", method = RequestMethod.POST)
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
			
		String detailData = matPurMainService.queryStoreCollectData(mapVo);
			
		return JSONObject.parseObject(detailData);
			
	}
	
	/**
	 * @Description 
	 * 查询科室需求计划
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatReqDeptMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatReqDeptMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matReqStoreMainJson = matPurMainService.queryMatReqDeptMain(getPage(mapVo));
		
		return JSONObject.parseObject(matReqStoreMainJson);

	}
	//组装仓库需求计划
	@RequestMapping(value = "/hrp/mat/purchase/make/queryDeptCollectData", method = RequestMethod.POST)
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
				
		String detailData = matPurMainService.queryDeptCollectData(mapVo);
				
		return JSONObject.parseObject(detailData);
				
	}
	
	//查看供应商证件信息
	@RequestMapping(value = "/hrp/mat/purchase/make/matPurSupMainPage", method = RequestMethod.GET)
	public String matPurSupMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		return "hrp/mat/purchase/make/matPurSupMainPage";

	}
	
	/**
	 * 查看供应商信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatPurSupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatPurSupDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
		
		String matPurMainJson =  matPurMainService.querySupDetails(getPage(mapVo));
		
		
		return JSONObject.parseObject(matPurMainJson);

	}
	
	//组装明细数据
	@RequestMapping(value = "/hrp/mat/purchase/make/queryPurDetailCollectData", method = RequestMethod.POST)
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
			
		String detailData = matPurMainService.queryPurDetailCollectData(mapVo);
			
		return JSONObject.parseObject(detailData);
			
	}
	/**
	 * 生成订单页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/addOrderPage", method = RequestMethod.GET)
	public String addOrderPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		mode.addAttribute("is_com", mapVo.get("is_com"));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04007", MyConfig.getSysPara("04007"));
		mode.addAttribute("p04008", MyConfig.getSysPara("04008"));
		
		return "/hrp/mat/purchase/make/addOrderPage";
	}
	
	/**
	 * 保存
	 * @param mapVo
	 * @param mode
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/addMatOrderInitByPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatOrderInitByPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matJson;
		
		try {
			matJson = matOrderInitService.addMatOrderInitByPur(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		if(matJson.contains("true")){
			mapVo.put("state", "2");
			mapVo.put("flag", "0");
			//mapVo.put("whereSql"," and ot.pur_amount = pt.pur_amount ");
			mapVo.put("whereSql"," and nvl(rt.countnum,0) = nvl(rt.equalnum,0) and nvl(rt.zeronum,0) = 0 ");
			
			matOrderInitService.updateMatPurState(mapVo);
		}
		
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 关闭材料
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/updateMatPurCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPurCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String matJson;
		try {
			matJson = matPurMainService.updateMatPurCloseInv(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 查看已经关闭的材料列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/matPurMainCloseInvInfoPage", method = RequestMethod.GET)
	public String matPurMainCloseInvInfoPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/purchase/make/matPurMainCloseInvInfo";
	}
	/**
	 * 查看关闭的材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatPurCloseInvInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurCloseInvInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
			
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String detailData = matPurMainService.queryMatPurCloseInvInfo(mapVo);
			
		return JSONObject.parseObject(detailData);
			
	}
	/**
	 * 取消关闭材料
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/updateMatPurCancleCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPurCancleCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String matJson;
		try {
			matJson = matPurMainService.updateMatPurCancleCloseInv(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//计划数量明细页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/make/matPurAmountRelaPage", method = RequestMethod.GET)
	public String matRequireAmountRelaPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("pur_id",mapVo.get("pur_id"));
		mode.addAttribute("pur_detail_id",mapVo.get("pur_detail_id"));
		mode.addAttribute("rowindex", mapVo.get("rowindex"));
		mode.addAttribute("inv_code", mapVo.get("inv_code"));
		mode.addAttribute("inv_name", mapVo.get("inv_name"));
		mode.addAttribute("state", mapVo.get("state"));

		mode.addAttribute("p04046", MyConfig.getSysPara("04046"));
		
		return "hrp/mat/purchase/make/matPurAmountRela";
	}
	
	/**
	 * 查询采购数量来源
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatPurAmountRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurAmountRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String detailData = matPurMainService.queryMatPurAmountRela(mapVo);
			
		return JSONObject.parseObject(detailData);
	}
	
	/**
	 * @Description 
	 * 更新对应关系
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/updateMatPurAmountRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMatPurAmountRela(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matJson;
		try {
			matJson = matPurMainService.updateMatPurAmountRela(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 根据安全库存生成采购计划
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/matPurPlanGenBySecuLimitPage", method = RequestMethod.GET)
	public String matPurPlanGenBySecuLimitPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		return "/hrp/mat/purchase/make/matPurPlanGenBySecuLimit";
	}
	
	/**
	 * @Description 
	 * 业务处理 根据安全库存生成采购计划
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/matPurPlanGenBySecuLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> matPurPlanGenBySecuLimit(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matJson;
		try { 
			matJson = matPurMainService.addMatPurPlanBySecuLimit(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 
	 * 更新对应关系
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/deleteMatPurDetailInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatPurDetailInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String matJson;
		try {
			matJson = matPurMainService.deleteMatPurDetailInv(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 
	 * 采购计划修改  材料近三月,六月入出库页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	//材料当前库存明细页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/make/matInvRecentInOutAmountPage", method = RequestMethod.GET)
	public String matInvRecentInOutAmountPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("end_date", mapVo.get("end_date"));
		return "hrp/mat/purchase/make/matInvRecentInOutAmount";
	}
	
	/**
	 * @Description 
	 * 采购计划修改  查看材料近三个月,六个月的入出库数量
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/make/queryMatInvRecentInOutAmount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatInvRecentInOutAmount(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurMainJson = matPurMainService.queryMatInvRecentInOutAmount(mapVo);
		return JSONObject.parseObject(matPurMainJson);

	}
}
