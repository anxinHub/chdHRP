/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.order.init;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.med.service.order.init.MedOrderInitService;
import com.chd.hrp.med.service.purchase.make.MedPurMainService;
import com.chd.hrp.med.service.storage.in.MedStorageInService;
/**
 * 
 * @Description:
 * MED_ORDER_MAIN
 * @Table:
 * MED_ORDER_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MedOrderInitController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedOrderInitController.class);
	
	//引入Service服务
	@Resource(name = "medOrderInitService")
	private final MedOrderInitService medOrderInitService = null;
	@Resource(name = "medStorageInService")
	private final MedStorageInService medStorageInService = null;
	
	@Resource(name = "medPurMainService")
	private final MedPurMainService medPurMainService = null;
	/**
	 * @Description 
	 * 订单页面--主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/order/init/medOrderInitMainPage", method = RequestMethod.GET)
	public String medOrderInitMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		mode.addAttribute("p08023", MyConfig.getSysPara("08023"));
		mode.addAttribute("p08033", MyConfig.getSysPara("08033"));
		mode.addAttribute("p08040", MyConfig.getSysPara("08040"));
		
		return "hrp/med/order/init/medOrderInitMain";

	}
	
	/**
	 * 订单编制--主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/queryMedOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOrderInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		
		String medOrder = medOrderInitService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medOrder);
	}
	/**
	 * 订单页面--增加
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/medOrderInitAddPage", method = RequestMethod.GET)
	public String medOrderInitAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08007", MyConfig.getSysPara("08007"));
		mode.addAttribute("p08008", MyConfig.getSysPara("08008"));

		return "hrp/med/order/init/medOrderInitAdd";

	}
	/**
	 * @Description 添加数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/addMedOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedOrderInit(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("maker") == null) {
			mapVo.put("maker", SessionManager.getUserId());
		}
		if (mapVo.get("make_date") == null) {
			mapVo.put("make_date", new Date());
		}
		if(mapVo.get("order_date") == null || "".equals(mapVo.get("order_date"))){
			return JSONObject.parseObject("{\"error\":\"订单日期不能为空\",\"state\":\"false\"}");
		}
		//截取期间
		String[] date = mapVo.get("order_date").toString().split("-");
		mapVo.put("year", date[0]);
		mapVo.put("month", date[1]);
		
		//转换日期格式
		if(mapVo.get("order_date") != null && !"".equals(mapVo.get("order_date"))){
			mapVo.put("order_date", DateUtil.stringToDate(mapVo.get("order_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
			mapVo.put("make_date", DateUtil.dateToString((Date) mapVo.get("make_date"), "yyyy-MM-dd"));
		}
		
		
		String medJson = medOrderInitService.add(mapVo);
		if(medJson.contains("true")){
			mapVo.put("state", "3");
			mapVo.put("flag", "0");
			mapVo.put("whereSql"," and ot.pur_amount != pt.pur_amount ");
			medOrderInitService.updateMedPurState(mapVo);
		}
		
		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * 订单页面--代销生成页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/medOrderInitAffiPage", method = RequestMethod.GET)
	public String medOrderInitAffiPage(Model mode) throws Exception {
		
		return "hrp/med/order/init/medOrderInitAffi";

	}
	/**
	 * 订单代销使用页面--查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/queryMedOrderInitAffi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOrderInitAffi(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		} 
	
		String medAffiOut = medOrderInitService.queryAffiOut(mapVo);

		return JSONObject.parseObject(medAffiOut);

	}
	/**
	 * 订单代销生成--查看出库明细页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/medOrderInitAffiDetailPage", method = RequestMethod.GET)
	public String medOrderInitAffiDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		Map<String, Object> medAffiOut = medOrderInitService.queryAffiOutMain(mapVo);
		
		mode.addAttribute("medAffiOut", medAffiOut);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		
		return "hrp/med/order/init/medOrderInitAffiDetail";

	}
	/**
	 * 订单代销生成--查看出库明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/queryMedOrderInitAffiOutDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOrderInitAffiOutDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
	
		String medAffiOut = medOrderInitService.queryAffiOutDetail(mapVo);

		return JSONObject.parseObject(medAffiOut);

	}
	
	/**
	 * @Description 代销使用生成订单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/genByAffiMedOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> genByAffiMedOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("out_id", ids[3]);
			mapVo.put("make_date", new Date());
			
			if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
				mapVo.put("make_date", DateUtil.dateToString((Date) mapVo.get("make_date"), "yyyy-MM-dd"));
			}
			//截取期间
			String[] date = mapVo.get("make_date").toString().split("-");
			mapVo.put("year", date[0]);
			mapVo.put("month", date[1]);
			
			listVo.add(mapVo);
		}
		String medOrderMain = medOrderInitService.genByAffiMedOrderInit(listVo);
		
		return JSONObject.parseObject(medOrderMain);
	}
	
	/**
	 * 订单修改页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/medOrderInitUpdatePage", method = RequestMethod.GET)
	public String medOrderInitUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		//System.out.println("*************** :"+mapVo.get("order_id"));
		
		Map<String, Object> medOrderMain = medOrderInitService.queryByCode(mapVo);
		
		
		mode.addAttribute("medOrderMain", medOrderMain);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08007", MyConfig.getSysPara("08007"));
		mode.addAttribute("p08023", MyConfig.getSysPara("08023"));
		
		
		return "hrp/med/order/init/medOrderInitUpdate";

	}
	
	/**
	 * @Description 修改页面：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/queryMedOrderInitDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOrderInitDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
	
		String medJson = medOrderInitService.queryOrderDetail(mapVo);

		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * @Description 订单修改
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/updateMedOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedOrderInit(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
	
		String medJson = medOrderInitService.update(mapVo);
		
		if(medJson.contains("true")){
			mapVo.put("state", "3");
			mapVo.put("flag", "1");
			mapVo.put("whereSql"," and pt.pur_amount != ot.pur_amount ");
			medOrderInitService.updateMedPurState(mapVo);
		}
		
		return JSONObject.parseObject(medJson);

	}
	/**
	 * 订单编制--合并订单
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/mergeMedOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> mergeMedOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("order_id", ids[3]);
			mapVo.put("make_date", new Date());
			
			if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
				mapVo.put("make_date", DateUtil.dateToString((Date) mapVo.get("make_date"), "yyyy-MM-dd"));
			}
			//截取期间
			String[] date = mapVo.get("make_date").toString().split("-");
			mapVo.put("year", date[0]);
			mapVo.put("month", date[1]);
			
			listVo.add(mapVo);
		}
			String medOrderMain = medOrderInitService.mergeMedOrderInit(listVo);
		
		return JSONObject.parseObject(medOrderMain);
	}
	/**
	 * 订单编制--中止订单
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/abortMedOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> abortMedOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("order_id", ids[3]);
			listVo.add(mapVo);
		}
		String medOrderMain = medOrderInitService.abortMedOrderInit(listVo);
		
		return JSONObject.parseObject(medOrderMain);
	}
	
	/**
	 * 订单编制--删除订单
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/deleteMedOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> deleteMedOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("order_id", ids[3]);
			listVo.add(mapVo);
		}
		String medOrderMain = medOrderInitService.deleteBatch(listVo);
		
		return JSONObject.parseObject(medOrderMain);
	}
	
	/**
	 * 订单编制--采购计划生成页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/medOrderInitGenPurPage", method = RequestMethod.GET)
	public String medOrderInitGenPurPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/order/init/medOrderInitGenPur";

	}
	

	
	/**
	 * @Description 订单编制   ---查询已审核采购订单主表信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/queryMedOrderGenPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOrderGenPur(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String medPur = medOrderInitService.queryMedOrderGenPur(mapVo);

		return JSONObject.parseObject(medPur);

	}
	
	/**
	 * 订单编制--查看采购计划明细页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/medOrderInitGenPurDetailPage", method = RequestMethod.GET)
	public String medOrderInitGenPurDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String, Object> medPurMainMap = medOrderInitService.queryMedPurById(mapVo);
		
		
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
		mode.addAttribute("is_dir", medPurMainMap.get("is_dir"));
		mode.addAttribute("pur_hos_id",medPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_name", medPurMainMap.get("pur_hos_name"));
		mode.addAttribute("req_hos_id", medPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_name", medPurMainMap.get("req_hos_name"));
		mode.addAttribute("pay_hos_id", medPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_name", medPurMainMap.get("pay_hos_name"));
		mode.addAttribute("dir_dept_id", medPurMainMap.get("dir_dept_id"));
		mode.addAttribute("dir_dept_no", medPurMainMap.get("dir_dept_no"));
		mode.addAttribute("dir_dept_code", medPurMainMap.get("dir_dept_code"));
		mode.addAttribute("dir_dept_name", medPurMainMap.get("dir_dept_name"));
		mode.addAttribute("state", medPurMainMap.get("state"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/order/init/medOrderInitGenPurDetail";

	}
	
	/**
	 * @Description 采购计划生成--查看采购计划明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/queryOrderInitGenPurDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOrderInitGenPurDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurDetail = medOrderInitService.queryMedPurDetail(mapVo);

		return JSONObject.parseObject(medPurDetail);

	}
	
	/**
	 * @Description 采购计划生成--生成订单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/genByPurMedOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> genByPurMedOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("pur_id", ids[3]);
			mapVo.put("is_dir", ids[4]);
			mapVo.put("make_date", new Date());
			
			if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
				mapVo.put("make_date", DateUtil.dateToString((Date) mapVo.get("make_date"), "yyyy-MM-dd"));
			}
			//截取期间
			String[] date = mapVo.get("make_date").toString().split("-");
			mapVo.put("year", date[0]);
			mapVo.put("month", date[1]);
			
			listVo.add(mapVo);
		}
		
		String medOrderMain;
		try {
			
			medOrderMain = medOrderInitService.genByPurMedOrderInit(listVo);
		} catch (Exception e) {
			
			medOrderMain = e.getMessage();
		}
		
		return JSONObject.parseObject(medOrderMain);
	}

	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/med/order/init/medOrderInitPrintSetPage", method = RequestMethod.GET)
	public String medAffiTranPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/med/order/init/queryMedOrderInitByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiTranByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=medOrderInitService.queryMedOrderInitByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	
	/**
	 * @Description 
	 * 采购计划导入页面跳转
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/order/init/medOrderInitPurImpPage", method = RequestMethod.GET)
	public String medOrderInitPurImpPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/order/init/medOrderInitPurImp";
	}
	
	/**
	 * @Description 
	 * 采购计划导入主查询 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/order/init/queryMedPurMainForOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurMainForOrder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//转换日期格式
		if(mapVo.get("begin_pur_date") != null && !"".equals(mapVo.get("begin_pur_date"))){
			mapVo.put("begin_pur_date", DateUtil.stringToDate(mapVo.get("begin_pur_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_pur_date") != null && !"".equals(mapVo.get("end_pur_date"))){
			mapVo.put("end_pur_date", DateUtil.stringToDate(mapVo.get("end_pur_date").toString(), "yyyy-MM-dd"));
		}
		
		String reJson=null;
		try {
			reJson=medOrderInitService.queryMedPurMainForOrder(getPage(mapVo));
		} catch (Exception e) {
			
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * @Description 
	 * 采购计划导入明细查询 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/order/init/queryMedPurDetailForOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurDetailForOrder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=medOrderInitService.queryMedPurDetailForOrder(mapVo);
		} catch (Exception e) {
			
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}

	/**
	 * @Description 
	 * 关闭采购计划明细  
	 * @param ParamVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/updateMedPurDetailByOrderClose", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPurDetailByOrderClose(@RequestParam(value="ParamVo") String paramVo, Model mode)throws Exception {

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
			
			medJson = medOrderInitService.updateMedPurDetailByOrderClose(listVo);
		} catch (Exception e) {
			
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 采购计划生成科室需求计划  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/addMedOrderByPurImp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedOrderByPurImp(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson;
		try {
			medJson = medOrderInitService.addMedOrderByPurImp(mapVo);
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
	 * 根据采购明细生成订单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/addMedOrderByPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedOrderByPur(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson;
		try {
			medJson = medOrderInitService.addMedOrderByPurDetail(mapVo);
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
	
	@RequestMapping(value = "/hrp/med/order/init/medOrderPurImpPage", method = RequestMethod.GET)
	public String medOrderPurImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String start_date = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("mod_code") == null) {
			mapVo.put("mod_code", "08");
		}
		start_date = medStorageInService.queryModeStartDate(mapVo);
		mode.addAttribute("start_date", start_date);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		
		return "hrp/med/order/init/medOrderPurImp";
	}
	
	
	/**
	 * @Description 
	 * 采购计划导入明细展示
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/order/init/queryMedPurDetailGenOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurDetailGenOrder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=medOrderInitService.queryMedPurDetailGenOrder(mapVo);
		} catch (Exception e) {
			
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 关闭药品
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/updateMedPurCloseInvs", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/med/order/init/medPurMainCloseInvInfosPage", method = RequestMethod.GET)
	public String medPurMainCloseInvInfosPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		if (paras != null && !"".equals(paras)) {
			String[] paraArray = paras.split("@");
			if (!"null".equals(paraArray[0])) {
				mode.addAttribute("sup_id", paraArray[0]);
			}
			if (!"null".equals(paraArray[1])) {
				mode.addAttribute("sup_text", paraArray[1]);
			}
		}
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/order/init/medPurMainCloseInvInfos";
	}
	/**
	 * 查看关闭的药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/init/queryMedPurCloseInvInfos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurCloseInvInfos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
	@RequestMapping(value = "/hrp/med/order/init/updateMedPurCancleCloseInvs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPurCancleCloseInvs(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
}

