/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.special;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Lang;
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
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.base.HrpMatSelectMapper;
import com.chd.hrp.mat.dao.base.MatBatchBalanceMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatFifoBalanceMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatInvBalanceMapper;
import com.chd.hrp.mat.dao.base.MatInvHoldMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.dao.base.MatOutResourceMapper;
import com.chd.hrp.mat.dao.initial.MatInitChargeMapper;
import com.chd.hrp.mat.dao.storage.in.MatStorageInMapper;
import com.chd.hrp.mat.dao.storage.out.MatCommonOutApplyCheckMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutDetailMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutMainMapper;
import com.chd.hrp.mat.dao.storage.special.MatSpecialAffiOutRelaMapper;
import com.chd.hrp.mat.dao.storage.special.MatSpecialMapper;
import com.chd.hrp.mat.entity.HrpMatSelect;
import com.chd.hrp.mat.entity.MatInMain;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.special.MatSpecialService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 专购品入库
 * @Table:
 * MAT_INV 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matSpecialService")
public class MatSpecialServiceImpl implements MatSpecialService {

	private static Logger logger = Logger.getLogger(MatSpecialServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matSpecialMapper")
	private final MatSpecialMapper matSpecialMapper = null;
	@Resource(name = "matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	@Resource(name = "matInitChargeMapper")
	private final MatInitChargeMapper matInitChargeMapper = null;
	
	@Resource(name = "matFifoBalanceMapper")
	private final MatFifoBalanceMapper matFifoBalanceMapper = null ;
	
	@Resource(name = "matInvBalanceMapper")
	private final MatInvBalanceMapper matInvBalanceMapper = null ;
	
	@Resource(name = "matBatchBalanceMapper")
	private final MatBatchBalanceMapper matBatchBalanceMapper = null ;
	@Resource(name = "matInvHoldMapper")
	private final MatInvHoldMapper matInvHoldMapper = null ;
	@Resource(name = "matNoOtherMapper")
	private final MatNoOtherMapper matNoOtherMapper = null;
	@Resource(name = "matStorageInMapper")
	private final MatStorageInMapper matStorageInMapper = null;
	
	//出库主表
	@Resource(name = "matOutMainMapper")
	private final MatOutMainMapper matOutMainMapper = null;
	//出库明细
	@Resource(name = "matOutDetailMapper")
	private final MatOutDetailMapper matOutDetailMapper = null;
	
	//出库资金来源
	@Resource(name = "matOutResourceMapper")
	private final MatOutResourceMapper matOutResourceMapper = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	//用于代销生成专购品保存出库数据
	@Resource(name = "matCommonOutApplyCheckMapper")
	private final MatCommonOutApplyCheckMapper matCommonOutApplyCheckMapper = null;
	@Resource(name = "matSpecialAffiOutRelaMapper")
	private final MatSpecialAffiOutRelaMapper matSpecialAffiOutRelaMapper = null;
	
	@Resource(name = "hrpMatSelectMapper")
	private final HrpMatSelectMapper hrpMatSelectMapper = null;
	
	private static Connection conn;
	
	/**
	 * @Description 
	 * 添加专购品入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//截取期间
			String[] date = entityMap.get("make_date").toString().split("-");
			//entityMap.put("year", date[0]);
			//entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("make_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			//入库 编制日期 
			entityMap.put("in_date",df.parse(String.valueOf(entityMap.get("make_date"))));
			//即入即出  出库编制日期 
			entityMap.put("out_date", df.parse(String.valueOf(entityMap.get("make_date"))));
			//专购品主表 编制日期
			entityMap.put("make_date",df.parse(String.valueOf(entityMap.get("make_date"))));
			
			//处理发票日期
			if(entityMap.get("bill_date") != null && !"".equals(entityMap.get("bill_date"))){
				entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
			}
			
			/* 专购品主表 、专购品 入库主表、专购品 出库库主表   业务类型 bus_type_code 配置*/
			//专购品主表  业务类型 bus_type_code（01：正常  02：冲账）
			entityMap.put("bus_type_code_sepcial", "01");
			if("1".equals(entityMap.get("falg"))){
				//专购品 入库主表  业务类型 bus_type_code（47  专购品入库）
				entityMap.put("bus_type_code_in", "47");
				
				//专购品 出库库主表  业务类型 bus_type_code（49  专购品出库）
				entityMap.put("bus_type_code_out", "49");
			}else{
				//专购品 入库主表  业务类型 bus_type_code（48 专购品退货）
				entityMap.put("bus_type_code_in", "48");
				
				//专购品 出库库主表  业务类型 bus_type_code（50  专购品退库）
				entityMap.put("bus_type_code_out", "50");
			}
			
			//入库日期需在系统启用日期之前
			/*int flag = matCommonMapper.existsInDateCheckBefore(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，入库日期需在系统启用日期之前！\",\"state\":\"false\"}";
			}*/
			/*//如果该仓库已期初记账，则不能添加期初入库单
			int flag = matInitChargeMapper.existsStoreIsAccount(entityMap);
			if(flag != 0){
				return "{\"error\":\"添加失败，仓库已期初记账！\",\"state\":\"false\"}";
			}*/
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成 专购品单据号
			Map<String,Object> specialMap = new HashMap<String,Object>();
			specialMap.put("group_id", entityMap.get("group_id"));
			specialMap.put("hos_id", entityMap.get("hos_id"));
			specialMap.put("copy_code", entityMap.get("copy_code"));
			specialMap.put("year", entityMap.get("year"));
			specialMap.put("month", entityMap.get("month"));
			specialMap.put("day", entityMap.get("day"));  //用于生成单号
			specialMap.put("store_id", entityMap.get("store_id"));
			specialMap.put("table_code", "MAT_SPECIAL_MAIN");
			specialMap.put("bus_type_code", "47");
			specialMap.put("prefixe", "");
			if("自动生成".equals(entityMap.get("special_no"))){
				entityMap.put("special_no", matCommonService.getMatNextNo(specialMap));
			}
			//专购品单据ID
			entityMap.put("special_id", matSpecialMapper.queryMatSpecialNextval());
			entityMap.put("come_from", "1");
			
			//自动生成 专购品入库单号
			Map<String,Object> inMap = new HashMap<String,Object>();
			inMap.put("group_id", entityMap.get("group_id"));
			inMap.put("hos_id", entityMap.get("hos_id"));
			inMap.put("copy_code", entityMap.get("copy_code"));
			inMap.put("year", entityMap.get("year"));
			inMap.put("month", entityMap.get("month"));
			inMap.put("day", entityMap.get("day"));  //用于生成单号
			inMap.put("store_id", entityMap.get("store_id"));
			inMap.put("table_code", "MAT_IN_MAIN");
			inMap.put("bus_type_code", "47");
			inMap.put("prefixe", "");
			entityMap.put("in_no", matCommonService.getMatNextNo(inMap));
			
			//取出主表ID（自增序列）
			entityMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
			//专购品入库主表  申请科室 对应  专购品主表领料科室
			entityMap.put("app_dept", entityMap.get("dept_id"));
			//要添加专购品出库主表的Id
			entityMap.put("out_id", matOutMainMapper.queryMatOutMainSeq());
			
			//自动生成 专购品出库单号 
			Map<String,Object> outMap = new HashMap<String,Object>();
			outMap.put("group_id", entityMap.get("group_id"));
			outMap.put("hos_id", entityMap.get("hos_id"));
			outMap.put("copy_code", entityMap.get("copy_code"));
			outMap.put("year", entityMap.get("year"));
			outMap.put("month", entityMap.get("month"));
			outMap.put("day", entityMap.get("day"));  //用于生成单号
			outMap.put("store_id", entityMap.get("store_id"));
			outMap.put("table_code", "MAT_OUT_MAIN");
			outMap.put("bus_type_code", "49");
			outMap.put("prefixe", "");
			entityMap.put("out_no", matCommonService.getMatNextNo(outMap));
			
			//状态   0:验收; 1:未审核；2审核；3入库确认；4财务记帐。
			entityMap.put("state", 1);
			//组装明细
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
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
				
				///*用于查询批次----begin-----*/
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
				///*用于查询批次----end-----*/
				//保存明细数据
				JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("detailData")));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("special_id", entityMap.get("special_id"));//专购品主表 单据ID
						detailMap.put("special_no", entityMap.get("special_no"));//专购品主表  单据号
						//detailMap.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());//专购品明细表id
						detailMap.put("in_id", entityMap.get("in_id"));//专购品入库主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//专购品入库主表  入库单号
						//detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
						detailMap.put("out_id", entityMap.get("out_id"));//专购品出库主表ID
						detailMap.put("out_no", entityMap.get("out_no"));//专购品出库主表 出库单号
						//detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料 变更ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						//detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(jsonObj.get("amount").toString())*Double.valueOf(jsonObj.get("price").toString()))));//金额
						
