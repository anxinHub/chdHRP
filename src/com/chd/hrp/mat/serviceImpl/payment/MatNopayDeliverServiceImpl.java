/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.payment;

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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.dao.initial.MatInitChargeMapper;
import com.chd.hrp.mat.dao.payment.MatBillMapper;
import com.chd.hrp.mat.dao.payment.MatNopayDeliverMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.payment.MatNopayDeliverService;
import com.github.pagehelper.PageInfo;
 
/**
 * 
 * @Description:
 * 期初未付款送货单入库
 * @Table:
 * MAT_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matNopayDeliverService")
public class MatNopayDeliverServiceImpl implements MatNopayDeliverService {

	private static Logger logger = Logger.getLogger(MatNopayDeliverServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matNopayDeliverMapper")
	private final MatNopayDeliverMapper matNopayDeliverMapper = null;
	@Resource(name = "matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	@Resource(name = "matInitChargeMapper")
	private final MatInitChargeMapper matInitChargeMapper = null;
	@Resource(name = "matNoOtherMapper")
	private final MatNoOtherMapper matNoOtherMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matBillMapper")
	private final MatBillMapper matBillMapper = null;
	
	/**
	 * @Description 
	 * 添加期初未付款送货单入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addMatNopayDeliver(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			entityMap.put("maker", SessionManager.getUserId());
			entityMap.put("state", 1);
			
			//转换日期
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			if(entityMap.get("bill_date") != null && !"".equals(entityMap.get("bill_date"))){
				entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
			}
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
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
			specialMap.put("table_code", "MAT_NOPAY_DELIVER");
			specialMap.put("prefixe", "");
			if("自动生成".equals(entityMap.get("deliver_no"))){
				entityMap.put("deliver_no", matCommonService.getMatNextNo(specialMap));
			}
			//期初未付款送货单单据ID
			entityMap.put("deliver_id", matNopayDeliverMapper.queryMatNopayDeliverNextval());
			
			//组装明细
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				float money_sum = 0;//记录明细总金额
				//用于查询个体码
				Map<String,Object> barCodeMap = new HashMap<String,Object>();
				barCodeMap.put("group_id", entityMap.get("group_id"));
				barCodeMap.put("hos_id", entityMap.get("hos_id"));
				barCodeMap.put("type_code", 1);
				String bar_code = matNoOtherMapper.queryMatNoOther(barCodeMap);//获取当前个体码
				//如果不存在则插入
				if(bar_code == null || "".equals(bar_code)){
					bar_code = "000000000000";
					barCodeMap.put("max_no", bar_code);
					matNoOtherMapper.insertMatNoOther(barCodeMap);
				}
				String init_bar_code = bar_code;
				
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
						detailMap.put("detail_id", matNopayDeliverMapper.queryMatNopayDeliverDetailSeq());//期初未付款送货单明细表id
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料 变更ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						money_sum = money_sum + Float.parseFloat(String.valueOf(detailMap.get("amount_money")));//记录总金额
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  String.valueOf(jsonObj.get("batch_no")));//批号
						}
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
							if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
								detailMap.put("bar_code",  String.valueOf(jsonObj.get("bar_code")));//个体码
							}else{
								detailMap.put("bar_code", "-");//个体码--个体码默认条形码
							}
							//该条明细数据添加到List中
							detailList.add(detailMap);
						}else{
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(String.valueOf(detailMap.get("amount"))); i++){
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(detailMap);
								if( i>1){
									barMap.put("detail_id", matNopayDeliverMapper.queryMatNopayDeliverDetailSeq());
								}
								bar_code = matCommonService.getNextBar_code(bar_code);
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
					matNoOtherMapper.updateMatNoOther(barCodeMap);
				}
				
				if(entityMap.get("bill_no") != null && !"".equals(entityMap.get("bill_no").toString())){
					entityMap.put("bill_state", 1);
				}else{
					entityMap.put("bill_state", 0);
				}

				entityMap.put("bus_type_code", "2");
				entityMap.put("amount_money", money_sum);
				//新增期初未付款送货单主表数据
				matNopayDeliverMapper.addMatNopayDeliver(entityMap);
				
				//批量新增期初未付款送货单明细数据
				matNopayDeliverMapper.addMatNopayDeliverDetailBatch(detailList);
				
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

			throw new SysException(e.getMessage());
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
	
	public String updateMatNopayDeliver(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
				return "{\"error\":\"更新失败,单据状态不为未审核状态！.\",\"state\":\"false\"}";
			}
			
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			entityMap.put("maker", SessionManager.getUserId());
			entityMap.put("state", 1);
			
			//转换日期
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			if(entityMap.get("bill_date") != null && !"".equals(entityMap.get("bill_date"))){
				entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
			}
			
			Map<String,Object> deleteMap = new HashMap<String,Object>();
			
			deleteMap.put("group_id", entityMap.get("group_id"));
			deleteMap.put("hos_id", entityMap.get("hos_id"));
			deleteMap.put("copy_code", entityMap.get("copy_code"));
			deleteMap.put("deliver_id", entityMap.get("deliver_id"));
			
			//删除期初未付款送货单 明细数据
			matNopayDeliverMapper.deleteMatNopayDeliverDetail(deleteMap);
			
			//组装明细
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				float money_sum = 0;//记录明细总金额
				//用于查询个体码
				Map<String,Object> barCodeMap = new HashMap<String,Object>();
				barCodeMap.put("group_id", entityMap.get("group_id"));
				barCodeMap.put("hos_id", entityMap.get("hos_id"));
				barCodeMap.put("type_code", 1);
				String bar_code = matNoOtherMapper.queryMatNoOther(barCodeMap);//获取当前个体码
				//如果不存在则插入
				if(bar_code == null || "".equals(bar_code)){
					bar_code = "000000000000";
					barCodeMap.put("max_no", bar_code);
					matNoOtherMapper.insertMatNoOther(barCodeMap);
				}
				String init_bar_code = bar_code;
				
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
						detailMap.put("detail_id", matNopayDeliverMapper.queryMatNopayDeliverDetailSeq());//期初未付款送货单入库明细表id
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料变更号
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  String.valueOf(jsonObj.get("batch_no")));//批号
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
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						
						//明细表ID
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("detail_id")))){
							// 期初未付款送货单明细表 ID
							detailMap.put("detail_id",  matNopayDeliverMapper.queryMatNopayDeliverDetailSeq());
							//生成个体码
							if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar"))) || "0".equals(String.valueOf(jsonObj.get("is_per_bar")))){
								if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
									detailMap.put("bar_code",  String.valueOf(jsonObj.get("bar_code")));//个体码
								}else{
									detailMap.put("bar_code", "-");//个体码--个体码默认条形码
								}
								//该条明细数据添加到List中
								detailAddList.add(detailMap);
							}else{
								//根据一码一物规则自动拆分数量并生成个体码
								for(int i = 1; i <= Integer.parseInt(String.valueOf(detailMap.get("num"))); i++){
									Map<String, Object> barMap = new HashMap<String, Object>();
									barMap.putAll(detailMap);
									bar_code = matCommonService.getNextBar_code(bar_code);
									if( i>1){
										barMap.put("detail_id", matNopayDeliverMapper.queryMatNopayDeliverDetailSeq());
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
								detailMap.put("bar_code",  String.valueOf(jsonObj.get("bar_code")));//个体码
							}else{
								detailMap.put("bar_code", "-");//个体码--个体码默认条形码
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
					matNoOtherMapper.updateMatNoOther(barCodeMap);
				}
				
				if(entityMap.get("bill_no") != null && !"".equals(entityMap.get("bill_no").toString())){
					entityMap.put("bill_state", 1);
				}else{
					entityMap.put("bill_state", 0);
				}
				
				entityMap.put("amount_money", money_sum);
				//修改 期初未付款送货单主表数据
				matNopayDeliverMapper.updateMatNopayDeliver(entityMap);
				
				//批量添加  期初未付款送货单明细记录
				if(detailAddList.size() > 0){
					matNopayDeliverMapper.addMatNopayDeliverDetailBatch(detailAddList);
				}
				
			}else{
				return "{\"error\":\"更新失败，明细数据为空\",\"state\":\"false\"}";
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
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

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatInitInCommon\"}";
		}	
	}
    
	/**
	 * @Description 
	 * 批量删除期初未付款送货单入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/

	public String deleteMatNopayDeliverBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//状态不为新建不能删除，从页面判断
			//期初记账状态不为0的不能删除，从页面判断
			
			//批量删除期初未付款送货单明细数据
			matNopayDeliverMapper.deleteMatNopayDeliverDetailBatch(entityList);
			
			//批量删除期初未付款送货单主数据
			matNopayDeliverMapper.deleteMatNopayDeliverBatch(entityList);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}	
	}
	
	
	//期初未付款送货单 出入库 审核、消审
	@Override
	public String updateStateMatNopayDeliver(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try {	
			//期初未付款送货单 入库单批量审核、消审
			matNopayDeliverMapper.updateStateMatNopayDeliver(listVo);
			
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
	public String copyMatNopayDeliverBatch(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try {	
		
			//存放组装数据的List
			List<Map<String, Object>> copyMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> copyDetailList = new ArrayList<Map<String,Object>>();
			
			for( Map<String, Object> item : listVo){
				
				Map<String, Object> deliver = matNopayDeliverMapper.queryMatNopayDeliverById(item);
				
				List<Map<String,Object>> detailList = matNopayDeliverMapper.queryMatNopayDeliverDetailById(item);
				
				Map<String,Object> noMap = new HashMap<String,Object>();
				noMap.put("group_id", deliver.get("group_id"));
				noMap.put("hos_id", deliver.get("hos_id"));
				noMap.put("copy_code", deliver.get("copy_code"));
				noMap.put("year", deliver.get("year"));
				noMap.put("month", deliver.get("month"));
				noMap.put("store_id", deliver.get("store_id"));
				noMap.put("table_code", "MAT_NOPAY_DELIVER");
				noMap.put("prefixe", "");
				
				deliver.put("deliver_no", matCommonService.getMatNextNo(noMap));
				deliver.put("deliver_id", matNopayDeliverMapper.queryMatNopayDeliverNextval());
				
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
					detail.put("detail_id",matNopayDeliverMapper.queryMatNopayDeliverDetailSeq());
					
					//用于查询个体码
					Map<String,Object> barCodeMap = new HashMap<String,Object>();
					barCodeMap.put("group_id", detail.get("group_id"));
					barCodeMap.put("hos_id", detail.get("hos_id"));
					barCodeMap.put("type_code", 1);
					
					String bar_code = matNoOtherMapper.queryMatNoOther(barCodeMap);//获取当前个体码
					
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
								barMap.put("detail_id", matNopayDeliverMapper.queryMatNopayDeliverDetailSeq());
							}
							bar_code = matCommonService.getNextBar_code(bar_code);
							
							barMap.put("bar_code",  bar_code);//个体码
							//该条明细数据添加到List中
							copyDetailList.add(barMap);
						}
					}
				}
			}
			
			matNopayDeliverMapper.addMatNopayDeliverBatch(copyMainList);
			
			matNopayDeliverMapper.addMatNopayDeliverDetailBatch(copyDetailList);
			
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
		List<Map<String, Object>> list = matNopayDeliverMapper.queryMatNopayDeliverDetailById(entityMap);

		if (list.size() > 0) {

			matNopayDeliverMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			matNopayDeliverMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatInitInCommon\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集  期初未付款送货单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryMatNopayDeliver(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matNopayDeliverMapper.queryMatNopayDeliver(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matNopayDeliverMapper.queryMatNopayDeliver(entityMap, rowBounds);
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
		return (T) matInCommonMapper.queryMatInMainByCode(entityMap);
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
	public String queryMatNopayDeliverDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> list = matNopayDeliverMapper.queryMatNopayDeliverDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}

	/**
	 * 期初未付款送货单 更新页面回值查询
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryMatNopayDeliverMainUpdate(Map<String, Object> mapVo) throws DataAccessException{
		
		return matNopayDeliverMapper.queryMatNopayDeliverMainUpdate(mapVo);
	}
	/**
	 * 根据 期初未付款送货单入库单Id 查询 期初未付款送货单入库单明细
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryMatNopayDeliverDetail(Map<String, Object> mapVo) throws DataAccessException{
		return matNopayDeliverMapper.queryMatNopayDeliverDetail(mapVo);
	}
	
	/**
	 * 上一张 、 下一张
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatNopayDeliverBeforeOrNextNo(Map<String, Object> mapVo) throws DataAccessException{
		try {	
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			//上一张
			if("-1".equals(mapVo.get("flag").toString())){
				map = matNopayDeliverMapper.queryMatNopayDeliverBefore(mapVo);
				if(map == null){
					return "{\"state\":\"false\"}";
				}
			//下一张
			}else if("1".equals(mapVo.get("flag").toString())){
				map = matNopayDeliverMapper.queryMatNopayDeliverNext(mapVo);
				if(map == null ){
					return "{\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"操作失败 页面参数异常 请联系管理员！方法queryMatNopayDeliverBeforeOrNextNo\"}";
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

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatNopayDeliverBeforeOrNextNo\"}";
		}	
	}
	@Override
	public int queryBillOrNot(Map<String, Object> mapVo) throws DataAccessException {
		
		return matNopayDeliverMapper.queryBillOrNot(mapVo);
	}
	
	@Override
	public Map<String, Object> impData(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String user_id = SessionManager.getUserId();
			
			Map<String, Object> seachMap = new HashMap<String, Object>();
			seachMap.put("group_id", group_id);
			seachMap.put("hos_id", hos_id);
			seachMap.put("copy_code", copy_code);
			seachMap.put("user_id", user_id);
			
			//仓库字典
			Map<String, String> storeMap = new HashMap<String, String>();
			List<Map<String, Object>> storeDictList = matCommonMapper.queryHosStoreDict(seachMap);
			for (Map<String, Object> storeDict : storeDictList) {
				storeMap.put(storeDict.get("STORE_NAME").toString(), storeDict.get("STORE_ID").toString()+"@"+storeDict.get("STORE_NO").toString());
				storeMap.put(storeDict.get("STORE_CODE").toString(), storeDict.get("STORE_ID").toString()+"@"+storeDict.get("STORE_NO").toString());
			}
			//供应商字典
			Map<String, String> supMap = new HashMap<String, String>();
			List<Map<String, Object>> supDictList = matCommonMapper.queryHosSupDict(seachMap);
			for (Map<String, Object> supDict : supDictList) {
				supMap.put(supDict.get("SUP_NAME").toString(), supDict.get("SUP_ID").toString()+"@"+supDict.get("SUP_NO").toString());
				supMap.put(supDict.get("SUP_CODE").toString(), supDict.get("SUP_ID").toString()+"@"+supDict.get("SUP_NO").toString());
			}
			//部门字典
			Map<String, String> deptMap = new HashMap<String, String>();
			List<Map<String,Object>> deptDictList = matCommonMapper.queryHosDeptDict(seachMap);
			for (Map<String,Object> deptDict : deptDictList) {
				deptMap.put(deptDict.get("DEPT_NAME").toString(), deptDict.get("DEPT_ID").toString());
			}
			//职工字典
			Map<String, String> empMap = new HashMap<String, String>();
			List<Map<String, Object>> empList = matCommonMapper.queryHosEmpDict(seachMap);
			for (Map<String, Object> emp : empList) {
				empMap.put(emp.get("EMP_NAME").toString(), emp.get("EMP_ID").toString());
				empMap.put(emp.get("EMP_CODE").toString(), emp.get("EMP_ID").toString());
			}
			//项目字典
			Map<String,Object> projMap = new HashMap<String, Object>();
			List<Map<String,Object>> projList = matCommonMapper.queryHosProjDict(seachMap);
			for (Map<String,Object> proj : projList) {
				projMap.put(proj.get("PROJ_NAME").toString(), proj.get("PROJ_ID").toString());
				projMap.put(proj.get("PROJ_CODE").toString(), proj.get("PROJ_ID").toString());
			}
			//采购类型字典
			Map<String,Object> stockTypeMap = new HashMap<String, Object>();
			List<Map<String,Object>> stockTypeList = matCommonMapper.queryMatStockType(seachMap);
			for (Map<String,Object> stockType : stockTypeList) {
				stockTypeMap.put(stockType.get("STOCK_TYPE_NAME").toString(), stockType.get("STOCK_TYPE_CODE").toString());
				stockTypeMap.put(stockType.get("STOCK_TYPE_CODE").toString(), stockType.get("STOCK_TYPE_CODE").toString());
			}
			//采购协议单据
			Map<String,Object> protocolMap = new HashMap<String, Object>();
			List<Map<String,Object>> protocolList = matCommonMapper.queryMatProtocolMain(seachMap);
			for (Map<String,Object> protocol : protocolList) {
				protocolMap.put(protocol.get("PROTOCOL_NAME").toString(), protocol.get("PROTOCOL_ID").toString());
				protocolMap.put(protocol.get("PROTOCOL_CODE").toString(), protocol.get("PROTOCOL_ID").toString());
			}
			//材料字典
			Map<String, String> invDictMap = new HashMap<String, String>();
			List<Map<String, Object>> invDictList = matCommonMapper.queryMatInvDict(seachMap);
			for (Map<String, Object> invDict : invDictList) {
				invDictMap.put(invDict.get("INV_NAME").toString(), invDict.get("INV_ID").toString()+"@"+invDict.get("INV_NO").toString());
				invDictMap.put(invDict.get("INV_CODE").toString(), invDict.get("INV_ID").toString()+"@"+invDict.get("INV_NO").toString());
			}
			
			//生成单号的Map
			Map<String,Object> noMap = new HashMap<String,Object>();
			noMap.put("group_id", group_id);
			noMap.put("hos_id", hos_id);
			noMap.put("copy_code", copy_code);
			noMap.put("table_code", "MAT_NOPAY_DELIVER");
			noMap.put("prefixe", "");
			
			//导入数据
			String content = entityMap.get("content").toString();
			if(content == null || "".equals(content)){
				retMap.put("state", "false");
				retMap.put("error", "未获得导入数据，请稍后再试！");
				return retMap;
			}
			List<Map<String, List<String>>> dataList = SpreadTableJSUtil.toListMap(content, 1);
			if(dataList == null || dataList.size() == 0){
				retMap.put("state", "false");
				retMap.put("error", "未获得导入数据，请稍后再试！");
				return retMap;
			}
			//存主表
			List<Map<String, Object>> mainList = new ArrayList<Map<String,Object>>();
			//存明细表
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			
			Map<String, Object> mainMap = null;
			Map<String, Object> detailMap = null;
			List<String> rowList = null;
			Map<String, Map<String, String>> existsMap = new HashMap<String, Map<String, String>>();
			Map<String, String> idMap = null;
			String existsKey = "";
			String in_date= "";
			String deliver_no = "";
			String deliver_id = "";
			for (Map<String, List<String>> dataMap : dataList) {
				
				/***********解析主表数据***********begin******************/
				mainMap = new HashMap<String, Object>();
				/**原始单号校验******begin*****/
				rowList = dataMap.get("原始单号");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					mainMap.put("origin_no", null);
				}else{
					mainMap.put("origin_no", rowList.get(1));
					existsKey += rowList.get(1);
				}
				/**原始单号校验******end*******/
				/**编制日期校验******begin*****/
				rowList = dataMap.get("编制日期");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，编制日期为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}else{
					in_date = rowList.get(1);
					//校验格式
					if(in_date.split("-").length != 3){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，日期格式应为文本型的【年-月-日】！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					mainMap.put("in_date", DateUtil.stringToDate(in_date, "yyyy-MM-dd"));
					mainMap.put("year", in_date.split("-")[0]);
					mainMap.put("month", in_date.split("-")[1]);
					//用于查询单号
					noMap.put("year", in_date.split("-")[0]);
					noMap.put("month", in_date.split("-")[1]);

					existsKey += rowList.get(1);
				}
				/**编制日期校验******end*******/
				/**供应商校验******begin*****/
				rowList = dataMap.get("供应商");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，供应商为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(!supMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，供应商不存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				mainMap.put("sup_id", supMap.get(rowList.get(1)).split("@")[0]);
				mainMap.put("sup_no", supMap.get(rowList.get(1)).split("@")[1]);
				
				existsKey += rowList.get(1);
				/**供应商校验******end*******/
				/**仓库校验******begin*****/
				rowList = dataMap.get("仓库");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，仓库为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(!storeMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，仓库不存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				mainMap.put("store_id", storeMap.get(rowList.get(1)).split("@")[0]);
				mainMap.put("store_no", storeMap.get(rowList.get(1)).split("@")[1]);

				//用于查询单号
				noMap.put("store_id", storeMap.get(rowList.get(1)).split("@")[0]);
				
				existsKey += rowList.get(1);
				/**仓库校验******end*******/
				/**采购员校验******begin*****/
				rowList = dataMap.get("采购员");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，采购员为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}else{
					if(!empMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，采购员不存在！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					mainMap.put("stocker", empMap.get(rowList.get(1)));
					
					existsKey += rowList.get(1);
				}
				/**采购员校验******end*******/
				/**库管员校验******begin*****/
				rowList = dataMap.get("库管员");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，库管员为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}else{
					if(!empMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，库管员不存在！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					mainMap.put("confirmer", empMap.get(rowList.get(1)));
					
					existsKey += rowList.get(1);
				}
				/**库管员校验******end*******/
				/**采购类型校验******begin*****/
				rowList = dataMap.get("采购类型");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					mainMap.put("stock_type_code", null);
				}else{
					if(!stockTypeMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，采购类型不存在！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					mainMap.put("stock_type_code", stockTypeMap.get(rowList.get(1)));
					
					existsKey += rowList.get(1);
				}
				/**采购类型校验******end*******/
				/**发票号校验******begin*****/
				rowList = dataMap.get("发票号");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					mainMap.put("bill_no", null);
					mainMap.put("bill_state", 0);
				}else{
					mainMap.put("bill_no", rowList.get(1));
					mainMap.put("bill_state", 1);
					existsKey += rowList.get(1);
				}
				/**发票号校验******end*******/
				/**发票日期校验******begin*****/
				rowList = dataMap.get("发票日期");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					mainMap.put("bill_date", null);
				}else{
					//校验格式
					if(rowList.get(1).split("-").length != 3){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，日期格式应为文本型的【年-月-日】！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					mainMap.put("bill_date", DateUtil.stringToDate(rowList.get(1), "yyyy-MM-dd"));
					
					existsKey += rowList.get(1);
				}
				/**发票日期校验******end*******/
				/**摘要校验******begin*****/
				rowList = dataMap.get("摘要");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					mainMap.put("brief", null);
				}else{
					mainMap.put("brief", rowList.get(1));
					
					existsKey += rowList.get(1);
				}
				/**摘要校验******end*******/
				/**协议编号校验******begin*****/
				rowList = dataMap.get("协议编号");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					mainMap.put("protocol_code", null);
				}else{

					if(!protocolMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，协议编号不存在！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					mainMap.put("protocol_code", protocolMap.get(rowList.get(1)));
					
					existsKey += rowList.get(1);
				}
				/**协议编号校验******end*******/
				/**项目校验******begin*****/
				rowList = dataMap.get("项目");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					mainMap.put("proj_id", null);
				}else{

					if(!projMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，项目不存在！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					mainMap.put("proj_id", projMap.get(rowList.get(1)));
					
					existsKey += rowList.get(1);
				}
				/**项目校验******end*******/
				if(existsMap.containsKey(existsKey)){
					deliver_id = existsMap.get(existsKey).get("deliver_id").toString();
					deliver_no = existsMap.get(existsKey).get("deliver_no").toString();
				}else{
					//新单据需获取ID和单号
					deliver_id = matNopayDeliverMapper.queryMatNopayDeliverNextval().toString();
					deliver_no = matCommonService.getMatNextNo(noMap);
					
					mainMap.put("group_id", group_id);
					mainMap.put("hos_id", hos_id);
					mainMap.put("copy_code", copy_code);
					mainMap.put("maker", user_id);
					mainMap.put("state", 1);
					mainMap.put("deliver_id", deliver_id);
					mainMap.put("deliver_no", deliver_no);
					mainMap.put("bus_type_code", "2");
					mainMap.put("amount_money", 0);  //最后批量更新这个字段
					mainList.add(mainMap);
					
					//记录ID和单号用于判断
					idMap = new HashMap<String, String>();
					idMap.put("deliver_id", deliver_id);
					idMap.put("deliver_no", deliver_no);
					existsMap.put(existsKey, idMap);
				}
				/***********解析主表数据***********end********************/
				
				/***********解析明细数据***********begin******************/
				detailMap = new HashMap<String, Object>();
				/**材料编码校验******begin*****/
				rowList = dataMap.get("材料编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，材料编码为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				if(!invDictMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，材料编码不存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				detailMap.put("inv_id", invDictMap.get(rowList.get(1)).split("@")[0]);
				detailMap.put("inv_no", invDictMap.get(rowList.get(1)).split("@")[1]);
				/**材料编码校验******end*******/
				/**批号校验******begin*****/
				rowList = dataMap.get("批号");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，批号为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				detailMap.put("batch_no", rowList.get(1));
				/**批号校验******end*******/
				/**单价校验******begin*****/
				rowList = dataMap.get("单价");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，单价为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				detailMap.put("price", rowList.get(1));
				/**单价校验******end*******/
				/**数量校验******begin*****/
				rowList = dataMap.get("数量");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，数量为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				detailMap.put("amount", rowList.get(1));
				/**数量校验******end*******/
				/**金额校验******begin*****/
				rowList = dataMap.get("金额");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，金额为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				detailMap.put("amount_money", rowList.get(1));
				/**金额校验******end*******/
				/**条形码校验******begin*****/
				rowList = dataMap.get("条形码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					detailMap.put("bar_code", "-");
					detailMap.put("sn", "-");
				}else{
					detailMap.put("bar_code", rowList.get(1));
					detailMap.put("sn", rowList.get(1));
				}
				/**条形码校验******end*******/
				/**有效日期校验******begin*****/
				rowList = dataMap.get("有效日期");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					detailMap.put("inva_date", null);
				}else{
					//校验格式
					if(rowList.get(1).split("-").length != 3){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，日期格式应为文本型的【年-月-日】！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					detailMap.put("inva_date", DateUtil.stringToDate(rowList.get(1), "yyyy-MM-dd"));
				}
				/**有效日期校验******end*******/
				/**灭菌日期校验******begin*****/
				rowList = dataMap.get("灭菌日期");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					detailMap.put("disinfect_date", null);
				}else{
					//校验格式
					if(rowList.get(1).split("-").length != 3){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，日期格式应为文本型的【年-月-日】！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					detailMap.put("disinfect_date", DateUtil.stringToDate(rowList.get(1), "yyyy-MM-dd"));
				}
				/**灭菌日期校验******end*******/
				/**备注校验******begin*****/
				rowList = dataMap.get("备注");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					detailMap.put("note", null);
				}else{
					detailMap.put("note", rowList.get(1));
				}
				/**备注校验******end*******/

				detailMap.put("group_id", group_id);
				detailMap.put("hos_id", hos_id);
				detailMap.put("copy_code", copy_code);
				detailMap.put("deliver_id", deliver_id);
				detailMap.put("deliver_no", deliver_no);
				detailMap.put("detail_id", matNopayDeliverMapper.queryMatNopayDeliverDetailSeq());
				detailList.add(detailMap);
				/***********解析明细数据***********end********************/
			}
			//添加主表
			matNopayDeliverMapper.addMatNopayDeliverBatch(mainList);
			//添加明细表
			matNopayDeliverMapper.addMatNopayDeliverDetailBatch(detailList);
			//更新主表金额字段
			matNopayDeliverMapper.updateMatDeliverMoneyByDetail(mainList);
			retMap.put("state", "true");
			retMap.put("msg", "导入成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
		
		return retMap;
	}

	/**
	 * @Description 
	 * 生成发票<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> addMatBillByBill(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			//发票明细表信息
			List<Map<String, Object>> detailList = JsonListMapUtil.toListMapLower(matNopayDeliverMapper.queryMatNopayDeliverDByNotBill(entityMap));
			if(detailList.size() > 0){
				//发票主表信息
				Map<String, Object> mainMap = JsonListMapUtil.toMapLower(matNopayDeliverMapper.queryMatNopayDeliverForBill(entityMap));
				if(mainMap.get("bill_no") == null || "".equals(mainMap.get("bill_no"))){
					retMap.put("error", "操作失败，请维护发票号！");
					return retMap;
				}
				if(mainMap.get("bill_date") == null || "".equals(mainMap.get("bill_date"))){
					retMap.put("error", "操作失败，请维护发票日期！");
					return retMap;
				}
				entityMap.put("bill_no",  mainMap.get("bill_no"));
				
				//根据发票号查找发票信息
				Map<String, Object> mbm= matBillMapper.queryMatBillMainByBillNo(entityMap);
				//当存在发票号时，更新发票明细。不存在的新增发票单。
				if(mbm == null || mbm.isEmpty()){
				    //发票类型：1普通2红冲
				    String bus_type_code = mainMap.get("bus_type_code").toString();
				    if("12".equals(bus_type_code)){
				    	mainMap.put("bill_type", "2");
				    }else{
				    	mainMap.put("bill_type", "1");
				    }
					/**获取发票ID和流水码*******begin*******/
					Long bill_id = matBillMapper.queryBillMainSeq();
					String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
					String[] dates = date.split("-");
					Map<String, Object> noMap = new HashMap<String, Object>();
					noMap.put("group_id", mainMap.get("group_id"));
					noMap.put("hos_id", mainMap.get("hos_id"));
					noMap.put("copy_code", mainMap.get("copy_code"));
					noMap.put("year", dates[0]);
					noMap.put("month", dates[1]);
					noMap.put("day", dates[2]);
					noMap.put("table_code", "MAT_BILL_MAIN");
					noMap.put("prefixe", "FP");
					String bill_code = matCommonService.getMatNextNo(noMap);
					mainMap.put("bill_id", bill_id);
					mainMap.put("bill_code", bill_code);
					/**获取发票ID和流水码*******end*********/
					mainMap.put("ori_no", "0000");
					mainMap.put("stock_type_code", "");
					mainMap.put("pay_code", "");
					mainMap.put("maker", SessionManager.getUserId());
				    mainMap.put("make_date", new Date());
				    mainMap.put("is_init", "1");
				    mainMap.put("state", "1");
				    mainMap.put("note", "");
					
				    double money_sum = 0;
					for(Map<String, Object> map : detailList){
						map.put("bill_no", mainMap.get("bill_no").toString());
						map.put("bill_detail_id", matBillMapper.queryBillDetailSeq());
						money_sum = NumberUtil.add(money_sum, Double.parseDouble(map.get("bill_money").toString()));
					}
					
					mainMap.put("payable_money", 0);
					mainMap.put("bill_money", money_sum);
					
					//添加采购发票
					matBillMapper.addMatBillMain(mainMap);
					matBillMapper.addMatBillDetail(mainMap, detailList);
				}else{
					if("1".equals(mbm.get("state").toString())){
						if(mainMap.get("sup_id").toString().equals(mbm.get("sup_id").toString())){

							double money_sum = Double.parseDouble(mbm.get("bill_money").toString());
							for(Map<String, Object> map : detailList){
								map.put("bill_no", mainMap.get("bill_no").toString());
								map.put("bill_detail_id", matBillMapper.queryBillDetailSeq());
								money_sum = NumberUtil.add(money_sum, Double.parseDouble(map.get("bill_money").toString()));
							}
							//更新发票主表的发票金额
							Map<String, Object> mapBill = new HashMap<String, Object>();
							
							mapBill.put("bill_id", mbm.get("bill_id"));
							mapBill.put("bill_money", money_sum);
							mapBill.putAll(entityMap);
							//修改主表发票金额
							matBillMapper.updateMatBillMoneyById(mapBill);
							//添加发票明细
							matBillMapper.addMatBillDetail(mbm, detailList);
						}else{
							retMap.put("error", "操作失败，该入库单的供应商与发票单上的供应商不一致，不能合并生成发票单！");
							return retMap;
						}
					}else{
						retMap.put("error", "操作失败，已经审核的发票单不能生成单据！");
						return retMap;
					}
				}
				
				retMap.put("state", "true");
				retMap.put("msg", "操作成功！");
			}else{
				retMap.put("error", "入库单所有材料已生成过发票，不能重复生成！");
				return retMap;
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}

	/**
	 * 批量生成发票
	 */
	@Override
	public Map<String, Object> addMatBillByBillBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			//keyMap就决定了生成发票的张数
			Map<String,Object> keyMap = new HashMap<String,Object>();
			for(Map<String,Object> map : entityList ){
				String key = map.get("group_id").toString()+'@'+map.get("hos_id").toString()+'@'+map.get("copy_code").toString()+'@'+map.get("sup_id").toString()+'@'+map.get("bill_no").toString()+'@'+map.get("bill_date").toString();
				if(!keyMap.containsKey(key)){
					keyMap.put(key, map); 
				}	
			}
			
			Map<String,Object> entityMap = new HashMap<String,Object>();
			Map<String, Object> noMap  = null;
			for (String key : keyMap.keySet()) {
				
				StringBuffer deliverIds = new StringBuffer();
				for(Map<String,Object> idMap : entityList){
					String keyM = idMap.get("group_id").toString()+'@'+idMap.get("hos_id").toString()+'@'+idMap.get("copy_code").toString()+'@'+idMap.get("sup_id").toString()+'@'+idMap.get("bill_no").toString()+'@'+idMap.get("bill_date").toString();
					if(key.equals(keyM)){
						if(deliverIds.length()>0){
							deliverIds.append(",").append(idMap.get("deliver_id").toString());
						}else{
							deliverIds.append(idMap.get("deliver_id").toString());
						}
					}
				}
				
				//发票主表信息
				String[] keys = key.split("@");
				entityMap.put("group_id", keys[0]);
				entityMap.put("hos_id", keys[1]);
				entityMap.put("copy_code", keys[2]);
				entityMap.put("sup_id", keys[3]);
				entityMap.put("bill_no", keys[4]);
				entityMap.put("deliver_ids", deliverIds);
				
				//获取当前日期用于生成单号
				String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
				String[] dates = date.split("-");
				noMap = new HashMap<String, Object>();
				noMap.put("group_id", keys[0]);
				noMap.put("hos_id", keys[1]);
				noMap.put("copy_code", keys[2]);
				noMap.put("year", dates[0]);
				noMap.put("month", dates[1]);
				noMap.put("day", dates[2]);
				noMap.put("table_code", "MAT_BILL_MAIN");
				noMap.put("prefixe", "FP");
				//发票明细表信息
				List<Map<String,Object>> detailList = JsonListMapUtil.toListMapLower(matNopayDeliverMapper.queryMatNopayDeliverDByNotBill(entityMap));
				if(detailList.size() > 0){
					//主表
					Map<String, Object> mainMap = JsonListMapUtil.toMapLower(matNopayDeliverMapper.queryMatNopayDeliverForBill(entityMap));
					//根据发票号查找发票信息
					Map<String, Object> mbm = matBillMapper.queryMatBillMainByBillNo(mainMap);
					if(null == mbm){
					    //发票类型：1普通2红冲
					    String bus_type_code = mainMap.get("bus_type_code").toString();
					    if("12".equals(bus_type_code)){
					    	mainMap.put("bill_type", "2");
					    }else{
					    	mainMap.put("bill_type", "1");
					    }
						/**获取发票ID和流水码*******begin*******/
						Long bill_id = matBillMapper.queryBillMainSeq();
						String bill_code = matCommonService.getMatNextNo(noMap);
						mainMap.put("bill_id", bill_id);
						mainMap.put("bill_code", bill_code);
						/**获取发票ID和流水码*******end*********/
						
						mainMap.put("ori_no", "0000");
						mainMap.put("stock_type_code", "");
						mainMap.put("pay_code", "");
						mainMap.put("maker", SessionManager.getUserId());
					    mainMap.put("make_date", new Date());
					    mainMap.put("is_init", "1");
					    mainMap.put("state", "1");
					    mainMap.put("note", "");
						
					    double money_sum = 0;
						for(Map<String, Object> map : detailList){
							map.put("bill_no", mainMap.get("bill_no").toString());
							map.put("bill_detail_id", matBillMapper.queryBillDetailSeq());
							money_sum = NumberUtil.add(money_sum, Double.parseDouble(map.get("bill_money").toString()));
						}
						
						mainMap.put("payable_money", 0);
						mainMap.put("bill_money", money_sum);
						
						matBillMapper.addMatBillMain(mainMap);
						matBillMapper.addMatBillDetail(mainMap, detailList);
					}else{
						if("1".equals(mbm.get("state").toString())){
							if(mainMap.get("sup_id").toString().equals(mbm.get("sup_id").toString())){

								double money_sum = Double.parseDouble(mbm.get("bill_money").toString());
								for(Map<String, Object> map : detailList){
									map.put("bill_no", mainMap.get("bill_no").toString());
									map.put("bill_detail_id", matBillMapper.queryBillDetailSeq());
									money_sum = NumberUtil.add(money_sum, Double.parseDouble(map.get("bill_money").toString()));
								}
								//更新发票主表的发票金额
								Map<String, Object> mapBill = new HashMap<String, Object>();
								
								mapBill.put("bill_id", mbm.get("bill_id"));
								mapBill.put("bill_money", money_sum);
								mapBill.putAll(entityMap);
								//修改主表发票金额
								matBillMapper.updateMatBillMoneyById(mapBill);
								//添加发票明细
								matBillMapper.addMatBillDetail(mbm, detailList);
							}else{
								retMap.put("error", "操作失败，该入库单的供应商与发票单上的供应商不一致，不能合并生成发票单！");
								return retMap;
							}
						}else{
							retMap.put("error", "操作失败，已经审核的发票单不能生成单据！");
							return retMap;
						}
					}
				}else{
					retMap.put("error", "操作失败，入库单所有材料已生成过发票！");
					return retMap;
				}
			}
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
		
		return retMap;
	}
}
