/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.serviceImpl.storage.out;

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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

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
import com.chd.hrp.med.dao.base.MedApplyOutRelaMapper;
import com.chd.hrp.med.dao.base.MedBatchBalanceMapper;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedFifoBalanceMapper;
import com.chd.hrp.med.dao.base.MedInvBalanceMapper;
import com.chd.hrp.med.dao.base.MedInvHoldMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.base.MedOutResourceMapper;
import com.chd.hrp.med.dao.storage.out.MedOutDetailMapper;
import com.chd.hrp.med.dao.storage.out.MedOutMainMapper;
import com.chd.hrp.med.entity.MedBatchBalance;
import com.chd.hrp.med.entity.MedFifoBalance;
import com.chd.hrp.med.entity.MedInvBalance;
import com.chd.hrp.med.entity.MedInvHold;
import com.chd.hrp.med.entity.MedOutDetail;
import com.chd.hrp.med.entity.MedOutMain;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.out.MedOutMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: MED_OUT_MAIN
 * @Table: MED_OUT_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("medOutMainService")
public class MedOutMainServiceImpl implements MedOutMainService {

	private static Logger logger = Logger.getLogger(MedOutMainServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "medOutMainMapper")
	private final MedOutMainMapper medOutMainMapper = null;

	// 引入DAO操作
	@Resource(name = "medOutDetailMapper")
	private final MedOutDetailMapper medOutDetailMapper = null;
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	@Resource(name = "medOutResourceMapper")
	private final MedOutResourceMapper medOutResourceMapper = null;
	@Resource(name = "medApplyOutRelaMapper")
	private final MedApplyOutRelaMapper medApplyOutRelaMapper = null;
	
	//四个账表
	@Resource(name = "medInvHoldMapper")
	private final MedInvHoldMapper medInvHoldMapper = null;
	@Resource(name = "medFifoBalanceMapper")
	private final MedFifoBalanceMapper medFifoBalanceMapper = null;
	@Resource(name = "medBatchBalanceMapper")
	private final MedBatchBalanceMapper medBatchBalanceMapper = null;
	@Resource(name = "medInvBalanceMapper")
	private final MedInvBalanceMapper medInvBalanceMapper = null;
	
	/*
	//耐用品
	@Resource(name = "medDuraTranStoreDeptMapper")
	private final MedDuraTranStoreDeptMapper medDuraTranStoreDeptMapper = null;
	@Resource(name = "medDuraStoreBalanceMapper")
	private final MedDuraStoreBalanceMapper medDuraStoreBalanceMapper = null;
	@Resource(name = "medDuraStoreSurplusMapper")
	private final MedDuraStoreSurplusMapper medDuraStoreSurplusMapper = null;
	@Resource(name = "medDuraStoreBarMapper")
	private final MedDuraStoreBarMapper medDuraStoreBarMapper = null;
	@Resource(name = "medDuraDeptBalanceMapper")
	private final MedDuraDeptBalanceMapper medDuraDeptBalanceMapper = null;
	@Resource(name = "medDuraDeptSurplusMapper")
	private final MedDuraDeptSurplusMapper medDuraDeptSurplusMapper = null;
	@Resource(name = "medDuraDeptBarMapper")
	private final MedDuraDeptBarMapper medDuraDeptBarMapper = null;
	*/
	
