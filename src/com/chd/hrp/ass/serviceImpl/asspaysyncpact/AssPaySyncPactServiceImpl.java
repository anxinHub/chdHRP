package com.chd.hrp.ass.serviceImpl.asspaysyncpact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.asspaysyncpact.AssPaySyncPactMapper;
import com.chd.hrp.ass.service.assinterfacejournal.AssInterfaceJournalService;
import com.chd.hrp.ass.service.asspaysyncpact.AssPaySyncPactService;
import com.chd.hrp.pac.dao.fkht.payment.PactPayDetFKHTMapper;
import com.chd.hrp.pac.dao.fkht.payment.PactPayMainFKHTMapper;
import com.chd.hrp.pac.dao.fkht.payment.PactPayPlanFKHTMapper;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

@Service("assPaySyncPactService")
@Transactional(rollbackFor = Exception.class)
public class AssPaySyncPactServiceImpl implements AssPaySyncPactService {

	private static Logger logger = Logger.getLogger(AssPaySyncPactServiceImpl.class);

	@Resource(name = "assPaySyncPactMapper")
	private final AssPaySyncPactMapper assPaySyncPactMapper = null;

	@Resource(name = "pactPayMainFKHTMapper")
	private final PactPayMainFKHTMapper pactPayMainFKHTMapper = null;

	@Resource(name = "pactNoRuleService")
	private final PactNoRuleService pactNoRuleService = null;

	@Resource(name = "pactPayDetFKHTMapper")
	private final PactPayDetFKHTMapper pactPayDetFKHTMapper = null;

	@Resource(name = "pactPayPlanFKHTMapper")
	private final PactPayPlanFKHTMapper pactPayPlanFKHTMapper = null;

	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;

	@Resource(name = "assInterfaceJournalService")
	private final AssInterfaceJournalService assInterfaceJournalService = null;

