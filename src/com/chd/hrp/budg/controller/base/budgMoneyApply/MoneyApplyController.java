/***
 * zzb 2018-4-26 09:16:35 
 * 
 * 用款申请登记
 */
package com.chd.hrp.budg.controller.base.budgMoneyApply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyService;
import com.chd.hrp.budg.service.base.budgMoneyApply.MoneyApplyService;

@Controller
public class MoneyApplyController extends BaseController{

	private static Logger logger = Logger.getLogger(MoneyApplyController.class);
	
	@Resource(name="moneyApplyService")
	private final MoneyApplyService moneyApplyService = null;
	
	@Resource(name="budgPaymentApplyService")
	private final BudgPaymentApplyService budgPaymentApplyService = null;
	
	@Resource(name="budgNoManagerService")
	private final BudgNoManagerService budgNoManagerService = null;
	
	/**
	 * 主页面跳转
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/MoneyApplyMainPage",method = RequestMethod.GET)
	public String MoneyApplyMainPage() {
		//返回日期
		
		return "hrp/budg/base/budgMoneyApply/MoneyApply/MoneyApplyMain";
	}
	
	
	
	
	/**
	 * 主页面查询数据
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/queryMoneyApply",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> queryMoneyApply(@RequestParam Map<String, Object> mapVo, Model mode){
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (!"".equals(mapVo.get("proj_dict")) && mapVo.get("proj_dict")!=null ) {
			mapVo.put("proj_dict", mapVo.get("proj_dict").toString().split("\\.")[0]);
		}
		mapVo.put("emp_id", SessionManager.getEmpCode());
		
		String data = moneyApplyService.queryMoneyApply(getPage(mapVo));
		
		return JSONObject.parseObject(data);
	}
	
	/**
	 * 添加页面跳转
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/MoneyApplyAddPage",method = RequestMethod.GET )
	public String MoneyApplyAddPage(Model mode) {
		
		HashMap<String,Object> mapVo = new HashMap<String, Object>();
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		mapVo.put("emp_id", SessionManager.getEmpCode());
		//拿到申请人
		//Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,Object> map = budgPaymentApplyService.queryAddPageData(mapVo);
		if (map != null && map.get("dept_id") != null) {
			map.put("dept_id", map.get("dept_id").toString().split("\\.")[0]);
			mode.addAllAttributes(map);
		}
		
		/*mode.addAttribute("p02009", MyConfig.getSysPara("02009"));
		mode.addAttribute("user_id", SessionManager.getUserId());*/
		
