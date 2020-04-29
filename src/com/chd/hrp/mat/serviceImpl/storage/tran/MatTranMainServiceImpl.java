/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.tran; 

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
import com.chd.hrp.mat.dao.base.MatApplyOutRelaMapper;
import com.chd.hrp.mat.dao.base.MatBatchBalanceMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatFifoBalanceMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatInvBalanceMapper;
import com.chd.hrp.mat.dao.base.MatInvHoldMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.base.MatOutResourceMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutDetailMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutMainMapper;
import com.chd.hrp.mat.dao.storage.tran.MatTranDetailMapper;
import com.chd.hrp.mat.dao.storage.tran.MatTranMainMapper;
import com.chd.hrp.mat.dao.storage.tran.MatTranRelaMapper;
import com.chd.hrp.mat.entity.MatBatchBalance;
import com.chd.hrp.mat.entity.MatFifoBalance;
import com.chd.hrp.mat.entity.MatInvBalance;
import com.chd.hrp.mat.entity.MatInvHold;
import com.chd.hrp.mat.entity.MatTranDetail;
import com.chd.hrp.mat.entity.MatTranMain;
import com.chd.hrp.mat.entity.MatTranRela;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.mat.service.storage.tran.MatTranMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
 * @Table:
 * MAT_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */ 
 


@Service("matTranMainService")
public class MatTranMainServiceImpl implements MatTranMainService {

	private static Logger logger = Logger.getLogger(MatTranMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matTranMainMapper")
	private final MatTranMainMapper matTranMainMapper = null;
	@Resource(name = "matTranDetailMapper")
	private final MatTranDetailMapper matTranDetailMapper = null;
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matApplyOutRelaMapper")
	private final MatApplyOutRelaMapper matApplyOutRelaMapper = null;
	
	//四个账表
	@Resource(name = "matInvHoldMapper")
	private final MatInvHoldMapper matInvHoldMapper = null;
	
	@Resource(name = "matFifoBalanceMapper")
	private final MatFifoBalanceMapper matFifoBalanceMapper = null;
	
	@Resource(name = "matBatchBalanceMapper")
	private final MatBatchBalanceMapper matBatchBalanceMapper = null;
	
	@Resource(name = "matInvBalanceMapper")
	private final MatInvBalanceMapper matInvBalanceMapper = null;
	
	@Resource(name = "matOutMainMapper")
	private final MatOutMainMapper matOutMainMapper = null;
	@Resource(name = "matOutDetailMapper")
	private final MatOutDetailMapper matOutDetailMapper = null;
	@Resource(name = "matOutResourceMapper")
	private final MatOutResourceMapper matOutResourceMapper = null;
	@Resource(name = "matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper = null;
	
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	
	@Resource(name = "matTranRelaMapper")
	private final MatTranRelaMapper matTranRelaMapper = null;
    
	/**
	 * @Description 
	 * 添加调拨方式TRAN_METHOD：1同价调拨  2 异价调拨；调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象调拨方式TRAN_METHOD：1同价调拨  2 异价调拨 调拨类型：1 院内调拨  2 院外调拨

		try {

			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005").toString());
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			entityMap = defaultValue(entityMap);

			String tran_date = (String) entityMap.get("tran_date");

			//entityMap.put("year", tran_date.substring(0, 4));
			//entityMap.put("month", tran_date.substring(5, 7));
			entityMap.put("day", tran_date.substring(8, 10));  //用于生成单号
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("tran_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			int flag = 1;
			
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			// (2)、调出判断库房是否已启用
			entityMap.put("store_id", entityMap.get("out_store_id"));
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，调出库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			// (3)、调入判断库房是否已启用
			entityMap.put("store_id", entityMap.get("in_store_id"));
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，调入库房本期间未启用请配置！\",\"state\":\"false\"}";
			}

			Long tran_id = matTranMainMapper.queryMatTranMainSeq();// 查询序列

			entityMap.put("tran_id", tran_id);
			entityMap.put("tran_no", getNextTran_no(entityMap));// 获取出库单号
			/*--------------需要同时生成出入库单--begin----------------------------*/
			//注：院内调拨需要同时生成出入库单；院外调拨只生成出库单，入库单在调入单位调入确认时生成。
			String brief = "由调拨单"+entityMap.get("tran_no")+"生成";
			String out_bus = "";
			String in_bus = "";
			boolean is_in = false;
			if("1".equals(String.valueOf(entityMap.get("tran_type")))){
				//院内调拨
				out_bus = "15";  //移出库
				in_bus = "14";  //移入库
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
			outMap.put("out_date", entityMap.get("tran_date"));
			outMap.put("store_id", entityMap.get("out_store_id"));
			outMap.put("store_no", entityMap.get("out_store_no"));
			outMap.put("table_code", "MAT_OUT_MAIN");
			outMap.put("prefixe", "");
			long out_id = matOutMainMapper.queryMatOutMainSeq();
			outMap.put("out_id", out_id);
			String out_no = matCommonService.getMatNextNo(outMap);
			if(out_no.indexOf("error")!=-1){
				return out_no;
			}
			outMap.put("out_no", out_no);
			
			if(is_in){
				//生成入库单
				inMap.put("group_id", entityMap.get("group_id"));
				inMap.put("hos_id", entityMap.get("hos_id"));
				inMap.put("copy_code", entityMap.get("copy_code"));
				inMap.put("year", entityMap.get("year"));
				inMap.put("month", entityMap.get("month"));
				inMap.put("day", entityMap.get("day"));  //用于生成单号
				inMap.put("bus_type_code", in_bus);
				inMap.put("state", 1);
				inMap.put("brief", brief);
				inMap.put("maker", SessionManager.getUserId());
				inMap.put("in_date", DateUtil.stringToDate(entityMap.get("tran_date").toString(), "yyyy-MM-dd"));
				
				inMap.put("bus_type_code", 14);//移入库
				inMap.put("store_id", entityMap.get("in_store_id"));
				inMap.put("store_no", entityMap.get("in_store_no"));
				inMap.put("table_code", "MAT_IN_MAIN");
				inMap.put("prefixe", "");
				inMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
				String in_no = matCommonService.getMatNextNo(inMap);
				if(in_no.indexOf("error")!=-1){
					return in_no;
				}
				inMap.put("in_no", in_no);
			}
			/*--------------需要同时生成出入库单--end------------------------------*/
			
			// 保存明细数据
			List<Map<String, Object>> tran_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray tran_detail_json = JSONArray.parseArray((String) entityMap.get("tran_detail_data"));
			
			double money_sum = 0;//记录明细总金额
			StringBuffer invEnoughMsg = new StringBuffer();//记录库存不足的材料
			Iterator tran_detail_it = tran_detail_json.iterator();

			while (tran_detail_it.hasNext()) {

				Map<String, Object> mapDetailVo = defaultDetailValue();
				
				/*材料主信息-----------------begin---------------------*/
				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("tran_id", entityMap.get("tran_id").toString());
				mapDetailVo.put("tran_no", entityMap.get("tran_no").toString());
				mapDetailVo.put("store_id", entityMap.get("out_store_id").toString());//用于查询材料信息

				JSONObject jsonObj = JSONObject.parseObject(tran_detail_it.next().toString());
				//没材料ID视为空行
				if(jsonObj.get("inv_id") != null && !"".equals(String.valueOf(jsonObj.get("inv_id")))){
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("amount")))) {mapDetailVo.put("amount", jsonObj.get("amount").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {mapDetailVo.put("price", jsonObj.get("price").toString());}
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
					if (validateJSON(String.valueOf(jsonObj.get("bar_code")))) {mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("inva_date",null);
					}
					if (validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("disinfect_date",null);
					}
					if (validateJSON(String.valueOf(jsonObj.get("location_out_id")))) {
						mapDetailVo.put("location_out_id", jsonObj.get("location_out_id").toString());
					}else{
						mapDetailVo.put("location_out_id", "0");
					}
					mapDetailVo.put("location_id", mapDetailVo.get("location_out_id"));  //用于先进先出算法
					if (validateJSON(String.valueOf(jsonObj.get("location_in_id")))) {
						mapDetailVo.put("location_in_id", jsonObj.get("location_in_id").toString());
					}else{
						mapDetailVo.put("location_in_id", "0");
					}
					if (validateJSON(String.valueOf(jsonObj.get("note")))) {mapDetailVo.put("note", jsonObj.get("note").toString());}
					/*材料主信息-----------------end-----------------------*/
	
					/*材料批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && validateJSON(String.valueOf(jsonObj.get("amount"))) && validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没材料ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								Map<String, Object> inDetailMap = new HashMap<String, Object>();
								Map<String, Object> outDetailMap = defaultOutDetailValue();
								detailMap.putAll(mapDetailVo);

								detailMap.put("tran_detail_id", matTranDetailMapper.queryMatTranDetailSeq());
								if (validateJSON(String.valueOf(m.get("batch_sn")))) {detailMap.put("batch_sn", m.get("batch_sn").toString());}
								if (validateJSON(String.valueOf(m.get("amount")))) {detailMap.put("amount", m.get("amount").toString());}
								if (validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
									//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
									money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
									
								}
								if (validateJSON(String.valueOf(m.get("sale_money")))) {detailMap.put("sale_money", m.get("sale_money").toString());}
								if (validateJSON(String.valueOf(m.get("sell_money")))) {detailMap.put("sell_money", m.get("sell_money").toString());}
								if (validateJSON(String.valueOf(m.get("allot_money")))) {detailMap.put("allot_money", m.get("allot_money").toString());}
								
								tran_detail_batch.add(detailMap);
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("out_detail_id", matOutDetailMapper.queryMatOutDetailSeq());
								outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("disinfect_date",  null);
									}
									inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									inDetailList.add(inDetailMap);
								}
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							Map<String, Object> inDetailMap = new HashMap<String, Object>();
							Map<String, Object> outDetailMap = defaultOutDetailValue();
							detailMap.putAll(mapDetailVo);
							
							detailMap.put("tran_detail_id", matTranDetailMapper.queryMatTranDetailSeq());
							detailMap.put("batch_sn", map.get("batch_sn").toString());
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
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								
								tran_detail_batch.add(detailMap);
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("out_detail_id", matOutDetailMapper.queryMatOutDetailSeq());
								outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("disinfect_date",  null);
									}
									inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
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
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								
								tran_detail_batch.add(detailMap);		
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("out_detail_id", matOutDetailMapper.queryMatOutDetailSeq());
								outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("disinfect_date",  null);
									}
									inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
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
					/*材料批次信息---------------end-----------------------*/
				}
			}
			if(tran_detail_batch.size() <= 0){
				return "{\"error\":\"调拨单明细数据为空!\"}";
			}
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足!\"}";
			}

			//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
			String invEnough = matCommonMapper.existsMatStockInvIsEnough(tran_detail_batch);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			}
			