						//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
						
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  jsonObj.get("batch_no").toString());//批号
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
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
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
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id",  jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_code")))){
							detailMap.put("cert_code",  jsonObj.get("cert_code"));//注册编码
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate( jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("serial_no")))){
							detailMap.put("serial_no",  jsonObj.get("serial_no"));//序列号
						}
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar")))){
							detailMap.put("is_per_bar",  jsonObj.get("is_per_bar"));//是否个体码
						}else{
							detailMap.put("is_per_bar", "0");//是否个体码(默认否)
						}
						//生成个体码
						if((!ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar"))) || "0".equals(String.valueOf(jsonObj.get("is_per_bar")))) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("04010"))))){
							//System.out.println(jsonObj.get("inv_name").toString()+"不生成个体码");
							if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}else{
								detailMap.put("bar_code",  String.valueOf(jsonObj.get("bar_code")));//个体码
							}
							//该条明细数据添加到List中
							detailMap.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());//专购品明细表id
							detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
							detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
							detailList.add(detailMap);
						}else{
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
								Map<String, Object> barMap = new HashMap<String, Object>();
								detailMap.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());//专购品明细表id
								detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
								detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
								barMap.putAll(detailMap);
								//System.out.println(jsonObj.get("inv_name").toString()+"生成个体码");
								//System.out.println(bar_code);
								if( i>1){
									barMap.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());
								}
								bar_code = matCommonService.getNextBar_code(bar_code);
								//拆分数量和金额
								barMap.put("amount",  1);//数量 
								if(detailMap.get("num_exchange") != null){ 
									barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
								}
								if(detailMap.get("num") != null){
									barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
								}
								//barMap.put("amount_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("price").toString()), money_sum)));
								barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("bar_code",  bar_code);//个体码
								//该条明细数据添加到List中
								detailList.add(barMap);
							}
						}
						/*入库单订单关系  专购品订单关系-------begin--*/
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("order_rela")))){
							//System.out.println(jsonObj.get("order_rela").toString());
							Map<String,Object> orderRelaMap = new HashMap<String,Object>();
							orderRelaMap.put("group_id", entityMap.get("group_id"));
							orderRelaMap.put("hos_id", entityMap.get("hos_id"));
							orderRelaMap.put("copy_code", entityMap.get("copy_code"));
							orderRelaMap.put("special_id", detailMap.get("special_id"));
							orderRelaMap.put("sp_detail_id", detailMap.get("detail_id"));
							orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
							orderRelaMap.put("in_id", entityMap.get("in_id"));
							orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
							
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("order_rela").toString())){
								orderRelaMap.put("order_id", m.get("order_id"));
								orderRelaMap.put("order_detail_id", m.get("order_detail_id"));
								orderRelaMap.put("in_amount", m.get("in_amount"));
								orderRelaMap.put("special_amount", m.get("in_amount"));
								orderRelaMap.put("order_amount", m.get("order_amount"));
								orderRelaList.add(orderRelaMap);
							}
						}
						/*入库单订单关系  专购品订单关系-------end--*/
					}
				}
				//更新个体码
				if(!init_bar_code.equals(bar_code)){
					barCodeMap.put("max_no", bar_code);
					matNoOtherMapper.updateMatNoOther(barCodeMap);
				}
				
				//新增专购品主表数据
				entityMap.put("come_from", 1);
				matSpecialMapper.addSpecialMain(entityMap);
				
				//添加主表金额和单据来源
				entityMap.put("come_from", 4);
				entityMap.put("amount_money", money_sum);
				
				//新增专购品入库主表数据
				matSpecialMapper.addInMain(entityMap);
				
				//新增专购品出库主表数据
				matSpecialMapper.addOutMain(entityMap);
				
				//批量新增专购品明细数据
				matSpecialMapper.addMatSpecialDetailBatch(detailList);
				
				//批量新增专购品入库明细数据
				matInCommonMapper.addMatInDetailBatch(detailList);
				
				//批量新增专购品出库明细数据
				matSpecialMapper.addMatOutDetailBatch(detailList);
				
				//维护专购品  与出入库对应关系
				Map<String,Object> relaMap = new HashMap<String,Object>();
				relaMap.put("group_id", entityMap.get("group_id"));
				relaMap.put("hos_id", entityMap.get("hos_id"));
				relaMap.put("copy_code", entityMap.get("copy_code"));
				relaMap.put("special_id", entityMap.get("special_id"));
				relaMap.put("special_no", entityMap.get("special_no"));
				relaMap.put("out_id", entityMap.get("out_id"));
				relaMap.put("out_no", entityMap.get("out_no"));
				relaMap.put("in_id", entityMap.get("in_id"));
				relaMap.put("in_no", entityMap.get("in_no"));
				matSpecialMapper.addMatSpecialRela(relaMap);
				
				//新增入库资金来源表
				entityMap.put("source_money", money_sum);
				matInCommonMapper.insertMatInResource(entityMap);
				
				//新增出库资金来源表
				matOutResourceMapper.add(entityMap);
				
				//新增入库单订单关系
				if(orderRelaList.size() > 0){
					matSpecialMapper.insertOrderRela(orderRelaList);
				}
				
				//新增专购品订单关系
				if(orderRelaList.size() > 0){
					matSpecialMapper.addSpecialOrderRelaBatch(orderRelaList);
				}
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
			return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				entityMap.get("special_id").toString()+","+
				entityMap.get("special_no").toString()+","
				+"\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	//批量复制专购品单
	@Override
	public String copyMatSpecialBatch(List<Map<String, Object>> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、材料Id
			String copy_code = "", in_ids = "";
			Map<String, Object> spMap = new HashMap<String, Object>();
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
				in_ids = in_ids + m.get("special_id").toString() + ",";
			}
			//组装数据
			spMap.put("group_id", group_id);
			spMap.put("hos_id", hos_id);
			spMap.put("copy_code", copy_code);
			spMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			
			//获取选中的主表、明细表数据
			List<Map<String, Object>> mainList = (List<Map<String, Object>>)matSpecialMapper.queryMatSpecialMainByIds(spMap);
			List<Map<String, Object>> detailList = (List<Map<String, Object>>)matSpecialMapper.queryMatSpecialDetailByIds(spMap);
			
			//存放组装数据的List
			List<Map<String, Object>> insertMainList = new ArrayList<Map<String,Object>>();
			
			double money_sum = 0;
			/*复制操作中的固定值-----begin--------*/
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String[] dates = DateUtil.dateToString(date, "yyyy-MM-dd").split("-");
			String user_id = SessionManager.getUserId();
			String old_id;
			/*复制操作中的固定值-----end--------*/
			
			for(Map<String, Object> mainMap : mainList){ 
				/*获取个体码----------begin----------*/
				Map<String,Object> barCodeMap = new HashMap<String,Object>();
				barCodeMap.put("group_id", spMap.get("group_id"));
				barCodeMap.put("hos_id", spMap.get("hos_id"));
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
				batchSnMap.put("group_id", spMap.get("group_id"));
				batchSnMap.put("hos_id", spMap.get("hos_id"));
				batchSnMap.put("copy_code", spMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				/*用于查询批次----end-----*/
				
				/* 专购品主表 、专购品 入库主表、专购品 出库库主表   业务类型 bus_type_code 配置*/
				//专购品主表  业务类型 bus_type_code（01：正常  02：冲账）
				mainMap.put("bus_type_code_sepcial", "01");
				//专购品 入库主表  业务类型 bus_type_code（47  专购品入库）
				mainMap.put("bus_type_code_in", "47");
				
				//专购品 出库库主表  业务类型 bus_type_code（49  专购品出库）
				mainMap.put("bus_type_code_out", "49");
				/*if("1".equals(mainMap.get("falg"))){
					//专购品 入库主表  业务类型 bus_type_code（47  专购品入库）
					mainMap.put("bus_type_code_in", "47");
					
					//专购品 出库库主表  业务类型 bus_type_code（49  专购品出库）
					mainMap.put("bus_type_code_out", "49");
				}else{
					//专购品 入库主表  业务类型 bus_type_code（48 专购品退货）
					mainMap.put("bus_type_code_in", "48");
					
					//专购品 出库库主表  业务类型 bus_type_code（50  专购品退库）
					mainMap.put("bus_type_code_out", "50");
				}*/
				
				mainMap.put("year", dates[0]);
				mainMap.put("month", dates[1]);
				mainMap.put("maker", user_id);
				mainMap.put("sup_no", mainMap.get("sup_no"));
				mainMap.put("sup_id", mainMap.get("sup_id"));
				mainMap.put("store_id", mainMap.get("store_id"));
				mainMap.put("store_no", mainMap.get("store_no"));
				mainMap.put("dept_id", mainMap.get("dept_id"));
				mainMap.put("dept_no", mainMap.get("dept_no"));
				mainMap.put("dept_emp", mainMap.get("dept_emp"));
				mainMap.put("brief", "复制"+mainMap.get("special_no")+" "+mainMap.get("brief"));
				mainMap.put("stocker", mainMap.get("stocker"));
				mainMap.put("stock_type_code", mainMap.get("stock_type_code"));
				mainMap.put("protocol_code", mainMap.get("protocol_code"));
				mainMap.put("proj_id", mainMap.get("proj_id"));
				mainMap.put("bill_date", mainMap.get("bill_date"));
				mainMap.put("bill_no", mainMap.get("bill_no"));
				mainMap.put("patient_name", mainMap.get("patient_name"));
				mainMap.put("hospital_no", mainMap.get("hospital_no"));
				mainMap.put("beds_no", mainMap.get("beds_no"));
				mainMap.put("patient_sex", mainMap.get("patient_sex"));
				mainMap.put("patient_age", mainMap.get("patient_age"));
				mainMap.put("physician", mainMap.get("physician"));
				mainMap.put("execution_dept", mainMap.get("execution_dept"));
				mainMap.put("advice_class", mainMap.get("advice_class"));
				mainMap.put("examiner", mainMap.get("examiner"));
				mainMap.put("make_date", "");
				mainMap.put("checker", "");
				mainMap.put("check_date", "");
				mainMap.put("confirmer", "");
				mainMap.put("confirm_date", "");
				  
				//自动生成 专购品单据号
				Map<String,Object> specialMap = new HashMap<String,Object>();
				specialMap.put("group_id", mainMap.get("group_id"));
				specialMap.put("hos_id", mainMap.get("hos_id"));
				specialMap.put("copy_code", mainMap.get("copy_code"));
				specialMap.put("year", dates[0]);
				specialMap.put("month", dates[1]);
				specialMap.put("day", dates[2]);  //用于生成单号
				specialMap.put("store_id", mainMap.get("store_id"));
				specialMap.put("table_code", "MAT_SPECIAL_MAIN");
				specialMap.put("bus_type_code", "47");
				specialMap.put("prefixe", "");
				specialMap.put("make_date",DateUtil.stringToDate(df.format(new Date()).toString(), "yyyy-MM-dd"));//专购品主表 编制日期
				String special_no = matCommonService.getMatNextNo(specialMap);
				if(special_no.indexOf("error") > 0){
					return special_no;
				}
				mainMap.put("special_no", special_no);
				//专购品单据ID
				mainMap.put("make_date", DateUtil.stringToDate(df.format(new Date()).toString(), "yyyy-MM-dd"));
				mainMap.put("special_id", matSpecialMapper.queryMatSpecialNextval());
				mainMap.put("come_from", "1");
				
				//自动生成 专购品入库单号
				Map<String,Object> inMap = new HashMap<String,Object>();
				inMap.put("group_id", mainMap.get("group_id"));
				inMap.put("hos_id", mainMap.get("hos_id"));
				inMap.put("copy_code", mainMap.get("copy_code"));
				inMap.put("year", dates[0]);
				inMap.put("month", dates[1]);
				inMap.put("day", dates[2]);  //用于生成单号
				inMap.put("store_id", mainMap.get("store_id"));
				inMap.put("table_code", "MAT_IN_MAIN");
				inMap.put("bus_type_code", "47");
				inMap.put("prefixe", "");
				mainMap.put("in_date",DateUtil.stringToDate(df.format(new Date()).toString(), "yyyy-MM-dd"));//入库 编制日期 
				mainMap.put("in_no", matCommonService.getMatNextNo(inMap));
				
				//取出主表ID（自增序列）
				mainMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
				//专购品入库主表  申请科室 对应  专购品主表领料科室
				mainMap.put("app_dept", mainMap.get("dept_id"));
				//要添加专购品出库主表的Id
				mainMap.put("out_id", matOutMainMapper.queryMatOutMainSeq());
				
				//自动生成 专购品出库单号 
				Map<String,Object> outMap = new HashMap<String,Object>();
				outMap.put("group_id", mainMap.get("group_id"));
				outMap.put("hos_id", mainMap.get("hos_id"));
				outMap.put("copy_code", mainMap.get("copy_code"));
				outMap.put("year", dates[0]);
				outMap.put("month", dates[1]);
				outMap.put("day", dates[2]);  //用于生成单号
				outMap.put("store_id", mainMap.get("store_id"));
				outMap.put("table_code", "MAT_OUT_MAIN");
				outMap.put("bus_type_code", "49");
				outMap.put("prefixe", "");
				mainMap.put("out_no", matCommonService.getMatNextNo(outMap));
				//状态   0:验收; 1:未审核；2审核；3入库确认；4财务记帐。
				mainMap.put("state", 1);
				//即入即出  出库编制日期 
				mainMap.put("out_date", DateUtil.stringToDate(df.format(new Date()).toString(), "yyyy-MM-dd"));
				
				
				old_id = mainMap.get("special_id").toString();//记录旧的in_id用于筛选明细
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> insertDetailList = new ArrayList<Map<String,Object>>();
				/*处理明细-------begin-------*/
				for(Map<String, Object> detailMap : detailList){
					//保存明细数据
					detailMap.put("group_id", mainMap.get("group_id"));
					detailMap.put("hos_id", mainMap.get("hos_id"));
					detailMap.put("copy_code", mainMap.get("copy_code"));
					detailMap.put("special_id", mainMap.get("special_id"));//专购品主表 单据ID
					detailMap.put("special_no", mainMap.get("special_no"));//专购品主表  单据号
					detailMap.put("in_id", mainMap.get("in_id"));//专购品入库主表ID
					detailMap.put("in_no", mainMap.get("in_no"));//专购品入库主表  入库单号
					detailMap.put("out_id", mainMap.get("out_id"));//专购品出库主表ID
					detailMap.put("out_no", mainMap.get("out_no"));//专购品出库主表 出库单号
					detailMap.put("inv_id",  detailMap.get("inv_id"));//材料ID
					detailMap.put("inv_no",  detailMap.get("inv_no"));//材料 变更ID
					detailMap.put("price",  detailMap.get("price"));//单价
					detailMap.put("amount",  detailMap.get("amount"));//数量
					detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString())*Double.valueOf(detailMap.get("price").toString()))));//金额
					
					//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
					money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
					
					if(!ChdJson.validateJSON(String.valueOf(detailMap.get("batch_no")))){
						detailMap.put("batch_no", "-");//默认批号
					}else{
						detailMap.put("batch_no",  detailMap.get("batch_no").toString());//批号
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
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("sell_price")))){
						detailMap.put("sell_price",  detailMap.get("sell_price"));//零售价
					}else{
						detailMap.put("sell_price",  "0");//零售价
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("sell_money")))){
						detailMap.put("sell_money",  detailMap.get("sell_money"));//零售金额
					}else{
						detailMap.put("sell_money",  "0");//零售金额
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("allot_price")))){
						detailMap.put("allot_price",  detailMap.get("allot_price"));//调拨价
					}else{
						detailMap.put("allot_price",  "0");//调拨价
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("allot_money")))){
						detailMap.put("allot_money",  detailMap.get("allot_money"));//调拨金额
					}else{
						detailMap.put("allot_money",  "0");//调拨金额
					}

					if(ChdJson.validateJSON(String.valueOf(detailMap.get("pack_code")))){
						detailMap.put("pack_code",  detailMap.get("pack_code"));//包装单位
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("num_exchange")))){
						detailMap.put("num_exchange",  detailMap.get("num_exchange"));//转换量
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("num")))){
						detailMap.put("num",  detailMap.get("num"));//包装数量
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("pack_price")))){
						detailMap.put("pack_price",  detailMap.get("pack_price"));//包装单价
					}else{
						detailMap.put("pack_price",  "0");//包装单价
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
						detailMap.put("inva_date", DateUtil.stringToDate( detailMap.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
						detailMap.put("disinfect_date", DateUtil.stringToDate( detailMap.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("sn")))){
						detailMap.put("sn",  detailMap.get("sn"));//条形码（这里用个体码）
					}else{
						detailMap.put("sn", "-");
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_id")))){
						detailMap.put("location_id",  detailMap.get("location_id"));//货位
					}else{
						detailMap.put("location_id",  "0");//货位
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("note")))){
						detailMap.put("note",  detailMap.get("note"));//备注
					}
					
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("cert_id")))){
						detailMap.put("cert_id",  detailMap.get("cert_id"));//注册证号
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("cert_code")))){
						detailMap.put("cert_code",  detailMap.get("cert_code"));//注册编码
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("fac_date")))){
						detailMap.put("fac_date", DateUtil.stringToDate( detailMap.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
					}
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("serial_no")))){
						detailMap.put("serial_no",  detailMap.get("serial_no"));//序列号
					}
					
					if(ChdJson.validateJSON(String.valueOf(detailMap.get("is_per_bar")))){
						detailMap.put("is_per_bar",  detailMap.get("is_per_bar"));//是否个体码
					}else{
						detailMap.put("is_per_bar", "0");//是否个体码(默认否)
					}
					//生成个体码
					if((!ChdJson.validateJSON(String.valueOf(detailMap.get("is_per_bar"))) || "0".equals(String.valueOf(detailMap.get("is_per_bar")))) && ("0".equals(String.valueOf(detailMap.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("04010"))))){
						//System.out.println(jsonObj.get("inv_name").toString()+"不生成个体码");
						if(!ChdJson.validateJSON(String.valueOf(detailMap.get("bar_code")))){
							detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
						}else{
							detailMap.put("bar_code",  String.valueOf(detailMap.get("bar_code")));//个体码
						}
						//该条明细数据添加到List中
						detailMap.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());//专购品明细表id
						detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
						detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
						insertDetailList.add(detailMap);
					}else{
						//根据一码一物规则自动拆分数量并生成个体码
						for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
							Map<String, Object> barMap = new HashMap<String, Object>();
							detailMap.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());//专购品明细表id
							detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
							detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
							barMap.putAll(detailMap);
							if( i>1){
								barMap.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());
							}
							bar_code = matCommonService.getNextBar_code(bar_code);
							//拆分数量和金额
							barMap.put("amount",  1);//数量 
							if(detailMap.get("num_exchange") != null){ 
								barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
							}
							if(detailMap.get("num") != null){
								barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
							}
							//barMap.put("amount_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("price").toString()), money_sum)));
							barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
							barMap.put("bar_code",  bar_code);//个体码
							//该条明细数据添加到List中
							insertDetailList.add(barMap);
						}
					}
					/*入库单订单关系  专购品订单关系-------begin--*/
//					Map<String,Object> orderRelaMap = new HashMap<String,Object>();
//					orderRelaMap.put("group_id", mainMap.get("group_id"));
//					orderRelaMap.put("hos_id", mainMap.get("hos_id"));
//					orderRelaMap.put("copy_code", mainMap.get("copy_code"));
//					orderRelaMap.put("special_id", mainMap.get("special_id"));
//					orderRelaMap.put("sp_detail_id", detailMap.get("detail_id"));
//					orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
//					orderRelaMap.put("in_id", mainMap.get("in_id"));
//					orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
					
//					orderRelaMap.put("order_id", m.get("order_id"));
//					orderRelaMap.put("order_detail_id", m.get("order_detail_id"));
//					orderRelaMap.put("in_amount", m.get("in_amount"));
//					orderRelaMap.put("special_amount", m.get("in_amount"));
//					orderRelaMap.put("order_amount", m.get("order_amount"));
//					orderRelaList.add(orderRelaMap);
					/*入库单订单关系  专购品订单关系-------end--*/
				
					
				}
