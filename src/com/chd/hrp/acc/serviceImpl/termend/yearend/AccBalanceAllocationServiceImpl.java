/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.termend.yearend;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.termend.yearend.AccBalanceAllocationMapper;
import com.chd.hrp.acc.dao.termend.yearend.AccBudgTargetMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.service.termend.yearend.AccBalanceAllocationService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/**
 * @Title. @Description. 盈余分配<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accBalanceAllocationService")
public class AccBalanceAllocationServiceImpl implements AccBalanceAllocationService {

	private static Logger logger = Logger.getLogger(AccBalanceAllocationServiceImpl.class);

	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	@Resource(name = "accBalanceAllocationMapper")
	private final AccBalanceAllocationMapper accBalanceAllocationMapper = null;
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	@Resource(name = "accBudgTargetMapper")
	private final AccBudgTargetMapper accBudgTargetMapper = null;
	
	/**
	 * 1.校验财政收入金额与财政支出金额相等则不能生成凭证。
	 * 2.凭证借方分录金额 = 财政收入金额 - 财政支出金额
	 * 3.凭证贷方分录金额 = 财政收入金额 - 财政支出金额
	 */
	@Override
	public String addAccBalanceAllocationVouch(Map<String, Object> entityMap) throws DataAccessException {
		//存放凭证主表信息
		Map<String, Object> vouchMap = new HashMap<String, Object>();
		//存放凭证分录信息
		List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
		//存放日志信息
		Map<String, Object> logMap = new HashMap<String, Object>();
		//01：单独生成财务凭证，02：单独生成预算凭证
		String create_kind = "";
		
		try{
			if(entityMap.get("acc_month") != null && !"12".equals(entityMap.get("acc_month"))){
				return "{\"error\":\"12月才能生成盈余分配凭证!\"}";
			}
			//获取凭证模板信息
			AccTermendTemplate att = accTermendTemplateMapper.queryAccTermendTemplateByCode(entityMap);
			if(att.getVouch_type_code() == null || "".equals(att.getVouch_type_code())){
				return "{\"error\":\"模板"+att.getTemplate_name()+"还不存在，请先进行设置！\"}";
			}
			
			String template_type_codes = "'"+att.getTemplate_type_code()+"'";
			//如果单独生成财务或预算凭证需要更改原来的模板类型
			if(entityMap.get("create_kind") != null && !"".equals(entityMap.get("create_kind"))){
				create_kind = entityMap.get("create_kind").toString();
				att.setTemplate_type_code(att.getTemplate_type_code() + create_kind);
				template_type_codes += ",'"+att.getTemplate_type_code()+"'";
			}else if("Z00901".equals(att.getTemplate_type_code()) || "Z00904".equals(att.getTemplate_type_code())){
				template_type_codes += ",'"+att.getTemplate_type_code()+"01','"+att.getTemplate_type_code()+"02'";
			}
			
			//如果本期间已生成凭证则不允许生成凭证
			entityMap.put("template_type_codes", template_type_codes);
			entityMap.put("template_id", att.getTemplate_id());
			int flag = accTermendTemplateMapper.existsAccVouchByTemplate(entityMap);
			if(flag > 0){
				return "{\"error\":\"本期间已生成凭证，不能重复生成!\"}";
			}
			
			//如果存在未记账凭证则不允许生成凭证
			flag = accTermendTemplateMapper.queryAccUnAccountVouch(entityMap);
			if(flag > 0){
				return "{\"error\":\"本期间有未记账凭证，不能生成盈余分配凭证!\"}";
			}
			Double money = 0.00, money1 = 0.00;
			String template_type_code = att.getTemplate_type_code();
			Date date = new Date();
			Long group_id = att.getGroup_id();
			Long hos_id = att.getHos_id();
			String copy_code = att.getCopy_code();
			int vouch_row = 1;
			
			/*************************组装凭证主表信息*******************************/
			String vouch_id = UUIDLong.absStringUUID();
			vouchMap.put("group_id", group_id);
			vouchMap.put("hos_id", hos_id); 
			vouchMap.put("copy_code", copy_code);
			vouchMap.put("acc_year", att.getAcc_year());
			vouchMap.put("acc_month", entityMap.get("acc_month"));
			vouchMap.put("vouch_id", vouch_id);
			vouchMap.put("vouch_type_code", att.getVouch_type_code());  //凭证类型
			vouchMap.put("vouch_date", accTermendTemplateMapper.queryAccVouchDateByYearMonth(vouchMap));  //凭证日期
			vouchMap.put("vouch_att_num", 0);  //附件数量
			vouchMap.put("busi_type_code", "Z009");  //业务类型
			vouchMap.put("create_date", date);  //制单日期
			
			/****************************组装分录信息***************************************/
			Map<String, Object> debitMap = null;
			Map<String, Object> creditMap = null;
			if(template_type_code != null && template_type_code.startsWith("Z00901")){//盈余转出
				/***********************财务会计********begin*******************/
				if("".equals(create_kind) || "01".equals(create_kind)){
					if(att.getDebit_subj_code1() == null || "".equals(att.getDebit_subj_code1())){
						return "{\"error\":\"本期盈余科目为空，请维护后再生成！\"}";
					}
					//获取本期盈余的余额
					entityMap.put("subj_code", att.getDebit_subj_code1());
					money = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
					
					if(money == null || money == 0){
						return "{\"error\":\"本期盈余科目【"+att.getDebit_subj_name1()+"】的余额等于0，不能生成盈余转出凭证！\"}";
					}
					/***********************财务借方分录***************************/
					debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", att.getDebit_subj_code1());
					debitMap.put("summary", att.getSummary());
					debitMap.put("debit", money);
					debitMap.put("credit", 0);
					debitMap.put("debit_w", 0);
					debitMap.put("credit_w", 0);
					detailList.add(debitMap);
					vouch_row += 1;  //分录行数加1
					/***********************财务贷方分录***************************/
					if(att.getCredit_subj_code1() == null || "".equals(att.getCredit_subj_code1())){
						return "{\"error\":\"盈余分配科目为空，请维护后再生成！\"}";
					}
					creditMap = new HashMap<String, Object>();
					creditMap.put("group_id", group_id);
					creditMap.put("hos_id", hos_id); 
					creditMap.put("copy_code", copy_code);
					creditMap.put("vouch_id", vouch_id);
					creditMap.put("vouch_row", vouch_row);
					creditMap.put("subj_code", att.getCredit_subj_code1());
					creditMap.put("summary", att.getSummary());
					creditMap.put("debit", 0);
					creditMap.put("credit", money);
					creditMap.put("debit_w", 0);
					creditMap.put("credit_w", 0);
					detailList.add(creditMap);
					vouch_row += 1;  //分录行数加1
				}
				/***********************财务会计********end*********************/
				
				/***********************预算会计********begin*******************/
				if("".equals(create_kind) || "02".equals(create_kind)){
					money = 0.0; money1= 0.0;
					/***********************预算借方分录1***************************/
					if(att.getDebit_subj_code3() == null || "".equals(att.getDebit_subj_code3())){
						return "{\"error\":\"非财政拨款结转科目为空，请维护后再生成！\"}";
					}
					//获取非财政拨款结转的余额
					entityMap.put("subj_code", att.getDebit_subj_code3());
					money = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
					if(money != null && money > 0){
						debitMap = new HashMap<String, Object>();
						debitMap.put("group_id", group_id);
						debitMap.put("hos_id", hos_id); 
						debitMap.put("copy_code", copy_code);
						debitMap.put("vouch_id", vouch_id);
						debitMap.put("vouch_row", vouch_row);
						debitMap.put("subj_code", att.getDebit_subj_code3());
						debitMap.put("summary", att.getSummary());
						debitMap.put("debit", money);
						debitMap.put("credit", 0);
						debitMap.put("debit_w", 0);
						debitMap.put("credit_w", 0);
						detailList.add(debitMap);
						vouch_row += 1;  //分录行数加1
						money1 += money;
					}
					/***********************预算借方分录2***************************/
					money = 0.0; 
					if(att.getDebit_subj_code4() == null || "".equals(att.getDebit_subj_code4())){
						return "{\"error\":\"经营结余科目为空，请维护后再生成！\"}";
					}
					//获取经营结余的余额
					entityMap.put("subj_code", att.getDebit_subj_code4());
					money = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
					if(money != null && money > 0){
						debitMap = new HashMap<String, Object>();
						debitMap.put("group_id", group_id);
						debitMap.put("hos_id", hos_id); 
						debitMap.put("copy_code", copy_code);
						debitMap.put("vouch_id", vouch_id);
						debitMap.put("vouch_row", vouch_row);
						debitMap.put("subj_code", att.getDebit_subj_code4());
						debitMap.put("summary", att.getSummary());
						debitMap.put("debit", money);
						debitMap.put("credit", 0);
						debitMap.put("debit_w", 0);
						debitMap.put("credit_w", 0);
						detailList.add(debitMap);
						vouch_row += 1;  //分录行数加1
						money1 += money;
					}
					/***********************预算借方分录3***************************/
					money = 0.0; 
					if(att.getDebit_subj_code5() == null || "".equals(att.getDebit_subj_code5())){
						return "{\"error\":\"其他结余科目为空，请维护后再生成！\"}";
					}
					//获取其他结余的余额
					entityMap.put("subj_code", att.getDebit_subj_code5());
					money = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
					if(money != null && money > 0){
						debitMap = new HashMap<String, Object>();
						debitMap.put("group_id", group_id);
						debitMap.put("hos_id", hos_id); 
						debitMap.put("copy_code", copy_code);
						debitMap.put("vouch_id", vouch_id);
						debitMap.put("vouch_row", vouch_row);
						debitMap.put("subj_code", att.getDebit_subj_code5());
						debitMap.put("summary", att.getSummary());
						debitMap.put("debit", money);
						debitMap.put("credit", 0);
						debitMap.put("debit_w", 0);
						debitMap.put("credit_w", 0);
						detailList.add(debitMap);
						vouch_row += 1;  //分录行数加1
						money1 += money;
					}
					
					if(money1 == null || money1 == 0){
						return "{\"error\":\"预算转出科目总余额等于0，不能生成盈余转出凭证！\"}";
					}
					/***********************预算贷方分录***************************/
					if(att.getCredit_subj_code3() == null || "".equals(att.getCredit_subj_code3())){
						return "{\"error\":\"非财政拨款结余分配科目为空，请维护后再生成！\"}";
					}
					creditMap = new HashMap<String, Object>();
					creditMap.put("group_id", group_id);
					creditMap.put("hos_id", hos_id); 
					creditMap.put("copy_code", copy_code);
					creditMap.put("vouch_id", vouch_id);
					creditMap.put("vouch_row", vouch_row);
					creditMap.put("subj_code", att.getCredit_subj_code3());
					creditMap.put("summary", att.getSummary());
					creditMap.put("debit", 0);
					creditMap.put("credit", money1);
					creditMap.put("debit_w", 0);
					creditMap.put("credit_w", 0);
					detailList.add(creditMap);
				}
				/***********************预算会计********end*********************/
			}else if(template_type_code != null && "Z00902".equals(template_type_code)){//提取员工福利基金
				/***********************财务会计********begin*******************/
				if(att.getDebit_subj_code1() == null || "".equals(att.getDebit_subj_code1())){
					return "{\"error\":\"盈余分配科目为空，请维护后再生成！\"}";
				}
				entityMap.put("subj_code", att.getDebit_subj_code1());
				money = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
				if(money == null || money == 0){
					return "{\"error\":\"盈余分配科目【"+att.getDebit_subj_name1()+"】的余额等于0，不能生成提取专用基金凭证！\"}";
				}
				
				Double empMoney = NumberUtil.numberToRound(money * att.getRate() / 100);
				if(empMoney == null || empMoney == 0){
					return "{\"error\":\"提取后的专用基金科目【"+att.getCredit_subj_code1()+"】金额为0，不能生成提取专用基金凭证！\"}";
				}
				
				/***********************财务借方分录***************************/
				debitMap = new HashMap<String, Object>();
				debitMap.put("group_id", group_id);
				debitMap.put("hos_id", hos_id); 
				debitMap.put("copy_code", copy_code);
				debitMap.put("vouch_id", vouch_id);
				debitMap.put("vouch_row", vouch_row);
				debitMap.put("subj_code", att.getDebit_subj_code1());
				debitMap.put("summary", att.getSummary());
				debitMap.put("debit", empMoney);
				debitMap.put("credit", 0);
				debitMap.put("debit_w", 0);
				debitMap.put("credit_w", 0);
				detailList.add(debitMap);
				vouch_row += 1;  //分录行数加1
				/***********************财务贷方分录-专用基金***************************/
				if(att.getCredit_subj_code1() == null || "".equals(att.getCredit_subj_code1())){
					return "{\"error\":\"提取专用基金科目为空，请维护后再生成！\"}";
				}
				creditMap = new HashMap<String, Object>();
				creditMap.put("group_id", group_id);
				creditMap.put("hos_id", hos_id); 
				creditMap.put("copy_code", copy_code);
				creditMap.put("vouch_id", vouch_id);
				creditMap.put("vouch_row", vouch_row);
				creditMap.put("subj_code", att.getCredit_subj_code1());
				creditMap.put("summary", att.getSummary());
				creditMap.put("debit", 0);
				creditMap.put("credit", empMoney);
				creditMap.put("debit_w", 0);
				creditMap.put("credit_w", 0);
				detailList.add(creditMap);
				vouch_row += 1;  //分录行数加1
				/***********************财务会计********end*********************/
				
				/***********************预算会计********begin*******************/
				if(att.getDebit_subj_code3() == null || "".equals(att.getDebit_subj_code3())){
					return "{\"error\":\"非财政拨款结余分配科目为空，请维护后再生成！\"}";
				}
				//与财务平行记账，财务提多少预算相应提多少
				/***********************预算借方分录***************************/
				debitMap = new HashMap<String, Object>();
				debitMap.put("group_id", group_id);
				debitMap.put("hos_id", hos_id); 
				debitMap.put("copy_code", copy_code);
				debitMap.put("vouch_id", vouch_id);
				debitMap.put("vouch_row", vouch_row);
				debitMap.put("subj_code", att.getDebit_subj_code3());
				debitMap.put("summary", att.getSummary());
				debitMap.put("debit", empMoney);
				debitMap.put("credit", 0);
				debitMap.put("debit_w", 0);
				debitMap.put("credit_w", 0);
				detailList.add(debitMap);
				vouch_row += 1;  //分录行数加1
				/***********************预算贷方分录-专用基金***************************/
				if(att.getCredit_subj_code3() == null || "".equals(att.getCredit_subj_code3())){
					return "{\"error\":\"提取专用结余科目为空，请维护后再生成！\"}";
				}
				creditMap = new HashMap<String, Object>();
				creditMap.put("group_id", group_id);
				creditMap.put("hos_id", hos_id); 
				creditMap.put("copy_code", copy_code);
				creditMap.put("vouch_id", vouch_id);
				creditMap.put("vouch_row", vouch_row);
				creditMap.put("subj_code", att.getCredit_subj_code3());
				creditMap.put("summary", att.getSummary());
				creditMap.put("debit", 0);
				creditMap.put("credit", empMoney);
				creditMap.put("debit_w", 0);
				creditMap.put("credit_w", 0);
				detailList.add(creditMap);
				/***********************预算会计********end*********************/
			}else if(template_type_code != null && "Z00903".equals(template_type_code)){//转员工福利基金
				/***********************财务会计********begin*******************/
				if(att.getDebit_subj_code1() == null || "".equals(att.getDebit_subj_code1())){
					return "{\"error\":\"提取专用基金科目为空，请维护后再生成！\"}";
				}
				//获取提取专用基金科目余额
				entityMap.put("subj_code", att.getDebit_subj_code1());
				money = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
				if(money == null || money == 0){
					return "{\"error\":\"提取专用基金科目【"+att.getDebit_subj_name1()+"】的余额等于0，不能生成转员工福利基金凭证！\"}";
				}
				/***********************财务借方分录***************************/
				debitMap = new HashMap<String, Object>();
				debitMap.put("group_id", group_id);
				debitMap.put("hos_id", hos_id); 
				debitMap.put("copy_code", copy_code);
				debitMap.put("vouch_id", vouch_id);
				debitMap.put("vouch_row", vouch_row);
				debitMap.put("subj_code", att.getDebit_subj_code1());
				debitMap.put("summary", att.getSummary());
				debitMap.put("debit", money);
				debitMap.put("credit", 0);
				debitMap.put("debit_w", 0);
				debitMap.put("credit_w", 0);
				detailList.add(debitMap);
				vouch_row += 1;  //分录行数加1
				/***********************财务贷方分录***************************/
				if(att.getCredit_subj_code1() == null || "".equals(att.getCredit_subj_code1())){
					return "{\"error\":\"专用基金科目为空，请维护后再生成！\"}";
				}
				creditMap = new HashMap<String, Object>();
				creditMap.put("group_id", group_id);
				creditMap.put("hos_id", hos_id); 
				creditMap.put("copy_code", copy_code);
				creditMap.put("vouch_id", vouch_id);
				creditMap.put("vouch_row", vouch_row);
				creditMap.put("subj_code", att.getCredit_subj_code1());
				creditMap.put("summary", att.getSummary());
				creditMap.put("debit", 0);
				creditMap.put("credit", money);
				creditMap.put("debit_w", 0);
				creditMap.put("credit_w", 0);
				detailList.add(creditMap);
				vouch_row += 1;  //分录行数加1
				/***********************财务会计********end*********************/
				
				/***********************预算会计********begin*******************/
				if(att.getDebit_subj_code3() == null || "".equals(att.getDebit_subj_code3())){
					return "{\"error\":\"提取专用结余科目为空，请维护后再生成！\"}";
				}
				//获取提取专用结余科目余额
				entityMap.put("subj_code", att.getDebit_subj_code3());
				money1 = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);

				if(money1 == null || money1 == 0){
					return "{\"error\":\"提取专用结余科目【"+att.getDebit_subj_name3()+"】的余额等于0，不能生成转员工福利基金凭证！\"}";
				}
				/***********************预算借方分录***************************/
				debitMap = new HashMap<String, Object>();
				debitMap.put("group_id", group_id);
				debitMap.put("hos_id", hos_id); 
				debitMap.put("copy_code", copy_code);
				debitMap.put("vouch_id", vouch_id);
				debitMap.put("vouch_row", vouch_row);
				debitMap.put("subj_code", att.getDebit_subj_code3());
				debitMap.put("summary", att.getSummary());
				debitMap.put("debit", money1);
				debitMap.put("credit", 0);
				debitMap.put("debit_w", 0);
				debitMap.put("credit_w", 0);
				detailList.add(debitMap);
				vouch_row += 1;  //分录行数加1
				/***********************预算贷方分录***************************/
				if(att.getCredit_subj_code3() == null || "".equals(att.getCredit_subj_code3())){
					return "{\"error\":\"专用结余科目为空，请维护后再生成！\"}";
				}
				creditMap = new HashMap<String, Object>();
				creditMap.put("group_id", group_id);
				creditMap.put("hos_id", hos_id); 
				creditMap.put("copy_code", copy_code);
				creditMap.put("vouch_id", vouch_id);
				creditMap.put("vouch_row", vouch_row);
				creditMap.put("subj_code", att.getCredit_subj_code3());
				creditMap.put("summary", att.getSummary());
				creditMap.put("debit", 0);
				creditMap.put("credit", money1);
				creditMap.put("debit_w", 0);
				creditMap.put("credit_w", 0);
				detailList.add(creditMap);
				/***********************预算会计********end*********************/
			}else if(template_type_code != null && template_type_code.startsWith("Z00904")){//转累计盈余
				/***********************财务会计********begin*********************/
				if("".equals(create_kind) || "01".equals(create_kind)){
					if(att.getDebit_subj_code1() == null || "".equals(att.getDebit_subj_code1())){
						return "{\"error\":\"盈余分配科目为空，请维护后再生成！\"}";
					}
					//获取盈余分配余额
					entityMap.put("subj_code", att.getDebit_subj_code1());
					money = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
					
					if(money == null || money == 0){
						return "{\"error\":\"盈余分配科目【"+att.getDebit_subj_name1()+"】的余额等于0，不能生成转累计盈余凭证！\"}";
					}
					/***********************财务借方分录***************************/
					debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", att.getDebit_subj_code1());
					debitMap.put("summary", att.getSummary());
					debitMap.put("debit", money);
					debitMap.put("credit", 0);
					debitMap.put("debit_w", 0);
					debitMap.put("credit_w", 0);
					detailList.add(debitMap);
					vouch_row += 1;  //分录行数加1
					/***********************财务贷方分录***************************/
					if(att.getCredit_subj_code1() == null || "".equals(att.getCredit_subj_code1())){
						return "{\"error\":\"累计盈余科目为空，请维护后再生成！\"}";
					}
					creditMap = new HashMap<String, Object>();
					creditMap.put("group_id", group_id);
					creditMap.put("hos_id", hos_id); 
					creditMap.put("copy_code", copy_code);
					creditMap.put("vouch_id", vouch_id);
					creditMap.put("vouch_row", vouch_row);
					creditMap.put("subj_code", att.getCredit_subj_code1());
					creditMap.put("summary", att.getSummary());
					creditMap.put("debit", 0);
					creditMap.put("credit", money);
					creditMap.put("debit_w", 0);
					creditMap.put("credit_w", 0);
					detailList.add(creditMap);
					vouch_row += 1;  //分录行数加1
				}
				/***********************财务会计********end***********************/
				
				/***********************预算会计********begin*********************/
				if("".equals(create_kind) || "02".equals(create_kind)){
					if(att.getDebit_subj_code3() == null || "".equals(att.getDebit_subj_code3())){
						return "{\"error\":\"非财政拨款结余分配科目为空，请维护后再生成！\"}";
					}
					//获取非财政拨款结余分配余额
					entityMap.put("subj_code", att.getDebit_subj_code3());
					money1 = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
					
					if(money1 == null || money1 == 0){
						return "{\"error\":\"非财政拨款结余分配科目【"+att.getDebit_subj_name1()+"】的余额等于0，不能生成转累计盈余凭证！\"}";
					}
					/***********************预算借方分录***************************/
					debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", att.getDebit_subj_code3());
					debitMap.put("summary", att.getSummary());
					debitMap.put("debit", money1);
					debitMap.put("credit", 0);
					debitMap.put("debit_w", 0);
					debitMap.put("credit_w", 0);
					detailList.add(debitMap);
					vouch_row += 1;  //分录行数加1
					/***********************预算贷方分录***************************/
					if(att.getCredit_subj_code3() == null || "".equals(att.getCredit_subj_code3())){
						return "{\"error\":\"累计结余分配科目为空，请维护后再生成！\"}";
					}
					creditMap = new HashMap<String, Object>();
					creditMap.put("group_id", group_id);
					creditMap.put("hos_id", hos_id); 
					creditMap.put("copy_code", copy_code);
					creditMap.put("vouch_id", vouch_id);
					creditMap.put("vouch_row", vouch_row);
					creditMap.put("subj_code", att.getCredit_subj_code3());
					creditMap.put("summary", att.getSummary());
					creditMap.put("debit", 0);
					creditMap.put("credit", money1);
					creditMap.put("debit_w", 0);
					creditMap.put("credit_w", 0);
					detailList.add(creditMap);
				}
				/***********************预算会计********end***********************/
			}else if(template_type_code != null && "Z00905".equals(template_type_code)){//无偿调拨净资产转入
				if(att.getDebit_subj_code1() == null || "".equals(att.getDebit_subj_code1())){
					return "{\"error\":\"无偿调拨净资产科目为空，请维护后再生成！\"}";
				}
				//获取无偿调拨净资产的余额
				entityMap.put("subj_code", att.getDebit_subj_code1());
				money = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
				
				if(money == null || money == 0){
					return "{\"error\":\"无偿调拨净资产科目【"+att.getDebit_subj_name1()+"】的余额等于0，不能生成无偿调拨净资产转入凭证！\"}";
				}
				/***********************借方分录***************************/
				debitMap = new HashMap<String, Object>();
				debitMap.put("group_id", group_id);
				debitMap.put("hos_id", hos_id); 
				debitMap.put("copy_code", copy_code);
				debitMap.put("vouch_id", vouch_id);
				debitMap.put("vouch_row", vouch_row);
				debitMap.put("subj_code", att.getDebit_subj_code1());
				debitMap.put("summary", att.getSummary());
				if(money > 0){
					debitMap.put("debit", money);
					debitMap.put("credit", 0);
				}else{
					debitMap.put("debit", 0);
					debitMap.put("credit", money);
				}
				debitMap.put("debit_w", 0);
				debitMap.put("credit_w", 0);
				detailList.add(debitMap);
				/***********************贷方分录***************************/
				if(att.getCredit_subj_code1() == null || "".equals(att.getCredit_subj_code1())){
					return "{\"error\":\"累计盈余科目为空，请维护后再生成！\"}";
				}
				creditMap = new HashMap<String, Object>();
				creditMap.put("group_id", group_id);
				creditMap.put("hos_id", hos_id); 
				creditMap.put("copy_code", copy_code);
				creditMap.put("vouch_id", vouch_id);
				creditMap.put("vouch_row", vouch_row);
				creditMap.put("subj_code", att.getCredit_subj_code1());
				creditMap.put("summary", att.getSummary());
				if(money > 0){
					creditMap.put("debit", 0);
					creditMap.put("credit", money);
				}else{
					creditMap.put("debit", money);
					creditMap.put("credit", 0);
				}
				creditMap.put("debit_w", 0);
				creditMap.put("credit_w", 0);
				detailList.add(creditMap);
			}else if(template_type_code != null && "Z00906".equals(template_type_code)){//转以前年度盈余调整
				if(att.getDebit_subj_code1() == null || "".equals(att.getDebit_subj_code1())){
					return "{\"error\":\"以前年度盈余调整科目为空，请维护后再生成！\"}";
				}
				//获取以前年度盈余调整的余额
				entityMap.put("subj_code", att.getDebit_subj_code1());
				money = accBalanceAllocationMapper.queryEndMoneyBySubj(entityMap);
				
				if(money == null || money == 0){
					return "{\"error\":\"以前年度盈余调整科目【"+att.getDebit_subj_name1()+"】的余额等于0，不能生成转以前年度盈余调整凭证！\"}";
				}
				
				/***********************借方分录***************************/
				debitMap = new HashMap<String, Object>();
				debitMap.put("group_id", group_id);
				debitMap.put("hos_id", hos_id); 
				debitMap.put("copy_code", copy_code);
				debitMap.put("vouch_id", vouch_id);
				debitMap.put("vouch_row", vouch_row);
				debitMap.put("subj_code", att.getDebit_subj_code1());
				debitMap.put("summary", att.getSummary());
				debitMap.put("debit", money);
				debitMap.put("credit", 0);
				debitMap.put("debit_w", 0);
				debitMap.put("credit_w", 0);
				detailList.add(debitMap);
				vouch_row += 1;  //分录行数加1
				/***********************贷方分录***************************/
				if(att.getCredit_subj_code1() == null || "".equals(att.getCredit_subj_code1())){
					return "{\"error\":\"累计盈余科目为空，请维护后再生成！\"}";
				}
				creditMap = new HashMap<String, Object>();
				creditMap.put("group_id", group_id);
				creditMap.put("hos_id", hos_id); 
				creditMap.put("copy_code", copy_code);
				creditMap.put("vouch_id", vouch_id);
				creditMap.put("vouch_row", vouch_row);
				creditMap.put("subj_code", att.getCredit_subj_code1());
				creditMap.put("summary", att.getSummary());
				creditMap.put("debit", 0);
				creditMap.put("credit", money);
				creditMap.put("debit_w", 0);
				creditMap.put("credit_w", 0);
				detailList.add(creditMap);
				vouch_row += 1;  //分录行数加1
			}else if(template_type_code != null && "Z00907".equals(template_type_code)){//新旧转换盈余弥补亏损
				//获取明细科目年末余额
				List<Map<String, Object>> tmpList = JsonListMapUtil.toListMapLower(accBalanceAllocationMapper.querySubjEndMoneyList(entityMap));
				
				if(tmpList == null || tmpList.size() == 0){
					return "{\"error\":\"新旧转换盈余科目为空，请维护后再生成！\"}";
				}
				Double end_os = 0.0;
				for(Map<String, Object> tmpMap : tmpList){
					end_os = Double.parseDouble(tmpMap.get("end_os").toString());
					if(end_os == null || end_os == 0){
						continue;
					}
					/***********************借方分录***************************/
					debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", tmpMap.get("subj_code"));
					debitMap.put("summary", att.getSummary());
					debitMap.put("debit", end_os);
					debitMap.put("credit", 0);
					debitMap.put("debit_w", 0);
					debitMap.put("credit_w", 0);
					detailList.add(debitMap);
					money += end_os;
					vouch_row += 1;  //分录行数加1
				}
				
				if(money == null || money == 0){
					return "{\"error\":\"以前年度盈余调整科目【"+att.getDebit_subj_name1()+"】的余额等于0，不能生成新旧转换盈余弥补亏损凭证！\"}";
				}
				
				/***********************贷方分录***************************/
				if(att.getCredit_subj_code1() == null || "".equals(att.getCredit_subj_code1())){
					return "{\"error\":\"累计盈余科目为空，请维护后再生成！\"}";
				}
				creditMap = new HashMap<String, Object>();
				creditMap.put("group_id", group_id);
				creditMap.put("hos_id", hos_id); 
				creditMap.put("copy_code", copy_code);
				creditMap.put("vouch_id", vouch_id);
				creditMap.put("vouch_row", vouch_row);
				creditMap.put("subj_code", att.getCredit_subj_code1());
				creditMap.put("summary", att.getSummary());
				creditMap.put("debit", 0);
				creditMap.put("credit", money);
				creditMap.put("debit_w", 0);
				creditMap.put("credit_w", 0);
				detailList.add(creditMap);
				vouch_row += 1;  //分录行数加1
			}else if(template_type_code != null && "Z00908".equals(template_type_code)){//财政拨款收入
				Map<String, Object> accBudgTarget = accBudgTargetMapper.queryAccBudgTargetByYear(entityMap);
				
				if(accBudgTarget.get("state") == null || "1".equals(accBudgTarget.get("state").toString())){
					return "{\"state\":\"false\",\"error\":\"本年度预算指标已审核不能获取直接支付数！\"}";
				}
				//获取本年财政直接支付预算指标数
				Double dircet_target = Double.parseDouble(accBudgTarget.get("dircet_target").toString());
				//获取直接支付实际发生额
				Double dircet_money = Double.parseDouble(accBudgTarget.get("dircet_money").toString());
				//获取本年财政授权支付预算指标数
				Double grant_target = Double.parseDouble(accBudgTarget.get("grant_target").toString());
				//获取授权支付实际发生额
				Double grant_money = Double.parseDouble(accBudgTarget.get("grant_money").toString());
				
				if(dircet_target > dircet_money){
					/***********************借方分录-财政应返还额度--财政直接支付***************************/
					debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", att.getDebit_subj_code1());
					debitMap.put("summary", att.getSummary());
					debitMap.put("debit", dircet_target - dircet_money);
					debitMap.put("credit", 0);
					debitMap.put("debit_w", 0);
					debitMap.put("credit_w", 0);
					detailList.add(debitMap);
					vouch_row += 1;  //分录行数加1
				}
				if(grant_target > grant_money){
					/***********************借方分录-财政应返还额度--财政授权支付***************************/
					debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", att.getDebit_subj_code1());
					debitMap.put("summary", att.getSummary());
					debitMap.put("debit", dircet_target - dircet_money);
					debitMap.put("credit", 0);
					debitMap.put("debit_w", 0);
					debitMap.put("credit_w", 0);
					detailList.add(debitMap);
					vouch_row += 1;  //分录行数加1
				}
				/***********************贷方分录-财政拨款收入***************************/
				if(dircet_target > dircet_money || grant_target > grant_money){
					creditMap = new HashMap<String, Object>();
					creditMap.put("group_id", group_id);
					creditMap.put("hos_id", hos_id); 
					creditMap.put("copy_code", copy_code);
					creditMap.put("vouch_id", vouch_id);
					creditMap.put("vouch_row", vouch_row);
					creditMap.put("subj_code", att.getCredit_subj_code1());
					creditMap.put("summary", att.getSummary());
					creditMap.put("debit", 0);
					money = (dircet_target > dircet_money ? dircet_target - dircet_money : 0) + (grant_target > grant_money ? grant_target - grant_money : 0);
					creditMap.put("credit", money);
					creditMap.put("debit_w", 0);
					creditMap.put("credit_w", 0);
					detailList.add(creditMap);
				}else{
					return "{\"state\":\"false\",\"error\":\"本年度预算指标不大于实际支付发生额不能生成凭证！\"}";
				}
			}
			
			if(detailList == null || detailList.size() == 0){
				return "{\"state\":\"false\",\"error\":\"模板没有维护结余科目或结余科目没有发生数据！\"}";
			}
			
			//日志信息
			logMap.put("group_id", group_id);
			logMap.put("hos_id", hos_id);
			logMap.put("copy_code", copy_code);
			logMap.put("vouch_id", vouch_id);
			logMap.put("business_no", att.getTemplate_id());
			logMap.put("busi_type_code", att.getTemplate_type_code());
			logMap.put("template_code", att.getTemplate_id());
			logMap.put("create_date", date);
			
			//凭证临时表--主表
			superVouchMapper.saveAutoVouch(vouchMap);
			//凭证临时表--明细表
			superVouchMapper.saveAutoVouchDetail(detailList);
			//凭证临时表--凭证日志
			accTermendTemplateMapper.saveAutoVouchLogTemp(logMap);
			
			return "{\"state\":\"true\",\"vouch_id\":\""+vouch_id+"\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"{生成失败！\"}");
		}
	}
}
