package com.chd.hrp.med.serviceImpl.affi.in;

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
import com.chd.hrp.med.dao.affi.back.MedAffiBackMapper;
import com.chd.hrp.med.dao.affi.in.MedAffiInCommonMapper;
import com.chd.hrp.med.dao.base.MedAffiInMapper;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedInCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.base.MedStoreModMapper;
import com.chd.hrp.med.dao.bill.MedBillMainMapper;
import com.chd.hrp.med.dao.initial.MedInitChargeMapper;
import com.chd.hrp.med.service.affi.in.MedAffiInCommonService;
import com.chd.hrp.med.service.base.MedCommonService;
import com.github.pagehelper.PageInfo;

@Service("medAffiInCommonService")
public class MedAffiInCommonServiceImpl implements MedAffiInCommonService{
	private static Logger logger = Logger.getLogger(MedAffiInCommonServiceImpl.class);
	
	//引入dao
	@Resource(name = "medAffiInMapper")
	private final MedAffiInMapper medAffiInMapper = null;
	@Resource(name = "medAffiInCommonMapper")
	private final MedAffiInCommonMapper medAffiInCommonMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	@Resource(name = "medInCommonMapper")
	private final MedInCommonMapper medInCommonMapper = null;
	@Resource(name = "medInitChargeMapper")
	private final MedInitChargeMapper medInitChargeMapper = null;
	@Resource(name = "medAffiBackMapper")
	private final MedAffiBackMapper medAffiBackMapper = null;
	
	@Resource(name = "medBillMainMapper")
	private final MedBillMainMapper medBillMainMapper = null;
	
	@Resource(name = "medStoreModMapper")
	private final MedStoreModMapper medStoreModMapper = null;
	
	
	/**
	 * @Description 
	 * 查询结果集 代销入库<BR> 
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
			List<?> list = medAffiInCommonMapper.query(entityMap);			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medAffiInCommonMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 添加 代销入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap) throws DataAccessException{
		try {
			 
			if(entityMap.get("in_date") == null || "".equals(entityMap.get("in_date"))){
				return "{\"error\":\"入库日期不能为空\",\"state\":\"false\"}";
			}
			 
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			 
			//判断期初入库单的日期不能大于仓库的启用日期
			  Map<String, Object> info = medStoreModMapper.existsStoreMod(entityMap) ; 
			 int year_month =  Integer.parseInt(info.get("YEAR_MONTH").toString()) ;
			 int in_date1  = Integer.parseInt(entityMap.get("in_date").toString().trim().substring(0,4)+""+entityMap.get("in_date").toString().trim().substring(5,7)) ;
			 if(in_date1 < year_month){
		 		return "{\"error\":\"添加失败，入库日期必须大于仓库启用日期！\",\"state\":\"false\"}";
			 }
			
			
			 
			 
			//转换日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			 
			 
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = medCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断当前期间是否已结账，如结账，则不能添加入库单
			flag = medCommonMapper.existsMedYearMonthIsAccount(entityMap);
			if(flag != 0){
				return "{\"error\":\"当月已结账不能保存！\",\"state\":\"false\"}";
			}
			
			//自动生成代销入库单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MED_AFFI_IN");
				entityMap.put("in_no", medCommonService.getMedNextNo(entityMap));
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", medAffiInMapper.queryAffiInMainSeq());
			
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				//用于查询个体码
				Map<String,Object> barCodeMap = new HashMap<String,Object>();
				barCodeMap.put("group_id", entityMap.get("group_id"));
				barCodeMap.put("hos_id", entityMap.get("hos_id"));
				barCodeMap.put("type_code", 1);
				String bar_code = medAffiInMapper.queryMedPerBar(barCodeMap);//获取当前个体码
				//如果不存在则插入
				if(bar_code == null || "".equals(bar_code)){
					bar_code = "000000000000";
					barCodeMap.put("max_no", bar_code);
					medAffiInMapper.insertMedPerBar(barCodeMap);
				}
				String init_bar_code = bar_code;
				
				///*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "med_affi_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同药品批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				///*用于查询批次----end-----*/
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						
						 //判断该供应商下该药品是否有库存，如果有则该药品不能添加别的供应商，否则可以
						 
						Map<String,Object> detailSupMap = new HashMap<String,Object>();
						
						detailSupMap.put("group_id", entityMap.get("group_id"));
						detailSupMap.put("hos_id", entityMap.get("hos_id"));
						detailSupMap.put("copy_code", entityMap.get("copy_code"));
						detailSupMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailSupMap.put("sup_id",  entityMap.get("sup_id"));//供应商ID
						