	/**
	 * 资产付款数据同步至合同付款 mapVo包括pay_no（付款/预付款单号） prePay是否预付款（0否 1是） mark（add delete
	 * update check uncheck） pi_ismakeup是否补录（0否 1是）
	 */
	public int assPaySyncPact(Map<String, Object> mapVo) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String pay_no = mapVo.get("pay_no").toString(); // 付款单号
		String mark = mapVo.get("mark").toString(); // 标志（add delete）
		String pre_pay = mapVo.get("pre_pay").toString(); // 是否预付款（0否 1是）
		String pi_ismakeup = mapVo.get("pi_ismakeup").toString(); // 是否补录（0否 1是）
		try {
			if (queryAccParaByCode()) { // 合同参数11003=1执行数据同步
				// 添加
				int res1, res2;
				if ("add".equals(mark)) {
					if ("1".equals(pre_pay)) { // 资产预付款同步添加
						res1 = addPactPrePayFKHT(mapVo);
						if (res1 > 0) {
							// 添加接口操作日志 操作成功
							mapVo.put("pi_mresultdesc", "成功");
							mapVo.put("pi_operatetype", "A");
							mapVo.put("pi_operateresult", "1");
							if ("1".equals(pi_ismakeup)) {
								mapVo.put("makeupresult", "1");// 补录结果（0 1）
							}
							res2 = addPactInterfaceLog(mapVo);
						} else {
							// 保存接口操作日志 保存失败
							mapVo.put("pi_mresultdesc", "失败");
							mapVo.put("pi_operatetype", "A");
							mapVo.put("pi_operateresult", "0");
							mapVo.put("pi_oresultdesc", "添加失败");
							if ("1".equals(pi_ismakeup)) {
								mapVo.put("makeupresult", "0");// 补录结果（0 1）
							}
							res2 = addPactInterfaceLog(mapVo);
						}
						return res1;
					} else { // 资产付款同步添加
						res1 = addPactPayFKHT(mapVo);
						if (res1 > 0) {
							// 保存接口操作日志 保存成功
							mapVo.put("pi_mresultdesc", "成功");
							mapVo.put("pi_operatetype", "A");
							mapVo.put("pi_operateresult", "1");
							if ("1".equals(pi_ismakeup)) {
								mapVo.put("makeupresult", "1");// 补录结果（0 1）
							}
							res2 = addPactInterfaceLog(mapVo);
						} else {
							// 保存接口操作日志 保存失败
							mapVo.put("pi_mresultdesc", "失败");
							mapVo.put("pi_operatetype", "A");
							mapVo.put("pi_operateresult", "0");
							mapVo.put("pi_oresultdesc", "添加失败");
							if ("1".equals(pi_ismakeup)) {
								mapVo.put("makeupresult", "0");// 补录结果（0 1）
							}
							res2 = addPactInterfaceLog(mapVo);
						}
						return res1;
					}
				}
				// 删除
				if ("delete".equals(mark)) {
					res1 = deletePactPayFKHT(mapVo);
					if (res1 > 0) {
						// 保存接口操作日志 删除成功
						mapVo.put("pi_operatetype", "D");
						mapVo.put("pi_operateresult", "1");
						res2 = addPactInterfaceLog(mapVo);
						return 1;
					} else {
						// 保存接口操作日志 删除失败
						mapVo.put("pi_operatetype", "D");
						mapVo.put("pi_operateresult", "0");
						mapVo.put("pi_oresultdesc", "删除失败");
						res2 = addPactInterfaceLog(mapVo);
						return 0;
					}

				}
				// 编辑
				if ("update".equals(mark)) {
					if ("1".equals(pre_pay)) { // 资产预付款同步修改
						res1 = deletePactPayFKHT(mapVo);
						res2 = addPactPrePayFKHT(mapVo);
						if (res1 > 0 && res2 > 0) {
							mapVo.put("pi_operatetype", "U");
							mapVo.put("pi_operateresult", "1");
							res2 = addPactInterfaceLog(mapVo);
							return 1;
						} else {
							mapVo.put("pi_operatetype", "D");
							mapVo.put("pi_operateresult", "0");
							mapVo.put("pi_oresultdesc", "修改失败");
							res2 = addPactInterfaceLog(mapVo);
							return 0;
						}
					} else {
						res1 = deletePactPayFKHT(mapVo);
						res2 = addPactPayFKHT(mapVo);
						if (res1 >= 0 && res2 >= 0) {
							mapVo.put("pi_operatetype", "U");
							mapVo.put("pi_operateresult", "1");
							res2 = addPactInterfaceLog(mapVo);
							return 1;
						} else {
							mapVo.put("pi_operatetype", "D");
							mapVo.put("pi_operateresult", "0");
							mapVo.put("pi_oresultdesc", "修改失败");
							res2 = addPactInterfaceLog(mapVo);
							return 0;
						}
					}
				}
				// 审核
				if ("check".equals(mark)) {
					mapVo.put("state", "03");
					mapVo.put("confirmer", SessionManager.getUserId());// 确认人
					mapVo.put("confirm_date", sdf1.format(new Date())); // 确认日期
					// 更新状态
					assPaySyncPactMapper.updatePactPayMainFKHTState(mapVo);
					List<Map<String, Object>> list = assPaySyncPactMapper.queryPactPlanFKHT(mapVo);
					List<Map<String, Object>> lists = new ArrayList<>();
					for (Map<String, Object> map : list) {
						Double payed_money = 0.0;
						Double pay_money = 0.0;
						Double plan_money = 0.0;
						if (map.get("payed_money") != "" && map.get("payed_money") != null) {
							payed_money = Double.parseDouble(map.get("payed_money").toString());// 已付金额
						}
						if (map.get("pay_money") != "" && map.get("pay_money") != null) {
							pay_money = Double.parseDouble(map.get("pay_money").toString()); // 付款金额
						}
						if (map.get("plan_money") != "" && map.get("plan_money") != null) {
							plan_money = Double.parseDouble(map.get("plan_money").toString()); // 计划金额
						}
						payed_money = payed_money + pay_money;
						map.put("payed_money", payed_money);
						map.put("pay_flag", 0);
						if (plan_money <= payed_money) {
							map.put("pay_flag", 1);
						}
						map.put("group_id", mapVo.get("group_id"));
						map.put("hos_id", mapVo.get("hos_id"));
						map.put("copy_code", mapVo.get("copy_code"));
						lists.add(map);
					}
					// 更新已付金额、付款标识
					int res = assPaySyncPactMapper.updatePactPlanFKHT(lists);

					mapVo.put("pi_operatetype", "C");
					mapVo.put("pi_operateresult", "1");
					res2 = addPactInterfaceLog(mapVo);

					return res;
				}
				// 取消审核
				if ("uncheck".equals(mark)) {
					mapVo.put("state", "02");
					mapVo.put("confirmer", SessionManager.getUserId());// 确认人
					mapVo.put("confirm_date", sdf1.format(new Date())); // 确认日期
					// 更新状态
					assPaySyncPactMapper.updatePactPayMainFKHTState(mapVo);
					List<Map<String, Object>> list = assPaySyncPactMapper.queryPactPlanFKHT(mapVo);
					List<Map<String, Object>> lists = new ArrayList<>();
					for (Map<String, Object> map : list) {
						Double payed_money = 0.0;
						Double pay_money = 0.0;
						Double plan_money = 0.0;
						if (map.get("payed_money") != "" && map.get("payed_money") != null) {
							payed_money = Double.parseDouble(map.get("payed_money").toString());// 已付金额
						}
						if (map.get("pay_money") != "" && map.get("pay_money") != null) {
							pay_money = Double.parseDouble(map.get("pay_money").toString()); // 付款金额
						}
						if (map.get("plan_money") != "" && map.get("plan_money") != null) {
							plan_money = Double.parseDouble(map.get("plan_money").toString()); // 计划金额
						}
						payed_money = payed_money - pay_money;
						map.put("payed_money", payed_money);
						map.put("pay_flag", 0);
						map.put("group_id", mapVo.get("group_id"));
						map.put("hos_id", mapVo.get("hos_id"));
						map.put("copy_code", mapVo.get("copy_code"));
						lists.add(map);
					}
					// 更新已付金额、付款标识
					int res = assPaySyncPactMapper.updatePactPlanFKHT(lists);

					mapVo.put("pi_operatetype", "UC");
					mapVo.put("pi_operateresult", "1");
					res2 = addPactInterfaceLog(mapVo);

					return res;
				}
			}
			return 0;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			if ("add".equals(mark)) {
				// 保存接口操作日志 保存失败
				mapVo.put("pi_mresultdesc", "失败");
				mapVo.put("pi_operatetype", "A");
				mapVo.put("pi_operateresult", "0");
				mapVo.put("pi_oresultdesc", "添加失败");
				if ("1".equals(pi_ismakeup)) {
					mapVo.put("makeupresult", "0");// 补录结果（0 1）
				}
				addPactInterfaceLog(mapVo);

			}
			if ("delete".equals(mark)) {
				// 保存接口操作日志 删除失败
				mapVo.put("pi_operatetype", "D");
				mapVo.put("pi_operateresult", "0");
				mapVo.put("pi_oresultdesc", "删除失败");
				addPactInterfaceLog(mapVo);
			}
			if ("update".equals(mark)) {
				mapVo.put("pi_operatetype", "U");
				mapVo.put("pi_operateresult", "0");
				mapVo.put("pi_oresultdesc", "修改失败");
				addPactInterfaceLog(mapVo);
			}
			if ("check".equals(mark)) {
				mapVo.put("pi_operatetype", "C");
				mapVo.put("pi_operateresult", "0");
				mapVo.put("pi_oresultdesc", "确认失败");
				addPactInterfaceLog(mapVo);
			}
			if ("uncheck".equals(mark)) {
				mapVo.put("pi_operatetype", "UC");
				mapVo.put("pi_operateresult", "0");
				mapVo.put("pi_oresultdesc", "取消确认失败");
				addPactInterfaceLog(mapVo);
			}
			throw new SysException(e.getMessage(), e);
		}
	}

	private Boolean queryAccParaByCode() {
		try {
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("para_code", "11003");
			AccPara accpara = assPaySyncPactMapper.queryAccParaByCode(entityMap);
			if ("1".equals(accpara.getPara_value())) {
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	private int addPactPrePayFKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> payMain = assPaySyncPactMapper.queryPactPayMainFKHTByPayNo(mapVo);
			if (payMain.size() <= 0) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				int res1 = 0, res2 = 0, res3 = 0;
				List<Map<String, Object>> list = assPaySyncPactMapper.queryAssPrePayByNo(mapVo);
				for (Map<String, Object> map : list) {
					// 添加付款合同付款主表 pact_pay_main_fkht
					map.put("group_id", mapVo.get("group_id"));
					map.put("hos_id", mapVo.get("hos_id"));
					map.put("copy_code", mapVo.get("copy_code"));
					map.put("is_init", "0"); // 是否期初
					map.put("confirmer", map.get("audit_emp"));// 确认人
					map.put("confirm_date", sdf1.format(map.get("audit_date"))); // 确认日期
					map.put("bill_code", map.get("invoice_no")); // 发票号
					map.put("maker", map.get("create_emp")); // 制单人
					map.put("make_date", sdf1.format(map.get("create_date"))); // 制单日期
					map.put("sup_id", map.get("ven_id")); // 供应商id
					map.put("sup_no", map.get("ven_no")); // 供应商变更id
					map.put("pay_date", sdf1.format(map.get("pay_date"))); // 付款日期
					map.put("pay_money", map.get("bill_money")); // 付款金额
					map.put("checker", ""); // 审核人
					map.put("check_date", ""); // 审核日期
					if ("03".equals(map.get("state"))) {
						map.put("state", "02");
					}
					map.put("bill_source", "2"); // 单据来源 bill_source 单据号 pay_no
					String pay_code = pactNoRuleService.getNo("PACT_PAY_MAIN_FKHT", map);
					map.put("pay_code", pay_code);
					res1 = assPaySyncPactMapper.addPactPayMainFKHT(map);
					// 添加付款合同付款明细表 pact_pay_det_fkht
					mapVo.put("pay_no", map.get("pay_no"));
					mapVo.put("invoice_no", map.get("invoice_no"));
					List<Map<String, Object>> listDet = assPaySyncPactMapper.queryAssPrePayDetByNo(mapVo);
					Integer detail_id = assPaySyncPactMapper.queryMaxDetailId(map);
					if (detail_id == null) {
						detail_id = 1;
						for (Map<String, Object> m : listDet) {
							m.put("cheq_no", "");
							m.put("pay_code", pay_code);
							m.put("pact_code", map.get("pact_code"));
							m.put("detail_id", detail_id++);
							res2 = assPaySyncPactMapper.addPactPayDetFKHT(m);
						}
					} else {
						for (Map<String, Object> m : listDet) {
							m.put("cheq_no", "");
							m.put("pay_code", pay_code);
							m.put("pact_code", map.get("pact_code"));
							m.put("detail_id", ++detail_id);
							res2 = assPaySyncPactMapper.addPactPayDetFKHT(m);
						}
					}
					if (map.get("plan_detail_id") != "" && map.get("plan_detail_id") != null) {
						// 添加付款合同按计划付款pact_pay_plan_fkht
						Map<String, Object> PayPlan = new HashMap<>();
						PayPlan.put("group_id", SessionManager.getGroupId());
						PayPlan.put("hos_id", SessionManager.getHosId());
						PayPlan.put("copy_code", SessionManager.getCopyCode());
						PayPlan.put("pay_code", pay_code);
						PayPlan.put("pact_code", map.get("pact_code"));
						PayPlan.put("plan_det_id", map.get("plan_detail_id"));
						PayPlan.put("pay_money", map.get("bill_money"));
						res3 = pactPayPlanFKHTMapper.add(PayPlan);
					}

				}
				if (res1 > 0 && res2 > 0) {
					return 1;
				}
			}
			return 0;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	private int deletePactPayFKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> payMain = assPaySyncPactMapper.queryPactPayMainFKHTByPayNo(mapVo);

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> m : payMain) {
				m.put("group_id", mapVo.get("group_id"));
				m.put("hos_id", mapVo.get("hos_id"));
				m.put("copy_code", mapVo.get("copy_code"));
				list.add(m);
			}
			if (list.size() > 0) {
				int res1 = assPaySyncPactMapper.deletePactPayPlanFKHT(list); // 删除-付款合同按计划付款
				int res2 = assPaySyncPactMapper.deletePactPayDetFKHT(list); // 删除-付款合同付款明细表
				int res3 = assPaySyncPactMapper.deletePactPayMainFKHT(list); // 删除-付款合同付款主表
				if (res1 > 0 && res2 > 0) {
					return 1;
				}
			}
			return 0;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	private int addPactPayFKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> payMain = assPaySyncPactMapper.queryPactPayMainFKHTByPayNo(mapVo);
			if (payMain.size() <= 0) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				int res1 = 0, res2 = 0, res3 = 0;
				List<Map<String, Object>> list = assPaySyncPactMapper.queryAssPayByNo(mapVo);
				for (Map<String, Object> map : list) {
					// 添加付款合同付款主表 pact_pay_main_fkht
					map.put("group_id", mapVo.get("group_id"));
					map.put("hos_id", mapVo.get("hos_id"));
					map.put("copy_code", mapVo.get("copy_code"));
					map.put("is_init", "0"); // 是否期初
					map.put("bill_code", map.get("invoice_no")); // 发票号
					map.put("sup_id", map.get("ven_id")); // 供应商id
					map.put("sup_no", map.get("ven_no")); // 供应商变更id
					map.put("pay_date", sdf1.format(map.get("pay_date"))); // 付款日期
					map.put("pay_money", map.get("bill_money")); // 付款金额
					map.put("maker", map.get("create_emp")); // 制单人
					map.put("make_date", sdf1.format(map.get("create_date"))); // 制单日期
					map.put("checker", map.get("audit_emp")); // 审核人
					map.put("check_date", sdf1.format(map.get("audit_date"))); // 审核日期
					map.put("confirmer", map.get("acc_check_emp")); // 确认人
					map.put("confirm_date", sdf1.format(map.get("acc_check_time"))); // 确认日期
					map.put("bill_source", "1"); // 单据来源 bill_source 单据号 pay_no
					String pay_code = pactNoRuleService.getNo("PACT_PAY_MAIN_FKHT", map);
					map.put("pay_code", pay_code);
					res1 = assPaySyncPactMapper.addPactPayMainFKHT(map);
					// 添加付款合同付款明细表 pact_pay_det_fkht
					mapVo.put("pay_no", map.get("pay_no"));
					mapVo.put("invoice_no", map.get("invoice_no"));
					mapVo.put("pact_code", map.get("pact_code"));
					List<Map<String, Object>> listDet = assPaySyncPactMapper.queryAssPayDetByNo(mapVo);
					Integer detail_id = assPaySyncPactMapper.queryMaxDetailId(map);
					if (detail_id == null) {
						detail_id = 1;
						for (Map<String, Object> m : listDet) {
							m.put("cheq_no", "");
							m.put("pay_code", pay_code);
							m.put("pact_code", map.get("pact_code"));
							m.put("detail_id", detail_id++);
							res2 = assPaySyncPactMapper.addPactPayDetFKHT(m);
						}
					} else {
						for (Map<String, Object> m : listDet) {
							m.put("cheq_no", "");
							m.put("pay_code", pay_code);
							m.put("pact_code", map.get("pact_code"));
							m.put("detail_id", ++detail_id);
							res2 = assPaySyncPactMapper.addPactPayDetFKHT(m);
						}
					}
					// 添加付款合同按计划付款pact_pay_plan_fkht
					Double bill_money = assPaySyncPactMapper.queryAssDetStage(mapVo); // 发票金额
					Double pay_money = 0.0;
					List<Map<String, Object>> listPlan = assPaySyncPactMapper.queryPactPlanStage(mapVo);
					if (listPlan.size() == 1) {
						pay_money = bill_money;
						Map<String, Object> PayPlan = new HashMap<>();
						PayPlan.put("group_id", SessionManager.getGroupId());
						PayPlan.put("hos_id", SessionManager.getHosId());
						PayPlan.put("copy_code", SessionManager.getCopyCode());
						PayPlan.put("pay_code", pay_code);
						PayPlan.put("pact_code", map.get("pact_code"));
						PayPlan.put("plan_det_id", listPlan.get(0).get("plan_detail_id"));
						PayPlan.put("pay_money", pay_money);
						res3 = pactPayPlanFKHTMapper.add(PayPlan);
					}
					if (listPlan.size() > 1) {
						Double money = 0.0;
						Double plan_money = 0.0; // 计划金额
						Double payed_money = 0.0;// 已付金额
						Double no_money = 0.0; // 未付金额
						for (Map<String, Object> plan : listPlan) {
							if (plan.get("plan_money") != "" && plan.get("plan_money") != null) {
								plan_money = Double.parseDouble(plan.get("plan_money").toString()); // 计划金额
							}
							if (plan.get("payed_money") != "" && plan.get("payed_money") != null) {
								payed_money = Double.parseDouble(plan.get("payed_money").toString()); // 已付金额
							}
							no_money = plan_money - payed_money;
							Map<String, Object> PayPlan = new HashMap<>();
							PayPlan.put("group_id", SessionManager.getGroupId());
							PayPlan.put("hos_id", SessionManager.getHosId());
							PayPlan.put("copy_code", SessionManager.getCopyCode());
							PayPlan.put("pay_code", pay_code);
							PayPlan.put("pact_code", map.get("pact_code"));
							PayPlan.put("plan_det_id", plan.get("plan_det_id"));
							PayPlan.put("pay_money", no_money);
							money += no_money;
							res3 = pactPayPlanFKHTMapper.add(PayPlan);
						}
						if (money < bill_money) { // 若有剩余发票金额，加到最后一条计划明细的付款金额上
							Integer plan_det_id = Integer
									.parseInt(listPlan.get(listPlan.size() - 1).get("plan_det_id").toString());
							Map<String, Object> PayPlan = new HashMap<>();
							PayPlan.put("group_id", SessionManager.getGroupId());
							PayPlan.put("hos_id", SessionManager.getHosId());
							PayPlan.put("copy_code", SessionManager.getCopyCode());
							PayPlan.put("pay_code", pay_code);
							PayPlan.put("pact_code", map.get("pact_code"));
							PayPlan.put("plan_det_id", plan_det_id);
							Double pay_money1 = assPaySyncPactMapper.queryPactPayPlanFKHT(PayPlan);
							pay_money1 = pay_money1 + (bill_money - money);
							PayPlan.put("pay_money", pay_money1);
							assPaySyncPactMapper.updatePactPayPlanFKHT(PayPlan);
						}
					}
				}
				if (res1 >= 0 && res2 > 0) {
					return 1;
				}
			}
			return 0;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	private int addPactInterfaceLog(Map<String, Object> mapVo) {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
			String pi_ismakeup = mapVo.get("pi_ismakeup").toString(); // 是否补录
			String pay_no = mapVo.get("pay_no").toString(); // 资产付款单号-单据号
			String mark = mapVo.get("mark").toString(); // 添加add 删除delete 修改update...
			String pre_pay = mapVo.get("pre_pay").toString(); // 是否预付款（0否 1是）
			String pi_recorddata = "pay_no:" + pay_no + ",mark:" + mark + ",pre_pay:" + pre_pay + ",pi_ismakeup:"
					+ pi_ismakeup;
			mapVo.put("pit_typename", "资产付款数据与合同付款数据同步"); // 分类名称
			mapVo.put("pi_methodname", "assPaySyncPact"); // 方法名
			mapVo.put("pi_classname", "com.chd.hrp.ass.serviceImpl.asspaysyncpact.AssPaySyncPactServiceImpl");// 类名
			List<Map<String, Object>> list = assPaySyncPactMapper.queryPactInterfaceType(mapVo);
			Map<String, Object> map = list.get(0);
			Map<String, Object> pi_map = new HashMap<>();
			if ("1".equals(pi_ismakeup)) {
				if (map.get("pit_typecode") != "" && map.get("pit_typecode") != null) {
					pi_map.put("pit_typecode", map.get("pit_typecode")); // 分类编码（合同接口来源）
				} else {
					pi_map.put("pit_typecode", "10001"); // 分类编码（合同接口来源）
				}
				pi_map.put("pi_logid", mapVo.get("pi_logid")); // 合同接口日志id
				pi_map.put("pit_typename", map.get("pit_typename")); // 分类名称
				pi_map.put("pi_methodname", "assPaySyncPact"); // 方法名
				pi_map.put("pi_calssname", "com.chd.hrp.ass.serviceImpl.asspaysyncpact.AssPaySyncPactServiceImpl"); // 类名
				pi_map.put("pi_classdesc", "资产付款数据与合同付款数据同步"); // 类描述
				pi_map.put("pi_beanname", "AssPaySyncPactService"); // beanname
				pi_map.put("pi_record", "group_id,hos_id,copy_code,pay_code,pact_code"); // 操作记录主键
				pi_map.put("pi_recorddata", pi_recorddata); // 操作数据（即入参）
				pi_map.put("pi_operatetype", mapVo.get("pi_operatetype")); // 操作动作
				pi_map.put("pi_operatedate", sdf1.format(new Date())); // 操作日期
				pi_map.put("pi_operatetime", sdf2.format(new Date())); // 操作时间
				pi_map.put("pi_operateresult", mapVo.get("pi_operateresult")); // 操作结果
				if ("0".equals(mapVo.get("pi_operateresult"))) {
					pi_map.put("pi_oresultdesc", mapVo.get("pi_oresultdesc")); // 操作失败描述
				}
				pi_map.put("pi_operaterid", SessionManager.getUserId()); // 操作人id
				List<Map<String, Object>> li = assPaySyncPactMapper.queryUserDict(mapVo);
				pi_map.put("pi_operatercode", li.get(0).get("user_code")); // 操作人编码
				pi_map.put("pi_operater", li.get(0).get("user_name")); // 操作人名称
				pi_map.put("pi_ismakeup", "1"); // 是否补录 0:否 1:是
				pi_map.put("makeupresult", mapVo.get("makeupresult"));// 补录结果（0 1）
				pi_map.put("pi_mresultdesc", mapVo.get("pi_mresultdesc"));// 补录结果描述
				pi_map.put("pi_makeupdate", sdf1.format(new Date())); // 补录日期
				pi_map.put("pi_makeuptime", sdf2.format(new Date())); // 补录时间
				pi_map.put("pi_makeuperid", SessionManager.getUserId()); // 补录人id
				pi_map.put("pi_makeupercode", li.get(0).get("user_code")); // 补录人编码
				pi_map.put("pi_makeuper", li.get(0).get("user_name")); // 补录人名称
			} else {
				pi_map.put("pit_typecode", map.get("pit_typecode")); // 分类编码（合同接口来源）
				pi_map.put("pit_typename", map.get("pit_typename")); // 分类名称
				pi_map.put("pi_methodname", "assPaySyncPact"); // 方法名
				pi_map.put("pi_calssname", "com.chd.hrp.ass.serviceImpl.asspaysyncpact.AssPaySyncPactServiceImpl"); // 类名
				pi_map.put("pi_classdesc", "资产付款数据与合同付款数据同步"); // 类描述
				pi_map.put("pi_beanname", "AssPaySyncPactService"); // beanname
				pi_map.put("pi_record", "group_id,hos_id,copy_code,pay_code,pact_code"); // 操作记录主键
				pi_map.put("pi_recorddata", pi_recorddata); // 操作数据（即入参）
				pi_map.put("pi_operatetype", mapVo.get("pi_operatetype")); // 操作动作
				pi_map.put("pi_operatedate", sdf1.format(new Date())); // 操作日期
				pi_map.put("pi_operatetime", sdf2.format(new Date())); // 操作时间
				pi_map.put("pi_operateresult", mapVo.get("pi_operateresult")); // 操作结果
				if ("0".equals(mapVo.get("pi_operateresult"))) {
					pi_map.put("pi_oresultdesc", mapVo.get("pi_oresultdesc")); // 操作失败描述
				}
				pi_map.put("pi_operaterid", SessionManager.getUserId()); // 操作人id
				List<Map<String, Object>> li = assPaySyncPactMapper.queryUserDict(mapVo);
				pi_map.put("pi_operatercode", li.get(0).get("user_code")); // 操作人编码
				pi_map.put("pi_operater", li.get(0).get("user_name")); // 操作人名称
				pi_map.put("pi_ismakeup", "0"); // 是否补录 0:否 1:是
			}
			int res = assInterfaceJournalService.insertAssInterfaceJournal(pi_map);
			return res;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
