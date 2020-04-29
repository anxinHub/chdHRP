/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.storage.in;

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
import com.chd.hrp.med.dao.base.MedNoOtherMapper;
import com.chd.hrp.med.dao.base.MedStoreModMapper;
import com.chd.hrp.med.dao.bill.MedBillDetailMapper;
import com.chd.hrp.med.dao.bill.MedBillMainMapper;
import com.chd.hrp.med.dao.initial.MedInitChargeMapper;
import com.chd.hrp.med.dao.storage.back.MedStorageBackMapper;
import com.chd.hrp.med.dao.storage.in.MedStorageInMapper;
import com.chd.hrp.med.entity.MedBillMain;
import com.chd.hrp.med.entity.MedInMain;
import com.chd.hrp.med.entity.MedInResource;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.in.MedStorageInService;
import com.chd.hrp.sup.dao.SupDeliveryMainMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 常备药品入库
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medStorageInService")
public class MedStorageInServiceImpl implements MedStorageInService {

	private static Logger logger = Logger.getLogger(MedStorageInServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medStorageInMapper")
	private final MedStorageInMapper medStorageInMapper = null;
	@Resource(name = "medInCommonMapper")
	private final MedInCommonMapper medInCommonMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	@Resource(name = "medNoOtherMapper")
	private final MedNoOtherMapper medNoOtherMapper = null;
	@Resource(name = "medInitChargeMapper")
	private final MedInitChargeMapper medInitChargeMapper = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	@Resource(name = "medBillMainMapper")
	private final MedBillMainMapper medBillMainMapper = null;
	@Resource(name = "medBillDetailMapper")
	private final MedBillDetailMapper medBillDetailMapper = null;
	@Resource(name = "medStorageBackMapper")
	private final MedStorageBackMapper medStorageBackMapper = null;
	// 引入DAO操作
	@Resource(name = "supDeliveryMainMapper")
	private final SupDeliveryMainMapper supDeliveryMainMapper = null;
	
	@Resource(name = "medStoreModMapper")
	private final MedStoreModMapper medStoreModMapper = null;
	
	
	
	/**
	 * @Description 
	 * 添加常备药品入库<BR> 
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
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断库房是否已启用
			flag = medCommonMapper.existsMedStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成常备药品入库单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MED_IN_MAIN");
				String in_no = medCommonService.getMedNextNo(entityMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				entityMap.put("in_no", in_no);
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", medInCommonMapper.queryMedInMainSeq());
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				float money_sum = 0;//记录明细总金额

				/*用于查询个体码----begin-----*/
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
				/*用于查询个体码----end-------*/
				
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
						detailMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//药品ID
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
						
						money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额

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
						//生成个体码--如果系统参数08010高值药品自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if("0".equals(detailMap.get("is_per_bar").toString()) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("08010"))))){
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
								bar_code = medCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
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
						medNoOtherMapper.updateMedNoOther(barCodeMap);
					}
					//添加发票号和发票时间
					if (entityMap.get("bill_date") != null ) {
						if(!entityMap.get("bill_date").toString().equals("")){
							entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
						}
					}
					
					if (entityMap.get("bill_no") != null) {
						
						entityMap.put("bill_no", entityMap.get("bill_no").toString());
					}
					
