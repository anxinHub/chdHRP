/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.in;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.tool.HttpClientTool;
import com.chd.base.tool.ServerUrl;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.dao.base.MatStoreModMapper;
import com.chd.hrp.mat.dao.initial.MatInitChargeMapper;
import com.chd.hrp.mat.dao.matpay.MatPayMainMapper;
import com.chd.hrp.mat.dao.payment.MatBillMapper;
import com.chd.hrp.mat.dao.storage.back.MatStorageBackMapper;
import com.chd.hrp.mat.dao.storage.in.MatStorageInMapper;
import com.chd.hrp.mat.entity.MatInMain;
import com.chd.hrp.mat.entity.MatInResource;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.platform.dao.ZjMapper;
import com.chd.hrp.sup.dao.SupDeliveryMainMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 
 * 常备材料入库
 * @Table: 
 * MAT_INV 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0 
 */
@Service("matStorageInService")
public class MatStorageInServiceImpl implements MatStorageInService {         

	private static Logger logger = Logger.getLogger(MatStorageInServiceImpl.class);   
	//引入DAO操作
	@Resource(name = "matStorageInMapper")
	private final MatStorageInMapper matStorageInMapper = null;
	@Resource(name = "matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	@Resource(name = "matNoOtherMapper")
	private final MatNoOtherMapper matNoOtherMapper = null;
	@Resource(name = "matInitChargeMapper")
	private final MatInitChargeMapper matInitChargeMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matBillMapper")
	private final MatBillMapper matBillMapper = null;
	@Resource(name = "matStorageBackMapper")
	private final MatStorageBackMapper matStorageBackMapper = null;
	// 引入DAO操作
	@Resource(name = "supDeliveryMainMapper")
	private final SupDeliveryMainMapper supDeliveryMainMapper = null;
	
	@Resource(name = "matStoreModMapper")
	private final MatStoreModMapper matStoreModMapper = null;
	
	@Resource(name = "matPayMainMapper")
	private final MatPayMainMapper matPayMainMapper = null;
	@Resource(name = "zjMapper")
	private final ZjMapper zjMapper = null;
	
	/**
	 * @Description 
	 * 添加常备材料入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//金额位数
		    int money_para = Integer.valueOf(MyConfig.getSysPara("04005").toString());
			//金额进数据库的时候小数点不根据参数控制，只有在显示的时候控制
			//int money_para =6;
			
			if(entityMap.get("in_date") == null || "".equals(entityMap.get("in_date"))){
				return "{\"error\":\"制单日期不能为空\",\"state\":\"false\"}";
			}
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			//entityMap.put("year", date[0]);
			//entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("in_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			//转换日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			 
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成常备材料入库单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MAT_IN_MAIN");
				String in_no = matCommonService.getMatNextNo(entityMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				entityMap.put("in_no", in_no);
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				double money_sum = 0;//记录明细总金额

				/*用于查询个体码----begin-----*/
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
				/*用于查询个体码----end-------*/
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				/*用于查询批次----end-----*/
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						//取出明细表ID（自增序列）
						detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						//detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						//由于有时会出现（单价 * 数量 != 金额）的情况，所以不直接取前台的金额放到后台计算
						detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(jsonObj.get("amount").toString())*Double.valueOf(jsonObj.get("price").toString()), money_para)));//金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_code")))){
							detailMap.put("cert_code", jsonObj.get("cert_code"));//注册证号名称
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_no")))){
							detailMap.put("disinfect_no", jsonObj.get("disinfect_no"));//灭菌批号
						}
						
						//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no",  jsonObj.get("batch_no").toString().trim());//批号
						}else{
							detailMap.put("batch_no", "-");//默认批号
						}
						/**********************自动生成批次-------begin--------*/
						invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
						//判断是否已经取出该批号的最大批次
						if(invBatchKeySnMap.get(invBatchKey) == null){
							//查询该批号最大批次
							batchSnMap.put("c_value", detailMap.get("inv_id"));//材料ID
							batchSnMap.put("c_value1", detailMap.get("batch_no"));//材料批号
							String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
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
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))){
							detailMap.put("sale_price",  jsonObj.get("sale_price"));//批发价
						}else{
							detailMap.put("sale_price",  "0");//批发价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_money")))){
							detailMap.put("sale_money",  jsonObj.get("sale_money"));//批发金额
						}else{
							detailMap.put("sale_money",  "0");//批发金额
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
							detailMap.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码（这里用个体码）
						}else{
							detailMap.put("sn", "-");
						}
						//之前的货位名称，如果存在在存当前的货位其他则为0 
						 if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id",  "0");//货位
						} 
						 
						  
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar")))){
							detailMap.put("is_per_bar",  jsonObj.get("is_per_bar"));//是否个体码
						}else{
							detailMap.put("is_per_bar", "0");//是否个体码(默认否)
						}
						//生成个体码--如果系统参数04010高值材料自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if("0".equals(detailMap.get("is_per_bar").toString()) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("04010"))))){
							//System.out.println(jsonObj.get("inv_name").toString()+"不生成个体码");
							if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
								detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
							}else{
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							} 
							//该条明细数据添加到List中
							detailList.add(detailMap);
						}else{
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Double.parseDouble(detailMap.get("amount").toString()) ; i++){
								detailMap.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) +i-1);//个体码,让每个材料的批次都不同
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(detailMap);
								//System.out.println(jsonObj.get("inv_name").toString()+"生成个体码");
								//System.out.println(bar_code);
								bar_code = matCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
								}
								//拆分数量和金额
								barMap.put("amount",  1);//数量
								if(detailMap.get("num_exchange") != null){
									barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
								}
								if(detailMap.get("num") != null){
									barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
								}
								barMap.put("amount_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("price").toString()), money_para)));
								barMap.put("sell_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("sell_price").toString()), money_para)));
								barMap.put("sale_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("sale_price").toString()), money_para)));
								barMap.put("allot_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("allot_price").toString()), money_para)));
								//barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("bar_code",  bar_code);//个体码
								//该条明细数据添加到List中
								detailList.add(barMap);
							}
						}
						/*订单关系-------begin--*/
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("order_rela")))){
							//System.out.println(jsonObj.get("order_rela").toString());
							Map<String,Object> orderRelaMap = new HashMap<String,Object>();
							orderRelaMap.put("group_id", entityMap.get("group_id"));
							orderRelaMap.put("hos_id", entityMap.get("hos_id"));
							orderRelaMap.put("copy_code", entityMap.get("copy_code"));
							orderRelaMap.put("in_id", entityMap.get("in_id"));
							orderRelaMap.put("in_no", entityMap.get("in_no"));
							orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("order_rela").toString())){
								orderRelaMap.put("order_id", m.get("order_id"));
								orderRelaMap.put("order_detail_id", m.get("order_detail_id"));
								orderRelaMap.put("in_amount", Float.parseFloat(m.get("in_amount").toString()));
								orderRelaMap.put("order_amount", Float.parseFloat(m.get("order_amount").toString()));
								orderRelaList.add(orderRelaMap);
							}
						}
						/*订单关系-------end--*/
					}
				}
				if(detailList.size() > 0){
					//更新个体码
					if(!init_bar_code.equals(bar_code)){
						barCodeMap.put("max_no", bar_code);
						matNoOtherMapper.updateMatNoOther(barCodeMap);
					}
					
					//添加发票号和发票时间
					if (entityMap.get("bill_date") != null && !"".equals(entityMap.get("bill_date").toString())) {
						entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
					}
					
					if (entityMap.get("bill_no") != null && !"".equals(entityMap.get("bill_no").toString())) {
						entityMap.put("bill_state", 1); //货票同到
					}else{
						entityMap.put("bill_state", 0); //货到票未到
					}
					
					
					//明细总金额
					entityMap.put("amount_money", money_sum);
					//新增入库主表数据
					matInCommonMapper.addMatInMain(entityMap);
					//新增入库明细数据
					matInCommonMapper.addMatInDetailBatch(detailList);
					//新增资金来源表
					entityMap.put("source_money", money_sum);
					matInCommonMapper.insertMatInResource(entityMap);
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						matStorageInMapper.insertOrderRela(orderRelaList);
					}
				}else{
					return "{\"error\":\"请选择材料\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStorageIn\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("in_id").toString()+","
			+"\"}";
	}
	/**
	 * @Description 
	 * 批量添加常备材料入库<BR> 
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
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatStorageIn\"}";
		}
		
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	}
	
		/**
	 * @Description 
	 * 更新常备材料入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		String orderNewIds = "";
		//金额位数
		int money_para = Integer.valueOf(MyConfig.getSysPara("04005").toString());
		//金额存数的时候默认不设置小数点几位
		//int money_para = 6;
		try {
			if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
				return "{\"error\":\"更新失败,单据状态不是未审核状态！\",\"state\":\"false\"}";
			}
			
			if(matCommonService.existsStateBatch("mat_in_main", "in_id", entityMap.get("in_id").toString(), "state", "1", null) > 0){
				return "{\"error\":\"更新失败,单据状态不是未审核状态！\",\"state\":\"false\"}";
			}
			
			
			//转日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(String.valueOf(entityMap.get("in_date")), "yyyy-MM-dd"));
			}
			
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMM" );
			//判断期初入库单的日期不能大于仓库的启用日期
			  Map<String, Object> info = matStoreModMapper.existsStoreMod(entityMap) ; 
			 int year_month =  Integer.parseInt(info.get("YEAR_MONTH").toString()) ; 
			 int in_date1  = Integer.parseInt(sdf.format(entityMap.get("in_date")).trim().toString()) ;
			 if(in_date1 < year_month){
		 		return "{\"error\":\"添加失败，制单日期必须大于仓库启用日期！\",\"state\":\"false\"}";
			 }

			
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				double money_sum = 0;//记录明细总金额
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
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				/*用于查询批次----end-----*/
				//修改明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				//List<Map<String,Object>> detailUpdateList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> detailAddList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> deliveryRelaList = new ArrayList<Map<String,Object>>();
				//StringBuffer detail_ids = new StringBuffer();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> detailMap=new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料变更号
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						//detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						//由于有时会出现（单价 * 数量 != 金额）的情况，所以不直接取前台的金额放到后台计算
						detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(jsonObj.get("amount").toString())*Double.valueOf(jsonObj.get("price").toString()), money_para)));//金额
						//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_code")))){
							detailMap.put("cert_code", jsonObj.get("cert_code"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_no")))){
							detailMap.put("disinfect_no", jsonObj.get("disinfect_no"));//灭菌批号
						}
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no",  jsonObj.get("batch_no").toString());//批号
						}else{
							detailMap.put("batch_no", "-");//默认批号
						}
						
						/**********************自动生成批次-------begin--------*/
						invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
						//判断是否已经取出该批号的最大批次
						if(invBatchKeySnMap.get(invBatchKey) == null){
							//查询该批号最大批次
							batchSnMap.put("c_value", detailMap.get("inv_id"));//材料ID
							batchSnMap.put("c_value1", detailMap.get("batch_no"));//材料批号
							String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
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
							detailMap.put("sell_price",  jsonObj.get("sell_price"));//零售单价
						}else{
							detailMap.put("sell_price",  "0");//零售单价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money"));//零售金额
						}else{
							detailMap.put("sell_money",  "0");//零售金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))){
							detailMap.put("sale_price",  jsonObj.get("sale_price"));//批发单价
						}else{
							detailMap.put("sale_price",  "0");//批发单价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_money")))){
							detailMap.put("sale_money",  jsonObj.get("sale_money"));//批发金额
						}else{
							detailMap.put("sale_money",  "0");//批发金额
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
							detailMap.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码
						}else{
							detailMap.put("sn", "-");
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id",  "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("serial_no")))){
							detailMap.put("serial_no",  jsonObj.get("serial_no"));//序列号
						}
						
						//明细表ID
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("in_detail_id")))){
							//生成个体码--如果系统参数04010高值材料自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
							if((!ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar"))) || "0".equals(String.valueOf(jsonObj.get("is_per_bar")))) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("04010"))))){
								if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
									detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
								}else{
									detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
								}
								detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
								//该条明细数据添加到List中
								detailAddList.add(detailMap);
							}else{
								//根据一码一物规则自动拆分数量并生成个体码
								for(int i = 1; i <= Double.parseDouble(detailMap.get("amount").toString()); i++){
									detailMap.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) +i-1);//个体码,让每个材料的批次都不同
									detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
									Map<String, Object> barMap = new HashMap<String, Object>();
									barMap.putAll(detailMap);
									bar_code = matCommonService.getNextBar_code(bar_code);
									if(i > 1){
										barMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
									}
									//拆分数量和金额
									barMap.put("amount",  1);//数量
									if(detailMap.get("num_exchange") != null){
										barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
									}
									if(detailMap.get("num") != null){
										barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
									}
									barMap.put("amount_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("price").toString()), money_para)));
									barMap.put("sell_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("sell_price").toString()), money_para)));
									barMap.put("sale_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("sale_price").toString()), money_para)));
									barMap.put("allot_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("allot_price").toString()), money_para)));
									//barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
									barMap.put("bar_code",  bar_code);//个体码
									//该条明细数据添加到List中
									detailAddList.add(barMap);
								}
							}
						}else{
							if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}else{
								detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
							}
							//detail_ids.append(jsonObj.get("in_detail_id").toString()).append(",");
							detailMap.put("in_detail_id", jsonObj.get("in_detail_id"));
							//detailUpdateList.add(detailMap);
							detailAddList.add(detailMap);
						}
						/*订单关系-------begin--*/
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("order_rela")))){
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.getString("order_rela"))){
								Map<String,Object> orderRelaMap = new HashMap<String,Object>();
								orderRelaMap.put("group_id", entityMap.get("group_id"));
								orderRelaMap.put("hos_id", entityMap.get("hos_id"));
								orderRelaMap.put("copy_code", entityMap.get("copy_code"));
								orderRelaMap.put("in_id", entityMap.get("in_id"));
								orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
								if(!orderNewIds.contains(m.get("order_id").toString())){
									orderNewIds=orderNewIds+m.get("order_id").toString()+",";
								}
								orderRelaMap.put("order_id", m.get("order_id"));
								orderRelaMap.put("order_detail_id", m.get("order_detail_id"));
								orderRelaMap.put("in_amount", Float.parseFloat(jsonObj.get("amount").toString()));
								//orderRelaMap.put("in_amount", m.get("in_amount"));
								orderRelaMap.put("order_amount", Float.parseFloat(m.get("order_amount").toString()));
								orderRelaList.add(orderRelaMap);
							}
						}
						/*订单关系-------end--*/
						
						/*送货单关系-------begin--*/
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("delivery_rela")))){
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.getString("delivery_rela"))){
								Map<String,Object> deliveryRelaMap = new HashMap<String,Object>();
								deliveryRelaMap.put("group_id", Integer.parseInt(entityMap.get("group_id").toString()));
								deliveryRelaMap.put("hos_id", Integer.parseInt(entityMap.get("hos_id").toString()));
								deliveryRelaMap.put("copy_code", entityMap.get("copy_code"));
								deliveryRelaMap.put("in_id", Integer.parseInt(entityMap.get("in_id").toString()));
								deliveryRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
								deliveryRelaMap.put("is_com", 0);
								/*if(!orderNewIds.contains(m.get("delivery_id").toString())){
									orderNewIds=orderNewIds+m.get("delivery_id").toString()+",";
								}*/
								deliveryRelaMap.put("delivery_id", Integer.parseInt(m.get("delivery_id").toString()));
								deliveryRelaMap.put("delivery_no", m.get("delivery_no").toString());
								deliveryRelaMap.put("inv_id", Integer.parseInt(m.get("inv_id").toString()));
								deliveryRelaMap.put("price", detailMap.get("price"));
								deliveryRelaMap.put("in_amount", Float.parseFloat(jsonObj.get("amount").toString()));
								deliveryRelaMap.put("delivery_amount", Float.parseFloat(m.get("delivery_amount").toString()));
								deliveryRelaList.add(deliveryRelaMap);
							}
						}
						/*订单关系-------end--*/
					}
				}
				
				if(detailAddList.size() > 0){
					//更新个体码
					if(!init_bar_code.equals(bar_code)){
						barCodeMap.put("max_no", bar_code);
						matNoOtherMapper.updateMatNoOther(barCodeMap);
					}

					//添加发票号和发票时间
					if (entityMap.get("bill_date") != null && !"".equals(entityMap.get("bill_date").toString())) {
						entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
					}
					
					if (entityMap.get("bill_no") != null && !"".equals(entityMap.get("bill_no").toString())) {
						entityMap.put("bill_state", 1); //货票同到
					}else{
						entityMap.put("bill_state", 0); //货到票未到
					}

					//明细总金额
					entityMap.put("amount_money", money_sum);
					//修改入库主表数据
					matInCommonMapper.updateMatInMain(entityMap);
					/* 修改明细为先删除所有再插入
					Map<String,Object> deleteMap = new HashMap<String,Object>();
					deleteMap.put("group_id", entityMap.get("group_id"));
					deleteMap.put("hos_id", entityMap.get("hos_id"));
					deleteMap.put("copy_code", entityMap.get("copy_code"));
					deleteMap.put("in_id", entityMap.get("in_id"));//主表ID
					//删除页面上删掉的明细记录
					if(detail_ids.length() > 0){
						deleteMap.put("detail_ids", detail_ids.substring(0,detail_ids.length()-1).toString());//明细IDS
						matInCommonMapper.deleteMatInDetailForUpdate(deleteMap);
					}else{
						matInCommonMapper.deleteMatInDetail(deleteMap);
					}
					//添加新明细记录
					if(detailAddList.size() > 0){
						matInCommonMapper.addMatInDetailBatch(detailAddList);
					}
					//修改明细数据
					if(detailUpdateList.size() > 0){
						matInCommonMapper.updateMatInDetail(detailUpdateList);
					}
					*/
					matInCommonMapper.deleteMatInDetail(entityMap);
					matInCommonMapper.addMatInDetailBatch(detailAddList);
					//修改资金来源表
					entityMap.put("source_money", money_sum);
					matInCommonMapper.updateMatInResource(entityMap);
					
					//获取删除的order_id
					if(!"".equals(orderNewIds) && orderNewIds!=null){
						entityMap.put("orderNewIds", orderNewIds.substring(0, orderNewIds.length()-1));
						String oldOrderId = matStorageInMapper.queryDeleteOldIds(entityMap);
						if(oldOrderId!=null && !"".equals(oldOrderId)){
							entityMap.put("oldOrderId", oldOrderId);
						}
					}
					
					//重新建立入库单订单关系表
					matStorageInMapper.deleteOrderRela(entityMap);
					
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						matStorageInMapper.insertOrderRela(orderRelaList);
						/*//修改完后查询是否改变，改变后要修改状态
						entityMap.put("state", 5);
						String orderIds = matStorageInMapper.queryOrderIdById(entityMap);
						if( orderIds!=null && !"".equals(orderIds)){
							//更新状态  state = 2
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("group_id",entityMap.get("group_id"));
							map.put("hos_id", entityMap.get("hos_id"));
							map.put("copy_code", entityMap.get("copy_code"));
							map.put("state", "2");
							map.put("orderIds", orderIds);
							matStorageInMapper.updateOrderStates(map);		
						}*/
					}
					
					//对配送单关系表进行操作
					if(deliveryRelaList.size() > 0){
						//删除之前记录一下修改的信息
						String deliveryOrderIds =matStorageInMapper.queryDeliveryOrderIds(deliveryRelaList); 
						if(deliveryOrderIds!=null && !"".equals(deliveryOrderIds)){
							entityMap.put("deliveryOrderIds", deliveryOrderIds);
							//更新订单状态
							//matStorageInMapper.updateOrderStateByDelivery(entityMap);
						}
						
						//先删除
						entityMap.put("is_com", 0);
						matStorageInMapper.deleteDeliveryInRelaByInId(entityMap);
						//在插入
						matStorageInMapper.insertBatchDeliveryInRela(deliveryRelaList);
					}
					
				}else{
					return "{\"error\":\"请选择材料\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"更新失败，明细数据为空\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatStorageIn\"}";
		}		
		
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
	
	
	/**
	 * @Description 
	 * 批量更新常备材料入库，无此业务<BR> 
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
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatStorageIn\"}";
		}	

		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 删除常备材料入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			//状态不是新建不能删除
			if(matCommonService.existsStateBatch("mat_in_main", "in_id", entityMap.get("in_id").toString(), "state", "1", null) > 0){
				return "{\"error\":\"更新失败,单据状态不是未审核状态！\",\"state\":\"false\"}";
			}
			//期初记账状态不是0的不能删除，从页面判断
			//删除明细
			matInCommonMapper.deleteMatInDetail(entityMap);
			//删除主表 
			matInCommonMapper.deleteMatInMain(entityMap);
			//删除资金来源表
			matInCommonMapper.deleteMatInResource(entityMap);
			//删除订单对应关系表
			matStorageInMapper.deleteOrderRela(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatStorageIn\"}";
		}	

		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
  }
    
	/**
	 * @Description 
	 * 批量删除常备材料入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//状态不是新建不能删除
			if(matCommonService.existsStateBatch("mat_in_main", "in_id", null, "state", "1", entityList) > 0){
				return "{\"error\":\"删除失败,存在未审核单据请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			//期初记账状态不是0的不能删除，从页面判断
			//批量删除明细表
			matInCommonMapper.deleteMatInDetailBatch(entityList);
			//批量删除主表
			matInCommonMapper.deleteMatInMainBatch(entityList);
			//批量删除资金来源表
			matInCommonMapper.deleteMatInResourceBatch(entityList);
			
			//更新送货单状态
			String orderIdByDelivery = matStorageInMapper.queryMatOrderIdByDelivery(entityList);
			if( orderIdByDelivery!=null && !"".equals(orderIdByDelivery)){
				Map<String,Object> mapD = new HashMap<String,Object>();
				mapD.put("group_id", SessionManager.getGroupId());
				mapD.put("hos_id", SessionManager.getHosId());
				mapD.put("copy_code", SessionManager.getCopyCode());
				mapD.put("state", "2");
				mapD.put("orderIds", orderIdByDelivery);
				matStorageInMapper.updateOrderStates(mapD);
			}
			//删除送货单关系表
			matInCommonMapper.deleteMatInAmountBatch(entityList);
			
		  	List<Map<String,Object>>disList =  matInCommonMapper.queryMatInDis(entityList);
		  	if(disList != null && disList.size() > 0){
				//删除省平台配送关系表
				matInCommonMapper.deleteMatInDis(disList);
		  	}
			//更新订单状态
			/*String orderIds = matStorageInMapper.queryMatOrderId(entityList);
			if( orderIds!=null && !"".equals(orderIds)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				if(MyConfig.getSysPara("04033") != null && "1".equals(MyConfig.getSysPara("04033"))){
					map.put("state", "2");
				}else{
					map.put("state", "1");
				}
				map.put("orderIds", orderIds);
				matStorageInMapper.updateOrderStates(map);
			}*/
			//更新订单状态
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			matStorageInMapper.updateOrderStateByInRela(map, entityList);
			
			//批量删除订单对应关系表
			matStorageInMapper.deleteOrderRelaBatch(entityList);
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatStorageIn\"}";
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
	public String copyMatStorageInBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、材料Id
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
			List<Map<String, Object>> mainList = (List<Map<String, Object>>)matInCommonMapper.queryMatInMainByIds(inMap);
			List<Map<String, Object>> detailList = (List<Map<String, Object>>)matInCommonMapper.queryMatInDetailByIds(inMap);
			
			//存放组装数据的List
			List<Map<String, Object>> insertMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> insertDetailList = new ArrayList<Map<String,Object>>();
			double money_sum = 0;
			
			/*复制操作中的固定值-----begin--------*/
			Date date = new Date();
			String[] dates = DateUtil.dateToString(date, "yyyy-MM-dd").split("-");
			String user_id = SessionManager.getUserId();
			String old_id;
			/*复制操作中的固定值-----end--------*/
			
			/*获取个体码----------begin----------*/
			Map<String,Object> barCodeMap = new HashMap<String,Object>();
			barCodeMap.put("group_id", inMap.get("group_id"));
			barCodeMap.put("hos_id", inMap.get("hos_id"));
			barCodeMap.put("type_code", 1);
			String bar_code = matNoOtherMapper.queryMatNoOther(barCodeMap);//获取当前个体码
			//如果不存在则插入
			if(bar_code == null || "".equals(bar_code)){
				bar_code = "000000000000";
				barCodeMap.put("max_no", bar_code);
				matNoOtherMapper.insertMatNoOther(barCodeMap);
			}
			//记录初始条形码
			String init_bar_code = bar_code;
			/*获取个体码----------end------------*/

			/*用于查询批次----begin-----*/
			Map<String,Object> batchSnMap = new HashMap<String,Object>();
			batchSnMap.put("group_id", inMap.get("group_id"));
			batchSnMap.put("hos_id", inMap.get("hos_id"));
			batchSnMap.put("copy_code", inMap.get("copy_code"));
			batchSnMap.put("c_max", "batch_sn");
			batchSnMap.put("table_name", "mat_in_detail");
			batchSnMap.put("c_name", "inv_id");//查询批次所用
			batchSnMap.put("c_name1", "batch_no");//查询批次所用
			//存放相同材料批号的最大批次号
			Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
			String invBatchKey = "";
			/*用于查询批次----end-----*/
			//循环组装数据
			for(Map<String, Object> mainMap : mainList){ 
				old_id = mainMap.get("in_id").toString();//记录旧的in_id用于筛选明细
				mainMap.put("in_id", matInCommonMapper.queryMatInMainSeq());//替换旧的主表ID（自增序列）
				mainMap.put("in_date", date);//制单日期
				mainMap.put("year", dates[0]);//年份
				mainMap.put("month", dates[1]);//月份
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("maker", user_id);//制单人
				mainMap.put("in_date", date);//制单日期
				mainMap.put("state", 1);//状态（新建状态）
				mainMap.put("brief", "复制"+mainMap.get("in_no")+"入库单");
				//重新获取单据号
				mainMap.put("table_code", "MAT_IN_MAIN");
				String in_no = matCommonService.getMatNextNo(mainMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				mainMap.put("in_no", in_no);
				
				/*处理明细-------begin-------*/
				for(Map<String, Object> detailMap : detailList){
					/*判断是否为该主表的明细*/
					if(old_id.equals(detailMap.get("in_id").toString())){
						detailMap.put("in_id", mainMap.get("in_id"));//替换旧的主表ID
						detailMap.put("in_no", mainMap.get("in_no"));
						//money_sum = money_sum + Double.valueOf(detailMap.get("amount_money").toString());
						money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
						
						detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//替换旧表ID（自增序列）

						/**********************自动生成批次-------begin--------*/
						invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
						//判断是否已经取出该批号的最大批次
						if(invBatchKeySnMap.get(invBatchKey) == null){
							//查询该批号最大批次
							batchSnMap.put("c_value", detailMap.get("inv_id"));//材料ID
							batchSnMap.put("c_value1", detailMap.get("batch_no"));//材料批号
							String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
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
						
						/**********************处理个体码-------begin-------*/
						//生成个体码--如果系统参数04010高值材料自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if(("1".equals(String.valueOf(detailMap.get("is_per_bar")))) || ("1".equals(String.valueOf(detailMap.get("is_highvalue"))) && "1".equals(String.valueOf(MyConfig.getSysPara("04010"))))){
							
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(detailMap);
								//System.out.println(jsonObj.get("inv_name").toString()+"生成个体码");
								//System.out.println(bar_code);
								bar_code = matCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
								}
								//拆分数量和金额
								barMap.put("amount",  1);//数量
								if(detailMap.get("num_exchange") != null){
									barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
								}
								if(detailMap.get("num") != null){
									barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
								}
								barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("bar_code",  bar_code);//个体码
								//该条明细数据添加到List中
								insertDetailList.add(barMap);
							}
						}else{
							//添加到明细表新增的list中
							insertDetailList.add(detailMap);
						}
						/**********************处理个体码-------end---------*/
					}
				}
				//明细总金额
				mainMap.put("amount_money", money_sum);
				//资金来源
				mainMap.put("source_money", money_sum);
				//单据来源
				mainMap.put("come_from", "1");
				//添加到主表新增的list中
				insertMainList.add(mainMap);
			}
			
			//更新个体码
			if(!init_bar_code.equals(bar_code)){
				barCodeMap.put("max_no", bar_code);
				matNoOtherMapper.updateMatNoOther(barCodeMap);
			}
			//批量新增主表数据
			matInCommonMapper.addMatInMainBatch(insertMainList);
			//批量新增明细表数据
			matInCommonMapper.addMatInDetailBatch(insertDetailList);
			//批量处理入库资金来源
			matInCommonMapper.insertMatInResourceBatch(insertMainList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 copyMatStorageInBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 
	 * 批量冲销<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String offsetMatStorageInBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、材料Id
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
			List<Map<String, Object>> mainList = (List<Map<String, Object>>)matInCommonMapper.queryMatInMainByIds(inMap);
			List<Map<String, Object>> detailList = (List<Map<String, Object>>)matInCommonMapper.queryMatInDetailByIds(inMap);
			
			//存放组装数据的List
			List<Map<String, Object>> insertMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> insertDetailList = new ArrayList<Map<String,Object>>();
			double money_sum = 0;
			/*冲销操作中的固定值-----begin--------*/
			Date date = new Date();
			String[] dates = DateUtil.dateToString(date, "yyyy-MM-dd").split("-");
			String user_id = SessionManager.getUserId();
			String old_id;
			/*冲销操作中的固定值-----end--------*/
			
			//循环组装数据
			for(Map<String, Object> mainMap : mainList){ 
				old_id = mainMap.get("in_id").toString();//记录旧的in_id用于筛选明细
				mainMap.put("in_id", matInCommonMapper.queryMatInMainSeq());//替换旧的主表ID（自增序列）
				mainMap.put("in_date", date);//制单日期
				mainMap.put("year", dates[0]);//年份
				mainMap.put("month", dates[1]);//月份
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("maker", user_id);//制单人
				mainMap.put("in_date", date);//制单日期
				mainMap.put("state", 1);//状态（新建状态）
				/******20191226*****hsy修改****冲账单据业务类型不变****/
				//mainMap.put("bus_type_code", "12");//业务类型(冲销为退货)
				mainMap.put("brief", "冲销"+mainMap.get("in_no")+"入库单");
				//重新获取单据号
				mainMap.put("table_code", "MAT_IN_MAIN");
				String in_no = matCommonService.getMatNextNo(mainMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				mainMap.put("in_no", in_no);
				
				/*处理明细-------begin-------*/
				for(Map<String, Object> detailMap : detailList){
					/*判断是否为该主表的明细*/
					if(old_id.equals(detailMap.get("in_id").toString())){
						detailMap.put("in_id", mainMap.get("in_id"));//替换旧的主表ID
						detailMap.put("in_no", mainMap.get("in_no"));
						detailMap.put("store_id", mainMap.get("store_id"));  //用于库存判断
						detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//替换旧表ID（自增序列）
						detailMap.put("amount", -1 * Float.parseFloat(detailMap.get("amount").toString()));//冲销数量为原来的负数
						detailMap.put("amount_money", -1 * Double.parseDouble(detailMap.get("amount_money").toString()));//冲销金额为原来的负数
						//money_sum = money_sum + -1 * Double.parseDouble(detailMap.get("amount_money").toString());
						money_sum = NumberUtil.add(Double.valueOf(money_sum), -1 *Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
						
						detailMap.put("sell_money", -1 * Double.parseDouble(detailMap.get("sell_money").toString()));//冲销金额为原来的负数
						
						//添加到明细表新增的list中
						insertDetailList.add(detailMap);
					}
				}
				//明细总金额
				mainMap.put("amount_money", money_sum);
				//资金来源
				mainMap.put("source_money", money_sum);
				//添加到主表新增的list中
				insertMainList.add(mainMap);
			}
			//冲账操作由于数量为负数所以走退货的库存判断
			String invEnough = matStorageBackMapper.existsMatBackStockInvIsEnough(insertDetailList);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			}
			//批量新增主表数据
			matInCommonMapper.addMatInMainBatch(insertMainList);
			//批量新增明细表数据
			matInCommonMapper.addMatInDetailBatch(insertDetailList);
			//批量新增资金来源表数据
			matInCommonMapper.insertMatInResourceBatch(insertMainList);
			//修改原单据冲账状态
			matInCommonMapper.updateMatInMainRaState(inMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 offsetMatStorageInBatch\"}";
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
	public String auditMatStorageIn(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//状态判断
			if(matCommonService.existsStateBatch("mat_in_main", "in_id", entityMap.get("in_id").toString(), "state", "1", null) > 0){
				return "{\"error\":\"审核失败,单据状态不是未审核状态！\",\"state\":\"false\"}";
			}
			//批量审核
			matStorageInMapper.auditOrUnAudit(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMatStorageIn\"}";
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
	public String unAuditMatStorageIn(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//状态判断
			if(matCommonService.existsStateBatch("mat_in_main", "in_id", entityMap.get("in_id").toString(), "state", "2", null) > 0){
				return "{\"error\":\"消审失败,单据状态不是审核状态！\",\"state\":\"false\"}";
			}
			//批量消审
			matStorageInMapper.auditOrUnAudit(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMatStorageIn\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String confirmMatStorageIn(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
			String confirm_date=DateUtil.getSysDate();//获取服务器当前日期
			if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString())){
				confirm_date = entityMap.get("confirm_date").toString();
			}
			entityMap.put("confirm_date",confirm_date);
			
			//操作用户
			entityMap.put("user_id", SessionManager.getUserId());
			//因存储过程为批量确认故需要该参数
			entityMap.put("in_ids", entityMap.get("in_id"));
			
			//校验入库单状态
			int flag = matStorageInMapper.existsMatInStateForConfirm(entityMap);
			if(flag != 0){
				return "{\"error\":\"单据已确认，请勿重复操作！\",\"state\":\"false\"}";
			}
			
			//批量入库确认
			matInCommonMapper.confirmCommonIn(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 confirmMatStorageIn\"}";
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
	public String auditMatStorageInBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//状态判断
			if(matCommonService.existsStateBatch("mat_in_main", "in_id", null, "state", "1", entityList) > 0){
				return "{\"error\":\"审核失败,存在非未审核单据请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			//批量审核
			matStorageInMapper.auditOrUnAuditBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMatStorageInBatch\"}";
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
	public String unAuditMatStorageInBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//状态判断
			if(matCommonService.existsStateBatch("mat_in_main", "in_id", null, "state", "2", entityList) > 0){
				return "{\"error\":\"消审失败,存在非审核单据请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			//批量消审
			matStorageInMapper.auditOrUnAuditBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMatStorageInBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String confirmMatStorageInBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、材料Id
			String copy_code = "", in_ids = "", confirm_date="",is_store="";
			Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
			confirm_date=DateUtil.getSysDate();//获取服务器当前日期
			//取得所有in_id组装map
			for(Map<String, Object> m : entityList){
				if(group_id == null){
					group_id = Integer.parseInt(m.get("group_id").toString());
				}
				if(hos_id == null){
					hos_id = Integer.parseInt(m.get("hos_id").toString());
				}
				if("".equals(copy_code)){
					copy_code = m.get("copy_code").toString();
				}
				if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString()) &&"".equals(confirm_date)){
					confirm_date = m.get("confirm_date").toString();
				}
			/*	if("".equals(is_store)){
					is_store = m.get("is_store").toString();
				}*/
				in_ids = in_ids + m.get("in_id").toString() + ",";
			}
			
			/*
			String falg = "" ;
			for (Map<String, Object> map : entityList) {
				map.put("group_id", group_id);
				map.put("hos_id", hos_id);
				map.put("copy_code", copy_code);
				map.put("year", confirm_date.substring(0, 4));
				map.put("month", confirm_date.substring(5, 7));
				if(is_store.equals("1")){
					map.put("store_id", map.get("store_id"));
					int MatCount = matStorageInMapper.queryMatTermendStoreByYearMonth(map);
					if(MatCount!=0){
						falg = "所选单据中,单据号"+map.get("in_no")+"的确认日期小于结账日期，不能做确认入库操作";
					}
				}else{
					Map<String,Object> MatCount = matStorageInMapper.queryAccYearMonthByYearMonth(map);
					if(MatCount!=null){
						falg = "所选单据中,单据号"+map.get("in_no")+"的确认日期小于结账日期，不能做确认入库操作";
					}
				}
			}
			if(!"".equals(falg)){
				return "{\"error\":\"确认失败! "+falg+"\"}";
			}*/
			
			
			
			//组装数据
			inMap.put("group_id", group_id);
			inMap.put("hos_id", hos_id);
			inMap.put("copy_code", copy_code);
			inMap.put("user_id", SessionManager.getUserId());
			inMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			inMap.put("confirm_date", confirm_date);
			
			
			//校验入库单状态
			int flag = matStorageInMapper.existsMatInStateForConfirm(inMap);
			if(flag != 0){
				return "{\"error\":\"所选单据中含已确认单据，请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			
			//批量入库确认
			matInCommonMapper.confirmCommonIn(inMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 confirmMatStorageInBatch\"}";
		}	

		return inMap.get("msg") == null ? "" : inMap.get("msg").toString();
	}
	
	/**
	 * @Description 
	 * 添加常备材料入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象常备材料入库
		MatInMain matInMain = queryByCode(entityMap);

		if (matInMain != null) {

			int state = matStorageInMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matStorageInMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatStorageIn\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集常备材料入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			List<?> list = matStorageInMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matStorageInMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 入库明细查询
	 */
	@Override
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matStorageInMapper.queryDetails(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matStorageInMapper.queryDetails(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象常备材料入库<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matInCommonMapper.queryMatInMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象常备材料入库<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<?> list = matInCommonMapper.queryMatInDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 获取常备材料入库<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatStorageIn
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matStorageInMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 
	 * 订单结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String queryOrder(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matStorageInMapper.queryOrder(entityMap));
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matStorageInMapper.queryOrder(entityMap, rowBounds));
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * @Description 
	 * 订单明细结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryOrderDetail(List<Map<String, Object>> entityMap) throws DataAccessException {
		//集团、单位变量
		Integer group_id = null, hos_id = null;
		//帐套、入库单Id、材料Id
		String copy_code = "", order_ids = "", order_detail_ids="";
		Map<String, Object> orderMap = new HashMap<String, Object>();
		//取得所有in_id组装map
		for(Map m : entityMap){
			if(group_id == null){
				group_id = Integer.parseInt(m.get("group_id").toString());
			}
			if(hos_id == null){
				hos_id = Integer.parseInt(m.get("hos_id").toString());
			}
			if("".equals(copy_code)){
				copy_code = m.get("copy_code").toString();
			}
			//order_ids = order_ids + m.get("in_id").toString() + ",";
			order_detail_ids = order_detail_ids + m.get("order_detail_id").toString() + ",";
		}
		//组装数据
		orderMap.put("group_id", group_id);
		orderMap.put("hos_id", hos_id);
		orderMap.put("copy_code", copy_code);
		//orderMap.put("order_ids", order_ids.substring(0, order_ids.length()-1));
		orderMap.put("order_detail_ids", order_detail_ids.substring(0, order_detail_ids.length()-1));
		List<Map<String,Object>> list = matStorageInMapper.queryOrderDetail(orderMap);
		return ChdJson.toJsonLower(list); 
	}
	
	@Override
	public String queryOrderDetailNew(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matStorageInMapper.queryOrderDetailNew(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matStorageInMapper.queryOrderDetailNew(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 配套表结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatch(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matStorageInMapper.queryMatch(entityMap);
		
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 
	 * 协议结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryProtocol(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matStorageInMapper.queryProtocol(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 获取下一张或上一张入库单ID
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryMatStorageInBeforeOrNext(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			//定义入库单ID
			String in_id = "";
			
			//上一张
			if("before".equals(entityMap.get("type").toString())){
				in_id = matInCommonMapper.queryMatInBefore(entityMap);
				if(in_id == null || "".equals(in_id) || "0".equals(in_id)){
					return "{\"msg\":\"已经是第一张单据了！\",\"state\":\"false\"}";
				}
			}else if("next".equals(entityMap.get("type").toString())){//下一张
				in_id = matInCommonMapper.queryMatInNext(entityMap);
				if(in_id == null || "".equals(in_id) || "0".equals(in_id)){
					return "{\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"操作失败 页面参数异常 请联系管理员！方法queryMatStorageInBeforeOrNext\"}";
			}
			
			return "{\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				in_id+","
				+"\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatStorageInBeforeOrNext\"}";
		}	
	}

	/**
	 * @Description 
	 * 资金来源<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public MatInResource queryMatInResource(Map<String, Object> entityMap) throws DataAccessException {
		
		return matInCommonMapper.queryMatInResource(entityMap);
	}

	/**
	 * @Description 
	 * 资金来源结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public List<MatInResource> queryMatInResourceList(Map<String, Object> entityMap) throws DataAccessException {
		
		return matInCommonMapper.queryMatInResourceList(entityMap);
	}
	
	//入库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMatInByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			entityMap.put("hos_name", SessionManager.getHosName());
			//查询入库主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=matStorageInMapper.queryMatInPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=matStorageInMapper.queryMatInPrintTemlateByDetail(entityMap);
				
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				Map<String,Object> map=matStorageInMapper.queryMatInPrintTemlateByMain(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=matStorageInMapper.queryMatInPrintTemlateByDetail(entityMap);
				
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		
	}
	//新版打印  调用的方法
	@Override
	public Map<String, Object> queryMatInByPrintTemlateNewPrint(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			entityMap.put("show_history", MyConfig.getSysPara("03001")); 
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatStorageInMapper matStorageInMapper = (MatStorageInMapper)context.getBean("matStorageInMapper");
			
			boolean is_type = false;  //是否按类别打印
			if(entityMap.get("is_type") != null && "1".equals(entityMap.get("is_type"))){
				is_type = true;
			} 
			
			entityMap.put("hos_name", SessionManager.getHosName()); 
			if(entityMap.get("print_title") == null || "".equals(entityMap.get("print_title"))){
				entityMap.put("print_title", "材料入库单");
			}
			
			//查询入库主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map = matStorageInMapper.queryMatInPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list = null; 
				if(is_type){
					list = matStorageInMapper.queryMatInPrintTemlateByDetailType(entityMap);
				}else{
					list = matStorageInMapper.queryMatInPrintTemlateByDetail(entityMap);
				}
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ 
				
				Map<String,Object> map = matStorageInMapper.queryMatInPrintTemlateByMain(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list = null;
				if(is_type){
					list = matStorageInMapper.queryMatInPrintTemlateByDetailType(entityMap);
				}else{
					list = matStorageInMapper.queryMatInPrintTemlateByDetail(entityMap);
				}
				
			
				reMap.put("main", map);
				
				reMap.put("detail", list);
				
				return reMap;
				
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		
	}
	/**
	 * 基于条码模板的条码打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatInBarcodeByPrintTemlateNewPrint(Map<String, Object> entityMap)throws DataAccessException {
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
			String inDetailIdStr=(String) entityMap.get("inDetailIdStr");
			Map<String,Object> reMap=new HashMap<String,Object>();
			if(inDetailIdStr!=null && !"".equals(inDetailIdStr)){
				String[] in_detail_idArray=inDetailIdStr.split(",");
				List<String> in_detail_idList=new ArrayList<String>();
				for (String string : in_detail_idArray) {
					in_detail_idList.add(string);
				}
				entityMap.put("in_detail_idList", in_detail_idList);
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				MatStorageInMapper matStorageInMapper = (MatStorageInMapper)context.getBean("matStorageInMapper");
				List<Map<String,Object>> list=matStorageInMapper.queryMatInBarcodeByPrintTemlateNewPrint(entityMap);
				reMap.put("main", list); 
			}
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		
	} 
	
	//合并打印
	@Override
	public String querymergePrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
		
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = matStorageInMapper.querymergePrint(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list =  matStorageInMapper.querymergePrint(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}
	
	/**
	 * 导入订单保存
	 */
	@Override
	public String addInByOrder(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005").toString());
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			if(entityMap.get("in_date") == null || "".equals(entityMap.get("in_date"))){
				return "{\"error\":\"制单日期不能为空\",\"state\":\"false\"}";
			}
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			//entityMap.put("year", date[0]);
			//entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("in_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			//转换日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成常备材料入库单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MAT_IN_MAIN");
				String in_no = matCommonService.getMatNextNo(entityMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				entityMap.put("in_no", in_no);
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
			//组装明细
			if(entityMap.get("jsonArray") != null && !"[]".equals(String.valueOf(entityMap.get("jsonArray")))){
				double money_sum = 0;//记录明细总金额

				/*用于查询个体码----begin-----*/
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
				/*用于查询个体码----end-------*/
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				/*用于查询批次----end-----*/
				//保存明细数据
				JSONArray json = JSONArray.parseArray(entityMap.get("jsonArray").toString());
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						String cert_code=supDeliveryMainMapper.queryCertCode(jsonObj);
						detailMap.put("cert_code", cert_code);
						//取出明细表ID（自增序列）
						detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						//detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						//由于有时会出现（单价 * 数量 != 金额）的情况，所以不直接取前台的金额放到后台计算
						detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(jsonObj.get("amount").toString())*Double.valueOf(jsonObj.get("price").toString()), money_para)));//金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_no")))){
							detailMap.put("disinfect_no", jsonObj.get("disinfect_no"));//灭菌批号
						}
						
						//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no",  jsonObj.get("batch_no"));//批号
						}else{
							detailMap.put("batch_no", "-");//默认批号
						}
						/**********************自动生成批次-------begin--------*/
						invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
						//判断是否已经取出该批号的最大批次
						if(invBatchKeySnMap.get(invBatchKey) == null){
							//查询该批号最大批次
							batchSnMap.put("c_value", detailMap.get("inv_id"));//材料ID
							batchSnMap.put("c_value1", detailMap.get("batch_no"));//材料批号
							String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
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
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))){
							detailMap.put("sale_price",  jsonObj.get("sale_price"));//批发价
						}else{
							detailMap.put("sale_price",  "0");//批发价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_money")))){
							detailMap.put("sale_money",  jsonObj.get("sale_money"));//批发金额
						}else{
							detailMap.put("sale_money",  "0");//批发金额
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
							detailMap.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码（这里用个体码）
						}else{
							detailMap.put("sn", "-");
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id",  "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar")))){
							detailMap.put("is_per_bar",  jsonObj.get("is_per_bar"));//是否个体码
						}else{
							detailMap.put("is_per_bar", "0");//是否个体码(默认否)
						}
						//生成个体码--如果系统参数04010高值材料自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if("0".equals(detailMap.get("is_per_bar").toString()) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("04010"))))){
							//System.out.println(jsonObj.get("inv_name").toString()+"不生成个体码");
							if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
								detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
							}else{
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}
							//该条明细数据添加到List中
							detailList.add(detailMap);
						}else{
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(detailMap);
								//System.out.println(jsonObj.get("inv_name").toString()+"生成个体码");
								//System.out.println(bar_code);
								bar_code = matCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
								}
								//拆分数量和金额
								barMap.put("amount",  1);//数量
								if(detailMap.get("num_exchange") != null){
									barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
								}
								if(detailMap.get("num") != null){
									barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
								}
								barMap.put("amount_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("price").toString()), money_para)));
								//barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("bar_code",  bar_code);//个体码
								//该条明细数据添加到List中
								detailList.add(barMap);
							}
						}
						/*订单关系-------begin--*/
						
							Map<String,Object> orderRelaMap = new HashMap<String,Object>();
							orderRelaMap.put("group_id", entityMap.get("group_id"));
							orderRelaMap.put("hos_id", entityMap.get("hos_id"));
							orderRelaMap.put("copy_code", entityMap.get("copy_code"));
							orderRelaMap.put("in_id", entityMap.get("in_id"));
							orderRelaMap.put("in_no", entityMap.get("in_no"));
							orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
							orderRelaMap.put("order_id", jsonObj.get("order_id"));
							orderRelaMap.put("order_detail_id", jsonObj.get("order_detail_id"));
							orderRelaMap.put("in_amount", Float.parseFloat(jsonObj.get("amount").toString()));
							if(jsonObj.containsKey("already_amount")){
								if(Float.parseFloat(jsonObj.get("already_amount").toString())>0){
									orderRelaMap.put("order_amount", Float.parseFloat(jsonObj.get("order_amount").toString())+Float.parseFloat(jsonObj.get("already_amount").toString()));
								}else{
									orderRelaMap.put("order_amount", Float.parseFloat(jsonObj.get("order_amount").toString()));
								}
							}else{
								orderRelaMap.put("order_amount", Float.parseFloat(jsonObj.get("order_amount").toString()));
							}
							
							
							orderRelaList.add(orderRelaMap);
							
						
						/*订单关系-------end--*/
					}
				}
				if(detailList.size() > 0){
					//更新个体码
					if(!init_bar_code.equals(bar_code)){
						barCodeMap.put("max_no", bar_code);
						matNoOtherMapper.updateMatNoOther(barCodeMap);
					}
					//明细总金额
					entityMap.put("amount_money", money_sum);
					//新增入库主表数据
					matInCommonMapper.addMatInMain(entityMap);
					//新增入库明细数据
					matInCommonMapper.addMatInDetailBatch(detailList);
					//新增资金来源表
					entityMap.put("source_money", money_sum);
					matInCommonMapper.insertMatInResource(entityMap);
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						matStorageInMapper.insertOrderRela(orderRelaList);
						
						List<Map<String,Object>> entityList = new ArrayList<Map<String,Object>>();
						entityList.add(entityMap);
						matStorageInMapper.updateOrderStateByInRela(entityMap, entityList);
					}
					
				}else{
					return "{\"error\":\"请选择材料\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStorageIn\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("in_id").toString()+","
			+"\"}";
	}
	
	//更新订单状态
	@Override
	public void updateMatOrderState(Map<String, Object> entityMap) throws DataAccessException {
		//若有删除的order_id  直接更新
		if(entityMap.containsKey("oldOrderId")){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("group_id",entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("state", "2");
			map.put("orderIds", entityMap.get("oldOrderId"));
			matStorageInMapper.updateOrderStates(map);
			entityMap.remove("in_id");
		}
		
		String orderIds  = matStorageInMapper.queryOrderIds(entityMap);
		if( orderIds!=null && !"".equals(orderIds)){
			//更新状态  state = 5
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("group_id",entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			
			
			if(entityMap.get("state").equals("2")){
				map.put("state", "5");
			}
			if(entityMap.get("state").equals("5")){
				map.put("state", "2");
			}
			
			map.put("orderIds", orderIds);
			matStorageInMapper.updateOrderStates(map);
		}
		
		if(entityMap.containsKey("deliveryOrderIds")){
			entityMap.put("is_com", 0);
			List<Map<String,Object>> list = (List<Map<String,Object>>)matStorageInMapper.querySupDeliveryInOrderIsExists(entityMap);
			if(list.size()>0){
				entityMap.put("state", 3);
			}else{
				entityMap.put("state", 2);
			}
			
			matStorageInMapper.updateOrderStateByDelivery(entityMap);		
		}
		
	}
	
	@Override
	public List<Map<String,Object>> queryOrderRelaExists(Map<String, Object> entityMap) throws DataAccessException {
		return matStorageInMapper.queryOrderRelaExists(entityMap);
	}
	
	/**
	 * @Description 冲账 明细表数据<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String mergeOffsetMatInMain(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list= (List<Map<String, Object>>) matStorageInMapper.mergeOffsetMatInMain(entityMap);
		return  ChdJson.toJsonLower(list);  
	}
	/**
	 * @Description 冲账  主表数据<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
    @Override
	public Map<String, Object> queryMatInOffsetMainByCode(Map<String, Object> entityMap) throws DataAccessException {
		return JsonListMapUtil.toMapLower((Map<String, Object>) matStorageInMapper.queryMatInOffsetMainByCode(entityMap));
	}
	
	@Override
	public String queryModeStartDate(Map<String, Object> entityMap) throws DataAccessException {
		String date = "";
		date = matStorageInMapper.queryModeStartDate(entityMap);
		return  date;  
	}
	
	@Override
	public String queryAllStorageMatbySupId(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list=matCommonMapper.queryAllStorageMatbySupId(entityMap);
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 关闭材料
	 */
	@Override
	public String updateMatStorageInCloseInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//批量关闭材料
			matStorageInMapper.updateMatStorageInCloseInv(entityList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * 和并打印
	 */
	@Override
	public String updateMatStorageInCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//批量关闭材料
			matStorageInMapper.updateMatStorageInCancleCloseInv(entityList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	@Override
	public Map<String, Object> queryMatInByNewCombinePrint(Map<String, Object> entityMap) throws DataAccessException {
		Map<String,Object> reMap=new HashMap<String,Object>();
		//查询入库主表
			Map<String,Object> map=matStorageInMapper.queryMatInPrintTemlateByMain(entityMap);
			//查询入库明细表
			List<Map<String,Object>> list=matStorageInMapper.queryMatInByNewCombinePrintByDetail(entityMap);
			
			reMap.put("main", map);
			
			reMap.put("detail", list); 
			
			return reMap;
	}
	@Override
	public String verifyMatClosingDate(List<Map<String, Object>> listVo) {
		//集团、单位变量
		Integer group_id = null, hos_id = null;
		//帐套、入库单Id、材料Id
		String copy_code = "", in_ids = "", confirm_date="",is_store="";
		//取得所有in_id组装map
		for(Map<String, Object> m : listVo){
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
			if("".equals(is_store)){
				is_store = m.get("is_store").toString();
			}
			in_ids = in_ids + m.get("in_id").toString() + ",";
		}
		
		
		String falg = "" ;
		for (Map<String, Object> map : listVo) {
			map.put("group_id", group_id);
			map.put("hos_id", hos_id);
			map.put("copy_code", copy_code);
			map.put("year", confirm_date.substring(0, 4));
			map.put("month", confirm_date.substring(5, 7));
			map.put("confirm_date", confirm_date);
			if(is_store.equals("1")){
				map.put("store_id", map.get("store_id"));
				int MatCount = matStorageInMapper.queryMatTermendStoreByYearMonth(map);
				if(MatCount!=0){
					falg = "所选单据中,单据号"+map.get("in_no")+"的制单日期所在月份已结账，不能做确认入库操作";
				}
			}else{
				Map<String,Object> MatCount = matStorageInMapper.queryAccYearMonthByYearMonth(map);
				if(MatCount!=null){
					falg = "所选单据中,单据号"+map.get("in_no")+"的制单日期所在月份已结账，不能做确认入库操作";
				}
			}
		}
		if(!"".equals(falg)){
			return "{\"error\":\"确认失败! "+falg+"\",\"state\":\"false\"}";
		}else{
			return"{\"state\":\"true\"}";
		}
	

	}
	@Override
	public String queryInvInDetail(Map<String, Object> mapVo) {
		List<Map<String, Object>> detailList = matInCommonMapper.queryInvInDetail(mapVo);

		return ChdJson.toJson(detailList);
	}
	
	/**
	 * 根据供应商查询材料
	 */
	@Override
	public String queryMatChoiceInvBySup(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> detailList = ChdJson.toListLower(matStorageInMapper.queryMatChoiceInvBySup(entityMap));
		return ChdJson.toJson(detailList);
	}
	
	/**
	 * 组装材料信息
	 */
	@Override
	public String queryChoiceInvBySupData(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
		
		JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
		Iterator it = json.iterator();
		while (it.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
				Map<String, Object> detailMap = new HashMap<String, Object>();
			
				detailMap.put("group_id", entityMap.get("group_id"));
				detailMap.put("hos_id", entityMap.get("hos_id"));
				detailMap.put("copy_code", entityMap.get("copy_code"));
				detailMap.put("inv_id", jsonObj.get("inv_id"));
				detailMap.put("inv_no", jsonObj.get("inv_no"));
				detailMap.put("sup_id", jsonObj.get("sup_id"));
				detailList.add(detailMap);
			}
		}
		
		List<Map<String, Object>> list= matStorageInMapper.queryChoiceInvBySupData(detailList);
		return ChdJson.toJsonLower(list);
		
	}

	/**
	 * @Description 
	 * 明细数据是否已经全部维护发票信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String existsMatInDetailByInvoice(Map<String, Object> entityMap) throws DataAccessException {
		try{
			int flag = matStorageInMapper.existsMatInDetailByInvoice(entityMap);
			if(flag != 0){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatInDetailByInvoice\"}";
		}	
	}

	/**
	 * @Description 
	 * 更新发票号<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatStorageInInvoice(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			
			List<Map<String, Object>> payList = matPayMainMapper.queryMatBillDetail_PayNsa(entityMap);
			List<Map<String, Object>> inList = matStorageInMapper.queryInList(entityMap);
			String pay_money = null;
			if(payList.size()>0 && payList.get(0).get("pay_money") != null){
				 pay_money = payList.get(0).get("pay_money").toString();
			}else{
				 pay_money = "0";
			}
			
			String amount_money = inList.get(0).get("amount_money").toString();
			
			if(pay_money .equals(amount_money)){
				return "{\"error\":\"此单据在付款单已经全部付款,不能更新发票号\"}";
			} else{
				//更新发票号
				matStorageInMapper.updateMatStorageInInvoice(entityMap);
			}
			
			
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateMatStorageInInvoice\"}";
		}	
		
		return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
	}

	/**
	 * @Description 
	 * 生成发票<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String addMatBillByInvoice(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//发票明细表信息
			List<Map<String, Object>> detailList = JsonListMapUtil.toListMapLower(matStorageInMapper.queryMatInDetailByNotBill(entityMap));
			if(detailList.size() > 0){
				//发票主表信息
				Map<String, Object> mainMap = JsonListMapUtil.toMapLower(matStorageInMapper.queryMatInMainForBill(entityMap));
				if(mainMap.get("bill_no") == null || "".equals(mainMap.get("bill_no"))){
					return "{\"error\":\"操作失败，请维护发票号！\"}";
				}
				if(mainMap.get("bill_date") == null || "".equals(mainMap.get("bill_date"))){
					return "{\"error\":\"操作失败，请维护发票日期！\"}";
				}
				entityMap.put("bill_no",  mainMap.get("bill_no"));
				
				//根据发票号查找发票信息
				Map<String, Object> mbm= matBillMapper.queryMatBillMainByBillNo(entityMap);
				/*
				int is_flag = matStorageInMapper.existsMatBillByInvoice(entityMap);
				if(is_flag >0){
					return "{\"error\":\"操作失败，发票已存在\"}";
				}
				*/
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
				    mainMap.put("is_init", "0");
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
							return "{\"error\":\"操作失败，该入库单的供应商与发票单上的供应商不一致，不能合并生成发票单！\"}";
						}
					}else{
						return "{\"error\":\"操作失败，已经审核的发票单不能生成单据！\"}";
					}
				}
				
				return "{\"msg\":\"操作成功！\"}";
			}else{
				return "{\"warn\":\"入库单所有材料已生成过发票，不能重复生成\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败！\"}");
		}	
	}

	/**
	 * 批量生成发票
	 */
	@Override
	public String updateBatchMatStorageInInvoice(List<Map<String, Object>> entityList) throws DataAccessException {
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
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			String[] dates = date.split("-");
			for (String key : keyMap.keySet()) {
				
				StringBuffer inIds = new StringBuffer();
				for(Map<String,Object> idMap : entityList){
					String keyM = idMap.get("group_id").toString()+'@'+idMap.get("hos_id").toString()+'@'+idMap.get("copy_code").toString()+'@'+idMap.get("sup_id").toString()+'@'+idMap.get("bill_no").toString()+'@'+idMap.get("bill_date").toString();
					if(key.equals(keyM)){
						if(inIds.length()>0){
							inIds.append(",").append(idMap.get("in_id").toString());
						}else{
							inIds.append(idMap.get("in_id").toString());
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
				entityMap.put("in_ids", inIds);
				
				//用于生成单号
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
				List<Map<String,Object>> detailList = JsonListMapUtil.toListMapLower(matStorageInMapper.queryMatInDetailByNotBill(entityMap));
				if(detailList.size() > 0){
					//主表
					Map<String, Object> mainMap = JsonListMapUtil.toMapLower(matStorageInMapper.queryMatInMainForBill(entityMap));
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
					    mainMap.put("is_init", "0");
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
								return "{\"error\":\"操作失败，该入库单的供应商与发票单上的供应商不一致，不能合并生成发票单！\"}";
							}
						}else{
							return "{\"error\":\"操作失败，已经审核的发票单不能生成单据！\"}";
						}
					}
				}else{
					return "{\"error\":\"操作失败，入库单所有材料已生成过发票\"}";
				}
			}
			return "{\"msg\":\"操作成功！\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}

	/**
	 * @Description 
	 * 维护发票明细列表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatInMainByInvoice(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) matStorageInMapper.queryMatInMainByInvoice(entityMap));
		for(Map<String, Object> tmp : list){ 
			tmp.put("init","0");
			List<Map<String,Object>> detailList = matBillMapper.queryMatBillDetailById(tmp);
			
			tmp.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
		}
		
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 根据送货单来更新订单状态
	 */
	@Override
	public void updateMatDeliveryOrderState(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> orderList  = JsonListMapUtil.toListMapLower(supDeliveryMainMapper.queryDeliveryOrderIds(entityMap));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//keyMap中存放<主键, 具体信息的map>用于判断
		Map<String,Map<String,Object>> keyMap = new HashMap<String,Map<String,Object>>();
		
		if(orderList.size() > 0 ){
			for(Map<String,Object> map : orderList){
				String key = map.get("group_id").toString()+"-"+map.get("hos_id").toString()+"-"+map.get("copy_code").toString()
							+"-"+map.get("order_id").toString()+"-"+map.get("sum_order_amount").toString();
				
				if(!keyMap.containsKey(key)){
					keyMap.put(key, map);
				}
			}
			
			for (String key : keyMap.keySet()) {
				Map<String,Object> orderMap = new HashMap<String,Object>();//需要改变的MAP
				int sum_amount = 0;//用于计算入库的总数量
				Map<String,Object> mapK = keyMap.get(key);
				int sum_order_amount = Integer.parseInt(mapK.get("sum_order_amount").toString());//订单总的数量
				for(Map<String,Object> mapO : orderList){
					String keyO = mapO.get("group_id").toString()+"-"+mapO.get("hos_id").toString()+"-"+mapO.get("copy_code").toString()
							+"-"+mapO.get("order_id").toString()+"-"+mapO.get("sum_order_amount").toString();
					if(key.equals(keyO)){
						if(Integer.parseInt(mapO.get("order_amount").toString()) <= Integer.parseInt(mapO.get("in_amount").toString())){
							sum_amount += Integer.parseInt(mapO.get("in_amount").toString());
						}
					}
				}
				
				if(sum_order_amount <= sum_amount){
					orderMap.put("group_id", mapK.get("group_id"));
					orderMap.put("hos_id", mapK.get("hos_id"));
					orderMap.put("copy_code", mapK.get("copy_code"));
					orderMap.put("order_id", mapK.get("order_id"));
					orderMap.put("state", "5");
					list.add(orderMap);
				}
			}
		}
		if(list.size() > 0){
			supDeliveryMainMapper.updateDeliveryOrderStates(list);
		}
		
		
	}
	
	
	/**
	 * @Description 冲账 明细表数据<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */ 
	@Override
	public String queryMatStorageMegerBackDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list= JsonListMapUtil.toListMapLower((List<Map<String, Object>>) matStorageInMapper.queryMatStorageMegerBackDetail(entityMap));
		//return  ChdJson.toJsonLower(list);  
		return matCommonService.getInvJsonByInvListInBack(list);
	}
	
	/**
	 * 订单如果是省平台的走这个方法(不用)
	 * @throws Exception 
	 */
	@Override
	public String addInByOrderIsGood(Map<String, Object> mapVo){
		String trues = null;
		int index = 0 ;
		try {
			//StringBuffer errMsg=new StringBuffer();//错误信息
			//Map<String,String> sendOrderMap=new HashMap<String, String>();//省平台订单数据
			//获取订单明细数据
			JSONArray json = JSONArray.parseArray(mapVo.get("jsonArray").toString());
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				ServerUrl su=new ServerUrl();
				String url = su.getProperty("chdsup.ip", "chdsup.H002");//参看 HRP系统的 serverUrl.properties属性文件
				List<String> orderDetailIDList= zjMapper.getPlatformOrderDetailId(jsonObj);
				Map<String, String> getOrderMap=new HashMap<String, String>();
				getOrderMap.put("currentPageNumber", "1");//后期需要通过计算来获取页码,如计算省平台一共能传过来多少条数据,在看看咱们系统有多少明细数据,他们相除算出页码
				//还少一个采购部门,现在先不用填,不填默认第一个
				getOrderMap.put("list", JSON.toJSONString(orderDetailIDList));
				String getOrderResultStr = HttpClientTool.doPost(url, JSON.toJSONString(getOrderMap));
				Map orderReturnMap = JSONObject.parseObject(getOrderResultStr, Map.class);
				if ("1".equals(String.valueOf(orderReturnMap.get("returnCode")))) {
					List<Map<String, Object>> successList = JSONObject.parseObject(String.valueOf(orderReturnMap.get("successList")), List.class);
					for (Map<String, Object> map : successList) {
						if("8".equals(map.get("orderDetailState"))){
							//获取配送订单
							url = su.getProperty("chdsup.ip", "chdsup.H004");
							Map<String, String> submitOrderMap=new HashMap<String, String>();
							String submitOrderReturnStr = HttpClientTool.doPost(url, JSON.toJSONString(submitOrderMap));
							Map submitOrderReturnMap = JSONObject.parseObject(submitOrderReturnStr, Map.class);
							if ("1".equals(String.valueOf(submitOrderReturnMap.get("returnCode")))) {
								List<Map<String, Object>> successList1 = JSONObject.parseObject(String.valueOf(submitOrderReturnMap.get("successList")), List.class);
								  String amounts = successList1.get(0).get("warehouseCount").toString();
								  json.add(index, amounts);
								  index++;
								return "{\"state\":\"true\",\"pageUrl\":\"\"}";
							}else{
								return "{\"state\":\"false\",\"errMsg\":\"订单省平台提交失败"+String.valueOf(submitOrderReturnMap.get("returnMsg"))+"\"}";
								
							}
						}else{
							return "{\"msg\":\"该订单,没有收到货物\",\"state\":\"false\"}";
						}
					}
				}else{
					//note 值为 orderMain 说明主表添加失败,值为orderDetail 说明明细表添加失败
					if("orderMain".equals(String.valueOf(orderReturnMap.get("note")))){
						return "{\"state\":\"false\",\"errMsg\":\"主表添加失败"+String.valueOf(orderReturnMap.get("returnMsg"))+"\"}";
					}else{
						return "{\"state\":\"false\",\"errMsg\":\"明细表添加失败"+String.valueOf(orderReturnMap.get("returnMsg"))+"\"}";
					}
				}
			}
			
			//这里就是把入库数量替换成配送单里的入库数量,在传给普通的方法
			 trues = inByOrderIsGood(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return trues;
		 
	}
	private String inByOrderIsGood(Map<String, Object> entityMap) {
		try {
			//金额位数
			//int money_para = Integer.valueOf(MyConfig.getSysPara("04005").toString());
			//金额存数的时候默认不设置小数点几位
			int money_para = 6;
			if(entityMap.get("in_date") == null || "".equals(entityMap.get("in_date"))){
				return "{\"error\":\"制单日期不能为空\",\"state\":\"false\"}";
			}
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			//entityMap.put("year", date[0]);
			//entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("in_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			//转换日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成常备材料入库单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MAT_IN_MAIN");
				String in_no = matCommonService.getMatNextNo(entityMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				entityMap.put("in_no", in_no);
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
			//组装明细
			if(entityMap.get("jsonArray") != null && !"[]".equals(String.valueOf(entityMap.get("jsonArray")))){
				double money_sum = 0;//记录明细总金额

				/*用于查询个体码----begin-----*/
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
				/*用于查询个体码----end-------*/
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				/*用于查询批次----end-----*/
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("jsonArray"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						String cert_code=supDeliveryMainMapper.queryCertCode(jsonObj);
						detailMap.put("cert_code", cert_code);
						//取出明细表ID（自增序列）
						detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  entityMap.get("amount"));//数量
						//detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						//由于有时会出现（单价 * 数量 != 金额）的情况，所以不直接取前台的金额放到后台计算
						detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(entityMap.get("amount").toString())*Double.valueOf(jsonObj.get("price").toString()), money_para)));//金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_no")))){
							detailMap.put("disinfect_no", jsonObj.get("disinfect_no"));//灭菌批号
						}
						
						//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no",  jsonObj.get("batch_no"));//批号
						}else{
							detailMap.put("batch_no", "-");//默认批号
						}
						/**********************自动生成批次-------begin--------*/
						invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
						//判断是否已经取出该批号的最大批次
						if(invBatchKeySnMap.get(invBatchKey) == null){
							//查询该批号最大批次
							batchSnMap.put("c_value", detailMap.get("inv_id"));//材料ID
							batchSnMap.put("c_value1", detailMap.get("batch_no"));//材料批号
							String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
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
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))){
							detailMap.put("sale_price",  jsonObj.get("sale_price"));//批发价
						}else{
							detailMap.put("sale_price",  "0");//批发价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_money")))){
							detailMap.put("sale_money",  jsonObj.get("sale_money"));//批发金额
						}else{
							detailMap.put("sale_money",  "0");//批发金额
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
							detailMap.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码（这里用个体码）
						}else{
							detailMap.put("sn", "-");
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id",  "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar")))){
							detailMap.put("is_per_bar",  jsonObj.get("is_per_bar"));//是否个体码
						}else{
							detailMap.put("is_per_bar", "0");//是否个体码(默认否)
						}
						//生成个体码--如果系统参数04010高值材料自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if("0".equals(detailMap.get("is_per_bar").toString()) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("04010"))))){
							//System.out.println(jsonObj.get("inv_name").toString()+"不生成个体码");
							if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
								detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
							}else{
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}
							//该条明细数据添加到List中
							detailList.add(detailMap);
						}else{
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(detailMap);
								//System.out.println(jsonObj.get("inv_name").toString()+"生成个体码");
								//System.out.println(bar_code);
								bar_code = matCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
								}
								//拆分数量和金额
								barMap.put("amount",  1);//数量
								if(detailMap.get("num_exchange") != null){
									barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
								}
								if(detailMap.get("num") != null){
									barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
								}
								barMap.put("amount_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("price").toString()), money_para)));
								//barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("bar_code",  bar_code);//个体码
								//该条明细数据添加到List中
								detailList.add(barMap);
							}
						}
						/*订单关系-------begin--*/
						
							Map<String,Object> orderRelaMap = new HashMap<String,Object>();
							orderRelaMap.put("group_id", entityMap.get("group_id"));
							orderRelaMap.put("hos_id", entityMap.get("hos_id"));
							orderRelaMap.put("copy_code", entityMap.get("copy_code"));
							orderRelaMap.put("in_id", entityMap.get("in_id"));
							orderRelaMap.put("in_no", entityMap.get("in_no"));
							orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
							orderRelaMap.put("order_id", jsonObj.get("order_id"));
							orderRelaMap.put("order_detail_id", jsonObj.get("order_detail_id"));
							orderRelaMap.put("in_amount", Float.parseFloat(entityMap.get("amount").toString()));
							if(jsonObj.containsKey("already_amount")){
								if(Float.parseFloat(jsonObj.get("already_amount").toString())>0){
									orderRelaMap.put("order_amount", Float.parseFloat(jsonObj.get("order_amount").toString())+Float.parseFloat(jsonObj.get("already_amount").toString()));
								}else{
									orderRelaMap.put("order_amount", Float.parseFloat(jsonObj.get("order_amount").toString()));
								}
							}else{
								orderRelaMap.put("order_amount", Float.parseFloat(jsonObj.get("order_amount").toString()));
							}
							
							
							orderRelaList.add(orderRelaMap);
							
						
						/*订单关系-------end--*/
					}
				}
				if(detailList.size() > 0){
					//更新个体码
					if(!init_bar_code.equals(bar_code)){
						barCodeMap.put("max_no", bar_code);
						matNoOtherMapper.updateMatNoOther(barCodeMap);
					}
					//明细总金额
					entityMap.put("amount_money", money_sum);
					//新增入库主表数据
					matInCommonMapper.addMatInMain(entityMap);
					//新增入库明细数据
					matInCommonMapper.addMatInDetailBatch(detailList);
					//新增资金来源表
					entityMap.put("source_money", money_sum);
					matInCommonMapper.insertMatInResource(entityMap);
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						matStorageInMapper.insertOrderRela(orderRelaList);
						
						/*entityMap.put("state", "2");
						//entityMap.remove("in_id");
						String orderIds  = matStorageInMapper.queryOrderIds(entityMap);
						if( orderIds!=null && !"".equals(orderIds)){
							//更新状态  state = 5
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("group_id",entityMap.get("group_id"));
							map.put("hos_id", entityMap.get("hos_id"));
							map.put("copy_code", entityMap.get("copy_code"));
							map.put("state", "5");
							map.put("orderIds", orderIds);
							matStorageInMapper.updateOrderStates(map);
						}*/
					}
					
				}else{
					return "{\"error\":\"请选择材料\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStorageIn\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("in_id").toString()+","
			+"\"}";
		
	}
	
	// 查询入库单ID 在发票mat_bill_detail表中是否有明细数据
	@Override
	public Integer queryBillCountByInId(Map<String, Object> mapVo) throws DataAccessException{
		
		Integer count = matStorageInMapper.queryBillCountByInId(mapVo);
		
		return count;
	}
}
