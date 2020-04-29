/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.termend.monthend;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateDeptMapper;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.termend.monthend.AccFundExtractMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.entity.AccTermendTemplateDept;
import com.chd.hrp.acc.service.termend.monthend.AccFundExtractService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/**
 * @Title. @Description. 医疗风险基金提取<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accFundExtractService")
public class AccFundExtractServiceImpl implements AccFundExtractService {

	private static Logger logger = Logger.getLogger(AccFundExtractServiceImpl.class);

	@Resource(name = "accFundExtractMapper")
	private final AccFundExtractMapper accFundExtractMapper = null;
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	@Resource(name = "accTermendTemplateDeptMapper")
	private final AccTermendTemplateDeptMapper accTermendTemplateDeptMapper = null;
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;

	/**
	 * 1.校验设置的收入科目本期是否有发生额。 2.校验本期收入科目发生额*提取比例是否大于零。 3.凭证借方分录金额 = 本期收入科目发生额 * 提取比例 =
	 * 贷方分录金额 4.借方辅助核算金额 = 借方分录金额 * (科室比例 / ∑(科室比例)) 5.调整最后一条分录金额为：借方分录金额 -
	 * ∑(其他辅助核算金额)
	 */
	@Override
	public String addAccFundExtractVouch(Map<String, Object> entityMap) throws DataAccessException {
		// 存放凭证主表信息
		Map<String, Object> vouchMap = new HashMap<String, Object>();
		// 存放凭证分录信息
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
		// 存放辅助核算信息
		List<Map<String, Object>> vouchCheckList = new ArrayList<Map<String, Object>>();
		// 存放日志信息
		Map<String, Object> logMap = new HashMap<String, Object>();
		try {
			// 获取凭证模板信息
			AccTermendTemplate att = accTermendTemplateMapper.queryAccTermendTemplateByCode(entityMap);
			if (att.getVouch_type_code() == null || "".equals(att.getVouch_type_code())) {
				return "{\"error\":\"模板" + att.getTemplate_name() + "还不存在，请先进行设置！\"}";
			}
			// 如果本期间已生成凭证则不允许生成凭证
			entityMap.put("template_type_code", att.getTemplate_type_code());
			entityMap.put("template_id", att.getTemplate_id());
			int flag = accTermendTemplateMapper.existsAccVouchByTemplate(entityMap);
			if (flag > 0) {
				return "{\"error\":\"本期间已生成凭证，不能重复生成!\"}";
			}
			// 如果存在未记账凭证则不允许生成凭证
			flag = accTermendTemplateMapper.queryAccUnAccountVouch(entityMap);
			if (flag > 0) {
				return "{\"error\":\"本期间有未记账凭证，不能生成凭证!\"}";
			}
			Double money_sum = accFundExtractMapper.querySumIncomSubjMoney(entityMap);
			if (money_sum == null || money_sum == 0) {
				return "{\"error\":\"医疗风险基金设置收入科目所选期间发生额为零，不能生成凭证!\"}";
			}
			Double detail_money = NumberUtil.numberToRound(money_sum * att.getRate() / 100);
			if (detail_money == null || detail_money == 0) {
				return "{\"error\":\"医疗风险基金设置收入科目提取金额为零，不能生成凭证!\"}";
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

			/************************* 组装凭证主表信息 *******************************/
			String vouch_id = UUIDLong.absStringUUID();
			vouchMap.put("group_id", group_id);
			vouchMap.put("hos_id", hos_id);
			vouchMap.put("copy_code", copy_code);
			vouchMap.put("acc_year", acc_year);
			vouchMap.put("acc_month", entityMap.get("acc_month"));
			vouchMap.put("vouch_id", vouch_id);
			vouchMap.put("vouch_type_code", att.getVouch_type_code()); // 凭证类型
			vouchMap.put("vouch_date", accTermendTemplateMapper.queryAccVouchDateByYearMonth(vouchMap)); // 凭证日期
			vouchMap.put("vouch_att_num", 0); // 附件数量
			vouchMap.put("busi_type_code", template_type_code); // 业务类型
			vouchMap.put("create_date", date); // 制单日期

			/****************************
			 * 组装凭证借方分录信息
			 ***************************************/
			Map<String, Object> detailDebitMap = new HashMap<String, Object>();
			detailDebitMap.put("group_id", group_id);
			detailDebitMap.put("hos_id", hos_id);
			detailDebitMap.put("copy_code", copy_code);
			detailDebitMap.put("vouch_id", vouch_id);
			detailDebitMap.put("vouch_row", vouch_row);
			detailDebitMap.put("subj_code", att.getDebit_subj_code1());
			detailDebitMap.put("summary", att.getSummary());
			detailDebitMap.put("debit", detail_money);
			detailDebitMap.put("credit", 0);
			detailDebitMap.put("debit_w", 0);
			detailDebitMap.put("credit_w", 0);
			// 存放借方分录
			detailList.add(detailDebitMap);

			/****************************
			 * 组装借方辅助核算信息
			 ***************************************/
			// 提取科室比例
			List<AccTermendTemplateDept> attdList = accTermendTemplateDeptMapper
					.queryAccTermendTemplateDeptByCode(entityMap);
			// 提取科室总比例
			Double sumRate = accTermendTemplateDeptMapper.queryDeptSumByCode(entityMap);
			if (attdList.size() > 0) {
				// 查询科目是否包含部门辅助核算
				Map<String, Object> queryCheckMap = new HashMap<String, Object>();
				queryCheckMap.put("group_id", group_id);
				queryCheckMap.put("hos_id", hos_id);
				queryCheckMap.put("copy_code", copy_code);
				queryCheckMap.put("acc_year", acc_year);
				queryCheckMap.put("subj_code", att.getDebit_subj_code1());
				queryCheckMap.put("table_code", "HOS_DEPT_DICT");
				String checkColumn = accTermendTemplateMapper.querySubjCheckColumnByTableCode(queryCheckMap);
				// 判断是否包含部门辅助核算
				if (checkColumn != null && !"".equals(checkColumn)) {
					Double checkMoney = 0.0; // 辅助核算金额
					Double checkMoneySum = 0.0; // 辅助核算总金额
					int index = 0; // 循环次数
					int maxIndex = attdList.size() - 1; // 循环总次数
					// 准备循环科室组装辅助核算数据
					for (AccTermendTemplateDept attd : attdList) {
						Map<String, Object> checkMap = new HashMap<String, Object>();
						checkMap.put("group_id", group_id);
						checkMap.put("hos_id", hos_id);
						checkMap.put("copy_code", copy_code);
						checkMap.put("vouch_id", vouch_id);
						checkMap.put("vouch_row", vouch_row);
						checkMap.put("vouch_check_id", check_row);
						checkMap.put("subj_code", att.getDebit_subj_code1()); // 科目编码
						checkMap.put("summary", att.getSummary()); // 摘要
						if (index == maxIndex) {
							// 最后一个辅助核算的金额应为：分录金额 - 辅助核算总金额(除了该辅助核算)
							checkMap.put("debit", detail_money - checkMoneySum);
						} else {
							// 正常辅助核算金额为：分录金额 * 科室比重(即：科室比例 / 科室总比例)
							checkMoney = NumberUtil.numberToRound(detail_money * (attd.getRate() / sumRate));
							checkMoneySum += checkMoney;
							checkMap.put("debit", checkMoney);
						}
						checkMap.put("credit", 0);
						checkMap.put("debit_w", 0);
						checkMap.put("credit_w", 0);

						checkMap.put(checkColumn + "_id", attd.getDept_id()); // 科室ID
						checkMap.put(checkColumn + "_no", attd.getDept_no()); // 科室NO

						if (checkMoney != 0) {
							vouchCheckList.add(checkMap);
							check_row += 1;
							checkItem.put(checkColumn + "_id", checkColumn + "_id");
							checkItem.put(checkColumn + "_no", checkColumn + "_no");
						}
						index++; // 循环次数+1
					}
				}
			}
			vouch_row += 1; // 分录行数加1

			/****************************
			 * 组装凭证贷方分录信息
			 ***************************************/
			Map<String, Object> detailCreditMap = new HashMap<String, Object>();
			detailCreditMap.put("group_id", group_id);
			detailCreditMap.put("hos_id", hos_id);
			detailCreditMap.put("copy_code", copy_code);
			detailCreditMap.put("vouch_id", vouch_id);
			detailCreditMap.put("vouch_row", vouch_row);
			detailCreditMap.put("subj_code", att.getCredit_subj_code1());
			detailCreditMap.put("summary", att.getSummary());
			detailCreditMap.put("debit", 0);
			detailCreditMap.put("credit", detail_money);
			detailCreditMap.put("debit_w", 0);
			detailCreditMap.put("credit_w", 0);
			// 存放贷方分录
			detailList.add(detailCreditMap);

			/****************************
			 * 组装贷方辅助核算信息
			 ***************************************/
			if (attdList.size() > 0) {
				// 查询科目是否包含部门辅助核算
				Map<String, Object> queryCheckMap = new HashMap<String, Object>();
				queryCheckMap.put("group_id", att.getGroup_id());
				queryCheckMap.put("hos_id", att.getHos_id());
				queryCheckMap.put("copy_code", att.getCopy_code());
				queryCheckMap.put("acc_year", att.getAcc_year());
				queryCheckMap.put("subj_code", att.getCredit_subj_code1());
				queryCheckMap.put("table_code", "HOS_DEPT_DICT");
				String checkColumn = accTermendTemplateMapper.querySubjCheckColumnByTableCode(queryCheckMap);
				// 判断是否包含部门辅助核算
				if (checkColumn != null && !"".equals(checkColumn)) {
					Double checkMoney = 0.0; // 辅助核算金额
					Double checkMoneySum = 0.0; // 辅助核算总金额
					int index = 0; // 循环次数
					int maxIndex = attdList.size() - 1; // 循环总次数
					// 准备循环科室组装辅助核算数据
					for (AccTermendTemplateDept attd : attdList) {
						Map<String, Object> checkMap = new HashMap<String, Object>();
						checkMap.put("group_id", group_id);
						checkMap.put("hos_id", hos_id);
						checkMap.put("copy_code", copy_code);
						checkMap.put("vouch_id", vouch_id);
						checkMap.put("vouch_row", vouch_row);
						checkMap.put("vouch_check_id", check_row);
						checkMap.put("subj_code", att.getCredit_subj_code1()); // 科目编码
						checkMap.put("summary", att.getSummary()); // 摘要
						if (index == maxIndex) {
							// 最后一个辅助核算的金额应为：分录金额 - 辅助核算总金额(除了该辅助核算)
							checkMap.put("credit", detail_money - checkMoneySum);
						} else {
							// 正常辅助核算金额为：分录金额 * 科室比重(即：科室比例 / 科室总比例)
							checkMoney = NumberUtil.numberToRound(detail_money * (attd.getRate() / sumRate));
							checkMoneySum += checkMoney;
							checkMap.put("credit", checkMoney);
						}
						checkMap.put("debit", 0);
						checkMap.put("debit_w", 0);
						checkMap.put("credit_w", 0);

						checkMap.put(checkColumn + "_id", attd.getDept_id()); // 科室ID
						checkMap.put(checkColumn + "_no", attd.getDept_no()); // 科室NO

						if (checkMoney != 0) {
							vouchCheckList.add(checkMap);
							check_row += 1;
							checkItem.put(checkColumn + "_id", checkColumn + "_id");
							checkItem.put(checkColumn + "_no", checkColumn + "_no");
						}
						index++; // 循环次数+1
					}
				}
			}
			vouch_row += 1; // 分录行数加1

			boolean is_frist = true;
			for (Map<String, Object> map : vouchCheckList) {
				for (Map.Entry<String, String> entry : checkItem.entrySet()) {
					if (is_frist) {
						column += ("," + entry.getKey());
						column_value += (", #{item." + entry.getKey() + ",jdbcType=INTEGER}");
					}
					if (!map.containsKey(entry.getKey())) {
						map.put(entry.getKey(), null);
					}
				}
				is_frist = false;
			}

			// 日志信息
			logMap.put("group_id", group_id);
			logMap.put("hos_id", hos_id);
			logMap.put("copy_code", copy_code);
			logMap.put("vouch_id", vouch_id);
			logMap.put("business_no", att.getTemplate_id());
			logMap.put("busi_type_code", att.getTemplate_type_code());
			logMap.put("template_code", att.getTemplate_id());
			logMap.put("create_date", date);

			// 操作数据库
			superVouchMapper.saveAutoVouch(vouchMap);
			superVouchMapper.saveAutoVouchDetail(detailList);
			superVouchMapper.saveAccAutoCheckByTermend(column, column_value, vouchCheckList);
			accTermendTemplateMapper.saveAutoVouchLogTemp(logMap);

			return "{\"state\":\"true\",\"vouch_id\":\"" + vouch_id + "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"{生成失败！\"}");
		}
	}

	@Override
	public String queryAccFundExtractDept(Map<String, Object> entityMap) throws DataAccessException {
		List<AccTermendTemplateDept> list = accTermendTemplateDeptMapper.queryAccTermendTemplateDept(entityMap);
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 期末处理模板<BR>
	 *              保存科室比例
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String saveAccFundExtractDept(Map<String, Object> entityMap) throws DataAccessException {

		if (entityMap.get("deptData") == null) {
			return "{\"error\":\"无科室数据\"}";
		}
		try {
			// 保存明细数据
			JSONArray json = JSONArray.parseArray((String) entityMap.get("deptData"));
			List<Map<String, Object>> list_template_detail_batch = new ArrayList<Map<String, Object>>();
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				if (!"".equals(jsonObj.get("rate")) && !"0".equals(jsonObj.get("rate").toString())
						&& !"0.00".equals(jsonObj.get("rate").toString()) && jsonObj.get("rate") != null) {
					Map<String, Object> mapDetailVo = new HashMap<String, Object>();
					mapDetailVo.put("group_id", entityMap.get("group_id"));
					mapDetailVo.put("hos_id", entityMap.get("hos_id"));
					mapDetailVo.put("copy_code", entityMap.get("copy_code"));
					mapDetailVo.put("template_id", entityMap.get("template_id"));
					mapDetailVo.put("dept_id", jsonObj.get("dept_id"));
					mapDetailVo.put("rate", jsonObj.get("rate"));
					list_template_detail_batch.add(mapDetailVo);
				}
			}
			if (list_template_detail_batch.size() > 0) {
				// 删除模板明细数据
				accTermendTemplateDeptMapper.deleteAccTermendTemplateDeptForAdd(list_template_detail_batch);
				// 保存模板明细表
				accTermendTemplateDeptMapper.addAccTermendTemplateDept(list_template_detail_batch);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"保存失败！\"}");
			// return "{\"error\":\"保存失败 数据库异常 请联系管理员! 错误编码 saveAccFundExtractDept\"}";
		}
		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 期末处理模板<BR>
	 *              提取科室收入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String saveAccFundExtractGetDeptIncom(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String, Object>> list_template_dept_batch = new ArrayList<Map<String, Object>>();
			// 获取各科室总收入
			List<Map<String, Object>> listDept = accTermendTemplateDeptMapper
					.queryAccTermendTemplateDeptIncom(entityMap);
			// 获取所有科室总收入
			Double sumMoney = accTermendTemplateDeptMapper.queryAccTermendTemplateDeptIncomSum(entityMap);

			if (sumMoney != null && sumMoney != 0) {
				// 科室收入转换为比例
				double rate = 0;

				for (Map<String, Object> map : listDept) {
					Map<String, Object> utilMap = new HashMap<String, Object>();
					// 科室总收入/所有科室总收入
					rate = ((Double) (map.get("money") == null ? 0.0D : Double.valueOf(map.get("money").toString())))
							.doubleValue() / sumMoney.doubleValue();

					utilMap.put("group_id", entityMap.get("group_id"));
					utilMap.put("hos_id", entityMap.get("hos_id"));
					utilMap.put("copy_code", entityMap.get("copy_code"));
					utilMap.put("template_id", entityMap.get("template_id"));
					utilMap.put("dept_id", map.get("dept_id"));
					utilMap.put("rate", rate);
					list_template_dept_batch.add(utilMap);
				}
				// 删除老数据
				accTermendTemplateDeptMapper.deleteAccTermendTemplateDeptForImport(entityMap);
				// 插入数据
				accTermendTemplateDeptMapper.addAccTermendTemplateDept(list_template_dept_batch);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			} else {
				return "{\"error\":\"科室总收入为空,不能保存.\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败！\"}");
			// return "{\"error\":\"保存失败 数据库异常 请联系管理员! 错误编码 saveAccFundExtractDept\"}";
		}
	}

	@Override
	public String saveAccFundExtractGetDeptIncomAcc(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> list_template_dept_batch = new ArrayList<>();

			this.accTermendTemplateDeptMapper.deleteAccTermendTemplateDeptForImport(entityMap);

			List<Map<String, Object>> listDept = this.accTermendTemplateDeptMapper
					.queryAccTermendTemplateDeptIncomAcc(entityMap);

			Double sumMoney = this.accTermendTemplateDeptMapper.queryAccTermendTemplateDeptIncomSumAcc(entityMap)
					.doubleValue();
			if ((sumMoney != null) && (sumMoney.doubleValue() != 0.0D)) {
				double rate = 0.0D;
				for (Map<String, Object> map : listDept) {
					Map<String, Object> utilMap = new HashMap<>();

					rate = ((Double) (map.get("money") == null ? 0.0D : Double.valueOf(map.get("money").toString())))
							.doubleValue() / sumMoney.doubleValue();

					utilMap.put("group_id", entityMap.get("group_id"));
					utilMap.put("hos_id", entityMap.get("hos_id"));
					utilMap.put("copy_code", entityMap.get("copy_code"));
					utilMap.put("template_id", entityMap.get("template_id"));
					utilMap.put("dept_id", map.get("dept_id"));
					utilMap.put("rate", Double.valueOf(rate));
					list_template_dept_batch.add(utilMap);
				}
				this.accTermendTemplateDeptMapper.addAccTermendTemplateDept(list_template_dept_batch);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			}
			return "{\"error\":\"科室总收入为空，不能保存.\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
}