					//新增入库主表数据
					medInCommonMapper.addMedInMain(entityMap);
					//新增入库明细数据
					medInCommonMapper.addMedInDetailBatch(detailList);
					//新增资金来源表
					entityMap.put("source_money", money_sum);
					medInCommonMapper.insertMedInResource(entityMap);
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						medStorageInMapper.insertOrderRela(orderRelaList);
					}
				}else{
					return "{\"error\":\"请选择药品\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStorageIn\"}";
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
	 * 批量添加常备药品入库<BR> 
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
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedStorageIn\"}";
		}
		
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	}
	
		/**
	 * @Description 
	 * 更新常备药品入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		String orderNewIds = "";
		try {
			if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
				return "{\"error\":\"更新失败,单据状态不为未审核状态！.\",\"state\":\"false\"}";
			}
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("08005").toString());
			
			//转日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(String.valueOf(entityMap.get("in_date")), "yyyy-MM-dd"));
			}
			
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMM" );
			//判断期初入库单的日期不能大于仓库的启用日期
			  Map<String, Object> info = medStoreModMapper.existsStoreMod(entityMap) ; 
			 int year_month =  Integer.parseInt(info.get("YEAR_MONTH").toString()) ; 
			 int in_date1  = Integer.parseInt(sdf.format(entityMap.get("in_date")).trim().toString()) ;
			 if(in_date1 < year_month){
		 		return "{\"error\":\"添加失败，制单日期必须大于仓库启用日期！\",\"state\":\"false\"}";
			 }

			
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
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
				//List<Map<String,Object>> detailUpdateList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> detailAddList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
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
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//药品变更号
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						//detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						//由于有时会出现（单价 * 数量 != 金额）的情况，所以不直接取前台的金额放到后台计算
						detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(jsonObj.get("amount").toString())*Double.valueOf(jsonObj.get("price").toString()), money_para)));//金额
						money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						
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
						
						//明细表ID
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("in_detail_id")))){
							//生成个体码--如果系统参数08010高值药品自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
							if((!ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar"))) || "0".equals(String.valueOf(jsonObj.get("is_per_bar")))) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("08010"))))){
								if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
									detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
								}else{
									detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
								}
								detailMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
								//该条明细数据添加到List中
								detailAddList.add(detailMap);
							}else{
								//根据一码一物规则自动拆分数量并生成个体码
								for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
									detailMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
									Map<String, Object> barMap = new HashMap<String, Object>();
									barMap.putAll(detailMap);
									bar_code = medCommonService.getNextBar_code(bar_code);
									if(i > 1){
										barMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
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
					}
				}
				if(detailAddList.size() > 0){
					//更新个体码
					if(!init_bar_code.equals(bar_code)){
						barCodeMap.put("max_no", bar_code);
						medNoOtherMapper.updateMedNoOther(barCodeMap);
					}
					//添加发票号和发票时间
					if (entityMap.get("bill_date") != null ) {
						if(!entityMap.get("bill_date").toString().equals("")){
							entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
						}
					}
					
					if (entityMap.get("bill_no") != null) {
						
						entityMap.put("bill_no", entityMap.get("bill_no").toString());
					}
					
					//修改入库主表数据
					medInCommonMapper.updateMedInMain(entityMap);
					/* 修改明细为先删除所有再插入
					Map<String,Object> deleteMap = new HashMap<String,Object>();
					deleteMap.put("group_id", entityMap.get("group_id"));
					deleteMap.put("hos_id", entityMap.get("hos_id"));
					deleteMap.put("copy_code", entityMap.get("copy_code"));
					deleteMap.put("in_id", entityMap.get("in_id"));//主表ID
					//删除页面上删掉的明细记录
					if(detail_ids.length() > 0){
						deleteMap.put("detail_ids", detail_ids.substring(0,detail_ids.length()-1).toString());//明细IDS
						medInCommonMapper.deleteMedInDetailForUpdate(deleteMap);
					}else{
						medInCommonMapper.deleteMedInDetail(deleteMap);
					}
					//添加新明细记录
					if(detailAddList.size() > 0){
						medInCommonMapper.addMedInDetailBatch(detailAddList);
					}
					//修改明细数据
					if(detailUpdateList.size() > 0){
						medInCommonMapper.updateMedInDetail(detailUpdateList);
					}
					*/
					medInCommonMapper.deleteMedInDetail(entityMap);
					medInCommonMapper.addMedInDetailBatch(detailAddList);
					//修改资金来源表
					entityMap.put("source_money", money_sum);
					if(Float.parseFloat(String.valueOf(entityMap.get("old_money_sum") == null ? "0" : entityMap.get("old_money_sum"))) != Float.parseFloat(String.valueOf(entityMap.get("source_money")))){
						medInCommonMapper.updateMedInResource(entityMap);
					}
					
					//获取删除的order_id
					if(!"".equals(orderNewIds) && orderNewIds!=null){
						entityMap.put("orderNewIds", orderNewIds.substring(0, orderNewIds.length()-1));
						String oldOrderId = medStorageInMapper.queryDeleteOldIds(entityMap);
						if(oldOrderId!=null && !"".equals(oldOrderId)){
							entityMap.put("oldOrderId", oldOrderId);
						}
					}
					
					//重新建立入库单订单关系表
					medStorageInMapper.deleteOrderRela(entityMap);
					
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						medStorageInMapper.insertOrderRela(orderRelaList);
						/*//修改完后查询是否改变，改变后要修改状态
						entityMap.put("state", 5);
						String orderIds = medStorageInMapper.queryOrderIdById(entityMap);
						if( orderIds!=null && !"".equals(orderIds)){
							//更新状态  state = 2
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("group_id",entityMap.get("group_id"));
							map.put("hos_id", entityMap.get("hos_id"));
							map.put("copy_code", entityMap.get("copy_code"));
							map.put("state", "2");
							map.put("orderIds", orderIds);
							medStorageInMapper.updateOrderStates(map);		
						}*/
					}
				}else{
					return "{\"error\":\"请选择药品\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"更新失败，明细数据为空\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedStorageIn\"}";
		}		
		
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
	
	
	/**
	 * @Description 
	 * 批量更新常备药品入库，无此业务<BR> 
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
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMedStorageIn\"}";
		}	

		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 删除常备药品入库<BR> 
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
			//删除资金来源表
			medInCommonMapper.deleteMedInResource(entityMap);
			//删除订单对应关系表
			medStorageInMapper.deleteOrderRela(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedStorageIn\"}";
		}	

		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
  }
    
	/**
	 * @Description 
	 * 批量删除常备药品入库<BR> 
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
			//批量删除资金来源表
			medInCommonMapper.deleteMedInResourceBatch(entityList);
			//删除送货单关系表
			medInCommonMapper.deleteMedInAmountBatch(entityList);
			
			//更新订单状态
			String orderIds = medStorageInMapper.queryMedOrderId(entityList);
			if( orderIds!=null && !"".equals(orderIds)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("state", "2");
				map.put("orderIds", orderIds);
				medStorageInMapper.updateOrderStates(map);
			}
			
			
			//批量删除订单对应关系表
			medStorageInMapper.deleteOrderRelaBatch(entityList);
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedStorageIn\"}";
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
	public String copyMedStorageInBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
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
			List<Map<String, Object>> detailList = (List<Map<String, Object>>)medInCommonMapper.queryMedInDetailByIds(inMap);
			
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
			String bar_code = medNoOtherMapper.queryMedNoOther(barCodeMap);//获取当前个体码
			//如果不存在则插入
			if(bar_code == null || "".equals(bar_code)){
				bar_code = "000000000000";
				barCodeMap.put("max_no", bar_code);
				medNoOtherMapper.insertMedNoOther(barCodeMap);
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
			batchSnMap.put("table_name", "med_in_detail");
			batchSnMap.put("c_name", "inv_id");//查询批次所用
			batchSnMap.put("c_name1", "batch_no");//查询批次所用
			//存放相同药品批号的最大批次号
			Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
			String invBatchKey = "";
			/*用于查询批次----end-----*/
			//循环组装数据
			for(Map<String, Object> mainMap : mainList){ 
				old_id = mainMap.get("in_id").toString();//记录旧的in_id用于筛选明细
				mainMap.put("in_id", medInCommonMapper.queryMedInMainSeq());//替换旧的主表ID（自增序列）
				mainMap.put("in_date", date);//制单日期
				mainMap.put("year", dates[0]);//年份
				mainMap.put("month", dates[1]);//月份
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("maker", user_id);//制单人
				mainMap.put("in_date", date);//制单日期
				mainMap.put("state", 1);//状态（新建状态）
				mainMap.put("brief", "复制"+mainMap.get("in_no")+"入库单");
				//重新获取单据号
				mainMap.put("table_code", "MED_IN_MAIN");
				String in_no = medCommonService.getMedNextNo(mainMap);
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
						money_sum = money_sum + Double.valueOf(detailMap.get("amount_money").toString());
						detailMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());//替换旧表ID（自增序列）

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
						
						/**********************处理个体码-------begin-------*/
						//生成个体码--如果系统参数08010高值药品自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if(("1".equals(String.valueOf(detailMap.get("is_per_bar")))) || ("1".equals(String.valueOf(detailMap.get("is_highvalue"))) && "1".equals(String.valueOf(MyConfig.getSysPara("08010"))))){
							
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(detailMap);
								//System.out.println(jsonObj.get("inv_name").toString()+"生成个体码");
								//System.out.println(bar_code);
								bar_code = medCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
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
								detailList.add(barMap);
							}
						}else{
							//添加到明细表新增的list中
							insertDetailList.add(detailMap);
						}
						/**********************处理个体码-------end---------*/
					}
				}
				mainMap.put("source_money", money_sum);
				//添加到主表新增的list中
				insertMainList.add(mainMap);
			}
			
			//更新个体码
			if(!init_bar_code.equals(bar_code)){
				barCodeMap.put("max_no", bar_code);
				medNoOtherMapper.updateMedNoOther(barCodeMap);
			}
			//批量新增主表数据
			medInCommonMapper.addMedInMainBatch(insertMainList);
			//批量新增明细表数据
			medInCommonMapper.addMedInDetailBatch(insertDetailList);
			//批量处理入库资金来源
			medInCommonMapper.insertMedInResourceBatch(insertMainList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 copyMedStorageInBatch\"}";
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
	public String offsetMedStorageInBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
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
			List<Map<String, Object>> detailList = (List<Map<String, Object>>)medInCommonMapper.queryMedInDetailByIds(inMap);
			
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
				mainMap.put("in_id", medInCommonMapper.queryMedInMainSeq());//替换旧的主表ID（自增序列）
				mainMap.put("in_date", date);//制单日期
				mainMap.put("year", dates[0]);//年份
				mainMap.put("month", dates[1]);//月份
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("maker", user_id);//制单人
				mainMap.put("in_date", date);//制单日期
				mainMap.put("state", 1);//状态（新建状态）
				mainMap.put("bus_type_code", "12");//业务类型(冲销为退货)
				mainMap.put("brief", "冲销"+mainMap.get("in_no")+"入库单");
				//重新获取单据号
				mainMap.put("table_code", "MED_IN_MAIN");
				String in_no = medCommonService.getMedNextNo(mainMap);
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
						detailMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());//替换旧表ID（自增序列）
						detailMap.put("amount", -1 * Float.parseFloat(detailMap.get("amount").toString()));//冲销数量为原来的负数
						detailMap.put("amount_money", -1 * Double.parseDouble(detailMap.get("amount_money").toString()));//冲销金额为原来的负数
						money_sum = money_sum + -1 * Double.parseDouble(detailMap.get("amount_money").toString());
						detailMap.put("sell_money", -1 * Double.parseDouble(detailMap.get("sell_money").toString()));//冲销金额为原来的负数
						
						//添加到明细表新增的list中
						insertDetailList.add(detailMap);
					}
				}
				mainMap.put("source_money", money_sum);
				//添加到主表新增的list中
				insertMainList.add(mainMap);
			}
			//冲账操作由于数量为负数所以走退货的库存判断
			String invEnough = medStorageBackMapper.existsMedBackStockInvIsEnough(insertDetailList);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下药品库存药品不足!"+invEnough+"\"}";
			}
			//批量新增主表数据
			medInCommonMapper.addMedInMainBatch(insertMainList);
			//批量新增明细表数据
			medInCommonMapper.addMedInDetailBatch(insertDetailList);
			//批量新增资金来源表数据
			medInCommonMapper.insertMedInResourceBatch(insertMainList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 offsetMedStorageInBatch\"}";
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
	public String auditMedStorageIn(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//批量审核
			medStorageInMapper.auditOrUnAudit(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMedStorageIn\"}";
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
	public String unAuditMedStorageIn(Map<String, Object> entityMap) throws DataAccessException {
		
		try {	
			//批量消审
			medStorageInMapper.auditOrUnAudit(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMedStorageIn\"}";
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
	public String confirmMedStorageIn(Map<String, Object> entityMap) throws DataAccessException {
		
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
			
			//批量入库确认
			medInCommonMapper.confirmCommonIn(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 confirmMedStorageIn\"}";
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
	public String auditMedStorageInBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//批量审核
			medStorageInMapper.auditOrUnAuditBatch(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMedStorageInBatch\"}";
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
	public String unAuditMedStorageInBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//批量消审
			medStorageInMapper.auditOrUnAuditBatch(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMedStorageInBatch\"}";
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
	public String confirmMedStorageInBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		Map<String, Object> inMap = new HashMap<String, Object>();
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、药品Id
			String copy_code = "", in_ids = "", confirm_date="";
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
			inMap.put("confirm_date", confirm_date);

			//校验入库单状态
			int flag = medStorageInMapper.existsMedInStateForConfirm(inMap);
			if(flag != 0){
				return "{\"error\":\"所选单据中含已确认单据，请点击查询后重新选择！\",\"state\":\"false\"}";
			}
			
			//批量入库确认
			medInCommonMapper.confirmCommonIn(inMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 confirmMedStorageInBatch\"}";
		}	

		return inMap.get("msg") == null ? "" : inMap.get("msg").toString();
	}
	
	/**
	 * @Description 
	 * 添加常备药品入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象常备药品入库
		MedInMain medInMain = queryByCode(entityMap);

		if (medInMain != null) {

			int state = medStorageInMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medStorageInMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedStorageIn\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集常备药品入库<BR> 
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
			List<?> list = medStorageInMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medStorageInMapper.query(entityMap, rowBounds);
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
			List<Map<String,Object>> list = medStorageInMapper.queryDetails(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = medStorageInMapper.queryDetails(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象常备药品入库<BR> 
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
	 * 获取对象常备药品入库<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<?> list = medInCommonMapper.queryMedInDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 获取常备药品入库<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStorageIn
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medStorageInMapper.queryByUniqueness(entityMap);
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
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medStorageInMapper.queryOrder(entityMap));
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medStorageInMapper.queryOrder(entityMap, rowBounds));
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
		//帐套、入库单Id、药品Id
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
		List<Map<String,Object>> list = medStorageInMapper.queryOrderDetail(orderMap);
		return ChdJson.toJsonLower(list);
	}
	
	@Override
	public String queryOrderDetailNew(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medStorageInMapper.queryOrderDetailNew(entityMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medStorageInMapper.queryOrderDetailNew(entityMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
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
		
		List<?> list = medStorageInMapper.queryMatch(entityMap);
		
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
		
		List<?> list = medStorageInMapper.queryProtocol(entityMap);
		
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
	public String queryMedStorageInBeforeOrNext(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			//定义入库单ID
			String in_id = "";
			
			//上一张
			if("before".equals(entityMap.get("type").toString())){
				in_id = medInCommonMapper.queryMedInBefore(entityMap);
				if(in_id == null || "".equals(in_id) || "0".equals(in_id)){
					return "{\"msg\":\"已经是第一张单据了！\",\"state\":\"false\"}";
				}
			}else if("next".equals(entityMap.get("type").toString())){//下一张
				in_id = medInCommonMapper.queryMedInNext(entityMap);
				if(in_id == null || "".equals(in_id) || "0".equals(in_id)){
					return "{\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"操作失败 页面参数异常 请联系管理员！方法queryMedStorageInBeforeOrNext\"}";
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

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMedStorageInBeforeOrNext\"}";
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
	public MedInResource queryMedInResource(Map<String, Object> entityMap) throws DataAccessException {
		
		return medInCommonMapper.queryMedInResource(entityMap);
	}

	/**
	 * @Description 
	 * 资金来源结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public List<MedInResource> queryMedInResourceList(Map<String, Object> entityMap) throws DataAccessException {
		
		return medInCommonMapper.queryMedInResourceList(entityMap);
	}

	/**
	 * @Description 
	 * 明细数据是否已经全部维护发票信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String existsMedInDetailByInvoice(Map<String, Object> entityMap) throws DataAccessException {
		try{
			int flag = medStorageInMapper.existsMedInDetailByInvoice(entityMap);
			if(flag != 0){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedInDetailByInvoice\"}";
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
	public String updateMedStorageInInvoice(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			//更新发票号
			medStorageInMapper.updateMedStorageInInvoice(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateMedStorageInInvoice\"}";
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
	public String addMedBillByInvoice(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//发票明细表信息
			List<Map<String, Object>> detailList = JsonListMapUtil.toListMapLower(medStorageInMapper.queryMedInDetailByNotBill(entityMap));
			if(detailList.size() > 0){
				//发票主表信息
				Map<String, Object> mainMap = JsonListMapUtil.toMapLower(medStorageInMapper.queryMedInMainForBill(entityMap));
				if(mainMap.get("bill_no") == null || "".equals(mainMap.get("bill_no"))){
					return "{\"error\":\"操作失败，请维护发票号！\"}";
				}
				if(mainMap.get("bill_date") == null || "".equals(mainMap.get("bill_date"))){
					return "{\"error\":\"操作失败，请维护发票日期！\"}";
				}
				entityMap.put("bill_no",  mainMap.get("bill_no"));
				
				//根据发票号查找发票信息
				MedBillMain mbm= medBillMainMapper.queryMedBillMainByUniqueness(entityMap);
				/*
				int is_flag = medStorageInMapper.existsMedBillByInvoice(entityMap);
				if(is_flag >0){
					return "{\"error\":\"操作失败，发票已存在\"}";
				}
				*/
				//当存在发票号时，更新发票明细。不存在的新增发票单。
				if(null==mbm){
					/**获取发票ID和流水码*******begin*******/
					Long bill_id = medBillMainMapper.queryBillMainNextSeq();
					String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
					String[] dates = date.split("-");
					entityMap.put("year", dates[0]);
					entityMap.put("month", dates[1]);
					entityMap.put("day", dates[2]);
					entityMap.put("table_code", "MED_BILL_MAIN");
					entityMap.put("prefixe", "FP");
					String bill_code = medCommonService.getMedNextNo(entityMap);
					mainMap.put("bill_id", bill_id);
					mainMap.put("bill_code", bill_code);
					/**获取发票ID和流水码*******end*********/
					mainMap.put("ori_no", "0000");
					mainMap.put("stock_type_code", "");
					mainMap.put("pay_code", "");
					mainMap.put("maker", SessionManager.getUserId());
				    mainMap.put("make_date", new Date());
				    mainMap.put("checker", "");
				    mainMap.put("chk_date", null);
				    mainMap.put("is_init", "0");
				    mainMap.put("state", "1");
				    mainMap.put("note", "");
				    //发票类型：1普通2红冲
				    String bus_type_code = mainMap.get("bus_type_code").toString();
				    if("12".equals(bus_type_code)){
				    	mainMap.put("bill_type", "2");
				    }else{
				    	mainMap.put("bill_type", "1");
				    }
					
				    double money_sum = 0;
					for(Map<String, Object> map : detailList){
						map.put("bill_id", bill_id);
						map.put("bill_no", mainMap.get("bill_no").toString());
						money_sum = NumberUtil.add(money_sum, Double.parseDouble(map.get("bill_money").toString()));
					}
					
					mainMap.put("payable_money", money_sum);
					mainMap.put("bill_money", money_sum);
					
					medBillMainMapper.addMedBillMain(mainMap);
					
					medBillDetailMapper.addBatchMedBillDetail(detailList);
				}else{
					if(mbm.getState()==1){
						if(mainMap.get("sup_id").toString().equals(mbm.getSup_id().toString())){

							double money_sum = mbm.getBill_money();
							for(Map<String, Object> map : detailList){
								map.put("bill_id", mbm.getBill_id());
								map.put("bill_no", mainMap.get("bill_no").toString());
								money_sum = NumberUtil.add(money_sum, Double.parseDouble(map.get("bill_money").toString()));
							}
							//更新发票主表的发票金额
							Map<String, Object> mapBill=new HashMap<String, Object>();
							
							mapBill.put("bill_id", mbm.getBill_id());
							mapBill.put("payable_money", money_sum);
							mapBill.put("bill_money", money_sum);
							mapBill.putAll(entityMap);
							medBillMainMapper.updateMedBillMainBillMoney(mapBill);
							//添加发票明细
							medBillDetailMapper.addBatchMedBillDetail(detailList);
							
							
						}else{
							return "{\"error\":\"操作失败，该入库单的供应商与发票单上的供应商不一致，不能合并生成发票单！\"}";
						}
					}else{
						return "{\"error\":\"操作失败，已经审核的发票单不能生成单据！\"}";
					}
					
					
				}
				
				return "{\"msg\":\"操作成功！\"}";
				
				
			}else{
				return "{\"error\":\"操作失败，入库单所有药品已生成过发票\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedInDetailByInvoice\"}";
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
	public String queryMedInMainByInvoice(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) medStorageInMapper.queryMedInMainByInvoice(entityMap));
		for(Map<String, Object> tmp : list){ 
			tmp.put("init","0");
			tmp.put("flag", "0");
			tmp.put("table", "MED_IN_DETAIL");
			List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(tmp);
			
			tmp.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
		}
		
		return ChdJson.toJsonLower(list);
	}
	
	//入库模板打印（包含主从表）
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
	//新版打印  调用的方法
	@Override
	public Map<String, Object> queryMedInByPrintTemlateNewPrint(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			entityMap.put("show_history", MyConfig.getSysPara("03001")); 
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MedStorageInMapper medStorageInMapper = (MedStorageInMapper)context.getBean("medStorageInMapper");
			 
			//查询入库主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=medStorageInMapper.queryMedInPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=medStorageInMapper.queryMedInPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ 
				
				Map<String,Object> map=medStorageInMapper.queryMedInPrintTemlateByMain(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=medStorageInMapper.queryMedInPrintTemlateByDetail(entityMap);
				
			
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
	public Map<String, Object> queryMedInBarcodeByPrintTemlateNewPrint(Map<String, Object> entityMap)throws DataAccessException {
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
				MedStorageInMapper medStorageInMapper = (MedStorageInMapper)context.getBean("medStorageInMapper");
				List<Map<String,Object>> list=medStorageInMapper.queryMedInBarcodeByPrintTemlateNewPrint(entityMap);
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
				
				List<Map<String, Object>> list = medStorageInMapper.querymergePrint(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list =  medStorageInMapper.querymergePrint(entityMap, rowBounds);
				
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
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断库房是否已启用
			flag = medCommonMapper.existsMedStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成常备药品入库单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MED_IN_MAIN");
				String in_no = medCommonService.getMedNextNo(entityMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				entityMap.put("in_no", in_no);
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", medInCommonMapper.queryMedInMainSeq());
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				float money_sum = 0;//记录明细总金额

				/*用于查询个体码----begin-----*/
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
				/*用于查询个体码----end-------*/
				
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
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
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
						detailMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//药品ID
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
						
						money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额

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
						//生成个体码--如果系统参数08010高值药品自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if("0".equals(detailMap.get("is_per_bar").toString()) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("08010"))))){
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
								bar_code = medCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("in_detail_id", medInCommonMapper.queryMedInDetailSeq());
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
						medNoOtherMapper.updateMedNoOther(barCodeMap);
					}
					//新增入库主表数据
					medInCommonMapper.addMedInMain(entityMap);
					//新增入库明细数据
					medInCommonMapper.addMedInDetailBatch(detailList);
					//新增资金来源表
					entityMap.put("source_money", money_sum);
					medInCommonMapper.insertMedInResource(entityMap);
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						medStorageInMapper.insertOrderRela(orderRelaList);
						
						/*entityMap.put("state", "2");
						//entityMap.remove("in_id");
						String orderIds  = medStorageInMapper.queryOrderIds(entityMap);
						if( orderIds!=null && !"".equals(orderIds)){
							//更新状态  state = 5
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("group_id",entityMap.get("group_id"));
							map.put("hos_id", entityMap.get("hos_id"));
							map.put("copy_code", entityMap.get("copy_code"));
							map.put("state", "5");
							map.put("orderIds", orderIds);
							medStorageInMapper.updateOrderStates(map);
						}*/
					}
					
				}else{
					return "{\"error\":\"请选择药品\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStorageIn\"}";
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
	public void updateMedOrderState(Map<String, Object> entityMap) throws DataAccessException {
		//若有删除的order_id  直接更新
		if(entityMap.containsKey("oldOrderId")){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("group_id",entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("state", "2");
			map.put("orderIds", entityMap.get("oldOrderId"));
			medStorageInMapper.updateOrderStates(map);
			entityMap.remove("in_id");
		}
		
		String orderIds  = medStorageInMapper.queryOrderIds(entityMap);
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
			medStorageInMapper.updateOrderStates(map);
		}
	}
	
	@Override
	public List<Map<String,Object>> queryOrderRelaExists(Map<String, Object> entityMap) throws DataAccessException {
		return medStorageInMapper.queryOrderRelaExists(entityMap);
	}
	
	/**
	 * @Description 冲账 明细表数据<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String mergeOffsetMedInMain(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list= (List<Map<String, Object>>) medStorageInMapper.mergeOffsetMedInMain(entityMap);
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
	public Map<String, Object> queryMedInOffsetMainByCode(Map<String, Object> entityMap) throws DataAccessException {
		return JsonListMapUtil.toMapLower((Map<String, Object>) medStorageInMapper.queryMedInOffsetMainByCode(entityMap));
	}
	
	@Override
	public String queryModeStartDate(Map<String, Object> entityMap) throws DataAccessException {
		String date = "";
		date = medStorageInMapper.queryModeStartDate(entityMap);
		return  date;  
	}
	
	@Override
	public String queryAllStorageMedbySupId(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list=medCommonMapper.queryAllStorageMedbySupId(entityMap);
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 关闭药品
	 */
	@Override
	public String updateMedStorageInCloseInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//批量关闭药品
			medStorageInMapper.updateMedStorageInCloseInv(entityList);
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
	public String updateMedStorageInCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//批量关闭药品
			medStorageInMapper.updateMedStorageInCancleCloseInv(entityList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	@Override
	public Map<String, Object> queryMedInByNewCombinePrint(Map<String, Object> entityMap) throws DataAccessException {
		Map<String,Object> reMap=new HashMap<String,Object>();
		//查询入库主表
			Map<String,Object> map=medStorageInMapper.queryMedInPrintTemlateByMain(entityMap);
			//查询入库明细表
			List<Map<String,Object>> list=medStorageInMapper.queryMedInByNewCombinePrintByDetail(entityMap);
			
			reMap.put("main", map);
			
			reMap.put("detail", list); 
			
			return reMap;
	}
	@Override
	public String verifyMedClosingDate(List<Map<String, Object>> listVo) {
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
				int MedCount = medStorageInMapper.queryMedTermendStoreByYearMonth(map);
				if(MedCount!=0){
					falg = "所选单据中,单据号"+map.get("in_no")+"的确认日期小于结账日期，不能做确认入库操作";
				}
			}else{
				Map<String,Object> MedCount = medStorageInMapper.queryAccYearMonthByYearMonth(map);
				if(MedCount!=null){
					falg = "所选单据中,单据号"+map.get("in_no")+"的确认日期小于结账日期，不能做确认入库操作";
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
		List<Map<String, Object>> detailList = medInCommonMapper.queryInvInDetail(mapVo);

		return ChdJson.toJson(detailList);
	}
	
}