	/**
	 * @Description 添加MED_OUT_MAIN<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("08005").toString());
			// 保存主表数据
			entityMap = defaultValue(entityMap);
			String out_date = (String) entityMap.get("out_date");
			entityMap.put("year", out_date.substring(0, 4));
			entityMap.put("month", out_date.substring(5, 7));
			entityMap.put("day", out_date.substring(8, 10));  //用于生成单号
			
			boolean isBack = false;  //是否退库
			 
			int flag = 1;
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = medCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			// (2)、判断库房是否已启用
			flag = medCommonMapper.existsMedStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//保存申领日期  2017/08/10 即墨需求 gaopei 
			
			if(entityMap.get("app_date") != null && !"".equals(entityMap.get("app_date").toString())){
				
				entityMap.put("app_date", DateUtil.stringToDate(entityMap.get("app_date").toString(), "yyyy-MM-dd HH:mm:ss"));   
			}else {
				
				entityMap.put("app_date", null);   
			}
			
			// 查询序列
			Long out_id = medOutMainMapper.queryMedOutMainSeq();
			entityMap.put("out_id", out_id);
			// 获取出库单号
			entityMap.put("table_code", "MED_OUT_MAIN");
			String out_no = medCommonService.getMedNextNo(entityMap);
			if(out_no.indexOf("error") > 0){
				return out_no;
			}
			entityMap.put("out_no", out_no);
			
			long in_id =0;//定向出库使用 记录入库单ID
			float money_sum = 0;//记录明细总金额

			// 保存明细数据
			List<Map<String, Object>> out_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray out_detail_json = JSONArray.parseArray((String) entityMap.get("out_detail_data"));
			
			StringBuffer invEnoughMsg = new StringBuffer(); 
			Iterator out_detail_it = out_detail_json.iterator();
			while (out_detail_it.hasNext()) {

				Map<String, Object> mapDetailVo = defaultDetailValue();

				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("out_id", entityMap.get("out_id").toString());
				mapDetailVo.put("out_no", entityMap.get("out_no").toString());
				//用于批次查询
				mapDetailVo.put("store_id", entityMap.get("store_id").toString());
				mapDetailVo.put("dept_id", entityMap.get("dept_id").toString());  //用于退库的判断
				mapDetailVo.put("bus_type_code", entityMap.get("bus_type_code").toString());

				JSONObject jsonObj = JSONObject.parseObject(out_detail_it.next().toString());
				
				
				//不存在药品ID视为空行
				if (validateJSON(String.valueOf(jsonObj.get("inv_id")))) {
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					/*药品主信息-----------------begin---------------------*/
					if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}else{
						mapDetailVo.put("batch_no", "-");//货位
					}
					if (validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}else{
						mapDetailVo.put("bar_code", "-");
					}
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {mapDetailVo.put("price", jsonObj.get("price").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("sale_price")))) {mapDetailVo.put("sale_price", jsonObj.get("sale_price").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("sell_price")))) {mapDetailVo.put("sell_price", jsonObj.get("sell_price").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("inva_date")))) {mapDetailVo.put("inva_date", jsonObj.get("inva_date").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {mapDetailVo.put("disinfect_date", jsonObj.get("disinfect_date").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("location_id")))) {
						mapDetailVo.put("location_id", jsonObj.get("location_id").toString());
					}else{
						mapDetailVo.put("location_id", "0");//货位
					}
					if (validateJSON(String.valueOf(jsonObj.get("note")))) {mapDetailVo.put("note", jsonObj.get("note").toString());}
					/*药品主信息-----------------end-----------------------*/
					
					//判断是否为退库（只要数量有负数存在极为退库）
					if(!isBack && Double.valueOf(String.valueOf(jsonObj.get("amount"))) < 0){
						isBack = true;
					}
					/*药品批次信息---------------begin---------------------*/
					
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && validateJSON(String.valueOf(jsonObj.get("amount"))) && validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){

						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							
							//没药品ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								detailMap.putAll(mapDetailVo);

								detailMap.put("out_detail_id", medOutDetailMapper.queryMedOutDetailSeq());
								if (validateJSON(String.valueOf(m.get("batch_sn")))) {detailMap.put("batch_sn", m.get("batch_sn").toString());}
								if (validateJSON(String.valueOf(m.get("amount")))) {detailMap.put("amount", m.get("amount").toString());}
								if (validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
									money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								}
								if (validateJSON(String.valueOf(m.get("sale_money")))) {detailMap.put("sale_money", m.get("sale_money").toString());}
								if (validateJSON(String.valueOf(m.get("sell_money")))) {detailMap.put("sell_money", m.get("sell_money").toString());}
								out_detail_batch.add(detailMap);
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;
						
						int is_defeat = 1;
						if(amount < 0){
							is_defeat = -1; //需按先出先退生成出库单
						}
						amount = is_defeat * amount;
						
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							detailMap.putAll(mapDetailVo);
							
							detailMap.put("out_detail_id", medOutDetailMapper.queryMedOutDetailSeq());
							detailMap.put("batch_sn", map.get("batch_sn").toString());
							if(is_defeat < 0){
								//当前批次已出库数量
								imme_amount = Double.valueOf(String.valueOf((map.get("out_amount") == null ? 0 : map.get("out_amount"))));
							}else{
								//当前批次即时库存
								imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
							}
							//判断当前批号批次是否充足
							if(amount <= imme_amount){
								detailMap.put("amount", String.valueOf(is_defeat * amount));
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								out_detail_batch.add(detailMap);
								amount = 0.0;
								break;
							}else{
								//取当前批号批次数量并且申请单数量响应减少
								detailMap.put("amount", String.valueOf(is_defeat * imme_amount));		
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								out_detail_batch.add(detailMap);			
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
			
			if(out_detail_batch.size() <= 0){
				return "{\"error\":\"出库单明细数据为空!\"}";
			}
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存药品不足!\"}";
			}
			//判断即时库存是否充足(防止多人同时操作同一药品，额外加的判断)
			String invEnough = "";
			if(isBack){
				//退库判断科室领用是否充足
				invEnough = medCommonMapper.existsMedStockInvIsAppEnough(out_detail_batch);
				
				if(invEnough != null && !"".equals(invEnough)){
					return "{\"error\":\"以下药品领用数量不足，不能退库!"+invEnough+"\"}";
				}
			}else{
				//出库判断仓库即时库存是否充足
				invEnough = medCommonMapper.existsMedStockInvIsEnough(out_detail_batch);
				
				if(invEnough != null && !"".equals(invEnough)){
					return "{\"error\":\"以下药品库存药品不足!"+invEnough+"\"}";
				}
			}
			
			medOutMainMapper.add(entityMap);// 保存主表

			medOutDetailMapper.addBatch(out_detail_batch);// 保存明细表
			
			entityMap.put("source_money", money_sum);
			
			medOutResourceMapper.add(entityMap);// 保存资金来源
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("out_id").toString()+","+
			entityMap.get("store_id").toString()+","
			+"\"}";
	}

	/**
	 * @Description 批量添加MED_OUT_MAIN<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			medOutMainMapper.addBatch(entityList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}

		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 更新MED_OUT_MAIN<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("08005").toString());
			// 保存主表数据
			entityMap = defaultValue(entityMap);

			String out_date = (String) entityMap.get("out_date");

			entityMap.put("year", out_date.substring(0, 4));

			entityMap.put("month", out_date.substring(5, 7));
			
			entityMap.put("day", out_date.substring(8, 10));
			
			boolean isBack = false;  //是否退库
			
			int flag = 1;
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = medCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"修改失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			// (2)、判断库房是否已启用
			flag = medCommonMapper.existsMedStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}

			//保存申领日期  2017/08/10 即墨需求 gaopei 
			
			if(entityMap.get("app_date") != null && !"".equals(entityMap.get("app_date").toString())){
				
				entityMap.put("app_date", DateUtil.stringToDate(entityMap.get("app_date").toString(), "yyyy-MM-dd HH:mm:ss"));   
			}else {
				
				entityMap.put("app_date", null);   
			}
			
			float money_sum = 0;//记录明细总金额

			// 保存明细数据
			List<Map<String, Object>> out_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray out_detail_json = JSONArray.parseArray((String) entityMap.get("out_detail_data"));
			StringBuffer invEnoughMsg = new StringBuffer();
			Iterator out_detail_it = out_detail_json.iterator();
			
			while (out_detail_it.hasNext()) {

				Map<String, Object> mapDetailVo = defaultDetailValue();

				mapDetailVo.put("group_id", Integer.parseInt(entityMap.get("group_id").toString()));
				mapDetailVo.put("hos_id", Integer.parseInt(entityMap.get("hos_id").toString()));
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("out_id", Integer.parseInt(entityMap.get("out_id").toString()));
				mapDetailVo.put("out_no", entityMap.get("out_no").toString());
				
				mapDetailVo.put("store_id", entityMap.get("store_id"));
				mapDetailVo.put("dept_id", entityMap.get("dept_id").toString());  //用于退库的判断
				mapDetailVo.put("bus_type_code", entityMap.get("bus_type_code"));

				JSONObject jsonObj = JSONObject.parseObject(out_detail_it.next().toString());
				//不存在药品ID视为空行
				if (validateJSON(String.valueOf(jsonObj.get("inv_id")))) {
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					/*药品主信息-----------------begin---------------------*/
					if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}else{
						mapDetailVo.put("batch_no", "-");
					}
					if (validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}else{
						mapDetailVo.put("bar_code", "-");
					}
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {mapDetailVo.put("price", jsonObj.get("price").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("sale_price")))) {mapDetailVo.put("sale_price", jsonObj.get("sale_price").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("sell_price")))) {mapDetailVo.put("sell_price", jsonObj.get("sell_price").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("inva_date")))) {mapDetailVo.put("inva_date", jsonObj.get("inva_date").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {mapDetailVo.put("disinfect_date", jsonObj.get("disinfect_date").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("location_id")))) {
						mapDetailVo.put("location_id", jsonObj.get("location_id").toString());
					}else{
						mapDetailVo.put("location_id", "0");//货位
					}
					if (validateJSON(String.valueOf(jsonObj.get("note")))) {mapDetailVo.put("note", jsonObj.get("note").toString());}
					/*药品主信息-----------------end-----------------------*/
					
					//判断是否为退库（只要数量有负数存在极为退库）
					if(!isBack && Double.valueOf(String.valueOf(jsonObj.get("amount"))) < 0){
						isBack = true;
					}
					
					/*药品批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && validateJSON(String.valueOf(jsonObj.get("amount"))) && validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没药品ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								detailMap.putAll(mapDetailVo);
								
								if (validateJSON(String.valueOf(m.get("out_detail_id")))) {
									detailMap.put("out_detail_id", Integer.parseInt(m.get("out_detail_id").toString()));
								}else{
									detailMap.put("out_detail_id", Integer.parseInt(String.valueOf(medOutDetailMapper.queryMedOutDetailSeq()).toString()));
								}
								if (validateJSON(String.valueOf(m.get("batch_sn")))) {detailMap.put("batch_sn", m.get("batch_sn").toString());}
								if (validateJSON(String.valueOf(m.get("amount")))) {detailMap.put("amount", m.get("amount").toString());}
								if (validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
									money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								}
								if (validateJSON(String.valueOf(m.get("sale_money")))) {detailMap.put("sale_money", m.get("sale_money").toString());}
								if (validateJSON(String.valueOf(m.get("sell_money")))) {detailMap.put("sell_money", m.get("sell_money").toString());}
								out_detail_batch.add(detailMap);
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;

						int is_defeat = 1;
						if(amount < 0){
							is_defeat = -1; //需按先出先退生成出库单
						}
						amount = is_defeat * amount;
						
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							detailMap.putAll(mapDetailVo);

							detailMap.put("out_detail_id", Integer.parseInt(String.valueOf(medOutDetailMapper.queryMedOutDetailSeq()).toString()));
							detailMap.put("batch_sn", map.get("batch_sn").toString());
							if(is_defeat < 0){
								//当前批次已出库数量
								imme_amount = Double.valueOf(String.valueOf((map.get("out_amount") == null ? 0 : map.get("out_amount"))));
							}else{
								//当前批次即时库存
								imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
							}
							//判断当前批号批次是否充足
							if(amount <= imme_amount){
								detailMap.put("amount", String.valueOf(is_defeat * amount));
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(is_defeat * amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								out_detail_batch.add(detailMap);
								amount = 0.0;
								break;
							}else{
								//取当前批号批次数量并且申请单数量响应减少
								detailMap.put("amount", String.valueOf(is_defeat * imme_amount));		
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								out_detail_batch.add(detailMap);			
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
			if(out_detail_batch.size() <= 0){
				
				return "{\"error\":\"出库单明细数据为空!\"}";
			}
			
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				
				return "{\"error\":\""+invEnoughMsg.substring(0, invEnoughMsg.length()-1).toString()+"库存药品不足!\"}";
			}

			//判断即时库存是否充足(防止多人同时操作同一药品，额外加的判断)
			String invEnough = "";
			if(isBack){
				//退库判断科室领用是否充足
				invEnough = medCommonMapper.existsMedStockInvIsAppEnough(out_detail_batch);
				
				if(invEnough != null && !"".equals(invEnough)){
					return "{\"error\":\"以下药品领用数量不足，不能退库!"+invEnough+"\"}";
				}
			}else{
				//出库判断仓库即时库存是否充足
				invEnough = medCommonMapper.existsMedStockInvIsEnough(out_detail_batch);
			}
			
			medOutMainMapper.update(entityMap);// 保存主表
			
			medOutDetailMapper.delete(entityMap);

			medOutDetailMapper.addBatch(out_detail_batch);// 保存明细表
			//删除对应关系表
			/*List<Map<String,Object>> relaList= JsonListMapUtil.toListMapLower(medOutDetailMapper.queryMedApplyOutRela(out_detail_batch));
			
			if(relaList.size()>0){
				
				medOutDetailMapper.deleteMedApplyOutRela(relaList);
			
			}*/
			
			entityMap.put("source_money", money_sum);
			
			medOutResourceMapper.update(entityMap);// 保存资金来源
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"修改失败\"}");
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 方法 update\"}";
		}
		
		return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 批量更新MED_OUT_MAIN<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			medOutMainMapper.updateBatch(entityList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}

		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

	}

	/**
	 * @Description 删除MED_OUT_MAIN<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = medOutMainMapper.delete(entityMap);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}

		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 复制<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String copyMedOutMain(List<Map<String, Object>> entityMap) throws DataAccessException {

		List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放主表信息
		List<Map<String, Object>> detailList =new ArrayList<Map<String, Object>>();//存放明细表信息
		Map<String, List<Map<String, Object>>> fifoMap = new HashMap<String, List<Map<String,Object>>>(); //存放药品批次信息

		//金额位数
		int money_para = Integer.valueOf(MyConfig.getSysPara("08005").toString());
		String user_id = SessionManager.getUserId();
		String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);  //用于生成单号
		String fifoKey = "";
		StringBuffer invEnoughMsg = new StringBuffer(); 
		boolean isBack = false;  //是否退库
		
		try {

			for(Map<String, Object> tmp:entityMap){
				
				MedOutMain mom = (MedOutMain) medOutMainMapper.queryByCode(tmp);
				
				Map<String, Object> mainMap = defaultBeanToMap(mom);
				mainMap.put("year", year);
				mainMap.put("month", month);
				mainMap.put("day", day);  //用于生成单号
				// 获取出库单号
				mainMap.put("table_code", "MED_OUT_MAIN");
				String out_no = medCommonService.getMedNextNo(mainMap);
				if(out_no.indexOf("error") > 0){
					return out_no;
				}
				mainMap.put("out_no", out_no);
				
				Long out_id = medOutMainMapper.queryMedOutMainSeq();// 查询序列
				
				mainMap.put("out_id",out_id);mainMap.put("state", "1");mainMap.put("is_dir", "0");

				mainMap.put("maker", user_id);mainMap.put("out_date", date);
				
				mainMap.put("checker", "");mainMap.put("check_date", "");
				
				mainMap.put("confirmer", "");mainMap.put("confirm_date", "");

				mainMap.put("brief", "复制"+mom.getOut_no()+"出库单");
				
				mainList.add(mainMap);
				
				float money_sum = 0;//记录明细总金额
				//查询明细表
				List<Map<String, Object>> list_detail = JsonListMapUtil.toListMapLower(medOutDetailMapper.queryDetailForCopy(tmp));

				for(Map<String, Object> mod : list_detail){
					Map<String, Object> mapDetailVo = defaultDetailValue();
					if(ChdJson.validateJSON(String.valueOf(mod.get("inva_date")))){
						mod.put("inva_date", DateUtil.dateToString((Date)mod.get("inva_date"), "yyyy-MM-dd"));
					}
					if(ChdJson.validateJSON(String.valueOf(mod.get("disinfect_date")))){
						mod.put("disinfect_date", DateUtil.dateToString((Date)mod.get("disinfect_date"), "yyyy-MM-dd"));
					}
					mapDetailVo.putAll(mod);
					mapDetailVo.put("out_id", out_id);
					mapDetailVo.put("out_no", out_no);
					mapDetailVo.put("store_id", mainMap.get("store_id"));  //用于库存查询
					mapDetailVo.put("dept_id", mainMap.get("dept_id"));  //用于退库的判断
					mapDetailVo.put("bus_type_code", mainMap.get("bus_type_code"));  //用于库存查询

					/**********************按先进先出匹配批次信息************************/
					fifoKey = mapDetailVo.get("inv_id").toString() + mapDetailVo.get("batch_no").toString() + mapDetailVo.get("bar_code").toString() + Double.parseDouble(mapDetailVo.get("price").toString());
					List<Map<String, Object>> fifoList;
					if(fifoMap.get(fifoKey) != null){
						fifoList = fifoMap.get(fifoKey);
					}else{
						fifoList = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(mapDetailVo));
					}
					Double amount = Double.valueOf(String.valueOf((mapDetailVo.get("amount") == null ? 0 : mapDetailVo.get("amount"))));
					Double imme_amount = null;
					
					//判断是否为退库（只要数量有负数存在极为退库）
					if(!isBack && amount < 0){
						isBack = true;
					}
					
					int is_defeat = 1;
					if(amount < 0){
						is_defeat = -1; //需按先出先退生成出库单
					}
					amount = is_defeat * amount;
					
					List<Map<String, Object>> keyList = new ArrayList<Map<String,Object>>();
					//按先进先出生成出库单
					for(Map<String, Object> map : fifoList){
						//当数量为0，证明已经完成先进先出操作
						if(amount == 0){
							keyList.add(map);
							continue;
						}
						Map<String, Object> detailMap = new HashMap<String, Object>();
						detailMap.putAll(mapDetailVo);
						
						detailMap.put("out_detail_id", medOutDetailMapper.queryMedOutDetailSeq());
						detailMap.put("batch_sn", map.get("batch_sn").toString());
						if(is_defeat < 0){
							//当前批次已出库数量
							imme_amount = Double.valueOf(String.valueOf((map.get("out_amount") == null ? 0 : map.get("out_amount"))));
						}else{
							//当前批次即时库存
							imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
						}
						//判断当前批号批次是否充足
						if(amount <= imme_amount){
							detailMap.put("amount", String.valueOf(is_defeat * amount));
							//计算金额
							detailMap.put("amount_money", NumberUtil.numberToString(is_defeat * amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
							detailMap.put("sale_money", NumberUtil.numberToString(is_defeat * amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
							detailMap.put("sell_money", NumberUtil.numberToString(is_defeat * amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
							detailMap.put("allot_money", NumberUtil.numberToString(is_defeat * amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
							money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
							detailList.add(detailMap);
							if(is_defeat < 0){
								map.put("out_amount", NumberUtil.sub(imme_amount, amount));
							}else{
								map.put("imme_amount", NumberUtil.sub(imme_amount, amount));
							}
							amount = 0.0;
							keyList.add(map);
							break;
						}else{
							//取当前批号批次数量并且申请单数量响应减少
							detailMap.put("amount", String.valueOf(is_defeat * imme_amount));		
							//计算金额
							detailMap.put("amount_money", NumberUtil.numberToString(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para));
							detailMap.put("sale_money", NumberUtil.numberToString(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para));
							detailMap.put("sell_money", NumberUtil.numberToString(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para));
							detailMap.put("allot_money", NumberUtil.numberToString(is_defeat * imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para));
							money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
							detailList.add(detailMap);			
							amount = NumberUtil.sub(amount, imme_amount);
						}
					}
					fifoMap.put(fifoKey, keyList);
					if(amount > 0){
						invEnoughMsg.append(String.valueOf(mapDetailVo.get("inv_code"))).append(" ").append(String.valueOf(mapDetailVo.get("inv_name"))).append(",");
					}
				}
				mainMap.put("source_money", money_sum);
			}
			
			if(detailList.size() == 0){
				return "{\"error\":\"所选单据中的药品库存药品不足!\"}";
			}
			if(invEnoughMsg.length() > 0){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存药品不足!\"}";
			}
			
			//判断即时库存是否充足(防止多人同时操作同一药品，额外加的判断)
			String invEnough = "";
			if(isBack){
				//退库判断科室领用是否充足
				invEnough = medCommonMapper.existsMedStockInvIsAppEnough(detailList);
				
				if(invEnough != null && !"".equals(invEnough)){
					return "{\"error\":\"以下药品领用数量不足，不能退库!"+invEnough+"\"}";
				}
			}else{
				//出库判断仓库即时库存是否充足
				invEnough = medCommonMapper.existsMedStockInvIsEnough(detailList);
				
				if(invEnough != null && !"".equals(invEnough)){
					return "{\"error\":\"以下药品库存药品不足!"+invEnough+"\"}";
				}
			}
			
			medOutMainMapper.addBatch(mainList);
			medOutDetailMapper.addBatch(detailList);
			medOutResourceMapper.addBatch(mainList);//保存主表资金来源

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"复制失败 数据库异常 请联系管理员! 方法 copyMedOutMain\"}";
		}
		
		return "{\"msg\":\"复制成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 冲账<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String offsetMedOutMain(List<Map<String, Object>> entityMap) throws DataAccessException {

		List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放med_out_main字段
		
		List<Map<String, Object>> detailList =new ArrayList<Map<String, Object>>();//存放med_out_detail字段
		
		Integer is_dir = 0;
		
		String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);  //用于生成单号
		
		try {

			List<Map<String, Object>> listVo = entityMap;
			
			for(Map<String, Object> tmp:entityMap){
				
				MedOutMain mom = (MedOutMain) medOutMainMapper.queryByCode(tmp);
				
				Map<String, Object> mainMap = defaultBeanToMap(mom);
				
				Long out_id = medOutMainMapper.queryMedOutMainSeq();// 查询序列
				
				mainMap.put("out_id",out_id);
				
				mainMap.put("state", "1");
				
				mainMap.put("is_dir", "0");
				
				mainMap.put("maker", tmp.get("maker"));
				mainMap.put("out_date", date);
				mainMap.put("year", year);
				mainMap.put("month", month);
				mainMap.put("day", day);  //用于生成单号
				mainMap.put("checker", "");
				mainMap.put("check_date", "");
				mainMap.put("confirmer", "");
				mainMap.put("confirm_date", "");
				mainMap.put("bus_type_code", "21");
				mainMap.put("brief", "冲"+mom.getOut_no()+"出库单");
				// 获取出库单号
				mainMap.put("table_code", "MED_OUT_MAIN");
				String out_no = medCommonService.getMedNextNo(mainMap);
				if(out_no.indexOf("error") > 0){
					return out_no;
				}
				mainMap.put("out_no", out_no);
				
				mainList.add(mainMap);
				
				float money_sum = 0;//记录明细总金额
				
				//查询明细表
				List<MedOutDetail> list_detail = (List<MedOutDetail>)medOutDetailMapper.query(tmp);

				for(MedOutDetail mod:list_detail){
					
					Map<String, Object> detailMap = detailBeanToMap(mod);

					detailMap.put("out_id", out_id);
					detailMap.put("out_no", out_no);
					
					detailMap.put("out_detail_id", medOutDetailMapper.queryMedOutDetailSeq());

					money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
					
					detailMap.put("amount", -mod.getAmount());
					
					detailMap.put("sell_money", -mod.getSell_money());
					
					if(mod.getAllot_money() != null){
						detailMap.put("allot_money", -mod.getAllot_money());
					}else{
						detailMap.put("allot_money", "0.0");
					}
					
					
					detailMap.put("amount_money", -mod.getAmount_money());
					
					detailList.add(detailMap);

				}
				
				mainMap.put("source_money", -money_sum);
			}
			
			medOutMainMapper.addBatch(mainList);
			
			medOutDetailMapper.addBatch(detailList);
			
			medOutResourceMapper.addBatch(mainList);//保存主表资金来源

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"冲账失败 数据库异常 请联系管理员! 方法 offsetMedOutMain\"}";
		}
		
		return "{\"msg\":\"冲账成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 批量删除MED_OUT_MAIN<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			
			//删除申请单与出库单对应关系
			medApplyOutRelaMapper.deleteMedApplyOutRelaBatch(entityList);
			//删除主表资金来源
			medOutResourceMapper.deleteBatch(entityList);
			//删除明细
			medOutDetailMapper.deleteBatch(entityList);
			//删除主表
			medOutMainMapper.deleteBatch(entityList);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 添加MED_OUT_MAIN<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象MED_OUT_MAIN
		MedOutMain medOutMain = queryByCode(entityMap);

		if (medOutMain != null) {

			int state = medOutMainMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = medOutMainMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集MED_OUT_MAIN<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		

		if (sysPage.getTotal() == -1) {

			List<MedOutMain> list = (List<MedOutMain>) medOutMainMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<MedOutMain> list = (List<MedOutMain>) medOutMainMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	
	/**
	 * @Description 查询结果集MED_OUT_MAIN<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedOutMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMain(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}
	
	/**
	 * @Description 查询结果集med_Out_Main<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedInMainByIsDir(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedInMainByIsDir(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedInMainByIsDir(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}
	
	/**
	 * @Description 查询结果集med_Out_Detail<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedInDetailByIsDir(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedInDetailByIsDir(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedInDetailByIsDir(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象MED_OUT_MAIN<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return medOutMainMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取MED_OUT_MAIN<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return MedOutMain
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {

		return medOutMainMapper.queryByUniqueness(entityMap);

	}

	/**
	 * @Description 获取结果集<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的条件
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Description 查询med_inv_hold 表 返回药品 用来计算库存<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedOutMainByInvHold(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMainByInvHold(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMainByInvHold(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	/**
	 * @Description 查询med_fifo_balance 表 返回药品 用来计算库存 不分页<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedOutMainByFifoBalance(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			//页面选择药品的类型
			String flag = String.valueOf(entityMap.get("flag"));
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			if (flag!=null && "fifo".equals(flag)) {//没有选择明细数据 走先进先出
				if(entityMap.get("mainData") != null){
					//保存明细数据
					JSONArray json = JSONArray.parseArray((String)entityMap.get("mainData"));
					Iterator it = json.iterator();
					while (it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
							//当前数据的出库数量
							double amount = Double.parseDouble(String.valueOf(jsonObj.get("amount")));
							Map<String, Object> mapVo = new HashMap<String, Object>();
							mapVo.put("group_id", entityMap.get("group_id"));
							mapVo.put("hos_id", entityMap.get("hos_id"));
							mapVo.put("copy_code", entityMap.get("copy_code"));
							mapVo.put("show_history", entityMap.get("show_history"));
							mapVo.put("store_id", entityMap.get("store_id"));
							mapVo.put("inv_id", jsonObj.get("inv_id"));
							mapVo.put("amount", jsonObj.get("amount"));
							//获取该药品各批次数据
							List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(medOutMainMapper.queryMedOutMainByFifoBalance(mapVo));
							if(list.size()>0){
								for (Map<String, Object> map : list) {
									Map<String, Object> viewMap = map;
									//本批次药品剩余量
									double  imme_amount = Double.parseDouble(String.valueOf(viewMap.get("imme_amount")));
									//本批次数量充足直接出库
									if(imme_amount >= amount){
										viewMap.put("amount", amount);
										returnList.add(viewMap);
										break;
									}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
										viewMap.put("amount", imme_amount);
										returnList.add(viewMap);
										amount = NumberUtil.sub(amount, imme_amount);
									}
								}
							}
						}
					}
				}
				return ChdJson.toJsonLower(returnList);
			}else {
				List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMainByFifoBalance(entityMap);
				return ChdJson.toJsonLower(list);
			}	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMainByFifoBalance(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	
	
	
	@Override
	public String queryMedOutMainByFifoBalanceOld(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			//页面选择药品的类型
			String flag = String.valueOf(entityMap.get("flag"));
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			if (flag!=null && "fifo".equals(flag)) {//没有选择明细数据 走先进先出
				//当前数据的出库数量
				double amount = Double.parseDouble(String.valueOf(entityMap.get("amount")));
				//获取该药品各批次数据
				List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMainByFifoBalance(entityMap);
				if (list.size() > 0) {
					for (Map<String, Object> map : list) {
						Map<String, Object> viewMap = new HashMap<String, Object>();
						// 首先转换成小写的KEY
						for (String key : map.keySet()) {viewMap.put(key.toLowerCase(), map.get(key));}
						//本批次药品剩余量
						double  imme_amount = Double.parseDouble(String.valueOf(viewMap.get("imme_amount")));
						//本批次数量充足直接出库
						if(imme_amount >= amount){
							viewMap.put("amount", amount);
							returnList.add(viewMap);
							break;
						}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
							viewMap.put("amount", imme_amount);
							returnList.add(viewMap);
							amount = NumberUtil.sub(amount, imme_amount);
						}
					}
				}
				return ChdJson.toJsonLower(returnList);
			}else if("matched".equals(flag)){
				//当前数据的出库数量
				double amount = Double.parseDouble(String.valueOf(entityMap.get("amount")));
				// 解析明细数据组合查询条件
				JSONArray detail_json = JSONArray.parseArray((String) entityMap.get("detail_data"));
				Iterator detail_it = detail_json.iterator();
				try {
					while (detail_it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(detail_it.next().toString());
						Map<String,Object> mapDetailVo =new HashMap<String,Object>();
						mapDetailVo.put("group_id", entityMap.get("group_id"));
						mapDetailVo.put("hos_id", entityMap.get("hos_id"));
						mapDetailVo.put("copy_code", entityMap.get("copy_code"));
						mapDetailVo.put("inv_id", jsonObj.get("inv_id"));
						mapDetailVo.put("inv_no", jsonObj.get("inv_no"));
						mapDetailVo.put("store_id", jsonObj.get("store_id"));
						mapDetailVo.put("store_no", jsonObj.get("store_no"));
						StringBuffer inv_detail_data = null;
						//获取该药品各批次数据
						List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMainByFifoBalance(mapDetailVo);
						if (list.size() > 0) {
							for (Map<String, Object> map : list) {
								Map<String, Object> viewMap = new HashMap<String, Object>();
								// 首先转换成小写的KEY
								for (String key : map.keySet()) {viewMap.put(key.toLowerCase(), map.get(key));}
								//本批次药品剩余量
								double  imme_amount = Double.parseDouble(String.valueOf(viewMap.get("imme_amount")));
								//本批次数量充足直接出库
								if(imme_amount >= amount){
									viewMap.put("amount", amount);
									returnList.add(viewMap);
									break;
								}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
									viewMap.put("amount", imme_amount);
									returnList.add(viewMap);
									amount = NumberUtil.sub(amount, imme_amount);
								}
							}
						}
					}
					return ChdJson.toJsonLower(returnList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMainByFifoBalance(entityMap);
				return ChdJson.toJsonLower(list);
			}
			return "";
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedOutMainByFifoBalance(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	/**
	 * @Description 配套导入结果集<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedOutDetailByMatch(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//当前数据的出库数量
			double amount;
			double num = Double.parseDouble(String.valueOf(entityMap.get("num")));
			StringBuffer msg = new StringBuffer();
			List<Map<String, Object>> detailData = new ArrayList<Map<String,Object>>();
			// 解析明细数据组合查询条件
			JSONArray detail_json = JSONArray.parseArray((String) entityMap.get("detail_data"));
			Iterator detail_it = detail_json.iterator();
			while (detail_it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(detail_it.next().toString());
				Map<String,Object> mapDetailVo =new HashMap<String,Object>();
				//出库数量 = 套数 * 配套表药品数量
				amount = num * Double.parseDouble(String.valueOf(jsonObj.get("amount")));
				mapDetailVo.put("group_id", entityMap.get("group_id"));
				mapDetailVo.put("hos_id", entityMap.get("hos_id"));
				mapDetailVo.put("copy_code", entityMap.get("copy_code"));
				mapDetailVo.put("store_id", entityMap.get("store_id"));
				mapDetailVo.put("inv_id", jsonObj.get("inv_id"));
				mapDetailVo.put("inv_code", jsonObj.get("inv_code"));
				mapDetailVo.put("inv_name", jsonObj.get("inv_name"));
				mapDetailVo.put("amount", amount);
				mapDetailVo.put("sum_amount", amount);
				mapDetailVo.put("cur_amount", 0);
				mapDetailVo.put("imma_amount", 0);
				
				StringBuffer inv_detail_data = new StringBuffer();  //存储批次明细信息的json串
				String batchBar = "";  //用于判断批号条码是否相同
				String newBatchBar = "";  //用于判断批号条码是否相同
				//获取该药品各批次数据
				List<Map<String, Object>> list = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(mapDetailVo));
				if (list.size() > 0) {
					for (Map<String, Object> map : list) {
						newBatchBar = map.get("batch_no") == null ? "" : map.get("batch_no").toString() + map.get("bar_code") == null ? "" : map.get("bar_code").toString();
						//药品是否已出完
						if(amount > 0){
							/*药品主信息---------------begin----------------------*/
							if(!batchBar.equals(newBatchBar)){
								mapDetailVo.put("inv_no", map.get("inv_no"));
								mapDetailVo.put("inv_code", map.get("inv_code"));
								mapDetailVo.put("inv_name", map.get("inv_name"));
								mapDetailVo.put("inv_model", map.get("inv_model"));
								mapDetailVo.put("unit_name", map.get("unit_name"));
								mapDetailVo.put("med_type_name", map.get("med_type_name"));
								mapDetailVo.put("fac_name", map.get("fac_name"));
								mapDetailVo.put("batch_no", map.get("batch_no"));
								mapDetailVo.put("bar_code", map.get("bar_code"));
								mapDetailVo.put("price", map.get("price"));
								mapDetailVo.put("sale_price", map.get("sale_price"));
								mapDetailVo.put("sell_price", map.get("sell_price"));
								mapDetailVo.put("location_id", map.get("location_id"));
								mapDetailVo.put("location_code", map.get("location_code"));
								mapDetailVo.put("location_name", map.get("location_name"));
								mapDetailVo.put("inva_date", map.get("inva_date"));
								mapDetailVo.put("disinfect_date", map.get("disinfect_date"));
								mapDetailVo.put("cur_amount", map.get("cur_amount"));
								mapDetailVo.put("imme_amount", map.get("imme_amount"));
								mapDetailVo.put("amount_money", amount*Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))));
								mapDetailVo.put("sale_money", amount*Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))));
								mapDetailVo.put("sell_money", amount*Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))));
								inv_detail_data.append("[");
							}else{
								//库存信息累加
								mapDetailVo.put("cur_amount", Double.valueOf(mapDetailVo.get("cur_amount").toString())+Double.valueOf(map.get("cur_amount").toString()));
								mapDetailVo.put("imme_amount", Double.valueOf(mapDetailVo.get("imme_amount").toString())+Double.valueOf(map.get("imme_amount").toString()));
							}
							/*药品主信息---------------end------------------------*/
							/*药品批次信息-------------begin----------------------*/
							inv_detail_data.append("{\"inv_id\":").append(map.get("inv_id"))
								.append(",\"inv_no\":").append(map.get("inv_no"))
								.append(",\"inv_code\":\"").append(map.get("inv_code"))
								.append("\",\"inv_name\":\"").append(map.get("inv_name"))
								.append("\",\"batch_sn\":").append(map.get("batch_sn"))
								.append(",\"cur_amount\":").append(map.get("cur_amount"))
								.append(",\"imme_amount\":").append(map.get("imme_amount"))
								.append(",\"price\":").append(map.get("price"))
								.append(",\"sale_price\":").append(map.get("sale_price"))
								.append(",\"sell_price\":").append(map.get("sell_price"));
							
							//本批次药品剩余量
							double  imme_amount = Double.parseDouble(String.valueOf(map.get("imme_amount")));
							
							//本批次数量充足直接出库
							if(amount <= imme_amount){
								inv_detail_data.append(",\"amount\":").append(amount)
									.append(",\"amount_money\":").append(amount*Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))))
									.append(",\"sale_money\":").append(amount*Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))))
									.append(",\"sell_money\":").append(amount*Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))))
									.append("},");
								amount = 0;
							}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
								inv_detail_data.append(",\"amount\":").append(imme_amount)
									.append(",\"amount_money\":").append(imme_amount*Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))))
									.append(",\"sale_money\":").append(imme_amount*Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))))
									.append(",\"sell_money\":").append(imme_amount*Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))))
									.append("},");
								amount = NumberUtil.sub(amount, imme_amount);
							}
							/*药品批次信息-------------end------------------------*/
						}else{
							/*药品主信息---------------begin----------------------*/
							if(!batchBar.equals(newBatchBar)){
								inv_detail_data.substring(0, inv_detail_data.length()-1);
								inv_detail_data.append("]");
								mapDetailVo.put("inv_detail_data", inv_detail_data.toString());
								detailData.add(mapDetailVo);
								break;
							}else{
								//库存信息累加
								mapDetailVo.put("cur_amount", Double.valueOf(mapDetailVo.get("cur_amount").toString())+Double.valueOf(map.get("cur_amount").toString()));
								mapDetailVo.put("imme_amount", Double.valueOf(mapDetailVo.get("imme_amount").toString())+Double.valueOf(map.get("imme_amount").toString()));
							}
							/*药品主信息---------------end----------------------*/
						}
						//记录批号条码信息
						batchBar = newBatchBar;
					}
					if(batchBar.equals(newBatchBar)){
						inv_detail_data.substring(0, inv_detail_data.length()-1);
						inv_detail_data.append("]");
						mapDetailVo.put("inv_detail_data", inv_detail_data.toString());
						detailData.add(mapDetailVo);
					}
				}else{
					msg.append(mapDetailVo.get("inv_code")).append(" ").append(mapDetailVo.get("inv_name")).append("药品库存不足").append("<br>");
				}
			}
			
			//返回库存不足的药品
			if(msg.toString().length() > 0){
				return "{\"error\":\""+msg.toString()+"\"}";
			}
			return ChdJson.toJsonLower(detailData);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"配套导入失败 数据库异常 请联系管理员! 方法 queryMedOutDetailByMatch\"}";
		}
	}

	/**
	 * @Description 定向出库结果集<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedOutDetailByIsDir(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			return ChdJson.toJsonLower(medOutDetailMapper.queryMedOutDetailByIsDir(entityMap));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"配套导入失败 数据库异常 请联系管理员! 方法 queryMedOutDetailByIsDir\"}";
		}
	}

	@Override
	public String queryMedOutDetailHistory(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutDetailMapper.queryMedOutDetailByHistory(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutDetailMapper.queryMedOutDetailByHistory(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	/**
	 * @Description 
	 * 消审功能<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String unAuditMedOutMain(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放med_out_main字段
		
		try {

			List<Map<String, Object>> listVo = entityMap;
			
			for(Map<String, Object> tmp:entityMap){

				tmp.put("state", "1");

				mainList.add(tmp);
				
			}
			
			medOutMainMapper.updateAuditBatch(mainList);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"消审失败 数据库异常 请联系管理员! 方法 unAuditMedOutMain\"}";

		}
		
		return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 
	 * 审核功能<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String auditMedOutMain(List<Map<String, Object>> entityMap) throws DataAccessException {

		List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放med_out_main字段
		
		try {

			List<Map<String, Object>> listVo = entityMap;
			
			for(Map<String, Object> tmp:entityMap){
				
				tmp.put("state", "2");

				mainList.add(tmp);
				
			}
			
			medOutMainMapper.updateAuditBatch(mainList);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"审核失败 数据库异常 请联系管理员! 方法 auditMedOutMain\"}";

		}
		
		return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
	}

/**	出库确认

    (1)、判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能出库确认！

    (2)、只有审核状态的单据允许出库确认

    (3)、除21科室退库业务之外，其他出库业务 判断药品出库数量是否大于当前库存，若大于，提示：库存不足，出库确认失败！

    (4)、21科室退库业务：判断药品出库数量是否能容纳这么多退库数量。

    (5)、按照先进先出算法，将出库单数量写入物流出库结存表 MED_FIFO_BALANCE的出库数量中；

          （定向出库不走 先进先出算法，根据出入库对应关系表 MED_IN_OUT_RELA 中 业务类型=3 的关联关系进行整单出库）

    (6)、同时，将当月出库数量累加到库存结余表：药品批次结余表 MED_BATCH_BALANCE；药品药品结存表 MED_INV_HOLD；药品结余表 MED_INV_BALANCE

    (7)、修改出库单状态为出库确认（state=3） ，以及确认人，确认日期，年，月，*/

	@Override
	public synchronized String confirmOutMedOutMain(List<Map<String, Object>> entityList) throws DataAccessException {
		try {

			//校验出库单状态
			int flag = medOutMainMapper.existsMedOutStateForConfirm(entityList);
			if(flag != 0){
				return "{\"error\":\"所选单据中含已确认单据，请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			
			//获取当前日期所在会计期间
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("group_id", SessionManager.getGroupId());
			checkMap.put("hos_id", SessionManager.getHosId());
			checkMap.put("copy_code", SessionManager.getCopyCode());
			checkMap.put("date", new Date());
			String yearMonth = medCommonMapper.queryAccYearMonthByDate(checkMap);
			if(yearMonth == null || "".equals(yearMonth)){
				return "{\"error\":\"操作失败，对应期间不存在请配置！\",\"state\":\"false\"}";
			}
			String year = yearMonth.substring(0, 4);
			String month = yearMonth.substring(4, 6);
			
			//更新list中的期间
			for(Map<String, Object> entityMap : entityList){
				entityMap.put("year", year);
				entityMap.put("month", month);
			}
			
			List<Map<String, Object>> listUpdateFifoBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listUpdateBatchBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listUpdateInvHold = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listUpdateInvBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listAddBatchBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			List<Map<String, Object>> listAddInvBalance = new ArrayList<Map<String, Object>>();//保存修改账表数据
			
			StringBuffer invMsg = new StringBuffer();//存放库存不足的药品信息
			StringBuffer storeMsg = new StringBuffer();//存放已结账的库房信息
			boolean is_enough = true;  //库存是否充足
			
			//获取所选单据中的所有药品
			List<MedOutDetail> detailList = medOutDetailMapper.queryMedOutDetailForConfirm(entityList);
			//查询帐表所需
			List<Map<String, Object>> invList = new ArrayList<Map<String,Object>>();
			
			//存放明细数据用于判断
			Map<String,Map<String,Object>> mapFifoBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapBatchBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInvHold = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInvBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			
			//累计相同药品的数量和金额
			Map<String,Double> sum_amount_map = new HashMap<String,Double>();
			Map<String,Double> sum_amount_money = new HashMap<String,Double>();		
			Map<String,Double> sum_sale_money = new HashMap<String,Double>();				
			
			for(MedOutDetail mod : detailList){
				/**如果确认日期按编制日期走则放开年月的重新赋值并改detailList相应的xml*/
				//year = mod.getYear();
				//month = mod.getMonth();
				//药品编码和名称用于库存不足的提示
				Map<String,Object> mapDetail = new HashMap<String,Object>();
				Map<String, Object> invMap = new HashMap<String, Object>();
				mapDetail.put("group_id", mod.getGroup_id());
				mapDetail.put("hos_id", mod.getHos_id());
				mapDetail.put("copy_code", mod.getCopy_code());
				mapDetail.put("store_id", mod.getStore_id());
				mapDetail.put("inv_id", mod.getInv_id());
				mapDetail.put("inv_code", mod.getInv_code());
				mapDetail.put("inv_name", mod.getInv_name());
				mapDetail.put("batch_no", mod.getBatch_no());
				mapDetail.put("batch_sn", mod.getBatch_sn());
				mapDetail.put("bar_code", mod.getBar_code());
				mapDetail.put("price", mod.getPrice() == null ? 0 : mod.getPrice());
				mapDetail.put("amount", mod.getAmount() == null ? 0 : mod.getAmount());
				mapDetail.put("amount_money", mod.getAmount_money() == null ? 0 : mod.getAmount_money());
				mapDetail.put("sale_price", mod.getSale_price() == null ? 0 : mod.getSale_price());
				mapDetail.put("sale_money", mod.getSale_money() == null ? 0 : mod.getSale_money());
				mapDetail.put("location_id", mod.getLocation_id() == null ? 0 : mod.getLocation_id());
				
				invMap.put("group_id", mod.getGroup_id());
				invMap.put("hos_id", mod.getHos_id());
				invMap.put("copy_code", mod.getCopy_code());
				invMap.put("year", year);
				invMap.put("month", month);
				invMap.put("store_id", mod.getStore_id());
				invMap.put("inv_id", mod.getInv_id());
				invMap.put("price", mod.getPrice());
				invMap.put("batch_no", mod.getBatch_no());
				invMap.put("batch_sn", mod.getBatch_sn());
				invMap.put("bar_code", mod.getBar_code());
				invMap.put("location_id", mod.getLocation_id() == null ? 0 : mod.getLocation_id());

				//判断库房是否已结账
				String store_flag = medCommonMapper.queryMedStoreIsAccount(invMap);
				if(store_flag != null && !"".equals(store_flag)){
					storeMsg.append(store_flag).append("<br/>");
					continue;
				}
				
				invList.add(invMap);
				
				//Map中存放<各帐表主键, 具体药品信息的map>用于判断
				mapFifoBalance.put(mod.getStore_id().toString()+mod.getInv_id()+mod.getPrice()+mod.getBatch_no()+mod.getBatch_sn()+mod.getBar_code()+mod.getLocation_id(), mapDetail);
				mapInvHold.put(mod.getStore_id().toString()+mod.getInv_id()+mod.getLocation_id(), mapDetail);
				//下面这两张表是区分年月的
				mapDetail.put("year", year);
				mapDetail.put("month", month);
				mapBatchBalance.put(year+month+mod.getStore_id()+mod.getInv_id()+mod.getPrice()+mod.getBatch_no()+mod.getBatch_sn()+mod.getBar_code()+mod.getLocation_id(), mapDetail);
				mapInvBalance.put(year+month+mod.getStore_id()+mod.getInv_id()+mod.getLocation_id(), mapDetail);
				
				//根据药品ID累计数量
				if(sum_amount_map.get(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id()) != null){
					sum_amount_map.put(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id(), NumberUtil.add(sum_amount_map.get(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id()), mod.getAmount()));
				}else{
					sum_amount_map.put(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id(),mod.getAmount());
				}
				//根据药品ID累计金额
				if(sum_amount_money.get(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id()) != null){
					sum_amount_money.put(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id(), NumberUtil.add(sum_amount_money.get(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id()), mod.getAmount_money()));
				}else{
					sum_amount_money.put(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id(), mod.getAmount_money());
				}
				//根据药品ID累计金额
				if(sum_sale_money.get(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id()) != null){
					sum_sale_money.put(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id(), NumberUtil.add(sum_sale_money.get(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id()), mod.getSale_money()));
				}else{
					sum_sale_money.put(mod.getStore_id()+"-"+mod.getInv_id()+"-"+mod.getLocation_id(), mod.getSale_money());
				}
			}

			//存在已结账库房
			if(storeMsg != null && !"".equals(storeMsg.toString())){
				return "{\"error\":\"以下库房本期间已结账：<br/>"+storeMsg.toString()+"\"}";
			}

			//------------------------------查询账表 取出相应数据 并且组织账表数据 MED_FIFO_BALANCE----------------------------------
			List<MedFifoBalance> listFifoBalance = (List<MedFifoBalance>)medFifoBalanceMapper.queryByInvList(invList);
			
			Map<String,MedFifoBalance> mfb_mb = new HashMap<String,MedFifoBalance>();//所有 根据条件 取出表（MED_FIFO_BALANCE）的数据都放到map里面
			
			for(MedFifoBalance mfb : listFifoBalance){
				String key  = mfb.getStore_id().toString()+mfb.getInv_id()+mfb.getPrice()+mfb.getBatch_no()+mfb.getBatch_sn()+mfb.getBar_code()+mfb.getLocation_id();
				mfb_mb.put(key, mfb);
			}
			
			for (String key : mapFifoBalance.keySet()) {//循环当前药品的明细 key查询条件 取相应的值
				
				Map<String,Object> mapBatch = new HashMap<String,Object>();
				if(mfb_mb.get(key) !=null){
					Map<String,Object> map = mapFifoBalance.get(key);
					mapBatch.putAll(map);
					
					MedFifoBalance mfb = (MedFifoBalance)mfb_mb.get(key);
					
					double amount = (Double)map.get("amount");
					double amount_money = (Double)map.get("amount_money");
					double sale_money = (Double)map.get("sale_money");
					/**判断实际库存是否充足-------------------begin--------------------------*/
					//所选单据药品合计数量 > 0 && 合计数量 > 帐表药品剩余数量 ? 剩余库存不足 : 剩余库存充足（用于出库判断）
					//所选单据药品合计数量 < 0 && (-1)合计数量 > 帐表药品剩余数量 ? 科室药品不足 : 科室药品充足（用于退库判断）
					if((amount > 0 && amount > mfb.getLeft_amount()) || (amount < 0 && -1 * amount > mfb.getOut_amount())){
						invMsg.append(map.get("inv_code")).append(" ").append(map.get("inv_name")).append(",");
						if(is_enough){
							is_enough = false;
						}
					}
					/**判断实际库存是否充足-------------------end----------------------------*/
					if(is_enough){
						mapBatch.put("out_amount", NumberUtil.add(amount, mfb.getOut_amount()));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add(amount_money, mfb.getOut_money()));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add(sale_money, mfb.getOut_sale_money()));//出库批发金额 等于出库批发金额 + 当前出库批发金额
						mapBatch.put("left_amount", NumberUtil.sub(mfb.getLeft_amount(), amount));//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("left_money", NumberUtil.sub(mfb.getLeft_money(), amount_money));//剩余金额 等于剩余金额 - 当前出库金额
						mapBatch.put("left_sale_money", NumberUtil.sub(mfb.getLeft_sale_money(), sale_money));//剩余数量 等于剩余数量 - 当前出库数量
						//以下字段的计算方式有待讨论
						if(NumberUtil.sub(mfb.getLeft_amount(), amount) == 0){
							mapBatch.put("remove_zero_error", NumberUtil.sub(mfb.getIn_money(), (Double)mapBatch.get("out_money")));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", NumberUtil.sub(mfb.getIn_sale_money(), (Double)mapBatch.get("out_sale_money")));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
						listUpdateFifoBalance.add(mapBatch);
					}
				}else{
					//帐表中没有该药品信息，提示库存不足！
					invMsg.append(mapFifoBalance.get(key).get("inv_code")).append(" ").append(mapFifoBalance.get(key).get("inv_name")).append(",");
					if(is_enough){
						is_enough = false;
					}
				}
			}
			
			//如果药品出现库存药品不足的就不需要组装、判断另外几个帐表了
			if(is_enough){
				//------------------------------查询账表 取出相应数据 并且组织账表数据MED_BATCH_BALANCE----------------------------------
				List<MedBatchBalance> listBatchBalance = (List<MedBatchBalance>)medBatchBalanceMapper.queryByInvList(invList);

				Map<String,MedBatchBalance> mbb_mb = new HashMap<String,MedBatchBalance>();//所有 根据条件 取出表（MED_BATCH_BALANCE）的数据都放到map里面
				
				for(MedBatchBalance mbb : listBatchBalance){
					String key = mbb.getYear().toString()+mbb.getMonth()+mbb.getStore_id()+mbb.getInv_id()+mbb.getPrice()+mbb.getBatch_no()+mbb.getBatch_sn()+mbb.getBar_code()+mbb.getLocation_id();
					mbb_mb.put(key, mbb);
				}
				
				for (String key : mapBatchBalance.keySet()) {//循环当前药品的明细 key查询条件 取相应的值
					Map<String,Object> mapBatch = new HashMap<String,Object>();

					Map<String,Object> map = mapBatchBalance.get(key);
					mapBatch.putAll(map);
					
					double amount = (Double)map.get("amount");
					double amount_money = (Double)map.get("amount_money");
					double sale_money = (Double)map.get("sale_money");
					
					if(mbb_mb.get(key) !=null){
						MedBatchBalance mbb = (MedBatchBalance)mbb_mb.get(key);
						mapBatch.put("out_amount", NumberUtil.add(amount, mbb.getOut_amount()));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add(amount_money, mbb.getOut_money()));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add(sale_money, mbb.getOut_sale_money()));//出库批发金额 等于出库的批发金额 + 当前出库的批发金额
						mapBatch.put("y_out_amount", NumberUtil.add(amount, mbb.getOut_amount()));//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("y_out_money", NumberUtil.add(amount_money, mbb.getOut_money()));//剩余数量 等于剩余的金额 - 当前出库的金额
						mapBatch.put("y_out_sale_money", NumberUtil.add(sale_money, mbb.getOut_sale_money()));//剩余批发金额 等于剩余的批发金额 - 当前出库的批发金额
						//以下字段的计算方式有待讨论
						if(NumberUtil.sub(mbb.getIn_amount(), (Double)mapBatch.get("out_amount")) == 0){
							mapBatch.put("remove_zero_error", NumberUtil.sub(mbb.getIn_money(), (Double)mapBatch.get("out_money")));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", NumberUtil.sub(mbb.getIn_sale_money(), (Double)mapBatch.get("out_sale_money")));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
						
						listUpdateBatchBalance.add(mapBatch);
					}else{
						mapBatch.put("end_amount", 0);
						mapBatch.put("end_money", 0);
						mapBatch.put("end_sale_money", 0);
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", 0);
						mapBatch.put("in_money", 0);
						mapBatch.put("in_sale_money", 0);
						mapBatch.put("out_amount", amount);
						mapBatch.put("out_money", amount_money);
						mapBatch.put("out_sale_money", sale_money);
						mapBatch.put("y_in_amount", 0);
						mapBatch.put("y_in_money", 0);
						mapBatch.put("y_in_sale_money", 0);
						mapBatch.put("y_out_amount", amount);
						mapBatch.put("y_out_money", amount_money);
						mapBatch.put("y_out_sale_money", sale_money);
						mapBatch.put("location_id", mapBatch.get("location_id") == null ? 0 : mapBatch.get("location_id"));
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);
						
						listAddBatchBalance.add(mapBatch);
					}
				}
				
				//------------------------------查询账表 取出相应数据 并且组织账表数据MED_INV_HOLD----------------------------------
				List<MedInvHold> listInvHold = (List<MedInvHold>)medInvHoldMapper.queryByInvList(invList);

				Map<String,MedInvHold> mih_mb = new HashMap<String,MedInvHold>();//所有 根据条件 取出表（MED_INV_HOLD）的数据都放到map里面
				
				for(MedInvHold mih : listInvHold){
					String key = mih.getStore_id().toString()+mih.getInv_id()+mih.getLocation_id();
					mih_mb.put(key, mih);
				}
				
				for (String key : mapInvHold.keySet()) {//循环当前药品的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					
					if(mih_mb.get(key) !=null){
						Map<String,Object> map = mapInvHold.get(key);
						mapBatch.putAll(map);
						
						MedInvHold mih = (MedInvHold)mih_mb.get(key);
						
						mapBatch.put("cur_amount", NumberUtil.sub(mih.getCur_amount(), (Double)sum_amount_map.get(mih.getStore_id()+"-"+mih.getInv_id()+"-"+mih.getLocation_id())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("cur_money", NumberUtil.sub(mih.getCur_money(), (Double)sum_amount_money.get(mih.getStore_id()+"-"+mih.getInv_id()+"-"+mih.getLocation_id())));//出库金额 等于出库的金额 + 当前出库的金额
						
						listUpdateInvHold.add(mapBatch);
					}
				}

				//------------------------------查询账表 取出相应数据 并且组织账表数据MED_INV_Batch----------------------------------
				List<MedInvBalance> listInvBalance = (List<MedInvBalance>)medInvBalanceMapper.queryByInvList(invList);
				
				Map<String,MedInvBalance> mib_mb = new HashMap<String,MedInvBalance>();//所有 根据条件 取出表（MED_INV_HOLD）的数据都放到map里面
				for(MedInvBalance mib : listInvBalance){
					String key = mib.getYear().toString()+mib.getMonth()+mib.getStore_id()+mib.getInv_id()+mib.getLocation_id();
					mib_mb.put(key, mib);
				}
				
				for (String key : mapInvBalance.keySet()) {//循环当前药品的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					Map<String,Object> map = mapInvBalance.get(key);
					
					mapBatch.putAll(map);
					
					if(mib_mb.get(key) !=null){
						MedInvBalance mib = (MedInvBalance)mib_mb.get(key);
						mapBatch.put("out_amount", NumberUtil.add((Double)sum_amount_map.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_amount()));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add((Double)sum_amount_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_money()));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add((Double)sum_sale_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_sale_money()));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("y_out_amount", NumberUtil.add((Double)sum_amount_map.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_amount()));//累计数量 等于累计的数量 + 当前出库的数量
						mapBatch.put("y_out_money", NumberUtil.add((Double)sum_amount_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_money()));//累计金额 等于累计的金额 + 当前出库的金额
						mapBatch.put("y_out_sale_money", NumberUtil.add((Double)sum_sale_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_sale_money()));//累计数量 等于累计的金额 + 当前出库的金额
						//以下字段的计算方式有待讨论
						if(NumberUtil.sub(mib.getIn_amount(), (Double)mapBatch.get("out_amount")) == 0){
							mapBatch.put("remove_zero_error", NumberUtil.sub(mib.getIn_money(), (Double)mapBatch.get("out_money")));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", NumberUtil.sub(mib.getIn_sale_money(), (Double)mapBatch.get("out_sale_money")));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
							
						listUpdateInvBalance.add(mapBatch);
					}else{
						mapBatch.put("end_amount", 0);
						mapBatch.put("end_money", 0);
						mapBatch.put("end_sale_money", 0);
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", 0);
						mapBatch.put("in_money", 0);
						mapBatch.put("in_sale_money", 0);
						mapBatch.put("out_amount", (Double)sum_amount_map.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))+"-"+String.valueOf(map.get("location_id"))));
						mapBatch.put("out_money",  (Double)sum_amount_money.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))+"-"+String.valueOf(map.get("location_id"))));
						mapBatch.put("out_sale_money",  (Double)sum_sale_money.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))+"-"+String.valueOf(map.get("location_id"))));
						mapBatch.put("y_in_amount", 0);
						mapBatch.put("y_in_money", 0);
						mapBatch.put("y_in_sale_money", 0);
						mapBatch.put("y_out_amount", (Double)sum_amount_map.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))+"-"+String.valueOf(map.get("location_id"))));
						mapBatch.put("y_out_money",  (Double)sum_amount_money.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))+"-"+String.valueOf(map.get("location_id"))));
						mapBatch.put("y_out_sale_money",  (Double)sum_sale_money.get(String.valueOf(map.get("store_id"))+"-"+String.valueOf(map.get("inv_id"))+"-"+String.valueOf(map.get("location_id"))));
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);
						
						mapBatch.put("location_id", mapBatch.get("location_id") == null ? 0 : mapBatch.get("location_id"));
						
						listAddInvBalance.add(mapBatch);
					}
				}
			}
			if(!is_enough){
				//药品库存不足提示
				return "{\"error\":\""+invMsg.substring(0, invMsg.length()-1).toString()+"库存药品不足!\"}";
			}
			
			/***********处理单据中的耐用品****begin*****************//*
			checkMap.put("mod_code", "0813");  //耐用品管理子系统
			int start_dura = medCommonMapper.existsMedModStartByYearMonth(checkMap);  //是否启用耐用品
			if(start_dura > 0){
				//耐用品主表数据
				List<Map<String, Object>> duraMainList = new ArrayList<Map<String,Object>>();
				//耐用品明细表数据
				List<Map<String, Object>> duraDetailList = new ArrayList<Map<String,Object>>();
				
				//获取所选单据中的所有药品
				List<Map<String, Object>> duraList = JsonListMapUtil.toListMapLower(medOutDetailMapper.queryMedOutForDura(entityList));
				
				//用于拆分耐用品数据
				Map<String, Map<String, Object>> duraMainMap = new HashMap<String, Map<String, Object>>();
				String duraKey = "";
				
				//存放StoreBalance表数据
				List<Map<String, Object>> addStoreBalanceList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> updateStoreBalanceList = new ArrayList<Map<String,Object>>();
				Map<String, Map<String, Object>> storeBalanceMap = new HashMap<String, Map<String, Object>>();
				//存放DeptBalance表数据
				List<Map<String, Object>> addDeptBalanceList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> updateDeptBalanceList = new ArrayList<Map<String,Object>>();
				Map<String, Map<String, Object>> deptBalanceMap = new HashMap<String, Map<String, Object>>();
				String balanceKey = ""; 
				
				//存放StoreSurplus表数据
				List<Map<String, Object>> addStoreSurplusList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> updateStoreSurplusList = new ArrayList<Map<String,Object>>();
				Map<String, Map<String, Object>> storeSurplusMap = new HashMap<String, Map<String, Object>>();
				//存放DeptSurplus表数据
				List<Map<String, Object>> addDeptSurplusList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> updateDeptSurplusList = new ArrayList<Map<String,Object>>();
				Map<String, Map<String, Object>> deptSurplusMap = new HashMap<String, Map<String, Object>>();
				String surplusKey = "";
				
				//存放DeptBar表数据
				List<Map<String, Object>> addDeptBarList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> updateDeptBarList = new ArrayList<Map<String,Object>>();
				Map<String, Map<String, Object>> deptBarMap = new HashMap<String, Map<String, Object>>();
				String barKey = "";
				
				Double amount = null;  //转移数量
				Double amount_money = null;  //转移金额
				
				//用于记录数据
				Map<String, Object> tempMap = null;
				
				//循环单据中耐用品数据
				for(Map<String, Object> map : duraList){
					//duraMap中的key用于判断库房到科室的单据是否已存在
					duraKey = map.get("store_id").toString() + map.get("dept_id").toString();
					
					*//******处理耐用品主表数据********begin************//*
					Map<String, Object> dMainMap = null;
					if(!duraMainMap.containsKey(duraKey)){
						dMainMap = new HashMap<String, Object>();	
						dMainMap.put("group_id", map.get("group_id"));
						dMainMap.put("hos_id", map.get("hos_id"));
						dMainMap.put("copy_code", map.get("copy_code"));
						dMainMap.put("year", year);
						dMainMap.put("month", month);
						dMainMap.put("bus_type_code", "37");  //耐用品库到科室转移
						dMainMap.put("store_id", map.get("store_id"));
						dMainMap.put("store_no", map.get("store_no"));
						dMainMap.put("dept_id", map.get("dept_id"));
						dMainMap.put("dept_no", map.get("dept_no"));
						dMainMap.put("maker", SessionManager.getUserId());
						dMainMap.put("make_date", new Date());
						dMainMap.put("state", 3);
						dMainMap.put("brief", "由药品出库业务自动生成");
						dMainMap.put("checker", SessionManager.getUserId());
						dMainMap.put("check_date", new Date());
						dMainMap.put("confirmer", SessionManager.getUserId());
						dMainMap.put("confirm_date", new Date());
						//自增序列
						dMainMap.put("dura_id", medDuraTranStoreDeptMapper.queryMainSeq());
						//转移单号
						dMainMap.put("table_code", "MED_DURA_STORE_DEPT");
						dMainMap.put("length_no", "5");  //流水码5位
						dMainMap.put("containsDay", "0");  //单据号不按day生成
						dMainMap.put("dura_no", medCommonService.getMedNextNo(dMainMap));
						//记录主表
						duraMainMap.put(duraKey, dMainMap);
					}else{
						
						dMainMap = duraMainMap.get(duraKey);
					}
					*//******处理耐用品主表数据********end**************//*
					
					*//******处理耐用品明细表数据******begin************//*
					//存放明细数据
					Map<String, Object> detailMap = new HashMap<String, Object>();
					detailMap.put("group_id", map.get("group_id").toString());
					detailMap.put("hos_id", map.get("hos_id").toString());
					detailMap.put("copy_code", map.get("copy_code").toString());
					detailMap.put("dura_id", dMainMap.get("dura_id").toString());  //主表ID
					detailMap.put("detail_id", medDuraTranStoreDeptMapper.queryDetailSeq());  //明细表ID
					
					detailMap.put("inv_id",  map.get("inv_id").toString());  //药品ID
					detailMap.put("inv_no",  map.get("inv_no").toString());  //药品变更号
					detailMap.put("price",  map.get("price").toString());  //单价
					detailMap.put("amount",  map.get("amount").toString());  //数量
					detailMap.put("amount_money",  map.get("amount_money").toString());  //金额
					detailMap.put("bar_code",  map.get("bar_code") == null ? "" : map.get("bar_code").toString());  //条码
					detailMap.put("note", map.get("note") == null ? "" : map.get("note").toString());  //备注
					
					duraDetailList.add(detailMap);
					*//******处理耐用品明细表数据******end**************//*
					
					*//******处理耐用品账表************begn*************//*
					//库房为即入即出
					amount = Double.valueOf(map.get("amount").toString());
					amount_money = Double.valueOf(map.get("amount_money").toString());
					
					//查询库房信息
					Map<String, Object> searchMap = new HashMap<String, Object>();  
					searchMap.put("group_id", map.get("group_id"));
					searchMap.put("hos_id", map.get("hos_id"));
					searchMap.put("copy_code", map.get("copy_code"));
					searchMap.put("year", year);
					searchMap.put("month", month);
					searchMap.put("store_id", map.get("store_id"));  //先处理库房数据
					searchMap.put("inv_id", map.get("inv_id"));
					searchMap.put("price", map.get("price"));
					searchMap.put("bar_code", map.get("bar_code"));
					
					barKey = map.get("store_id").toString() + map.get("inv_id");
					balanceKey = barKey + map.get("price").toString();
					surplusKey = year + month + balanceKey;
					
					*//**操作med_dura_store_balance表***********begin***********//*
					tempMap = new HashMap<String, Object>();  //初始化map
					if(storeBalanceMap.containsKey(balanceKey)){
						tempMap = storeBalanceMap.get(balanceKey);
					}else{
						//查询库房帐表数据
						tempMap = medDuraStoreBalanceMapper.queryByCode(searchMap);
					}
					
					if(tempMap == null || tempMap.size() == 0){
						//如果不存在则新增
						tempMap = new HashMap<String, Object>();  //初始化对象，防止空指针错误
						tempMap.put("group_id", map.get("group_id"));
						tempMap.put("hos_id", map.get("hos_id"));
						tempMap.put("copy_code", map.get("copy_code"));
						tempMap.put("store_id", map.get("store_id")); 
						tempMap.put("inv_id", map.get("inv_id"));
						tempMap.put("price", map.get("price"));
						tempMap.put("in_amount", amount);
						tempMap.put("in_money", amount_money);
						tempMap.put("out_amount", amount);
						tempMap.put("out_money", amount_money);
						tempMap.put("left_amount", 0);
						tempMap.put("left_money", 0);
						//记录标示为新增
						tempMap.put("flag", "insert");
					}else{
						//累加如库数据
						tempMap.put("in_amount", NumberUtil.add(Double.valueOf(tempMap.get("in_amount").toString()), amount));
						tempMap.put("in_money", NumberUtil.add(Double.valueOf(tempMap.get("in_money").toString()), amount_money));
						//累加出库数据
						tempMap.put("out_amount", NumberUtil.add(Double.valueOf(tempMap.get("out_amount").toString()), amount));
						tempMap.put("out_money", NumberUtil.add(Double.valueOf(tempMap.get("out_money").toString()), amount_money));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!tempMap.containsKey("flag")){
							//记录标示为修改
							tempMap.put("flag", "update");
						}
					}
					//放入Map中
					storeBalanceMap.put(balanceKey, tempMap);
					*//**操作med_dura_store_balance表***********end*************//*
					
					*//**操作med_dura_store_surplus表************begin***********//*
					tempMap = new HashMap<String, Object>();  //初始化map
					if(storeSurplusMap.containsKey(surplusKey)){
						tempMap = storeSurplusMap.get(surplusKey);
					}else{
						//查询库房帐表数据
						tempMap = medDuraStoreSurplusMapper.queryByCode(searchMap);
					}
					
					//移出库如果不存在则新增
					if(tempMap == null || tempMap.size() == 0){
						tempMap = new HashMap<String, Object>();  //初始化对象，防止空指针错误
						tempMap.put("group_id", map.get("group_id"));
						tempMap.put("hos_id", map.get("hos_id"));
						tempMap.put("copy_code", map.get("copy_code"));
						tempMap.put("year", year);
						tempMap.put("month", month);
						tempMap.put("store_id", map.get("store_id"));
						tempMap.put("inv_id", map.get("inv_id"));
						tempMap.put("price", map.get("price"));
						tempMap.put("begin_amount", 0);
						tempMap.put("begin_money", 0);
						tempMap.put("in_amount", amount); 
						tempMap.put("in_money", amount_money);
						tempMap.put("out_amount", amount);
						tempMap.put("out_money", amount_money);
						tempMap.put("end_amount", 0);
						tempMap.put("end_money", 0);
						//记录标示为新增
						tempMap.put("flag", "insert");
					}else{
						//累加出库数据
						tempMap.put("out_amount", NumberUtil.add(Double.valueOf(tempMap.get("out_amount").toString()), amount));
						tempMap.put("out_money", NumberUtil.add(Double.valueOf(tempMap.get("out_money").toString()), amount_money));
						//累加入库数据
						tempMap.put("in_amount", NumberUtil.add(Double.valueOf(tempMap.get("in_amount").toString()), amount));
						tempMap.put("in_money", NumberUtil.add(Double.valueOf(tempMap.get("in_money").toString()), amount_money));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!tempMap.containsKey("flag")){
							//记录标示为修改
							tempMap.put("flag", "update");
						}
					}
					//放入Map中
					storeSurplusMap.put(surplusKey, tempMap);
					*//**操作med_dura_store_surplus表************end*************//*
					
					*//**操作med_dura_store_bar表****************begin***********//*
					//已出到科室，库房不需要存储条码信息
					*//**操作med_dura_store_bar表****************end*************//*
					
					//查询科室信息
					searchMap.put("dept_id", map.get("dept_id"));  //处理科室数据
					
					barKey = map.get("dept_id").toString() + map.get("inv_id");
					balanceKey = barKey + map.get("price").toString();
					surplusKey = year + month + balanceKey;
					
					*//**操作med_dura_dept_balance表***********begin***********//*
					tempMap = new HashMap<String, Object>();  //初始化map
					if(deptBalanceMap.containsKey(balanceKey)){
						tempMap = deptBalanceMap.get(balanceKey);
					}else{
						//查询科室帐表数据
						tempMap = medDuraDeptBalanceMapper.queryByCode(searchMap);
					}
					
					if(tempMap == null || tempMap.size() == 0){
						//如果不存在则新增
						tempMap = new HashMap<String, Object>();  //初始化对象，防止空指针错误
						tempMap.put("group_id", map.get("group_id"));
						tempMap.put("hos_id", map.get("hos_id"));
						tempMap.put("copy_code", map.get("copy_code"));
						tempMap.put("dept_id", map.get("dept_id")); 
						tempMap.put("inv_id", map.get("inv_id"));
						tempMap.put("price", map.get("price"));
						tempMap.put("in_amount", amount);
						tempMap.put("in_money", amount_money);
						tempMap.put("out_amount", 0);
						tempMap.put("out_money", 0);
						tempMap.put("left_amount", amount);
						tempMap.put("left_money", amount_money);
						//记录标示为新增
						tempMap.put("flag", "insert");
					}else{
						//累加入库数据
						tempMap.put("in_amount", NumberUtil.add(Double.valueOf(tempMap.get("in_amount").toString()), amount));
						tempMap.put("in_money", NumberUtil.add(Double.valueOf(tempMap.get("in_money").toString()), amount_money));
						//增加库存量
						tempMap.put("left_amount", NumberUtil.add(Double.valueOf(tempMap.get("left_amount").toString()), amount));
						tempMap.put("left_money", NumberUtil.add(Double.valueOf(tempMap.get("left_money").toString()), amount_money));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!tempMap.containsKey("flag")){
							//记录标示为修改
							tempMap.put("flag", "update");
						}
					}
					//放入Map中
					deptBalanceMap.put(balanceKey, tempMap);
					*//**操作med_dura_dept_balance表***********end*************//*
					
					*//**操作med_dura_dept_surplus表************begin***********//*
					tempMap = new HashMap<String, Object>();  //初始化map
					if(deptSurplusMap.containsKey(surplusKey)){
						tempMap = deptSurplusMap.get(surplusKey);
					}else{
						//查询科室帐表数据
						tempMap = medDuraDeptSurplusMapper.queryByCode(searchMap);
					}
					
					//移出库如果不存在则新增
					if(tempMap == null || tempMap.size() == 0){
						tempMap = new HashMap<String, Object>();  //初始化对象，防止空指针错误
						tempMap.put("group_id", map.get("group_id"));
						tempMap.put("hos_id", map.get("hos_id"));
						tempMap.put("copy_code", map.get("copy_code"));
						tempMap.put("year", year);
						tempMap.put("month", month);
						tempMap.put("dept_id", map.get("dept_id"));
						tempMap.put("inv_id", map.get("inv_id"));
						tempMap.put("price", map.get("price"));
						tempMap.put("begin_amount", 0);
						tempMap.put("begin_money", 0);
						
						//库到科室转移入库数据记录为0反之出库数量记录为0
						tempMap.put("in_amount", amount); 
						tempMap.put("in_money", amount_money);
						tempMap.put("out_amount", 0);
						tempMap.put("out_money", 0);
						
						tempMap.put("end_amount", 0);
						tempMap.put("end_money", 0);
						//记录标示为新增
						tempMap.put("flag", "insert");
					}else{
						//累加入库数据
						tempMap.put("in_amount", NumberUtil.add(Double.valueOf(tempMap.get("in_amount").toString()), amount));
						tempMap.put("in_money", NumberUtil.add(Double.valueOf(tempMap.get("in_money").toString()), amount_money));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!tempMap.containsKey("flag")){
							//记录标示为修改
							tempMap.put("flag", "update");
						}
					}
					//放入Map中
					deptSurplusMap.put(surplusKey, tempMap);
					*//**操作med_dura_dept_surplus表************end*************//*
					
					*//**操作med_dura_dept_bar表****************begin***********//*
					//判断是否为存在条码
					if(map.get("bar_code") == null || "".equals(map.get("bar_code"))){
						map.put("bar_code", "-");
					}
					tempMap = new HashMap<String, Object>();  //初始化map
					if(deptBarMap.containsKey(barKey)){
						tempMap = deptBarMap.get(barKey);
					}else{
						//查询科室条码数据
						tempMap = medDuraDeptBarMapper.queryByCode(searchMap);
					}
	
					if(tempMap == null || tempMap.size() == 0){
						tempMap = new HashMap<String, Object>();  //初始化map
						tempMap.put("group_id", map.get("group_id"));
						tempMap.put("hos_id", map.get("hos_id"));
						tempMap.put("copy_code", map.get("copy_code"));
						tempMap.put("dept_id", map.get("dept_id")); 
						tempMap.put("inv_id", map.get("inv_id")); 
						tempMap.put("price", map.get("price")); 
						tempMap.put("bar_code", map.get("bar_code")); 
						tempMap.put("amount", amount); 
						//记录标示为新增
						tempMap.put("flag", "insert");
					}else{
						//累加条码数量
						tempMap.put("amount", NumberUtil.add(Double.valueOf(tempMap.get("amount").toString()), amount));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!tempMap.containsKey("flag")){
							//记录标示为修改
							tempMap.put("flag", "update");
						}
					}
					//放入Map中
					deptBarMap.put(barKey, tempMap);
					*//**操作med_dura_dept_bar表****************end*************//*
					*//******处理耐用品账表************end**************//*
				}
				//解析主表数据
				for(String key : duraMainMap.keySet()){
					duraMainList.add(duraMainMap.get(key));
				}
				
				//解析storeBalanceMap获得需要添加和修改的数据
				for(String key : storeBalanceMap.keySet()){
					Map<String, Object> vMap = storeBalanceMap.get(key);
					if("insert".equals(vMap.get("flag").toString())){
						//新增
						addStoreBalanceList.add(vMap);
					}else if("update".equals(vMap.get("flag").toString())){
						//修改
						updateStoreBalanceList.add(vMap);
					}
				}
				
				//解析storeSurplusMap获得需要添加和修改的数据
				for(String key : storeSurplusMap.keySet()){
					Map<String, Object> vMap = storeSurplusMap.get(key);
					if("insert".equals(vMap.get("flag").toString())){
						//新增
						addStoreSurplusList.add(vMap);
					}else if("update".equals(vMap.get("flag").toString())){
						//修改
						updateStoreSurplusList.add(vMap);
					}
				}
				
				//解析deptBalanceMap获得需要添加和修改的数据
				for(String key : deptBalanceMap.keySet()){
					Map<String, Object> vMap = deptBalanceMap.get(key);
					if("insert".equals(vMap.get("flag").toString())){
						//新增
						addDeptBalanceList.add(vMap);
					}else if("update".equals(vMap.get("flag").toString())){
						//修改
						updateDeptBalanceList.add(vMap);
					}
				}
				
				//解析deptSurplusMap获得需要添加和修改的数据
				for(String key : deptSurplusMap.keySet()){
					Map<String, Object> vMap = deptSurplusMap.get(key);
					if("insert".equals(vMap.get("flag").toString())){
						//新增
						addDeptSurplusList.add(vMap);
					}else if("update".equals(vMap.get("flag").toString())){
						//修改
						updateDeptSurplusList.add(vMap);
					}
				}
				
				//解析deptBarMap获得需要添加和修改的数据
				for(String key : deptBarMap.keySet()){
					Map<String, Object> vMap = deptBarMap.get(key);
					if("update".equals(vMap.get("flag").toString())){
						//移除
						updateDeptBarList.add(vMap);
					}else if("insert".equals(vMap.get("flag").toString())){
						//新增
						addDeptBarList.add(vMap);
					}
				}
				
				//插入或修改med_dura_store_balance表
				if(addStoreBalanceList.size() > 0){
					medDuraStoreBalanceMapper.addBatch(addStoreBalanceList);
				}
				if(updateStoreBalanceList.size() > 0){
					medDuraStoreBalanceMapper.updateBatch(updateStoreBalanceList);
				}
				
				//插入或修改med_dura_store_surplus表
				if(addStoreSurplusList.size() > 0){
					medDuraStoreSurplusMapper.addBatch(addStoreSurplusList);
				}
				if(updateStoreSurplusList.size() > 0){
					medDuraStoreSurplusMapper.updateBatch(updateStoreSurplusList);
				}
				
				//插入或修改med_dura_dept_balance表
				if(addDeptBalanceList.size() > 0){
					medDuraDeptBalanceMapper.addBatch(addDeptBalanceList);
				}
				if(updateDeptBalanceList.size() > 0){
					medDuraDeptBalanceMapper.updateBatch(updateDeptBalanceList);
				}
				
				//插入或修改med_dura_dept_surplus表
				if(addDeptSurplusList.size() > 0){
					medDuraDeptSurplusMapper.addBatch(addDeptSurplusList);
				}
				if(updateDeptSurplusList.size() > 0){
					medDuraDeptSurplusMapper.updateBatch(updateDeptSurplusList);
				}
	
				//插入或修改med_dura_dept_bar表
				if(addDeptBarList.size() > 0){
					medDuraDeptBarMapper.addBatch(addDeptBarList);
				}
				if(updateDeptBarList.size() > 0){
					medDuraDeptBarMapper.updateBatch(updateDeptBarList);
				}
				
				//修改单据状态
				medDuraTranStoreDeptMapper.addMainBatch(duraMainList);
				//修改单据状态
				medDuraTranStoreDeptMapper.addDetail(duraDetailList);
			}
			*//***********处理单据中的耐用品****end*******************/
			
			//更新med_fifo_balance账表
			medFifoBalanceMapper.updateBatch(listUpdateFifoBalance);
			
			//添加/更新med_batch_balance帐表
			if(listUpdateBatchBalance.size() >0 || listAddBatchBalance.size() >0){
				if(listUpdateBatchBalance.size() >0){
					medBatchBalanceMapper.updateBatch(listUpdateBatchBalance);
				}
				if(listAddBatchBalance.size() >0){
					medBatchBalanceMapper.addBatch(listAddBatchBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 med_batch_balance帐表无数据变动\"}");
			}
			
			//添加/更新med_inv_balance帐表
			if(listUpdateInvBalance.size() >0 || listAddInvBalance.size() >0){
				if(listUpdateInvBalance.size() >0){
					medInvBalanceMapper.updateBatch(listUpdateInvBalance);
				}
				if(listAddInvBalance.size()>0){
					medInvBalanceMapper.addBatch(listAddInvBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 med_inv_balance帐表无数据变动\"}");
			}
			
			//更新med_inv_hold帐表
			medInvHoldMapper.updateBatch(listUpdateInvHold);
			
			//修改状态为出库确认
			medOutMainMapper.updateConfirmBatch(entityList);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"出库确认失败 数据库异常 请联系管理员! 方法 confirmOutMedOutMain\"}";
		}
		
		return "{\"msg\":\"出库确认成功.\",\"state\":\"true\"}";
	}

	@Override
	public String queryMedDeptMatchByMatchd(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedDeptMatchByMatchd(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryMedDeptMatchByMatchd(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	
	public boolean validateJSON(String str) {

		if (str != null && !"null".equals(str) && !"".equals(str)) {

			return true;

		}

		return false;

	}

	// 返回用用于保存的默认值
	public Map<String, Object> defaultDetailValue() {

		Map<String, Object> mapDetailVo = new HashMap<String, Object>();

		mapDetailVo.put("group_id", "0");
		mapDetailVo.put("hos_id", "0");
		mapDetailVo.put("copy_code", "");
		mapDetailVo.put("out_id", "0");
		mapDetailVo.put("out_no", "");
		// mapDetailVo.put("out_detail_id", "");
		mapDetailVo.put("inv_id", "0");
		mapDetailVo.put("inv_no", "0");
		mapDetailVo.put("batch_sn", "0");
		mapDetailVo.put("batch_no", "");
		mapDetailVo.put("price", "0");
		mapDetailVo.put("amount", "0");
		mapDetailVo.put("sale_price", "0");
		mapDetailVo.put("sale_money", "0");
		mapDetailVo.put("sell_price", "0");
		mapDetailVo.put("sell_money", "0");
		mapDetailVo.put("allot_price", "0");
		mapDetailVo.put("allot_money", "0");
		mapDetailVo.put("amount_money", "0");
		mapDetailVo.put("num_exchange", "0");
		mapDetailVo.put("pack_price", "0");
		mapDetailVo.put("num", "0");
		mapDetailVo.put("bar_code", "");
		mapDetailVo.put("is_per_bar", "0");
		mapDetailVo.put("sn", "");
		mapDetailVo.put("inva_date", "");
		mapDetailVo.put("disinfect_date", "");
		mapDetailVo.put("location_id", "0");
		mapDetailVo.put("note", "");
		mapDetailVo.put("pack_code", "");

		return mapDetailVo;
	}

	/**
	 * 返回用用于保存的默认值 out_id 序列 out_no 自动生成 year 年 截取入库日期 month 月 截取入库日期
	 * bus_type_code 前台选择 store_id 前台选择 分割提取 store_no 前台选择 分割提取 brief 摘要 前台提取
	 * out_date 出库日期 前台提取 dept_id 前台选择 分割提取 dept_no 前台选择 分割提取 dept_emp 前台选择
	 * company 调拨单位 前台录入
	 */
	public Map<String, Object> defaultValue(Map<String, Object> mapVo) {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", mapVo.get("group_id"));
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", mapVo.get("hos_id"));
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", mapVo.get("copy_code"));
		}

		if (mapVo.get("year") == null) {
			mapVo.put("year", "");
		}

		if (mapVo.get("month") == null) {
			mapVo.put("month", "");
		}

		if (mapVo.get("bus_type_code") == null) {
			mapVo.put("bus_type_code", "");
		}

		if (mapVo.get("store_id") == null) {
			mapVo.put("store_id", "");
		}

		if (mapVo.get("store_no") == null) {
			mapVo.put("store_no", "");
		}

		if (mapVo.get("brief") == null) {
			mapVo.put("brief", "");
		}

		if (mapVo.get("out_date") == null) {
			mapVo.put("out_date", "");
		}

		if (mapVo.get("dept_id") == null) {
			mapVo.put("dept_id", "");
		}

		if (mapVo.get("dept_no") == null) {
			mapVo.put("dept_no", "");
		}

		if (mapVo.get("dept_emp") == null) {
			mapVo.put("dept_emp", "");
		}

		if (mapVo.get("company") == null) {
			mapVo.put("company", "");
		}

		if (mapVo.get("use_code") == null) {
			mapVo.put("use_code", "");
		}

		if (mapVo.get("proj_id") == null) {
			mapVo.put("proj_id", "");
		}

		if (mapVo.get("maker") == null) {
			mapVo.put("maker", "");
		}

		if (mapVo.get("checker") == null) {
			mapVo.put("checker", "");
		}

		if (mapVo.get("check_date") == null) {
			mapVo.put("check_date", "");
		}

		if (mapVo.get("confirmer") == null) {
			mapVo.put("confirmer", "");
		}

		if (mapVo.get("confirm_date") == null) {
			mapVo.put("confirm_date", "");
		}

		if (mapVo.get("state") == null) {
			mapVo.put("state", "1");
		}

		if (mapVo.get("is_dir") == null) {
			mapVo.put("is_dir", "0");
		}

		if (mapVo.get("his_flag") == null) {
			mapVo.put("his_flag", "");
		}

		return mapVo;
	}

	public Map<String, Object> defaultBeanToMap(MedOutMain mom) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", mom.getGroup_id());
		map.put("hos_id", mom.getHos_id());
		map.put("copy_code", mom.getCopy_code());
		map.put("out_id", mom.getOut_id());
		map.put("year", mom.getYear());
		map.put("month", mom.getMonth());
		map.put("bus_type_code", mom.getBus_type_code());
		map.put("store_id", mom.getStore_id());
		map.put("store_no", mom.getStore_no());
		map.put("out_no", mom.getOut_no());
		map.put("brief", mom.getBrief());
		map.put("out_date", DateUtil.dateToString(mom.getOut_date(), "yyyy-MM-dd"));
		map.put("dept_id", mom.getDept_id());
		map.put("dept_no", mom.getDept_no());
		map.put("dept_emp", mom.getDept_emp());
		map.put("use_code", mom.getUse_code());
		map.put("proj_id", mom.getProj_id());
		map.put("maker", mom.getMaker());
		map.put("checker", mom.getChecker());
		map.put("check_date", DateUtil.dateToString(mom.getCheck_date(), "yyyy-MM-dd"));
		map.put("confirmer", mom.getConfirmer());
		map.put("confirm_date", DateUtil.dateToString(mom.getConfirm_date(), "yyyy-MM-dd"));
		map.put("state", mom.getState());
		map.put("is_dir", mom.getIs_dir());
		map.put("his_flag", mom.getHis_flag());

		return map;
	}

	public Map<String, Object> detailBeanToMap(MedOutDetail mod) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", mod.getGroup_id());
		map.put("hos_id", mod.getHos_id());
		map.put("copy_code", mod.getCopy_code());
		map.put("store_id", mod.getStore_id());
		map.put("out_id", mod.getOut_id());
		// map.put("out_detail_id", 0);
		map.put("inv_id", mod.getInv_id());
		map.put("inv_no", mod.getInv_no());
		map.put("batch_sn", mod.getBatch_sn());
		map.put("batch_no", mod.getBatch_no());
		map.put("price", mod.getPrice());
		map.put("amount", mod.getAmount());
		map.put("sale_price", mod.getSale_price());
		map.put("sale_money", mod.getSale_money() != null ? mod.getSale_money() : "0");
		map.put("sell_price", mod.getSell_price());
		map.put("sell_money", mod.getSell_price() != null ? mod.getSell_money() : "0");
		map.put("allot_price", mod.getAllot_price() != null ? mod.getAllot_price() : "0");
		map.put("allot_money", mod.getAllot_money() != null ? mod.getAllot_money() : "0");
		map.put("amount_money", mod.getAmount_money() != null ? mod.getAmount_money() : "0");
		map.put("num_exchange", mod.getNum_exchange() != null ? mod.getNum_exchange() : "0");
		map.put("pack_price", mod.getPack_price() != null ? mod.getPack_price() : "0");
		map.put("num", mod.getNum() != null ? mod.getNum() : "0");
		map.put("bar_code", mod.getBar_code() != null ? mod.getBar_code() : "");
		map.put("inva_date", mod.getInva_date() != null ? DateUtil.dateToString(mod.getInva_date(), "yyyy-MM-dd") : "");
		map.put("disinfect_date", mod.getDisinfect_date() != null ? DateUtil.dateToString(mod.getDisinfect_date(), "yyyy-MM-dd") : "");
		map.put("location_id", mod.getLocation_id() != null ? mod.getLocation_id() : "0");
		map.put("note", mod.getNote() != null ? mod.getNote() : "");
		map.put("pack_code", mod.getPack_code() != null ? mod.getPack_code() : "");

		return map;
	}

	@SuppressWarnings("unchecked")
    @Override
	public Map<String, Object> queryMedOutMainByCode(Map<String, Object> entityMap) throws DataAccessException {
		return JsonListMapUtil.toMapLower((Map<String, Object>) medOutMainMapper.queryMedOutMainByCode(entityMap));
	}

	@Override
	public String queryMedOutDetailByOutId(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = (List<Map<String, Object>>) medOutDetailMapper.queryMedOutDetailByOutId(entityMap);

		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * @Description 获取下一张或上一张出库单ID
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryMedOutMainBeforeOrNext(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			//定义入库单ID
			int out_id = 0;
			
			//上一张
			if("before".equals(entityMap.get("type").toString())){
				out_id = medOutMainMapper.queryMedOutMainPre(entityMap);
				if(out_id == 0){
					return "{\"msg\":\"已经是第一张单据了！\",\"state\":\"false\"}";
				}
			}else if("next".equals(entityMap.get("type").toString())){//下一张
				out_id = medOutMainMapper.queryMedOutMainNext(entityMap);
				if(out_id == 0){
					return "{\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"操作失败 页面参数异常 请联系管理员！方法queryMedOutMainBeforeOrNext\"}";
			}
			
			return "{\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				out_id+","
				+"\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMedStorageInBeforeOrNext\"}";
		}	
	}
	
	//list<map>键转小写
	public List<Map<String, Object>> toListMapLower(List<Map<String, Object>> list) {
		
		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();
		
		if (list.size() > 0) {

			for (Map<String, Object> map : list) {

				Map<String, Object> viewMap = new HashMap<String, Object>();

				for (String key : map.keySet()) {

					viewMap.put(key.toLowerCase(), map.get(key));
				}

				viewList.add(viewMap);
			}
		}
		return viewList;
	}
	
	/*//出库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMedOutByPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		if("1".equals(String.valueOf(entityMap.get("p_num")))){
			System.out.println("=============="+entityMap);
			//查询出库主表批量
			List<Map<String,Object>> map=medOutMainMapper.queryMedOutPrintTemlateByMainBatch(entityMap);
			//查询出库明细表
			List<Map<String,Object>> list=medOutMainMapper.queryMedOutPrintTemlateByDetail(entityMap);
			return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
		}else{
			//查询出库主表
			Map<String,Object> map=medOutMainMapper.queryMedOutPrintTemlateByMain(entityMap);
			//查询出库明细表
			List<Map<String,Object>> list=medOutMainMapper.queryMedOutPrintTemlateByDetail(entityMap);
			return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
		}
		
	}*/
	
	//出库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public Map<String,Object> queryMedOutByPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		MedOutMainMapper medOutMainMapper = (MedOutMainMapper)context.getBean("medOutMainMapper");
		
		
		if("1".equals(String.valueOf(entityMap.get("p_num")))){
			
			//查询出库主表批量
			List<Map<String,Object>> map=medOutMainMapper.queryMedOutPrintTemlateByMainBatch(entityMap);
			//查询出库明细表
			List<Map<String,Object>> list=medOutMainMapper.queryMedOutPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}else{
			//查询出库主表
			Map<String,Object> map=medOutMainMapper.queryMedOutPrintTemlateByMain(entityMap);
			//查询出库明细表
			List<Map<String,Object>> list=medOutMainMapper.queryMedOutPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}
		
	}
	
	/**
	 * @Description 冲账<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String mergeOffsetMedOutMain(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list= (List<Map<String, Object>>) medOutDetailMapper.mergeOffsetMedOutMain(entityMap);
		
		return  ChdJson.toJsonLower(list);  
	}

	/**
	 * @Description 查询科室申领主表数据
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String queryMedApplyMainOut(Map<String,Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(medOutMainMapper.queryMedApplyMainOut(entityMap));
			
			return ChdJson.toJson(list);
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(medOutMainMapper.queryMedApplyMainOut(entityMap, rowBounds));
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * @Description 查询科室申领明细数据
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String queryMedApplyDetailOut(Map<String,Object> entityMap) throws DataAccessException{

		List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(medOutMainMapper.queryMedApplyDetailOut(entityMap));

		return ChdJson.toJsonLower(list);
	}

	/**
	 * @Description 申领单汇总生成出库单--组装出库主表
	 * @param entityMap
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> queryOutMainByAppCollect(Map<String,Object> entityMap) throws DataAccessException{
		Map<String, Object> mainMap = new HashMap<String, Object>();
		List<Map<String, Object>> mainList = JsonListMapUtil.toListMapLower(medOutMainMapper.queryOutMainByAppCollect(entityMap));
		if(mainList.size() > 1){
			mainMap.put("error", "{\"error\":\"请选择相同仓库、库房的申领单！\"}");
			return mainMap;
		}else{
			mainMap.putAll(mainList.get(0));
		}
		
		return mainMap;
	}

	/**
	 * @Description 申领单汇总生成出库单--组装出库明细表
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String queryOutDetailByAppCollect(Map<String,Object> entityMap) throws DataAccessException{//存放出库单明细信息
		try {
			//明细信息
			List<Map<String, Object>> detailList = toListMapLower((List<Map<String, Object>>) medOutMainMapper.queryOutDetailByAppCollect(entityMap));
			return medCommonService.getInvJsonByInvList(detailList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 addOutBatchMedCommonOutApplyCheck\"}";
		}	
	}

	/**
	 * @Description 查询药品批次列表
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String queryMedInvBatchList(Map<String,Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(medOutMainMapper.queryMedInvBatchList(entityMap));
			
			return ChdJson.toJson(list);
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(medOutMainMapper.queryMedInvBatchList(entityMap, rowBounds));
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 选择药品返回List
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String queryOutInvListByChoiceInv(List<Map<String,Object>> entityList) throws DataAccessException{
		try {
			return medCommonService.getInvJsonByInvList(entityList);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
	

	/**
	 * @Description 申领单汇总生成出库单--组装出库明细表
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public String queryOutDetailByAppCollect1(Map<String,Object> entityMap) throws DataAccessException{//存放出库单明细信息
		List<Map<String, Object>> outDetailList = new ArrayList<Map<String,Object>>();
		//是否退库
		boolean is_back = false; 
		if(entityMap.get("is_back") != null && !"1".equals(entityMap.get("is_back"))){
			is_back = true;
		}
		//药品Map的key值
		String invKey = "";
		//药品Map的key值
		String beforeInvKey = "";
		//出库药品Map，用于合并相同药品(key：inv_id+batch_no+bar_code+price+location_id，value：明细Map)
		Map<String, Map<String, Object>> invMap = new HashMap<String, Map<String, Object>>();
		//出库药品Map，用于合并相同药品(key：inv_id+batch_no+bar_code+price+location_id，value：明细隐藏的json串)
		Map<String, StringBuffer> invJsonMap = new HashMap<String, StringBuffer>();

		//药品Map的key值
		String immeKey = "";
		//出库药品Map，用于判断库存(key : inv_id，value：药品库存List)
		Map<String, List<Map<String, Object>>> immeMap = new HashMap<String, List<Map<String,Object>>>();
			
		try {
			//明细信息
			List<Map<String, Object>> detailList = toListMapLower((List<Map<String, Object>>) medOutMainMapper.queryOutDetailByAppCollect(entityMap));
			
			for(Map<String, Object> map : detailList){
				//获取药品来源--申请单ID@明细ID@剩余申请数量
				
				double amount = Double.valueOf(map.get("amount") == null ? "0" : map.get("amount").toString());
				double imme_amount = 0;
				double do_amount = 0;
				immeKey = map.get("inv_id").toString();
				//先进先出药品列表
				List<Map<String, Object>> fifoList;
				if(immeMap.containsKey(immeKey)){
					fifoList = immeMap.get(immeKey);
				}else{
					//fifo帐表信息
					fifoList = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedStockInvDetailList(map));
				}
				StringBuffer inv_detail_data = new StringBuffer();  //存储批次明细信息的json串
				String batchBar = "";  //用于判断批号条码是否相同
				String newBatchBar = "";  //用于判断批号条码是否相同
				boolean isNot_frist = false;  //是否不是第一次循环
				
				//存放明细记录
				Map<String, Object> detailMap = new HashMap<String, Object>();
				
				//按先进先出生成出库单
				for(Map<String, Object> fifoMap : fifoList){
					
					//当前批次即时库存
					imme_amount = Double.valueOf(String.valueOf((fifoMap.get("imme_amount") == null ? 0 : fifoMap.get("imme_amount"))));
					if(imme_amount == 0){
						continue;
					}
					
					//获取invKey
					invKey = map.get("inv_id").toString() + fifoMap.get("batch_no").toString() + fifoMap.get("bar_code").toString() + fifoMap.get("price").toString();

					boolean isNotAlready = true;
					//如果该药品已存在
					if(invMap.containsKey(invKey)){
						//获取已有的detailMap
						detailMap.putAll(invMap.get(invKey));
						//初始化batchBar
						batchBar = fifoMap.get("batch_no").toString() + fifoMap.get("bar_code").toString() + fifoMap.get("price").toString();
						isNotAlready = false;
					}
					
					newBatchBar = fifoMap.get("batch_no").toString() + fifoMap.get("bar_code").toString() + fifoMap.get("price").toString();
					//药品是否已出完
					if(amount > 0){
						/*药品主信息---------------begin----------------------*/
						if(!batchBar.equals(newBatchBar)){
							//如果该药品第二次出现批号条码不对应则需要添加新的明细记录
							if(isNot_frist){
								//追加invJson
								if(invJsonMap.containsKey(beforeInvKey)){
									invJsonMap.put(beforeInvKey, invJsonMap.get(beforeInvKey).append(inv_detail_data));
								}else{
									invJsonMap.put(beforeInvKey, inv_detail_data);
								}
								if(detailMap.get("inv_id") != null){
									String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
									if("".equals(other_ids)){
										other_ids = map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
									}else{
										other_ids += "," + map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
									}
									detailMap.put("other_ids", other_ids);
									//存储
									invMap.put(beforeInvKey, detailMap);
								}
								
								detailMap = new HashMap<String, Object>();
								inv_detail_data = new StringBuffer();
							}
							/**---------------------数量初始化--begin---------------------*/
							detailMap.put("amount", 0);
							detailMap.put("sum_amount", 0);
							detailMap.put("cur_amount", 0);
							detailMap.put("imme_amount", 0);
							detailMap.put("amount_money", 0);
							detailMap.put("sale_money", 0);
							detailMap.put("sell_money", 0);
							/**---------------------数量初始化--end-----------------------*/
							detailMap.put("inv_id", fifoMap.get("inv_id"));
							detailMap.put("inv_no", fifoMap.get("inv_no"));
							detailMap.put("inv_code", fifoMap.get("inv_code"));
							detailMap.put("inv_name", fifoMap.get("inv_name"));
							detailMap.put("inv_model", fifoMap.get("inv_model"));
							detailMap.put("unit_name", fifoMap.get("unit_name"));
							detailMap.put("med_type_name", fifoMap.get("med_type_name"));
							detailMap.put("fac_name", fifoMap.get("fac_name"));
							detailMap.put("batch_no", fifoMap.get("batch_no"));
							detailMap.put("bar_code", fifoMap.get("bar_code"));
							detailMap.put("price", fifoMap.get("price"));
							detailMap.put("sale_price", fifoMap.get("sale_price"));
							detailMap.put("sell_price", fifoMap.get("sell_price"));
							detailMap.put("location_id", fifoMap.get("location_id"));
							detailMap.put("location_code", fifoMap.get("location_code"));
							detailMap.put("location_name", fifoMap.get("location_name"));
							if(ChdJson.validateJSON(String.valueOf(fifoMap.get("inva_date")))){
								detailMap.put("inva_date", DateUtil.dateToString((Date)fifoMap.get("inva_date"), "yyyy-MM-dd"));
							}else{
								detailMap.put("inva_date", fifoMap.get("inva_date"));
							}
							if(ChdJson.validateJSON(String.valueOf(fifoMap.get("disinfect_date")))){
								detailMap.put("disinfect_date", DateUtil.dateToString((Date)fifoMap.get("disinfect_date"), "yyyy-MM-dd"));
							}else{
								detailMap.put("disinfect_date", fifoMap.get("disinfect_date"));
							}
							/**---------------------数量计算---------------------*/
							detailMap.put("cur_amount", fifoMap.get("cur_amount"));
							detailMap.put("imme_amount", fifoMap.get("imme_amount"));
							inv_detail_data.append("[");
						}else{
							if(isNotAlready){
								//库存信息累加
								detailMap.put("cur_amount", Double.valueOf(detailMap.get("cur_amount").toString())+Double.valueOf(fifoMap.get("cur_amount").toString()));
								detailMap.put("imme_amount", Double.valueOf(detailMap.get("imme_amount").toString())+Double.valueOf(fifoMap.get("imme_amount").toString()));
							}
						}
						/*药品主信息---------------end------------------------*/
						/*药品批次信息-------------begin----------------------*/
						inv_detail_data.append("{\"inv_id\":").append(fifoMap.get("inv_id"))
							.append(",\"inv_no\":").append(fifoMap.get("inv_no"))
							.append(",\"inv_code\":\"").append(fifoMap.get("inv_code"))
							.append("\",\"inv_name\":\"").append(fifoMap.get("inv_name"))
							.append("\",\"batch_sn\":").append(fifoMap.get("batch_sn"))
							.append(",\"cur_amount\":").append(fifoMap.get("cur_amount"))
							.append(",\"imme_amount\":").append(fifoMap.get("imme_amount"))
							.append(",\"price\":").append(fifoMap.get("price"))
							.append(",\"sale_price\":").append(fifoMap.get("sale_price"))
							.append(",\"sell_price\":").append(fifoMap.get("sell_price"));
						
						//本批次数量充足直接出库
						if(amount <= imme_amount){
							detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) + amount);
							detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) + amount);
							detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
							detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
							detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
							inv_detail_data.append(",\"amount\":").append(amount)
								.append(",\"amount_money\":").append(amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))))
								.append(",\"sale_money\":").append(amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))))
								.append(",\"sell_money\":").append(amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))))
								.append("},");
							fifoMap.put("imme_amount", NumberUtil.sub(imme_amount, amount));
							do_amount += amount;  //实际处理数量
							amount = 0.0;
						}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
							detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) + imme_amount);
							detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) + imme_amount);
							detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
							detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
							detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
							inv_detail_data.append(",\"amount\":").append(imme_amount)
								.append(",\"amount_money\":").append(imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))))
								.append(",\"sale_money\":").append(imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))))
								.append(",\"sell_money\":").append(imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))))
								.append("},");
							fifoMap.put("imme_amount", 0);
							do_amount += imme_amount;  //实际处理数量
							amount = NumberUtil.sub(amount, imme_amount);
						}
						/*药品批次信息-------------end------------------------*/
					}else{
						/*药品主信息---------------begin----------------------*/
						if(!batchBar.equals(newBatchBar)){
							if(invJsonMap.containsKey(beforeInvKey)){
								invJsonMap.put(beforeInvKey, invJsonMap.get(beforeInvKey).append(inv_detail_data));
							}else{
								invJsonMap.put(beforeInvKey, inv_detail_data);
							}
							//处理科室申领对应关系
							if(map.get("apply_id") != null && !"".equals(map.get("apply_id").toString())){
								String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
								if("".equals(other_ids)){
									other_ids = map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
								}else{
									other_ids += "," + map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
								}
								detailMap.put("other_ids", other_ids);
							}
							invMap.put(beforeInvKey, detailMap);
							break;
						}else{
							if(isNotAlready){
								//库存信息累加
								detailMap.put("cur_amount", Double.valueOf(detailMap.get("cur_amount").toString())+Double.valueOf(fifoMap.get("cur_amount").toString()));
								detailMap.put("imme_amount", Double.valueOf(detailMap.get("imme_amount").toString())+Double.valueOf(fifoMap.get("imme_amount").toString()));
							}
						}
						/*药品主信息---------------end----------------------*/
					}
					//记录批号条码信息
					batchBar = newBatchBar;
					beforeInvKey = invKey;
					isNot_frist = true;
				}
				if(batchBar.equals(newBatchBar) && isNot_frist){
					if(invJsonMap.containsKey(invKey)){
						invJsonMap.put(invKey, invJsonMap.get(invKey).append(inv_detail_data));
					}else{
						invJsonMap.put(invKey, inv_detail_data);
					}
					if(detailMap.get("inv_id") != null){
						String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
						if("".equals(other_ids)){
							other_ids = map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
						}else{
							other_ids += "," + map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
						}
						detailMap.put("other_ids", other_ids);
						invMap.put(invKey, detailMap);
					}
				}

				immeMap.put(immeKey, fifoList);
			}
			
			for (String key : invMap.keySet()) {
				Map<String, Object> detailMap = invMap.get(key);
				StringBuffer detailJson = invJsonMap.get(key);
				detailJson.substring(0, detailJson.length()-1);
				detailJson.append("]");
				detailMap.put("inv_detail_data", detailJson.toString());
				outDetailList.add(detailMap);
			}
			
			if(outDetailList.size() == 0){
				return "{\"error\":\"所选单据药品库存药品不足\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 addOutBatchMedCommonOutApplyCheck\"}";
		}	
		
		return ChdJson.toJson(outDetailList);
	}
	
	/**
	 * 出库明细查询
	 */
	@Override
	public String queryOutDetails(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryOutDetails(entityMap);
			return ChdJson.toJsonLower(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medOutMainMapper.queryOutDetails(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@Override
	public void updateMedApplyOutRela(Map<String, Object> entityMap) throws DataAccessException {
		
		/*if("1".equals(entityMap.get("is_apply").toString())){
			List<Map<String,Object>> relaList= JsonListMapUtil.toListMapLower(medOutDetailMapper.queryMedOutDetailAmount(entityMap));
			if(relaList.size()>0){
				medOutDetailMapper.updateMedApplyOutRela(relaList);
			}
		}
		*/
		
		
		List<Map<String,Object>> relaList= JsonListMapUtil.toListMapLower(medOutDetailMapper.queryMedApplyOutRela(entityMap));
		if(relaList.size()>0){
			medOutDetailMapper.deleteMedApplyOutRela(relaList);
		}
		
	}
	/**
	 * 查看出库单是否是科室申领生成的
	 */
	@Override
	public int queryMedOutMainIsApply(Map<String, Object> entityMap) throws DataAccessException {
		Integer  is_apply = medOutDetailMapper.queryMedOutMainIsApply(entityMap);
		return is_apply;
	}
}
