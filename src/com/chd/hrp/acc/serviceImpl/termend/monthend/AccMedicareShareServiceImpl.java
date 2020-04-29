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
import com.chd.hrp.acc.dao.termend.monthend.AccMedicareShareMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.service.termend.monthend.AccMedicareShareService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/**
 * @Title. @Description. 医保结算差额分摊<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accMedicareShareService")
public class AccMedicareShareServiceImpl implements AccMedicareShareService {

	private static Logger logger = Logger.getLogger(AccMedicareShareServiceImpl.class);

	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	@Resource(name = "accMedicareShareMapper")
	private final AccMedicareShareMapper accMedicareShareMapper = null;
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	
	/**
	 * 1.本月含未记账凭证,不能进行医保结算差额分摊
	 * 2.提取医保结算差额为零，不能生成凭证!
	 * 3.医保结算转入的医疗收入科目本期发生额为零，不能生成凭证!
	 * 4.本凭证医保记贷方负数，医疗收入科目记借方正数
	 * 5.医疗收入科目金额为：医保结算总额 * (收入科目本月发生额 / 收入科目本月总金额)
	 * 6.各收入科目辅助核算金额：科目金额 * (辅助核算本月发生额 / 收入科目本月发生额)
	 */
	@Override
	public String addAccMedicareShareVouch(Map<String, Object> entityMap) throws DataAccessException {
		//存放凭证主表信息
		Map<String, Object> vouchMap = new HashMap<String, Object>();
		//存放凭证分录信息
		List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
		//存放辅助核算信息
		List<Map<String, Object>> vouchCheckList = new ArrayList<Map<String,Object>>();
		//存放日志信息
		Map<String, Object> logMap = new HashMap<String, Object>();
		
		try{
			//取凭证模板
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
			//如果存在未记账凭证则不允许生成凭证
			flag = accTermendTemplateMapper.queryAccUnAccountVouch(entityMap);
			if(flag > 0){
				return "{\"error\":\"本期间含未记账凭证,不能进行医保结算差额分摊!\"}";
			}
			
			//获取医保结算差额科目账本期发生金额
			Map<String, Object> entityMapMed = new HashMap<String, Object>();
			entityMapMed.putAll(entityMap);
			entityMapMed.put("detail_type", "debit");
			Double medSumMoney = accMedicareShareMapper.queryMedSubjSumMoneyByType(entityMapMed);
			if(medSumMoney == 0){
				return "{\"error\":\"提取医保结算差额为零，不能生成凭证!\"}";
			}
			
			//获取医疗收入科目账本期发生金额
			Map<String, Object> entityMapIncom = new HashMap<String, Object>();
			entityMapIncom.putAll(entityMap);
			entityMapIncom.put("detail_type", "credit");
			Double incomSumMoney = accMedicareShareMapper.queryMedSubjSumMoneyByType(entityMapIncom);
			if(incomSumMoney == 0){
				return "{\"error\":\"医保结算转入的医疗收入科目本期发生额为零，不能生成凭证!\"}";
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
			
			/****************************循环组装凭证医保结算差额分录信息***************************************/
			entityMapMed.put("detail_type", "debit");  //获取借方对应明细科目
			List<Map<String, Object>> medList = JsonListMapUtil.toListMapLower(accMedicareShareMapper.queryMedSubjCreditMoneyListByType(entityMapMed));
			for(Map<String, Object> map : medList){
				Map<String, Object> detailMap = new HashMap<String, Object>();
				detailMap.put("group_id", group_id);
				detailMap.put("hos_id", hos_id); 
				detailMap.put("copy_code", copy_code);
				detailMap.put("vouch_id", vouch_id);
				detailMap.put("vouch_row", vouch_row);
				detailMap.put("subj_code", map.get("subj_code"));
				detailMap.put("summary", att.getSummary());
				detailMap.put("debit", 0);  //分录借方金额
				detailMap.put("credit", -1 * Double.parseDouble(map.get("money").toString()));  //分录贷方金额
				detailMap.put("debit_w", 0);
				detailMap.put("credit_w", 0);
				
				detailList.add(detailMap);
				
				if(map.get("is_check") != null && "1".equals(map.get("is_check").toString())){
					/*************************组装辅助核算信息*************************************/
					//获取模板中支出收入科目辅助账本期发生金额
					entityMapMed.put("subj_code", map.get("subj_code"));
					//获取该科目的辅助核算项
					List<Map<String, Object>> sqlList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.querySubjCheckColumnBySubj(entityMapMed));
					
					StringBuffer columns = new StringBuffer();  //记录辅助核算列 用于查询
					String column_check = "";
					
					for(Map<String, Object> sqlMap : sqlList){
						column_check = sqlMap.get("column_check").toString().toLowerCase();
						//判断是否含变更信息
						if(sqlMap.get("is_sys") != null && "1".equals(sqlMap.get("is_sys").toString())){
							if(sqlMap.get("is_change") != null && "1".equals(sqlMap.get("is_change").toString())){
								//记录包含的辅助核算项
								if(!checkItem.containsKey(column_check + "_no")){
									checkItem.put(column_check + "_no", column_check + "_no");
								}
								columns.append(column_check).append("_no,");
							}
							//记录包含的辅助核算项
							if(!checkItem.containsKey(column_check + "_id")){
								checkItem.put(column_check + "_id", column_check + "_id");
							}
							columns.append(column_check).append("_id,");
						}else{
							//记录包含的辅助核算项
							if(!checkItem.containsKey(column_check)){
								checkItem.put(column_check.toString(), column_check.toString());
							}
							columns.append(column_check).append(",");
						}
					}
					if(columns.length() > 0){
						entityMap.put("columns", columns.substring(0, columns.length() - 1).toString());
						List<Map<String, Object>> tmpList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.queryCheckMoneyBySubjDire(entityMapMed));
						for(Map<String, Object> tmp : tmpList){
							tmp.put("group_id", group_id);
							tmp.put("hos_id", hos_id); 
							tmp.put("copy_code", copy_code);
							tmp.put("vouch_id", vouch_id);
							tmp.put("vouch_row", vouch_row);
							tmp.put("summary", att.getSummary());
							tmp.put("vouch_check_id", check_row);
							tmp.put("debit", -1 * Double.parseDouble(tmp.get("debit").toString()));
							tmp.put("credit", -1 * Double.parseDouble(tmp.get("credit").toString()));
							tmp.put("debit_w", -1 * Double.parseDouble(tmp.get("debit_w").toString()));
							tmp.put("credit_w", -1 * Double.parseDouble(tmp.get("credit_w").toString()));
							vouchCheckList.add(tmp);
							check_row += 1;
						}
					}
					/*//老自动凭证方式
					StringBuffer selectSql = new StringBuffer();  //查询字段
					StringBuffer joinSql = new StringBuffer();  //关联表
					StringBuffer groupSql = new StringBuffer();  //group列
					StringBuffer whereSql = new StringBuffer(); //where 条件
					int index = 1;
					String tab = "";
					
					for(Map<String, Object> sqlMap : sqlList){
						tab = "temp"+index;  //表别名
						joinSql.append("left join ").append(sqlMap.get("check_table")).append(" ").append(tab);
						joinSql.append(" on leder.group_id = ").append(tab).append(".group_id");
						joinSql.append(" and leder.hos_id = ").append(tab).append(".hos_id");
						
						if("ACC_CHECK_ITEM".equals(sqlMap.get("check_table").toString())){
							whereSql.append(" and (subj.check1="+tab+".check_type_id or subj.check2="+tab+".check_type_id or subj.check3="+tab+".check_type_id or subj.check4="+tab+".check_type_id) ");
						}
						
						//判断是否含变更信息
						if(sqlMap.get("is_sys") != null && "1".equals(sqlMap.get("is_sys").toString())){
							joinSql.append(" and leder.").append(sqlMap.get("column_check")).append("_id = ").append(tab).append(".").append(sqlMap.get("column_id"));
							if(sqlMap.get("is_change") != null && "1".equals(sqlMap.get("is_change").toString())){
								selectSql.append("leder.").append(sqlMap.get("column_check")).append("_id ||'@'|| ").append(sqlMap.get("column_check")).append("_no as ").append(sqlMap.get("column_check")).append(",");
								groupSql.append("leder.").append(sqlMap.get("column_check")).append("_id,leder.").append(sqlMap.get("column_check")).append("_no,");
								joinSql.append(" and leder.").append(sqlMap.get("column_check")).append("_no = ").append(tab).append(".").append(sqlMap.get("column_no"));
								//joinSql.append(" and ").append(tab).append(".is_stop = 0");
							}else{
								selectSql.append("leder.").append(sqlMap.get("column_check")).append("_id as ").append(sqlMap.get("column_check")).append(",");
								groupSql.append("leder.").append(sqlMap.get("column_check")).append("_id,");
							}
						}else{
							joinSql.append(" and leder.copy_code = ").append(tab).append(".copy_code");
							joinSql.append(" and leder.").append(sqlMap.get("column_check")).append(" = ").append(tab).append(".").append(sqlMap.get("column_id"));
							selectSql.append("leder.").append(sqlMap.get("column_check")).append(" as ").append(sqlMap.get("column_check")).append(",");
							groupSql.append("leder.").append(sqlMap.get("column_check")).append(",");
						}
						selectSql.append(tab).append(".").append(sqlMap.get("column_code")).append(" ||' '|| ").append(tab).append(".").append(sqlMap.get("column_name")).append(" as ").append(sqlMap.get("column_check")).append("_name,");
						groupSql.append(tab).append(".").append(sqlMap.get("column_code")).append(",").append(tab).append(".").append(sqlMap.get("column_name")).append(",");
						//循环次数加1
						index++;
					}
					if(selectSql.length() > 0 && joinSql.length() > 0 && groupSql.length() > 0){
						entityMapMed.put("selectSql", selectSql.substring(0, selectSql.length()-1).toString());
						entityMapMed.put("groupSql", groupSql.substring(0, groupSql.length()-1).toString());
						entityMapMed.put("joinSql", joinSql.toString());
						entityMap.put("whereSql", whereSql.toString());
						entityMapMed.put("summary", att.getSummary());
						List<Map<String, Object>> checkList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.queryCheckMoneyBySubjDireOld(entityMapMed));
						for(Map<String, Object> checkMap : checkList){
							checkMap.put("money", -1 * Double.parseDouble(checkMap.get("money").toString()));
							vouchCheckList.add(checkMap);
							check_row += 1;  //辅助核算行数加1
						}
					}
				*/
				}
				vouch_row += 1;  //分录行数加1
			}
			
			/****************************组装凭证贷方分录信息***************************************/
			entityMapIncom.put("detail_type", "credit");  //获取贷方对应明细科目
			List<Map<String, Object>> incomList = JsonListMapUtil.toListMapLower(accMedicareShareMapper.queryMedSubjCreditMoneyListByType(entityMapIncom));
			int dIndex = 0, dMaxIndex = incomList.size() - 1, cIndex, cMaxIndex;  //index：循环次数，maxIndex：循环总次数
			Double sumDetailMoney = 0.00, detailMoney = 0.00, sumCheckMoney = 0.00, checkMoney = 0.00, subjMoney = 0.00;
			
			for(Map<String, Object> map : incomList){
				Map<String, Object> detailMap = new HashMap<String, Object>();
				detailMap.put("group_id", group_id);
				detailMap.put("hos_id", hos_id); 
				detailMap.put("copy_code", copy_code);
				detailMap.put("vouch_id", vouch_id);
				detailMap.put("vouch_row", vouch_row);
				detailMap.put("subj_code", map.get("subj_code"));
				detailMap.put("summary", att.getSummary());
				detailMap.put("debit", 0);
				if(dIndex == dMaxIndex){
					//最后一条分录金额：总金额 - 除最后一条分录金额合计
					detailMap.put("credit", medSumMoney - sumDetailMoney);  //分录贷方金额
				}else{
					detailMap.put("credit", NumberUtil.numberToRound(medSumMoney * (Double.parseDouble(map.get("money").toString()) / incomSumMoney)));  //分录贷方金额
				}
				detailMap.put("debit_w", 0);
				detailMap.put("credit_w", 0);
				subjMoney = Double.parseDouble(map.get("money").toString());  //该科目本期发生额
				detailMoney = Double.parseDouble(detailMap.get("credit").toString());  //分录金额
				sumDetailMoney += detailMoney;  //分录累计金额
				//存放贷方分录
				detailList.add(detailMap);
				
				if(map.get("is_check") != null && "1".equals(map.get("is_check").toString())){
					/*************************组装辅助核算信息*************************************/
					entityMapIncom.put("subj_code", map.get("subj_code"));
					//获取该科目的辅助核算项
					List<Map<String, Object>> sqlList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.querySubjCheckColumnBySubj(entityMapIncom));

					StringBuffer columns = new StringBuffer();  //记录辅助核算列 用于查询
					
					for(Map<String, Object> sqlMap : sqlList){
						//判断是否含变更信息
						if(sqlMap.get("is_sys") != null && "1".equals(sqlMap.get("is_sys").toString())){
							if(sqlMap.get("is_change") != null && "1".equals(sqlMap.get("is_change").toString())){
								//记录包含的辅助核算项
								if(!checkItem.containsKey(sqlMap.get("column_check") + "_no")){
									checkItem.put(sqlMap.get("column_check") + "_no", sqlMap.get("column_check") + "_no");
								}
								columns.append(sqlMap.get("column_check")).append("_no,");
							}
							//记录包含的辅助核算项
							if(!checkItem.containsKey(sqlMap.get("column_check") + "_id")){
								checkItem.put(sqlMap.get("column_check") + "_id", sqlMap.get("column_check") + "_id");
							}
							columns.append(sqlMap.get("column_check")).append("_id,");
						}else{
							//记录包含的辅助核算项
							if(!checkItem.containsKey(sqlMap.get("column_check"))){
								checkItem.put(sqlMap.get("column_check").toString(), sqlMap.get("column_check").toString());
							}
							columns.append(sqlMap.get("column_check")).append(",");
						}
					}
					entityMap.put("columns", columns.substring(0, columns.length() - 1).toString());
					List<Map<String, Object>> tmpList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.queryCheckMoneyBySubjDire(entityMapMed));
					if(tmpList.size() > 0){
						//初始化数据
						cIndex = 0;
						cMaxIndex = tmpList.size() - 1;
						checkMoney = sumCheckMoney = 0.00;
						for(Map<String, Object> tmp : tmpList){
							if(cIndex == cMaxIndex){
								//最后一个辅助核算的金额应为：分录金额 - 辅助核算总金额(除了该辅助核算)
								tmp.put("credit", detailMoney - sumCheckMoney);
							}else{
								//正常辅助核算金额为：分录金额 * 科室收入比重(即：科室收入 / 分录收入)
								checkMoney = NumberUtil.numberToRound(detailMoney * (Double.parseDouble(tmp.get("credit").toString()) / subjMoney));
								sumCheckMoney += checkMoney;
								tmp.put("credit", checkMoney);
							}
							if(checkMoney != 0 || cIndex == cMaxIndex){
								tmp.put("group_id", group_id);
								tmp.put("hos_id", hos_id); 
								tmp.put("copy_code", copy_code);
								tmp.put("vouch_id", vouch_id);
								tmp.put("vouch_row", vouch_row);
								tmp.put("summary", att.getSummary());
								tmp.put("vouch_check_id", check_row);
								vouchCheckList.add(tmp);
								check_row += 1;
							}
							cIndex += 1;
						}
					}
				}
				dIndex += 1;

				vouch_row += 1;  //分录行数加1
			}

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