						//查询 该药品该供应商下是否有数据存在  有的话进行添加，没有则进行判断  该药品在别的供应商下是否有库存
						int supCount =  medAffiInCommonMapper.queryMedAffiInCommonInBySup(detailSupMap); 
						if(supCount == 0 ){ 
							//查询该药品是否有出库数量  没有的话直接查询该药品在入库时的供应商 是否和该供应商匹配
							int outCount = medAffiInCommonMapper.queryMedAffiInCommonOutAmount(detailSupMap);
							
							if(outCount!= 0 ){
								//有出库数量时， 计算该药品的入库数量-出库数量是否不等于0  如果是则可进行保存  该药品该供应商
								 Map<String,Object>  inAmount = medAffiInCommonMapper.queryMedAffiInCommonInAmount(detailSupMap);
									
									if(inAmount != null){
										
										if(Integer.parseInt(inAmount.get("amount").toString()) != 0){
											
											return "{\"error\":\"该药品已在别的供应商下存在，不能入库\",\"state\":\"false\"}";
											
										}
										
									} 
									
							}else {
								
								 Map<String,Object>  inAmount = medAffiInCommonMapper.queryMedAffiInCommonInAmountBySup(detailSupMap); 
								 
								 if(inAmount  != null ){
									 if(!inAmount.get("sup_id").equals(entityMap.get("sup_id"))){
										  
										 return "{\"error\":\"该药品已在别的供应商下存在，不能入库\",\"state\":\"false\"}";
									 }
								 }
								 
							}
							 
						} 
						