//				//添加到主表新增的list中
//				insertMainList.add(mainMap);
				
				//更新个体码
				if(!init_bar_code.equals(bar_code)){
					barCodeMap.put("max_no", bar_code);
					matNoOtherMapper.updateMatNoOther(barCodeMap);
				}
				
				//新增专购品主表数据
				mainMap.put("come_from", "1");
				matSpecialMapper.addSpecialMain(mainMap);

				//添加主表金额和单据来源
				mainMap.put("come_from", 4);
				mainMap.put("amount_money", money_sum);
				
				//新增专购品入库主表数据
				matSpecialMapper.addInMain(mainMap);
				
				//新增专购品出库主表数据
				matSpecialMapper.addOutMain(mainMap);
				
				//批量新增专购品明细数据
				matSpecialMapper.addMatSpecialDetailBatch(insertDetailList);
				
				//批量新增专购品入库明细数据
				matInCommonMapper.addMatInDetailBatch(insertDetailList);
				
				//批量新增专购品出库明细数据
				matSpecialMapper.addMatOutDetailBatch(insertDetailList);
				
				//维护专购品  与出入库对应关系
				Map<String,Object> relaMap = new HashMap<String,Object>();
				relaMap.put("group_id", mainMap.get("group_id"));
				relaMap.put("hos_id", mainMap.get("hos_id"));
				relaMap.put("copy_code", mainMap.get("copy_code"));
				relaMap.put("special_id", mainMap.get("special_id"));
				relaMap.put("special_no", mainMap.get("special_no"));
				relaMap.put("out_id", mainMap.get("out_id"));
				relaMap.put("out_no", mainMap.get("out_no"));
				relaMap.put("in_id", mainMap.get("in_id"));
				relaMap.put("in_no", mainMap.get("in_no"));
				matSpecialMapper.addMatSpecialRela(relaMap);
				
				//新增入库资金来源表
				mainMap.put("source_money", money_sum);
				matInCommonMapper.insertMatInResource(mainMap);
				
				//新增出库资金来源表
				matOutResourceMapper.add(mainMap);
				
