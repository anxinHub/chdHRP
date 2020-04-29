/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.termend.monthend;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.termend.monthend.AccCostExtractMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.service.termend.monthend.AccCostExtractService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/**
 * @Title. @Description. 支出费用提取<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accCostExtractService")
public class AccCostExtractServiceImpl implements AccCostExtractService {

	private static Logger logger = Logger.getLogger(AccCostExtractServiceImpl.class);
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	@Resource(name = "accCostExtractMapper")
	private final AccCostExtractMapper accCostExtractMapper = null;
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	

	/**
	 * 1.校验设置的支出科目本期是否有发生额。
	 * 2.校验本期支出科目发生额*提取比例是否大于零。
	 * 3.凭证借方分录金额 = 本期收入科目发生额 * 提取比例 = 贷方分录金额
	 * 4.借方辅助核算金额 = 借方分录金额 * (科室收入 / ∑(科室总收入))
	 * 5.调整最后一条分录金额为：借方分录金额 - ∑(其他辅助核算金额)
	 */
	@Override
	public String addAccCostExtractVouch(Map<String, Object> entityMap) throws DataAccessException {
		//存放凭证主表信息
		Map<String, Object> vouchMap = new HashMap<String, Object>();
		//存放凭证分录信息
		List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
		//存放辅助核算信息
		List<Map<String, Object>> vouchCheckList = new ArrayList<Map<String,Object>>();
		//存放日志信息
		Map<String, Object> logMap = new HashMap<String, Object>();
		
		try{
			//获取凭证模板信息
			AccTermendTemplate att = accTermendTemplateMapper.queryAccTermendTemplateByCode(entityMap);
			if(att.getVouch_type_code() == null || "".equals(att.getVouch_type_code())){
				return "{\"error\":\"模板"+att.getTemplate_name()+"还不存在，请先进行设置！\"}";
			}
			//如果本期间已生成凭证则不允许生成凭证
			entityMap.put("template_type_code", att.getTemplate_type_code());
			entityMap.put("template_id", att.getTemplate_id());
			int flag = accTermendTemplateMapper.existsAccVouchByTemplate(entityMap);
			if(flag > 0){
				return "{\"error\":\"本期间已生成凭证，不能重复生成!\"}";
			}
			//医疗支出
			entityMap.put("subj_type_code", "5001");
			Double money_sum_5001 = accCostExtractMapper.querySumCostSubjMoneyByType(entityMap);  //总额
			//管理支出
			entityMap.put("subj_type_code", "5101");
			Double money_sum_5101 = accCostExtractMapper.querySumCostSubjMoneyByType(entityMap);  //总额
			
			if((money_sum_5001 == null || money_sum_5001 == 0) && (money_sum_5101 == null || money_sum_5101 == 0)){
				return "{\"error\":\"支出费用设置支出科目所选期间发生额为零，不能生成凭证!\"}";
			}
			//提取医疗支出金额
			Double detail_money_5001 = NumberUtil.numberToRound(money_sum_5001 * att.getRate() / 100);
			//提取管理支出金额
			Double detail_money_5101 = NumberUtil.numberToRound(money_sum_5101 * att.getRate() / 100);
			
			if((detail_money_5001 == null || detail_money_5001 == 0) && (detail_money_5101 == null || detail_money_5101 == 0)){
				return "\"error\":\"支出费用设置支出科目提取金额为零，不能生成凭证!\"";
			}

			Date date = new Date();
			Long group_id = att.getGroup_id();
			Long hos_id = att.getHos_id();
			String copy_code = att.getCopy_code();
			String acc_year = att.getAcc_year();
			String template_type_code = att.getTemplate_type_code();
			int vouch_row = 1, check_row = 1;
			Map<String, String> checkItem = new HashMap<String, String>();
			String column = "", column_value = "";
			
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
			vouchMap.put("busi_type_code", template_type_code);  //业务类型
			vouchMap.put("create_date", date);  //制单日期
			
			/****************************组装凭证借方5001分录信息***************************************/
			Map<String, Object> queryCheckMap = new HashMap<String, Object>();
			queryCheckMap.put("group_id", group_id);
			queryCheckMap.put("hos_id", hos_id);
			queryCheckMap.put("copy_code", copy_code);
			queryCheckMap.put("acc_year", acc_year);
			String checkColumn = "";
			if(detail_money_5001 > 0){
				Map<String, Object> detailDebitMap_5001 = new HashMap<String, Object>();
				detailDebitMap_5001.put("group_id", group_id);
				detailDebitMap_5001.put("hos_id", hos_id); 
				detailDebitMap_5001.put("copy_code", copy_code);
				detailDebitMap_5001.put("vouch_id", vouch_id);
				detailDebitMap_5001.put("vouch_row", vouch_row);
				detailDebitMap_5001.put("subj_code", att.getDebit_subj_code1());  //借方5001科目编码
				detailDebitMap_5001.put("summary", att.getSummary());
				detailDebitMap_5001.put("debit", detail_money_5001);
				detailDebitMap_5001.put("credit", 0);
				detailDebitMap_5001.put("debit_w", 0);
				detailDebitMap_5001.put("credit_w", 0);
				detailList.add(detailDebitMap_5001);

				/****************************组装借方5001辅助核算信息***************************************/
				//查询科目是否包含部门辅助核算
				queryCheckMap.put("subj_code", att.getDebit_subj_code1());
				queryCheckMap.put("table_code", "HOS_DEPT_DICT");
				checkColumn = accTermendTemplateMapper.querySubjCheckColumnByTableCode(queryCheckMap);
				//判断是否包含部门辅助核算
				if(checkColumn != null && !"".equals(checkColumn)){
					checkColumn = checkColumn.toLowerCase();
					entityMap.put("checkColumn", checkColumn);
					entityMap.put("subj_type_code", "5001");
					List<Map<String, Object>> deptList_5001 = JsonListMapUtil.toListMapLower(accCostExtractMapper.queryDeptCostMoneyByType(entityMap));  //部门辅助核算
					if(deptList_5001.size() > 0){
						Double checkMoney = 0.0;  //辅助核算金额
						Double checkMoneySum = 0.0;  //辅助核算总金额
						int index = 0; //循环次数
						int maxIndex = deptList_5001.size() - 1;  //循环总次数
						//准备循环科室组装辅助核算数据
						for(Map<String, Object> map : deptList_5001){
							Map<String, Object> checkMap = new HashMap<String, Object>();
							checkMap.put("group_id", group_id);
							checkMap.put("hos_id", hos_id); 
							checkMap.put("copy_code", copy_code);
							checkMap.put("vouch_id", vouch_id);
							checkMap.put("vouch_row", vouch_row);
							checkMap.put("vouch_check_id", check_row);
							checkMap.put("subj_code", att.getDebit_subj_code1());  //科目编码
							checkMap.put("summary", att.getSummary());  //摘要
							checkMap.put("credit", 0);
							checkMap.put("debit_w", 0);
							checkMap.put("credit_w", 0);
							if(index == maxIndex){
								//最后一个辅助核算的金额应为：分录金额 - 辅助核算总金额(除了该辅助核算)
								checkMap.put("debit", detail_money_5001 - checkMoneySum);
							}else{
								//正常辅助核算金额为：分录金额 * 科室比重(即：科室比例 / 科室总比例)
								checkMoney = NumberUtil.numberToRound(detail_money_5001 * (Double.valueOf(String.valueOf(map.get("dept_money"))) / money_sum_5001));
								checkMoneySum += checkMoney;
								checkMap.put("debit", checkMoney);
							}
							checkMap.put(checkColumn+"_id", map.get(checkColumn+"_id"));  //科室ID
							checkMap.put(checkColumn+"_no", map.get(checkColumn+"_no"));  //科室NO
							if(checkMoney != 0){
								vouchCheckList.add(checkMap);
								check_row += 1;
								checkItem.put(checkColumn+"_id", checkColumn+"_id");
								checkItem.put(checkColumn+"_no", checkColumn+"_no");
							}
							index ++;  //循环次数+1
						}
					}
				}
				vouch_row += 1;  //分录行数加1
			}
			
			/****************************组装凭证借方5101分录信息***************************************/
			if(detail_money_5101 > 0){
				Map<String, Object> detailDebitMap_5101 = new HashMap<String, Object>();
				detailDebitMap_5101.put("group_id", group_id);
				detailDebitMap_5101.put("hos_id", hos_id); 
				detailDebitMap_5101.put("copy_code", copy_code);
				detailDebitMap_5101.put("vouch_id", vouch_id);
				detailDebitMap_5101.put("vouch_row", vouch_row);
				detailDebitMap_5101.put("subj_code", att.getDebit_subj_code2());  //借方5001科目编码
				detailDebitMap_5101.put("summary", att.getSummary());
				detailDebitMap_5101.put("debit", detail_money_5101);
				detailDebitMap_5101.put("credit", 0);
				detailDebitMap_5101.put("debit_w", 0);
				detailDebitMap_5101.put("credit_w", 0);
				
				detailList.add(detailDebitMap_5101);
	
				/****************************组装借方5101辅助核算信息***************************************/
				//查询科目是否包含部门辅助核算
				queryCheckMap.put("subj_code", att.getDebit_subj_code2());
				queryCheckMap.put("table_code", "HOS_DEPT_DICT");
				checkColumn = accTermendTemplateMapper.querySubjCheckColumnByTableCode(queryCheckMap);
				//判断是否包含部门辅助核算
				if(checkColumn != null && !"".equals(checkColumn)){
					checkColumn = checkColumn.toLowerCase();
					entityMap.put("checkColumn", checkColumn);
					entityMap.put("subj_type_code", "5101");
					List<Map<String, Object>> deptList_5101 = JsonListMapUtil.toListMapLower(accCostExtractMapper.queryDeptCostMoneyByType(entityMap));  //部门辅助核算
					if(deptList_5101.size() > 0){
						Double checkMoney = 0.0;  //辅助核算金额
						Double checkMoneySum = 0.0;  //辅助核算总金额
						int index = 0; //循环次数
						int maxIndex = deptList_5101.size() - 1;  //循环总次数
						//准备循环科室组装辅助核算数据
						for(Map<String, Object> map : deptList_5101){
							Map<String, Object> checkMap = new HashMap<String, Object>();
							checkMap.put("group_id", group_id);
							checkMap.put("hos_id", hos_id); 
							checkMap.put("copy_code", copy_code);
							checkMap.put("vouch_id", vouch_id);
							checkMap.put("vouch_row", vouch_row);
							checkMap.put("vouch_check_id", check_row);
							checkMap.put("subj_code", att.getDebit_subj_code2());  //科目编码
							checkMap.put("summary", att.getSummary());  //摘要
							if(index == maxIndex){
								//最后一个辅助核算的金额应为：分录金额 - 辅助核算总金额(除了该辅助核算)
								checkMap.put("debit", detail_money_5101 - checkMoneySum);
							}else{
								//正常辅助核算金额为：分录金额 * 科室比重(即：科室比例 / 科室总比例)
								checkMoney = NumberUtil.numberToRound(detail_money_5101 * (Double.valueOf(String.valueOf(map.get("dept_money"))) / money_sum_5101));
								checkMoneySum += checkMoney;
								checkMap.put("debit", checkMoney);
							}
							checkMap.put("credit", 0);
							checkMap.put("debit_w", 0);
							checkMap.put("credit_w", 0);
							
							checkMap.put(checkColumn+"_id", map.get(checkColumn+"_id"));  //科室ID
							checkMap.put(checkColumn+"_no", map.get(checkColumn+"_no"));  //科室NO
	
							if(checkMoney != 0){
								vouchCheckList.add(checkMap);
								check_row += 1;
								checkItem.put(checkColumn+"_id", checkColumn+"_id");
								checkItem.put(checkColumn+"_no", checkColumn+"_no");
							}
							index ++;  //循环次数+1
						}
					}
				}
				vouch_row += 1;  //分录行数加1
			}
			
			/****************************组装凭证贷方分录信息***************************************/
			Map<String, Object> detailCreditMap = new HashMap<String, Object>();
			detailCreditMap.put("group_id", group_id);
			detailCreditMap.put("hos_id", hos_id); 
			detailCreditMap.put("copy_code", copy_code);
			detailCreditMap.put("vouch_id", vouch_id);
			detailCreditMap.put("vouch_row", vouch_row);
			detailCreditMap.put("subj_code", att.getCredit_subj_code1());
			detailCreditMap.put("summary", att.getSummary());
			detailCreditMap.put("debit", 0);
			Double credit_money = detail_money_5001 + detail_money_5101;
			detailCreditMap.put("credit", credit_money);
			detailCreditMap.put("debit_w", 0);
			detailCreditMap.put("credit_w", 0);
			
			detailList.add(detailCreditMap);

			/****************************组装贷方辅助核算信息***************************************/
			//查询科目是否包含部门辅助核算
			queryCheckMap.put("subj_code", att.getCredit_subj_code1());
			queryCheckMap.put("table_code", "HOS_DEPT_DICT");
			checkColumn = accTermendTemplateMapper.querySubjCheckColumnByTableCode(queryCheckMap);
			//判断是否包含部门辅助核算
			if(checkColumn != null && !"".equals(checkColumn)){
				checkColumn = checkColumn.toLowerCase();
				//获取所选科目所有的部门支出金额
				entityMap.remove("subj_type_code");
				List<Map<String, Object>> creditDeptList = JsonListMapUtil.toListMapLower(accCostExtractMapper.queryDeptCostMoneyByType(entityMap));  //部门辅助核算
				if(creditDeptList.size() > 0){
					Double money_sum = money_sum_5001 + money_sum_5101;
					Double checkMoney = 0.0;  //辅助核算金额
					Double checkMoneySum = 0.0;  //辅助核算总金额
					int index = 0; //循环次数
					int maxIndex = creditDeptList.size() - 1;  //循环总次数
					//准备循环科室组装辅助核算数据
					for(Map<String, Object> map : creditDeptList){
						Map<String, Object> checkMap = new HashMap<String, Object>();
						checkMap.put("group_id", group_id);
						checkMap.put("hos_id", hos_id); 
						checkMap.put("copy_code", copy_code);
						checkMap.put("vouch_id", vouch_id);
						checkMap.put("vouch_row", vouch_row);
						checkMap.put("vouch_check_id", check_row);
						checkMap.put("subj_code", att.getDebit_subj_code2());  //科目编码
						checkMap.put("summary", att.getSummary());  //摘要
						if(index == maxIndex){
							//最后一个辅助核算的金额应为：分录金额 - 辅助核算总金额(除了该辅助核算)
							checkMap.put("credit", credit_money - checkMoneySum);
						}else{
							//正常辅助核算金额为：分录金额 * 科室比重(即：科室比例 / 科室总比例)
							checkMoney = NumberUtil.numberToRound(credit_money * (Double.valueOf(String.valueOf(map.get("dept_money"))) / money_sum));
							checkMoneySum += checkMoney;
							checkMap.put("credit", checkMoney);
						}
						checkMap.put("debit", 0);
						checkMap.put("debit_w", 0);
						checkMap.put("credit_w", 0);
						
						checkMap.put(checkColumn+"_id", map.get(checkColumn+"_id"));  //科室ID
						checkMap.put(checkColumn+"_no", map.get(checkColumn+"_no"));  //科室NO
						
						if(checkMoney != 0){
							vouchCheckList.add(checkMap);
							check_row += 1;
							checkItem.put(checkColumn+"_id", checkColumn+"_id");
							checkItem.put(checkColumn+"_no", checkColumn+"_no");
						}
						index ++;  //循环次数+1
					}
				}
			}
			vouch_row += 1;  //分录行数加1
			
			boolean is_frist = true;
			for(Map<String, Object> map : vouchCheckList){
				for (Map.Entry<String, String> entry : checkItem.entrySet()) {
					if(is_frist){
						column += ("," + entry.getKey());
						column_value += (", #{item." + entry.getKey() + ",jdbcType=INTEGER}");
					}
					if(!map.containsKey(entry.getKey())){
						map.put(entry.getKey(), null);
					}
				}
				is_frist = false;
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
			
			//操作数据库
			superVouchMapper.saveAutoVouch(vouchMap);
			superVouchMapper.saveAutoVouchDetail(detailList);
			if(vouchCheckList.size() > 0){
				superVouchMapper.saveAccAutoCheckByTermend(column, column_value, vouchCheckList);
			}
			accTermendTemplateMapper.saveAutoVouchLogTemp(logMap);
			
			return "{\"state\":\"true\",\"vouch_id\":\""+vouch_id+"\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"{生成失败！\"}");
		}
	}
}
