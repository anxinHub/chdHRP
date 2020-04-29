/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.affi.tran;

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
import com.chd.hrp.med.dao.affi.in.MedAffiInCommonMapper;
import com.chd.hrp.med.dao.affi.out.MedAffiOutCommonMapper;
import com.chd.hrp.med.dao.affi.tran.MedAffiTranDetailMapper;
import com.chd.hrp.med.dao.affi.tran.MedAffiTranMainMapper;
import com.chd.hrp.med.dao.affi.tran.MedAffiTranRelaMapper;
import com.chd.hrp.med.dao.base.MedAffiBalanceMapper;
import com.chd.hrp.med.dao.base.MedAffiBatchMapper;
import com.chd.hrp.med.dao.base.MedAffiFifoMapper;
import com.chd.hrp.med.dao.base.MedAffiInMapper;
import com.chd.hrp.med.dao.base.MedAffiInvHoldMapper;
import com.chd.hrp.med.dao.base.MedAffiOutMapper;
import com.chd.hrp.med.dao.base.MedApplyOutRelaMapper;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.entity.MedAffiTranDetail;
import com.chd.hrp.med.entity.MedAffiTranMain;
import com.chd.hrp.med.entity.MedAffiTranRela;
import com.chd.hrp.med.service.affi.tran.MedAffiTranMainService;
import com.chd.hrp.med.service.base.MedCommonService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
 * @Table:
 * MED_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAffiTranMainService")
public class MedAffiTranMainServiceImpl implements MedAffiTranMainService{