//				//新增入库单订单关系
//				if(orderRelaList.size() > 0){
//					matSpecialMapper.insertOrderRela(orderRelaList);
//				}
//				//新增专购品订单关系
//				if(orderRelaList.size() > 0){
//					matSpecialMapper.addSpecialOrderRelaBatch(orderRelaList);
//				}
			}
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
	 * 批量添加专购品入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatInitInCommon\"}";
		}
	}
	
	/**
	 * @Description 
	 * 更新专购品入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			//截取期间
			String[] date = entityMap.get("make_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
				return "{\"error\":\"更新失败,单据状态不为未审核状态！.\",\"state\":\"false\"}";
			}
			//状态判断
			if(matCommonService.existsStateBatch("mat_special_main", "special_id", entityMap.get("special_id").toString(), "state", "1", null) > 0){
				return "{\"error\":\"更新失败,单据状态不是未审核状态！\",\"state\":\"false\"}";
			}
			
			//判断库房是否已启用
			int flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("make_date")))){
				entityMap.put("make_date", DateUtil.stringToDate( entityMap.get("make_date").toString(), "yyyy-MM-dd"));
			}
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("in_date")))){
				entityMap.put("in_date", DateUtil.stringToDate( entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("out_date")))){
				entityMap.put("out_date", DateUtil.stringToDate( entityMap.get("out_date").toString(), "yyyy-MM-dd"));
			}
			
			Map<String,Object> data = matSpecialMapper.queryMatInOutData(entityMap);
			
			//入库单ID
			entityMap.put("in_id", data.get("in_id"));
			//入库单号
			entityMap.put("in_no", data.get("in_no"));
			//出库单ID
			entityMap.put("out_id", data.get("out_id"));
			//出库单号
			entityMap.put("out_no", data.get("out_no"));
			
			Map<String,Object> deleteMap = new HashMap<String,Object>();
			
			deleteMap.put("group_id", entityMap.get("group_id"));
			deleteMap.put("hos_id", entityMap.get("hos_id"));
			deleteMap.put("copy_code", entityMap.get("copy_code"));
			deleteMap.put("special_id", entityMap.get("special_id"));
			deleteMap.put("special_no", entityMap.get("special_no"));
			deleteMap.put("in_id", entityMap.get("in_id"));
			deleteMap.put("in_no", entityMap.get("in_no"));
			deleteMap.put("out_id", entityMap.get("out_id"));
			deleteMap.put("out_no", entityMap.get("out_no"));
			
			//删除专购品 明细数据
			matSpecialMapper.deleteSpecialDetail(deleteMap);
			//删除入库单明细数据
			matInCommonMapper.deleteMatInDetail(deleteMap);
			//删除出库单明细数据
			matOutDetailMapper.delete(deleteMap);
			//组装明细
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
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
				
				/*
				 //更新 专购品明细表数据 用List
				//List<Map<String,Object>> detailUpdateList = new ArrayList<Map<String,Object>>();
				 */		
				
				//添加专购品明细表数据 用List
				List<Map<String,Object>> detailAddList = new ArrayList<Map<String,Object>>();
				
				//添加 专购品入库明细用List
				List<Map<String,Object>> addInDetailList = new ArrayList<Map<String,Object>>();
				
				//添加 专购品出库明细用List
				List<Map<String,Object>> addOutDetailList = new ArrayList<Map<String,Object>>();
				//添加 专购品入库单订单关系 用List
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				StringBuffer detail_ids = new StringBuffer();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						Map<String,Object> detailMap=new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("special_id", entityMap.get("special_id"));//专购品主表 单据ID
						detailMap.put("special_no", entityMap.get("special_no"));//专购品主表  单据号
						detailMap.put("in_id", entityMap.get("in_id"));//专购品入库主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//专购品入库主表  入库单号
						//detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
						detailMap.put("out_id", entityMap.get("out_id"));//专购品出库主表ID
						detailMap.put("out_no", entityMap.get("out_no"));//专购品出库主表 出库单号
						//detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料变更号
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						//detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(jsonObj.get("amount").toString())*Double.valueOf(jsonObj.get("price").toString()))));//金额
						//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
						
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  jsonObj.get("batch_no").toString());//批号
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
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
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
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id",  jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_code")))){
							detailMap.put("cert_code",  jsonObj.get("cert_code"));//注册编码
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate( jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("serial_no")))){
							detailMap.put("serial_no",  jsonObj.get("serial_no"));//序列号
						}
						
						//明细表ID
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("detail_id")))){
							// 专购品明细表 ID
							detailMap.put("detail_id",  matSpecialMapper.queryMatSpecialDetailSeq());
							detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
							detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
							//生成个体码
							if((!ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar"))) || "0".equals(String.valueOf(jsonObj.get("is_per_bar")))) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("04010"))))){
								if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
									detailMap.put("bar_code",  String.valueOf(jsonObj.get("bar_code")));//个体码
								}else{
									detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
								}
								//该条明细数据添加到List中
								detailAddList.add(detailMap);
								addInDetailList.add(detailMap);
								addOutDetailList.add(detailMap);
								
							}else{
								//根据一码一物规则自动拆分数量并生成个体码
								for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
									Map<String, Object> barMap = new HashMap<String, Object>();
									detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
									detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
									barMap.putAll(detailMap);
									bar_code = matCommonService.getNextBar_code(bar_code);
									if( i>1){
										barMap.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());
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
									detailAddList.add(detailMap);
									addInDetailList.add(detailMap);
									addOutDetailList.add(detailMap);
								}
							}
						}else{
							if(jsonObj.containsKey("bar_code")){
								if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
									detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
								}else{
									detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
								}
							}else{
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}
							
							//detailMap.put("detail_id",  matSpecialMapper.queryMatSpecialDetailSeq());
							detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//专购品入库明细表id
							detailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//专购品入库明细表id
							
							detail_ids.append(jsonObj.get("detail_id").toString()).append(",");
							detailMap.put("detail_id", jsonObj.get("detail_id"));
							//detailUpdateList.add(detailMap);
							detailAddList.add(detailMap);
							addInDetailList.add(detailMap);
							addOutDetailList.add(detailMap);
						}
						/*专购品入库单订单关系   专购品订单关系-------begin--*/
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("order_rela")))){
							Map<String,Object> orderRelaMap = new HashMap<String,Object>();
							orderRelaMap.put("group_id", entityMap.get("group_id"));
							orderRelaMap.put("hos_id", entityMap.get("hos_id"));
							orderRelaMap.put("copy_code", entityMap.get("copy_code"));
							orderRelaMap.put("special_id", entityMap.get("special_id"));
							orderRelaMap.put("in_id", entityMap.get("in_id"));
							orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
							orderRelaMap.put("sp_detail_id", detailMap.get("detail_id"));
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.getString("order_rela").toString())){
								orderRelaMap.put("order_id", m.get("order_id"));
								orderRelaMap.put("order_detail_id", m.get("order_detail_id"));
								orderRelaMap.put("special_amount", m.get("in_amount"));
								orderRelaMap.put("in_amount", m.get("in_amount"));
								orderRelaMap.put("order_amount", m.get("order_amount"));
								orderRelaList.add(orderRelaMap);
							}
						}
						/*订单关系-------end--*/
					}
				}
				//更新个体码
				if(!init_bar_code.equals(bar_code)){
					barCodeMap.put("max_no", bar_code);
					matNoOtherMapper.updateMatNoOther(barCodeMap);
				}
				
				//修改 专购品主表数据
				matSpecialMapper.updateSpecialMain(entityMap);
				//添加主表金额和单据来源
				entityMap.put("come_from", 4);
				entityMap.put("amount_money", money_sum);
				//修改 专购品入库主表数据
				matInCommonMapper.updateMatInMain(entityMap);
				//修改 专购品出库主表数据
				matSpecialMapper.updateMatOutMain(entityMap);
				/*//组装 删除 页面上删掉的专购品明细数据  所需的条件数据
				if(detail_ids.length() > 0){
					Map<String,Object> pageMap = new HashMap<String,Object>();
					pageMap.put("group_id", entityMap.get("group_id"));
					pageMap.put("hos_id", entityMap.get("hos_id"));
					pageMap.put("copy_code", entityMap.get("copy_code"));
					pageMap.put("special_id", entityMap.get("special_id"));
					pageMap.put("detail_ids", detail_ids.substring(0,detail_ids.length()-1).toString());//明细IDS
					//删除页面上删掉的  专购品明细记录
					matSpecialMapper.deleteMatSpecialDetailForUpdate(pageMap);
				}*/
					
				//批量添加  专购品明细记录
				if(detailAddList.size() > 0){
					matSpecialMapper.addMatSpecialDetailBatch(detailAddList);
				}
			/*	//修改 专购品明细数据
				if(detailUpdateList.size() > 0){
					matSpecialMapper.updateMatSpecialDetailBatch(detailUpdateList);
				}*/
				
				//批量添加 专购品入库明细数据
				matInCommonMapper.addMatInDetailBatch(addInDetailList);
				
				
				//批量添加 专购品出库明细数据
				matSpecialMapper.addMatOutDetailBatch(addOutDetailList);
				
				//修改资金来源表
				entityMap.put("source_money", money_sum);
				//入库
				matInCommonMapper.updateMatInResource(entityMap);
				//出库
				matSpecialMapper.updateMatOutResource(entityMap);
				
				//删除入库单订单关系表
				matSpecialMapper.deleteOrderRela(entityMap);
				
				//删除 专购品订单关系
				matSpecialMapper.deleteSpecialOrderRela(entityMap);
				
				//重新建立入库单订单关系表
				if(orderRelaList.size() > 0){
					matSpecialMapper.insertOrderRela(orderRelaList);
				}
				
				//重新建立 专购品订单关系表
				if(orderRelaList.size() > 0){
					matSpecialMapper.addSpecialOrderRelaBatch(orderRelaList);
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
	 * 批量更新专购品入库，无此业务<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
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
	 * 删除专购品入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			//状态不是新建不能删除
			if(matCommonService.existsStateBatch("mat_special_main", "special_id", entityMap.get("special_id").toString(), "state", "1", null) > 0){
				return "{\"error\":\"删除失败,单据状态不是未审核状态！\",\"state\":\"false\"}";
			}
			//期初记账状态不为0的不能删除，从页面判断
			//删除明细
			int state = matInCommonMapper.deleteMatInDetail(entityMap);
			
			if(state > 0){
				//删除主表
				matInCommonMapper.deleteMatInMain(entityMap);
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatInitInCommon\"}";
		}	
  }
    
	/**
	 * @Description 
	 * 批量删除专购品入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/

	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//状态不是新建不能删除
			if(matCommonService.existsStateBatch("mat_special_main", "special_id", null, "state", "1", entityList) > 0){
				return "{\"error\":\"删除失败,存在未审核单据请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			//期初记账状态不为0的不能删除，从页面判断
			
			//批量删除专购品明细数据
			int  special = matSpecialMapper.deleteSpecialDetailBatch(entityList);
			
			//批量删除专购品入库单明细表
			int state = matInCommonMapper.deleteMatInDetailBatch(entityList);
			
			//批量删除专购品出库单明细表
			int count = matOutDetailMapper.deleteBatch(entityList);
			
			//删除入库资金来源表记录
			matSpecialMapper.deleteMatInSourceBatch(entityList);
			
			//删除出库资金来源表记录
			matSpecialMapper.deleteMatOutSourceBatch(entityList);
			
			//批量删除专购品主数据
			matSpecialMapper.deleteBatch(entityList);
			
			//批量删除专购品入库主表
			matInCommonMapper.deleteMatInMainBatch(entityList);
			
			//批量删除专购品出库主表
			matOutMainMapper.deleteBatch(entityList);
			
			//删除专购品出入库对应关系
			matSpecialMapper.deleteBatchSpecialRela(entityList);
			
			//删除入库单订单关系
			matSpecialMapper.deleteInOrderRelaBatch(entityList);
			//删除 专购品订单关系
			matSpecialMapper.deleteSpecialOrderRelaBatch(entityList);
			//删除专购品和代销对应关系
			matSpecialAffiOutRelaMapper.deleteMatSpecialAffiOutRelaBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}	
	}
	
	//冲账
	@Override
	public String offsetMatSpecialBatch(List<Map<String, Object>> listvo) throws DataAccessException {
			
		try {
			
			/*冲销操作中的固定值-----begin--------*/
			Date date = new Date();
			String[] dates = DateUtil.dateToString(date, "yyyy-MM-dd").split("-");
			String user_id = SessionManager.getUserId();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			/*冲销操作中的固定值-----end--------*/
			
			//冲销 专购品主表 MAT_SPECIAL_MAIN 数据 List
			List<Map<String, Object>> specialList = new ArrayList<Map<String, Object>>();
			
			//冲销 专购品明细 MAT_SPECIAL_DETAIL 数据 List
			List<Map<String, Object>> specialDetailList = new ArrayList<Map<String, Object>>();
			
			//冲销 专购品入库主表  MAT_IN_MAIN 数据 List
			List<Map<String, Object>> inList = new ArrayList<Map<String, Object>>();
			
			//冲销 专购品入库明细表  MAT_IN_DETAIL 数据 List
			List<Map<String, Object>> inDetailList = new ArrayList<Map<String, Object>>();
			
			//冲销 专购品出库主表  MAT_OUT_MAIN 数据 List
			List<Map<String, Object>> outList = new ArrayList<Map<String, Object>>();
			
			//冲销 专购品出库明细表  MAT_OUT_DETAIL 数据 List
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String, Object>>();
			
			//冲销 专购品入库资金来源  MAT_IN_RESOURCE 数据 List
			List<Map<String, Object>> inResourceList = new ArrayList<Map<String, Object>>();
			
			//冲销 专购品出库资金来源  MAT_OUT_RESOURCE 数据 List
			List<Map<String, Object>> outResourceList = new ArrayList<Map<String, Object>>();
			
			//冲销 重新维护专购品与出入库对应关系  MAT_SPECIAL_RELA
			List<Map<String, Object>> relaList = new ArrayList<Map<String,Object>>();
			
			for(Map<String, Object> m : listvo){
				//根据 ID 查询 专购品主表 MAT_SPECIAL_MAIN 数据
				Map<String, Object> specialMap = matSpecialMapper.queryMatSpecialById(m);
				// 构建冲销后 专购品主表 MAT_SPECIAL_MAIN 数据 
				specialMap.put("year", dates[0]);//年份
				specialMap.put("month", dates[1]);//月份
				specialMap.put("maker", user_id);//制单人
				specialMap.put("make_date", date);//制单日期
				specialMap.put("state", 1);//状态（新建状态）
				specialMap.put("bus_type_code", "02");//专购品 业务类型改为 冲账（02）
				specialMap.put("brief", "冲销"+specialMap.get("special_no")+" 专购品主表数据");
				specialMap.put("checker", "");
				specialMap.put("check_date", "");
				specialMap.put("confirmer", "");
				specialMap.put("confirm_date", "");
				
				if(specialMap.get("stock_type_code") == null || specialMap.get("stock_type_code") == ""){
					specialMap.put("stock_type_code", "");
				}
				if(specialMap.get("protocol_code") == null || specialMap.get("protocol_code") == ""){
					specialMap.put("protocol_code", "");
				}
				if(specialMap.get("proj_id") == null || specialMap.get("proj_id") == ""){
					specialMap.put("proj_id", "");
				}
				if(specialMap.get("dept_no") == null || specialMap.get("dept_no") == ""){
					specialMap.put("dept_no", "");
				}
				
				// 构建生成专购品主表 单据号 用Map
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", specialMap.get("group_id"));
				map.put("hos_id", specialMap.get("hos_id"));
				map.put("copy_code", specialMap.get("copy_code"));
				map.put("year", dates[0]);
				map.put("month", dates[1]);
				map.put("day", dates[2]);  //用于生成单号
				map.put("store_id", specialMap.get("store_id"));
				map.put("table_code", "MAT_SPECIAL_MAIN");
				map.put("bus_type_code", "48");// 业务类型改为 专购品退货（48）
				map.put("prefixe", "");
				specialMap.put("special_no", matCommonService.getMatNextNo(map));//重新获取专购品主表单据号
				specialMap.put("special_id", matSpecialMapper.queryMatSpecialNextval());//重新获取专购品主表Id
				
				specialList.add(specialMap);
				//查询 对应的 专购品明细数据 
				List<Map<String,Object>> detailList = matSpecialMapper.querySpecialDetail(m);
				
				//循环 构建冲销后的 专购品 明细数据
				for(Map<String,Object> item : detailList){
					item.put("special_id", specialMap.get("special_id"));// 替换 新的专购品主表ID
					item.put("special_no", specialMap.get("special_no"));// 替换 新的专购品主表 单据号
					item.put("detail_id", matSpecialMapper.queryMatSpecialDetailSeq());//重新获取专购品明细ID
					
					item.put("amount", -1 * Float.parseFloat(item.get("amount").toString()));//冲销数量为原来的负数
					item.put("amount_money", -1 * Double.parseDouble(item.get("amount_money").toString()));//冲销金额为原来的负数
					if(item.get("sell_money") != null){
						item.put("sell_money", -1 * Double.parseDouble(item.get("sell_money").toString()));//冲销金额为原来的负数
					}else{
						item.put("sell_money", 0);//冲销金额为原来的负数
					}
					if(item.get("sale_money") != null){
						item.put("sale_money", -1 * Double.parseDouble(item.get("sale_money").toString()));//冲销金额为原来的负数
					}else{
						item.put("sale_money", 0);//冲销金额为原来的负数
					}
					if(item.get("allot_money") != null){
						item.put("allot_money", -1 * Double.parseDouble(item.get("allot_money").toString()));//冲销金额为原来的负数
					}else{
						item.put("allot_money", 0);//冲销金额为原来的负数
					}
					
					specialDetailList.add(item);
				}
				
				//根据 ID查询专购品入库单主表 数据
				Map<String, Object> inMainMap = matSpecialMapper.queryInMainDataById(m);
				
				// 构建冲销后 专购品入库主表 MAT_IN_MAIN 数据 
				inMainMap.put("in_date", date);//编制日期
				inMainMap.put("year", dates[0]);//年份
				inMainMap.put("month", dates[1]);//月份
				inMainMap.put("maker", user_id);//制单人
				inMainMap.put("state", 1);//状态（新建状态）
				inMainMap.put("bus_type_code", "48");//专购品入库 业务类型改为 专购品退货（48）
				inMainMap.put("brief", "冲销"+inMainMap.get("in_no")+"入库单");
				inMainMap.put("checker", "");
				inMainMap.put("check_date", "");
				inMainMap.put("confirmer", "");
				inMainMap.put("confirm_date", "");
				inMainMap.put("is_dir", "0");
				
				if(inMainMap.get("stock_type_code") == null || inMainMap.get("stock_type_code") == ""){
					inMainMap.put("stock_type_code", "");
				}
				if(inMainMap.get("protocol_code") == null || inMainMap.get("protocol_code") == ""){
					inMainMap.put("protocol_code", "");
				}
				if(inMainMap.get("proj_id") == null || inMainMap.get("proj_id") == ""){
					inMainMap.put("proj_id", "");
				}
				//构建生成专购品入库单 单据号 用Map
				Map<String,Object> inMap = new HashMap<String,Object>();
				inMap.put("group_id", inMainMap.get("group_id"));
				inMap.put("hos_id", inMainMap.get("hos_id"));
				inMap.put("copy_code", inMainMap.get("copy_code"));
				inMap.put("year", dates[0]);
				inMap.put("month", dates[1]);
				inMap.put("day", dates[2]);  //用于生成单号
				inMap.put("store_id", inMainMap.get("store_id"));
				inMap.put("table_code", "MAT_IN_MAIN");
				inMap.put("bus_type_code", "48");
				inMap.put("prefixe", "");
				
				inMainMap.put("in_no", matCommonService.getMatNextNo(inMap));//重新获取专购品入库单 单据号
				inMainMap.put("in_id", matInCommonMapper.queryMatInMainSeq());//重新获取专购品入库单 Id
				
				inList.add(inMainMap);
				
				List<Map<String,Object>> matInDetailList = matSpecialMapper.queryMatInDetailById(m);
				
				//循环 构建冲销后的 专购品入库 明细数据
				for(Map<String,Object> item : matInDetailList){
					item.put("in_id", inMainMap.get("in_id"));// 替换 新的专购品入库单 ID
					item.put("in_no", inMainMap.get("in_no"));// 替换 新的专购品入库单 单据号
					item.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());//重新获取专购品入库单 明细ID
					
					item.put("amount", -1 * Float.parseFloat(item.get("amount").toString()));//冲销数量为原来的负数
					item.put("amount_money", -1 * Double.parseDouble(item.get("amount_money").toString()));//冲销金额为原来的负数
					if(item.get("sell_money") != null){
						item.put("sell_money", -1 * Double.parseDouble(item.get("sell_money").toString()));//冲销金额为原来的负数
					}else{
						item.put("sell_money", 0);//冲销金额为原来的负数
					}
					if(item.get("sale_money") != null){
						item.put("sale_money", -1 * Double.parseDouble(item.get("sale_money").toString()));//冲销金额为原来的负数
					}else{
						item.put("sale_money", 0);//冲销金额为原来的负数
					}
					if(item.get("allot_money") != null){
						item.put("allot_money", -1 * Double.parseDouble(item.get("allot_money").toString()));//冲销金额为原来的负数
					}else{
						item.put("allot_money", 0);//冲销金额为原来的负数
					}
					
					inDetailList.add(item);
				}
				
				//根据 ID查询专购品出库单主表 数据
				Map<String, Object> outMainMap = matSpecialMapper.queryMatOutMainByCode(m);
				
				// 构建冲销后 专购品入库主表 MAT_IN_MAIN 数据 
				outMainMap.put("out_date", date);//编制日期
				outMainMap.put("year", dates[0]);//年份
				outMainMap.put("month", dates[1]);//月份
				outMainMap.put("maker", user_id);//制单人
				outMainMap.put("state", 1);//状态（新建状态）
				outMainMap.put("bus_type_code", "50");//专购品出库 业务类型改为 专购品退库（50）
				outMainMap.put("brief", "冲销"+outMainMap.get("out_no")+"出库单");
				outMainMap.put("checker", "");
				outMainMap.put("check_date", "");
				outMainMap.put("confirmer", "");
				outMainMap.put("confirm_date", "");
				outMainMap.put("is_dir", "0");
				
				if(outMainMap.get("use_code") == null || outMainMap.get("use_code") == ""){
					outMainMap.put("use_code", "");
				}
				if(outMainMap.get("his_flag") == null || outMainMap.get("his_flag") == ""){
					outMainMap.put("his_flag", "");
				}
				if(outMainMap.get("proj_id") == null || outMainMap.get("proj_id") == ""){
					outMainMap.put("proj_id", "");
				}
				if(outMainMap.get("dept_no") == null || outMainMap.get("dept_no") == ""){
					outMainMap.put("dept_no", "");
				}
				if(outMainMap.get("dept_emp") == null || outMainMap.get("dept_emp") == ""){
					outMainMap.put("dept_emp", "");
				}
				//构建生成专购品出库单 单据号 用Map
				Map<String,Object> outMap = new HashMap<String,Object>();
				outMap.put("group_id", outMainMap.get("group_id"));
				outMap.put("hos_id", outMainMap.get("hos_id"));
				outMap.put("copy_code", outMainMap.get("copy_code"));
				outMap.put("year", dates[0]);
				outMap.put("month", dates[1]);
				outMap.put("day", dates[2]);  //用于生成单号
				outMap.put("store_id", outMainMap.get("store_id"));
				outMap.put("table_code", "MAT_OUT_MAIN");
				outMap.put("bus_type_code", "50");
				outMap.put("prefixe", "");
				
				outMainMap.put("out_no", matCommonService.getMatNextNo(outMap));//重新获取专购品出库单 单据号
				outMainMap.put("out_id", matOutMainMapper.queryMatOutMainSeq());//重新获取专购品出库单 Id （参数无关紧要）
				
				outList.add(outMainMap);
				
				List<Map<String,Object>> matOutDetailList = matSpecialMapper.queryMatOutDetailById(m);
				
				//循环 构建冲销后的 专购品出库 明细数据
				for(Map<String,Object> item : matOutDetailList){
					item.put("out_id", outMainMap.get("out_id"));// 替换 新的专购品出库单 ID
					item.put("out_no", outMainMap.get("out_no"));// 替换 新的专购品出库单 单据号
					item.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());//重新获取专购品出库单 明细ID
					
					item.put("amount", -1 * Float.parseFloat(item.get("amount").toString()));//冲销数量为原来的负数
					item.put("amount_money", -1 * Double.parseDouble(item.get("amount_money").toString()));//冲销金额为原来的负数
					if(item.get("sell_money") != null){
						item.put("sell_money", -1 * Double.parseDouble(item.get("sell_money").toString()));//冲销金额为原来的负数
					}else{
						item.put("sell_money", 0);//冲销金额为原来的负数
					}
					if(item.get("sale_money") != null){
						item.put("sale_money", -1 * Double.parseDouble(item.get("sale_money").toString()));//冲销金额为原来的负数
					}else{
						item.put("sale_money", 0);//冲销金额为原来的负数
					}
					if(item.get("allot_money") != null){
						item.put("allot_money", -1 * Double.parseDouble(item.get("allot_money").toString()));//冲销金额为原来的负数
					}else{
						item.put("allot_money", 0);//冲销金额为原来的负数
					}
					
					outDetailList.add(item);
				}
				
				//查询入库资金来源
				Map<String,Object> inResource = matSpecialMapper.queryMatInResource(m);
				
				//组装  专购品 冲销 入库 资金来源数据
				inResource.put("in_id", inMainMap.get("in_id"));
				inResource.put("source_money", -1 * Float.parseFloat(inResource.get("source_money").toString()));
				inResourceList.add(inResource);
				//查询出库资金来源
				Map<String,Object> outResource =  matSpecialMapper.queryMatOutResource(m);
				
				//组装  专购品 冲销 出库 资金来源数据
				outResource.put("out_id", outMainMap.get("out_id"));
				if(outResource.get("source_money")!=null && !"".equals(outResource.get("source_money")) ){
					outResource.put("source_money", -1 * Float.parseFloat(outResource.get("source_money").toString()));
				}else{
					outResource.put("source_money", "0.00"); 
				}
				
				outResourceList.add(outResource);
				
				//组装 专购品出入库对应关系 数据
				Map<String,Object> inOutRela = new HashMap<String,Object>();
				inOutRela.put("group_id", specialMap.get("group_id"));
				inOutRela.put("hos_id", specialMap.get("hos_id"));
				inOutRela.put("copy_code", specialMap.get("copy_code"));
				inOutRela.put("special_id", specialMap.get("special_id"));
				inOutRela.put("special_no", specialMap.get("special_no"));
				inOutRela.put("in_id", inMainMap.get("in_id"));
				inOutRela.put("in_no", inMainMap.get("in_no"));
				inOutRela.put("out_id", outMainMap.get("out_id"));
				inOutRela.put("out_no", outMainMap.get("out_no"));
				
				relaList.add(inOutRela);
			}
			
			//批量新增专购品主表数据
			matSpecialMapper.addMatSpecialMainBatch(specialList);
			//批量新增专购品明细数据
			matSpecialMapper.addMatSpecialDetailBatch(specialDetailList);
			
			// 冲账 批量新增  专购品入库 主表数据
			matInCommonMapper.addMatInMainBatch(inList);
			//冲账 批量新增 专购品入库 明细表数据
			matInCommonMapper.addMatInDetailBatch(inDetailList);
			
			//冲账 批量新增  专购品入库 主表数据
			matSpecialMapper.addOutMainBatch(outList);
			
			//冲账 批量新增 专购品入库 明细表数据
			matSpecialMapper.addMatOutDetailBatch(outDetailList);
			
			//冲账 批量新增 专购品入库 资金来源 数据
			//matSpecialMapper.addInResourceBatch(inResourceList);
			matInCommonMapper.insertMatInResourceBatch(inResourceList);
			//冲账 批量新增 专购品出库 资金来源 数据
			//matSpecialMapper.addOutResourceBatch(outResourceList); 
			matOutResourceMapper.addBatch(outResourceList);
			//冲账 批量新增 专购品出入库对应关系 数据
			matSpecialMapper.addSpecialRelaBatch(relaList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"冲账失败\"}");
		}	
	}
	
	//专购品 出入库 审核、消审
	@Override
	public String updateState(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try {	
			//专购品 入库单批量审核、消审
			matSpecialMapper.updateStateSpecial(listVo);
			
			//专购品 入库单批量审核、消审
			matSpecialMapper.updateStateIn(listVo);
			
			//专购品 出库单批量审核、消审
			matSpecialMapper.updateStateOut(listVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	
	//专购品 出入库 确认
	@Override
	public synchronized String confirmMatSpecialBatch(List<Map<String, Object>> listVo) throws DataAccessException {
		
		//批量添加出入库结存表 MAT_FIFO_BALANCE 用List
		List<Map<String,Object>> addFifo = new ArrayList<Map<String,Object>>();
		//批量修改出入库结存表 MAT_FIFO_BALANCE 用List
		List<Map<String,Object>> updateFifo = new ArrayList<Map<String,Object>>();
		//记录操作MAT_FIFO_BALANCE的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invFifoBalanceMap = new HashMap<String, Map<String,Object>>();
		
		//批量添加物资材料结余表MAT_INV_BALANCE 用List
		List<Map<String,Object>> addInv = new ArrayList<Map<String,Object>>();
		//批量修改物资材料结余表MAT_INV_BALANCE 用List
		List<Map<String,Object>> updateInv = new ArrayList<Map<String,Object>>();
		//记录操作MAT_INV_BALANCE的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invBalanceMap = new HashMap<String, Map<String,Object>>();
		
		//批量添加物资批次结余表 MAT_BATCH_BALANCE 用List
		List<Map<String,Object>> addBatch = new ArrayList<Map<String,Object>>();
		//批量修改物资批次结余表 MAT_BATCH_BALANCE 用List
		List<Map<String,Object>> updateBatch = new ArrayList<Map<String,Object>>();
		//记录操作MAT_BATCH_BALANCE的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invBatchBalanceMap = new HashMap<String, Map<String,Object>>();
		
		//批量添加物资材料结存表 MAT_INV_HOLD 用List
		List<Map<String,Object>> addInvHold = new ArrayList<Map<String,Object>>();
		//记录操作MAT_INV_HOLD的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invHoldMap = new HashMap<String, Map<String,Object>>();
		
		// 批量添加 材料灭菌日期表	 MAT_BATCH_DISINFECT
		List<Map<String,Object>> addDisinfect = new ArrayList<Map<String,Object>>();
		//记录操作MAT_BATCH_DESINFECT的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invBatchDisMap = new HashMap<String, Map<String,Object>>();

		// 批量添加 材料期效表  MAT_BATCH_VALIDITY
		List<Map<String,Object>> addVal = new ArrayList<Map<String,Object>>();
		//记录操作MAT_BATCH_DESINFECT的数据，防止重复添加或修改
		Map<String, Map<String, Object>> invBatchValMap = new HashMap<String, Map<String,Object>>();
		
		//Map中的key键
		String invBatchKey, invBatchBarKey, invKey;
		
		//临时Map
		Map<String, Object> invMap;
		try {	
			//判断所选单据是否已结账
			int flag = matSpecialMapper.existsMatSpecialStateForConfrim(listVo);
			if(flag != 0){
				return "{\"error\":\"所选单据中含已确认单据，请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			
			//获取当前日期所在会计期间
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("group_id", SessionManager.getGroupId());
			checkMap.put("hos_id", SessionManager.getHosId());
			checkMap.put("copy_code", SessionManager.getCopyCode());
			checkMap.put("date", listVo.get(0).get("confirm_date"));
//			Date date= new Date();
//			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
//			checkMap.put("date", dateFormater.format(date));
			String yearMonth = matCommonMapper.queryAccYearMonthByDate(checkMap);
			if(yearMonth == null || "".equals(yearMonth)){
				return "{\"error\":\"操作失败，对应期间不存在请配置！\",\"state\":\"false\"}";
			}
			String year = listVo.get(0).get("confirm_date").toString().substring(0, 4);
			String month = listVo.get(0).get("confirm_date").toString().substring(5, 7);
			
			//更新list中的期间
			for(Map<String, Object> mapVo : listVo){
				mapVo.put("year", year);
				mapVo.put("month", month);
			}
			
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			
			StringBuffer storeMsg = new StringBuffer();//存放已结账的库房信息
			
			List<Map<String, Object>> detailList = JsonListMapUtil.toListMapLower(matSpecialMapper.querySpecialDetailForConfrim(listVo));
			
			for(Map<String,Object> item : detailList){
				invKey = item.get("store_id").toString() + item.get("inv_id").toString() + item.get("location_id").toString();
				invBatchKey = item.get("inv_id").toString() + item.get("batch_no").toString();
				invBatchBarKey = invKey + item.get("batch_no") + item.get("batch_sn").toString() + item.get("bar_code").toString();
				
				//添加新期间
				item.put("year", year);
				item.put("month", month);
				
				//判断库房是否已结账
				String store_flag = matCommonMapper.queryMatStoreIsAccount(item);
				if(store_flag != null && !"".equals(store_flag)){
					storeMsg.append(store_flag).append("<br/>");
					continue;
				}
				
				/*******处理MAT_FIFO_BALANCE********BEGIN***************************/
				//查询 出入库结存表 MAT_FIFO_BALANCE 是否存在该记录（存在则修改该记录 否则就添加）
				invMap = new HashMap<String, Object>();  //初始化Map
				if(invFifoBalanceMap.containsKey("add" + invBatchBarKey)){
					//用于添加
					invMap = invFifoBalanceMap.get("add" +invBatchBarKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));

					invFifoBalanceMap.put("add" + invBatchBarKey, invMap);
				}else if(invFifoBalanceMap.containsKey("update" + invBatchBarKey)){
					//用于修改
					invMap = invFifoBalanceMap.get("update" + invBatchBarKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));

					invFifoBalanceMap.put("update" + invBatchBarKey, invMap);
				}else{
					invMap = JsonListMapUtil.toMapLower(matSpecialMapper.queryFifoExists(item));
					if(invMap == null || invMap.size() == 0){
						invMap = new HashMap<String, Object>();
						invMap.put("group_id", item.get("group_id"));
						invMap.put("hos_id", item.get("hos_id"));
						invMap.put("copy_code", item.get("copy_code"));
						invMap.put("store_id", item.get("store_id"));
						invMap.put("inv_id", item.get("inv_id"));
						invMap.put("batch_no", item.get("batch_no"));
						invMap.put("batch_sn", item.get("batch_sn"));
						invMap.put("bar_code", item.get("bar_code"));
						invMap.put("price", item.get("price"));
						invMap.put("sale_price", item.get("sale_price"));
						invMap.put("in_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("in_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("in_sale_money", item.get("sale_money"));
						invMap.put("out_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("out_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("out_sale_money", item.get("sale_money"));
						invMap.put("left_amount", "0");
						invMap.put("left_money", "0");
						invMap.put("left_sale_money", "0");
						invMap.put("remove_zero_error", "0");
						invMap.put("sale_zero_error", "0");
						invMap.put("location_id", item.get("location_id"));
						invFifoBalanceMap.put("add" + invBatchBarKey, invMap);
					}else{
						invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString())));
						invMap.put("in_sale_money", NumberUtil.add(Double.valueOf(invMap.get("in_sale_money").toString()), Double.valueOf(item.get("sale_money").toString())));
						invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
						invMap.put("out_sale_money", NumberUtil.add(Double.valueOf(invMap.get("out_sale_money").toString()), Double.valueOf(item.get("sale_money").toString())));
						invFifoBalanceMap.put("update" + invBatchBarKey, invMap);
					}
				}
				/*******处理MAT_FIFO_BALANCE********END*****************************/
				
				/*******处理MAT_BATCH_BALANCE******BEGIN***************************/
				//查询 物资批次结余表 MAT_BATCH_BALANCE 是否存在该记录（存在则修改该记录 否则就添加
				invMap = new HashMap<String, Object>();  //初始化Map
				if(invBatchBalanceMap.containsKey("add" + invBatchBarKey)){
					invMap = invBatchBalanceMap.get("add" + invBatchBarKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
					invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					
					invBatchBalanceMap.put("add" + invBatchBarKey, invMap);
				}else if(invBatchBalanceMap.containsKey("update" + invBatchBarKey)){
					//用于修改
					invMap = invBatchBalanceMap.get("update" + invBatchBarKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
					invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					
					invBatchBalanceMap.put("update" + invBatchBarKey, invMap);
				}else{
					invMap = matSpecialMapper.queryBatchExists(item);
					if(invMap == null || invMap.size() == 0 ){
						invMap = new HashMap<String, Object>();
						invMap.put("group_id", item.get("group_id"));
						invMap.put("hos_id", item.get("hos_id"));
						invMap.put("copy_code", item.get("copy_code"));
						invMap.put("year", item.get("year"));
						invMap.put("month", item.get("month"));
						invMap.put("store_id", item.get("store_id"));
						invMap.put("inv_id", item.get("inv_id"));
						invMap.put("batch_no", item.get("batch_no"));
						invMap.put("batch_sn", item.get("batch_sn"));
						invMap.put("bar_code", item.get("bar_code"));
						invMap.put("price", item.get("price"));
						invMap.put("sale_price", item.get("sale_price"));
						invMap.put("begin_amount", "0");
						invMap.put("begin_money", "0");
						invMap.put("begin_sale_money", "0");
						invMap.put("in_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("in_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("in_sale_money", item.get("sale_money"));
						invMap.put("out_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("out_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("out_sale_money", item.get("sale_money"));
						invMap.put("end_amount", "0");
						invMap.put("end_money", "0");
						invMap.put("end_sale_money", "0");
						invMap.put("remove_zero_error", "0");
						invMap.put("sale_zero_error", "0");
						invMap.put("y_in_sale_money", "0");
						invMap.put("y_out_sale_money", "0");
						invMap.put("y_in_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("y_in_money", NumberUtil.numberToRound(Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("y_out_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("y_out_money", NumberUtil.numberToRound(Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("location_id", item.get("location_id"));
						
						invBatchBalanceMap.put("add" + invBatchBarKey, invMap);
					}else{
						invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
						invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						
						invBatchBalanceMap.put("update" + invBatchBarKey, invMap);
					}
				}
				/*******处理MAT_BATCH_BALANCE******END*****************************/
				
				/*******处理MAT_INV_BALANCE*********BEGIN***************************/
				//查询 物资材料结余表MAT_INV_BALANCE 是否存在该记录（存在则修改该记录 否则就添加）
				invMap = new HashMap<String, Object>();  //初始化Map
				if(invBalanceMap.containsKey("add" + invKey)){
					invMap = invBalanceMap.get("add" + invKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
					invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					
					invBalanceMap.put("add" + invKey, invMap);
				}else if(invBalanceMap.containsKey("update" + invKey)){
					//用于修改
					invMap = invBalanceMap.get("update" + invKey);
					invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
					invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
					invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
					
					invBalanceMap.put("update" + invKey, invMap);
				}else{
					invMap = matSpecialMapper.queryInvExists(item);
					if(invMap == null || invMap.size() == 0){
						invMap = new HashMap<String, Object>();
						invMap.put("group_id", item.get("group_id"));
						invMap.put("hos_id", item.get("hos_id"));
						invMap.put("copy_code", item.get("copy_code"));
						invMap.put("year", item.get("year"));
						invMap.put("month", item.get("month"));
						invMap.put("store_id", item.get("store_id"));
						invMap.put("inv_id", item.get("inv_id"));
						invMap.put("begin_amount", "0");
						invMap.put("begin_money", "0");
						invMap.put("begin_sale_money", "0");
						invMap.put("in_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("in_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("in_sale_money", item.get("sale_money"));
						invMap.put("out_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("out_money", Double.valueOf(item.get("amount_money").toString()));
						invMap.put("out_sale_money", item.get("sale_money"));
						invMap.put("end_amount", "0");
						invMap.put("end_money", "0");
						invMap.put("end_sale_money", "0");
						invMap.put("remove_zero_error", "0");
						invMap.put("sale_zero_error", "0");
						invMap.put("y_in_sale_money", "0");
						invMap.put("y_out_sale_money", "0");
						invMap.put("y_in_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("y_in_money", NumberUtil.numberToRound(Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("y_out_amount", Double.valueOf(item.get("amount").toString()));
						invMap.put("y_out_money", NumberUtil.numberToRound(Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("location_id", item.get("location_id"));
						
						invBalanceMap.put("add" + invKey, invMap);
					}else{
						invMap.put("in_amount", NumberUtil.add(Double.valueOf(invMap.get("in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("in_money", NumberUtil.add(Double.valueOf(invMap.get("in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("y_in_amount", NumberUtil.add(Double.valueOf(invMap.get("y_in_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_in_money", NumberUtil.add(Double.valueOf(invMap.get("y_in_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));
						invMap.put("out_amount", NumberUtil.add(Double.valueOf(invMap.get("out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("out_money", NumberUtil.add(Double.valueOf(invMap.get("out_money").toString()), Double.valueOf(item.get("amount_money").toString())));
						invMap.put("y_out_amount", NumberUtil.add(Double.valueOf(invMap.get("y_out_amount").toString()), Double.valueOf(item.get("amount").toString())));
						invMap.put("y_out_money", NumberUtil.add(Double.valueOf(invMap.get("y_out_money").toString()), Double.valueOf(item.get("amount_money").toString()), money_para));

						invBalanceMap.put("update" + invKey, invMap);
					}
				}
				/*******处理MAT_INV_BALANCE*********END*****************************/
				
				/*******处理MAT_INV_HOLD*************BEGIN***************************/
				//查询 物资材料结存表 MAT_INV_HOLD 是否存在该记录（存在则不修改 否则就添加
				invMap = new HashMap<String, Object>();  //初始化Map
				if(!invHoldMap.containsKey(invKey)){
					Long invHold = matSpecialMapper.queryInvHoldExists(item);
					if(invHold == null || invHold == 0 ){
						invMap = new HashMap<String, Object>();

						invMap.put("group_id", item.get("group_id"));
						invMap.put("hos_id", item.get("hos_id"));
						invMap.put("copy_code", item.get("copy_code"));
						invMap.put("year", item.get("year"));
						invMap.put("month", item.get("month"));
						invMap.put("store_id", item.get("store_id"));
						invMap.put("inv_id", item.get("inv_id"));
						invMap.put("cur_amount", 0);
						invMap.put("cur_money", 0);
						invMap.put("location_id", item.get("location_id"));
						invMap.put("imme_amount", "0");
						
						invHoldMap.put(invKey, invMap);
					}
				}
				/*******处理MAT_INV_HOLD*************END*****************************/
				
				//查询 材料期效表  MAT_BATCH_VALIDITY 是否存在该记录（存在则不修改 否则就添加）
				invMap = new HashMap<String, Object>();  //初始化Map
				if("1".equals(String.valueOf(item.get("is_quality"))) && !"-".equals(String.valueOf(item.get("batch_no")))){
					if(!invBatchValMap.containsKey(invBatchKey)){
						Long invVal = matSpecialMapper.queryValidityExists(item);
						if(invVal == 0 && item.get("inva_date") != null && item.get("inva_date") != ""){
							invMap = new HashMap<String, Object>();
							invMap.put("group_id", item.get("group_id"));
							invMap.put("hos_id", item.get("hos_id"));
							invMap.put("copy_code", item.get("copy_code"));
							invMap.put("inv_id", item.get("inv_id"));
							invMap.put("batch_no", item.get("batch_no"));
							invMap.put("inva_date", item.get("inva_date"));
							
							invBatchValMap.put(invBatchKey, invMap);
						}
					}
				}
				
				//查询 材料灭菌日期表MAT_BATCH_DISINFECT 是否存在该记录（存在则不修改 否则就添加）
				invMap = new HashMap<String, Object>();  //初始化Map
				if("1".equals(String.valueOf(item.get("is_disinfect"))) && !"-".equals(String.valueOf(item.get("batch_no")))){
					if(!invBatchDisMap.containsKey(invBatchKey)){
						Long invDis = matSpecialMapper.queryDisinfectExists(item);
						if(invDis == 0 && item.get("distinfect_date") != null && item.get("distinfect_date") != "" ){
							invMap = new HashMap<String, Object>();
							invMap.put("group_id", item.get("group_id"));
							invMap.put("hos_id", item.get("hos_id"));
							invMap.put("copy_code", item.get("copy_code"));
							invMap.put("inv_id", item.get("inv_id"));
							invMap.put("batch_no", item.get("batch_no"));
							invMap.put("distinfect_date", item.get("distinfect_date"));
							
							invBatchDisMap.put(invBatchKey, invMap);
						}
					}
				}
			}
			
			//存在已结账库房
			if(storeMsg != null && !"".equals(storeMsg.toString())){
				return "{\"error\":\"以下库房本期间已结账：<br/>"+storeMsg.toString()+"\"}";
			}
			
			//专购品批量  确认
			matSpecialMapper.confirmMatSpecial(listVo);
			
			//专购品批量入库确认
			matSpecialMapper.confirmMatSpecialIn(listVo);
			
			//专购品批量出库确认
			matSpecialMapper.confirmMatSpecialOut(listVo);

			//维护 出入库结存表 MAT_FIFO_BALANCE
			for (String key : invFifoBalanceMap.keySet()) {
				if(key.startsWith("add")){
					addFifo.add(invFifoBalanceMap.get(key));
				}else{
					updateFifo.add(invFifoBalanceMap.get(key));
				}
			}
			if(addFifo.size()>0 || updateFifo.size()>0){
				if(addFifo.size()>0){
					matFifoBalanceMapper.addBatch(addFifo);
				}
				if(updateFifo.size()>0){
					matFifoBalanceMapper.updateBatch(updateFifo);
				}
			}else{
				throw new SysException("{\"error\":\"确认失败！帐表查询不出数据\"}");
			}
			
			//维护 物资批次结余表 MAT_BATCH_BALANCE
			for (String key : invBatchBalanceMap.keySet()) {
				if(key.startsWith("add")){
					addBatch.add(invBatchBalanceMap.get(key));
				}else{
					updateBatch.add(invBatchBalanceMap.get(key));
				}
			}
			if(addBatch.size()>0 || updateBatch.size()>0){
				if(addBatch.size()>0){
					matBatchBalanceMapper.addBatch(addBatch);
				}else if(updateBatch.size()>0){
					matBatchBalanceMapper.updateBatch(updateBatch);
				}
			}else{
				throw new SysException("{\"error\":\"确认失败！帐表查询不出数据\"}");
			}
			
			//维护 物资材料结余表MAT_INV_BALANCE
			for (String key : invBalanceMap.keySet()) {
				if(key.startsWith("add")){
					addInv.add(invBalanceMap.get(key));
				}else{
					updateInv.add(invBalanceMap.get(key));
				}
			}
			if(addInv.size()>0 || updateInv.size()>0){
				if(addInv.size()>0){
					matInvBalanceMapper.addBatch(addInv);
				}else if(updateInv.size()>0){
					matInvBalanceMapper.updateBatch(updateInv);
				}
			}else{
				throw new SysException("{\"error\":\"确认失败！帐表查询不出数据\"}");
			}
			
			// 维护 物资材料结存表 MAT_INV_HOLD
			for (String key : invHoldMap.keySet()) {
				addInvHold.add(invHoldMap.get(key));
			}
			if(addInvHold.size()>0){
				matInvHoldMapper.addBatch(addInvHold);
			}
			
			// 维护 材料灭菌日期表  MAT_BATCH_DISINFECT
			for (String key : invBatchDisMap.keySet()) {
				addDisinfect.add(invBatchDisMap.get(key));
			}
			if(addDisinfect.size()>0){
				matSpecialMapper.addBatchDisinfect(addDisinfect);
			}
			
			// 维护 材料期效表  MAT_BATCH_VALIDITY 
			for (String key : invBatchValMap.keySet()) {
				addVal.add(invBatchValMap.get(key));
			}
			if(addVal.size()>0){
				matSpecialMapper.addBatchValidity(addVal);
			}
			if(!listVo.isEmpty()){
				String driverClass=null;
				String jdbcUrl=null;
				InputStream in = DbUtil.class.getClassLoader().getResourceAsStream("sqlServer.properties");  
				if(Lang.isEmpty(in)) {
				     return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
				}
				Properties properties = new Properties();  
		        try {
		            properties.load(in);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }  
		        driverClass = properties.getProperty("driver");  
		        jdbcUrl=properties.getProperty("jdbcUrl"); 
		        //取sqlServer.properties配置文件参数 为""则表示不此方法
				if(!"".equals(driverClass)&&!"".equals(jdbcUrl)){
					Map<String, Object> map = new HashMap<String, Object>();
					String id="";
					for (Map<String, Object> m : listVo) {
						if("".equals(id)){
							id=(String) m.get("special_id");
						}else{
							id=","+(String) m.get("special_id");
						}
					}
					map.put("in_ids", id);
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					List<Map<String, Object>> listM=matSpecialMapper.queryMatSpecailInvDetailByIds(map);
					if(!listM.isEmpty()){
						DbUtil sa=new DbUtil();
						CallableStatement sp=null;
						conn=sa.getConnection(); 
						Date date = new Date();
						for (Map<String, Object> ma : listM) {
							logger.info(ma);
							sp=null;
							sp=conn.prepareCall("{call SP_RG_INSERT(?,?,?,?,?,?,?,?,?,?,?,?)}");
							sp.setObject(1,ma.get("INV_CODE"));
							sp.setObject(2,ma.get("INV_NAME"));
							sp.setObject(3,ma.get("batch_no"));
							sp.setObject(4,ma.get("bill_no"));
							sp.setObject(5,ma.get("price"));
							sp.setObject(6,ma.get("amount"));
							sp.setObject(7,String.valueOf(ma.get("inva_date")));
							sp.setObject(8,ma.get("special_id"));
							sp.setObject(9,SessionManager.getUserId());
							sp.setObject(10,DateUtil.dateToString(date, "yyyy-MM-dd"));
							if(0<Integer.valueOf(String.valueOf(ma.get("amount")))){
								sp.setString(11,"1");
							}else{
								sp.setString(11,"-1");
							}
							sp.registerOutParameter(12, Types.VARCHAR);//获取输出参数，以注入位置为准提取
							sp.execute();
							String result=sp.getString(12);
							if(!"1".equals(result)){
								sa.close(sp, conn);
								throw new SysException("{\"error\":\""+result+"}");
							}
						}
						sa.close(sp, conn);
					}
				}
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"确认失败！方法 confirmMatSpecialBatch\"}");
		}
	}
	
	/**
	 * @Description 
	 * 添加专购品入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象专购品入库
		MatInMain matInMain = queryByCode(entityMap);

		if (matInMain != null) {

			int state = matSpecialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matSpecialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatInitInCommon\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 查询结果集  专购品入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatSpecial(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matSpecialMapper.queryMatSpecial(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matSpecialMapper.queryMatSpecial(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象专购品入库<BR> 
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
	 * 获取对象专购品明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatSpecialDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> list = matSpecialMapper.queryMatSpecialDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 获取专购品入库<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInitInCommon
	 * @throws DataAccessException
	*/
	
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matSpecialMapper.queryByUniqueness(entityMap);
	}
	
	/*public List<?> queryExists(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	@Override
	public String queryOrder(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matSpecialMapper.queryOrder(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matSpecialMapper.queryOrder(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public String queryOrderDetail(List<Map<String, Object>> entityMap) throws DataAccessException {
		//集团、单位变量
		Integer group_id = null, hos_id = null;
		//帐套、入库单Id、材料Id
		String copy_code = "", order_ids = "";
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
			order_ids = order_ids + m.get("in_id").toString() + ",";
		}
		//组装数据
		orderMap.put("group_id", group_id);
		orderMap.put("hos_id", hos_id);
		orderMap.put("copy_code", copy_code);
		orderMap.put("order_ids", order_ids.substring(0, order_ids.length()-1));
		
		List<?> list = matSpecialMapper.queryOrderDetail(orderMap);
			
		return ChdJson.toJson(list);
	}
	/**
	 * 代销出库单主数据查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAffiOut(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matSpecialMapper.queryAffiOut(entityMap));
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matSpecialMapper.queryAffiOut(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list), page.getTotal());
		}
	}
	
	/**
	 * 代销出库单 明细数据 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatAffiDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matSpecialMapper.queryMatAffiDetail(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	@Override
	public String queryProtocol(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = matSpecialMapper.queryProtocol(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * 专购品 更新页面回值查询
	 * @param mapVo
	 * @return
	 */
	@Override
	public Map<String, Object> queryMatSpecialMainUpdate(Map<String, Object> mapVo) throws DataAccessException{
		
		return matSpecialMapper.queryMatSpecialMainUpdate(mapVo);
	}
	/**
	 * 根据专购品主表ID 查询专购品  出入库单Id、出入库单号
	 * @param mapVo
	 * @return
	 */
	@Override
	public Map<String,Object> queryMatInOutData(Map<String, Object> mapVo) throws DataAccessException{
		return matSpecialMapper.queryMatInOutData(mapVo);
	}
	/**
	 * 根据 专购品入库单Id 查询 专购品入库单明细
	 * @param mapVo
	 * @return
	 */
	@Override
	public List<Map<String, Object>> querySpecialDetail(Map<String, Object> mapVo) throws DataAccessException{
		return matSpecialMapper.querySpecialDetail(mapVo);
	}
	/**
	 * 查询 专购品出库单序列
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatOutMainNextval(Map<String, Object> mapVo) throws DataAccessException{
		return matOutMainMapper.queryMatOutMainSeq();
	}
	/**
	 * 查询 专购品入库单序列
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatInMainSeq() throws DataAccessException{
		return   matInCommonMapper.queryMatInMainSeq();
	}
	
	/**
	 * 上一张 、 下一张
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatSpecialBeforeOrNextNo(Map<String, Object> mapVo) throws DataAccessException{
		try {	
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			//上一张
			if("-1".equals(mapVo.get("flag").toString())){
				map = matSpecialMapper.queryMatSpecialBefore(mapVo);
				if(map == null){
					return "{\"state\":\"false\"}";
				}
			//下一张
			}else if("1".equals(mapVo.get("flag").toString())){
				map = matSpecialMapper.queryMatSpecialNext(mapVo);
				if(map == null ){
					return "{\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"操作失败 页面参数异常 请联系管理员！方法queryMatSpecialBeforeOrNextNo\"}";
			}
			
			return "{\"state\":\"true\",\"update_para\":\""+
					mapVo.get("group_id").toString()+","+
					mapVo.get("hos_id").toString()+","+
					mapVo.get("copy_code").toString()+","+
					map.get("special_id")+","+
					map.get("special_no")+","
					+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatSpecialBeforeOrNextNo\"}";
		}	
	}
	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int updateAffiOutState(Map<String, Object> mapVo) throws DataAccessException {
		return matSpecialMapper.updateAffiOutState(mapVo);
	}
	
	//入库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMatSpecialByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		//查询入库主表

		if(entityMap.get("p_num") != null && "1".equals(entityMap.get("p_num").toString())){
			List<Map<String,Object>> map=matSpecialMapper.queryMatSpecialPrintTemlateByMainBatch(entityMap);
			//查询入库明细表
			List<Map<String,Object>> list=matSpecialMapper.queryMatSpecialPrintTemlateByDetail(entityMap);
			
			return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			
		}else{
			Map<String,Object> map=matSpecialMapper.queryMatSpecialPrintTemlateByMain(entityMap);
				
			//查询入库明细表
			List<Map<String,Object>> list;

			if(entityMap.get("is_collect") != null && "1".equals(entityMap.get("is_collect").toString())){
				
				list=matSpecialMapper.queryMatSpecialPrintTemlateByDetailCollect(entityMap);
			}else{
				
				list=matSpecialMapper.queryMatSpecialPrintTemlateByDetail(entityMap);
			}
			
			return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
		}
	}
		
		
	/**
	 * 代销使用生成
	 */
	@Override
	public String addByAffiOut(Map<String, Object> entityMap) throws DataAccessException {
		try{
			/**-------------常量-------begin---------*/
			Date make_date = new Date();
			String[] dates =  DateUtil.dateToString(make_date, "yyyy-MM-dd").split("-");
			String year = dates[0];
			String month = dates[1];
			String day = dates[2];
			String user_id = SessionManager.getUserId();
			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString();
			String copy_code = entityMap.get("copy_code").toString();
			/**-------------常量-------end-----------*/
			
			//用于返回生成的专购品单据ID（用逗号隔开）
			String special_ids = "";
			
			/**---------用于单号生成--------begin-----------*/
			Map<String, Object> specialCodeMap = new HashMap<String, Object>();
			specialCodeMap.put("group_id", group_id);
			specialCodeMap.put("hos_id", hos_id);
			specialCodeMap.put("copy_code", copy_code);
			specialCodeMap.put("year", year);
			specialCodeMap.put("month", month);
			specialCodeMap.put("day", day);
			specialCodeMap.put("table_code", "MAT_SPECIAL_MAIN");
			specialCodeMap.put("bus_type_code", "47");
			specialCodeMap.put("prefixe", "");

			Map<String, Object> inCodeMap = new HashMap<String, Object>();
			inCodeMap.put("group_id", group_id);
			inCodeMap.put("hos_id", hos_id);
			inCodeMap.put("copy_code", copy_code);
			inCodeMap.put("year", year);
			inCodeMap.put("month", month);
			inCodeMap.put("day", day);
			inCodeMap.put("store_id", entityMap.get("store_id"));
			inCodeMap.put("table_code", "MAT_IN_MAIN");
			inCodeMap.put("bus_type_code", "47");
			inCodeMap.put("prefixe", "");
			
			Map<String, Object> outCodeMap = new HashMap<String, Object>();
			outCodeMap.put("group_id", group_id);
			outCodeMap.put("hos_id", hos_id);
			outCodeMap.put("copy_code", copy_code);
			outCodeMap.put("year", year);
			outCodeMap.put("month", month);
			outCodeMap.put("day", day);
			outCodeMap.put("store_id", entityMap.get("store_id"));
			outCodeMap.put("table_code", "MAT_OUT_MAIN");
			outCodeMap.put("bus_type_code", "49");
			outCodeMap.put("prefixe", "");
			/**---------用于单号生成--------end-------------*/
			
			//存放主表信息
			List<Map<String, Object>> spcMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> inMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> outMainList = new ArrayList<Map<String,Object>>();
			//存放明细信息
			List<Map<String, Object>> spcDetailList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> inDetailList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String,Object>>();
			//存放对应关系
			List<Map<String, Object>> affiRelaList= new ArrayList<Map<String,Object>>();
			//存放对应关系
			List<Map<String, Object>> spcRelaList= new ArrayList<Map<String,Object>>();
			
			//记录主表信息
			Map<String, Map<String, Object>> spcMain = new HashMap<String, Map<String, Object>>();
			String spcMainKey = "", inMainKey = "", outMainKey = "";  
			//记录明细表信息
			Map<String, Map<String, Object>> spcDetail = new HashMap<String, Map<String, Object>>();
			String spcDetailKey = "", inDetailKey = "", outDetailKey = "";
			
			Long special_id, out_id, in_id;
			String special_no, out_no, in_no;
			Double sum_money = 0.0;
			boolean isContainsSup = true;
			StringBuffer storeMsg = new StringBuffer();//记录未启用的库房
			String brief = "";//摘要
			StringBuffer hisCode = new StringBuffer();//摘要中的住院号
			int hisCodeIndex = 0;//"住院号："在摘要中的位置
			
			//查询所选主从表信息
			List<Map<String, Object>> spcList = JsonListMapUtil.toListMapLower(matSpecialMapper.queryAffiForAdd(entityMap));
			
			for(Map<String, Object> map : spcList){
				//用于保存的主表数据
				Map<String, Object> mainMap, inMainMap, outMainMap;
				//判断供应商是否为空
				if(map.get("sup_id") == null){
					isContainsSup = false;
					break;
				}
				//获取摘要中的住院号
				if(map.get("brief") != null){
					brief = map.get("brief").toString();
					hisCodeIndex = brief.lastIndexOf("住院号：");
					if(hisCodeIndex > 0){
						hisCode.append(brief.substring(hisCodeIndex + 4, brief.length())).append("/");
					}
				}
				//判断库房是否已启用
				Map<String, Object> checkMap = new HashMap<String, Object>();
				checkMap.put("group_id", group_id);
				checkMap.put("hos_id", hos_id);
				checkMap.put("copy_code", copy_code);
				checkMap.put("store_id", map.get("store_id"));
				checkMap.put("year", year);
				checkMap.put("month", month);
				String store_flag = matCommonMapper.queryMatStoreIsStart(checkMap);
				if(store_flag != null && !"".equals(store_flag)){
					storeMsg.append(store_flag).append("<br/>");
					continue;
				}
				
				spcMainKey = map.get("store_id").toString() + map.get("sup_id").toString() + map.get("dept_id").toString();
				inMainKey = "in" + spcMainKey;
				outMainKey = "out" + spcMainKey;
				spcDetailKey = spcMainKey + map.get("inv_id").toString() + map.get("bar_code").toString() + map.get("batch_no").toString() + map.get("batch_sn").toString() + map.get("price").toString();
				inDetailKey = "in" + spcDetailKey;
				outDetailKey = "out" + spcDetailKey;
				
				if(spcMain.containsKey(spcMainKey)){
					mainMap = spcMain.get(spcMainKey);
					inMainMap = spcMain.get(inMainKey);
					outMainMap = spcMain.get(outMainKey);
					special_id = Long.parseLong(mainMap.get("special_id").toString());
					special_no = mainMap.get("special_no").toString();
					in_id = Long.parseLong(inMainMap.get("in_id").toString());
					in_no = inMainMap.get("in_no").toString();
					out_id = Long.parseLong(outMainMap.get("out_id").toString());
					out_no = outMainMap.get("out_no").toString();
					sum_money = Double.valueOf(inMainMap.get("source_money").toString());
					//更细摘要，数据库字段长度为100超出需截取
					if(hisCode.length() > 87){
						mainMap.put("brief", hisCode.toString().substring(0, 87) + "代销使用生成");
						inMainMap.put("brief", hisCode.toString().substring(0, 87) + "代销使用生成");
						outMainMap.put("brief", hisCode.toString().substring(0, 87) + "代销使用生成");
					}else{
						mainMap.put("brief", hisCode.toString() + "代销使用生成");
						inMainMap.put("brief", hisCode.toString() + "代销使用生成");
						outMainMap.put("brief", hisCode.toString() + "代销使用生成");
					}
					spcMain.put(spcMainKey, mainMap);
					spcMain.put(inMainKey, inMainMap);
					spcMain.put(outMainKey, outMainMap);
				}else{
					sum_money = 0.0;
					mainMap = new HashMap<String, Object>();
					inMainMap = new HashMap<String, Object>();
					outMainMap = new HashMap<String, Object>();
					//专购品主表信息
					mainMap.put("group_id", group_id);
					mainMap.put("hos_id", hos_id);
					mainMap.put("copy_code", copy_code);
					mainMap.put("year", year);
					mainMap.put("month", month);
					mainMap.put("make_date", make_date);
					mainMap.put("maker", user_id);
					mainMap.put("bus_type_code", "01");
					mainMap.put("store_id", map.get("store_id"));
					mainMap.put("store_no", map.get("store_no"));
					mainMap.put("dept_id", map.get("dept_id"));
					mainMap.put("dept_no", map.get("dept_no"));
					mainMap.put("dept_emp", map.get("dept_emp"));
					mainMap.put("use_code", map.get("use_code"));
					mainMap.put("proj_id", map.get("proj_id"));
					mainMap.put("stocker", map.get("stocker"));
					mainMap.put("sup_id", map.get("sup_id"));
					mainMap.put("sup_no", map.get("sup_no"));
					mainMap.put("brief", hisCode.toString() + "代销使用生成");
					mainMap.put("state", 1);
					mainMap.put("come_from", "2");
					/**自动生成专购品单据号-----------------------------begin---------------------------*/
					specialCodeMap.put("store_id", map.get("store_id"));
					special_no = matCommonService.getMatNextNo(specialCodeMap);
					mainMap.put("special_no", special_no);
					entityMap.put("special_no", special_no);
					
					/**自动生成专购品单据号-----------------------------end-----------------------------*/
					//获得自增ID
					special_id = matSpecialMapper.queryMatSpecialNextval();
					special_ids += special_id + ",";
					mainMap.put("special_id", String.valueOf(special_id));
					entityMap.put("special_id", special_id);
					
					/**同时生成入库单-------------begin----------------*/
					inMainMap.putAll(mainMap);
					inMainMap.put("in_date", make_date);
					if(Double.parseDouble(map.get("amount_money").toString())>0){
					inMainMap.put("bus_type_code", "47");	//47专购品入库
					}else{
					inMainMap.put("bus_type_code", "48");	//48专购品退货
					}
					
					//单号
					inCodeMap.put("store_id", map.get("store_id"));
					in_no = matCommonService.getMatNextNo(inCodeMap);
					inMainMap.put("in_no", in_no);
					in_id = matInCommonMapper.queryMatInMainSeq();
					inMainMap.put("in_id", String.valueOf(in_id));
					/**同时生成入库单-------------end------------------*/
					/**同时生成出库单-------------begin----------------*/
					outMainMap.putAll(mainMap);
					outMainMap.put("out_date", make_date);
					if(Double.parseDouble(map.get("amount_money").toString())>0){
					outMainMap.put("bus_type_code", "49");	//49专购品出库
					}else{
					outMainMap.put("bus_type_code", "50");	//50专购品退库
					}
					
					//单号
					outCodeMap.put("store_id", map.get("store_id"));
					out_no = matCommonService.getMatNextNo(outCodeMap);
					outMainMap.put("out_no", out_no);
					out_id = Long.valueOf(matOutMainMapper.queryMatOutMainSeq());
					outMainMap.put("out_id", String.valueOf(out_id));
					/**同时生成出库单-------------end----------------*/

					/**处理专购品与出入库对应关系----------begin-----------*/
					Map<String, Object> spcRelaMap = new HashMap<String, Object>();
					spcRelaMap.put("group_id", group_id);
					spcRelaMap.put("hos_id", hos_id);
					spcRelaMap.put("copy_code", copy_code);
					spcRelaMap.put("special_id", special_id);
					spcRelaMap.put("special_no", special_no);
					spcRelaMap.put("in_id", in_id);
					spcRelaMap.put("in_no", in_no);
					spcRelaMap.put("out_id", out_id);
					spcRelaMap.put("out_no", out_no);
					spcRelaList.add(spcRelaMap);
					/**处理专购品与出入库对应关系----------end-------------*/
				}
				Map<String, Object> detailMap, inDetailMap, outDetailMap;
				if(spcDetail.containsKey(spcDetailKey)){
					detailMap = spcDetail.get(spcDetailKey);
					detailMap.put("amount", ""+(Double.valueOf(detailMap.get("amount").toString())+Double.valueOf(map.get("amount").toString())));
					detailMap.put("amount_money", ""+(Double.valueOf(detailMap.get("amount_money").toString())+Double.valueOf(map.get("amount_money").toString())));
					detailMap.put("sell_money", ""+(Double.valueOf(detailMap.get("sell_money").toString())+Double.valueOf(map.get("sell_money").toString())));
					detailMap.put("sale_money", ""+(Double.valueOf(detailMap.get("sale_money").toString())+Double.valueOf(map.get("sale_money").toString())));
					detailMap.put("allot_money", ""+(Double.valueOf(detailMap.get("allot_money").toString())+Double.valueOf(map.get("allot_money").toString())));
					
					inDetailMap = spcDetail.get(inDetailKey);
					inDetailMap.put("amount", ""+(Double.valueOf(inDetailMap.get("amount").toString())+Double.valueOf(map.get("amount").toString())));
					inDetailMap.put("amount_money", ""+(Double.valueOf(inDetailMap.get("amount_money").toString())+Double.valueOf(map.get("amount_money").toString())));
					inDetailMap.put("sell_money", ""+(Double.valueOf(inDetailMap.get("sell_money").toString())+Double.valueOf(map.get("sell_money").toString())));
					inDetailMap.put("sale_money", ""+(Double.valueOf(inDetailMap.get("sale_money").toString())+Double.valueOf(map.get("sale_money").toString())));
					inDetailMap.put("allot_money", ""+(Double.valueOf(inDetailMap.get("allot_money").toString())+Double.valueOf(map.get("allot_money").toString())));
					
					outDetailMap = spcDetail.get(outDetailKey);
					outDetailMap.put("amount", ""+(Double.valueOf(outDetailMap.get("amount").toString())+Double.valueOf(map.get("amount").toString())));
					outDetailMap.put("amount_money", ""+(Double.valueOf(outDetailMap.get("amount_money").toString())+Double.valueOf(map.get("amount_money").toString())));
					outDetailMap.put("sell_money", ""+(Double.valueOf(outDetailMap.get("sell_money").toString())+Double.valueOf(map.get("sell_money").toString())));
					outDetailMap.put("sale_money", ""+(Double.valueOf(outDetailMap.get("sale_money").toString())+Double.valueOf(map.get("sale_money").toString())));
					outDetailMap.put("allot_money", ""+(Double.valueOf(outDetailMap.get("allot_money").toString())+Double.valueOf(map.get("allot_money").toString())));
				}else{
					detailMap = new HashMap<String, Object>();
					inDetailMap = new HashMap<String, Object>();
					outDetailMap = new HashMap<String, Object>();
					
					detailMap.put("group_id", group_id);
					detailMap.put("hos_id", hos_id);
					detailMap.put("copy_code", copy_code);
					
					detailMap.put("special_id", special_id);  //主表ID
					detailMap.put("special_no", special_no);  //主表单号
					detailMap.put("detail_id", String.valueOf(matSpecialMapper.queryMatSpecialDetailSeq()));	//明细ID
					
					detailMap.put("inv_id", map.get("inv_id").toString());  //材料ID
					detailMap.put("inv_no", map.get("inv_no").toString());  //材料变更ID
					
					detailMap.put("batch_no", map.get("batch_no").toString());  //批号
					detailMap.put("batch_sn", map.get("batch_sn").toString());  //批次
					detailMap.put("sn", map.get("bar_code").toString());  //条码
					detailMap.put("bar_code", map.get("bar_code").toString());  //个体条码
					
					detailMap.put("price", map.get("price").toString());  //计划单价
					detailMap.put("amount", map.get("amount").toString());  //数量
					detailMap.put("amount_money", map.get("amount_money").toString());  //金额
					
					detailMap.put("sale_price", map.get("sale_price").toString());  //批发单价
					detailMap.put("sale_money", map.get("sale_money").toString());  //批发金额
					detailMap.put("sell_price", map.get("sell_price").toString());  //零售单价
					detailMap.put("sell_money", map.get("sell_money").toString());  //零售金额
					detailMap.put("allot_price", map.get("allot_price").toString());  //调拨单价
					detailMap.put("allot_money", map.get("allot_money").toString());  //调拨金额
					
					detailMap.put("pack_code", null);  //包装单位
					detailMap.put("pack_price", null);  //包装单价
					detailMap.put("num", null);  //包装数量
					detailMap.put("num_exchange", null);  //包装换算量
					
					detailMap.put("cert_id", map.get("cert_id"));  //注册证号
					detailMap.put("cert_code", map.get("cert_code"));  //注册证号
					detailMap.put("location_id", map.get("location_id"));  //货位
					detailMap.put("inva_date", map.get("inva_date"));  //有效日期
					detailMap.put("disinfect_date", map.get("disinfect_date"));  //灭菌日期
					detailMap.put("fac_date", map.get("fac_date"));  //生产日期
					detailMap.put("serial_no", map.get("serial_no"));  //序列号
					
					detailMap.put("note", map.get("brief") == null ? null : map.get("brief").toString());  //备注
					
					/**生成入库明细-------------begin-----------------*/
					inDetailMap.putAll(detailMap);
					inDetailMap.put("in_id", in_id);
					inDetailMap.put("in_no", in_no);
					inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
					inDetailMap.put("note", map.get("note") == null ? null : map.get("note").toString());  //备注
					/**生成入库明细-------------end-------------------*/
					/**生成出库明细-------------begin-----------------*/
					outDetailMap.putAll(detailMap);
					outDetailMap.put("out_id", out_id);
					outDetailMap.put("out_no", out_no);
					outDetailMap.put("out_detail_id", matSpecialMapper.queryMatOutDetailSeq());
					outDetailMap.put("note", map.get("note") == null ? null : map.get("note").toString());  //备注
					/**生成出库明细-------------end-------------------*/
				}
				/**处理专购品与代销对应关系----------begin-----------*/
				Map<String, Object> affiRelaMap = new HashMap<String, Object>();
				affiRelaMap.put("group_id", group_id);
				affiRelaMap.put("hos_id", hos_id);
				affiRelaMap.put("copy_code", copy_code);
				affiRelaMap.put("affi_out_id", map.get("out_id"));
				affiRelaMap.put("affi_detail_id", map.get("detail_id"));
				affiRelaMap.put("out_amount", map.get("amount"));
				affiRelaMap.put("special_id", special_id);
				affiRelaMap.put("sp_detail_id", detailMap.get("detail_id").toString());
				affiRelaMap.put("special_amount", map.get("amount").toString());
				affiRelaList.add(affiRelaMap);
				/**处理专购品与代销对应关系----------end-------------*/

				//处理资金来源信息
				sum_money += Double.valueOf(map.get("amount_money").toString());
				inMainMap.put("source_money", sum_money);
				inMainMap.put("amount_money", sum_money);
				outMainMap.put("source_money", sum_money);
				outMainMap.put("amount_money", sum_money);
				//存放到spcMain中
				spcMain.put(spcMainKey, mainMap);
				spcMain.put(inMainKey, inMainMap);
				spcMain.put(outMainKey, outMainMap);
				//存放到spcDetail中
				spcDetail.put(spcDetailKey, detailMap);
				spcDetail.put(inDetailKey, inDetailMap);
				spcDetail.put(outDetailKey, outDetailMap);
			}
			
			if(!isContainsSup){
				throw new SysException("{\"error\":\"请选择代销出库材料供应商不能为空\"}");
			}
			
			//存在未启用库房
			if(storeMsg != null && !"".equals(storeMsg.toString())){
				return "{\"error\":\"以下库房本期间未启用：<br/>"+storeMsg.toString()+"\"}";
			}
			
			//解析主表
			for (String key : spcMain.keySet()) {
				if(key.startsWith("in")){
					inMainList.add(spcMain.get(key));
				}else if(key.startsWith("out")){
					outMainList.add(spcMain.get(key));
				}else{
					spcMainList.add(spcMain.get(key));
				}
			}
			//解析明细表
			for (String key : spcDetail.keySet()) {
				if(key.startsWith("in")){
					inDetailList.add(spcDetail.get(key));
				}else if(key.startsWith("out")){
					outDetailList.add(spcDetail.get(key));
				}else{
					spcDetailList.add(spcDetail.get(key));
				}
			}
			
			if(spcDetailList.size() > 0){
				//插入专购品主表
				matSpecialMapper.addMatSpecialMainBatch(spcMainList);
				//插入专购品明细
				matSpecialMapper.addMatSpecialDetailBatch(spcDetailList);
				//插入入库主表
				matInCommonMapper.addMatInMainBatch(inMainList);
				//插入入库明细表
				matInCommonMapper.addMatInDetailBatch(inDetailList);
				//插入入库资金来源表
				matInCommonMapper.insertMatInResourceBatch(inMainList);
				//插入出库主表
				matSpecialMapper.addMatOutMainBySpecialBatch(outMainList);
				//插入出库明细表
				matSpecialMapper.addMatOutDetailBySpecialBatch(outDetailList);
				//插入出库资金来源表
				matOutResourceMapper.addBatch(outMainList);
				//插入与代销的对应关系
				matSpecialAffiOutRelaMapper.addMatSpecialAffiOutRelaBatch(affiRelaList);
				//插入与出入库的对应关系
				matSpecialMapper.addMatSpecialRelaBatch(spcRelaList);
			}else{
				return "{\"error\":\"汇总失败，获取到的明细数据为空！\"}";
			}
			
			return "{\"msg\":\"添加成功\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("special_id").toString()+","+
			entityMap.get("special_no").toString()+","
			+"\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	//代销生成专购品 查询
	@Override
	public String queryMatAffiSpecial(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matSpecialMapper.queryMatAffiSpecial(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matSpecialMapper.queryMatAffiSpecial(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
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
	public String updateMatSpecialInvoice(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			//更新专购品发票号
			matSpecialMapper.updateMatSpecialInvoice(entityMap);  
			//更新入库单发票号
			matStorageInMapper.updateMatStorageInInvoice(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateMatStorageInInvoice\"}";
		}	
		
		return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
	}
	/**
	 * 批量添加选择材料
	 */
	@Override
	public String queryMatSpecailInvBatch(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matSpecialMapper.queryMatSpecailInvBatch(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matSpecialMapper.queryMatSpecailInvBatch(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 批量选择材料生成专购品单
	 */
	@Override
	public String queryMatSpecailInvDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> invList = new ArrayList<Map<String,Object>>();
		
		JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
		Iterator it = json.iterator();
		while (it.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("store_id", entityMap.get("store_id"));
				map.put("sup_id", entityMap.get("sup_id"));
				map.put("inv_id", jsonObj.get("inv_id"));
				map.put("inv_no", jsonObj.get("inv_no"));
				invList.add(map);
			}
		}
		
		List<Map<String,Object>> list=matSpecialMapper.queryMatSpecailInvDetail(invList);
		
		return ChdJson.toJsonLower(list);
	}
	
	//明细查询
	@Override
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matSpecialMapper.queryMatSpecialDetail(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matSpecialMapper.queryMatSpecialDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 请求解析表格数据
	 */
	@Override
	public String importMatCommonInsup(Map<String, Object> map,Map<String,Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			String columns=map.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			String content = map.get("content").toString();
			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			List<Map<String,Object>> listSpe = matCommonMapper.queryMatInvListSpecial(entityMap);
			
			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			
			
			for(Map<String, List<String>> item:liData){
				for(String st :item.keySet()){
					if(item.get(st).get(1) == null){
						break;
					}
					
					
					List<String> inv_code = item.get("材料编码");
					List<String> inv_name = item.get("材料名称");
					List<String> inv_model = item.get("规格型号");
					List<String> unit_name = item.get("计量单位");
					List<String> amount = item.get("数量");
					List<String> price = item.get("单价");
					List<String> amount_money = item.get("金额");
					List<String> cert_code = item.get("注册证号");
					List<String> batch_no = item.get("生产批号");
					List<String> inva_date = item.get("有效日期");
					List<String> fac_date = item.get("生产日期");
					List<String> disinfect_date = item.get("灭菌日期");
					List<String> fac_name = item.get("生产厂商");
					List<String> note = item.get("备注");
					
					/*List<String> bid_code = item.get("交易编码");
					List<String> pack_code = item.get("包装单位");
					List<String> num_exchange = item.get("装换量");
					List<String> num = item.get("包装件数");
					List<String> pack_price = item.get("包装单价");
					List<String> sell_price = item.get("零售单价");
					List<String> sell_money = item.get("零售金额");
					List<String> location_name = item.get("货位名称");
					List<String> location_new_name = item.get("货位");
					List<String> serial_no = item.get("序列号");
					List<String> is_per_bar = item.get("是否个体码");
					*/
					
					
					if(inv_code.get(1) == null){
						return "{\"warn\":\"材料编码为空！\",\"state\":\"false\",\"row_cell\":" +inv_code.get(0) +"\"}";
					}else if(inv_name.get(1) == null){
						return "{\"warn\":\"材料名称为空！\",\"state\":\"false\",\"row_cell\":" +inv_name.get(0) +"\"}";
					}else if(inv_model.get(1) == null){
						return "{\"warn\":\"规格型号为空！\",\"state\":\"false\",\"row_cell\":" +inv_model.get(0) +"\"}";
					}
					else if(unit_name.get(1) == null){
						return "{\"warn\":\"计量单位为空！\",\"state\":\"false\",\"row_cell\":" +unit_name.get(0) +"\"}";
					}
					/*else if(inva_date.get(1) == null){
						return "{\"warn\":\"有效日期为空！\",\"state\":\"false\",\"row_cell\":" +inva_date.get(0) +"\"}";
					}*/else if(fac_date.get(1) == null){
						return "{\"warn\":\"生产日期为空！\",\"state\":\"false\",\"row_cell\":" +fac_date.get(0) +"\"}";
					}else if(cert_code.get(1) == null){
						return "{\"warn\":\"注册编号为空！\",\"state\":\"false\",\"row_cell\":" +cert_code.get(0) +"\"}";
					}
					
						
					if(amount.get(1) == null){
						return "{\"warn\":\"数量为空！\",\"state\":\"false\",\"row_cell\":\" " + amount.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((amount.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + amount.get(1) + ",数量输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + amount.get(0) + "\"}";
						  }
					}
					
					if(price.get(1) == null){
						return "{\"warn\":\"单价为空！\",\"state\":\"false\",\"row_cell\":\" " + price.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((price.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + price.get(1) + ",单价输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + price.get(0) + "\"}";
						  }
					}
					
					if(amount_money.get(1) == null){
						return "{\"warn\":\"金额为空！\",\"state\":\"false\",\"row_cell\":\" " + amount_money.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((amount_money.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + amount_money.get(1) + ",金额输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + amount_money.get(0) + "\"}";
						  }
					}
					
					// 判断可为数字，可为汉字
					/*if(is_per_bar.get(1) == null){
						return "{\"warn\":\"是否个体码为空！\",\"state\":\"false\",\"row_cell\":" +is_per_bar.get(0) +"\"\"}";
					}else if(Character.isDigit(is_per_bar.get(1).charAt(0))){
						is_per_bar.add(1,(is_per_bar.get(1).equals("1")?is_per_bar.get(1).replace("1","是"):is_per_bar.get(1).replace("0","否")));
					}*/
					
					//导入数据匹配材料明细数据，通过code
					for(Map<String,Object> mapSpe :listSpe){//inv_name.get(1).equals(mapSpe.get("inv_name").toString()) && 
						String invCode = mapSpe.get("inv_code").toString();
						if(inv_code.get(1).equals(invCode)){
							//添加数据Map中add到returnList
							Map<String,Object> returnMap = new HashMap<String,Object>();
							returnMap.put("mat_type_id", mapSpe.get("mat_type_id"));
							returnMap.put("mat_type_name", mapSpe.get("mat_type_name"));
							returnMap.put("inv_model", mapSpe.get("inv_model"));
							returnMap.put("unit_code", mapSpe.get("unit_code"));
							returnMap.put("bid_code", mapSpe.get("bid_code"));
							returnMap.put("inv_id", mapSpe.get("inv_id"));
							returnMap.put("inv_no", mapSpe.get("inv_no"));
							returnMap.put("inv_code", mapSpe.get("inv_code"));
							returnMap.put("inv_name", mapSpe.get("inv_name"));
							returnMap.put("inv_model", mapSpe.get("inv_model"));
							returnMap.put("unit_name", mapSpe.get("unit_name"));
							returnMap.put("amount", amount.get(1));
							returnMap.put("price", mapSpe.get("price"));
							BigDecimal b = (BigDecimal)mapSpe.get("price");
							returnMap.put("amount_money", (Double.parseDouble(amount.get(1))* b.doubleValue()));
							
							//获取材料ID，查询该材料的注册证号
							entityMap.put("inv_id", mapSpe.get("inv_id"));
							List<HrpMatSelect> listCertId = hrpMatSelectMapper.queryMatInvCertId(entityMap);
							for(HrpMatSelect h :listCertId){
								if(cert_code.get(1).equalsIgnoreCase(h.getName())){
									returnMap.put("cert_id", h.getCode());
									returnMap.put("cert_code", h.getName());
								}
							}
							
							returnMap.put("batch_no",(batch_no==null?"":batch_no.get(1)));
							returnMap.put("inva_date", inva_date.get(1));
							returnMap.put("fac_date", fac_date.get(1));
							returnMap.put("disinfect_date", (disinfect_date==null?"":disinfect_date.get(1)));
							returnMap.put("sn", "");
							returnMap.put("bar_code", "");
							returnMap.put("fac_name", mapSpe.get("fac_name"));
							returnMap.put("is_per_bar",mapSpe.get("is_per_bar"));
							returnMap.put("note",(note==null?"":note.get(1)));
							listMap.add(returnMap);
						}
					}
					
					//添加数据Map中add到returnList
					/*Map<String,Object> returnMap = new HashMap<String,Object>();
					returnMap.put("inv_code", inv_code.get(1));
					returnMap.put("inv_name", inv_name.get(1));
					returnMap.put("inv_model", inv_model.get(1));
					returnMap.put("unit_name", unit_name.get(1));
					returnMap.put("amount", amount.get(1));
					returnMap.put("price", price.get(1));
					returnMap.put("amount_money", amount_money.get(1));
					returnMap.put("cert_code", cert_id.get(1));
					returnMap.put("batch_no", (batch_no==null?"":batch_no.get(1)));
					returnMap.put("inva_date", inva_date.get(1));
					returnMap.put("fac_date", fac_date.get(1));
					returnMap.put("disinfect_date", (disinfect_date==null?"":disinfect_date.get(1)));
					returnMap.put("sn", (sn==null?"":sn.get(1)));
					returnMap.put("bar_code", (bar_code==null?"":bar_code.get(1)));
					returnMap.put("fac_name", (fac_name==null?"":fac_name.get(1)));
					returnMap.put("is_per_bar",(is_per_bar.get(1).equals("是")?1:0));
					returnMap.put("num_exchange", num_exchange.get(1));
					returnMap.put("pack_code", pack_code.get(1));
					returnMap.put("num", num.get(1));
					returnMap.put("pack_price", pack_price.get(1));
					returnMap.put("sell_price", sell_price.get(1));
					returnMap.put("sell_money", sell_money.get(1));
					returnMap.put("location_name", location_name.get(1));
					returnMap.put("location_new_name", location_new_name.get(1));
					returnMap.put("serial_no", serial_no.get(1));
					returnMap.put("note", note.get(1));
					listMap.add(returnMap);*/
					
					break;
				}
			}
			if(listMap != null && listMap.size() > 0){
				return ChdJson.toJson(listMap);
			}else{
				return "{\"warn\":\"材料编码不存在！！\",\"state\":\"false\",\"row_cell\":\"" + "1" +"\"}";
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"msg\":\"操作失败.\",\"state\":\"false\"}");
		}
		
	}
	
	@Override
	public Map<String, Object> queryMatSpecialByPrintTemlate1(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		//查询入库主表

		Map<String,Object> reMap=new HashMap<String,Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		MatSpecialMapper matSpecialMapper = (MatSpecialMapper)context.getBean("matSpecialMapper");
		
		
		if(entityMap.get("p_num") != null && "1".equals(entityMap.get("p_num").toString())){
			List<Map<String,Object>> map=matSpecialMapper.queryMatSpecialPrintTemlateByMainBatch(entityMap);
			//查询入库明细表
			List<Map<String,Object>> list=matSpecialMapper.queryMatSpecialPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
			
		}else{
			Map<String,Object> map=matSpecialMapper.queryMatSpecialPrintTemlateByMain(entityMap);
				
			//查询入库明细表
			List<Map<String,Object>> list;

			if(entityMap.get("is_collect") != null && "1".equals(entityMap.get("is_collect").toString())){
				
				list=matSpecialMapper.queryMatSpecialPrintTemlateByDetailCollect(entityMap);
			}else{
				
				list=matSpecialMapper.queryMatSpecialPrintTemlateByDetail(entityMap);
			}
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}
	}
	@Override
	public String addAffiRela(List<Map<String, Object>> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try {	
			//专购品 入库单批量审核、消审
			matSpecialMapper.addAffiRelaBatch(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	
	
}
