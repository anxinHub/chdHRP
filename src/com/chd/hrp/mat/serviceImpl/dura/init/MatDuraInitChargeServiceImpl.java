/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.dura.init;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraDeptBalanceMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraDeptBarMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraDeptSurplusMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraStoreBalanceMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraStoreBarMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraStoreSurplusMapper;
import com.chd.hrp.mat.dao.dura.init.MatDuraInitChargeMapper;
import com.chd.hrp.mat.service.dura.init.MatDuraInitChargeService;

/**
 * @Description:  耐用品期初记账
 * @Table: MAT_DURA_(STORE/DEPT)_REG
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matDuraInitChargeService")
public class MatDuraInitChargeServiceImpl implements MatDuraInitChargeService {

	private static Logger logger = Logger.getLogger(MatDuraInitChargeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matDuraInitChargeMapper")
	private final MatDuraInitChargeMapper matDuraInitChargeMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matDuraStoreBalanceMapper")
	private final MatDuraStoreBalanceMapper matDuraStoreBalanceMapper = null;
	@Resource(name = "matDuraStoreBarMapper")
	private final MatDuraStoreBarMapper matDuraStoreBarMapper = null;
	@Resource(name = "matDuraStoreSurplusMapper")
	private final MatDuraStoreSurplusMapper matDuraStoreSurplusMapper = null;
	@Resource(name = "matDuraDeptBalanceMapper")
	private final MatDuraDeptBalanceMapper matDuraDeptBalanceMapper = null;
	@Resource(name = "matDuraDeptBarMapper")
	private final MatDuraDeptBarMapper matDuraDeptBarMapper = null;
	@Resource(name = "matDuraDeptSurplusMapper")
	private final MatDuraDeptSurplusMapper matDuraDeptSurplusMapper = null;

	/**
	 * @Description 
	 * 获取左侧树形结构<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryTree(Map<String,Object> entityMap) throws DataAccessException{
		
		return JsonListMapUtil.listToJson(matDuraInitChargeMapper.queryTree(entityMap));
	}
	
	/**
	 * @Description 
	 * 记账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public synchronized String save(Map<String,Object> entityMap)throws DataAccessException{
		
		try {

			if(entityMap.get("charge_date") == null || "".equals(entityMap.get("charge_date"))){
				return "{\"error\":\"记账日期不能为空\",\"state\":\"false\"}";
			}
			//转换日期格式
			entityMap.put("year", entityMap.get("charge_date").toString().substring(0, 4));
			entityMap.put("month", entityMap.get("charge_date").toString().substring(5, 7));
			entityMap.put("charge_date", DateUtil.stringToDate(entityMap.get("charge_date").toString(), "yyyy-MM-dd"));
			
			//记账人
			entityMap.put("user_id", SessionManager.getUserId());
			//状态
			entityMap.put("state", 1);
			//备注
			entityMap.put("note", "结账");
			
			//获取启用年月到物流结账年月之间的期间数据
			List<Map<String,Object>> periodList = JsonListMapUtil.toListMapLower(matCommonMapper.getFinishedYearMonthList(entityMap));
			int period_size = periodList.size();
			int index = 1;
			
			//临时集合用于list过大时拆分list
			List<Map<String, Object>> tmpList = new ArrayList<Map<String,Object>>();
			int tmpIndex = 0;
			
			//判断状态
			int flag = 0;
			/*//记账日期是否大于当前账套起始日期
			flag =  matCommonMapper.existsDateCheckBefore(entityMap);
			if(flag > 0){
				return "{\"error\":\"记账日期不能大于当前账套起始日期.\",\"state\":\"false\"}";
			}*/
			//检测记账的物资期间是否存在
			flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"请配置物资期间表.\",\"state\":\"false\"}";
			}
			if(entityMap.get("type") != null && "0".equals(entityMap.get("type").toString())){
				entityMap.put("store_id", entityMap.get("charge_code"));
				//仓库是否可记账
				flag =  matDuraInitChargeMapper.existsStoreAccount(entityMap);
				if(flag == 0){
					return "{\"error\":\"所选仓库已记账.\",\"state\":\"false\"}";
				}
				//查询是否存在未确认的期初入库单据
				flag = matDuraInitChargeMapper.existsStoreInitNotCheck(entityMap);
				if(flag > 0){
					return "{\"error\":\"含有未审核的库房期初耐用品数据.\",\"state\":\"false\"}";
				}
				
				//取出期初库存数据
				List<Map<String, Object>> balanceAddList = matDuraInitChargeMapper.queryStoreBalanceAddListForCharge(entityMap);
				List<Map<String, Object>> balanceUpdateList = matDuraInitChargeMapper.queryStoreBalanceUpdateListForCharge(entityMap);
				
				List<Map<String, Object>> surplusBeginList = matDuraInitChargeMapper.queryStoreSurplusListForCharge(entityMap);
				List<Map<String, Object>> surplusPeriodList = matDuraInitChargeMapper.queryStoreSurplusPeriodListForCharge(entityMap);
				
				//取出期初条码数据
				//List<Map<String, Object>> barList = matDuraInitChargeMapper.queryStoreInvBarListForCharge(entityMap);

				//取出所选的记账库房
				List<Map<String, Object>> storeList = matDuraInitChargeMapper.queryStoreListForChange(entityMap); 
				
				if(surplusBeginList.size() > 0){
					//组装结余表数据
					List<Map<String, Object>> surAddList = new ArrayList<Map<String,Object>>();
					List<Map<String, Object>> surUpdateList = new ArrayList<Map<String,Object>>();
					String initKey = "";
					String periodKey = "";
					boolean is_add = false;
					double begin_amount = 0, begin_money = 0, end_amount = 0, end_money = 0;
					for(Map<String, Object> stockMap : surplusBeginList){
						//期初数据
						Map<String, Object> initMap = new HashMap<String, Object>();
						initMap.putAll(stockMap);
						surAddList.add(initMap);
						end_amount = Double.valueOf(stockMap.get("end_amount").toString());
						end_money = Double.valueOf(stockMap.get("end_money").toString());
						
						index = 1;  //初始化循环次数
						//补已结账数据
						for(Map<String, Object> periodMap : periodList){
							initKey = initMap.get("group_id").toString() + initMap.get("hos_id").toString() + 
									initMap.get("copy_code").toString() + periodMap.get("acc_year").toString() + 
									periodMap.get("acc_month").toString() + initMap.get("store_id").toString() + 
									initMap.get("inv_id").toString() + initMap.get("price") + initMap.get("bar_code").toString();
							//如果期初记账前存在该材料的转移记录则需修改该记录的信息即可不需要增加新记录
							if(surplusPeriodList.size() > 0){
								//循环历史记录
								for(Map<String, Object> surplusMap : surplusPeriodList){
									
									begin_amount = end_amount;
									begin_money = end_money;
									
									periodKey = surplusMap.get("group_id").toString() + surplusMap.get("hos_id").toString() + 
											surplusMap.get("copy_code").toString() + surplusMap.get("year").toString() + 
											surplusMap.get("month").toString() + surplusMap.get("store_id").toString() + 
											surplusMap.get("inv_id").toString() + surplusMap.get("price") + surplusMap.get("bar_code").toString();
									
									if(initKey.equals(periodKey)){
										
										Map<String, Object> startMap = new HashMap<String, Object>();
										
										startMap.put("group_id", stockMap.get("group_id"));
										startMap.put("hos_id", stockMap.get("hos_id"));
										startMap.put("copy_code", stockMap.get("copy_code"));
										startMap.put("year", periodMap.get("acc_year"));
										startMap.put("month", periodMap.get("acc_month"));
										startMap.put("store_id", stockMap.get("store_id"));
										startMap.put("inv_id", stockMap.get("inv_id"));
										startMap.put("price", stockMap.get("price"));
										startMap.put("bar_code", stockMap.get("bar_code"));
										startMap.put("begin_amount", begin_amount);
										startMap.put("begin_money", begin_money);
										startMap.put("in_amount", surplusMap.get("in_amount"));
										startMap.put("in_money", surplusMap.get("in_money"));
										startMap.put("out_amount", surplusMap.get("out_amount"));
										startMap.put("out_money", surplusMap.get("out_money"));
										if(index < period_size){
											end_amount = NumberUtil.sub(NumberUtil.add(end_amount, Double.valueOf(surplusMap.get("in_amount").toString())), Double.valueOf(surplusMap.get("out_amount").toString()));
											end_money = NumberUtil.sub(NumberUtil.add(end_money, Double.valueOf(surplusMap.get("in_money").toString())), Double.valueOf(surplusMap.get("out_money").toString()));
											startMap.put("end_amount", end_amount);
											startMap.put("end_money", end_money);
										}else{
											startMap.put("end_amount", 0);
											startMap.put("end_money", 0);
										}
										is_add = false;
										surUpdateList.add(startMap);
									}
								}
							}
							//is_add为true则不存在该月份的转移记录需新增
							if(is_add){
								Map<String, Object> startMap = new HashMap<String, Object>();
								
								startMap.put("group_id", stockMap.get("group_id"));
								startMap.put("hos_id", stockMap.get("hos_id"));
								startMap.put("copy_code", stockMap.get("copy_code"));
								startMap.put("year", periodMap.get("acc_year"));
								startMap.put("month", periodMap.get("acc_month"));
								startMap.put("store_id", stockMap.get("store_id"));
								startMap.put("inv_id", stockMap.get("inv_id"));
								startMap.put("price", stockMap.get("price"));
								startMap.put("bar_code", stockMap.get("bar_code"));
								startMap.put("begin_amount", end_amount);
								startMap.put("begin_money", end_amount);
								startMap.put("in_amount", 0);
								startMap.put("in_money", 0);
								startMap.put("out_amount", 0);
								startMap.put("out_money", 0);
								if(index < period_size){
									startMap.put("end_amount", end_amount);
									startMap.put("end_money", end_money);
								}else{
									startMap.put("end_amount", 0);
									startMap.put("end_money", 0);
								}
								surAddList.add(startMap);
							}
							index ++;
						}
					}
					
					//新增结存表数据
					if(balanceAddList.size() > 0){
						if(balanceAddList.size() > 1000){
							tmpIndex = 0;
							tmpList = new ArrayList<Map<String,Object>>();
							for(Map<String, Object> tmpMap : balanceAddList){
								tmpIndex ++;
								tmpList.add(tmpMap);
								if(tmpIndex == 1000){
									matDuraStoreBalanceMapper.addBatch(tmpList);
									tmpIndex = 0;
									tmpList = new ArrayList<Map<String,Object>>();
								}
							}
							if(tmpList.size() > 0){
								matDuraStoreBalanceMapper.addBatch(tmpList);
							}
						}else{
							matDuraStoreBalanceMapper.addBatch(balanceAddList);
						}
					}
					if(balanceUpdateList.size() > 0){
						if(balanceUpdateList.size() > 1000){
							tmpIndex = 0;
							tmpList = new ArrayList<Map<String,Object>>();
							for(Map<String, Object> tmpMap : balanceUpdateList){
								tmpIndex ++;
								tmpList.add(tmpMap);
								if(tmpIndex == 1000){
									matDuraStoreBalanceMapper.updateBatch(tmpList);
									tmpIndex = 0;
									tmpList = new ArrayList<Map<String,Object>>();
								}
							}
							if(tmpList.size() > 0){
								matDuraStoreBalanceMapper.updateBatch(tmpList);
							}
						}else{
							matDuraStoreBalanceMapper.updateBatch(balanceUpdateList);
						}
					}
					//新增结余表数据
					if(surAddList.size() > 0){
						if(surAddList.size() > 1000){
							tmpIndex = 0;
							tmpList = new ArrayList<Map<String,Object>>();
							for(Map<String, Object> tmpMap : surAddList){
								tmpIndex ++;
								tmpList.add(tmpMap);
								if(tmpIndex == 1000){
									matDuraStoreSurplusMapper.addBatch(tmpList);
									tmpIndex = 0;
									tmpList = new ArrayList<Map<String,Object>>();
								}
							}
							if(tmpList.size() > 0){
								matDuraStoreSurplusMapper.addBatch(tmpList);
							}
						}else{
							matDuraStoreSurplusMapper.addBatch(surAddList);
						}
					}
					if(surUpdateList.size() > 0){
						if(surUpdateList.size() > 1000){
							tmpIndex = 0;
							tmpList = new ArrayList<Map<String,Object>>();
							for(Map<String, Object> tmpMap : surUpdateList){
								tmpIndex ++;
								tmpList.add(tmpMap);
								if(tmpIndex == 1000){
									matDuraStoreSurplusMapper.updateBatch(tmpList);
									tmpIndex = 0;
									tmpList = new ArrayList<Map<String,Object>>();
								}
							}
							if(tmpList.size() > 0){
								matDuraStoreSurplusMapper.updateBatch(tmpList);
							}
						}else{
							matDuraStoreSurplusMapper.updateBatch(surUpdateList);
						}
					}
				}

				//新增库房记账数据
				matDuraInitChargeMapper.addStore(storeList);
				
			}else if(entityMap.get("type") != null && "1".equals(entityMap.get("type").toString())){
				entityMap.put("dept_id", entityMap.get("charge_code"));
				//科室是否可记账
				flag =  matDuraInitChargeMapper.existsDeptAccount(entityMap);
				if(flag == 0){
					return "{\"error\":\"所选科室已记账.\",\"state\":\"false\"}";
				}
				//查询是否存在未确认的期初入库单据
				flag = matDuraInitChargeMapper.existsDeptInitNotCheck(entityMap);
				if(flag > 0){
					return "{\"error\":\"含有未审核的科室期初耐用品数据.\",\"state\":\"false\"}";
				}
				
				//取出期初库存数据
				List<Map<String, Object>> balanceAddList = matDuraInitChargeMapper.queryDeptBalanceAddListForCharge(entityMap);
				List<Map<String, Object>> balanceUpdateList = matDuraInitChargeMapper.queryDeptBalanceUpdateListForCharge(entityMap);
				
				List<Map<String, Object>> surplusBeginList = matDuraInitChargeMapper.queryDeptSurplusListForCharge(entityMap);
				List<Map<String, Object>> surplusPeriodList = matDuraInitChargeMapper.queryDeptSurplusPeriodListForCharge(entityMap);
				
				//取出期初条码数据
				//List<Map<String, Object>> barList = matDuraInitChargeMapper.queryDeptInvBarListForCharge(entityMap);
				
				//取出所选的记账科室
				List<Map<String, Object>> deptList = matDuraInitChargeMapper.queryDeptListForChange(entityMap); 
				
				if(surplusBeginList.size() > 0){
					//组装结余表数据
					List<Map<String, Object>> surAddList = new ArrayList<Map<String,Object>>();
					List<Map<String, Object>> surUpdateList = new ArrayList<Map<String,Object>>();
					String initKey = "";
					String periodKey = "";
					boolean is_add = true;
					double begin_amount = 0, begin_money = 0, end_amount = 0, end_money = 0;
					for(Map<String, Object> stockMap : surplusBeginList){
						//期初数据
						Map<String, Object> initMap = new HashMap<String, Object>();
						initMap.putAll(stockMap);
						surAddList.add(initMap);
						end_amount = Double.valueOf(stockMap.get("end_amount").toString());
						end_money = Double.valueOf(stockMap.get("end_money").toString());
						
						index = 1;  //初始化循环次数
						//补已结账数据
						for(Map<String, Object> periodMap : periodList){
							initKey = initMap.get("group_id").toString() + initMap.get("hos_id").toString() + 
									initMap.get("copy_code").toString() + periodMap.get("acc_year").toString() + 
									periodMap.get("acc_month").toString() + initMap.get("dept_id").toString() + 
									initMap.get("inv_id").toString() + initMap.get("price") + initMap.get("bar_code").toString();
							//如果期初记账前存在该材料的转移记录则需修改该记录的信息即可不需要增加新记录
							if(surplusPeriodList.size() > 0){
								//循环历史记录
								for(Map<String, Object> surplusMap : surplusPeriodList){
									
									begin_amount = end_amount;
									begin_money = end_money;
									
									periodKey = surplusMap.get("group_id").toString() + surplusMap.get("hos_id").toString() + 
											surplusMap.get("copy_code").toString() + surplusMap.get("year").toString() + 
											surplusMap.get("month").toString() + surplusMap.get("dept_id").toString() + 
											surplusMap.get("inv_id").toString() + surplusMap.get("price") + surplusMap.get("bar_code").toString();
									
									if(initKey.equals(periodKey)){
										
										Map<String, Object> startMap = new HashMap<String, Object>();
										
										startMap.put("group_id", stockMap.get("group_id"));
										startMap.put("hos_id", stockMap.get("hos_id"));
										startMap.put("copy_code", stockMap.get("copy_code"));
										startMap.put("year", periodMap.get("acc_year"));
										startMap.put("month", periodMap.get("acc_month"));
										startMap.put("dept_id", stockMap.get("dept_id"));
										startMap.put("inv_id", stockMap.get("inv_id"));
										startMap.put("price", stockMap.get("price"));
										startMap.put("bar_code", stockMap.get("bar_code"));
										startMap.put("begin_amount", begin_amount);
										startMap.put("begin_money", begin_money);
										startMap.put("in_amount", surplusMap.get("in_amount"));
										startMap.put("in_money", surplusMap.get("in_money"));
										startMap.put("out_amount", surplusMap.get("out_amount"));
										startMap.put("out_money", surplusMap.get("out_money"));
										if(index < period_size){
											end_amount = NumberUtil.sub(NumberUtil.add(end_amount, Double.valueOf(surplusMap.get("in_amount").toString())), Double.valueOf(surplusMap.get("out_amount").toString()));
											end_money = NumberUtil.sub(NumberUtil.add(end_money, Double.valueOf(surplusMap.get("in_money").toString())), Double.valueOf(surplusMap.get("out_money").toString()));
											startMap.put("end_amount", end_amount);
											startMap.put("end_money", end_money);
										}else{
											startMap.put("end_amount", 0);
											startMap.put("end_money", 0);
										}
										is_add = false;
										surUpdateList.add(startMap);
									}
								}
							}
							//is_add为true则不存在该月份的转移记录需新增
							if(is_add){
								Map<String, Object> startMap = new HashMap<String, Object>();
								
								startMap.put("group_id", stockMap.get("group_id"));
								startMap.put("hos_id", stockMap.get("hos_id"));
								startMap.put("copy_code", stockMap.get("copy_code"));
								startMap.put("year", periodMap.get("acc_year"));
								startMap.put("month", periodMap.get("acc_month"));
								startMap.put("dept_id", stockMap.get("dept_id"));
								startMap.put("inv_id", stockMap.get("inv_id"));
								startMap.put("price", stockMap.get("price"));
								startMap.put("bar_code", stockMap.get("bar_code"));
								startMap.put("begin_amount", end_amount);
								startMap.put("begin_money", end_amount);
								startMap.put("in_amount", 0);
								startMap.put("in_money", 0);
								startMap.put("out_amount", 0);
								startMap.put("out_money", 0);
								if(index < period_size){
									startMap.put("end_amount", end_amount);
									startMap.put("end_money", end_money);
								}else{
									startMap.put("end_amount", 0);
									startMap.put("end_money", 0);
								}
								surAddList.add(startMap);
							}
							index ++;
						}
					}
					
					//新增结存表数据
					if(balanceAddList.size() > 0){
						if(balanceAddList.size() > 1000){
							tmpIndex = 0;
							tmpList = new ArrayList<Map<String,Object>>();
							for(Map<String, Object> tmpMap : balanceAddList){
								tmpIndex ++;
								tmpList.add(tmpMap);
								if(tmpIndex == 1000){
									matDuraDeptBalanceMapper.addBatch(tmpList);
									tmpIndex = 0;
									tmpList = new ArrayList<Map<String,Object>>();
								}
							}
							if(tmpList.size() > 0){
								matDuraDeptBalanceMapper.addBatch(tmpList);
							}
						}else{
							matDuraDeptBalanceMapper.addBatch(balanceAddList);
						}
					}
					if(balanceUpdateList.size() > 0){
						if(balanceUpdateList.size() > 1000){
							tmpIndex = 0;
							tmpList = new ArrayList<Map<String,Object>>();
							for(Map<String, Object> tmpMap : balanceUpdateList){
								tmpIndex ++;
								tmpList.add(tmpMap);
								if(tmpIndex == 1000){
									matDuraDeptBalanceMapper.updateBatch(tmpList);
									tmpIndex = 0;
									tmpList = new ArrayList<Map<String,Object>>();
								}
							}
							if(tmpList.size() > 0){
								matDuraDeptBalanceMapper.updateBatch(tmpList);
							}
						}else{
							matDuraDeptBalanceMapper.updateBatch(balanceUpdateList);
						}
					}
					//新增结余表数据
					if(surAddList.size() > 0){
						if(surAddList.size() > 1000){
							tmpIndex = 0;
							tmpList = new ArrayList<Map<String,Object>>();
							for(Map<String, Object> tmpMap : surAddList){
								tmpIndex ++;
								tmpList.add(tmpMap);
								if(tmpIndex == 1000){
									matDuraDeptSurplusMapper.addBatch(tmpList);
									tmpIndex = 0;
									tmpList = new ArrayList<Map<String,Object>>();
								}
							}
							if(tmpList.size() > 0){
								matDuraDeptSurplusMapper.addBatch(tmpList);
							}
						}else{
							matDuraDeptSurplusMapper.addBatch(surAddList);
						}
					}
					if(surUpdateList.size() > 0){
						if(surUpdateList.size() > 1000){
							tmpIndex = 0;
							tmpList = new ArrayList<Map<String,Object>>();
							for(Map<String, Object> tmpMap : surUpdateList){
								tmpIndex ++;
								tmpList.add(tmpMap);
								if(tmpIndex == 1000){
									matDuraDeptSurplusMapper.updateBatch(tmpList);
									tmpIndex = 0;
									tmpList = new ArrayList<Map<String,Object>>();
								}
							}
							if(tmpList.size() > 0){
								matDuraDeptSurplusMapper.updateBatch(tmpList);
							}
						}else{
							matDuraDeptSurplusMapper.updateBatch(surUpdateList);
						}
					}
				}

				//新增科室记账数据
				matDuraInitChargeMapper.addDept(deptList);
			}else{
				return "{\"error\":\"请选择库房或者科室再进行记账\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"记账失败\"}");
		}
		
		return "{\"msg\":\"记账成功\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量删除<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//暂无该业务
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatDuraInitCharge\"}";
		}	
	}
}
