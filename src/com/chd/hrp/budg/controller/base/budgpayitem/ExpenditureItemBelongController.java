/** 
 * @Description:
 * @Copyright: Copyright (c) 2018-4-24 10:21:18
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.base.budgpayitem;

import java.util.ArrayList;
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
import com.chd.base.SessionManager;
import com.chd.hrp.budg.service.base.budgpayitem.ExpenditureItemBelongService;


/**
 * 
 * @Description:
 * 支出项目归口设置
 * @Table:
 * BUDG_DUTY_SET 支出项目归口设置表
 * @Author: zzb
 * @email:  jonZhang@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class ExpenditureItemBelongController extends BaseController{

	private static Logger logger = Logger.getLogger(ExpenditureItemBelongController.class);
	
	//引入Service服务
	@Resource(name = "expenditureItemBelongService")
	private final ExpenditureItemBelongService expenditureItemBelongService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/expenditureItem/expenditureItemBelongMainPage", method = RequestMethod.GET)
	public String expenditureItemBelongMainPage(Model mode) throws Exception {
		System.out.println("1111");
		return "hrp/budg/base/budgpayitem/expenditureItem/expenditureItemBelongMain";
	}
	
	/**
	 * 主页面查询数据
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/expenditureItem/queryExpenditureItemBelong" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExpenditureItemBelong(@RequestParam Map<String, Object> mapVo, Model mode){
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String querydata = expenditureItemBelongService.queryExpenditureData(getPage(mapVo));
		return JSONObject.parseObject(querydata);
	}
	
	/**
	 * 添加页面跳转
	 * @return
	 */
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/expenditureItem/ExpenditureItemBelongAddPage",method = RequestMethod.GET)
	public String ExpenditureItemBelongAddPage(){
		
		return "hrp/budg/base/budgpayitem/expenditureItem/ExpenditureItemBelongAdd";
	}
	
	/**
	 * 添加页面的查询
	 * @return
	 */
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/expenditureItem/addQueryExpenditureItemBelong" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addQueryExpenditureItemBelong(@RequestParam Map<String, Object> mapVo, Model mode){
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String querydata = expenditureItemBelongService.addQueryExpenditureItemBelong(mapVo);
		
		
		
		return JSONObject.parseObject(querydata);
	}
	
	
	/**
	 * 添加修改页面的保存
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="/hrp/budg/base/budgpayitem/expenditureItem/addExpenditureItemBelongData",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addExpenditureItemBelongData(@RequestParam Map<String, Object> mapVo, Model mode) {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String isAddData = expenditureItemBelongService.addExpenditureItemBelongData(mapVo);
		
		return JSONObject.parseObject(isAddData);
		
	}
	
	/**
	 * 主页面的删除
	 * @param paramVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="/hrp/budg/base/budgpayitem/expenditureItem/deleteExpenditureItemBelong",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteExpenditureItemBelong(@RequestParam(value = "ParamVo") String paramVo, Model mode){
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("dept_id", ids[0]);
			mapVo.put("payment_item_id", ids[1]);
			mapVo.put("duty_dept_id", ids[2]);

			listVo.add(mapVo);

		}
		
		
		
		return JSONObject.parseObject(expenditureItemBelongService.deleteBatch(listVo));
	}
	
	
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/expenditureItem/ExpenditureItemBelongUpdatePage", method = RequestMethod.GET)
	public String ExpenditureItemBelongUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> dataMap = expenditureItemBelongService.queryDictData(mapVo);
		
		mode.addAttribute("up_id",dataMap.get("id"));
		mode.addAttribute("up_text",dataMap.get("text"));
		mode.addAttribute("up_id2",dataMap.get("id2"));
		mode.addAttribute("up_text2",dataMap.get("text2"));
		
		return "hrp/budg/base/budgpayitem/expenditureItem/ExpenditureItemBelongAdd";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 归口科室下拉框选项
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/expenditureItem/queryDutyDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryDutyDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		
		String dutyDept = expenditureItemBelongService.queryDutyDept(mapVo);
		return JSONArray.parseArray(dutyDept).toString();

	}
	
	
	/**
	 * 支出项目下拉框选项
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/expenditureItem/queryPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		
		String paymentItem = expenditureItemBelongService.queryPaymentItem(mapVo);
		return paymentItem;

	}
	
	
	
	
	
	
	
	
	/**
	 * 导入
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/budg/base/budgpayitem/expenditureItem/budgExpenditureItemBelongImport",method = RequestMethod.POST)
	@ResponseBody
	public String budgExpenditureItemBelongImport(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String para = mapVo.get("para").toString();
		JSONObject JSONOpara = JSONObject.parseObject(para);

		
		
		//Map<String, Object> table = accLederService.queryTitle(mapVo);
		//String accVouchCheckJson=accVouchCheckService.getIsAccFlag(mapVo);
		//JSONObject aa = JSONObject.parseObject(accVouchCheckJson);
		
		//查询科室信息做校验
		List<HashMap<String,Object>> listDept = expenditureItemBelongService.queryAllDeptData(mapVo);
		
		//查询支出项目信息
		List<HashMap<String,Object>> listPayment_item = expenditureItemBelongService.queryAllPayment_itemData(mapVo);
		
		
		try {
			
			String reJson = expenditureItemBelongService.readFilesImport(mapVo,listDept,listPayment_item);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	
	
	
	
	
	
	
	
}
