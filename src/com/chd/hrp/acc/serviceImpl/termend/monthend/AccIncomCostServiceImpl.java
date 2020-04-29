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

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.MyConfig;
import com.chd.base.exception.SysException;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.termend.monthend.AccIncomCostMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccTermendTemplate;
import com.chd.hrp.acc.service.termend.AccTermendTemplateService;
import com.chd.hrp.acc.service.termend.monthend.AccIncomCostService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/** 
 * @Title. @Description. 收支结转<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accIncomCostService")
public class AccIncomCostServiceImpl implements AccIncomCostService {

	private static Logger logger = Logger.getLogger(AccIncomCostServiceImpl.class);
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	@Resource(name = "accIncomCostMapper")
	private final AccIncomCostMapper accIncomCostMapper = null;
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;
	@Resource(name = "accTermendTemplateService")
	private final AccTermendTemplateService accTermendTemplateService = null;
	
	/**
	 * 1."本期间含未记账凭证，不能收支结转!
	 * 2.校验收入与支出科目本期是否有发生额。
	 * 3.提取收入贷方发生额记到借方；支出借方发生额记到贷方(与原来科目的方向相反)
	 * 4.收入贷方总发生额记到结余科目贷方正数；支出借方总发生额记到结余科目贷方负数
	 */
	@Override
	public String addAccIncomCostVouch(Map<String, Object> entityMap) throws DataAccessException {
		
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
		//判断是否结转预算科目
		boolean is_exec_budg = false;
		
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
				return "{\"error\":\"本期间含未记账凭证，不能收支结转!\"}";
			}
			
			//判断是否只有年末才生成预算科目
			if("2".equals(MyConfig.getSysPara("041"))){
				if(entityMap.get("acc_month") != null ){
					is_exec_budg = true;
				}
			}else{
				is_exec_budg = true;
			}
			
			//放入资金来源项 对应辅助核算表的check7字段
			entityMap.put("source_id", att.getSource_id());
			
			//判断是从科目账取数还是从辅助账取数
			if(att.getSource_id() > 0){
				entityMap.put("sum_table", "acc_leder_check");
			}else{
				entityMap.put("sum_table", "acc_leder");
			}
			
			//获取模板中财政收支科目账表中本期发生金额
			entityMap.put("kind_code", "01");
			List<Map<String, Object>> incomCostList = JsonListMapUtil.toListMapLower(accIncomCostMapper.queryIncomCostSubjMoney(entityMap));
			
			//年末获取模板中预算收支科目账表中本期发生金额
			List<Map<String, Object>> budgIncomCostList = new ArrayList<Map<String,Object>>();
			if(is_exec_budg && entityMap.get("acc_month") != null ){
				entityMap.put("kind_code", "02");
				budgIncomCostList = JsonListMapUtil.toListMapLower(accIncomCostMapper.queryIncomCostSubjMoney(entityMap));
				if(incomCostList.size() == 0 && budgIncomCostList.size() == 0){
					return "{\"error\":\"转出科目在所选期间发生额为零，不能收支结转!\"}";
				}
			}else{
				if(incomCostList.size() == 0){
					return "{\"error\":\"转出科目在所选期间发生额为零，不能收支结转!\"}";
				}
			}
			
			//获取辅助核算列
			String checkColumn = accTermendTemplateMapper.queryAllSubjCheckColumn(entityMap);
			entityMap.put("checkColumn", checkColumn);
			//记录支出发生合计
			Double costMoney = 0.00; 
			Double costMoney_w = 0.00; 
			//记录收入发生合计
			Double incomMoney = 0.00;
			Double incomMoney_w = 0.00;

			Date date = new Date();
			Long group_id = att.getGroup_id();
			Long hos_id = att.getHos_id();
			String copy_code = att.getCopy_code();
			String acc_year = att.getAcc_year();
			String template_type_code = att.getTemplate_type_code();
			int vouch_row = 1, check_row = 1;
			Map<String, String> checkItem = new HashMap<String, String>();
			String column = "", column_value = "";
			int subj_dire;
			Double check_money = 0.0, check_money_w = 0.0;
			
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

			String thisSubj = "";
			List<Map<String, Object>> checkColList = null;
			Map<String, Map<String, Object>> tmpCheckMap = null;
			//处理财务会计凭证部分
			if(att.getCredit_subj_code1() != null && !"".equals(att.getCredit_subj_code1())){
				/****************************循环组装凭证财务收支分录信息************begin********************/
				for(Map<String, Object> map : incomCostList){
					if(!"".equals(thisSubj) && !thisSubj.equals(map.get("subj_type_code").toString())){
						/****************************组装凭证结余分录信息***********begin********************/
						//收入科目结余
						if(incomMoney != 0){
							Map<String, Object> debitMap = new HashMap<String, Object>();
							if(att.getCredit_subj_code1()==null){
								throw new SysException("模板【"+att.getTemplate_name()+"】财务结余科目为空！");
							}
							debitMap.put("group_id", group_id);
							debitMap.put("hos_id", hos_id); 
							debitMap.put("copy_code", copy_code);
							debitMap.put("vouch_id", vouch_id);
							debitMap.put("vouch_row", vouch_row);
							debitMap.put("subj_code", att.getCredit_subj_code1());
							debitMap.put("summary", att.getSummary());
							//收入科目结余应是贷方正数
							debitMap.put("debit", 0);
							debitMap.put("debit_w", 0);
							debitMap.put("credit", incomMoney);
							debitMap.put("credit_w", incomMoney_w);
							detailList.add(debitMap);
							
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
							incomMoney = 0.0;
							incomMoney_w = 0.0;
							vouch_row += 1;  //分录行数加1
						}
						//支出科目结余
						if(costMoney != 0){
							Map<String, Object> debitMap = new HashMap<String, Object>();
							if(att.getCredit_subj_code1()==null){
								throw new SysException("模板【"+att.getTemplate_name()+"】财务结余科目为空！");
							}
							debitMap.put("group_id", group_id);
							debitMap.put("hos_id", hos_id); 
							debitMap.put("copy_code", copy_code);
							debitMap.put("vouch_id", vouch_id);
							debitMap.put("vouch_row", vouch_row);
							debitMap.put("subj_code", att.getCredit_subj_code1());
							debitMap.put("summary", att.getSummary());
							//支出科目结余应是借方正数
							debitMap.put("debit", costMoney);
							debitMap.put("debit_w", costMoney_w);
							debitMap.put("credit", 0);
							debitMap.put("credit_w", 0);
							detailList.add(debitMap);
							
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
									curMap.put("debit", entry.getValue().get("money"));
									curMap.put("debit_w", entry.getValue().get("money_w"));
									curMap.put("credit", 0);
									curMap.put("credit_w", 0);
									vouchCheckList.add(curMap);
									check_row += 1;  //辅助核算行数加1
								}
							}
							costMoney = 0.0;
							costMoney_w = 0.0;
							vouch_row += 1;  //分录行数加1
						}
						
						curCheck = new HashMap<String, Map<String,Object>>();
						/****************************组装凭证结余分录信息***********end**********************/
					}
					Map<String, Object> debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", map.get("subj_code"));
					debitMap.put("summary", att.getSummary());
					//生成的凭证应该借贷方互换
					debitMap.put("debit", map.get("credit"));  //分录借方金额
					debitMap.put("debit_w", map.get("credit_w"));
					debitMap.put("credit", map.get("debit"));  //分录贷方金额
					debitMap.put("credit_w", map.get("debit_w"));
					
					detailList.add(debitMap);
					
					//subj_type_code : 04 收入, 05 费用
					if(map.get("subj_type_code") != null && "04".equals(map.get("subj_type_code").toString())){
						
						incomMoney += Double.parseDouble(map.get("credit").toString())+(-1)*Double.parseDouble(map.get("debit").toString());
						incomMoney_w += Double.parseDouble(map.get("credit_w").toString())+(-1)*Double.parseDouble(map.get("debit_w").toString());
					}else if(map.get("subj_type_code") != null && "05".equals(map.get("subj_type_code").toString())){
						
						costMoney += Double.parseDouble(map.get("debit").toString())+(-1)*Double.parseDouble(map.get("credit").toString());
						costMoney_w += Double.parseDouble(map.get("debit_w").toString())+(-1)*Double.parseDouble(map.get("credit_w").toString());
					}
					thisSubj = map.get("subj_type_code").toString();
					
					if(map.get("is_check") != null && "1".equals(map.get("is_check").toString())){
						/*************************组装辅助核算信息******************begin********************/
						entityMap.put("subj_code", map.get("subj_code"));
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
							subj_dire = Double.parseDouble(map.get("credit").toString()) == 0 ? 0 : 1;  //根据科目本期发生额的方向取辅助核算金额
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
									//辅助核算余额与分录方向一致
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
									if(map.get("subj_type_code") != null && "04".equals(map.get("subj_type_code").toString())){
										//结余辅助核算为 贷方发生 - 借方发生
										check_money = subj_dire == 0 ? -1 * check_money : check_money;
										check_money_w = subj_dire == 0 ? -1 * check_money_w : check_money_w;
									}else if(map.get("subj_type_code") != null && "05".equals(map.get("subj_type_code").toString())){
										//结余辅助核算为 借方发生 - 贷方发生
										check_money = subj_dire == 0 ? check_money : -1 * check_money;
										check_money_w = subj_dire == 0 ? check_money_w : -1 * check_money_w;
									}
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
						
						/*************************组装辅助核算信息******************end**********************/
					}
					vouch_row += 1;  //分录行数加1
				}
				/****************************循环组装凭证财务收支分录信息************end**********************/
				/****************************组装凭证财务结余分录信息****************begin********************/
				//收入科目结余
				if(incomMoney != 0){
					Map<String, Object> debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", att.getCredit_subj_code1());
					debitMap.put("summary", att.getSummary());
					//收入科目结余应是贷方正数
					debitMap.put("debit", 0);
					debitMap.put("debit_w", 0);
					debitMap.put("credit", incomMoney);
					debitMap.put("credit_w", incomMoney_w);
					detailList.add(debitMap);
					
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
					incomMoney = 0.0;
					incomMoney_w = 0.0;
					vouch_row += 1;  //分录行数加1
				}
				//支出科目结余
				if(costMoney != 0){
					Map<String, Object> debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", att.getCredit_subj_code1());
					debitMap.put("summary", att.getSummary());
					//支出科目结余应是借方正数
					debitMap.put("debit", costMoney);
					debitMap.put("debit_w", costMoney_w);
					debitMap.put("credit", 0);
					debitMap.put("credit_w", 0);
					detailList.add(debitMap);
	
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
							curMap.put("debit", entry.getValue().get("money"));
							curMap.put("debit_w", entry.getValue().get("money_w"));
							curMap.put("credit", 0);
							curMap.put("credit_w", 0);
							vouchCheckList.add(curMap);
							
							check_row += 1;  //辅助核算行数加1
						}
					}
					
					costMoney = 0.0;
					costMoney_w = 0.0;
					vouch_row += 1;  //分录行数加1
				}
				/****************************组装凭证财务结余分录信息****************end**********************/
				curCheck = new HashMap<String, Map<String,Object>>();
			}
			//如果是年末且预算收支科目存在发生数据则需要处理预算科目
			if(is_exec_budg && budgIncomCostList !=null && budgIncomCostList.size() > 0 && att.getCredit_subj_code2() != null && !"".equals(att.getCredit_subj_code2())){
				/****************************循环组装凭证预算收支分录信息************begin********************/
				thisSubj = "";
				costMoney = 0.00; 
				incomMoney = 0.00;
				for(Map<String, Object> map : budgIncomCostList){
					if(!"".equals(thisSubj) && !thisSubj.equals(map.get("subj_type_code").toString())){
						/****************************组装凭证结余分录信息***********begin********************/
						//收入科目结余
						if(incomMoney != 0){
							Map<String, Object> debitMap = new HashMap<String, Object>();
							if(att.getCredit_subj_code2()==null){
								throw new SysException("模板【"+att.getTemplate_name()+"】预算结余科目为空！");
							}
							debitMap.put("group_id", group_id);
							debitMap.put("hos_id", hos_id); 
							debitMap.put("copy_code", copy_code);
							debitMap.put("vouch_id", vouch_id);
							debitMap.put("vouch_row", vouch_row);
							debitMap.put("subj_code", att.getCredit_subj_code2());
							debitMap.put("summary", att.getSummary());
							//收入科目结余应是贷方正数
							debitMap.put("debit", 0);
							debitMap.put("debit_w", 0);
							debitMap.put("credit", incomMoney);
							debitMap.put("credit_w", incomMoney_w);
							detailList.add(debitMap);
							
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
							incomMoney = 0.0;
							incomMoney_w = 0.0;
							vouch_row += 1;  //分录行数加1
						}
						//支出科目结余
						if(costMoney != 0){
							Map<String, Object> debitMap = new HashMap<String, Object>();
							if(att.getCredit_subj_code2()==null){
								throw new SysException("模板【"+att.getTemplate_name()+"】预算结余科目为空！");
							}
							debitMap.put("group_id", group_id);
							debitMap.put("hos_id", hos_id); 
							debitMap.put("copy_code", copy_code);
							debitMap.put("vouch_id", vouch_id);
							debitMap.put("vouch_row", vouch_row);
							debitMap.put("subj_code", att.getCredit_subj_code2());
							debitMap.put("summary", att.getSummary());
							//支出科目结余应是借方正数
							debitMap.put("debit", costMoney);
							debitMap.put("debit_w", costMoney_w);
							debitMap.put("credit", 0);
							debitMap.put("credit_w", 0);
							detailList.add(debitMap);
							
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
									curMap.put("debit", entry.getValue().get("money"));
									curMap.put("debit_w", entry.getValue().get("money_w"));
									curMap.put("credit", 0);
									curMap.put("credit_w", 0);
									vouchCheckList.add(curMap);
									check_row += 1;  //辅助核算行数加1
								}
							}
							costMoney = 0.0;
							costMoney_w = 0.0;
							vouch_row += 1;  //分录行数加1
						}
						
						//detailList = new ArrayList<Map<String,Object>>();
						//vouchCheckList = new ArrayList<Map<String,Object>>();
						curCheck = new HashMap<String, Map<String,Object>>();
						/****************************组装凭证结余分录信息***********end**********************/
					}
					Map<String, Object> debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", map.get("subj_code"));
					debitMap.put("summary", att.getSummary());
					//生成的凭证应该借贷方互换
					debitMap.put("debit", map.get("credit"));  //分录借方金额
					debitMap.put("debit_w", map.get("credit_w"));
					debitMap.put("credit", map.get("debit"));  //分录贷方金额
					debitMap.put("credit_w", map.get("debit_w"));
					
					detailList.add(debitMap);
					
					//subj_type_code : 06 收入, 07 费用
					if(map.get("subj_type_code") != null && "06".equals(map.get("subj_type_code").toString())){
						
						incomMoney += Double.parseDouble(map.get("credit").toString())+(-1)*Double.parseDouble(map.get("debit").toString());
						incomMoney_w += Double.parseDouble(map.get("credit_w").toString())+(-1)*Double.parseDouble(map.get("debit_w").toString());
					}else if(map.get("subj_type_code") != null && "07".equals(map.get("subj_type_code").toString())){
						
						costMoney += Double.parseDouble(map.get("debit").toString())+(-1)*Double.parseDouble(map.get("credit").toString());
						costMoney_w += Double.parseDouble(map.get("debit_w").toString())+(-1)*Double.parseDouble(map.get("credit_w").toString());
					}
					thisSubj = map.get("subj_type_code").toString();
					
					if(map.get("is_check") != null && "1".equals(map.get("is_check").toString())){
						/*************************组装辅助核算信息******************begin********************/
						entityMap.put("subj_code", map.get("subj_code"));
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
							subj_dire = Double.parseDouble(map.get("credit").toString()) == 0 ? 0 : 1;  //根据科目本期发生额的方向取辅助核算金额
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
									//辅助核算余额与分录方向一致
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
									if(map.get("subj_type_code") != null && "06".equals(map.get("subj_type_code").toString())){
										//结余辅助核算为 贷方发生 - 借方发生
										check_money = subj_dire == 0 ? -1 * check_money : check_money;
										check_money_w = subj_dire == 0 ? -1 * check_money_w : check_money_w;
									}else if(map.get("subj_type_code") != null && "07".equals(map.get("subj_type_code").toString())){
										//结余辅助核算为 借方发生 - 贷方发生
										check_money = subj_dire == 0 ? check_money : -1 * check_money;
										check_money_w = subj_dire == 0 ? check_money_w : -1 * check_money_w;
									}
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
						/*************************组装辅助核算信息******************end**********************/
					}
					vouch_row += 1;  //分录行数加1
				}
				/****************************循环组装凭证预算收支分录信息************end**********************/
				/****************************组装凭证预算结余分录信息****************begin********************/
				//收入科目结余
				if(incomMoney != 0){
					Map<String, Object> debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", att.getCredit_subj_code2());
					debitMap.put("summary", att.getSummary());
					//收入科目结余应是贷方正数 
					debitMap.put("debit", 0);
					debitMap.put("debit_w", 0);
					debitMap.put("credit", incomMoney);
					debitMap.put("credit_w", incomMoney_w);
					detailList.add(debitMap);
					
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
					
					incomMoney = 0.0;
					incomMoney_w = 0.0;
					vouch_row += 1;  //分录行数加1
				}
				//支出科目结余
				if(costMoney != 0){
					Map<String, Object> debitMap = new HashMap<String, Object>();
					debitMap.put("group_id", group_id);
					debitMap.put("hos_id", hos_id); 
					debitMap.put("copy_code", copy_code);
					debitMap.put("vouch_id", vouch_id);
					debitMap.put("vouch_row", vouch_row);
					debitMap.put("subj_code", att.getCredit_subj_code2());
					debitMap.put("summary", att.getSummary());
					//支出科目结余应是借方正数
					debitMap.put("debit", costMoney);
					debitMap.put("debit_w", costMoney_w);
					debitMap.put("credit", 0);
					debitMap.put("credit_w", 0);
					detailList.add(debitMap);

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
							curMap.put("debit", entry.getValue().get("money"));
							curMap.put("debit_w", entry.getValue().get("money_w"));
							curMap.put("credit", 0);
							curMap.put("credit_w", 0);
							vouchCheckList.add(curMap);
							
							check_row += 1;  //辅助核算行数加1
						}
					}
					
					costMoney = 0.0;
					costMoney_w = 0.0;
					vouch_row += 1;  //分录行数加1
				}
				/****************************组装凭证预算结余分录信息****************end**********************/
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
	
	@Override
	public String querySubjAllFirm(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		}
		return JSON.toJSONString(accIncomCostMapper.querySubjAllFirm(entityMap, rowBounds));
	}
}
