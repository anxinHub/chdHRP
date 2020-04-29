/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.order.init;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.jdbc.ConfigInit;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.service.order.init.MatOrderInitService;
import com.chd.hrp.mat.service.purchase.make.MatPurMainService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
/**
 * 
 * @Description:
 * MAT_ORDER_MAIN
 * @Table:
 * MAT_ORDER_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com 
 * @Version: 1.0
 */
 
@Controller
public class MatOrderInitController extends BaseController{    
	
	private static Logger logger = Logger.getLogger(MatOrderInitController.class);
	
	//引入Service服务
	@Resource(name = "matOrderInitService")
	private final MatOrderInitService matOrderInitService = null;
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	
	
	@Resource(name = "matPurMainService")
	private final MatPurMainService matPurMainService = null;
	/**
	 * @Description 
	 * 订单页面--主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/order/init/matOrderInitMainPage", method = RequestMethod.GET)
	public String matOrderInitMainPage(Model mode) throws Exception {
		
		String url=ConfigInit.getConfigProperties("qzj_url");
		if(url!=null && !url.equals("")){
			mode.addAttribute("is_qzj", "true");
		}
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04023", MyConfig.getSysPara("04023"));
		mode.addAttribute("p04033", MyConfig.getSysPara("04033"));
		mode.addAttribute("p04040", MyConfig.getSysPara("04040"));
		
		return "hrp/mat/order/init/matOrderInitMain";

	}
	
	/**
	 * 订单编制--主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/queryMatOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		if(mapVo.get("show_detail") != null && "1".equals(mapVo.get("show_detail").toString())){
			String matOrder = matOrderInitService.queryShowDetail(getPage(mapVo));
			return JSONObject.parseObject(matOrder);
		}else{
			String matOrder = matOrderInitService.query(getPage(mapVo));
			return JSONObject.parseObject(matOrder);
		}
		
	}
	/**
	 * 订单页面--增加
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/matOrderInitAddPage", method = RequestMethod.GET)
	public String matOrderInitAddPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04007", MyConfig.getSysPara("04007"));
		mode.addAttribute("p04008", MyConfig.getSysPara("04008"));
		return "hrp/mat/order/init/matOrderInitAdd";
	}
	/**
	 * @Description 添加数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/addMatOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatOrderInit(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		

		
		String matJson = "";
		try {
			matJson = matOrderInitService.add(mapVo);
		} catch (Exception e) {
			matJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * 订单页面--代销生成页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/matOrderInitAffiPage", method = RequestMethod.GET)
	public String matOrderInitAffiPage(Model mode) throws Exception {
		
		return "hrp/mat/order/init/matOrderInitAffi";

	}
	/**
	 * 订单代销使用页面--查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/queryMatOrderInitAffi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderInitAffi(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		} 
	
		String matAffiOut = matOrderInitService.queryAffiOut(mapVo);

		return JSONObject.parseObject(matAffiOut);

	}
	/**
	 * 订单代销生成--查看出库明细页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/matOrderInitAffiDetailPage", method = RequestMethod.GET)
	public String matOrderInitAffiDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		Map<String, Object> matAffiOut = matOrderInitService.queryAffiOutMain(mapVo);
		
		mode.addAttribute("matAffiOut", matAffiOut);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/order/init/matOrderInitAffiDetail";

	}
	/**
	 * 订单代销生成--查看出库明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/queryMatOrderInitAffiOutDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderInitAffiOutDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
	
		String matAffiOut = matOrderInitService.queryAffiOutDetail(mapVo);

		return JSONObject.parseObject(matAffiOut);

	}
	
	/**
	 * @Description 代销使用生成订单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/genByAffiMatOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> genByAffiMatOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		
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
		
		String matJson = "";
		try {
			matJson = matOrderInitService.genByAffiMatOrderInit(listVo);
		} catch (Exception e) {
			matJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 订单修改页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/matOrderInitUpdatePage", method = RequestMethod.GET)
	public String matOrderInitUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		Map<String, Object> matOrderMain = matOrderInitService.queryByCode(mapVo);
		
		mode.addAttribute("matOrderMain", matOrderMain);
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04007", MyConfig.getSysPara("04007"));
		mode.addAttribute("p04008", MyConfig.getSysPara("04008"));
		mode.addAttribute("p04023", MyConfig.getSysPara("04023"));
		mode.addAttribute("p04033", MyConfig.getSysPara("04033"));
		return "hrp/mat/order/init/matOrderInitUpdate";
	}
	
	/**
	 * @Description 修改页面：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/queryMatOrderInitDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderInitDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
	
		String matJson = matOrderInitService.queryOrderDetail(mapVo);

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 订单明细省平台状态更新同步
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/stateMatOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> stateMatOrderInit(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
	
		String matJson = matOrderInitService.stateMatOrderInit(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	
	
	/**
	 * @Description 订单修改
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/updateMatOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatOrderInit(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = "";
		try {
			matJson = matOrderInitService.update(mapVo);
		} catch (Exception e) {
			matJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(matJson);

	}
	/**
	 * 订单编制--合并订单
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/mergeMatOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> mergeMatOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String matJson = "";
		try {
			matJson = matOrderInitService.mergeMatOrderInit(listVo);
		} catch (Exception e) {
			matJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	/**
	 * 订单编制--中止订单
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/abortMatOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> abortMatOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String matJson = "";
		
		try {
			matJson = matOrderInitService.abortMatOrderInit(listVo);
		} catch (Exception e) {
			matJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 订单编制--删除订单
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/deleteMatOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> deleteMatOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String matJson = "";
		
		try {
			matJson = matOrderInitService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 订单编制--采购计划生成页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/matOrderInitGenPurPage", method = RequestMethod.GET)
	public String matOrderInitGenPurPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/order/init/matOrderInitGenPur";

	}
	

	
	/**
	 * @Description 订单编制   ---查询已审核采购订单主表信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/queryMatOrderGenPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderGenPur(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String matPur = matOrderInitService.queryMatOrderGenPur(mapVo);

		return JSONObject.parseObject(matPur);

	}
	
	/**
	 * 订单编制--查看采购计划明细页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/matOrderInitGenPurDetailPage", method = RequestMethod.GET)
	public String matOrderInitGenPurDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String, Object> matPurMainMap = matOrderInitService.queryMatPurById(mapVo);
		
		
		mode.addAttribute("group_id", matPurMainMap.get("group_id"));
		mode.addAttribute("hos_id", matPurMainMap.get("hos_id"));
		mode.addAttribute("copy_code",matPurMainMap.get("copy_code"));
		mode.addAttribute("pur_id", matPurMainMap.get("pur_id"));
		mode.addAttribute("pur_code", matPurMainMap.get("pur_code"));
		mode.addAttribute("dept_id", matPurMainMap.get("dept_id"));
		mode.addAttribute("dept_no",matPurMainMap.get("dept_no"));
		mode.addAttribute("dept_code",matPurMainMap.get("dept_code"));
		mode.addAttribute("dept_name", matPurMainMap.get("dept_name"));
		mode.addAttribute("make_date", matPurMainMap.get("make_date"));
		mode.addAttribute("arrive_date", matPurMainMap.get("arrive_date"));
		mode.addAttribute("pur_type", matPurMainMap.get("pur_type"));
		mode.addAttribute("brif", matPurMainMap.get("brif"));
		mode.addAttribute("is_dir", matPurMainMap.get("is_dir"));
		mode.addAttribute("pur_hos_id",matPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_name", matPurMainMap.get("pur_hos_name"));
		mode.addAttribute("req_hos_id", matPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_name", matPurMainMap.get("req_hos_name"));
		mode.addAttribute("pay_hos_id", matPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_name", matPurMainMap.get("pay_hos_name"));
		mode.addAttribute("dir_dept_id", matPurMainMap.get("dir_dept_id"));
		mode.addAttribute("dir_dept_no", matPurMainMap.get("dir_dept_no"));
		mode.addAttribute("dir_dept_code", matPurMainMap.get("dir_dept_code"));
		mode.addAttribute("dir_dept_name", matPurMainMap.get("dir_dept_name"));
		mode.addAttribute("state", matPurMainMap.get("state"));

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/order/init/matOrderInitGenPurDetail";

	}
	
	/**
	 * @Description 采购计划生成--查看采购计划明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/queryOrderInitGenPurDetail", method = RequestMethod.POST)
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
		
		String matPurDetail = matOrderInitService.queryMatPurDetail(mapVo);

		return JSONObject.parseObject(matPurDetail);

	}
	
	/**
	 * @Description 采购计划生成--生成订单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/genByPurMatOrderInit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> genByPurMatOrderInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		
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
		
		String matOrderMain;
		try {
			matOrderMain = matOrderInitService.genByPurMatOrderInit(listVo);
		} catch (Exception e) {
			
			matOrderMain = e.getMessage();
		}
		
		return JSONObject.parseObject(matOrderMain);
	}

	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/mat/order/init/matOrderInitPrintSetPage", method = RequestMethod.GET)
	public String matAffiTranPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/order/init/queryMatOrderInitByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiTranByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=matOrderInitService.queryMatOrderInitByPrintTemlate(mapVo);
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
	@RequestMapping(value = "/hrp/mat/order/init/matOrderInitPurImpPage", method = RequestMethod.GET)
	public String matOrderInitPurImpPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/order/init/matOrderInitPurImp";
	}
	
	/**
	 * @Description 
	 * 采购计划导入主查询 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/order/init/queryMatPurMainForOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurMainForOrder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		//转换日期格式
		if(mapVo.get("begin_pur_date") != null && !"".equals(mapVo.get("begin_pur_date"))){
			mapVo.put("begin_pur_date", DateUtil.stringToDate(mapVo.get("begin_pur_date").toString(), "yyyy-MM-dd"));
		} 
		if(mapVo.get("end_pur_date") != null && !"".equals(mapVo.get("end_pur_date"))){
			mapVo.put("end_pur_date", DateUtil.stringToDate(mapVo.get("end_pur_date").toString(), "yyyy-MM-dd"));
		}
		
		String reJson=null;
		try {
			reJson=matOrderInitService.queryMatPurMainForOrder(getPage(mapVo));
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
	@RequestMapping(value = "/hrp/mat/order/init/queryMatPurDetailForOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurDetailForOrder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=matOrderInitService.queryMatPurDetailForOrder(mapVo);
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
	@RequestMapping(value = "/hrp/mat/order/init/updateMatPurDetailByOrderClose", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPurDetailByOrderClose(@RequestParam(value="ParamVo") String paramVo, Model mode)throws Exception {

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
			
			matJson = matOrderInitService.updateMatPurDetailByOrderClose(listVo);
		} catch (Exception e) {
			
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 采购计划生成科室需求计划  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/addMatOrderByPurImp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatOrderByPurImp(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson;
		try {
			matJson = matOrderInitService.addMatOrderByPurImp(mapVo);
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
	 * 根据采购明细生成订单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/addMatOrderByPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatOrderByPur(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
 
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		mode.addAttribute("pre_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		String matJson;
		try {
			matJson = matOrderInitService.addMatOrderByPurDetail(mapVo);
		} catch (Exception e) {

			matJson = e.getMessage();
		}
		
		if(matJson.contains("true")){
			mapVo.put("state", "2");
			mapVo.put("flag", "0");
			//mapVo.put("whereSql"," and ot.pur_amount = pt.pur_amount ");
			mapVo.put("whereSql"," and nvl(rt.countnum,0) = nvl(rt.equalnum,0) and nvl(rt.zeronum,0) = 0");
			
			matOrderInitService.updateMatPurState(mapVo);
		}
		return JSONObject.parseObject(matJson);
	}
	
	@RequestMapping(value = "/hrp/mat/order/init/matOrderPurImpPage", method = RequestMethod.GET)
	public String matOrderPurImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("mod_code", "04");
		}
		start_date = matStorageInService.queryModeStartDate(mapVo);
		mode.addAttribute("start_date", start_date);
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		
		return "hrp/mat/order/init/matOrderPurImp";
		//return "hrp/mat/order/init/matOrderInitPurImp";
	}
	
	
	/**
	 * @Description 
	 * 采购计划导入明细展示
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/order/init/queryMatPurDetailGenOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurDetailGenOrder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=matOrderInitService.queryMatPurDetailGenOrder(mapVo);
		} catch (Exception e) {
			
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 关闭材料
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/updateMatPurCloseInvs", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/mat/order/init/matPurMainCloseInvInfosPage", method = RequestMethod.GET)
	public String matPurMainCloseInvInfosPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		if (paras != null && !"".equals(paras)) {
			String[] paraArray = paras.split("@");
			if (!"null".equals(paraArray[0])) {
				mode.addAttribute("sup_id", paraArray[0]);
			}
			if (!"null".equals(paraArray[1])) {
				mode.addAttribute("sup_text", paraArray[1]);
			}
		}
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/order/init/matPurMainCloseInvInfos";
	}
	/**
	 * 查看关闭的材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/queryMatPurCloseInvInfos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurCloseInvInfos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
	@RequestMapping(value = "/hrp/mat/order/init/updateMatPurCancleCloseInvs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPurCancleCloseInvs(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	
	
	
	
}

