/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.storage.back;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedInCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.initial.MedInitChargeMapper;
import com.chd.hrp.med.dao.storage.back.MedStorageBackMapper;
import com.chd.hrp.med.dao.storage.in.MedStorageInMapper;
import com.chd.hrp.med.entity.MedInMain;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.back.MedStorageBackService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 常备药品退货
 * @Table:
 * MED_IN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medStorageBackService")
public class MedStorageBackServiceImpl implements MedStorageBackService {

	private static Logger logger = Logger.getLogger(MedStorageBackServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medInCommonMapper")
	private final MedInCommonMapper medInCommonMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	@Resource(name = "medInitChargeMapper")
	private final MedInitChargeMapper medInitChargeMapper = null;
	@Resource(name = "medStorageBackMapper")
	private final MedStorageBackMapper medStorageBackMapper = null;
	@Resource(name = "medStorageInMapper")
	private final MedStorageInMapper medStorageInMapper = null;
	/**
	 * @Description 
	 * 添加常备药品退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("08005").toString());

			if(entityMap.get("in_date") == null || "".equals(entityMap.get("in_date"))){
				return "{\"error\":\"制单日期不能为空\",\"state\":\"false\"}";
			}
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			//转换日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = medCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断库房是否已启用
			flag = medCommonMapper.existsMedStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成常备药品退货单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MED_IN_MAIN");
				String in_no = medCommonService.getMedNextNo(entityMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				entityMap.put("in_no", in_no);
			}
			
			//业务类型
			if("10".equals(entityMap.get("bus_type_code").toString()) || "16".equals(entityMap.get("bus_type_code").toString()) || "22".equals(entityMap.get("bus_type_code").toString())	){
				entityMap.put("bus_type_code", entityMap.get("bus_type_code"));
			}else{
				entityMap.put("bus_type_code", "12");
			}
			
			//取出主表ID（自增序列）
			entityMap.put("in_id", medInCommonMapper.queryMedInMainSeq());
			StringBuffer invEnoughMsg = new StringBuffer(); 
			int old_in_id = 0;
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(entityMap.get("detailData"))){
				float money_sum = 0;//记录明细总金额
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				
				if(json.getJSONObject(0).containsKey("in_id")){
					old_in_id=(Integer) json.getJSONObject(0).get("in_id");
				}
				
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					//不存在药品ID视为空行
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_id")))) {
						Map<String,Object> detailMap = new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表ID
						detailMap.put("store_id", entityMap.get("store_id"));//库房ID用于判断库存
						//detailMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());//明细ID
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//药品变更号
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						detailMap.put("batch_no",  jsonObj.get("batch_no"));//批号
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))){
							detailMap.put("sell_price",  jsonObj.get("sell_price"));//零售价
						}else{
							detailMap.put("sell_price", "0");//零售价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money"));//零售金额
						}else{
							detailMap.put("sell_money", "0");//零售金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
							detailMap.put("bar_code",  jsonObj.get("bar_code"));//个体码
							detailMap.put("sn",  jsonObj.get("bar_code"));//条形码
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id", "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id",  jsonObj.get("cert_id"));//备注
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}

						/*药品批次信息---------------begin---------------------*/
						//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
							
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString().replace(":,", ":\"\","))){
								//没药品ID视为空行
								if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
									Map<String, Object> invMap = new HashMap<String, Object>();
									invMap.putAll(detailMap);
									invMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());//明细ID
									if (ChdJson.validateJSON(String.valueOf(m.get("batch_sn")))) {invMap.put("batch_sn", m.get("batch_sn"));}
									if (ChdJson.validateJSON(String.valueOf(m.get("amount")))) {invMap.put("amount", m.get("amount"));}
									if (ChdJson.validateJSON(String.valueOf(m.get("amount_money")))) {
										invMap.put("amount_money", m.get("amount_money"));
										money_sum = money_sum + Float.parseFloat(invMap.get("amount_money").toString());//记录总金额
									}
									if (ChdJson.validateJSON(String.valueOf(m.get("sale_money")))) {invMap.put("sale_money", m.get("sale_money"));}
									if (ChdJson.validateJSON(String.valueOf(m.get("sell_money")))) {invMap.put("sell_money", m.get("sell_money"));}
									//该条明细数据添加到List中
									detailList.add(invMap);
								}
							}
						}else{
							//按先进先出匹配批次信息
							List<Map<String, Object>> fifoList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(detailMap));
							Double amount = (-1)*Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
							Double imme_amount = null;
							//按先进先出生成出库单
							for(Map<String, Object> map : fifoList){
								Map<String, Object> invMap = new HashMap<String, Object>();
								invMap.putAll(detailMap);
								invMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());//明细ID
								invMap.put("batch_sn", map.get("batch_sn"));
								//当前批次即时库存
								imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
								//判断当前批号批次是否充足
								if(amount <= imme_amount){
									invMap.put("amount", -1*amount);
									//计算金额
									invMap.put("amount_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
									invMap.put("sale_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
									invMap.put("sell_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
									invMap.put("allot_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
									money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
									//该条明细数据添加到List中
									detailList.add(invMap);
									amount = 0.0;
									break;
								}else{
									//取当前批号批次数量并且申请单数量响应减少
									invMap.put("amount", -1*imme_amount);		
									//计算金额
									invMap.put("amount_money", NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
									invMap.put("sale_money", NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
									invMap.put("sell_money", NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
									invMap.put("allot_money", NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
									money_sum = money_sum + Float.parseFloat(invMap.get("amount_money").toString());//记录总金额
									//该条明细数据添加到List中
									detailList.add(invMap);			
									amount = NumberUtil.sub(amount, imme_amount);
								}
								//当数量为0，证明已经完成先进先出操作
								if(amount == 0){
									break;
								}
							}
							if(amount > 0){
								invEnoughMsg.append(String.valueOf(jsonObj.get("inv_code"))).append(" ").append(String.valueOf(jsonObj.get("inv_name"))).append(",");
							}
						}
						/*药品批次信息---------------end-----------------------*/
					}
				}
				
				if(detailList.size() <= 0){
					return "{\"error\":\"出库单明细数据为空!\"}";
				}
				if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
					return "{\"error\":\""+invEnoughMsg.toString()+"库存药品不足!\"}";
				}
				//判断即时库存是否充足(防止多人同时操作同一药品，额外加的判断)
				String invEnough = medStorageBackMapper.existsMedBackStockInvIsEnough(detailList);
				if(invEnough != null && !"".equals(invEnough)){
					return "{\"error\":\"以下药品库存药品不足!"+invEnough+"\"}";
				}
				if(!"0".equals(old_in_id)){
					entityMap.put("old_in_id", old_in_id);
					entityMap.put("ra_state", entityMap.get("in_id"));
				}
				
				//新增退货主表数据
				medInCommonMapper.addMedInMain(entityMap);
				//新增退货明细数据
				medInCommonMapper.addMedInDetailBatch(detailList);
				//更新冲账状态
				if(!"0".equals(old_in_id)){
					medInCommonMapper.updateMedInMainRaState(entityMap);
				}
				//新增资金来源表
				if(entityMap.get("money_sum") == null){
					entityMap.put("source_money", money_sum);
				}
				medInCommonMapper.insertMedInResource(entityMap);
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStorageBack\"}";
		}
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("in_id").toString()+","+
			entityMap.get("store_id").toString()+","
			+"\"}";
	}
	/**
	 * @Description 
	 * 批量添加常备药品退货<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedStorageBack\"}";
		}
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	}
	
		/**
	 * @Description 
	 * 更新常备药品退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
				return "{\"error\":\"更新失败,单据状态不为未审核状态！.\",\"state\":\"false\"}";
			}
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("08005").toString());

			if(entityMap.get("in_date") == null || "".equals(entityMap.get("in_date"))){
				return "{\"error\":\"入库日期不能为空\",\"state\":\"false\"}";
			}
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			
			//判断库房是否已启用
			int flag = medCommonMapper.existsMedStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//转换日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}

			StringBuffer invEnoughMsg = new StringBuffer(); 
			
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(entityMap.get("detailData"))){
				float money_sum = 0;//记录明细总金额
				//修改明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表编码
						detailMap.put("store_id", entityMap.get("store_id"));//库房ID用于判断库存
						/*if(ChdJson.validateJSON(String.valueOf(jsonObj.get("in_detail_id")))){
							detailMap.put("in_detail_id", jsonObj.get("in_detail_id"));//明细ID
						}else{
							detailMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());//明细ID
						}*/
						detailMap.put("inv_id",  Integer.parseInt(jsonObj.get("inv_id").toString()));//药品ID
						detailMap.put("inv_no",  Integer.parseInt(jsonObj.get("inv_no").toString()));//药品变更号
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						detailMap.put("batch_no",  jsonObj.get("batch_no"));//批号
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))){
							detailMap.put("sell_price",  jsonObj.get("sell_price"));//零售价
						}else{
							detailMap.put("sell_price",  "0");//零售价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money"));//零售金额
						}else{
							detailMap.put("sell_money",  "0");//零售金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
							detailMap.put("bar_code",  jsonObj.get("bar_code"));//个体码
							detailMap.put("sn",  jsonObj.get("bar_code"));//条形码
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id", "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id",  jsonObj.get("cert_id"));//备注
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}

						/*药品批次信息---------------begin---------------------*/
						//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
							
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
								//没药品ID视为空行
								if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
									Map<String, Object> invMap = new HashMap<String, Object>();
									invMap.putAll(detailMap);
									
									invMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());//明细ID
									
									if (ChdJson.validateJSON(String.valueOf(m.get("batch_sn")))) {invMap.put("batch_sn", Integer.parseInt(m.get("batch_sn").toString()));}
									if (ChdJson.validateJSON(String.valueOf(m.get("amount")))) {invMap.put("amount", Double.valueOf(m.get("amount").toString()));}
									if (ChdJson.validateJSON(String.valueOf(m.get("amount_money")))) {
										invMap.put("amount_money", Double.valueOf(m.get("amount_money").toString()));
										money_sum = money_sum + Float.parseFloat(invMap.get("amount_money").toString());//记录总金额
									}
									if (ChdJson.validateJSON(String.valueOf(m.get("sale_money")))) {invMap.put("sale_money", m.get("sale_money"));}
									if (ChdJson.validateJSON(String.valueOf(m.get("sell_money")))) {invMap.put("sell_money", m.get("sell_money"));}
									//该条明细数据添加到List中
									detailList.add(invMap);
								}
							}
						}else{
							//按先进先出匹配批次信息
							List<Map<String, Object>> fifoList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(detailMap));
							Double amount = -1*Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
							Double imme_amount = null;
							//按先进先出生成出库单
							for(Map<String, Object> map : fifoList){
								Map<String, Object> invMap = new HashMap<String, Object>();
								invMap.putAll(detailMap);
								invMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());//明细ID
								invMap.put("batch_sn", Integer.parseInt(map.get("batch_sn").toString()));
								//当前批次即时库存
								imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
								//判断当前批号批次是否充足
								if(amount <= imme_amount){
									invMap.put("amount", -1*Double.valueOf(amount.toString()));
									//计算金额
									invMap.put("amount_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
									invMap.put("sale_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
									invMap.put("sell_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
									invMap.put("allot_money", NumberUtil.numberToRound(-1*amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
									money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
									//该条明细数据添加到List中
									detailList.add(invMap);
									amount = 0.0;
									break;
								}else{
									//取当前批号批次数量并且申请单数量响应减少
									invMap.put("amount", -1*imme_amount);		
									//计算金额
									invMap.put("amount_money", NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
									invMap.put("sale_money", NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
									invMap.put("sell_money", NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
									invMap.put("allot_money", NumberUtil.numberToRound(-1*imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
									money_sum = money_sum + Float.parseFloat(invMap.get("amount_money").toString());//记录总金额
									//该条明细数据添加到List中
									detailList.add(invMap);			
									amount = NumberUtil.sub(amount, imme_amount);
								}
								//当数量为0，证明已经完成先进先出操作
								if(amount == 0){
									break;
								}
							}
							if(amount > 0){
								invEnoughMsg.append(String.valueOf(jsonObj.get("inv_code"))).append(" ").append(String.valueOf(jsonObj.get("inv_name"))).append(",");
							}
						}
						/*药品批次信息---------------end-----------------------*/
						
					}
				}
				
				if(detailList.size() <= 0){
					return "{\"error\":\"出库单明细数据为空!\"}";
				}
				if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
					return "{\"error\":\""+invEnoughMsg.toString()+"库存药品不足!\"}";
				}
				//判断即时库存是否充足(防止多人同时操作同一药品，额外加的判断)
				String invEnough = medStorageBackMapper.existsMedBackStockInvIsEnough(detailList);
				if(invEnough != null && !"".equals(invEnough)){
					return "{\"error\":\"以下药品库存药品不足!"+invEnough+"\"}";
				}
				
				//修改退货主表数据
				medInCommonMapper.updateMedInMain(entityMap);
				//明细先删除再添加
				medInCommonMapper.deleteMedInDetail(entityMap);
				medInCommonMapper.addMedInDetailBatch(detailList);
				//修改资金来源表
				if(entityMap.get("money_sum") == null){
					entityMap.put("source_money", money_sum);
				}
				if(Float.parseFloat(String.valueOf(entityMap.get("old_money_sum") == null ? "0" : entityMap.get("old_money_sum"))) != Float.parseFloat(String.valueOf(entityMap.get("source_money")))){
					medInCommonMapper.updateMedInResource(entityMap);
				}
			}else{
				return "{\"error\":\"更新失败，明细数据为空\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedStorageBack\"}";
		}		
		
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 批量更新常备药品退货，无此业务<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMedStorageBack\"}";
		}	
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 删除常备药品退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			//状态不为新建不能删除，从页面判断
			//期初记账状态不为0的不能删除，从页面判断
			//删除明细
			medInCommonMapper.deleteMedInDetail(entityMap);
			//删除主表
			medInCommonMapper.deleteMedInMain(entityMap);
			//删除资金来源
			medInCommonMapper.deleteMedInResource(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedStorageBack\"}";
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
  }
    
	/**
	 * @Description 
	 * 批量删除常备药品退货<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//状态不为新建不能删除，从页面判断
			//期初记账状态不为0的不能删除，从页面判断
			//批量删除明细表
			medInCommonMapper.deleteMedInDetailBatch(entityList);
			//批量删除主表
			medInCommonMapper.deleteMedInMainBatch(entityList);
			//
			medInCommonMapper.updateMedInMainRaStateNull(entityList);
			//批量删除资金来源
			medInCommonMapper.deleteMedInResourceBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedStorageBack\"}";
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 
	 * 批量复制<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String copyMedStorageBackBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、药品Id
			String copy_code = "", in_ids = "";
			Map<String, Object> inMap = new HashMap<String, Object>();
			//取得所有in_id组装map
			for(Map<String, Object> m : entityMap){
				if(group_id == null){
					group_id = Integer.parseInt(m.get("group_id").toString());
				}
				if(hos_id == null){
					hos_id = Integer.parseInt(m.get("hos_id").toString());
				}
				if("".equals(copy_code)){
					copy_code = m.get("copy_code").toString();
				}
				in_ids = in_ids + m.get("in_id").toString() + ",";
			}
			//组装数据
			inMap.put("group_id", group_id);
			inMap.put("hos_id", hos_id);
			inMap.put("copy_code", copy_code);
			inMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			
			//获取选中的主表、明细表数据
			List<Map<String, Object>> mainList = (List<Map<String, Object>>)medInCommonMapper.queryMedInMainByIds(inMap);
			//List<Map<String, Object>> detailList = (List<Map<String, Object>>)medInCommonMapper.queryMedInDetailByIds(inMap);

			//存放组装数据的List
			List<Map<String, Object>> insertMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> insertDetailList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> insertSourceList = new ArrayList<Map<String,Object>>();
			Map<String, List<Map<String, Object>>> fifoMap = new HashMap<String, List<Map<String,Object>>>(); //存放药品批次信息

			/*复制操作中的固定值-----begin--------*/
			Date date = new Date();
			String[] dates = DateUtil.dateToString(date, "yyyy-MM-dd").split("-");
			String user_id = SessionManager.getUserId();
			int money_para = Integer.valueOf(MyConfig.getSysPara("08005").toString());  //金额位数
			String old_id;
			double money_sum = 0;
			String fifoKey = "";
			StringBuffer invEnoughMsg = new StringBuffer(); 
			//用于查询明细记录
			Map<String, Object> entityDetailMap = new HashMap<String, Object>();
			entityDetailMap.put("group_id", group_id);
			entityDetailMap.put("hos_id", hos_id);
			entityDetailMap.put("copy_code", copy_code);
			/*复制操作中的固定值-----end--------*/
			
			//循环组装数据
			for(Map<String, Object> mainMap : mainList){ 
				old_id = mainMap.get("in_id").toString();//记录旧的in_id用于筛选明细
				mainMap.put("brief", "复制"+mainMap.get("in_no")+"退货单");
				mainMap.put("in_id", medInCommonMapper.queryMedInMainSeq());//替换旧的主表ID（自增序列）
				mainMap.put("in_date", date);//制单日期
				mainMap.put("year", dates[0]);//年份
				mainMap.put("month", dates[1]);//月份
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("maker", user_id);//制单人
				mainMap.put("in_date", date);//制单日期
				mainMap.put("state", 1);//状态（新建状态）
				//重新生成入库单号
				mainMap.put("table_code", "MED_IN_MAIN");
				String in_no = medCommonService.getMedNextNo(mainMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				mainMap.put("in_no", in_no);
				
				//添加到主表新增的list中
				insertMainList.add(mainMap);
				
				entityDetailMap.put("in_id", old_id);
				List<Map<String, Object>> detailList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>)medStorageBackMapper.queryMedInDetailForCopy(entityDetailMap));
				
				/*处理明细-------begin-------*/
				for(Map<String, Object> detailMap : detailList){
					detailMap.put("in_id", mainMap.get("in_id"));//替换旧的主表ID
					detailMap.put("in_no", mainMap.get("in_no"));//替换旧的主表单号
					detailMap.put("store_id", mainMap.get("store_id"));//用于判断库存是否充足

					/**********************按先进先出匹配批次信息************************/
					fifoKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString() + detailMap.get("bar_code").toString() + Double.parseDouble(detailMap.get("price").toString());
					List<Map<String, Object>> fifoList;
					if(fifoMap.get(fifoKey) != null){
						fifoList = fifoMap.get(fifoKey);
					}else{
						fifoList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(detailMap));
					}
					//由于退货数量为负数所以要转成整数判断
					Double amount = -1 * Double.valueOf(String.valueOf((detailMap.get("amount") == null ? 0 : detailMap.get("amount"))));
					Double imme_amount = null;
					List<Map<String, Object>> keyList = new ArrayList<Map<String,Object>>();
					//按先进先出生成出库单
					for(Map<String, Object> map : fifoList){
						//当数量为0，证明已经完成先进先出操作
						if(amount == 0){
							keyList.add(map);
							continue;
						}
						Map<String, Object> invMap = new HashMap<String, Object>();
						invMap.putAll(detailMap);
						invMap.put("batch_sn", map.get("batch_sn").toString());
						//当前批次即时库存
						imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
						//判断当前批号批次是否充足
						if(amount <= imme_amount){
							invMap.put("amount", NumberUtil.numberToString(-1 * amount));
							//计算金额
							invMap.put("amount_money", NumberUtil.numberToString(amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
							invMap.put("sale_money", NumberUtil.numberToString(amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
							invMap.put("sell_money", NumberUtil.numberToString(amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
							invMap.put("allot_money", NumberUtil.numberToString(amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
							money_sum = money_sum + Float.parseFloat(invMap.get("amount_money").toString());//记录总金额

							invMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
							insertDetailList.add(invMap);
							map.put("imme_amount", NumberUtil.sub(imme_amount, amount));
							amount = 0.0;
							keyList.add(map);
							break;
						}else{
							//取当前批号批次数量并且申请单数量响应减少
							invMap.put("amount", NumberUtil.numberToString(-1 * imme_amount));		
							//计算金额
							invMap.put("amount_money", NumberUtil.numberToString(imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
							invMap.put("sale_money", NumberUtil.numberToString(imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
							invMap.put("sell_money", NumberUtil.numberToString(imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
							invMap.put("allot_money", NumberUtil.numberToString(imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
							money_sum = money_sum + Float.parseFloat(invMap.get("amount_money").toString());//记录总金额
			
							invMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
							insertDetailList.add(invMap);			
							amount = NumberUtil.sub(amount, imme_amount);
						}
						//当数量为0，证明已经完成先进先出操作
						if(amount == 0){
							break;
						}
					}
					fifoMap.put(fifoKey, keyList);
					if(amount > 0){
						invEnoughMsg.append(String.valueOf(detailMap.get("inv_code"))).append(" ").append(String.valueOf(detailMap.get("inv_name"))).append(",");
					}
				}
				mainMap.put("source_money", money_sum);
				insertSourceList.add(mainMap);
			}
			if(insertDetailList.size() == 0){
				return "{\"error\":\"所选单据中的药品库存药品不足!\"}";
			}
			if(invEnoughMsg.length() > 0){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存药品不足!\"}";
			}
			//判断即时库存是否充足
			String invEnough = medStorageBackMapper.existsMedBackStockInvIsEnough(insertDetailList);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下药品库存药品不足!"+invEnough+"\"}";
			}
			
			//批量新增主表数据
			medInCommonMapper.addMedInMainBatch(insertMainList);
			//批量新增明细表数据
			medInCommonMapper.addMedInDetailBatch(insertDetailList);
			//批量新增资金来源表数据
			medInCommonMapper.insertMedInResourceBatch(insertSourceList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 copyMedStorageBackBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String auditMedStorageBack(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//批量审核
			medStorageBackMapper.auditOrUnAudit(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMedStorageBack\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String unAuditMedStorageBack(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//批量消审
			medStorageBackMapper.auditOrUnAudit(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMedStorageBack\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 退货确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String confirmMedStorageBack(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//操作用户
			entityMap.put("user_id", SessionManager.getUserId());
			//因存储过程为批量确认故需要该参数
			entityMap.put("in_ids", entityMap.get("in_id"));
			
			//校验入库单状态
			int flag = medStorageInMapper.existsMedInStateForConfirm(entityMap);
			if(flag != 0){
				return "{\"error\":\"单据已确认，请勿重复操作！\",\"state\":\"false\"}";
			}
			
			//批量退货确认
			medInCommonMapper.confirmCommonInBack(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 confirmMedStorageBack\"}";
		}	

		return entityMap.get("msg") == null ? "" : entityMap.get("msg").toString();
	}
	
	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String auditMedStorageBackBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//批量审核
			medStorageBackMapper.auditOrUnAuditBatch(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMedStorageBackBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String unAuditMedStorageBackBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//批量消审
			medStorageBackMapper.auditOrUnAuditBatch(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMedStorageBackBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量退货确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String confirmMedStorageBackBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、退货单Id、药品Id
			String copy_code = "", in_ids = "",confirm_date="";
			//取得所有in_id组装map
			for(Map<String, Object> m : entityMap){
				if(group_id == null){
					group_id = Integer.parseInt(m.get("group_id").toString());
				}
				if(hos_id == null){
					hos_id = Integer.parseInt(m.get("hos_id").toString());
				}
				if("".equals(copy_code)){
					copy_code = m.get("copy_code").toString();
				}
				if("".equals(confirm_date)){
					confirm_date = m.get("confirm_date").toString();
				}
				in_ids = in_ids + m.get("in_id").toString() + ",";
			}
			//组装数据
			inMap.put("group_id", group_id);
			inMap.put("hos_id", hos_id);
			inMap.put("copy_code", copy_code);
			inMap.put("user_id", SessionManager.getUserId());
			inMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			//String confirm_date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			inMap.put("confirm_date", confirm_date);
			
			//校验入库单状态
			int flag = medStorageInMapper.existsMedInStateForConfirm(inMap);
			if(flag != 0){
				return "{\"error\":\"单据已确认，请勿重复操作！\",\"state\":\"false\"}";
			}
			
			//批量退货确认
			medInCommonMapper.confirmCommonInBack(inMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 confirmMedStorageBackBatch\"}";
		}	

		return inMap.get("msg") == null ? "" : inMap.get("msg").toString();
	}
	
	/**
	 * @Description 
	 * 添加常备药品退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象常备药品退货
		MedInMain medInMain = queryByCode(entityMap);

		if (medInMain != null) {

			int state = medStorageBackMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medStorageBackMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedStorageBack\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集常备药品退货<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = medStorageBackMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medStorageBackMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象常备药品退货<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medInCommonMapper.queryMedInMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象常备药品退货<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String, Object>> list = medStorageBackMapper.queryMedStorageBackDetailByCode(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * @Description 
	 * 获取常备药品退货<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedCommonIn
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medStorageBackMapper.queryByUniqueness(entityMap);
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Description 入库单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryIn(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) medStorageBackMapper.queryIn(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medStorageBackMapper.queryIn(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	/**
	 * @Description 入库单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryInDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) medStorageBackMapper.queryInDetail(entityMap);
			
		return ChdJson.toJsonLower(list);
	}
	

	
	
	//退库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMedInByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			//查询入库主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=medStorageInMapper.queryMedInPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=medStorageInMapper.queryMedInPrintTemlateByDetail(entityMap);
				
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				Map<String,Object> map=medStorageInMapper.queryMedInPrintTemlateByMain(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=medStorageInMapper.queryMedInPrintTemlateByDetail(entityMap);
				
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 入库单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryBackDetailByImp(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) medStorageBackMapper.queryBackDetailByImp(entityMap);
			
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 查询明细
	 */
	@Override
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) medStorageBackMapper.queryDetails(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medStorageBackMapper.queryDetails(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
}
