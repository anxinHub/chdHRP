package com.chd.hrp.acc.controller.paper;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.paper.AccPaperIncomeService;

@Controller
public class AccPaperIncomeController extends BaseController{

	private static Logger logger = Logger.getLogger(AccPaperIncomeController.class);
	
	@Resource(name = "accPaperIncomeService")
	private final AccPaperIncomeService accPaperIncomeService = null;
	
	/**
	 * 应收票据<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/accPaperIncomeMainPage", method = RequestMethod.GET)
	public String accPaperIncomeMainPage(Model mode) throws Exception {
		return "hrp/acc/accpaper/accpaperincome/accPaperIncomeMain";
	}
	
	/**
	 * 应收票据<BR>
	 * 添加页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/accPaperIncomeAddPage", method = RequestMethod.GET)
	public String accPaperIncomeAddPage(Model mode) throws Exception {
		mode.addAttribute("name",SessionManager.getUserName());
		return "hrp/acc/accpaper/accpaperincome/accPaperIncomeAdd";
	}
	
	/**
	 * 应收票据<BR>
	 * 退票页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/accPaperIncomeRefundUpdatePage", method = RequestMethod.GET)
	public String accPaperIncomeRefundUpdatePage(String money,Model mode) throws Exception {
		mode.addAttribute("date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		mode.addAttribute("money",money);
		return "hrp/acc/accpaper/accpaperincome/accPaperIncomeRefundUpdate";
	}
	
	/**
	 * 应收票据<BR>
	 * 收款页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/accPaperIncomePutUpdatePage", method = RequestMethod.GET)
	public String accPaperIncomePutUpdatePage(String money,Model mode) throws Exception {
		mode.addAttribute("date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		mode.addAttribute("money",money);
		return "hrp/acc/accpaper/accpaperincome/accPaperIncomePutUpdate";
	}
	
	/**
	 * 票据类型下拉框加载
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncomeType_code", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperIncomeType_code(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return JSON.toJSONString(accPaperIncomeService.queryAccPaperIncomeType_code(mapVo));
	}
	
	/**
	 * 币种下拉框加载
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncomeMoney", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperIncomeMoney(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		return JSON.toJSONString(accPaperIncomeService.queryAccPaperIncomeMoney(mapVo));
	}
	/**
	 * 汇率下拉框加载
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncomeRatename", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperIncomeRatename(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return JSON.toJSONString(accPaperIncomeService.queryAccPaperIncomeRatename(mapVo));
	}
	/**
	 * 核算项下拉框加载
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncomeCheckItemNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperIncomeCheckItemNo(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return JSON.toJSONString(accPaperIncomeService.queryAccPaperIncomeCheckItemNo(mapVo));
	}
	/**
	 * 核算项下拉框加载
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncomeCheckTypeId", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperIncomeCheckTypeId(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return JSON.toJSONString(accPaperIncomeService.queryAccPaperIncomeCheckTypeId(mapVo));
	}
	/**
	 * 应收票据添加
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/addPaperIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addPaperIncome(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("username", SessionManager.getUserId());
		
		mapVo.put("busi_type_code", 1);
		
		String [] str = ((String)mapVo.get("check_item_no_val")).split("\\|");
		String [] rate = ((String)mapVo.get("rate_code_val")).split("\\|");
		
		mapVo.put("rate_code_val",rate[0]);
		mapVo.put("check_item_no",str[0]);
		mapVo.put("check_item_id",str[1]);
		
		try{
			return JSONObject.parseObject(accPaperIncomeService.addPaperIncome(mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	/**
	 * 应收票据查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAccPaperIncome(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_code", SessionManager.getUserId());
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		mapVo.put("busi_type_code", 1);
		
		if (mapVo.get("check_item_no") != null) {
			
			String [] str = ((String)mapVo.get("check_item_no")).split("\\|");
			
			mapVo.put("check_item_no",str[0]);
		}
		
		return JSONObject.parseObject(accPaperIncomeService.queryAccPaperIncome(getPage(mapVo)));
	}
	/**
	 * 应收票据修改-数据回显
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/accPaperIncomeUpdatePage", method = RequestMethod.GET)
	public String accPaperIncomeUpdatePage(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("busi_type_code", 1);
		
		//查询回显数据
		mapVo = accPaperIncomeService.accPaperIncomeUpdateQuery(mapVo);
		
		mode.addAttribute("vo",mapVo);
		
		return "hrp/acc/accpaper/accpaperincome/accPaperIncomeUpdate";
	}
	
	/**
	 * 应收票据修改
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/updatePaperIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePaperIncome(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("username", SessionManager.getUserId());
		
		mapVo.put("busi_type_code", 1);
		
		//多ID存储,切割取出
		String [] str = ((String)mapVo.get("check_item_no_val")).split("\\|");
		//多ID存储,切割取出
		String [] rate = ((String)mapVo.get("rate_code_val")).split("\\|");
		
		mapVo.put("rate_code_val",rate[0]);
		mapVo.put("check_item_no",str[0]);
		mapVo.put("check_item_id",str[1]);
		
		try{
			return JSONObject.parseObject(accPaperIncomeService.updatePaperIncome(mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 应收票据修改-背书数据回显
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncomeBook", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAccPaperIncomeBook(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("busi_type_code", 1);
		
		String list = accPaperIncomeService.queryAccPaperIncomeBook(mapVo);
		
		return JSONObject.parseObject(list);
	}
	/**
	 * 应收据删除
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/deleteAccPaperIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteAccPaperIncome(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		String ids = (String) mapVo.get("paramVo");
		
		List<Map> list = JSONObject.parseArray(ids,Map.class);
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("busi_type_code", 1);
		
		try{
			return JSONObject.parseObject(accPaperIncomeService.deleteAccPaperIncome(list,mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 应收票据审核
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/updateAuditAccPaperIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateAuditAccPaperIncome(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		String ids = (String) mapVo.get("paramVo");
		
		List<Map> list = JSONObject.parseArray(ids,Map.class);
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		mapVo.put("busi_type_code", 1);
		
		try{
			return JSONObject.parseObject(accPaperIncomeService.updateAuditAccPaperIncome(list,mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	/**
	 * 应收票据取消审核
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/updateCancelAccPaperIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateCancelAccPaperIncome(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		String ids = (String) mapVo.get("paramVo");
		
		List<Map> list = JSONObject.parseArray(ids,Map.class);
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		mapVo.put("busi_type_code", 1);
		
		try{
			return JSONObject.parseObject(accPaperIncomeService.updateCancelAccPaperIncome(list,mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	/**
	 * 应收票据作废
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/updateZuofeiAccPaperIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateZuofeiAccPaperIncome(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		String ids = (String) mapVo.get("paramVo");
		
		List<Map> list = JSONObject.parseArray(ids,Map.class);
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("busi_type_code", 1);
		
		try{
			return JSONObject.parseObject(accPaperIncomeService.updateZuofeiAccPaperIncome(list,mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 应收票据退票
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/updateRefundAccPaperIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateRefundAccPaperIncome(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("busi_type_code", 1);
		
		try{
			return JSONObject.parseObject(accPaperIncomeService.updateRefundAccPaperIncome(mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 应收票据收款
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/updatePutAccPaperIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePutAccPaperIncome(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("busi_type_code", 1);
		
		try{
			return JSONObject.parseObject(accPaperIncomeService.updatePutAccPaperIncome(mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	
	/**
	 * 条件查询-制单人下拉加载
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncomeOpCreateuser", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperIncomeOpCreateuser(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("busi_type_code", 1);
		
		try{
			return JSON.toJSONString(accPaperIncomeService.queryAccPaperIncomeOpCreateuser(mapVo));
		}catch(Exception e){
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 条件查询-审核单人下拉加载
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncomeOpAudituser", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperIncomeOpAudituser(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("busi_type_code", 1);
		
		try{
			return JSON.toJSONString(accPaperIncomeService.queryAccPaperIncomeOpAudituser(mapVo));
		}catch(Exception e){
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 条件查询-删除背书信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/deletePaperIncomeBeishu", method = RequestMethod.POST)
	@ResponseBody
	public String deletePaperIncomeBeishu(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("busi_type_code", 1);
		
		try{
			return JSON.toJSONString(accPaperIncomeService.deletePaperIncomeBeishu(mapVo));
		}catch(Exception e){
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 条件查询-核算项下拉框加载
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperincome/queryAccPaperIncomeCheckNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperIncomeCheckNo(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return JSON.toJSONString(accPaperIncomeService.queryAccPaperIncomeCheckNo(mapVo));
	}

	
}