						Map<String,Object> detailMap = new HashMap<String,Object>();
						//取出明细表ID（自增序列）
						detailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//药品ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  jsonObj.get("batch_no"));//批号
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
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_no")))){
							detailMap.put("disinfect_no",  jsonObj.get("disinfect_no"));//灭菌批号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码（这里用个体码）
						}else{
							detailMap.put("sn", "-");
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id", "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("serial_no")))){
							detailMap.put("serial_no", jsonObj.get("serial_no"));//序列号
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
									barMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
								}
								//拆分数量和金额
								barMap.put("amount",  1);//数量
								if(detailMap.get("num") != null){
									barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
								}
								if(detailMap.containsKey("num")){
									barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
								}else{
									barMap.put("pack_price",  "0");//包装件数
								}
								
								barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("sell_money",  Float.parseFloat(detailMap.get("sell_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("sale_money",  Float.parseFloat(detailMap.get("sale_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("allot_money",  Float.parseFloat(detailMap.get("allot_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								
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
							orderRelaMap.put("in_detail_id", detailMap.get("detail_id"));
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("order_rela").toString())){
								orderRelaMap.put("order_id", m.get("order_id"));
								orderRelaMap.put("order_detail_id", m.get("order_detail_id"));
								orderRelaMap.put("in_amount", m.get("in_amount"));
								orderRelaMap.put("order_amount", m.get("order_amount"));
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
						medAffiInMapper.updateMedPerBar(barCodeMap);
					}
					//新增入库主表数据
					medAffiInMapper.addMedAffiInMain(entityMap);
					//新增入库明细数据
					medAffiInMapper.addMedAffiInDetail(detailList);
					
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						medAffiInMapper.insertOrderRela(orderRelaList);
					}
				}else{
					return "{\"error\":\"请选择药品\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
			}
			
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedAffiInCommon\"}";
		}
		return "{\"state\":\"true\",\"update_para\":\""+
		entityMap.get("group_id").toString()+","+
		entityMap.get("hos_id").toString()+","+
		entityMap.get("copy_code").toString()+","+
		entityMap.get("in_id").toString()+","
		+"\"}";
	}
	
	/**
	 * @Description 
	 * 批量添加 代销入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedAffiInCommon\"}";
		}
	}
	/**
	 * @Description 
	 * 修改 代销入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {
			String orderNewIds = "";
			
			
			
			 
			//状态不是未审核的单据不能修改
			if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
				return "{\"error\":\"更新失败,单据状态不为未审核状态！.\",\"state\":\"false\"}";
			}
			
			//判断期初入库单的日期不能大于仓库的启用日期
			  Map<String, Object> info = medStoreModMapper.existsStoreMod(entityMap) ; 
			 int year_month =  Integer.parseInt(info.get("YEAR_MONTH").toString()) ;
			 int in_date1  = Integer.parseInt(entityMap.get("in_date").toString().trim().substring(0,4)+""+entityMap.get("in_date").toString().trim().substring(5,7)) ;
			 if(in_date1 < year_month){
		 		return "{\"error\":\"添加失败，入库日期必须大于仓库启用日期！\",\"state\":\"false\"}";
			 }
			 
			//转换日期格式
			//if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				//entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			//}
			
			//取出主表ID（自增序列）
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				//用于查询个体码
				Map<String,Object> barCodeMap = new HashMap<String,Object>();
				barCodeMap.put("group_id", entityMap.get("group_id"));
				barCodeMap.put("hos_id", entityMap.get("hos_id"));
				barCodeMap.put("type_code", 1);
				String bar_code = medAffiInMapper.queryMedPerBar(barCodeMap);//获取当前个体码
				//如果不存在则插入
				if(bar_code == null || "".equals(bar_code)){
					bar_code = "000000000000";
					barCodeMap.put("max_no", bar_code);
					medAffiInMapper.insertMedPerBar(barCodeMap);
				}
				String init_bar_code = bar_code;
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "med_affi_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同药品批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				///*用于查询批次----end-----*/
				//修改明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				//List<Map<String,Object>> detailUpdateList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> detailAddList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				StringBuffer detail_ids = new StringBuffer();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						
						 //判断该供应商下该药品是否有库存，如果有则该药品不能添加别的供应商，否则可以
						 
						Map<String,Object> detailSupMap = new HashMap<String,Object>();
						
						detailSupMap.put("group_id", entityMap.get("group_id"));
						detailSupMap.put("hos_id", entityMap.get("hos_id"));
						detailSupMap.put("copy_code", entityMap.get("copy_code"));
						detailSupMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailSupMap.put("sup_id",  entityMap.get("sup_id"));//供应商ID
						
						int supCount =  medAffiInCommonMapper.queryMedAffiInCommonInBySup(detailSupMap);
						
						if(supCount == 0 ){
							
							int outCount = medAffiInCommonMapper.queryMedAffiInCommonOutAmount(detailSupMap);
							
							if(outCount!= 0 ){
								
								 Map<String,Object>  inAmount = medAffiInCommonMapper.queryMedAffiInCommonInAmount(detailSupMap);
									
									if(inAmount != null){
										if(Integer.parseInt(inAmount.get("amount").toString()) != 0){
											
											return "{\"error\":\"该药品已在别的供应商下存在，不能入库\",\"state\":\"false\"}";
											
										}
										
									} 
									
							}else {
								
								 Map<String,Object>  inAmount = medAffiInCommonMapper.queryMedAffiInCommonInAmountBySup(detailSupMap);
								 
								 if(inAmount != null ){
									 
									 if(!inAmount.get("sup_id").equals(entityMap.get("sup_id"))){
										  
										 return "{\"error\":\"该药品已在别的供应商下存在，不能入库\",\"state\":\"false\"}";
									 }
								 }
								 
							}
							 
						} 
						
						Map<String,Object> detailMap=new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表no
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//药品ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//药品变更号
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no", "-");//默认批号
						}else{
							detailMap.put("batch_no",  jsonObj.get("batch_no").toString());//批号
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
							detailMap.put("inva_date", DateUtil.stringToDate( jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate( jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_no")))){
							detailMap.put("disinfect_no",  jsonObj.get("disinfect_no"));//灭菌批号
						}
						if(jsonObj.containsKey("sn")){
							if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
								detailMap.put("sn",  jsonObj.get("sn"));//条形码
							}else{
								detailMap.put("sn", "-");
							}
						}else{
							detailMap.put("sn", "-");
						}
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id", "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("serial_no")))){
							detailMap.put("serial_no", jsonObj.get("serial_no"));//序列号
						}
						
						//System.out.println("*******:"+ChdJson.validateJSON(String.valueOf(jsonObj.get("detail_id"))));
						//明细表ID
						if(!ChdJson.validateJSON(String.valueOf(jsonObj.get("detail_id")))){
							//生成个体码--如果系统参数08010高值药品自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
							if((!ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar"))) || "0".equals(String.valueOf(jsonObj.get("is_per_bar")))) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("08010"))))){		
								if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
									detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
								}else{
									detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
								}
								//System.out.println("************** 1:");
								detailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
								//该条明细数据添加到List中
								detailAddList.add(detailMap);
							}else{
								//根据一码一物规则自动拆分数量并生成个体码
								for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
									detailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
									Map<String, Object> barMap = new HashMap<String, Object>();
									barMap.putAll(detailMap);
									bar_code = medCommonService.getNextBar_code(bar_code);
									if(i > 1){
										barMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
									}
									//拆分数量和金额
									barMap.put("amount",  1);//数量
									if(detailMap.get("num") != null){
										barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
									}
									if(detailMap.get("pack_price") != null){
										if(detailMap.containsKey("num")){
											barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
										}else{
											barMap.put("pack_price",  "0");//包装件数
										}
										
									}
									barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
									barMap.put("sell_money",  Float.parseFloat(detailMap.get("sell_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
									barMap.put("sale_money",  Float.parseFloat(detailMap.get("sale_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
									barMap.put("allot_money",  Float.parseFloat(detailMap.get("allot_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
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
							detailMap.put("detail_id", jsonObj.get("detail_id"));
							detailAddList.add(detailMap);
						
						}
						/*订单关系-------begin--*/
						if(jsonObj.get("order_rela") != null && !"".equals(jsonObj.get("order_rela").toString())){
							for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.getString("order_rela"))){
								Map<String,Object> orderRelaMap = new HashMap<String,Object>();
								orderRelaMap.put("group_id", entityMap.get("group_id"));
								orderRelaMap.put("hos_id", entityMap.get("hos_id"));
								orderRelaMap.put("copy_code", entityMap.get("copy_code"));
								orderRelaMap.put("in_id", entityMap.get("in_id"));
								orderRelaMap.put("in_detail_id", detailMap.get("detail_id"));
								
								if(!orderNewIds.contains(m.get("order_id").toString())){
									orderNewIds=orderNewIds+m.get("order_id").toString()+",";
								}
								
								orderRelaMap.put("order_id", m.get("order_id"));
								orderRelaMap.put("order_detail_id", m.get("order_detail_id"));
								orderRelaMap.put("in_amount", m.get("in_amount"));
								orderRelaMap.put("order_amount", m.get("order_amount"));
								orderRelaList.add(orderRelaMap);
							}
						}
						/*订单关系-------end--*/
					}
				}
				//System.out.println("************ size:"+detailAddList.size());
				if(detailAddList.size() > 0){
					//更新个体码
					if(!init_bar_code.equals(bar_code)){
						barCodeMap.put("max_no", bar_code);
						medAffiInMapper.updateMedPerBar(barCodeMap);
					}
	
					//修改入库主表数据
					medAffiInMapper.updateMedAffiInMain(entityMap);//更新主表信息
					medAffiInMapper.deleteBatchDetail(entityMap);//批量删除明细表信息
					medAffiInMapper.addMedAffiInDetail(detailAddList);//批量插入明细表信息
					
					//获取删除的order_id
					if(!"".equals(orderNewIds) && orderNewIds!=null){
						entityMap.put("orderNewIds", orderNewIds.substring(0, orderNewIds.length()-1));
						String oldOrderId = medAffiInCommonMapper.queryAffiDeleteOldIds(entityMap);
						if(oldOrderId!=null && !"".equals(oldOrderId)){
							entityMap.put("oldOrderId", oldOrderId);
						}
					}
					
					//重新建立入库单订单关系表
					medAffiInMapper.deleteOrderRela(entityMap);
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						medAffiInMapper.insertOrderRela(orderRelaList);
					}
					
				}else{
					return "{\"error\":\"请选择药品\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"更新失败，明细数据为空\",\"state\":\"false\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedAffiInCommon\"}";
		}	
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量修改  代销入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMedAffiInCommon\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 删除   代销药品  入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			//1、状态不为新建不能删除，从页面判断
			//2、根据参数08011判断是否倒序删除，如果是，则只能删除单据号最大的入库单。否则，医院人为控制单据连续性
			//3、判断当前登录用户是否为制单人，非制单人不允许删除,页面判断
			//4、删除订单关系表、删除明细、删除主表
			
			//删除订单关系表
			medAffiInMapper.deleteOrderRela(entityMap);
			//删除明细表
			medAffiInMapper.deleteMedAffiInDetail(entityMap);
			//删除主表
			medAffiInMapper.deleteMedAffiInMain(entityMap);			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedAffiInCommon\"}";
		}	
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量删除   代销药品  入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {	
			//1、状态不为新建不能删除，从页面判断
			//2、根据参数08011判断是否倒序删除，如果是，则只能删除单据号最大的入库单。否则，医院人为控制单据连续性----待定
			//3、判断当前登录用户是否为制单人，非制单人不允许删除,页面判断
			//4、删除订单关系表、删除明细、删除主表	
			//4-1 、删除资金来源表
			
			//更新状态  state=2
			String orderIds = medAffiInCommonMapper.queryMedAffiOrderId(entityList);
			if( orderIds!=null && !"".equals(orderIds)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("state", "2");
				map.put("orderIds", orderIds);
				medAffiInCommonMapper.updateAffiOrderStates(map);
			}
			
			medAffiInMapper.deleteBatchOrderRela(entityList);
			//4-2 、删除明细表
			medAffiInMapper.deleteBatchMedAffiInDetail(entityList);
			//4-3 、删除主表
			medAffiInMapper.deleteBatchMedAffiInMain(entityList);
			
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedAffiInCommon\"}";
		}
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 审核   代销药品  入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String auditMedAffiInCommon(List<Map<String,Object>> entityList)throws DataAccessException{
		try {	
			//1、状态不为新建不能审核，从页面判断
			//2、修改状态 state = 2 ,添加审核人，审核日期
			medAffiInCommonMapper.auditMedAffiInCommon(entityList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"审核失败 数据库异常 请联系管理员! 方法 auditMedAffiInCommon\"}";
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 取消审核   代销药品  入库<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String unAuditMedAffiInCommon(List<Map<String,Object>> entityList)throws DataAccessException{
		try {	
			//1、状态不为新建不能审核，从页面判断
			//2、修改状态 state =1 ,审核人，审核日期置为空
			medAffiInCommonMapper.unAuditMedAffiInCommon(entityList);				
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"销审失败 数据库异常 请联系管理员! 方法 unAuditMedAffiInCommon\"}";
		}
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 复制  入库单据
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String copyMedAffiInCommon(List<Map<String,Object>> entityList)throws DataAccessException{
		try {	
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、药品Id
			String copy_code = "", in_ids = "";
			Map<String, Object> inMap = new HashMap<String, Object>();
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
				in_ids = in_ids + m.get("in_id").toString() + ",";
			}
			//组装数据
			inMap.put("group_id", group_id);
			inMap.put("hos_id", hos_id);
			inMap.put("copy_code", copy_code);
			inMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			
			//获取选中的主表、明细表数据
			List<Map<String, Object>> mainList = (List<Map<String, Object>>)medAffiInMapper.queryMedInMainByIds(inMap);
			List<Map<String, Object>> detailList = (List<Map<String, Object>>)medAffiInMapper.queryMedInDetailByIds(inMap);
			
			//存放组装数据的List
			List<Map<String, Object>> insertMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> insertDetailList = new ArrayList<Map<String,Object>>();
			
			
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
			String bar_code = medAffiInMapper.queryMedPerBar(barCodeMap);//获取当前个体码
			//如果不存在则插入
			if(bar_code == null || "".equals(bar_code)){
				bar_code = "000000000000";
				barCodeMap.put("max_no", bar_code);
				medAffiInMapper.insertMedPerBar(barCodeMap);
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
			batchSnMap.put("table_name", "med_affi_in_detail");
			batchSnMap.put("c_name", "inv_id");//查询批次所用
			batchSnMap.put("c_name1", "batch_no");//查询批次所用
			//存放相同药品批号的最大批次号
			Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
			String invBatchKey = "";
			/*用于查询批次----end-----*/
			//循环组装数据
			for(Map<String, Object> mainMap : mainList){ 
				old_id = mainMap.get("in_id").toString();//记录旧的in_id用于筛选明细
				mainMap.put("in_id", medAffiInMapper.queryAffiInMainSeq());//替换旧的主表ID（自增序列）
				mainMap.put("in_date", date);//编制日期
				mainMap.put("year", dates[0]);//年份
				mainMap.put("month", dates[1]);//月份
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("maker", user_id);//制单人
				mainMap.put("make_date", date);//制单日期
				mainMap.put("state", 1);//状态（新建状态）
				mainMap.put("brief", "复制"+mainMap.get("in_no")+"入库单");
				mainMap.put("table_code", "MED_AFFI_IN");
				mainMap.put("in_no", medCommonService.getMedNextNo(mainMap));//重新获取单据号
				
				/*处理明细-------begin-------*/
				for(Map<String, Object> detailMap : detailList){
					/*判断是否为该主表的明细*/
					if(old_id.equals(detailMap.get("in_id").toString())){
						detailMap.put("in_id", mainMap.get("in_id"));//替换旧的主表ID
						detailMap.put("in_no", mainMap.get("in_no"));
						detailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());//替换旧表ID（自增序列）

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
						/*处理个体码-------begin-------*/
						//生成个体码--如果系统参数08010高值药品自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if((!ChdJson.validateJSON(String.valueOf(detailMap.get("is_per_bar"))) || "0".equals(String.valueOf(detailMap.get("is_per_bar")))) && ("0".equals(String.valueOf(detailMap.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("08010"))))){		
							if(ChdJson.validateJSON(String.valueOf(detailMap.get("bar_code")))){
								detailMap.put("bar_code",  detailMap.get("bar_code").toString());//个体码
							}else{
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}
							//System.out.println("************** 1:");
							detailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
							//该条明细数据添加到List中
							insertDetailList.add(detailMap);
						}else{
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){
								detailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(detailMap);
								bar_code = medCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
								}
								//拆分数量和金额
								barMap.put("amount",  1);//数量
								if(detailMap.get("num") != null){
									barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
								}
								if(detailMap.get("pack_price") != null){
									if(detailMap.containsKey("num")){
										barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
									}else{
										barMap.put("pack_price",  "0");//包装件数
									}
									
								}
								barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("bar_code",  bar_code);//个体码
								//该条明细数据添加到List中
								insertDetailList.add(barMap);
							}
						}
						/*处理个体码-------end---------*/
					}
				}
				insertMainList.add(mainMap);
			}
	
			//更新个体码
			if(!init_bar_code.equals(bar_code)){
				barCodeMap.put("max_no", bar_code);
				medAffiInMapper.updateMedPerBar(barCodeMap);
			}
			//批量新增主表数据
			medAffiInMapper.addMedAffiInMainBatch(insertMainList);
			//批量新增明细表数据
			medAffiInMapper.addMedAffiInDetail(insertDetailList);
			
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 copyMedStorageInBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * 冲销
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String offsetMedAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException {
		try{
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、药品Id
			String copy_code = "", in_ids = "";
			Map<String, Object> inMap = new HashMap<String, Object>();
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
				in_ids = in_ids + m.get("in_id").toString() + ",";
			}
			//组装数据
			inMap.put("group_id", group_id);
			inMap.put("hos_id", hos_id);
			inMap.put("copy_code", copy_code);
			inMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			
			//获取选中的主表、明细表数据
			List<Map<String, Object>> mainList = (List<Map<String, Object>>)medAffiInMapper.queryMedInMainByIds(inMap);
			List<Map<String, Object>> detailList = (List<Map<String, Object>>)medAffiInMapper.queryMedInDetailByIds(inMap);
			
			//存放组装数据的List
			List<Map<String, Object>> insertMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> insertDetailList = new ArrayList<Map<String,Object>>();
			
			
			/*冲销操作中的固定值-----begin--------*/
			Date date = new Date();
			String[] dates = DateUtil.dateToString(date, "yyyy-MM-dd").split("-");
			String user_id = SessionManager.getUserId();
			String old_id;
			/*冲销操作中的固定值-----end--------*/
			
			//循环组装数据
			for(Map<String, Object> mainMap : mainList){ 
				old_id = mainMap.get("in_id").toString();//记录旧的in_id用于筛选明细
				mainMap.put("in_id", medAffiInMapper.queryAffiInMainSeq());//替换旧的主表ID（自增序列）
				mainMap.put("in_date", date);//编制日期
				mainMap.put("year", dates[0]);//年份
				mainMap.put("month", dates[1]);//月份
				mainMap.put("day", dates[2]);  //用于生成单号
				mainMap.put("maker", user_id);//制单人
				mainMap.put("make_date", date);//制单日期
				mainMap.put("state", 1);//状态（新建状态）
				mainMap.put("bus_type_code", "29");//业务类型(冲销为退货)
				mainMap.put("brief", "冲销"+mainMap.get("in_no")+"入库单");
				mainMap.put("table_code", "MED_AFFI_IN");
				mainMap.put("in_no", medCommonService.getMedNextNo(mainMap));//重新获取单据号
				
				//添加到主表新增的list中
				insertMainList.add(mainMap);
				
				/*处理明细-------begin-------*/
				for(Map<String, Object> detailMap : detailList){
					/*判断是否为该主表的明细*/
					if(old_id.equals(detailMap.get("in_id").toString())){
						detailMap.put("in_id", mainMap.get("in_id"));//替换旧的主表ID
						detailMap.put("in_no", mainMap.get("in_no"));
						detailMap.put("store_id", mainMap.get("store_id"));  //用于库存判断
						detailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());//替换旧表ID（自增序列）
						detailMap.put("amount", -1 * Float.parseFloat(detailMap.get("amount").toString()));//冲销数量为原来的负数
						detailMap.put("amount_money", -1 * Double.parseDouble(detailMap.get("amount_money").toString()));//冲销金额为原来的负数
						if(detailMap.containsKey("sell_money")){
							detailMap.put("sell_money", -1 * Double.parseDouble(detailMap.get("sell_money").toString()));//冲销金额为原来的负数
						}
						//添加到明细表新增的list中
						insertDetailList.add(detailMap);
					}
				}
			}
			
			//冲账操作由于数量为负数所以走退货的库存判断
			String invEnough = medAffiBackMapper.existsMedAffiBackInvIsEnough(detailList);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下药品库存物资不足!"+invEnough+"\"}";
			}
			
			//批量新增主表数据
			medAffiInMapper.addMedAffiInMainBatch(insertMainList);
			//批量新增明细表数据
			medAffiInMapper.addMedAffiInDetail(insertDetailList);
			
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 offsetMedStorageInBatch\"}";
		}
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	
	
	
	/**
	 * 配套导入 查询 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAffiInMatchDetail(Map<String, Object> entityMap) throws DataAccessException {
		return ChdJson.toJson(medAffiInCommonMapper.queryMedAffiInMatchDetail(entityMap));
		
	}
	
	/**
	 * 订单导入 查询
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryMedOrder(Map<String, Object> entityMap) throws DataAccessException {
		return ChdJson.toJson(medAffiInCommonMapper.queryMedOrder(entityMap));
	}
	
	/**
	 * 订单导入  查看明细
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedOrderDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<?> list = medAffiInCommonMapper.queryMedOrderDetail(entityMap);
		return ChdJson.toJson(list);
	}
	/**
	 * 订单导入  查看导入订单的明细
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryOrderDetailImp(List<Map<String, Object>> entityList) throws DataAccessException {
		//集团、单位变量
		Integer group_id = null, hos_id = null;
		//帐套、入库单Id、药品Id
		String copy_code = "", order_ids = "";
		Map<String, Object> orderMap = new HashMap<String, Object>();
		//取得所有in_id组装map
		for(Map m : entityList){
			if(group_id == null){
				group_id = Integer.parseInt(m.get("group_id").toString());
			}
			if(hos_id == null){
				hos_id = Integer.parseInt(m.get("hos_id").toString());
			}
			if("".equals(copy_code)){
				copy_code = m.get("copy_code").toString();
			}
			order_ids = order_ids + m.get("order_id").toString() + ",";
		}
		//组装数据
		orderMap.put("group_id", group_id);
		orderMap.put("hos_id", hos_id);
		orderMap.put("copy_code", copy_code);
		orderMap.put("order_ids", order_ids.substring(0, order_ids.length()-1));
				
		List<?> list = medAffiInCommonMapper.queryOrderDetailImp(orderMap);
		//System.out.println("*********** :"+ChdJson.toJson(list));			
		return ChdJson.toJson(list);
	}
	
	/**
	 * 协议导入查询
	 */
	@Override
	public String queryMedAffiInPro(Map<String, Object> entityMap) throws DataAccessException {
		return ChdJson.toJson(medAffiInCommonMapper.queryMedAffiInPro(entityMap));
	}

	
	/**
	 * 入库确认
	 * @param 
	 * @return
	 */
	@Override
	public String confirmMedAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			//集团、单位变量
			Integer group_id = null, hos_id = null;
			//帐套、入库单Id、药品Id
			String copy_code = "", in_ids = "";
			Map<String, Object> inMap = new HashMap<String, Object>();
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
				in_ids = in_ids + m.get("in_id").toString() + ",";
			}
			//组装数据
			inMap.put("group_id", group_id);
			inMap.put("hos_id", hos_id);
			inMap.put("copy_code", copy_code);
			inMap.put("user_id", SessionManager.getUserId());
			inMap.put("in_ids", in_ids.substring(0, in_ids.length()-1));
			String confirm_date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			inMap.put("confirm_date", confirm_date);
			
			//判断当前期间是否已结账，如结账，则不能添加入库单
			inMap.put("year", confirm_date.substring(0, 4));
			inMap.put("month", confirm_date.substring(5, 7));
			int flag = medCommonMapper.existsMedYearMonthIsAccount(inMap);
			if(flag != 0){
				return "{\"error\":\"当月已结账不能确认！\",\"state\":\"false\"}";
			}
			//批量入库确认
			medAffiInCommonMapper.confirmAffiIn(inMap);			
			
			return inMap.get("msg") == null ? "" : inMap.get("msg").toString();	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 confirmMedCommonInBatch\"}";
		}
		
		
	}
	
	/**
	 * @Description 获取下一张或上一张入库单ID
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String queryMedAffiInBeforeOrNext(Map<String,Object> entityMap) throws DataAccessException{
		try {	
			//定义入库单ID
			String in_id = "";
			
			//下一张
			if("before".equals(entityMap.get("type").toString())){
				in_id = medAffiInMapper.queryMedAffiInBefore(entityMap);
				if("".equals(in_id)){
					return "{\"msg\":\"已经是第一张单据了！\",\"state\":\"false\"}";
				}
			}else if("next".equals(entityMap.get("type").toString())){
				in_id = medAffiInMapper.queryMedAffiInNext(entityMap);
				if(in_id == null || "".equals(in_id)){
					return "{\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"操作失败 页面参数异常 请联系管理员！方法queryMedAffiInBeforeOrNext\"}";
			}
			
			return "{\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				in_id+","
				+"\"}";
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMedAffiInBeforeOrNext\"}";
		}	
	}
	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * 根据主键查询主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return medAffiInMapper.queryByCode(entityMap);
	}
	/**
	 * 根据主键 查明细表信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		List<?> list = medAffiInMapper.queryDetailByCode(entityMap);
		return ChdJson.toJson(list);
	}
	
	
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 查询是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 维护发票页面跳转查询
	 */
	@Override
	public String queryMedAffiInMainByInvoice(Map<String, Object> entityMap) throws DataAccessException {
		//return ChdJson.toJsonLower((List<Map<String, Object>>) medAffiInCommonMapper.queryMedAffiInMainByInvoice(entityMap));
		
		List<Map<String, Object>> list = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) medAffiInCommonMapper.queryMedAffiInMainByInvoice(entityMap));
		for(Map<String, Object> tmp : list){ 
			tmp.put("init","0");
			tmp.put("flag", "1");
			tmp.put("table", "med_affi_in_detail");
			List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(tmp);
			
			tmp.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
		}
		
		return ChdJson.toJsonLower(list);
		
	}
	
	/**
	 * 细数据是否已经全部维护发票信息
	 */
	@Override
	public String existsMedAffiInDetailByInvoice(Map<String, Object> entityMap) throws DataAccessException {
		try{
			int flag = medAffiInCommonMapper.existsMedAffiInDetailByInvoice(entityMap);
			if(flag != 0){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\"}";
			}
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedInDetailByInvoice\"}";
		}	
	}

	
	/**
	 * 获取上一张、下一张单据
	 */
	@Override
	public Map<String, Object> queryMedAffiInIds(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object>  mapVo = new HashMap<String,Object>();
		
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		
		String in_idUp = medAffiInCommonMapper.queryAffiUpOutId(entityMap);//上一张ID
		if(in_idUp == null){
			mapVo.put("in_idUp", entityMap.get("in_id"));
		}else{
			mapVo.put("in_idUp", Integer.parseInt(in_idUp.toString()));
		}
		
		String in_idNext = medAffiInCommonMapper.queryAffiNextOutId(entityMap); //下一张ID
		if(in_idNext == null){
			mapVo.put("in_idNext", entityMap.get("in_id"));
		}else{
			mapVo.put("in_idNext", Integer.parseInt(in_idNext.toString()));
		}
		
		return mapVo;
	}
	

	//入库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMedAffiInByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			//查询入库主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
				//查询入库主表
				List<Map<String, Object>> map = medAffiInCommonMapper.queryMedAffiInPrintTemlateByMainBatch(entityMap);
				List<Map<String,Object>> list=medAffiInCommonMapper.queryMedAffiInPrintTemlateByDetail(entityMap);
				
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
				
			}else{
				Map<String,Object> map=medAffiInCommonMapper.queryMedAffiInPrintTemlateByMain(entityMap);
				
				//查询明细表
				List<Map<String,Object>> list;

				if(entityMap.get("is_collect") != null && "1".equals(entityMap.get("is_collect").toString())){
					
					list=medAffiInCommonMapper.queryMedAffiInPrintTemlateByDetailCollect(entityMap);
				}else{
					
					list=medAffiInCommonMapper.queryMedAffiInPrintTemlateByDetail(entityMap);
				}
				
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	
		//查询入库明细表
		
	}
	
	@Override
	public String queryAllMedbySupId(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list=medCommonMapper.queryAllMedbySupId(entityMap);
			
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 历史引入查询明细数据
	 */
	@Override
	public String queryMedAffiInHistoryDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list=medAffiInCommonMapper.queryMedAffiInHistoryDetail(entityMap);
		return ChdJson.toJsonLower(list);
	}
	/**
	 * 历史引入生成入库单
	 */
	@Override
	public String queryMedAffiInHisDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer detailIds = new StringBuffer();
		JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
		Iterator it = json.iterator();
		while (it.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
				detailIds.append(jsonObj.get("detail_id").toString()).append(",");
			}
		}
		
		if(detailIds.length() > 0){
			entityMap.put("detailIds", detailIds.substring(0,detailIds.length()-1).toString());
		}
		
		List<Map<String,Object>> list=medAffiInCommonMapper.queryMedAffiInHistoryDetail(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 订单导入明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMedAffiInOrderDetailN(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medAffiInCommonMapper.queryMedAffiInOrderDetailN(entityMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medAffiInCommonMapper.queryMedAffiInOrderDetailN(entityMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	
	/**
	 * 导入订单保存
	 */
	@Override
	public String addAffiInByOrder(Map<String,Object> entityMap) throws DataAccessException {
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
			
			//判断当前期间是否已结账，如结账，则不能添加入库单
			flag = medCommonMapper.existsMedYearMonthIsAccount(entityMap);
			if(flag != 0){
				return "{\"error\":\"当月已结账不能保存！\",\"state\":\"false\"}";
			}
			
			
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MED_AFFI_IN");
				entityMap.put("in_no", medCommonService.getMedNextNo(entityMap));
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", medAffiInMapper.queryAffiInMainSeq());
			
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				
				/*用于查询个体码----begin-----*/
				Map<String,Object> barCodeMap = new HashMap<String,Object>();
				barCodeMap.put("group_id", entityMap.get("group_id"));
				barCodeMap.put("hos_id", entityMap.get("hos_id"));
				barCodeMap.put("type_code", 1);
				String bar_code = medAffiInMapper.queryMedPerBar(barCodeMap);//获取当前个体码
				//如果不存在则插入
				if(bar_code == null || "".equals(bar_code)){
					bar_code = "000000000000";
					barCodeMap.put("max_no", bar_code);
					medAffiInMapper.insertMedPerBar(barCodeMap);
				}
				String init_bar_code = bar_code;
				/*用于查询个体码----end-------*/
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "med_affi_in_detail");
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
						detailMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
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
									barMap.put("detail_id", medAffiInMapper.queryAffiInDetailSeq());
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
							orderRelaMap.put("in_detail_id", detailMap.get("detail_id"));
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
						medAffiInMapper.updateMedPerBar(barCodeMap);
					}
					//新增入库主表数据
					medAffiInMapper.addMedAffiInMain(entityMap);
					//新增入库明细数据
					medAffiInMapper.addMedAffiInDetail(detailList);
					
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						medAffiInMapper.insertOrderRela(orderRelaList);
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
	public void updateMedAffiOrderState(Map<String, Object> entityMap) throws DataAccessException {
		//若有删除的order_id  直接更新
		if(entityMap.containsKey("oldOrderId")){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("group_id",entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("state", "2");
			map.put("orderIds", entityMap.get("oldOrderId"));
			medAffiInCommonMapper.updateAffiOrderStates(map);
			entityMap.remove("in_id");
		}
		
		String orderIds  = medAffiInCommonMapper.queryAffiOrderIds(entityMap);
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
			medAffiInCommonMapper.updateAffiOrderStates(map);
		}
	}
	
	/**
	 * 批量添加选择药品
	 */
	@Override
	public String queryMedAffiInInvBatch(Map<String, Object> entityMap)throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medAffiInCommonMapper.queryMedAffiInInvBatch(entityMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medAffiInCommonMapper.queryMedAffiInInvBatch(entityMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 批量选择药品生成入库单
	 */
	@Override
	public String queryMedAffiInInvBatchDetail(Map<String, Object> entityMap)throws DataAccessException {
		
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
		
		List<Map<String,Object>> list=medAffiInCommonMapper.queryMedAffiInInvBatchDetail(invList);
		
		return ChdJson.toJsonLower(list);
	}
	
	//显示明细
	@Override
	public String queryDetails(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = medAffiInCommonMapper.queryDetails(entityMap);			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = medAffiInCommonMapper.queryDetails(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	@Override
	public List<Map<String, Object>> queryOrderRelaExists(Map<String, Object> entityMap) throws DataAccessException {
		return medAffiInCommonMapper.queryAffiOrderRelaExists(entityMap);
	}
	@Override
	public Map<String, Object> queryMedAffiInByPrintTemlate1(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MedAffiInCommonMapper medAffiInCommonMapper = (MedAffiInCommonMapper)context.getBean("medAffiInCommonMapper");
			
			//查询入库主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
				//查询入库主表
				List<Map<String, Object>> map = medAffiInCommonMapper.queryMedAffiInPrintTemlateByMainBatch(entityMap);
				List<Map<String,Object>> list=medAffiInCommonMapper.queryMedAffiInPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				reMap.put("detail", list);
				
			}else{
				Map<String,Object> map=medAffiInCommonMapper.queryMedAffiInPrintTemlateByMain(entityMap);
				
				//查询明细表
				List<Map<String,Object>> list;

				if(entityMap.get("is_collect") != null && "1".equals(entityMap.get("is_collect").toString())){
					
					list=medAffiInCommonMapper.queryMedAffiInPrintTemlateByDetailCollect(entityMap);
				}else{
					
					list=medAffiInCommonMapper.queryMedAffiInPrintTemlateByDetail(entityMap);
				}
				
				reMap.put("main", map);
				reMap.put("detail", list);
				
			}
			
			return reMap;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	
		//查询入库明细表
		
	}
}
