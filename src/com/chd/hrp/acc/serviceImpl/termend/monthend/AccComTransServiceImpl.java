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
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.termend.monthend.AccComTransMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.service.termend.AccTermendTemplateService;
import com.chd.hrp.acc.service.termend.monthend.AccComTransService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/**
 * @Title. @Description. 通用结转<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accComTransService")
public class AccComTransServiceImpl implements AccComTransService {

	private static Logger logger = Logger.getLogger(AccComTransServiceImpl.class);

	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	@Resource(name = "accComTransMapper")
	private final AccComTransMapper accComTransMapper = null;
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	@Resource(name = "accTermendTemplateService")
	private final AccTermendTemplateService accTermendTemplateService = null;

	@Override
	public String addAccComTransVouch(Map<String, Object> entityMap) throws DataAccessException {
		//存放凭证主表信息
		Map<String, Object> vouchMap = new HashMap<String, Object>();
		//存放凭证分录信息
		List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
		//存放辅助核算信息
		List<Map<String, Object>> vouchCheckList = new ArrayList<Map<String,Object>>();
		//记录辅助核算
		Map<String, Map<String, Object>> curCheck = new HashMap<String, Map<String,Object>>();
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
				return "{\"error\":\"本期间含未记账凭证，不能结转!\"}";
			}
	
			Double creditMoney = 0.00, creditMoneyW = 0.00, money_sum = 0.0;
			String template_type_code = att.getTemplate_type_code();
			Date date = new Date();
			Long group_id = att.getGroup_id();
			Long hos_id = att.getHos_id();
			String copy_code = att.getCopy_code();
			String acc_year = att.getAcc_year();
			int vouch_row = 1, check_row = 1;
			Map<String, String> checkItem = new HashMap<String, String>();
			String column = "", column_value = "";
			
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
			
			/****************************组装分录信息***************************************/
			Map<String, Object> debitMap = new HashMap<String, Object>();
			Map<String, Object> creditMap = new HashMap<String, Object>();
			List<Map<String, Object>> subjList = null;
			List<Map<String, Object>> checkColList = null;
			Map<String, Map<String, Object>> tmpCheckMap = null;
			Double check_money = 0.0, check_money_w = 0.0;
			int subj_dire = 0;
			//处理财务会计凭证部分
			if(att.getCredit_subj_code1() != null && !"".equals(att.getCredit_subj_code1())){
				//获取收入结转科目余额
				entityMap.put("kind_code", "01");
				subjList = JsonListMapUtil.toListMapLower(accComTransMapper.queryComTransSubjMoneyList(entityMap));
	
				if(subjList == null || subjList.size() == 0){
					return "{\"error\":\"财务会计转出科目为空，请维护后再生成！\"}";
				}
				for(Map<String, Object> subjMap : subjList){
					/***********************借方分录***************************/
					debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", subjMap.get("subj_code"));
					debitMap.put("summary", att.getSummary());
					//生成的凭证应该借贷方互换
					debitMap.put("debit", subjMap.get("credit"));  //分录借方金额
					debitMap.put("debit_w", subjMap.get("credit_w"));
					debitMap.put("credit", subjMap.get("debit"));  //分录贷方金额
					debitMap.put("credit_w", subjMap.get("debit_w"));
					detailList.add(debitMap);
					
					//记录结转科目贷方发生 - 借方发生 = 结余科目发生
					creditMoney += NumberUtil.sub(Double.parseDouble(subjMap.get("credit").toString()), Double.parseDouble(subjMap.get("debit").toString()));
					creditMoneyW += NumberUtil.sub(Double.parseDouble(subjMap.get("credit_w").toString()), Double.parseDouble(subjMap.get("debit_w").toString()));
	
					/************组装辅助核算信息***********begin*************/
					if(subjMap.get("is_check") != null && "1".equals(subjMap.get("is_check").toString())){
						entityMap.put("subj_code", subjMap.get("subj_code"));
						//获取该科目的辅助核算项
						checkColList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.querySubjCheckColumnBySubj(entityMap));
	
						StringBuffer columns = new StringBuffer();  //记录辅助核算列 用于查询
						int index = 0;
						String [] cols = new String[4];
						String column_check = "";
						String keyCheck = "";
						for(Map<String, Object> checkCol : checkColList){
							column_check = checkCol.get("column_check").toString().toLowerCase();
							//判断是否含变更信息
							if(checkCol.get("is_sys") != null && "1".equals(checkCol.get("is_sys").toString())){
								if(checkCol.get("is_change") != null && "1".equals(checkCol.get("is_change").toString())){
									//记录包含的辅助核算项
									if(!checkItem.containsKey(column_check + "_no")){
										checkItem.put(column_check + "_no", column_check + "_no");
									}
									columns.append(column_check).append("_no,");
									cols[index] = column_check+"_no";
								}
								//记录包含的辅助核算项
								if(!checkItem.containsKey(column_check + "_id")){
									checkItem.put(column_check + "_id", column_check + "_id");
								}
								columns.append(column_check).append("_id,");
								cols[index] = column_check+"_id";
							}else{
								//记录包含的辅助核算项
								if(!checkItem.containsKey(column_check)){
									checkItem.put(column_check.toString(), column_check.toString());
								}
								columns.append(column_check).append(",");
								cols[index] = column_check;
							}
							index ++;
						}
	
						if(columns.length() > 0){
							entityMap.put("columns", columns.substring(0, columns.length() - 1).toString());
							//根据科目本期发生额的方向取辅助核算金额
							subj_dire = Double.parseDouble(subjMap.get("credit").toString()) == 0 ? 0 : 1;  
							entityMap.put("subj_dire", subj_dire);
							
							List<Map<String, Object>> tmpList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.queryCheckMoneyBySubjDire(entityMap));
							for(Map<String, Object> tmp : tmpList){
								//过滤发生额为0的辅助核算
								if((tmp.get("debit") != null && Double.parseDouble(tmp.get("debit").toString()) != 0)
										|| (tmp.get("credit") != null && Double.parseDouble(tmp.get("credit").toString()) != 0)){
									
									//根据分录方向 取辅助核算金额
									check_money = subj_dire == 0 ? Double.parseDouble(tmp.get("debit").toString()) : Double.parseDouble(tmp.get("credit").toString());
									check_money_w = subj_dire == 0 ? Double.parseDouble(tmp.get("debit_w").toString()) : Double.parseDouble(tmp.get("credit_w").toString());
									
									tmp.put("group_id", group_id);
									tmp.put("hos_id", hos_id); 
									tmp.put("copy_code", copy_code);
									tmp.put("vouch_id", vouch_id);
									tmp.put("vouch_row", vouch_row);
									tmp.put("summary", att.getSummary());
									tmp.put("vouch_check_id", check_row);
									//与分录一直互换借贷方
									tmp.put("debit", subj_dire == 0 ? check_money : 0);
									tmp.put("debit_w", subj_dire == 0 ? check_money_w : 0);
									tmp.put("credit", subj_dire == 0 ? 0 : check_money);
									tmp.put("credit_w", subj_dire == 0 ? 0 : check_money_w);
									vouchCheckList.add(tmp);
									//记录辅助核算
									for(int i = 0; i < cols.length; i++){
										if(cols[i]!=null && tmp.get(cols[i])!=null){
											keyCheck += tmp.get(cols[i]);
										}
									} 
									Map<String, Object> curMap = null;
									//结余辅助核算为 贷方发生-借方发生
									check_money = subj_dire == 0 ? -1 * check_money : check_money;
									check_money_w = subj_dire == 0 ? -1 * check_money_w : check_money_w;
									if(curCheck.containsKey(keyCheck)){
										curMap = curCheck.get(keyCheck);
										//互换借贷方与分录一直
										curMap.put("money", Double.parseDouble(curMap.get("money").toString()) + check_money);
										curMap.put("money_w", Double.parseDouble(curMap.get("money_w").toString()) + check_money_w);
									}else{
										curMap = new HashMap<String, Object>();
										curMap.putAll(tmp);
										curMap.put("money", check_money);
										curMap.put("money_w", check_money_w);
										curMap.remove("debit");
										curMap.remove("debit_w");
										curMap.remove("credit");
										curMap.remove("credit_w");
									}
									curCheck.put(keyCheck, curMap);
	
									check_row += 1;  //辅助核算行数加1
								}
							}
						}
					}
					/************组装辅助核算信息***********end***************/
					vouch_row += 1;  //分录行数加1
				}
				
				if(creditMoney != null && creditMoney != 0){
					
					/***********************贷方分录***************************/
					creditMap = new HashMap<String, Object>();
					creditMap.put("group_id", group_id);
					creditMap.put("hos_id", hos_id); 
					creditMap.put("copy_code", copy_code);
					creditMap.put("vouch_id", vouch_id);
					creditMap.put("vouch_row", vouch_row);
					creditMap.put("subj_code", att.getCredit_subj_code1());
					creditMap.put("summary", att.getSummary());
					creditMap.put("debit", 0);
					creditMap.put("debit_w", 0);
					creditMap.put("credit", creditMoney);
					creditMap.put("credit_w", creditMoneyW);
					detailList.add(creditMap);
	
					/************组装辅助核算信息***********begin*************/
					if(att.getCredit_is_check1() == 1){
						entityMap.put("subj_code", att.getCredit_subj_code1());
						//获取该科目的辅助核算项
						checkColList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.querySubjCheckColumnBySubj(entityMap));
						tmpCheckMap = accTermendTemplateService.getMapByCheckCol(curCheck, checkColList);
						//辅助核算
						for(Map.Entry<String, Map<String, Object>> entry : tmpCheckMap.entrySet()){
							Map<String, Object> curMap = new HashMap<String, Object>();
							curMap.putAll(entry.getValue());
							curMap.put("subj_code", att.getCredit_subj_code1());
							curMap.put("vouch_row", vouch_row);
							curMap.put("vouch_check_id", check_row);
							//辅助核算金额与分录保持一致
							curMap.put("debit", 0);
							curMap.put("debit_w", 0);
							curMap.put("credit", entry.getValue().get("money"));
							curMap.put("credit_w", entry.getValue().get("money_w"));
							vouchCheckList.add(curMap);
							check_row += 1;  //辅助核算行数加1
						}
					}
					/************组装辅助核算信息***********begin***************/
	
					vouch_row += 1;  //分录行数加1
					money_sum += creditMoney;
				}
			}
			//处理预算会计凭证部分
			if(att.getCredit_subj_code2() != null && !"".equals(att.getCredit_subj_code2())){
				curCheck = new HashMap<String, Map<String,Object>>();
				creditMoney = 0.0;
				creditMoneyW = 0.0;
				//获取收入结转科目余额
				entityMap.put("kind_code", "02");
				subjList = JsonListMapUtil.toListMapLower(accComTransMapper.queryComTransSubjMoneyList(entityMap));
	
				if(subjList == null || subjList.size() == 0){
					return "{\"error\":\"预算会计转出科目为空，请维护后再生成！\"}";
				}
				for(Map<String, Object> subjMap : subjList){
					/***********************借方分录***************************/
					debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", subjMap.get("subj_code"));
					debitMap.put("summary", att.getSummary());
					//生成的凭证应该借贷方互换
					debitMap.put("debit", subjMap.get("credit"));  //分录借方金额
					debitMap.put("debit_w", subjMap.get("credit_w"));
					debitMap.put("credit", subjMap.get("debit"));  //分录贷方金额
					debitMap.put("credit_w", subjMap.get("debit_w"));
					detailList.add(debitMap);
					
					//记录结转科目贷方发生 - 借方发生 = 结余科目发生
					creditMoney += NumberUtil.sub(Double.parseDouble(subjMap.get("credit").toString()), Double.parseDouble(subjMap.get("debit").toString()));
					creditMoneyW += NumberUtil.sub(Double.parseDouble(subjMap.get("credit_w").toString()), Double.parseDouble(subjMap.get("debit_w").toString()));
	
					/************组装辅助核算信息***********begin*************/
					if(subjMap.get("is_check") != null && "1".equals(subjMap.get("is_check").toString())){
						entityMap.put("subj_code", subjMap.get("subj_code"));
	
						//获取该科目的辅助核算项
						checkColList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.querySubjCheckColumnBySubj(entityMap));
	
						StringBuffer columns = new StringBuffer();  //记录辅助核算列 用于查询
						int index = 0;
						String [] cols = new String[4];
						String column_check = "";
						String keyCheck = "";
						for(Map<String, Object> checkCol : checkColList){
							column_check = checkCol.get("column_check").toString().toLowerCase();
							//判断是否含变更信息
							if(checkCol.get("is_sys") != null && "1".equals(checkCol.get("is_sys").toString())){
								if(checkCol.get("is_change") != null && "1".equals(checkCol.get("is_change").toString())){
									//记录包含的辅助核算项
									if(!checkItem.containsKey(column_check + "_no")){
										checkItem.put(column_check + "_no", column_check + "_no");
									}
									columns.append(column_check).append("_no,");
									cols[index] = column_check+"_no";
								}
								//记录包含的辅助核算项
								if(!checkItem.containsKey(column_check + "_id")){
									checkItem.put(column_check + "_id", column_check + "_id");
								}
								columns.append(column_check).append("_id,");
								cols[index] = column_check+"_id";
							}else{
								//记录包含的辅助核算项
								if(!checkItem.containsKey(column_check)){
									checkItem.put(column_check.toString(), column_check.toString());
								}
								columns.append(column_check).append(",");
								cols[index] = column_check;
							}
							index ++;
						}
	
						if(columns.length() > 0){
							entityMap.put("columns", columns.substring(0, columns.length() - 1).toString());
							subj_dire = Double.parseDouble(subjMap.get("credit").toString()) == 0 ? 0 : 1;  //根据科目本期发生额的方向取辅助核算金额
							entityMap.put("subj_dire", subj_dire);
							
							List<Map<String, Object>> tmpList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.queryCheckMoneyBySubjDire(entityMap));
							for(Map<String, Object> tmp : tmpList){
								//过滤发生额为0的辅助核算
								if((tmp.get("debit") != null && Double.parseDouble(tmp.get("debit").toString()) != 0)
										|| (tmp.get("credit") != null && Double.parseDouble(tmp.get("credit").toString()) != 0)){
									
									//根据分录方向 取辅助核算金额
									check_money = subj_dire == 0 ? Double.parseDouble(tmp.get("debit").toString()) : Double.parseDouble(tmp.get("credit").toString());
									check_money_w = subj_dire == 0 ? Double.parseDouble(tmp.get("debit_w").toString()) : Double.parseDouble(tmp.get("credit_w").toString());
									
									tmp.put("group_id", group_id);
									tmp.put("hos_id", hos_id); 
									tmp.put("copy_code", copy_code);
									tmp.put("vouch_id", vouch_id);
									tmp.put("vouch_row", vouch_row);
									tmp.put("summary", att.getSummary());
									tmp.put("vouch_check_id", check_row);
									//与分录一直互换借贷方
									tmp.put("debit", subj_dire == 0 ? check_money : 0);
									tmp.put("debit_w", subj_dire == 0 ? check_money_w : 0);
									tmp.put("credit", subj_dire == 0 ? 0 : check_money);
									tmp.put("credit_w", subj_dire == 0 ? 0 : check_money_w);
									vouchCheckList.add(tmp);
									//记录辅助核算
									for(int i = 0; i < cols.length; i++){
										if(cols[i]!=null && tmp.get(cols[i])!=null){
											keyCheck += tmp.get(cols[i]);
										}
									} 
									Map<String, Object> curMap = null;
									//结余辅助核算为 贷方发生-借方发生
									check_money = subj_dire == 0 ? -1 * check_money : check_money;
									check_money_w = subj_dire == 0 ? -1 * check_money_w : check_money_w;
									if(curCheck.containsKey(keyCheck)){
										curMap = curCheck.get(keyCheck);
										//互换借贷方与分录一直
										curMap.put("money", Double.parseDouble(curMap.get("money").toString()) + check_money);
										curMap.put("money_w", Double.parseDouble(curMap.get("money_w").toString()) + check_money_w);
									}else{
										curMap = new HashMap<String, Object>();
										curMap.putAll(tmp);
										curMap.put("money", check_money);
										curMap.put("money_w", check_money_w);
										curMap.remove("debit");
										curMap.remove("debit_w");
										curMap.remove("credit");
										curMap.remove("credit_w");
									}
									curCheck.put(keyCheck, curMap);
	
									check_row += 1;  //辅助核算行数加1
								}
							}
						}
					}
					/************组装借方辅助核算信息***********end***************/
					vouch_row += 1;  //分录行数加1
				}
				
				if(creditMoney != null && creditMoney != 0){
					
					/***********************贷方分录***************************/
					creditMap = new HashMap<String, Object>();
					creditMap.put("group_id", group_id);
					creditMap.put("hos_id", hos_id); 
					creditMap.put("copy_code", copy_code);
					creditMap.put("vouch_id", vouch_id);
					creditMap.put("vouch_row", vouch_row);
					creditMap.put("subj_code", att.getCredit_subj_code2());
					creditMap.put("summary", att.getSummary());
					creditMap.put("debit", 0);
					creditMap.put("debit_w", 0);
					creditMap.put("credit", creditMoney);
					creditMap.put("credit_w", creditMoneyW);
					detailList.add(creditMap);
	
					/************组装贷方辅助核算信息***********begin*************/
					if(att.getCredit_is_check2() == 1){
						entityMap.put("subj_code", att.getCredit_subj_code2());
						//获取该科目的辅助核算项
						checkColList = JsonListMapUtil.toListMapLower(accTermendTemplateMapper.querySubjCheckColumnBySubj(entityMap));
						tmpCheckMap = accTermendTemplateService.getMapByCheckCol(curCheck, checkColList);
						//辅助核算
						for(Map.Entry<String, Map<String, Object>> entry : tmpCheckMap.entrySet()){
							Map<String, Object> curMap = new HashMap<String, Object>();
							curMap.putAll(entry.getValue());
							curMap.put("subj_code", att.getCredit_subj_code2());
							curMap.put("vouch_row", vouch_row);
							curMap.put("vouch_check_id", check_row);
							//辅助核算金额与分录保持一致
							curMap.put("debit", 0);
							curMap.put("debit_w", 0);
							curMap.put("credit", entry.getValue().get("money"));
							curMap.put("credit_w", entry.getValue().get("money_w"));
							vouchCheckList.add(curMap);
							check_row += 1;  //辅助核算行数加1
						}
					}
					/************组装贷方辅助核算信息***********begin***************/
	
					money_sum += creditMoney;
				}
			}
			
			if(detailList == null || detailList.size() == 0){
				return "{\"error\":\"模板没有维护结转科目或结转科目本年没有发生数据！\"}";
			}
			
			/*if(money_sum == 0){
				return "{\"error\":\"收支结转科目余额为0，不能生成年度收支结余凭证！\"}";
			}*/
			
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
			if(vouchCheckList.size()>0){
				superVouchMapper.saveAccAutoCheckByTermend(column, column_value, vouchCheckList);
			}
			accTermendTemplateMapper.saveAutoVouchLogTemp(logMap);
			
			return "{\"state\":\"true\",\"vouch_id\":\""+vouch_id+"\"}";
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			
			throw new SysException(StringTool.string2Json(e.getMessage()));
		}
	}
}
