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
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.termend.yearend.AccFiscalExpenditureMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.service.termend.yearend.AccFiscalExpenditureService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/**
 * @Title. @Description. 财政基本补助支出结转<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accFiscalExpenditureService")
public class AccFiscalExpenditureServiceImpl implements AccFiscalExpenditureService {

	private static Logger logger = Logger.getLogger(AccFiscalExpenditureServiceImpl.class);

	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	@Resource(name = "accFiscalExpenditureMapper")
	private final AccFiscalExpenditureMapper accFiscalExpenditureMapper = null;
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;

	/**
	 * 1.校验财政收入金额与财政支出金额相等则不能生成凭证。
	 * 2.凭证借方分录金额 = 财政收入金额 - 财政支出金额
	 * 3.凭证贷方分录金额 = 财政收入金额 - 财政支出金额
	 */
	@Override
	public String addAccFiscalExpenditureVouch(Map<String, Object> entityMap) throws DataAccessException {
		//存放凭证主表信息
		Map<String, Object> vouchMap = new HashMap<String, Object>();
		//存放凭证分录信息
		List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
		//存放日志信息
		Map<String, Object> logMap = new HashMap<String, Object>();
		try{
			if(entityMap.get("acc_month") == null && !"12".equals(entityMap.get("acc_money"))){
				return "{\"error\":\"12月才能生成财政基本补助支出结转凭证！\"}";
			}
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
			//如果存在未记账凭证则允许生成凭证
			flag = accTermendTemplateMapper.queryAccUnAccountVouch(entityMap);
			if(flag > 0){
				return "{\"error\":\"本期间有未记账凭证，不能生成财政基本补助支出结转凭证！\"}";
			}
			//本年度财政收入总金额
			Double creditMoney = accFiscalExpenditureMapper.querySumCreditMoney(entityMap);
			//本年度财政支出总金额
			Double debitMoney = accFiscalExpenditureMapper.querySumDebitMoney(entityMap);
			if(creditMoney == null || debitMoney == null || creditMoney - debitMoney == 0){
				return "{\"error\":\"本年度财政收支转账为0，不能生成财政基本补助支出结转凭证！\"}";
			}

			Date date = new Date();
			Long group_id = att.getGroup_id();
			Long hos_id = att.getHos_id();
			String copy_code = att.getCopy_code();
			String acc_year = att.getAcc_year();
			String template_type_code = att.getTemplate_type_code();
			int vouch_row = 1;
			
			/*************************组装凭证主表信息*******************************/
			String vouch_id = UUIDLong.absStringUUID();
			vouchMap.put("group_id", group_id);
			vouchMap.put("hos_id", hos_id); 
			vouchMap.put("copy_code", copy_code);
			vouchMap.put("acc_year", acc_year);
			vouchMap.put("acc_month", entityMap.get("acc_month"));
			vouchMap.put("vouch_id", vouch_id);
			vouchMap.put("vouch_type_code", att.getVouch_type_code());  //凭证类型
			vouchMap.put("vouch_date", accTermendTemplateMapper.queryAccVouchDateByYearMonth(vouchMap));  //凭证日期
			vouchMap.put("vouch_att_num", 0);  //附件数量
			vouchMap.put("busi_type_code", template_type_code);  //业务类型
			vouchMap.put("create_date", date);  //制单日期
			
			/****************************组装凭证借方分录信息***************************************/
			Map<String, Object> debitMap = new HashMap<String, Object>();
			debitMap.put("group_id", group_id);
			debitMap.put("hos_id", hos_id); 
			debitMap.put("copy_code", copy_code);
			debitMap.put("vouch_id", vouch_id);
			debitMap.put("vouch_row", vouch_row);
			debitMap.put("subj_code", att.getDebit_subj_code1());
			debitMap.put("summary", att.getSummary());
			debitMap.put("debit", debitMoney);
			debitMap.put("credit", 0);
			debitMap.put("debit_w", 0);
			debitMap.put("credit_w", 0);

			//存放借方分录
			detailList.add(debitMap);
			
			vouch_row += 1;  //分录行数加1
			
			/****************************组装凭证贷方分录信息***************************************/
			debitMap = new HashMap<String, Object>();
			debitMap.put("group_id", group_id);
			debitMap.put("hos_id", hos_id); 
			debitMap.put("copy_code", copy_code);
			debitMap.put("vouch_id", vouch_id);
			debitMap.put("vouch_row", vouch_row);
			debitMap.put("subj_code", att.getCredit_subj_code2());
			debitMap.put("summary", att.getSummary());
			debitMap.put("debit", 0);
			debitMap.put("credit", creditMoney);

			//存放贷方分录
			detailList.add(debitMap);
			
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
			accTermendTemplateMapper.saveAutoVouchLogTemp(logMap);
			
			return "{\"state\":\"true\",\"vouch_id\":\""+vouch_id+"\"}";
			
		}catch(Exception e){
			logger.error(e.getMessage());
			
			throw new SysException("{\"error\":\"{生成失败！\"}");
		}
	}
}