			//新增调拨单主表
			entityMap.put("amount_money", money_sum);
			matTranMainMapper.add(entityMap);
			//新增调拨单明细表
			matTranDetailMapper.addBatch(tran_detail_batch);
			
			//新增出库单主表
			outMap.put("come_from", "5"); 
			outMap.put("amount_money", money_sum);
			matOutMainMapper.add(outMap);
			//新增出库单明细表
			matOutDetailMapper.addBatch(outDetailList);
			//新增出库单资金来源
			outMap.put("source_money", money_sum);
			matOutResourceMapper.add(outMap);
			
			//新增入库单主表
			inMap.put("come_from", "5"); 
			inMap.put("amount_money", money_sum);
			matInCommonMapper.addMatInMain(inMap);
			//新增入库单明细表
			matInCommonMapper.addMatInDetailBatch(inDetailList);
			//新增入库单资金来源
			inMap.put("source_money", money_sum);
			matInCommonMapper.insertMatInResource(inMap);

			//添加调拨单与出入库单对应关系
			entityMap.put("out_id", outMap.get("out_id"));
			entityMap.put("out_no", outMap.get("out_no"));
			entityMap.put("in_id", inMap.get("in_id"));
			entityMap.put("in_no", inMap.get("in_no"));
			matTranRelaMapper.add(entityMap);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("tran_id").toString()+","+
			entityMap.get("out_store_id").toString()+","
			+"\"}";
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

		if (mapVo.get("tran_type") == null) {mapVo.put("tran_type", "");}

		if (mapVo.get("bus_type") == null) {mapVo.put("bus_type", "");}

		if (mapVo.get("tran_method") == null) {mapVo.put("tran_method", "1");}

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

		if (mapVo.get("state") == null) {mapVo.put("state", "1");}