		return "hrp/budg/base/budgMoneyApply/MoneyApply/MoneyApplyAdd";
	}
	
	
	
	/**
	 * 添加明细页面保存
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/addMoneyApply",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> addMoneyApply(@RequestParam Map<String, Object> mapVo, Model mode){
		
		boolean isAdd = true;
		


		
		//拿抬头数据
		String[] data = ((String) mapVo.get("top")).split("@");
		String apply_date = data[0]; //申请日期
		String apply_name = data[1]; //申请人id
		String apply_dept = data[2]; //申请科室id
		String proj_dict = "";
		if (!"-1".equals(data[3])) {
			proj_dict = data[3].split("\\.")[0];//项目名称id
		}
		String YS_dept = data[4];//预算归口科室
		String remark = data[5];//申请事由
		String apply_amount = data[6];//申请金额
		String phone = "";
		if (!"-1".equals(data[7])) {
			 phone = data[7];//联系电话
		}
		String apply_code = "";
		if(!"-1".equals(data[8])) {
			//说明是添加页面多次保存或者是修改页面的保存
			isAdd = false;
			apply_code = data[8];
		}
		HashMap<String, Object> entMap = new HashMap<String, Object>();
		

		entMap.put("group_id", SessionManager.getGroupId());
		entMap.put("hos_id", SessionManager.getHosId());
		entMap.put("copy_code", SessionManager.getCopyCode());
		entMap.put("apply_date", apply_date);
		entMap.put("apply_name", apply_name.split("\\.")[0]);
		entMap.put("apply_dept", apply_dept);
		entMap.put("proj_dict", proj_dict);
		entMap.put("YS_dept", YS_dept);
		entMap.put("remark", remark);
		entMap.put("apply_amount", apply_amount);
		entMap.put("phone", phone);
		
		if (isAdd) {
			//生成主键。自动生成，编码规则“YKSQ+年月+四位流水”
//			apply_code = moneyApplyService.queryMaxKey(mapVo);
//			apply_code = "YKSQ"+apply_date.replace("-", "").substring(0, 6)+apply_code;
			HashMap<String,Object> entityMap = new HashMap<String, Object>();
			entityMap.put("table_name", "用款申请");
			entityMap.put("prefixe", "YKSQ");
			entityMap.put("table_code", "BUDG_USE_APPLY");
			apply_code = budgNoManagerService.getBillNOSeqNo(entityMap);
			
			
			
			
			
		}
		
		
		entMap.put("apply_code", apply_code);
		entMap.put("maker", SessionManager.getUserId());
		
		
		//拿明细数据
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for (String str : mapVo.get("paramVo").toString().split(",")) {
			
			HashMap<String,Object> map = new HashMap<String, Object>();
			
			String[] paramVo=str.split("@");
			String source_id = paramVo[0];
			String payment_item_id = paramVo[1];
			String apply_amount_det = paramVo[2];
			String remark_det = "";
			if(paramVo.length>3) {
				remark_det = paramVo[3];
			}
			
			
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			map.put("source_id", source_id);
			map.put("payment_item_id", payment_item_id);
			map.put("apply_amount_det", apply_amount_det);
			map.put("remark_det", remark_det);
			
			map.put("apply_code", apply_code);
			list.add(map);
		}

		//System.out.println(list);
		//System.out.println(entMap);
		if (!isAdd) {
			moneyApplyService.deleteFromId(entMap);
		}
		
		
		String stat = moneyApplyService.addMoneyApply(list,entMap);
		budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_USE_APPLY");
		
		return JSONObject.parseObject(stat);
	}
	
	
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/MoneyApplyUpdatePage", method = RequestMethod.GET)
	public String MoneyApplyUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		Map<String,Object> map=moneyApplyService.queryUpdatePageData(mapVo);
		mode.addAllAttributes(map);
		
		mode.addAttribute("p02009", MyConfig.getSysPara("02009"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		return "hrp/budg/base/budgMoneyApply/MoneyApply/MoneyApplyUpdate";
		
	}
	
	
	
	
	/**
	 * 修改页面的查询明细
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/queryMoneyApplyDet",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> queryMoneyApplyDet(@RequestParam Map<String, Object> mapVo, Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//获取系统编码规则
		String p02007 = MyConfig.getSysPara("02007");
		
		//预算额度:若项目为空，根据02007预算控制层次、申请年月、支出项目、归口科室（取自支出项目归口设置）从科室其他费用预算BUDG_EXPENSE中取支出预算合计。
		//预算额度:若项目不为空，根据申请年度、项目、资金来源、支出项目取项目预算分解BUDG_PROJ_APPLY_RESOLVE中预算金额合计；
		
		
		//已申请额度:若项目为空，根据02007预算控制层次、申请年月、归口科室（取自支出项目归口设置）、支出项目取已审核的用款申请中的申请金额合计值
		//已申请额度:若项目不为空，根据申请年度、项目、资金来源、支出项目取已审核的用款申请中的申请金额合计值；
		
		//已执行额度:若项目为空，根据02007预算控制层次、申请年月、归口科室（取自支出项目归口设置）、支出项目取报销申请和费用支付申请中报销金额和付款金额合计值
		//已执行额度:若项目不为空，根据申请年度、项目、资金来源、支出项目取报销申请和费用支付申请中报销金额和付款金额合计值；
		
		
		//与用款申请关联的报销申请和费用支付申请单中金额合计
		
		
		HashMap<String,Object> map = moneyApplyService.queryApplyDataForCode(mapVo);
		
		if (map.get("proj_id")==null || "".equals(map.get("proj_id"))) {
			
			mapVo.put("flag", "A");
			
			if ("0".equals(p02007)) {
				
				//{"code":0,"value":"科室年"},
				mapVo.put("year", map.get("apply_date").toString().substring(0,4));
				mapVo.put("dept_id", map.get("dept_id"));
				mapVo.put("dept_no", map.get("dept_no"));
				
			}else if ("1".equals(p02007)) {
				
				//{"code":1,"value":"科室月"},
				mapVo.put("year", map.get("apply_date").toString().substring(0,4));//申请年
				mapVo.put("month", map.get("apply_date").toString().substring(5,7));//申请月
				mapVo.put("yearMonth", map.get("apply_date").toString().substring(0,4)+map.get("apply_date").toString().substring(5,7));//申请月
				mapVo.put("dept_id", map.get("dept_id"));
				mapVo.put("dept_no", map.get("dept_no"));
				
			}else if ("2".equals(p02007)) {
				
				//{"code":2,"value":"医院年"},
				mapVo.put("year", map.get("apply_date").toString().substring(0,4));//申请年
				
			}else if ("3".equals(p02007)) {
				
				//{"code":3,"value":"按医院月"}
				mapVo.put("year", map.get("apply_date").toString().substring(0,4));//申请年
				mapVo.put("month", map.get("apply_date").toString().substring(5,7));//申请月
				mapVo.put("yearMonth", map.get("apply_date").toString().substring(0,4)+map.get("apply_date").toString().substring(5,7));//申请月
				
			}
			
			//归口科室（取自支出项目归口设置）
		}else {
			mapVo.put("flag", "B");
			mapVo.put("apply_date", map.get("apply_date").toString().substring(0,4));//申请年度
			mapVo.put("year", map.get("apply_date").toString().substring(0,4));//申请年
			mapVo.put("proj_id", map.get("proj_id"));//项目id
			mapVo.put("proj_no", map.get("proj_no"));//项目no
		}
		
		
		
		String data = moneyApplyService.queryMoneyApplyDet(mapVo);
		return JSONObject.parseObject(data);
	}
	
	
	
	/**
	 * 主页面删除
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/deleteMoneyApplyMain",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> deleteMoneyApplyMain(@RequestParam Map<String, Object> mapVo, Model mode){
		
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		
		for (String apply_code : mapVo.get("paramVo").toString().split(",")) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			
			map.put("hos_id", SessionManager.getHosId());
			
			map.put("copy_code", SessionManager.getCopyCode());
			
			map.put("apply_code", apply_code);
			
			list.add(map);
		}
		
		
		

		String stat = moneyApplyService.deleteFromBatch(list);
		
		
		
		return JSONObject.parseObject(stat);
	}
	
	
	/**
	 * 添加或修改页面删除明细
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/deleteMoneyApplyDetailed",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> deleteMoneyApplyDetailed(@RequestParam Map<String, Object> mapVo, Model mode){
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for (String data : mapVo.get("paramVo").toString().split(",")) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			
			map.put("hos_id", SessionManager.getHosId());
			
			map.put("copy_code", SessionManager.getCopyCode());
			String[] dataDet = data.split("@");
			map.put("apply_code", dataDet[0]);
			map.put("source_id", dataDet[1]);
			map.put("payment_item_id", dataDet[2]);
			
			list.add(map);
		}
		
		String stat = moneyApplyService.deleteFromBatchDetailed(list);
		//删除明细后需要重新修改主表的总金额
		moneyApplyService.updatMainApply_amount(list);
		
		return JSONObject.parseObject(stat);
	}
	
	
	/**
	 * 主页面提交、明细页面提交   state_t 01新建、02已提交、03已审核
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/updateMoneyApply",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> updateMoneyApply(@RequestParam Map<String, Object> mapVo, Model mode){
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for (String data : mapVo.get("paramVo").toString().split(",")) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			
			map.put("hos_id", SessionManager.getHosId());
			
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("apply_code", data);

			
			list.add(map);
		}
		
		String stat = moneyApplyService.updateMoneyApplyState(list);

		
		return JSONObject.parseObject(stat);
	}
	
	
	/**
	 * 主页面撤回、明细页面撤回   state_t 01新建、02已提交、03已审核
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/updateMoneyApplyStateRevoke",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> updateMoneyApplyStateRevoke(@RequestParam Map<String, Object> mapVo, Model mode){
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for (String data : mapVo.get("paramVo").toString().split(",")) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			
			map.put("hos_id", SessionManager.getHosId());
			
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("apply_code", data);

			
			list.add(map);
		}
		
		String stat = moneyApplyService.updateMoneyApplyStateRevoke(list);

		
		return JSONObject.parseObject(stat);
	}
	
	
	
	// 项目下拉框
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/queryProjDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		if (mapVo.get("group_id") == null) {
					
			mapVo.put("group_id", SessionManager.getGroupId());
				
		}
				
		if (mapVo.get("hos_id") == null) {
					
			mapVo.put("hos_id", SessionManager.getHosId());
				
		}
				
		String dept = moneyApplyService.queryProjDict(mapVo);
			
		return dept;

	}
	
	
	// 借款申请单 下拉框  use_apply_code_up
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/queryUserApplyCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryUserApplyCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		String dept = moneyApplyService.queryUserApplyCode(getPage(mapVo));
		
		return dept;
		
	}
	
	
	//查询用款申请额度、预算额度、已执行额度 根据参数02007  控制层次
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/queryBudgetQuota",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> queryBudgetQuota(@RequestParam Map<String, Object> mapVo, Model mode){
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		String ParamVo = mapVo.get("ParamVo").toString();
		Map<String,Object> ParaMap = (Map<String, Object>) JSONObject.parse(ParamVo);
		for (Entry<String, Object> map : ParaMap.entrySet()) {
			mapVo.put(map.getKey(), map.getValue());
		}
		
		//用款申请额度
		//预算额度
		//已执行额度
		
		String p02007 = MyConfig.getSysPara("02007");
		if (mapVo.get("proj_id")==null || "".equals(mapVo.get("proj_id"))) {
			mapVo.put("flag", "A");
			
			if ("0".equals(p02007)) {
				
				//{"code":0,"value":"科室年"},
				mapVo.put("year", mapVo.get("apply_date").toString().substring(0,4));
				
			}else if ("1".equals(p02007)) {
				
				//{"code":1,"value":"科室月"},
				mapVo.put("year", mapVo.get("apply_date").toString().substring(0,4));//申请年
				mapVo.put("month", mapVo.get("apply_date").toString().substring(5,7));//申请月
				mapVo.put("yearMonth", mapVo.get("apply_date").toString().substring(0,4) + mapVo.get("apply_date").toString().substring(5,7));//申请月

				
			}else if ("2".equals(p02007)) {
				
				//{"code":2,"value":"医院年"},
				mapVo.put("year", mapVo.get("apply_date").toString().substring(0,4));//申请年
				
			}else if ("3".equals(p02007)) {
				
				//{"code":3,"value":"按医院月"}
				mapVo.put("year", mapVo.get("apply_date").toString().substring(0,4));//申请年
				mapVo.put("month", mapVo.get("apply_date").toString().substring(5,7));//申请月
				mapVo.put("yearMonth", mapVo.get("apply_date").toString().substring(0,4) + mapVo.get("apply_date").toString().substring(5,7));//申请月
				
			}
			
			//归口科室（取自支出项目归口设置）
		}else {
			mapVo.put("flag", "B");
			mapVo.put("year", mapVo.get("apply_date").toString().substring(0,4));//申请年
		}
		
		HashMap<String,Object> data = moneyApplyService.queryBudgetQuota(mapVo);
		
		return data;
	}
	
	
}