	private static Logger logger = Logger.getLogger(MedAffiTranMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAffiTranMainMapper")
	private final MedAffiTranMainMapper medAffiTranMainMapper = null;
	
	@Resource(name = "medAffiTranDetailMapper")
	private final MedAffiTranDetailMapper medAffiTranDetailMapper = null;
	
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;

	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	  
	@Resource(name = "medAffiOutMapper")
	private final MedAffiOutMapper medAffiOutMapper = null;
	
	@Resource(name = "medAffiInMapper")
	private final MedAffiInMapper medAffiInMapper = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	@Resource(name = "medAffiTranRelaMapper")
	private final MedAffiTranRelaMapper medAffiTranRelaMapper = null;
	
	@Resource(name = "medAffiFifoMapper")
	private final MedAffiFifoMapper medAffiFifoMapper = null;
	
	@Resource(name = "medAffiInvHoldMapper")
	private final MedAffiInvHoldMapper medAffiInvHoldMapper = null;
	
	@Resource(name = "medAffiBatchMapper")
	private final MedAffiBatchMapper medAffiBatchMapper = null;
	
	@Resource(name = "medAffiBalanceMapper")
	private final MedAffiBalanceMapper medAffiBalanceMapper = null;
	
	@Resource(name = "medAffiInCommonMapper")
	private final MedAffiInCommonMapper medAffiInCommonMapper = null;
	@Resource(name = "medAffiOutCommonMapper")
	private final MedAffiOutCommonMapper medAffiOutCommonMapper = null;
	@Resource(name = "medApplyOutRelaMapper")
	private final MedApplyOutRelaMapper medApplyOutRelaMapper = null;
	
	/**
	 * @Description 
	 * 添加调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象调拨方式TRAN_METHOD：1同价调拨  2 异价调拨 调拨类型：1 院内调拨  2 院外调拨
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("08005"));
			entityMap = defaultValue(entityMap);
			String tran_date = (String) entityMap.get("tran_date");
			entityMap.put("year", tran_date.substring(0, 4));
			entityMap.put("month", tran_date.substring(5, 7));	
			entityMap.put("day", tran_date.substring(8, 10));	  //用于生成单号
			int flag = 1;			
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = medCommonMapper.existsAccYearMonthCheck(entityMap);			
			if(flag == 0){				
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";				
			}			
			//(2)、判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能保存！			
			flag = medCommonMapper.existsMedYearMonthIsAccount(entityMap);
			if(flag != 0){				
				return "{\"error\":\"添加失败，当月已结账不能保存！\",\"state\":\"false\"}";				
			}

			Long tran_id = medAffiTranMainMapper.queryMedAffiTranMainSeq();// 查询序列

			entityMap.put("tran_id", tran_id);
			entityMap.put("tran_no", getNextIn_no(entityMap));// 获取出库单号
			entityMap.put("state", "1");
			/*--------------需要同时生成出入库单--begin----------------------------*/
			//注：院内调拨需要同时生成出入库单；院外调拨只生成出库单，入库单在调入单位调入确认时生成。
			String brief = "由调拨单"+entityMap.get("tran_no")+"生成";
			String out_bus = "";
			String in_bus = "";
			boolean is_in = false;
			if("1".equals(String.valueOf(entityMap.get("tran_type")))){
				//院内调拨
				out_bus = "32";  //移出库
				in_bus = "31";  //移入库
				is_in = true;
			}else{
				//院外调拨
				if("1".equals(entityMap.get("bus_type").toString())){
					out_bus = "7";  //无偿调出
					in_bus = "6";  //无偿调入
				}else{
					out_bus = "5";  //有偿调出
					in_bus = "4";  //有偿调入
				}
			}
			//存放出库主从表
			Map<String, Object> outMap = new HashMap<String, Object>();
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			//存放入库主从表
			Map<String, Object> inMap = new HashMap<String, Object>();
			List<Map<String, Object>> inDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			//生成出库单
			outMap.put("group_id", entityMap.get("group_id"));
			outMap.put("hos_id", entityMap.get("hos_id"));
			outMap.put("copy_code", entityMap.get("copy_code"));
			outMap.put("year", entityMap.get("year"));
			outMap.put("month", entityMap.get("month"));
			outMap.put("day", entityMap.get("day"));  //用于生成单号
			outMap.put("bus_type_code", out_bus);
			outMap.put("state", 1);
			outMap.put("brief", brief);
			//出库的主表用的to_date函数转换的，所以这里直接用String型
			outMap.put("maker", SessionManager.getUserId());
			outMap.put("out_date", DateUtil.stringToDate(entityMap.get("tran_date").toString(), "yyyy-MM-dd"));
			outMap.put("store_id", entityMap.get("out_store_id").toString());
			outMap.put("store_no", entityMap.get("out_store_no"));
			outMap.put("table_code", "MED_AFFI_OUT");
			outMap.put("prefixe", "");
			String out_no = medCommonService.getMedNextNo(outMap);
			if(out_no.indexOf("error")!=-1){
				return out_no;
			}
			outMap.put("out_no", out_no);
			long out_id = medAffiOutMapper.queryMedAffiOutMainSeq();
			outMap.put("out_id", out_id);
			
			if(is_in){
				//生成入库单
				inMap.put("group_id", entityMap.get("group_id").toString());
				inMap.put("hos_id", entityMap.get("hos_id").toString());
				inMap.put("copy_code", entityMap.get("copy_code").toString());
				inMap.put("year", entityMap.get("year").toString());
				inMap.put("month", entityMap.get("month").toString());
				inMap.put("day", entityMap.get("day"));  //用于生成单号
				inMap.put("bus_type_code", in_bus);
				inMap.put("state", 1);
				inMap.put("brief", brief);
				inMap.put("maker", SessionManager.getUserId());
				inMap.put("in_date", DateUtil.stringToDate(entityMap.get("tran_date").toString(), "yyyy-MM-dd"));
				
				inMap.put("bus_type_code", 31);//移入库
				inMap.put("store_id", entityMap.get("in_store_id").toString());
				inMap.put("store_no", entityMap.get("in_store_no").toString());
				inMap.put("table_code", "MED_AFFI_IN");
				inMap.put("prefixe", "");
				String in_no = medCommonService.getMedNextNo(inMap);
				if(in_no.indexOf("error")!=-1){
					return in_no;
				}
				inMap.put("in_no", in_no);
				inMap.put("in_id", medAffiInMapper.queryAffiInMainSeq());
			}
			/*--------------需要同时生成出入库单--end------------------------------*/
			
			// 保存明细数据
			List<Map<String, Object>> tran_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray tran_detail_json = JSONArray.parseArray((String) entityMap.get("tran_detail_data"));
			
			StringBuffer invEnoughMsg = new StringBuffer();//记录库存不足的药品
			Iterator tran_detail_it = tran_detail_json.iterator();

			while (tran_detail_it.hasNext()) {

				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				Map<String, Object> inDetailMap = new HashMap<String, Object>();
				Map<String, Object> outDetailMap = new HashMap<String, Object>();
				/*药品主信息-----------------begin---------------------*/
				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("tran_id", entityMap.get("tran_id").toString());
				mapDetailVo.put("tran_no", entityMap.get("tran_no").toString());
				mapDetailVo.put("store_id", entityMap.get("out_store_id").toString());//用于查询药品信息

				JSONObject jsonObj = JSONObject.parseObject(tran_detail_it.next().toString());
				//没有药品ID视为空行
				if(jsonObj.get("inv_id") != null && !"".equals(String.valueOf(jsonObj.get("inv_id")))){
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("amount")))) {
						mapDetailVo.put("amount", jsonObj.get("amount").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {
						mapDetailVo.put("price", jsonObj.get("price").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("sale_price")))) {
						mapDetailVo.put("sale_price", jsonObj.get("sale_price").toString());
					}else{
						mapDetailVo.put("sale_price", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("sell_price")))) {
						mapDetailVo.put("sell_price", jsonObj.get("sell_price").toString());
					}else{
						mapDetailVo.put("sell_price", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("allot_price")))) {
						mapDetailVo.put("allot_price", jsonObj.get("allot_price").toString());
					}else{
						mapDetailVo.put("allot_price", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}else{
						mapDetailVo.put("bar_code", null);
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date",  DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("location_out_id")))) {
						mapDetailVo.put("location_out_id", jsonObj.get("location_out_id"));
					}else{
						mapDetailVo.put("location_out_id", "0");
					}
					mapDetailVo.put("location_id", mapDetailVo.get("location_out_id"));  //用于先进先出算法
					
					if (validateJSON(String.valueOf(jsonObj.get("location_in_id")))) {
						mapDetailVo.put("location_in_id", jsonObj.get("location_in_id"));
					}else{
						mapDetailVo.put("location_in_id", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("note")))) {
						mapDetailVo.put("note", jsonObj.get("note"));
					}
					/*药品主信息-----------------end-----------------------*/
	
					/*药品批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && validateJSON(String.valueOf(jsonObj.get("amount"))) && validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没药品ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								detailMap.putAll(mapDetailVo);
								detailMap.put("tran_detail_id", medAffiTranDetailMapper.queryMedAffiTranDetailSeq());
								if (validateJSON(String.valueOf(m.get("batch_sn")))) {
									detailMap.put("batch_sn", m.get("batch_sn").toString());
								}
								if (validateJSON(String.valueOf(m.get("amount")))) {
									detailMap.put("amount", m.get("amount").toString());
								}
								if (validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
								}
								if (validateJSON(String.valueOf(m.get("sale_money")))) {detailMap.put("sale_money", m.get("sale_money").toString());}
								if (validateJSON(String.valueOf(m.get("sell_money")))) {detailMap.put("sell_money", m.get("sell_money").toString());}
								if (validateJSON(String.valueOf(m.get("allot_money")))) {detailMap.put("allot_money", m.get("allot_money").toString());}
								tran_detail_batch.add(detailMap);
								
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("detail_id", medAffiOutMapper.queryMedAffiOutDetailSeq());
								if (validateJSON(String.valueOf(detailMap.get("location_out_id")))) {
									outDetailMap.put("location_id", detailMap.get("location_out_id"));
								}else{
									outDetailMap.put("location_id", "0");
								}
								
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
									if(validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date",DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
									}
									if(validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date",DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
									}
									
									if (validateJSON(String.valueOf(detailMap.get("location_in_id")))) {
										inDetailMap.put("location_id", detailMap.get("location_in_id"));
									}else{
										inDetailMap.put("location_id", "0");
									}
									
									inDetailList.add(inDetailMap);
								}
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedAffiOutDetailInvList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							detailMap.putAll(mapDetailVo);
							detailMap.put("batch_sn", map.get("batch_sn").toString());
							detailMap.put("tran_detail_id", medAffiTranDetailMapper.queryMedAffiTranDetailSeq());
							//当前批次即时库存
							imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
							//判断当前批号批次是否充足
							if(amount <= imme_amount){
								detailMap.put("amount", amount.toString());
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								
								tran_detail_batch.add(detailMap);
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("detail_id", medAffiOutMapper.queryMedAffiOutDetailSeq());
								
								if (validateJSON(String.valueOf(detailMap.get("location_out_id")))) {
									outDetailMap.put("location_id", detailMap.get("location_out_id"));
								}else{
									outDetailMap.put("location_id", "0");
								}
								
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
									if(validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date",DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
									}
									if(validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date",DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
									}
									
									if (validateJSON(String.valueOf(detailMap.get("location_in_id")))) {
										inDetailMap.put("location_id", detailMap.get("location_in_id"));
									}else{
										inDetailMap.put("location_id", "0");
									}
									
									inDetailList.add(inDetailMap);
								}
								amount = 0.0;
								break;
							}else{
								//取当前批号批次数量并且申请单数量响应减少
								detailMap.put("amount", imme_amount.toString());		
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								
								tran_detail_batch.add(detailMap);		
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("detail_id", medAffiOutMapper.queryMedAffiOutDetailSeq());
								
								if (validateJSON(String.valueOf(detailMap.get("location_out_id")))) {
									outDetailMap.put("location_id", detailMap.get("location_out_id"));
								}else{
									outDetailMap.put("location_id", "0");
								}
								
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("detail_id",medAffiInMapper.queryAffiInDetailSeq());
									if(validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date",DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
									}
									if(validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date",DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
									}
									
									if (validateJSON(String.valueOf(detailMap.get("location_in_id")))) {
										inDetailMap.put("location_id", detailMap.get("location_in_id"));
									}else{
										inDetailMap.put("location_id", "0");
									}
									
									inDetailList.add(inDetailMap);
								}
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
			if(tran_detail_batch.size() <= 0){
				return "{\"error\":\"调拨单明细数据为空!\"}";
			}
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足!\"}";
			}

			//判断即时库存是否充足(防止多人同时操作同一药品，额外加的判断)
			String invEnough = medCommonMapper.existsMedStockInvIsEnough(tran_detail_batch);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下药品库存物资不足!"+invEnough+"\"}";
			}
			
			
			//新增调拨单明细表
			if(tran_detail_batch.size() > 0){
				//新增调拨单主表
				medAffiTranMainMapper.add(entityMap);
				medAffiTranDetailMapper.addBatch(tran_detail_batch);
				//新增出库单主表
				medAffiOutMapper.addMedAffiOutMain(outMap);
				//新增出库单明细表
				medAffiOutMapper.addMedAffiOutDetail(outDetailList);
				//新增入库单主表
				medAffiInMapper.addMedAffiInMain(inMap);
				//新增入库单明细表
				medAffiInMapper.addMedAffiInDetail(inDetailList);
			}
			
			//添加调拨单与出入库单对应关系
			if(outMap.containsKey("out_id")){
				entityMap.put("out_id", outMap.get("out_id"));
				entityMap.put("out_no", outMap.get("out_no"));
			}
			if(inMap.containsKey("in_id")){
				entityMap.put("in_id", inMap.get("in_id"));
				entityMap.put("in_no", inMap.get("in_no"));
			}
			
			medAffiTranRelaMapper.add(entityMap);
			
			return "{\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("tran_id").toString()+","
			+"\"}";
			//return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
		
	}
	
	/**
	 * 返回用用于保存的默认值 
	 */
	public Map<String, Object> defaultValue(Map<String, Object> mapVo) {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", mapVo.get("group_id"));}
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", mapVo.get("hos_id"));}
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", mapVo.get("copy_code"));}
		if (mapVo.get("tran_no") == null) {mapVo.put("tran_no", "");}
		if (mapVo.get("year") == null) {mapVo.put("year", "");}
		if (mapVo.get("month") == null) {mapVo.put("month", "");}
		if (mapVo.get("tran_method") == null) {mapVo.put("tran_method", "");}
		if (mapVo.get("tran_type") == null) {mapVo.put("tran_type", "");}
		if (mapVo.get("out_hos_id") == null) {mapVo.put("out_hos_id", "");}
		if (mapVo.get("out_store_id") == null) {mapVo.put("out_store_id", "");}
		if (mapVo.get("out_store_no") == null) {mapVo.put("out_store_no", "");}
		if (mapVo.get("in_hos_id") == null) {mapVo.put("in_hos_id", "");}
		if (mapVo.get("in_store_id") == null) {mapVo.put("in_store_id", "");}
		if (mapVo.get("in_store_no") == null) {mapVo.put("in_store_no", "");}	
		if (mapVo.get("brief") == null) {mapVo.put("brief", "");}		
		if (mapVo.get("tran_date") == null) {mapVo.put("tran_date", "");}
		if (mapVo.get("maker") == null) {mapVo.put("maker", "");}			
		if (mapVo.get("checker") == null) {mapVo.put("checker", "");}		
		if (mapVo.get("check_date") == null) {mapVo.put("check_date", "");}		
		if (mapVo.get("confirmer") == null) {mapVo.put("confirmer", "");}		
		if (mapVo.get("confirm_date") == null) {mapVo.put("confirm_date", "");}
		if (mapVo.get("state") == null) {mapVo.put("state", "");}
		return mapVo;
	}
	
	// 返回用用于保存的默认值
		public Map<String, Object> defaultDetailValue() {
			Map<String, Object> mapDetailVo = new HashMap<String, Object>();
			mapDetailVo.put("group_id", 0);
			mapDetailVo.put("hos_id", 0);
			mapDetailVo.put("copy_code", "");
			mapDetailVo.put("tran_id", 0);
			mapDetailVo.put("inv_id", 0);
			mapDetailVo.put("inv_no", 0);
			mapDetailVo.put("batch_sn", 0);
			mapDetailVo.put("batch_no", "");
			mapDetailVo.put("amount", "");
			mapDetailVo.put("price", 0);
			mapDetailVo.put("amount_money", "");
			mapDetailVo.put("sell_price", "");
			mapDetailVo.put("sell_money", "");
			mapDetailVo.put("allot_price", "");
			mapDetailVo.put("allot_money", "");
			mapDetailVo.put("pack_code", "");
			mapDetailVo.put("num_exchange", "");
			mapDetailVo.put("pack_price", "");
			mapDetailVo.put("num", "");
			mapDetailVo.put("bar_code", "");
			mapDetailVo.put("is_per_bar", "");
			mapDetailVo.put("sn", "");
			mapDetailVo.put("inva_date", "");
			mapDetailVo.put("disinfect_date", "");
			mapDetailVo.put("location_out_id", "");
			mapDetailVo.put("location_in_id", "");
			mapDetailVo.put("note", "");
			return mapDetailVo;
		}
		
		public boolean validateJSON(String str) {

			if (str != null && !"null".equals(str)  && !"".equals(str)  && !"0".equals(str)) {

				return true;

			}

			return false;

		}
	
	/**
	 * @Description 
	 * 批量添加调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{		
		try {			
			medAffiTranMainMapper.addBatch(entityList);			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}		
	}
	
		/**
	 * @Description 
	 * 更新调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{		
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("08005"));		
			entityMap = defaultValue(entityMap);
			String tran_date = (String) entityMap.get("tran_date");
			
			entityMap.put("year", tran_date.substring(0, 4));
			entityMap.put("month", tran_date.substring(5, 7));	
			
			entityMap.put("tran_date", DateUtil.stringToDate(entityMap.get("tran_date").toString(), "yyyy-MM-dd"));
			int flag = 1;		
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = medCommonMapper.existsAccYearMonthCheck(entityMap);		
			if(flag == 0){				
				return "{\"error\":\"修改失败，所选期间不存在请配置！\",\"state\":\"false\"}";				
			}			
			//(2)、判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能保存！			
			flag = medCommonMapper.existsMedYearMonthIsAccount(entityMap);
			if(flag != 0){				
				return "{\"error\":\"修改失败，当月已结账不能保存！\",\"state\":\"false\"}";				
			}
			
			/*--------------需要同时修改对应的出入库单--begin----------------------------*/
			//注：院内调拨需要同时生成出入库单；院外调拨只生成出库单，入库单在调入单位调入确认时生成。
			boolean is_in = false;
			//获取调拨单与出入库单对应关系
			MedAffiTranRela medTranRela = medAffiTranRelaMapper.queryByCode(entityMap);
			if(medTranRela.getIn_id() != null && medTranRela.getIn_id() != 0){
				is_in = true;
			}
			//存放出库主从表
			Map<String, Object> outMap = new HashMap<String, Object>();
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			//存放入库主从表
			Map<String, Object> inMap = new HashMap<String, Object>();
			List<Map<String, Object>> inDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			//修改出库单
			outMap.put("group_id", entityMap.get("group_id").toString());
			outMap.put("hos_id", entityMap.get("hos_id").toString());
			outMap.put("copy_code", entityMap.get("copy_code").toString());
			outMap.put("out_id", medTranRela.getOut_id());
			//出库的主表用的to_date函数转换的，所以这里直接用String型
			outMap.put("out_date", entityMap.get("tran_date"));
			outMap.put("store_id", entityMap.get("out_store_id").toString());
			outMap.put("store_no", entityMap.get("out_store_no").toString());
			
			if(is_in){
				//修改入库单
				inMap.put("group_id", entityMap.get("group_id").toString());
				inMap.put("hos_id", entityMap.get("hos_id").toString());
				inMap.put("copy_code", entityMap.get("copy_code").toString());
				inMap.put("in_id", medTranRela.getIn_id());
				inMap.put("in_date", tran_date);

				inMap.put("store_id", entityMap.get("in_store_id").toString());
				inMap.put("store_no", entityMap.get("in_store_no").toString());
			}
			/*--------------需要同时修改对应的出入库单--end------------------------------*/

			// 保存明细数据
			List<Map<String, Object>> tran_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray tran_detail_json = JSONArray.parseArray((String) entityMap.get("tran_detail_data"));
			//记录明细总金额
			float money_sum = 0;
			//存放库存不足的药品信息
			StringBuffer invEnoughMsg = new StringBuffer();
			
			Iterator tran_detail_it = tran_detail_json.iterator();

			while (tran_detail_it.hasNext()) {

				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				Map<String, Object> inDetailMap = new HashMap<String, Object>();
				Map<String, Object> outDetailMap = new HashMap<String, Object>();

				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("tran_id", entityMap.get("tran_id").toString());
				mapDetailVo.put("tran_no", entityMap.get("tran_no").toString());
				mapDetailVo.put("store_id", entityMap.get("out_store_id").toString());//用于先进先出匹配药品

				JSONObject jsonObj = JSONObject.parseObject(tran_detail_it.next().toString());
				//没有药品ID视为空行
				if(jsonObj.get("inv_id") != null && !"".equals(String.valueOf(jsonObj.get("inv_id")))){
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
	
					if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("amount")))) {
						mapDetailVo.put("amount", jsonObj.get("amount").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {
						mapDetailVo.put("price", jsonObj.get("price").toString());
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("sale_price")))) {
						mapDetailVo.put("sale_price", jsonObj.get("sale_price").toString());
					}else{
						mapDetailVo.put("sale_price", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("sell_price")))) {
						mapDetailVo.put("sell_price", jsonObj.get("sell_price").toString());
					}else{
						mapDetailVo.put("sell_price", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("allot_price")))) {
						mapDetailVo.put("allot_price", jsonObj.get("allot_price").toString());
					}else{
						mapDetailVo.put("allot_price", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}else{
						mapDetailVo.put("bar_code", null);
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("location_out_id")))) {
						mapDetailVo.put("location_out_id", jsonObj.get("location_out_id"));
					}else{
						mapDetailVo.put("location_out_id", "0");
					}
					mapDetailVo.put("location_id", mapDetailVo.get("location_out_id"));  //用于先进先出算法
					
					if (validateJSON(String.valueOf(jsonObj.get("location_in_id")))) {
						mapDetailVo.put("location_in_id", jsonObj.get("location_in_id"));
					}else{
						mapDetailVo.put("location_in_id", "0");
					}
					if (validateJSON(String.valueOf(jsonObj.get("note")))) {mapDetailVo.put("note", jsonObj.get("note"));}
					
					//System.out.println("************:"+jsonObj.get("inv_detail_data").toString());
					/*药品批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && validateJSON(String.valueOf(jsonObj.get("amount"))) && validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						//System.out.println("****** size:"+JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString()).size());
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没药品ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								detailMap.putAll(mapDetailVo);
								if (validateJSON(String.valueOf(m.get("tran_detail_id")))) {
									detailMap.put("tran_detail_id", m.get("tran_detail_id").toString());
								}else{
									detailMap.put("tran_detail_id", medAffiTranDetailMapper.queryMedAffiTranDetailSeq());
								}
								
								if (validateJSON(String.valueOf(m.get("batch_sn")))) {
									detailMap.put("batch_sn", m.get("batch_sn").toString());
								}
								if (validateJSON(String.valueOf(m.get("amount")))) {
									detailMap.put("amount", m.get("amount").toString());
								}
								if (validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
								}
								if (validateJSON(String.valueOf(m.get("sale_money")))) {
									detailMap.put("sale_money", m.get("sale_money").toString());
								}
								if (validateJSON(String.valueOf(m.get("sell_money")))) {
									detailMap.put("sell_money", m.get("sell_money").toString());
								}
								if (validateJSON(String.valueOf(m.get("allot_money")))) {
									detailMap.put("allot_money", m.get("allot_money").toString());
								}
								detailMap.put("out_id", outMap.get("out_id"));
								//tran_detail_batch.add(detailMap);
								//处理出入库明细
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id"));
								outDetailMap.put("out_no", medTranRela.getOut_no().toString());
								outDetailMap.put("detail_id", medAffiOutMapper.queryMedAffiOutDetailSeq());
								
								if (validateJSON(String.valueOf(detailMap.get("location_out_id")))) {
									outDetailMap.put("location_id", detailMap.get("location_out_id"));
								}else{
									outDetailMap.put("location_id", "0");
								}
								
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									detailMap.put("in_id", inMap.get("in_id"));
									inDetailMap.put("in_id", inMap.get("in_id"));
									inDetailMap.put("in_no", medTranRela.getIn_no().toString());
									inDetailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
									
								
									if(validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date",detailMap.get("inva_date"));
									}
									if(validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date",detailMap.get("disinfect_date"));
									}
									
									if (validateJSON(String.valueOf(detailMap.get("location_in_id")))) {
										inDetailMap.put("location_id", detailMap.get("location_in_id"));
									}else{
										inDetailMap.put("location_id", "0");
									}
									
									inDetailList.add(inDetailMap);
								}
								tran_detail_batch.add(detailMap);
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) medCommonMapper.queryMedAffiOutDetailInvList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							detailMap.putAll(mapDetailVo);
							detailMap.put("tran_detail_id", medAffiTranDetailMapper.queryMedAffiTranDetailSeq());
							detailMap.put("batch_sn", map.get("batch_sn").toString());
							//当前批次即时库存
							imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
							//判断当前批号批次是否充足
							if(amount <= imme_amount){
								detailMap.put("amount", Double.toString(amount));
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
							
								detailMap.put("out_id", outMap.get("out_id"));
								//tran_detail_batch.add(detailMap);
								//处理出入库明细
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id"));
								outDetailMap.put("out_no", medTranRela.getOut_no().toString());
								outDetailMap.put("detail_id", medAffiOutMapper.queryMedAffiOutDetailSeq());
								
								if (validateJSON(String.valueOf(detailMap.get("location_out_id")))) {
									outDetailMap.put("location_id", detailMap.get("location_out_id"));
								}else{
									outDetailMap.put("location_id", "0");
								}
								
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									detailMap.put("in_id", inMap.get("in_id"));
									inDetailMap.put("in_id", inMap.get("in_id"));
									inDetailMap.put("in_no", medTranRela.getIn_no().toString());
									inDetailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
									//System.out.println("***********2:"+detailMap.get("inva_date").toString());
									if(validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date",detailMap.get("inva_date"));
									}
									if(validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date",detailMap.get("disinfect_date"));
									}
									
									if (validateJSON(String.valueOf(detailMap.get("location_in_id")))) {
										inDetailMap.put("location_id", detailMap.get("location_in_id"));
									}else{
										inDetailMap.put("location_id", "0");
									}
									
									inDetailList.add(inDetailMap);
								}
								tran_detail_batch.add(detailMap);
								
								amount = 0.0;
								break;
							}else{
								//取当前批号批次数量并且申请单数量响应减少
								detailMap.put("amount", Double.toString(imme_amount));		
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								
								//tran_detail_batch.add(detailMap);		
								//处理出入库明细
								outDetailMap.putAll(detailMap);
								detailMap.put("out_id", outMap.get("out_id"));
								outDetailMap.put("out_id", outMap.get("out_id"));
								outDetailMap.put("out_no", medTranRela.getOut_no().toString());
								outDetailMap.put("detail_id", medAffiOutMapper.queryMedAffiOutDetailSeq());
								
								if (validateJSON(String.valueOf(detailMap.get("location_out_id")))) {
									outDetailMap.put("location_id", detailMap.get("location_out_id"));
								}else{
									outDetailMap.put("location_id", "0");
								}
								
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									detailMap.put("in_id", inMap.get("in_id").toString());
									
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", medTranRela.getIn_no().toString());
									inDetailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
									if(validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date",detailMap.get("inva_date"));
									}
									if(validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date",detailMap.get("disinfect_date"));
									}
									
									if (validateJSON(String.valueOf(detailMap.get("location_in_id")))) {
										inDetailMap.put("location_id", detailMap.get("location_in_id"));
									}else{
										inDetailMap.put("location_id", "0");
									}
									
									inDetailList.add(inDetailMap);
								}	
								amount = NumberUtil.sub(amount, imme_amount);
								tran_detail_batch.add(detailMap);	
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
			if(tran_detail_batch.size() <= 0){
				return "{\"error\":\"调拨单明细数据为空!\"}";
			}
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足!\"}";
			}

			//判断即时库存是否充足(防止多人同时操作同一药品，额外加的判断)
			String invEnough = medCommonMapper.existsMedAffiInvIsEnough(tran_detail_batch);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下药品库存物资不足!"+invEnough+"\"}";
			}
			if(tran_detail_batch.size() > 0){
				//修改调拨单主表
				medAffiTranMainMapper.update(entityMap);
				//修改调拨单明细表先清空再添加
				medAffiTranDetailMapper.delete(entityMap);
				medAffiTranDetailMapper.addBatch(tran_detail_batch);
			}
			
			if(outDetailList.size() > 0){
				//修改出库单主表
				medAffiOutMapper.updateMedAffiOutMain(outMap);
				//修改出库单明细表先清空再添加
				medAffiOutMapper.deleteMedAffiOutDetail(outMap);
				medAffiOutMapper.addMedAffiOutDetail(outDetailList);
			}
			
			if(inDetailList.size()>0){
				//修改入库单主表
				medAffiInMapper.updateMedAffiInMain(inMap);
				//修改入库单明细表先清空再添加
				medAffiInMapper.deleteMedAffiInDetail(inMap);
				medAffiInMapper.addMedAffiInDetail(inDetailList);
			}
			

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}	
	
	}
	/**
	 * @Description 
	 * 批量更新调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{		
		try {			
			medAffiTranMainMapper.updateBatch(entityList);			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}			
	}
	/**
	 * @Description 
	 * 删除调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {    	
		try {			
			int state = medAffiTranMainMapper.delete(entityMap);			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}			
  }
    
	/**
	 * @Description 
	 * 批量删除调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{		
		try {
			//删除申请单与出库单对应关系
			medApplyOutRelaMapper.deleteMedApplyOutRelaBatch(entityList);
			//获取调拨单与出入库单对应关系
			List<Map<String, Object>> relaList = toListMapLower(medAffiTranRelaMapper.queryListForDelete(entityList));
			if(relaList.size() > 0){
				//当关系中没有匹配出入库单是 不执行删除语句 如果没有匹配说明关系表没有写对 需要考虑业务
				List<Map<String, Object>> delete_in= new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> delete_out= new ArrayList<Map<String,Object>>();
				for (Map<String, Object> map : relaList) {
					if(map.get("in_id")!=null){
						delete_in.add(map);
					}
					if(map.get("out_id")!=null){
						delete_out.add(map);
					}
					
                }
				if(delete_out.size()>0){
					//删除已经生成的出库单
					medAffiOutMapper.deleteBatchMedAffiOutDetail(delete_out);//明细表
					medAffiOutMapper.deleteBatchMedAffiOutMain(delete_out);//主表
				}
				if(delete_in.size()>0){
					//删除已经生成的入库单
					medAffiInMapper.deleteBatchMedAffiInDetail(delete_in);//明细表
					medAffiInMapper.deleteBatchMedAffiInMain(delete_in);//主表
				}
				
				//删除对应关系表
				medAffiTranRelaMapper.deleteBatch(entityList);
			}
			if(entityList.size() > 0){
				//删除明细
				medAffiTranDetailMapper.deleteBatch(entityList);
				//删除主表
				medAffiTranMainMapper.deleteBatch(entityList);
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}	
	
	}
	
	/**
	 * @Description 
	 * 添加调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象调拨方式TRAN_METHOD：1同价调拨  2 异价调拨 调拨类型：1 院内调拨  2 院外调拨
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<MedAffiTranMain> list = (List<MedAffiTranMain>)medAffiTranMainMapper.queryExists(mapVo);		
		if (list.size()>0) {
			int state = medAffiTranMainMapper.update(entityMap);		
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		
		try {			
			int state = medAffiTranMainMapper.add(entityMap);		
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<MedAffiTranMain> list = (List<MedAffiTranMain>)medAffiTranMainMapper.query(entityMap);			
			return ChdJson.toJson(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<MedAffiTranMain> list = (List<MedAffiTranMain>)medAffiTranMainMapper.query(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medAffiTranMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedAffiTranMain
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medAffiTranMainMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<MedAffiTranMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return medAffiTranMainMapper.queryExists(entityMap);
	}
	/**
	 * 代销调拨--主页面查询
	 */
	@Override
	public String queryMedAffiTranMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<Map<String, Object>> list = (List<Map<String, Object>>)medAffiTranMainMapper.queryMedAffiTranMain(entityMap);			
			return ChdJson.toJsonLower(list);
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<Map<String, Object>> list = (List<Map<String, Object>>)medAffiTranMainMapper.queryMedAffiTranMain(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());			
		}
	}
	/**
	 * 代销调拨--整单调拨页面主查询
	 */
	@Override
	public String queryMedInMainBySingle(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<Map<String, Object>> list = (List<Map<String, Object>>)medAffiTranMainMapper.queryMedInMainBySingle(entityMap);			
			return ChdJson.toJsonLower(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<Map<String, Object>> list = (List<Map<String, Object>>)medAffiTranMainMapper.queryMedInMainBySingle(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());			
		}
	}
	/**
	 * 代销调拨--整单调拨页面明细查询
	 */
	@Override
	public String queryMedInDetailBySingle(Map<String, Object> entityMap) throws DataAccessException {		
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<Map<String, Object>> list = (List<Map<String, Object>>)medAffiTranMainMapper.queryMedInDetailBySingle(entityMap);			
			return ChdJson.toJsonLower(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<Map<String, Object>> list = (List<Map<String, Object>>)medAffiTranMainMapper.queryMedInDetailBySingle(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());			
		}
	}
	/**
	 * 代销调拨--组装明细数据
	 */
	@Override
	public String queryMedAffiTranDetailBySingle(Map<String, Object> entityMap) throws DataAccessException {
		try {			
			return ChdJson.toJsonLower(medAffiTranDetailMapper.queryMedAffiTranDetailBySingle(entityMap));
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);		
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"配套导入失败 数据库异常 请联系管理员! 方法 queryMedOutDetailByIsDir\"}";
		}
	}
	
	/**
	 * 获取需要生成的出库单号
	 * 
	 * @param entityMap
	 *            map参数必须包含(group_id, hos_id, copy_code, store_id, year, month,
	 *            bus_type_code)这六个键值
	 * @return
	 */
	public String getNextIn_no(Map<String, Object> entityMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("store_id", entityMap.get("store_id"));
		// 获取仓库别名store_alias
		//String store_alias = medCommonMapper.queryStoreAliasById(map);
		map.put("table_code", "MED_AFFI_TRAN_MAIN");
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		map.put("prefixe", "DB");
		map.put("store_alias", "");
		map.put("bus_type", "");
		// 判断是否存在该业务流水码
		int flag = medNoManageMapper.queryIsExists(map);
		String max_no = "";
		if (flag == 0) {// 如不存在则流水码为1，并插入流水码表中
			max_no = "1";
			map.put("max_no", 1);
			medNoManageMapper.add(map);
		} else {
			// 更新该业务流水码+1
			medNoManageMapper.updateMaxNo(map);
			// 取出该业务更新后的流水码
			max_no = medNoManageMapper.queryMaxCode(map);
		}
		// 补流水码前缀0
		for (int i = max_no.length(); i < 5; i++) {
			max_no = "0" + max_no;
		}
		// 组装流水码
		String in_no = map.get("prefixe") + "-" + entityMap.get("year").toString() + entityMap.get("month").toString() + max_no;
		return in_no;
	}

	/**
	 * @Description 调出确认<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String outConfirmMedAffiTranMain(List<Map<String, Object>> entityList) throws DataAccessException {
		List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();// 存放med_out_main字段
		List<Map<String, Object>> inList = new ArrayList<Map<String, Object>>();// 入库List
		List<Map<String, Object>> outList = new ArrayList<Map<String, Object>>();// 出库List
		
		Map<String, Object> inMap = new HashMap<String,Object>();
		Map<String, Object> outMap = new HashMap<String,Object>();
		try {
			for (Map<String, Object> tmp : entityList) {
				tmp.put("state", "2");				
				tmp.put("checker", SessionManager.getUserId());				
				tmp.put("check_date", DateUtil.stringToDate(tmp.get("check_date").toString(), "yyyy-MM-dd"));	
				mainList.add(tmp);
				
				MedAffiTranRela mtr = medAffiTranRelaMapper.queryByCode(tmp);
				if(validateJSON(String.valueOf(mtr.getIn_id()))){
					inMap.put("hos_id", mtr.getHos_id());
					inMap.put("group_id", mtr.getGroup_id());
					inMap.put("copy_code", mtr.getCopy_code());
					inMap.put("in_id", mtr.getIn_id());
					inMap.put("in_no", mtr.getIn_no());
					inMap.put("state", tmp.get("state"));
					inMap.put("checker", tmp.get("checker"));
					inMap.put("check_date", tmp.get("check_date"));
					inList.add(inMap);
				}
				
				if(validateJSON(String.valueOf(mtr.getOut_id()))){
					outMap.put("hos_id", mtr.getHos_id());
					outMap.put("group_id", mtr.getGroup_id());
					outMap.put("copy_code", mtr.getCopy_code());
					outMap.put("out_id", mtr.getOut_id());
					outMap.put("out_no", mtr.getOut_no());
					outMap.put("state", tmp.get("state"));
					outMap.put("checker", tmp.get("checker"));
					outMap.put("check_date", tmp.get("check_date"));
					outList.add(outMap);
				}
			}
			if(inList.size() > 0){
				medAffiInMapper.updateBatchMedAffiInMain(inList);
			}
			if(outList.size() > 0){
				medAffiOutMapper.updateBatchMedAffiOutMain(outList);
			}
			if(mainList.size() > 0){
				medAffiTranMainMapper.updateBatchState(mainList);
			}
			
			return "{\"msg\":\"调出确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"调出确认失败 数据库异常 请联系管理员! 方法 outConfirmMedAffiTranMain\"}";
		}
	}

	/**
	 * @Description 取消调出确认<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String unOutConfirmMedAffiTranMain(List<Map<String, Object>> entityList) throws DataAccessException {
		List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();// 存放med_out_main字段
		List<Map<String, Object>> inList = new ArrayList<Map<String, Object>>();// 入库List
		List<Map<String, Object>> outList = new ArrayList<Map<String, Object>>();// 出库List
		
		Map<String, Object> inMap = new HashMap<String,Object>();
		Map<String, Object> outMap = new HashMap<String,Object>();
		
		try {
			for (Map<String, Object> tmp : entityList) {
				tmp.put("state", "1");				
				tmp.put("checker", "");			
				tmp.put("check_date", "");
				mainList.add(tmp);
				
				MedAffiTranRela mtr = medAffiTranRelaMapper.queryByCode(tmp);
				if(validateJSON(String.valueOf(mtr.getIn_id()))){
					inMap.put("hos_id", mtr.getHos_id());
					inMap.put("group_id", mtr.getGroup_id());
					inMap.put("copy_code", mtr.getCopy_code());
					inMap.put("in_id", mtr.getIn_id());
					inMap.put("in_no", mtr.getIn_no());
					inMap.put("state", "1");
					inMap.put("checker", "");
					inMap.put("check_date", "");
					inList.add(inMap);
				}
				
				if(validateJSON(String.valueOf(mtr.getOut_id()))){
					outMap.put("hos_id", mtr.getHos_id());
					outMap.put("group_id", mtr.getGroup_id());
					outMap.put("copy_code", mtr.getCopy_code());
					outMap.put("out_id", mtr.getOut_id());
					outMap.put("out_no", mtr.getOut_no());
					outMap.put("state", "1");
					outMap.put("checker", "");
					outMap.put("check_date", "");
					outList.add(outMap);
				}	
			}
			if(inList.size() > 0){
				medAffiInCommonMapper.unAuditMedAffiInCommon(inList);
			}
			if(outList.size() > 0){
				medAffiOutCommonMapper.unAuditMedAffiOutCommon(outList);
			}
			if(mainList.size() > 0){
				medAffiTranMainMapper.updateBatchState(mainList);//更新调拨主表
			}
			
			return "{\"msg\":\"取消调出确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"取消调出确认失败 数据库异常 请联系管理员! 方法 unOutConfirmMedAffiTranMain\"}";
		}
	}

	/**
	 * @Description 调入确认<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String inConfirmMedAffiTranMain(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			
			String date = DateUtil.dateToString(new Date(), "yyyy/MM/dd");
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			
			//判断当前期间是否已结账，如结账，则不能添加入库单
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("group_id", SessionManager.getGroupId());
			checkMap.put("hos_id", SessionManager.getHosId());
			checkMap.put("copy_code", SessionManager.getCopyCode());
			checkMap.put("year", year);
			checkMap.put("month", month);
			int flag = medCommonMapper.existsMedYearMonthIsAccount(checkMap);
			if(flag != 0){
				return "{\"error\":\"当月已结账不能确认！\",\"state\":\"false\"}";
			}
			
			//存放需要确认的单据
			List<Map<String, Object>> listUpdateMainTran = new ArrayList<Map<String, Object>>();  //调拨单
			List<Map<String, Object>> listUpdateMainIn = new ArrayList<Map<String, Object>>();  //入库单
			List<Map<String, Object>> listUpdateMainOut = new ArrayList<Map<String, Object>>();  //出库单
			
			List<Map<String, Object>> listAddFifoBalance = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			List<Map<String, Object>> listUpdateFifoBalance = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			
			List<Map<String, Object>> listAddBatchBalance = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			List<Map<String, Object>> listUpdateBatchBalance = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			
			List<Map<String, Object>> listAddInvHold = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			List<Map<String, Object>> listUpdateInvHold = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			
			List<Map<String, Object>> listAddInvBalance = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			List<Map<String, Object>> listUpdateInvBalance = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			
			/*院外调拨需要考虑是否重新生成批号有效期、灭菌期信息
			List<Map<String, Object>> listAddBatchValidity = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			List<Map<String, Object>> listAddBatchDisinfect = new ArrayList<Map<String, Object>>();  //保存修改账表数据
			*/
			for(Map<String, Object> tmp : entityList){
				
				Map<String,Object> mapMainTran = new HashMap<String,Object> ();
				Map<String,Object> mapMainOut = new HashMap<String,Object> ();
				Map<String,Object> mapMainIn = new HashMap<String,Object> ();
				
				mapMainTran.put("group_id", tmp.get("group_id").toString());
				mapMainTran.put("hos_id", tmp.get("hos_id").toString());
				mapMainTran.put("copy_code", tmp.get("copy_code").toString());
				mapMainTran.put("tran_id", tmp.get("tran_id").toString());
				mapMainTran.put("confirmer", tmp.get("confirmer"));
				mapMainTran.put("state", tmp.get("state"));
				mapMainTran.put("confirm_date", DateUtil.stringToDate(tmp.get("confirm_date").toString(), "yyyy-MM-dd"));
				mapMainTran.put("year", year);
				mapMainTran.put("month", month);
				listUpdateMainTran.add(mapMainTran);
				
				//查询对应的出入库单据ID
				MedAffiTranRela mtr = medAffiTranRelaMapper.queryByCode(mapMainTran);
				mapMainOut.putAll(mapMainTran);
				mapMainOut.put("out_id", mtr.getOut_id());
				listUpdateMainOut.add(mapMainOut);
				mapMainIn.putAll(mapMainTran);
				mapMainIn.put("in_id", mtr.getIn_id());
				listUpdateMainIn.add(mapMainIn);
			}
			
			StringBuffer invMsg = new StringBuffer();//存放库存不足的药品信息
			boolean is_enough = true;  //库存是否充足
			
			//获取所选单据中的所有出库药品
			List<MedAffiTranDetail> outDetailList = medAffiTranDetailMapper.queryMedTranDetailForOutConfirm(entityList);
			//获取所选单据中的所有入库药品
			List<MedAffiTranDetail> inDetailList = medAffiTranDetailMapper.queryMedTranDetailForInConfirm(entityList);
			
			//查询帐表所需
			List<Map<String, Object>> invList = new ArrayList<Map<String,Object>>();
			
			//存放明细数据用于判断出库
			Map<String,Map<String,Object>> mapOutAffiFifo = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapOutBatchBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapOutInvHold = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapOutInvBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			//存放明细数据用于判断入库
			Map<String,Map<String,Object>> mapInAffiFifo = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInBatchBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInInvHold = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInInvBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			
			//累计同仓库同药品的数量和金额
			Map<String,Double> sum_amount_map = new HashMap<String,Double>();
			Map<String,Double> sum_amount_money = new HashMap<String,Double>();		
			Map<String,Double> sum_sale_money = new HashMap<String,Double>();				

			/**出库数据-----------------------------------------begin---------------------------*/
			for(MedAffiTranDetail mtd : outDetailList){
				/**如果确认日期按编制日期走则放开年月的重新赋值并改detailList相应的xml*/
				//year = mtd.getYear();
				//month = mtd.getMonth();
				//药品编码和名称用于库存不足的提示
				Map<String,Object> mapOutDetail = new HashMap<String,Object>();
				Map<String, Object> invOutMap = new HashMap<String, Object>();
				
				mapOutDetail.put("group_id", mtd.getGroup_id().toString());
				mapOutDetail.put("hos_id", mtd.getHos_id().toString());
				mapOutDetail.put("copy_code", mtd.getCopy_code().toString());
				mapOutDetail.put("store_id", mtd.getOut_store_id());
				mapOutDetail.put("inv_id", mtd.getInv_id().toString());
				mapOutDetail.put("inv_code", mtd.getInv_code().toString());
				mapOutDetail.put("inv_name", mtd.getInv_name().toString());
				mapOutDetail.put("batch_no", mtd.getBatch_no().toString());
				mapOutDetail.put("batch_sn", mtd.getBatch_sn().toString());
				mapOutDetail.put("bar_code", mtd.getBar_code().toString());
				mapOutDetail.put("price", mtd.getPrice() == null ? 0 : mtd.getPrice().toString());
				mapOutDetail.put("amount", mtd.getAmount() == null ? 0 : mtd.getAmount().toString());
				mapOutDetail.put("amount_money", mtd.getAmount_money() == null ? 0 : mtd.getAmount_money().toString());
				mapOutDetail.put("sale_price", mtd.getSale_price() == null ? 0 : mtd.getSale_price().toString());
				mapOutDetail.put("sale_money", mtd.getSale_money() == null ? 0 : mtd.getSale_money().toString());
				mapOutDetail.put("location_id", mtd.getLocation_out_id());
				
				/*用于查询出库信息-------begin------*/
				invOutMap.put("group_id", mtd.getGroup_id().toString());
				invOutMap.put("hos_id", mtd.getHos_id().toString());
				invOutMap.put("copy_code", mtd.getCopy_code().toString());
				invOutMap.put("year", year);
				invOutMap.put("month", month);
				invOutMap.put("store_id", mtd.getOut_store_id());
				invOutMap.put("inv_id", mtd.getInv_id().toString());
				invOutMap.put("price", mtd.getPrice().toString());
				invOutMap.put("batch_no", mtd.getBatch_no().toString());
				invOutMap.put("batch_sn", mtd.getBatch_sn().toString());
				invOutMap.put("bar_code", mtd.getBar_code().toString());
				String location_out_id = (String) (mtd.getLocation_out_id()== null ? 0 : mtd.getLocation_out_id().toString());
				invOutMap.put("location_id", Long.parseLong(location_out_id.toString()));
				//invOutMap.put("location_id", mtd.getLocation_out_id() == null ? 0 : mtd.getLocation_out_id());
				
				invList.add(invOutMap);
				/*用于查询出库信息-------end--------*/	
				
				//Map中存放<各帐表主键, 具体药品信息的map>用于判断
				mapOutAffiFifo.put(mtd.getOut_store_id().toString()+mtd.getInv_id()+mtd.getPrice()+mtd.getBatch_no()+mtd.getBatch_sn()+mtd.getBar_code()+mtd.getLocation_out_id(), mapOutDetail);
				mapOutInvHold.put(mtd.getOut_store_id().toString()+mtd.getInv_id()+mtd.getLocation_out_id(), mapOutDetail);
				//下面这两张表是区分年月的
				mapOutDetail.put("year", year);
				mapOutDetail.put("month", month);
				mapOutBatchBalance.put(year+month+mtd.getOut_store_id()+mtd.getInv_id()+mtd.getPrice()+mtd.getBatch_no()+mtd.getBatch_sn()+mtd.getBar_code()+mtd.getLocation_out_id(), mapOutDetail);
				mapOutInvBalance.put(year+month+mtd.getOut_store_id()+mtd.getInv_id()+mtd.getLocation_out_id(), mapOutDetail);
				
				//根据药品ID累计数量
				if(sum_amount_map.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()) != null){
					sum_amount_map.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), NumberUtil.add(sum_amount_map.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()), mtd.getAmount()));
				}else{
					sum_amount_map.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(),mtd.getAmount());
				}
				//根据药品ID累计金额
				if(sum_amount_money.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()) != null){
					sum_amount_money.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), NumberUtil.add(sum_amount_money.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()), mtd.getAmount_money()));
				}else{
					sum_amount_money.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), mtd.getAmount_money());
				}
				//根据药品ID累计金额
				if(sum_sale_money.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()) != null){
					sum_sale_money.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), NumberUtil.add(sum_sale_money.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()), mtd.getSale_money()));
				}else{
					sum_sale_money.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), mtd.getSale_money());
				}
			}	
			/**出库数据-----------------------------------------end-----------------------------*/	

			/**入库数据-----------------------------------------begin---------------------------*/
			for(MedAffiTranDetail mtd : inDetailList){
				/**如果确认日期按编制日期走则放开年月的重新赋值并改detailList相应的xml*/
				//year = mtd.getYear();
				//month = mtd.getMonth();
				//药品编码和名称用于库存不足的提示
				Map<String,Object> mapInDetail = new HashMap<String,Object>();
				Map<String, Object> invInMap = new HashMap<String, Object>();
				
				mapInDetail.put("group_id", mtd.getGroup_id().toString());
				mapInDetail.put("hos_id", mtd.getHos_id().toString());
				mapInDetail.put("copy_code", mtd.getCopy_code().toString());
				mapInDetail.put("store_id", mtd.getIn_store_id().toString());
				mapInDetail.put("inv_id", mtd.getInv_id().toString());
				mapInDetail.put("inv_code", mtd.getInv_code().toString());
				mapInDetail.put("inv_name", mtd.getInv_name().toString());
				mapInDetail.put("batch_no", mtd.getBatch_no().toString());
				mapInDetail.put("batch_sn", mtd.getBatch_sn().toString());
				mapInDetail.put("bar_code", mtd.getBar_code().toString());
				mapInDetail.put("price", mtd.getPrice() == null ? 0 : mtd.getPrice().toString());
				mapInDetail.put("amount", mtd.getAmount() == null ? 0 : mtd.getAmount().toString());
				mapInDetail.put("amount_money", mtd.getAmount_money() == null ? 0 : mtd.getAmount_money().toString());
				mapInDetail.put("sale_price", mtd.getSale_price() == null ? 0 : mtd.getSale_price().toString());
				mapInDetail.put("sale_money", mtd.getSale_money() == null ? 0 : mtd.getSale_money().toString());
				mapInDetail.put("location_id", mtd.getLocation_in_id());

				/*用于查询入库信息-------begin------*/
				invInMap.put("group_id", mtd.getGroup_id().toString());
				invInMap.put("hos_id", mtd.getHos_id().toString());
				invInMap.put("copy_code", mtd.getCopy_code().toString());
				invInMap.put("year", year);
				invInMap.put("month", month);
				invInMap.put("store_id", mtd.getIn_store_id());
				invInMap.put("inv_id", mtd.getInv_id().toString());
				invInMap.put("price", mtd.getPrice().toString());
				invInMap.put("batch_no", mtd.getBatch_no().toString());
				invInMap.put("batch_sn", mtd.getBatch_sn().toString());
				invInMap.put("bar_code", mtd.getBar_code().toString());
				String getLocation_in_id = (String) (mtd.getLocation_in_id()== null ? 0 : mtd.getLocation_in_id().toString());
				invInMap.put("location_id", Long.parseLong(getLocation_in_id.toString()));
				//invInMap.put("location_id", mtd.getLocation_in_id() == null ? 0 : mtd.getLocation_in_id());
				
				invList.add(invInMap);
				/*用于查询入库信息-------end--------*/
				
				//Map中存放<各帐表主键, 具体药品信息的map>用于判断
				mapInAffiFifo.put(mtd.getIn_store_id().toString()+mtd.getInv_id()+mtd.getPrice()+mtd.getBatch_no()+mtd.getBatch_sn()+mtd.getBar_code()+mtd.getLocation_in_id(), mapInDetail);
				mapInInvHold.put(mtd.getIn_store_id().toString()+mtd.getInv_id()+mtd.getLocation_in_id(), mapInDetail);
				//下面这两张表是区分年月的
				mapInDetail.put("year", year);
				mapInDetail.put("month", month);
				mapInBatchBalance.put(year+month+mtd.getIn_store_id()+mtd.getInv_id()+mtd.getPrice()+mtd.getBatch_no()+mtd.getBatch_sn()+mtd.getBar_code()+mtd.getLocation_in_id(), mapInDetail);
				mapInInvBalance.put(year+month+mtd.getIn_store_id()+mtd.getInv_id()+mtd.getLocation_in_id(), mapInDetail);
				
				//根据药品ID累计数量
				if(sum_amount_map.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()) != null){
					sum_amount_map.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), NumberUtil.add(sum_amount_map.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()), mtd.getAmount()));
				}else{
					sum_amount_map.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(),mtd.getAmount());
				}
				//根据药品ID累计金额
				if(sum_amount_money.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()) != null){
					sum_amount_money.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), NumberUtil.add(sum_amount_money.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()), mtd.getAmount_money()));
				}else{
					sum_amount_money.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), mtd.getAmount_money());
				}
				//根据药品ID累计金额
				if(sum_sale_money.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()) != null){
					sum_sale_money.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), NumberUtil.add(sum_sale_money.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()), mtd.getSale_money()));
				}else{
					sum_sale_money.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), mtd.getSale_money());
				}
			}
			/**入库数据-----------------------------------------end-----------------------------*/

			//------------------------------查询账表 取出相应数据 并且组织账表数据 med_affi_fifo----------------------------------
			List<Map<String,Object>> listFifoBalance = (List<Map<String,Object>>)medAffiFifoMapper.queryByInvList(invList);			
			
			Map<String,Map<String,Object>> mfb_mb = new HashMap<String,Map<String,Object>>();//所有 根据条件 取出表（MED_FIFO_BALANCE）的数据都放到map里面			
			
			for(Map<String,Object> mfb : listFifoBalance){
				String key  = mfb.get("store_id").toString()+mfb.get("inv_id")+Double.parseDouble(mfb.get("price").toString())+mfb.get("batch_no")+mfb.get("batch_sn")+mfb.get("bar_code")+mfb.get("location_id");
				mfb_mb.put(key, mfb);
			}
			//出库判断
			for (String key : mapOutAffiFifo.keySet()) {//循环当前药品的明细 key查询条件 取相应的值				
				
				Map<String,Object> mapBatch = new HashMap<String,Object>();
				if(mfb_mb.get(key) !=null){
					Map<String,Object> map = mapOutAffiFifo.get(key);
					mapBatch.putAll(map);
					//选中的单据中药品 map 与 mfb(账表中的药品) 中数量作对比
					Map<String,Object> mfb = mfb_mb.get(key);
					
					double amount = Double.parseDouble(map.get("amount").toString());
					double amount_money = Double.parseDouble(map.get("amount_money").toString());
					double sale_money = Double.parseDouble(map.get("sale_money").toString());
					/**判断实际库存是否充足-------------------begin--------------------------*/
					//所选单据药品合计数量 > 0 && 合计数量 > 帐表药品剩余数量 ? 剩余库存不足 : 剩余库存充足（用于出库判断）
					//所选单据药品合计数量 < 0 && (-1)合计数量 > 帐表药品剩余数量 ? 科室药品不足 : 科室药品充足（用于退库判断）
					if((amount > 0 && amount > Double.parseDouble(mfb.get("left_amount").toString())) || (amount < 0 && -1 * amount > Double.parseDouble(mfb.get("out_amount").toString()))){
						invMsg.append(map.get("inv_code")).append(" ").append(map.get("inv_name")).append(",");
						if(is_enough){
							is_enough = false;
						}
					}
					/**判断实际库存是否充足-------------------end----------------------------*/
					if(is_enough){
						mapBatch.put("out_amount", NumberUtil.add(amount, Double.parseDouble(mfb.get("out_amount").toString())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add(amount_money, Double.parseDouble(mfb.get("out_money").toString())));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mfb.get("out_sale_money").toString())));//出库批发金额 等于出库批发金额 + 当前出库批发金额
						mapBatch.put("left_amount", NumberUtil.sub(Double.parseDouble(mfb.get("left_amount").toString()), amount));//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("left_money", NumberUtil.sub(Double.parseDouble(mfb.get("left_money").toString()), amount_money));//剩余金额 等于剩余金额 - 当前出库金额
						mapBatch.put("left_sale_money", NumberUtil.sub(Double.parseDouble(mfb.get("left_sale_money").toString()), sale_money));//剩余数量 等于剩余数量 - 当前出库数量
						//以下字段的计算方式有待讨论
						if(NumberUtil.sub(Double.parseDouble(mfb.get("left_amount").toString()), amount) == 0){
							mapBatch.put("remove_zero_error", NumberUtil.sub(Double.parseDouble(mfb.get("in_money").toString()), Double.parseDouble(mapBatch.get("out_money").toString())));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", NumberUtil.sub(Double.parseDouble(mfb.get("in_sale_money").toString()), Double.parseDouble(mapBatch.get("out_sale_money").toString())));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
						
						listUpdateFifoBalance.add(mapBatch);
					}
				}else{
					//帐表中没有该药品信息，提示库存不足！
					invMsg.append(mapOutAffiFifo.get(key).get("inv_code")).append(" ").append(mapOutAffiFifo.get(key).get("inv_name")).append(",");
					if(is_enough){
						is_enough = false;
					}
				}
			}
			
			//如果药品出现库存物资不足的就不需要组装、判断另外几个帐表了
			if(is_enough){
				//入库判断
				for (String key : mapInAffiFifo.keySet()) {//循环当前药品的明细 key查询条件 取相应的值				
					Map<String,Object> mapBatch = new HashMap<String,Object>();			
					Map<String,Object> map = mapInAffiFifo.get(key);
					mapBatch.putAll(map);
					
					double amount = Double.parseDouble(map.get("amount").toString());
					double amount_money = Double.parseDouble(map.get("amount_money").toString());
					double sale_money = Double.parseDouble(map.get("sale_money").toString());
					
					if(mfb_mb.get(key) !=null){
						Map<String,Object> mfb = (Map<String,Object>)mfb_mb.get(key);
						mapBatch.put("in_amount", NumberUtil.add(amount, Double.parseDouble(mfb.get("in_amount").toString())));//入库数量 等于入库的数量 + 当前入库的数量
						mapBatch.put("in_money", NumberUtil.add(amount_money, Double.parseDouble(mfb.get("in_money").toString())));//入库金额 等于入库的金额 + 当前入库的金额
						mapBatch.put("in_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mfb.get("in_sale_money").toString())));//入库批发金额 等于入库批发金额 + 当前入库批发金额
						mapBatch.put("left_amount", NumberUtil.add(amount, Double.parseDouble(mfb.get("left_amount").toString())));//剩余数量 等于剩余的数量 + 当前入库的数量
						mapBatch.put("left_money", NumberUtil.add(amount_money, Double.parseDouble(mfb.get("left_money").toString())));//剩余金额 等于剩余金额 + 当前入库金额
						mapBatch.put("left_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mfb.get("left_sale_money").toString())));//剩余数量 等于剩余数量 + 当前入库数量
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);
						
						listUpdateFifoBalance.add(mapBatch);
					}else{
						mapBatch.put("in_amount", amount);
						mapBatch.put("in_money", amount_money);
						mapBatch.put("in_sale_money", sale_money);
						mapBatch.put("out_amount", 0);
						mapBatch.put("out_money", 0);
						mapBatch.put("out_sale_money", 0);
						mapBatch.put("left_amount", amount);
						mapBatch.put("left_money", amount_money);
						mapBatch.put("left_sale_money", sale_money);
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);
						
						listAddFifoBalance.add(mapBatch);
					}
				}
			
				//------------------------------查询账表 取出相应数据 并且组织账表数据MED_BATCH_BALANCE----------------------------------
				List<Map<String,Object>> listBatchBalance = (List<Map<String,Object>>)medAffiBatchMapper.queryByInvList(invList);

				Map<String,Map<String,Object>> mbb_mb = new HashMap<String,Map<String,Object>>();//所有 根据条件 取出表（MED_BATCH_BALANCE）的数据都放到map里面
				
				for(Map<String,Object> mbb : listBatchBalance){
					String key = mbb.get("year").toString()+mbb.get("month")+mbb.get("store_id")+mbb.get("inv_id")+Double.parseDouble(mbb.get("price").toString())+mbb.get("batch_no")+mbb.get("batch_sn")+mbb.get("bar_code")+mbb.get("location_id");
					mbb_mb.put(key, mbb);
				}
				
				//出库判断
				for (String key : mapOutBatchBalance.keySet()) {//循环当前药品的明细 key查询条件 取相应的值
					Map<String,Object> mapBatch = new HashMap<String,Object>();

					Map<String,Object> map = mapOutBatchBalance.get(key);
					mapBatch.putAll(map);
					
					double amount = Double.parseDouble(map.get("amount").toString());
					double amount_money = Double.parseDouble(map.get("amount_money").toString());
					double sale_money = Double.parseDouble(map.get("sale_money").toString());
					
					if(mbb_mb.get(key) !=null){
						Map<String,Object> mbb = (Map<String,Object>)mbb_mb.get(key);
						mapBatch.put("out_amount", NumberUtil.add(amount, Double.parseDouble(mbb.get("out_amount").toString())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add(amount_money, Double.parseDouble(mbb.get("out_money").toString())));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mbb.get("out_sale_money").toString())));//出库批发金额 等于出库的批发金额 + 当前出库的批发金额
						mapBatch.put("y_out_amount", NumberUtil.add(amount, Double.parseDouble(mbb.get("out_amount").toString())));//累计数量 等于剩余的数量 + 当前出库的数量
						mapBatch.put("y_out_money", NumberUtil.add(amount_money, Double.parseDouble(mbb.get("out_money").toString())));//累计数量 等于剩余的金额 + 当前出库的金额
						mapBatch.put("y_out_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mbb.get("out_sale_money").toString())));//累计批发金额 等于剩余的批发金额 + 当前出库的批发金额
						
						//以下字段的计算方式有待讨论
						if(NumberUtil.sub(Double.parseDouble(mbb.get("in_amount").toString()), Double.parseDouble(mapBatch.get("out_amount").toString())) == 0){
							mapBatch.put("remove_zero_error", NumberUtil.sub(Double.parseDouble(mbb.get("in_money").toString()), Double.parseDouble(mapBatch.get("out_money").toString())));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", NumberUtil.sub(Double.parseDouble(mbb.get("in_sale_money").toString()), Double.parseDouble(mapBatch.get("out_sale_money").toString())));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
							
						listUpdateBatchBalance.add(mapBatch);
					}else{
						mapBatch.put("y_begin_amount", 0);
						mapBatch.put("y_begin_money", 0);
						mapBatch.put("y_begin_sale_money", 0);
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
				//入库判断
				for (String key : mapInBatchBalance.keySet()) {//循环当前药品的明细 key查询条件 取相应的值
					Map<String,Object> mapBatch = new HashMap<String,Object>();

					Map<String,Object> map = mapInBatchBalance.get(key);
					mapBatch.putAll(map);
					
					double amount = Double.parseDouble(map.get("amount").toString());
					double amount_money = Double.parseDouble(map.get("amount_money").toString());
					double sale_money = Double.parseDouble(map.get("sale_money").toString());
					
					if(mbb_mb.get(key) !=null){
						Map<String,Object> mbb = (Map<String,Object>)mbb_mb.get(key);
						mapBatch.put("in_amount", NumberUtil.add(amount, Double.parseDouble(mbb.get("in_amount").toString())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("in_money", NumberUtil.add(amount_money, Double.parseDouble(mbb.get("in_money").toString())));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("in_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mbb.get("in_sale_money").toString())));//出库批发金额 等于出库的批发金额 + 当前出库的批发金额
						mapBatch.put("y_in_amount", NumberUtil.add(amount, Double.parseDouble(mbb.get("in_amount").toString())));//累计数量 等于剩余的数量 + 当前出库的数量
						mapBatch.put("y_in_money", NumberUtil.add(amount_money, Double.parseDouble(mbb.get("in_money").toString())));//累计数量 等于剩余的金额 + 当前出库的金额
						mapBatch.put("y_in_sale_money", NumberUtil.add(sale_money, Double.parseDouble(mbb.get("in_sale_money").toString())));//累计批发金额 等于剩余的批发金额 + 当前出库的批发金额
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);
						
						listUpdateBatchBalance.add(mapBatch);
					}else{
						mapBatch.put("y_begin_amount", 0);
						mapBatch.put("y_begin_money", 0);
						mapBatch.put("y_begin_sale_money", 0);
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", amount);
						mapBatch.put("in_money", amount_money);
						mapBatch.put("in_sale_money", sale_money);
						mapBatch.put("out_amount", 0);
						mapBatch.put("out_money", 0);
						mapBatch.put("out_sale_money", 0);
						mapBatch.put("y_in_amount", amount);
						mapBatch.put("y_in_money", amount_money);
						mapBatch.put("y_in_sale_money", sale_money);
						mapBatch.put("y_out_amount", 0);
						mapBatch.put("y_out_money", 0);
						mapBatch.put("y_out_sale_money", 0);
						mapBatch.put("location_id", mapBatch.get("location_id") == null ? 0 : mapBatch.get("location_id"));
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);
						
						listAddBatchBalance.add(mapBatch);
					}
				}
				
				//------------------------------查询账表 取出相应数据 并且组织账表数据MED_INV_HOLD----------------------------------
				List<Map<String,Object>> listInvHold = (List<Map<String,Object>>)medAffiInvHoldMapper.queryByInvList(invList);

				Map<String,Map<String,Object>> mih_mb = new HashMap<String,Map<String,Object>>();//所有 根据条件 取出表（MED_INV_HOLD）的数据都放到map里面
				
				for(Map<String,Object> mih : listInvHold){
					String key = mih.get("store_id").toString()+mih.get("inv_id")+mih.get("location_id");
					mih_mb.put(key, mih);
				}
				//出库判断
				for (String key : mapOutInvHold.keySet()) {//循环当前药品的明细 key查询条件 取相应的值					
					Map<String,Object> mapBatch = new HashMap<String,Object>();				
					if(mih_mb.get(key) !=null){
						Map<String,Object> map = mapOutInvHold.get(key);
						mapBatch.putAll(map);
						
						Map<String,Object> mih = (Map<String,Object>)mih_mb.get(key);
						
						mapBatch.put("cur_amount", NumberUtil.sub(Double.parseDouble(mih.get("cur_amount").toString()), Double.parseDouble(sum_amount_map.get(mih.get("store_id").toString()+"-"+mih.get("inv_id").toString()+"-"+mih.get("location_id").toString()).toString())));//剩余数量 等于剩余的数量 - 当前出库的数量	
						mapBatch.put("cur_money", NumberUtil.sub(Double.parseDouble(mih.get("cur_money").toString()), Double.parseDouble(sum_amount_money.get(mih.get("store_id").toString()+"-"+mih.get("inv_id").toString()+"-"+mih.get("location_id").toString()).toString())));//剩余金额 等于剩余的金额 - 当前出库的金额						
					
						listUpdateInvHold.add(mapBatch);
					}
				}
				//入库判断
				for (String key : mapInInvHold.keySet()) {//循环当前药品的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					Map<String,Object> map = mapInInvHold.get(key);
					mapBatch.putAll(map);
					
					if(mih_mb.get(key) !=null){
						
						Map<String,Object> mih = (Map<String,Object>)mih_mb.get(key);
						 //
						mapBatch.put("cur_amount", NumberUtil.add(Double.parseDouble(mih.get("cur_amount").toString()), Double.parseDouble(sum_amount_map.get((mih.get("store_id")+"-"+mih.get("inv_id")+"-"+mih.get("location_id"))).toString())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("cur_money", NumberUtil.add(Double.parseDouble(mih.get("cur_money").toString()), Double.parseDouble(sum_amount_money.get((mih.get("store_id")+"-"+mih.get("inv_id")+"-"+mih.get("location_id"))).toString())));//出库金额 等于出库的金额 + 当前出库的金额
						
						listUpdateInvHold.add(mapBatch);
					}else{
						mapBatch.put("cur_amount", Double.parseDouble(sum_amount_map.get((map.get("store_id")+"-"+map.get("inv_id")+"-"+map.get("location_id"))).toString()));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("cur_money", Double.parseDouble(sum_amount_money.get((map.get("store_id")+"-"+map.get("inv_id")+"-"+map.get("location_id"))).toString()));//出库金额 等于出库的金额 + 当前出库的金额
						
						listAddInvHold.add(mapBatch);
					}
				}

				//------------------------------查询账表 取出相应数据 并且组织账表数据MED_INV_Batch----------------------------------
				List<Map<String,Object>> listInvBalance = (List<Map<String,Object>>)medAffiBalanceMapper.queryByInvList(invList);
				
				Map<String,Map<String,Object>> mib_mb = new HashMap<String,Map<String,Object>>();//所有 根据条件 取出表（MED_INV_HOLD）的数据都放到map里面
				for(Map<String,Object> mib : listInvBalance){
					String key = mib.get("year").toString()+mib.get("month")+mib.get("store_id")+mib.get("inv_id")+mib.get("location_id");
					mib_mb.put(key, mib);
				}
				
				//出库判断
				for (String key : mapOutInvBalance.keySet()) {//循环当前药品的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					Map<String,Object> map = mapOutInvBalance.get(key);
					
					mapBatch.putAll(map);
					 
					if(mib_mb.get(key) !=null){ 
						Map<String,Object> mib = (Map<String,Object>)mib_mb.get(key);
						mapBatch.put("out_amount", NumberUtil.add(Double.parseDouble(sum_amount_map.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("out_amount").toString())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add(Double.parseDouble(sum_amount_money.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("out_money").toString())));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add(Double.parseDouble(sum_sale_money.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("out_sale_money").toString())));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("y_out_amount", NumberUtil.add(Double.parseDouble(sum_amount_map.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("out_amount").toString())));//累计数量 等于累计的数量 + 当前出库的数量
						mapBatch.put("y_out_money", NumberUtil.add(Double.parseDouble(sum_amount_money.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("out_money").toString())));//累计数量 等于累计的金额 + 当前出库的金额
						mapBatch.put("y_out_sale_money", NumberUtil.add(Double.parseDouble(sum_sale_money.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("out_sale_money").toString())));//累计数量 等于累计的金额 + 当前出库的金额
						//以下字段的计算方式有待讨论
						if(NumberUtil.sub(Double.parseDouble(mib.get("in_amount").toString()), Double.parseDouble(mapBatch.get("out_amount").toString())) == 0){
							mapBatch.put("remove_zero_error", NumberUtil.sub(Double.parseDouble(mib.get("in_money").toString()), Double.parseDouble(mapBatch.get("out_money").toString())));//拆零误差 等于入库金额 - 出库金额
							mapBatch.put("sale_zero_error", NumberUtil.sub(Double.parseDouble(mib.get("in_sale_money").toString()), Double.parseDouble(mapBatch.get("out_sale_money").toString())));//批发拆零误差 等于入库批发金额 - 出库批发金额
						}
							
						listUpdateInvBalance.add(mapBatch);
					}else{
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", 0);
						mapBatch.put("in_money", 0);
						mapBatch.put("in_sale_money", 0);
						mapBatch.put("out_amount",  Double.parseDouble(sum_amount_map.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("out_money",  Double.parseDouble(sum_amount_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("out_sale_money",  Double.parseDouble(sum_sale_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("y_in_amount", 0);
						mapBatch.put("y_in_money", 0);
						mapBatch.put("y_in_sale_money", 0);
						mapBatch.put("y_out_amount", Double.parseDouble(sum_amount_map.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("y_out_money", Double.parseDouble(sum_amount_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("y_out_sale_money", Double.parseDouble(sum_sale_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);//拆零误差 等于入库金额 - 出库金额
						mapBatch.put("sale_zero_error", 0);//批发拆零误差 等于入库批发金额 - 出库批发金额
						
						mapBatch.put("location_id", mapBatch.get("location_id") == null ? 0 : mapBatch.get("location_id"));
						
						listAddInvBalance.add(mapBatch);
					}
				}
				//入库判断
				for (String key : mapOutInvBalance.keySet()) {//循环当前药品的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					Map<String,Object> map = mapOutInvBalance.get(key);
					
					mapBatch.putAll(map);
					
					if(mib_mb.get(key) !=null){
						Map<String,Object> mib = (Map<String,Object>)mib_mb.get(key);
						mapBatch.put("in_amount", NumberUtil.add(Double.parseDouble(sum_amount_map.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("in_amount").toString())));//入库数量 等于入库的数量 + 当前入库的数量
						mapBatch.put("in_money", NumberUtil.add(Double.parseDouble(sum_amount_money.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("in_money").toString())));//入库金额 等于入库的金额 + 当前入库的金额
						mapBatch.put("in_sale_money", NumberUtil.add(Double.parseDouble(sum_sale_money.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("in_sale_money").toString())));//入库金额 等于入库的金额 + 当前入库的金额
						mapBatch.put("y_in_amount", NumberUtil.add(Double.parseDouble(sum_amount_map.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("in_amount").toString())));//累计数量 等于累计的数量 + 当前入库的数量
						mapBatch.put("y_in_money", NumberUtil.add(Double.parseDouble(sum_amount_money.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("in_money").toString())));//累计数量 等于累计的金额 + 当前入库的金额
						mapBatch.put("y_in_sale_money", NumberUtil.add(Double.parseDouble(sum_sale_money.get(mib.get("store_id").toString()+"-"+mib.get("inv_id").toString()+"-"+mib.get("location_id").toString()).toString()), Double.parseDouble(mib.get("in_sale_money").toString())));//累计金额 等于累计的金额 - 当前入库的金额
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);//拆零误差 等于入库金额 - 出库金额
						mapBatch.put("sale_zero_error", 0);//批发拆零误差 等于入库批发金额 - 出库批发金额
						
						listUpdateInvBalance.add(mapBatch);
					}else{
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", Double.parseDouble(sum_amount_map.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("in_money", Double.parseDouble(sum_amount_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("in_sale_money", Double.parseDouble(sum_sale_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("out_amount", 0);
						mapBatch.put("out_money",  0);
						mapBatch.put("out_sale_money",  0);
						mapBatch.put("y_in_amount", Double.parseDouble(sum_amount_map.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("y_in_money", Double.parseDouble(sum_amount_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("y_in_sale_money", Double.parseDouble(sum_sale_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()).toString()));
						mapBatch.put("y_out_amount", 0);
						mapBatch.put("y_out_money", 0);
						mapBatch.put("y_out_sale_money", 0);
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
				return "{\"error\":\""+invMsg.substring(0, invMsg.length()-1).toString()+"库存物资不足!\"}";
			}


			//添加/更新med_affi_fifo帐表
			if(listAddFifoBalance.size() >0 || listUpdateFifoBalance.size() >0){
				if(listAddFifoBalance.size()>0){
					medAffiFifoMapper.addBatch(listAddFifoBalance);
				}
				if(listUpdateFifoBalance.size()>0){
					medAffiFifoMapper.updateBatch(listUpdateFifoBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 med_affi_fifo帐表无数据变动\"}");
			}

			//添加/更新med_affi_batch帐表
			if(listAddBatchBalance.size() >0 || listUpdateBatchBalance.size() >0){
				if(listAddBatchBalance.size() >0){
					medAffiBatchMapper.addBatch(listAddBatchBalance);
				}
				if(listUpdateBatchBalance.size() >0){
					medAffiBatchMapper.updateBatch(listUpdateBatchBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 med_affi_batch帐表无数据变动\"}");
			}

			//添加/更新med_affi_balance帐表
			if(listAddInvBalance.size() >0 || listUpdateInvBalance.size() >0){
				if(listAddInvBalance.size()>0){
					medAffiBalanceMapper.addBatch(listAddInvBalance);
				}
				if(listUpdateInvBalance.size() >0){
					medAffiBalanceMapper.updateBatch(listUpdateInvBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 med_affi_balance帐表无数据变动\"}");
			}

			//添加/更新med_affi_inv_hold帐表
			if(listAddInvHold.size() >0 || listUpdateInvHold.size() >0){
				if(listAddInvHold.size() >0){
					medAffiInvHoldMapper.addBatch(listAddInvHold);
				}
				if(listUpdateInvHold.size() >0){
					medAffiInvHoldMapper.updateBatch(listUpdateInvHold);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 med_affi_inv_hold帐表无数据变动\"}");
			}
			
			//修改调拨单状态为调入确认
			medAffiTranMainMapper.updateConfirmBatch(listUpdateMainTran);
			//修改出库单状态为出库确认
			medAffiOutMapper.updateBatchConfirm(listUpdateMainOut);
			//修改入库单状态为入库确认
			medAffiInMapper.updateBatchConfirm(listUpdateMainIn);
		
			return "{\"msg\":\"调入确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"调入确认失败 数据库异常 请联系管理员! 方法 inConfirmMedTranMain\"}";
		}
	}

	/**
	 * @Description 代销调拨  冲账
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String balanceConfirmMedAffiTranMain(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放med_tran_main字段
		List<Map<String, Object>> detailList =new ArrayList<Map<String, Object>>();//存放med_tran_detail字段
		
		List<Map<String, Object>> outMainList =new ArrayList<Map<String, Object>>();//存放med_out_main字段
		List<Map<String, Object>> outDetailList =new ArrayList<Map<String, Object>>();//存放med_in_detail字段
		
		List<Map<String, Object>> inMainList =new ArrayList<Map<String, Object>>();//存放med_out_main字段
		List<Map<String, Object>> inDetailList =new ArrayList<Map<String, Object>>();//存放med_in_detail字段
		String out_hos_id = "";
		String out_copy_code = "";
		String out_store_id = "";
		String out_store_no = "";
		String in_hos_id = "";
		String in_copy_code = "";
		String in_store_id = "";
		String in_store_no = "";
		String tran_date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String maker = SessionManager.getUserId();
	
		try {

			List<Map<String, Object>> listVo = entityMap;
			for (Map<String, Object> tmp : entityMap) {
				
				MedAffiTranMain mtm = (MedAffiTranMain) medAffiTranMainMapper.queryByCode(tmp);
				Map<String, Object> mainMap = defaultBeanToMap(mtm);
				Long tran_id = medAffiTranMainMapper.queryMedAffiTranMainSeq();// 查询序列
				mainMap.put("tran_id", tran_id);
				mainMap.put("tran_no", getNextIn_no(mainMap));// 获取出库单号
				mainMap.put("state", "1");
				mainMap.put("year", tran_date.substring(0, 4));
				mainMap.put("month", tran_date.substring(5, 7));
				mainMap.put("day", tran_date.substring(8, 10));  //用于生成单号
				
				mainMap.put("tran_date", DateUtil.stringToDate(tran_date, "yyyy-MM-dd"));
				mainMap.put("maker", maker);
				mainMap.put("checker", "");
				mainMap.put("check_date", "");
				mainMap.put("confirmer", "");
				mainMap.put("confirm_date", "");
				mainMap.put("bus_type", mtm.getBus_type());
				//冲账需要生成反向单据即调出信息和调入信息交换
				in_hos_id = mainMap.get("out_hos_id").toString();
				in_copy_code = mainMap.get("out_copy_code") == null ? "" : mainMap.get("out_copy_code").toString();
				in_store_id = mainMap.get("out_store_id").toString();
				in_store_no = mainMap.get("out_store_no").toString();
				out_hos_id = mainMap.get("in_hos_id").toString();
				out_copy_code = mainMap.get("in_copy_code") == null ? "" : mainMap.get("in_copy_code").toString();
				out_store_id = mainMap.get("in_store_id").toString();
				out_store_no = mainMap.get("in_store_no").toString();
				mainMap.put("in_hos_id", in_hos_id);
				mainMap.put("in_copy_code", in_copy_code);
				mainMap.put("in_store_id", in_store_id);
				mainMap.put("in_store_no", in_store_no);
				mainMap.put("out_hos_id", out_hos_id);
				mainMap.put("out_copy_code", out_copy_code);
				mainMap.put("out_store_id", out_store_id);
				mainMap.put("out_store_no", out_store_no);
				
				/*--------------需要同时生成出入库单--begin----------------------------*/
				//注：院内调拨需要同时生成出入库单；院外调拨只生成出库单，入库单在调入单位调入确认时生成。
				String brief = "由调拨单"+mainMap.get("tran_no")+"生成";
				String out_bus = "";
				String in_bus = "";
				if("1".equals(String.valueOf(mainMap.get("tran_type")))){
					//院内调拨
					out_bus = "32";  //移出库
					in_bus = "31";  //移入库
				}else{
					//院外调拨
					if("1".equals(mainMap.get("bus_type").toString())){
						out_bus = "7";  //无偿调出
						in_bus = "6";  //无偿调入
					}else{
						out_bus = "5";  //有偿调出
						in_bus = "4";  //有偿调入
					}
				}
				//存放出库主从表
				Map<String, Object> outMap = new HashMap<String, Object>();
				//存放入库主从表
				Map<String, Object> inMap = new HashMap<String, Object>();
				//生成出库单
				outMap.put("group_id", mainMap.get("group_id").toString());
				outMap.put("hos_id", mainMap.get("hos_id").toString());
				outMap.put("copy_code", mainMap.get("copy_code").toString());
				outMap.put("year", mainMap.get("year"));
				outMap.put("month", mainMap.get("month"));
				outMap.put("day", mainMap.get("day"));  //用于生成单号
				outMap.put("bus_type_code", out_bus);
				outMap.put("state", 1);
				outMap.put("brief", brief);
				//出库的主表用的to_date函数转换的，所以这里直接用String型
				outMap.put("maker", SessionManager.getUserId());
				outMap.put("out_date", mainMap.get("tran_date"));
				outMap.put("store_id", out_store_id.toString());
				outMap.put("store_no", out_store_no.toString());
				outMap.put("table_code", "MED_AFFI_OUT");
				outMap.put("prefixe", "");
				long out_id = medAffiOutMapper.queryMedAffiOutMainSeq();
				outMap.put("out_id", out_id);
				String out_no = medCommonService.getMedNextNo(outMap);
				if(out_no.indexOf("error")!=-1){
					return out_no;
				}
				outMap.put("out_no", out_no);
				
				//生成入库单
				inMap.put("group_id", mainMap.get("group_id").toString());
				inMap.put("hos_id", mainMap.get("hos_id").toString());
				inMap.put("copy_code", mainMap.get("copy_code").toString());
				inMap.put("year", mainMap.get("year"));
				inMap.put("month", mainMap.get("month"));
				inMap.put("day", mainMap.get("day"));  //用于生成单号
				inMap.put("bus_type_code", in_bus);
				inMap.put("state", 1);
				inMap.put("brief", brief);
				inMap.put("maker", SessionManager.getUserId());
				inMap.put("in_date", mainMap.get("tran_date"));
				inMap.put("bus_type_code", 31);//移入库
				inMap.put("store_id", in_store_id.toString());
				inMap.put("store_no", in_store_no.toString());
				inMap.put("table_code", "MED_AFFI_IN");
				inMap.put("prefixe", "");
				inMap.put("in_id", medAffiInMapper.queryAffiInMainSeq());
				String in_no = medCommonService.getMedNextNo(inMap);
				if(in_no.indexOf("error")!=-1){
					return in_no;
				}
				inMap.put("in_no", in_no);
				
				//处理与出入库单的对应关系
				mainMap.put("in_id", inMap.get("in_id").toString());
				mainMap.put("in_no", inMap.get("in_no").toString());
				mainMap.put("out_id", outMap.get("out_id").toString());
				mainMap.put("out_no", outMap.get("out_no").toString());
				mainList.add(mainMap);
				
					
				/*--------------需要同时生成出入库单--end------------------------------*/

				//查询明细表
				List<MedAffiTranDetail> list_detail = (List<MedAffiTranDetail>)medAffiTranDetailMapper.query(tmp);				
				for(MedAffiTranDetail mtd:list_detail){					
					Map<String, Object> detailMap = detailBeanToMap(mtd);
					Map<String, Object> outDetailMap = new HashMap<String, Object>();
					Map<String, Object> inDetailMap = new HashMap<String, Object>();

					detailMap.put("tran_id", tran_id);
					detailMap.put("tran_no", mainMap.get("tran_no").toString());
					detailMap.put("tran_detail_id", medAffiTranDetailMapper.queryMedAffiTranDetailSeq());
					detailMap.put("store_id", out_store_id.toString());
					if(validateJSON(String.valueOf(mtd.getInva_date()))){
						
						detailMap.put("inva_date",mtd.getInva_date());
					}
					if(validateJSON(String.valueOf(mtd.getDisinfect_date()))){
						detailMap.put("disinfect_date",mtd.getDisinfect_date());
					}
					detailList.add(detailMap);
					
					/*处理出入库明细---------begin----------------*/
					//出入明细
					outDetailMap.putAll(detailMap);
					outDetailMap.put("out_id", outMap.get("out_id").toString());
					outDetailMap.put("out_no", outMap.get("out_no").toString());
					outDetailMap.put("detail_id", medAffiOutMapper.queryMedAffiOutDetailSeq());
					
					if (validateJSON(String.valueOf(detailMap.get("location_out_id")))) {
						outDetailMap.put("location_id", detailMap.get("location_out_id"));
					}else{
						outDetailMap.put("location_id", "0");
					}
					
					//outDetailMap.put("location_id", detailMap.get("location_out_id"));
					
					if(validateJSON(String.valueOf(mtd.getInva_date()))){
						outDetailMap.put("inva_date",mtd.getInva_date());
					}
					if(validateJSON(String.valueOf(mtd.getDisinfect_date()))){
						outDetailMap.put("disinfect_date",mtd.getDisinfect_date());
					}
					outDetailList.add(outDetailMap);
					
					//入库明细
					inDetailMap.putAll(detailMap);
					inDetailMap.put("in_id", inMap.get("in_id").toString());
					inDetailMap.put("in_no", inMap.get("in_no").toString());
					inDetailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
					if(validateJSON(String.valueOf(mtd.getInva_date()))){
						inDetailMap.put("inva_date",mtd.getInva_date());
					}
					if(validateJSON(String.valueOf(mtd.getDisinfect_date()))){
						inDetailMap.put("disinfect_date",mtd.getDisinfect_date());
					}
					
					if (validateJSON(String.valueOf(detailMap.get("location_in_id")))) {
						inDetailMap.put("location_id", detailMap.get("location_in_id"));
					}else{
						inDetailMap.put("location_id", "0");
					}
					inDetailList.add(inDetailMap);
					/*处理出入库明细---------end------------------*/
				}
				
				inMainList.add(inMap);
				outMainList.add(outMap);
			}

			//判断即时库存是否充足(防止多人同时操作同一药品，额外加的判断)
			String invEnough = medCommonMapper.existsMedAffiInvIsEnough(outDetailList);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下药品库存物资不足!"+invEnough+"\"}";
			}
			//调拨明细表
			if(detailList.size()>0){
				medAffiTranMainMapper.addBatch(mainList);
				medAffiTranDetailMapper.addBatch(detailList);
				medAffiOutMapper.addMedAffiOutMainBatch(outMainList);
				medAffiOutMapper.addMedAffiOutDetail(outDetailList);
				medAffiInMapper.addMedAffiInDetail(inDetailList);
				medAffiInMapper.addMedAffiInMainBatch(inMainList);
				medAffiTranRelaMapper.addBatch(mainList);
			}else{
				return "{\"error\":\"操作失败，获取数据为空！\"}";
			}

			return "{\"msg\":\"冲账成功.\",\"state\":\"true\"}";

		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"冲账失败 数据库异常 请联系管理员! 方法 balanceConfirmMedTranMain\"}";
		}
		
	}

	@Override
	public String queryMedAffiTranMainByMedInv(Map<String, Object> entityMap) throws DataAccessException {		
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiTranMainMapper.queryMedAffiTranMainByMedInv(entityMap);		
			return ChdJson.toJsonLower(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiTranMainMapper.queryMedAffiTranMainByMedInv(entityMap, rowBounds);		
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());			
		}
	}
	
	
	
	
	
	// 返回出库明细用于保存的默认值
		public Map<String, Object> defaultOutDetailValue() {
			Map<String, Object> mapDetailVo = new HashMap<String, Object>();

			mapDetailVo.put("group_id", 0);
			mapDetailVo.put("hos_id", 0);
			mapDetailVo.put("copy_code", "");
			mapDetailVo.put("out_id", 0);
			mapDetailVo.put("out_no", "");
			// mapDetailVo.put("out_detail_id", "");
			mapDetailVo.put("inv_id", 0);
			mapDetailVo.put("inv_no", 0);
			mapDetailVo.put("batch_sn", 0);
			mapDetailVo.put("batch_no", "");
			mapDetailVo.put("price", 0);
			mapDetailVo.put("amount", 0);
			mapDetailVo.put("sale_price", 0);
			mapDetailVo.put("sale_money", 0);
			mapDetailVo.put("sell_price", 0);
			mapDetailVo.put("sell_money", 0);
			mapDetailVo.put("allot_price", 0);
			mapDetailVo.put("allot_money", 0);
			mapDetailVo.put("amount_money", 0);
			mapDetailVo.put("num_exchange", 0);
			mapDetailVo.put("pack_price", 0);
			mapDetailVo.put("num", 0);
			mapDetailVo.put("bar_code", "");
			mapDetailVo.put("is_per_bar", 0);
			mapDetailVo.put("sn", "");
			mapDetailVo.put("inva_date", "");
			mapDetailVo.put("disinfect_date", "");
			mapDetailVo.put("location_id", 0);
			mapDetailVo.put("note", "");
			mapDetailVo.put("pack_code", "");

			return mapDetailVo;
		}
		
		//调拨主表信息
		public Map<String, Object> defaultBeanToMap(MedAffiTranMain mtm) {
			Map<String, Object> map = new HashMap<String, Object>();
			
				map.put("group_id", mtm.getGroup_id());
				map.put("hos_id", mtm.getHos_id());
				map.put("copy_code", mtm.getCopy_code());
				map.put("year", mtm.getYear());
				map.put("month", mtm.getMonth());
				map.put("tran_method", mtm.getTran_method());
				map.put("tran_type", mtm.getTran_type());
				map.put("out_hos_id", mtm.getOut_hos_id());
				map.put("out_store_id", mtm.getOut_store_id());
				map.put("out_store_no", mtm.getOut_store_no());
				map.put("in_hos_id", mtm.getIn_hos_id());
				map.put("in_store_id", mtm.getIn_store_id());
				map.put("in_store_no", mtm.getIn_store_no());
				map.put("brief", mtm.getBrief());
				map.put("tran_date", DateUtil.dateToString(mtm.getTran_date(), "yyyy-MM-dd"));
				map.put("maker", mtm.getMaker());
				map.put("checker", mtm.getChecker());
				map.put("check_date", DateUtil.dateToString(mtm.getCheck_date(), "yyyy-MM-dd"));
				map.put("confirmer", mtm.getConfirmer());
				map.put("confirm_date", DateUtil.dateToString(mtm.getConfirm_date(), "yyyy-MM-dd"));
				map.put("state", "1");
			
				return map;
		}
		//调拨明细表信息
		public Map<String, Object> detailBeanToMap(MedAffiTranDetail mtd) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("group_id", mtd.getGroup_id());
			map.put("hos_id", mtd.getHos_id());
			map.put("copy_code", mtd.getCopy_code());
			map.put("tran_id", mtd.getTran_id());
			map.put("inv_id", mtd.getInv_id());
			map.put("inv_no", mtd.getInv_no());
			map.put("batch_sn", mtd.getBatch_sn() != null ? mtd.getBatch_sn().toString() : "");
			map.put("batch_no", mtd.getBatch_no() != null ? mtd.getBatch_no().toString() : "");
			map.put("amount", mtd.getAmount() != null ? mtd.getAmount() : "0");
			map.put("price", mtd.getPrice() != null ? mtd.getPrice() : "0");
			map.put("amount_money", mtd.getAmount_money() != null ? mtd.getAmount_money() : "0");
			map.put("sell_price", mtd.getSell_price() != null ? mtd.getSell_price() : "0");
			map.put("sell_money", mtd.getSell_money() != null ? mtd.getSell_money() : "0");
			map.put("allot_price", mtd.getAllot_price() != null ? mtd.getAllot_price() : "0");
			map.put("allot_money", mtd.getAllot_money() != null ? mtd.getAllot_money() : "0");
			map.put("pack_code", mtd.getPack_code() != null ? mtd.getPack_code() : "0");
			map.put("num_exchange", mtd.getNum_exchange() != null ? mtd.getNum_exchange() : "0");
			map.put("pack_price", mtd.getPack_price() != null ? mtd.getPack_price() : "0");
			map.put("num", mtd.getNum() != null ? mtd.getNum() : "0");
			map.put("bar_code", mtd.getBar_code() != null ? mtd.getBar_code().toString() : "");
			map.put("inva_date", mtd.getInva_date() != null ? DateUtil.dateToString(mtd.getInva_date(), "yyyy-MM-dd") : "");
			map.put("disinfect_date", mtd.getDisinfect_date() != null ? DateUtil.dateToString(mtd.getDisinfect_date(), "yyyy-MM-dd") : "");
			map.put("location_out_id", mtd.getLocation_out_id() != null ? mtd.getLocation_out_id() : "0");
			map.put("location_in_id", mtd.getLocation_in_id() != null ? mtd.getLocation_in_id() : "0");
			map.put("note", mtd.getNote() != null ? mtd.getNote() : "");
			
			return map;
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
		
		/**
		 * 修改加载明细数据
		 */
		@Override
		public String queryMedAffiTranDetailByTranID( Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();			
			sysPage = (SysPage) entityMap.get("sysPage");			
			if (sysPage.getTotal()==-1){				
				List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiTranDetailMapper.queryMedAffiTranDetailByTranID(entityMap);				
				return ChdJson.toJsonLower(list);				
			}else{				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());				
				List<Map<String,Object>> list = (List<Map<String,Object>>)medAffiTranDetailMapper.queryMedAffiTranDetailByTranID(entityMap, rowBounds);				
				PageInfo page = new PageInfo(list);
				return ChdJson.toJsonLower(list, page.getTotal());				
			}
		}
		
		/**
		 * 用来返回主表信息
		 */
		@Override
		public MedAffiTranMain queryMedAffiTranMainByCode(Map<String, Object> entityMap) throws DataAccessException {
			return medAffiTranMainMapper.queryMedAffiTranMainByCode(entityMap);
		}

		@Override
		public Map<String, Object> queryMainByCode(Map<String, Object> entityMap) throws DataAccessException {
			return medAffiTranMainMapper.queryMainByCode(entityMap);
		}
        
		
		//入库模板打印（包含主从表）
		@Resource(name = "superPrintService")
		private final SuperPrintService superPrintService = null;
		@Override
		public String queryMedAffiTranByPrintTemlate(Map<String, Object> entityMap)
				throws DataAccessException {
			try {
				if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
					List<Map<String, Object>> map =medAffiTranMainMapper.queryMedAffiTranPrintTemlateByMainBatch(entityMap);
					List<Map<String,Object>> list=medAffiTranMainMapper.queryMedAffiTranPrintTemlateByDetail(entityMap);
					return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
				}else{
					//查询入库主表
					Map<String,Object> map=medAffiTranMainMapper.queryMedAffiTranPrintTemlateByMain(entityMap);
					
					//查询入库明细表
					List<Map<String,Object>> list=medAffiTranMainMapper.queryMedAffiTranPrintTemlateByDetail(entityMap);
					
					return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
					
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
			
			
			
		}
		
		//PageOffice批量打印凭证-返回Map
				@Override
				public Map<String,Object> queryMedAffiTranPrintTemlateMain(Map<String, Object> map)throws DataAccessException {
					
					try{
						Map<String,Object> reMap=new HashMap<String,Object>();
						WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
						MedAffiTranMainMapper medAffiTranMainMapper = (MedAffiTranMainMapper)context.getBean("medAffiTranMainMapper");
						if("1".equals(String.valueOf(map.get("p_num")))){ 
						//查询凭证主表
						//Map<String,Object> mainList=medAffiTranMainMapper.queryMedAffiTranPrintTemlateByMain(map);
						List<Map<String, Object>> mainList =medAffiTranMainMapper.queryMedAffiTranPrintTemlateByMainBatch(map);		
						//查询凭证明细表
						List<Map<String,Object>> detailList=medAffiTranMainMapper.queryMedAffiTranPrintTemlateByDetail(map);
						reMap.put("main", mainList);
						reMap.put("detail", detailList);
						}else{
							//查询入库主表
							Map<String,Object> mainList=medAffiTranMainMapper.queryMedAffiTranPrintTemlateByMain(map);
							
							//查询入库明细表
							List<Map<String,Object>> detailList=medAffiTranMainMapper.queryMedAffiTranPrintTemlateByDetail(map);
							reMap.put("main", mainList);
							reMap.put("detail", detailList);
							
						}
						
						
						
						return reMap;
					}catch(Exception e){
						logger.error(e.getMessage(),e);
						throw new SysException(e.getMessage());
					}
					
				}
		
		
		
}