		return mapVo;
	}
	
	// 返回用用于保存的默认值
		public Map<String, Object> defaultDetailValue() {

			Map<String, Object> mapDetailVo = new HashMap<String, Object>();

			mapDetailVo.put("group_id", "0");
			mapDetailVo.put("hos_id", "0");
			mapDetailVo.put("copy_code", "");
			mapDetailVo.put("tran_id", "0");
			mapDetailVo.put("inv_id", "0");
			mapDetailVo.put("inv_no", "0");
			mapDetailVo.put("batch_sn", "0");
			mapDetailVo.put("batch_no", "");
			mapDetailVo.put("amount", "");
			mapDetailVo.put("price", "0");
			mapDetailVo.put("amount_money", "");
			mapDetailVo.put("sale_price", "0");
			mapDetailVo.put("sale_money", "");
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
			mapDetailVo.put("location_out_id", "0");
			mapDetailVo.put("location_in_id", "0");
			mapDetailVo.put("note", "");

			return mapDetailVo;
		}
		
		public boolean validateJSON(String str) {

			if (str != null && !"null".equals(str) && !"".equals(str)) {

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
			
			matTranMainMapper.addBatch(entityList);

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}
		
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
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

			//状态判断
			if(matCommonService.existsStateBatch("mat_tran_main", "tran_id", entityMap.get("tran_id").toString(), "state", "1", null) > 0){
				return "{\"error\":\"更新失败,单据状态不是未审核状态！\",\"state\":\"false\"}";
			}
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			
			entityMap = defaultValue(entityMap);

			String tran_date = (String) entityMap.get("tran_date");

			//entityMap.put("year", tran_date.substring(0, 4));

			//entityMap.put("month", tran_date.substring(5, 7));
			
			entityMap.put("day", tran_date.substring(8, 10));
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("tran_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			int flag = 1;
			
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"修改失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			// (2)、调出判断库房是否已启用
			entityMap.put("store_id", entityMap.get("out_store_id"));
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，调出库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			// (3)、调入判断库房是否已启用
			entityMap.put("store_id", entityMap.get("in_store_id"));
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，调入库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			/*--------------需要同时修改对应的出入库单--begin----------------------------*/
			//注：院内调拨需要同时生成出入库单；院外调拨只生成出库单，入库单在调入单位调入确认时生成。
			boolean is_in = false;
			//获取调拨单与出入库单对应关系
			MatTranRela matTranRela = matTranRelaMapper.queryByCode(entityMap);
			if(matTranRela.getIn_id() != null && matTranRela.getIn_id() != 0){
				is_in = true;
			}
			//存放出库主从表
			Map<String, Object> outMap = new HashMap<String, Object>();
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			//存放入库主从表
			Map<String, Object> inMap = new HashMap<String, Object>();
			List<Map<String, Object>> inDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			String out_bus= "", in_bus = "";
			if("1".equals(String.valueOf(entityMap.get("tran_type")))){
				//院内调拨
				out_bus = "15";  //移出库
				in_bus = "14";  //移入库
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
			//修改出库单
			outMap.put("group_id", entityMap.get("group_id"));
			outMap.put("hos_id", entityMap.get("hos_id"));
			outMap.put("copy_code", entityMap.get("copy_code"));
			outMap.put("out_id", matTranRela.getOut_id());
			//出库的主表用的to_date函数转换的，所以这里直接用String型
			outMap.put("out_date", entityMap.get("tran_date"));
			outMap.put("year", entityMap.get("year").toString());
			outMap.put("month", entityMap.get("month").toString());
			outMap.put("store_id", entityMap.get("out_store_id"));
			outMap.put("store_no", entityMap.get("out_store_no"));
			outMap.put("bus_type_code", out_bus);
			
			if(is_in){
				//修改入库单
				inMap.put("group_id", entityMap.get("group_id"));
				inMap.put("hos_id", entityMap.get("hos_id"));
				inMap.put("copy_code", entityMap.get("copy_code"));
				inMap.put("in_id", matTranRela.getIn_id());
				inMap.put("in_date", DateUtil.stringToDate(String.valueOf(entityMap.get("tran_date")), "yyyy-MM-dd"));
				inMap.put("year", entityMap.get("year").toString());
				inMap.put("month", entityMap.get("month").toString());
				inMap.put("bus_type_code", in_bus);
				
				inMap.put("store_id", entityMap.get("in_store_id"));
				inMap.put("store_no", entityMap.get("in_store_no"));
			}
			/*--------------需要同时修改对应的出入库单--end------------------------------*/

			// 保存明细数据
			List<Map<String, Object>> tran_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray tran_detail_json = JSONArray.parseArray((String) entityMap.get("tran_detail_data"));
			//记录明细总金额
			double money_sum = 0;
			//存放库存不足的材料信息
			StringBuffer invEnoughMsg = new StringBuffer();
			Iterator tran_detail_it = tran_detail_json.iterator();
			while (tran_detail_it.hasNext()) {

				Map<String, Object> mapDetailVo = defaultDetailValue();

				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("tran_id", entityMap.get("tran_id").toString());
				mapDetailVo.put("tran_no", entityMap.get("tran_no").toString());
				mapDetailVo.put("store_id", entityMap.get("out_store_id").toString());//用于先进先出匹配材料
				
				/*if(matTranRela.getIn_id() != null && matTranRela.getIn_id() != 0){
					mapDetailVo.put("in_id", matTranRela.getIn_id().toString());
				}
				if(matTranRela.getOut_id() != null && matTranRela.getOut_id() != 0){
					mapDetailVo.put("out_id", matTranRela.getOut_id().toString());
				}*/
				
				JSONObject jsonObj = JSONObject.parseObject(tran_detail_it.next().toString());
				//没材料ID视为空行
				if(jsonObj.get("inv_id") != null && !"".equals(String.valueOf(jsonObj.get("inv_id")))){
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
	
					if (validateJSON(String.valueOf(jsonObj.get("batch_no")))) {mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("amount")))) {mapDetailVo.put("amount", jsonObj.get("amount").toString());}
					if (validateJSON(String.valueOf(jsonObj.get("price")))) {mapDetailVo.put("price", jsonObj.get("price").toString());}
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
					}
					if (validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));
					}
					if (validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("location_out_id")))) {
						mapDetailVo.put("location_out_id", jsonObj.get("location_out_id").toString());
					}else{
						mapDetailVo.put("location_out_id", "0");
					}
					mapDetailVo.put("location_id", mapDetailVo.get("location_out_id"));  //用于先进先出算法
					if (validateJSON(String.valueOf(jsonObj.get("location_in_id")))){
						mapDetailVo.put("location_in_id", jsonObj.get("location_in_id").toString());
					}else{
						mapDetailVo.put("location_in_id", "0");
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("note")))) {
						mapDetailVo.put("note", jsonObj.get("note").toString());
					}
					
					/*材料批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && validateJSON(String.valueOf(jsonObj.get("amount"))) && validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没材料ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								Map<String, Object> inDetailMap = new HashMap<String, Object>();
								Map<String, Object> outDetailMap = defaultOutDetailValue();
								detailMap.putAll(mapDetailVo);
								
								if (validateJSON(String.valueOf(m.get("tran_detail_id")))) {
									detailMap.put("tran_detail_id", m.get("tran_detail_id").toString());
								}else{
									detailMap.put("tran_detail_id", String.valueOf(matTranDetailMapper.queryMatTranDetailSeq()));
								}
								
								if (validateJSON(String.valueOf(m.get("batch_sn")))) {detailMap.put("batch_sn", m.get("batch_sn").toString());}
								if (validateJSON(String.valueOf(m.get("amount")))) {detailMap.put("amount", m.get("amount").toString());}
								if (validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
									//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
									money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
									
								}
								if (validateJSON(String.valueOf(m.get("sale_money")))) {detailMap.put("sale_money", m.get("sale_money").toString());}
								if (validateJSON(String.valueOf(m.get("sell_money")))) {detailMap.put("sell_money", m.get("sell_money").toString());}
								if (validateJSON(String.valueOf(m.get("allot_money")))) {detailMap.put("allot_money", m.get("allot_money").toString());}
								
								//tran_detail_batch.add(detailMap);
								//处理出入库明细
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								detailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", matTranRela.getOut_no().toString());
								outDetailMap.put("out_detail_id", matOutDetailMapper.queryMatOutDetailSeq());
								outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									detailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", matTranRela.getIn_no().toString());
									inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									inDetailList.add(inDetailMap);
								}
								tran_detail_batch.add(detailMap);
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							Map<String, Object> inDetailMap = new HashMap<String, Object>();
							Map<String, Object> outDetailMap = defaultOutDetailValue();
							detailMap.putAll(mapDetailVo);
							
							detailMap.put("tran_detail_id", String.valueOf(matTranDetailMapper.queryMatTranDetailSeq()));
							detailMap.put("batch_sn", map.get("batch_sn").toString());
							detailMap.put("in_id", inMap.get("in_id").toString());
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
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								
								//tran_detail_batch.add(detailMap);
								//处理出入库明细
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								detailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", matTranRela.getOut_no().toString());
								outDetailMap.put("out_detail_id", matOutDetailMapper.queryMatOutDetailSeq());
								outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									detailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", matTranRela.getIn_no().toString());
									inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq().toString());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									inDetailList.add(inDetailMap);
								}
								tran_detail_batch.add(detailMap);
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
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								
								//tran_detail_batch.add(detailMap);		
								//处理出入库明细
								outDetailMap.putAll(detailMap);
								detailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", matTranRela.getOut_no().toString());
								outDetailMap.put("out_detail_id", matOutDetailMapper.queryMatOutDetailSeq());
								outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									detailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", matTranRela.getIn_no().toString());
									inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq().toString());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									inDetailList.add(inDetailMap);
								}	
								tran_detail_batch.add(detailMap);	
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
					/*材料批次信息---------------end-----------------------*/
				}
			}
			if(tran_detail_batch.size() <= 0){
				return "{\"error\":\"调拨单明细数据为空!\"}";
			}
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足!\"}";
			}

			//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
			String invEnough = matCommonMapper.existsMatStockInvIsEnough(tran_detail_batch);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			}
			
			//修改调拨单主表
			entityMap.put("amount_money", money_sum);
			matTranMainMapper.update(entityMap);
			//修改调拨单明细表先清空再添加
			matTranDetailMapper.delete(entityMap);
			matTranDetailMapper.addBatch(tran_detail_batch);
			
			//修改出库单主表
			outMap.put("come_from", "5"); 
			outMap.put("amount_money", money_sum);
			matOutMainMapper.update(outMap);
			//修改出库单明细表先清空再添加
			matOutDetailMapper.delete(outMap);
			matOutDetailMapper.addBatch(outDetailList);
			//修改出库单资金来源
			outMap.put("source_money", money_sum);
			matOutResourceMapper.update(outMap);

			//修改入库单主表
			inMap.put("come_from", "5"); 
			inMap.put("amount_money", money_sum);
			matInCommonMapper.updateMatInMain(inMap);
			//修改入库单明细表先清空再添加
			matInCommonMapper.deleteMatInDetail(inMap);
			matInCommonMapper.addMatInDetailBatch(inDetailList);
			//修改入库单资金来源
			inMap.put("source_money", money_sum);
			matInCommonMapper.updateMatInResource(inMap);
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 update\"}";
		}	

		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
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
			
		  matTranMainMapper.updateBatch(entityList);

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}	
		
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
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

			//状态不是新建不能删除
			if(matCommonService.existsStateBatch("mat_tran_main", "tran_id", entityMap.get("tran_id").toString(), "state", "1", null) > 0){
				return "{\"error\":\"删除失败,单据状态不是未审核状态！\",\"state\":\"false\"}";
			}
			int state = matTranMainMapper.delete(entityMap);
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}	
	
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
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
			//状态不是新建不能删除
			if(matCommonService.existsStateBatch("mat_tran_main", "tran_id", null, "state", "1", entityList) > 0){
				return "{\"error\":\"删除失败,存在未审核单据请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			//删除申请单与出库单对应关系
			matApplyOutRelaMapper.deleteMatApplyOutRelaBatch(entityList);
			//获取调拨单与出入库单对应关系
			List<Map<String, Object>> relaList = toListMapLower(matTranRelaMapper.queryListForDelete(entityList));
			if(relaList.size() > 0){
				//删除已经生成的出库单
				matOutResourceMapper.deleteBatch(relaList);
				matOutDetailMapper.deleteBatch(relaList);
				matOutMainMapper.deleteBatch(relaList);
				
				//删除已经生成的入库单
				matInCommonMapper.deleteMatInResourceBatch(relaList);
				matInCommonMapper.deleteMatInDetailBatch(relaList);
				matInCommonMapper.deleteMatInMainBatch(relaList);
				
				//删除对应关系表
				matTranRelaMapper.deleteBatch(entityList);
			}
			//删除明细
			matTranDetailMapper.deleteBatch(entityList);
			//删除主表
			matTranMainMapper.deleteBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
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
		
		List<MatTranMain> list = (List<MatTranMain>)matTranMainMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = matTranMainMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matTranMainMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

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
			
			List<MatTranMain> list = (List<MatTranMain>)matTranMainMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatTranMain> list = (List<MatTranMain>)matTranMainMapper.query(entityMap, rowBounds);
			
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
		return matTranMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatTranMain
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matTranMainMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<MatTranMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return matTranMainMapper.queryExists(entityMap);
	}
	@Override
	public String queryMatTranMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)matTranMainMapper.queryMatTranMain(entityMap);
			
			return ChdJson.toJsonLower(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)matTranMainMapper.queryMatTranMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}
	}
	@Override
	public String queryMatInMainBySingle(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)matTranMainMapper.queryMatInMainBySingle(entityMap);
			
			return ChdJson.toJsonLower(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)matTranMainMapper.queryMatInMainBySingle(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}
	}
	@Override
	public String queryMatInDetailBySingle(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)matTranMainMapper.queryMatInDetailBySingle(entityMap);
			
			return ChdJson.toJsonLower(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)matTranMainMapper.queryMatInDetailBySingle(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}
	}

	/**
	 * @Description 组装整单调拨材料数据<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatTranDetailBySingle(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			return ChdJson.toJsonLower(matTranDetailMapper.queryMatTranDetailBySingle(entityMap));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"配套导入失败 数据库异常 请联系管理员! 方法 queryMatOutDetailByIsDir\"}";
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
	public String getNextTran_no(Map<String, Object> entityMap) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", entityMap.get("group_id"));

		map.put("hos_id", entityMap.get("hos_id"));

		map.put("copy_code", entityMap.get("copy_code"));

		map.put("store_id", entityMap.get("store_id"));

		// 获取仓库别名store_alias
		//String store_alias = matCommonMapper.queryStoreAliasById(map);

		map.put("table_code", "MAT_TRAN_MAIN");

		map.put("year", entityMap.get("year"));

		map.put("month", entityMap.get("month"));

		map.put("prefixe", "DB");

		map.put("store_alias", "");

		map.put("bus_type", "");

		// 判断是否存在该业务流水码
		int flag = matNoManageMapper.queryIsExists(map);

		String max_no = "";

		if (flag == 0) {// 如不存在则流水码为1，并插入流水码表中

			max_no = "1";

			map.put("max_no", 1);

			matNoManageMapper.add(map);

		} else {
			// 更新该业务流水码+1
			matNoManageMapper.updateMaxNo(map);
			// 取出该业务更新后的流水码
			max_no = matNoManageMapper.queryMaxCode(map);
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
	public String confirmOutMatTranMain(List<Map<String, Object>> entityMap) throws DataAccessException {

		List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();// 存放mat_out_main字段

		try {

			//状态判断
			if(matCommonService.existsStateBatch("mat_tran_main", "tran_id", null, "state", "1", entityMap) > 0){
				return "{\"error\":\"审核失败,存在非未审核单据请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			List<Map<String, Object>> listVo = entityMap;

			for (Map<String, Object> tmp : entityMap) {

				tmp.put("state", "2");

				mainList.add(tmp);

			}

			matTranMainMapper.updateAuditBatch(mainList);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"调出确认失败 数据库异常 请联系管理员! 方法 confirmOutMatTranMain\"}";
		}

		return "{\"msg\":\"调出确认成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 取消调出确认<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String unConfirmOutMatTranMain(List<Map<String, Object>> entityMap) throws DataAccessException {

		List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();// 存放mat_out_main字段

		try {

			//状态判断
			if(matCommonService.existsStateBatch("mat_tran_main", "tran_id", null, "state", "2", entityMap) > 0){
				return "{\"error\":\"消审失败,存在非审核单据请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			List<Map<String, Object>> listVo = entityMap;

			for (Map<String, Object> tmp : entityMap) {

				tmp.put("state", "1");

				mainList.add(tmp);
				System.out.append("tmp="+tmp);
			}
			
			matTranMainMapper.updateAuditBatch(mainList);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"取消调出确认失败 数据库异常 请联系管理员! 方法 unConfirmOutMatTranMain\"}";
		}

		return "{\"msg\":\"取消调出确认成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 调入确认<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public synchronized String confirmInMatTranMain(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			//校验出库单状态
			int flag = matTranMainMapper.existsMatTranStateForConfirm(entityList);
			if(flag != 0){
				return "{\"error\":\"所选单据中含调入确认单据，请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			
			//获取当前日期所在会计期间
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("group_id", SessionManager.getGroupId());
			checkMap.put("hos_id", SessionManager.getHosId());
			checkMap.put("copy_code", SessionManager.getCopyCode());
			checkMap.put("date", entityList.get(0).get("confirm_date"));
			String yearMonth = matCommonMapper.queryAccYearMonthByDate(checkMap);
			if(yearMonth == null || "".equals(yearMonth)){
				return "{\"error\":\"操作失败，对应期间不存在请配置！\",\"state\":\"false\"}";
			}
			String year = yearMonth.substring(0, 4);
			String month = yearMonth.substring(4, 6);
			
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
			for(Map<String, Object> tmp:entityList){
				
				Map<String,Object> mapMainTran = new HashMap<String,Object> ();
				Map<String,Object> mapMainOut = new HashMap<String,Object> ();
				Map<String,Object> mapMainIn = new HashMap<String,Object> ();
				
				tmp.put("year", year);
				tmp.put("month", month);
				mapMainTran.putAll(tmp);
				listUpdateMainTran.add(mapMainTran);
				
				//查询对应的出入库单据ID
				MatTranRela mtr = matTranRelaMapper.queryByCode(mapMainTran);
				mapMainOut.putAll(mapMainTran);
				mapMainOut.put("out_id", mtr.getOut_id());
				listUpdateMainOut.add(mapMainOut);
				mapMainIn.putAll(mapMainTran);
				mapMainIn.put("confirm_date", DateUtil.stringToDate(mapMainIn.get("confirm_date").toString(), "yyyy-MM-dd"));
				mapMainIn.put("in_id", mtr.getIn_id());
				listUpdateMainIn.add(mapMainIn);
			}
			
			StringBuffer invMsg = new StringBuffer();//存放库存不足的材料信息
			boolean is_enough = true;  //库存是否充足
			
			//获取所选单据中的所有出库材料
			List<MatTranDetail> outDetailList = matTranDetailMapper.queryMatTranDetailForOutConfirm(entityList);
			//获取所选单据中的所有入库材料
			List<MatTranDetail> inDetailList = matTranDetailMapper.queryMatTranDetailForInConfirm(entityList);
			//查询帐表所需
			List<Map<String, Object>> invList = new ArrayList<Map<String,Object>>();
			
			//存放明细数据用于判断出库
			Map<String,Map<String,Object>> mapOutFifoBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapOutBatchBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapOutInvHold = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapOutInvBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			//存放明细数据用于判断入库
			Map<String,Map<String,Object>> mapInFifoBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInBatchBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInInvHold = new HashMap<String,Map<String,Object>>();//存放明细数据
			Map<String,Map<String,Object>> mapInInvBalance = new HashMap<String,Map<String,Object>>();//存放明细数据
			
			//累计同仓库同材料的数量和金额
			Map<String,Double> sum_amount_map = new HashMap<String,Double>();
			Map<String,Double> sum_amount_money = new HashMap<String,Double>();		
			Map<String,Double> sum_sale_money = new HashMap<String,Double>();		
			
			StringBuffer storeMsg = new StringBuffer();//存放已结账库房
			
			/**出库数据-----------------------------------------begin---------------------------*/
			for(MatTranDetail mtd : outDetailList){
				/**如果确认日期按编制日期走则放开年月的重新赋值并改detailList相应的xml*/
				//year = mtd.getYear();
				//month = mtd.getMonth();
				//材料编码和名称用于库存不足的提示
				Map<String,Object> mapOutDetail = new HashMap<String,Object>();
				Map<String, Object> invOutMap = new HashMap<String, Object>();
				
				mapOutDetail.put("group_id", mtd.getGroup_id());
				mapOutDetail.put("hos_id", mtd.getHos_id());
				mapOutDetail.put("copy_code", mtd.getCopy_code());
				mapOutDetail.put("store_id", mtd.getOut_store_id());
				mapOutDetail.put("inv_id", mtd.getInv_id());
				mapOutDetail.put("inv_code", mtd.getInv_code());
				mapOutDetail.put("inv_name", mtd.getInv_name());
				mapOutDetail.put("batch_no", mtd.getBatch_no());
				mapOutDetail.put("batch_sn", mtd.getBatch_sn());
				mapOutDetail.put("bar_code", mtd.getBar_code());
				mapOutDetail.put("price", mtd.getPrice() == null ? 0 : mtd.getPrice());
				mapOutDetail.put("amount", mtd.getAmount() == null ? 0 : mtd.getAmount());
				mapOutDetail.put("amount_money", mtd.getAmount_money() == null ? 0 : mtd.getAmount_money());
				mapOutDetail.put("sale_price", mtd.getSale_price() == null ? 0 : mtd.getSale_price());
				mapOutDetail.put("sale_money", mtd.getSale_money() == null ? 0 : mtd.getSale_money());
				mapOutDetail.put("location_id", mtd.getLocation_out_id() == null ? 0 : mtd.getLocation_out_id());
				
				/*用于查询出库信息-------begin------*/
				invOutMap.put("group_id", mtd.getGroup_id());
				invOutMap.put("hos_id", mtd.getHos_id());
				invOutMap.put("copy_code", mtd.getCopy_code());
				invOutMap.put("year", year);
				invOutMap.put("month", month);
				invOutMap.put("store_id", mtd.getOut_store_id());
				invOutMap.put("inv_id", mtd.getInv_id());
				invOutMap.put("price", mtd.getPrice());
				invOutMap.put("batch_no", mtd.getBatch_no());
				invOutMap.put("batch_sn", mtd.getBatch_sn());
				invOutMap.put("bar_code", mtd.getBar_code());
				invOutMap.put("location_id", mtd.getLocation_out_id() == null ? 0 : mtd.getLocation_out_id());
				
				//判断库房是否已结账
				String store_flag = matCommonMapper.queryMatStoreIsAccount(invOutMap);
				if(store_flag != null && !"".equals(store_flag)){
					storeMsg.append(store_flag).append("<br/>");
					continue;
				}
				
				invList.add(invOutMap);
				/*用于查询出库信息-------end--------*/
				
				//Map中存放<各帐表主键, 具体材料信息的map>用于判断
				mapOutFifoBalance.put(mtd.getOut_store_id().toString()+mtd.getInv_id()+mtd.getPrice()+mtd.getBatch_no()+mtd.getBatch_sn()+mtd.getBar_code()+mtd.getLocation_out_id(), mapOutDetail);
				mapOutInvHold.put(mtd.getOut_store_id().toString()+mtd.getInv_id()+mtd.getLocation_out_id(), mapOutDetail);
				//下面这两张表是区分年月的
				mapOutDetail.put("year", year);
				mapOutDetail.put("month", month);
				mapOutBatchBalance.put(year+month+mtd.getOut_store_id()+mtd.getInv_id()+mtd.getPrice()+mtd.getBatch_no()+mtd.getBatch_sn()+mtd.getBar_code()+mtd.getLocation_out_id(), mapOutDetail);
				mapOutInvBalance.put(year+month+mtd.getOut_store_id()+mtd.getInv_id()+mtd.getLocation_out_id(), mapOutDetail);
				
				//根据材料ID累计数量
				if(sum_amount_map.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()) != null){
					sum_amount_map.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), NumberUtil.add(sum_amount_map.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()), mtd.getAmount()));
				}else{
					sum_amount_map.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(),mtd.getAmount());
				}
				//根据材料ID累计金额
				if(sum_amount_money.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()) != null){
					sum_amount_money.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), NumberUtil.add(sum_amount_money.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()), mtd.getAmount_money()));
				}else{
					sum_amount_money.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), mtd.getAmount_money());
				}
				//根据材料ID累计金额
				if(sum_sale_money.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()) != null){
					sum_sale_money.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), NumberUtil.add(sum_sale_money.get(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id()), mtd.getSale_money()));
				}else{
					sum_sale_money.put(mtd.getOut_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_out_id(), mtd.getSale_money());
				}
			}
			/**出库数据-----------------------------------------end-----------------------------*/
			
			/**入库数据-----------------------------------------begin---------------------------*/
			for(MatTranDetail mtd : inDetailList){
				/**如果确认日期按编制日期走则放开年月的重新赋值并改detailList相应的xml*/
				//year = mtd.getYear();
				//month = mtd.getMonth();
				//材料编码和名称用于库存不足的提示
				Map<String,Object> mapInDetail = new HashMap<String,Object>();
				Map<String, Object> invInMap = new HashMap<String, Object>();
				
				mapInDetail.put("group_id", mtd.getGroup_id());
				mapInDetail.put("hos_id", mtd.getHos_id());
				mapInDetail.put("copy_code", mtd.getCopy_code());
				mapInDetail.put("store_id", mtd.getIn_store_id());
				mapInDetail.put("inv_id", mtd.getInv_id());
				mapInDetail.put("inv_code", mtd.getInv_code());
				mapInDetail.put("inv_name", mtd.getInv_name());
				mapInDetail.put("batch_no", mtd.getBatch_no());
				mapInDetail.put("batch_sn", mtd.getBatch_sn());
				mapInDetail.put("bar_code", mtd.getBar_code());
				mapInDetail.put("price", mtd.getPrice() == null ? 0 : mtd.getPrice());
				mapInDetail.put("amount", mtd.getAmount() == null ? 0 : mtd.getAmount());
				mapInDetail.put("amount_money", mtd.getAmount_money() == null ? 0 : mtd.getAmount_money());
				mapInDetail.put("sale_price", mtd.getSale_price() == null ? 0 : mtd.getSale_price());
				mapInDetail.put("sale_money", mtd.getSale_money() == null ? 0 : mtd.getSale_money());
				mapInDetail.put("location_id", mtd.getLocation_in_id() == null ? 0 : mtd.getLocation_in_id());

				/*用于查询入库信息-------begin------*/
				invInMap.put("group_id", mtd.getGroup_id());
				invInMap.put("hos_id", mtd.getHos_id());
				invInMap.put("copy_code", mtd.getCopy_code());
				invInMap.put("year", year);
				invInMap.put("month", month);
				invInMap.put("store_id", mtd.getIn_store_id());
				invInMap.put("inv_id", mtd.getInv_id());
				invInMap.put("price", mtd.getPrice());
				invInMap.put("batch_no", mtd.getBatch_no());
				invInMap.put("batch_sn", mtd.getBatch_sn());
				invInMap.put("bar_code", mtd.getBar_code());
				invInMap.put("location_id", mtd.getLocation_in_id() == null ? 0 : mtd.getLocation_in_id());

				//判断库房是否已结账
				String store_flag = matCommonMapper.queryMatStoreIsAccount(invInMap);
				if(store_flag != null && !"".equals(store_flag)){
					storeMsg.append(store_flag).append("<br/>");
					continue;
				}
				
				invList.add(invInMap);
				/*用于查询入库信息-------end--------*/
				
				//Map中存放<各帐表主键, 具体材料信息的map>用于判断
				mapInFifoBalance.put(mtd.getIn_store_id().toString()+mtd.getInv_id()+mtd.getPrice()+mtd.getBatch_no()+mtd.getBatch_sn()+mtd.getBar_code()+mtd.getLocation_in_id(), mapInDetail);
				mapInInvHold.put(mtd.getIn_store_id().toString()+mtd.getInv_id()+mtd.getLocation_in_id(), mapInDetail);
				//下面这两张表是区分年月的
				mapInDetail.put("year", year);
				mapInDetail.put("month", month);
				mapInBatchBalance.put(year+month+mtd.getIn_store_id()+mtd.getInv_id()+mtd.getPrice()+mtd.getBatch_no()+mtd.getBatch_sn()+mtd.getBar_code()+mtd.getLocation_in_id(), mapInDetail);
				mapInInvBalance.put(year+month+mtd.getIn_store_id()+mtd.getInv_id()+mtd.getLocation_in_id(), mapInDetail);
				
				//根据材料ID累计数量
				if(sum_amount_map.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()) != null){
					sum_amount_map.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), NumberUtil.add(sum_amount_map.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()), mtd.getAmount()));
				}else{
					sum_amount_map.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(),mtd.getAmount());
				}
				//根据材料ID累计金额
				if(sum_amount_money.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()) != null){
					sum_amount_money.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), NumberUtil.add(sum_amount_money.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()), mtd.getAmount_money()));
				}else{
					sum_amount_money.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), mtd.getAmount_money());
				}
				//根据材料ID累计金额
				if(sum_sale_money.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()) != null){
					sum_sale_money.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), NumberUtil.add(sum_sale_money.get(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id()), mtd.getSale_money()));
				}else{
					sum_sale_money.put(mtd.getIn_store_id()+"-"+mtd.getInv_id()+"-"+mtd.getLocation_in_id(), mtd.getSale_money());
				}
			}
			/**入库数据-----------------------------------------end-----------------------------*/

			//存在已结账库房
			if(storeMsg != null && !"".equals(storeMsg.toString())){
				return "{\"error\":\"以下库房本期间已结账：<br/>"+storeMsg.toString()+"\"}";
			}
			
			//------------------------------查询账表 取出相应数据 并且组织账表数据 MAT_FIFO_BALANCE----------------------------------
			List<MatFifoBalance> listFifoBalance = (List<MatFifoBalance>)matFifoBalanceMapper.queryByInvList(invList);
			
			Map<String,MatFifoBalance> mfb_mb = new HashMap<String,MatFifoBalance>();//所有 根据条件 取出表（MAT_FIFO_BALANCE）的数据都放到map里面
			
			for(MatFifoBalance mfb : listFifoBalance){
				String key  = mfb.getStore_id().toString()+mfb.getInv_id()+mfb.getPrice()+mfb.getBatch_no()+mfb.getBatch_sn()+mfb.getBar_code()+mfb.getLocation_id();
				mfb_mb.put(key, mfb);
			}
			//出库判断
			for (String key : mapOutFifoBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
				
				Map<String,Object> mapBatch = new HashMap<String,Object>();
				if(mfb_mb.get(key) !=null){
					Map<String,Object> map = mapOutFifoBalance.get(key);
					mapBatch.putAll(map);
					
					MatFifoBalance mfb = (MatFifoBalance)mfb_mb.get(key);
					
					double amount = (Double)map.get("amount");
					double amount_money = (Double)map.get("amount_money");
					double sale_money = (Double)map.get("sale_money");
					/**判断实际库存是否充足-------------------begin--------------------------*/
					//所选单据材料合计数量 > 0 && 合计数量 > 帐表材料剩余数量 ? 剩余库存不足 : 剩余库存充足（用于出库判断）
					//所选单据材料合计数量 < 0 && (-1)合计数量 > 帐表材料剩余数量 ? 科室材料不足 : 科室材料充足（用于退库判断）
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
						//if(NumberUtil.sub(mfb.getLeft_amount(), amount) == 0){
							//mapBatch.put("remove_zero_error", NumberUtil.sub(mfb.getIn_money(), (Double)mapBatch.get("out_money")));//拆零误差 等于入库金额 - 出库金额
							//mapBatch.put("sale_zero_error", NumberUtil.sub(mfb.getIn_sale_money(), (Double)mapBatch.get("out_sale_money")));//批发拆零误差 等于入库批发金额 - 出库批发金额
						//}
							
						listUpdateFifoBalance.add(mapBatch);
					}
				}else{
					//帐表中没有该材料信息，提示库存不足！
					invMsg.append(mapOutFifoBalance.get(key).get("inv_code")).append(" ").append(mapOutFifoBalance.get(key).get("inv_name")).append(",");
					if(is_enough){
						is_enough = false;
					}
				}
			}
			//如果材料出现库存物资不足的就不需要组装、判断另外几个帐表了
			if(is_enough){
				//入库判断
				for (String key : mapInFifoBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					
					Map<String,Object> map = mapInFifoBalance.get(key);
					mapBatch.putAll(map);
					
					double amount = (Double)map.get("amount");
					double amount_money = (Double)map.get("amount_money");
					double sale_money = (Double)map.get("sale_money");
					
					if(mfb_mb.get(key) !=null){
						MatFifoBalance mfb = (MatFifoBalance)mfb_mb.get(key);
						mapBatch.put("in_amount", NumberUtil.add(amount, mfb.getIn_amount()));//入库数量 等于入库的数量 + 当前入库的数量
						mapBatch.put("in_money", NumberUtil.add(amount_money, mfb.getIn_money()));//入库金额 等于入库的金额 + 当前入库的金额
						mapBatch.put("in_sale_money", NumberUtil.add(sale_money, mfb.getIn_sale_money()));//入库批发金额 等于入库批发金额 + 当前入库批发金额
						mapBatch.put("left_amount", NumberUtil.add(mfb.getLeft_amount(), amount));//剩余数量 等于剩余的数量 + 当前入库的数量
						mapBatch.put("left_money", NumberUtil.add(mfb.getLeft_money(), amount_money));//剩余金额 等于剩余金额 + 当前入库金额
						mapBatch.put("left_sale_money", NumberUtil.add(mfb.getLeft_sale_money(), sale_money));//剩余数量 等于剩余数量 + 当前入库数量
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
			
				//------------------------------查询账表 取出相应数据 并且组织账表数据MAT_BATCH_BALANCE----------------------------------
				List<MatBatchBalance> listBatchBalance = (List<MatBatchBalance>)matBatchBalanceMapper.queryByInvList(invList);

				Map<String,MatBatchBalance> mbb_mb = new HashMap<String,MatBatchBalance>();//所有 根据条件 取出表（MAT_BATCH_BALANCE）的数据都放到map里面
				
				for(MatBatchBalance mbb : listBatchBalance){
					String key = mbb.getYear().toString()+mbb.getMonth()+mbb.getStore_id()+mbb.getInv_id()+mbb.getPrice()+mbb.getBatch_no()+mbb.getBatch_sn()+mbb.getBar_code()+mbb.getLocation_id();
					mbb_mb.put(key, mbb);
				}
				
				//出库判断
				for (String key : mapOutBatchBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					Map<String,Object> mapBatch = new HashMap<String,Object>();

					Map<String,Object> map = mapOutBatchBalance.get(key);
					mapBatch.putAll(map);
					
					double amount = (Double)map.get("amount");
					double amount_money = (Double)map.get("amount_money");
					double sale_money = (Double)map.get("sale_money");
					
					if(mbb_mb.get(key) !=null){
						MatBatchBalance mbb = (MatBatchBalance)mbb_mb.get(key);
						mapBatch.put("out_amount", NumberUtil.add(amount, mbb.getOut_amount()));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add(amount_money, mbb.getOut_money()));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add(sale_money, mbb.getOut_sale_money()));//出库批发金额 等于出库的批发金额 + 当前出库的批发金额
						mapBatch.put("y_out_amount", NumberUtil.add(amount, mbb.getOut_amount()));//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("y_out_money", NumberUtil.add(amount_money, mbb.getOut_money()));//剩余数量 等于剩余的金额 - 当前出库的金额
						mapBatch.put("y_out_sale_money", NumberUtil.add(sale_money, mbb.getOut_sale_money()));//剩余批发金额 等于剩余的批发金额 - 当前出库的批发金额
						//以下字段的计算方式有待讨论
						//if(NumberUtil.sub(mbb.getIn_amount(), (Double)mapBatch.get("out_amount")) == 0){
							//mapBatch.put("remove_zero_error", NumberUtil.sub(mbb.getIn_money(), (Double)mapBatch.get("out_money")));//拆零误差 等于入库金额 - 出库金额
							//mapBatch.put("sale_zero_error", NumberUtil.sub(mbb.getIn_sale_money(), (Double)mapBatch.get("out_sale_money")));//批发拆零误差 等于入库批发金额 - 出库批发金额
						//}
							
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
				//入库判断
				for (String key : mapInBatchBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					Map<String,Object> mapBatch = new HashMap<String,Object>();

					Map<String,Object> map = mapInBatchBalance.get(key);
					mapBatch.putAll(map);
					
					double amount = (Double)map.get("amount");
					double amount_money = (Double)map.get("amount_money");
					double sale_money = (Double)map.get("sale_money");
					
					if(mbb_mb.get(key) !=null){
						MatBatchBalance mbb = (MatBatchBalance)mbb_mb.get(key);
						mapBatch.put("in_amount", NumberUtil.add(amount, mbb.getIn_amount()));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("in_money", NumberUtil.add(amount_money, mbb.getIn_money()));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("in_sale_money", NumberUtil.add(sale_money, mbb.getIn_sale_money()));//出库批发金额 等于出库的批发金额 + 当前出库的批发金额
						mapBatch.put("y_in_amount", NumberUtil.add(amount, mbb.getIn_amount()));//剩余数量 等于剩余的数量 - 当前出库的数量
						mapBatch.put("y_in_money", NumberUtil.add(amount_money, mbb.getIn_money()));//剩余数量 等于剩余的金额 - 当前出库的金额
						mapBatch.put("y_in_sale_money", NumberUtil.add(sale_money, mbb.getIn_sale_money()));//剩余批发金额 等于剩余的批发金额 - 当前出库的批发金额
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);
						mapBatch.put("sale_zero_error", 0);
						
						listUpdateBatchBalance.add(mapBatch);
					}else{
						mapBatch.put("end_amount", 0);
						mapBatch.put("end_money", 0);
						mapBatch.put("end_sale_money", 0);
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
				
				//------------------------------查询账表 取出相应数据 并且组织账表数据MAT_INV_HOLD----------------------------------
				List<MatInvHold> listInvHold = (List<MatInvHold>)matInvHoldMapper.queryByInvList(invList);

				Map<String,MatInvHold> mih_mb = new HashMap<String,MatInvHold>();//所有 根据条件 取出表（MAT_INV_HOLD）的数据都放到map里面
				
				for(MatInvHold mih : listInvHold){
					String key = mih.getStore_id().toString()+mih.getInv_id()+mih.getLocation_id();
					mih_mb.put(key, mih);
				}
				//出库判断
				for (String key : mapOutInvHold.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					
					if(mih_mb.get(key) !=null){
						Map<String,Object> map = mapOutInvHold.get(key);
						mapBatch.putAll(map);
						
						MatInvHold mih = (MatInvHold)mih_mb.get(key);
						
						mapBatch.put("cur_amount", NumberUtil.sub(mih.getCur_amount(), (Double)sum_amount_map.get(mih.getStore_id()+"-"+mih.getInv_id()+"-"+mih.getLocation_id())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("cur_money", NumberUtil.sub(mih.getCur_money(), (Double)sum_amount_money.get(mih.getStore_id()+"-"+mih.getInv_id()+"-"+mih.getLocation_id())));//出库金额 等于出库的金额 + 当前出库的金额
						
						listUpdateInvHold.add(mapBatch);
					}
				}
				//入库判断
				for (String key : mapInInvHold.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					Map<String,Object> map = mapInInvHold.get(key);
					mapBatch.putAll(map);
					
					if(mih_mb.get(key) !=null){
						
						MatInvHold mih = (MatInvHold)mih_mb.get(key);
						
						mapBatch.put("cur_amount", NumberUtil.add(mih.getCur_amount(), (Double)sum_amount_map.get(mih.getStore_id()+"-"+mih.getInv_id()+"-"+mih.getLocation_id())));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("cur_money", NumberUtil.add(mih.getCur_money(), (Double)sum_amount_money.get(mih.getStore_id()+"-"+mih.getInv_id()+"-"+mih.getLocation_id())));//出库金额 等于出库的金额 + 当前出库的金额
						
						listUpdateInvHold.add(mapBatch);
					}else{
						mapBatch.put("cur_amount", (Double)sum_amount_map.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("cur_money", (Double)sum_amount_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));//出库金额 等于出库的金额 + 当前出库的金额
						
						listAddInvHold.add(mapBatch);
					}
				}

				//------------------------------查询账表 取出相应数据 并且组织账表数据MAT_INV_Batch----------------------------------
				List<MatInvBalance> listInvBalance = (List<MatInvBalance>)matInvBalanceMapper.queryByInvList(invList);
				
				Map<String,MatInvBalance> mib_mb = new HashMap<String,MatInvBalance>();//所有 根据条件 取出表（MAT_INV_HOLD）的数据都放到map里面
				for(MatInvBalance mib : listInvBalance){
					String key = mib.getYear().toString()+mib.getMonth()+mib.getStore_id()+mib.getInv_id()+mib.getLocation_id();
					mib_mb.put(key, mib);
				}
				
				//出库判断
				for (String key : mapOutInvBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					Map<String,Object> map = mapOutInvBalance.get(key);
					
					mapBatch.putAll(map);
					
					if(mib_mb.get(key) !=null){
						MatInvBalance mib = (MatInvBalance)mib_mb.get(key);
						mapBatch.put("out_amount", NumberUtil.add((Double)sum_amount_map.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_amount()));//出库数量 等于出库的数量 + 当前出库的数量
						mapBatch.put("out_money", NumberUtil.add((Double)sum_amount_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_money()));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("out_sale_money", NumberUtil.add((Double)sum_sale_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_sale_money()));//出库金额 等于出库的金额 + 当前出库的金额
						mapBatch.put("y_out_amount", NumberUtil.add((Double)sum_amount_map.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_amount()));//累计数量 等于累计的数量 + 当前出库的数量
						mapBatch.put("y_out_money", NumberUtil.add((Double)sum_amount_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_money()));//累计数量 等于累计的金额 + 当前出库的金额
						mapBatch.put("y_out_sale_money", NumberUtil.add((Double)sum_sale_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getOut_sale_money()));//累计数量 等于累计的金额 + 当前出库的金额
						//以下字段的计算方式有待讨论
						//if(NumberUtil.sub(mib.getIn_amount(), (Double)mapBatch.get("out_amount")) == 0){
						//	mapBatch.put("remove_zero_error", NumberUtil.sub(mib.getIn_money(), (Double)mapBatch.get("out_money")));//拆零误差 等于入库金额 - 出库金额
						//	mapBatch.put("sale_zero_error", NumberUtil.sub(mib.getIn_sale_money(), (Double)mapBatch.get("out_sale_money")));//批发拆零误差 等于入库批发金额 - 出库批发金额
						//}
							
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
						mapBatch.put("out_amount", (Double)sum_amount_map.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("out_money",  (Double)sum_amount_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("out_sale_money",  (Double)sum_sale_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("y_in_amount", 0);
						mapBatch.put("y_in_money", 0);
						mapBatch.put("y_in_sale_money", 0);
						mapBatch.put("y_out_amount", (Double)sum_amount_map.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("y_out_money",  (Double)sum_amount_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("y_out_sale_money",  (Double)sum_sale_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);//拆零误差 等于入库金额 - 出库金额
						mapBatch.put("sale_zero_error", 0);//批发拆零误差 等于入库批发金额 - 出库批发金额
						
						mapBatch.put("location_id", mapBatch.get("location_id") == null ? 0 : mapBatch.get("location_id"));
						
						listAddInvBalance.add(mapBatch);
					}
				}
				//入库判断
				for (String key : mapInInvBalance.keySet()) {//循环当前材料的明细 key查询条件 取相应的值
					
					Map<String,Object> mapBatch = new HashMap<String,Object>();
					Map<String,Object> map = mapInInvBalance.get(key);
					
					mapBatch.putAll(map);
					
					if(mib_mb.get(key) !=null){
						MatInvBalance mib = (MatInvBalance)mib_mb.get(key);
						mapBatch.put("in_amount", NumberUtil.add((Double)sum_amount_map.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getIn_amount()));//入库数量 等于入库的数量 + 当前入库的数量
						mapBatch.put("in_money", NumberUtil.add((Double)sum_amount_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getIn_money()));//入库金额 等于入库的金额 + 当前入库的金额
						mapBatch.put("in_sale_money", NumberUtil.add((Double)sum_sale_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getIn_sale_money()));//入库金额 等于入库的金额 + 当前入库的金额
						mapBatch.put("y_in_amount", NumberUtil.add((Double)sum_amount_map.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getIn_amount()));//累计数量 等于累计的数量 + 当前入库的数量
						mapBatch.put("y_in_money", NumberUtil.add((Double)sum_amount_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getIn_money()));//累计数量 等于累计的金额 + 当前入库的金额
						mapBatch.put("y_in_sale_money", NumberUtil.add((Double)sum_sale_money.get(mib.getStore_id()+"-"+mib.getInv_id()+"-"+mib.getLocation_id()), mib.getIn_sale_money()));//累计金额 等于累计的金额 - 当前入库的金额
						//以下字段的计算方式有待讨论
						mapBatch.put("remove_zero_error", 0);//拆零误差 等于入库金额 - 出库金额
						mapBatch.put("sale_zero_error", 0);//批发拆零误差 等于入库批发金额 - 出库批发金额
						
						listUpdateInvBalance.add(mapBatch);
					}else{
						mapBatch.put("end_amount", 0);
						mapBatch.put("end_money", 0);
						mapBatch.put("end_sale_money", 0);
						mapBatch.put("begin_amount", 0);
						mapBatch.put("begin_money", 0);
						mapBatch.put("begin_sale_money", 0);
						mapBatch.put("in_amount", (Double)sum_amount_map.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("in_money", (Double)sum_amount_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("in_sale_money", (Double)sum_sale_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("out_amount", 0);
						mapBatch.put("out_money",  0);
						mapBatch.put("out_sale_money",  0);
						mapBatch.put("y_in_amount", (Double)sum_amount_map.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("y_in_money", (Double)sum_amount_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
						mapBatch.put("y_in_sale_money", (Double)sum_sale_money.get(map.get("store_id").toString()+"-"+map.get("inv_id").toString()+"-"+map.get("location_id").toString()));
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
				//材料库存不足提示
				return "{\"error\":\""+invMsg.substring(0, invMsg.length()-1).toString()+"库存物资不足!\"}";
			}
			
			/***********处理单据中的耐用品****begin*****************/
	/*		checkMap.put("year", year);
			checkMap.put("month", month);
			checkMap.put("mod_code", "0413");  //耐用品管理子系统
			int start_dura = matCommonMapper.existsMatModStartByYearMonth(checkMap);  //是否启用耐用品
			if(start_dura > 0){
				List<Map<String, Object>> duraMainList = new ArrayList<Map<String,Object>>();
				//耐用品明细表数据
				List<Map<String, Object>> duraDetailList = new ArrayList<Map<String,Object>>();
				
				//获取所选单据中的所有材料
				List<Map<String, Object>> duraList = JsonListMapUtil.toListMapLower(matTranMainMapper.queryMatTranMainForDura(entityList));
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
						dMainMap.put("bus_type_code", "45");  //耐用品调拨入库45
						dMainMap.put("out_store_id", map.get("out_store_id"));
						dMainMap.put("out_store_no", map.get("out_store_no"));
						dMainMap.put("in_store_id", map.get("in_store_id"));
						dMainMap.put("in_store_no", map.get("in_store_no"));
						dMainMap.put("maker", SessionManager.getUserId());
						dMainMap.put("make_date", new Date());
						dMainMap.put("state", 3);
						dMainMap.put("brief", "由材料调拨业务自动生成");
						dMainMap.put("checker", SessionManager.getUserId());
						dMainMap.put("check_date", new Date());
						dMainMap.put("confirmer", SessionManager.getUserId());
						dMainMap.put("confirm_date", new Date());
						//自增序列
						dMainMap.put("dura_id", matTranMainMapper.queryMatTranMainSeq());
						//转移单号
						dMainMap.put("table_code", "MAT_DURA_STORE_STORE");
						dMainMap.put("length_no", "5");  //流水码5位
						dMainMap.put("containsDay", "0");  //单据号不按day生成
						dMainMap.put("dura_no", matCommonService.getMatNextNo(dMainMap));
						//记录主表
						duraMainMap.put(duraKey, dMainMap);
					}else{
						
						dMainMap = duraMainMap.get(duraKey);
					}
					
				
				
			}
			*/
			
			/***********处理单据中的耐用品****end*******************/
				
				
				
				
			//添加/更新mat_fifo_balance帐表
			if(listAddFifoBalance.size() >0 || listUpdateFifoBalance.size() >0){
				if(listAddFifoBalance.size()>0){
					matFifoBalanceMapper.addBatch(listAddFifoBalance);
				}
				if(listUpdateFifoBalance.size()>0){
					matFifoBalanceMapper.updateBatch(listUpdateFifoBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 mat_fifo_balance帐表无数据变动\"}");
			}
			
			//添加/更新mat_batch_balance帐表
			if(listAddBatchBalance.size() >0 || listUpdateBatchBalance.size() >0){
				if(listAddBatchBalance.size() >0){
					matBatchBalanceMapper.addBatch(listAddBatchBalance);
				}
				if(listUpdateBatchBalance.size() >0){
					matBatchBalanceMapper.updateBatch(listUpdateBatchBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 mat_batch_balance帐表无数据变动\"}");
			}
			
			//添加/更新mat_inv_balance帐表
			if(listAddInvBalance.size() >0 || listUpdateInvBalance.size() >0){
				if(listAddInvBalance.size()>0){
					matInvBalanceMapper.addBatch(listAddInvBalance);
				}
				if(listUpdateInvBalance.size() >0){
					matInvBalanceMapper.updateBatch(listUpdateInvBalance);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 mat_inv_balance帐表无数据变动\"}");
			}
			
			//添加/更新mat_inv_hold帐表
			if(listAddInvHold.size() >0 || listUpdateInvHold.size() >0){
				if(listAddInvHold.size() >0){
					matInvHoldMapper.addBatch(listAddInvHold);
				}
				if(listUpdateInvHold.size() >0){
					matInvHoldMapper.updateBatch(listUpdateInvHold);
				}
			}else{
				throw new SysException("{\"error\":\"操作失败 mat_inv_hold帐表无数据变动\"}");
			}
			
			//修改调拨单状态为调入确认
			matTranMainMapper.updateConfirmBatch(listUpdateMainTran);
			//修改出库单状态为出库确认
			matOutMainMapper.updateConfirmBatch(listUpdateMainOut);
			//修改入库单状态为入库确认
			matInCommonMapper.updateMatInMainConfirm(listUpdateMainIn);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"调入确认失败 数据库异常 请联系管理员! 方法 confirmInMatTranMain\"}";
		}
		
		return "{\"msg\":\"调入确认成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 冲账<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String offsetMatTranMain(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> mainList =new ArrayList<Map<String, Object>>();//存放mat_tran_main字段
		List<Map<String, Object>> detailList =new ArrayList<Map<String, Object>>();//存放mat_tran_detail字段
		
		List<Map<String, Object>> outMainList =new ArrayList<Map<String, Object>>();//存放mat_out_main字段
		List<Map<String, Object>> outDetailList =new ArrayList<Map<String, Object>>();//存放mat_in_detail字段
		
		List<Map<String, Object>> inMainList =new ArrayList<Map<String, Object>>();//存放mat_out_main字段
		List<Map<String, Object>> inDetailList =new ArrayList<Map<String, Object>>();//存放mat_in_detail字段
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
		double money_sum;
		try {

			List<Map<String, Object>> listVo = entityMap;

			for (Map<String, Object> tmp : entityMap) {
				
				money_sum = 0;
				MatTranMain mtm = (MatTranMain) matTranMainMapper.queryByCode(tmp);
				Map<String, Object> mainMap = defaultBeanToMap(mtm);
				mainMap.put("brief", "冲调拨单"+mainMap.get("tran_no"));
				Long tran_id = matTranMainMapper.queryMatTranMainSeq();// 查询序列
				mainMap.put("tran_id", tran_id);
				mainMap.put("tran_no", getNextTran_no(mainMap));// 获取出库单号
				mainMap.put("state", "1");
				mainMap.put("tran_date", tran_date);
				mainMap.put("year", tran_date.substring(0, 4));
				mainMap.put("month", tran_date.substring(5, 7));
				mainMap.put("day", tran_date.substring(8, 10));  //用于生成单号
				mainMap.put("maker", maker);
				mainMap.put("checker", "");
				mainMap.put("check_date", "");
				mainMap.put("confirmer", "");
				mainMap.put("confirm_date", "");
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
					out_bus = "15";  //移出库
					in_bus = "14";  //移入库
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
				outMap.put("group_id", mainMap.get("group_id"));
				outMap.put("hos_id", mainMap.get("hos_id"));
				outMap.put("copy_code", mainMap.get("copy_code"));
				outMap.put("year", mainMap.get("year"));
				outMap.put("month", mainMap.get("month"));
				outMap.put("day", mainMap.get("day"));  //用于生成单号
				outMap.put("bus_type_code", out_bus);
				outMap.put("state", 1);
				outMap.put("brief", brief);
				//出库的主表用的to_date函数转换的，所以这里直接用String型
				outMap.put("maker", SessionManager.getUserId());
				outMap.put("out_date", mainMap.get("tran_date"));
				outMap.put("store_id", out_store_id);
				outMap.put("store_no", out_store_no);
				outMap.put("table_code", "MAT_OUT_MAIN");
				outMap.put("prefixe", "");
				long out_id = matOutMainMapper.queryMatOutMainSeq();
				outMap.put("out_id", out_id);
				String out_no = matCommonService.getMatNextNo(outMap);
				if(out_no.indexOf("error")!=-1){
					return out_no;
				}
				outMap.put("out_no", out_no);
				
				//生成入库单
				inMap.put("group_id", mainMap.get("group_id"));
				inMap.put("hos_id", mainMap.get("hos_id"));
				inMap.put("copy_code", mainMap.get("copy_code"));
				inMap.put("year", mainMap.get("year"));
				inMap.put("month", mainMap.get("month"));
				inMap.put("day", mainMap.get("day"));  //用于生成单号
				inMap.put("bus_type_code", in_bus);
				inMap.put("state", 1);
				inMap.put("brief", brief);
				inMap.put("maker", SessionManager.getUserId());
				inMap.put("in_date", DateUtil.stringToDate(mainMap.get("tran_date").toString(), "yyyy-MM-dd"));
				inMap.put("bus_type_code", 14);//移入库
				inMap.put("store_id", in_store_id);
				inMap.put("store_no", in_store_no);
				inMap.put("table_code", "MAT_IN_MAIN");
				inMap.put("prefixe", "");
				inMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
				String in_no = matCommonService.getMatNextNo(inMap);
				if(in_no.indexOf("error")!=-1){
					return in_no;
				}
				inMap.put("in_no", in_no);
				
				//处理与出入库单的对应关系
				mainMap.put("in_id", inMap.get("in_id"));
				mainMap.put("in_no", inMap.get("in_no"));
				mainMap.put("out_id", outMap.get("out_id"));
				mainMap.put("out_no", outMap.get("out_no"));
				
					
				/*--------------需要同时生成出入库单--end------------------------------*/

				//查询明细表
				List<MatTranDetail> list_detail = (List<MatTranDetail>)matTranDetailMapper.query(tmp);
				
				for(MatTranDetail mtd:list_detail){
					
					Map<String, Object> detailMap = detailBeanToMap(mtd);
					Map<String, Object> outDetailMap = new HashMap<String, Object>();
					Map<String, Object> inDetailMap = new HashMap<String, Object>();

					detailMap.put("tran_id", tran_id);
					detailMap.put("tran_no", mainMap.get("tran_no"));
					detailMap.put("store_id", out_store_id);
					detailMap.put("tran_detail_id", matTranDetailMapper.queryMatTranDetailSeq());
					
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
						detailMap.put("inva_date", DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
					}else{
						detailMap.put("inva_date",  null);
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
						detailMap.put("disinfect_date", DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}else{
						detailMap.put("disinfect_date",  null);
					}
					
					detailList.add(detailMap);
					
					/*处理出入库明细---------begin----------------*/
					if(mtd.getAmount_money() != null){
						//money_sum = money_sum + mtd.getAmount_money();
						money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(mtd.getAmount_money().toString()));//记录总金额
						
					}
					//出入明细
					outDetailMap.putAll(detailMap);
					outDetailMap.put("out_id", outMap.get("out_id"));
					outDetailMap.put("out_no", outMap.get("out_no"));
					outDetailMap.put("out_detail_id", matOutDetailMapper.queryMatOutDetailSeq());
					outDetailMap.put("location_id", detailMap.get("location_out_id"));
					outDetailList.add(outDetailMap);
					
					//入库明细
					inDetailMap.putAll(detailMap);
					inDetailMap.put("in_id", inMap.get("in_id"));
					inDetailMap.put("in_no", inMap.get("in_no"));
					inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
					/*if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
						inDetailMap.put("inva_date", DateUtil.stringToDate(detailMap.get("inva_date").toString(), "yyyy-MM-dd"));
					}else{
						inDetailMap.put("inva_date",  null);
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
						inDetailMap.put("disinfect_date", DateUtil.stringToDate(detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}else{
						inDetailMap.put("disinfect_date",  null);
					}*/
					inDetailMap.put("location_id", detailMap.get("location_in_id"));
					inDetailList.add(inDetailMap);
					/*处理出入库明细---------end------------------*/
				}
				//明细金额
				mainMap.put("amount_money", money_sum);
				inMap.put("amount_money", money_sum);
				outMap.put("amount_money", money_sum);
				inMap.put("come_from", "5"); 
				outMap.put("come_from", "5"); 
				//处理资金来源信息
				inMap.put("source_money", money_sum);
				outMap.put("source_money", money_sum);

				mainList.add(mainMap);
				inMainList.add(inMap);
				outMainList.add(defaultOutValue(outMap));
			}

			//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
			String invEnough = matCommonMapper.existsMatStockInvIsEnough(outDetailList);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			}
			
			//调拨主表
			matTranMainMapper.addBatch(mainList);
			//调拨明细表
			matTranDetailMapper.addBatch(detailList);
			//出库单主表
			matOutMainMapper.addBatch(outMainList);
			//出库单明细表
			matOutDetailMapper.addBatch(outDetailList);
			//出库单资金来源表
			matOutResourceMapper.addBatch(outMainList);
			//入库单主表
			matInCommonMapper.addMatInMainBatch(inMainList);
			//入库单明细表
			matInCommonMapper.addMatInDetailBatch(inDetailList);
			//入库单资金来源表
			matInCommonMapper.insertMatInResourceBatch(inMainList);
			//调拨与出入库对应关系表
			matTranRelaMapper.addBatch(mainList);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"冲账失败 数据库异常 请联系管理员! 方法 offsetMatTranMain\"}";
		}

		return "{\"msg\":\"冲账成功.\",\"state\":\"true\"}";
	}
	
	public Map<String, Object> defaultBeanToMap(MatTranMain mtm) {
		Map<String, Object> map = new HashMap<String, Object>();
		
			map.put("group_id", mtm.getGroup_id());
			map.put("hos_id", mtm.getHos_id());
			map.put("copy_code", mtm.getCopy_code());
			map.put("tran_id", mtm.getTran_id());
			map.put("tran_no", mtm.getTran_no());
			map.put("year", mtm.getYear());
			map.put("month", mtm.getMonth());
			map.put("tran_type", mtm.getTran_type());
			map.put("bus_type", mtm.getBus_type());
			map.put("tran_method", mtm.getTran_method());
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
	public Map<String, Object> detailBeanToMap(MatTranDetail mtd) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("group_id", mtd.getGroup_id());
		map.put("hos_id", mtd.getHos_id());
		map.put("copy_code", mtd.getCopy_code());
		map.put("tran_id", mtd.getTran_id());
		map.put("inv_id", mtd.getInv_id());
		map.put("inv_no", mtd.getInv_no());
		map.put("batch_sn", mtd.getBatch_sn() != null ? mtd.getBatch_sn() : "");
		map.put("batch_no", mtd.getBatch_no() != null ? mtd.getBatch_no() : "");
		map.put("amount", mtd.getAmount() != null ? mtd.getAmount() : "0");
		map.put("price", mtd.getPrice() != null ? mtd.getPrice() : "0");
		map.put("amount_money", mtd.getAmount_money() != null ? mtd.getAmount_money() : "0");
		map.put("sale_price", mtd.getSale_price() != null ? mtd.getSale_price() : "0");
		map.put("sale_money", mtd.getSale_money() != null ? mtd.getSale_money() : "0");
		map.put("sell_price", mtd.getSell_price() != null ? mtd.getSell_price() : "0");
		map.put("sell_money", mtd.getSell_money() != null ? mtd.getSell_money() : "0");
		map.put("allot_price", mtd.getAllot_price() != null ? mtd.getAllot_price() : "0");
		map.put("allot_money", mtd.getAllot_money() != null ? mtd.getAllot_money() : "0");
		map.put("pack_code", mtd.getPack_code() != null ? mtd.getPack_code() : "");
		map.put("num_exchange", mtd.getNum_exchange() != null ? mtd.getNum_exchange() : "");
		map.put("pack_price", mtd.getPack_price() != null ? mtd.getPack_price() : "");
		map.put("num", mtd.getNum() != null ? mtd.getNum() : "");
		map.put("bar_code", mtd.getBar_code() != null ? mtd.getBar_code() : "");
		map.put("inva_date", mtd.getInva_date() != null ? DateUtil.dateToString(mtd.getInva_date(), "yyyy-MM-dd") : "");
		map.put("disinfect_date", mtd.getDisinfect_date() != null ? DateUtil.dateToString(mtd.getDisinfect_date(), "yyyy-MM-dd") : "");
		map.put("location_out_id", mtd.getLocation_out_id() != null ? mtd.getLocation_out_id() : "0");
		map.put("location_in_id", mtd.getLocation_in_id() != null ? mtd.getLocation_in_id() : "0");
		map.put("note", mtd.getNote() != null ? mtd.getNote() : "");
		
		return map;
	}

	@Override
	public String queryMatTranMainByMatInv(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)matTranMainMapper.queryMatTranMainByMatInv(entityMap);
			
			return ChdJson.toJsonLower(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)matTranMainMapper.queryMatTranMainByMatInv(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}
	}

	@Override
	public MatTranMain queryMatTranMainByCode(Map<String, Object> entityMap) throws DataAccessException {
		return matTranMainMapper.queryMatTranMainByCode(entityMap);
	}

	@Override
	public String queryMatTranDetailByTranID(Map<String, Object> entityMap) throws DataAccessException {
			
		List<Map<String,Object>> list = (List<Map<String,Object>>)matTranDetailMapper.queryMatTranDetailByTranID(entityMap);
			
		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryMatTranDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
			
		List<Map<String,Object>> list = (List<Map<String,Object>>)matTranDetailMapper.queryMatTranDetailByCode(entityMap);
			
		return matCommonService.getDetailJsonByDetailList(JsonListMapUtil.toListMapLower(list));
	}
	
	public Map<String, Object> defaultOutValue(Map<String, Object> mapVo) {

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
	
	// 返回出库明细用于保存的默认值
	public Map<String, Object> defaultOutDetailValue() {

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

	
	//入库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMatTranByPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
			try {
				//查询入库主表
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					
					List<Map<String,Object>> map=matTranMainMapper.queryMatTranPrintTemlateByMainBatch(entityMap);
					//查询入库明细表
					List<Map<String,Object>> list=matTranMainMapper.queryMatTranPrintTemlateByDetail(entityMap);
					return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
				}else{
					//查询入库主表
					Map<String,Object> map=matTranMainMapper.queryMatTranPrintTemlateByMain(entityMap);
					
					//查询入库明细表
					List<Map<String,Object>> list=matTranMainMapper.queryMatTranPrintTemlateByDetail(entityMap);
					
					return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
				}
		
				
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					throw new SysException(e.getMessage());
					}
		
	
	}
	//PageOffice批量打印凭证-返回Map
		@Override
		public Map<String,Object> queryMatTranByPrintTemlateMain(Map<String, Object> map)throws DataAccessException {
			
			try{
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				MatTranMainMapper matTranMainMapper = (MatTranMainMapper)context.getBean("matTranMainMapper");
               if("1".equals(String.valueOf(map.get("p_num")))){
					
					List<Map<String,Object>> mainList=matTranMainMapper.queryMatTranPrintTemlateByMainBatch(map);
				//查询凭证明细表
				List<Map<String,Object>> detailList=matTranMainMapper.queryMatTranPrintTemlateByDetail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
               }else{
            	 //查询入库主表
					Map<String,Object> mainList=matTranMainMapper.queryMatTranPrintTemlateByMain(map);
					
						
				//查询凭证明细表
				List<Map<String,Object>> detailList=matTranMainMapper.queryMatTranPrintTemlateByDetail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
				}
				
				return reMap;
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
			
		}
	
	@Override
	public String queryMatTranDetails(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)matTranDetailMapper.queryTranDetails(entityMap);
			
			return ChdJson.toJsonLower(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)matTranDetailMapper.queryTranDetails(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}
	}
	
	@Override
	public void updateMatTranOutRela(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> relaList= JsonListMapUtil.toListMapLower(matTranDetailMapper.queryMatTranOutRela(entityMap));
		if(relaList.size()>0){
			matTranDetailMapper.deleteMatTranRela(relaList);
		}
		
	}
	
	/**
	 * 查看出库单是否是科室申领生成的
	 */
	@Override
	public int queryMatTranMainIsApply(Map<String, Object> entityMap) throws DataAccessException {
		Integer  is_apply = matTranDetailMapper.queryMatTranMainIsApply(entityMap);
		return is_apply;
	}
	/**
	 * 材料调拨材料条形码打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryMatTranDetailBarcodeByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
		try{
			if (entityMap.get("group_id") == null) {
				entityMap.put("group_id", SessionManager.getGroupId());
			}
			if (entityMap.get("hos_id") == null) {
				entityMap.put("hos_id", SessionManager.getHosId());
			}
			if (entityMap.get("copy_code") == null) {
				entityMap.put("copy_code", SessionManager.getCopyCode());
			}
			String tranDetailIdStr=(String) entityMap.get("tranDetailIdStr");
			Map<String,Object> reMap=new HashMap<String,Object>();
			if(tranDetailIdStr!=null && !"".equals(tranDetailIdStr)){
				String[] in_detail_idArray=tranDetailIdStr.split(",");
				List<String> tran_detail_idList=new ArrayList<String>();
				for (String string : in_detail_idArray) {
					tran_detail_idList.add(string);
				}
				entityMap.put("tran_detail_idList", tran_detail_idList);
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				MatTranDetailMapper matTranDetailMapper = (MatTranDetailMapper)context.getBean("matTranDetailMapper");
//				List<Map<String,Object>> list=matStorageInMapper.queryMatInBarcodeByPrintTemlateNewPrint(entityMap);
				List<Map<String,Object>> list=matTranDetailMapper.queryMatTranDetailBarcodeByPrintTemlate(entityMap);
				reMap.put("main", list); 
			} 
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
}
