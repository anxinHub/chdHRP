/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.mednopaydeliver;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedInCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.base.MedNoOtherMapper;
import com.chd.hrp.med.dao.initial.MedInitChargeMapper;
import com.chd.hrp.med.dao.mednopaydeliver.MedNopayDeliverMapper;
import com.chd.hrp.med.entity.MedInMain;
import com.chd.hrp.med.entity.MedNopayDeliver;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.mednopaydeliver.MedNopayDeliverService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 期初未付款送货单入库
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medNopayDeliverService")
public class MedNopayDeliverServiceImpl implements MedNopayDeliverService {

	private static Logger logger = Logger.getLogger(MedNopayDeliverServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medNopayDeliverMapper")
	private final MedNopayDeliverMapper medNopayDeliverMapper = null;
	@Resource(name = "medInCommonMapper")
	private final MedInCommonMapper medInCommonMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	@Resource(name = "medInitChargeMapper")
	private final MedInitChargeMapper medInitChargeMapper = null;
	
	@Resource(name = "medNoOtherMapper")
	private final MedNoOtherMapper medNoOtherMapper = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	/**
	 * @Description 
	 * 添加期初未付款送货单入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addMedNopayDeliver(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = medCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成 期初未付款送货单单据号
			Map<String,Object> specialMap = new HashMap<String,Object>();
			specialMap.put("group_id", entityMap.get("group_id"));
			specialMap.put("hos_id", entityMap.get("hos_id"));
			specialMap.put("copy_code", entityMap.get("copy_code"));
			specialMap.put("year", entityMap.get("year"));
			specialMap.put("month", entityMap.get("month"));
			specialMap.put("store_id", entityMap.get("store_id"));
			specialMap.put("table_code", "MED_NOPAY_DELIVER");
			specialMap.put("prefixe", "");
			if("自动生成".equals(entityMap.get("deliver_no"))){
				entityMap.put("deliver_no", getMedNextNo(specialMap));
			}
			//期初未付款送货单单据ID
			entityMap.put("deliver_id", medNopayDeliverMapper.queryMedNopayDeliverNextval());
			
			//组装明细
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				float money_sum = 0;//记录明细总金额
				//用于查询个体码
				Map<String,Object> barCodeMap = new HashMap<String,Object>();
				barCodeMap.put("group_id", entityMap.get("group_id"));
				barCodeMap.put("hos_id", entityMap.get("hos_id"));
				barCodeMap.put("type_code", 1);
				String bar_code = medNoOtherMapper.queryMedNoOther(barCodeMap);//获取当前个体码
				//如果不存在则插入
				if(bar_code == null || "".equals(bar_code)){
					bar_code = "000000000000";
					barCodeMap.put("max_no", bar_code);
					medNoOtherMapper.insertMedNoOther(barCodeMap);
				}
				String init_bar_code = bar_code;
				
				///*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "med_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同药品批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				///*用于查询批次----end-----*/
				//保存明细数据
				JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("detailData")));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("deliver_id", entityMap.get("deliver_id"));//期初未付款送货单主表 单据ID
						detailMap.put("deliver_no", entityMap.get("deliver_no"));//期初未付款送货单主表  单据号
						detailMap.put("detail_id", medNopayDeliverMapper.queryMedNopayDeliverDetailSeq());//期初未付款送货单明细表id
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//药品 变更ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						money_sum = money_sum + Float.parseFloat(String.valueOf(detailMap.get("amount_money")));//记录总金额
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  String.valueOf(jsonObj.get("batch_no")));//批号
						}
						/**********************自动生成批次-------begin--------*/
						invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
						//判断是否已经取出该批号的最大批次
						if(invBatchKeySnMap.get(invBatchKey) == null){
							//查询该批号最大批次
							batchSnMap.put("c_value", detailMap.get("inv_id"));//药品ID
							batchSnMap.put("c_value1", detailMap.get("batch_no"));//药品批号
							String batchSn = medCommonMapper.getMedMaxNo(batchSnMap);//最大批次
							if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
								detailMap.put("batch_sn",  1);//批次
							}else{
								detailMap.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
							}
							invBatchKeySnMap.put(invBatchKey, Integer.parseInt(String.valueOf(detailMap.get("batch_sn"))));
						}else{
							detailMap.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) + 1);//批次
							invBatchKeySnMap.put(invBatchKey, invBatchKeySnMap.get(invBatchKey) + 1);
						}
						/**********************自动生成批次-------end---------*/
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

						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pack_code")))){
							detailMap.put("pack_code",  jsonObj.get("pack_code"));//包装单位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("num_exchange")))){
							detailMap.put("num_exchange",  jsonObj.get("num_exchange"));//转换量
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("num")))){
							detailMap.put("num",  jsonObj.get("num"));//包装数量
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pack_price")))){
							detailMap.put("pack_price",  jsonObj.get("pack_price"));//包装单价
						}else{
							detailMap.put("pack_price",  "0");//包装单价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate( String.valueOf(jsonObj.get("inva_date")), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( String.valueOf(jsonObj.get("disinfect_date")), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码（这里用个体码）
						}else{
							detailMap.put("sn", "-");
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar")))){
							detailMap.put("is_per_bar",  jsonObj.get("is_per_bar"));//是否个体码
						}else{
							detailMap.put("is_per_bar", "0");//是否个体码(默认否)
						}
						//生成个体码
						if("0".equals(String.valueOf(detailMap.get("is_per_bar")))){
							if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}else{
								detailMap.put("bar_code",  String.valueOf(jsonObj.get("bar_code")));//个体码
							}
							//该条明细数据添加到List中
							detailList.add(detailMap);
						}else{
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(String.valueOf(detailMap.get("amount"))); i++){
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(detailMap);
								if( i>1){
									barMap.put("detail_id", medNopayDeliverMapper.queryMedNopayDeliverDetailSeq());
								}
								bar_code = medCommonService.getNextBar_code(bar_code);
								//拆分数量和金额
								barMap.put("amount",  1);//数量
								barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
								barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
								barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("bar_code",  bar_code);//个体码
								//该条明细数据添加到List中
								detailList.add(barMap);
							}
						}
					}
				}
				//更新个体码
				if(!init_bar_code.equals(bar_code)){
					barCodeMap.put("max_no", bar_code);
					medNoOtherMapper.updateMedNoOther(barCodeMap);
				}
				
				//新增期初未付款送货单主表数据
				medNopayDeliverMapper.addMedNopayDeliver(entityMap);
				
				//批量新增期初未付款送货单明细数据
				medNopayDeliverMapper.addMedNopayDeliverDetailBatch(detailList);
				
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				entityMap.get("deliver_id").toString()+","
				+"\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	/**
	 * @Description 
	 * 批量添加期初未付款送货单入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}
	}
	
		/**
	 * @Description 
	 * 更新期初未付款送货单入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	
	public String updateMedNopayDeliver(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
				return "{\"error\":\"更新失败,单据状态不为未审核状态！.\",\"state\":\"false\"}";
			}
			
			Map<String,Object> deleteMap = new HashMap<String,Object>();
			
			deleteMap.put("group_id", entityMap.get("group_id"));
			deleteMap.put("hos_id", entityMap.get("hos_id"));
			deleteMap.put("copy_code", entityMap.get("copy_code"));
			deleteMap.put("deliver_id", entityMap.get("deliver_id"));
			deleteMap.put("deliver_no", entityMap.get("deliver_no"));
			
			//删除期初未付款送货单 明细数据
			medNopayDeliverMapper.deleteMedNopayDeliverDetail(deleteMap);
			
			//组装明细
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				float money_sum = 0;//记录明细总金额
				//用于查询个体码
				Map<String,Object> barCodeMap = new HashMap<String,Object>();
				barCodeMap.put("group_id", entityMap.get("group_id"));
				barCodeMap.put("hos_id", entityMap.get("hos_id"));
				barCodeMap.put("type_code", 1);
				String bar_code = medNoOtherMapper.queryMedNoOther(barCodeMap);//获取当前个体码
				//如果不存在则插入
				if(bar_code == null || "".equals(bar_code)){
					bar_code = "000000000000";
					barCodeMap.put("max_no", bar_code);
					medNoOtherMapper.insertMedNoOther(barCodeMap);
				}
				String init_bar_code = bar_code;
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "med_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同药品批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				/*用于查询批次----end-----*/
				//修改明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				
				
				//添加期初未付款送货单明细表数据 用List
				List<Map<String,Object>> detailAddList = new ArrayList<Map<String,Object>>();
				
				StringBuffer detail_ids = new StringBuffer();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						Map<String,Object> detailMap=new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("deliver_id", entityMap.get("deliver_id"));//期初未付款送货单主表 单据ID
						detailMap.put("deliver_no", entityMap.get("deliver_no"));//期初未付款送货单主表  单据号
						detailMap.put("detail_id", medNopayDeliverMapper.queryMedNopayDeliverDetailSeq());//期初未付款送货单入库明细表id
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//药品变更号
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  String.valueOf(jsonObj.get("batch_no")));//批号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_sn")))){
							detailMap.put("batch_sn", jsonObj.get("batch_sn"));
						}else{
							/**********************自动生成批次-------begin--------*/
							invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
							//判断是否已经取出该批号的最大批次
							if(invBatchKeySnMap.get(invBatchKey) == null){
								//查询该批号最大批次
								batchSnMap.put("c_value", detailMap.get("inv_id"));//药品ID
								batchSnMap.put("c_value1", detailMap.get("batch_no"));//药品批号
								String batchSn = medCommonMapper.getMedMaxNo(batchSnMap);//最大批次
								if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
									detailMap.put("batch_sn",  1);//批次
								}else{
									detailMap.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
								}
								invBatchKeySnMap.put(invBatchKey, Integer.parseInt(String.valueOf(detailMap.get("batch_sn"))));
							}else{
								detailMap.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) + 1);//批次
								invBatchKeySnMap.put(invBatchKey, invBatchKeySnMap.get(invBatchKey) + 1);
							}
							/**********************自动生成批次-------end---------*/
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))){
							detailMap.put("sell_price",  jsonObj.get("sell_price"));//零售单价
						}else{
							detailMap.put("sell_price",  "0");//零售单价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money"));//零售金额
						}else{
							detailMap.put("sell_money",  "0");//零售金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("allot_price")))){
							detailMap.put("allot_price",  jsonObj.get("allot_price"));//调拨价
						}else{
							detailMap.put("allot_price",  "0");//调拨价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("allot_money")))){
							detailMap.put("allot_money",  jsonObj.get("allot_money"));//调拨金额
						}else{
							detailMap.put("allot_money",  "0");//调拨金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pack_code")))){
							detailMap.put("pack_code",  jsonObj.get("pack_code"));//包装单位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("num_exchange")))){
							detailMap.put("num_exchange",  jsonObj.get("num_exchange"));//转换量
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("num")))){
							detailMap.put("num",  jsonObj.get("num"));//包装数量
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pack_price")))){
							detailMap.put("pack_price",  jsonObj.get("pack_price"));//包装单价
						}else{
							detailMap.put("pack_price",  "0");//包装单价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate( String.valueOf(jsonObj.get("inva_date")), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( String.valueOf(jsonObj.get("disinfect_date")), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码
						}else{
							detailMap.put("sn", "-");
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						
						//明细表ID
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("detail_id")))){
							// 期初未付款送货单明细表 ID
							detailMap.put("detail_id",  medNopayDeliverMapper.queryMedNopayDeliverDetailSeq());
							//生成个体码
							if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar"))) || "0".equals(String.valueOf(jsonObj.get("is_per_bar")))){
								if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
									detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
								}else{
									detailMap.put("bar_code",  String.valueOf(jsonObj.get("bar_code")));//个体码
								}
								//该条明细数据添加到List中
								detailAddList.add(detailMap);
							}else{
								//根据一码一物规则自动拆分数量并生成个体码
								for(int i = 1; i <= Integer.parseInt(String.valueOf(detailMap.get("num"))); i++){
									Map<String, Object> barMap = new HashMap<String, Object>();
									barMap.putAll(detailMap);
									bar_code = medCommonService.getNextBar_code(bar_code);
									if( i>1){
										barMap.put("detail_id", medNopayDeliverMapper.queryMedNopayDeliverDetailSeq());
									}
									//拆分数量和金额
									barMap.put("amount",  1);//数量
									barMap.put("num",  Float.parseFloat(String.valueOf(barMap.get("amount")))/Float.parseFloat(String.valueOf(detailMap.get("num_exchange"))));//包装件数
									barMap.put("pack_price",  Float.parseFloat(String.valueOf(detailMap.get("num")))*Float.parseFloat(String.valueOf(detailMap.get("price"))));//包装件数
									barMap.put("amount_money",  Float.parseFloat(String.valueOf(detailMap.get("amount_money")))/Float.parseFloat(String.valueOf(detailMap.get("amount"))));//金额
									barMap.put("bar_code",  bar_code);//个体码
									//该条明细数据添加到List中
									detailAddList.add(detailMap);
								}
							}
						}else{
							if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}else{
								detailMap.put("bar_code",  String.valueOf(jsonObj.get("bar_code")));//个体码
							}
							detail_ids.append(jsonObj.get("detail_id").toString()).append(",");
							detailMap.put("detail_id", jsonObj.get("detail_id"));
							detailAddList.add(detailMap);
						}
					}
				}
				//更新个体码
				if(!init_bar_code.equals(bar_code)){
					barCodeMap.put("max_no", bar_code);
					medNoOtherMapper.updateMedNoOther(barCodeMap);
				}
				
				//修改 期初未付款送货单主表数据
				medNopayDeliverMapper.updateMedNopayDeliver(entityMap);
				
				//批量添加  期初未付款送货单明细记录
				if(detailAddList.size() > 0){
					medNopayDeliverMapper.addMedNopayDeliverDetailBatch(detailAddList);
				}
				
			}else{
				return "{\"error\":\"更新失败，明细数据为空\",\"state\":\"false\"}";
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");
		}		
	}
	/**
	 * @Description 
	 * 批量更新期初未付款送货单入库，无此业务<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/

	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMedInitInCommon\"}";
		}	
	}
    
	/**
	 * @Description 
	 * 批量删除期初未付款送货单入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/

	public String deleteMedNopayDeliverBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//状态不为新建不能删除，从页面判断
			//期初记账状态不为0的不能删除，从页面判断
			
			//批量删除期初未付款送货单明细数据
			int  count = medNopayDeliverMapper.deleteMedNopayDeliverDetailBatch(entityList);
			
			//批量删除期初未付款送货单主数据
			medNopayDeliverMapper.deleteMedNopayDeliverBatch(entityList);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}	
	}
	
	
	//期初未付款送货单 出入库 审核、消审
	@Override
	public String updateStateMedNopayDeliver(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try {	
			//期初未付款送货单 入库单批量审核、消审
			medNopayDeliverMapper.updateStateMedNopayDeliver(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	/**
	 * 复制
	 */
	public String copyMedNopayDeliverBatch(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try {	
		
			//存放组装数据的List
			List<Map<String, Object>> copyMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> copyDetailList = new ArrayList<Map<String,Object>>();
			
			for( Map<String, Object> item : listVo){
				
				Map<String, Object> deliver = medNopayDeliverMapper.queryMedNopayDeliverById(item);
				
				List<Map<String,Object>> detailList = medNopayDeliverMapper.queryMedNopayDeliverDetailById(item);
				
				Map<String,Object> noMap = new HashMap<String,Object>();
				noMap.put("group_id", deliver.get("group_id"));
				noMap.put("hos_id", deliver.get("hos_id"));
				noMap.put("copy_code", deliver.get("copy_code"));
				noMap.put("year", deliver.get("year"));
				noMap.put("month", deliver.get("month"));
				noMap.put("store_id", deliver.get("store_id"));
				noMap.put("table_code", "MED_NOPAY_DELIVER");
				noMap.put("prefixe", "");
				
				deliver.put("deliver_no", getMedNextNo(noMap));
				deliver.put("deliver_id", medNopayDeliverMapper.queryMedNopayDeliverNextval());
				
				if(deliver.get("stock_type_code") == null || deliver.get("stock_type_code") == null){
					deliver.put("stock_type_code", "");
				}
				deliver.put("state", "1");
				deliver.put("checker", "");
				deliver.put("check_date", "");
				copyMainList.add(deliver) ;
				
				for(Map<String,Object> detail : detailList){
					detail.put("deliver_id",deliver.get("deliver_id"));
					detail.put("deliver_no",deliver.get("deliver_no"));
					detail.put("detail_id",medNopayDeliverMapper.queryMedNopayDeliverDetailSeq());
					
					//用于查询个体码
					Map<String,Object> barCodeMap = new HashMap<String,Object>();
					barCodeMap.put("group_id", detail.get("group_id"));
					barCodeMap.put("hos_id", detail.get("hos_id"));
					barCodeMap.put("type_code", 1);
					
					String bar_code = medNoOtherMapper.queryMedNoOther(barCodeMap);//获取当前个体码
					
					//生成个体码
					if("0".equals(String.valueOf(detail.get("is_per_bar")))){
						if(!ChdJson.validateJSON(String.valueOf(detail.get("bar_code")))){
							detail.put("bar_code",  String.valueOf(detail.get("bar_code")));//个体码
						}
						//该条明细数据添加到List中
						copyDetailList.add(detail);
					}else{
						//根据一码一物规则自动拆分数量并生成个体码
						for(int i = 1; i <= Integer.parseInt(detail.get("amount").toString()); i++){
							Map<String, Object> barMap = new HashMap<String, Object>();
							barMap.putAll(detail);
							if( i>1){
								barMap.put("detail_id", medNopayDeliverMapper.queryMedNopayDeliverDetailSeq());
							}
							bar_code = medCommonService.getNextBar_code(bar_code);
							
							barMap.put("bar_code",  bar_code);//个体码
							//该条明细数据添加到List中
							copyDetailList.add(barMap);
						}
					}
				}
			}
			
			medNopayDeliverMapper.addMedNopayDeliverBatch(copyMainList);
			
			medNopayDeliverMapper.addMedNopayDeliverDetailBatch(copyDetailList);
			
			return "{\"msg\":\"复制成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"复制失败\"}");
		}	
	}
	/**
	 * @Description 
	 * 添加期初未付款送货单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象期初未付款送货单入库
		List<Map<String, Object>> list = medNopayDeliverMapper.queryMedNopayDeliverDetailById(entityMap);

		if (list.size() > 0) {

			int state = medNopayDeliverMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medNopayDeliverMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedInitInCommon\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集  期初未付款送货单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryMedNopayDeliver(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = medNopayDeliverMapper.queryMedNopayDeliver(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medNopayDeliverMapper.queryMedNopayDeliver(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象期初未付款送货单入库<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/

	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return (T) medInCommonMapper.queryMedInMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象期初未付款送货单明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedNopayDeliverDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> list = medNopayDeliverMapper.queryMedNopayDeliverDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}

	/**
	 * 期初未付款送货单 更新页面回值查询
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryMedNopayDeliverMainUpdate(Map<String, Object> mapVo) throws DataAccessException{
		
		return medNopayDeliverMapper.queryMedNopayDeliverMainUpdate(mapVo);
	}
	/**
	 * 根据 期初未付款送货单入库单Id 查询 期初未付款送货单入库单明细
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryMedNopayDeliverDetail(Map<String, Object> mapVo) throws DataAccessException{
		return medNopayDeliverMapper.queryMedNopayDeliverDetail(mapVo);
	}
	
	/**
	 * 上一张 、 下一张
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedNopayDeliverBeforeOrNextNo(Map<String, Object> mapVo) throws DataAccessException{
		try {	
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			//上一张
			if("-1".equals(mapVo.get("flag").toString())){
				map = medNopayDeliverMapper.queryMedNopayDeliverBefore(mapVo);
				if(map == null){
					return "{\"state\":\"false\"}";
				}
			//下一张
			}else if("1".equals(mapVo.get("flag").toString())){
				map = medNopayDeliverMapper.queryMedNopayDeliverNext(mapVo);
				if(map == null ){
					return "{\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"操作失败 页面参数异常 请联系管理员！方法queryMedNopayDeliverBeforeOrNextNo\"}";
			}
			
			return "{\"state\":\"true\",\"update_para\":\""+
					mapVo.get("group_id").toString()+","+
					mapVo.get("hos_id").toString()+","+
					mapVo.get("copy_code").toString()+","+
					map.get("special_id")+","+
					map.get("deliver_no")+","
					+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMedNopayDeliverBeforeOrNextNo\"}";
		}	
	}
	
	/**
	 * 期初未付款送货单  单据号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String getMedNextNo(Map<String, Object> entityMap) throws DataAccessException{
		if(entityMap.get("group_id") == null){
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		if(entityMap.get("hos_id") == null){
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		if(entityMap.get("copy_code") == null){
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("store_id", entityMap.get("store_id"));
		//获取仓库别名store_alias
		String store_alias = medCommonMapper.queryStoreAliasById(map);
		
		String bus_type = "NP" ;
		map.put("store_alias", store_alias);
		map.put("bus_type", bus_type);//期初未付款单送货单  
		map.put("table_code", entityMap.get("table_code"));
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		map.put("prefixe", entityMap.get("prefixe"));
		
		//判断是否存在该业务流水码
		int flag = medNoManageMapper.queryIsExists(map);
		String max_no = "";
		if(flag == 0){
			//如不存在则流水码为1，并插入流水码表中
			max_no = "1";
			map.put("max_no", 1);
			medNoManageMapper.add(map);
		}else{
			//更新该业务流水码+1
			medNoManageMapper.updateMaxNo(map);
			//取出该业务更新后的流水码
			max_no = medNoManageMapper.queryMaxCode(map);
		}
		//补流水码前缀0
		for (int i = max_no.length() ; i < 4; i++) {
			max_no = "0" + max_no;
		}
		//组装流水码
		String next_no = store_alias + "-" + bus_type + entityMap.get("year").toString().substring(2, 4) + entityMap.get("month").toString() + max_no;
		
		return next_no;
	}
	@Override
	public int queryBillOrNot(Map<String, Object> mapVo) throws DataAccessException {
		
		return medNopayDeliverMapper.queryBillOrNot(mapVo);
	}

	
	//模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMedNopayDeliveryByPrintTemlate1(Map<String, Object> entityMap)throws DataAccessException {
		try{
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
					List<Map<String,Object>> map=medNopayDeliverMapper.queryPrintTemlateByMainBatch(entityMap);
					List<Map<String,Object>> list = medNopayDeliverMapper.queryPrintTemlateByDetail(entityMap);
					return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
					//查询主表
					Map<String,Object> map = medNopayDeliverMapper.queryPrintTemlateByMain(entityMap);
						
					//查询明细表
					List<Map<String,Object>> list = medNopayDeliverMapper.queryPrintTemlateByDetail(entityMap);
					return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
						
				}
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
		}
	
}
